package com.armygame.recruits.xml.parsestates;

/**
 * Title: Width
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.IntArgument;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.EndMethodCallSentinel;


/**
 * Class for handling XML Width tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class Width extends DocumentParseState {

  /**
   * The single Width
   */
  private static Width theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private Width() {
    super();
  }

  /**
   * Return the Width <b>Singleton</b>
   * @return The Width <b>Singleton</b>
   */
  public static Width Instance() {
    if( theirInstance == null ) {
      theirInstance = new Width();
    }
    return theirInstance;
  }


  /**
   * This state is called when we get the end element for state Width
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new ConstructionMethodCall( "SetWidth", new Class[] { int.class } ) );
    context.AddConstructionTask( new IntArgument( context.CharBufferToInt() ) );
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}
