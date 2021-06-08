/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.playlist;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.armygame.recruits.playlist.CharacterUsage;
import com.armygame.recruits.mediaelements.CastUsage;
import com.armygame.recruits.locations.LocationUsage;
import com.armygame.recruits.locations.Location;
import com.armygame.recruits.globals.SentenceGrammarMap;
import com.armygame.recruits.interaction.generativegrammar.PhraseParser;
import com.armygame.recruits.locations.RelativePlacementTable;


/**
 * An <code>ActionFrame</code> contains the specification for what media events are to occur
 * together as a group in a <code>Playlist</code>.  Each <code>ActionFrame</code> contains
 * the following sections:
 * 1)  <b>Media Actions</b> - Specifies the operations on the media playback channels (Sound, Animation, Screen Transitions) for starting and stopping playback of media.
 * 2)  <b>Exit Criteria</b> - Specifies the synchronization policy used to determine when this frame's actions are complete and it is OK to transition to the next <code>ActionFrame</code> in the <code>Playlist</code>.
 * 3)  <b>Exit Actions</b> - Specifies a set of actions to be performed when exiting this <code>ActionFrame</code> - for example, telling the frame to report back to Java any game state changes that should be permanently committed as a result of the player having viewed this frame's actions in their entirety.
 *
 */
public class ActionFrame {

  /**
   * Each frame contains a documentation string for use in debugging
   */
  private String myDocumentation;

  /**
   * The string to appear in the status window
   */
  private String myStatusString;

  /**
   * The sequential list of all media control actions in this frame.  The sequential ordering
   * is enforeced as the order in which calls to <code>AddMediaAction</code> are made via the
   * following public methods: <code>SetLocation()</code>, <code>PlaySound()</code>,
   * <code>PlayAnimation()</code>
   */
  private ArrayList myMediaActions;


  /**
   * The synchronization policy to use to determine when this frame is finished.
   */
  private ActionSynchronizationPolicy mySynchronizationPolicy;

  /**
   * The sequential list of exit actions in this frame.  The sequential ordering is
   * enforced as the order in which calls to <code>AddExitAction()</code> are made
   */
  private ExitActions myExitActions;

  /**
   * The summary of Cast usage for all the media actions added to this frame
   */
  private CastUsage myCastUsage;

  /**
   * The summary of Character usage for all the media actions added to this frame
   */
  private CharacterUsage myCharacterUsage;

  /**
   * The summary of Location usage for all the media actions added to this frame
   */
  private LocationUsage myLocationUsage;

  private boolean myForceSyncTestFlag;

  private boolean myDefinesLocationFlag;

  private boolean myDefinesAnimationFlag;


  /**
   * Construct an empty <code>ActionFrame</code> with a comment as its documentation
   * @param doc The documentation string that tells us where this <code>ActionFrame</code> came from in the Story Engine/Scene
   */
  public ActionFrame( String doc ) {
    myDocumentation = doc;
    myStatusString = "";
    myMediaActions = new ArrayList();
    mySynchronizationPolicy = null;
    myExitActions = new ExitActions();
    myCastUsage = new CastUsage();
    myCharacterUsage = new CharacterUsage();
    myLocationUsage = new LocationUsage();
    myForceSyncTestFlag = false;
    myDefinesLocationFlag = false;
    myDefinesAnimationFlag = false;
  }

  public ActionFrame( String doc, String statusString ) {
    myDocumentation = doc;
    myStatusString = statusString;
    myMediaActions = new ArrayList();
    mySynchronizationPolicy = null;
    myExitActions = new ExitActions();
    myCastUsage = new CastUsage();
    myCharacterUsage = new CharacterUsage();
    myLocationUsage = new LocationUsage();
    myForceSyncTestFlag = false;
    myDefinesLocationFlag = false;
    myDefinesAnimationFlag = false;
  }

  public SynchronizationComponent GetSynchronizationPolicy() {
    return mySynchronizationPolicy.GetRootPolicy();
  }

  /**
   * Returns this frame's documentation string
   * @return The documentation for this frame
   */
  public String Documentation() {
    return myDocumentation;
  }

  public boolean IsLocation() {
    return myDefinesLocationFlag;
  }

