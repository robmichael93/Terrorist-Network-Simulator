package com.armygame.recruits.playlist;

public class ImmediateExitSynchronizationAction extends SynchronizationComponent {

  /**
   * Construct a take exit action for the named character
   * @param name The name of the character who is taking this exit action
   */
  public ImmediateExitSynchronizationAction() {
    super();
  }

  /**
   * Does nothing - can't add nested actions to a terminal action
   */
  public void AddSynchronizationAction( SynchronizationComponent nestedComponent ) {
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    System.out.println( "TestEvent - ImmediateExitsyncAction returning true" );
    myIsDoneFlag = true;
    return myIsDoneFlag;
  }

  /**
   * Emit the taking exit action as XML optimized for Java-Director messaging and
   * not human readability
   * @return The XML for this take exit synchronization action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<ImmediateExit>" );
    Result.append( "</ImmediateExit>" );
    return Result.toString();
  }

}
