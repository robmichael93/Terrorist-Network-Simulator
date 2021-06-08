/**
 * Title: LocationsConfiguration
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.globals;

import com.armygame.recruits.locations.LocationsConfiguration;


/**
 * Creates a <b>Singleton</b> <code>LocationsConfiguration</code> as the master known to the
 * program at large
 */

public class MasterLocationsConfiguration {

  /**
   * The <code>LocationsConfiguration</code> <b>Singleton</b>
   */
  private static LocationsConfiguration theirLocationsConfiguration = null;

  /**
   * Constructor private to enforce <b>Singleton</b>
   */
  private MasterLocationsConfiguration() {
    // Empty
  }

  /**
   * Return the master <code>LocationsConfiguration</code>
   * @return The master <code>LocationsConfiguration</code>
   */
  public static LocationsConfiguration Instance() {
    if ( theirLocationsConfiguration == null ) {
      theirLocationsConfiguration = new LocationsConfiguration();
    }
    return theirLocationsConfiguration;
  }

}
