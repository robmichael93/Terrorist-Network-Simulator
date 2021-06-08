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

public class StringPairVector {

   private Vector stringPairs;

   public StringPairVector() {
      stringPairs = new Vector();
   }

//   public void addStringPair(String string1, String string2) {
//      StringPair sp = new StringPair(string1, string2);
//      stringPairs.add(sp);
//   }

   public void addStringPair(StringPair sp) {
      stringPairs.add(sp);
   }

   public Vector getStringPairVector() {
      return stringPairs;
   }

   public void removeStringPair(StringPair sp) {
      Enumeration enum = stringPairs.elements();
      while (enum.hasMoreElements()) {
         StringPair spCurrent = (StringPair)enum.nextElement();
         if ( (spCurrent.getString1()).equalsIgnoreCase(sp.getString1()) &&
              (spCurrent.getString2()).equalsIgnoreCase(sp.getString2()) ) {
            stringPairs.remove(spCurrent);
            return;
         }
      }
   }
}