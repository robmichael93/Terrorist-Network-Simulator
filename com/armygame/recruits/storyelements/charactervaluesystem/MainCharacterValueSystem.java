package com.armygame.recruits.storyelements.charactervaluesystem;

import java.io.*;
import java.util.*;

import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.agentutils.connectors.*;
import com.armygame.recruits.utils.*;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 3.0
 * (Ver 3.0) This version creates the connectors and observable values from a
 * file.  It removes all "by name" reference to the values"
 *
 * (Ver 2.0)this version includes methods to register a StateVector with the connectors
 * in the value system
 */

public class MainCharacterValueSystem implements GameClockListener {

   private ObservableValuesHashTable observableValues;
   private ConnectorHashTable connectors;
   private ConnectorFactory connectorFactory;
   private ObservableValueFactory observableValueFactory;
   //private File observableValuesDefinitions;
   //private File connectorDefinitions;
   private String observableValuesDefinitions;
   private String connectorDefinitions;

   //public MainCharacterValueSystem(File obsValDefs, File connDefs) {
   public MainCharacterValueSystem(String obsValDefs, String connDefs) {

      observableValuesDefinitions = obsValDefs;
      connectorDefinitions = connDefs;
      connectors = new ConnectorHashTable();
      connectorFactory = new ConnectorFactory();
      observableValues = new ObservableValuesHashTable();
      observableValueFactory = new ObservableValueFactory();

      observableValues.setHashTable(observableValueFactory.makeObservableValues(observableValuesDefinitions));

      connectors.setHashTable(connectorFactory.makeConnectors(connectorDefinitions,
                              observableValues.getObservableValuesTable()));
      /*
      *  Need to set the initial values for the personalities, traits, etc
      *  after all of the listeners have been established and lilnked.  If this
      *  is not done last, then the initial values will not propogate through
      *  the network of dependencies established above.
      */

      observableValues.broadcastStatus();

   } // end constructor

//   public void addConnectorChangeListener(Connector c, ConnectorChangeListener ccl) {
//      c.addConnectorChangeListener(ccl);
//   }
//
//   public void removeConnectorChangeListener(Connector c, ConnectorChangeListener ccl) {
//      c.removeConnectorChangeListener(ccl);
//   }

   public boolean updateTrait(String trait, double delta) {
      if (observableValues.containsValue(trait)) {
         //System.out.println("Updating trait: " + trait + " by " + delta);
         observableValues.getObservableValue(trait).incrementValue(delta);
         return true;
      }
      return false;
   }

   public void registerWithStateVector(StateVectorManager sVM) {
      Enumeration enum = (connectors.getConnectorTable()).elements();
      Connector c;
      while (enum.hasMoreElements()) {
         c = (Connector) enum.nextElement();
         boolean b = c.registerStateVector(sVM);
         if (!b) {
            System.out.println("Register error(MCVS): No StateVectorRange found for " + c.getName());
         }
      }
   }

   public void unregisterWithStateVector(StateVectorManager sVM) {
      Enumeration enum = (connectors.getConnectorTable()).elements();
      Connector c;
      while (enum.hasMoreElements()) {
         c = (Connector) enum.nextElement();
         boolean b = c.unregisterStateVector(sVM);
         if (!b) {
            System.out.println("Unregister error: No StateVectorRange found for " + c.getName());
         }
      }
   }

   /* Add a listener to every connector in the connector hashtable. */
   public void addConnectorChangeListener(ConnectorChangeListener cCL) {
      Enumeration enum = connectors.getConnectorTable().elements();
      while (enum.hasMoreElements()) {
         ((Connector) enum.nextElement()).addConnectorChangeListener(cCL);
      }
   }

   public void reportConnectorsStatus() {
      connectors.reportConnectorsStatus();
   }

   public void setValue(String valueName, double newValue) {
      observableValues.getObservableValue(valueName).changeValue(newValue);
   }

   public double getValue(String valueName) {
      ObservableValue oV = observableValues.getObservableValue(valueName);
      if (oV != null) {
         return oV.getValue();
      }
      else {
         System.out.println("* Error:MainCharValueSystem: getValue - " +
                            "ValueName not found " + valueName);
         return 0.0;
      }
   }

   public void addObservableValueListenerAllValues(ValueChangeListener vcL) {
      observableValues.addValueChangeListenerAllValues(vcL);
   }

