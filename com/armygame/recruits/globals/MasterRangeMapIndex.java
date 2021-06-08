/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.globals;

import com.armygame.recruits.utils.RangeMapIndex;


/**
 * A <b>Singleton</b> that holds a master index of <code>RangeMap</code>s indexed by name
 */
public class MasterRangeMapIndex {

  /**
   * Holds the actual index
   */
  private static RangeMapIndex theirRangeMapIndex = null;

  /**
   * The constructor is private to enforce <b>Singleton</b> - clients use <code>Instance()</code> to get access
   * to the global index
   */
  private MasterRangeMapIndex() {
  }

  /**
   * Returns the master <code>RangeMapIndex</code> <b>Singleton</b>
   */
  public static RangeMapIndex Instance() {
    if ( theirRangeMapIndex == null ) {
      theirRangeMapIndex = new RangeMapIndex();
    }
    return theirRangeMapIndex;
  }

}