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
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;

public class SubSystemVersion extends com.armygame.recruits.xml.DocumentParseState {

  private static SubSystemVersion theirInstance = null;

  private SubSystemVersion() {
    super();
  }

  public static SubSystemVersion Instance() {
    if( theirInstance == null ) {
      theirInstance = new SubSystemVersion();
    }
    return theirInstance;
  }

  public void InitializeState( ParserStateMachine context ) {
    context.AddConstructionTask( new ConstructionMethodCall( "SetSubSystemVersion", new Class[] { String.class, int.class, int.class, int.class } ) );
  }


  public void EndElement( ParserStateMachine context, String urui, String localName, String qName ) {
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}
