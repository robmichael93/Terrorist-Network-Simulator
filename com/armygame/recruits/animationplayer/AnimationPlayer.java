package com.armygame.recruits.animationplayer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Math;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;
import java.lang.Character;
import java.net.URL;

import com.armygame.recruits.mediaelements.CastUsage;
import com.armygame.recruits.mediaelements.CastUsageInfo;
import com.armygame.recruits.playlist.CharacterUsage;
import com.armygame.recruits.playlist.MediaPlaybackSynchronizer;
import com.armygame.recruits.playlist.MediaSynchronizationEvent;
import com.armygame.recruits.playlist.CharacterUsageInfo;
import com.armygame.recruits.locations.Location;
import com.armygame.recruits.locations.LocationMediaPlaceholder;
import com.armygame.recruits.locations.PlaceholderShadow;
import com.armygame.recruits.mediaelements.RegistrationPoint;
import com.armygame.recruits.mediaelements.MediaDimensions;
import com.armygame.recruits.globals.MasterConfiguration;
import com.armygame.recruits.globals.RecruitsConfiguration;
import com.armygame.recruits.globals.MissingAssetLog;
import com.armygame.recruits.globals.ResourceReader;
// import com.armygame.recruits.globals.CharacterRoleMap;
import com.armygame.recruits.locations.LocationAnimationPlaceholder;
import com.armygame.recruits.globals.RecruitsRandom;


/**
 * Provides a <CODE>JPanel</CODE> for drawing Recruits Animations, Film Loops, Locations, and
 * QuickTime movies.  Uses a <CODE>JLayeredPane</CODE> for allowing layering and
 * compositing of graphics into a depth-ordered scene.
 */
public class AnimationPlayer extends JPanel implements Runnable, ScreenTransition.ScreenTransitionDoneCallback {


  private class ZBufferEntry {

    private JLabel myBGImage;
    private JLabel myImage;
    private JLabel myFGImage;

    public ZBufferEntry() {
      myBGImage = myImage = myFGImage = null;
    }

    public ZBufferEntry( JLabel bgImage, JLabel image, JLabel fgImage ) {
      myBGImage = bgImage;
      myImage = image;
      myFGImage = fgImage;
    }

    public void AddBGImage( JLabel bgImage ) {
      myBGImage = bgImage;
    }

    public void AddImage( JLabel image ) {
      myImage = image;
    }

    public void AddFGImage( JLabel fgImage ) {
      myFGImage = fgImage;
    }

    public JLabel[] Images() {
      return new JLabel[] { myFGImage, myImage, myBGImage };
    }

  }


  private class LoopsTracker {

    private int myLoopCount;
    private MediaSynchronizationEvent myTestEvent;

    public LoopsTracker( int loops, MediaSynchronizationEvent testEvent ) {
      myLoopCount = loops;
      myTestEvent = testEvent;
    }

    public boolean TestLoops( String role ) {

      boolean Result = false;
      myLoopCount--;

      if ( myLoopCount == 0 ) {
        Result = true;
        if ( myTestEvent != null ) {
          ( (MediaPlaybackSynchronizer) myCallbacks.get( role ) ).TestEvent( myTestEvent );
        }
      }

      return Result;

    }

  }

  private static File theirScreenTransitionFilename;

  public final static int theirInfiniteLoopCount = Integer.MAX_VALUE;

  private final static int theirZBufferDepth = 102;

  /**
   * The default width (in pixels) of the animation player panel
   */
  public final static int theirDefaultWidth = 640;

  /**
   * The default height (in pixels) of the animation player panel
   */
  public final static int theirDefaultHeight = 480;

  /**
   * The frame redraw update interval in milliseconds computed as
   * 1000 mS / 7fps = 142mS
   */
  private final static int theirDefaultRedrawDelay = 142;

  /**
   * This delay interval (when set to 0) tells Java's <CODE>wait( int interval )</CODE>
   * construct to block indefinetly pending a <CODE>notify[All]()</CODE>
   */
  private final static int theirBlockIndefinitelyDelay = 142;

  /**
   * The width (in pixels) of the animation player panel
   */
  private int myPanelWidth;

  /**
   * The height (in pixels) of the animation player panel
   */
  private int myPanelHeight;

  /**
   * Provides a set of drawing layers for compositing scenes by stacking images on
   * layers (back-to-front).
   */
  private JLayeredPane myDrawingLayers;

