//
//  CharSelectPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class CharSelectPanel extends RPanel
{
  MainFrame mf;
  JLabel portrait;
  final int scrollIncrement;
  final JPanel scrollHeadPanel;
  int displayedIndex = 0;
  final JButton moveLeftButt,moveRightButt;
  public int selectedIndex = -1;
  public JButton okButt;
  int numHeads;
  final int maxHeads = 6;
  JLabel [] heads;
  int lastSelected = -1;
  Border selectedBorder;

  CharSelectPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    scrollHeadPanel = new JPanel();
    scrollHeadPanel.setLayout(null);
    JPanel scrollBackPanel = new JPanel();
    scrollBackPanel.setLayout(null);

    numHeads = Ggui.getIntProp("NUM_SOLDIERHEADS");
    String[] names = new String[numHeads];
    JPanel[] minis = new JPanel[numHeads];
    JLabel[] backs = new JLabel[numHeads];
    /*JLabel[]*/ heads = new JLabel[numHeads];
    JButton[] selectButt = new JButton[numHeads];
    int[] buttNum = new int[]{ButtonFactory.CHARSELECT0,ButtonFactory.CHARSELECT1,
                              ButtonFactory.CHARSELECT2,ButtonFactory.CHARSELECT3,
                              ButtonFactory.CHARSELECT4,ButtonFactory.CHARSELECT5};
    ActionListener selList = new ActionListener()
    {
       public void actionPerformed(ActionEvent ev)
       {
         int i = Integer.parseInt(ev.getActionCommand());
         if(lastSelected != -1)
           heads[lastSelected].setBorder(null);
         heads[i].setBorder(selectedBorder);
         lastSelected=i;
       }
    };
    selectedBorder = BorderFactory.createLineBorder(Color.green,2);

    int x = 0;
    for(int i=0;i<numHeads;i++)
    {
      names[i] = Ggui.getProp("ACTOR"+i);
      minis[i] = new JPanel();
      minis[i].setLayout(null);
      backs[i] = new JLabel(Ggui.imgIconGet("CHARCREATEMINIPANEL"));
      backs[i].setSize(backs[i].getPreferredSize());
      backs[i].setLocation(0,0);
      heads[i] = new JLabel(Ggui.imgIconGet(names[i] + "portrait")); //"SOLDIERHEAD"+i));
      heads[i].setSize(heads[i].getPreferredSize());
      heads[i].setLocation(30,10);;
      selectButt[i] = ButtonFactory.make(buttNum[i],mf);
      selectButt[i].setLocation(27,95);
      selectButt[i].addActionListener(selList);
      selectButt[i].setActionCommand(""+i);

      minis[i].add(heads[i]);
      minis[i].add(selectButt[i]);
      minis[i].add(backs[i]);
      minis[i].setSize(backs[i].getSize());
      minis[i].setLocation(x,0);
      
      scrollHeadPanel.add(minis[i]);
      x+=backs[i].getWidth();
    }
    scrollIncrement = 3 * backs[0].getWidth();
    

    // backpanel is 3, 6, 9, etc., heads in width
    scrollHeadPanel.setSize(3*((numHeads+3)/3)*backs[0].getWidth(),backs[0].getHeight());
    scrollHeadPanel.setBackground(new Color(51,51,51));
    scrollHeadPanel.setLocation(0,0);

    scrollBackPanel.setSize(3*backs[0].getWidth(),backs[0].getHeight());
    scrollBackPanel.add(scrollHeadPanel);
    scrollBackPanel.setLocation(134,193);
    add(scrollBackPanel);
    
    
    moveLeftButt = ButtonFactory.make(ButtonFactory.CHARSELECTLEFT,mf);
    add(moveLeftButt);
    //moveLeftButt.setEnabled(true); // at left
    moveLeftButt.setEnabled(false); // at left

    moveRightButt = ButtonFactory.make(ButtonFactory.CHARSELECTRIGHT,mf);
    add(moveRightButt);
    //moveRightButt.setEnabled(false); // at 0
    moveRightButt.setEnabled(true); // at 0

    //moveLeftButt.addActionListener(new ActionListener()
    moveRightButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        displayedIndex += 3;
        Point p = new Point();
        scrollHeadPanel.getLocation(p);
        p.x -= scrollIncrement;
        scrollHeadPanel.setLocation(p);
        //moveRightButt.setEnabled(true);
        moveLeftButt.setEnabled(true);
        if(displayedIndex+3 >= numHeads)
          //moveLeftButt.setEnabled(false);
          moveRightButt.setEnabled(false);
      }
    });

    //moveRightButt.addActionListener(new ActionListener()
    moveLeftButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        displayedIndex -= 3;
        Point p = new Point();
        scrollHeadPanel.getLocation(p);
        p.x += scrollIncrement;
        scrollHeadPanel.setLocation(p);
        //moveLeftButt.setEnabled(true);
        moveRightButt.setEnabled(true);
        if(displayedIndex == 0)
          //moveRightButt.setEnabled(false);
          moveLeftButt.setEnabled(false);
      }
    });

    okButt = ButtonFactory.make(ButtonFactory.CREATEOK,mf);
    okButt.setEnabled(false);
    add(okButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("CREATEBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("SELECTSOLDIERTITLE");
  }
}
