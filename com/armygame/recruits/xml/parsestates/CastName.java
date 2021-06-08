package com.armygame.recruits.xml.parsestates;

/**
 * Title: CastName
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.StringArgument;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;

/**
 * Class for handling XML CastName tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class CastName extends DocumentParseState {

  /**
   * The single CastName
   */
  private static CastName theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private CastName() {
    super();
  }

  /**
   * Return the CastName <b>Singleton</b>
   * @return The CastName <b>Singleton</b>
   */
  public static CastName Instance() {
    if( theirInstance == null ) {
      theirInstance = new CastName();
    }
    return theirInstance;
  }

  /**
   * This state is called when we get the end element for state CastName
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new ConstructionMethodCall( "SetCastName", new Class[] { String.class } ) );
    context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}