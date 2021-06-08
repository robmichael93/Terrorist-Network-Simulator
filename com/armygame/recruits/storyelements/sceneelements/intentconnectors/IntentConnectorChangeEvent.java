package com.armygame.recruits.storyelements.sceneelements.intentconnectors;

/**
 * Title:        Connectors
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Naval Postgraduate School
 * @author Brian Osborn
 * @version 1.0
 */

public class IntentConnectorChangeEvent {

   private IntentConnector source;

   public IntentConnectorChangeEvent(IntentConnector c) {
      source = c;
   }

   public IntentConnector getSource() {
      return source;
   }
}

