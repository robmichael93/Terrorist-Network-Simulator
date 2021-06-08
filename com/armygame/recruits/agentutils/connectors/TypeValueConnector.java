package com.armygame.recruits.agentutils.connectors;


import java.util.*;
import com.armygame.recruits.utils.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 * This listens to noone.  In essence the observable values are built into
 * the connector.  The way to change the value of the connector is through the
 * CharacterStateSystem (analogous to the MainCharacterValueSystem).  The
 * CSS listens to the scene to get requests to modify the characters
 * TypeValueConnectors.  The CCS maintains a hashtable of TVConnectors and
 * modifies them directly.
 * Right now, there are no elements in the system that listen to (implement
 * the TypeValueConnectorChangeListener inteface).  Any changes to the
 * TVConnector are reflected in the associated StateVector.  This vector is
 * in turn used to search for the appropriate story object.
 */

public class TypeValueConnector {

   private String type;
   private String role;
   private int id;
   private Vector listeners;
   private Hashtable valueSet; /* value, Vector */
   private String oldValue;
   private String value;

   public TypeValueConnector(String t, Vector initValSet, String initValue) {
      type = t;
      id = 0;
      listeners = new Vector();
      valueSet = new Hashtable();
      initializeValueSet(initValSet);
      value = initValue;
      oldValue = null;
      role = null;
      changeValue(value);
   }

   public TypeValueConnector(String t, Vector initValSet) {
      this(t, initValSet, null);
   }

   private void initializeValueSet(Vector initValSet) {
      /**
       * The keys of the hashtable are the connector values.  Associated
       * with each value (key) is a vector that holds the stateVectorRanges
       * that are associated with the value.  When the connector changes
       * value, the associated stateVectorRanges will also be changed.
       */
      Enumeration enum = initValSet.elements();
      while (enum.hasMoreElements()) {
         valueSet.put((String)enum.nextElement(), new Vector());
      }
   }

   public boolean isExtended() {
      return (value != null);
   }

   public boolean isRetracted() {
      return (value == null);
   }

   public void extendConnector() {
      changeValue(value);
   }

   public void retractConnector() {
      changeValue(null);
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return type;
   }

   public String getType() {
      return type;
   }

   public String getValue() {
      return value;
   }

   public Vector getValueSet() {
      Enumeration enum = valueSet.keys();
      Vector possibleValues = new Vector();
      while (enum.hasMoreElements()) {
         possibleValues.add((String)enum.nextElement());
      }
      return possibleValues;
   }

   public boolean addTypeValueConnectorChangeListener(TypeValueConnectorChangeListener v) {
      if (listeners.contains(v)) {
         return false;
      }
      listeners.add(v);
      return true;
   }

   public boolean removeTypeValueConnectorChangeListener(TypeValueConnectorChangeListener v) {
      return listeners.remove(v);
   }

   public void changeValue(String newValue) {
      if (newValue == null) {
         oldValue = value;
         value = newValue;
         Vector tempVector;
         Enumeration enum = valueSet.elements();
         while (enum.hasMoreElements()) {
            tempVector = (Vector) enum.nextElement();
            Enumeration enum1 = tempVector.elements();
            while (enum1.hasMoreElements()) {
               ((StateVectorRange) enum1.nextElement()).Clear();
                  //System.out.println("Clear TVConnector: " + type + " Clearing all values.");
            }
         }
         enum = listeners.elements();
         while (enum.hasMoreElements()) {
            TypeValueConnectorChangeListener ccl = (TypeValueConnectorChangeListener) enum.nextElement();
            ccl.typeValueConnectorChanged(new TypeValueConnectorChangeEvent(this));
         }
      }
      else {
         if (!valueSet.containsKey(newValue)) {
            //throw a invalid connector value exception
            System.out.println("Unable to set value " + newValue + " for connector "
                               + type + ".");
         }
         else {
            oldValue = value;
            value = newValue;
            Vector tempVector;

            if (oldValue != null) {
               tempVector = (Vector) valueSet.get(oldValue);
               //tempVector holds the stateVectorRanges that are linked to this
               //value.  There may be more than one because more than one
               //stateVector may be interested in the same typeValueConnector
               Enumeration enum = tempVector.elements();
               while (enum.hasMoreElements()) {
                  //System.out.println("Clear TVConnector: " + type + " value: " + oldValue);
                  ((StateVectorRange) enum.nextElement()).Clear();
               }
            }
            tempVector = (Vector) valueSet.get(value);
            Enumeration enum = tempVector.elements();
            while (enum.hasMoreElements()) {
               ((StateVectorRange) enum.nextElement()).Set();
               //System.out.println("Set TVConnector: " + type + " value: " + value);
            }
            enum = listeners.elements();
            while (enum.hasMoreElements()) {
               TypeValueConnectorChangeListener ccl = (TypeValueConnectorChangeListener) enum.nextElement();
               ccl.typeValueConnectorChanged(new TypeValueConnectorChangeEvent(this));
            }
         }
      }
   }

   public void reportStatus() {
      changeValue(value);
   }

   public String getRole() {
      return role;
   }

   public void setRole(String r) {
      role = r;
   }

   public boolean registerStateVector(StateVectorManager sVM) {
      // the name of the connector value must match the name of the range name
      // from the StateVectorManager
      boolean fullyRegistered = true;
      String key;
      Vector tempVector;
      StateVectorRange sVR;
      Enumeration enum = valueSet.keys();
      while (enum.hasMoreElements()) {
         key = (String) enum.nextElement();
         sVR = sVM.getRange(key);
         if (sVR != null) {
            tempVector = (Vector) valueSet.get(key);
            if (!tempVector.contains(sVR)) {
               tempVector.add(sVR);
               valueSet.remove(key);
               valueSet.put(key, tempVector);
            }
         }
         else {
            fullyRegistered = false;
         }
      }
      return fullyRegistered;
   }

   public boolean registerGoalWithStateVector(StateVectorManager sVM) {
      // the name of the connector "value + goal" name must match the name of the range name
      // from the StateVectorManager
      boolean fullyRegistered = true;
      String key;
      Vector tempVector;
      StateVectorRange sVR;
      Enumeration enum = valueSet.keys();
      while (enum.hasMoreElements()) {
         key = (String) enum.nextElement();
         /* for goals, the range is the 'type + : + value' i.e. 'GoalA:selected'*/
         sVR = sVM.getRange(type + ":" + key);
         if (sVR != null) {
            tempVector = (Vector) valueSet.get(key);
            if (!tempVector.contains(sVR)) {
               tempVector.add(sVR);
               valueSet.remove(key);
               valueSet.put(key, tempVector);
            }
         }
         else {
            fullyRegistered = false;
         }
      }
      return fullyRegistered;
   }

   public void unregisterStateVector(StateVectorManager sVM) {
      String key;
      Vector tempVector;
      Enumeration enum = valueSet.keys();
      while (enum.hasMoreElements()) {
         key = (String) enum.nextElement();
         tempVector = (Vector) valueSet.get(key);
         tempVector.remove(sVM.getRange(key));
         valueSet.remove(key);
         valueSet.put(key, tempVector);
      }
   }

}