  /**
   * Holds the current state of the animation player for use in correctly managing
   * state change and thread synchronization
   */
  private PlaybackState myCurrentState;

  /**
   * The time (in milliseconds) to use as a blocking interval in the executive.
   * If this is set to 0 it indicates an indefinite block per Java's
   * <CODE>wait()</CODE> construct
   */
  private int myBlockingInterval;

  /**
   * The operation the player executive should initiate the next time it is awoken
   */
  private OperationRequest myNextOperation;

  private HashMap myNamedImages;

  private HashMap myBufferedAnimations;

  private HashMap myPlayingAnimations;

  private HashMap myAnimationStagings;

  private HashMap myAnimationLabels;

  private HashMap myCallbacks;

  private ZBufferEntry[] myZBuffer;

  private HashMap myAnimationLoopCounts;

  private CharacterRoleMap myCharRoleInfo;

  private ScreenTransition myScreenTransition;

  private MediaPlaybackSynchronizer myScreenTransCallback;

  private boolean myDoingScreenTransFlag;

  private JLabel myScreenTransitionImage;

  private ImageIcon myScreenTransIcon;

  /**
   * Initializes the player with an empty content pane of default width and height and
   * initializes the playback state of the player to idle
   */
  public AnimationPlayer() {
    super();
    myCharRoleInfo = CharacterRoleMap.Instance();
    String x = MasterConfiguration.Instance().AnimationDefinitionsPath() +
                                              "/" + "Screen Transitions" +
                                              "/" + "St_Fl_1_filmloop.xml";
    theirScreenTransitionFilename = new File(x  );
    myAnimationStagings = null;
    myBufferedAnimations = new HashMap();
    myPlayingAnimations = new HashMap();
    myAnimationLabels = new HashMap();
    myCallbacks = new HashMap();
    myAnimationLoopCounts = new HashMap();
    myZBuffer = new ZBufferEntry[ theirZBufferDepth ];
    InitDrawingPane( theirDefaultWidth, theirDefaultHeight );
    InitializePlayerState();
    myNamedImages = new HashMap();
    myAnimationStagings = new HashMap();
    myScreenTransition = new ScreenTransition( this, 640, 480 );
    myDoingScreenTransFlag = false;
  }

  /**
   * Initializes the player with an empty content pane of the specified width and
   * height and initializes the playback state of the player to idle
   *
   * @param width The width of the content pane (in pixels)
   * @param height The height of the content pane (in pixels)
   */
  public AnimationPlayer( int width, int height ) {
    super();
    myCharRoleInfo = CharacterRoleMap.Instance();
    theirScreenTransitionFilename = new File( MasterConfiguration.Instance().AnimationDefinitionsPath() +
                                              "/" + "Screen Transitions" +
                                              "/" + "St_Fl_1_filmloop.xml" );
    myAnimationStagings = null;
    myBufferedAnimations = new HashMap();
    myPlayingAnimations = new HashMap();
    myAnimationLabels = new HashMap();
    myCallbacks = new HashMap();
    myAnimationLoopCounts = new HashMap();
    myZBuffer = new ZBufferEntry[ theirZBufferDepth ];
    InitDrawingPane( width, height );
    InitializePlayerState();
    myScreenTransition = new ScreenTransition( this, 640, 480 );
    myDoingScreenTransFlag = false;
  }

  /**
   * Initializes an empty content pane with no layout manager and the specified width and height
   *
   * @param width The width of the content pane (in pixels)
   * @param height The height of the content pane (in pixels)
   */
  private void InitDrawingPane( int width, int height ) {

    setBounds( 0, 0, width, height );
    setBackground( Color.black );
    setLayout( null );
    setBorder( null );
    Rectangle Blah = getBounds();
    // System.out.println( "Bounds of Drawing pane to draw are " + Blah.toString() );

    for( int i = 0; i < myZBuffer.length; i++ ) {
      myZBuffer[ i ] = null;
    }

    myNamedImages = new HashMap(); // Collections.synchronizedMap( new HashMap() );
    myDrawingLayers = new JLayeredPane();
    myDrawingLayers.setBackground( Color.black );
    myDrawingLayers.setDoubleBuffered( true );

    // We don't want a layout manager - we'll place things in the pane using good ol' (x,y) coords
    myDrawingLayers.setLayout( null );
    myDrawingLayers.setLocation( 0, 0 );
    myDrawingLayers.setPreferredSize( new Dimension( width, height ) );
    myDrawingLayers.setSize( myDrawingLayers.getPreferredSize() );
    myDrawingLayers.setVisible(true);
    myDrawingLayers.setBorder( null );
    myDrawingLayers.setBounds( 0, 0, 640, 480 );
    Blah = myDrawingLayers.getBounds();
    // System.out.println( "Bounds of layers draw are " + Blah.toString() );

    // Put the content pane into the encompassing panel
    add( myDrawingLayers );
  }

