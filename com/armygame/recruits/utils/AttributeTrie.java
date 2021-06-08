package com.armygame.recruits.utils;

import java.util.ArrayList;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;
import com.armygame.recruits.utils.BitVector;
import com.armygame.recruits.utils.QueryVector;

/**
 * Maintains an association between a <code>StateVector</code> acting as a key and an
 * <code>Object</code> in a data structure that facilitates wilcard searches to 
 * retrieve all objects in a set that match a wilcard-based <code>QueryVector</code>.
 * This data structure is based on the <b>PatriciaTrie</b> data structure
 * See "Alogirthms" - Sedgewick, Addison Wesely, 1983, pp. 219-223, and "Sorting and Searching",
 * Knuth, Addison-Wesely
 */
public class AttributeTrie extends DefaultXMLSerializer implements XMLSerializable {

 
  /**
   * Each node in the Patricia Trie is encoded as a <code>TrieNode</code>.  Each
   * <code>TrieNode</code> maintains a key for what matches it, and maintains a 
   * <code>TargetList</code> of all objects in the Trie under that key.  Each
   * <code>TrieNode</code> also encodes the radix bit of interest for comparisons
   * and a left and right pointer that point to the <code>TrieNode</code> that
   * matches the next lower and next greater bits of interest respectively.  The
   * idea is that the Patricia Trie does not strictyl encode every comparison necessary
   * to find a node, but instead encodes the next meaningful bit to compare to differentiate
   * the nodes in terms of less than or greater than.  This eliminates redundancy in comparisons 
   * and allows the comparison to skip over similarities between keys and get right to the
   * where they differ.  The Trie of <code>TrieNode</code>s thus maintains the relationship
   * that every node  whose bit of interest with respect to the current node is 0 is to the left
   * of the node, and every node that differs with a 1 is to the right.
   */
  private class TrieNode {

    /**
     * This inner class maintains an extensible list of all target objects that match
     * a given TrieNode.
     */
    private class TargetList {
      /**
       * The extensible list of all objects that match under a given key
       */
      ArrayList myMatches;

      public TargetList() {
        myMatches = new ArrayList( 1 );
      }

      public void AddMatch( Object targetObj ) {
        myMatches.add( targetObj );
      }

      public ArrayList GetMatches() {
        return myMatches;
      }

      public void Clear() {
        myMatches.clear();
      }

    } // inner class TargetList

    // Note the following about branches:
    // Branches aren't set to null if not used, but point to this
    // TrieNode itself as an up pointer.
    // We know if a branch is pointing up because the nature of a Patricia Trie
    // means that all bits of interest decrease as we go down in the tree. so an up-pointing
    // link will necessarily point to a TrieNode whose bit of interest is <= current TrieNode's
    // bit of interest.

    /**
     * The branch to take if the bit of interest is 0
     */
    private TrieNode myLeft;

    /**
     * The branch to take if the bit of interest is 1
     */
    private TrieNode myRight;

    /**
     * If this TrieNode is a match the things it matches will be found in the target list
     */
    private TargetList myMatches;

    /**
     * The key for matches of this node
     */
    private StateVector myKey;

    /**
     * The bit of interest in deciding how to branch from this TrieNode
     */
    private int myBitOfInterest;

    /**
     * Initialize the TrieNode as follows:
     * Links are self up-pointers
     * The index arg tells us where to find matches for this TrieNode in the Attribute Set
     * @param key The key that matches this node
     * @param target The <code>Object</code> associated with the key
     * @param initialBit The bit of interest for comparisons of this key.  As a <code>TrieNode</code>
     * is placed in the Trie it will know what bit of interest is used to differentiate this node
     * from those that refer to it and those that it refers to.
     */
    public TrieNode( StateVector key, Object target, int initialBit ) {
      myLeft = this;
      myRight = this;
      myKey = key;
      myMatches = new TargetList();
      myMatches.AddMatch( target );
      myBitOfInterest = initialBit;
    }

