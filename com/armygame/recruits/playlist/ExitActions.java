package com.armygame.recruits.playlist;

import java.util.ArrayList;


/**
 * Maintains a list of exit actions to be performed at the end of an <code>ActionFrame</code>.
 * The exit actions will be performed sequentially in the order they are placed in the list
 */
public class ExitActions {


  /**
   * The sequential list of exit actions
   */
  private ArrayList myExitActions;

  /**
   * Create an empty set of actions
   */
  public ExitActions() {
    myExitActions = new ArrayList();
  }

  /**
   * Add an exit action to the list
   * @param exitAction The exit action to add to the list
   */
  public void AddExitAction( ExitAction exitAction ) {
    myExitActions.add( exitAction );
  }

  /**
   * Emit all the exit actions as XML - The XML is optimized for Java-Director messaging
   * and contains no whitespace or pretty printing
   * @return The exit actions as XML
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<ExitActions>" );
    for( int i = 0, len = myExitActions.size(); i< len; i++ ) {
      Result.append( ( (ExitAction) myExitActions.get( i ) ).toXML() );
    }
    Result.append( "</ExitActions>" );
    return Result.toString();
  }

}