  private void InitializePlayerState() {
    myNextOperation = OperationRequest.NOOP;
    myCurrentState = PlaybackState.PLAYING_ANIMATION; // was IDLE
    myBlockingInterval = theirDefaultRedrawDelay; // was BlockIndefinitelyDelay;
  }

  public synchronized void PreBufferInit() {
    myNamedImages.clear();
    myAnimationLabels.clear();
    myBufferedAnimations.clear();
  }

  public synchronized void BufferImages( CastUsage castUse ) {
    List AllImages = castUse.EnnumerateUsage();
    Iterator Itr = AllImages.iterator();
    String BasePath = MasterConfiguration.Instance().LocationImagesPath();
    while( Itr.hasNext() ) {
      CastUsageInfo ImageInfo = (CastUsageInfo) Itr.next();
      // System.out.println( "Cast data for image -> " + ImageInfo.CastName() + ", " + ImageInfo.CastMemberName() );
      if ( ImageInfo.CastName().equalsIgnoreCase( "Film Loops" ) ) {
        File FilmLoopFilename = new File( MasterConfiguration.Instance().AnimationDefinitionsPath() +
                                          "/" + "Film Loops" +
                                          "/" + ImageInfo.CastMemberName() + "_filmloop" + ".xml" );
        // System.out.println( "Film loop file would be " + FilmLoopFilename.toString() );
        Animation FilmLoopAnimation = new Animation( this, FilmLoopFilename.toString(), ImageInfo.UniqueName(), "filmloop" );
        FilmLoopAnimation.ReadAnimationFrames();
        myBufferedAnimations.put( ImageInfo.UniqueName(), FilmLoopAnimation );
      } else {
        File ImageFile = new File( BasePath + "/" + ImageInfo.Filename() );
        // System.out.println( "Loading image file " + ImageFile.toString() );
        URL ImageAssetURL = ResourceReader.instance().getURL( ImageFile.toString() );
        if ( ImageAssetURL != null ) {
          myNamedImages.put( ImageInfo.UniqueName(), new ImageIcon( ImageAssetURL ) );
        } else {
          MissingAssetLog.Instance().AddMissingAsset( ImageFile.toString() );
        }
      }
    }
  }

  public synchronized void BufferAnimationFrames( CharacterUsage charUse ) {
    // System.out.println( "In BufferAnimationFrames" );
    // InitializeScreenTransition();
    List CharacterAnimations = charUse.EnnumerateUsage();
    Iterator Itr = CharacterAnimations.iterator();
    while( Itr.hasNext() ) {
      CharacterUsageInfo CharInfo = (CharacterUsageInfo) Itr.next();
      System.out.println( "CharInfo is " + CharInfo.Name().toString() + "," + CharInfo.AnimationName().toString() );
      CharacterRoleMap.RoleInfo AnimInfo = myCharRoleInfo.GetInfo( CharInfo.Name() );
      if ( AnimInfo == null ) {
        continue;
      }
      AnimInfo.SetActionPose( CharInfo.AnimationName() );
      String CharName = AnimInfo.Name();
      // System.out.println( "Char name is " + CharName );
      File AnimationFilename = new File ( MasterConfiguration.Instance().AnimationDefinitionsPath() +
                                          "/" + CharName +
                                          "/" + CharName +
                                          "_" + CharInfo.AnimationName() +
                                          "_" + AnimInfo.Uniform() +
                                          "_" + AnimInfo.Hat() + ".xml" );
      // System.out.println( "AnimationFilename is " + AnimationFilename );
      // System.out.println( "New Animation Under " + AnimInfo.Name() + ", " + CharInfo.AnimationName() );
      Animation NewAnimation = new Animation( this, AnimationFilename.toString(), AnimInfo.Name(), CharInfo.AnimationName() );
      NewAnimation.ReadAnimationFrames();
      myBufferedAnimations.put( AnimInfo.Name() + CharInfo.AnimationName(), NewAnimation );
    }
  }

