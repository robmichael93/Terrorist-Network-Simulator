package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ConditionAction {

   //private Interaction myParentInteraction;
   private Vector conditions;
   private Vector actions;
   private Vector intentConnectorDependencies;
   private Vector connectorDependencies;
   private Vector typeValueConnectorDependencies;
   private Vector goalDependencies;
   private Vector obsValueDependencies;

   public ConditionAction() {
      conditions = new Vector();
      actions = new Vector();
   }

   public void addCondition(Condition condition) {
      conditions.add(condition);
   }

   public void addAction(Action action) {
      actions.add(action);
   }

   public boolean allConditionsMet() {
      /**
       * check all conditions, if all are true then return true, else false
       */
      boolean status = true;
      boolean condMet = false;
      Enumeration enum = conditions.elements();
      while (enum.hasMoreElements()) {
         condMet = ((Condition)enum.nextElement()).conditionMet();
         status = status & condMet;
      }
      return status;
   } /* isTrue */

   public boolean execute(ActionHandler actionHandler) {
      /**
       * check isTrue, if true, then execute all actions and return true,
       * else do nothing and return false.
       */
      if (allConditionsMet()) {
         Enumeration enum = actions.elements();
         while (enum.hasMoreElements()) {
            ((Action)enum.nextElement()).execute(actionHandler);
         }
         return true;
      }
      else {
         return false;
      }
   } /* execute */

   public boolean isACondition(String connectorName) {
      System.out.println("** isACondition(" + connectorName + ") not implemented **");
      return true;
   }

   public boolean isACondition(String connectorType, String connectorValue) {
      System.out.println("** isACondition(" + connectorType + ", " + connectorValue + ") not implemented **");
      return true;
   }

   public Vector getUpdateResourceActions() {
      Vector resourceActions = new Vector();
      Enumeration enum = actions.elements();
      while (enum.hasMoreElements()) {
         Object o = enum.nextElement();
         if (o instanceof ConditionActionFactory.UpdateResourceAction) {
            Object[] temp = new Object[2];
            temp[0] = ((ConditionActionFactory.UpdateResourceAction)o).getResource();
            temp[1] = new Double(((ConditionActionFactory.UpdateResourceAction)o).getDelta());
            resourceActions.add(temp);
         }
      }
      if (resourceActions.size() > 0) {
         return resourceActions;
      }
      else {
         return null;
      }
   }

   public void setIntentConnectorDependencies(Vector iCD) {
      intentConnectorDependencies = iCD;
   }

   public void setConnectorDependencies (Vector cD) {
      connectorDependencies = cD;
   }

   public void setTypeValueConnectorDependencies (Vector tVCD) {
      typeValueConnectorDependencies = tVCD;
   }

   public void setGoalDependencies (Vector gD) {
      goalDependencies = gD;
   }

   public void setObsValueDependencies (Vector ovD) {
      obsValueDependencies = ovD;
   }

   public Vector getIntentConnectorDependencies() {
      return intentConnectorDependencies;
   }

   public Vector getConnectorDependencies () {
      return connectorDependencies;
   }

   public Vector getTypeValueConnectorDependencies () {
      return typeValueConnectorDependencies;
   }

   public Vector getGoalDependencies () {
      return goalDependencies;
   }

   public Vector getObsValueDependencies() {
      return obsValueDependencies;
   }

   public String[] conditionsToString() {
      int size = conditions.size();
      String[] conditionsAsStrings = new String[size];
      int i = 0;
      Enumeration enum = conditions.elements();
      while (enum.hasMoreElements()) {
         conditionsAsStrings[i] = ((Condition)enum.nextElement()).toString();
         i++;
      }
      return conditionsAsStrings;
   }

   public String[] actionsToString() {
      int size = actions.size();
      String[] actionsAsStrings = new String[size];
      int i = 0;
      Enumeration enum = actions.elements();
      while (enum.hasMoreElements()) {
         actionsAsStrings[i] = ((Action)enum.nextElement()).toString();
         i++;
      }
      return actionsAsStrings;
   }

   public String[] goalHelpActionsToString() {
      int size = actions.size();
      Action a;
      String[] tempStringArray = new String[size];

      int i = 0;
      Enumeration enum = actions.elements();
      while (enum.hasMoreElements()) {
         a = (Action)enum.nextElement();
         if (!(a instanceof ConditionActionFactory.UpdateGoalAction)) {
            tempStringArray[i] = a.toString();
            i++;
         }
      }
      if (i == actions.size()) {
         return tempStringArray;
      }
      else {
         String[] actionsAsStrings = new String[i];
         i = 0;
         for (int j = 0; j < tempStringArray.length; j++) {
            if (tempStringArray[j] != null) {
               actionsAsStrings[i] = tempStringArray[j];
               i++;
            }
         }
         return actionsAsStrings;
      }
   }

}