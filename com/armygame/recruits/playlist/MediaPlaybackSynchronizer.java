package com.armygame.recruits.playlist;

import java.util.HashSet;

import com.armygame.recruits.services.SimpleObjectFIFO;


public class MediaPlaybackSynchronizer implements Runnable {

  private PlaylistSynchronizationCallback myInvoker;
  private MediaPlayerContext myMediaPlayerContext;
  private SynchronizationComponent mySynchronizationRoot;
  private HashSet myUncaughtSyncEvents;
  private MediaSynchronizationEvent myTestEvent;
  private boolean myRunningFlag;
  private SimpleObjectFIFO myRequestQueue;

  public MediaPlaybackSynchronizer( MediaPlayerContext playerContext, PlaylistSynchronizationCallback invoker, SynchronizationComponent root ) {
    // System.out.println( "In MediaPlaybackSYnchronizer Constructor" );
    myInvoker = invoker;
    myMediaPlayerContext = playerContext;
    mySynchronizationRoot = root;
    myUncaughtSyncEvents = new HashSet();
    myTestEvent = null;
    myRunningFlag = true;
    myRequestQueue = new SimpleObjectFIFO( 8 );
  }

  public void run () {
    WaitForSyncEvents();
  }

  public void WaitForSyncEvents() {
    while( myRunningFlag ) {
      try {
        myTestEvent = ( MediaSynchronizationEvent ) myRequestQueue.remove();
        boolean TestResult = mySynchronizationRoot.TestEvent( myMediaPlayerContext, myTestEvent );
        if ( TestResult == false ) {
          System.out.println( "Adding event that tested false - " + myTestEvent.toString() );
          myUncaughtSyncEvents.add( myTestEvent );
        }
        myTestEvent = null;
        if ( mySynchronizationRoot.IsDone() ) {
          myRunningFlag = false;
        }
      } catch( Exception e ) {
        System.out.println( "Synchronizer threw" );
        e.printStackTrace();
        return;
      }

    }

    //System.out.println( "Synchronizer done" );
    myInvoker.FrameComplete();

  }

  public synchronized void TestEvent( MediaSynchronizationEvent testEvent ) {
    //System.out.println( "MPSynchronizer asked to sync with " + testEvent.toString() );
    try {
      myRequestQueue.add( testEvent );
    } catch( InterruptedException ie ) {
      return;
    }
  }

  public synchronized void Interrupt() {
    // System.out.println( "Synchronizer Interrupt called" );
    myRunningFlag = false;
    notifyAll();
  }

}
