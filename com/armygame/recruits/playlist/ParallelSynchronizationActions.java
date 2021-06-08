package com.armygame.recruits.playlist;

import java.util.HashSet;
import java.util.Iterator;


public class ParallelSynchronizationActions extends SynchronizationComponent {

  private HashSet myParallelSynchActions;

  /**
   * Make new, empty sequential synchronization component
   */
  public ParallelSynchronizationActions() {
    super();
    myParallelSynchActions = new HashSet();
  }

  /**
   * Add the supplied <code>SynchronizationComponent</code> to the end of the list of
   * sequential constructs we are maintaining
   */
  public void AddSynchronizationAction( SynchronizationComponent nestedConstruct ) {
    myParallelSynchActions.add( nestedConstruct );
  }

  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {

    Iterator OutstandingActionsItr = myParallelSynchActions.iterator();

    //System.out.println( "ParSyncAction" );
    while( OutstandingActionsItr.hasNext() ) {
      SynchronizationComponent TestAction = (SynchronizationComponent) OutstandingActionsItr.next();
      if ( TestAction.TestEvent( playerContext, testEvent ) ) {
        // myParallelSynchActions.remove( TestAction );
        System.out.println( "Removing " + TestAction.toString() );
        OutstandingActionsItr.remove();
      } else {
        // break;
      }
    }

    if ( myParallelSynchActions.size() == 0 ) {
      myIsDoneFlag = true;
    }


    //System.out.println( "ParSyncAction returning " + myIsDoneFlag );
    return myIsDoneFlag;

  }

  /**
   * Emits the synchronization policy components in this sequential list via recursive traversal
   * to the set of XML that describes the exit criteria for an action frame.  The XML is
   * optimized for Java-Director messaging and contains no whitespace and is not very human readbale
   * @return The XML for the nested constructs in this policy block
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<Par>" );
    // for( int i = 0, len = myParallelSynchActions.size(); i < len; i++ ) {
    //  Result.append( ( (SynchronizationComponent) myParallelSynchActions.get( i ) ).toXML() );
    // }
    Result.append( "</Par>" );
    return Result.toString();
  }

}
