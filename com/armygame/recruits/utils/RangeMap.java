/**
 * Title: RangeMap
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;

import java.util.HashMap;
import java.util.Iterator;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * A <code>RangeMap</code> defines a set of named range allocations of a bit vector representation
 * of binary attributes.  Each RangeMap gives a set of allowable named ranges for interpretation of the
 * bit patterns of a <code>BitVector</code>-based attribute set.  A <code>RangeMap</code> object is intended to be
 * passed in to the construction of a <code>StateVector</code> object as the definitive guide to acquiring
 * and operating on named and defined chunks of the underlying <code>BitVector</code> representing its attributes.
 * @see BitVector
 * @see StateVector
 * @see RecruitsXMLPersistence
 */
public class RangeMap extends DefaultXMLSerializer implements XMLSerializable {

  /**
   * The size of the underlying <code>BitVector</code> that represents the super-set of all named ranges.
   * Contract: <code>myBaseRangeSize</code> must be in the range [1..maxint].
   */
  private int myBaseRangeSize;

  /**
   * The <code>HashMap</code> used to maintain the mapping of names to bit range specifications
   */
  private HashMap myRangeMappings;

  /**
   * Constructs an empty <code>RangeMap</code> designed to manage <code>BitVector</code> representations
   */
  public RangeMap( int size ) {
    super();
    myBaseRangeSize = size;
    myRangeMappings = new HashMap( 600 );
  }

  /**
   * No arg constructor for <code>fromXML()</code>
   */
  public RangeMap() {
    super();
    myRangeMappings = new HashMap( 600 );
    myBaseRangeSize = 0;
  }

  /**
   * Reads state into this <code>RangeMap</code> from the specified XML file
   * @param file The full path of the XML file to read
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }

  /**
   * Set the size of the master range
   * @param size Size of the <code>BitVector</code> used to contain the entire set of attributes available
   */
  public void SetBaseRange( String rangeName, int startPos, int endPos ) {
    myBaseRangeSize = endPos - startPos + 1;
    DefineRange( rangeName, startPos, endPos );
  }

  /**
   * Returns the size of <code>StateVector</code> this map manages
   * @return The size of the <code>StateVector</code> this is a map for
   */
  public int BaseRangeSize() {
    return myBaseRangeSize;
  } // BaseRangeSize()

  public void Debug() {
    // Print out everything in the hash map
    Iterator DebugItr = myRangeMappings.values().iterator();
    while( DebugItr.hasNext() ) {
      StateRange NextValue = (StateRange) DebugItr.next();
      System.out.println( "Value: " + NextValue.Name() + "-> [" + NextValue.StartPos() + ", " + NextValue.EndPos() + "]" );
    }
  }

  public Iterator GetMapIterator() {
    return myRangeMappings.values().iterator();
  }

  public void UndefineRange( String rangeName ) {
    myRangeMappings.remove( rangeName );
  }

  /**
   * Creates a new, named <code>StateRange</code> entry in the range mapping table
   * @param rangeName A string used to uniquely identify this range by name
   * @param startPos The starting bit position for the range
   * @param endPos The end bit position for the range
   * @return A <code>StateRange</code> object that encapsulates this range and associates it with a <code>StateVector</code>
   */
   public StateRange DefineRange( String rangeName, int startPos, int endPos ) {
    // Add the range to the HashMap
    //System.out.println( "Defining range " + rangeName + ":[" + startPos + ", " + endPos + "]" );
    StateRange NewRange = new StateRange( rangeName, startPos, endPos );
    myRangeMappings.put( rangeName, NewRange );
    // StateRange Result = (StateRange) myRangeMappings.get( rangeName );
    // System.out.println( "Retrieved range " + rangeName + ":[" + Result.StartPos() + ", " + Result.EndPos() + "]" );
    return NewRange;
   } // DefineRange( String, int, int )

  /**
   * Creates a new, named 1-bit <code>StateRange</code> entry in the range mapping table
   * @param rangeName A string used to uniquely identify this range by name
   * @param bitPos The bit position for the range - will be defined as a range of [bitPos,bitPos]
   * @return A <code>StateRange</code> object that encapsulates this range and associates it with a <code>StateVector</code>
   */
   public StateRange DefineRange( String rangeName, int bitPos ) {
    return DefineRange( rangeName, bitPos, bitPos );
   } // DefineRange( String, int )

   /**
    * Return the <code>StateRange</code> for the given name
    * @param rangeName The name of the range to return
    * @return The <code>StateRange</code> that matches the requested name
    */
   public StateRange RangeFor( String rangeName ) {
     return (StateRange) myRangeMappings.get( rangeName );
   } // RangeFor( String )

   public void toXML( String filename ) {

   }

} // class RangeMap
