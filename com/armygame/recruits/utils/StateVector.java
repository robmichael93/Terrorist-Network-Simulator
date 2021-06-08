/**
 * Title: StateVector
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;


import com.armygame.recruits.globals.MasterRangeMapIndex;
import com.armygame.recruits.utils.RangeMapIndex;
import java.util.*;


/**
 * A <code>StateVector</code> manages a <code>BitVector</code> representing a set of binary
 * attributes.  The <code>StateVector</code> provides the following services:
 * 1)  Maintains a definitive store for a loosely 'typed' set of binary attributes as a <code>BitVector</code>
 * 2)  Maintains an aliasing mechanism for identifying and manipulating ranges of bits within the vector as a <code>RangeMap</code>
 * 3)  Provides a set of 'factory' methods for producing <code>StateVectorRange</code>s, <code>TargetObjectKey</code>s, and <code>QueryVector</code>s
 * 4)  Provides <code>BitOperations</code> on the <code>BitVector</code> it maintains represnting the state of the connector
 * @see BitVector
 * @see RangeMap
 * @see StateVectorRange
 * @see TargetObjectKey
 * @see QueryVector
 */
public class StateVector implements BitOperations {

  /**
   * The bit set representing the attributes in this <code>StateVector</code>
   */
  private BitVector myBitVector;

  /**
   * The <code>RangeMap</code> used to define named aliases for the bit ranges of the connector
   */
  private RangeMap myConnectorMappings;

  /**
   * No-arg constructor required by <code>fromXML()>/code>
   */
  public StateVector() {
    myConnectorMappings = null;
    myBitVector = null;
  }

  /**
   * Associate a <code>RangeMap</code> with this <code>StateVector</code> by looking it up by name
   * in the global <code>RangeMapIndex</code>
   * @param rangeMapName The name of the <code>RangeMap</code> to associate with this <code>StateVector</code>
   */
  public void AssociateRangeMap( String rangeMapName ) {
    myConnectorMappings = MasterRangeMapIndex.Instance().GetRangeMap( rangeMapName );
    myBitVector = new BitVector( myConnectorMappings.BaseRangeSize() );
  }

  /**
   * Constructs a <code>StateVector</code> by associating a <code>RangeMap</code> that
   * defines a named mapping for interpreting the bits of the connector vector.  Note that the <code>RangeMap</code>
   * may be populated with range definitions before being passed in.  Or, new or additional range definitions can be
   * added to the map via methods in this class.
   * @param connectorMap A <code>RangeMap</code> the defines how to interpret the bits of the connector vector.
   * @see #DefineRange( String, int, int )
   * @see #DefineRange( String, int )
   */
  public StateVector( RangeMap connectorMap ) {
    // Our bit vector is sized according to what our map is set to manage
    myBitVector = new BitVector( connectorMap.BaseRangeSize() );

    myConnectorMappings = connectorMap;
  } // StateVector( RangeMap )

  /**
   * Creates a new, named <code>StateRange</code> entry in the range mapping table
   * @param rangeName A string used to uniquely identify this range by name
   * @param startPos The starting bit position for the range
   * @param endPos The end bit position for the range
   * @return A <code>StateRange</code> object that encapsulates this range and associates it with a <code>StateVector</code>
   */
  public StateRange DefineRange( String rangeName, int startBitPos, int endBitPos ) {
    return myConnectorMappings.DefineRange( rangeName, startBitPos, endBitPos );
  } // StateRange DefineRange( String, int, int )

  /**
   * Creates a new, named 1-bit <code>StateRange</code> entry in the range mapping table
   * @param rangeName A string used to uniquely identify this range by name
   * @param bitPos The bit position for the range - will be defined as a range of [bitPos,bitPos]
   * @return A <code>StateRange</code> object that encapsulates this range and associates it with a <code>StateVector</code>
   */
  public StateRange DefineRange( String rangeName, int singleBitPos ) {
    return DefineRange( rangeName, singleBitPos, singleBitPos );
  } // StateRange DefineRange( String, int )

  //Auxiliary Routines
  public void SetRange( String rangeName ) throws Exception {
    for( int i = myConnectorMappings.RangeFor( rangeName ).StartPos(); i <= myConnectorMappings.RangeFor( rangeName ).EndPos(); i++ ) {
      myBitVector.SetBit( i );
    }
  }

