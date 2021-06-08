package com.armygame.recruits.storyelements.characterstatesystem;

import java.util.*;
import java.io.*;

import com.armygame.recruits.utils.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.globals.ResourceReader;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class CharacterStateSystem {

   private TypeValueConnectorHashTable connectors;

   public CharacterStateSystem() {
      connectors = new TypeValueConnectorHashTable();
   }

   //public void buildConnectors(File connectorDefinitions) {
   public void buildConnectors(String connectorDefinitions) {
      BufferedReader br;
      String type = null;
      try {
         br = new BufferedReader(ResourceReader.getInputReader(connectorDefinitions)); //new FileReader(connectorDefinitions));
         String line = new String();
         // get length of state vector in bits

         while (( line = br.readLine()) != null) {
            if (line.equalsIgnoreCase("<StartConnector>")) {
               Vector values = new Vector();
               type = br.readLine();
               line = br.readLine();
               while (!line.equalsIgnoreCase("<EndConnector>")) {
                  values.add(line);
                  line = br.readLine();
               }
               connectors.put(type,new TypeValueConnector(type,values));
            }
         }
         br.close();
      }
      catch (IOException e) {
         System.err.println("Error reading file " + connectorDefinitions + ": " + e.toString());
      }
   }

   public void registerWithStateVector(StateVectorManager sVM) {
      Enumeration enum = connectors.elements();
      TypeValueConnector tVC;
      while (enum.hasMoreElements()) {
         tVC = (TypeValueConnector) enum.nextElement();
         boolean b = tVC.registerStateVector(sVM);
         if (!b) {
            System.out.println("Register error (CSS): No StateVectorRange found for " + tVC.getName());
         }
      }
   }

   public void unregisterWithStateVector(StateVectorManager sVM) {
      Enumeration enum = connectors.elements();
      TypeValueConnector tVC;
      while (enum.hasMoreElements()) {
         tVC = (TypeValueConnector) enum.nextElement();
         tVC.unregisterStateVector(sVM);
      }
   }

   public void addTypeValueConnectorChangeListener(TypeValueConnectorChangeListener tVCCL) {
      Enumeration enum = connectors.getConnectorTable().elements();
      while (enum.hasMoreElements()) {
         ((TypeValueConnector) enum.nextElement()).addTypeValueConnectorChangeListener(tVCCL);
      }
   }

   public String getConnectorValue(String type) {
      return ((TypeValueConnector)connectors.get(type)).getValue();
   }

   public Vector getConnectorValueSet(String type) {
      return ((TypeValueConnector)connectors.get(type)).getValueSet();
   }

   public void changeConnectorValue(String type, String value) {
      //System.out.println("Change Connector Value: Type = " + type + " Value= " + value);
      ((TypeValueConnector)connectors.get(type)).changeValue(value);
   }

   public TypeValueConnectorHashTable getConnectors() {
      return connectors;
   }

   public void reportConnectorStatus() {
      connectors.reportConnectorsStatus();
   }


}