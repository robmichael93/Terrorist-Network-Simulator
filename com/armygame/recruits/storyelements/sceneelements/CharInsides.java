package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.gui.GoalTree;
import java.util.ArrayList;
import java.util.Vector;
import java.io.Serializable;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class CharInsides implements Serializable{

   //values
   public double loyalty;
   public double duty;
   public double respect;
   public double selfless;
   public double honor;
   public double integrity;
   public double courage;
   public double unallocated;

   //resources
   public double energy;
   public double strength;
   public double knowledge;
   public double skill;
   public double financial;
   public double popularity;

   public double energylast;
   public double strengthlast;
   public double knowledgelast;
   public double skilllast;
   public double financiallast;
   public double popularitylast;
   public double bankBalancelast;

   // goals
   public String goalString;
   public GoalTree goalTree;

   // financial data
   public Vector debits;
   public Vector credits;
   public double bankBalance;
   public double time;

   // character info
   public String actorName="";
   public String charName="";

   public Goal    [] chosenGoals = new Goal[5];
   public boolean [] goalChangedByGui={false,false,false,false,false};
   public double  [] goalEmphasis= {0.,0.,0.,0.,0.}; // sums to 100

   // history
   public ArrayList history = new ArrayList(32);  // array of history elements, in chron. order
   class HistoryElement
   {
     String when; // calendar data or game time
     String where;
     String iconName;
     String description;
     String impact;
  }

  public void setGoalTree(GoalTree gt) {
   goalTree = gt;
  }

   public CharInsides() {
      goalTree = new GoalTree();
   }
}