package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import com.armygame.recruits.locations.*;
import com.armygame.recruits.playlist.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ConditionActionFactory {

   private Hashtable commands;
   private Hashtable receiveIntents; /* intent, MyBoolean */
   private Hashtable receiveConnectors;/* role, Hashtable keyed on connector names */
   private Hashtable receiveTypeValueConnectors;/*role, Hashtable keyed on TVConnector names */
   private Hashtable goals;/* MY_CHARACTER, Hashtable keyed on goal names */
   private Hashtable observableValues; /* role, Hashtable keyed on ObsValue names */
   private Vector intentConnectorDependencies;
   private Vector connectorDependencies;
   private Vector typeValueConnectorDependencies;
   private Vector goalDependencies;
   private Vector obsValueDependencies;

   private final String MY_CHARACTER = "myCharacter";

   private Vector input;
   private ConditionAction newConditionAction;

   public ConditionActionFactory(Hashtable recIntents,
                                 Hashtable recConnectors,
                                 Hashtable recTypeValConnectors) {
      this(recIntents, recConnectors, recTypeValConnectors, null, null);
   }

   public ConditionActionFactory(Hashtable recIntents, /* intent, MyBoolean */
                                 Hashtable recConnectors, /* role, Hashtable */
                                 Hashtable recTypeValConnectors, /* role, Hashtable */
                                 Hashtable recGoals /* MY_CHARACTER, Hashtable */) {
      this(recIntents, recConnectors, recTypeValConnectors, recGoals, null);
   }
   public ConditionActionFactory(Hashtable recIntents, /* intent, MyBoolean */
                                 Hashtable recConnectors, /* role, Hashtable */
                                 Hashtable recTypeValConnectors, /* role, Hashtable */
                                 Hashtable recGoals, /* MY_CHARACTER, Hashtable */
                                 Hashtable recObsVals /* role, Hashtable */) {
      receiveIntents = recIntents;
      receiveConnectors = recConnectors;
      receiveTypeValueConnectors = recTypeValConnectors;
      goals = recGoals;
      observableValues = recObsVals;

      commands = new Hashtable();
      commands.put(new String("<IntentConnector>"), new Integer(1));
      commands.put(new String("<Connector>"), new Integer(2));
      commands.put(new String("<TypeValueConnector>"), new Integer(3));
      commands.put(new String("<UpdateValuePoints>"), new Integer(4));
      commands.put(new String("<SetLocation>"), new Integer(5));
      commands.put(new String("<TypeValueConnectorUpdate>"), new Integer(6));
      commands.put(new String("<TraitUpdate>"), new Integer(7));
      commands.put(new String("<PlaySounds>"), new Integer(8));
      commands.put(new String("<StopSound>"), new Integer(9));
      commands.put(new String("<StopAllSounds>"), new Integer(10));
      commands.put(new String("<PlayAnimation>"), new Integer(11));
      commands.put(new String("<PlayMovie>"), new Integer(12));
      commands.put(new String("<AddScreenTransition>"), new Integer(13));
      commands.put(new String("<OutputDescription>"), new Integer(14));
      commands.put(new String("<FrameDoneWhenCharsTakeExit>"), new Integer(15));
      commands.put(new String("<FrameDoneWhenSoundDone>"), new Integer(16));
      commands.put(new String("<ExtendIntentConnector>"), new Integer(17));
      commands.put(new String("<RetractIntentConnector>"), new Integer(18));
      commands.put(new String("<UpdateGoalPoints>"), new Integer(19));
      commands.put(new String("<UpdateValue>"), new Integer(20));
      commands.put(new String("<UpdateGoal>"), new Integer(21));
      commands.put(new String("<UpdateResource>"), new Integer(22));
      commands.put(new String("<GoalCondition>"), new Integer(23));
      commands.put(new String("<ObservableValueCondition>"), new Integer(24));
      commands.put(new String("<GoalOrCondition>"), new Integer(25));
      commands.put(new String("<ObtainPossession>"), new Integer(26));
      commands.put(new String("<CueChooser>"), new Integer(27));
      commands.put(new String("<DisplayQuip>"), new Integer(28));
      commands.put(new String("<PlaySentence>"), new Integer(29));

   }

   public ConditionAction makeConditionAction(Vector in) {
      input = in;
      int i = 0;
      String line = new String();
      createConditionAction();
      while ((line = readNextLine()) != null) {
         if ((line.charAt(0)) == '-') {
            i = 30;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 1: // IntentConnector
               createIntentConnectorCondition();
               break;
            case 2: // Connector
               createConnectorCondition();
               break;
            case 3: // TypeValueConnector
               createTypeValueConnectorCondition();
               break;
            case 4: // UpdateValuePoints
               createUpdateValuePointsAction();
               break;
            case 5: // SetLocation
               createSetLocationAction();
               break;
            case 6: // TypeValueConnectorUpdate
               createTypeValueConnectorUpdateAction();
               break;
            case 7: // TraitUpdate
               createTraitUpdateAction();
               break;
            case 8: // PlaySounds
               createPlaySoundsAction();
               break;
            case 9: // StopSounds
               createStopSoundAction();
               break;
            case 10: // StopAllSounds
               createStopAllSoundsAction();
               break;
            case 11: // PlayAnimation
               createPlayAnimationAction();
               break;
            case 12: // PlayMovie
               createPlayMovieAction();
               break;
            case 13: // AddScreenTransition
               createAddScreenTransitionAction();
               break;
            case 14: // OutputDescription
               createOutputDescriptionAction();
               break;
            case 15: // FrameDoneWhenCharsTakeExit
               createFrameDoneWhenCharsTakeExitAction();
               break;
            case 16: // FrameDoneWhenSoundDone
               createFrameDoneWhenSoundDoneAction();
               break;
            case 17: // ExtendIntentConnector
               createExtendIntentConnectorAction();
               break;
            case 18: // ExtendIntentConnector
               createRetractIntentConnectorAction();
               break;
            case 19: // UpdateGoalPoints
               createUpdateGoalPointsAction();
               break;
            case 20: // UpdateValue
               createUpdateValueAction();
               break;
            case 21: // UpdateGoal
               createUpdateGoalAction();
               break;
            case 22: // UpdateResource
               createUpdateResourceAction();
               break;
            case 23: // Goal Condition
               createGoalCondition();
               break;
            case 24: // ObservableValue Condition
               createObservableValueCondition();
               break;
            case 25: // GoalOr Condition
               createGoalOrCondition();
               break;
            case 26: // Obtain Possession
               createObtainPossessionAction();
               break;
            case 27: // Cue Chooser
               createCueChooserAction();
               break;
            case 28: // Display Quip
               createDisplayQuipAction();
               break;
            case 29: // PlaySentence
               createPlaySentenceAction();
               break;
            case 30: // comment
               break;
         }
      }
      newConditionAction.setIntentConnectorDependencies(intentConnectorDependencies);
      newConditionAction.setConnectorDependencies(connectorDependencies);
      newConditionAction.setTypeValueConnectorDependencies(typeValueConnectorDependencies);
      newConditionAction.setGoalDependencies(goalDependencies);
      newConditionAction.setObsValueDependencies(obsValueDependencies);
      return newConditionAction;

   } /* makeConditionAction */

   private void createConditionAction() {
      newConditionAction = new ConditionAction();
      intentConnectorDependencies = new Vector();
      connectorDependencies = new Vector();
      typeValueConnectorDependencies = new Vector();
      goalDependencies = new Vector();
      obsValueDependencies = new Vector();
   }

   private void createIntentConnectorCondition() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</IntentConnector>")) {
         String conn = line;
         Condition c = new IntentConnectorCondition((MyBoolean)receiveIntents.get(conn), conn);
         newConditionAction.addCondition(c);
         intentConnectorDependencies.add(conn);
         line = readNextLine();
      }
   } /* createIntentConnectorCondition */

   private void createConnectorCondition() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</Connector>")) {
         String role = null;
         String conn = null;
         StringTokenizer tokens = new StringTokenizer(line, ",");
         if (tokens.countTokens() == 1) {
            role = MY_CHARACTER;
         }
         else {
            role = tokens.nextToken();
         }
         conn = tokens.nextToken();
         Hashtable tempTable = (Hashtable) receiveConnectors.get(role);
         Condition c = new ConnectorCondition((MyBoolean)tempTable.get(conn), conn);
         newConditionAction.addCondition(c);
         String[] dep = new String[2];
         dep[0] = new String(role);
         dep[1] = new String(conn);
         connectorDependencies.add(dep);
         line = readNextLine();
      }
   } /* createConnectorCondition */

   private void createTypeValueConnectorCondition() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</TypeValueConnector>")) {
         String role = null;
         String conn = null;
         String targetVal = null;
         StringTokenizer tokens = new StringTokenizer(line, ",");
         if (tokens.countTokens() == 2) {
            role = MY_CHARACTER;
         }
         else {
            role = tokens.nextToken();
         }
         conn = tokens.nextToken();
         targetVal = new String(tokens.nextToken());
         Hashtable tempTable = (Hashtable) receiveTypeValueConnectors.get(role);
         Condition c = new TypeValueConnectorCondition((MyString)tempTable.get(conn), new MyString(targetVal), conn);
         newConditionAction.addCondition(c);
         String[] dep = new String[2];
         dep[0] = new String(role);
         dep[1] = new String(conn);
         typeValueConnectorDependencies.add(dep);
         line = readNextLine();
      }
   } /* createTypeValueConnectorCondition */

   private void createGoalCondition() {
         newConditionAction.addCondition(makeGoalCondition());
   }

   private Condition makeGoalCondition() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Role>"), new Integer(0));
      tags.put(new String("<RequiredGoal>"), new Integer(1));
      tags.put(new String("<Status>"), new Integer(2));
      String role = null;
      String goal = null;
      String status = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</GoalCondition>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: //<Role>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Role>")) {
                  role = line;
                  line = readNextLine();
               }
               break;
            case 1: // <Goal>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</RequiredGoal>")) {
                  goal = line;
                  line = readNextLine();
               }
               break;
            case 2: // <Status>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Status>")) {
                  status = line;
                  line = readNextLine();
               }
               break;
         }
      }
      if (role == null) {
         role = MY_CHARACTER;
      }
      if (status == null) {
         status = "achieved";
      }
      if (goal != null) {
         Hashtable tempTable = (Hashtable) goals.get(role);
         Condition c = new GoalCondition((MyString)tempTable.get(goal), new MyString(status), goal);
         //newConditionAction.addCondition(c);
         String[] dep = new String[2];
         dep[0] = new String(role);
         dep[1] = new String(goal);
         goalDependencies.add(dep);
         return c;
      }
      else {
         System.out.println("* Error (CreateGoalCondition): No Goal is set");
         return null;
      }
   } /* createGoalCondition */

   private void createGoalOrCondition() {
      String line = new String();
      Condition c = null;
      Vector orConditions = new Vector();
      while (!(line = readNextLine()).equalsIgnoreCase("</GoalOrCondition>")) {
         if (line.equalsIgnoreCase("<GoalCondition>")) {
            c = makeGoalCondition();
         }
         orConditions.add(c);
      }
      Condition orCond = new GoalOrCondition(orConditions);
      newConditionAction.addCondition(orCond);
   }

   private void createObservableValueCondition() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Role>"), new Integer(0));
      tags.put(new String("<SourceValue>"), new Integer(1));
      tags.put(new String("<CompareOperator>"), new Integer(2));
      tags.put(new String("<CompareValue>"), new Integer(3));
      tags.put(new String("<CompareValueLow>"), new Integer(4));
      tags.put(new String("<CompareValueHigh>"), new Integer(5));
      String role = null;
      String source = null;
      String operator = null;
      Double compVal = null;
      Double compValLo = null;
      Double compValHi = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</ObservableValueCondition>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: //<Role>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Role>")) {
                  role = line;
                  line = readNextLine();
               }
               break;
            case 1: // <SourceValue>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SourceValue>")) {
                  source = line;
                  line = readNextLine();
               }
               break;
            case 2: // <CompareOperator>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</CompareOperator>")) {
                  operator = line;
                  line = readNextLine();
               }
               break;
            case 3: // <CompareValue>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</CompareValue>")) {
                  compVal = new Double(line);
                  line = readNextLine();
               }
               break;
            case 4: // <CompareValueLow>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</CompareValueLow>")) {
                  compValLo = new Double(line);
                  line = readNextLine();
               }
               break;
            case 5: // <CompareValueHigh>
               line = readNextLine();
               while (!line.equalsIgnoreCase("</CompareValueHigh>")) {
                  compValHi = new Double(line);
                  line = readNextLine();
               }
               break;
         }
      }
      if (role == null) {
         role = MY_CHARACTER;
      }
      if (source != null) {
         Hashtable tempTable = (Hashtable) observableValues.get(role);
         if ((operator.equalsIgnoreCase("LT")) | (operator.equalsIgnoreCase("GT")) ){
            if (compVal!=null) {
               Condition c = new ObservableValueCondition(source, (MyDouble)tempTable.get(source), operator, compVal);
               newConditionAction.addCondition(c);
               String[] dep = new String[2];
               dep[0] = new String(role);
               dep[1] = new String(source);
               obsValueDependencies.add(dep);
            }
            else {
               System.out.println("Error(ConditionActionFactory) - ObservableValueConditon(GT/LT): No Compare value");
            }
         }
         else {
            if (operator.equalsIgnoreCase("Between")) {
               if ( (compValLo != null) && (compValHi != null) ) {
                  Condition c = new ObservableValueCondition(source, (MyDouble)tempTable.get(source), operator, compValLo, compValHi);
                  newConditionAction.addCondition(c);
                  String[] dep = new String[2];
                  dep[0] = new String(role);
                  dep[1] = new String(source);
                  obsValueDependencies.add(dep);
               }
               else {
                  System.out.println("Error(ConditionActionFactory) - ObservableValueConditon(Between): Missing value");
               }
            }
            else {
               System.out.println("Error(ConditionActionFactory) - ObservableValueConditon: Invalid Operator");
            }
         }
      }
      else {
         System.out.println("Error(ConditionActionFactory) - ObservableValueConditon: Source value required");
      }
   } /* createObservableValueCondition */

   private void createUpdateValuePointsAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</UpdateValuePoints>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Object temp[] = new Object[3];
         if (tokens.countTokens() == 1) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String("unallocatedValuePts");
         temp[2] = new Double(tokens.nextToken());
         Action a = new UpdateValuePointsAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createUpdateGoalPointsAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</UpdateGoalPoints>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Object temp[] = new Object[3];
         if (tokens.countTokens() == 1) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String("unallocatedGoalPts");
         temp[2] = new Double(tokens.nextToken());
         Action a = new UpdateGoalPointsAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createSetLocationAction() {
      Vector rangesOfInt = new Vector();
      Vector reqConns = new Vector();
      String type;
      String value;
      String role = null;
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</SetLocation>")) {
         if (line.equalsIgnoreCase("<Role>")) {
            line = readNextLine();
            while (!line.equalsIgnoreCase("</Role>")) {
               role = line;
               line = readNextLine();
            }
         }
         else {
            if (line.equalsIgnoreCase("<RangeOfInterest>")) {
               line = readNextLine();
               while (!line.equalsIgnoreCase("</RangeOfInterest>")) {
                  rangesOfInt.add(line);
                  line = readNextLine();
               } /* while */
            } /* if */
            else {
               if (line.equalsIgnoreCase("<RequiredConnectors>")) {
                  line = readNextLine();
                  while (!line.equalsIgnoreCase("</RequiredConnectors>")) {
                     StringTokenizer token = new StringTokenizer(line, ",");
                     String typeValue[] = new String[2];
                     typeValue[0] = token.nextToken();
                     typeValue[1] = token.nextToken();
                     reqConns.add(typeValue);
                     line = readNextLine();
                  } /* while */
               } /* if */
            } /* else */
         } /* else */
         line = readNextLine();
      } /* while */
      Action a = new SetLocationAction(role, rangesOfInt, reqConns);
      newConditionAction.addAction(a);
   } /* createSetLocationAction */

   private void createTypeValueConnectorUpdateAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</TypeValueConnectorUpdate>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         String temp[] = new String[3];
         if (tokens.countTokens() == 2) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String(tokens.nextToken());
         temp[2] = new String(tokens.nextToken());
         Action a = new TypeValueConnectorUpdateAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