  public void run() {
    PlayerExecutive();
  }

  private synchronized void PlayerExecutive() {

    boolean Running = true;

    while( Running ) {

      boolean Waiting = true;

      // Our blocking strategy depends upon whether we are drawing animations or not.
      // If we are updating animations we periodically wake up from our wait to refresh the
      // animations, otherwise we block indefinetly waiting for a notification that there is
      // new work to do
      try {
        while( Waiting ) {
          wait( myBlockingInterval );
          Waiting = false;
        }
      } catch( InterruptedException e ) {
        Running = false;
        return;
      }

      // Handle the new request
      if ( myCurrentState.equals( PlaybackState.PLAYING_ANIMATION ) ) {
        UpdateAnimations();
      } else if ( myCurrentState.equals( PlaybackState.PLAYING_MOVIE ) ) {
        myBlockingInterval = theirBlockIndefinitelyDelay;
      } else if ( myCurrentState.equals( PlaybackState.IDLE_DIRTY ) ) {
        UpdateGraphics();
      } else if ( myCurrentState.equals( PlaybackState.QUIT ) ) {
        Running = false;
      }

    }

  }

  private void UpdateAll() {
    UpdateGraphics();
    UpdateAnimations();
  }

  private void UpdateGraphics() {

  }

  private void UpdateAnimations() {
    Iterator AnimItr = myPlayingAnimations.values().iterator();
    while( AnimItr.hasNext() ) {
      Animation AnimationToUpdate = (Animation) AnimItr.next();
      AnimationToUpdate.DrawFrame();
    }
  }

  private void EraseGraphics() {
    myDrawingLayers.removeAll();
    myCurrentState = PlaybackState.PLAYING_ANIMATION; // was IDLE
  }

  public void ScreenTransitionStep() {
    repaint();
  }

  public void ScreenTransitionDone() {
    myDoingScreenTransFlag = false;
    myScreenTransCallback.TestEvent( new MediaSynchronizationEvent( "Screen Transition Complete", "FADE_TO_BLACK" ) );
  }

  public void InitializeScreenTransition() {
    myScreenTransition.Reset();
    // System.out.println( "Initializing Screen Transition" );
    // Animation ScreenTransitionAnimation = new Animation( this, theirScreenTransitionFilename.toString(), "Film LoopsSt_Fl_1", "filmloop" );
    // ScreenTransitionAnimation.ReadAnimationFrames();
    // myBufferedAnimations.put( "Film LoopsSt_Fl_1", ScreenTransitionAnimation );
  }

  public synchronized void ScreenTransition( MediaPlaybackSynchronizer callback ) {
    myScreenTransCallback = callback;
    myScreenTransition.Reset();
    myDoingScreenTransFlag = true;
/*     myScreenTransIcon = new ImageIcon( myScreenTransition.GetImage() ); */
/*     myScreenTransitionImage = new JLabel( myScreenTransIcon );          */
/*     myScreenTransitionImage.setBorder( null );                          */
/*     myScreenTransitionImage.setBounds( 0, 0, 640, 480 );                */
/*     myScreenTransitionImage.setVerticalAlignment( SwingConstants.TOP ); */
/*     myZBuffer[ 101 ] = myScreenTransitionImage;                         */
/*     AppendToZBuffer( 101 );                                             */
/*     RenderZBuffer();                                                    */
    myScreenTransition.Start();
  }

