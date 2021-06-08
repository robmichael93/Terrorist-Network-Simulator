package com.armygame.recruits.playlist;


/**
 * Encapsulates what we need to know about a <code>Location</code> to keep it in the playlist
 */
public class PlaylistScreenTransition implements PlaylistMediaAction {

  /**
   * The <code>ScreenTransition</code> to use
   */
  private ScreenTransition myTransition;

  /**
   * Maintains a screen transition in the playlist
   * @param transition The <code>ScreenTransition</code> to use in the playlist
   */
  public PlaylistScreenTransition( ScreenTransition transition ) {
    myTransition = transition;
  }

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    playerContext.GetAnimationPlayer().ScreenTransition( synchronizer );
  }

  /**
   * Emit what we need to know about a <code>Location</code> as an action as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a location action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<ScreenTransition>" );
    Result.append( "<Effect>" ); Result.append( myTransition.toString() ); Result.append( "</Effect>" );
    Result.append( "</ScreenTransition>" );
    return Result.toString();
  }

}
