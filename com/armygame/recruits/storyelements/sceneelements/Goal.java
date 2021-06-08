package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.utils.*;
import java.util.*;
import java.io.Serializable;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p> MOVES Institute, Army Game Project
 * @author Brian Osborn
 * @version 1.0
 *
 * Once a goal has been acheived, it can not be 'unachieved'.
 */

public class Goal implements ActionHandler, Serializable {

   private String name;
   private String code;
   private transient TypeValueConnector status;
   private transient TypeValueConnector phase;
   private transient ConditionAction prerequisites;
   private transient ConditionAction requirements;
   private transient RecurringEvent recurringEvent;
   public String recurringFinancialEventName;
   public int recurringFinancialEventAmt;
   public int recurringFinancialEventMaxPayments;
   public int recurringFinancialEventPaymentsComplete;
   public int recurringFinancialEventFreq;

   private int duration;
   private int focus;
   private int cost;
   private int accumulatedCostUnits;
   private double timeOfLastUpdate;
   private boolean reevaluateInProgress;
   private transient Vector resourceAndValueProductionSources;
   private String help;
   private transient GoalManager myManager;

   private final String MY_CHARACTER = "myCharacter";
   private double measure; /* This indicates how well the goal is being satisfied.*/
                           /* Low value means not critical, high means critical   */
                           /* After each scene is played, measures should be recomputed. */
   private boolean firstPlay;

   public Goal(String goalName, String code, int durat, ConditionAction preReqs,
               ConditionAction reqs, RecurringEvent recurring, int goalCost, String goalHelp, GoalManager me) {
      name = goalName;
      this.code = code;
      duration = durat;
      prerequisites = preReqs;
      requirements = reqs;
      recurringEvent = recurring;
      cost = goalCost;
      help = goalHelp;
      myManager = me;
      status = makeGoalTypeValueConnector();
      phase = makeGoalPhaseTypeValueConnector();
      timeOfLastUpdate = 0;
      focus = 0;
      accumulatedCostUnits = 0;
      firstPlay = true;

      resourceAndValueProductionSources = new Vector();
      Vector temp = requirements.getObsValueDependencies();
      Object[] roleSource;
      String source;
      String role;
      Enumeration enum = temp.elements();
      while (enum.hasMoreElements()) {
         roleSource = (Object[])enum.nextElement();
         role = (String)roleSource[0];
         source = (String)roleSource[1];
         resourceAndValueProductionSources.add( ((Hashtable)myManager.getObservableValues().get(role)).get(source) );
      }
   }

   public void reevaluateStatus() {
      if (reevaluateInProgress) {
         return;
      }
      else {
         reevaluateInProgress = true;
      }
      boolean conditionsMet = false;
      if (isNotSelectable()) {
         if (prerequisites == null) {
            updateGoal("selectable");
//            System.out.println("Update Goal Status (" + name + ") - " + status.getValue());
            conditionsMet = true;
         }
         else {
            conditionsMet = prerequisites.execute(this);
         }
/* Progress can not be made to 'achieving' a goal until it is selected */
//         if (conditionsMet) {
//            System.out.println("Update Goal Status (" + name + ") - " + status.getValue());
//            if (costLevelAchieved() && requirements.execute(this)) {
//               System.out.println("Update Goal Status (" + name + ") - " + status.getValue());
//            }
//         }
      }
      else {
         if (isSelectable()) {
            if (prerequisites == null) {
               conditionsMet = true;
            }
            else {
               conditionsMet = prerequisites.allConditionsMet();
            }
            if (conditionsMet) {
/* Progress can not be made to 'achieving' a goal until it is selected */
//               if (costLevelAchieved() && requirements.execute(this)) {
//                  System.out.println("Update Goal Status (" + name + ") - " + status.getValue());
//               }
            }
            else { //Conditions no longer met for the goal to be 'selectable'
               updateGoal("notSelectable");
//               System.out.println("Update Goal Status (" + name + ") - " + status.getValue());
            }
         }
         else {
            if (isSelected()) {
               if (costLevelAchieved() && requirements.execute(this)) {
                  if (recurringEvent != null) {
                     recurringEvent.initialize((int)com.armygame.recruits.StoryEngine.instance().clock().getTime());
                  }
                  double time = com.armygame.recruits.StoryEngine.instance().clock().getTime();
                  String message = name + " goal achieved.";
                  AlertMessage am = new AlertMessage(AlertMessage.MESSAGE_ALERT, AlertMessage.MEDIUM_PRIORITY, message);
                  com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
//                  System.out.println("Update Goal Status (" + name + ") - " + status.getValue() + " Clock is " +
//                  com.armygame.recruits.StoryEngine.instance().clock().getTime());
               }
            }
         }
      }
      reevaluateInProgress = false;
   }

   public void updateValue(String role, String valueName, double delta) {
      /* The Goal Manager can ignore the 'role'. */
      myManager.updateValue(valueName, delta);
   }

