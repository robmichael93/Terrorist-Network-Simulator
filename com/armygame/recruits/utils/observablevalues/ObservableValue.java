package com.armygame.recruits.utils.observablevalues;

import java.util.*;
import javax.swing.event.*;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 2.0
 *
 * 2/8/02: min and max values are user settable and enforced.
 */

public class ObservableValue implements ValueChangeListener {

   private String name;
   private double value;
   private double lastValue;
   private double min_Value;
   private double max_Value;
   private Vector listeners;
   private Hashtable sourceValues;
   private double deltaValue;
   private double pctAffect;
   private String source;
   private IndependentValue tempValue;

   public ObservableValue(String n, double v, Vector sourceVals) {
      this(n, v, sourceVals, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
   }

   public ObservableValue(String n, double v, Vector sourceVals, double min, double max) {
      name = n;
      deltaValue = 0.0;
      pctAffect = 0.0;
      min_Value = min;
      max_Value = max;
      source = null;
      listeners = new Vector();
      sourceValues = new Hashtable();
      this.changeValue(v);

/**
 *  sourceVals will be a vector of object arrays.  The first element
 *  in the array will be a string which corresponds to one of the characters
 *  personality or trait values (these must be ObservableValues).  The second
 *  element will be a Double value that corresponds to the percent of affect on
 *  the overall value.
 */
      Enumeration enum = sourceVals.elements();
      while (enum.hasMoreElements()) {
         Object[] nextSource = (Object[]) enum.nextElement();
         String valueName = (String) nextSource[0];
         double pctAff = ((Double) nextSource[1]).doubleValue();
         addIndependentValue(valueName, pctAff);
      }

   }

   public ObservableValue(String n) {
      this(n, 0, new Vector());
   }

   public ObservableValue() {
      this("");
   }

   public boolean addValueChangeListener(ValueChangeListener v) {
      if (listeners.contains(v)) {
         return false;
      }
      listeners.add(v);
      return true;
   }

   public boolean removeValueChangeListener(ValueChangeListener v) {
      return listeners.remove(v);
   }

   public void changeValue(double v) {
      lastValue = value;
      value = v;
      if (value > max_Value) {
         value = max_Value;
      }
      else {
         if (value < min_Value) {
            value = min_Value;
         }
      }
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         ValueChangeListener vcl = (ValueChangeListener) enum.nextElement();
         vcl.ValueChanged(new ValueChangeEvent(this));
      }
   }

   public void incrementValue(double delta) {
      changeValue(value + delta);
   }

   public void ValueChanged(ValueChangeEvent e){
      source = e.getSource().getName();
      tempValue = (IndependentValue) sourceValues.get(source);
      if (tempValue != null) {
         tempValue.updateValue(e.getSource().getValue());
         deltaValue = tempValue.getDelta();
         pctAffect = tempValue.getPctAffect();
         incrementValue(deltaValue * pctAffect);
      }
   }


   public double getValue() {
      return value;
   }

   public double getLastValue() {
      return lastValue;
   }

   public void setValue(double v) {
      changeValue(v);
   }

   public String getName() {
      return name.toString();
   }

   public void addIndependentValue(String valueName, double percentAffect) {
      IndependentValue indVal = new IndependentValue(new String(valueName), percentAffect);
      if (!sourceValues.containsKey(valueName)) {
         sourceValues.put(valueName, indVal);
      }
      else {
         // Throw an exception?
         System.out.println("*  Error * ObservableValue - duplicate IndependentValue name");
      }
   }

   public boolean hasSourceValues() {
      return (sourceValues.size() > 0);
   }

   public Enumeration getSourceValueNames() {
      return sourceValues.keys();
   }



   private class IndependentValue {
      private String name;
      private double oldValue;
      private double newValue;
      private double pctAffect;
      private double delta;

      public IndependentValue(String valueName, double pctAff) {
         name = valueName;
         oldValue = 0.0;
         newValue = 0.0;
         pctAffect = pctAff;
         delta = 0.0;
      }

      public void updateValue(double newVal) {
         oldValue = newValue;
         newValue = newVal;
         delta = newValue - oldValue;
      }

      public double getDelta() {
         return delta;
      }

      public double getPctAffect() {
         return pctAffect;
      }
   }

}