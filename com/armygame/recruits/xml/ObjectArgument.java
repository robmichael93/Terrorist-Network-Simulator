package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ObjectArgument extends MethodArgument {

  private Object myArg;

  public ObjectArgument( Object arg ) {
    myArg = arg;
  }

  public void Execute( ParserStateMachine context ) {
    context.AddArgument( myArg );
  }

}