    /**
     * Adds the given object to the set of matching objects maintained by this node
     * @param targetObj The <code>Object</code> that matches this key.
     * <b>Note:</b> It is up to the client to remember the actual type of the <code>Object</code>
     * at a given node - they have to know how to dereference it when they get it back as the
     * result of a future query
     */ 
    public void AddMatch( Object targetObj ) {
      myMatches.AddMatch( targetObj );
    }

    /**
     * Returns the key for this node
     * @return The key for this node
     */
    public StateVector GetKey() {
      return myKey;
    }

    /**
     * Returns the node to the left of this node
     * @return The node to the left of this node
     */
    public TrieNode GetLeft() {
      return myLeft;
    }

    /**
     * Returns the node to the right of this node
     * @return The node to the right of this node
     */
    public TrieNode GetRight() {
      return myRight;
    }

    /**
     * Sets the node to the left of this node
     * @param newLeft The node to the left of this node
     */
    public void SetLeft( TrieNode newLeft ) {
      myLeft = newLeft;
    }

    /**
     * Sets the node to the right of this node
     * @param newLeft The node to the right of this node
     */
    public void SetRight( TrieNode newRight ) {
      myRight = newRight;
    }

    /**
     * Returns the bit of interest in the <code>StateVector</code> key for this node.
     * This is the bit of differentiation between this node and its left and right children
     * @return The bit of interest within the <code>StateVector</code> that differentiates this
     * node from its children at this point in the Trie.
     */
    public int KeyBit() {
      return myBitOfInterest;
    }

    /**
     * Sets the bit of interest to the bit supplied
     * @param newBit The new bit of interest/differentiation for this node
     */
    public void SetBit( int newBit ) {
      myBitOfInterest = newBit;
    }

    /**
     * Return all the <code>Object</code>s that match this node's key
     * @return The set of all <code>Object</code>s that match this node's key
     */
    public ArrayList GetMatches() {
      return myMatches.GetMatches();
    }

    /**
     * <b>Debug</b> Turns this set of matches into a readable string
     */
    public String toString() {
      String Result = "";
      ArrayList Matches = myMatches.GetMatches();
      for( int i = 0; i < Matches.size(); i++ ) {
        Result += Matches.get(i).toString();
      }
      return Result;
    }

  }

  /**
   * The size of the BitVector keys used to index matches in the Trie, in bits
   */
  private int myKeySize;

  /**
   * The first element of the Trie
   */
  private TrieNode myHead;

  /**
   * This holds the current query vector so we don't have to pass it to recursive calls
   */
  private QueryVector myQueryVector;

  /**
   *This holds the cummulative result of the objects that matched a queryu so we don't
   * have to pass it to recursive calls
   */
  private ArrayList myQueryResults;

  /**
   * Each AttributeTrie is initialized to use a TemplateAttributeSet afor BitVectors of the specific bit size
   * @param rangeMap The <code>RangeMap</code> that the keys of this Trie use for their encoding
   * @param size The size (in bits) of the keys
   */
  public AttributeTrie( RangeMap rangeMap, int size ) {
    myKeySize = size;
    myQueryVector = null;
    myQueryResults = new ArrayList();

    // The first node of the Trie always refers to the special 'bogus' 0th member of the
    // TemplateAttributeSet
    myHead = new TrieNode( new StateVector( rangeMap ), null, myKeySize );
  }

  /**
   * A no-arg constructor useful for XML parsing
   */
  public AttributeTrie() {
    super();
    myKeySize = 0;
    myQueryVector = null;
    myQueryResults = new ArrayList();
    myHead = null;
  }

  /**
   * Set a prototype <code>RangeMap</code> to use with this Trie.  We assume the size of the keys
   * is the same as the key size of the range map
   * @param rangeMap The prototype <code>RangeMap</code> to use to initialize this <code>AttributeTrie</code>
   **/
  public void SetPrototypeRangeMap( RangeMap rangeMap ) {
    myKeySize = rangeMap.BaseRangeSize();

    // The first node of the Trie always refers to the special 'bogus' 0th member of the
    // TemplateAttributeSet
    myHead = new TrieNode( new StateVector( rangeMap ), null, myKeySize );
  }

