package com.armygame.recruits.utils;

import com.armygame.recruits.globals.ResourceReader;

import java.util.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class StateVectorManager {

   private Hashtable stateVectorRanges;
   private Hashtable bitNumberToRangeNameMap;
   private RangeMap rangeMap;
   private StateVector stateVector;
   private int nextAvailableSlot;
   private Vector fullStateVectorForOutput;

   public StateVectorManager(int rangeMapSize) {
      stateVectorRanges = new Hashtable();
      bitNumberToRangeNameMap = new Hashtable();
      rangeMap = new RangeMap(rangeMapSize);
      stateVector = new StateVector(rangeMap);
      fullStateVectorForOutput = new Vector();
      nextAvailableSlot = 0;
   }

   //public StateVectorManager(File inputFile) {
   public StateVectorManager(String inputFile) {
      BufferedReader br;
      stateVectorRanges = new Hashtable();
      bitNumberToRangeNameMap = new Hashtable();
      fullStateVectorForOutput = new Vector();
      int numberOfTokens;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(inputFile)); //new FileReader(inputFile));
         String line = new String();

         // First line is length of state vector in bits
         // "-" at the beginning of the line designates a comment line
         line = br.readLine();
         while (line.charAt(0) == '-') {
            line = br.readLine();
         }
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Integer bits = new Integer(tokens.nextToken());

         stateVectorRanges = new Hashtable();
         rangeMap = new RangeMap(bits.intValue());
         fullStateVectorForOutput.add(new String(bits.toString()));
         stateVector = new StateVector(rangeMap);

         //Second line is a range that covers the entire vector
         // "-" at the beginning of the line designates a comment line
         line = br.readLine();
         while (line.charAt(0) == '-') {
            line = br.readLine();
         }
         tokens = new StringTokenizer(line, ",");
         addRange(tokens.nextToken(), (new Integer(tokens.nextToken())).intValue(),
                                      (new Integer(tokens.nextToken())).intValue());
         nextAvailableSlot = 0;

         while (( line = br.readLine()) != null) {
            tokens = new StringTokenizer(line, ",");
            numberOfTokens=tokens.countTokens();
            if (line.charAt(0) == '-') {
               numberOfTokens = 0;
            }
            switch (numberOfTokens) {
               case 0:
                  break;
               case 2 :
                  addRange(tokens.nextToken(),
                          (new Integer(tokens.nextToken())).intValue());
                  break;
               case 3 :
                  addRange(tokens.nextToken(),
                          (new Integer(tokens.nextToken())).intValue(),
                          (new Integer(tokens.nextToken())).intValue());
                  break;
               default:
                  System.out.println("Error in state vector file format");
                  break;
            }
         }
         br.close();
      }
      catch (IOException e) {
         System.err.println("Error reading file " + inputFile + ": " + e.toString());
      }
   }


   public void addRange(String rangeName, int startBit, int stopBit) {
      StateRange sR = rangeMap.DefineRange(rangeName, startBit, stopBit);
      fullStateVectorForOutput.add(new String(rangeName + "," + new Integer(startBit).toString() +
                                                          "," + new Integer(stopBit).toString() ));
      StateVectorRange sVR = stateVector.MakeStateVectorRange(sR);
      if (stateVectorRanges.containsKey(rangeName)) {
         System.out.println("Error (StateVectorManager): Adding a duplicate range name (" + rangeName + ")");
      }
      else {
         stateVectorRanges.put(rangeName, sVR);
      }
      if (stopBit >= nextAvailableSlot) {
         nextAvailableSlot = stopBit + 1;
      }
   }

   public void addRange(String rangeName, int startBit) {
      StateRange sR = rangeMap.DefineRange(rangeName, startBit);
      fullStateVectorForOutput.add(new String(rangeName + "," + new Integer(startBit).toString() ));
      StateVectorRange sVR = stateVector.MakeStateVectorRange(sR);
      if (stateVectorRanges.containsKey(rangeName)) {
         System.out.println("Warning (StateVectorManager): Adding a duplicate range name (" + rangeName + ")");
      }
      else {
         stateVectorRanges.put(rangeName, sVR);
         bitNumberToRangeNameMap.put((new Integer(startBit)).toString(), rangeName);
      }
      if (startBit >= nextAvailableSlot) {
         nextAvailableSlot = startBit + 1;
      }
   }

   public StateVectorRange getRange(String range) {
      return (StateVectorRange)stateVectorRanges.get(range);
   }

   public StateVector getStateVector() {
      return stateVector;
   }

   public RangeMap getRangeMap() {
      return rangeMap;
   }

   public String getNameOfBitSetInRange(String rangeName) {
      int b = this.getRange(rangeName).getBitNumberOfSetBit();
      if (b != -1) {
         return (String) bitNumberToRangeNameMap.get( (new Integer(b)).toString());
      }
      else {
         return null;  // no bit was set in the range
      }
   }

   public void dumpStateVector() {
      Enumeration enum = stateVectorRanges.elements();
      Enumeration keys = stateVectorRanges.keys();
      while (enum.hasMoreElements()) {
         StateVectorRange svr = (StateVectorRange) enum.nextElement();
         String k = (String)keys.nextElement();
         //if (svr.IsSet(0)) {
            System.out.println(k + " " + svr.IsSet(0));
         //}
      }
   }

   public void outputStateVectorToFile(String fileName) {
      try {
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
         Enumeration enum = fullStateVectorForOutput.elements();
         while (enum.hasMoreElements()) {
            out.println((String)enum.nextElement());
         }
         out.close();
      }
      catch (Exception e) {
         System.out.println("** Error ** Unable to write to StateVectorManager output file");
      }
   }

   public int getNextAvailableSlot() {
      return nextAvailableSlot;
   }

}