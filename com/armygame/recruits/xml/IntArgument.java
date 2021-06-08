package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class IntArgument extends MethodArgument {

  private int myInt;

  public IntArgument( int arg ) {
    myInt = arg;
  }

  public void Execute( ParserStateMachine context ) {
    context.AddArgument( new Integer( myInt ) );
  }

}