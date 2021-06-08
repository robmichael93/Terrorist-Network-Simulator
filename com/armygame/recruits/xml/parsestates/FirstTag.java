package com.armygame.recruits.xml.parsestates;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;

public class FirstTag extends DocumentParseState {

  private static FirstTag theirInstance = null;

  private FirstTag() {
  }

  public static FirstTag Instance() {
    if ( theirInstance == null ) {
      theirInstance = new FirstTag();
    }
    return theirInstance;
  }

}
