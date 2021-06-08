package com.armygame.recruits.playlist;

public class MovieCompletedSynchronizationAction extends SynchronizationComponent {

  private MediaSynchronizationEvent mySyncEvent;

  public MovieCompletedSynchronizationAction( String movieName ) {
    super();
    mySyncEvent = new MediaSynchronizationEvent( "Movie Completed", movieName );
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    return mySyncEvent.equals( testEvent );
  }

  public void AddSynchronizationAction( SynchronizationComponent nestedComponent ) {
    // EMpty
  }

  public String toXML() {
    return "NOT IMPLEMENTED";
  }
}
