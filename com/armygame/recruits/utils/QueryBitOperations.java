/**
 * Title: QueryBitOperations
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;


public interface QueryBitOperations {
  public boolean IsWild( int bitNum );
  public void SetWild( int bitNum );
  public void ClearWild( int bitNum );
  public void ClearAll();
  public void SetAll();
  public int GetWildBit( int bitNum );
}