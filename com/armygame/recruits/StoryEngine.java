package com.armygame.recruits;

import java.io.*;
import java.util.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.storyelements.characterstatesystem.*;
import com.armygame.recruits.storyelements.charactervaluesystem.*;
import com.armygame.recruits.globals.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.messaging.*;


/**
 * Title:
 * Description: Story Engine main routine
 * Copyright:    Copyright (c) 2002
 * Company: MOVES Institute
 * @author Brian Osborn
 * @version 1.0
 */

public class StoryEngine {

   private static StoryEngine storyEng;

   private StoryElementReference nextTicket;

   private ExecutableSceneElement ese;

   private MainCharacter char01;
   private MainCharacter char02;
   private MainCharacter char03;
   private MainCharacter char04;

   //public Hashtable gameCharacters;
   private AttributeTrie sceneTrie;
   private AttributeTrie ticketTrie;
   private Hashtable sceneRefs;
   private Hashtable ticketRefs;
   private StoryLineManager storyLine;
   private Playlist playlist;
   private String currentScene;
   private GoalManager goalManager;
   private LinkedList goalSceneQueue;
   private boolean goalScene;

   private Hashtable scenes;

   private static QuipTable quips;

   private static GameClock clock;

   private CharacterFactory charFactory;

   public CastManager castManager;

   private RangeMap characterRangeMap;

   private StoryEngineMessaging mess;

   private StoryEngine() { /* Singleton */
   }

   private void initialize() {

      castManager = new CastManager("data/roles.xml", "data/actors.xml", "data/CharacterFactoryConfig.xml");

      characterRangeMap = mainCharacter().getStoryState().getRangeMap();

      RecruitsGame.Instance().Initialize("data/RecruitsConfiguration.xml",
                                         "Character", characterRangeMap);

      /**
       * Read scene definition file and build the sceneTrie
       */
      SceneFactory sf = new SceneFactory();
      scenes = sf.makeSceneTable(castManager.getCast(), "data/Scenes/");
      TargetObjectKey tok;
      StateVector key;
      sceneTrie = new AttributeTrie(characterRangeMap, characterRangeMap.BaseRangeSize());

      Enumeration enum = scenes.elements();
      while (enum.hasMoreElements()) {
         key = new StateVector(characterRangeMap);
         Scene currentScene = (Scene) enum.nextElement();
         Vector sceneKeys = currentScene.getKeys();
         Enumeration enum1 = sceneKeys.elements();
         while (enum1.hasMoreElements()) {
            String k = (String) enum1.nextElement();
            try {
            	key.SetRange(k);
            } catch(Exception e) {
            	System.out.println("Bad key: " + k + " in " + currentScene.getName());
            	System.exit(-1);
            }
         }
         tok = key.MakeTargetObjectKey(currentScene);
         sceneTrie.TrieInsert(tok);
      }

      /**
       * Build scene references from a data file and assoicate them with the
       * sceneTrie AttributeTrie
       */
      StoryElementReferenceFactory sRF = new StoryElementReferenceFactory();
      sceneRefs = sRF.makeStoryElementReferences(sceneTrie, mainCharacter(),
                                                    "data/SceneReferences_Rev1.txt");

      /**
       * Build ticket references from a data file and associate it with an
       * AttributeTrie (Trie that will contain tickets).
       */
      ticketTrie = new AttributeTrie(characterRangeMap, characterRangeMap.BaseRangeSize());
      ticketRefs = sRF.makeStoryElementReferences(ticketTrie, mainCharacter(),
                                                    "data/TicketReferences.txt");

      /**
       * Read the ticket definition file and build the ticketTrie
       */
      TicketFactory tF = new TicketFactory();

      /**
       * This call refers to a datafile that calls for some scenes by name that do not exist in the
       * current scene definition file.  They do exist in SimpleSceneDefinitions.xml
       * but the new code does not work with "SimpleScenes."  See notes file for
       * explanation.  This line can be uncommented and it will run, but
       * it displays error messages.
       *
       * Hashtable tickets = tF.makeTickets(scenes, sceneRefs, ticketRefs, new File("data/TicketDefinitions.txt"));
       *
       */
      // 3/5/02 (0015) Hashtable tickets = tF.makeTickets(scenes, sceneRefs, ticketRefs, new File("data/SceneTestTicketDef.txt"));
      //Hashtable tickets = tF.makeTickets(scenes, sceneRefs, ticketRefs, new File("data/SceneTestTicketDef.txt"));
      //Hashtable tickets = tF.makeTickets(scenes, sceneRefs, ticketRefs, new File("data/FocusGroupTicketDefinitions_Rev1.txt"));
      Hashtable tickets = tF.makeTickets(scenes, sceneRefs, ticketRefs, "data/FocusGroupTicketDefinitions_Rev1.txt");
      TargetObjectKey tok1;
      StateVector key1;

      enum = tickets.elements();
      while (enum.hasMoreElements()) {
         key1 = new StateVector(characterRangeMap);
         SequentialStoryTicket currentTicket = (SequentialStoryTicket) enum.nextElement();
         Vector ticketKeys = currentTicket.getKeys();
         Enumeration enum1 = ticketKeys.elements();
         while (enum1.hasMoreElements()) {
            String s = (String) enum1.nextElement();
            try {
            	key1.SetRange(s);
            } catch(Exception e) {}
         }
         tok1 = key1.MakeTargetObjectKey(currentTicket);
         ticketTrie.TrieInsert(tok1);
      }

      storyLine = new StoryLineManager(sceneTrie, ticketTrie);

      playlist = null;
      currentScene = null;

      goalSceneQueue = new LinkedList();
      goalScene = false;

   }

