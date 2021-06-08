/**
 * Title: QueryVector
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;

import java.util.*;

/**
 *
 */
public class QueryVector implements BitOperations, QueryBitOperations {

  /**
   * This holds the set of wild bits for this <code>QueryVector</code>
   */
  BitVector myWildBits;

  /**
   * The <code>StateVector</code> associated with this query.  This is the repository for the explicit
   * connector representation of the query.
   */
  StateVector myStateVector;

  public String toString() {
    return myStateVector.toString() + "\n" + myWildBits.toString();
  }

  /**
   * Construct a QueryVector from the supplied <code>StateVector</code> with all bits set to wild
   * @param stateVector The <code>StateVector</code> to use to know what the range size is
   */
  public QueryVector( StateVector stateVector ) {
    myStateVector = stateVector;
    myWildBits = new BitVector( myStateVector.Size() );
    myWildBits.Set();
  }

  public QueryVector( StateVector stateVector, StateVectorRange explicitConnector, BitVector sourceWildBits ) {
    myStateVector = stateVector;

    // Allocate a BitVector to hold the wild bits
    myWildBits = new BitVector( myStateVector.Size() );

    // Assume all bits are wild and then revert from this using the StateRange of the explicitConnector
    // We also might have to set some wild bits in the explicitConnector
    myWildBits.Set();
    int Offset = explicitConnector.GetRange().StartPos();
    for( int i = explicitConnector.GetRange().StartPos(), len = explicitConnector.GetRange().EndPos(); i <= len; i++ ) {
      if ( !sourceWildBits.IsSet( i - Offset ) ) {
        myWildBits.ClearBit( i );
      } // if
    } // for

  } // QueryVector( StateVector, StateVectorRange, BitVector )

  public QueryVector( StateVector stateVector, Vector rangesOfInterest ) {
    myStateVector = stateVector;

    // Allocate a BitVector to hold the wild bits
    myWildBits = new BitVector( myStateVector.Size() );

    // Assume all bits are wild and then revert from this using the StateRange of the ranges contained
    // in the Vector rangesOfInterest.  All bits are wild except for those bits that
    // are set in myStateVector corrseponding to the rangesOfInterest.  This results in a
    // query vector that will return those items that that match the query vector.
    // For example, if mos is one of the rangesOfInterest, then the query will
    // match those items whose mos's match the current mos from the statevector.
    myWildBits.Set();
    int offset = 0;
    String range = null;
    Enumeration enum = rangesOfInterest.elements();
    while (enum.hasMoreElements()) {
       range = (String) enum.nextElement();
       offset = myStateVector.RangeFor(range).StartPos();
       for( int i = myStateVector.RangeFor(range).StartPos(), len = myStateVector.RangeFor(range).EndPos(); i <= len; i++ ) {
         if ( myStateVector.IsSet( i ) ) {
           myWildBits.ClearBit(i);
         } // if
       } // for
    } // while
  } // QueryVector( StateVector, Vector )

  /**
   * Replaces the named range in the <code>Vector</code> with the specified range, correctly
   * clearing the wild bit status of that range
   * @param range The range to override in this <code>QueryVector</code>
   * @param source The <code>QueryVector</code> whose values are used to pass through
   */
  public void CopyQueryRange( StateRange range, QueryVector source ) {
    for( int i = range.StartPos(), len = range.EndPos(); i <= len; i++ ) {
      if ( source.IsSet( i ) ) {
        SetBit( i );
         myWildBits.ClearBit( i );
      } else {
        ClearBit( i );
      }
      // myWildBits.ClearBit( i );
    }
  }

  /**
   * Sets all the bits in the supplied range to be wild
   * @param range The range to set the wild bits in
   */
  public void ForceWild( StateRange range ) {
    for( int i = range.StartPos(), len = range.EndPos(); i <= len; i++ ) {
      myWildBits.SetBit( i );
    }
  }

