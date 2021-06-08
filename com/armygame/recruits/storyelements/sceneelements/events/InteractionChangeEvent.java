package com.armygame.recruits.storyelements.sceneelements.events;

import com.armygame.recruits.storyelements.sceneelements.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class InteractionChangeEvent {

   private Interaction source;

   public InteractionChangeEvent(Interaction s) {
      source = s;
   }

   public Interaction getSource() {
      return source;
   }

   public boolean isInteractionReady() {
      return source.isReady();
   }

}
