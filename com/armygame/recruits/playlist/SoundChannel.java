package com.armygame.recruits.playlist;

/**
 * Enum for the available sound channels
 */
public class SoundChannel {

  /**
   * The Director sound channel number for this channel's name
   */
  private final int myChannelID;

  /**
   * Since this constructor is private, the only available sound channel objects are those
   * defined below as <code>public static final</code> channels
   */
  private SoundChannel( int channelID ) {
    myChannelID = channelID;
  }

  /**
   * Turn the sound channel enum into its value
   * @return The Director sound channel number corresponding to the channel enum name
   */
  public int ChannelID() {
    return myChannelID;
  }

  /**
   * Return all the sound channels as a list for group operations on sounds
   * @return An array of all the sound channels
   */
  public static SoundChannel[] AllSoundChannels() {
    return ALL_SOUND_CHANNELS;
  }

  /**
   * Return the name of the sound channel as a string - for debugging
   * @return The name of the sound channel as a string
   */
  public String toString() {
    String Result = "INVALID";

    // THESE NUMBER MAPPINGS NEED TO BE IN SYNCH WITH THE public static final FIELDS BELOW!!!!
    switch( myChannelID ) {
      case 1 :
	Result = "AMBIENT_CHANNEL";
	break;
      case 2 :
	Result = "FOREGROUND_CHANNEL";
	break;
    }
    return Result;
  }

  /**
   * The backgroudn sound channel used for music and ambient scene sound tracks
   */
  public static final SoundChannel AMBIENT_CHANNEL = new SoundChannel( 1 );


  /**
   * The foreground sound channel used for dialog and on-demand Foley sound effects
   */
  public static final SoundChannel FOREGROUND_CHANNEL = new SoundChannel( 2 );

  /**
   * The set of all sound channels - For use in operations (such as <code>StopAllSounds()</code>
   * that need to operate with all sound channels
   */
  private static final SoundChannel[] ALL_SOUND_CHANNELS = { AMBIENT_CHANNEL, FOREGROUND_CHANNEL };

}

