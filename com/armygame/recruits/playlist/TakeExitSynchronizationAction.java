package com.armygame.recruits.playlist;

public class TakeExitSynchronizationAction extends SynchronizationComponent {

  /**
   * The name of the character who should take an exit action
   */
  private MediaSynchronizationEvent  myInterestedEvent;
  private String myRoleName;

  /**
   * Construct a take exit action for the named character
   * @param name The name of the character who is taking this exit action
   */
  public TakeExitSynchronizationAction( String name ) {
    super();
    myRoleName = name;
    myInterestedEvent = new MediaSynchronizationEvent( "TakeExit", name );
  }

  /**
   * Does nothing - can't add nested actions to a terminal action
   */
  public void AddSynchronizationAction( SynchronizationComponent nestedComponent ) {
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    System.out.println( "TestEvent - TakeExitSyncAction Taking Exit returning true for " + myRoleName );
    playerContext.GetAnimationPlayer().TakeExit( myRoleName );
    return true;
  }

  /**
   * Emit the taking exit action as XML optimized for Java-Director messaging and
   * not human readability
   * @return The XML for this take exit synchronization action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<TakeExit>" );
    // Result.append( myExitingCharName );
    Result.append( "</TakeExit>" );
    return Result.toString();
  }

}

