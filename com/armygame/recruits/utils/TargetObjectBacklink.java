/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 */

package com.armygame.recruits.utils;

/**
 * Objects that are the targets of <code>TargetObjectKey</code> should implement this interface in order
 * to support automagic backlinking of the target object to its <code>TargetObjectKey</code>.  This works
 * by passing the <code>TargetObjectKey</code> to the object and having it call <code>Backlink</code>.
 */
public interface TargetObjectBacklink {
  public void Backlink( TargetObjectKey targetObjectKey );
}