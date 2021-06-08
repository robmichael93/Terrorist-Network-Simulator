package com.armygame.recruits.mediaelements;


import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Provides a facility for managing the summary of cast usage for the media elements
 * within a story element
 */
public class CastUsage {

  /**
   * Maintains a set of the cast members used within a cast
   */
  private class CastMemberList {

    /**
     * Maintains the set of members for a given cast name - we use a set to enforce only
     * a single entry for any given member name
     */
    private Set myMembers;

    public CastMemberList() {
      myMembers = Collections.synchronizedSet( new HashSet() );
    }

    /**
     * Add the supplied name to the set (letting the set prevent duplicates)
     * @param memberName The name of the member to add
     */
    public void AddMember( String memberName ) {
      myMembers.add( memberName );
    }

    /**
     * Empties the member name list
     */
    public void Clear() {
      myMembers.clear();
    }

    /**
     * Return an array of all the member names in the cast member list
     */
    public String[] Members() {
      return (String[]) myMembers.toArray( new String[1] );
    }

  }

  /**
   * Maintains a set of the cast names used, with each cast name holding a <code>CastMemberList</code>
   * of the cast member names from the cast
   */
  private Map myCasts;

  public CastUsage() {
    myCasts = Collections.synchronizedMap( new HashMap() );
  }


  /**
   * Adds the supplied cast and cast member to the usage summary
   * We use the behavior of the <code>HasSet</code>s to enforce no duplicates
   * @param castName The cast name to record this cast member in
   * @param castMemberName The cast member in the cast
   */
  public void AddCastUse( String castName, String castMemberName ) {
    // Find the cast if it's there, otherwise add one.  Then add the cast member name to 
    // the cast's set
    if ( myCasts.containsKey( castName ) ) {
      ( (CastMemberList) myCasts.get( castName ) ).AddMember( castMemberName );  
    } else {
      CastMemberList NewList = new CastMemberList();
      NewList.AddMember( castMemberName );
      myCasts.put( castName, NewList );
    }
  }

  /**
   * Folds the cast usage summary of another <code>CastUsage</code> object into this summary.
   * This routine is used to build a globally summary out of a collection of piecewise summaries
   * @param addedUses The <code>CastUsage</code> object whose summaries are to be folded into this summary
   */
  public void UpdateUsageSummary( CastUsage addedUses ) {
    // Get a Set view of the cast usage and iterate through it to add all the cast usages it contains
    // to this set
    Set AddedUsesSet = addedUses.myCasts.keySet();
    Iterator AddedUsesItr = AddedUsesSet.iterator();
    while( AddedUsesItr.hasNext() ) {
      String CastKey = (String) AddedUsesItr.next();
      CastMemberList NewMembers = (CastMemberList) addedUses.myCasts.get( CastKey );
      String[] MemberNames = NewMembers.Members();
      for( int i = 0, len = MemberNames.length; i < len; i++ ) {
        AddCastUse( CastKey, MemberNames[i] );
      } 
    }   
  }

  /**
   * Returns the set of all cast members used as a <CODE>List</CODE> of <CODE>CastUsageInfo</CODE>
   * objects.  All list and collection accesses are <B>synchronized</B>
   * 
   * @return The set of all cast members used as a <CODE>List</CODE> of <CODE>CastUsageInfo</CODE> objects
   * @see CastUsageInfo
   */
  public List EnnumerateUsage() {
    List Result = Collections.synchronizedList( new ArrayList() );
    synchronized( Result ) {
      synchronized( myCasts ) {
        Set Casts = myCasts.keySet();
        Iterator Itr = Casts.iterator();
        while( Itr.hasNext() ) {
          String CastName = (String) Itr.next();
          CastMemberList CastMembers = (CastMemberList) myCasts.get( CastName );
          String[] MemberNames = CastMembers.Members();
          for( int i = 0, len = MemberNames.length; i < len; i++ ) {
            Result.add( new CastUsageInfo( CastName, MemberNames[i] ) );
          }
        }
      }
    }
    return Result;
  }

  /**
   * Serializes this <code>CastUsage</code> to the XML format used for Java-Director messaging.
   * This routine outputs the CastUseSummary XML tag and that tag's contents.
   * It doesn't use whitespace or pretty printing because, for efficiency, this XML is intended
   * as a message - not human readable.
   * @return The XML for the CastUseSummary tag
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<CastUseSummary>" );
    Set Casts = myCasts.keySet();
    Iterator XMLItr = Casts.iterator();
    while( XMLItr.hasNext() ) {
      Result.append( "<CastUse>" );
      String CastName = (String) XMLItr.next();
      Result.append( "<CastName>" ); Result.append( CastName ); Result.append( "</CastName>" );
      Result.append( "<CastMemberList>" );
      CastMemberList CastMembers = (CastMemberList) myCasts.get( CastName );
      String[] MemberNames = CastMembers.Members();
      for( int i = 0, len = MemberNames.length; i < len; i++ ) {
        Result.append( "<CastMember>" ); Result.append( MemberNames[i] ); Result.append( "</CastMember>" );
      }
      Result.append( "</CastMemberList>" );
      Result.append( "</CastUse>" );
    }
    Result.append( "</CastUseSummary>" );
    return Result.toString();
  }
}
