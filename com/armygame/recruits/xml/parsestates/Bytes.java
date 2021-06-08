/**
 * Title: Bytes
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
package com.armygame.recruits.xml.parsestates;


import org.xml.sax.Attributes;

import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;


/**
 * Class for handling XML Bytes tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class Bytes extends DocumentParseState {

  /**
   * The single Bytes
   */
  private static Bytes theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private Bytes() {
    super();
  }

  /**
   * Return the Bytes <b>Singleton</b>
   * @return The Bytes <b>Singleton</b>
   */
  public static Bytes Instance() {
    if( theirInstance == null ) {
      theirInstance = new Bytes();
    }
    return theirInstance;
  }

}
