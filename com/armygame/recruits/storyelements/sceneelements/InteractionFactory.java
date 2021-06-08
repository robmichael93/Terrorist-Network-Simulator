package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 *
 * Read info from a file and instantiate a hashtable of scene references.
 */

public class InteractionFactory {
   private Hashtable commands;
   private String name;
   private String description;
   private Hashtable expressIntents;
   private Hashtable receiveIntents;
   private Hashtable receiveConnectors;
   private Hashtable receiveTypeValueConnectors;
   private Hashtable receiveGoals;
   private Vector conditionActions;
   private Vector input;
   private ConditionActionFactory conditionActionFactory;
   private ConditionAction requiredConditions;

   public InteractionFactory() {
      expressIntents = new Hashtable();
      receiveIntents = new Hashtable();
      receiveConnectors = new Hashtable();
      receiveTypeValueConnectors = new Hashtable();
      receiveGoals = new Hashtable();
      conditionActions = new Vector();
      description = new String();
      requiredConditions = null;
      conditionActionFactory = null;
      input = new Vector();

      commands = new Hashtable();
      commands.put(new String("<Name>"), new Integer(0));
      commands.put(new String("<Description>"), new Integer(1));
      commands.put(new String("<ExpressIntent>"), new Integer(2));
      commands.put(new String("<ReceiveIntent>"), new Integer(3));
      commands.put(new String("<ReceiveConnector>"), new Integer(4));
      commands.put(new String("<ReceiveTypeValueConnector>"), new Integer(5));
      commands.put(new String("<ReceiveGoal>"), new Integer(6));
      commands.put(new String("<ConditionAction>"), new Integer(7));
      commands.put(new String("<RequiredConditions>"), new Integer(8));
   }

   public NewInteraction makeInteraction(Vector in) {
      input = in;
      int i = 0;
      String line = new String();
      startInteraction();
      while ((line = readNextLine()) != null) {
         if ((line.charAt(0)) == '-') {
            i = 9;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 0: // Name
               startName();
               break;
            case 1: // Description
               startDescription();
               break;
            case 2: // ExpressIntent
               expressIntent();
               break;
            case 3: // ReceiveIntent
               receiveIntent();
               break;
            case 4: // ReceiveConnector
               receiveConnector();
               break;
            case 5: // ReceiveTypeValueConnector
               receiveTypeValueConnector();
               break;
            case 6: // ReceiveGoal
               receiveGoal();
               break;
            case 7: // ConditionAction
               createConditionAction();
               break;
            case 8: // RequiredConditions
               createRequiredConditions();
               break;
            case 9: // comment
               break;
         }
      }
      NewInteraction x = new NewInteraction(name, description, expressIntents,
                                            receiveIntents, receiveConnectors,
                                            receiveTypeValueConnectors, receiveGoals,
                                            requiredConditions,
                                            conditionActions);
      return x;
   } /* makeInteraction */

   private void startInteraction() {
      name = "none";
      description = "none";
      expressIntents = new Hashtable();
      receiveIntents = new Hashtable();
      receiveConnectors = new Hashtable();
      receiveTypeValueConnectors = new Hashtable();
      receiveGoals = new Hashtable();
      conditionActions = new Vector();
      description = new String();
      requiredConditions = null;
      conditionActionFactory = new ConditionActionFactory(receiveIntents,
                                                          receiveConnectors,
                                                          receiveTypeValueConnectors,
                                                          receiveGoals);
   } /* startInteraction */


   private void startName() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Name>")) {
         name = line;
         line = readNextLine();
      }
   } /* startName */

   private void startDescription() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Description>")) {
         description = line;
         line = readNextLine();
      }
   } /* startDescription */

   private void expressIntent() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</ExpressIntent>")) {
         expressIntents.put(line, new MyBoolean(false));
         line = readNextLine();
      }
   } /* expressIntent */

   private void receiveIntent() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</ReceiveIntent>")) {
         receiveIntents.put(line, new MyBoolean(false));
         line = readNextLine();
      }
   } /* receiveIntent */

   private void receiveConnector() {
      String line = readNextLine();
      String role = null;
      String connector = null;
      while (!line.equalsIgnoreCase("</ReceiveConnector>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         role = new String(tokens.nextToken());
         connector = new String(tokens.nextToken());
         if (!receiveConnectors.containsKey(role)) {
            receiveConnectors.put(role, new Hashtable());
         }
         ((Hashtable) receiveConnectors.get(role)).put(connector, new MyBoolean(false));
         line = readNextLine();
      }
   } /* receiveConnector */

   private void receiveTypeValueConnector() {
      String line = readNextLine();
      String role = null;
      String typeValueConnector = null;
      while (!line.equalsIgnoreCase("</ReceiveTypeValueConnector>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         role = new String(tokens.nextToken());
         typeValueConnector = new String(tokens.nextToken());
         if (!receiveTypeValueConnectors.containsKey(role)) {
            receiveTypeValueConnectors.put(role, new Hashtable());
         }
         ((Hashtable) receiveTypeValueConnectors.get(role)).put(typeValueConnector, new MyString("empty"));
         line = readNextLine();
      }
   } /* receiveTypeValueConnector */

   private void receiveGoal() {
      String line = readNextLine();
      String role = null;
      String goal = null;
      while (!line.equalsIgnoreCase("</ReceiveGoal>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         role = new String(tokens.nextToken());
         goal = new String(tokens.nextToken());
         if (!receiveGoals.containsKey(role)) {
            receiveGoals.put(role, new Hashtable());
         }
         ((Hashtable) receiveGoals.get(role)).put(goal, new MyString("empty"));
         line = readNextLine();
      }
   } /* receiveGoal */

   private void createConditionAction() {
      Vector input = new Vector();
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</ConditionAction>")) {
         input.add(line);
         line = readNextLine();
      }
      conditionActions.add(conditionActionFactory.makeConditionAction(input));
   } /* createConditionAction */

   private void createRequiredConditions() {
      Vector input = new Vector();
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</RequiredConditions>")) {
         input.add(line);
         line = readNextLine();
      }
      requiredConditions = conditionActionFactory.makeConditionAction(input);
   } /* createRequiredConditions */

   private String readNextLine() {
      if (input.size() > 0) {
         return (String)input.remove(0);
      }
      else {
         return null;
      }
   } /* readNextLine */

} /* InteractionFactory */