   public void updateResource(String role, String resourceName, double delta) {
      /* The Goal Manager can ignore the 'role'. */
      myManager.updateResource(resourceName, delta);
   }

   public void updateValuePoints(String role, String valuePts, double delta) {
      /* The Goal Manager can ignore the 'role'. */
      myManager.updateValuePoints(valuePts, delta);
   }

   public void updateGoalPoints(String role, String goalPts, double delta) {
      /* The Goal Manager can ignore the 'role'. */
      myManager.updateGoalPoints(goalPts, delta);
   }

   public void updateTypeValueConnector(String role, String type, String newValue) {
      /* The Goal Manager can ignore the 'role'. */
      myManager.updateTypeValueConnector(type, newValue);
   }

   public void updateGoal(String role, String name, String newStatus) {
      /* This method is called from the ConditionAction object 'prerequisites' or
         'requirements'.
         It can also be called from the GoalManager in response to an
         Interaction directly changing the status of a goal.                  */

      status.changeValue(newStatus); /* State Vector notified by default */
      updateMeasure();

      /* Call to the GoalManagers updateGoal method */
      myManager.goalChanged(this);

   }

   public void obtainPossession(String possessionName) {
      myManager.obtainPossession(possessionName, this);
   }

   public void cueChooser(String role) {
      myManager.cueChooser();
   }

   public void displayQuip(String quipName) {
      myManager.displayQuip(quipName);
   }

   public void selectGoal() {
      updateGoal("selected");
   }

   public void deselectGoal() {
      updateGoal("notSelectable");
   }

   public void updateGoal(String newStatus) {
      updateGoal(MY_CHARACTER, name, newStatus);
      //System.out.println("Update goal status (" + name + ") - " + newStatus);
   }

   private void updateMeasure() {
      // update the 'measure' variable
   }

   private void updateAccumulatedCostUnits() {
      // This method is only called from the costLevelAchieved() method
      int accumProductionLevels = 0;
      Enumeration enum = resourceAndValueProductionSources.elements();
      while (enum.hasMoreElements()) {
         accumProductionLevels += (int)((MyDouble)enum.nextElement()).getValue();
      }
      double currentTime = com.armygame.recruits.StoryEngine.instance().clock().getTime();
      accumulatedCostUnits += (accumProductionLevels * (currentTime - timeOfLastUpdate) * (((double)focus)/100));
      System.out.println("Goal (" + getName() + "): Upd Accum Cost Units: accumProLvl=" + accumProductionLevels + " accumCostUn=" + accumulatedCostUnits);
      System.out.println("curr Time=" + currentTime + " last Time=" + timeOfLastUpdate + " focus/100=" + ((double)focus)/100);
      timeOfLastUpdate = currentTime;
      System.out.println("Progress: " + getProgress());
      updateGoalPhase(getProgress());
   }

   private void updateGoalPhase(double progress) {
      System.out.println("Goal: " + name + " Update Progress: " + progress);
      boolean newPhase = false;
      if ((progress >= 0.0) && (progress <= 20.0)) {
         if (firstPlay && progress > 10.00) {
            phase.changeValue("need");
            newPhase = true;
            firstPlay = false;
         }
      }
      else {
         if ((progress >20.0) && (progress <= 40.0)) {
            if (!phase.getValue().equalsIgnoreCase("accept")) {
               phase.changeValue("accept");
               newPhase = true;
            }
         }
         else {
            if ((progress > 40.0) && (progress <= 60.0)) {
               if (!phase.getValue().equalsIgnoreCase("difficulty")) {
                  phase.changeValue("difficulty");
                  newPhase = true;
               }
            }
            else {
               if ((progress > 60.0) && (progress <= 80.0)) {
                  if (!phase.getValue().equalsIgnoreCase("progress")) {
                     phase.changeValue("progress");
                     newPhase = true;
                  }
               }
               else {
                  if ((progress >80.0) && (progress <= 100.0)) {
                     phase.changeValue("achievement");
                     newPhase = true;
                  }
                  else {
                     System.out.println("Goal progress out of bounds (" + progress + ")");
                  }
               }
            }
         }
      }
      if (newPhase) {
         Vector rangeOfInterest = new Vector();
         rangeOfInterest.add(name + ":" + phase.getValue());
         StoryElementReference seRef
           = new StoryElementReference(com.armygame.recruits.StoryEngine.instance().getSceneTrie(),
                                       com.armygame.recruits.StoryEngine.instance().castManager.mainCharacter(),
                                       rangeOfInterest,
                                       new Vector(),
                                       "Goal Phase Scene Reference - Goal:" + name + " Phase:" + phase.getValue());
         com.armygame.recruits.StoryEngine.instance().addGoalSceneReference(seRef);
         System.out.println("Sent seRef to story engine: " + "Goal Phase Scene Reference - Goal:" + name + " Phase:" + phase.getValue());
      }
   }

