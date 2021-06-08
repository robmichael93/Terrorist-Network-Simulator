/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

import com.armygame.recruits.xml.ParserStateMachine;


public class EndConstructorSentinel extends ConstructionEndSentinel {

  public EndConstructorSentinel() {
  }

  public void Execute( ParserStateMachine context ) {
    // System.out.println( "End of constructor" );
  }
}
