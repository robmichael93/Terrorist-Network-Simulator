package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.storyelements.sceneelements.intentconnectors.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.utils.*;



/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 * @todo
 *  -
 */

public abstract class Interaction implements ConnectorChangeListener,
                                             IntentConnectorChangeListener,
                                             TypeValueConnectorChangeListener,
                                             ActionHandler,
                                             GoalListener,
                                             ExecutableSceneElement {

   private String name;
   private String description;
   private Vector listeners; // objects listening to this interaction

   // Connectors that this interaction needs to know about when they change
   private StringPairVector characterConnectorsOfInterest;
   private StringVector receiveIntentConnectorsOfInterest;
   private StringPairVector typeValueConnectorsOfInterest;
   private StringPairVector goalsOfInterest;

   // Intent connectors this interaction notifies to express its intent
   private StringVector expressIntentConnectorsOfInterest;

   private boolean status;

   private Hashtable roles; // ** Is this needed by the Interaction? ** //
   private Playlist playlist;
   private ActionFrame actionframe;

   public Interaction(String n, String desc) {
      name = n;
      description = desc;
      listeners = new Vector();
      characterConnectorsOfInterest = new StringPairVector();
      typeValueConnectorsOfInterest = new StringPairVector();
      goalsOfInterest = new StringPairVector();
      receiveIntentConnectorsOfInterest = new StringVector();
      expressIntentConnectorsOfInterest = new StringVector();
   }

   public Interaction(String n) {
      this(n, "");
   }

   public Interaction(ESEManager eseMgr) {
      this("");
   }

   public void execute() {
      actionframe = new ActionFrame(this.toString());
      executeInstance();
      playlist.AddActionFrame(actionframe);
      Scene.interactionManager.execute();
   }

   public void setStack(Stack s) {}

   public void setInteractionReady() {
      changeStatus(true);
   }

   public void setInteractionNotReady() {
      changeStatus(false);
   }

   public boolean isReady() {
      return status;
   }

   public boolean isNotReady() {
      return !status;
   }

/**
 * Add listeners
 */
   public boolean addInteractionChangeListener(InteractionChangeListener v) {
      if (listeners.contains(v)) {
         return false;
      }
      listeners.add(v);
      return true;
   }

/**
 * Remove listeners
 */
   public boolean removeInteractionChangeListener(InteractionChangeListener v) {
      return listeners.remove(v);
   }

/**
 * Notify all listeners (just the Scene for now)
 * that the status of the interaction has changed.
 */
   public void changeStatus(boolean b) {
      status = b;
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.InteractionStatusChanged(new InteractionChangeEvent(this));
      }
   }

/**
 * Change the characters Value (ObservableValue) in the character's
 * MainCharacterValueSystem.
 * Notify all listeners (just the Scene is interested in this
 * type of event for now now) that a character trait is being updated.
 */
   public void updateValue(String role, String trait, double delta) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.CharacterValueAndConnectorUpdate(new CharacterValueAndConnectorUpdateEvent(role, trait, new Double(delta)));
      }
   }

/**
 * Change the characters Resource (ObservableValue) in the character's
 * MainCharacterValueSystem.
 * Notify all listeners (just the Scene is interested in this
 * type of event for now now) that a character trait is being updated.
 */
   public void updateResource(String role, String trait, double delta) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.CharacterValueAndConnectorUpdate(new CharacterValueAndConnectorUpdateEvent(role, trait, new Double(delta)));
      }
   }

/**
 * Change the characters Value Points (ObservableValue) in the character's
 * MainCharacterValueSystem.
 * Notify all listeners (just the Scene is interested in this
 * type of event for now now) that a character trait is being updated.
 */
   public void updateValuePoints(String role, String trait, double delta) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.CharacterValueAndConnectorUpdate(new CharacterValueAndConnectorUpdateEvent(role, trait, new Double(delta)));
      }
   }

/**
 * Change the characters Goal Points (ObservableValue) in the character's
 * MainCharacterValueSystem.
 * Notify all listeners (just the Scene is interested in this
 * type of event for now now) that a character trait is being updated.
 */
   public void updateGoalPoints(String role, String trait, double delta) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.CharacterValueAndConnectorUpdate(new CharacterValueAndConnectorUpdateEvent(role, trait, new Double(delta)));
      }
   }

/**
 * Change the characters typeValueConnector value that is in the character's
 * CharacterStateSystem
 * Notify all listeners (just the Scene is interested in this
 * type of event for now now) that a character trait is being updated.
 */
   public void updateTypeValueConnector(String role, String connector, String newValue) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.CharacterValueAndConnectorUpdate(new CharacterValueAndConnectorUpdateEvent(role, connector, newValue));
      }
   }

   public void updateGoal(String role, String name, String status) {
      /* This method will need to make a call to the GoalManager
         within the MainCharacter in order to update the goal.*/
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.UpdateGoal(new GoalUpdateEvent(role, name, status));
      }
   }

