package com.armygame.recruits.storyelements.sceneelements;

import java.io.*;
import java.util.*;

import com.armygame.recruits.storyelements.charactervaluesystem.*;
import com.armygame.recruits.storyelements.characterstatesystem.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.gui.SoldierNameProperties;

//import com.armygame.recruits.demophrases.*;
import com.armygame.recruits.interaction.intents.IntentActionMap;

import com.armygame.recruits.utils.*;


/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 2.0
 * This revision adds a state vector to the character. This state vector is
 * focused on the narrative side of the game.
 */

public class MainCharacter {

   //private String name;  //this is currently being used to help control the role
   private String firstName;
   private String lastName;
   private String nickName;
   private String actorName;
   private String voiceID;
   private String rank;
   private String gender;
   private int timeInService;
   private String role; //this is working in concert with name
   private IntentActionMap intentActionMap;
   private StateVectorManager storyState;
   private GoalManager myGoals;
   private boolean currentProtagonist;
   private TypeValueConnector charNameConnector;
   private Vector nameVec;

   private static final String MALE = "male";
   private static final String FEMALE = "female";
   private static final String NA = "na";

   // Character's photo
   //private File photo;
   private String photo;

   /*
   * Character Personality
   * For now, these values do not change during the game.  To make them
   * changeable, look at the education example where education is instantiated
   * as an EducationObservableValue.
   */

   private MainCharacterValueSystem values;
   private CharacterStateSystem connectorState;

   public MainCharacter(String name) {
      this.lastName = name;

      //photo = new File("data/SoldierPortrait.jpg");
      //photo = "data/SoldierPortrait.jpg";

/**
 * The following four statements need to be included to use Neal's generative
 * voice system. They were commented out so I could use my own IntentActionMap.
 */
//      PhraseParser p = new PhraseParser();
//      DemoPhrase testPhrase = new Homesick_B_Greet();
//      intentActionMap = new IntentActionMap();
//      intentActionMap.AddToMap(testPhrase.GetIntentName(), p.PhraseFromDescriptor( "[(B_Sd_Greet_1,B_Sd_Greet_2,B_Sd_Greet_3),(B_Sd_Cigaret_1,B_Sd_Cigaret_2,B_Sd_Cigaret_3),B_Sd_Cigaret_4]"));

/**
 * This statement is for my debugger/demo IntentActionMap
 */
      //intentActionMap = new IntentActionMap(name);
//      intentActionMap.addToMap("B_Greet", "Greetings sucker");
   }

   public void setStoryState(StateVectorManager svm) {
      storyState = svm;
   }

   public void setMainCharacterValueSystem(MainCharacterValueSystem mcvs) {
      values = mcvs;
      values.registerWithStateVector(storyState);
   }

   public void setCharacterStateSystem(CharacterStateSystem css) {
      connectorState = css;
      connectorState.registerWithStateVector(storyState);
   }

   public void setGoalManager(GoalManager manager) {
      myGoals = manager;
      myGoals.setMyCharacter(this);
      myGoals.registerWithStateVector(storyState);
      values.addConnectorChangeListener(myGoals);
      connectorState.addTypeValueConnectorChangeListener(myGoals);

   }

   public void initializeCharacter(String rank, int timeInService) {
      this.rank = rank;
      this.timeInService = timeInService;
      myGoals.initializeRank(rank, timeInService);

      buildNameTypeValueConnector();

      reportConnectorStatus();
      myGoals.reevaluateStatusAllGoals();
   }

   private void buildNameTypeValueConnector() {
      SoldierNameProperties namePs = new SoldierNameProperties();
      Enumeration enum = namePs.keys();   //namePs.elements();

      nameVec = new Vector();
      while (enum.hasMoreElements()) {
         nameVec.add((String)enum.nextElement());
      }

      charNameConnector = new TypeValueConnector("name", nameVec);


      int nextSlot;
      int startBit;
      int stopBit;
      String cName;
      startBit = storyState.getNextAvailableSlot();
      Vector charNameList = charNameConnector.getValueSet();
      Enumeration enum1 = charNameList.elements();
      while (enum1.hasMoreElements()) {
         cName = (String)enum1.nextElement();
         nextSlot = storyState.getNextAvailableSlot();
         storyState.addRange(cName, nextSlot);
      }
      stopBit = storyState.getNextAvailableSlot() - 1;
      storyState.addRange(charNameConnector.getName(), startBit, stopBit);

      charNameConnector.registerStateVector(storyState);

   }

   public void setCharacterNameConnector(String name) {
      charNameConnector.changeValue(name);
   }

