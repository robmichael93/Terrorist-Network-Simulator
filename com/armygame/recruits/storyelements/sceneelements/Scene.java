package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import com.armygame.recruits.storyelements.sceneelements.intentconnectors.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.agentutils.tickets.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.*;

import com.armygame.recruits.playlist.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class Scene  implements InteractionChangeListener,
                                        ExecutableSceneElement {

   protected static Stack ticketControlStack;
   protected static ESEManager interactionManager;

   private Vector keys;
   private ConnectorHashTable intentConnectors;
   private Hashtable roles;
   private Hashtable interactions;
   private TicketBase sceneTicket;
   private Playlist playlist;
   private String name;

   public Scene() {
      intentConnectors = new ConnectorHashTable();
      roles = new Hashtable(); /* Populated by the Scene Factory */
      interactions = new Hashtable();
      ticketControlStack = new Stack();
      playlist = new Playlist();
      interactionManager = new ESEManager();
   }

   public void setSceneTicket(TicketBase t) {
      sceneTicket = t;
   }

   private StoryEngine storyEngineInstance() {
      return com.armygame.recruits.StoryEngine.instance();
   }

   public void initializeScene() {

      storyEngineInstance().castManager.clearCurrentCast();
      Enumeration enum = roles.keys();
      while (enum.hasMoreElements()) {
         storyEngineInstance().castManager.addRoleToCurrentCast((String) enum.nextElement());
      }

      // clear the play list
//      playList.Clear();

      //register elements and set up listeners
      registerSceneElements();

      //all characters report their connector status
      enum = roles.elements();
      while (enum.hasMoreElements()) {
         ((MainCharacter)enum.nextElement()).reportConnectorsAndGoalStatus();
      }
   }

/**
 * Need to add error checking to this method.  No checking is done to see if
 * key will match a hashed element!!!!
 */
   public void registerSceneElements() {
      NewInteraction i;
      Enumeration connEnum;
      String s;
      RoleConnectorPair rcp;
      IntentConnector ic;
      Connector c;
      TypeValueConnector tvC;
      Enumeration intEnum = interactions.elements();
      while (intEnum.hasMoreElements()) {
         i = (NewInteraction)intEnum.nextElement();

         i.setPlaylist(playlist);
         i.setRoles(roles);

         /**
          * register Scene as a listener to the interactions in order for the
          * interaction to have the scene change a characters trait value.  the
          * change is done by "role" so it need to go through the scene rather
          * than directly to the character.
          */
         //System.out.println("ActiveScene listens to " + i.getClass().getName());
         i.addInteractionChangeListener(this);

         /**
          * register appropriate intentConnectors as listeners to the interaction
         */
         connEnum = i.getExpressIntentConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            s = (String)connEnum.nextElement();
            ic = (IntentConnector)intentConnectors.getConnector(s);
         //System.out.println("IntentConnector " + ic.getName() +
         //    " listens to interaction " + i.getClass().getName());
            i.addInteractionChangeListener(ic);
         }

         /**
          * register interactions as listeners to the appropriate IntentConnectors
          */
         connEnum = i.getReceiveIntentConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            s = (String)connEnum.nextElement();
            ic = (IntentConnector)intentConnectors.getConnector(s);
//         System.out.println("Interaction " + i.getName() + " listens to intent connector " +
//                            ic.getName());
            ic.addConnectorChangeListener(i);
         }

         /**
          * register interactions as listeners to the appropriate Character Connectors
          */
         connEnum = i.getCharacterConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            rcp = (RoleConnectorPair)connEnum.nextElement();
            ConnectorHashTable cht = getCharacterConnectors(rcp.Role());
            c = cht.getConnector(rcp.Connector());
            c.addConnectorChangeListener(i);
            c.setRole(rcp.Role());
         }

         /**
          * register interactions as listeners to the appropriate TypeValue Connectors
          */
         connEnum = i.getTypeValueConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            rcp = (RoleConnectorPair)connEnum.nextElement();
            Hashtable ht = getCharacterTypeValueConnectors(rcp.Role());
            tvC = (TypeValueConnector) ht.get(rcp.Connector());
            tvC.addTypeValueConnectorChangeListener(i);
            tvC.setRole(rcp.Role());
         }

         /**
          * register interactions as listeners to the appropriate Goals
          *
          */
         connEnum = i.getGoalsOfInterest().elements();
         StringPair sp;
         String role;
         String goal;
         while (connEnum.hasMoreElements()) {
            sp = (StringPair)connEnum.nextElement();
            role = sp.getString1();
            goal = sp.getString2();
            GoalManager gm = getGoalManager(role);
            gm.addGoalListener(goal, i);
         }
      }
   }

   public void execute() {
      ExecutableSceneElement ese;
      ExecutableSceneElement tempEse;
      initializeScene();
      ticketControlStack.push(sceneTicket);
      while (!ticketControlStack.empty()) {
         ese = (ExecutableSceneElement)ticketControlStack.pop();
         if (ese != null) {
            ese.execute();
         }
      }
      closeScene();
      //System.out.println(playlist.toXML());
      StoryEngine.instance().setPlaylist(playlist);
      playlist = new Playlist();
   }

   public void executeInstance() {
   }

   public void closeScene() {
      unregisterSceneElements();
      // removeRoles();
      // dump the play list
   }

   public void unregisterSceneElements() {
      NewInteraction i;
      Enumeration connEnum;
      String s;
      RoleConnectorPair rcp;
      IntentConnector ic;
      Connector c;
      TypeValueConnector tvC;
      Enumeration intEnum = interactions.elements();
      while (intEnum.hasMoreElements()) {
         i = (NewInteraction)intEnum.nextElement();

         i.setPlaylist(null);
         i.setRoles(null);

         /**
          * Unregister Scene as a listener to the interactions.
          */
         //System.out.println("ActiveScene removed as listener from " + i.getClass().getName());
         i.removeInteractionChangeListener(this);

         /**
          * Unregister appropriate intentConnectors as listeners to the interaction
         */
         connEnum = i.getExpressIntentConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            s = (String)connEnum.nextElement();
            ic = (IntentConnector)intentConnectors.getConnector(s);
         //System.out.println("IntentConnector " + ic.getName() +
         //    " removed as listener from interaction " + i.getClass().getName());
            i.removeInteractionChangeListener(ic);
         }

         /**
          * Unregister interactions as listeners to the appropriate IntentConnectors
          */
         connEnum = i.getReceiveIntentConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            s = (String)connEnum.nextElement();
            ic = (IntentConnector)intentConnectors.getConnector(s);
         //System.out.println("Interaction " + i.getClass().getName() + " removed as listener from intent connector " +
         //                  ic.getName());
            ic.removeConnectorChangeListener(i);
         }

         /**
          * Unregister interactions as listeners to the appropriate Character Connectors
          */
         connEnum = i.getCharacterConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            rcp = (RoleConnectorPair)connEnum.nextElement();
            ConnectorHashTable cht = getCharacterConnectors(rcp.Role());
            c = cht.getConnector(rcp.Connector());
            c.removeConnectorChangeListener(i);
            c.setRole(null);
         }

         /**
          * Unregister interactions as listeners to the appropriate TypeValue Connectors
          */
         connEnum = i.getTypeValueConnectorsOfInterest().elements();
         while (connEnum.hasMoreElements()) {
            rcp = (RoleConnectorPair)connEnum.nextElement();
            Hashtable ht = getCharacterTypeValueConnectors(rcp.Role());
            tvC = (TypeValueConnector) ht.get(rcp.Connector());
            tvC.removeTypeValueConnectorChangeListener(i);
            tvC.setRole(null);
         }

         /**
          * register interactions as listeners to the appropriate Goals
          *
          */
         connEnum = i.getGoalsOfInterest().elements();
         StringPair sp;
         String role;
         String goal;
         while (connEnum.hasMoreElements()) {
            sp = (StringPair)connEnum.nextElement();
            role = sp.getString1();
            goal = sp.getString2();
            GoalManager gm = getGoalManager(role);
            gm.removeGoalListener(goal, i);
         }
      }
   }

   public void registerCharacter(String role, MainCharacter c) {
      roles.put(role, c);
      //c.setRole(role); // This call should not be necessary.
                         // The actors are placed in roles at game startup and those
                         // role to actor mappings won't changed during the game.
   }

   public void removeRoles() {
      Enumeration enum = roles.elements();
      while (enum.hasMoreElements()) {
         ((MainCharacter) enum.nextElement()).setRole(null);
      }
   }

   public void registerIntentConnector(IntentConnector ic) {
      intentConnectors.addConnector(ic);
   }

   public void registerInteraction(NewInteraction i) {
      interactions.put(i.getName(), i);
   }

   public Hashtable getInteractions() {
      return interactions;
   }

   public Hashtable getIntentConnectors() {
      return intentConnectors.getConnectorTable();
   }

   public ConnectorHashTable getCharacterConnectors(String role) {
      return ((MainCharacter)roles.get(role)).Values().getConnectors();
   }

   public Hashtable getCharacterTypeValueConnectors(String role) {
      return ((MainCharacter)roles.get(role)).ConnectorState().getConnectors().getConnectorTable();
   }

   public GoalManager getGoalManager(String role) {
      return ((MainCharacter)roles.get(role)).goals();
   }

