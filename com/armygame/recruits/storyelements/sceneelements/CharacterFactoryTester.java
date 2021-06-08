/*
 * CharacterFactoryTester.java
 *
 * Created on December 9, 2002, 11:29 AM
 */

package com.armygame.recruits.storyelements.sceneelements;

import java.io.*;
import com.armygame.recruits.storyelements.sceneelements.CharacterFactory_1;
/**
 *
 * @author  robmichael
 */
public class CharacterFactoryTester {
   
   /** Creates a new instance of CharacterFactoryTester */
   public CharacterFactoryTester() {
   }
   
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      System.out.println("Testing original CharacterFactory...\nDefinition Files:");
      CharacterFactory cf = new CharacterFactory("C:/Documents and Settings/Admin/My Documents/Thesis/data/CharacterFactoryConfig.xml");
      System.out.println(cf.ObservableValueDefinitions());
      System.out.println(cf.ConnectorDefinitions());
      System.out.println(cf.TypeValueConnectorDefinitions());
      System.out.println(cf.StateVectorDefinition());
      System.out.println(cf.GoalDefinitions());
      System.out.println(cf.PossessionDefinitions());

      System.out.println("\nTesting new CharacterFactory...\nDefinition Files:");
      CharacterFactory_1 cf1 = new CharacterFactory_1("C:/Documents and Settings/Admin/My Documents/Thesis/data/CharacterFactoryConfig.xml");
      System.out.println(cf1.ObservableValueDefinitions());
      System.out.println(cf1.ConnectorDefinitions());
      System.out.println(cf1.TypeValueConnectorDefinitions());
      System.out.println(cf1.StateVectorDefinition());
      System.out.println(cf1.GoalDefinitions());
      System.out.println(cf1.PossessionDefinitions());
}
   
}
