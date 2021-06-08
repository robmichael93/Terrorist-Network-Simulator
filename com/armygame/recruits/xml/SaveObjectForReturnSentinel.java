/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.xml;


/**
 * This class is used to indicate that the last object on the frames stack is to be moved to the return stack
 * as an object to be returned from the parsing operation
 */
public class SaveObjectForReturnSentinel extends ConstructionOperation {

  public SaveObjectForReturnSentinel() {
    super();
  }

  /**
   * Tell the context to move this item to the return stack
   */
  public void Execute( ParserStateMachine context ) {
    context.MoveFrameObjectToReturnStack();
  }

}