  public synchronized void SetLocation( MediaPlaybackSynchronizer callback, Location locationToDraw ) {
    //System.out.println( "Start SetLocation" );
    Clear();
    myAnimationStagings = locationToDraw.GetAnimationStagings();
    ArrayList ImageDescriptions = locationToDraw.GetImagePlaceholders();
    // System.out.println( "This location " + locationToDraw.Name() + " has " + ImageDescriptions.size() + " placeholders" );
    for( int i = 0, len = ImageDescriptions.size(); i < len; i++ ) {
      //System.out.println( "i = " + i );
      LocationMediaPlaceholder Layout = (LocationMediaPlaceholder) ImageDescriptions.get( i );
      // System.out.println( "Cast name is " + Layout.CastName() + ", Cast Member is " + Layout.CastMemberName() );
      String CastRole = Layout.CastName() + Layout.CastMemberName();
      RegistrationPoint RegPt = Layout.RegistrationPoint();
      MediaDimensions BBox = Layout.BoundingBox();
      // If this placeholder is a film loop we need to register its animation
      if ( Layout.CastName().equalsIgnoreCase( "Film Loops" ) ) {
        // System.out.println( "Got a film loop" );
        Animation FilmLoopAnimation = (Animation) myBufferedAnimations.get( CastRole );
        ImageIcon FilmLoopIcon = new ImageIcon( ( (ImageIcon) FilmLoopAnimation.GetFirstFrame() ).getImage() );
        JLabel FilmLoopImage = new JLabel( FilmLoopIcon );
        FilmLoopImage.setBorder( null );
        myAnimationLabels.put( CastRole, FilmLoopImage );
        RegistrationPoint FilmLoopRegPt = Layout.RegistrationPoint();
        MediaDimensions FilmLoopBBox = Layout.BoundingBox();
        FilmLoopImage.setBounds( FilmLoopRegPt.RegX() - FilmLoopBBox.Width() / 2,
                                 FilmLoopRegPt.RegY() - FilmLoopBBox.Height() + 1,
                                 FilmLoopBBox.Width(), FilmLoopBBox.Height() );
        ZBufferEntry FLImage = new ZBufferEntry( null, FilmLoopImage, null );
        myZBuffer[ Layout.Layer() ] = FLImage;
        FilmLoopAnimation.Play( CastRole, "FIX ME" );
        myPlayingAnimations.put( CastRole, FilmLoopAnimation );
        myAnimationLoopCounts.put( CastRole, new LoopsTracker( theirInfiniteLoopCount, null ) );
      } else {
        ImageIcon TempIcon = (ImageIcon) myNamedImages.get( CastRole );
/*jmb*/    if(TempIcon == null)
 System.out.println("Error...AnimPlayer.setLocation(): CastRole = "+CastRole);
/*jmb*/
        JLabel NewImage = new JLabel( TempIcon );
        NewImage.setBorder( null );
        // System.out.println( CastRole + " Image Reg Pt(" + RegPt.RegX() + "," + RegPt.RegY() + ") - BBox(" + BBox.Width() + "," + BBox.Height() + ") - > (" + ( RegPt.RegX() - BBox.Width() / 2 ) + "," + Math.max( 0, RegPt.RegY() - TempIcon.getIconHeight() ) + ")" );
        // NewImage.setBounds( RegPt.RegX() - BBox.Width() / 2, RegPt.RegY() - BBox.Height() / 2, BBox.Width(), BBox.Height() );
        // int Temp1 = RegPt.RegY() - BBox.Height();
        // int Temp2 = BBox.Height() - TempIcon.getIconHeight();
        // int Temp3 = Temp1 + Temp2;
        // if ( Temp3 < 0 ) {
        //   Temp3 = 0;
        // }
        // NewImage.setBounds( RegPt.RegX() - BBox.Width() / 2, RegPt.RegY() - BBox.Height() + 1, BBox.Width(), BBox.Height() );
        NewImage.setBounds( RegPt.RegX() - BBox.Width() / 2, Math.max( 0, RegPt.RegY() - TempIcon.getIconHeight() ), BBox.Width(), BBox.Height() );
        Rectangle Blah = NewImage.getBounds();
        // System.out.println( "Bounds of image to draw are " + Blah.toString() );
        ZBufferEntry ImageEntry = new ZBufferEntry( null, NewImage, null );
        myZBuffer[ Layout.Layer() ] = ImageEntry;
        // myDrawingLayers.add( NewImage, Layout.Layer() );
      }
    }


    // Set placeholders for all the character stagings
    Set RolesUsed = myAnimationStagings.keySet();

    Iterator PlaceholderItr = RolesUsed.iterator();
    while( PlaceholderItr.hasNext() ) {
      String StageName = (String) PlaceholderItr.next();
      LocationAnimationPlaceholder AnimationLocation = (LocationAnimationPlaceholder) myAnimationStagings.get( StageName );
      if ( AnimationLocation.IsUsed() ) {
        String ParsedRole = AnimationLocation.RoleName();
        System.out.println( ">>>> USED STAGING <<<< " + ParsedRole );
        // myCharRoleInfo.AddRole( RoleName, AnimationLocation.Uniform(), AnimationLocation.Hat(),
        //                      com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( RoleName ).getActorName() );
        RegistrationPoint CharRegPt = AnimationLocation.RegistrationPoint();
        MediaDimensions CharBBox = AnimationLocation.BoundingBox();
        Animation CharAnimation = (Animation) myBufferedAnimations.get( myCharRoleInfo.GetInfo( ParsedRole ).Name() + myCharRoleInfo.GetInfo( ParsedRole ).GetActionPose() );
 /*jmb*/if(CharAnimation == null)
 /*jmb*/  continue;
        ImageIcon AnimationIcon = new ImageIcon( ( (ImageIcon) CharAnimation.GetFirstFrame() ).getImage() );
        if ( AnimationIcon == null ) {
          System.out.println( "Animation Icon Is NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL" );
        }
        JLabel CharImage = new JLabel( AnimationIcon );
        CharImage.setBorder( null );
        // System.out.println( "++++++ Putting AnimationLabel " + RoleName );
        // myAnimationLabels.put( RoleName, CharImage );
        myAnimationLabels.put( com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( ParsedRole ).getActorName(), CharImage );
        CharImage.setBounds( CharRegPt.RegX() - CharBBox.Width() / 2, CharRegPt.RegY() - CharBBox.Height() + 1, CharBBox.Width(), CharBBox.Height() );
        // Rectangle Blah = CharImage.getBounds();
        // System.out.println( "Bounds of char to draw are " + Blah.toString() );
        // myDrawingLayers.add( CharImage, AnimationLocation.Layer() );

        // We need to correctly sandwhich the first frame of the animation with an (optional) shadow.
        // The shadow can either be in front of or behind the character animation frames, but are part
        // of the same JLayeredPane layer (ordering handled by order drawn to the layer
        //
        ZBufferEntry PlaceholderImages = new ZBufferEntry();
        PlaceholderImages.AddImage( CharImage );
        PlaceholderShadow Shadow = AnimationLocation.Shadow();
        if ( Shadow != null ) {
          RegistrationPoint ShadowRegPt = Shadow.RegPoint();
          MediaDimensions ShadowBBox = Shadow.BBox();
          File ShadowFile = new File(  MasterConfiguration.Instance().LocationImagesPath() + "/" + "All" + "/" + Shadow.ImageName() );
          URL ShadowURL = ResourceReader.instance().getURL( ShadowFile.toString() );
          ImageIcon ShadowIcon = new ImageIcon( ShadowURL );
          JLabel ShadowImage = new JLabel( ShadowIcon );
          ShadowImage.setBorder( null );
          ShadowImage.setBounds( ShadowRegPt.RegX() - ShadowBBox.Width() / 2, ShadowRegPt.RegY() - ShadowBBox.Height() + 1, ShadowBBox.Width(), ShadowBBox.Height() );
          if ( Shadow.InFront() ) {
            PlaceholderImages.AddFGImage( ShadowImage );
          } else {
            PlaceholderImages.AddBGImage( ShadowImage );
          }
        }
        myZBuffer[ AnimationLocation.Layer() ] = PlaceholderImages;
      }
    }


    // Set up a transparent screen transition icon on top of everything
    myScreenTransIcon = new ImageIcon( myScreenTransition.GetImage() );
    myScreenTransitionImage = new JLabel( myScreenTransIcon );
    myScreenTransitionImage.setBorder( null );
    myScreenTransitionImage.setBounds( 0, 0, 640, 480 );
    myScreenTransitionImage.setVerticalAlignment( SwingConstants.TOP );
    myZBuffer[ 101 ] = new ZBufferEntry( null, myScreenTransitionImage, null );
    AppendToZBuffer( 101 );

    RenderZBuffer();

    //System.out.println( "End SetLocation" );
    // myCurrentState = PlaybackState.IDLE_DIRTY;
    // Play();
    callback.TestEvent( new MediaSynchronizationEvent( "Location Completed", locationToDraw.Name() ) );
  }

