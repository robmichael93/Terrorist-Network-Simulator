package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class RetractConnectorRequestEvent {

   String IntentConnectorName;

   public RetractConnectorRequestEvent(String name) {
      IntentConnectorName = name;
   }

   public String getName() {
      return IntentConnectorName;
   }
}