
/**

<B>File:</B> TerminalPhrase.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.mediaelements.DialogMediaElement;
import com.armygame.recruits.mediaelements.rawmedia.MediaAsset;

// Debug
import com.armygame.recruits.exceptions.ExitUtilities;


/**

An <code>TerminalPhrase</code> object forms a leaf of a production rule
grammar.  The terminal phrase holds the concrete production element
corresponding with grammar

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public class TerminalPhrase extends GenerativePhrase {

  /**
   * The media element associated with this terminal
  **/
  private DialogMediaElement myMediaElement;


  /**
   * TerminalPhrase
   * Creates a new terminal phrase with an empty MediaElement
  **/
  public TerminalPhrase() {
    myMediaElement = null;
  } // TerminalPhrase( String phrasetext )


  /**
   * TerminalPhrase
   * @param mediaelement  The media element that holds the media for this
   *                      node.
   * @see TerminalPhrase( MediaElement )
  **/
  public TerminalPhrase( DialogMediaElement mediaelement ) {
    myMediaElement = mediaelement;
  } // TerminalPhrase( String phrasetext, MediaElement mediaelement )


  /**
   * getMediaElement
   * @returns MediaElement  The Media element
   * @see TerminalPhrase()
  **/
  public DialogMediaElement getMediaElement() {
    return myMediaElement;
  }

  // Implement Cloneable

  /**
   * clone
   * Performs a deep copy of a TerminalPhrase
   * @returns Object The copy of the TerminalPhrase
   * @see RelatedMethods
  **/
  public Object clone() {

    ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone TerminalPhrase" );
    Object Result = null;

    // To do a deep copy we need a bitwise copy of ourselves and then need
    // to clone the composed MediaElement
    Result = super.clone();
    if ( myMediaElement != null ) {
      ((TerminalPhrase)Result).myMediaElement = (DialogMediaElement)( myMediaElement.clone() );
    }

    ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone TerminalPhrase" );
    return Result;
  }

  // Implement Visitable


  /**
   * accept
   * Accept a visitor to the TerminalPhrase and perform the visitor's operation
   * which defaults to reporting the TerminalPhrase's MediaAsset for adding
   * to the visitor's playlist
   * @param visitor  The concrete visitor for traversing phrases
   * @returns returntype - ReturnDescription
   * @see Visitor
  **/
  public void accept( Visitor visitor ) {
    if ( myMediaElement != null ) {
      visitor.visit( myMediaElement );
    }
  }

  public void addPhrase(GenerativePhrase phrase) {
  }

} // class TerminalPhrase

