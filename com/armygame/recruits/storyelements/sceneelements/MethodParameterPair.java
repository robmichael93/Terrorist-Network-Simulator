package com.armygame.recruits.storyelements.sceneelements;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class MethodParameterPair extends StringPair {

   public MethodParameterPair(String method, String parameter) {
      super(method, parameter);
   }

   public int getMethod() {
      return new Integer(getString1()).intValue();
   }

   public String getParameter() {
      return getString2();
   }
}