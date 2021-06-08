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

public class SubSystemVersions extends com.armygame.recruits.xml.DocumentParseState {

  private static SubSystemVersions theirInstance = null;

  private SubSystemVersions() {
    super();
  }

  public static SubSystemVersions Instance() {
    if( theirInstance == null ) {
      theirInstance = new SubSystemVersions();
    }
    return theirInstance;
  }

}
