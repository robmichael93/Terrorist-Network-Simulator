package com.armygame.recruits.utils.observablevalues;

import java.util.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.agentutils.connectors.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class FactoryTest {

   public static void main(String[] args) {

      ObservableValueFactory ovFactory = new ObservableValueFactory();
      Hashtable observableValues = ovFactory.makeObservableValues("ObservableValues.txt"); //new File("ObservableValues.txt"));
      ObservableValue leadership = (ObservableValue) observableValues.get("leadership");
      ObservableValue charisma = (ObservableValue) observableValues.get("charisma");
      ObservableValue social = (ObservableValue) observableValues.get("social");
      ObservableValue ambition = (ObservableValue) observableValues.get("ambition");
      System.out.println("Leadership = " + leadership.getValue());
      System.out.println("Charisma = " + charisma.getValue());
      System.out.println("Social = " + social.getValue());
      System.out.println("Ambition = " + ambition.getValue());
      charisma.changeValue(20.0);
      System.out.println("Change charisma to 20.0");
      System.out.println("Leadership = " + leadership.getValue());
      System.out.println("Charisma = " + charisma.getValue());
      charisma.incrementValue(5.0);
      System.out.println("Leadership = " + leadership.getValue());
      System.out.println("Charisma = " + charisma.getValue());

      ConnectorFactory connFactory = new ConnectorFactory();
      Hashtable connectors = connFactory.makeConnectors("connectors.txt",observableValues); //new File("connectors.txt"), observableValues);
      Connector weakLeader = (Connector) connectors.get("weakLeader");
      Connector moderateLeader = (Connector) connectors.get("moderateLeader");
      Connector strongLeader = (Connector) connectors.get("strongLeader");
      System.out.println(weakLeader.isExtended() + " " +
                         moderateLeader.isExtended() + " " +
                         strongLeader.isExtended());
      charisma.incrementValue(80.0);
      ambition.changeValue(100.0);
      social.changeValue(100.0);
      System.out.println("Leadership = " + leadership.getValue());

      System.out.println(weakLeader.isExtended() + " " +
                         moderateLeader.isExtended() + " " +
                         strongLeader.isExtended());




  }
}