/**
 * Title: BitLength
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
 * Class for handling XML BitLength tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class BitLength extends com.armygame.recruits.xml.DocumentParseState {

  /**
   * The single BitLength
   */
  private static BitLength theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private BitLength() {
    super();
  }

  /**
   * Return the BitLength <b>Singleton</b>
   * @return The BitLength <b>Singleton</b>
   */
  public static BitLength Instance() {
    if( theirInstance == null ) {
      theirInstance = new BitLength();
    }
    return theirInstance;
  }


}
