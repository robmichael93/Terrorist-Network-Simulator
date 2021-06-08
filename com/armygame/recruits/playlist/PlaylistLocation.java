package com.armygame.recruits.playlist;


import com.armygame.recruits.locations.Location;


/**
 * Encapsulates what we need to know about a <code>Location</code> to keep it in the playlist
 */
public class PlaylistLocation implements PlaylistMediaAction {

  /**
   * The name of this location - should be the name of the <code>LocationTemplate</code> we
   * instantiated to get the <code>Location</code> object we've instantied
   */
  private String myLocationName;

  private Location myLocation;

  /**
   * For a playlist, the location is characterized by the name that was instantiated from
   * the <code>LocationTemplate</code> into the <code>Location</code> object
   * @param newLocation The <code>Location</code> object that was instantiated - we'll use it to get the name we need
   */
  public PlaylistLocation( Location newLocation ) {
    myLocation = newLocation;
    myLocationName = myLocation.Name();
  }


  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    playerContext.GetAnimationPlayer().SetLocation( synchronizer, myLocation );
  }

  /**
   * Emit what we need to know about a <code>Location</code> as an action as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a location action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<Location>" ); Result.append( myLocationName ); Result.append( "</Location>" );
    return Result.toString();
  }

}
