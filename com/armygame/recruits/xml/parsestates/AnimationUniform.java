
package com.armygame.recruits.xml.parsestates;

/**
 * Title: AnimationUniform
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
import com.armygame.recruits.xml.StringArgument;


/**
 * Class for handling XML AnimationUniform tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class AnimationUniform extends DocumentParseState {

  /**
   * The single AnimationUniform
   */
  private static AnimationUniform theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private AnimationUniform() {
    super();
  }

  /**
   * Return the AnimationUniform <b>Singleton</b>
   * @return The AnimationUniform <b>Singleton</b>
   */
  public static AnimationUniform Instance() {
    if( theirInstance == null ) {
      theirInstance = new AnimationUniform();
    }
    return theirInstance;
  }


  /**
   * This state is called when we get the end element for state AnimationUniform
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new ConstructionMethodCall( "SetUniform", new Class[] { String.class } ) );
    context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
    context.AddConstructionTask( new EndMethodCallSentinel() );  
  }

}