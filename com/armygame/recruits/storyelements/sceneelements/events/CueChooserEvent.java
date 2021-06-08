package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class CueChooserEvent {

   private String role;

   public CueChooserEvent(String role) {
      this.role = role;
   }

   public String getRole() {
      return role;
   }
}