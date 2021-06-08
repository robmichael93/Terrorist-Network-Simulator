/**
 * Title: ConstructionTaskList
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.xml;

import java.util.ArrayList;


/**
 * Maintains a list of construction tasks that result from parsing a Recruits XML data file.
 * The items in the task list know how to construct Recruits class objects via their polymorphic
 * <code>Execute()</code> method.
 */
public class ConstructionTaskList {

  /**
   * The list of task items
   */
  private ArrayList myConstructionTasks;

  /**
   * Initialize an empty task list
   */
  public ConstructionTaskList() {
    myConstructionTasks = new ArrayList();
  }

  /**
   * Adds a new <code>ConstructionTaskItem</code> to the set of tasks
   * @param newTaskItem The <code>ConstructionTaskItem</code> to insert
   */
  public void AddItem( ConstructionTaskItem newTaskItem ) {
    myConstructionTasks.add( newTaskItem );
  }

  /**
   * Empties the task list
   */
  public void ClearTaskList() {
    myConstructionTasks.clear();
  }

  /**
   * Traverse the list of tasks and do each task found
   */
  public void Execute( ParserStateMachine context ) {
    for( int i = 0; i < myConstructionTasks.size(); i++ ) {
      ( (ConstructionTaskItem) myConstructionTasks.get( i ) ).Execute( context );
    } // for
    ClearTaskList();
  }

}