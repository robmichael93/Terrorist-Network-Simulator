package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GoalUpdateEvent {

   private String role;
   private String name;
   private String status;

   public GoalUpdateEvent(String charRole, String goalName, String goalStatus) {
      role = charRole;
      name = goalName;
      status = goalStatus;
   }

   public String getRole() {
      return role;
   }

   public String getGoalName() {
      return name;
   }

   public String getStatus() {
      return status;
   }
}