/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.playlist;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Maintains a summary of the characters in a <code>Playlist</code> and the animation
 * actions they are asked to perform in that <code>Playlist</code>.
 */
public class CharacterUsage {

  /**
   * Animations are recorded by name and whether or not they are special or normal
   */
  public class AnimationDef {

    /**
     * The name of the animation
     */
    private String myAnimationName;

    /**
     * Flag - when <code>true</code> indicates a special animation, otherwise
     * <code>false</code> indicates a normal animation
     */
    private boolean myIsSpecialFlag;

    /**
     * Each animation definition records the name of the animation, and whether it is a special
     * or normal animation
     * @param name The name of the animation
     * @param specialFlag <code>true</code> if this is a special animation, <code>false</code> if it is a normal animation
     */
    public AnimationDef( String name, boolean specialFlag ) {
      myAnimationName = name;
      myIsSpecialFlag = specialFlag;
    }

    /**
     * Define <code>equals</code> for this class to prevent duplicate animation names in the
     * <b>CharacterActionsSummary</b> so that <code>java.util.HashSet</code>
     * knows about the duplicates since they aren't stored by a simple key but
     * instead by this an <code>AnimationDef</code> object as the key.
     * @param rhs The Right Hand Side <code>AnimationDef</code> for the <code>equals</code> comparison
     */
   // public boolean equals( AnimationDef rhs ) {
    public boolean equals( Object rhs_temp ) {
      AnimationDef rhs = (AnimationDef) rhs_temp;
      boolean Result = false;
      if ( ( myAnimationName.equalsIgnoreCase(rhs.myAnimationName) ) &&
           ( myIsSpecialFlag == rhs.myIsSpecialFlag ) ) {
        Result = true;
      }
      return Result;
    }

    /**
     * We need to override <CODE>hashCode</CODE> since we overrode <CODE>equals</CODE>.
     * Our hash code is the animation name with a 0 or 1 appended
     * depending on whether the animation is special or not
     *
     * @return Returns the hashcode of the name string we built
     */
    public int hashCode() {
      String UniqueName = myAnimationName + ( myIsSpecialFlag ? "1" : "0" );
      return UniqueName.hashCode();
    }

    /**
     * Return the animation name
     * @return The animation name
     */
    public String Name() {
      return myAnimationName;
    }

    public void SetName( String newName ) {
      myAnimationName = newName;
    }

    /**
     * Return the speciality of this animation
     * @return <code>true</code> if this animation is special, <code>false</code> if it is normal
     */
    public boolean IsSpecial() {
     return myIsSpecialFlag;
    }

  }

  /**
   * Maintains a set of the character animations used by a character
   */
  private class AnimationsList {

    /**
     * Maintains the set of animations for a given character name - we use a set to enforce only
     * a single entry for any given animation name
     */
    private HashSet myAnimations;

    public AnimationsList() {
      myAnimations = new HashSet();
    }

    /**
     * Add the supplied name to the set (letting the set prevent duplicates)
     * @param animationName The name of the animation to add
     * @param specialFlag <code>true</code> if this is a special animation, <code>false</code> if it is a normal animation
     */
    public void AddAnimation( String animationName, boolean specialFlag ) {
      myAnimations.add( new AnimationDef( animationName, specialFlag ) );
    }

    /**
     * Empties the animation name list
     */
    public void Clear() {
      myAnimations.clear();
    }

    /**
     * Return an array of all the animation names in the character animation list
     */
    public AnimationDef[] Animations() {
      return (AnimationDef[]) myAnimations.toArray( new AnimationDef[1] );
    }

  }


  /**
   * Maintains a set of the character names used, with each character name holding a <code>AnimationsList</code>
   * of the character animation names from the character
   */
  private HashMap myCharacters;

  /**
   * Construct an empty mapping of character to animations
   */
  public CharacterUsage() {
    myCharacters = new HashMap();
  }


  /**
   * Adds the supplied character and character animation to the usage summary
   * We use the behavior of the <code>HasSet</code>s to enforce no duplicates
   * @param characterName The character name to record this character animation in
   * @param characterAnimationName The character animation in the character
   * @param isSpecialFlag <code>true</code> if we are addind a special animation, else <code>false</code> to indicate a normal animation
   */
  public void AddCharacterUse( String characterName, String characterAnimationName, boolean isSpecialFlag ) {
    // Find the character if it's there, otherwise add one.  Then add the character animation name to
    // the character's set
    if ( myCharacters.containsKey( characterName ) ) {
      ( (AnimationsList) myCharacters.get( characterName ) ).AddAnimation( characterAnimationName, isSpecialFlag );
    } else {
      AnimationsList NewList = new AnimationsList();
      NewList.AddAnimation( characterAnimationName, isSpecialFlag );
      myCharacters.put( characterName, NewList );
    }
  }

