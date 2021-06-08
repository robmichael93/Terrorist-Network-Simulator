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
import com.armygame.recruits.storyelements.sceneelements.Goal;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

public class CharGoalsListPanel extends RPanel
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
  JButton selectButt,cancelButt;
  String chosenName;
  JPanel leftPanel,rightPanel;
  ConnectedGoal selectedGoal;

  public CharGoalsListPanel(MainFrame mf)
  {
    this.mf = mf;
    setLayout(null);
    setBackground(Ggui.transparent); // for shadow border

    JLabel header = new JLabel("Select the goal to read its definition.");
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
 //   fillLeftPanel(leftPanel);
    d.height-=20;
    d.width-=20;
    leftPanel.setSize(d);
    leftPanel.setLocation(new Point(leftP.x+10,leftP.y+10));
leftPanel.setBackground(Ggui.transparent);
    add(leftPanel);
    add(leftBack);

    JLabel rightBack= new JLabel(Ggui.imgIconGet("SUBWINDOWBACK"));
    d = rightBack.getPreferredSize();
    d.height+=5+5;
    d.width+=5+5;
    rightBack.setSize(d);
    rightBack.setBorder(new ShadowBorder(5));
    rightBack.setLocation(rightP);

    rightPanel = new JPanel();
    fillRightPanel(rightPanel);
    d.height-=20;
    d.width-=20;
    rightPanel.setSize(d);
    rightPanel.setLocation(new Point(rightP.x+10,rightP.y+10));
    rightPanel.setOpaque(false);
    add(rightPanel);
    add(rightBack);

    cancelButt = ButtonFactory.make(ButtonFactory.GOALSLISTCANCEL,mf);//LOADCHAR_CANCEL,mf);
    add(cancelButt);
    selectButt = ButtonFactory.make(ButtonFactory.GOALSLISTSELECT,mf);//LOADCHAR_CANCEL,mf);
    selectButt.setEnabled(false);
    add(selectButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("GOALSLISTBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);

  }

  private void fillLeftPanel(JPanel p)
  {
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("Available Goals");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    p.add(l);

    p.add(Box.createVerticalStrut(10));
/*
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
    */
    JScrollPane jsp = buildList(gatherGoals());

    jsp.setAlignmentX(Box.CENTER_ALIGNMENT);
    jsp.setBackground(Ggui.darkBackground);   // border uses
    jsp.setBorder(null); //BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
                          //                                          new Color(91,91,91),
                          //                                          new Color(51,51,51)));
    p.add(jsp);

  }

  private void fillRightPanel(JPanel p)
  {
    p.removeAll();
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("Goal Description");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    p.add(l);

    p.add(Box.createVerticalStrut(10));

    CharGoalsHelpPanel2 description = new CharGoalsHelpPanel2();
    if(selectedGoal != null)
      description.setGoal(selectedGoal);
    description.setAlignmentX(Box.LEFT_ALIGNMENT);
    description.setSize(description.getPreferredSize());
    p.add(description);

  }


  public void go()
  //--------------
  {
    super.go();
 //   goalT = new GoalJTree(mf.globals.charinsides.goalTree); // put the tree from the global char attrs here
    selectButt.setEnabled(false);
    leftPanel.removeAll();
    fillLeftPanel(leftPanel);
    selectedGoal = null;
    fillRightPanel(rightPanel);
  }

  boolean cancelled=false;;
  public void done()
  {
    if(cancelled == true)
      cancelled = false;
    else
    {
      ConnectedGoal g = (ConnectedGoal)goalList.getSelectedValue();
      if(g != null)
        mf.charGoalsPanel.goalChosen(g);
    }
    super.done();
  }

  public void cancel()
  {
    cancelled = true;
  }

  private Object[] gatherGoals()
  {
    ArrayList al = new ArrayList();
    Goal[] chGoals = mf.charGoalsPanel.goals;
    //bigloop: while(it.hasNext())
    bigloop:for(Iterator it = mf.globals.charinsides.goalTree.goals.values().iterator();
         it.hasNext();)
    {
      ConnectedGoal cg = (ConnectedGoal)it.next();
      if(cg.goal.isSelectable() && !cg.goal.isSelected())
      {
        for(int i=0;i<chGoals.length; i++)
        {
          Goal g = chGoals[i];
          if(g != null)
            if(g.equals(cg.goal))
              continue bigloop;
        }
        al.add(cg);
      }
    }
    Object[] oa = al.toArray();

    Arrays.sort(oa,new Comparator()
    {
      public int compare(Object o1, Object o2)
      {
        return ((ConnectedGoal)o1).toString().compareTo(((ConnectedGoal)o2).toString());
      }
      public boolean equals(Object obj)
      {
        return this.equals(obj);
      }
    });
    return oa;
  }
  /*
    if(jsp != null)
      remove(jsp);
    jsp = buildList(oa);
    add(jsp);

  } */
  JList goalList;
  private JScrollPane buildList(Object[] oa)
  {
    //final JList jlist = new RecruitsJList(oa);
    goalList = new RecruitsJList(oa);
    goalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    goalList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        selectedGoal = (ConnectedGoal)goalList.getSelectedValue();
        fillRightPanel(rightPanel);
        selectButt.setEnabled(true);
   validate();
      }
    });

    JScrollPane jsp = new JScrollPane(goalList);
    jsp.setBorder(null);
    jsp.setAlignmentX(Box.CENTER_ALIGNMENT);
    jsp.setBackground(Ggui.darkBackground);
    return jsp;
  }
}