   public CharInsides getCharInsides() {
      CharInsides ci = new CharInsides();
      values.getAttributes(ci);
      myGoals.getGoalManagerData(ci);
      ci.actorName = this.actorName;
      ci.charName = this.lastName;
      return ci;
   }

   public void setCharInsides(CharInsides ci) {
      values.setAttributesFromCharInsides(ci);
      myGoals.updateSelectedGoalsFromCharInsides(ci);
   }

   public void dumpStateVector() {
      System.out.println("state vector");
      storyState.dumpStateVector();
      System.out.println(storyState.getStateVector().toString());
   }

   public String getName() {
      return lastName;
   }

   public String getCharLastName() {
      return lastName;
   }

   public void setCharLastName(String charName) {
      this.lastName = charName;
      //setCharacterNameConnector(this.lastName);
   }

   public String getActorName() {
      return actorName;
   }

   public void setActorName(String actorName) {
      this.actorName = actorName;
   }

   //public File getPhoto() {
   public String getPhoto() {
      return photo;
   }

   public MainCharacterValueSystem Values() {
      return values;
   }

   public GoalManager goals() {
      return myGoals;
   }

   public CharacterStateSystem ConnectorState() {
      return connectorState;
   }

   public String toString() {
      return "Character: " + lastName;
   }

   public boolean updateTrait(String trait, double delta) {
      return values.updateTrait(trait, delta);
   }

   public void updateGoal(String goalName, String newStatus) {
      myGoals.updateGoal(goalName, newStatus);
   }

   public void updateConnector(String type, String value) {
      connectorState.changeConnectorValue(type, value);
   }

   public void cueChooser() {
      Vector choices = connectorState.getConnectorValueSet("mos");
      String[] cmfChoices = new String[choices.size()];
      int i = 0;
      String substr;
      Enumeration enum = choices.elements();
      while (enum.hasMoreElements()) {
         String cmf = (String)enum.nextElement();
         substr = cmf.substring(0,4);
         if (substr.equalsIgnoreCase("cmf-")) {
            cmfChoices[i] = cmf;
            i++;
         }
      }
      AlertMessage am = new AlertMessage(AlertMessage.CUE_CHOOSER_ALERT, cmfChoices);
      com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);

   }

   public void displayQuip(String quipName) {
      System.out.println("*** Display Quip Message sent ****");
      String quip = com.armygame.recruits.StoryEngine.quips().getQuip(quipName);
      AlertMessage am = new AlertMessage(AlertMessage.DISPLAY_QUIP, quip);
      com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
   }

   // Brian: adjust this if required
   public void displayNarrator(int narratorFaceIndex, String text)
   {
      System.out.println("*** Display Narrator Message ***");
      AlertMessage am = new AlertMessage(AlertMessage.DISPLAY_NARRATOR,text,narratorFaceIndex);
      com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
   }

   public void reportConnectorStatus() {
      values.reportConnectorsStatus();
      connectorState.reportConnectorStatus();
   }

   public void reportGoalStatus() {
      myGoals.reportStatusOfAllGoals();
   }

   public void reportConnectorsAndGoalStatus() {
      reportConnectorStatus();
      reportGoalStatus();
   }

   public IntentActionMap getIntentActionMap() {
      return intentActionMap;
   }

   public void setIntentActionMap(IntentActionMap newMap) {
      intentActionMap = newMap;
   }

   public void setRole(String r) {
      role = r;
   }

   public String getRole() {
      return role;
   }

   public void setRank(String rank) {
      this.rank = rank;
   }

   public String getRank() {
      return rank;
   }

   public void setGenderMale() {
      gender = this.MALE;
   }

   public void setGenderFemale() {
      gender = this.FEMALE;
   }

   public void setGenderNA() {
      gender = this.NA;
   }

   public String getGender() {
      return gender;
   }

   public void setVoiceID(String voice) {
      voiceID = voice;
   }

   public String getVoiceID() {
      return voiceID;
   }

   public void setCurrentProtagonistTrue() {
      currentProtagonist = true;
   }

   public void setCurrentProtagonistFalse() {
      currentProtagonist = false;
   }

   public boolean isCurrentProtagonist() {
      return currentProtagonist;
   }

   public void setTimeInService(int timeInService) {
      this.timeInService = timeInService;
   }

   public int getTimeInService() {
      return timeInService;
   }

   public StateVectorManager getStoryState() {
      return storyState;
   }

   public Enumeration getConnectorNames() {
      return values.getConnectors().getConnectorTable().keys();
   }

   public Enumeration getTypeValueConnectorNames() {
      return connectorState.getConnectors().getConnectorTable().keys();
   }
}