package com.armygame.recruits.agentutils.connectors;

import java.util.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.utils.*;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 2.0
 * This version add behavior to allow connectors to work with state vectors.
 */

public class Connector implements ValueChangeListener {

   private String name;
   private String role;
   protected boolean status;
   protected Vector listeners;
   private Vector stateVectorRanges;
   private double lowerBound;
   private double upperBound;
   private double currentValue;
   private String sourceValue;

   public Connector(String n, double lowBound, double upBound, String source) {
      name = n;
      lowerBound = lowBound;
      upperBound = upBound;
      sourceValue = source;
      listeners = new Vector();
      stateVectorRanges = new Vector();
      changeStatus(false);
      role = null;
   }

   public void ValueChanged(ValueChangeEvent e) {
      if (((String)e.getSource().getName()).equalsIgnoreCase(sourceValue)) {
         currentValue = e.getSource().getValue();
         if ((currentValue >= lowerBound) && (currentValue < upperBound) && (isRetracted())) {
            extendConnector();
         }
         else {
            if (((currentValue < lowerBound) || (currentValue >= upperBound)) && (isExtended())) {
               retractConnector();
            }
         }
         return;
      }
   }

   public boolean isExtended() {
      return status;
   }

   public boolean isRetracted() {
      return !status;
   }

   public double getValue() {
      return currentValue;
   }

   public void setStatus (boolean s) {
      status = s;
   }

   public void extendConnector() {
      changeStatus(true);
   }

   public void retractConnector() {
      changeStatus(false);
   }

   public String getName() {
      return name;
   }

   public boolean addConnectorChangeListener(ConnectorChangeListener v) {
      if (listeners.contains(v)) {
         return false;
      }
      listeners.add(v);
      return true;
   }

   public boolean removeConnectorChangeListener(ConnectorChangeListener v) {
      return listeners.remove(v);
   }

   public void changeStatus(boolean s) {
      status = s;

      // notify listeners of status change
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         ConnectorChangeListener ccl = (ConnectorChangeListener) enum.nextElement();
         ccl.ConnectorChanged(new ConnectorChangeEvent(this));
      }

      // change the status of the associated state vectors
      enum = stateVectorRanges.elements();
      while (enum.hasMoreElements()) {
         if (status) {
//      System.out.println("Change Status for Connector (set): " + getName());
            ((StateVectorRange)enum.nextElement()).Set();
         }
         else {
//      System.out.println("Change Status for Connector (clear): " + getName());
            ((StateVectorRange)enum.nextElement()).Clear();
         }
      }
   }

   public void reportStatus() {
      changeStatus(status);
   }

   public String getRole() {
      return role;
   }

   public void setRole(String r) {
      role = r;
   }

   public boolean registerStateVector(StateVectorManager sVM) {
      // the name of the connector must match the name of the range name
      // from the StateVectorManager
      StateVectorRange sVR = sVM.getRange(name);
      if (sVR != null) {
         addStateVectorRange(sVR);
         return true;
      }
      else {
         return false;
      }
   }

   public boolean unregisterStateVector(StateVectorManager sVM) {
      StateVectorRange sVR = sVM.getRange(name);
      if (sVR != null) {
         removeStateVectorRange(sVR);
         return true;
      }
      else {
         return false;
      }
   }

   private void addStateVectorRange(StateVectorRange sVR) {
      if (! stateVectorRanges.contains(sVR)) {
         stateVectorRanges.add(sVR);
      }
   }

   private void removeStateVectorRange(StateVectorRange sVR) {
      stateVectorRanges.remove(sVR);
   }
}