  /**
   * Use the supplied <code>StateVectorOverride</code> object to set/clear a named range in
   * this <code>QueryVector</code>
   * @param overrideOperator The <code>StateVectorOverride</code> object that knows what range to set or clear when its <code>Override</code> method is called
   */
  public void Override( StateVectorOverride overrideOperator ) {
    overrideOperator.Override( myStateVector, this );
  }

  /**
   * @see BitOperations#IsSet( int bitNum )
   */
  public boolean IsSet( int bitNum ) {
    return myStateVector.IsSet( bitNum );
  } // boolean IsSet( int )

  /**
   * @see BitOperations#SetBit( int bitNum )
   */
  public void SetBit( int bitNum ) {
    myStateVector.SetBit( bitNum );
  }

  /**
   * @see BitOperations#ClearBit( int bitNum )
   */
  public void ClearBit( int bitNum ) {
    myStateVector.ClearBit( bitNum );
  }

  /**
   * @see BitOperations#Clear()
   */
  public void Clear() {
    myStateVector.Clear();
  }

  /**
   * @see BitOperations#Set()
   */
  public void Set() {
    myStateVector.Set();
  }

  /**
   * @see BitOperations#GetBit( int bitNum )
   */
  public int GetBit( int bitNum ) {
    return myStateVector.GetBit( bitNum );
  }

  /**
   * @see QueryBitOperations#IsWild( int bitNum )
   */
  public boolean IsWild( int bitNum ) {
    return myWildBits.IsSet( bitNum );
  }

  /**
   * @see QueryBitOperations#SetWild( int bitNum )
   */
  public void SetWild( int bitNum ) {
    myWildBits.SetBit( bitNum );
  }

  /**
   * @see QueryBitOperations#ClearWild( int bitNum )
   */
  public void ClearWild( int bitNum ) {
    myWildBits.ClearBit( bitNum );
  }

  /**
   * @see QueryBitOperations#ClearAll()
   */
  public void ClearAll() {
    myWildBits.Clear();
  }

  /**
   * @see QueryBitOperations#Set()
   */
  public void SetAll() {
    myWildBits.Set();
  }

  public boolean Matches(BitVector test) {
    boolean result = true;
    for (int i = 0; i < test.GetSize(); i++) {
      if ((!myWildBits.IsSet(i)) && (test.GetBit(i) != myStateVector.GetBit(i))) {
         result = false;
         break;
      }
    }
    return result;
  }

  public boolean Matches( int startBit, BitVector test1 ) {
    //System.out.println( "My Wilds    " + myWildBits.toString() );
    //System.out.println( "Explicit is " + myStateVector.toString() );
    //System.out.println( "Bit Vector  " + test1.toString() );
    boolean Result = true;
    for( int i = startBit; i < test1.GetSize(); i++ ) {
      //System.out.println( i );
      if ( ( !myWildBits.IsSet(i) ) && ( test1.GetBit(i) != myStateVector.GetBit(i) ) ) {
        Result = false;
        break;
      }
    }
    return Result;
  }

  /**
   * @see QueryBitOperations#GetWildBit( int bitNum )
   */
  public int GetWildBit( int bitNum ) {
    return myWildBits.GetBit( bitNum );
  }

  // Returns the position of the next wild bit to the left of the supplied bit num
  // or -1 if ran off the end
  public int FirstNonWildFrom( int bitNum ) {
    int Result = -1;

    for( int i = bitNum - 1; i >= 0; i-- ) {
      if ( !myWildBits.IsSet( i ) ) {
        Result = i;
        break;
      }
    }

    return Result;
  }

  public void Randomize() {
    for( int i = 0; i < myWildBits.mySize; i++ ) {
      if ( Math.random() < 0.975 ) {
        myWildBits.SetBit( i );
      } else {
        if ( Math.random() > 0.5 ) {
          myStateVector.SetBit( i );
        }
      }
    }
  }

}
