package com.armygame.recruits.agentutils.connectors;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 1.0
 */

public class ConnectorChangeEvent {

   private Connector source;

   public ConnectorChangeEvent(Connector c) {
      source = c;
   }

   public double getConnectorValue() {
      return source.getValue();
   }

   public boolean isConnectorExtended() {
      return source.isExtended();
   }

   public String getConnectorName() {
      return source.getName();
   }

   public String getConnectorRole() {
      return source.getRole();
   }

}
