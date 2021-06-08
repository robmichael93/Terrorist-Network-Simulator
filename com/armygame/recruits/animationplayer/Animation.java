package com.armygame.recruits.animationplayer;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.accessibility.*;
import java.util.Hashtable;
import java.net.URL;


import com.armygame.recruits.globals.MasterConfiguration;
import com.armygame.recruits.globals.ResourceReader;
import com.armygame.recruits.globals.MissingAssetLog;
// import com.armygame.recruits.globals.CharacterRoleMap;
import com.armygame.recruits.xml.DefaultXMLSerializer;


/**
 * Maintains the images and animation playback state of an animation.  Uses a
 * <CODE>AnimationFrameFactory</CODE> to construct the correct type of
 * <CODE>BranchingFrame</CODE> or <CODE>NonBranchingFrame</CODE> from an
 * <CODE>AnimationFramePool</CODE> for efficiency.  Knows how to parse an XML
 * description of an animation and its frames.  Also maintains the executin state
 * of a playing animation, including logic to branch to the correct next frame in an
 * animation sequence, and to have animations take intelligent exit actions by having
 * the animation default to the correct idle animation as an intermediate behavior
 * when an animation has been given no further instructions (thus the animation
 * knows how to "take care of itself" in the absence of instructions so that characters
 * don't "freeze up").
 */
public class Animation extends DefaultXMLSerializer {


  abstract private class FrameRenderer {
    abstract public void DrawFrame();
  }

  private class PlayingFrameRenderer extends FrameRenderer {

    public void DrawFrame() {

      System.out.println( myRoleName + " Frame # " + myCurrentFrame );
      AnimationFrame CurrentFrame = myAnimationFrames[ myCurrentFrame ];

      myAnimationPlayer.DrawFrame( myRoleName, myFrameImages[ myCurrentFrame ] );
      myCurrentFrame = CurrentFrame.NextFrame();

      // Tell the animation player if we've reached the end of this animation so it can track
      // loop counts
      if ( myCurrentFrame == ( myAnimationFrames.length - 1 ) ) {
        // myAnimationPlayer.EndOfLoop( myRoleName );
        myAnimationPlayer.EndOfLoop( myRoleName );
        // System.out.println( "Last frame -> " + myCurrentFrame );
      }

      if ( myCurrentState.equals( AnimationState.TAKING_EXIT ) ) {
        System.out.println( "Animation trying to take exit " + myRoleName );
        if ( CurrentFrame.CanExit() ) {
          System.out.println( "Did exit " + myRoleName );
          myCurrentState = AnimationState.DEFAULT_IDLING;
          myAnimationPlayer.TookExit( myRoleName, myID );
          // myAnimationPlayer.TookExit( myID );
        } else {
          System.out.println( "Not ready to exit " + myRoleName );
        }
      }

    }

  }

  private class NonPlayingFrameRenderer extends FrameRenderer {
    public void DrawFrame() {
      return;
    }
  }

  private class MotionPathFrameRenderer extends FrameRenderer {
    public void DrawFrame() {
      return;
    }
  }

  /**
   * The frame number in the animation sequence of the currently displayed frame
   * <B>Important Note:</B> Animation frame sequences are 1-based even though implemented
   * as a Java <B>array</B>, the zeroth slot is unused as a frame sequence number
   */
  private int myCurrentFrame;

  /**
   * This field is used during the parsing of the frames from XML to remember the index
   * at which to add the next frame.  <B>Important Note:</B> Animation frame sequences are
   * 1-based event though implemented as a Java <B>array</B>, the zeroth slot is unused as
   * a frame sequence number
   */
  private int myConstructionCurrentFrame;

  /**
   * The factory that knows how to obtain the correct type of <CODE>AnimationFrame</CODE>
   * (either a <CODE>NonBranchingFrame</CODE> or a <CODE>BranchingFrame</CODE> from
   * a pre-generated pool of frame objects.  Because it is <B>static</B>, all animations
   * pull from the same frame pool.
   */
  private static AnimationFrameFactory theirFrameFactory = new AnimationFrameFactory( 500 );

