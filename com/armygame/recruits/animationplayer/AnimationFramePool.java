package com.armygame.recruits.animationplayer;


import java.util.Stack;



/**
 * Provides a pool of pre-constructed <CODE>BranchingFrame</CODE> and <CODE>NonBranchingFrame</CODE>
 * objects.  The pool is self-scaling in that if it is out of frames of a given type a 
 * request for a new one will call the appropriate constructor.
 * 
 * <B>Important Note</B>: The success of the pool is reliant upon clients properly 
 * recylcing the frames they've obtained from the pool.  Clients <B>should ALWAYS</B> 
 * call <CODE>ReturnFrame()</CODE> before exiting a context in which a data structure
 * or object that refers to a frame from the pool goes out of scope.  This functionality
 * can be handled in any <CODE>java.util.[Collection]</CODE> context whenever
 * something like a <B>clear()</B> method is called.
 */
public class AnimationFramePool {

  /**
   * The pool of <CODE>BranchingFrame</CODE>s implemented as a <CODE>Stack</CODE> for 
   * locality of reference so that frames that were just used and memory fresh are the 
   * first re-used.
   */
  private Stack myBranchingFrames;
  /**
   * The pool of <CODE>NonBranchingFrame</CODE>s implemented as a <CODE>Stack</CODE> for
   * locality of reference so that frames that were just used and memory fresh are the
   * first re-used
   */
  private Stack myNonBranchingFrames;

  /**
   * Tracks the number of <CODE>BranchingFrame</CODE>s currently available in the pool
   */
  private int myNumAvailableBranchingFrames;

  /**
   * Tracks the number of <CODE>NonBranchingFrame</CODE>s currently available in the pool
   */
  private int myNumAvailableNonBranchingFrames;

  private int myNominalCapacity;

  /**
   * Constructs a new pool of frames and pre-populates it to the specified initial
   * capacity
   * 
   * @param initialCapacity The number of <CODE>BranchingFrames</CODE> and <CODE>NonBranchingFrames</CODE> to
   * initially place in the pool
   */
  public AnimationFramePool( int initialCapacity ) {
    myNominalCapacity = initialCapacity;
    myBranchingFrames = new Stack();
    myNonBranchingFrames = new Stack();
    for( int i = 0; i < initialCapacity; i++ ) {
      myBranchingFrames.push( new BranchingFrame() );
      myNonBranchingFrames.push( new NonBranchingFrame() );
    }
    myNumAvailableBranchingFrames = myNumAvailableNonBranchingFrames = initialCapacity;
  }

  /**
   * Returns the next available <CODE>BranchingFrame</CODE> if there is one in the pool.
   * Otherwise, it constructs a new one and returns that.
   * 
   * @return The requested <CODE>BranchingFrame</CODE>
   */
  public BranchingFrame GetBranchingFrame() {

    BranchingFrame Result = null;

    if ( myNumAvailableBranchingFrames == 0 ) {
      // Note that we don't change the count of available frames as we haven't changed the empty
      // status of the pool by newing up this one.  The count will correct itself when the user of 
      // this frame calls <B>ReturnFrame()</B>
      Result = new BranchingFrame();
    } else {
      Result = (BranchingFrame) myBranchingFrames.pop();
      myNumAvailableBranchingFrames--;
    }

    return Result;

  }

  /**
   * Returns the next available <CODE>NonBranchingFrame</CODE> if there is one in the pool.
   * Otherwise, it constructs a new one and returns that.
   * 
   * @return 
   */
  public NonBranchingFrame GetNonBranchingFrame() {
    NonBranchingFrame Result = null;

    if ( myNumAvailableNonBranchingFrames == 0 ) {
      // Note that we don't change the count of available frames as we haven't changed the empty
      // status of the pool by newing up this one.  The count will correct itself when the user of 
      // this frame calls <B>ReturnFrame()</B>
      Result = new NonBranchingFrame();
    } else {
      Result = (NonBranchingFrame) myNonBranchingFrames.pop();
      myNumAvailableNonBranchingFrames--;
    }

    return Result;

  }

  /**
   * Returns the <CODE>AnimationFrame</CODE> (which will be either a <CODE>BranchingFrame</CODE>
   * or a <CODE>NonBranchingFrame</CODE>) to the pool by returning its reference to the
   * type-appropriate pool of frame objects.
   * 
   * @param returnedFrame The frame to return
   */
  public void ReturnFrame( AnimationFrame returnedFrame ) {
    if ( returnedFrame instanceof BranchingFrame ) {
      if ( myNumAvailableBranchingFrames < myNominalCapacity ) {
        myBranchingFrames.push( (BranchingFrame) returnedFrame );
        myNumAvailableBranchingFrames++;
      } else {
        returnedFrame = null;
      }
    } else {
      if ( returnedFrame instanceof NonBranchingFrame ) {
        if ( myNumAvailableNonBranchingFrames < myNominalCapacity ) {
          myNonBranchingFrames.push( (NonBranchingFrame) returnedFrame );
          myNumAvailableNonBranchingFrames++;
        } else {
          returnedFrame = null;
        }
      }
    }
  }

}