  public boolean IsAnimation() {
    return myDefinesAnimationFlag;
  }

  /**
   * Sets the <code>Location</code> for this frame.  Not all <code>ActionFrame</code>s require
   * the setting of a location.  If an <code>ActionFrame</code> doesn't have its location set the
   * rendering of the <code>Playlist</code> will render the frame atop a previously specified
   * location.  The <code>Location</code> passed in should be the <code>Location</code> object
   * that results from the instantiation of a <code>LocationTemplate</code>. As a result of
   * adding a new <code>Location</code> the frame's <code>LocationUsage</code> is updated to
   * reflect the usage summary of the supplied <code>Location</code>
   * @param newLocation The instantiated <code>Location</code> that describes the graphical backdrop and character staging registration points for this portion of the scene
   */
  public void SetLocation( Location newLocation ) {
    myDefinesLocationFlag = true;
    myLocationUsage.AddLocationUse( newLocation );
    myMediaActions.add( new PlaylistLocation( newLocation ) );

    ActionSynchronizationPolicy ExitPolicy = new ActionSynchronizationPolicy();
    SequentialSynchronizationActions SeqActions = new SequentialSynchronizationActions();
    SeqActions.AddSynchronizationAction( new LocationCompletedSynchronizationAction( newLocation.Name() ) );
    ExitPolicy.SetRootPolicy( SeqActions );
    mySynchronizationPolicy = ExitPolicy;
  }

  /**
   * Plays the specified sound assets on the specified sound channel in the specified loop mode
   * @param soundAssets A list of the sound assets to add to the channels sound queue
   * @param soundType The type of sound so we know what cast this sound came from
   * @param soundChannel The channel whose sound queue is to be augmented with these sounds
   * @param loopMode The mode for sound playback
   */
  public void PlaySounds( String[] soundAssets, SoundType soundType, SoundChannel soundChannel, SoundLoopMode loopMode ) {
    // Update the cast usage summary for all the sounds
    // for( int i = 0, len = soundAssets.length; i < len; i++ ) {
    //   myCastUsage.AddCastUse( soundType.toString(), soundAssets[i] );
    // }
    // System.out.println( "Loop mode version" );
    // PlaySentence( "AAAB", soundType, soundChannel, 1 );
    myMediaActions.add( new PlaylistSounds( soundChannel, loopMode, soundType, soundAssets ) );
  }

  /**
   * Plays the specified sound assets on the specified sound channel and instructs them to loop
   * for the designated number of iterations
   * @param soundAssets A list of the sound assets to add to the channel's sound queue
   * @param soundType The type of sound so we know what cast this sound came from
   * @param soundChannel The channel whose sound queue is to be augmented with these sounds
   * @param iterations The number of iterations to play these sounds
   */
  public void PlaySounds( String[] soundAssets, SoundType soundType, SoundChannel soundChannel, int iterations ) {
    // Update the cast usage summary for all the sounds
    // for( int i = 0, len = soundAssets.length; i < len; i++ ) {
    //   myCastUsage.AddCastUse( soundType.toString(), soundAssets[i] );
    // }
    // PlaySentence( "AAAA", "AB", soundType, soundChannel, iterations );
    myMediaActions.add( new PlaylistSounds( soundChannel, iterations, soundType, soundAssets ) );
  }

  public void PlaySentence( String role, String sentenceName ) {
    // Determine the set of sounds to play for this sentence by instantiating its grammar with the current game state
    String Grammar = SentenceGrammarMap.Instance().GrammarForSpeech( sentenceName );
    PhraseParser PP = new PhraseParser();
    String[] PhraseWaves = PP.InstantiateSentence( role, sentenceName, com.armygame.recruits.StoryEngine.instance().castManager.getCharacterFromRole( role ).getVoiceID(), Grammar );
    PlaySounds( PhraseWaves, SoundType.CHARACTER_DIALOG, SoundChannel.FOREGROUND_CHANNEL, 1 );
  }
  /**
   * Stops audio playback on the named audio channel
   * @param soundChannel The channel whose audio is to stop
   */
  public void StopSound( SoundChannel soundChannel ) {
    myMediaActions.add( new PlaylistStopSound( soundChannel ) );
  }

