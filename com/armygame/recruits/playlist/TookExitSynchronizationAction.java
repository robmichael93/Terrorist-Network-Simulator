package com.armygame.recruits.playlist;

public class TookExitSynchronizationAction extends SynchronizationComponent {

  /**
   * The name of the character who should take an exit action
   */
  private MediaSynchronizationEvent  myInterestedEvent;

  /**
   * Construct a take exit action for the named character
   * @param name The name of the character who is taking this exit action
   */
  public TookExitSynchronizationAction( String name ) {
    super();
    myInterestedEvent = new MediaSynchronizationEvent( "TookExit", name );
  }

  /**
   * Does nothing - can't add nested actions to a terminal action
   */
  public void AddSynchronizationAction( SynchronizationComponent nestedComponent ) {
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    System.out.println( "TestEvent - TookExitSyncAction " + myInterestedEvent.toString() + " vs. " + testEvent.toString() + " returning " + myInterestedEvent.equals( testEvent ) );
    return myInterestedEvent.equals( testEvent );
  }

  public String toString() {
    String Result = "ToookExitSyncAction " + myInterestedEvent.toString();
    return Result;
  }

  /**
   * Emit the taking exit action as XML optimized for Java-Director messaging and
   * not human readability
   * @return The XML for this take exit synchronization action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<TookExit>" );
    // Result.append( myExitingCharName );
    Result.append( "</TookExit>" );
    return Result.toString();
  }

}

