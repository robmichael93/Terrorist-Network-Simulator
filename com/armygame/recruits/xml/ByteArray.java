/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.xml;

/**
 * A class for creating and operating on arrays of bytes.
 * This class is designed to support reading in of arrays of bytes in support of <code>fromXML()</code> needs
 * for things like <code>StateVector</code>s
 */
public class ByteArray {

  /**
   * The actual array of bytes
   */
  private byte[] myBytes;

  /**
   * This keeps track of which byte has been written.  This is set up so that consecutive calls to
   * <code>SetNextByte</code> can know which byte to set
   */
  private int myCurrentByte;

  /**
   * The no-arg constructor for <code>fromXML()</code>
   */
  public ByteArray() {
    myBytes = null;
    myCurrentByte = 0;
  }

  /**
   * Sets the size of the byte array and allocates it
   * @param size The number of bytes in the array
   */
  public void SetSize( int size ) {
    myBytes = new byte[ size ];
  }

  /**
   * Resets the current byte count to start from the beginning of the array
   */
  public void Reset() {
    myCurrentByte = 0;
  }

  /**
   * Sets the byte at the current position to the passed in value and increments the current position
   * @param theByte The byte to set
   */
  public void SetNextByte( byte theByte ) {
    myBytes[ myCurrentByte ] = theByte;
    myCurrentByte++;
  }

  /**
   * Returns the byte array
   * @return The byte array
   */
  public byte[] GetBytes() {
    return myBytes;
  }

}