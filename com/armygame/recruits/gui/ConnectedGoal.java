//
//  ConnectedGoal.java
//  projectbuilder5
//
//  Created by Mike Bailey on Mon Apr 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.storyelements.sceneelements.Goal;
import java.util.ArrayList;
import javax.swing.Icon;
import java.io.Serializable;

public class ConnectedGoal implements Serializable
{
  public ArrayList prereqs = new ArrayList(5);
  public ArrayList prereqOf= new ArrayList(5);
  public Icon icon;
  public Goal goal;
  String goalName;
  public ConnectedGoal(Goal g)
  {
    goal = g;
    if(g == null || g.getName() == null || g.getName().equals(""))
    {
      goalName="<null>";
      icon = Ggui.imgIconGet("DEFAULT_GOAL");
    }
    else
    {
      goalName=g.getName();
      icon = Ggui.imgIconGet(g.getCode()+"_GOAL");
    }
  }

  public String toString()
  {
    return goalName;
  }
}
