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

import com.armygame.recruits.utils.StateVector;
import com.armygame.recruits.utils.StateVectorOverride;
import com.armygame.recruits.mediaelements.LocationMediaElement;
import com.armygame.recruits.mediaelements.RegistrationPoint;
import com.armygame.recruits.mediaelements.MediaDimensions;


/**
 * Contains the description of a media placeholder for an image media element within a <code>LocationTemplate</code>
 * The information used to define the placeholder describes the following:
 * <b>Occurrence Properties:</b> Probability of Occurrence.
 * <b>Instantiation Properties:</b> Describe how to use the <code>QueryVector</code> used to locate
 * a <code>LocationTemplate</code> to instantiate the appropriate image components within the
 * <code>Location</code>.
 *
 * <b>Note:</b>  The fields in this class are <code>protected</code> in anticipation that we may wish to
 * derive <code>LocationMediaPlaceholder</code> sub-classes with custom behaviors.
 */
public class LocationImagePlaceholder extends LocationMediaPlaceholder {

  /**
   * The <code>LocationMediaElement</code> that gives the positioning offset, role name, and cast info
   * for this image
   */
  private LocationMediaElement myMediaElement;

  /**
   * The probability of occurrence of this placeholder within a <code>LocationTemplate</code>.  Probabilities are
   * specified in the range [0-100] with the following interpretation:
   * <b>0</b>: Does not appear in the <code>Location</code> object built by instantiating this <code>LocationTemplate</code> (used primarily for debugging to 'comment out' a placeholder)
   *
   * <b>[1-99]</b>: A random number in the inclusive range [1-99] is generated and compared to the specified probability of occurrence.
   * If the random number is less than or equal to the specified probability then the placeholder IS INCLUDED in the instantiated
   * <code>Location</code> object, otherwise this placeholder is NOT CONSIDERED.  For example, lets say the specified
   * probability of occurrence was 47.  If during instantiation the random number generated was 23, then we would include
   * this placeholder in the instantiated <code>Location</code>.  If the random number were 83 in the above example we would
   * not include this placeholder.  Since the range is inclusive, if the random number were 1 or 47 we would include as well.
   */
  protected int myProbabilityOfOccurrence;

  /**
   * The set of roles that this placeholder is associated with.  The roles are the used as indices into the
   * <code>AttributeGrove</code> of <code>AttributeTrie</code>s so that placeholder saerches are localized to the
   * attribute sets associated with the given role names, for efficiency.
   * <b>IMPORTANT OPTIMIZATION NOTE:</b>  The current list and supporting methods for updating it utilize
   * <code>String</code>s to hold the role names, thus requiring a look-up step during instantiation from role
   * name to the <code>AttributeGrove</code> branch that encodes for that name.  For optimization the roles
   * should probably use the name to cache a reference to the branch in the grove.
   */
  protected ArrayList myRoles;

  /**
   * These lists maintain the range names of those parts of the <code>QueryVector</code> used to match the
   * <code>LocationTemplate</code> that holds this <code>LocationMediaPlaceholder</code>.  The range names in this
   * list will be used during instantiation to build the <code>QueryVector</code> that will be passed to the
   * <code>AttributeGrove</code> for querying the placeholders.
   * <b>IMPORTANT OPTIMIZATION NOTE:</b>  The current list of range names is maintained as <code>String</code>s which
   * require an additional look-up step during instantiation when building the <code>QueryVector</code> used to search
   * the <code>AttributeGrove</code>.  For optimization the name should be used to look-up and cache an actual
   * set of <code>QueryVector</code> pieces to avoid having to do the expensive name-to-<code>QueryVector</code>
   * look-up each time.
   *
   * These ranges are copied from the <code>QueryVector</code> used to find a <code>LocationTemplate</code>
   * verbatim into the Instantiation <code>QueryVector</code>
   */
  protected ArrayList myPassThroughKeyRanges;

  /**
    * These ranges are forced to be wildcards, overriding what was in the <code>QueryVector</code>
    * used to find the current <code>LocationTemplate</code>
    */
  protected ArrayList myWildcardKeyRanges;

  /**
   * These ranges are forced to be set/cleared, overriding what was in the <code>QueryVector</code>
   * used to find the current <code>LocationTemplate</code>
   */
  protected ArrayList myOverrideKeyRanges;

  /**
   * The no-arg constructor required by <code>fromXML()</code>
   */
  public LocationImagePlaceholder() {
    super();
    myProbabilityOfOccurrence = 0; // This makes uninitialized placeholders not appear in instantiated <code>Location</code>s!!!
    myRoles = new ArrayList();
    myPassThroughKeyRanges = new ArrayList();
    myWildcardKeyRanges = new ArrayList();
    myOverrideKeyRanges = new ArrayList();
    myMediaElement = null;
  }


  public String RoleName() {
    return (String) myRoles.get( 0 );
  }

  public String CastName() {
    return myMediaElement.CastName();
  }

