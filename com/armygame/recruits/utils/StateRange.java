/**
 * Title: StateRange
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;

/**
 * A <code>StateRange</code> object defines a bit span within an attribute <code>BitVector</code>.  It is
 * used to form named aliases to bit ranges within a fuller <code>StateVector</code>
 */
public class StateRange {

  /**
   * The name of this range
   */
  private String myName;

  private String myComment;

  /**
   * The starting bit index of the range
   */
  private int myStartBitOfRange;

  /**
   * The ending bit index of the range
   */
  private int myEndBitOfRange;

  /**
   * This one argument constructor builds a 1-bit range at the bit position specified
   * @param bitPos The bit position of the range
   */
  public StateRange( String name, int bitPos ) {
    myName = name;
    myComment = "";
    myStartBitOfRange = bitPos;
    myEndBitOfRange = myStartBitOfRange;
  } // StateRange( int bitPos )

  /**
   * This two argument constructor builds an N bit span of bits from the starting position to the ending position
   * @param startBitPos The starting position
   * @param endBitPos The ending position
   */
  public StateRange( String name, int startBitPos, int endBitPos ) {
    myName = name;
    myComment = "";
    myStartBitOfRange = startBitPos;
    myEndBitOfRange = endBitPos;
  } // StateRange( int startBitPos, int endBitPos )

  /**
   * Return the starting position of the range
   * @return The starting position of the range
   */
  public int StartPos() {
    return myStartBitOfRange;
  } // StartPos()

  /**
   * Return the end position of the range
   * @return The end position of the range
   */
  public int EndPos() {
    return myEndBitOfRange;
  } // EndPos()

  /**
   * Return the name
   * @return The Name of the range
   */
  public String Name() {
    return myName;
  }

  public int SpanLength() {
    return myEndBitOfRange - myStartBitOfRange;
  }

  public String Comment() {
    return myComment;
  }


} // class StateRange
