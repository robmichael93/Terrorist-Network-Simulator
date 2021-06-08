package com.armygame.recruits.agentutils.connectors;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 * This event used used to notify any objects listening to the TypeValueConnector
 * that the value of the connector has changed.  This has nothing to do with how
 * the Interactions cause the value of the connector to change.  That occurs
 * by the Interaction notifiying all of its InteractionChangeListeners via a
 * CharacterValueAndConnectorUpdateEvent of the desired change.
 */

public class TypeValueConnectorChangeEvent {

   private TypeValueConnector source;

   public TypeValueConnectorChangeEvent(TypeValueConnector c) {
      source = c;
   }

   public String getConnectorName() {
      return source.getName();
   }

   public String getConnectorValue() {
      return source.getValue();
   }

   public String getConnectorRole() {
      return source.getRole();
   }

}
