
/**

<B>File:</B> PhraseList.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.exceptions.NoPhraseList;


/**

An <code>PhraseList</code> interface describes the functionality
for maintaining a set of phrases for a NonTerminalPhrase node in a
grammar.  The interface defines the operations on a set of phrases
that allow the NonTerminalPhrase to impose the correct order and
phrase selection policies for a concrete NonTerminalPhrase

@version 1.0
@author Neal Elzenga
@since Build P1

**/
interface PhraseList {



  /**
   * addPhrase
   * adds a new phrase to a non-terminal node's set of phrases.  The
   * concrete phrase object is responsible for correctly adding the phrase
   * according to the semantics of the phrase type.
   * @param phrase  The GenerativePhrase concrete phrase to add
   * @returns void
  **/
  public void addPhrase( GenerativePhrase phrase );
  public void addPhrase( Object[] args );

  /**
   * clear
   * Removes all elements from a PhraseList
   * @returns void
  **/
  public void clear() throws NoPhraseList;


  /**
   * size
   * Returns the number of elements in the PhraseList
   * (Does not perform a deep count - use an enumeration
   * visitor for that)
   * @returns int - Count of the number of phrases in this GenerativePhrase
  **/
  public int size() throws NoPhraseList;

} // interface PhraseList
