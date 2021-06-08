//
//  ContactsPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
public class oldCharGoalsViewAllPanel extends RPanel
{
  MainFrame mf;
  int xoff,yoff;
  GoalJTree goalT;
  RecruitsTreeScroller rts;
  JScrollPane jsp;
  JPanel borderPanel;
  Insets insets;
  ConnectedGoal selectedGoal;
  private final static int TREEMAXWIDTH = 420;
  private final static int TREE_YOFFSET = 150;
  private final static int TREEMAXHEIGHT = 480-TREE_YOFFSET-15; // fudge

  oldCharGoalsViewAllPanel(MainFrame main)
  {
    mf = main;

    setLayout(null);
    // not avail at build time:
    //goalT = new GoalJTree(mf.globals.charinsides.goalTree);
    setBackground(Ggui.darkBackground);

    JButton closeButt = ButtonFactory.make(ButtonFactory.GOALSVIEWALLCLOSE,mf);
    add(closeButt);

    JLabel doubClickHelp = new JLabel("Double click on a goal to learn more about it.");
    doubClickHelp.setFont(new Font("Arial",Font.PLAIN,10));
    //doubClickHelp.setForeground(new Color(230,179,0)); // yello
    doubClickHelp.setForeground(new Color(94,94,94));  // grey
    doubClickHelp.setSize(doubClickHelp.getPreferredSize());
    int dy = 130;
    int dx = (640-doubClickHelp.getPreferredSize().width)/2;
    doubClickHelp.setLocation(dx,dy);
    add(doubClickHelp);


    //setBackground(new Color(38,38,38)); //Color.black);
    setVisible(false);
  }

  private void sizePlaceTree()
  {
    jsp.setPreferredSize(new Dimension(TREEMAXWIDTH,TREEMAXHEIGHT));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setLocation((640-jsp.getWidth())/2,TREE_YOFFSET);
  }
  boolean wentOnce = false;

  public void go()
  {
    super.go();
    mf.setTitleBar("VIEWALLGOALSTITLE");
    if(wentOnce)
    {
      //rts.jtree.expandEveryThing();
      return;
    }
    wentOnce = true;

    goalT = new GoalJTree(mf.globals.charinsides.goalTree); // put the tree from the global char attrs here
    //rts = new RecruitsTreeScroller(new Dimension(TREEMAXWIDTH,TREEMAXHEIGHT),goalT);
    jsp = new JScrollPane(goalT);
    jsp.setBackground(Ggui.darkBackground);   // border uses
    jsp.setBorder(new CompoundBorder(new ShadowBorder(8),
                                   BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
                                                                    new Color(91,91,91),
                                                                    new Color(51,51,51))));
    sizePlaceTree();
    add(jsp);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("GOALSALLBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    goalT.addTreeSelectionListener(new TreeSelectionListener()
    {
      public void valueChanged(TreeSelectionEvent e)
      {
        selectedGoal = (ConnectedGoal)goalT.getLastSelectedPathComponent();
      }
    });
  }
}
