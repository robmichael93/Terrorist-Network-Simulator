//
//  CharGoalsPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharGoalsPanel extends RPanel
{
  MainFrame mf;

  CharGoalsPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    JPanel goals = new JPanel();
    goals.setLayout(new BoxLayout(goals,BoxLayout.Y_AXIS));
    goals.setBackground(new Color(.18f,.18f,.18f));
    goals.setOpaque(true);

    //goals.setSize(440,1200);
    //goals.setPreferredSize(new Dimension(440,1200));
    //goals.setMinimumSize(new Dimension(440,1200));
    //goals.setMaximumSize(new Dimension(440,1200));

OneGoalPanel g1 = new OneGoalPanel();
//g1.setLocation(0,120);
g1.setAlignmentX(Component.LEFT_ALIGNMENT);
goals.add(g1);

OneGoalPanel g2 = new OneGoalPanel();
g2.setAlignmentX(Component.LEFT_ALIGNMENT);
goals.add(g2);

OneGoalPanel g3 = new OneGoalPanel();
g3.setAlignmentX(Component.LEFT_ALIGNMENT);
goals.add(g3);

OneGoalPanel g4 = new OneGoalPanel();
g4.setAlignmentX(Component.LEFT_ALIGNMENT);
goals.add(g4);

Dimension d = goals.getPreferredSize();
goals.setSize(d);
goals.setMinimumSize(d);
goals.setMaximumSize(d);

    JScrollPane jsp = new JScrollPane(goals);
    jsp.setBorder(null);
    jsp.setPreferredSize(new Dimension(640,228));
    //jsp.setBackground(Color.black); doesn't get rid of the borders
    //jsp.setForeground(Color.black);
    //jsp.setOpaque(true);
    jsp.setSize(jsp.getPreferredSize());
    jsp.setLocation(0,52);
    add(jsp);
    
    JButton cancelButt = ButtonFactory.make(ButtonFactory.GOALSCANCEL,mf);
    add(cancelButt);
    
    JButton okButt = ButtonFactory.make(ButtonFactory.GOALSOK,mf);
    add(okButt);
    
    JLabel backLabel = new JLabel(Ggui.imgIconGet("GOALSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }
  class OneGoalPanel extends RPanel
  {
    OneGoalPanel()
    {
      this.setLayout(null);
 
      JLabel img = new JLabel(Ggui.imgIconGet("ONEGOALPANEL"));
      img.setSize(img.getPreferredSize());
      this.add(img);
      this.setPreferredSize(img.getSize());
      this.setMinimumSize(img.getSize());
      this.setMaximumSize(img.getSize());
    }
  }
}