/**
 * InteractionChangeListener Interface Methods
 */

/**
 * This method updates a trait value in one of the scene characters
 * as specifed in the CharacterValueAndConnectorUpdateEvent
 */
   public void CharacterValueAndConnectorUpdate(CharacterValueAndConnectorUpdateEvent e) {
      if (e.getTargetType().equalsIgnoreCase("trait")) {
         ((MainCharacter)roles.get(e.getRole())).updateTrait(e.getTrait(),e.getDelta());
      }
      else {
         ((MainCharacter)roles.get(e.getRole())).updateConnector(e.getConnectorType(),e.getConnectorValue());
      }
   }

   public void UpdateGoal(GoalUpdateEvent e) {
      ((MainCharacter)roles.get(e.getRole())).updateGoal(e.getGoalName(), e.getStatus());
   }

   public void InteractionStatusChanged(InteractionChangeEvent e) {
      if (e.isInteractionReady()) {
         interactionManager.addESE(e.getSource());
      }
   }

   public void cueChooser(CueChooserEvent e) {
      ((MainCharacter)roles.get(e.getRole())).cueChooser();
   }

   public void displayQuip(DisplayQuipEvent e) {
      Enumeration enum = roles.elements();  //get a MainCharacter object
      ((MainCharacter)enum.nextElement()).displayQuip(e.getQuipName());
   }

//   public void GeneratePhrase(GeneratePhraseEvent e) {
//      ((MainCharacter)roles.get(e.getRole())).getIntentActionMap().GeneratePhrase(e.getIntent(),  playList);
//   }

   public void setName(String n) {
      name = n;
   }

   public String getName() {
      return name;
   }

   public void setKeys(Vector k) {
      keys = k;
   }

   public Vector getKeys() {
      return keys;
   }

   public String playlistToXML() {
      return playlist.toXML();
   }

   public void GeneratePhrase(GeneratePhraseEvent e) {}

   public void ExtendConnectorRequest(ExtendConnectorRequestEvent e) {}

   public void RetractConnectorRequest(RetractConnectorRequestEvent e) {}

   public void setStack(Stack s) {}

}