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
import com.armygame.recruits.xml.StringArgument;

public class SubSystemName extends DocumentParseState {

  private static SubSystemName theirInstance = null;

  private SubSystemName() {
    super();
  }

  public static SubSystemName Instance() {
    if( theirInstance == null ) {
      theirInstance = new SubSystemName();
    }
    return theirInstance;
  }

  public void EndElement( ParserStateMachine context, String urui, String localName, String qName ) {
    context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
  }

}
