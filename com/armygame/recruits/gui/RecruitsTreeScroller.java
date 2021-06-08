/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: Apr 26, 2002
 * Time: 8:33:22 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.*;

public class RecruitsTreeScroller extends RecruitsWidgetScroller
{
  Object[] data;
  public GoalJTree jtree;

  RecruitsTreeScroller(Dimension maxd, GoalJTree jtree)
  {
    super(maxd);
    this.jtree=jtree;
    myConstructor(jtree);
  }
 /*
  public void reBuild(GoalTree gt)
  {
    jtree = new GoalJTree(gt);
    jtree.setModel(treemodel);
    jtree.expandEveryThing();
    super.reBuild(jtree);
  }
  */
}
