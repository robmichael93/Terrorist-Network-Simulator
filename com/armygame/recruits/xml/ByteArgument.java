package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ByteArgument extends MethodArgument {

  private byte myByte;

  public ByteArgument( byte arg ) {
    myByte = arg;
  }

  public void Execute( ParserStateMachine context ) {
    context.AddArgument( new Byte( myByte ) );
  }

}