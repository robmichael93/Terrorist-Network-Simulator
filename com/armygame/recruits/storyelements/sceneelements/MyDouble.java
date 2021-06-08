package com.armygame.recruits.storyelements.sceneelements;

import java.io.Serializable;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class MyDouble implements Serializable {

   private double myValue;
   private static double DELTA = .0001;

   public MyDouble(Double doubleValue) {
      myValue = doubleValue.doubleValue();
   }

   public MyDouble(double doubleValue) {
      myValue = doubleValue;
   }

   public MyDouble(String doubleValue) {
      myValue = (new Double(doubleValue)).doubleValue();
   }

   public double getValue() {
      return myValue;
   }

   public void setValue(double b) {
      myValue = b;
   }

   public Double doubleValue() {
      return new Double(myValue);
   }

   public boolean isGreaterThan(double d) {
      return (myValue > d);
   }

   public boolean isGreaterThanOrEqual(double d) {
      return (myValue + DELTA > d);
   }

   public boolean isLessThan(double d) {
      return (myValue < d);
   }

   public boolean isLessThanOrEqual(double d) {
      return (myValue - DELTA < d);
   }

   public boolean isBetween(double low, double high) {
      return ((myValue + DELTA > low) & (myValue - DELTA < high));
   }
}