  /**
   * Reads state into this <code>AttributeTrie</code> from the specified XML file
   * @param file The full path of the XML file to read
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }  

  /**
   * Inserts the given <code>TargetObjectKey</code> (which includes both the key and the
   * <code>Object</code> into the Trie at the proper place - either adding this <code>Object</code>
   * to an existing key in the Trie if it is already there, or adding a new node to the Trie
   * (and fixing up the Radix sort pointers in the Trie) if this key is not yet in the Trie
   * @param target The <code>TargetObjectKey</code> that includes both the <code>StateVector</code>
   * key and the <code>Object</code> of what we want to insert
   */
  public void TrieInsert( TargetObjectKey target ) {
    // // System.out.println( "Inserting " + obj );
    TrieNode Result = PatriciaInsert( target.GetKey(), myHead, target.GetTargetObj() );
  }

  /**
   * Searches this Trie and returns all items in it whose key matches the search key <code>StateVector</code>
   * supplied.
   * @param key A <code>StateVector</code> that encodes the key to match on
   * @return An <code>ArrayList</code> of all the target objects in this Trie that match the supplied key, or <code>null</code> if nothing matched
   */
  public ArrayList TrieQuery( StateVector key ) {
    TrieNode BestMatch = PatriciaSearch( key, myHead );

    // The best match may not be an EQUALS match to the search key, so we need to double check it
    if ( BestMatch.GetKey().equals( key ) ) {
      return BestMatch.GetMatches();
    }
    else {
      return null;
    }

  }

  /*
  public Object[] TrieQuery( QueryVector key ) {
    Object[] Result = PatriciaQuery( key, myHead );
    return Result;
  }
  */

  /**
   * <b>Debug</b> method for viewing what's in this Trie
   */
  public void PrintTrie() {
    PrintTrie( myHead, "" );
  }

  /**
   * <b>Debug</b> method.  Recursively prints the Trie with indenting to represent nesting
   */
  private void PrintTrie( TrieNode startingNode, String indent ) {

    // We print this node and then follow down links, NOT up links in recursively printing the children
    // System.out.print( startingNode.GetKey() + " BOI = " + startingNode.KeyBit() + ", Matches -> " );
    ArrayList Matches = startingNode.GetMatches();
    for( int i = 0; i < Matches.size(); i++ ) {
      System.out.print( (String) Matches.get( i ) + "," );
    }
    System.out.println();


    // Go Left?
    if ( startingNode.GetLeft().KeyBit() < startingNode.KeyBit() ) {
      System.out.print( indent + " L-> " );
      PrintTrie( startingNode.GetLeft(), indent + "  " );
    } else {
      System.out.println( indent  + " L UP-> " + startingNode.GetLeft().GetKey() );
    }

    // Go Right?
    if ( startingNode.GetRight().KeyBit() < startingNode.KeyBit() ) {
      System.out.print( indent + " R-> " );
      PrintTrie( startingNode.GetRight(), indent + "  " );
    } else {
      System.out.println( indent + " R UP-> " + startingNode.GetRight().GetKey() );
    }
  }

  /**
   * Queries the Trie and finds all items whose key matches the supplied <code>QueryVector</code>.
   * <code>QueryVector</code>s support the notion of wildcard searching in which the bit in a
   * given position in the <code>StateVector</code> that encodes the keys may be specified
   * precisely as 0 or 1, or as wildcard (*) which matches either 0 or 1.  If multiple items
   * were placed in the Trie with the same key, or if the wildcard query matches multiple items,
   * then all such items are returned.  Note that it is the responsibility of the client of the
   * Trie to know how to dereference the found items as they are held as anonymous <code>Object</code>s
   * by the Trie.
   * @param query The <code>QueryVector</code> that encodes what key(s) to search for
   * @return An <code>ArrayList</code> of all the keys that matched the query search
   */
  public ArrayList PatriciaQuery( QueryVector query ) {
    myQueryVector = query;

    // Forget any pending results
    myQueryResults.clear();

    // Since the head node is a bogus placeholder sentinel we always start traversals to its left
    PatriciaQuery( myKeySize, myHead.GetLeft() );

    return myQueryResults;
  }

