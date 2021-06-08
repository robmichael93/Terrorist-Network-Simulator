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
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

import com.armygame.recruits.mediaelements.CastUsage;
import com.armygame.recruits.locations.LocationUsage;


/**
 *  This class manages the creation and XML formatting/generation of Playlists
 *  Playlists provide the following services:
 *  1)  Playlist creation and editing operations (create, clear)
 *  2)  Add a <code>ActionFrame</code> in sequence to the <code>Playlist</code>
 *  3)  Maintain a set of summary information (cast usage, character action usage,
 *      character staging, and instantiated location descriptions) necessary for
 *      communicating Playlists as XML to Director
 *  4)  Provide a message formatting capability to emit the contents of the Playlist
 *      as XML for consumption by Director.
 *  It is intended that each <code>Playlist</code> encode the media actions to take to render
 *  an entire scene.  Scenes will contain multiple sub-scenes, each of which will take place
 *  (and will thus require specification of) a <code>Location</code>.  Refer to the
 *  <code>ActionFrame</code> class for specifics on the actions used to describe each frame
 *  of the <code>Playlist</code>, and how to synchronize those actions.
 */
public class Playlist {

  /**
   * The summary of cast usage for this <code>Playlist</code>.  Every time an item is
   * added to this <code>Playlist</code> that involves media assets, this summary should be
   * informed and updated
   */
  private CastUsage myCastUsageSummary;

  /**
   * This set summarizes the characters sppearing in the scene and the animation actions they
   * require.
   */
  private CharacterUsage myCharacterUsageSummary;


  /**
   * This set summarizes the <code>Location</code>s used in this scene's <code>Playlist</code>
   */
  private LocationUsage myLocationUsageSummary;

  /**
   * The sequential list of <code>ActionFrame</code>s for this <code>Playlist</code>
   */
  private List myActionFrames;

  /**
   * Creates a new, empty playlist
   *
   * @param animationPlayer The <CODE>AnimationPlayer</CODE> that will manage/buffer resources for and that
   * will play this <CODE>Playlist</CODE>
   */
  public Playlist() {
    myCastUsageSummary = new CastUsage();
    myCharacterUsageSummary = new CharacterUsage();
    myLocationUsageSummary = new LocationUsage();
    myActionFrames = Collections.synchronizedList( new ArrayList() );
  }

  public List GetActionFrames() {
    return myActionFrames;
  }

  public CastUsage GetCastUse() {
    return myCastUsageSummary;
  }

  public CharacterUsage GetCharUse() {
    return myCharacterUsageSummary;
  }

  public LocationUsage GetLocUse() {
    return myLocationUsageSummary;
  }

  /**
   * This utility folds the cast, character, and location usage summaries of an
   * <code>ActionFrame</code> to the global usage summaries for these categories maintained
   * by this <code>Playlist</code>
   * @param frame The <code>ActionFrame</code> whose usage summary is to be folded into the <code>Playlist</code>s global usage summary.  We use set-based implementations of the summaries so
   * adding redundant information that's already in the global summary will correctly ignore
   * new usage summary information from an <code>ActionFrame</code> by making sure it appears only
   * once in the <code>Playlist</code>'s global summary.
   */
  private void UpdateUsageSummaries( ActionFrame frame ) {
    myCastUsageSummary.UpdateUsageSummary( frame.CastUsageSummary() );
    myCharacterUsageSummary.UpdateUsageSummary( frame.CharacterUsageSummary() );
    myLocationUsageSummary.UpdateUsageSummary( frame.LocationUsageSummary() );
    myLocationUsageSummary.SummarizeCastUsage( myCastUsageSummary );
  }

  /**
   * Adds an <code>ActionFrame</code> to the end of the <code>PlayList</code>.  There are
   * no random access capabilities for the <code>Playlist</code> - We assume the Scene under
   * construction knows how to serialize the <code>ActionFrame</code>s so that they can always
   * be added in sequential order.
   * @param newFrame The new <code>ActionFrame</code> to add to the <code>Playlist</code>
   */
  public void AddActionFrame( ActionFrame newFrame ) {
    // Fold the new <code>ActionFrame</code>'s usage summary into the <code>Playlist</code>'s
    // global usage summary
    UpdateUsageSummaries( newFrame );

    // If this <code>ActionFrame</code>'s synchronization policy isn't set we
    // force it to be an immediate exit
    if ( newFrame.SynchronizationPolicy() == null ) {
      ActionSynchronizationPolicy DefaultPolicy = new ActionSynchronizationPolicy();
      SequentialSynchronizationActions Actions = new SequentialSynchronizationActions();
      Actions.AddSynchronizationAction( new ImmediateExitSynchronizationAction() );
      DefaultPolicy.SetRootPolicy( Actions );
      newFrame.SetSynchronizationPolicy( DefaultPolicy );
    }

    // Frames are added sequentially to the end of the list of ActionFrames
    myActionFrames.add( newFrame );
  }

  /**
   * Convert the playlist to an XML string for sending as a message from Java to Director
   * @return The playlist formatted as an XML String for sending as a message to Director
   */
  public String toXML(){
    StringBuffer FormatBuf = new StringBuffer();
    FormatBuf.append( "<Playlist>" );
    FormatBuf.append( myCastUsageSummary.toXML() );
    FormatBuf.append( myCharacterUsageSummary.toXML() );
    FormatBuf.append( myLocationUsageSummary.toXML() );
    FormatBuf.append( "<ActionFrames>" );
    for( int i = 0, len = myActionFrames.size(); i < len; i++ ) {
      FormatBuf.append( ( (ActionFrame) myActionFrames.get( i )).toXML() );
    }
    FormatBuf.append( "</ActionFrames>" );
    FormatBuf.append( "</Playlist>" );
    return FormatBuf.toString();
  }

}