  /**
   * The sequence of <CODE>AnimationFrame</CODE>s for this animation.  The index of the
   * frame corresponds to the frames sequence number in the animation.
   */
  private AnimationFrame[] myAnimationFrames;

  private ImageIcon[] myFrameImages;

  /**
   * The name of the action that these frames represent
   */
  private String myAction;

  /**
   * The current playback state of this animation
   */
  private AnimationState myCurrentState;

  /**
   * The file name of the file that contains the XML description of the frames in this
   * animation.
   */
  private String myFilename;

  private String myUniform;

  private String myHat;

  private String myCharacter;

  private AnimationPlayer myAnimationPlayer;

  private FrameRenderer myFrameRenderer;

  private FrameRenderer myNonPlayingFrameRenderer; // new NonPlayingFrameRenderer();
  private FrameRenderer myPlayingFrameRenderer; // new PlayingFrameRenderer();

  private String myRoleName;
  private String myID;

  private Hashtable myLoadedFrameNames;

  /**
   * Constructs a new, <B>UNINITIALIZED</B> animation.  Clients using <CODE>Animation</CODE>
   * objects should call <B>ReadAnimationFrames()</B> after construction to read the
   * animation frame branching description and initialize the animation for playback.
   *
   * @param fileName The name of the file that contains the XML description of the animation's frames
   * @param action The name of the action that corresponds to this animation
   */
  public Animation( AnimationPlayer player, String fileName, String character, String action ) {

    super();

    myLoadedFrameNames = new Hashtable();
    myPlayingFrameRenderer = new PlayingFrameRenderer();
    myNonPlayingFrameRenderer = new NonPlayingFrameRenderer();

    myAnimationPlayer = player;
    myFilename = fileName;
    myCharacter = character;
    myRoleName = null;
    myAction = action;
    myCurrentFrame = 0;
    myAnimationFrames = null;
    myFrameImages = null;
    myUniform = null;
    myHat = null;
    myCurrentState = AnimationState.UNINITIALIZED;
    myFrameRenderer = myNonPlayingFrameRenderer;
    myConstructionCurrentFrame = 0;
  }

  /**
   * Parses the XML description of the animation's frames.  After a sucessful parse
   * the animation is initialized and ready for playback
   */
  public void ReadAnimationFrames() {
    // Sets things up so we start adding new frames at position 1 (0th slot skipped)
    System.out.println( "Animation filename: " + myFilename );
    myConstructionCurrentFrame = 1;
    Parse( this, myFilename );
    myCurrentFrame = 1;
    myCurrentState = AnimationState.INITIALIZED;
  }

  public void SetRole( String role ) {
    myRoleName = role;
  }

  public void SetFrameCount( int frameCount ) {
    myAnimationFrames = new AnimationFrame[ frameCount + 1 ];
    myFrameImages = new ImageIcon[ frameCount + 1 ];
  }

  public void SetUniform( String uniform ) {
    myUniform = uniform;
  }

  public void SetHat( String hat ) {
    myHat = hat;
    // if ( hat.equalsIgnoreCase( "yes" ) ) {
    //   myHat = "H";
    // } else if ( hat.equalsIgnoreCase( "no" ) ) {
    //   myHat = "NH";
    // }
  }

  public ImageIcon GetFirstFrame() {
    // Zeroth frame is unused in animations - first frame is in slot 1
    return myFrameImages[ 1 ];
  }

