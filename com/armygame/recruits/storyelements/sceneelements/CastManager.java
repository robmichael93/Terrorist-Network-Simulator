package com.armygame.recruits.storyelements.sceneelements;

import java.io.*;
import java.util.*;
import com.armygame.recruits.globals.ResourceReader;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class CastManager {

   private Hashtable roles;
   private Hashtable roleToActorMap;
   private Hashtable actors;
   private BufferedReader br;
   private CharacterFactory charFactory;
   private Vector currentCast;

   public CastManager(String roleFile, String actorFile, String charFactoryConfig) {
      charFactory = new CharacterFactory(charFactoryConfig);
      readRoles(roleFile);
      closeReader();
      readActors(actorFile);
      closeReader();
      initializeReader(roleFile);
      currentCast = new Vector();
   }

   private void readRoles(String roleFile) {
      roles = new Hashtable();
      roleToActorMap = new Hashtable();
      String line;
      initializeReader(roleFile);
      line = readNextLine(); //<Roles>
      while(!(line = readNextLine()).equalsIgnoreCase("</Roles>")) {
         line = readNextLine(); // role name
         roles.put(line, charFactory.makeCharacter(line, "E-1", 10));
         roleToActorMap.put(line, new Vector());
         readNextLine(); //</Role>
      }
   }

   private void readActors(String actorFile) {
      actors = new Hashtable();
      String name = null;
      String voice = "none";
      String gender = "na";
      Vector roleVector = new Vector();
      String role = null;
      Actor newActor;
      Hashtable commands = new Hashtable();
      commands.put(new String("<Actors>"), new Integer(0));
      commands.put(new String("<Actor>"), new Integer(1));
      commands.put(new String("<Name>"), new Integer(2));
      commands.put(new String("<Voice_ID>"), new Integer(3));
      commands.put(new String("<Role>"), new Integer(4));
      commands.put(new String("<Gender>"), new Integer(5));
      commands.put(new String("</Actor>"), new Integer(6));
      initializeReader(actorFile);
      int i = 0;
      String line = new String();
      while (!(line = readNextLine()).equalsIgnoreCase("</Actors>")) {
         if ((line.charAt(0)) == '-') {
            i = 7;
         }
         else {
            i = ((Integer) commands.get(line)).intValue();
         }
         switch (i) {
            case 0: //Actors
               break;
            case 1: // Actor
               name = null;
               voice = "none";
               gender = "na";
               roleVector = new Vector();
               newActor = null;
               break;
            case 2: // Name
               name = readNextLine();
               readNextLine();
               break;
            case 3: // Voice_ID
               voice = readNextLine();
               readNextLine();
               break;
            case 4: // Role
               roleVector.add(readNextLine());
               readNextLine();
               break;
            case 5: // Gender
               gender = readNextLine();
               readNextLine();
               break;
            case 6: // endActor
               actors.put(name, new Actor(name, voice, gender));
               Enumeration enum = roleVector.elements();
               while (enum.hasMoreElements()) {
                  role = (String)enum.nextElement();
                  ((Vector)roleToActorMap.get(role)).add(name);
               }
               break;
            case 7: // comment
               break;
         }
      }
   }

   public void setCast(String mainChar) {
      String currentRole;
      String actorName;
      boolean currentRoleFilled;
      Actor currentActor;
      Vector actorList;
      Enumeration enum1;
      Enumeration enum;

      fillRoleWithActor("MC", mainChar);
      ((MainCharacter)roles.get("MC")).setCurrentProtagonistTrue();

      enum = roleToActorMap.keys();
      while (enum.hasMoreElements()) {
         currentRole = (String)enum.nextElement();
         currentRoleFilled = false;
         if (!currentRole.equalsIgnoreCase("MC")) {
            actorList = (Vector) roleToActorMap.get(currentRole);
            enum1 = actorList.elements();
            while (enum1.hasMoreElements() & (!currentRoleFilled)) {
               actorName = (String)enum1.nextElement();
               currentActor = (Actor)actors.get(actorName);
               if (!currentActor.isCastInRole()) {
                  fillRoleWithActor(currentRole, actorName);
                  currentRoleFilled = true;
               }
            }
            if (!currentRoleFilled) {
               System.out.println("Warning: No character assigned to fill role (" + currentRole + ")");
            }
         }
      }
   }

   public MainCharacter getCharacterFromRole(String role) {
      return (MainCharacter) roles.get(role);
   }

   private void initializeReader(String activeFile) {

      try {
         br = new BufferedReader(ResourceReader.getInputReader(activeFile));
      }
      catch (IOException e) {
         System.err.println("Error(" + this.getClass().getName() + ") - unable to create BufferedReader" +
                            " for file " + activeFile);
      }
   }

   private void closeReader() {
      try {
         br.close();
      }
      catch (IOException e) {
         System.err.println("Error: - unable to close BufferedReader" +
                            " br: " + br.toString());
      }
   }

   private String readNextLine() {
      try {
         return br.readLine();
      }
      catch (IOException e) {
         System.err.println("Error (" + this.getClass().getName() + ") - Unable to read from file.");
         return null;
      }
   }

   public MainCharacter mainCharacter() {
      return (MainCharacter)roles.get("MC");
   }

   public Hashtable getCast() {
      return roles;
   }

   public void clearCurrentCast() {
      currentCast.clear();
   }

   public void addRoleToCurrentCast(String roleName) {
      System.out.println("**** Adding to Cast **** " + roleName);
      currentCast.add(roleName);
   }

   public Vector getCastForCurrentScene() {
      return currentCast;
   }

   public int currentCastSize() {
      return currentCast.size();
   }

   private void fillRoleWithActor(String role, String actor) {
      Actor a = (Actor)actors.get(actor);
      ((MainCharacter)roles.get(role)).setRole(role);
      ((MainCharacter)roles.get(role)).setVoiceID(a.voice);
      ((MainCharacter)roles.get(role)).setActorName(a.name);
      if ((a.gender).equalsIgnoreCase("male")) {
         ((MainCharacter)roles.get(role)).setGenderMale();
      }
      else {
         if ((a.gender).equalsIgnoreCase("female")) {
            ((MainCharacter)roles.get(role)).setGenderFemale();
         }
         else {
            ((MainCharacter)roles.get(role)).setGenderNA();
         }
      }
      a.setCastInRole();
      System.out.println("Actor: " + a.name + " cast in role: " + role);
   }

   private class Actor {

      public String name;
      public String voice;
      public String gender;
      public boolean castInRole;

      public Actor(String name, String voice, String gender) {
         this.name = name;
         this.voice = voice;
         this.gender = gender;
         castInRole = false;
      }

      public void setCastInRole() {
         castInRole = true;
      }

      public void clearCastInRole() {
         castInRole = false;
      }

      public boolean isCastInRole() {
         return castInRole;
      }

   }
}
