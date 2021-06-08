/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.utils;

import java.util.HashMap;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;

/**
 * Maintains an index for managing an index file that stores and retrieves <code>RangeMap</code>s as
 * (name, <code>RangeMap</code>) pairs.  Intended use is for the project to maintain a global
 * <b>Singleton</b> index of its <code>RangeMap</code>s
 */
public class RangeMapIndex extends DefaultXMLSerializer implements XMLSerializable {

  /**
   * The (name,<code>RangeMap</code> index set
   */
  HashMap myRangeMapIndex;

  /**
   * Initialize an empty index.
   */
  public RangeMapIndex() {
    myRangeMapIndex = new HashMap( 8 ); // we don't expect many <code>RangeMaps</code>
  }

  public void Debug() {
    System.out.println( "Range Map Index Debug dump" );
    System.out.println( "The range map index contains " + myRangeMapIndex.size() + " entries" );
    System.out.println( "They are " + myRangeMapIndex.toString() );
  }

  /**
   * Reads the <code>RangeMaps</code> for this index from an XML manifest
   * @param file The full path to the XML file to read
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }

  /**
   * Clear the index
   */
  public void Clear() {
    myRangeMapIndex.clear();
  }

  /**
   * Add a <code>RangeMap</code> index of the specified name
   * <b>Note:</b> Doesn't check for dupes
   * @param name The name of the <code>RangeMap</code>
   * @param rangeMap The <code>RangeMap</code> to add
   */
  public void AddRangeMap( String name, RangeMap rangeMap ) {
    myRangeMapIndex.put( name, rangeMap );
  }

  /**
   * See if a <code>RangeMap</code> with the given index is in the map
   * @param name The name of the <code>RangeMap</code> to search for
   * @return Returns <code>true</code> if a <code>RangeMap</code> of the specified name is in the index, otherwise <code>false</code>
   */
  public boolean Exists( String name ) {
    return myRangeMapIndex.containsKey( name );
  }

  /**
   * Retrieve the <code>RangeMap</code> with the specified name from the index
   * @param name The name of the <code>RangeMap</code> to search for
   * @return The matching <code>RangeMap</code>
   */
  public RangeMap GetRangeMap( String name ) {
    return (RangeMap) myRangeMapIndex.get( name );
  }

}
