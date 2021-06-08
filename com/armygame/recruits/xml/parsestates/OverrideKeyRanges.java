package com.armygame.recruits.xml.parsestates;

/**
 * Title: OverrideKeyRanges
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;

import com.armygame.recruits.utils.StateVector;
import com.armygame.recruits.globals.RecruitsPackageConstants;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.ConstructorCall;
import com.armygame.recruits.xml.EndConstructorSentinel;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;
import com.armygame.recruits.xml.MakeArgumentFromStackTopSentinel;
import com.armygame.recruits.xml.StringArgument;


/**
 * Class for handling XML OverrideKeyRanges tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class OverrideKeyRanges extends DocumentParseState implements RecruitsPackageConstants {

  /**
   * The single OverrideKeyRanges
   */
  private static OverrideKeyRanges theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private OverrideKeyRanges() {
    super();
  }

  /**
   * Return the OverrideKeyRanges <b>Singleton</b>
   * @return The OverrideKeyRanges <b>Singleton</b>
   */
  public static OverrideKeyRanges Instance() {
    if( theirInstance == null ) {
      theirInstance = new OverrideKeyRanges();
    }
    return theirInstance;
  }

  /**
   * This state is called when a <b>&lt;OverrideKEyRanges&gt;</b> element is parsed.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void InitializeState( ParserStateMachine context ) {
    // context.AddConstructionTask( new ConstructionMethodCall( "AddOverrideRange", new Class[] { StateVector.class } ) );
    // context.AddConstructionTask( new ConstructorCall( UTILS_PACKAGE, "StateVector" ) );
    // context.AddConstructionTask( new ConstructionMethodCall( "AssociateRangeMap", new Class[] { String.class } ) );
    // context.AddConstructionTask( new StringArgument( context.RecallTagValue( "RangeMapName" ) ) );
    // context.AddConstructionTask( new EndMethodCallSentinel() );
  }

  /**
   * This state is called when we get the end element for state LocationTemplateKeyStateVector
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    // context.AddConstructionTask( new EndConstructorSentinel() );
    // context.AddConstructionTask( new MakeArgumentFromStackTopSentinel() );
    // context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}
