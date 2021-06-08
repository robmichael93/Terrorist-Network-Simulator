package com.armygame.recruits.xml;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

abstract class ConstructionEndSentinel implements ConstructionTaskItem {

  public ConstructionEndSentinel() {
  }

  public boolean IsSentinel() {
    return true;
  }
}