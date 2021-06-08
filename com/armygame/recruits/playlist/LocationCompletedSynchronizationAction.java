package com.armygame.recruits.playlist;

public class LocationCompletedSynchronizationAction extends SynchronizationComponent {

  private MediaSynchronizationEvent mySyncEvent;

  public LocationCompletedSynchronizationAction( String locName ) {
    super();
    mySyncEvent = new MediaSynchronizationEvent( "Location Completed", locName );
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    System.out.println( "TestEvent - Location Completed returning true" );
    return mySyncEvent.equals( testEvent );
  }

  public void AddSynchronizationAction( SynchronizationComponent nestedComponent ) {
    // EMpty
  }

  public String toXML() {
    return "NOT IMPLEMENTED";
  }
}
