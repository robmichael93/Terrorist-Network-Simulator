package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class StringVector {

   private Vector strings;

   public StringVector() {
      strings = new Vector();
   }

   public void addString(String s) {
      strings.add(s);
   }

   public boolean removeString(String s) {
      Enumeration enum = strings.elements();
      while (enum.hasMoreElements()) {
         String str = (String) enum.nextElement();
         if (str.equalsIgnoreCase(s)) {
            strings.remove(s);
            return true;
         }
      }
      return false;
   }

   public Vector getStringVector() {
      return strings;
   }
}