/**
 * This method should eventualy be replaced with ResourceUpdate and ValueUpdate
 */
   private void createTraitUpdateAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</TraitUpdate>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Object temp[] = new Object[3];
         if (tokens.countTokens() == 2) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String(tokens.nextToken());
         temp[2] = new Double(tokens.nextToken());
         Action a = new TraitUpdateAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createUpdateValueAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</UpdateValue>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Object temp[] = new Object[3];
         if (tokens.countTokens() == 2) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String(tokens.nextToken());
         temp[2] = new Double(tokens.nextToken());
         Action a = new UpdateValueAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createUpdateGoalAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</UpdateGoal>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         String temp[] = new String[3];
         if (tokens.countTokens() == 2) {
            temp[0] = new String(MY_CHARACTER);
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String(tokens.nextToken());
         temp[2] = new String(tokens.nextToken());
         Action a = new UpdateGoalAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createUpdateResourceAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</UpdateResource>")) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         Object temp[] = new Object[3];
         if (tokens.countTokens() == 2) {
            temp[0] = MY_CHARACTER;
         }
         else {
            temp[0] = new String(tokens.nextToken());
         }
         temp[1] = new String(tokens.nextToken());
         temp[2] = new Double(tokens.nextToken());
         Action a = new UpdateResourceAction(temp);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createObtainPossessionAction() {
      String line = readNextLine();
      while (!line.equalsIgnoreCase("</ObtainPossession>")) {
         Action a = new ObtainPossessionAction(line);
         newConditionAction.addAction(a);
         line = readNextLine();
      }
   }

   private void createPlaySoundsAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<SoundAsset>"), new Integer(0));
      tags.put(new String("<SoundType>"), new Integer(1));
      tags.put(new String("<SoundChannel>"), new Integer(2));
      tags.put(new String("<SoundLoopMode>"), new Integer(3));
      tags.put(new String("<SoundIterations>"), new Integer(4));
      Vector tempAssetHold = new Vector();
      String[] soundAssets;
      String asset = null;
      SoundType type = null;
      SoundChannel channel = null;
      SoundLoopMode loopMode = null;
      Integer soundIterations = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</PlaySounds>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // SoundAsset
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundAsset>")) {
                  asset = line;
                  tempAssetHold.add(asset);
                  line = readNextLine();
               }
               break;
            case 1: // SoundType
               String sType = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundType>")) {
                  sType = line;
                  if (sType.equalsIgnoreCase("BACKGROUND_AMBIENT")) {
                     type = SoundType.BACKGROUND_AMBIENT;
                  }
                  else {
                     if (sType.equalsIgnoreCase("BACKGROUND_MUSIC")) {
                        type = SoundType.BACKGROUND_MUSIC;
                     }
                     else {
                        if (sType.equalsIgnoreCase("CHARACTER_DIALOG")) {
                           type = SoundType.CHARACTER_DIALOG;
                        }
                        else {
                           if (sType.equalsIgnoreCase("FOLEY_SOUND")) {
                              type = SoundType.FOLEY_SOUND;
                           }
                           else {
                              System.out.println("* Error (CreatePlaySoundsAction): Invalid sound type (" + sType + ")");
                           }
                        }
                     }
                  }
                  line = readNextLine();
               }
               break;
            case 2: // SoundChannel
               String sChannel = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundChannel>")) {
                  sChannel = line;
                  if (sChannel.equalsIgnoreCase("AMBIENT_CHANNEL")) {
                     channel = SoundChannel.AMBIENT_CHANNEL;
                  }
                  else {
                     if (sChannel.equalsIgnoreCase("FOREGROUND_CHANNEL")) {
                        channel = SoundChannel.FOREGROUND_CHANNEL;
                     }
                     else {
                        System.out.println("* Error (CreatePlaySoundsAction): Invalid sound channel (" + sChannel + ")");
                     }
                  }
                  line = readNextLine();
               }
               break;
            case 3: // SoundLoopMode
               String sLoopMode = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundLoopMode>")) {
                  sLoopMode = line;
                  if (sLoopMode.equalsIgnoreCase("INFINITE")) {
                     loopMode = SoundLoopMode.INFINITE;
                  }
                  else {
                     System.out.println("* Error (CreatePlaySoundAction): Invalid loop mode (" + sLoopMode + ")");
                  }
                  line = readNextLine();
               }
               break;
            case 4: // SoundIterations
               String iterations = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundIterations>")) {
                  iterations = line;
                  line = readNextLine();
               }
               if (iterations != null) {
                  soundIterations = new Integer(iterations);
               }
               break;
         }
      }
      soundAssets = (String[])tempAssetHold.toArray(new String[0]);
      Action a = null;
      if (soundIterations == null) {
         a = new PlaySoundsAction(soundAssets, type, channel, loopMode);
      }
      else {
         a = new PlaySoundsAction(soundAssets, type, channel, soundIterations.intValue());
      }
      newConditionAction.addAction(a);
   }

   private void createStopSoundAction() {
      String sChannel;
      SoundChannel channel = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</StopSound>")) {
         sChannel = line;
         if (sChannel.equalsIgnoreCase("AMBIENT_CHANNEL")) {
            channel = SoundChannel.AMBIENT_CHANNEL;
         }
         else {
            if (sChannel.equalsIgnoreCase("FOREGROUND_CHANNEL")) {
               channel = SoundChannel.FOREGROUND_CHANNEL;
            }
            else {
               System.out.println("* Error (CreateStopSoundAction): Invalid sound channel (" + sChannel + ")");
            }
         }
      line = readNextLine();
      }
      Action a = new StopSoundAction(channel);
      newConditionAction.addAction(a);
   }

   private void createPlaySentenceAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Role>"), new Integer(0));
      tags.put(new String("<SentenceName>"), new Integer(1));
      String role = null;
      String sentenceName = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</PlaySentence>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // Role
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Role>")) {
                  role = line;
                  line = readNextLine();
               }
               break;
            case 1: // SentenceName
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SentenceName>")) {
                  sentenceName = line;
                  line = readNextLine();
               }
               break;
         }
      }
      Action a = new PlaySentenceAction(role, sentenceName);
      newConditionAction.addAction(a);
   } //CreatePlaySentenceAction

   private void createStopAllSoundsAction() {
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</StopAllSounds>")) {
         line = readNextLine();
      }
      Action a = new StopAllSoundsAction();
      newConditionAction.addAction(a);
   }

   private void createPlayAnimationAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Character>"), new Integer(0));
      tags.put(new String("<AnimationAction>"), new Integer(1));
      tags.put(new String("<AnimationMode>"), new Integer(2));
      tags.put(new String("<Flag>"), new Integer(3));
      tags.put(new String("<AnimationIterations>"), new Integer(4));
      String character = null;
      String animationAction = null;
      AnimationMode animationMode = null;
      boolean flag = false;
      Integer animationIterations = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</PlayAnimation>")) {
         i = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // Character
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Character>")) {
                  character = line;
                  line = readNextLine();
               }
               break;
            case 1: // AnimationAction
               line = readNextLine();
               while (!line.equalsIgnoreCase("</AnimationAction>")) {
                  animationAction = line;
                  line = readNextLine();
               }
               break;
            case 2: // AnimationMode
               String aniMode = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</AnimationMode>")) {
                  aniMode = line;
                  if (aniMode.equalsIgnoreCase("LOOP")) {
                     animationMode = AnimationMode.LOOP;
                  }
                  else {
                        System.out.println("* Error (CreatePlayAnimationAction): Invalid mode (" + aniMode + ")");
                  }
                  line = readNextLine();
               }
               break;
            case 3: // Flag
               String aFlag = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Flag>")) {
                  aFlag = line;
                  if (aFlag.equalsIgnoreCase("TRUE")) {
                     flag = true;
                  }
                  else {
                     if (aFlag.equalsIgnoreCase("FALSE")) {
                        flag = false;
                     }
                     else {
                        System.out.println("* Error (CreatePlaySoundAction): Invalid flag (" + aFlag + ")");
                     }
                  }
                  line = readNextLine();
               }
               break;
            case 4: // AnimationIterations
               String iterations = null;
               line = readNextLine();
               while (!line.equalsIgnoreCase("</AnimationIterations>")) {
                  iterations = line;
                  line = readNextLine();
               }
               if (iterations != null) {
                  animationIterations = new Integer(iterations);
               }
               break;
         }
      }
      Action a = null;
      if (animationIterations == null) {
         a = new PlayAnimationAction(character, animationAction, animationMode, flag);
      }
      else {
         a = new PlayAnimationAction(character, animationAction, animationIterations.intValue(), flag);
      }
      newConditionAction.addAction(a);
   }

   private void createPlayMovieAction() {
      String movie = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</PlayMovie>")) {
         movie = line;
         line = readNextLine();
      }
      Action a = new PlayMovieAction(movie);
      newConditionAction.addAction(a);
   }

   private void createAddScreenTransitionAction() {
      ScreenTransition scrTrans = null;
      String screenTransition = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</AddScreenTransition>")) {
         screenTransition = line;
         line = readNextLine();
      }
      if (screenTransition.equalsIgnoreCase("ERASE_SCREEN")) {
         scrTrans = ScreenTransition.ERASE_SCREEN;
      }
      else {
         if (screenTransition.equalsIgnoreCase("FADE_TO_BLACK")) {
            scrTrans = ScreenTransition.FADE_TO_BLACK;
         }
         else {
            System.out.println("* Error (CreateAddScreenTransition): Invalid transition (" + screenTransition + ")");
         }
      }
      if (scrTrans != null) {
         Action a = new AddScreenTransitionAction(scrTrans);
         newConditionAction.addAction(a);
      }
   }

   private void createOutputDescriptionAction() {
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</OutputDescription>")) {
         line = readNextLine();
      }
      Action a = new OutputDescriptionAction();
      newConditionAction.addAction(a);
   }

   private void createFrameDoneWhenCharsTakeExitAction() {
      String[] charactersArray = null;
      Vector characterVector = new Vector();
      String character = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</FrameDoneWhenCharsTakeExit>")) {
         line = readNextLine();
         while(!line.equalsIgnoreCase("</Character>")) {
            character = line;
            characterVector.add(character);
            line = readNextLine();
         }
         line = readNextLine();
      }
      if (characterVector.size() > 0) {
         charactersArray = (String[])characterVector.toArray(new String[0]);
         Action a = new FrameDoneWhenCharsTakeExitAction(charactersArray);
         newConditionAction.addAction(a);
      }
   }

   private void createFrameDoneWhenSoundDoneAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Character>"), new Integer(0));
      tags.put(new String("<SoundChannel>"), new Integer(1));
      String[] charactersArray = null;
      Vector characterVector = new Vector();
      String character = null;
      SoundChannel channel = null;
      String sChannel = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</FrameDoneWhenSoundDone>")) {
         i  = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // Character
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Character>")) {
                  character = line;
                  characterVector.add(character);
                  line = readNextLine();
               }
               break;
            case 1: // SoundChannel
               line = readNextLine();
               while (!line.equalsIgnoreCase("</SoundChannel>")) {
                  sChannel = line;
                  if (sChannel.equalsIgnoreCase("AMBIENT_CHANNEL")) {
                     channel = SoundChannel.AMBIENT_CHANNEL;
                  }
                  else {
                     if (sChannel.equalsIgnoreCase("FOREGROUND_CHANNEL")) {
                        channel = SoundChannel.FOREGROUND_CHANNEL;
                     }
                     else {
                        System.out.println("* Error (CreateFrameDoneWhenSoundDoneAction): Invalid sound channel (" + sChannel + ")");
                     }
                  }
                  line = readNextLine();
               }
               break;
         }
      }
      if (characterVector.size() > 0) {
         charactersArray = (String[])characterVector.toArray(new String[0]);
         Action a = new FrameDoneWhenSoundDoneAction(channel, charactersArray);
         newConditionAction.addAction(a);
      }
   }

   private void createExtendIntentConnectorAction() {
      String intentConnector = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</ExtendIntentConnector>")) {
         intentConnector = line;
         line = readNextLine();
      }
      Action a = new ExtendIntentConnectorAction(intentConnector);
      newConditionAction.addAction(a);
   }

   private void createRetractIntentConnectorAction() {
      String intentConnector = null;
      String line = readNextLine();
      while(!line.equalsIgnoreCase("</RetractIntentConnector>")) {
         intentConnector = line;
         line = readNextLine();
      }
      Action a = new RetractIntentConnectorAction(intentConnector);
      newConditionAction.addAction(a);
   }

   private void createCueChooserAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<Role>"), new Integer(0));
      String role = null;
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</CueChooser>")) {
         i  = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // role
               line = readNextLine();
               while (!line.equalsIgnoreCase("</Role>")) {
                  role = line;
                  line = readNextLine();
               }
               break;
         }
      }
      if (role == null) {
         role = this.MY_CHARACTER;
      }
      Action a = new CueChooserAction(role);
      newConditionAction.addAction(a);
   }

   private void createDisplayQuipAction() {
      Hashtable tags = new Hashtable();
      tags.put(new String("<QuipName>"), new Integer(0));
      String quipName = "emptyQuip";
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</DisplayQuip>")) {
         i  = ((Integer) tags.get(line)).intValue();
         switch (i) {
            case 0: // quip name
               line = readNextLine();
               while (!line.equalsIgnoreCase("</QuipName>")) {
                  quipName = line;
                  line = readNextLine();
               }
               break;
         }
      }
      Action a = new DisplayQuipAction(quipName);
      newConditionAction.addAction(a);
   }

   private String readNextLine() {
      if (input.size() > 0) {
         return (String)input.remove(0);
      }
      else {
         return null;
      }
   } /* readNextLine */




