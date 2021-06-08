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
import java.util.ArrayList;
import java.util.Iterator;

import com.armygame.recruits.storyelements.sceneelements.Goal;
import com.armygame.recruits.gui.laf.ShadowBorder;

public class CharGoalsHelpPanel  extends RPanel
{
  MainFrame mf;
  ConnectedGoal cgoal;
  JPanel enclosingPanel;
  Insets borders;
  JLabel contentJLabel;
  JLabel iconLab;
  JEditorPane content;
  JButton closeButt;
  JLabel bgLabel;
  TitledBorder titledBorder;
  //RecruitsEditpaneScroller res;
  JScrollPane jsp;
  Icon myIcon;

  String html1 = "<html bgcolor='#3C3C3C'><table width='300' border='0'>"+
                 "<tr><td><font face='Arial'><b>Goal</b>: ";
  //Qualify: Basic PT 2-Mile Run
  String html2 = "</td></tr>"+
               "<tr><td><font face='Arial'><b>Prerequisite goals</b>:";
  String htmlUL = "<UL";
  String htmlUnUL = "</UL>";
  String htmlLI = "<LI>";
  //Work Hard<LI>College AA Degree
  String html3 = "</td></tr>"+
               "<tr><td><font face='Arial'><b>Cost</b>: ";
  //25 value units
  String html4 = "</td></tr>"+
               "<tr><td><font face='Arial'><b>Requirements</b>:";
  //+20 resource points
  String html5 = "</td></tr>"+
               "<tr><td><font face='Arial'><b>Results</b>:";
  //Marriage
  String html6 = "</td></tr>"+
               "<tr><td><font face='Arial'><b>Allows</b>:";
  //Goal x
  String html7 = "</td></tr>"+
               "</font>"+
               "</table></html>";

  CharGoalsHelpPanel(MainFrame main)
  {
    mf = main;
    //setBackground(Ggui.darkBackground);
    setLayout(null);
    myIcon = Ggui.imgIconGet("DEFAULT_GOAL");
    makeit("<html><body>dummy</body></html>","Goal help");
    setVisible(false);
  }

  private void makeit(String s, String helpstring)
  {
    removeAll();
    enclosingPanel = new JPanel();
    enclosingPanel.setLayout(new BoxLayout(enclosingPanel,BoxLayout.Y_AXIS));
    enclosingPanel.setBackground(Ggui.darkBackground);
    titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
    titledBorder.setTitle(helpstring);

    enclosingPanel.setBorder(titledBorder);
    borders = enclosingPanel.getInsets();

    iconLab = new JLabel(myIcon);
    iconLab.setAlignmentX(CENTER_ALIGNMENT);
    enclosingPanel.add(iconLab);

    enclosingPanel.add(Box.createVerticalStrut(10));

    makeContent(s);


    jsp = new JScrollPane(content);
    jsp.setBorder(null);
    jsp.setBorder(new CompoundBorder(new ShadowBorder(8),
                                   BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
                                                                    new Color(91,91,91),
                                                                    new Color(51,51,51))));
    jsp.setPreferredSize(new Dimension(400,300));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setBackground(Ggui.darkBackground);

    enclosingPanel.add(jsp);

    enclosingPanel.add(Box.createVerticalStrut(10));

    closeButt = ButtonFactory.make(ButtonFactory.GOALHELPCLOSE,mf);
    closeButt.setAlignmentX(CENTER_ALIGNMENT);

    enclosingPanel.add(closeButt);

    enclosingPanel.setSize(enclosingPanel.getPreferredSize());
    enclosingPanel.setLocation(centerMe());
    add(enclosingPanel);

    bgLabel = new JLabel();
    bgLabel.setBackground(Ggui.darkBackground); //new Color(0,0,0,128));    // do this for a transparent bgrnd
    bgLabel.setSize(640,480);
    bgLabel.setOpaque(true);
    add(bgLabel);
  }
  private void makeContent(String s)
  {
    content = new JEditorPane();
    content.setContentType("text/html");
    content.setText(s);
    content.setEditable(false);
    //content.setFont(new Font("Arial",Font.PLAIN,24));
    content.setAlignmentX(CENTER_ALIGNMENT);
    content.setBorder(null); //BorderFactory.createLineBorder(Color.black,2));
    content.setBackground(Ggui.lightBackground);
  }
  public void setGoal(Object go)
  {
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
    sb.append(cgoal.goal.getName());

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
    //enclosingPanel.setSize(enclosingPanel.getPreferredSize());
    //enclosingPanel.setLocation(centerMe());
  }
  private Point centerMe()
  {
    //Insets ins = enclosingPanel.getInsets();
    int x = (640-enclosingPanel.getWidth())/2; //-ins.left-ins.right)/2;
    int y = (480-enclosingPanel.getHeight())/2; //-ins.top-ins.bottom)/2;
    return new Point(x,y);
  }
}
