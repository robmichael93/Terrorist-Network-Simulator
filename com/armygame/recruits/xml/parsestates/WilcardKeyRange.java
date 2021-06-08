package com.armygame.recruits.xml.parsestates;

/**
 * Title: WilcardKeyRange
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
 * Class for handling XML WilcardKeyRange tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class WilcardKeyRange extends DocumentParseState {

  /**
   * The single WilcardKeyRange
   */
  private static WilcardKeyRange theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private WilcardKeyRange() {
    super();
  }

  /**
   * Return the WilcardKeyRange <b>Singleton</b>
   * @return The WilcardKeyRange <b>Singleton</b>
   */
  public static WilcardKeyRange Instance() {
    if( theirInstance == null ) {
      theirInstance = new WilcardKeyRange();
    }
    return theirInstance;
  }


  /**
   * This state is called when we get the end element for state WilcardKeyRange
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new ConstructionMethodCall( "AddWildcardRange", new Class[] { String.class } ) );
    context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
    context.AddConstructionTask( new EndMethodCallSentinel() );
  }

}