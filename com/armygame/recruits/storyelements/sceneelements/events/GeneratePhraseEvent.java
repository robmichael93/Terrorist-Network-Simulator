package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class GeneratePhraseEvent {

   private String intent;
   String role;

   public GeneratePhraseEvent(String r, String i) {
      role = r;
      intent = i;
   }

   public String getIntent() {
      return intent;
   }

   public String getRole() {
      return role;
   }
}