  public void ClearRange( String rangeName ) {
    for( int i = myConnectorMappings.RangeFor( rangeName ).StartPos(); i <= myConnectorMappings.RangeFor( rangeName ).EndPos(); i++ ) {
      myBitVector.ClearBit( i );
    }
  }

  public void SetBitInRange( String rangeName, int bitNum ) {
    myBitVector.SetBit( bitNum + myConnectorMappings.RangeFor( rangeName ).StartPos() );
  }

  public void ClearBitInRange( String rangeName, int bitNum ) {
    myBitVector.ClearBit( bitNum + myConnectorMappings.RangeFor( rangeName ).StartPos() );
  }

  public void SetRangeFromString( String rangeName, String bitString ) {
    for( int i = 0; i < bitString.length(); i++ ) {
      if ( bitString.charAt( i ) == '1' ) {
        SetBitInRange( rangeName, i );
      } else {
        ClearBitInRange( rangeName, i );
      }
    }
  }

  /**
    * Return the <code>StateRange</code> for the given name
    * @param rangeName The name of the range to return
    * @return The <code>StateRange</code> that matches the requested name
    */
  public StateRange RangeFor( String rangeName ) {
    return myConnectorMappings.RangeFor( rangeName );
  } // StateRange RangeFor( String )

  /**
   * Return the size of the <code>BitVector</code> this connector's holding
   * @return The size of the <code>BitVector</code> this connector's holding
   */
  public int Size() {
    return myConnectorMappings.BaseRangeSize();
  } // int Size()

  public boolean equals( StateVector rhs ) {
    // Two StateVectors are equal if they came from the same RangeMap and their bit vectors are equal
    boolean Result = false;

    if ( this.myConnectorMappings.equals( rhs.myConnectorMappings ) &&
         this.myBitVector.equals( rhs.myBitVector ) ) {
          Result = true;
         }
    return Result;
  }

  /**
   * @see BitOperations#IsSet( int bitNum )
   */
  public boolean IsSet( int bitNum ) {
    return myBitVector.IsSet( bitNum );
  }

  /**
   * @see BitOperations#SetBit( int bitNum )
   */
  public void SetBit( int bitNum ) {
    myBitVector.SetBit( bitNum );
  }

  /**
   * @see BitOperations#ClearBit( int bitNum )
   */
  public void ClearBit( int bitNum ) {
    myBitVector.ClearBit( bitNum );
  }

  /**
   * @see BitOperations#Clear()
   */
  public void Clear() {
    myBitVector.Clear();
  }

  /**
   * @see BitOperations#Set()
   */
  public void Set() {
    myBitVector.Set();
  }

  public BitVector BitVector() {
   return myBitVector;
  }

  /**
   * @see BitOperations#GetBit( int bitNum )
   */
  public int GetBit( int bitNum ) {
    return myBitVector.GetBit( bitNum );
  }

  /**
   * Factory method for creating new <code>StateVectorRange</code>s.  With no arguments this call returns
   * a <code>StateVectorRange</code> that maps onto the entire bit range of the <codeStateVector</code>'s
   * <code>BitVector</code>
   * @return A new <code>StateVectorRange</code> composed of all the bits managed by this <code>StateVector</code>'s <code>BitVector</code>
   */
  public StateVectorRange MakeStateVectorRange() {
    StateVectorRange Result = new StateVectorRange( this, new StateRange( "Full", 0, myConnectorMappings.BaseRangeSize() - 1 ) );
    return Result;
  }

  /**
   * Factory method for creating new <code>StateVectorRange</code>s.  With no arguments this call returns
   * a <code>StateVectorRange</code> that maps onto the entire bit range of the <codeStateVector</code>'s
   * <code>BitVector</code>
   * @param range A single <code>StateRange</code> that defines this <code>StateVectorRange</code>
   * @return A new <code>StateVectorRange</code> composed of all the bits managed by this <code>StateVector</code>'s <code>BitVector</code>
   */
  public StateVectorRange MakeStateVectorRange( StateRange range ) {
    StateVectorRange Result = new StateVectorRange( this, range );
    return Result;
  }

