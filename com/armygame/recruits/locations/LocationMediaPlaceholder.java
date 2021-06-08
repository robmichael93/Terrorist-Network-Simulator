/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.locations;


import com.armygame.recruits.mediaelements.RegistrationPoint;
import com.armygame.recruits.mediaelements.MediaDimensions;


/**
 * Contains the description of a media placeholder for a media element within a <code>LocationTemplate</code>
 * The information used to define the placeholder describes the following:
 * <b>Graphics Layout Properties:</b> <code>RegistrationPoint</code>, <code>MediaDimensions</code>, Layer
 *
 * <b>Note:</b>  The fields in this class are <code>protected</code> in anticipation that we may wish to
 * derive <code>LocationMediaPlaceholder</code> sub-classes with custom behaviors.
 */
abstract public class LocationMediaPlaceholder {

  /**
   * The drawing layer this placeholder occupies.  Layers are specified from back to front with layer 1 being the
   * back-most layer
   */
  protected int myLayer;

  /**
   * The <code>MediaDimensions</code> of this placeholder.  The  media dimensions give the width and height of the
   * bounding box of the placeholder in pixels.
   * <b>Important Note:</b> The bounding box of the placeholder (and its registration point) are NOT indicative of
   * the actual location that a media element put into that placeholder will be drawn at.  The placeholder defines
   * a suggested piece of screen real estate for a media element.  For flexibility in placing media elements that
   * satisfy a given role within a <code>LocationTemplate</code> we allow the individual media elements to specify
   * their precise location as an offset from the <code>RegistrationPoint</code> of the <code>LocationMediaPlaceholder</code>
   */
  protected MediaDimensions myPlaceholderBoundingBox;

  /**
   * The <code>RegistrationPoint</code> of this placeholder.  The registration point is the bottom-middle point
   * of the bounding box of the media element as an (x, y) coordinate with the origin (0, 0) being at the top-left
   * of the image with x increasing to the right and y increasing down
   */
  protected RegistrationPoint myRegistrationPoint;


  /**
   * The no-arg constructor required by <code>fromXML()</code>
   */
  public LocationMediaPlaceholder() {
    myLayer = 0;
    myPlaceholderBoundingBox = new MediaDimensions();
    myRegistrationPoint = new RegistrationPoint();
  }

  abstract public String RoleName();
  public void SetRoleName( String roleName ) {};

  abstract public String CastName();

  abstract public String CastMemberName();

  /**
   * Set the layer number of this placeholder
   * @param layer The layer number to change this placeholder to
   */
  public void SetLayer( int layer ) {
    myLayer = layer;
  }

  /**
   * Set the bounding box for this placeholder
   * @param boundingBox The <code>MediaDimensions</code> specifing the (width, height) of the placeholder's bounding box
   */
  public void SetBoundingBox( MediaDimensions boundingBox ) {
    myPlaceholderBoundingBox = boundingBox;
  }

  /**
   * Set the <code>RegistrationPoint</code> for this placeholder
   * @param registrationPoint The new (x, y) registration point for this placeholder
   */
  public void SetRegistrationPoint( RegistrationPoint registrationPoint ) {
    myRegistrationPoint = registrationPoint;
  }


  /**
   * Return the layer of this placeholder
   * @return The layer number of this placeholder
   */
  public int Layer() {
    return myLayer;
  }

  /**
   * Return the dimensions of the placeholder's bounding box
   * @return The (width, height) of the placeholder's bounding box
   */
  public MediaDimensions BoundingBox() {
    return myPlaceholderBoundingBox;
  }

  /**
   * Return the registration point of the placeholder
   * @return The (x,y) of the registration point for the placeholder
   */
  public RegistrationPoint RegistrationPoint() {
    return myRegistrationPoint;
  }

}
