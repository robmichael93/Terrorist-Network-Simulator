/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

/**
 * Base class for all construction operations.
 */
abstract class ConstructionOperation implements ConstructionTaskItem {

  public ConstructionOperation() {
  }

  public boolean IsSentinel() {
    return false;
  }

}