package com.armygame.recruits.playlist;


/**
 * Enum for the allowable animation modes
 */
public class AnimationMode {

  /**
   * Holds the animation looping mode according to the following codes:
   * 0 = Loop infinitely
   */
  private final int myModeCode;

  /**
   * Since this constructor is private the only publically available animation loop modes
   * are those declared as <code>public static final</code> below
   * @param modeCode An <code>int</code> representing the code for the mode we want to set
   */
  private AnimationMode( int modeCode ) {
    myModeCode = modeCode;
  }

  /**
   * Return the animation mode as a code
   * @return The animation mode code
   */
  public int AnimationMode() {
    return myModeCode;
  }

  /**
   * Indicates the Animation is to loop infinitely until told to stop
   */
  public static final AnimationMode LOOP = new AnimationMode( 0 );

}
