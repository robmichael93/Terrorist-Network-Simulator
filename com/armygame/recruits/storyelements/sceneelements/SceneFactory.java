package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.agentutils.tickets.*;
import com.armygame.recruits.storyelements.sceneelements.intentconnectors.*;
import com.armygame.recruits.globals.ResourceReader;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 2.0
 *
 * This class has been rewritten to build the scenes from a directory of
 * individual scene files.
 *
 * The endScene() method assumes that the tickets are SequentialStoryTickets
 */

public class SceneFactory {

   private Hashtable scenes;
   private Hashtable interactions;
   private NewInteraction singleIntx;
   private InteractionFactory interactionFactory;
   //private File definitions;
   private String currentFile;
   private Hashtable commands;
   private BufferedReader br; /* visible throughout the class and inner classes */
   private Vector keys;
   private MainCharacter mainChar;
   private Hashtable gameCharacters;
   private Vector sceneCharacters;
   private Hashtable tickets;
   private SequentialSceneTicket sceneTicket;
   private Vector sceneTicketFrames;
   private String name;
   private String sceneDirectoryPath;

   public SceneFactory() {
      commands = new Hashtable();
      commands.put(new String("<Story>"), new Integer(0));
      commands.put(new String("<Scene>"), new Integer(1));
      commands.put(new String("<Name>"), new Integer(2));
      commands.put(new String("<Interaction>"), new Integer(3));
      commands.put(new String("<Key>"), new Integer(4));
      commands.put(new String("<Ticket>"), new Integer(5));
      commands.put(new String("<SceneTicket>"), new Integer(6));
      commands.put(new String("<Character>"), new Integer(7));
      commands.put(new String("</Scene>"), new Integer(8));
      commands.put(new String("</Story>"), new Integer(9));
      scenes = new Hashtable();
      sceneCharacters = new Vector();
      interactions = new Hashtable();
      tickets = new Hashtable();
      sceneTicket = new SequentialSceneTicket("Scene Ticket");
      sceneTicketFrames = new Vector();
      singleIntx = null;
      keys = new Vector();
      currentFile = null;
      sceneDirectoryPath = null;
      br = null;
      interactionFactory = new InteractionFactory();
   }

   //public Hashtable makeScenes(Hashtable gameCast, File sceneDefs) {
   public Hashtable makeSceneTable(Hashtable gameCast, String sceneDirPath) {
      sceneDirectoryPath = sceneDirPath;
      gameCharacters = gameCast;
      initializeReader(sceneDirectoryPath + "index.txt");
      Vector sceneVector = new Vector(100, 50);
      String sceneFile = new String();
      while ((sceneFile = readNextLine()) != null) {
         sceneVector.add(sceneFile);
      }
      Enumeration enum = sceneVector.elements();
      while (enum.hasMoreElements()) {
         sceneFile = (String)enum.nextElement();
         makeScene(sceneFile);
      }
      return scenes;
   }

   private void makeScene(String sceneFile) {
      initializeReader(sceneDirectoryPath + sceneFile);
      int i = 0;
      String line = new String();
      while ((line = readNextLine()) != null) {
         if (line.startsWith("<?xml"))
           continue;
         if ((line.charAt(0)) == '-') {
            i = 9;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 1: // Scene
               startScene();
               break;
            case 2: // Name
               startName();
               break;
            case 3: // Interaction
               startInteraction();
               break;
            case 4: // StartKey
               startKey();
               break;
            case 5: // Ticket
               startTicket();
               break;
            case 6: // SceneTicket
               startSceneTicket();
               break;
            case 7: // Character
               startCharacter();
               break;
            case 8: // endScene
               endScene(sceneFile);
               break;
            case 9: // comment
               break;
         }
      }
   }

