package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class CheckVersionSentinel extends EndConstructorSentinel {

  public CheckVersionSentinel() {
  }

  public void Execute( ParserStateMachine context ) {
    // Force the just constructed RecruitsVersion to check compatability with the MasterVersion
    context.CheckMasterVersion();
  }
}