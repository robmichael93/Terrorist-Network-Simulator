package com.armygame.recruits.storyelements.sceneelements;

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

public class PossessionsFactory {

   //private File possessionDefFile;
   private String possessionDefFile;
   private BufferedReader br;

   private Hashtable commands;

   private Hashtable possessions;
   private String name;
   private String cost;
   private String recurringCost;
   private String frequency;
   private String numberOfPayments;
   private String repossess;
   private Vector repoActions;

   public PossessionsFactory(String /*File*/ possessionDefinitionFile) {

      possessionDefFile = possessionDefinitionFile;

      initializeReader(possessionDefFile);

      possessions = new Hashtable();

      commands = new Hashtable();
      commands.put(new String("<PossessionList>"), new Integer(0));
      commands.put(new String("<Possession>"), new Integer(1));
      commands.put(new String("<Name>"), new Integer(2));
      commands.put(new String("<Cost>"), new Integer(3));
      commands.put(new String("<RecurringCost>"), new Integer(4));
      commands.put(new String("<Frequency>"), new Integer(5));
      commands.put(new String("<NumberOfPayments>"), new Integer(6));
      commands.put(new String("<Repossess>"), new Integer(7));
      commands.put(new String("<RepossessionActions>"), new Integer(8));
      commands.put(new String("</Possession>"), new Integer(9));
      commands.put(new String("</PossessionList>"), new Integer(0));

   }

   private void initializeReader(String /*File*/ inputFile)
   {
      try {
         br = new BufferedReader(ResourceReader.getInputReader(inputFile));  //(new FileReader(inputFile));
      }
      catch (IOException e) {
         System.err.println("user.dir "+System.getProperty("user.dir"));
         System.err.println("Error(PossessionFactory) "+e+" - unable to create BufferedReader" +
                            " for file " + inputFile);
      }
   }

   public Hashtable buildPossessionTable() {
      int i = 0;
      String line = new String();

      while ((line = readNextLine()) != null) {
         if ((line.charAt(0)) == '-') {
            i = 10;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 0: //PossessionList
               break;
            case 1: //Possession
               startPossession();
               break;
            case 2: //Name
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Name>")) {
                  name = line;
                  line = readNextLine();
               }
               break;
            case 3: //Cost
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Cost>")) {
                  cost = line;
                  line = readNextLine();
               }
               break;
            case 4: //RecurringCost
               line = readNextLine();
               while (!line.equalsIgnoreCase("</RecurringCost>")) {
                  recurringCost = line;
                  line = readNextLine();
               }
               break;
            case 5: //Frequency
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Frequency>")) {
                  frequency = line;
                  line = readNextLine();
               }
               if ( (new Integer(frequency).intValue()) <= 0) {
                  frequency = "1";
                  System.out.println("** Error(PossessionFactory):(" + name + ") Invalid frequency");
               }
               break;
            case 6: //NumberOfPayments
               line = readNextLine();
               while (!line.equalsIgnoreCase("</NumberOfPayments>")) {
                  numberOfPayments = line;
                  line = readNextLine();
               }
               break;
            case 7: //Repossess
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Repossess>")) {
                  repossess = line;
                  line = readNextLine();
               }
               break;
            case 8: //RepossessionActions
               line = readNextLine();
               Vector input = new Vector();
               while (!line.equalsIgnoreCase("</RepossessionActions>")) {
                  input.add(line);
                  line = readNextLine();
               }
               startRepossessionActions(input);
               break;
            case 9: // /Possession
               makePossession();
               break;
            case 10: //comment
               break;
         }
      }
      return possessions;
   }

   private void startPossession() {
      name = "Undefined Possession";
      cost = "0";
      recurringCost = "0";
      frequency = "1";
      numberOfPayments = "0";
      repossess = "true";
      repoActions = new Vector();
   }

   private void startRepossessionActions(Vector input) {
      String line = null;
      Enumeration enum = input.elements();
      while (enum.hasMoreElements()) {
         line = (String) enum.nextElement();  // get the start tag
         if ( (line.equalsIgnoreCase("<UpdateResource>")) |
              (line.equalsIgnoreCase("<UpdateValue>")) ) {
            line = (String) enum.nextElement(); // get the data
            StringTokenizer tokens = new StringTokenizer(line, ",");
            Object temp[] = new Object[2];
            temp[0] = new String(tokens.nextToken());
            temp[1] = new String(tokens.nextToken());
            repoActions.add(temp);
         }
         else {
            System.out.println("** Error (PossessionFactory:repossessionActions): Invalid action or action format("
                               + line + ")");
         }
         line = (String) enum.nextElement(); // eat the ending tag
      }
   }

   private void makePossession() {
      Possession p = new Possession(name, new Integer(cost).intValue(), new Integer(recurringCost).intValue(),
                                    new Integer(frequency).intValue(), new Integer(numberOfPayments).intValue(),
                                    new Boolean(repossess).booleanValue(), repoActions);
      if (!possessions.contains(p.getName())) {
         possessions.put(p.getName(),p);
      }
      else{
         System.out.println("** Error (PossessionFactory:MakePossession): Duplicate possession name (" +
                            p.getName() + ")");
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


}