package com.armygame.recruits.storyelements.sceneelements;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class StringPair {

   private String string1;
   private String string2;

   public StringPair(String s1, String s2) {
      string1 = s1;
      string2 = s2;
   }

   public String getString1() {
      return string1;
   }

   public String getString2() {
      return string2;
   }
}