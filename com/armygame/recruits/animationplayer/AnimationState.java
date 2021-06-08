package com.armygame.recruits.animationplayer;

/**
 * Provides an ennumeration data type that represents the playabck state of this
 * <CODE>Animation</CODE>
 */
public class AnimationState {

  /**
   * The <B>int</B> code for this state.
   */
  private final int myState;

  /**
   * Constructs a new state with the given code.  Since this constructor is private the
   * only allowed <CODE>AnimationState</CODE>s are those declared
   * <B>public static final</B> below.
   *
   * @param state The unique code for this state
   */
  private AnimationState( int state ) {
    myState = state;
  }

  /**
   * Provides an equality test between <CODE>AnimationState</CODE> objects
   *
   * @param rhs The state to compare to
   * @return <B>true</B> if the state codes of this object and the <B>rhs</B> argument are the
   *         same
   */
  public boolean equals( Object rhs ) {
    boolean Result = false;

    if ( rhs instanceof AnimationState ) {
      if ( myState == ((AnimationState) rhs).myState ) {
        Result = true;
      }
    }

    return Result;
  }

  /**
   * Returns the state code for this state (for use in <B>switch{}</B> statements)
   *
   * @return The state code as an <B>int</B>
   */
  public int State() {
    return myState;
  }

  /**
   * This <CODE>Animation</CODE> has been constructed but not its XML data has not yet been read
   */
  public static final AnimationState UNINITIALIZED = new AnimationState( 0 );

  /**
   * This <CODE>Animation</CODE> has had its XML read and is ready to play
   */
  public static final AnimationState INITIALIZED = new AnimationState( 1 );

  /**
   * This <CODE>Animation</CODE> is currently playing normally (not taking exits or playing a 'filler' or
   * transition animation)
   */
  public static final AnimationState PLAYING = new AnimationState( 2 );

  /**
   * This <CODE>Animation</CODE> has been paused
   */
  public static final AnimationState PAUSED = new AnimationState( 3 );

  /**
   * This <CODE>Animation</CODE> has been instructed to exit the currently playing animation but has not
   * yet reached a key frame from which it can actually exit
   */
  public static final AnimationState TAKING_EXIT = new AnimationState( 4 );

  /**
   * This <CODE>Animation</CODE> has been instructed to exit and has exited and is now playing a default
   * 'filler' idle animation (or some other transition animation)
   */
  public static final AnimationState DEFAULT_IDLING = new AnimationState( 5 );

  /**
   * This <CODE>Animation</CODE> has completed and its frames may be returned to the frame pool
   */
  public static final AnimationState DONE = new AnimationState( 6 );

}

