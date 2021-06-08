package com.armygame.recruits.playlist;

import java.io.File;

import com.armygame.recruits.globals.MasterConfiguration;


/**
 * Encapsulates what we need to know about a set of sounds together in a playlist
 */
public class PlaylistSounds implements PlaylistMediaAction {


  /**
   * The sound channel we want to play these sounds on
   */
  private SoundChannel mySoundChannel;

  /**
   * The mode to play the sounds in if we don;t specify a specific number of iterations,
   * <b>Note:</b> All sounds in the list are repeated with the same loop mode
   */
  private SoundLoopMode mySoundLoopMode;

  /**
   * The specific number of times to loop this sound if specified
   */
  private int myLoopCount;


  /**
   * The <code>SoundType</code> of this sound so we know what cast it came from
   */
  private SoundType mySoundType;

  /**
   * The list of sound asset names to play - we play them in the sequential order they are
   * placed in this list
   */
  private String[] mySoundList;

  /**
   * Constructs a group of sound media assets to play together consecutively on the same sound channel
   * This version of the constructor is called when we specify a <code>SoundLoopMode</code> rather
   * than an explicit count
   * @param soundChannel The <code>SoundChannel</code> to play the sounds on
   * @param loopMode The <code>SoundLoopMode</code> to use for playing this group
   * @param soundType the <code>SoundType</code> for this sound
   * @param sounds The list of sounds to play consecutively in this group
   */
  public PlaylistSounds( SoundChannel soundChannel, SoundLoopMode loopMode, SoundType soundType, String[] sounds ) {
    mySoundChannel = soundChannel;
    mySoundLoopMode = loopMode;
    myLoopCount = 0;
    mySoundType = soundType;
    mySoundList = sounds;
  }

  /**
   * Constructs a group of sound media assets to play together consecutively on the same sound channel
   * This version of the constructor is called when we specify an explicit loop count rather than
   * a <code>SoundLoopMode</code>
   * than an explicit count
   * @param soundChannel The <code>SoundChannel</code> to play the sounds on
   * @param loopCount The number of times to iterate the sounds in this group
   * @param soundType the <code>SoundType</code> for this sound
   * @param sounds The list of sounds to play consecutively in this group
   */
  public PlaylistSounds( SoundChannel soundChannel, int loopCount, SoundType soundType, String[] sounds ) {
    mySoundChannel = soundChannel;
    mySoundLoopMode = null;
    myLoopCount = loopCount;
    mySoundType = soundType;
    mySoundList = sounds;
  }


  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    com.armygame.recruits.sound.SoundChannel SoundChan = playerContext.GetSoundChannel( mySoundChannel.ChannelID() );
    for( int i = 0; i < mySoundList.length; i++ ) {
      File FilePath = new File( MasterConfiguration.Instance().AudioAssetsRootPath() + "/" + mySoundList[ i ] + ".mp3" ); //".wav" );
      System.out.println( "Queing sound " + FilePath.toString() );
      SoundChan.QueueSound( FilePath.toString(), myLoopCount );
    }
    SoundChan.Play( synchronizer, mySoundChannel );
  }

  /**
   * Emit what we need to know about a the sounds played together in this group as an action as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a location action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<PlaySounds>" );
    Result.append( "<Channel>" ); Result.append( mySoundChannel.toString() ); Result.append( "</Channel>" );
    Result.append( "<Mode>" );
    Integer Iterations;
    if ( mySoundLoopMode == null ) {
      Iterations = new Integer( myLoopCount );  
    } else {
      Iterations = new Integer( mySoundLoopMode.LoopMode() );
    }
    Result.append( Iterations.toString() );
    Result.append( "</Mode>" );
    Result.append( "<SoundQueue>" );
    for( int i = 0, len = mySoundList.length; i < len; i++ ) {
      Result.append( "<SoundAsset>" ); Result.append( mySoundList[i] ); Result.append( "</SoundAsset>" );
    }
    Result.append( "</SoundQueue>" );
    Result.append( "</PlaySounds>" );
    return Result.toString();
  }

}
