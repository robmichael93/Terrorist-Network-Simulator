/**
 * Title: TargetObjectKey
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;

import com.armygame.recruits.utils.TargetObjectBacklink;


/**
 * A <code>TargetObjectKey</code> defines an association between a <code>BitVector</code> attribute key and an
 * <code>Object</code> referenced by the <code>BitVector</code> key.  It provides <code>BitOperations</code>
 * on the <code>BitVector</code> representation of the attributes through association with a
 * <code>StateVector</code> and its associated <code>RangeMap</code>.
 * <b>IMPORTANT NOTE:</b>See special instructions on generation of <code>TargetObjectKey</code>s in the <code>TargetObjectKey</code> constructor.
 * @see TargetObjectKey#TargetObjectKey( StateVector, Object )
 * @see BitVector
 * @see BitOperations
 * @see StateVector
 * @see RangeMap
 */
public class TargetObjectKey implements BitOperations {

  /**
   * A reference back to the <code>StateVector</code> that made this <code>TargetObjectKey</code>
   */
  StateVector myStateVector;

  /**
   * The target <code>Object</code> that we are associating with the <code>StateVector</code> as its key
   */
  Object myTargetObj;

  public String toString() {
    return myStateVector.toString();
  }

  /**
   * The no-arg constructor for XML parsing apps
   */
  public TargetObjectKey() {
    myStateVector = null;
    myTargetObj = null;
  }

  /**
   * Set the <code>StateVector</code> that represents the key to the object in question
   * @param key The <code>StateVector</code> that's our key
   */
  public void SetKey( StateVector key ) {
    myStateVector = key;
  }

  /**
   * Make this <code>TargetObjectKey</code> refer to a target and, if requested, (<code>backlinkFlag</code> = <code>true</code>)
   * we also make a backlink
   * @param targetObject The object this <code>TargetObjectKey</code> refers to
   * @param backlinkFlag When <code>true</code> we make the target object refer back to this <code>TargetObjectKey</code>
   */
  public void SetTargetObject( Object targetObject, boolean backlinkFlag ) {
    myTargetObj = targetObject;
    if ( backlinkFlag == true ) {
      ( (TargetObjectBacklink) myTargetObj ).Backlink( this );
    }
  }

  /**
   * Make this <code>TargetObjectKey</code> refer to a target (No backlinking)
   * @param targetObject The object this <code>TargetObjectKey</code> refers to
   */
  public void SetTargetObject( Object targetObject ) {
    SetTargetObject( targetObject, false );
  }

  /**
   * Constructs a <code>TargetObjectKey</code> by associating a <code>StateVector</code>
   * with an <code>Object</code>.  It is the responsibility of the user to know and correctly de-reference
   * the <code>Object</code>s being handled.
   * <b>IMPORTANT NOTE:</b>This constructor is intended to be called within a 'factory' method of the
   * <code>StateVector</code>.  This constructor is NOT INTENDED for instantiation outside of the
   * factory technique in <code>StateVector</code>.  The reason for this is that the
   * <code>StateVector</code> is the context in which the <code>RangeMap</code> and
   * <code>StateRange</code> objects necessary are most easily available.
   * @param key The <code>StateVector</code> that holds the bits and manages the <code>RangeMap</code> for this conector
   * @param targetObj The <code>Object</code> this <code>TargetObjectKey</code> is the attribute key for
   */
  public TargetObjectKey( StateVector key, Object targetObj ) {
    myStateVector = key;
    myTargetObj = targetObj;
  } // TargetObjectKey( StateVector, Object )

  /**
   * Constructs a <code>TargetObjectKey</code> by associating a <code>StateVector</code>
   * with an <code>Object</code> and assigning the supplied <code>BitVector</code> value as the
   * <code>TargetObjectKey</code>'s initial value.  It is the responsibility of the user to know and
   * correctly de-reference the <code>Object</code>s being handled.
   * @param stateVector The <code>StateVector</code> that holds the bits and manages the <code>RangeMap</code> for this conector
   * @param targetObj The <code>Object</code> this <code>TargetObjectKey</code> is the attribute key for
   * @param initialVector A <code>BitVector</code> used to initialize the bits of the <code>StateVector</code>
   */
  //public TargetObjectKey( StateVector stateVector, Object targetObj, BitVector initialVector ) {
  //  myStateVector = stateVector;
  //  myTargetObj = targetObj;
  //} // TargetObjectKey( StateVector, Object )

  /**
   * Return the Target Object that goes with this connector
   * @return The <code>Object</code> associated with this <code>TargetObjectKey</code> (User must de-reference appropriately)
   */
  public Object GetTargetObj() {
    return myTargetObj;
  } // Object GetTargetObj()

  public StateVector GetKey() {
    return myStateVector;
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
  } // SetBit( int )

  /**
   * @see BitOperations#ClearBit( int bitNum )
   */
  public void ClearBit( int bitNum ) {
    myStateVector.ClearBit( bitNum );
  } // ClearBit( int )

  /**
   * @see BitOperations#Clear()
   */
  public void Clear() {
    myStateVector.Clear();
  } // Clear()

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
  } // int GetBit( int )

} // class TargetObjectKey
