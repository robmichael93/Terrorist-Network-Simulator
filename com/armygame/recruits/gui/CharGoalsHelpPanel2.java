//
//  CharGoalsHelpPanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Thu Apr 18 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import com.armygame.recruits.storyelements.sceneelements.Goal;
import com.armygame.recruits.gui.laf.ShadowBorder;

public class CharGoalsHelpPanel2 extends JPanel
{
  MainFrame mf;
  ConnectedGoal cgoal;
  Insets xborders;
  JLabel contentJLabel;
  JLabel iconLab;
  JEditorPane content;
  JButton xcloseButt;
  JLabel xbgLabel;
  TitledBorder xtitledBorder;
  //RecruitsEditpaneScroller res;
  JScrollPane jsp;
  Icon myIcon;

  String html1 = "<html bgcolor='#3C3C3C'><table width='250' border='0'>";
                 //"<tr><td><font face='Arial'><b>Goal</b>: ";
  String html2 = //"</td></tr>"+
               "<tr><td><font face='Arial' color=white><b>Prerequisite goals</b>:";
  String htmlUL = "<UL";
  String htmlUnUL = "</UL>";
  String htmlLI = "<LI>";
  //Work Hard<LI>College AA Degree
  String html3 = "</td></tr>"+
               "<tr><td><font face='Arial' color=white><b>Cost</b>: ";
  //25 value units
  String html4 = "</td></tr>"+
               "<tr><td><font face='Arial' color=white><b>Requirements</b>:";
  //+20 resource points
  String html5 = "</td></tr>"+
               "<tr><td><font face='Arial' color=white><b>Results</b>:";
  //Marriage
  String html6 = "</td></tr>"+
               "<tr><td><font face='Arial' color=white><b>Allows</b>:";
  //Goal x
  String html7 = "</td></tr>"+
               "</font>"+
               "</table></html>";

  CharGoalsHelpPanel2()
  {
    //setBackground(Ggui.darkBackground);
    setBackground(Ggui.transparent);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    //myIcon = Ggui.imgIconGet("DEFAULT_GOAL");
    makeit("<html><body>&nbsp;</body></html>","");
  }

  private void makeit(String s, String gName)
  {
    removeAll();

    JPanel titlePanel = new JPanel();
    titlePanel.setAlignmentX(CENTER_ALIGNMENT);
    titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
    titlePanel.setBackground(Ggui.darkBackground);

    JTextArea nameLabel= new JTextArea(gName);
    nameLabel.setAlignmentY(TOP_ALIGNMENT);
    nameLabel.setFont(Ggui.bigButtonFont());
    nameLabel.setBackground(Ggui.darkBackground);
    nameLabel.setForeground(Color.white);

    nameLabel.setLineWrap(true);
    nameLabel.setWrapStyleWord(true);
    titlePanel.add(nameLabel);

    titlePanel.add(Box.createHorizontalGlue());
    titlePanel.add(Box.createHorizontalStrut(3));

    if(myIcon != null)
    {
      iconLab = new JLabel(myIcon);
      iconLab.setAlignmentY(TOP_ALIGNMENT);
      titlePanel.add(iconLab);
    }

    add(titlePanel);
    add(Box.createVerticalStrut(10));

    makeContent(s);

    jsp = new JScrollPane(content);
    jsp.setPreferredSize(new Dimension(270,285));
    jsp.setBorder(null);
    jsp.setBorder(BorderFactory.createLineBorder(Color.black,1));
    jsp.setBackground(Ggui.darkBackground);

    add(jsp);

    add(Box.createVerticalStrut(10));

    // Can't figure out how else to make the page start out at the top
    Timer tim=new Timer(400,new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
      }
    });
    tim.setRepeats(false);
    tim.start();

   }
  private void makeContent(String s)
  {
    content = new JEditorPane();
    content.setContentType("text/html");
    content.setText(s);
    content.setEditable(false);
    content.setFont(Ggui.buttonFont());

    content.setAlignmentX(CENTER_ALIGNMENT);
    content.setBorder(null);
    content.setBackground(Ggui.darkBackground);
  }
  public void setGoal(Object go)
  {
    if(go == null) return;
    cgoal = (ConnectedGoal)go;
    rebuild();
  }
  private void doArray(String preprelim, StringBuffer sb, String prelim, String[] sa, String postlim)
  {
    boolean needPrePrelim = true;
    for(int i=0;i<sa.length;i++)
    {
      if(sa[i] != null)
      {
        if(needPrePrelim)
        {
          needPrePrelim = false;
          sb.append(preprelim);
        }
        sb.append(prelim);
        sb.append(sa[i]);
      }
    }
    if(needPrePrelim == false)
      sb.append(postlim);
  }

  private void doArrayList(String preprelim, StringBuffer sb, String prelim, ArrayList al, String postlim)
  {
    boolean needPrePrelim = true;
    //Iterator it = al.iterator();
    //while(it.hasNext())
    for(Iterator it = al.iterator(); it.hasNext();)
    {
      ConnectedGoal o = (ConnectedGoal)it.next();
      if(o.goalName.equals("<null>") || o.goalName.equals("")) ;
      else
      {
        if(needPrePrelim)
        {
          needPrePrelim = false;
          sb.append(preprelim);
        }

        sb.append(prelim);
        sb.append(o.goalName);
      }
    }
    if(needPrePrelim == false)
      sb.append(postlim);

  }

  private void rebuild()
  {
    myIcon = cgoal.icon;

    //titledBorder.setTitle (cgoal.toString());
    StringBuffer sb = new StringBuffer();
    sb.append(html1);
 //   sb.append(cgoal.goal.getName());

    sb.append(html2);

    doArrayList(htmlUL,sb,htmlLI,cgoal.prereqs,htmlUnUL);

    sb.append(html3);
    sb.append(""+cgoal.goal.getCost());
    sb.append(html4);

    doArray(htmlUL,sb,htmlLI,cgoal.goal.getRequirementsStringArray(),htmlUnUL);


    sb.append(html5);

    doArray(htmlUL,sb,htmlLI,cgoal.goal.getResultsStringArray(),htmlUnUL);

    sb.append(html6);

    doArrayList(htmlUL,sb,htmlLI,cgoal.prereqOf,htmlUnUL);

    sb.append(html7);

    //contentJLabel.setText(sb.toString());
    makeit(sb.toString(),cgoal.goalName);
  }
}
