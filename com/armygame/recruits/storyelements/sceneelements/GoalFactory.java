package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GoalFactory {

   private String name;
   private String code;
   private int duration;
   private int cost;
   private String help;
   private ConditionAction prerequisites;
   private ConditionAction requirements;
   private RecurringEvent recurringEvent;
   private GoalManager myManager;

   private Hashtable connectorState;
   private Hashtable typeValueConnectorState;
   private Hashtable goalStatus;
   private Hashtable observableValues;

   private Hashtable commands;
   private Vector input;

   private ConditionActionFactory condActionFactory;

   public GoalFactory(Hashtable connState, Hashtable typeValConnState, Hashtable goalStat, Hashtable obsValues, GoalManager me) {
      /* The GoalFactory uses the MyString, MyBoolean and MyDouble objects contained in the Hashtables.  These
         objects are embedded in the ConditionAction objects.  Thus, when the GoalManager receives notice
         that a connector, typeValConnector or goal has changed status, it updates the MyString, MyBoolean or MyDouble
         object.  This is automatically reflected in the ConditionAction object since the ConditionAction
         object holds a reference to the MyString or MyBoolean object in the GoalManager.  Once the change occurs,
         the GoalManager will cycle through the appropriate 'dependecy' vector and instruct the
         appropriate goals to reevaluate their status.
      */

      connectorState = connState;
      typeValueConnectorState = typeValConnState;
      goalStatus = goalStat;
      observableValues = obsValues;
      myManager = me;

      initialize();

      condActionFactory = new ConditionActionFactory(null, connectorState,
                              typeValueConnectorState, goalStatus, observableValues);

      commands = new Hashtable();
      commands.put(new String("<Name>"), new Integer(0));
      commands.put(new String("<Duration>"), new Integer(1));
      commands.put(new String("<Prerequisites>"), new Integer(2));
      commands.put(new String("<Cost>"), new Integer(3));
      commands.put(new String("<Requirements>"), new Integer(4));
      commands.put(new String("<RecurringEvent>"), new Integer(5));
      commands.put(new String("<Help>"), new Integer(6));
      commands.put(new String("<Code>"), new Integer(7));

      input = new Vector();
   }

   public Goal makeGoal(Vector in) {
      input = in;
      initialize();
      int i = 0;
      String line = new String();

      while ((line = readNextLine()) != null) {
         if ((line.charAt(0)) == '-') {
            i = 8;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 0: //name
               startName();
               break;
            case 1: //duration
               startDuration();
               break;
            case 2: //prerequisites
               startPrerequisites();
               break;
            case 3: //cost
               startCost();
               break;
            case 4: //requirements
               startRequirements();
               break;
            case 5: //recurringEvent
               startRecurringEvent();
               break;
            case 6: //help
               startHelp();
               break;
            case 7: //code
               startCode();
               break;
            case 8: //comment
               break;
         }
      }

      return new Goal(name, code, duration, prerequisites, requirements, recurringEvent, cost, help, myManager);
   }

   private void initialize() {
      prerequisites = null;
      code = "DEFAULT";
      requirements = null;
      recurringEvent = null;
      duration = 0;
      cost = 0;
      help = null;
   }

   private void startName() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Name>")) {
         name = new String(line);
         line = readNextLine();
      }
   }

   private void startDuration() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Duration>")) {
         duration = (new Integer(line)).intValue();
         line = readNextLine();
      }
   }

   private void startPrerequisites() {
      Vector in = new Vector();
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Prerequisites>")) {
         in.add(line);
         line = readNextLine();
      }
      in.add("<UpdateGoal>");
      in.add(name + ",selectable");
      in.add("</UpdateGoal>");
      prerequisites = condActionFactory.makeConditionAction(in);
   }

   private void startCost() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Cost>")) {
         cost = (new Integer(line)).intValue();
         line = readNextLine();
      }
   }

   private void startRequirements() {
      Vector in = new Vector();
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Requirements>")) {
         in.add(line);
         line = readNextLine();
      }
      in.add("<UpdateGoal>");
      in.add(name + ",achieved");
      in.add("</UpdateGoal>");
      requirements = condActionFactory.makeConditionAction(in);
   }

   private void startRecurringEvent() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Description>"), new Integer(0));
      tags.put(new String("<Frequency>"), new Integer(1));
      tags.put(new String("<MaxOccurances>"), new Integer(2));
      tags.put(new String("<RecurringAction>"), new Integer(3));

      String desc = null;
      String freq = null;
      String maxOccur = null;
      Vector recurringActions = new Vector();
      ConditionAction recurringAction = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</RecurringEvent>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: //<Description>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Description>")) {
                  desc = line;
                  line = readNextLine();
               }
               break;
            case 1: // <Frequency>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Frequency>")) {
                  freq = line;
                  line = readNextLine();
               }
               break;
            case 2: // <MaxOccurances>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</MaxOccurances>")) {
                  maxOccur = line;
                  line = readNextLine();
               }
               break;
            case 3: //<RecurringAction>
               Vector in = new Vector();
               line = readNextLine();
               while (!line.equalsIgnoreCase("</RecurringAction>")) {
                  in.add(line);
                  line = readNextLine();
               }
               recurringActions.add(condActionFactory.makeConditionAction(in));
               //recurringAction = condActionFactory.makeConditionAction(in);
            break;
         }
      }
      recurringEvent = new RecurringEvent(desc, (new Integer(freq)).intValue(),
                                                (new Integer(maxOccur)).intValue(),
                                                 recurringActions);
   }


   private void startHelp() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Help>")) {
         help = new String(line);
         line = readNextLine();
      }
   }

   private void startCode() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Code>")) {
         code = line;
         line = readNextLine();
      }
   }

   private String readNextLine() {
      if (input.size() > 0) {
         return (String) input.remove(0);
      }
      else {
         return null;
      }
   }
}