/**

<B>File:</B> SequencePhrase.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.exceptions.NoPhraseList;
import com.armygame.recruits.exceptions.ExitUtilities;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.utils.Visitable;
import com.armygame.recruits.utils.ReflectiveVisitor;
import java.util.*;

// For test harness
import com.armygame.recruits.mediaelements.*;
import com.armygame.recruits.mediaelements.rawmedia.*;
import com.armygame.recruits.mediaelements.players.*;

/**

An <code>SequencePhrase</code> object maintains the phrases it composes
in sequential FIFO order.  This sequential order is enforced by it's
accept()ance of visitors

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public class SequencePhrase extends NonTerminalPhrase {


  /**
   * The sequenced list of phrases that compose the SequencePhrase
  **/
  private SequencePhraseList myPhraseList;


  /**

  An <code>SequencePhraseList</code> object maintains a sequential list
  of GenerativePhrases.  The method supports cloning and its visitors
  know how to recurse in sequential order on the phrases in the list

  @version 1.0
  @author Neal Elzenga
  @since Build P1

  **/
  public class SequencePhraseList implements PhraseList, Cloneable, Visitable {


    /**
     * The ordered set of phrases
    **/
    private LinkedList myPhraseSequence;


    /**
     * get
     * Returns the GenerativePhrase at the given index in the list
     * @param index  The index in the list of the item sought
     * @returns GenerativePhrase  The phrase stored at the requested index or null
     *                            if the requested index is out of range
     * @see LinkedList.get( int )
    **/
    public GenerativePhrase get( int index ) {

      GenerativePhrase Result = null;

      try {
        Result =(GenerativePhrase)(myPhraseSequence.get( index ));
      } catch( IndexOutOfBoundsException e ) {
      }

      return Result;

    } // GenerativePhrase get( int index )


    public void set( int index, GenerativePhrase phrase ) {
      myPhraseSequence.set( index, phrase );
    }


    // Implement PhraseList

    public void addPhrase( GenerativePhrase phrase ) {
      myPhraseSequence.addLast( (GenerativePhrase) phrase.clone() );
    }

    public void addPhrase( Object[] args ) {
    }


    public void clear() throws NoPhraseList {
      myPhraseSequence.clear();
    }

    public int size() throws NoPhraseList {
      return myPhraseSequence.size();
    }

    // Implement Visitable
    public void accept( Visitor visitor ) {
      // Build a playlist by visiting the sequence of phrases
      ListIterator Itr = myPhraseSequence.listIterator(0);

      while( Itr.hasNext() ) {
        GenerativePhrase GP = (GenerativePhrase) Itr.next();
        visitor.visit( GP );
      }

    }

    // Implement Cloneable
    public Object clone() {
      ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone SequencePhraseList" );
      Object Result = null;

      try {
        Result = super.clone();
        ((SequencePhraseList)Result).myPhraseSequence = (LinkedList)(myPhraseSequence.clone() );
        int Size = myPhraseSequence.size();
        for( int j = 0; j < Size; j++ ) {
          GenerativePhrase TP = (GenerativePhrase)(myPhraseSequence.get(j));
          GenerativePhrase DP = (GenerativePhrase)(TP.clone());
          (((SequencePhraseList)Result).myPhraseSequence).set( j, DP );
        }
      } catch( CloneNotSupportedException e ) {
        ExitUtilities.ReportError( ExitUtilities.WARN, "Clone blew" );
      }

      ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone SequencePhraseList" );
      return Result;

    }

    public SequencePhraseList() {
      myPhraseSequence = new LinkedList();
    }

  } // class SequencePhraseList

  public SequencePhrase() {
    super();
    myPhraseList = new SequencePhraseList();
  }


  // Implement Visitable
  public void accept( Visitor visitor ) {
    for( int i = 0; i < PhraseCount(); i++ ) {
      visitor.visit( myPhraseList );
    }
  }

  // Implement Cloneable
  public Object clone() {
    ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone SequencePhrase" );
    Object Result = null;

    Result = super.clone();
    ((SequencePhrase)Result).myPhraseList = (SequencePhraseList)(myPhraseList.clone());
    ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone SequencePhrase" );
    return Result;

  }

  // Implement PhraseList

  public void addPhrase( GenerativePhrase phrase ) {
    myPhraseList.addPhrase( (GenerativePhrase)(phrase.clone()) );
  }

  public void addPhrase( Object[] args ) {
  }

  public void clear() throws NoPhraseList {
    myPhraseList.clear();
  }

  public int size() throws NoPhraseList {
    return myPhraseList.size();
  }


} // class SequencePhrase
