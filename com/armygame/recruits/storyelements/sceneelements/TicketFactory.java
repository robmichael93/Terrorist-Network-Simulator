package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.globals.ResourceReader;

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

public class TicketFactory {

   private Hashtable tickets;
   private Hashtable scenes;
   private Hashtable sceneRefs;
   private Hashtable ticketRefs;
   private Vector keys;
   //private File definitions;
   private String definitions;
   private String name;
   private String description;
   private String duration;
   private Hashtable commands;
   private BufferedReader br;
   private int nextAvailableFrame;
   private SequentialStoryTicket newTicket;

   public TicketFactory() {
      commands = new Hashtable();
      commands.put(new String("<StartTicket>"), new Integer(0));
      commands.put(new String("<StartName>"), new Integer(1));
      commands.put(new String("<StartDescription>"), new Integer(2));
      commands.put(new String("<StartDuration>"), new Integer(3));
      commands.put(new String("<StartKey>"), new Integer(4));
      commands.put(new String("<SceneReference>"), new Integer(5));
      commands.put(new String("<TicketReference>"), new Integer(6));
      commands.put(new String("<Scene>"), new Integer(7));
      commands.put(new String("<Ticket>"), new Integer(8));
      commands.put(new String("<EndTicket>"), new Integer(9));
      tickets = new Hashtable();
      keys = new Vector();
   }

   public Hashtable makeTickets(Hashtable sceneCollection, Hashtable sceneReferences,
                               //Hashtable ticketReferences, File ticketDefs) {
                               Hashtable ticketReferences, String ticketDefs) {
      scenes = sceneCollection;
      sceneRefs = sceneReferences;
      ticketRefs = ticketReferences;
      definitions = ticketDefs;
      int i = 0;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(definitions)); //new FileReader(definitions));
         String line = new String();
         while ((line = br.readLine()) != null) {
            if ((line.charAt(0)) == '-') {
               i = 10;
            }
            else {
               i = ((Integer) commands.get(line)).intValue();
            }
            switch (i) {
               case 0: // StartTicket
                  startTicket();
                  break;
               case 1: // StartName
                  startName();
                  break;
               case 2: // StartDescription
                  startDescription();
                  break;
               case 3: // StartDuration
                  startDuration();
                  break;
               case 4: // StartKey
                  startKey();
                  break;
               case 5: // SceneReference
                  sceneReference();
                  break;
               case 6: // TicketReference
                  ticketReference();
                  break;
               case 7: // SceneObject
                  sceneObject();
                  break;
               case 8: // TicketObject
                  ticketObject();
                  break;
               case 9: // endTicket
                  endTicket();
                  break;
               case 10: // Comment
                  break;
            }
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeScenes) - Input file error " +
                            definitions + ": " + e.toString());
      }
      return tickets;
   }

   private void startTicket() {
      name = "none";
      description = "none";
      duration = "0";
      newTicket = new SequentialStoryTicket(name);
      nextAvailableFrame = 0;
   }


   private void startName() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndName>")) {
            name = line;
            line = br.readLine();
         }
         newTicket.setName(name);
      }
      catch (IOException e) {
         System.err.println("Error(startName) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startDescription() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndDescription>")) {
            description = line;
            line = br.readLine();
         }
         newTicket.setDescription(description);
      }
      catch (IOException e) {
         System.err.println("Error(startDescription) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startDuration() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndDuration>")) {
            duration = line;
            line = br.readLine();
         }
         newTicket.setDuration((new Integer(duration)).intValue());
      }
      catch (IOException e) {
         System.err.println("Error(startDuration) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startKey() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndKey>")) {
            keys.add(line);
            line = br.readLine();
         }
         newTicket.setKeys(keys);
      }
      catch (IOException e) {
         System.err.println("Error(startRangesOfInterest) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void sceneReference() {
      try {
         String line = br.readLine();
         ExecutableSceneElement ese = (ExecutableSceneElement) sceneRefs.get(line);
         if (ese != null) {
            newTicket.addFrame(ese, nextAvailableFrame);
            nextAvailableFrame ++;
         }
         else {
            System.out.println("* Error* Scene Reference not found: " + line);
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRequiredConnectors) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void ticketReference() {
      try {
         String line = br.readLine();
         ExecutableSceneElement ese = (ExecutableSceneElement) ticketRefs.get(line);
         if (ese != null) {
            newTicket.addFrame(ese, nextAvailableFrame);
            nextAvailableFrame ++;
         }
         else {
            System.out.println("* Error* Ticket Reference not found: " + line);
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRequiredConnectors) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void sceneObject() {
      try {
         String line = br.readLine();
         ExecutableSceneElement ese = (ExecutableSceneElement) scenes.get(line);
         if (ese != null) {
            newTicket.addFrame(ese, nextAvailableFrame);
            nextAvailableFrame ++;
         }
         else {
            System.out.println("* Error* Scene not found: " + line);
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRequiredConnectors) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void ticketObject() {
      try {
         String line = br.readLine();
         ExecutableSceneElement ese = (ExecutableSceneElement) tickets.get(line);
         if (ese != null) {
            newTicket.addFrame(ese, nextAvailableFrame);
            nextAvailableFrame ++;
         }
         else {
            System.out.println("* Error* Ticket not found: " + line);
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRequiredConnectors) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }


   private void endTicket() {
      if (nextAvailableFrame != 0) {
         tickets.put(name, newTicket);
      }
      else {
         System.out.println("No ticket added for ticket: " + name);
      }
      keys = new Vector();
   }
}