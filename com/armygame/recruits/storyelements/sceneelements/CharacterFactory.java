package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.storyelements.characterstatesystem.*;
import com.armygame.recruits.storyelements.charactervaluesystem.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.globals.ResourceReader;

import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class CharacterFactory {

   //private File possessionDefinitions;
   //private File observableValueDefinitions;
   //private File connectorDefinitions;
   //private File typeValueConnectorDefinitions;
   //private File stateVectorDefinition;
   //private File goalDefinitions;

   private String possessionDefinitions;
   private String observableValueDefinitions;
   private String connectorDefinitions;
   private String typeValueConnectorDefinitions;
   private String stateVectorDefinition;
   private String goalDefinitions;

   private RangeMap characterRangeMap;

   //private File configurationFile;
   private String configurationFile;
   private Hashtable commands;

   private BufferedReader br;

   //public CharacterFactory(File configFile) {
   public CharacterFactory(String configFile) {
      configurationFile = configFile;

      commands = new Hashtable();
      commands.put(new String("<ObservableValues>"), new Integer(0));
      commands.put(new String("<Connectors>"), new Integer(1));
      commands.put(new String("<TypeValueConnectors>"), new Integer(2));
      commands.put(new String("<StateVector>"), new Integer(3));
      commands.put(new String("<Goals>"), new Integer(4));
      commands.put(new String("<Possessions>"), new Integer(5));
      commands.put(new String("<CharacterFactoryConfiguration>"), new Integer(6));
      commands.put(new String("</CharacterFactoryConfiguration>"), new Integer(6));


      initializeReader(configurationFile);

      setDefinitionFiles();
   }

   //private void initializeReader(File inputFile)
   private void initializeReader(String inputFile)
   {
      try {
        // = new BufferedReader(new FileReader(inputFile));
//        br = new BufferedReader(ResourceReader.getInputReader(inputFile)); //new FileReader(inputFile));
        br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
      }
      catch (IOException e) {
        System.err.println("user.dir "+System.getProperty("user.dir"));
        System.err.println("Error(GoalManager) "+e+" - unable to create BufferedReader" +
                            " for file " + inputFile);
      }
   }

   private void setDefinitionFiles() {
      int i = 0;
      String line = new String();

      while ((line = readNextLine()) != null) {
         if ((line.charAt(0)) == '-') {
            i = 7;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 0: //ObservableValues
               startObservableValues();
               break;
            case 1: //Connectors
               startConnectors();
               break;
            case 2: //TypeValueConnectors
               startTypeValueConnectors();
               break;
            case 3: //StateVector
               startStateVector();
               break;
            case 4: //Goals
               startGoals();
               break;
            case 5: //Goals
               startPossessions();
               break;
            case 6: //CharacterFactoryConfiguration
               break;
            case 7: //comment
               break;
         }
      }
//      StateVectorManager stateVectorMgr = new StateVectorManager(stateVectorDefinition);
   }

   public MainCharacter makeCharacter(String name, String rank, int timeInService) {

      StateVectorManager stateVectorMgr = new StateVectorManager(stateVectorDefinition);
      characterRangeMap = stateVectorMgr.getRangeMap();

      MainCharacterValueSystem values = new MainCharacterValueSystem(observableValueDefinitions, connectorDefinitions);

      CharacterStateSystem connectorState = new CharacterStateSystem();
      connectorState.buildConnectors(typeValueConnectorDefinitions);

      Enumeration connectorList = values.getConnectors().getConnectorTable().keys();
      Enumeration typeValConnectorList = connectorState.getConnectors().getConnectorTable().keys();
      Enumeration obsValueList = values.getObservableValues().getObservableValuesTable().keys();

      GoalManager goalManager = new GoalManager(connectorList, typeValConnectorList, obsValueList, goalDefinitions, possessionDefinitions);
      values.addObservableValueListenerAllValues(goalManager);
      com.armygame.recruits.StoryEngine.instance().clock().addClockListener(goalManager);
      com.armygame.recruits.StoryEngine.instance().clock().addClockListener(values);

      MainCharacter newChar = new MainCharacter(name);

      newChar.setStoryState(stateVectorMgr);
      newChar.setMainCharacterValueSystem(values);
      newChar.setCharacterStateSystem(connectorState);
      newChar.setGoalManager(goalManager);

      newChar.initializeCharacter(rank, timeInService);

      stateVectorMgr.outputStateVectorToFile("storyStateVectorOutput.txt");

      return newChar;
   }

   private void startObservableValues() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</ObservableValues>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         observableValueDefinitions = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) Observable Values definition file not set");
      }
   }

   private void startConnectors() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</Connectors>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         connectorDefinitions = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) Connectors definition file not set");
      }
   }

   private void startTypeValueConnectors() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</TypeValueConnectors>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         typeValueConnectorDefinitions = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) TypeValueConnectors definition file not set");
      }
   }

   private void startStateVector() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</StateVector>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         stateVectorDefinition = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) StateVector definition file not set");
      }
   }

   private void startGoals() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</Goals>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         goalDefinitions = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) Goals definition file not set");
      }
   }

   private void startPossessions() {
      String line = readNextLine();
      String fileName = null;
      while (!line.equalsIgnoreCase("</Possessions>")) {
         fileName = new String(line);
         line = readNextLine();
      }
      if (fileName != null) {
         possessionDefinitions = fileName; //getFile(fileName);
      }
      else {
         System.err.println("Error (CharacterFactory) Possessions definition file not set");
      }
   }

   private String readNextLine() {
      try {
         return br.readLine();
      }
      catch (IOException e) {
         System.err.println("Error (CharacterFactory) - Unable to read from file.");
         return null;
      }
   }

   //private File getFile(String fileName) {
   //   return new File(fileName);
   //}
   public String ObservableValueDefinitions () {
      return observableValueDefinitions;
   } // end ObservableValueDefinitions
   
   public String ConnectorDefinitions () {
      return connectorDefinitions;
   } // end ConnectorDefinitions
   
   public String TypeValueConnectorDefinitions () {
      return typeValueConnectorDefinitions;
   } // end typeValueConnectorDefinitions
   
   public String StateVectorDefinition () {
      return stateVectorDefinition;
   } // end StateVectorDefinition
   
   public String GoalDefinitions () {
      return goalDefinitions;
   } // end GoalDefinitions
   
   public String PossessionDefinitions () {
      return possessionDefinitions;
   } // end PossessionDefinitions

}
