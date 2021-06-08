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

public class PrincipleVersion extends DocumentParseState {

  private static PrincipleVersion theirInstance = null;

  private PrincipleVersion() {
    super();
  }

  public static PrincipleVersion Instance() {
    if( theirInstance == null ) {
      theirInstance = new PrincipleVersion();
    }
    return theirInstance;
  }

  public void InitializeState( ParserStateMachine context ) {
    context.AddConstructionTask( new ConstructionMethodCall( "SetPrincipleVersion", new Class[] { int.class, int.class, int.class } ) );
  }

  public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
    context.ClearCharacterBuffer();
    ChangeState( context, qName );
  }

  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }


}
