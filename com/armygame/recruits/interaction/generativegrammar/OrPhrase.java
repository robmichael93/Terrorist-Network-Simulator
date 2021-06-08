
/**

<B>File:</B> OrPhrase.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/


package com.armygame.recruits.interaction.generativegrammar;

import java.util.*;
import java.lang.Math;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.utils.Visitable;
import com.armygame.recruits.utils.Assert;
import com.armygame.recruits.exceptions.NoPhraseList;
import com.armygame.recruits.exceptions.ExitUtilities;
import com.armygame.recruits.exceptions.AssertFailedException;
import com.armygame.recruits.globals.RecruitsRandom;

// TestHarness
import com.armygame.recruits.mediaelements.*;
import com.armygame.recruits.mediaelements.rawmedia.*;
import com.armygame.recruits.mediaelements.players.*;

/**

An <code>OrPhrase</code> object maintains a set of GenerativePhrases and
implements the policies for phrase list operations, including traversals
for visitors.

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public class OrPhrase extends NonTerminalPhrase {

  /**
   * The object that encapsulates the PhraseList operations
   * that implement OrPhrase operations
  **/
  private OrPhraseList myPhraseList;

  private final static java.util.Random theirRandomSource = new java.util.Random();

  /**

  An <code>OrPhraseList</code> object encapsulates the functionality for
  maintaining a list of GenerativePhrases that may be selected from using
  an OR selection process.  The OR selection process uses probability
  weighting to allow the preference for a selection to be weighted as
  a probability from 0-100% that a phrase be selected during a visitor
  traversal

  @version 1.0
  @author Neal Elzenga
  @since Build P1

  **/
  public class OrPhraseList implements PhraseList, Cloneable, Visitable {


    /**
     * The set of OR phrases
    **/
    private LinkedList myPhraseSequence;

    /**
     * The set of weightings used to resolve OR selection
    **/
    private PhraseWeightTable myPhraseWeights;


    public class PhraseWeightTable implements Cloneable, Visitable {

      class AllocatedRange implements Cloneable {
        private int myStartOfRange;
        private int myEndOfRange;
        private int myPhraseRef;

        public AllocatedRange() {
          myStartOfRange = 0;
          myEndOfRange = 0;
          myPhraseRef = 0;
        }

        public AllocatedRange( int start, int end, int ref ) {
          myStartOfRange = start;
          myEndOfRange = end;
          myPhraseRef = ref;
        }

        public int startOfRange() {
          return myStartOfRange;
        }

        public int endOfRange() {
          return myEndOfRange;
        }

        public int associatedPhrase() {
          return myPhraseRef;
        }

        public void setRange( int start, int end, int ref ) {
          myStartOfRange = start;
          myEndOfRange = end;
          myPhraseRef = ref;
        }

        // Implement Cloneable
        public Object clone() {
          Object Result = null;
          try {
            Result = super.clone();
          } catch( Exception e ) {
          }
          return Result;
        }

      } // class AllocatedRange

      private int[] myPhraseWeightLookupTable;
      private ArrayList myAllocationTable;

      public PhraseWeightTable() {
        myPhraseWeightLookupTable = new int[ 100 ];
        myAllocationTable = new ArrayList();

        // Initialize the table to no-ops
        for( int i = 0; i < 100; i++ ) {
          // -1 is an illegal value guaranteed to throw if accesed through
          myPhraseWeightLookupTable[ i ] = -1;
        } // for

      } // PhraseWeightTable Constructor


      // This adds equally weighted phrase weights
      public void allocatePhraseWeights( int phrasesequenceref ) {

        int EqualPercentage = (int) Math.round( (1.0 / (double) ( myAllocationTable.size() + 1 ) ) * 100.0 );

        // Adjust everything already in the table
        int EndOfRange = 0;
        int Ref = 0;

        myAllocationTable.add( new AllocatedRange( myAllocationTable.size() * EqualPercentage, myAllocationTable.size() * EqualPercentage + EqualPercentage, phrasesequenceref ) );

        for( int i = 0; i < myAllocationTable.size(); i++ ) {

          AllocatedRange CurrentRange = (AllocatedRange)myAllocationTable.get( i );
          Ref = CurrentRange.associatedPhrase();

          CurrentRange.setRange( i * EqualPercentage, i * EqualPercentage + EqualPercentage, Ref );
          for( int j = CurrentRange.startOfRange(); j < CurrentRange.endOfRange(); j++ ) {
            myPhraseWeightLookupTable[ j ] = Ref;
          } // for weight range

          EndOfRange = CurrentRange.endOfRange();

        } // for allocation table

        // Fill in any leftovers due to percentage rounding errors
        for( int i = EndOfRange; i < 100; i++ ) {
          myPhraseWeightLookupTable[ i ] = Ref;
        }

      }

      // Implement Cloneable
      public Object clone() {
        Object Result = null;

        // Deep copy the phrase weight table
        try {
          Result = super.clone();
          ((PhraseWeightTable)Result).myPhraseWeightLookupTable = (int [])myPhraseWeightLookupTable.clone();
          for( int i = 0; i < myPhraseWeightLookupTable.length; i++ ) {
            ((PhraseWeightTable)Result).myPhraseWeightLookupTable[ i ] = myPhraseWeightLookupTable[ i ];
          }
          ((PhraseWeightTable)Result).myAllocationTable = (ArrayList)myAllocationTable.clone();
          for( int i = 0; i < myAllocationTable.size(); i++ ) {
            (((PhraseWeightTable)Result).myAllocationTable).set( i, (AllocatedRange)(((AllocatedRange)myAllocationTable.get( i )).clone()) );
          }
        } catch( Exception e ) {
        }
        return Result;
      }

      // Implement Visitable
      public void accept( Visitor visitor ) {
        // Visit according to a dice roll
        // int PhraseChoice = theirRandomSource.nextInt( 100 );
        int PhraseChoice = RecruitsRandom.RandomIndex( 100 );
        visitor.visit((GenerativePhrase)(myPhraseSequence.get( myPhraseWeightLookupTable[ PhraseChoice ] )));
      }

    } // class PhraseWeightTable



    /**
     * OrPhraseList
     * Constructs a new empty list of OR phrases
    **/
    public OrPhraseList() {
      myPhraseSequence = new LinkedList();
      myPhraseWeights = new PhraseWeightTable();
    }

    // Implement PhraseList

    public void addPhrase( GenerativePhrase phrase ) {
      myPhraseSequence.addLast( phrase );
      myPhraseWeights.allocatePhraseWeights( myPhraseSequence.size() - 1 );
    }

    public void addPhrase( Object[] args ) {
    }

    /**
     * clear
     * Removes all elements from the phrase list and empties the
     * phrase weights
     * @returns void
    **/
    public void clear() throws NoPhraseList {
      myPhraseSequence.clear();
      myPhraseWeights = null;
    }


    /**
     * size
     * Returns the number of elements in the phrase list
     * @returns Number of phrases in the list
    **/
    public int size() throws NoPhraseList {
      return myPhraseSequence.size();
    }

    // Implement Cloneable
    public Object clone() {
      Object Result = null;
      try {
        Result = super.clone();
        ((OrPhraseList)Result).myPhraseSequence = (LinkedList)myPhraseSequence.clone();
        int Size = myPhraseSequence.size();
        for( int j = 0; j < Size; j++ ) {
          GenerativePhrase TP = (GenerativePhrase)(myPhraseSequence.get(j));
          GenerativePhrase DP = (GenerativePhrase)(TP.clone());
          (((OrPhraseList)Result).myPhraseSequence).set( j, DP );
        }

      } catch( CloneNotSupportedException e ) {
        ExitUtilities.ReportError( ExitUtilities.WARN, "Clone not implemented" );
      }

      return Result;

    }

    // Implement Visitable
    public void accept( Visitor visitor ) {
      visitor.visit( myPhraseWeights );
    }

  } // class OrPhraseList


  /**
   * OrPhrase
   * Constructs a new empty OrPhrase
  **/
  public OrPhrase() {
    super();
    myPhraseList = new OrPhraseList();
  } // OrPhrase


  // Implement PhraseList

  /**
   * clear
   * Removes all elements from the PhraseList
   * @returns void
   * @see OrPhraseList.clear
  **/
  public void clear() throws NoPhraseList {
    if ( myPhraseList == null ) {
      throw new NoPhraseList();
    }
  } // clear

  public void addPhrase( GenerativePhrase phrase ) {
    myPhraseList.addPhrase( (GenerativePhrase)phrase.clone() );
  }

  public void addPhrase( Object[] args ) {
  }


  /**
   * size
   * returns the number of elements in this PhraseList
   * @returns int - The number of phrases
   * @see SequencePhraseList.size
  **/
  public int size() throws NoPhraseList {
    if ( myPhraseList == null ) {
      throw new NoPhraseList();
    }
    return myPhraseList.size();
  } // size()

  // Implement Visitable
  public void accept( Visitor visitor ) {
    for( int i = 0; i < PhraseCount(); i++ ) {
      visitor.visit( myPhraseList );
    }
  }

  // Implement Cloneable
  public Object clone() {

    Object Result = null;

    Result = super.clone();
    ((OrPhrase)Result).myPhraseList = (OrPhraseList)myPhraseList.clone();

    return Result;

  } // clone()


} // class OrPhrase

