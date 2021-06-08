package com.armygame.recruits.utils.observablevalues;


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
 * @version 2.0
 *
 * 2/9/02: minValue and maxValue added to data
 *
 * Read info from a file and instantiate a hashtable of scene references.
 */

public class ObservableValueFactory {

   private Hashtable observableValues;
   //private File definitions;
   private String definitions;
   private String name;
   private double initialValue;
   private double minValue;
   private double maxValue;
   private Hashtable commands;
   private BufferedReader br;
   private Vector sourceValues;

   public ObservableValueFactory() {
      observableValues = new Hashtable();
      commands = new Hashtable();
      sourceValues = new Vector();
      commands.put(new String("<StartValue>"), new Integer(0));
      commands.put(new String("<StartName>"), new Integer(1));
      commands.put(new String("<MinValue>"), new Integer(2));
      commands.put(new String("<MaxValue>"), new Integer(3));
      commands.put(new String("<StartInitialValue>"), new Integer(4));
      commands.put(new String("<StartSourceValue>"), new Integer(5));
      commands.put(new String("<EndValue>"), new Integer(6));
   }

   //public Hashtable makeObservableValues(File defs) {
   public Hashtable makeObservableValues(String defs) {
      definitions = defs;
      int i;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(definitions)); //new FileReader(definitions));
         String line = new String();
         while ((line = br.readLine()) != null) {
            if ((line.charAt(0)) == '-') {
               i = 7;
            }
            else {
               i = ((Integer) commands.get(line)).intValue();
            }
            switch (i) {
               case 0: // StartObservableValue
                  startValue();
                  break;
               case 1: // StartName
                  startName();
                  break;
               case 2: // MinValue
                  minValue();
                  break;
               case 3: // MaxValue
                  maxValue();
                  break;
               case 4: // StartInitialValue
                  startInitialValue();
                  break;
               case 5: // StartSourceValue
                  startSourceValue();
                  break;
               case 6: // EndValue
                  endValue();
                  break;
               case 7: // Comment
                  break;
            }
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeObservableValues) - Input file error " +
                            definitions + ": " + e.toString());
      }
      // Register listeners
      ObservableValue listeningValue;
      ObservableValue broadcaster;
      Enumeration enum = observableValues.elements();
      String broadcastingValue;
      while (enum.hasMoreElements()) {
         listeningValue = (ObservableValue) enum.nextElement();
         if (listeningValue.hasSourceValues()) {
            Enumeration enum1 = listeningValue.getSourceValueNames();
            while (enum1.hasMoreElements()) {
               broadcastingValue = (String) enum1.nextElement();
               broadcaster = (ObservableValue) observableValues.get(broadcastingValue);
               broadcaster.addValueChangeListener(listeningValue);
            }
         }
      }


      return observableValues;
   }

   private void startValue() {
      name = "no name";
      initialValue = 0.0;
      minValue = Double.NEGATIVE_INFINITY;
      maxValue = Double.POSITIVE_INFINITY;
      sourceValues = new Vector();
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
         System.err.println("Error(makeObservableValues: startName) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void minValue() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<\\MinValue>")) {
            minValue = (new Double(line)).doubleValue();
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeObservableValues: minValue) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void maxValue() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<\\MaxValue>")) {
            maxValue = (new Double(line)).doubleValue();
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeObservableValues: maxValue) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startInitialValue() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndInitialValue>")) {
            initialValue = (new Double(line)).doubleValue();
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeObservableValues: startInitialValue) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void startSourceValue() {
      try {
         String line = br.readLine();
         while (!line.equalsIgnoreCase("<EndSourceValue>")) {
            StringTokenizer tokens = new StringTokenizer(line, ",");
            Object source[] = new Object[2];
            source[0] = new String(tokens.nextToken());
            source[1] = new Double(new String(tokens.nextToken()));
            sourceValues.add(source);
            line = br.readLine();
         }
      }
      catch (IOException e) {
         System.err.println("Error(makeObservableValues: startSourceValues) - Input file error " +
                            definitions + ": " + e.toString());
      }
   }

   private void endValue() {
      ObservableValue oV = new ObservableValue(name, initialValue, sourceValues, minValue, maxValue);
      observableValues.put(name, oV);
   }

}