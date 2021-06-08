package com.armygame.recruits.animationplayer;

/**
 * Provides a factory for building the correct type of <CODE>AnimationFrame</CODE>
 * sub-class (<CODE>BranchingFrame</CODE> or <NonBranchingFrame</CODE>) using an
 * overloaded <B>MakeAnimationFrame()</B> method that takes the required parameters
 * for the type of frame required.  This factory uses a static <CODE>AniationFramePool</CODE>
 * to recycle the frames it uses so it doesn't have to <B>new</B> up a fresh frame every time.
 * <B>Important Note</B>: Use of the <CODE>AnimationFramePool</CODE> <B>REQUIRES</B> that
 * clients of this factory use the <B>ReturnFrame()</B> method to return frames to the
 * factory's pool when they are about to go out of scope.
 */
public class AnimationFrameFactory {

  /**
   * Holds the pool of available <CODE>BranchingFrame</CODE> and <CODE>NonBranchingFrame</CODE>
   * objects this factory is maintaining for efficiency.
   */
  private static AnimationFramePool theirFramePool;

  /**
   * Initializes the factory with an initial pool of branching and non-branching frames.
   *
   * @param initialCapacity The number of <CODE>BranchingFrame</CODE> and <CODE>NonBranchingFrame</CODE> objects
   * to initialize into the frame pool (Example - a value of 10 gives 20 total pooled frames,
   * 10 of each type)
   */
  public AnimationFrameFactory( int initialCapacity ) {
    theirFramePool = new AnimationFramePool( initialCapacity );
  }

  /**
   * Returns a <CODE>NonBranchingFrame</CODE> from the pool and initializes it with the
   * specified frame number and keyframe status,
   *
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame Sets the keyframe status of this frame where <B>true</B> is a keyframe and <B>false</B>
   * is a normal frame
   * @return A <CODE>NonBranchingFrame</CODE> from the pool as a parent <CODE>AnimationFrame</CODE>
   */
  public AnimationFrame MakeAnimationFrame( int frameNum, boolean isKeyFrame ) {
    NonBranchingFrame Result = theirFramePool.GetNonBranchingFrame();
    Result.Initialize( frameNum, isKeyFrame );
    return Result;
  }

  /**
   * Returns a <CODEBranchingFrame</CODE> from the pool and initializes it with the
   * specified frame number and keyframe statusnd branching choices
   *
   * @param frameNum The number of this frame in the animation sequence
   * @param isKeyFrame Sets the keyframe status of this frame where <B>true</B> is a keyframe and <B>false</B>
   * is a normal frame
   * @param next1 The first frame num to branch to if dice [1-50]
   * @param next2 The second frame num to branch to if dice [51-100]
   * @return A <CODE>BranchingFrame</CODE> from the pool as a parent <CODE>AnimationFrame</CODE>
   */
  public AnimationFrame MakeAnimationFrame( int frameNum, boolean isKeyFrame, int next1, int next2 ) {
    BranchingFrame Result = theirFramePool.GetBranchingFrame();
    Result.Initialize( frameNum, isKeyFrame, next1, next2 );
    return Result;
  }

  /**
   * Returns the given frame to the frame pool.
   * <B>Important Note</B>: Clients <B>MUST</B> use this method when they are done with frames
   *
   * @param returnedFrame The <CODE>AnimationFrame</CODE> to return to the pool
   */
  public void ReturnFrame( AnimationFrame returnedFrame ) {
    theirFramePool.ReturnFrame( returnedFrame );
  }

}