   private void initializeReader(String inputFile) {
      currentFile = inputFile;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(currentFile)); //new FileReader(inputFile));
      }
      catch (IOException e) {
         System.err.println("Error(makeScenes) - Unable to create BufferedReader " +
                            "from file " + currentFile + ": " + e.toString());
      }
   }

   private String readNextLine() {
      try {
         return br.readLine();
      }
      catch (IOException e) {
         System.err.println("Error(SceneFactory) - Input file error " +
                            currentFile + ": " + e.toString());
         return null;
      }
   }

   private void closeReader() {
      try {
         br.close();
      }
      catch (IOException e) {
         System.err.println("Error(makeScenes) - Unable to close BufferedReader for file " +
                            currentFile + ": " + e.toString());
      }
   }

   private void startStory() {
   }

   private void endStory() {
   }

   private void startScene() {
   }

   private void endScene(String sceneFile) {
      TemporaryTicket tempTicket = null;
      SequentialSceneTicket t;
      Scene s = new Scene();

      /* Register MainCharacter object for the scene by role */
      Enumeration enum = sceneCharacters.elements();
      while (enum.hasMoreElements()) {
         String role = (String)enum.nextElement();
         s.registerCharacter(role, (MainCharacter)gameCharacters.get(role));
      }

      /* Build tickets from ticket definitions and interactions
         Needed to wait to build the tickets until after all of the
         interactions had been instantiated.
      */
      enum = tickets.elements();
      while (enum.hasMoreElements()) {
         tempTicket = (TemporaryTicket)enum.nextElement();
         t = tempTicket.getTicket();
         Vector frames = tempTicket.getFrames();
         Enumeration enum1 = frames.elements();
         int i = 0;
         while (enum1.hasMoreElements()) {
            String interact = (String)enum1.nextElement();
            t.addFrame(interactions.get(interact), i);
            i++;
         }
         //replace the temp ticket with the SequentialStoryTicket
         tickets.put(tempTicket.getName(), t);
      }

      // Build the sceneTicket
      enum = sceneTicketFrames.elements();
      int i = 0;
      while(enum.hasMoreElements()) {
         String ese = (String) enum.nextElement();
         if (tickets.containsKey(ese)) {
            sceneTicket.addFrame(tickets.get(ese), i);
            i++;
         }
         else {
            if (interactions.containsKey(ese)) {
               sceneTicket.addFrame(interactions.get(ese), i);
               i++;
            }
            else {
               System.out.println("** Error ** Attempt to add unknown element " +
                                   " to SceneTicket (" + ese + ")");
            }
         }
      }
//         String ticketName = (String)enum.nextElement();
//         sceneTicket.addFrame(tickets.get(ticketName), i);

      // Register Interactions and IntentConnectors
      enum = interactions.elements();
      String intent = null;
      Vector intents = new Vector();
      while (enum.hasMoreElements()) {
         NewInteraction inter = (NewInteraction)enum.nextElement();
         intents = inter.getExpressIntentConnectorsOfInterest();
         Enumeration enum1 = intents.elements();
         while (enum1.hasMoreElements()) {
            intent = (String) enum1.nextElement();
            s.registerIntentConnector(new IntentConnector(intent, 0));
         }
         s.registerInteraction(inter);
      }

      s.setKeys(keys);
      s.setName(name);
      s.setSceneTicket(sceneTicket);
      //System.out.println("Adding scene (" + name + ") to hashtable.");
      //scenes.put(name, s);

      //System.out.println("Adding scene (" + sceneFile + ") to hashtable.");
      scenes.put(sceneFile, s);

      keys = new Vector();
      interactions = new Hashtable();
      sceneCharacters = new Vector();
      tickets = new Hashtable();
      sceneTicket = new SequentialSceneTicket("Scene Ticket");
      sceneTicketFrames = new Vector();
   }

   private void startKey() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Key>")) {
         keys.add(line);
         line = readNextLine();
      }
   }

   private void startName() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Name>")) {
         name = line;
         line = readNextLine();
      }
   }

   private void startInteraction() {
      Vector input = new Vector();
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Interaction>")) {
         input.add(line);
         line = readNextLine();
      }
      NewInteraction interact = interactionFactory.makeInteraction(input);
      interactions.put(interact.getName(), interact);
//      singleIntx = interactionFactory.makeInteraction(input);
   }

   private void startTicket() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Name>"), new Integer(0));
      tags.put(new String("<Type>"), new Integer(1));
      tags.put(new String("<Frame>"), new Integer(2));
      String name = null;
      SequentialSceneTicket tkt = null;
      Vector frames = new Vector();
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</Ticket>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // Name
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Name>")) {
                  name = line;
                  line = readNextLine();
               }
               break;
            case 1: // Type
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Type>")) {
                  if (line.equalsIgnoreCase("Sequential")) {
                     tkt = new SequentialSceneTicket(name);
                  }
                  else {
                     System.out.println("* Error * SceneFactory:Ticket - " +
                                        "Unknow ticket type [" + line + "]");
                  }
                  line = readNextLine();
               }
               break;
            case 2: // Frame
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Frame>")) {
                  frames.add(line);
                  line = readNextLine();
               }
               break;
         }
      }
      TemporaryTicket t = new TemporaryTicket(name, tkt, frames);
      tickets.put(name, t);
   }

   private void startSceneTicket() {
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</SceneTicket>")) {
         line = readNextLine();
         sceneTicketFrames.add(line);
         line = readNextLine();
      }
   }

   private void startCharacter() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Character>")) {
         if (gameCharacters.containsKey(line)) {
            sceneCharacters.add(line);
         }
         else {
            System.out.println("* Error * SceneFactory:startCharacter - " +
                               "Character [" + line + " not found in cast of game characters");
         }
         line = readNextLine();
      }
   }

   private class TemporaryTicket {

      private String name;
      private SequentialSceneTicket tick;
      private Vector frames;

      public TemporaryTicket(String n, SequentialSceneTicket t, Vector f) {
         name = n;
         tick = t;
         frames = f;
      }

      public String getName() {
         return name;
      }

      public SequentialSceneTicket getTicket() {
         return tick;
      }

      public Vector getFrames() {
         return frames;
      }
   }


} /* SceneFactory*/



