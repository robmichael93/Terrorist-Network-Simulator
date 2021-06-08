package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.storyelements.characterstatesystem.*;
import com.armygame.recruits.storyelements.charactervaluesystem.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.globals.ResourceReader;
import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;

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

public class CharacterFactory_1 extends DefaultXMLSerializer implements XMLSerializable {

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

   public CharacterFactory_1(String configFile) {
      ParseXMLObject(configFile);
//      StateVectorManager stateVectorMgr = new StateVectorManager(stateVectorDefinition);      
   } // end CharacterFactory constructor


   public void setObservableValueDefinitions (String file) {
       observableValueDefinitions = file;
   } // end setObservableValueDefinitions
   
   public void setConnectorDefinitions (String file) {
       connectorDefinitions = file;
   } // end setConnectorDefinitions
   
   public void setTypeValueConnectorDefinitions (String file) {
       typeValueConnectorDefinitions = file;
   } // end setTypeValueConnectorDefinitions
   
   public void setStateVectorDefinition (String file) {
       stateVectorDefinition = file;
   } // end setStateVectorDefinitions
   
   public void setGoalDefinitions (String file) {
       goalDefinitions = file;
   } // end setGoalDefinitions
   
   public void setPossessionDefinitions (String file) {
       possessionDefinitions = file;
   } // end setPossessionsDefinitions
   
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

   public void ParseXMLObject(String file) {
       Parse(this, file);
   } // end method ParseXMLObject
   
} // end class CharacterFactory_1
