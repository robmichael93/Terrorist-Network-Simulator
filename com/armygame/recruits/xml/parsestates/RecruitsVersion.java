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
import com.armygame.recruits.globals.RecruitsPackageConstants;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.ConstructorCall;
import com.armygame.recruits.xml.CheckVersionSentinel;

public class RecruitsVersion extends DocumentParseState implements RecruitsPackageConstants {

  private static RecruitsVersion theirInstance = null;

  private RecruitsVersion() {
    super();
  }

  public static RecruitsVersion Instance() {
    if ( theirInstance == null ) {
      theirInstance = new RecruitsVersion();
    }
    return theirInstance;
  }

  public void InitializeState( ParserStateMachine context ) {
    context.AddConstructionTask( new ConstructorCall( UTILS_PACKAGE, "RecruitsVersion" ) );
  }

  public void EndElement( ParserStateMachine context, String urui, String localName, String qName ) {
    //System.out.println( "END of Recruits Version" );
    context.AddConstructionTask( new CheckVersionSentinel() );
  }

}
