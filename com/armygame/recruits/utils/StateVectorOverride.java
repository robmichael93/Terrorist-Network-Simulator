package com.armygame.recruits.utils;

/**
 * Defines an interface for classes that want to explicitly set or clear ranges
 * in a <code>QueryVector</code> based on a named range
 */
public interface StateVectorOverride {

  /**
   * Set the name of the range we want to operate on
   * @param rangeName The name of the range to operate on
   */
  public void SetRangeName( String rangeName );

  /**
   * Peform a set or clear operation on the range name we know about by setting or clearing
   * the correspond bits of the explicitly vector bits and clearing the corresponding wild bits
   * @param rangeSource The <code>StateVector</code> for which our range name is meaningful
   * @param queryVector The <code>QueryVector</code> we are to operate on
   */
  public void Override( StateVector rangeSource, QueryVector queryVector );
}