/**
 * Cue the Chooser from within an interaction
 * Implemented as part of the ActionHander interface
 * @param role
 */

   public void cueChooser(String role) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.cueChooser(new CueChooserEvent(role));
      }
   }

/**
 * Display a quip
 * Implemented as part of the ActionHander interface
 * @param quipName
 */

   public void displayQuip(String quipName) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.displayQuip(new DisplayQuipEvent(quipName));
      }
   }

/**
 * Notify all listeners that an intent is being expressed (IntentConnector
 * is being extended).
 */
   public void extendIntentConnector(String name) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.ExtendConnectorRequest(new ExtendConnectorRequestEvent(name));
      }
   }

   public void retractIntentConnector(String name) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.RetractConnectorRequest(new RetractConnectorRequestEvent(name));
      }
   }


/**
 * Notify Scene that the interaction is requesting
 * the character specified by the 'role' generate a phrase specified by the
 * intent and add it to the play list.
 */
   public void generatePhrase(String role, String intent) {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         InteractionChangeListener icl = (InteractionChangeListener) enum.nextElement();
         icl.GeneratePhrase(new GeneratePhraseEvent(role, intent));
      }
   }

/**
 * Method required for ConnectorChangeListener Interface
 *  *** Should be overridden by the subclass ***
 *
 * When a connector changes status, the interaction is notified via this method.
 * Change in connector status may change the interactions status,
 * if so, then the interaction must notify all listeners by calling
 * method changeStatus(boolean).
 */
   public void ConnectorChanged(ConnectorChangeEvent e) {
   }

/**
 * Method for TypeValueConnectorChangeListener
 */
   public void typeValueConnectorChanged(TypeValueConnectorChangeEvent e) {
   }

/**
 * Empty method for ActionHandler interface
 */
   public void obtainPossession(String name) { }


   public void addCharacterConnectorOfInterest(String role, String connector) {
      characterConnectorsOfInterest.addStringPair(new RoleConnectorPair(role, connector));
   }

   public void addTypeValueConnectorOfInterest(String role, String typeValConnector) {
      typeValueConnectorsOfInterest.addStringPair(new RoleConnectorPair(role, typeValConnector));
   }

   public void addGoalOfInterest(String role, String goal) {
      goalsOfInterest.addStringPair(new StringPair(role, goal));
   }

   public Vector getCharacterConnectorsOfInterest() {
      return characterConnectorsOfInterest.getStringPairVector();
   }

   public Vector getTypeValueConnectorsOfInterest() {
      return typeValueConnectorsOfInterest.getStringPairVector();
   }

   public Vector getGoalsOfInterest() {
      return goalsOfInterest.getStringPairVector();
   }

/**
 * This method only removes the strings from the vector, it does not remove
 * the established listener connection.
 */
   public void removeCharacterConnectorOfInterest(String role, String connector) {
      characterConnectorsOfInterest.removeStringPair(new RoleConnectorPair(role, connector));
   }

   public void addReceiveIntentConnectorOfInterest(String name) {
      receiveIntentConnectorsOfInterest.addString(name);
   }

/**
 * This method only removes the strings from the vector, it does not remove
 * the established listener connection.
 */
   public void removeReceiveIntentConnectorOfInterest(String name) {
      receiveIntentConnectorsOfInterest.removeString(name);
   }

   public Vector getReceiveIntentConnectorsOfInterest() {
      return receiveIntentConnectorsOfInterest.getStringVector();
   }

   public void addExpressIntentConnectorOfInterest(String name) {
      expressIntentConnectorsOfInterest.addString(name);
   }

/**
 * This method only removes the strings from the vector, it does not remove
 * the established listener connection.
 */
   public void removeExpressIntentConnectorOfInterest(String name) {
      expressIntentConnectorsOfInterest.removeString(name);
   }

   public void setPlaylist(Playlist p) {
      playlist = p;
   }

   public void setRoles(Hashtable r) {
      roles = r;
   }

   public ActionFrame getActionFrame() {
      return actionframe;
   }

   protected MainCharacter getCharacter(String role) {
      MainCharacter mChar = (MainCharacter)roles.get(role);
      return mChar;
   }

   protected StateVector getStateVector(String role) {
      MainCharacter mChar = (MainCharacter)roles.get(role);
      return mChar.getStoryState().getStateVector();
   }

   public Vector getExpressIntentConnectorsOfInterest() {
      return expressIntentConnectorsOfInterest.getStringVector();
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public String toString() {
      return "-- " + description;
   }
}





