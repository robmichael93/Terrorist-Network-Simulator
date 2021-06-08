package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.utils.StateVectorManager;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.storyelements.sceneelements.CharInsides;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.gui.GoalTree;
import com.armygame.recruits.globals.ResourceReader;

import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GoalManager implements ConnectorChangeListener,
                                    TypeValueConnectorChangeListener,
                                    ValueChangeListener,
                                    GameClockListener {

   /****************************************************************************
     When a connector or goal changes status/state, the change is reflected in
     the State/Status Hashtables.  The ConditionActions within the goals hold a
     reference to MyString or MyBoolean object in the hastable.  Thus, when the
     GoalManager receives notice of a change in a connector (via an event), or
     a goal via the goalUpdate method, GoalManager changes the MyString/MyBoolean
     object in the Hashtable and the ConditionAction automatically reflects the
     change.  But, these changes do not cause the ConditionAction to reevaluate
     its status.  The GoalManager must force this by explicitly telling the
     ConditionAction to execute() itself, in which case the ConditionAction
     object will check to see if all of its Conditions are met, if they are,
     it will execute its Actions.
     The Dependencies Hashtables below tell the GoalManager which goals to
     execute when specific connectors or goals change state/status.
   ****************************************************************************/

   /* State and Status Hashtables - These are hashtables of hashtables */
   private Hashtable connectorState; /* "myCharacter", [ConnectorName, MyBoolean] */
   private Hashtable typeValueConnectorState; /* "myCharacter", [TVConnectorName, MyString] */
   private Hashtable goalStatus; /* "myCharacter", [GoalName, MyString] */
   private Hashtable observableValues; /* "myCharacter", [ObsValueName, MyDouble] */

   /* Dependencies Hashtables */
   private Hashtable connectorDependencies;/* ConnectorName, Vector of Goals */
   private Hashtable typeValueConnectorDependencies;/* TVConnectorName, Vector of Goals */
   private Hashtable goalDependencies;/* GoalName, Vector of Goals */
   private Hashtable obsValueDependencies; /* ObsValueName, Vector of Goals */

   private Hashtable goals;/* GoalName, Goal */
   private TypeValueConnector goalOfInterest;

   private GoalTree goalTree;
   private Goal[] selectedGoals;
   private final int MAX_SELECTED = 5;

   private Vector listeners;

   private Hashtable individualListeners;

   private MainCharacter myCharacter;

   private final String MY_CHARACTER = "myCharacter";

   private BufferedReader br;
   private String possessionDefinitions;
   //private File goalDefinitions;
   private String goalDefinitions;
   private GoalFactory goalFactory;
   private PossessionsManager possessionsMgr;
   private Possession payCheck;
   private String currentRank;
   private int timeInService;
   private String nextRank;
   private double lastTimeHack;
   private double promotionProgress;
   public Vector debits;
   public Vector credits;
   private Hashtable ranks;

   //public GoalManager(Enumeration connectors, Enumeration typeValConnectors, Enumeration obsValues, File goalDefs) {
   //public GoalManager(Enumeration connectors, Enumeration typeValConnectors, Enumeration obsValues, String goalDefs) {
   public GoalManager(Enumeration connectors, Enumeration typeValConnectors, Enumeration obsValues, String goalDefs, String possessionDefs) {

   //public GoalManager(Enumeration connectors, Enumeration typeValConnectors, Enumeration obsValues, File goalDefs, File possessionDefs) {

      goalDefinitions = goalDefs;
      possessionDefinitions = possessionDefs;
      initialize();

      String connName;
      String obsValName;
      while (connectors.hasMoreElements()) {
         connName = (String)connectors.nextElement();
         ((Hashtable)connectorState.get(MY_CHARACTER)).put(connName, new MyBoolean(false));
         connectorDependencies.put(connName, new Vector());
      }

      while (typeValConnectors.hasMoreElements()) {
         connName = (String)typeValConnectors.nextElement();
         ((Hashtable)typeValueConnectorState.get(MY_CHARACTER)).put(connName, new MyString("empty"));
         typeValueConnectorDependencies.put(connName, new Vector());
      }

      while (obsValues.hasMoreElements()) {
         obsValName = (String)obsValues.nextElement();
         ((Hashtable)observableValues.get(MY_CHARACTER)).put(obsValName, new MyDouble(0.0));
         obsValueDependencies.put(obsValName, new Vector());
      }

      String line = new String();
      String goalName;
      while((line = readNextLine()) != null) {
         if (line.equalsIgnoreCase("<Name>")) {
            goalName = readNextLine();
            ((Hashtable)goalStatus.get(MY_CHARACTER)).put(goalName, new MyString("notSelectable"));
            goalDependencies.put(goalName, new Vector());
         }
      }

      resetReader();

      goalFactory = new GoalFactory(connectorState, typeValueConnectorState, goalStatus, observableValues, this);
      Vector input = new Vector();
      while((line = readNextLine()) != null) {
         if (line.equalsIgnoreCase("<Goal>")) {
            while (!(line = readNextLine()).equalsIgnoreCase("</Goal>"))  {
               input.add(line);
            }
            Goal newGoal = goalFactory.makeGoal(input);
            addDependencies(newGoal.getRequirements(), newGoal);
            addDependencies(newGoal.getPrerequisites(), newGoal);
            if (! goals.containsKey(newGoal.getName())) {
               goals.put(newGoal.getName(), newGoal);
               individualListeners.put(newGoal.getName(), new Vector());
            }
            else {
               System.out.println("Duplicate goal name: " + newGoal.getName());
            }
         }
      }

      /* Build the goalOfInterest TypeValueConnector */
      Vector goalList = new Vector();
      Enumeration goalEnum = goals.keys();
      goalList.add(new String("noGoal"));
      while (goalEnum.hasMoreElements()) {
         goalList.add((String)goalEnum.nextElement());
      }
      goalOfInterest = new TypeValueConnector("goal",goalList, "noGoal");

      //Build the goalTree which will is used by the CharInsides object and GUI.
      Goal fromNode;
      Goal toNode;
      String fromNodeName;
      Vector toNodeVector;
      Enumeration enum = goalDependencies.keys();
      while (enum.hasMoreElements()) {
         fromNodeName = (String)enum.nextElement();
         fromNode = (Goal) goals.get(fromNodeName);
         toNodeVector = (Vector) goalDependencies.get(fromNodeName);
         Enumeration enum1 = toNodeVector.elements();
         if (!enum1.hasMoreElements()) {
            goalTree.addSingleGoal(fromNode);
         }
         else {
            while (enum1.hasMoreElements()) {
               toNode = (Goal)enum1.nextElement();
               goalTree.addDependency(fromNode, toNode);
   //            System.out.println("Adding goal dependency: addDependency(" +
   //                               fromNode.getName() + ", " + toNode.getName() + ")");
            }
         }
      }

      ranks.put("E-1", "Private (PVL)");
      ranks.put("E-2", "Private (PV2)");
      ranks.put("E-3", "Private First Class (PFC)");
      ranks.put("E-4", "Corporal (CPL)");
      ranks.put("E-5", "Sergeant (SGT)");
      ranks.put("E-6", "Staff Sergeant (SSG)");
      ranks.put("E-7", "Sergeant First Class (SFC)");

      PossessionsFactory pf = new PossessionsFactory(possessionDefinitions);
      possessionsMgr = new PossessionsManager(pf.buildPossessionTable());

      payCheck = new Possession("Pay", 0, 0, 2, 0, this);

      //dumpGoalPrereqsAndRqmts();
   }

   private void initialize() {

      lastTimeHack = 0;

      connectorState = new Hashtable();
      connectorState.put(MY_CHARACTER, new Hashtable());
      typeValueConnectorState = new Hashtable();
      typeValueConnectorState.put(MY_CHARACTER, new Hashtable());
      goalStatus = new Hashtable();
      goalStatus.put(MY_CHARACTER, new Hashtable());
      observableValues = new Hashtable();
      observableValues.put(MY_CHARACTER, new Hashtable());

      connectorDependencies = new Hashtable();
      typeValueConnectorDependencies = new Hashtable();
      goalDependencies = new Hashtable();
      obsValueDependencies = new Hashtable();

      goals = new Hashtable();
      goalTree = new GoalTree();
      selectedGoals = new Goal[MAX_SELECTED];
      listeners = new Vector();
      individualListeners = new Hashtable();

      credits = new Vector();
      debits = new Vector();

      ranks = new Hashtable();

      initializeReader();
   }

   private void initializeReader() {

      try {
         br = new BufferedReader(ResourceReader.getInputReader(goalDefinitions)); //new FileReader(goalDefinitions));
      }
      catch (IOException e) {
         System.err.println("Error(GoalManager) - unable to create BufferedReader" +
                            " for file " + goalDefinitions);
      }
   }

   private void resetReader() {
      try {
         br.close();
      }
      catch (IOException e) {
         System.err.println("Error(GoalManager) - unable to close BufferedReader" +
                            " for file " + goalDefinitions);
      }
      initializeReader();
   }

   public void setMyCharacter(MainCharacter mainChar) {
      myCharacter = mainChar;
   }

   public void updateValue(String valueName, double delta) {
      /* call update value within myCharacter*/
      myCharacter.updateTrait(valueName, delta);
   }

   public void updateResource(String resourceName, double delta) {
      /* call update resource within myCharacter*/
      myCharacter.updateTrait(resourceName, delta);
   }

   public void updateValuePoints(String valuePts, double delta) {
      /* call update value points within myCharacter*/
      myCharacter.updateTrait(valuePts, delta);
   }

   public void updateGoalPoints(String goalPts, double delta) {
      /* call update goal points within myCharacter*/
      myCharacter.updateTrait(goalPts, delta);
   }

   public void updateTypeValueConnector(String connectorName, String newValue) {
      /* call update type value connector within myCharacter*/
      myCharacter.updateConnector(connectorName, newValue);
   }

   public void updateGoal(String goal, String newStatus) {
      /* This method is called externally from an Interaction through the
         MainCharacter object.                                               */
      Goal g = (Goal)goals.get(goal);
      g.updateGoal(MY_CHARACTER, goal, newStatus);
      //System.out.println("GM.updateGoal (" + g.getName() + "): Goal Status - " + g.getStatus());
   }

   public void obtainPossession(String possession, Goal g) {
      possessionsMgr.obtainPossession(possession, g);
   }

   public void cueChooser() {
      myCharacter.cueChooser();
   }

   public void displayQuip(String quipName) {
      myCharacter.displayQuip(quipName);
   }

   public void reevaluateStatusAllGoals() {
      Enumeration enum = goals.elements();
      while (enum.hasMoreElements()) {
         ((Goal)enum.nextElement()).reevaluateStatus();
      }
   }

   public void goalChanged(Goal g) {
      /* This method is called from the goal.  The goal is telling the
         Goal Manager that its status has changed and that it needs to
         notify the appropriate listeners.                             */

      /* Change the status of the goal in the goalStatus hashtable */
      ((MyString)((Hashtable)goalStatus.get(MY_CHARACTER)).get(g.getName())).setValue(g.getStatus());

      /* Propogate this change to all listeners and appropriate goals */
      propogateGoalUpdate(g);
   }

   private void propogateGoalUpdate(Goal goal) {
      /* Notify listeners of change to goal status. */
      /* Director agent cares when goal is selected or achieved. */
      /* Chooser will care when the goal changes state in any way. */

      /* Get the character's role for use in the goal change event */
      String role = myCharacter.getRole();

      /* Notify those listeners that care about all goals */
      Enumeration enum = listeners.elements();
      GoalListener gl;
      while (enum.hasMoreElements()) {
         gl = (GoalListener) enum.nextElement();
         gl.goalChanged(new GoalChangeEvent(role, goal.getName(), goal.getStatus(), goal.getMeasure()));
      }

      /* Notify those listeners that care only about this specific goal */
      if ( individualListeners.containsKey(goal.getName()) ) {
         Vector thisGoalsListeners = (Vector) individualListeners.get(goal.getName());
         enum = thisGoalsListeners.elements();
         while ( enum.hasMoreElements() ) {
            gl = (GoalListener) enum.nextElement();
            gl.goalChanged(new GoalChangeEvent(role, goal.getName(), goal.getStatus(), goal.getMeasure()));
         }
      }

      /* Other goals only care when a goal is achieved.
         Goals get notified by changing the MyString object in the
         goalStatus Hashtable.  This will effect changes in the ConditionAction
         object.  But, still need to tell the goals to reevaluate
         their status (i.e. ConditionAction object)                           */

      if (goal.isAchieved()) {
         Vector dependentGoals = (Vector) goalDependencies.get(goal.getName());
         Enumeration enum1 = dependentGoals.elements();
         while (enum1.hasMoreElements()) {
            ((Goal) enum1.nextElement()).reevaluateStatus();
         }
      }
   }


/**
 * Notify all listeners of the status of all the goals.
 */
   public void reportStatusOfAllGoals() {
      Enumeration listOfGoals = goals.elements();
      while (listOfGoals.hasMoreElements()) {
         Enumeration enum = listeners.elements();
         GoalListener gl;
         Goal g = (Goal) listOfGoals.nextElement();
         while (enum.hasMoreElements()) {
            gl = (GoalListener) enum.nextElement();
            gl.goalChanged(new GoalChangeEvent(g.getName(), g.getStatus(), g.getMeasure()));
         }
      }
   }

   public void registerWithStateVector(StateVectorManager sVM) {
      Enumeration enum = goals.elements();
      Goal g;
      int startBit = 0;
      int nextSlot = 0;
      int stopBit = 0;
      boolean successfulRegistration = false;
      Vector statusList = null;
      Vector phaseList = null;
      String status = null;
      String phase = null;

      while (enum.hasMoreElements()) {
         g = (Goal) enum.nextElement();
         statusList = g.getStatusList();
         phaseList = g.getPhaseList();
         Enumeration enum1 = statusList.elements();
         startBit = sVM.getNextAvailableSlot();
         while (enum1.hasMoreElements()) {
            status = (String)enum1.nextElement();
            nextSlot = sVM.getNextAvailableSlot();
            sVM.addRange(g.getName() + ":" + status, nextSlot);
            //System.out.println("Added range (" + g.getName() + ":" + status + ") at Slot " + nextSlot);
         }
         stopBit = sVM.getNextAvailableSlot() - 1;
         sVM.addRange(g.getName() + ":Status", startBit, stopBit);
         //System.out.println("Added range (" + g.getName() + ":Status" + ") bits " + startBit + " to " + stopBit);

         Enumeration enum2 = phaseList.elements();
         startBit = sVM.getNextAvailableSlot();
         while (enum2.hasMoreElements()) {
            phase = (String)enum2.nextElement();
            nextSlot = sVM.getNextAvailableSlot();
            sVM.addRange(g.getName() + ":" + phase, nextSlot);
            //System.out.println("Added range (" + g.getName() + ":" + phase + ") at Slot " + nextSlot);
         }
         stopBit = sVM.getNextAvailableSlot() - 1;
         sVM.addRange(g.getName() + ":Phase", startBit, stopBit);
         //System.out.println("Added range (" + g.getName() + ":Phase" + ") bits " + startBit + " to " + stopBit);

         successfulRegistration = g.registerWithStateVector(sVM);
         if (!successfulRegistration) {
            System.out.println("Register error (GoalManager): No StateVectorRange found for " + g.getName());
         }
      }

      /* register the goalOfInterest TypeValueConnector */
      String gName;
      Vector goalList = goalOfInterest.getValueSet();
      startBit = sVM.getNextAvailableSlot();
      Enumeration enum3 = goalList.elements();
      while (enum3.hasMoreElements()) {
         gName = (String)enum3.nextElement();
         nextSlot = sVM.getNextAvailableSlot();
         sVM.addRange(gName, nextSlot);
      }
      stopBit = sVM.getNextAvailableSlot() - 1;
      sVM.addRange(goalOfInterest.getName(), startBit, stopBit);
      goalOfInterest.registerStateVector(sVM);

   }

   public void unregisterWithStateVector(StateVectorManager sVM) {
      Enumeration enum = goals.elements();
      Goal g;
      while (enum.hasMoreElements()) {
         g = (Goal) enum.nextElement();
         g.unregisterWithStateVector(sVM);
      }
   }

   public boolean addGoalListenerAllGoals(GoalListener gL) {
      if (listeners.contains(gL)) {
         return false;
      }
      else {
         listeners.add(gL);
         return true;
      }
   }

   public boolean addGoalListener(String goal, GoalListener gL) {
      if (individualListeners.containsKey(goal)) {
         ((Vector)individualListeners.get(goal)).add(gL);
         return true;
      }
      else {
         return false;
      }
   }

   public boolean removeGoalListener(String goal, GoalListener gL) {
      if (individualListeners.containsKey(goal)) {
         return ((Vector)individualListeners.get(goal)).remove(gL);
      }
      else {
         return false;
      }
   }

   public boolean removeGoalListenerAllGoals(GoalListener gL) {
      return listeners.remove(gL);
   }

   private void addDependencies(ConditionAction cA, Goal g) {
      if (cA == null) return;
      Vector connDep = cA.getConnectorDependencies();
      Vector tVConnDep = cA.getTypeValueConnectorDependencies();
      Vector goalDep = cA.getGoalDependencies();
      Vector obsValDep = cA.getObsValueDependencies();

      Enumeration enum;
      String[] roleValue;
      enum = connDep.elements();
      while (enum.hasMoreElements()) {
         roleValue = (String[])enum.nextElement();
         /* don't care about the 'role' in roleValue[0] */
         String conn = roleValue[1];
        ((Vector)connectorDependencies.get(conn)).add(g);
      }

      enum = tVConnDep.elements();
      while (enum.hasMoreElements()) {
         roleValue = (String[])enum.nextElement();
         /* don't care about the 'role' in roleValue[0] */
         String conn = roleValue[1];
        ((Vector)typeValueConnectorDependencies.get(conn)).add(g);
      }

      enum = goalDep.elements();
      while (enum.hasMoreElements()) {
         roleValue = (String[])enum.nextElement();
         /* don't care about the 'role' in roleValue[0] */
         String goal = roleValue[1];
         //System.out.println("need to get goal: " + goal);
        ((Vector)goalDependencies.get(goal)).add(g);
      }

      enum = obsValDep.elements();
      while (enum.hasMoreElements()) {
         roleValue = (String[])enum.nextElement();
         /* don't care about the 'role' in roleValue[0] */
         String obsVal = roleValue[1];
        ((Vector)obsValueDependencies.get(obsVal)).add(g);
      }
   }

   public void ConnectorChanged(ConnectorChangeEvent e) {
      /* Find the connector in the connectorState hashtable and change it's
         MyBoolean value. */
      String connectorName = e.getConnectorName();
      boolean state = e.isConnectorExtended();
      Hashtable connsOfInterest = (Hashtable)connectorState.get(MY_CHARACTER);
      if (connsOfInterest.containsKey(connectorName)) {
         ((MyBoolean)connsOfInterest.get(connectorName)).setValue(state);

         /*  Find the connector in the connectorDependencies hashtable and
            iterate through all of the goal in the vector and call reevalute(). */
         Vector dependentGoals = (Vector) connectorDependencies.get(connectorName);
         Enumeration enum = dependentGoals.elements();
         Goal g;
         while (enum.hasMoreElements()) {
            g = (Goal) enum.nextElement();
            g.reevaluateStatus();
         }
      }
   }

   public void typeValueConnectorChanged(TypeValueConnectorChangeEvent e) {
      /* Find the TypevalueConnector in the typevalueConnector hashtable and
         change it's MyString value. */
      String connectorName = e.getConnectorName();
      String value = e.getConnectorValue();
      Hashtable tVConnsOfInterest = (Hashtable) typeValueConnectorState.get(MY_CHARACTER);
      if (tVConnsOfInterest.containsKey(connectorName)) {
         ((MyString)tVConnsOfInterest.get(connectorName)).setValue(value);

         /*  Find the connector in the connectorDependencies hashtable and
            iterate through all of the goal in the vector and call reevalute(). */
         Vector dependentGoals = (Vector) typeValueConnectorDependencies.get(connectorName);
         Enumeration enum = dependentGoals.elements();
         Goal g;
         while (enum.hasMoreElements()) {
            g = (Goal) enum.nextElement();
            g.reevaluateStatus();
         }
      }
   }

   public void ValueChanged(ValueChangeEvent e) {
      String obsValueName = e.getSource().getName();
      double value = e.getSource().getValue();
      Hashtable obsValsOfInterest = (Hashtable) observableValues.get(MY_CHARACTER);
      if (obsValsOfInterest.containsKey(obsValueName)) {
         ((MyDouble)obsValsOfInterest.get(obsValueName)).setValue(value);
         /*  Find the observableValue in the obsValueDependencies hashtable and
            iterate through all of the goals in the vector and call reevalute(). */
         Vector dependentGoals = (Vector) obsValueDependencies.get(obsValueName);
         Enumeration enum = dependentGoals.elements();
         Goal g;
         while (enum.hasMoreElements()) {
            g = (Goal) enum.nextElement();
            g.reevaluateStatus();
         }
      }
      possessionsMgr.ValueChanged(e);
   }

   public void gameClockChanged(GameClockChangeEvent e) {
      debits.clear();
      credits.clear();
      Enumeration enum = goals.elements();
      Goal g;
      Vector withdrawls = new Vector();
      Vector deposits = new Vector();
      double transactionAmount = 0;
      double timeUnits = 0.0;
      double currentTime;

      double totalWithdrawlsPerTimeUnit = 0;
      double totalDepositsPerTimeUnit = 0;
      double billsPerTimeUnit;
      Object[] financialEvent;

      currentTime = e.getTime();
      updateRankProgress((int)currentTime);

      while (enum.hasMoreElements()) {
         g = (Goal) enum.nextElement();
         if (g.isSelected()) {
            g.reevaluateStatus();
            System.out.println("*GM(" + myCharacter.getName() +"):After reevaluate Status(" + currentTime + ") goal (" +
                              g.getName() + ") status (" + g.getStatus()+ ")");
         }
         else {
            if (g.isAchieved()) {
               if ((financialEvent = g.getRecurringFinancialEvent()) != null) {
                  timeUnits = ((Integer)financialEvent[2]).intValue();
                  transactionAmount = ((Double)financialEvent[1]).doubleValue();
                  if (transactionAmount > 0.0) {
                     totalDepositsPerTimeUnit += (transactionAmount / (double)timeUnits) ;
                     deposits.add(g);
                  }
                  else { // transaction amount less than zero
                     totalWithdrawlsPerTimeUnit += (transactionAmount / (double)timeUnits);
                     withdrawls.add(g);
                  }
               }
            }
         }
      }

      credits.add(new FinanceData(payCheck));
      while (payCheck.paymentDue((int)currentTime)) {
         payCheck.payRecurringCost((int)currentTime);
      }

      enum = deposits.elements();
      while (enum.hasMoreElements()) {
         g = (Goal) enum.nextElement();
         g.executeRecurringEvent((int)currentTime, g);
         credits.add(new FinanceData(g));
      }

      enum = withdrawls.elements();
      while (enum.hasMoreElements()) {
         g = (Goal) enum.nextElement();
         g.executeRecurringEvent((int)currentTime, g);
         debits.add(new FinanceData(g));
      }

      int payPerTimeUnit = payCheck.getRecurringCost() / payCheck.getFrequency();
      billsPerTimeUnit = possessionsMgr.payBills((int)currentTime);
      possessionsMgr.addBillsToDebitVector(debits);
      setFinancialResourceLevel(totalDepositsPerTimeUnit + totalWithdrawlsPerTimeUnit - billsPerTimeUnit + payPerTimeUnit);
   }

   private void setFinancialResourceLevel(double newValue) {
      myCharacter.Values().setValue("financial", newValue);
   }

   public void getGoalManagerData(CharInsides charInner) {
      charInner.setGoalTree(goalTree);
      charInner.credits = credits;
      charInner.debits = debits;

      //  Populate the array of "chosen" goals.
      for (int i = 0; i < selectedGoals.length; i++) {
         if (selectedGoals[i] != null) {
            if (!selectedGoals[i].isAchieved()) {
               charInner.chosenGoals[i] = selectedGoals[i];
               System.out.println("Goal added to 'chosenGoals': " + selectedGoals[i].getName());
            }
            else {
               charInner.chosenGoals[i] = null;
            }
         }
         else {
            charInner.chosenGoals[i] = null;
         }
      }
   }

   public void updateSelectedGoalsFromCharInsides(CharInsides charInner) {
      for (int i = 0; i < charInner.goalChangedByGui.length; i++) {
         System.out.println("** Checking boolean array ** Element " + i );
         if (charInner.chosenGoals[i] != null)  {
         	System.out.println("** Element " + i + " has an entry");
            if (charInner.goalChangedByGui[i]) {
            	System.out.println("** Element " + i + " has been changed");
               if (selectedGoals[i] != null) {
                  selectedGoals[i].deselectGoal();
                  selectedGoals[i].setFocus(0);
                  // Should I or do I need to reset the progress made up to the
                  // time in which it was deselected as a goal
               }
               selectedGoals[i] = ((Goal)goals.get(charInner.chosenGoals[i].getName()));
               System.out.println("Setting goal to selected: " + selectedGoals[i].getName());
               selectedGoals[i].selectGoal();
               selectedGoals[i].setFocus((int)charInner.goalEmphasis[i]);
               System.out.println("Updated emphasis for goal " + selectedGoals[i].getName() + "(" + selectedGoals[i].getFocus() + ")");
               selectedGoals[i].reevaluateStatus();
            }
            else {
            	if ((selectedGoals != null) && (i < selectedGoals.length) && (selectedGoals[i] != null)) {
	               selectedGoals[i].setFocus((int)charInner.goalEmphasis[i]);
   	            System.out.println("Updated emphasis for goal " + selectedGoals[i].getName() + "(" + selectedGoals[i].getFocus() + ")");
   	         }
            }
         }
         else {
            if (charInner.goalChangedByGui[i]) {
               if (selectedGoals[i] != null) {
                  selectedGoals[i].deselectGoal();
                  selectedGoals[i].setFocus(0);
                  selectedGoals[i] = null;
                  // Should I or do I need to reset the progress made up to the
                  // time in which it was deselected as a goal
               }
            }
         }
      }
   }

   public void initializeRank(String currentRank, int timeInService) {
      this.currentRank = currentRank;
      this.timeInService = timeInService;
      lastTimeHack = com.armygame.recruits.StoryEngine.instance().clock().getTime();
      updateRankProgress((int)lastTimeHack);
   }

   private void updateRankProgress (int currentTime) {
      double deltaT = currentTime - lastTimeHack;
      lastTimeHack = currentTime;
      timeInService += (int) (deltaT + .00001);
      if (timeInService < 26) {
         currentRank = "E-1";
         nextRank = "E-2";
         promotionProgress = ((double)timeInService ) / 26.0;
         if (myCharacter.isCurrentProtagonist()) {
            System.out.println("deltaT " + deltaT + " currentTime " + currentTime + " and lastTimehack " + lastTimeHack);
            System.out.println("TIS and Promote Progress: " + timeInService + " " + (promotionProgress * 100.0));
            AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                               AlertMessage.LOW_PRIORITY,
                                               (int)(promotionProgress * 100.0));
            com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);

         }
         payCheck.setName("E-1 pay");
         payCheck.setRecurringIncome(20);
      }
      else {
         if (timeInService < 52) {
            boolean promoted = (currentRank == "E-1");  // promotion occured
            currentRank = "E-2";
            nextRank = "E-3";
            promotionProgress = ((double)timeInService - 26.0) / 52.0;
            if (myCharacter.isCurrentProtagonist()) {
               System.out.println("deltaT " + deltaT + " currentTime " + currentTime + " and lastTimehack " + lastTimeHack);
               System.out.println("TIS and Promote Progress: " + timeInService + " " + (promotionProgress * 100.0));
               AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                  AlertMessage.LOW_PRIORITY,
                                                  (int)(promotionProgress * 100.0));
               com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
            }
            payCheck.setName("E-2 pay");
            payCheck.setRecurringIncome(30);
            if (promoted & myCharacter.isCurrentProtagonist()) {
               String message = "Congratulations! Your soldier has been promoted to " + ranks.get(currentRank) + ".";
               AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_ALERT, AlertMessage.MEDIUM_PRIORITY, currentRank, nextRank, message);
               com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
            }
         }
         else {
            if (timeInService < 112) {
               boolean promoted = (currentRank == "E-2");  // promotion occured
               currentRank = "E-3";
               nextRank = "E-4";
               promotionProgress = ((double)timeInService - 52.0) / 112.0;
               if (myCharacter.isCurrentProtagonist()) {
                  System.out.println("deltaT " + deltaT + " currentTime " + currentTime + " and lastTimehack " + lastTimeHack);
                  System.out.println("TIS and Promote Progress: " + timeInService + " " + (promotionProgress * 100.0));
                  AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                     AlertMessage.LOW_PRIORITY,
                                                     (int)(promotionProgress * 100.0));
                  com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
               }
               payCheck.setName("E-3 pay");
               payCheck.setRecurringIncome(40);
               if (promoted & myCharacter.isCurrentProtagonist()) {
                  String message = "Congratulations! Your soldier has been promoted to " + ranks.get(currentRank);
                  AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_ALERT, AlertMessage.MEDIUM_PRIORITY, currentRank, nextRank, message);
                  com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
               }
            }
            else {
               if (timeInService < 156) {
                  boolean promoted = (currentRank == "E-3");  // promotion occured
                  currentRank = "E-4";
                  nextRank = "E-5";
                  promotionProgress = ((double)timeInService - 112.0) / 156.0;
                  if (myCharacter.isCurrentProtagonist()) {
                     System.out.println("deltaT " + deltaT + " currentTime " + currentTime + " and lastTimehack " + lastTimeHack);
                     System.out.println("TIS and Promote Progress: " + timeInService + " " + (promotionProgress * 100.0));
                     AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                        AlertMessage.LOW_PRIORITY,
                                                        (int)(promotionProgress * 100.0));
                     com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
                  }
                  payCheck.setName("E-4 pay");
                  payCheck.setRecurringIncome(55);
                  if (promoted & myCharacter.isCurrentProtagonist()) {
                     String message = "Congratulations! Your soldier has been promoted to " + ranks.get(currentRank);
                     AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_ALERT, AlertMessage.MEDIUM_PRIORITY, currentRank, nextRank, message);
                     com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
                  }
               }
               else {
                  if (timeInService < 312) {
                     boolean promoted = (currentRank == "E-4");  // promotion occured
                     currentRank = "E-5";
                     nextRank = "E-6";
                     promotionProgress = ((double)timeInService - 156.0) / 312.0;
                     if (myCharacter.isCurrentProtagonist()) {
                        System.out.println("deltaT " + deltaT + " currentTime " + currentTime + " and lastTimehack " + lastTimeHack);
                        System.out.println("TIS and Promote Progress: " + timeInService + " " + (promotionProgress * 100.0));
                        AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                           AlertMessage.LOW_PRIORITY,
                                                           (int)(promotionProgress * 100.0));
                        com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
                     }
                     payCheck.setName("E-5 pay");
                     payCheck.setRecurringIncome(65);
                     if (promoted & myCharacter.isCurrentProtagonist()) {
                        String message = "Congratulations! Your soldier has been promoted to " + ranks.get(currentRank);
                        AlertMessage am = new AlertMessage(AlertMessage.PROMOTION_ALERT, AlertMessage.MEDIUM_PRIORITY, currentRank, nextRank, message);
                        com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
                     }
                  }
               }
            }
         }
      }
   }

   public Hashtable getObservableValues() {
      return observableValues;
   }

   public void dumpGoalPrereqsAndRqmts() {
      System.out.println("** ** ** ** ** Goal Data ** ** ** ** **");
      Enumeration goalEnum = goals.elements();
      while (goalEnum.hasMoreElements()) {
         String[] outArray;
         Goal g = (Goal)goalEnum.nextElement();
         System.out.println("-------- " + g.getName() + " --------");
         if (g.getPrerequisites() != null) {
            System.out.println("prereq conditons:");
            outArray = g.getPrerequisites().conditionsToString();
            for (int i = 0; i < outArray.length; i++) {
               System.out.println(outArray[i]);
            }
            System.out.println("prereq actions:");
            outArray = g.getPrerequisites().actionsToString();
            for (int i = 0; i < outArray.length; i++) {
               System.out.println(outArray[i]);
            }
         }
         else {
            System.out.println("* No prerequisites *");
         }
         if (g.getRequirements() != null) {
            System.out.println("req conditons:");
            outArray = g.getRequirements().conditionsToString();
            for (int i = 0; i < outArray.length; i++) {
               System.out.println(outArray[i]);
            }
            System.out.println("req actions:");
            outArray = g.getRequirements().actionsToString();
            for (int i = 0; i < outArray.length; i++) {
               System.out.println(outArray[i]);
            }
         }
         else {
            System.out.println("* No requirements *");
         }

      }
   }

   public String getRank() {
      return currentRank;
   }

   public int getTimeInService() {
      return timeInService;
   }

   private String readNextLine() {
      try {
         return br.readLine();
      }
      catch (IOException e) {
         System.err.println("Error (Goalmanager) - Unable to read from file.");
         return null;
      }
   }

}
