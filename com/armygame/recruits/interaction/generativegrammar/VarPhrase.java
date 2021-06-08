
package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.utils.Visitable;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.interaction.variables.*;
import com.armygame.recruits.mediaelements.*;
import com.armygame.recruits.mediaelements.rawmedia.*;
import com.armygame.recruits.mediaelements.players.*;
import com.armygame.recruits.exceptions.NoPhraseList;
import java.util.ArrayList;


public class VarPhrase extends NonTerminalPhrase {

  private VarPhraseList myVarPhrases;

  // The variable we're watching to evaluate during visits
  private EnumerationVariable myVariable;

  public class VarPhraseList implements PhraseList, Cloneable, Visitable {

    private ArrayList myPhraseSet;

    public VarPhraseList() {
      myPhraseSet = new ArrayList( myVariable.size() );
      for( int i = 0; i < myVariable.size(); i++ ) {
        myPhraseSet.add( null );
      }
    }

    // Implement Visitable
    public void accept( Visitor visitor ) {
      // Add the phrase that matches the variable's value
      int Index = ((EnumerationValue)myVariable.getValue()).getValue();
      visitor.visit( (GenerativePhrase) myPhraseSet.get( Index ) );
    }

    // Implement Cloneable
    public Object clone() {
      Object Result = null;

      try {
        Result = super.clone();
        ((VarPhraseList)Result).myPhraseSet = (ArrayList) myPhraseSet.clone();
        for( int i = 0; i < myPhraseSet.size(); i++ ) {
          ((VarPhraseList)Result).myPhraseSet.set( i, (GenerativePhrase)(((GenerativePhrase)myPhraseSet.get( i )).clone()) );
        }
      } catch( CloneNotSupportedException e ) {
      }

      return Result;
    }

    // Implement PhraseList
    /**
     * addPhrase
     * adds a new phrase to a non-terminal node's set of phrases.  The
     * concrete phrase object is responsible for correctly adding the phrase
     * according to the semantics of the phrase type.
     * @param phrase  The GenerativePhrase concrete phrase to add
     * @returns void
    **/
    public void addPhrase( GenerativePhrase phrase ) {
    }

    public void addPhrase( Object[] args ) {
      // phrase, EnumerationValue
      myPhraseSet.set( ((EnumerationValue)args[1]).getValue(), (GenerativePhrase)args[0] );
    }


    /**
     * clear
     * Removes all elements from a PhraseList
     * @returns void
    **/
    public void clear() throws NoPhraseList {
      myPhraseSet.clear();
    }


    /**
     * size
     * Returns the number of elements in the PhraseList
     * (Does not perform a deep count - use an enumeration
     * visitor for that)
     * @returns int - Count of the number of phrases in this GenerativePhrase
    **/
    public int size() throws NoPhraseList {
      return myPhraseSet.size();
    }

  } // class VarPhraseList

  public VarPhrase( final EnumerationVariable variable ) {
    super();
    myVariable = variable;
    myVarPhrases = new VarPhraseList();
  }

  // Implement Visitable
  public void accept( Visitor visitor ) {
    for( int i = 0; i < PhraseCount(); i++ ) {
      visitor.visit( myVarPhrases );
    }
  }

  // Implement Cloneable
  public Object clone() {
    Object Result = null;

    Result = super.clone();
    ((VarPhrase)Result).myVarPhrases = (VarPhraseList) myVarPhrases.clone();

    return Result;
  }

  // Implement PhraseList
  /**
   * addPhrase
   * adds a new phrase to a non-terminal node's set of phrases.  The
   * concrete phrase object is responsible for correctly adding the phrase
   * according to the semantics of the phrase type.
   * @param phrase  The GenerativePhrase concrete phrase to add
   * @returns void
  **/
  public void addPhrase( GenerativePhrase phrase ) {
    myVarPhrases.addPhrase( phrase );
  }

  public void addPhrase( Object[] args ) {
    // phrase, EnumerationValue
    myVarPhrases.addPhrase( args );
  }


  /**
   * clear
   * Removes all elements from a PhraseList
   * @returns void
  **/
  public void clear() throws NoPhraseList {
    myVarPhrases.clear();
  }


  /**
   * size
   * Returns the number of elements in the PhraseList
   * (Does not perform a deep count - use an enumeration
   * visitor for that)
   * @returns int - Count of the number of phrases in this GenerativePhrase
  **/
  public int size() throws NoPhraseList {
    return myVarPhrases.size();
  }


} // class VarPhrase