/********* Inner classes for Conditions and Actions *********/

   private class IntentConnectorCondition implements Condition {

      private MyBoolean intentStatus;
      private String connectorName;

      public IntentConnectorCondition(MyBoolean intentStat, String name) {
         connectorName = name;
         intentStatus = intentStat;
      }

      public boolean conditionMet() {
         return intentStatus.booleanValue();
      }

      public boolean isACondition(Object[] match) {
         String name = (String)match[0];
         if (name.equalsIgnoreCase(connectorName)) {
            return true;
         }
         else {
            return false;
         }
      }

      public String toString() {
         return "Intent Connector " + connectorName + " extended";
      }
   } /* IntentConnectorCondition */

   private class ConnectorCondition implements Condition {

      private MyBoolean connectorStatus;
      private String connectorName;

      public ConnectorCondition(MyBoolean connectorStat, String name) {
         connectorStatus = connectorStat;
         connectorName = name;
      }

      public boolean conditionMet() {
         return connectorStatus.booleanValue();
      }

      public boolean isACondition(Object[] match) {
         String name = (String)match[0];
         if (name.equalsIgnoreCase(connectorName)) {
            return true;
         }
         else {
            return false;
         }
      }

      public String toString() {
         return "Connector " + connectorName + " extended";
      }
   } /* ConnectorCondition */

   private class TypeValueConnectorCondition implements Condition {

      private MyString currentValue;
      private MyString targetValue;
      private String connectorType;

      public TypeValueConnectorCondition(MyString currentVal, MyString targetVal, String type) {
         currentValue = currentVal;
         targetValue = targetVal;
         connectorType = type;
      }

      public boolean conditionMet() {
         return currentValue.equalsIgnoreCase(targetValue);
      }

      public boolean isACondition(Object[] match) {
         String type = (String)match[0];
         String value = (String)match[1];
         if (connectorType.equalsIgnoreCase(type) & targetValue.equalsIgnoreCase(value)) {
            return true;
         }
         else {
            return false;
         }
      }

      public String toString() {
         return "Connector " + connectorType + " equals " + targetValue.myString;
      }
   }

   private class GoalCondition implements Condition {

      private MyString currentValue;
      private MyString targetValue;
      private String goalName;

      public GoalCondition(MyString currentVal, MyString targetVal, String name) {
         currentValue = currentVal;
         targetValue = targetVal;
         goalName = name;
      }

      public boolean conditionMet() {
         return currentValue.equalsIgnoreCase(targetValue);
      }

      public boolean isACondition(Object[] match) {
         String goal = (String)match[0];
         String value = (String)match[1];
         if (goalName.equalsIgnoreCase(goal) & targetValue.equalsIgnoreCase(value)) {
            return true;
         }
         else {
            return false;
         }
      }

      public String toString() {
         return goalName;
      }
   } /* GoalCondition */

   private class GoalOrCondition implements Condition {

      private Vector goalOrConds;

      public GoalOrCondition(Vector orConds) {
         goalOrConds = orConds;
      }

      public boolean conditionMet() {
         boolean met = false;
         Enumeration enum = goalOrConds.elements();
         while (enum.hasMoreElements()) {
            met = (met | ((Condition)enum.nextElement()).conditionMet());
         }
         System.out.println("Executing goal or condition - value is: " + met);
         return met;
      }

      public boolean isACondition(Object[] match) {
         System.out.println("isACondition Method not implemented (GoalOrCondition)");
         return false;
      }

      public String toString() {
         if (goalOrConds.size() > 0) {
            String description = null;
            description = ((GoalCondition)goalOrConds.firstElement()).toString();
            Enumeration enum = goalOrConds.elements();
            enum.nextElement();
            while (enum.hasMoreElements()) {
               description += " or " + ((GoalCondition)enum.nextElement()).toString();
            }
            return description;
         }
         else {
            return super.toString();
         }
      }
   } /* GoalOrCondition*/

   private class ObservableValueCondition implements Condition {

      private Double compareValueOne;
      private Double compareValueTwo;
      private MyDouble currentValue;
      private String source;
      private String operator;
      private String operatorLong;
      private int switchVal;

      public ObservableValueCondition (String sourceValue, MyDouble currVal, String op, Double compValOne) {
         source = sourceValue;
         currentValue = currVal;
         operator = op;
         compareValueOne = compValOne;
         if (operator.equalsIgnoreCase("GT")) {
            switchVal = 1;
            operatorLong = " greater than ";
         }
         else { /* LT */
            switchVal = 2;
            operatorLong = " less than ";
         }
      }

      public ObservableValueCondition (String sourceValue, MyDouble currVal, String op, Double compValOne, Double compValTwo) {
         source = sourceValue;
         currentValue = currVal;
         operator = op;
         compareValueOne = compValOne;
         compareValueTwo = compValTwo;
         switchVal = 3;
         operatorLong = " between ";
      }

      public boolean conditionMet() {
         switch (switchVal) {
            case 1:
               return currentValue.isGreaterThanOrEqual(compareValueOne.doubleValue());
            case 2:
               return currentValue.isLessThanOrEqual(compareValueOne.doubleValue());
            case 3:
               return currentValue.isBetween(compareValueOne.doubleValue(), compareValueTwo.doubleValue());
         }
         return false;
      }

      public boolean isACondition(Object[] match) {
         System.out.println("* Error* ConditonAction(ObservableValueCondition): isACondition has a null implementation");
         return true;
      }

      public String toString() {
         String description = super.toString();
         switch (switchVal) {
            case 1:
               description = source + operatorLong + compareValueOne.toString();
               break;
            case 2:
               description = source + operatorLong + compareValueOne.toString();
               break;
            case 3:
               description = source + operatorLong + compareValueOne.toString() + "and " + compareValueTwo.toString();
               break;
         }
         return description;
      }
   }

   private class UpdateValuePointsAction implements Action  {
      private String role;
      private String value;
      private double delta;
      private String verb;

      UpdateValuePointsAction(Object[] traitUpd) {
         role = new String((String)traitUpd[0]);
         value = new String((String)traitUpd[1]);
         delta = ((Double)traitUpd[2]).doubleValue();
         if (delta >= 0) {
            verb = " increases by ";
         }
         else {
            verb = " decreases by ";
         }
      }

      public void execute(ActionHandler ah) {
         ah.updateValuePoints(role, value, delta);
      } /* execute */

      public String toString() {
         return "Value Points" + verb + new Double(delta).toString();
      }

   }

   private class UpdateGoalPointsAction implements Action  {
      private String role;
      private String value;
      private double delta;
      private String verb;

      UpdateGoalPointsAction(Object[] traitUpd) {
         role = new String((String)traitUpd[0]);
         value = new String((String)traitUpd[1]);
         delta = ((Double)traitUpd[2]).doubleValue();
         if (delta >= 0) {
            verb = " increases by ";
         }
         else {
            verb = " decreases by ";
         }
      }

      public void execute(ActionHandler ah) {
         ah.updateGoalPoints(role, value, delta);
      } /* execute */

      public String toString() {
         return "Goal Points " + verb + new Double(delta).toString();
      }

   }


   private class TypeValueConnectorUpdateAction implements Action {

      private String role;
      private String type;
      private String newValue;

      TypeValueConnectorUpdateAction(String[] tvUpd) {
         role = new String(tvUpd[0]);
         type = new String(tvUpd[1]);
         newValue = new String(tvUpd[2]);
      }

      public void execute(ActionHandler ah) {
         ah.updateTypeValueConnector(role, type, newValue);
      } /* execute */
   } /* TypeValueConnectorUpdateAction */

   private class TraitUpdateAction implements Action {

      private String role;
      private String value;
      private double delta;
      private String verb;

      TraitUpdateAction(Object[] traitUpd) {
         role = new String((String)traitUpd[0]);
         value = new String((String)traitUpd[1]);
         delta = ((Double)traitUpd[2]).doubleValue();
         if (delta >= 0) {
            verb = " increases by ";
         }
         else {
            verb = " decreases by ";
         }
      }

      public void execute(ActionHandler ah) {
         ah.updateValue(role, value, delta);
      } /* execute */

      public String toString() {
         return value + verb + new Double(delta).toString();
      }

   } /* TraitUpdateAction */

   private class UpdateValueAction implements Action {

      private String role;
      private String value;
      private double delta;
      private String verb;

      UpdateValueAction(Object[] traitUpd) {
         role = new String((String)traitUpd[0]);
         value = new String((String)traitUpd[1]);
         delta = ((Double)traitUpd[2]).doubleValue();
         if (delta >= 0) {
            verb = " increases by ";
         }
         else {
            verb = " decreases by ";
         }
      }

      public void execute(ActionHandler ah) {
         ah.updateValue(role, value, delta);
      } /* execute */

      public String toString() {
         return value + verb + new Double(delta).toString();
      }

   } /* UpdateValueAction */

   public class UpdateGoalAction implements Action {

      private String role;
      private String goal;
      private String newStatus;

      UpdateGoalAction(String[] goalUpd) {
         role = new String(goalUpd[0]);
         goal = new String(goalUpd[1]);
         newStatus = new String(goalUpd[2]);
      }

      public void execute(ActionHandler ah) {
         ah.updateGoal(role, goal, newStatus);
      } /* execute */

      public String toString() {
         return "Change status of " + goal + " goal to " + newStatus;
      }

   } /* UpdateGoalAction */

   public class UpdateResourceAction implements Action {

      private String role;
      private String resource;
      private double delta;
      private String verb;

      UpdateResourceAction(Object[] traitUpd) {
         role = new String((String)traitUpd[0]);
         resource = new String((String)traitUpd[1]);
         delta = ((Double)traitUpd[2]).doubleValue();
         if (delta >= 0) {
            verb = " increases by ";
         }
         else {
            verb = " decreases by ";
         }
      }

      public void execute(ActionHandler ah) {
         ah.updateResource(role, resource, delta);
      } /* execute */

      public String getResource() {
         return resource;
      }

      public double getDelta() {
         return delta;
      }

      public String toString() {
         return resource + verb + new Double(delta).toString();
      }

   } /* UpdateResourceAction */

   private class ObtainPossessionAction implements Action {

      private String name;

      ObtainPossessionAction(String possessionName) {
         name = possessionName;
      }

      public void execute(ActionHandler ah) {
         ah.obtainPossession(name);
      }

      public String toString() {
         return name + " (including all additional expenses or payments)";
      }
   } /* ObtainPossessionAction */

   private class SetLocationAction implements Action {

      private Vector rangesOfInterest;
      private Vector requiredConnectors;
      private String role;

      SetLocationAction(String r, Vector rangesOfInt, Vector reqConns) {
         role = new String(r);
         rangesOfInterest = new Vector(rangesOfInt);
         requiredConnectors = new Vector(reqConns);
      }

      public void execute(ActionHandler ah) {
         Enumeration enum = requiredConnectors.elements();
         Object[] typeValue;
         String type;
         String value;
         while (enum.hasMoreElements()) {
            typeValue = (Object[]) enum.nextElement();
            type = (String) typeValue[0];
            value = (String) typeValue[1];
            ((NewInteraction)ah).getCharacter(role).ConnectorState().changeConnectorValue(type, value);
            rangesOfInterest.add(value);
         }
         QueryVector qVector = ((NewInteraction)ah).getStateVector(role).MakeQueryVector(rangesOfInterest);
         Location loc = new Location();
         loc.Instantiate(qVector);
         ah.getActionFrame().SetLocation(loc);
      }

      public String toString() {
         return "Set Location";
      }
   }

   private class PlaySoundsAction implements Action {

      private boolean loop = false;
      private String[] assets;
      private SoundType type;
      private SoundChannel channel;
      private SoundLoopMode loopMode;
      private int iter;

      PlaySoundsAction(String[] soundAssets, SoundType soundType, SoundChannel soundChannel, SoundLoopMode soundLoopMode) {
         loop = true;
         assets = soundAssets;
         type = soundType;
         channel = soundChannel;
         loopMode = soundLoopMode;
      }

      PlaySoundsAction(String[] soundAssets, SoundType soundType, SoundChannel soundChannel, int iterations) {
         loop = false;
         assets = soundAssets;
         type = soundType;
         channel = soundChannel;
         iter = iterations;
      }

      public void execute(ActionHandler ah) {
         if (loop) {
            ah.getActionFrame().PlaySounds(assets, type, channel, loopMode);
         }
         else {
            ah.getActionFrame().PlaySounds(assets, type, channel, iter);
         }
      }

      public String toString() {
         String description = "Play Sounds {";
         for (int i = 0; i < assets.length; i++) {
            description += assets[i] + " ";
         }
         description += "} on channel " + channel.toString();
         return description;
      }
   }

   private class PlaySentenceAction implements Action {

      private String role;
      private String sentenceName;

      PlaySentenceAction(String role, String sentenceName) {
         this.role = role;
         this.sentenceName = sentenceName;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().PlaySentence(role, sentenceName);
      }

      public String toString() {
         String description = "Play Sentence (Role: " + role + " Sentence:" +
                              sentenceName + ")";
         return description;
      }
   } //PlaySentenceAction

   private class StopSoundAction implements Action {

      SoundChannel soundChannel = null;

      StopSoundAction(SoundChannel channel) {
         soundChannel = channel;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().StopSound(soundChannel);
      }

      public String toString() {
         return "Stop sounds playing on channel " + soundChannel.toString();
      }
   }

   private class StopAllSoundsAction implements Action {

      StopAllSoundsAction() {
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().StopAllSounds();
      }

      public String toString() {
         return "Stop All Sounds";
      }
   }

   private class PlayAnimationAction implements Action {

      private boolean loop = false;
      private String character;
      private String animationAction;
      private AnimationMode animationMode;
      private boolean flag;
      private int iter;

      PlayAnimationAction(String aniCharacter, String aniAction, AnimationMode aniMode, boolean aniFlag) {
         loop = true;
         character = aniCharacter;
         animationAction = aniAction;
         animationMode = aniMode;
         flag = aniFlag;
      }

      PlayAnimationAction(String aniCharacter, String aniAction, int iterations, boolean aniFlag) {
         loop = false;
         character = aniCharacter;
         animationAction = aniAction;
         iter = iterations;
         flag = aniFlag;
      }

      public void execute(ActionHandler ah) {
         if (loop) {
            //ah.getActionFrame().PlayAnimation(character, animationAction, animationMode, flag);
            ah.getActionFrame().PlayAnimation(character, animationAction, animationMode, flag);
         }
         else {
            //ah.getActionFrame().PlayAnimation(character, animationAction, iter, flag);
            ah.getActionFrame().PlayAnimation(character, animationAction, iter, flag);
         }
      }

      public String toString() {
         return "Play " + animationAction + " action for character " + character;
      }
   }

   private class PlayMovieAction implements Action {

      private String mov = null;

      PlayMovieAction(String movie) {
         mov = movie;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().PlayMovie(mov);
      }

      public String toString() {
         return "Play " + mov + " movie";
      }
   }

   private class AddScreenTransitionAction implements Action {

      private ScreenTransition scrTrans = null;

      AddScreenTransitionAction(ScreenTransition screenTransition) {
         scrTrans = screenTransition;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().AddScreenTransition(scrTrans);
      }

      public String toString() {
         return "Add Screen Transition " + scrTrans.toString();
      }
   }

   private class OutputDescriptionAction implements Action {

      OutputDescriptionAction() {
      }

      public void execute(ActionHandler ah) {
        System.out.println(((NewInteraction)ah).getDescription());
      }
   }

   private class FrameDoneWhenCharsTakeExitAction implements Action {

      String[] charArray = null;

      FrameDoneWhenCharsTakeExitAction(String [] characterArray) {
         charArray = characterArray;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().FrameDoneWhenCharsTakeExit(charArray);
      }

      public String toString() {
         return "Frame Done When Chars Take Exit";
      }
   }

   private class FrameDoneWhenSoundDoneAction implements Action {

      String[] charArray = null;
      SoundChannel channel = null;

      FrameDoneWhenSoundDoneAction(SoundChannel sChannel, String[] characterArray) {
         charArray = characterArray;
         channel = sChannel;
      }

      public void execute(ActionHandler ah) {
         ah.getActionFrame().FrameDoneWhenSoundDone(channel, charArray);
      }

      public String toString() {
         return "Frame Done When Sound Done";
      }
   }



   private class ExtendIntentConnectorAction implements Action {

      String intentConnector;

      ExtendIntentConnectorAction(String ic) {
         intentConnector = ic;
      }

      public void execute(ActionHandler ah) {
         ah.extendIntentConnector(intentConnector);
      }

      public String toString() {
         return "Extend Intent Connector " + intentConnector;
      }
   }

   private class RetractIntentConnectorAction implements Action {

      String intentConnector;

      RetractIntentConnectorAction(String ic) {
         intentConnector = ic;
      }

      public void execute(ActionHandler ah) {
         ah.retractIntentConnector(intentConnector);
      }

      public String toString() {
         return "Retract Intent Connector " + intentConnector;
      }
   }

   private class CueChooserAction implements Action {

      String role;

      CueChooserAction(String role) {
         this.role = role;
      }

      public void execute(ActionHandler ah) {
         ah.cueChooser(role);
      }

      public String toString() {
         return "Cue the chooser.";
      }
   }

   private class DisplayQuipAction implements Action {

      String quipName;

      DisplayQuipAction(String quipName) {
         this.quipName = quipName;
      }

      public void execute(ActionHandler ah) {
         ah.displayQuip(quipName);
      }

      public String toString() {
         return "Display Quip (" + quipName + ")";
      }
   }

} /* ConditionAction*/
