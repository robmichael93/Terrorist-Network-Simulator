package com.armygame.recruits.playlist;

public class SoundQueueCompleteSynchronizationAction extends SynchronizationComponent {

  private MediaSynchronizationEvent mySyncEvent;

  public SoundQueueCompleteSynchronizationAction( String soundChannelName ) {
    super();
    System.out.println( "New SoundQComplete for " + soundChannelName );
    mySyncEvent = new MediaSynchronizationEvent( "Sound Completed", soundChannelName );
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    myIsDoneFlag = mySyncEvent.equals( testEvent );
    System.out.println( "TestEvent - Testing SoundQCompleteAction returning " + myIsDoneFlag );
    return myIsDoneFlag;
  }


  /**
   * Can't really add a nested component to an action - so this does nothing
   */
  public void AddSynchronizationAction(SynchronizationComponent nestedComponent) {
  }

  public String toXML() {
    return "Not Implemented";
  }


}
