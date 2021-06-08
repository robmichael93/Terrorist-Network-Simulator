package com.armygame.recruits.playlist;

/**
 * Enum for Sound Looping modes
 */
public class SoundLoopMode {

  /**
   * Holds the sound looping mode according to the following codes:
   * 0 = Loop infinitely
   */
  private final int myModeCode;

  /**
   * Since this constructor is private the only publically available sound loop modes
   * are those declared as <code>public static final</code> below
   * @param modeCode An <code>int</code> representing the code for the mode we want to set
   */
  private SoundLoopMode( int modeCode ) {
    myModeCode = modeCode;
  }

  /**
   * Return the Loop mode as a code
   * @return The loop mode code
   */
  public int LoopMode() {
    return myModeCode;
  }

  /**
   * Indicates the sound is to loop infinitely until told to stop
   */
  public static final SoundLoopMode INFINITE = new SoundLoopMode( 0 );

}

