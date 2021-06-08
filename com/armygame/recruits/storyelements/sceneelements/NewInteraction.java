package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.storyelements.sceneelements.intentconnectors.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.utils.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class NewInteraction extends Interaction {

   private Hashtable receiveConnectors;
   private Hashtable receiveTypeValueConnectors;
   private Hashtable expressIntents;
   private Hashtable receiveIntents;
   private Hashtable receiveGoals;
   private Vector conditionActionVector;
   private ConditionAction requiredConditions;

   public NewInteraction(String name, String desc, Hashtable expInt, Hashtable recInt,
                         Hashtable recConn, Hashtable recTypeValConn, Hashtable recGoals,
                         ConditionAction reqConditions,
                         Vector condActVector) {
      super(name, desc);

      receiveConnectors = recConn;
      receiveTypeValueConnectors = recTypeValConn;
      receiveGoals = recGoals;
      expressIntents = expInt;
      receiveIntents = recInt;
      requiredConditions = reqConditions;
      conditionActionVector = condActVector;

      String role;
      String conn;
      Enumeration enum;
      enum = recInt.keys();
      while (enum.hasMoreElements()) {
         addReceiveIntentConnectorOfInterest((String)enum.nextElement());
      }
      enum = expInt.keys();
      while (enum.hasMoreElements()) {
         addExpressIntentConnectorOfInterest((String)enum.nextElement());
      }
      enum = recConn.keys();
      Enumeration enum1;
      while (enum.hasMoreElements()) {
         role = (String) enum.nextElement();
         enum1 = ((Hashtable)recConn.get(role)).keys();
         while (enum1.hasMoreElements()) {
            conn = (String) enum1.nextElement();
            addCharacterConnectorOfInterest(role, conn);
         }
      }
      enum = recTypeValConn.keys();
      while (enum.hasMoreElements()) {
         role = (String) enum.nextElement();
         enum1 = ((Hashtable)recTypeValConn.get(role)).keys();
         while (enum1.hasMoreElements()) {
            conn = (String) enum1.nextElement();
            addTypeValueConnectorOfInterest(role, conn);
         }
      }
   }

   public void ConnectorChanged(ConnectorChangeEvent e) {
      String conn = e.getConnectorName();
      String role = e.getConnectorRole();
      Hashtable connsOfInterest = (Hashtable)receiveConnectors.get(role);
      if (connsOfInterest != null) {
         if (connsOfInterest.containsKey(conn)) {
            ((MyBoolean)connsOfInterest.get(conn)).setValue(e.isConnectorExtended());
            updateStatus();
         }
      }
   }

   public void typeValueConnectorChanged(TypeValueConnectorChangeEvent e) {
      String conn = e.getConnectorName();
      String role = e.getConnectorRole();
      Hashtable connsOfInterest = (Hashtable)receiveTypeValueConnectors.get(role);
      if (connsOfInterest != null) {
         if (connsOfInterest.containsKey(conn)) {
            ((MyString)connsOfInterest.get(conn)).setValue(e.getConnectorValue());
            updateStatus();
         }
      }
   }

   public void goalChanged(GoalChangeEvent e) {
      String role = e.getRole();
      String goal = e.getName();
      Hashtable goalsOfInterest = (Hashtable)receiveGoals.get(role);
      if (goalsOfInterest != null) {
         if (goalsOfInterest.containsKey(goal)) {
            ((MyString)goalsOfInterest.get(goal)).setValue(e.getStatus());
            updateStatus();
         }
      }
   }

   public void IntentConnectorChanged(IntentConnectorChangeEvent e) {
      String conn = e.getSource().getName();
      if (receiveIntents.containsKey(conn)) {
         ((MyBoolean)receiveIntents.get(conn)).setValue(e.getSource().isExtended());
         updateStatus();
      }
   }

   public void updateStatus() {
      changeStatus(requiredConditions.execute(this));
   }

   public void executeInstance() {
      Enumeration enum = conditionActionVector.elements();
      boolean actionComplete = false;
      while (enum.hasMoreElements()) {
         if (!actionComplete) {
            actionComplete = ((ConditionAction)enum.nextElement()).execute(this);
         }
         else {
            break;
         }
      }
   }

}