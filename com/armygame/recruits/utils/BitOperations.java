/**
 * Title: BitOperations
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.utils;

/**
 * Declares the interface used to perform bit operations on <code>BitVector</code>s.
 * This interface implies a zero-indexed bit vector representation of bits
 * @see BitVector
 * @see StateVectorRange
 * @see TargetObjectKey
 * @see QueryVector
 */
public interface BitOperations {
  /**
   * Returns <code>true</code> if the requested bit in the vector is set, otherwise <code>false</code>
   * @return <code>true</code> if the requested bit in the vector is set, otherwise <codefalse</code>
   */
  public boolean IsSet( int bitNum );

  /**
   * Sets the <code>bitNum</code>th bit of the vector to <code>true</code>
   * @param bitNum The index of the bit in the vector to set
   */
  public void SetBit( int bitNum );


  /**
   * Clears the <code>bitNum</code>th bit of the vector to <code>false</code>
   * @param bitNum The index of the bit in the vector to clear
   */
  public void ClearBit( int bitNum );

  /**
   * Clears the entire bit vector to <code>false</code>
   */
  public void Clear();

  /**
   * Sets every bit in the vector to <code>true</true>
   */
  public void Set();

  /**
   * Returns the <code>true</code> or <code>false</code> value of the <code>bitNum</code>th bit
   * @param bitNum The index of the bit in the vector whose value is to be returned
   * @return The <code>true</code> or <code>false</code> value of the <code>bitNum</code>th bit
   */
  public int GetBit( int bitNum );

  //? public boolean equals( BitOperations arg );
}