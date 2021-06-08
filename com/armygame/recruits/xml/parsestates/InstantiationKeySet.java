package com.armygame.recruits.xml.parsestates;

/**
 * Title: InstantiationKeySet
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;

/**
 * Class for handling XML InstantiationKeySet tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class InstantiationKeySet extends DocumentParseState {

  /**
   * The single InstantiationKeySet
   */
  private static InstantiationKeySet theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private InstantiationKeySet() {
    super();
  }

  /**
   * Return the InstantiationKeySet <b>Singleton</b>
   * @return The InstantiationKeySet <b>Singleton</b>
   */
  public static InstantiationKeySet Instance() {
    if( theirInstance == null ) {
      theirInstance = new InstantiationKeySet();
    }
    return theirInstance;
  }

}
