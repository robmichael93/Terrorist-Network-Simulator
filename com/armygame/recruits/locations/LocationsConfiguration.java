/**
 * Title: LocationsConfiguration
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.locations;


import com.armygame.recruits.utils.AttributeGrove;
import com.armygame.recruits.utils.AttributeTrie;


/**
 * Encapsulates a set of state for dealing with locations in the Recruits.
 * Specifically:
 * 1)  Maintains an <code>AttributeTrie</code> of all the <code>LocationTemplates</code> 
 * 2)  Maintains an <code>AttributeGrove</code> of all the <code>LocationMediaElement</code>s used to instantiate <code>LocationTemplate</code>s into <code>Location</code>s
 *
 */
public class LocationsConfiguration {

  /**
   * The <code>AttributeTrie</code> that holds all the <code>LocationTemplate</code>s we know about
   */
  private AttributeTrie myLocationTemplates;

  /**
   * The <code>AttributeGrove</code> that holds all the <code>LocationMediaElement</code>s we know about for instantiating <code>LocationTemplate</code>s into <code>Locations</code>
   */
  private AttributeGrove myLocationMediaElements;

  /**
   * No arg constructor
   */
  public LocationsConfiguration() {
    myLocationTemplates = null;
    myLocationMediaElements = null;
  }

  /**
   * Set the <code>AttributeTree</code> we'll use to hold all the <code>LocationTemplate</code>s
   * @param locationTemplateTrie The <code>AttributeTrie</code> that contains the <code>LocationTemplate</code>s we know about
   */
  public void SetLocationTemplateTrie( AttributeTrie locationTemplateTrie ) {
    myLocationTemplates = locationTemplateTrie;
  }


  /**
   * Retrieve the <code>AttributeTrie</code> that contains the <code>LocationTemplate</code>s
   * @return The <code>AttributeTrie</code> that contains the <code>LocationTemplate</code>s
   */
  public AttributeTrie LocationTemplateTrie() {
    return myLocationTemplates;
  }


  /**
   * Set the <code>AttributeGrove</code> we'll use to hold all the <code>LocationMediaElement</code>s we know about
   * @param locationMediaElementGrove The <code>AttributeGrove</code> we'll use to hold all the <code>LocationMediaElement</code>s 
   */
  public void SetLocationMediaElementsGrove( AttributeGrove locationMediaElementGrove ) {
    myLocationMediaElements = locationMediaElementGrove;
  }


  /**
   * Retrieve the <code>AttributeGrove</code> that contains the <code>LocationMediaElement</code>s we know about
   * @return The <code>AttributeGrove</code> that contains the <code>LocationMediaElement</code>s
   */
  public AttributeGrove LocationMediaElementsGrove() {
    return myLocationMediaElements;
  }

}

