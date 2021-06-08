/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * Manages a categorized set of <code>AttributeTrie</code>s as a collection.  Each <code>AttributeTrie</code> in
 * the collection is maintained as a mapping (name,<code>AttributeTrie</code>) so that operations on
 * Tries in the grove can be performed by name.  The intent of categorizing in this way is to refine
 * and limit where searches are made for things like location media assets.
 */
public class AttributeGrove extends DefaultXMLSerializer implements XMLSerializable {


  /**
   * The mapping of names to <code>AttributeTrie</codes> is managed by this <code>HashMap</code>
   */
  private HashMap myGroveMap;

  /**
   * Refers to the prototype <code>RangeMap</code> we'll use to instantiate each <code>AttributeTrie</code> in the grove
  */
  private RangeMap myPrototypeRangeMap;

  /**
   * The key size of vectors in the tries
   */
  private int myKeySize;

  /**
   * A no-arg constructor useful for XML parsing
   */
  public AttributeGrove() {
    super();
    myGroveMap = new HashMap();
    myPrototypeRangeMap = null;
    myKeySize = 0;
  }

  /**
   * The constructor needs a <code>RangeMap</code> and bit field size to use as prototypes for its
   * <code>AttributeTrie</code>s
   * @param rangeMap The prototype <code>RangeMap</code> to use to instantiate <code>AttributeTrie</code>s
   * @param size The size of the keys in the <code>AttributeTrie</code>s
   */
  public AttributeGrove( RangeMap rangeMap, int size ) {
    super();
    myGroveMap = new HashMap();
    myPrototypeRangeMap = rangeMap;
    myKeySize = size;
  }

  /**
   * Set a prototype <code>RangeMap</code> to use with this grove.  We assume the size of the keys
   * is the same as the key size of the range map
   * @param rangeMap The prototype <code>RangeMap</code> to use to initialize <code>AttributeTrie</code>s in the grove
   **/
  public void SetPrototypeRangeMap( RangeMap rangeMap ) {
    myPrototypeRangeMap = rangeMap;
    // We assume the size of the keys is the same as the key size of the range map
    myKeySize = myPrototypeRangeMap.BaseRangeSize();
  }

  /**
   * Reads state into this <code>AttributeGrove</code> from the specified XML file
   * @param file The full path of the XML file to read
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }

  /**
   * Adds the given <code>TargetObjectKey</code> to the grove as follows:
   * If grove already knows about the supplied role, the <code>TargetAttributeKey</code>
   * is inserted into that role's trie.  If the role is not yet known, the grove creates
   * a new <code>AttributeTrie</code> for that role name and inserts the <code>TargetObjectKey</code>
   * into the new trie.
   * @param roleName Used as the key in the grove map to find which <code>AttributeTrie</code> to insert into
   * @param target The item to insert into the trie named by the <code>roleName</code>
   */
  public void Insert( String roleName, TargetObjectKey target ) {
    // System.out.println( "Adding role " + roleName + " with TOK \n" + target.toString() );
    if ( myGroveMap.containsKey( roleName ) ) {
      // System.out.println( "Role already in grove" );
      ( (AttributeTrie) myGroveMap.get( roleName ) ).TrieInsert( target );
    } else {
      // System.out.println( "Role new to grove" );
      AttributeTrie NewTrie = new AttributeTrie( myPrototypeRangeMap, myKeySize );
      NewTrie.TrieInsert( target );
      myGroveMap.put( roleName, NewTrie );
    }
  }

  public void Insert( TargetObjectKey target, String roleName ) {
    Insert( roleName, target );
  }


  /**
   * Queries the grove for a <code>TargetObjectKey</code> under the given role name that matches
   * If either the role name is not know to the grove the result is <code>null</code>
   * the supplied query key
   * @param roleName Used as a key in the grove map to find which <code>AttributeTrie</code> to query
   * @param query The query key to use for the search
   * @return An <code>ArrayList</code> of all matches of the key in the grove's trie for the supplied <code>roleName</code>.  If the <code>roleName</code> is not known in this grove then returns <code>null</code>
   */
  public ArrayList Query( String roleName, QueryVector query ) {
    ArrayList Result = new ArrayList();
    if ( myGroveMap.containsKey( roleName ) ) {
      // System.out.println( "Grove knows about role " + roleName );
      // System.out.println( "Query is \n" + query.toString() );
      Result = ( (AttributeTrie) myGroveMap.get( roleName ) ).PatriciaQuery( query );
    } else {
      System.out.println( "Grove doesn't know about role " + roleName );
    }
    // System.out.println( "Grove query returned " + Result.size() + " results" );
    return Result;
  }

  /**
   * Returns the <code>AttributeTrie</code> associated with the specified role name, or
   * </code>null</code> if it ain't there
   * @param roleName The key name of the <code>AttributeTrie</code> we are looking for in this grove
   * @return The <code>AttributeTrie</code> known in this grove under the <code>roleName</code>, or <code>null</code> if not there
   */
  public AttributeTrie GetTrieForRole( String roleName ) {
    AttributeTrie Result = null;
    if ( myGroveMap.containsKey( roleName ) ) {
      Result = (AttributeTrie) myGroveMap.get( roleName );
    }
    return Result;
  }

  public void Debug() {
    // Print out everything in the hash map
    Iterator DebugItr = myGroveMap.values().iterator();
    while( DebugItr.hasNext() ) {
      AttributeTrie NextValue = (AttributeTrie) DebugItr.next();
      NextValue.PrintTrie();
    }

  }

}
