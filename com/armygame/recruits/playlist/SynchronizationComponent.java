package com.armygame.recruits.playlist;


/**
 * Acts as an abstract <b>Component</b> root class in the <b>Composite</b> design pattern.
 * See "Design Patterns", Gamma et.al for more on the <b>Composite</b> pattern.  This class
 * is intended to allow mixing of <code>SynchronizationAction</code>s and
 * <code>SynchronizationConstruct</code>s into whole-part hierarchies
 * such that we can compose nested Synchronization constructs like the following example:
 *
 * Seq[ SoundQueueComplete(Sound1), Par[ TakeExit(Buddy), TakeExit(Protagonist) ]]
 * Where Seq = Perform the list of actions contained in sequence, and
 * Par = Perform the list of actions in parallel
 */
abstract public class SynchronizationComponent {

  protected boolean myIsDoneFlag;

  public SynchronizationComponent() {
    myIsDoneFlag = false;
  }

  public boolean IsDone() {
    //System.out.println( "Testing Sync component for done yields " + myIsDoneFlag );
    return myIsDoneFlag;
  }

  abstract public boolean TestEvent( MediaPlayerContext playerContext, MediaSynchronizationEvent testEvent );

  abstract public String toXML();
  abstract public void AddSynchronizationAction( SynchronizationComponent nestedComponent );

}


