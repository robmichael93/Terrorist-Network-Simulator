//
//  GoalTree.java
//  projectbuilder5
//
//  Created by Mike Bailey on Mon Apr 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;

import com.armygame.recruits.storyelements.sceneelements.Goal;
import java.util.HashMap;
import java.io.Serializable;

public class GoalTree implements Serializable
/*******************/
{
  // create GoalTree object

  public HashMap goals;
  public ConnectedGoal root;
  private int edges;

  public GoalTree(HashMap goals)
  {
    if(goals == null)
      this.goals = new HashMap();
    else
      this.goals = goals;
  }

  public GoalTree()
  {
    this(null);
  }

  public int getEdges()
  {
    return edges;
  }

  public void addSingleGoal(Goal g)
  //-------------------------------
  {
    if(goals.get(g) == null)
      goals.put(g,new ConnectedGoal(g));
  }

  public void addDependency(Goal prerequisite, Goal finalG)
  //-------------------------------------------------------
  {
    edges++;
    ConnectedGoal fcg,pcg;
    if((fcg = (ConnectedGoal)goals.get(finalG)) == null)
    {
      fcg = new ConnectedGoal(finalG);
      goals.put(finalG,fcg);
    }
    if((pcg = (ConnectedGoal)goals.get(prerequisite)) == null)
    {
      pcg = new ConnectedGoal(prerequisite);
      goals.put(prerequisite,pcg);
    }
    pcg.prereqOf.add(fcg);
    fcg.prereqs.add(pcg);
  }

}