   public double getTotalPoints(Vector values) {
      double total = 0.0;
      double currentVal = 0.0;
      String valueName;
      Enumeration enum = values.elements();
      while (enum.hasMoreElements()) {
         valueName = (String) enum.nextElement();
         currentVal = observableValues.getObservableValue(valueName).getValue();
         total += currentVal;
      }
      return total;
   }

   public void gameClockChanged(GameClockChangeEvent e) {
      setValue("time", (double)e.getTime());
   }

   public CharInsides getAttributes(CharInsides charInner) {

      /* Character Values */
      charInner.loyalty = observableValues.getObservableValue("loyalty").getValue();
      charInner.duty = observableValues.getObservableValue("duty").getValue();
      charInner.respect = observableValues.getObservableValue("respect").getValue();
      charInner.selfless = observableValues.getObservableValue("selflessService").getValue();
      charInner.honor = observableValues.getObservableValue("honor").getValue();
      charInner.integrity = observableValues.getObservableValue("integrity").getValue();
      charInner.courage = observableValues.getObservableValue("personalCourage").getValue();

      /* Character Resources */
      charInner.energy = observableValues.getObservableValue("energy").getValue();
      charInner.energylast = observableValues.getObservableValue("energy").getLastValue();
      charInner.skill = observableValues.getObservableValue("skill").getValue();
      charInner.skilllast = observableValues.getObservableValue("skill").getLastValue();
      charInner.knowledge = observableValues.getObservableValue("knowledge").getValue();
      charInner.knowledgelast = observableValues.getObservableValue("knowledge").getLastValue();
      charInner.financial = observableValues.getObservableValue("financial").getValue();
      System.out.println("** ** Financial value is : " + charInner.financial);
      charInner.financiallast = observableValues.getObservableValue("financial").getLastValue();
      charInner.strength = observableValues.getObservableValue("strength").getValue();
      charInner.strengthlast = observableValues.getObservableValue("strength").getLastValue();
      charInner.popularity = observableValues.getObservableValue("popularity").getValue();
      charInner.popularitylast = observableValues.getObservableValue("popularity").getLastValue();

      charInner.bankBalance = observableValues.getObservableValue("bankBalance").getValue();
      charInner.bankBalancelast = observableValues.getObservableValue("bankBalance").getLastValue();
      charInner.time = observableValues.getObservableValue("time").getValue();

      /* unallocated Value and Goal points*/
      charInner.unallocated = observableValues.getObservableValue("unallocatedValuePts").getValue();
      //charInner.unallocatedGoalPts = observableValues.getObservableValue("unallocatedGoalPts").getValue();

      /* goals */
      charInner.goalString = "* Goals *";

      return charInner;
   }

   public void setAttributesFromCharInsides(CharInsides charInner) {
      observableValues.getObservableValue("loyalty").changeValue((new Double(charInner.loyalty)).doubleValue());
      observableValues.getObservableValue("duty").changeValue((new Double(charInner.duty)).doubleValue());
      observableValues.getObservableValue("respect").changeValue((new Double(charInner.respect)).doubleValue());
      observableValues.getObservableValue("selflessService").changeValue((new Double(charInner.selfless)).doubleValue());
      observableValues.getObservableValue("honor").changeValue((new Double(charInner.honor)).doubleValue());
      observableValues.getObservableValue("integrity").changeValue((new Double(charInner.integrity)).doubleValue());
      observableValues.getObservableValue("personalCourage").changeValue((new Double(charInner.courage)).doubleValue());

      observableValues.getObservableValue("energy").changeValue((new Double(charInner.energy)).doubleValue());
      observableValues.getObservableValue("skill").changeValue((new Double(charInner.skill)).doubleValue());
      observableValues.getObservableValue("knowledge").changeValue((new Double(charInner.knowledge)).doubleValue());
      observableValues.getObservableValue("financial").changeValue((new Double(charInner.financial)).doubleValue());
      observableValues.getObservableValue("strength").changeValue((new Double(charInner.strength)).doubleValue());
      observableValues.getObservableValue("popularity").changeValue((new Double(charInner.popularity)).doubleValue());
      observableValues.getObservableValue("bankBalance").changeValue((new Double(charInner.bankBalance)).doubleValue());

      observableValues.getObservableValue("unallocatedValuePts").changeValue((new Double(charInner.unallocated)).doubleValue());
   }

   public String[] listActiveConnectors() {
      return connectors.listActiveConnectors();
   }

   public ConnectorHashTable getConnectors() {
      //return connectors.getConnectorTable();
      return connectors;
   }

   public ObservableValuesHashTable getObservableValues() {
      return observableValues;
   }
}