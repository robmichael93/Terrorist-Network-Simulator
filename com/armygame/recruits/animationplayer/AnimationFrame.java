package com.armygame.recruits.animationplayer;


/**
 * Holds the basic information about all animation frames, its frame number and 
 * keyframe status.  Also specifies that all sub-classed animation frames must
 * implement a <B>NextFrame()</B> method that determines (using the sub-classes
 * specific branching logic) which frame number should come next in the animation
 * sequence.  Finally, this class holds the logic for whether the current frame is
 * a keyframe.
 * 
 * @see BranchingFrame
 * @see Non-BranchingFrame
 */
abstract public class AnimationFrame {

  /**
   * This frames number in the animation sequence
   */
  protected int myFrameNum;

  /**
   * When <B>true</B> this frame is a keyframe, else <B>false</B>
   */
  protected boolean myIsKeyFrameFlag;

  /**
   * Constructs a new frame and initializes its number in the seuence and keyframe status
   * 
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame Keyframe status flag, when <B>true</B> it's a keyframe
   */
  public AnimationFrame( int frameNum, boolean isKeyFrame ) {
    myFrameNum = frameNum;
    myIsKeyFrameFlag = isKeyFrame;
  }

  /**
   * Returns the number of this frame in the sequence
   * 
   * @return The number of this frame in the sequence
   */
  public int FrameNum() {
    return myFrameNum;
  }

  /**
   * Clients must override this method with logicl for determining which frame should
   * come next in the animation sequence
   * 
   * @return The next frame to play in the animation sequence, taking into account branching logic
   *         by the sub-class
   */
  abstract public int NextFrame();

  /**
   * Returns <B>true</B> if the frame is a keyframe which eans the animation can safely
   * seque to a new animation action.
   * 
   * @return <B>true</B> if this is a keyframe
   */
  public boolean CanExit() {
    return myIsKeyFrameFlag;
  }

}