  /**
   * Stops all audio playback on all sound channels
   */
  public void StopAllSounds() {
    // Create a <code>PlaylistStopSound</code> for all the audio channels
    SoundChannel[] AllSounds = SoundChannel.AllSoundChannels();
    for( int i = 0, len = AllSounds.length; i < len; i++ ) {
      myMediaActions.add( new PlaylistStopSound( AllSounds[i] ) );
    }
  }

  public void ResolveAnimationNames( CharacterUsage charUse ) {
    Iterator MediaActionItr = myMediaActions.iterator();
    while( MediaActionItr.hasNext() ) {
      Object CurrentAction = MediaActionItr.next();
      if ( CurrentAction instanceof PlaylistAnimation ) {
        System.out.println( "Got a playlist animation -----------------------------------------------++++" );
        PlaylistAnimation Anim = (PlaylistAnimation) CurrentAction;
        String NewAnimationName = ResolveAnimationName( Anim.CharRoleName(), Anim.ActionName() );
        charUse.ResolveAnimation( Anim.CharRoleName(), Anim.ActionName(), NewAnimationName );
        Anim.SetAnimationName( NewAnimationName );
      }
    }
  }

  private String ResolveAnimationName( String role, String actionName ) {
    System.out.println( "Asked to resolve " + actionName + " for role " + role );
    String Result = actionName;
    StringTokenizer ActionParser = new StringTokenizer( actionName, "(_)" );

    int ActionIndex = 0;
    int NumTokens = ActionParser.countTokens();
    String[] ActionPieces = new String[ NumTokens ];
    while( ActionParser.hasMoreTokens() ) {
      ActionPieces[ ActionIndex++ ] = ActionParser.nextToken();
    }

    switch( NumTokens ) {
      case 1:
        // No Role params - just pass it through (Shouldn't happen)
        break;

      case 2:
        // Of type front_idle (Concatenate and pass through
        Result = ActionPieces[ 0 ] + "_" + ActionPieces[ 1 ];
        break;

      case 4:
        // Of type front_look/point_at/away(SC)
        if ( ActionPieces[ 0 ].equals( "face" ) ) {
          if ( ActionPieces[ 1 ].equals( "at" ) ) {
            Result = RelativePlacementTable.Instance().ResolveRelativeAction( RelativePlacementTable.ACT_AT, role, ActionPieces[ 2 ] ) +
                       "_" + ActionPieces[ 3 ];
          } else if ( ActionPieces[ 1 ].equals( "away" ) ) {
            Result = RelativePlacementTable.Instance().ResolveRelativeAction( RelativePlacementTable.ACT_AWAY, role, ActionPieces[ 2 ] ) +
                       "_" + ActionPieces[ 3 ];
          } else {
            System.out.println( "Unparseable action name " + actionName );
          }
        } else {
          if ( ActionPieces[ 2 ].equals( "at" ) ) {
            Result = ActionPieces[ 0 ] + "_" + ActionPieces[ 1 ] +
                       RelativePlacementTable.Instance().ResolveRelativeAction( RelativePlacementTable.ACT_AT, role, ActionPieces[ 3 ] );
          } else if ( ActionPieces[ 2 ].equals( "away" ) ) {
            Result = ActionPieces[ 0 ] + "_" + ActionPieces[ 1 ] +
                       RelativePlacementTable.Instance().ResolveRelativeAction( RelativePlacementTable.ACT_AWAY, role, ActionPieces[ 3 ] );
          } else {
            System.out.println( "Unparseable action name " + actionName );
          }
        }
        break;

      default:
        // Of type face_away/at(SC)_talk
        System.out.println( "Unparsable action name with " + NumTokens + " pieces, " + actionName );
        break;
    }

    System.out.println( "Resolved Name is " + Result );
    return Result;

  }

