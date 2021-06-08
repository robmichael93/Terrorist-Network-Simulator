package com.armygame.recruits.xml.parsestates;

/**
 * Title: Range
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;

/**
 * Class for handling XML Range tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class Range extends DocumentParseState {

  /**
   * The single Range
   */
  private static Range theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private Range() {
    super();
  }

  /**
   * Return the Range <b>Singleton</b>
   * @return The Range <b>Singleton</b>
   */
  public static Range Instance() {
    if( theirInstance == null ) {
      theirInstance = new Range();
    }
    return theirInstance;
  }

  /**
   * This state is called when a <b>&lt;Range&gt;</b> element is parsed.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void InitializeState( ParserStateMachine context ) {
    context.AddConstructionTask( new ConstructionMethodCall( "DefineRange", new Class[] { String.class, int.class, int.class } ) );
  }


  /**
   * This state is called when we get the end element for state Range
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}