   public static StoryEngine instance() {
      if ( storyEng == null ) {
         storyEng = new StoryEngine();
         storyEng.initialize();
      }
      return storyEng;
   }

   public static GameClock clock() {
      if ( clock == null) {
         clock = new GameClock();
         clock.setIncrement(1.0);
      }
      return clock;
   }

   public static QuipTable quips() {
      if (quips == null) {
         quips = new QuipTable("data/quips.txt");
      }
      return quips;
   }

   public void go() {
      mess = RecruitsMain.instance().storyenginemessaging;
      initializeStoryLine();
   }

   private void initializeStoryLine() {
      mainCharacter().getStoryState().getStateVector().Clear();
      mainCharacter().ConnectorState().changeConnectorValue("mos", "noMOS");
      mainCharacter().ConnectorState().changeConnectorValue("careerPhase", "recruiter");
      nextTicket = (StoryElementReference) ticketRefs.get("ticketType2");
      ese = nextTicket.getSingleMatch();
      storyLine.pushElement(ese);
   }


   public void getNextPlaylist(String sceneName) {
      if (sceneName != null) {
         storyLine.pushElement((Scene)scenes.get(sceneName));
         MissingAssetLog.Instance().SetCurrentScene( sceneName );
         System.out.println("Requested Scene: " + sceneName);
         storyLine.execute();
         mess.loadPlayList(playlist);
         playlist = null;
         System.out.println("** Playlist transmitted to GUI **");
      }
      else {
         while ((!storyLine.isComplete()) && (playlist == null) && (goalSceneQueue.isEmpty())) {
            storyLine.execute();
         }
         if (!goalSceneQueue.isEmpty() && playlist == null) {
            ExecutableSceneElement goalSceneRef = (ExecutableSceneElement)goalSceneQueue.removeFirst();
            storyLine.pushElement(goalSceneRef);
            storyLine.execute();
            if (playlist != null) {
               goalScene = true;
            }
         }
         if (playlist != null) {
            /* Transmit playlist to GUI */
            mess.loadPlayList(playlist);
            if (!goalScene) {
               clock.incrementTime();
               System.out.println("** Clock incremented due to playlist being sent to GUI **");
               goalScene= false;
            }
            playlist = null;
         }
         else { //playlist is null - need a playlist or new ticket
            if (storyLine.isComplete()) {
               nextTicket = (StoryElementReference) ticketRefs.get("ticketType2");
               ese = nextTicket.getSingleMatch();
               if (ese != null) {
                  storyLine.pushElement(ese);
                  getNextPlaylist(null);
               }
               else {
                  mess.sendNoPlaylist();
               }
            }
            else {
               getNextPlaylist(null);
            }
         }
      }
   }

   public void sendCharacterAttributes() {
      System.out.println("Send Char Attributes: Getting info for char in role " + mainCharacter().getRole());
      CharInsides charInnner = ((MainCharacter)mainCharacter()).getCharInsides();
      mess.sendCharAttributes(charInnner);
   }

   public void receivedCharacterAttributes(CharInsides charInner) {
      System.out.println("Received update character Attributes for character in role" + mainCharacter().getRole());
      mainCharacter().setCharInsides(charInner);
      System.out.println("MC:Actor is " + mainCharacter().getActorName() + " Last name is " + mainCharacter().getCharLastName());
   }

   public void receivedCareerChoice(String cmfSelection) {
      mainCharacter().ConnectorState().changeConnectorValue("mos", cmfSelection);
      System.out.println("Set MOS to " + mainCharacter().ConnectorState().getConnectorValue("mos"));
   }

   public void receivedNewCharacter(CharInsides charInner) {
      System.out.println("** Received message defining a new character ** " + charInner.actorName);
      castManager.setCast((charInner.actorName).toLowerCase()); //set actor name and fills all other roles
      mainCharacter().setCharLastName(charInner.charName); //set character name picked by player
      mainCharacter().setCharInsides(charInner); //set the values, resources, and goals
      System.out.println("MC:Actor is " + mainCharacter().getActorName() + " Last name is " + mainCharacter().getCharLastName());
   }

   public void receivedGameSpeed(int speed)
   {
     System.out.println("** Received message setting game speed : "+speed);
     // Speed will be GameSpeedMessage.OVERVIEW (fast)
     //               GameSpeedMessage.MEDIUM
     //               GameSpeedMessage.FOCUS (slow)
     // Brian do something here
   }

   public void sendAlertMessage(AlertMessage am) {
      mess.sendAlertMessage(am);
      System.out.println("Sending Alert Message: Alert type =" + am.getAlertType() + " " + am.getQuip());
   }

   public void setPlaylist(Playlist p) {
      playlist = p;
   }

   public void setPlayerCharacterName(String name) {
      mainCharacter().setCharacterNameConnector(name);
   }

   public MainCharacter mainCharacter() {
      return castManager.mainCharacter();
   }

   public AttributeTrie getSceneTrie() {
      return sceneTrie;
   }

   public void addGoalSceneReference(ExecutableSceneElement gsRef) {
      goalSceneQueue.add(gsRef);
   }

}