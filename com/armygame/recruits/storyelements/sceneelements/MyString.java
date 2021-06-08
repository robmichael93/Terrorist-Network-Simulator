package com.armygame.recruits.storyelements.sceneelements;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class MyString {

   String myString;

   public MyString(String s) {
      myString = s;
   }

   public String getValue() {
      return new String(myString);
   }

   public void setValue(String newString) {
      myString = newString;
   }

   public boolean equalsIgnoreCase(String s) {
      if (myString != null) {
         if (s != null) {
            return myString.equalsIgnoreCase(s);
         }
         return false;
      }
      else {
         if (s == null) {
            return true;
         }
         else {
            return false;
         }
      }
   }

   public boolean equalsIgnoreCase(MyString s) {
      if (myString != null) {
         if (s != null) {
            return myString.equalsIgnoreCase(s.getValue());
         }
         return false;
      }
      else {
         if (s == null) {
            return true;
         }
         else {
            return false;
         }
      }
   }

}