package com.armygame.recruits.playlist;


/**
 * Contains a nested hierarchy of synchronization actions that define the criteria for
 * leaving the current <code>ActionFrame</code>.  The policy uses the <b>Composite</b>
 * design pattern (See "Design Patterns", Gamma et. al.) to build a nested structure
 * of sequential and parallel synchyronization blocks.  Each block may contain other
 * blocks, or a specification of a synchronization action
 */
public class ActionSynchronizationPolicy {

  /**
   * The root of the synchronization policy composite hierarchy
   */
  private SynchronizationComponent myRootPolicyBlock;

  /**
   * Construct an empty policy
   */
  public ActionSynchronizationPolicy() {
    myRootPolicyBlock = null;
  }

  /**
   * Set the root policy block
   * @param policyBlock The policy block to set as the root policy block
   */
  public void SetRootPolicy( SynchronizationComponent policyBlock ) {
    myRootPolicyBlock = policyBlock;
  }

  public SynchronizationComponent GetRootPolicy() {
    return myRootPolicyBlock;
  }

  /**
   * Emit the synchronization policy as the <b>ExitCriteria</b> section of the Playlist
   * output to XML by recursively traversing the policy blocks inside the root.  The format
   * of the generated XML is optimized for Java-Director message and contains no whitespace
   * or attempt at human readability
   * @return The <b>ExitCriteria</b> section of an action frame's entry in the playlist as XML
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<Synchronized>" );
    Result.append( myRootPolicyBlock.toXML() );
    Result.append( "</Synchronized>" );
    return Result.toString();
  }

}

