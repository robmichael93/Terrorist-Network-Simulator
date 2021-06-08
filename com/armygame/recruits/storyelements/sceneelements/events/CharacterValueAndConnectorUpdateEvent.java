package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class CharacterValueAndConnectorUpdateEvent {

   private Object role;
   private Object trait;
   private Object valueUpdate;
   private String targetType;

   public CharacterValueAndConnectorUpdateEvent(Object r, Object t, Object vU) {
      role = r;
      trait = t;
      valueUpdate = vU;
      if (!(vU instanceof String)) {
         targetType = "trait";
      }
      else {
         targetType = "connector";
      }
   }

   public String getRole() {
      return (String) role;
   }

   public String getTrait() {
      return (String) trait;
   }

   public double getDelta() {
      return ((Double) valueUpdate).doubleValue();
   }

   public String getConnectorType() {
      return (String) trait;
   }

   public String getConnectorValue() {
      return (String)valueUpdate;
   }

   public String getTargetType() {
      return targetType;
   }

}