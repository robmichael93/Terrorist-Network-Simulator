package com.armygame.recruits.xml.parsestates;

/**
 * Title: RangeValue
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.globals.RecruitsPackageConstants;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.StringArgument;
import com.armygame.recruits.xml.ConstructorCall;
import com.armygame.recruits.xml.EndConstructorSentinel;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;
import com.armygame.recruits.xml.MakeArgumentFromStackTopSentinel;


/**
 * Class for handling XML RangeValue tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class RangeValue extends DocumentParseState implements RecruitsPackageConstants {

  /**
   * The single RangeValue
   */
  private static RangeValue theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private RangeValue() {
    super();
  }

  /**
   * Return the RangeValue <b>Singleton</b>
   * @return The RangeValue <b>Singleton</b>
   */
  public static RangeValue Instance() {
    if( theirInstance == null ) {
      theirInstance = new RangeValue();
    }
    return theirInstance;
  }

  /**
   * This state is called when a <b>&lt;RangeValue&gt;</b> element is parsed.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void InitializeState( ParserStateMachine context ) {
    context.AddConstructionTask( new ConstructorCall( UTILS_PACKAGE, "StateVector" ) );
    context.AddConstructionTask( new EndConstructorSentinel() );

    // Associate the <code>RangeMap</code> we've remembered from earlier in the parse with this <code>StateVector</code>
    context.AddConstructionTask( new ConstructionMethodCall( "AssociateRangeMap", new Class[] { String.class } ) );
    context.AddConstructionTask( new StringArgument( context.RecallTagValue( "RangeMapName" ) ) );
    context.AddConstructionTask( new EndMethodCallSentinel() );  }

  /**
   * This state is called when we get the end element for state RangeValue
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new StringArgument( context.RecallTagValue( "OverrideRangeName" ) ) );
    context.AddConstructionTask( new MakeArgumentFromStackTopSentinel() );
  }

}