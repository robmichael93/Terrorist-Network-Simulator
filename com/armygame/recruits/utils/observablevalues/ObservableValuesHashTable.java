package com.armygame.recruits.utils.observablevalues;

import java.util.*;

/**
 * Title:        Connectors
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Naval Postgraduate School
 * @author Brian Osborn
 * @version 1.0
 */

public class ObservableValuesHashTable {

   private Hashtable observableValues;

   public ObservableValuesHashTable() {
      observableValues = new Hashtable();
   }

   public void addObservableValue(ObservableValue c) {
      String name = c.getName();
      observableValues.put(name, c);
   }

   public boolean removeObservableValue(ObservableValue c) {
      if (observableValues.containsValue(c)) {
         observableValues.remove(c);
         return true;
      }
      return false;
   }

   public ObservableValue getObservableValue(String name) {
      if (observableValues.containsKey(name)) {
         return (ObservableValue) observableValues.get(name);
      }
      return null;
   }

   public Hashtable getObservableValuesTable() {
      return observableValues;
   }

   public boolean containsValue(String val) {
      return observableValues.containsKey(val);
   }

   public void setHashTable(Hashtable obsVals) {
      observableValues = obsVals;
   }

   public void broadcastStatus() {
      ObservableValue obsVal = null;
      Enumeration enum = observableValues.elements();
      while (enum.hasMoreElements()) {
         obsVal = (ObservableValue)enum.nextElement();
         obsVal.changeValue(obsVal.getValue());
      }
   }

   public void addValueChangeListenerAllValues(ValueChangeListener vcL) {
      Enumeration enum = observableValues.elements();
      ObservableValue obsVal;
      while (enum.hasMoreElements()) {
         obsVal = (ObservableValue) enum.nextElement();
         obsVal.addValueChangeListener(vcL);
      }
   }

   public void removeValueChangeListenerAllValues(ValueChangeListener vcL) {
      Enumeration enum = observableValues.elements();
      ObservableValue obsVal;
      while (enum.hasMoreElements()) {
         obsVal = (ObservableValue) enum.nextElement();
         obsVal.removeValueChangeListener(vcL);
      }
   }

}
