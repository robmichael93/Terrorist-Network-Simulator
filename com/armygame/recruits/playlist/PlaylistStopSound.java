package com.armygame.recruits.playlist;


/**
 * Holds instructions for stoppping audio on a sound channel
 */
public class PlaylistStopSound implements PlaylistMediaAction {
  /**
   * The sound channel to stop sounds on
   */
  private SoundChannel myStopChannel;

  /**
   * Constructs a command for stopping sounds on the specified channel
   * @param soundChannel The <code>SoundChannel</code> to stop audio on
   */
  public PlaylistStopSound( SoundChannel soundChannel ) {
    myStopChannel = soundChannel;
  }

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    //System.out.println( "Received Sound stop command" );
    com.armygame.recruits.sound.SoundChannel SoundChan = playerContext.GetSoundChannel( myStopChannel.ChannelID() );
    SoundChan.Stop( synchronizer, myStopChannel );
  }
  /**
   * Emit what we need to know about stopping a sound as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a stopping a sound on the specified channel
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<StopSoundChannel>" );
    Result.append( myStopChannel.toString() );
    Result.append( "</StopSoundChannel>" );
    return Result.toString();
  }  

}

