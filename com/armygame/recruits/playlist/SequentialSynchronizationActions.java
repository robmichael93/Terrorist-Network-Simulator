package com.armygame.recruits.playlist;


import java.util.ArrayList;


public class SequentialSynchronizationActions extends SynchronizationComponent {

  private int myActiveTestIndex;

  /**
   * The list of synchronization actions that will be checked in the order placed in the list
   */
  private ArrayList mySequentialSynchActions;

  /**
   * Make new, empty sequential synchronization component
   */
  public SequentialSynchronizationActions() {
    super();
    myActiveTestIndex = 0;
    mySequentialSynchActions = new ArrayList();
  }

  /**
   * Add the supplied <code>SynchronizationComponent</code> to the end of the list of
   * sequential constructs we are maintaining
   */
  public void AddSynchronizationAction( SynchronizationComponent nestedConstruct ) {
    mySequentialSynchActions.add( nestedConstruct );
  }


  public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent ) {
    //System.out.println( "SeqSyncAction" );
    for(;;) {
      SynchronizationComponent TestComponent = (SynchronizationComponent) mySequentialSynchActions.get( myActiveTestIndex );
      boolean ThisTestResult = TestComponent.TestEvent( playerContext, testEvent );
      if ( ThisTestResult == true ) {
        myActiveTestIndex++;
        if ( myActiveTestIndex >= mySequentialSynchActions.size() ) {
          myIsDoneFlag = true;
          break;
        }
      } else {
        break;
      }
    }
    //System.out.println( "SeqSyncAction returning " + myIsDoneFlag );
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
    Result.append( "<Seq>" );
    for( int i = 0, len = mySequentialSynchActions.size(); i < len; i++ ) {
      Result.append( ( (SynchronizationComponent) mySequentialSynchActions.get( i ) ).toXML() );
    }
    Result.append( "</Seq>" );
    return Result.toString();
  }

}


