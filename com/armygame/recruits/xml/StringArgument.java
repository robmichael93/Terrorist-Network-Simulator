package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class StringArgument extends MethodArgument {

  private String myString;

  public StringArgument( String arg) {
    myString = arg;
  }

  public void Execute( ParserStateMachine context ) {
    context.AddArgument( new String( myString ) );
  }

}