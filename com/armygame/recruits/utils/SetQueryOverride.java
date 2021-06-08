package com.armygame.recruits.utils;

/**
 * Class to override a set of bits in a <code>QueryVector</code>
 */
public class SetQueryOverride implements StateVectorOverride {

  /**
   * The name of the range we are to set as an override
   */
  private String myRangeName;

  /**
   * No-arg constructor
   */
  public SetQueryOverride() {
    myRangeName = null;
  }

  /**
   * Set the range name
   * @param rangeName The name of the range we are to set when the <code>Override</code> method is called
   */
  public void SetRangeName( String rangeName ) {
    myRangeName = rangeName;
  }

  /**
   * Set the specified range in the supplied <code>QueryVector</code>, using the supplied
   * <code>StateVector</code> to get the right bits for th range
   * @param rangeSource The <code>StateVector</code> that tells use what the bits are in the named range
   * @param queryVector The <code>QueryVector</code> we are going to set our range's bits in, and we will clear the corresponding wild bits
   */
  public void Override( StateVector rangeSource, QueryVector queryVector ) {
    StateRange Range = rangeSource.RangeFor( myRangeName );
    for( int i = Range.StartPos(), len = Range.EndPos(); i <= len; i++ ) {
      queryVector.SetBit( i );
      queryVector.ClearWild( i );
    }
  }

}
