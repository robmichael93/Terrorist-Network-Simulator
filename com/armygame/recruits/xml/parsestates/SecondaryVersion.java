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

public class SecondaryVersion extends DocumentParseState {

  private static SecondaryVersion theirInstance = null;

  private SecondaryVersion() {
    super();
  }

  public static SecondaryVersion Instance() {
    if( theirInstance == null ) {
      theirInstance = new SecondaryVersion();
    }
    return theirInstance;
  }

  public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
    ChangeState( context, qName );
  }

  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new IntArgument( context.CharBufferToInt() ) );
  }

}
