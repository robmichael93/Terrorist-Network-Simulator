/**
 * Title: NewDocument
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.xml.parsestates;

import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;


public class NewDocument extends DocumentParseState {

  private static NewDocument theirInstance = null;

  private NewDocument() {
    super();
  }

  public static NewDocument Instance() {
    if ( theirInstance == null ) {
      theirInstance = new NewDocument();
    }
    return theirInstance;
  }

  public void StartDocument( ParserStateMachine context ) {
    ChangeState( context, FirstTag.Instance() );
  }

}