   public boolean costLevelAchieved() {
      updateAccumulatedCostUnits(); // This is the only place where this method is called.
      if (accumulatedCostUnits >= cost) {
         System.out.println("*******   Cost achieved(" + getName() + ") ***********");
      }
      return (accumulatedCostUnits >= cost);
   }

   public void executeRecurringEvent(int currTime, ActionHandler handler) {
      if (recurringEvent != null) {
         recurringEvent.execute(currTime, handler);
      }
   }

   public Object[] getRecurringFinancialEvent() {
      // This method returns the active recurring financial event if there is one.
      Object[] tempArray;
      if (recurringEvent != null) {
         tempArray = recurringEvent.getFinancialEvent();
         if (tempArray != null) {
            recurringFinancialEventName = recurringEvent.getDescription();
            recurringFinancialEventAmt = recurringEvent.getFinancialEventAmount();
            recurringFinancialEventMaxPayments = recurringEvent.getMaxNumberOfOccurances();
            recurringFinancialEventPaymentsComplete = recurringEvent.getOccurancesToDate();
            recurringFinancialEventFreq = recurringEvent.getFrequency();
            return tempArray;
         }
      }
      return null;
   }

   private TypeValueConnector makeGoalTypeValueConnector() {
      Vector statusVec = new Vector();
      statusVec.add("notSelectable");
      statusVec.add("selectable");
      statusVec.add("selected");
      statusVec.add("achieved");
      return new TypeValueConnector(name, statusVec, "notSelectable");
   }

   private TypeValueConnector makeGoalPhaseTypeValueConnector() {
      Vector phasesVec = new Vector();
      phasesVec.add("need");
      phasesVec.add("accept");
      phasesVec.add("difficulty");
      phasesVec.add("progress");
      phasesVec.add("achievement");
      return new TypeValueConnector(name, phasesVec, "need");
   }

   public boolean registerWithStateVector(StateVectorManager sVM) {
      boolean b1 = phase.registerGoalWithStateVector(sVM);
      boolean b2 = status.registerGoalWithStateVector(sVM);
      return  (b1 & b2);
   }

   public void unregisterWithStateVector(StateVectorManager sVM) {
      status.unregisterStateVector(sVM);
   }

   public boolean isNotSelectable() {
      return (status.getValue()).equalsIgnoreCase("notSelectable");
   }

   public boolean isSelectable() {
      return (status.getValue()).equalsIgnoreCase("selectable");
   }

   public boolean isSelected() {
      return (status.getValue()).equalsIgnoreCase("selected");
   }

   public boolean isAchieved() {
      return (status.getValue()).equalsIgnoreCase("achieved");
   }

   public String getName() {
      return new String(name);
   }

   public String getCode() {
      return code;
   }

   public String getStatus() {
      return new String(status.getValue());
   }

   public double getMeasure() {
      return measure;
   }

   public ConditionAction getRequirements() {
      return requirements;
   }

   public ConditionAction getPrerequisites() {
      return prerequisites;
   }

   public int getCost() {
      return cost;
   }

   public int getFocus() {
      return focus;
   }

   public void setFocus(int f) {
      focus = f;
   }

   public String getHelp() {
      return help;
   }

   public double getEmphasis() {
      return (double) getFocus();
   }

   public double getProgress() {
      return Math.min(100.0*((double)accumulatedCostUnits)/((double)cost),100.0);
   }

   public Vector getStatusList() {
      return status.getValueSet();
   }

   public Vector getPhaseList() {
      return phase.getValueSet();
   }

   public GoalManager getGoalManager() {
      return myManager;
   }

   /* Empty methods from the ActionHandler interface */
   public void retractIntentConnector(String name) {}

   public void extendIntentConnector(String name) {}

   public ActionFrame getActionFrame() {
      return null;
   }

   public String[] getRequirementsStringArray() {
      return requirements.conditionsToString();
   }

   public String[] getResultsStringArray() {
      return requirements.goalHelpActionsToString();
   }

   public String getHelpString()
   {
      return "Help for " + this.name + " goal.";
//     return "<html><table width='300' border='0'>"+
//     "<font face='Arial'>"+
//     "<tr><td><b>Goal name: "+name+"</b>: Goal title goes here</td></tr>"+
//     "</font>"+
//     "<font face='Arial'>"+
//     "<tr><td><b>Prerequisite goals</b>:<OL><LI>Work Hard<LI>College AA Degree</OL></td></tr>"+
//     "</font>"+
//     "<font face='Arial'>"+
//     "<tr><td><b>Requires</b>: 25 value units</td></tr>"+
//     "</font>"+
//     "<font face='Arial'>"+
//     "<tr><td><b>Result</b>: +20 resource points</td></tr>"+
//     "</font>"+
//     "<font face='Arial'>"+
//     "<tr><td><b>Allows</b>: Marriage</td></tr>"+
//     "</font>"+
//     "</table></html>";
   }


}