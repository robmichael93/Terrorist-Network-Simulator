/**
 * Title: MasterVersion
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.globals;

import com.armygame.recruits.utils.RecruitsVersion;


/**
 * A RecruitsVersion that is a <b>Singleton</b> (See "Design Patterns", Gamma et. al.) that encodes
 * the master versions of the game and its sub systems.
 */
public class MasterVersion {

  /**
   * This holds the master copy of the Version
   */
  private static RecruitsVersion theirMasterVersion = null;

  /**
   * The constructor is private to force clients to use the <code>Instance()</code> access
   * function that regulates this object as a <b>Singleton</b>
   */
  private MasterVersion() {
    // Empty
  }

  /**
   * Makes sure the MasterVersion is created once and shared amongst subsequent requests
   * @return The MasterVersion <b>Singleton</b>
   */
  public static RecruitsVersion Instance() {

    if ( theirMasterVersion == null ) {
      theirMasterVersion = new RecruitsVersion();
    }

    return theirMasterVersion;
  }

}