  public String CastMemberName() {
    return myMediaElement.CastMemberName();
  }

  /**
   * Return all the roles this image placeholder should use for searching for images during instantiation in the <code>AttributeGrove</code> as a <code>String[]</code>
   * @return The roles this placeholder is good for
   */
  public String[] Roles() {
    return (String[]) myRoles.toArray( new String[0] );
  }

  /**
   * Set the probability of including this placeholder in <code>Location</code> objects instantiated from the
   * <code>LocationTemplate</code> containing this <code>LocationMediaPlaceholder</code>
   * @param probabilityOfOccurrence The probability [0-100] that this placeholder will be instantiated
   */
  public void SetProbabilityOfOccurrence( int probabilityOfOccurrence ) {
    myProbabilityOfOccurrence = probabilityOfOccurrence;
  }


  /**
   * Return the probability of occurrence of instantiating this placeholder
   * @return The probability of occurrence of instantiating this placeholder
   */
  public int ProbabilityOfOccurrence() {
    return myProbabilityOfOccurrence;
  }

  /**
   * Add a role to the set of role names this placeholder will use to search the <code>AttributeGrove</code> for
   * matching media
   * @param roleName The name of the role to add
   */
  public void AddRole( String roleName ) {
    myRoles.add( roleName );
  }

  /**
   * Add a range name to the set of Pass Through Ranges this placeholder will use when interpreting the
   * <code>QueryVector</code> that matched the <code>LocationTemplate</code> that owns this
   * <code>LocationMediaPlaceholder</code>.
   * @param rangeName The name of the range to add
   */
  public void AddPassThroughRange( String rangeName ) {
    myPassThroughKeyRanges.add( rangeName );
  }

  /**
   * Return the pass through key ranges as an array of names
   * @return An array of the names of the pass through key ranges
   */
  public String[] PassThroughRanges() {
    return (String[]) myPassThroughKeyRanges.toArray( new String[0] );
  }

  /**
   * Add a range name to the set of Wilcard Ranges this placeholder will use when interpreting the
   * <code>QueryVector</code> that matched the <code>LocationTemplate</code> that owns this
   * <code>LocationMediaPlaceholder</code>.
   * @param rangeName The name of the range to add
   */
  public void AddWildcardRange( String rangeName ) {
    myWildcardKeyRanges.add( rangeName );
  }

  /**
   * Return the wildcard key ranges as an array of names
   * @return An array of the names of the wildcard ranges
   */
  public String[] WildcardRanges() {
    return (String[]) myWildcardKeyRanges.toArray( new String[0] );
  }

  /**
   * Add a range to the set of Override Ranges this placeholder will use when interpreting the
   * <code>QueryVector</code> that matched the <code>LocationTemplate</code> that owns this
   * <code>LocationMediaPlaceholder</code>.
   * @param overrideRange The <code>StateVector</code> whose range we are adding
   */
  public void AddOverrideRange( StateVectorOverride overrideRange ) {
    myOverrideKeyRanges.add( overrideRange );
  }

  /**
   * Return the set of <code>StateVector</code>s that we are explicitly overriding
   * @return The set of <code>StateVector</code>s that we are explicitly overriding
   */
  public StateVectorOverride[] OverrideRanges() {
    return (StateVectorOverride[]) myOverrideKeyRanges.toArray( new StateVectorOverride[0] );
  }

  /**
   * Associate a <code>LocationMediaElemnent</code> with this placeholder
   * @param mediaElement The <code>LocationMediaElement</code> to associated with this placeholder
   */
  public void SetMediaElement( LocationMediaElement mediaElement ) {
    myMediaElement = mediaElement;
  }


  /**
   * Retrieve the <code>LocationMediaElement</code> associated with this placeholder
   * @return The <code>LocationMediaElement</code> associated with this placeholder
   */
  public LocationMediaElement MediaElement() {
    return myMediaElement;
  }

  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<LocationImage>" );
    Integer Layer = new Integer( myLayer );
    Result.append( "<Layer>" ); Result.append( Layer.toString() ); Result.append( "</Layer>" );
    Result.append( "<CastName>" ); Result.append( myMediaElement.CastName() ); Result.append( "</CastName>" );
    Result.append( "<CastMemberName>" ); Result.append( myMediaElement.CastMemberName() ); Result.append( "</CastMemberName>" );
    Result.append( "<RoleName>" ); Result.append( myMediaElement.Role() ); Result.append( "</RoleName>" );
    Integer X = new Integer( myRegistrationPoint.RegX() + myMediaElement.XOffset() );
    Integer Y = new Integer( myRegistrationPoint.RegY() + myMediaElement.YOffset() );
    Result.append( "<RegistrationX>" ); Result.append( X.toString() ); Result.append( "</RegistrationX>" );
    Result.append( "<RegistrationY>" ); Result.append( Y.toString() ); Result.append( "</RegistrationY>" );
    Result.append( "</LocationImage>" );
    return Result.toString();
  }

}