  /**
   * Plays the named character animation for the named character
   * @param character The character that's animating
   * @param actionName The name of the character action to perform
   * @param mode The mode for this animation
   * @param specialFlag <code>true</code> if this animation is a special action, otherwise <code>false</code>
   */
  public void PlayAnimation( String role, String actionName, AnimationMode mode, boolean specialFlag ) {
    // Update the character usage summary
    // String Action = ResolveAnimationName( role, actionName );
    myDefinesAnimationFlag = true;
    String Action = actionName;
    myCharacterUsage.AddCharacterUse( role, Action, specialFlag );
    myMediaActions.add( new PlaylistAnimation( role, Action, mode, specialFlag ) );
  }

  /**
   * Plays the named character animation for the named character
   * @param character The character that's animating
   * @param actionName The name of the character action to perform
   * @param iterations The number of times to loop this animation
   * @param specialFlag <code>true</code> if this animation is a special action, otherwise <code>false</code>
   */
  public void PlayAnimation( String role, String actionName, int iterations, boolean specialFlag ) {
    // Update the character usage summary
    // String Action = ResolveAnimationName( role, actionName );
    myDefinesAnimationFlag = true;
    String Action = actionName;
    myCharacterUsage.AddCharacterUse( role, Action, specialFlag );
    myMediaActions.add( new PlaylistAnimation( role, Action, iterations, specialFlag ) );
  }

  /**
   * Play the named movie at this point in the playlist
   * @param movieName The name of the movie to play
   */
  public void PlayMovie( String movieName ) {
    myMediaActions.add( new PlaylistMovie( movieName ) );
    ActionSynchronizationPolicy ExitPolicy = new ActionSynchronizationPolicy();
    SequentialSynchronizationActions SeqActions = new SequentialSynchronizationActions();
    SeqActions.AddSynchronizationAction( new MovieCompletedSynchronizationAction( movieName ) );
    ExitPolicy.SetRootPolicy( SeqActions );
    mySynchronizationPolicy = ExitPolicy;
  }

  /**
   * Adds the specified screen transition to the list of media actions
   * @param transitionType The type of screen transition to insert
   */
  public void AddScreenTransition( ScreenTransition transitionType ) {
    myMediaActions.add( new PlaylistScreenTransition( transitionType ) );
    ActionSynchronizationPolicy ExitPolicy = new ActionSynchronizationPolicy();
    SequentialSynchronizationActions SeqActions = new SequentialSynchronizationActions();
    SeqActions.AddSynchronizationAction( new ScreenTransitionCompletedSynchronizationAction( transitionType.toString() ) );
    ExitPolicy.SetRootPolicy( SeqActions );
    mySynchronizationPolicy = ExitPolicy;
  }

  /**
   * Return the Cast Usage Summary
   * @return The <code>CastUsage</code> for this frame
   */
  public CastUsage CastUsageSummary() {
    return myCastUsage;
  }

  /**
   * Return the Character Usage Summary
   * @return The <code>CharacterUsage</code> for this frame
   */
  public CharacterUsage CharacterUsageSummary() {
    return myCharacterUsage;
  }

  /**
   * Return the Location Usage Summary
   * @return The <code>LocationUsage</code> for this frame
   */
  public LocationUsage LocationUsageSummary() {
    return myLocationUsage;
  }

  /**
   * Add an exit action to the set of exit actions for this frame
   * @param exitAction The exit action to add to the exit actions for this frame
   */
  public void AddExitAction( ExitAction exitAction ) {
    myExitActions.AddExitAction( exitAction );
  }


  /**
   * Convenience routine when the synchronization policy is for the frame to exit
   * when all the characters in the supplied list should take an exit
   * @param characterNames The list of characters that should take an exit to get out of this frame
   */
  public void FrameDoneWhenCharsTakeExit( String[] characterNames ) {
    myForceSyncTestFlag = true;
    ActionSynchronizationPolicy ExitPolicy = new ActionSynchronizationPolicy();
    SequentialSynchronizationActions SeqActions = new SequentialSynchronizationActions();
    ParallelSynchronizationActions ParTakeExitActions = new ParallelSynchronizationActions();
    ParallelSynchronizationActions ParTookExitActions = new ParallelSynchronizationActions();
    for( int i = 0, len = characterNames.length; i < len; i++ ) {
      ParTakeExitActions.AddSynchronizationAction( new TakeExitSynchronizationAction( characterNames[i] ) );
      ParTookExitActions.AddSynchronizationAction( new TookExitSynchronizationAction( characterNames[i] ) );
    }
    SeqActions.AddSynchronizationAction( ParTakeExitActions );
    SeqActions.AddSynchronizationAction( ParTookExitActions );
    ExitPolicy.SetRootPolicy( SeqActions );
    mySynchronizationPolicy = ExitPolicy;
  }