  /**
   * Factory method for creating new <code>StateVectorRange</code>s.  With no arguments this call returns
   * a <code>StateVectorRange</code> that maps onto the entire bit range of the <codeStateVector</code>'s
   * <code>BitVector</code>
   * @param ranges An array list of the <code>StateRange</code>s that make up this <code>StateVectorRange</code>
   * @return A new <code>StateVectorRange</code> composed of all the bits managed by this <code>StateVector</code>'s <code>BitVector</code>
   */
  //public StateVectorRange MakeStateVectorRange( StateRange[] ranges ) {
  //  StateVectorRange Result = new StateVectorRange();
  //  return Result;
  //}

  /**
   * Factory method for creating new <code>TargetObjectKey</code>s.  Each <code>TargetObjectKey</code> uses the
   * <code>BitVector</code> and <code>RangeMap</code> inside its associated <code>StateVector</code>.
   * Each <code>TargetObjectKey</code> also allows for association of an <code>Object</code> for which this
   * <code>TargetObjectKey</code> acts as a key when indexing connectors for searching.
   * @param targetObj The <code>Object</code> to associate with this <code>TargetObjectKey</code> as its key
   * @return A new <code>TargetObjectKey</code> that associates a <code>StateVector</code> with a target <code>Object</code>
   */
  public TargetObjectKey MakeTargetObjectKey( Object targetObj ) {
    TargetObjectKey Result = new TargetObjectKey( this, targetObj );
    return Result;
  }

  //public TargetObjectKey MakeTargetObjectKey( Object targetObj, BitVector initialVector ) {
  //  TargetObjectKey Result = new TargetObjectKey( this, targetObj, initialVector );
  //  return Result;
  //}

  /**
   * Factory method for creating new <code>QueryVector</code>s.  Each <code>QueryVector</code> contains
   * two parallel representations of its underlying attribute bits.  The explicit bits of the query
   * are the bits set in the <code>BitVector</code> of the <code>QueryVector</code>'s associated
   * <code>StateVector</code>.  In parallel with these bits is another <code>BitVector</code>
   * which contains <code>true</code> for bits in the query connector that are to be considered
   * wildcards, and <code>false</code> for bits that should use the bit representation in the
   * explicit <code>BitVector</code> in the <code>StateVector</code>.
   * <b>Note:</b> It's OK for the explicit connector to be a 'slice' of the <code>StateVector</code>,
   * in which case all bits of the explcit connector are considered non-wild, and all bits outside the slice
   * defined by the explicit connector to default to wild.  This facilitates using <code>StateVectorRange</code>s
   * to represent queries on the specific slice of the <code>StateVector</code>.
   * @param explicitConnector The <
   */
  public QueryVector MakeQueryVector( StateVectorRange explicitConnector, BitVector sourceWildBits ) {
    QueryVector Result = new QueryVector( this, explicitConnector, sourceWildBits );
    return Result;
  }

  /**
   * Factory method for creating new <code>QueryVector</code>s.  Each <code>QueryVector</code> contains
   * two parallel representations of its underlying attribute bits.  The explicit bits of the query
   * are the bits set in the <code>BitVector</code> of the <code>QueryVector</code>'s associated
   * <code>StateVector</code>.  In parallel with these bits is another <code>BitVector</code>
   * which contains <code>true</code> for bits in the query connector that are to be considered
   * wildcards, and <code>false</code> for bits that should use the bit representation in the
   * explicit <code>BitVector</code> in the <code>StateVector</code>.
   * <b>Note:</b> It's OK for the explicit connector to be a 'slice' of the <code>StateVector</code>,
   * in which case all bits of the explcit connector are considered non-wild, and all bits outside the slice
   * defined by the explicit connector to default to wild.  This facilitates using <code>StateVectorRange</code>s
   * to represent queries on the specific slice of the <code>StateVector</code>.
   * @param explicitConnector The <
   */
  public QueryVector MakeQueryVector( Vector rangesOfInterest ) {
    QueryVector Result = new QueryVector( this, rangesOfInterest );
    return Result;
  }

  /**
   *
   */
  //public QueryVector MakeQueryVector( StateVectorRange[] queryConnectors, BitVector[] sourceWildBits ) {
  //  QueryVector Result = new QueryVector();
  //  return Result;
  //}

  public String toString() {
    String Result = "";
    Result = myBitVector.toString();
    return Result;
  }

} // class StateVector
