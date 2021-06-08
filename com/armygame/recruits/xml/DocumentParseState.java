package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import org.xml.sax.Attributes;


/**
 * This abstract class is the prototype for all concrete parse state objects.
 * 
 * IMPORTANT:  This abstract base class provides the structure for automatic code generation
 * of parse state classes.  The code generator will put empty method calls for each parse
 * state action ( InitializeState, StartDocument, EndDocument, StartElement, EndElement,
 * and Characters ).  Note that this class is abstract but provides default 'do nothing' 
 * implementations for its operations so that generated classes need only define the 
 * operations of relevance.  Conversely, the implementor should DELETE the empty implementation
 * of any methods not customized in the generated file so as to let the class default to this
 * base class' default implementation.
 */
abstract public class DocumentParseState {

  public DocumentParseState() {
  }

  /**
   * This state is called when a element is parsed.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void InitializeState( ParserStateMachine context ) {}

  /**
   * This state should be called once per XML document to get the ball rolling
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void StartDocument( ParserStateMachine context ) {}

  /**
   * This state should be called once per XML document upon receipt of the END tag
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  public void EndDocument( ParserStateMachine context ) {}

  /**
   * This method is called when the parser sees a new element while parsing the current element.
   * A state need not override this if it delegates functionality to its sub-tags
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri The URI of the tag in SAX parlance
   * @param localName The Local Name of the tag in SAX parlance
   * @param qName The Q Name of the tag in SAX parlance
   * @param atts The attributes for this tag as a SAX <code>Attribute</code> object
   */
  public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
    // By default we change state to the Q Name
    ChangeState( context, qName );
  }


  /**
   * This method is called upon reading the closing tag of an element
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri The URI of the tag in SAX parlance
   * @param localName The Local Name of the tag in SAX parlance
   * @param qName The Q Name of the tag in SAX parlance
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {}


  /**
   * For non-empty tags this method is called to add the chars to the buffer for this state.
   * Note that the SAX parsing model may not swallow all the characters for a tag in one
   * gulp, so this routine must incrementally build up the buffer if called multiple times
   */
  public void Characters( ParserStateMachine context, char[] ch, int start, int length ) {
    context.AddCharsToBuffer( ch, start, length );
  }

  /**
   * When in a state and we receive a new tag event from SAX we use this method to change state
   * to the <code>DocumentParseState</code> object for that tag.  This method helps implement
   * the <b>StateMachine</b> design pattern.
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param newState The <code>DocumentParseState</code> object the state machine to switch to for procssing (up to the <code>EndElement</code> for this state)
   */
  protected void ChangeState( ParserStateMachine context, DocumentParseState newState ) {
    context.ChangeState( newState );
  }

  /**
   * Like the preceding method this version of change state uses the facilties of the 
   * context's <code>ParserStateMachine</code> to do the state change lookup by name
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param newStateName The name of the next state (typically the <b>Q Name</b> from the tag just parsed)
   */
  protected void ChangeState( ParserStateMachine context, String newStateName ) {
    context.ChangeState( newStateName );
  }

  /**
   * This method informs the <code>ParserStateMachine</code> that we are done with this tag,
   * and thus with this state
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   */
  protected void StateHasEnded( ParserStateMachine context ) {
    context.StateHasEnded();
  }

}