  /**
   * Adds the supplied character and character animation to the usage summary
   * We use the behavior of the <code>HasSet</code>s to enforce no duplicates
   * @param characterName The character name to record this character animation in
   * @param animationDetails The <code>AnimationDef</code> that describes the name and speciality of this animation
   */
  private void AddCharacterUse( String characterName, AnimationDef animationDetails ) {
    // Find the character if it's there, otherwise add one.  Then add the character animation name to
    // the character's set
    String AnimationName = animationDetails.Name();
    boolean SpecialFlag = animationDetails.IsSpecial();
    AddCharacterUse( characterName, animationDetails.Name(), animationDetails.IsSpecial() );
  }

  /**
   * Folds the character usage summary of another <code>CharacterUsage</code> object into this summary.
   * This routine is used to build a global summary out of a collection of piecewise summaries
   * @param addedUses The <code>CharacterUsage</code> object whose summaries are to be folded into this summary
   */
  public void UpdateUsageSummary( CharacterUsage addedUses ) {
    // Get a Set view of the character usage and iterate through it to add all the character usages it contains
    // to this set
    Set AddedUsesSet = addedUses.myCharacters.keySet();
    Iterator AddedUsesItr = AddedUsesSet.iterator();
    while( AddedUsesItr.hasNext() ) {
      String CharacterKey = (String) AddedUsesItr.next();
      AnimationsList NewAnimations = (AnimationsList) addedUses.myCharacters.get( CharacterKey );
      AnimationDef[] Animations = NewAnimations.Animations();
      for( int i = 0, len = Animations.length; i < len; i++ ) {
        AddCharacterUse( CharacterKey, Animations[i] );
      }
    }
  }

  public void ResolveAnimation( String roleName, String origAnimName, String newAnimName ) {
    AnimationsList CharsAnimations = (AnimationsList) myCharacters.get( roleName );
    AnimationDef[] AnimList = CharsAnimations.Animations();
    for( int i = 0; i < AnimList.length; i++ ) {
      if ( AnimList[ i ].Name().equals( origAnimName ) ) {
        AnimList[ i ].SetName( newAnimName );
      }
    }
  }

  public List EnnumerateUsage() {
    List Result = Collections.synchronizedList( new ArrayList() );
    synchronized( Result ) {
      synchronized( myCharacters ) {
        Set Characters = myCharacters.keySet();
        Iterator Itr = Characters.iterator();
        while( Itr.hasNext() ) {
          String CharName = (String) Itr.next();
          AnimationsList Chars = (AnimationsList) myCharacters.get( CharName );
          CharacterUsage.AnimationDef[] CharacterDefs = Chars.Animations();
          for( int i = 0, len = CharacterDefs.length; i < len; i++ ) {
            Result.add( new CharacterUsageInfo( CharName, CharacterDefs[i] ) );
          }
        }
      }
    }
    return Result;
  }
  /**
   * Serializes this <code>CharacterUsage</code> to the XML format used for Java-Director messaging.
   * This routine outputs the CharacterSummary XML tag and that tag's contents.
   * It doesn't use whitespace or pretty printing because, for efficiency, this XML is intended
   * as a message - not human readable.
   * @return The XML for the CharacterSummary tag
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<CharacterSummary>" );
    Result.append( "<CharacterList>" );
    Set CharacterSet = myCharacters.keySet();
    Iterator CharItr = CharacterSet.iterator();
    while( CharItr.hasNext() ) {
      String CharacterName = (String) CharItr.next();
      Result.append( "<Character>" );
      Result.append( "<CharacterName>" ); Result.append( CharacterName ); Result.append( "</CharacterName>" );
      Result.append( "<CharacterActionsSummary>" );
      AnimationsList AllAnimations = (AnimationsList) myCharacters.get( CharacterName );
      AnimationDef[] Animations = AllAnimations.Animations();
      Result.append( "<DefaultActionsList>" );
      int len = Animations.length;
      for( int i = 0; i < len; i++ ) {
        boolean SpecialFlag = Animations[i].IsSpecial();
        if ( SpecialFlag == false ) {
          Result.append( "<DefaultAction>" ); Result.append( Animations[i].Name() ); Result.append( "</DefaultAction>" );
        }
      }
      Result.append( "</DefaultActionsList>" );
      Result.append( "<SpecialActionsList>" );
      for( int j = 0; j < len; j++ ) {
        boolean SpecialFlag = Animations[j].IsSpecial();
        if ( SpecialFlag == true ) {
          Result.append( "<SpecialAction>" ); Result.append( Animations[j].Name() ); Result.append( "</SpecialAction>" );
        }
      }
      Result.append( "</SpecialActionsList>" );
      Result.append( "</CharacterActionsSummary>" );
      Result.append( "</Character>" );
    }
    Result.append( "</CharacterList>" );
    Result.append( "</CharacterSummary>" );
    return Result.toString();
  }

}

