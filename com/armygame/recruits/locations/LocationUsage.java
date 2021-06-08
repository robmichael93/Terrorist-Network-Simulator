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

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.armygame.recruits.mediaelements.CastUsage;


/**
 * This class knows how to interpret a <code>Location</code> object for use in
 * <code>Playlist</code>s.  It maintains a set of all <code>Location</code>s used in
 * a <code>Playlist</code> by name and knows how to output the summary of all
 * <code>Location</code>s to XML
 */
public class LocationUsage {

  private HashMap myLocations;
  private ArrayList myLocationArrivalRecord;

  public LocationUsage() {
    myLocations = new HashMap();
    myLocationArrivalRecord = new ArrayList();
  }

  /**
   * Adds the specified location to the set of <code>Location</code>s this usage summary is tracking
   * @param newLocation The new <code>Location</code> to track usage of
   */
  public void AddLocationUse( Location newLocation ) {
    String Name = newLocation.Name();
    System.out.println( "============ Adding Location Use For " + Name );
    myLocations.put( Name, newLocation );
    myLocationArrivalRecord.add( Name );
  }

  /**
   * Adds the information for usage of the specified
  /**
   * Summarizes the cast usage of all <code>Location</code>s in this summary to a
   * <code>CastUsage</code> object
   * @param castUsageSummary The <code>CastUsage</code> object to update with this <code>Location</code>'s cast usage
   */
  public void SummarizeCastUsage( CastUsage castUsageSummary ) {
    System.out.println( "Summarizing cast usage" );
    Set KeySet = myLocations.keySet();
    Iterator LocItr = KeySet.iterator();

    while( LocItr.hasNext() ) {
      ( (Location) myLocations.get( LocItr.next() ) ).UpdateCastUsage( castUsageSummary );
    }

  }

  public Location GetNextLocation() {
    System.out.println( "Asked for next location" );
    Location Result = null;
    if ( myLocationArrivalRecord.size() > 0 ) {
      System.out.println( "Got a location" );
      Result = (Location) myLocations.get( (String) myLocationArrivalRecord.remove( 0 ) );
    }
    return Result;
  }

  /**
   * Folds the location usage from the supplied <code>LocationUsage</code> object into this
   * <code>LocationUsage</code> object's summary.  Intended for folding an individual
   * <code>ActionFrame</code>'s location usage into a global summary of location usage
   * @param addedUses The <code>LocationUsage</code> to be folded into this object's usage summary
   */
  public void UpdateUsageSummary( LocationUsage addedUses ) {
    System.out.println( "Update Usage Summary" );
    Set KeySet = addedUses.myLocations.keySet();
    Iterator LocItr = KeySet.iterator();

    while( LocItr.hasNext() ) {
      String Key = (String) LocItr.next();
      System.out.println( "Usage key is " + Key );
      Location NewLocation = (Location) addedUses.myLocations.get( Key );
      myLocations.put( Key, NewLocation );
      myLocationArrivalRecord.add( NewLocation );
      System.out.println( "myLocations now contains " + new Integer( myLocations.size() ).toString() );
    }
    System.out.println( "myLocations now contains " + new Integer( myLocations.size() ).toString() );

  }

  /**
   * Serializes this <code>LocationUsage</code> to the XML format used for Java-Director messaging.
   * This routine outputs the LocationsSummary XML tag and that tag's contents.
   * It doesn't use whitespace or pretty printing because, for efficiency, this XML is intended
   * as a message - not human readable.
   * @return The XML for the LocationsSummary tag
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<LocationsSummary>" );
    Result.append( "<LocationsList>" );
    Set KeySet = myLocations.keySet();
    Iterator LocItr = KeySet.iterator();
    while( LocItr.hasNext() ) {
      Result.append(((Location) myLocations.get( LocItr.next() ) ).toXML());
    }
    Result.append( "</LocationsList>" );
    Result.append( "</LocationsSummary>" );
    return Result.toString();
  }

}

