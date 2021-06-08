/**
 * Title: StateVectorRange
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;

/**
 * A <code>StateVectorRange</code> of size N bits maps this range of bits onto the values maintained in the
 * <code>BitVector</code> of a <code>StateVector</code>.  A <code>StateVectorRange</code> thus represents
 * a 'slice' of the <code>StateVector</code>.  The <codeStateVectorRange</code> can use <code>BitOperations</code>
 * to address the bits in the <code>StateVector</code> using an indexing scheme local to the
 * <code>StateVectorRange</code>.
 * <b>IMPORTANT NOTE:</b>  See special instructions on generation of <code>StateVectorRange</code>s in the <code>StateVectorRange</code> constructor.
 * @see StateVectorRange#StateVectorRange( StateVector, StateRange )
 * @see BitVector
 * @see StateVector
 * @see BitOperations
 */
public class StateVectorRange implements BitOperations {

  /**
   * A reference back to the <code>StateVector</code> that made this <code>StateVectorRange</code>
   */
  StateVector myStateVector;

  /**
   * The <code>StateRange</code> that defines which bits in the <code>StateVector</code>'s
   * <code>BitVector</code> this connector maps onto.
   */
  StateRange myStateRange;

  /**
   * Constructs a <code>StateVectorRange</code> by associating it with a <code>StateVector</code>
   * (and thusly with a <code>RangeMap</code>) and with the <code>StateRange</code> definition
   * used to map this <code>StateVectorRange</code>'s range onto the bits of the <code>StateVector</code>
   * <b>IMPORTANT NOTE:</b>This constructor is intended to be called within a 'factory' method of the
   * <code>StateVector</code>.  This constructor is NOT INTENDED for instantiation outside of the
   * factory technique in <code>StateVector</code>.  The reason for this is that the
   * <code>StateVector</code> is the context in which the <code>RangeMap</code> and
   * <code>StateRange</code> objects necessary are most easily available.
   * @see StateVector
   * @see StateRange
   * @see RangeMap
   */
  public StateVectorRange( StateVector stateVector, StateRange connectorRange ) {
    myStateVector = stateVector;
    myStateRange = connectorRange;
  } // StateVectorRange( StateVector, StateRange )

  /**
   * Returns the <code>StateRange</code> that defines which bits in the <code>StateVector</code>
   * this <code>StateVectorRange</code> maps onto
   * @return The the <code>StateRange</code> that defines which bits in the <code>StateVector</code>
   * this <code>StateVectorRange</code> maps onto
   */
  public StateRange GetRange() {
    return myStateRange;
  }

  /**
   * @see BitOperations#IsSet( int bitNum )
   */
  public boolean IsSet( int bitNum ) {
    // The bitNum of the StateVectorRange is an index into the range
    return myStateVector.IsSet( bitNum + myStateRange.StartPos() );
  } // IsSet( int )

  /**
   * @see BitOperations#SetBit( int bitNum )
   */
  public void SetBit( int bitNum ) {
    myStateVector.SetBit( bitNum + myStateRange.StartPos() );
  } // SetBit( int )

  /**
   * @see BitOperations#ClearBit( int bitNum )
   */
  public void ClearBit( int bitNum ) {
    myStateVector.ClearBit( bitNum + myStateRange.StartPos() );
  }

  /**
   * @see BitOperations#Clear()
   */
  public void Clear() {
    // We need to clear this range one bit at a time
    for( int i = myStateRange.StartPos(); i <= myStateRange.EndPos(); i++ ) {
      myStateVector.ClearBit( i );
    } // for
  } // Clear()

  /**
   * @see BitOperations#Set()
   */
  public void Set() {
    // We need to set this range one bit at a time
    for( int i = myStateRange.StartPos(); i <= myStateRange.EndPos(); i++ ) {
      myStateVector.SetBit( i );
    } // for
  } // Set()

  /**
   * @see BitOperations#GetBit( int bitNum )
   */
  public int GetBit( int bitNum ) {
    return myStateVector.GetBit( bitNum + myStateRange.StartPos() );
  }

  public StateVector StateVector() {
    return myStateVector;
  }

  public int getBitNumberOfSetBit() {
    int start = myStateRange.StartPos();
    int stop = myStateRange.EndPos();
    for (int i = start; i <= stop; i++) {
      if ( myStateVector.IsSet(i) ) {
         return i;
      }
    }
    return -1; // no bit was set in the range
  }


} // class StateVectorRange