  private void AppendToZBuffer( int index ) {
    myDrawingLayers.add( myZBuffer[ index ].Images()[1], 0 );
  }

  private void RenderZBuffer() {
    myDrawingLayers.removeAll();
    for( int i = 0; i < myZBuffer.length; i++ ) {
      if ( myZBuffer[ i ] != null ) {
        JLabel[] Images = myZBuffer[ i ].Images();
        for( int j = 0; j < Images.length; j++ ) {
          if ( Images[ j ] != null ) {
            myDrawingLayers.setLayer( Images[ j ], i );
            myDrawingLayers.add( Images[ j ] );
          }
        }
      }
    }
  }


  public synchronized void Clear() {
    // myCharRoleInfo.Clear();
    myDrawingLayers.removeAll();
    myCallbacks.clear();
    myPlayingAnimations.clear();
    if ( myCurrentState.equals( PlaybackState.IDLE_DIRTY ) ) {
      EraseGraphics();
    }
  }

  public synchronized void AddAnimation() {
  }

  public synchronized void RemoveAnimation() {
  }

  public synchronized void RemoveAllAnimations() {
  }

  public synchronized void PlayAnimation( MediaPlaybackSynchronizer callback, String role, String action, int loops, MediaSynchronizationEvent testEvent ) {
    String CharName = com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( role ).getActorName();
    System.out.println( "Asked to play animation (" + CharName + "," + action + ")" );
    myCallbacks.put( CharName, callback );
    Animation PlayingAnimation = (Animation) myBufferedAnimations.get( CharName + action );
    PlayingAnimation.Play( CharName, role );
    // PlayingAnimation.Play( CharName );
    // myPlayingAnimations.put( CharName, PlayingAnimation );
    myPlayingAnimations.put( role, PlayingAnimation );
    myAnimationLoopCounts.put( CharName, new LoopsTracker( loops, testEvent ) );
    // Play();
  }

