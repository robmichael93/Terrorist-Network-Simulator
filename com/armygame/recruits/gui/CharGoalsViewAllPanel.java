/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 12, 2002
 * Time: 2:57:31 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;
import com.armygame.recruits.globals.SavedGame;
import com.armygame.recruits.globals.SavedCharacter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class CharGoalsViewAllPanel extends RPanel
{
  MainFrame mf;
  //Dimension leftD = new Dimension(57-28,132-60);
  //Dimension rightD= new Dimension(358-28,132-60);
  Point leftP = new Point(57-28,132-60);
  Point rightP= new Point(358-28,132-60);
  JPanel rightDescriptions;
  GoalJTree goalT;
  JTextArea ta;
  Properties descriptions;
  JButton proceedButt;
  String chosenName;
  JPanel leftPanel,rightPanel;
  JLabel rightBack,backLabel;
  ConnectedGoal selectedGoal;

  public CharGoalsViewAllPanel(MainFrame mf)
  {
    this.mf = mf;
    setLayout(null);
    setBackground(Ggui.transparent); // for shadow border

    JLabel header = new JLabel("Select the goal to read its definition.  Click the \"+\" icon to expand the Goal Tree.");
    header.setFont(Ggui.statusLineFont);
    header.setSize(header.getPreferredSize());
    header.setForeground(Color.white);
    header.setLocation(48-28,71-60);
    add(header);

    JLabel leftBack = new JLabel(Ggui.imgIconGet("SUBWINDOWBACK"));
    Dimension d = leftBack.getPreferredSize();
    d.height+=5+5;
    d.width+=5+5;
    leftBack.setSize(d);
    leftBack.setBorder(new ShadowBorder(5));
    leftBack.setLocation(leftP);

    leftPanel = new JPanel();

    fillLeftPanel(leftPanel);
    d.height-=20;
    d.width-=20;
    leftPanel.setSize(d);
    leftPanel.setLocation(new Point(leftP.x+10,leftP.y+10));
leftPanel.setBackground(Ggui.transparent);
    add(leftPanel);
    add(leftBack);

    rightBack= new JLabel(Ggui.imgIconGet("SUBWINDOWBACK"));
    d = rightBack.getPreferredSize();
    d.height+=5+5;
    d.width+=5+5;
    rightBack.setSize(d);
    rightBack.setBorder(new ShadowBorder(5));
    rightBack.setLocation(rightP);


    rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
    d.height-=20;
    d.width-=20;
    rightPanel.setSize(d);
    //fillRightPanel(rightPanel);
    rightPanel.setLocation(new Point(rightP.x+10,rightP.y+10));
rightPanel.setBackground(Ggui.transparent);
    add(rightPanel);
    add(rightBack);


    JButton closeButt = ButtonFactory.make(ButtonFactory.GOALSVIEWALLCLOSE,mf);//LOADCHAR_CANCEL,mf);
    add(closeButt);

    backLabel = new JLabel(Ggui.imgIconGet("GOALSALLBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);

  }

  private void fillLeftPanel(JPanel p)
  {

    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("All Goals");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    p.add(l);

    p.add(Box.createVerticalStrut(10));

    goalT = new GoalJTree(mf.globals.charinsides.goalTree); // put the tree from the global char attrs here
    goalT.addTreeSelectionListener(new TreeSelectionListener()
    {
      public void valueChanged(TreeSelectionEvent e)
      {
        selectedGoal = (ConnectedGoal)goalT.getLastSelectedPathComponent();
        fillRightPanel(rightPanel);
        validate();
      }
    });
    JScrollPane jsp = new JScrollPane(goalT);
    jsp.setAlignmentX(Box.CENTER_ALIGNMENT);
    jsp.setBackground(Ggui.darkBackground);   // border uses
    jsp.setBorder(null); //BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
                          //                                          new Color(91,91,91),
                          //                                          new Color(51,51,51)));
    p.add(jsp);

  }

  private void fillRightPanel(JPanel xp)
  {
    remove(backLabel);
    remove(rightBack);
    remove(rightPanel);

    rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
    Dimension d = rightBack.getSize();
    d.height-=20;
    d.width-=20;
    rightPanel.setSize(d);

    rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("Goal Description");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    rightPanel.add(l);

    rightPanel.add(Box.createVerticalStrut(10));

    CharGoalsHelpPanel2 description = new CharGoalsHelpPanel2();
    description.setGoal(selectedGoal);
    description.setAlignmentX(Box.LEFT_ALIGNMENT);
    description.setSize(description.getPreferredSize());
    rightPanel.add(description);

    rightPanel.setLocation(new Point(rightP.x+10,rightP.y+10));
    //rightPanel.setBackground(Ggui.transparent);
    rightPanel.setOpaque(false);
    add(rightPanel);
    add(rightBack);
    add(backLabel);
  }


  public void go()
  //--------------
  {
    super.go();
    goalT = new GoalJTree(mf.globals.charinsides.goalTree); // put the tree from the global char attrs here
    leftPanel.removeAll();
    fillLeftPanel(leftPanel);
  }

  boolean cancelled=false;;
  public void done()
  {
    cancelled = false;
    super.done();
  }

  public void cancel()
  {
    cancelled = true;
  }


}
