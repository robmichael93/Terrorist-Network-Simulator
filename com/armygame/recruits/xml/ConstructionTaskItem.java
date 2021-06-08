/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

/**
 * Defines the common interface that all task items in a <code>ConstructionTaskList</code>
 * use to implement their operations
 */
public interface ConstructionTaskItem {
  public void Execute( ParserStateMachine context );
  public boolean IsSentinel();
}