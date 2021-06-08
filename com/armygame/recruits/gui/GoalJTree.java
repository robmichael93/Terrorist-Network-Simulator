//
//  GoalJTree.java
//  GoalTree
//
//  Created by Mike Bailey on Sun Apr 14 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class GoalJTree extends JTree
{
  boolean inSelect = false;
  TreeModel model;

  GoalJTree(GoalTree tree)
  {
    super();
//tree=fakeOneUp();

    setModel(new GoalTreeModel(tree));
    setRootVisible(false);
    setShowsRootHandles(true);
    BasicTreeUI btui = (BasicTreeUI)super.getUI();
    btui.setCollapsedIcon(Ggui.imgIconGet("TREECOLLAPSEDICON"));
    btui.setExpandedIcon(Ggui.imgIconGet("TREEEXPANDEDICON"));

    //setBackground(new Color(38,38,38));
    setBackground(Ggui.darkBackground); //mediumBackground);
    setCellRenderer(new GoalTreeCellRenderer());

    getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    //Listen for when the selection changes.

    //expandEveryThing();
    // collapse everything:
    int i = -1;
    while(i++<getRowCount())
    {
      collapseRow(i);
    }

  }
  // This is our way of preventing double click expansions...
  // replaced by help
  protected void processMouseEvent(MouseEvent e)
  {
    if(e.getClickCount() == 2)
    {
      if(e.paramString().startsWith("MOUSE_CLICK"))  // only once per double click
        MainFrame.me.handlers.eventIn(ButtonFactory.GOAL_DOUBLE_CLICKED,getLastSelectedPathComponent());
    }
    else
      super.processMouseEvent(e);
  }
  /*
  public void expandEveryThing()
  {
    ArrayList al = new ArrayList();
    Object root = model.getRoot();
    al.add(root);
    addChildren(root,al,true);
  }

  private void addChildren(Object node, ArrayList al, boolean isRoot)
  {
    int childCount = model.getChildCount(node);
    if(childCount == 0)
    {
      // expand the path
      Object[] oa = al.toArray();
      TreePath tp = new TreePath(oa);
      //this.makeVisible(tp);
      collapsePath(tp);
      System.out.println("collapsing path: "+tp.toString());
    }
    else
    {
      for(int i=0;i<childCount;i++)
      {
        int sizeStart = al.size();
        Object n = model.getChild(node,i);
        al.add(n);
        addChildren(n,al,false);
        int sizeEnd = al.size();
        for(int j=sizeEnd;j>sizeStart;j--)
          al.remove(j-1);
      }
    }
  }
  */
  public void setModel(TreeModel model)
  {
    this.model = model;
    super.setModel(model);
    if(model instanceof GoalTreeModel)
      this.setVisibleRowCount(((GoalTreeModel)model).getNodeCount());
  }
  
  GoalTree fakeOneUp()
  {
    ConnectedGoal[] cga= new ConnectedGoal[25];
    //GoalTree gt = new GoalTree();
    HashMap g = new HashMap();
    for(int i=0;i<25;i++)
    {
      cga[i] = new ConnectedGoal(null);
      g.put(new Integer(i),cga[i]);
    }
    GoalTree gt = new GoalTree(g);
    //gt.goals = g;
    addDependency(cga[2],cga[3]);
    addDependency(cga[2],cga[6]);
    addDependency(cga[3],cga[4]);
    addDependency(cga[4],cga[5]);
    return gt;

  }
  void addDependency(ConnectedGoal prerequisite, ConnectedGoal finalG)
    //-------------------------------------------------------
  {
    prerequisite.prereqOf.add(finalG);
    finalG.prereqs.add(prerequisite);
  }    
}
