/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.xml;

import com.armygame.recruits.xml.ParserStateMachine;


/**
 * Converts the object on the top of the stack into an argument
 */
public class MakeArgumentFromStackTopSentinel extends ConstructionOperation {

  public MakeArgumentFromStackTopSentinel() {
  }

  public void Execute( ParserStateMachine context ) {
    context.AddArgumentFromStack();
  }

}