  /**
   * The public <code>PatriciaQuery()</code> calls this private method to do its recursive
   * searching.  
   * @param parentBitOfInterest The bit position in the Radix search that we are currently comparing on
   * @param startingNode For this (possibly recursive) call this is the node of interest for the search that we are currently doing comparisons with
   * <b>Note:</b> We use the <code>AttributeTrie</code>'s <code>myQueryResults</code> member to store results, rather than return them from each invocation to save on stack space.
   */
  private void PatriciaQuery( int parentBitOfInterest, TrieNode startingNode ) {

    int CurrentBitOfInterest = startingNode.KeyBit();
    int NextNonWild;
    int NonWildBit;

    // We set up a little lookup table for what to do for the Left and Right children during a traversal
    TrieNode[] GoNodes = new TrieNode[ 2 ];

    // // System.out.println( indent + "Looking for " + myQueryVector + " bit " + CurrentBitOfInterest + " in " + myTemplateAttributeSet.GetKeyForTemplate( startingNode.myAttributeSetIndex ) );
    // Wildcards tell us which children of this node we need to follow to try and find matches
    // If the bit is wild we need to search down both branches, if it is an explicit 1 we traverse
    // right, if it's a 0 we go left
    if ( myQueryVector.IsWild( CurrentBitOfInterest ) ) {
      // // System.out.println( indent + "Is Wild " );
      GoNodes[ 0 ] = startingNode.GetLeft();
      GoNodes[ 1 ] = startingNode.GetRight();
    } else {

      if ( myQueryVector.IsSet( CurrentBitOfInterest ) ) {
        // // System.out.println( indent + "Match going Right" );
        GoNodes[ 0 ] = null;
        GoNodes[ 1 ] = startingNode.GetRight();
      } else {
        // // System.out.println( indent + "Match going Left" );
        GoNodes[ 0 ] = startingNode.GetLeft();
        GoNodes[ 1 ] = null;
      }

    }

    // Loop through the work set up for us in the llookup table - we take the branch or branches
    // determined by the branch lookup table logic above
    for( int i = 0; i < 2; i++ ) {

      // System.out.println( "Checking " + ( ( i == 0 ) ? "Left" : "Right" ) );
      if ( GoNodes[ i ] != null ) {

        // We try and go both ways if it's a wildcard.  We don't follow up links if we aren't matching
        TrieNode NextNode = GoNodes[ i ];

        // System.out.println( "Next node's key bit is " + NextNode.KeyBit() );

        if ( NextNode.KeyBit() != myKeySize ) {

          if ( NextNode.KeyBit() >= CurrentBitOfInterest ) {

              //if ( myQueryVector.Matches( NextNode.GetKey().BitVector() ) ) {
              //  // System.out.println( "Should really have added " + NextNode.toString() );
                // myQueryResults.addAll( NextNode.GetMatches() );
              //}

            // System.out.println( "Left is Up" );
            // We need to consider all intervening non-wilds between us and our parent
            boolean InterveningAllMatched = true;  // Assume matched until proven otherwise - allows anding or results
            int BitToCheckFrom = parentBitOfInterest;
            NextNonWild = myQueryVector.FirstNonWildFrom( BitToCheckFrom );
            NonWildBit = ( ( NextNonWild == -1 ) ? -1 : myQueryVector.GetBit( NextNonWild ) );

            // System.out.println( "BitToCheckFrom " + BitToCheckFrom );
            // System.out.println( "NonWildBit " + NonWildBit );


            while( NextNonWild != -1 ) {

              // System.out.println( "Looping" );
              if ( NextNode.GetKey().GetBit( NextNonWild ) != NonWildBit ) {
                // We didn't match
                InterveningAllMatched = false;
                break;
              } else {

                BitToCheckFrom = NextNonWild;

                NextNonWild = myQueryVector.FirstNonWildFrom( BitToCheckFrom );
                NonWildBit = ( ( NextNonWild == -1 ) ? -1 : myQueryVector.GetBit( NextNonWild ) );

              }

            } // while

            if ( InterveningAllMatched == true ) {
              // System.out.println( "Adding " + NextNode.toString() );
              if ( myQueryVector.Matches( BitToCheckFrom, NextNode.GetKey().BitVector() ) ) {
                // System.out.println( "Should really have added " + NextNode.toString() );
                myQueryResults.addAll( NextNode.GetMatches() );
              }
            }

          } else {
            // Going Down
            // System.out.println( "Going Down" );
            PatriciaQuery( CurrentBitOfInterest, NextNode );
          }  // Left Stuff

        } else {
          // System.out.println( "Left was Sentinel so bail" );
        } // not sentinel

      } else {
        // System.out.println( "Node was NULL, i = " + i );
      }

    } // for

  } // PatriciaQuery


