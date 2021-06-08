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

public class StoryElementReferenceFactory {

   private AttributeTrie searchTrie;
   private StateVector sVec;
   private Hashtable storyElementReferences;
   //private File definitions;
   private String definitions;
   private String name;
   private String description;
   private Hashtable commands;
   private BufferedReader br;
   private Vector rangesOfInterest;
   private Vector requiredConnectors;
   private boolean typeIsScene;
   private MainCharacter currentProtag;

   public StoryElementReferenceFactory() {
      storyElementReferences = new Hashtable();
      commands = new Hashtable();
      rangesOfInterest = new Vector();
      requiredConnectors = new Vector();
      commands.put(new String("<StartReference>"), new Integer(0));
      commands.put(new String("<StartName>"), new Integer(1));
      commands.put(new String("<StartDescription>"), new Integer(2));
      commands.put(new String("<StartRangesOfInterest>"), new Integer(3));
      commands.put(new String("<StartRequiredConnectors>"), new Integer(4));
      commands.put(new String("<EndReference>"), new Integer(5));
   }

   //public Hashtable makeStoryElementReferences(AttributeTrie s, MainCharacter mainChar, File SEDefs) {
   public Hashtable makeStoryElementReferences(AttributeTrie s, MainCharacter mainChar, String SEDefs) {
      currentProtag = mainChar;
      return makeReferences(s, SEDefs);
   }

   //private Hashtable makeStoryElementReferences(AttributeTrie s, File SEDefs) {
   private Hashtable makeStoryElementReferences(AttributeTrie s, String SEDefs) {
      return makeReferences(s, SEDefs);
   }

   //private Hashtable makeReferences(AttributeTrie s, File defs) {
   private Hashtable makeReferences(AttributeTrie s, String defs) {
      searchTrie = s;
      definitions = defs;
      int i = 0;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(definitions));//new FileReader(definitions));
         String line = new String();
         while ((line = br.readLine()) != null) {
            if ((line.charAt(0)) == '-') {
               i = 6;
            }
            else {
               i = ((Integer) commands.get(line)).intValue();
            }
            switch (i) {
               case 0: // StartReference
                  startReference();
                  break;
               case 1: // StartName
                  startName();
                  break;
               case 2: // StartDescription
                  startDescription();
                  break;
               case 3: // StartRangesOfInterest
                  startRangesOfInterest();
                  break;
               case 4: // StartRequiredConnectors
                  startRequiredConnectors();
                  break;
               case 5: // EndReference
                  endReference();
                  break;
               case 6: // Comment
                  break;
            }
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeSceneReferences) - Input file error " +
                            definitions + ": " + e.toString());
      }
      return storyElementReferences;
   }

   private void startReference() {
      name = "none";
      description = "none";
      rangesOfInterest = new Vector();
      requiredConnectors = new Vector();
   }


   private void startName() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndName>")) {
            name = line;
            line = br.readLine();
         }
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
      }
      catch (IOException e) {
         System.err.println("Error(startDescription) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startRangesOfInterest() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndRangesOfInterest>")) {
            rangesOfInterest.add(line);
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRangesOfInterest) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startRequiredConnectors() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndRequiredConnectors>")) {
            StringTokenizer tokens = new StringTokenizer(line, ",");
            Object typeValue[] = new Object[2];
            typeValue[0] = new String(tokens.nextToken());
            typeValue[1] = new String(tokens.nextToken());
            requiredConnectors.add(typeValue);
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(startRequiredConnectors) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void endReference() {
      StoryElementReference sER = new StoryElementReference(searchTrie, currentProtag, rangesOfInterest, requiredConnectors, description);
      storyElementReferences.put(name, sER);
   }

}