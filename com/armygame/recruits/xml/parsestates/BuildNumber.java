package com.armygame.recruits.xml.parsestates;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.IntArgument;

public class BuildNumber extends com.armygame.recruits.xml.DocumentParseState {

  private static BuildNumber theirInstance = null;

  private BuildNumber() {
    super();
  }

  public static BuildNumber Instance() {
    if( theirInstance == null ) {
      theirInstance = new BuildNumber();
    }
    return theirInstance;
  }

  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new IntArgument( context.CharBufferToInt() ) );
  }

}