  /**
   * Finds the <code>TrieNode</code> that matches or is closest to the supplied search key
   * @param searchKey The key we are looking to insert or find the closest location of
   * @param startingNode The node to start our search traversal from
   * @return The closest matching node in the Trie
   */
  private TrieNode PatriciaSearch( StateVector searchKey, TrieNode startingNode ) {
    TrieNode f;

    // Keep branching DOWN until we find an UP pointer, when bit of interest of search key is 0 = go left, 1 = go right
    while( true ) {
      f = startingNode;
      if ( searchKey.IsSet( startingNode.KeyBit() ) ) {
        startingNode = startingNode.GetRight();
      } else {
        startingNode = startingNode.GetLeft();
      } // else
      if ( f.KeyBit() <= startingNode.KeyBit() ) {
        break;
      }
    } // while

    return startingNode;
  }

  /**
   * Inserts the supplied <code>Object</code> under the given insert key into the Trie.
   * The method first searches to find if it is already in the Trie (it adds it to the list
   * of matches for a matching key if its already there). Otherwise it adds a node to the Trie
   * under the new key.
   * @param insertKey The key to associate the supplied Object with in the Trie
   * @param startingNode The node to start our insertion search from
   * @param obj The <code>Object</code> to associate with this key.  Note that it is the clients responsibility to remember how to dereference the generic <code>Object</code>s the Trie is holding for them
   * @return The <code>TrieNode</code> where the inserted object actually ended up
   */
  public TrieNode PatriciaInsert( StateVector insertKey, TrieNode startingNode, Object obj ) {
    TrieNode t;
    TrieNode f;
    int i;

    t = PatriciaSearch( insertKey, startingNode );

    // Since we allow multiple entries with the same index in our Trie we need to check this initial
    // search result to see if it's a match.  If it is, we simply add the object to the list of objects
    // that match at this node's index in the TemplateAttributeSet, otherwise we need to keep searching
    StateVector KeyOfFound = t.GetKey();
    if ( insertKey.equals( KeyOfFound ) ) {
      t.AddMatch( obj );
    } else {
      i = myKeySize - 1;

      while( insertKey.GetBit( i ) == KeyOfFound.GetBit( i ) ) {
        i--;
      }

      while( true ) {
        f = startingNode;
        if ( insertKey.GetBit( startingNode.KeyBit() ) == 0 ) {
          startingNode = startingNode.GetLeft();
        } else {
          startingNode = startingNode.GetRight();
        }
        if ( ( startingNode.KeyBit() <= i ) || ( f.KeyBit() <= startingNode.KeyBit() ) ) {
          break;
        }
      }

      // Put this object and its key in
      t = new TrieNode( insertKey, obj, i );

      if ( insertKey.GetBit( t.KeyBit() ) == 0 ) {
        t.SetLeft( t );
        t.SetRight( startingNode );
      } else {
        t.SetRight( t );
        t.SetLeft( startingNode );
      }

      if ( insertKey.GetBit( f.KeyBit() ) == 0 ) {
        f.SetLeft( t );
      } else {
        f.SetRight( t );
      }
    } // else need to insert a new one

    return t;

  } // PatriciaInsert

} // class AttributeTrie
