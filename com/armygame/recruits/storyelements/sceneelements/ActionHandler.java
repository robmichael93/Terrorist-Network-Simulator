package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.playlist.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface ActionHandler {
   public void updateValue(String s1, String s2, double d);

   public void updateResource(String s1, String s2, double d);

   public void updateValuePoints(String s1, String s2, double d);

   public void updateGoalPoints(String s1, String s2, double d);

   public void updateTypeValueConnector(String s1, String s2, String s3);

   public void updateGoal(String role, String name, String status);

   public void retractIntentConnector(String name);

   public void extendIntentConnector(String name);

   public ActionFrame getActionFrame();

   public void obtainPossession(String name);

   public void cueChooser(String role);

   public void displayQuip(String quipName);
}