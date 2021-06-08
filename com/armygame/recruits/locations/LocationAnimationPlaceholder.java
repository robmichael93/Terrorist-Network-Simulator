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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains the description of a media placeholder for a media element within a <code>LocationTemplate</code>
 * The information used to define the placeholder describes the following:
 * <b>Graphics Layout Properties:</b> <code>RegistrationPoint</code>, <code>MediaDimensions</code>, Layer
 * <b>Occurrence Properties:</b> Probability of Occurrence.
 *
 * <b>Note:</b>  The fields in this class are <code>protected</code> in anticipation that we may wish to
 * derive <code>LocationMediaPlaceholder</code> sub-classes with custom behaviors.
 */
public class LocationAnimationPlaceholder extends LocationMediaPlaceholder {


  /**
   * The set of roles that this placeholder is associated with.  The roles are the used as indices into the
   * <code>AttributeGrove</code> of <code>AttributeTrie</code>s so that placeholder saerches are localized to the
   * attribute sets associated with the given role names, for efficiency.
   * <b>IMPORTANT OPTIMIZATION NOTE:</b>  The current list and supporting methods for updating it utilize
   * <code>String</code>s to hold the role names, thus requiring a look-up step during instantiation from role
   * name to the <code>AttributeGrove</code> branch that encodes for that name.  For optimization the roles
   * should probably use the name to cache a reference to the branch in the grove.
   */
  protected String myRoleName;

   /**
    * The uniform the character in the role named for this placeholder will wear
    */
  protected String myUniform;

  /**
   * The hat the character in the role named for this placeholder will be wearing
   * Note: nh = no hat
   */
  protected String myHat;

  /**
   * true if this placeholder is actually used in a placement of characters on the stage for the
   * current scene and location
   */
  protected boolean myIsUsedFlag;

  protected PlaceholderShadow myShadow;

  private static int theirUniqueifier = 0;

  /**
   * The no-arg constructor required by <code>fromXML()</code>
   */
  public LocationAnimationPlaceholder() {
    super();
    myRoleName = null;
    myIsUsedFlag = false;
    myShadow = null;
  }

  public void SetIsUsed( boolean flag ) {
    myIsUsedFlag = flag;
  }


  public boolean IsUsed() {
    return myIsUsedFlag;
  }

  /**
   * Set the role name
   * @param roleName The role name
   */
  public void SetRoleName( String roleName ) {
    theirUniqueifier++;
    String UniqueRoleName = roleName; // Debug
    // String UniqueRoleName = new Integer( theirUniqueifier ).toString() + "," + roleName;
    System.out.println( "Setting role name to " + UniqueRoleName );
    myRoleName = UniqueRoleName;
  }

  public void SetShadow( PlaceholderShadow shadow ) {
    myShadow = shadow;
  }

  public PlaceholderShadow Shadow() {
    return myShadow;
  }

  public void SetUniform( String uniform ) {
    myUniform = uniform;
  }

  public void SetHat( String hat ) {
    myHat = hat;
  }

  /**
   * Retrieve the role name
   * @return The role name
   */
  public String RoleName() {
    System.out.println( "Getting role name " + myRoleName );
    return myRoleName;
  }

  public String Uniform() {
    return myUniform;
  }

  public String Hat() {
    return myHat;
  }

  public String CastName() {
    return myRoleName;
  }

  public String CastMemberName() {
    return "Animation Placeholder";
  }


  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<CharacterStaging>" );
    Integer Layer = new Integer( myLayer );
    Result.append( "<Layer>" ); Result.append( Layer.toString() ); Result.append( "</Layer>" );
    Result.append( "<RoleName>" ); Result.append( myRoleName ); Result.append( "</RoleName>" );
    Integer X = new Integer( myRegistrationPoint.RegX() );
    Integer Y = new Integer( myRegistrationPoint.RegY() );
    Result.append( "<RegistrationX>" ); Result.append( X.toString() ); Result.append( "</RegistrationX>" );
    Result.append( "<RegistrationY>" ); Result.append( Y.toString() ); Result.append( "</RegistrationY>" );
    Result.append( "</CharacterStaging>" );
    return Result.toString();
  }

}

