package com.armygame.recruits.agentutils.connectors;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.utils.observablevalues.*;
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

public class ConnectorFactory {

   private Hashtable connectors;
   //private File definitions;
   private String definitions;
   private String name;
   private double lowerBound;
   private double upperBound;
   private Hashtable commands;
   private BufferedReader br;
   private String sourceValue;
   private Hashtable observableValues;

   public ConnectorFactory() {
      observableValues = new Hashtable();
      connectors = new Hashtable();
      commands = new Hashtable();
      commands.put(new String("<StartConnector>"), new Integer(0));
      commands.put(new String("<StartName>"), new Integer(1));
      commands.put(new String("<StartSourceValue>"), new Integer(2));
      commands.put(new String("<StartLowerBound>"), new Integer(3));
      commands.put(new String("<StartUpperBound>"), new Integer(4));
      commands.put(new String("<EndConnector>"), new Integer(5));
   }

   //public Hashtable makeConnectors(File defs, Hashtable obsValues) {
   public Hashtable makeConnectors(String defs, Hashtable obsValues) {
      definitions = defs;
      observableValues = obsValues;
      int i;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(definitions)); //new FileReader(definitions));
         String line = new String();
         while ((line = br.readLine()) != null) {
            if ((line.charAt(0)) == '-') {
               i = 6;
            }
            else {
               i = ((Integer) commands.get(line)).intValue();
            }
            switch (i) {
               case 0: // StartConnector
                  startConnector();
                  break;
               case 1: // StartName
                  startName();
                  break;
               case 2: // StartSourceValue
                  startSourceValue();
                  break;
               case 3: // StartLowerBound
                  startLowerBound();
                  break;
               case 4: // StartUpperBound
                  startUpperBound();
                  break;
               case 5: // endConnector
                  endConnector();
                  break;
               case 6: // comment line
                  break;
            }
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeConnectorss) - Input file error " +
                            definitions + ": " + e.toString());
      }
      return connectors;
   }

   private void startConnector() {
      name = "none";
      sourceValue = "none";
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
         System.err.println("Error(makeConnectorss: startName) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startSourceValue() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndSourceValue>")) {
            sourceValue = new String(line);
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeConnectorss: startSourceValue) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startLowerBound() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndLowerBound>")) {
            lowerBound = (new Double(line)).doubleValue();
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeConnectorss: startLowerBound) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startUpperBound() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndUpperBound>")) {
            upperBound = (new Double(line)).doubleValue();
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeConnectorss: startUpperBound) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void endConnector() {
      Connector c = new Connector(name, lowerBound, upperBound, sourceValue);
      ObservableValue observableSourceValue = (ObservableValue) observableValues.get(sourceValue);
      observableSourceValue.addValueChangeListener(c);
      connectors.put(name, c);
   }

}