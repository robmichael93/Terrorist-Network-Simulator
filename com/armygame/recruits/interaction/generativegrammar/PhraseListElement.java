
/**

<B>File:</B> PhraseListElement.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import java.util.*;


/**

An <code>PhraseListElement</code> object

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public class PhraseListElement {


  /**
   * The GenerativePhrase element
  **/
  private GenerativePhrase myPhrase;

  /**
   * The PhraseListIterator from the PhraseList that this PhraseListElement
   * came from
  **/
  private PhraseListIterator myPhraseListPosition;


  public PhraseListElement( GenerativePhrase phrase, PhraseListIterator itr ) {
    myPhrase = phrase;
    myPhraseListPosition = (PhraseListIterator)itr.clone();
  }

  public PhraseListIterator getItr() {
    // Requestors get a copy of our PhraseListIterator so there are no unexpeted
    // side effects
    return (PhraseListIterator)myPhraseListPosition.clone();
  }

  public void setItr( PhraseListIterator itr ) {
    // To be safe make sure we aren't making in side effets with the
    // PhraseListIterator use to create us
    myPhraseListPosition = (PhraseListIterator)itr.clone();
  }

  public GenerativePhrase getPhrase() {
    return myPhrase;
  }

  public void setPhrase( GenerativePhrase phrase ) {
    myPhrase = phrase;
  }

}
