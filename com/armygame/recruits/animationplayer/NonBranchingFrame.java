package com.armygame.recruits.animationplayer;

/**
 * Holds the information about a straight, non-branching frame - its number in the
 * animation sequence and its keyframe status
 * 
 * @see AnimationFrame
 */
public class NonBranchingFrame extends AnimationFrame {

  /**
   * Constructs a new non-branching frame with the specified frame umber and keyframe status
   * 
   * @param frameNum The number of the this frame in the animation sequence
   * @param isKeyFrame When <B>true</B> signifies that this is a keyframe
   */
  public NonBranchingFrame( int frameNum, boolean isKeyFrame ) {
    super( frameNum, isKeyFrame );
  }

  /**
   * Creates a new, empty non-branching frame
   */
  public NonBranchingFrame() {
    super( 0, false );
  }

  /**
   * Initializes this frame from the supplied parameters
   * 
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame When <B>true</B> this frame is a keyframe
   */
  public void Initialize( int frameNum, boolean isKeyFrame ) {
    myFrameNum = frameNum;
    myIsKeyFrameFlag = isKeyFrame;
  }

  /**
   * The next frame in a straight animation sequence is the next higher frame number.
   * <B>Important Note</B>: This straight branching does not take into account wrap
   * around at the end of a sequence - A <CODE>BranchingFrame</CODE> should be used to
   * handle the last frame in an animation sequence to prevent fal off.
   * 
   * @return The next frame in the sequence (our current frame num + 1)
   */
  public int NextFrame() {
    return myFrameNum + 1;
  }

}

