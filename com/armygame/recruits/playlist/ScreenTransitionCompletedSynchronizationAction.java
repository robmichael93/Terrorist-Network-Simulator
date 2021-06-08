package com.armygame.recruits.playlist;


public class ScreenTransitionCompletedSynchronizationAction extends SynchronizationComponent {

  private MediaSynchronizationEvent mySyncEvent;

  public ScreenTransitionCompletedSynchronizationAction( String transitionName ) {
    super();
    mySyncEvent = new MediaSynchronizationEvent( "Screen Transition Complete", transitionName );
  }

  public String toXML() {
    return "NOT IMPLEMENTED";
  }

  public boolean TestEvent(MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent) {
    System.out.println( "TestEvent - Screen Transition Complete returning true" );
    return mySyncEvent.equals( testEvent );
  }

  public void AddSynchronizationAction(SynchronizationComponent nestedComponent) {
    // EMPTY
  }

}
