package com.armygame.recruits.animationplayer;

/**
 * Implements a set of enumeration objects that define the state of the animation player.
 * Also provides an <CODE>equals()</CODE> method for type-safe state comparisons
 */
public class PlaybackState {

  /**
   * Holds the state of this object as an integer code
   */
  private final int myStateCode;

  /**
   * Initializes a state to the supplied code.  It is up to the implementor to assure the
   * codes for each state are unique.  Since the constructor is <B>private</B>, the
   * only allowed <CODE>PlaybackState</CODE> values are those explicitly declared as
   * <CODE>public static final PlaybacState</CODE>s below
   *
   * @param stateCode
   */
  private PlaybackState( int stateCode ) {
    myStateCode = stateCode;
  }

  /**
   * Provides a type-safe comparison between this <CODE>PlaybackState</CODE> object and
   * the supplied argument - Equality means their state codes are <B>==</B>
   *
   * @param rhs The <CODE>PlaybackState</CODE> for use in the equality test
   * @return
   */
  public boolean equals( PlaybackState rhs ) {
    return ( myStateCode == rhs.myStateCode );
  }

  /**
   * This state indicates the the animation player is currently not playing back any media
   * and that the content pane is empty (as opposed to 'dirty')
   */
  public static final PlaybackState IDLE = new PlaybackState( 0 );

  /**
   * This state indicates the animation player is currently not playing back any media,
   * but that the content pane is 'dirty' (that is - it has stuff drawn to it)
   */
  public static final PlaybackState IDLE_DIRTY = new PlaybackState( 1 );

  /**
   * This state indicates the animation player is currently playing animations and/or
   * film loops
   */
  public static final PlaybackState PLAYING_ANIMATION = new PlaybackState( 2 );

  /**
   * This state indicates the animation player is playing back a QuickTime movie
   */
  public static final PlaybackState PLAYING_MOVIE = new PlaybackState( 3 );

  /**
   * Indicates playback executive should quit, we are done with this animation player
   */
  public static final PlaybackState QUIT = new PlaybackState( -1 );

}

