package com.armygame.recruits.playlist;


public interface PlaylistMediaAction {

  /**
   * Every Media Action needs to be able to output itself as XML
   * @return The XML for this media action - not intended to be human readable -optimized for Java-Director messaging - no whitespace
   */
  public String toXML();

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer );

}
