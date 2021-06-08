/**
 * Title: LocationTemplate
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.locations;

import java.util.ArrayList;

import com.armygame.recruits.globals.MasterRangeMapIndex;
import com.armygame.recruits.locations.LocationMediaPlaceholder;
import com.armygame.recruits.utils.RangeMap;
import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;

/**
 * Class for reading <code>LocationTemplate</code> descriptions from XML and instantiating <code>Location</code>
 * objects from those descriptions.  The class maintains a set of placeholders that describe
 * the geometric location of image and animation 'tiles' that make up the background for a scene.
 * Each placeholder describes the z-ordering layer on which the elemnent (image or animation)
 * is to be rendered.  It also defines the geometric coordinates of the registration point
 * of the image or animation.  The registration point is defined to be the middle of the lower
 * edge of the image or animation.  This was chosen because it makes registration of things like
 * vehicles and people easier to line up with things like roads and sidewalks.  For images the
 * each placeholder contains a probability of occurrence.  This probabilty may be set to 100%,
 * indicating the image item must appear.  Probabilities in the range 1-99 are used to define
 * optional items that may or may not appear - providing a sense of believable ambience to scenes.
 */
public class LocationTemplate extends DefaultXMLSerializer implements XMLSerializable {

  /**
   * This inner class manages the list of <code>LocationMediaPlaceholder</code>s
   */
  private class PlaceholderList {
    /**
     * The list of placeholders
     */
    private ArrayList myPlaceholders;

    /**
     * Create and initialize the placeholders list
     */
    public PlaceholderList() {
      myPlaceholders = new ArrayList();
    }

    /**
     * Clear the placeholder list
     */
    public void Clear() {
      myPlaceholders.clear();
    }

    /**
     * Add a placeholder to the list
     * @param newPlaceholder The placeholder to add
     */
    public void AddPlaceholder( LocationMediaPlaceholder newPlaceholder ) {
      myPlaceholders.add( newPlaceholder );
    }

    /**
     * Return the placeholders as an array of <code>LocationMediaPlaceholder</code>s
     * @return An array of all the <code>LocationMediaPlaceholder</code>s in the template
     * @see java.util.ArrayList#toArray()
     */
    public LocationMediaPlaceholder[] Placeholders() {
      return (LocationMediaPlaceholder[]) myPlaceholders.toArray( new LocationMediaPlaceholder[0] );
    }

  }

  /**
   * The name of this <code>LocationTemplate</code>
   */
  private String myName;

  /**
   * The set of image Placeholders contained by this template
   */
  private PlaceholderList myImagePlaceholders;

  /**
   * The set of animation staging placeholders contained in this template
   */
  private PlaceholderList myAnimationStagingPlaceholders;

  /**
   * The <code>RangeMap</code> this <code>LocationTemplate</code> should use for resolving range names
   */
  private RangeMap myRangeMap;


  /**
   * The no-arg constructor (for use by <code>fromXML()</code>
   */
  public LocationTemplate() {
    myName = null;
    myImagePlaceholders = new PlaceholderList();
    myAnimationStagingPlaceholders = new PlaceholderList();
    myRangeMap = null;
  }

  /**
   * Reads state into this <code>LocationTemplate</code> from the specified XML file
   * @param file The full path of the XML file to read
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }

  /**
   * Set the name of this template
   * @param name The name of the template
   */
  public void SetName( String name ) {
    myName = name;
  }

  /**
   * Return the name of this template
   * @return The name of this template
   */
  public String Name() {
    return myName;
  }

  /**
   * Set the <code>RangeMap</code> this template should use to interpret range names and and othewr indexing and
   * querying operations
   * @param rangeMap The <code>RangeMap</code> that defines the names and range spans we'll use for instantiation operations
   */
  public void SetRangeMap( String rangeMapName ) {
    myRangeMap = MasterRangeMapIndex.Instance().GetRangeMap( rangeMapName );

  }

  /**
   * Returns the <code>RangeMap</code> used for state range definitions in this template
   * @return The <code>RangeMap</code> used for state range definitions in this template
   */
  public RangeMap GetRangeMap() {
    return myRangeMap;
  }

  /**
   * Add a <code>LocatoionMediaPlaceholder</code> to the set of image placeholders that define potential media pieces
   * to instantiate into this template.
   * <b>Note</b>The set of placeholders should be unique in that no two placeholders should occupy the same
   * layer in the media layout - but this is NOT CURRENTLY CHECKED (We assume the XML that defines the
   * template will validate this condition)
   * @param newPlaceholder The placeholder to add
   */
  public void AddImagePlaceholder( LocationMediaPlaceholder newPlaceholder ) {
    myImagePlaceholders.AddPlaceholder( newPlaceholder );
  }

  /**
   * Add a <code>LocatoionMediaPlaceholder</code> to the set of animation staging placeholders that define potential media pieces
   * to instantiate into this template.
   * <b>Note</b>The set of placeholders should be unique in that no two placeholders should occupy the same
   * layer in the media layout - but this is NOT CURRENTLY CHECKED (We assume the XML that defines the
   * template will validate this condition)
   * @param newPlaceholder The placeholder to add
   */
  public void AddAnimationStagingPlaceholder( LocationMediaPlaceholder newPlaceholder ) {
    myAnimationStagingPlaceholders.AddPlaceholder( newPlaceholder );
  }

  /**
   * Return the set of <code>LocationImagePlaceholder</code>s for this template
   * @return The set of <code>LocationImagePlaceholder</code>s as an array of <code>LocationMediaPlaceholder</code>s.  User should cast the elements to <code>LocationImagePlaceholder</code> for use
   */
  public LocationMediaPlaceholder[] ImagePlaceholders() {
   return myImagePlaceholders.Placeholders();
  }

  /**
   * Return the set of <code>LocationAnimationPlaceholder</code>s for this template
   * @return The set of <code>LocationANimationPlaceholder</code>s as an array of <code>LocationMediaPlaceholder</code>s.  User should cast the elements to <code>LocationAnimationPlaceholder</code> for use
   */
  public LocationMediaPlaceholder[] AnimationStagingPlaceholders() {
    return myAnimationStagingPlaceholders.Placeholders();
  }

  public void SetStagings( String[] stagings ) {
    LocationMediaPlaceholder[] PlaceholdersToFix = AnimationStagingPlaceholders();
    for( int i = 0; i < stagings.length; i++ ) {
      PlaceholdersToFix[ i ].SetRoleName( stagings[ i ] );
    }
  }

}