  public synchronized void PlayAnimation( MediaPlaybackSynchronizer callback, String role, String action ) {
    PlayAnimation( callback, role, action, theirInfiniteLoopCount, null );
  }

  public synchronized void PlayAnimation( MediaPlaybackSynchronizer callback, String role, String action, int loops ) {
    PlayAnimation( callback, role, action, loops, null );
  }

  public synchronized void Play() {
    myBlockingInterval = theirDefaultRedrawDelay;
    myCurrentState = PlaybackState.PLAYING_ANIMATION;
    notifyAll();
  }

  public synchronized void Pause() {
  }

  public synchronized void Resume() {
  }

  public synchronized void TakeExit( String role ) {
    System.out.println( role + " asked to take exit" );
    ( (Animation) myPlayingAnimations.get( role ) ).TakeExit();
    // ( (MediaPlaybackSynchronizer) myCallbacks.get( exitingAnim ) ).TestEvent( new MediaSynchronizationEvent( "TakeExit", exitingAnim ) );
  }

  public synchronized void TookExit( String exitingAnim, String animID ) {
    System.out.println( exitingAnim + " took exit" );
    ( (MediaPlaybackSynchronizer) myCallbacks.get( exitingAnim ) ).TestEvent( new MediaSynchronizationEvent( "TookExit", animID ) );
  }

  public synchronized void Quit() {
    myCurrentState = PlaybackState.QUIT;
    notifyAll();
  }

  public synchronized void DrawFrame( String role, ImageIcon thingToDraw ) {
    // System.out.println( "Player drawing frame with role=" + role + "(" + thingToDraw.getIconWidth() + "," + thingToDraw.getIconHeight() + ")" );
    ( (JLabel) myAnimationLabels.get( role ) ).setIcon( thingToDraw );
  }

  public synchronized void EndOfLoop( String role ) {
    LoopsTracker Tracker = (LoopsTracker) myAnimationLoopCounts.get( role );
    boolean EndTest = Tracker.TestLoops( role );

    if( EndTest == true ) {
      // We need to change the animation that just ended to the correct default idle animation
      System.out.println( "EndOfLoop for " + role );
    }
  }

  private void Stop() {
    synchronized( myCurrentState ) {
      Collection AnimsToClear = myPlayingAnimations.values();
      Iterator AnimClearItr = AnimsToClear.iterator();
      while( AnimClearItr.hasNext() ) {
        Animation AnimationToClear = (Animation) AnimClearItr.next();
        AnimationToClear.Done();
      }
      myPlayingAnimations.clear();
      myDrawingLayers.removeAll();
      myNamedImages.clear();
      myAnimationStagings.clear();
      myBufferedAnimations.clear();
      myAnimationLabels.clear();
      myCallbacks.clear();
      for( int i = 0; i < myZBuffer.length; i++ ) {
        myZBuffer[ i ] = null;
      }
    }
  }

  public synchronized void SceneDone() {
    Stop();
  }

}
