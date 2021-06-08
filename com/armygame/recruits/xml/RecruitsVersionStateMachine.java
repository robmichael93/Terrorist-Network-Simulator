/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

import com.armygame.recruits.utils.RecruitsVersion;

/**
 * A <code>ParserStateMachine</code> specialized for reading XML fragments that describe a <code>RecruitsVersion</code>
 * object.
 */
public class RecruitsVersionStateMachine extends ParserStateMachine {

  /**
   * The <code>RecruitsVersion</code> object that this state machine constructs
   */
  private RecruitsVersion myRecruitsVersion;

  public RecruitsVersionStateMachine() {
    super();
    myRecruitsVersion = new RecruitsVersion();
  }



}