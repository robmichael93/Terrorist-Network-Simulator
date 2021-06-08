package com.armygame.recruits.storyelements.sceneelements;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GoalChangeEvent {

   private String role;
   private String name;
   private String status;
   private double measure;
   private final String MY_CHARACTER = "myCharacter";

   public GoalChangeEvent(String goalName, String goalStatus, double goalMeasure) {
      name = goalName;
      status = goalStatus;
      measure = goalMeasure;
      role = MY_CHARACTER;
   }

   public GoalChangeEvent(String charRole, String goalName, String goalStatus, double goalMeasure) {
      role = charRole;
      name = goalName;
      status = goalStatus;
      measure = goalMeasure;
   }

   public String getRole() {
      return role;
   }

   public String getName() {
      return new String(name);
   }

   public String getStatus() {
      return new String(status);
   }

   public boolean isNotSelectable() {
      return status.equalsIgnoreCase("notSelectable");
   }

   public boolean isSelectable() {
      return status.equalsIgnoreCase("selectable");
   }

   public boolean isSelected() {
      return status.equalsIgnoreCase("selected");
   }

   public boolean isAchieved() {
      return status.equalsIgnoreCase("achieved");
   }

}