  private static String MakeFilename( String character, String action, String uniform, String hat, String frameImageIndex ) {
    // System.out.println( "Checking character for role " + character );
    String Character = character;
    // File Result = new File( MasterConfiguration.Instance().AnimationImagesRootPath() + "/" + Character + "/" + Character + "_" + action + "_" + uniform + "_" + hat + "_" + frameImageIndex + ".png" );
    File Result = null;
    if ( character.equals( "Film LoopsSt_Fl_1" ) ) {
      Result = new File( MasterConfiguration.Instance().AnimationImagesRootPath() + "/screentrans/screentrans_filmloop_BDUS_NH_" + frameImageIndex + ".png" );
    } else {
      Result = new File( MasterConfiguration.Instance().AnimationImagesRootPath() + "/" + Character + "/" + Character + "_" + uniform + "_" + hat + "_" + frameImageIndex + ".png" );
    }
    // System.out.println( "Loading animation " + System.getProperty( "user.dir" ) + "/" + Result.toString() );
    return Result.toString();
  }

  private void AddFrame( AnimationFrame newFrame, String frameImageIndex ) {
    myAnimationFrames[ myConstructionCurrentFrame ] = newFrame;
    String Filename = MakeFilename( myCharacter, myAction, myUniform, myHat, frameImageIndex );
    if ( myLoadedFrameNames.containsKey( Filename ) ) {
      // System.out.println( "Saving ++++++++++++++++" );
      myFrameImages[ myConstructionCurrentFrame ] = (ImageIcon) myLoadedFrameNames.get( Filename );
    } else {
      URL ImageAssetURL = ResourceReader.instance().getURL( Filename );
      if ( ImageAssetURL != null ) {
        myFrameImages[ myConstructionCurrentFrame ] = new ImageIcon( ImageAssetURL );
        myLoadedFrameNames.put( Filename, myFrameImages[ myConstructionCurrentFrame ] );
      } else {
        MissingAssetLog.Instance().AddMissingAsset( Filename );
      }
    }
    // System.out.println( "Adding frame with " + myFrameImages[ myConstructionCurrentFrame ].getIconWidth() + "," + myFrameImages[ myConstructionCurrentFrame ].getIconHeight() );
    myConstructionCurrentFrame++;
  }

  public void AddSimpleFrame( int frameNum, String frameImageIndex, String isKeyframe ) {
    boolean KeyFrameStatus = ( isKeyframe.equalsIgnoreCase( "yes" ) ) ? true : false;
    if ( KeyFrameStatus == true ) {
      System.out.println( "************** Adding keyframe ******************** " + frameNum );
    }
    AnimationFrame NewFrame = theirFrameFactory.MakeAnimationFrame( frameNum, KeyFrameStatus );
    AddFrame( NewFrame, frameImageIndex );
  }

  public void AddBranchingFrame( int frameNum, String frameImageIndex, String isKeyframe, int next1, int next2 ) {
    boolean KeyFrameStatus = ( isKeyframe.equalsIgnoreCase( "yes" ) ) ? true : false;
    if ( KeyFrameStatus == true ) {
      System.out.println( "************** Adding keyframe ******************** " + frameNum );
    }
    AnimationFrame NewFrame = theirFrameFactory.MakeAnimationFrame( frameNum, KeyFrameStatus, next1, next2 );
    AddFrame( NewFrame, frameImageIndex );
  }

  public void Play( String role, String id ) {
    myRoleName = role;
    myID = id;
    myFrameRenderer = myPlayingFrameRenderer;
    myCurrentState = AnimationState.PLAYING;
  }

  public void Pause() {
    myFrameRenderer = myNonPlayingFrameRenderer;
    myCurrentState = AnimationState.PAUSED;
  }

  public void TakeExit() {
    myCurrentState = AnimationState.TAKING_EXIT;
  }

  public void DrawFrame() {
    myFrameRenderer.DrawFrame();
  }

  public void Done() {

    myFrameRenderer = myNonPlayingFrameRenderer;
    myCurrentState = AnimationState.DONE;

    for( int i = 0, len = myAnimationFrames.length; i < len; i++ ) {
      theirFrameFactory.ReturnFrame( myAnimationFrames[ i ] );
      myAnimationFrames[ i ] = null;
      myFrameImages[ i ] = null;
    }

    myAnimationFrames = null;
    myFrameImages = null;

  }

}

