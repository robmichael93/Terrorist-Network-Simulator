package com.armygame.recruits.animationplayer;

import com.armygame.recruits.globals.RecruitsRandom;


/**
 * Holds the information about a branching frame in an animation.  This includes the
 * information maintained by a <CODE>AnimationFrame</CODE> as well as the set of
 * alternative frames to branch to and the smarts to correctly branch to one of them
 * using the <B>NextFrame()</B> method
 * 
 * @see AnimationFrame
 */
public class BranchingFrame extends AnimationFrame {

  /**
   * The frame to branch to if dice are [1-50]
   */
  private int myNext1;

  /**
   * The frame to branch to if dice are [51-100]
   */
  private int myNext2;

  /**
   * Creates a new branching frame and initializes its branch alternatives
   * 
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame When <B>true</B> this frame is a keyframe
   * @param next1 The frame number to branch to if dice [1-50]
   * @param next2 The frame number to branch to if dice are [51-100]
   */
  public BranchingFrame( int frameNum, boolean isKeyFrame, int next1, int next2 ) {
    super( frameNum, isKeyFrame );
    myNext1 = next1;
    myNext2 = next2;
  }

  /**
   * Makes an empty branching frame
   */
  public BranchingFrame() {
    super( 0, false );
    myNext1 = myNext2 = 0;
  }

  /**
   * Sets the paramters of this branching frame from the parameter list
   * 
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame When <B>true</B> this frame is a keyframe
   * @param next1 The frame number to branch to if dice [1-50]
   * @param next2 The frame number to branch to if dice are [51-100]
   */
  public void Initialize( int frameNum, boolean isKeyFrame, int next1, int next2 ) {
    myFrameNum = frameNum;
    myIsKeyFrameFlag = isKeyFrame;
    myNext1 = next1;
    myNext2 = next2;
  }

  /**
   * Returns the next frame in the animation sequence after this frame by rolling dice
   * and choosing between its <B>Next1</B> and <B>Next2</B> alternatives
   * 
   * @return The next frame number to play after this one
   */
  public int NextFrame() {
    return ( RecruitsRandom.RandomIndex( 100 ) < 50 ) ? myNext1 : myNext2;
  }

}

