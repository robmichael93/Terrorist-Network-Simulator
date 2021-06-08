package com.armygame.recruits.storyelements.sceneelements;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class RoleConnectorPair extends StringPair {

   public RoleConnectorPair(String role, String connector) {
      super(role, connector);
   }

   public String Role() {
      return getString1();
   }

   public String Connector() {
      return getString2();
   }
}