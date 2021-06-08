package com.armygame.recruits.playlist;

/**
 * Enum for screen transitions
 */
public class ScreenTransition {
  /**
   * Holds the name of the screen transition
   */
  private final String myTransitionName;

  /**
   * Since the constructor is private the only publically available screen transitions
   * are those declared <code>public static final</code> below
   * @param transitionName The name of the transition to insert
   */
  private ScreenTransition( String transitionName ) {
    myTransitionName = transitionName;
  }

  /**
   * Returns the screen transition name
   * @return The name of the screen transition
   */
  public String toString() {
    return myTransitionName;
  }

  /**
   * Immediately turns the screen blank (black) and leaves it that way
   */
  public static final ScreenTransition ERASE_SCREEN = new ScreenTransition( "ERASE" );

  /**
   * Slow, uniform fade to black
   */
  public static final ScreenTransition FADE_TO_BLACK = new ScreenTransition( "FADE_TO_BLACK" );

}