  /**
   * Convenience routine to wait for a sound and then have characters take exits
   * First we wait on the specified sound channel, then tell the named characters to
   * take their exit
   * @param syncSoundChannel The <code>SoundChannel</code> to wait for a done event on
   * @param characterNames The list of characters that should take their exit action after syncing to sound
   */
  public void FrameDoneWhenSoundDone( SoundChannel syncSoundChannel, String[] characterNames ) {
    ActionSynchronizationPolicy ExitPolicy = new ActionSynchronizationPolicy();
    SequentialSynchronizationActions SeqActions = new SequentialSynchronizationActions();
    SeqActions.AddSynchronizationAction( new SoundQueueCompleteSynchronizationAction( syncSoundChannel.toString() ) );
    ParallelSynchronizationActions ParTakeExitActions = new ParallelSynchronizationActions();
    ParallelSynchronizationActions ParTookExitActions = new ParallelSynchronizationActions();
    for( int i = 0, len = characterNames.length; i < len; i++ ) {
      ParTakeExitActions.AddSynchronizationAction( new TakeExitSynchronizationAction( characterNames[i] ) );
      ParTookExitActions.AddSynchronizationAction( new TookExitSynchronizationAction( characterNames[i] ) );
    }
    SeqActions.AddSynchronizationAction( ParTakeExitActions );
    SeqActions.AddSynchronizationAction( ParTookExitActions );
    ExitPolicy.SetRootPolicy( SeqActions );
    mySynchronizationPolicy = ExitPolicy;
  }

  /**
   * Set the synchronization policy
   */
  public void SetSynchronizationPolicy( ActionSynchronizationPolicy syncPolicy ) {
    mySynchronizationPolicy = syncPolicy;
  }

  /**
   * Return the Synchronization Policy
   */
  public ActionSynchronizationPolicy SynchronizationPolicy() {
    return mySynchronizationPolicy;
  }

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {

    //System.out.println( "In ActionFrame execute()" );
    boolean DidSomethingFlag = false;

    // Start up all the things we have to do
    Iterator ActionItr = myMediaActions.iterator();
    while( ActionItr.hasNext() ) {
      DidSomethingFlag = true;
      //System.out.println( "Got something to do" );
      PlaylistMediaAction NextAction = (PlaylistMediaAction) ActionItr.next();
      NextAction.Execute( playerContext, synchronizer );
    }

    // if ( DidSomethingFlag == false ) {
    if ( ( myForceSyncTestFlag == true ) || ( DidSomethingFlag == false ) ) {
      myForceSyncTestFlag = false;
      //System.out.println( "Action Frame was empty - forcing synchronizer" );
      synchronizer.TestEvent( new MediaSynchronizationEvent( "Force", "Did Nothing" ) );
    }

  }


  /**
   * Format this <code>ActionFrame</code> for inclusion in the actions list XML of
   * a Java-Director playlist message
   * @return The contents of this <code>ActionFrame</code> as XML - Note that the XML generated is not pretty printed (no whitespace) as this XML is intended for the efficiency of a message rather than human readbaility
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<ActionFrame>" );
    Result.append( "<Comment>" ); Result.append( myDocumentation ); Result.append( "</Comment>" );
    Result.append( "<Status>" ); Result.append( myStatusString ); Result.append( "</Status>" );
    Result.append( "<Actions>" );
    for( int i = 0, len = myMediaActions.size(); i < len; i++ ) {
      Result.append( ( (PlaylistMediaAction) myMediaActions.get( i ) ).toXML() );
    }
    Result.append( "</Actions>" );
    Result.append( "<ExitCriteria>" );
    Result.append( mySynchronizationPolicy.toXML() );
    Result.append( "</ExitCriteria>" );
    Result.append( myExitActions.toXML() );
    Result.append( "</ActionFrame>" );
    return Result.toString();
  }

}

