package com.armygame.recruits.storyelements.sceneelements;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class MyBoolean {

   private boolean myValue;

   public MyBoolean(boolean booleanValue) {
      myValue = booleanValue;
   }

   public MyBoolean(Boolean boolValue) {
      myValue = boolValue.booleanValue();
   }

   public boolean getValue() {
      return myValue;
   }

   public void setValue(boolean b) {
      myValue = b;
   }

   public boolean booleanValue() {
      return myValue;
   }
}