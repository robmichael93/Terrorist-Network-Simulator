/**
 * Title: MasterConfiguration
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.globals;


/**
 * A RecruitsVersion that is a <b>Singleton</b> (See "Design Patterns", Gamma et. al.) that encodes
 * the master versions of the game and its sub systems.
 */
public class MasterConfiguration {

  /**
   * This holds the master copy of the Version
   */
  private static RecruitsConfiguration theirMasterConfiguration = null;

  /**
   * The constructor is private to force clients to use the <code>Instance()</code> access
   * function that regulates this object as a <b>Singleton</b>
   */
  private MasterConfiguration() {
  }

  /**
   * Makes sure the MasterConfiguration is created once and shared amongst subsequent requests
   * @return The MasterConfiguration <b>Singleton</b>
   */
  public static RecruitsConfiguration Instance() {

    if ( theirMasterConfiguration == null ) {
      theirMasterConfiguration = new RecruitsConfiguration();
    }

    return theirMasterConfiguration;
  }

}

