//
//  UploadPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Fri Mar 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UploadPanel extends RPanel
{
  Color defBgd;
  JLabel title1Lab;
  JLabel title2Lab;
  JPanel titlePanel;
  JPanel outerTitlePanel;
  JTabbedPane tabs;
  JPanel charPanel;
  JPanel storyPanel;
  JPanel buttPanel;
  JButton canButt;
  JButton okButt;
  JList storyList;
  JScrollPane storySP;
  JTextArea storyTA,charDescribeTA;
  JTextField storyTF,charNameTF;
  JLabel nameLab,charNameLab;
  JLabel describeLab,charDescribeLab;
  
  MainFrame mf;
  UploadPanel(MainFrame main)
  {
    mf = main;
    defBgd = getBackground(); 
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    
    initWidgets();

    //pack();
    //titlePanel.setMaximumSize(titlePanel.getSize());
    Dimension sta = storyTA.getSize();
    sta.width += 250;
    storyTA.setSize(sta);
    //pack();
    //centerMe();
    //show();
    setVisible(false);
  }
  private JPanel titles()
  {
    outerTitlePanel = new JPanel();
    outerTitlePanel.setLayout(new BoxLayout(outerTitlePanel,BoxLayout.X_AXIS));
    outerTitlePanel.add(Box.createHorizontalGlue());
      titlePanel = new JPanel();
      titlePanel.setLayout(new BorderLayout());
        title1Lab = new JLabel("Submit your character or story");
        title2Lab = new JLabel("for other players to use");
        title1Lab.setFont(new Font("SansSerif",Font.PLAIN,14));
        title2Lab.setFont(new Font("SansSerif",Font.PLAIN,14));
        title1Lab.setHorizontalAlignment(SwingConstants.CENTER);
        title2Lab.setHorizontalAlignment(SwingConstants.CENTER);

      titlePanel.setAlignmentY(Component.TOP_ALIGNMENT);
      titlePanel.add(title1Lab,BorderLayout.NORTH);
      titlePanel.add(title2Lab,BorderLayout.SOUTH);
      titlePanel.setBorder(BorderFactory.createEtchedBorder());//createRaisedBevelBorder()); 
    outerTitlePanel.add(titlePanel);
    outerTitlePanel.add(Box.createHorizontalGlue());
    outerTitlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    return outerTitlePanel;
  }
  
  private JTabbedPane tabbedpane()
  {
    tabs = new JTabbedPane();
    tabs.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    charPanel = new JPanel();
    GridBagLayout gridBag = new GridBagLayout();
    charPanel.setLayout(gridBag);
    GridBagConstraints c = new GridBagConstraints();
    
      charNameLab = new JLabel("Name your character");
      charNameLab.setHorizontalAlignment(SwingConstants.CENTER);
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(5,5,5,5);
      gridBag.setConstraints(charNameLab,c);
      charPanel.add(charNameLab);
      
      charNameTF = new JTextField();
      gridBag.setConstraints(charNameTF,c);
      charPanel.add(charNameTF);
      
      charDescribeLab = new JLabel("Describe this story in a few words");
      charDescribeLab.setHorizontalAlignment(SwingConstants.CENTER);
      gridBag.setConstraints(charDescribeLab,c);
      charPanel.add(charDescribeLab);
      
      charDescribeTA = new JTextArea();
        //charDescribeTA.setRows(12);
      charDescribeTA.setBorder(BorderFactory.createEtchedBorder());
      charDescribeTA.setLineWrap(true);
      charDescribeTA.setWrapStyleWord(true);
        //storyTA.setBackground(defBgd);
      charDescribeTA.setToolTipText("Type your description here");
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1.0;
      c.weighty = 1.0;
      gridBag.setConstraints(charDescribeTA,c);
      charPanel.add(charDescribeTA);

      storyPanel = new JPanel();
      storyPanel.add(Box.createVerticalStrut(6));
      storyPanel.setLayout(new BoxLayout(storyPanel,BoxLayout.Y_AXIS));
        JLabel lab1 =  new JLabel("Choose your completed story");
        lab1.setAlignmentX(Component.CENTER_ALIGNMENT);
      storyPanel.add(lab1); 
      storyPanel.add(Box.createVerticalStrut(6));
        storyList = new JList(
        new String[]
        {"Airborne at Ft. Bragg",
        "KP in Basic",
        "Cold weather training at Wainright",
        "155s at Ft. Sill",
        "Huachuca interrogations"});
        storyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        storyList.setVisibleRowCount(4);
        storyList.getSelectionModel().addListSelectionListener(new storyListListener());
        storySP = new JScrollPane(storyList);
        storySP.setAlignmentX(Component.CENTER_ALIGNMENT);
        
      storyPanel.add(storySP);
      storyPanel.add(Box.createVerticalStrut(6));
        nameLab = new JLabel("Name your story");
        nameLab.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLab.setEnabled(false);
      storyPanel.add(nameLab);
      storyPanel.add(Box.createVerticalStrut(6));
        storyTF = new JTextField();
        storyTF.setAlignmentX(Component.CENTER_ALIGNMENT);
        storyTF.setEnabled(false);
      storyPanel.add(storyTF);
      storyPanel.add(Box.createVerticalStrut(6));
        describeLab = new JLabel("Describe this story in a few words");
        describeLab.setAlignmentX(Component.CENTER_ALIGNMENT);
        describeLab.setEnabled(false);
      storyPanel.add(describeLab);
      storyPanel.add(Box.createVerticalStrut(6));
        storyTA = new JTextArea();
        storyTA.setRows(12);
        storyTA.setBorder(BorderFactory.createEtchedBorder());
        storyTA.setAlignmentX(Component.CENTER_ALIGNMENT);
        storyTA.setLineWrap(true);
        storyTA.setWrapStyleWord(true);
        //storyTA.setBackground(defBgd);
        storyTA.setEnabled(false);
        storyTA.setToolTipText("Type your description here");
      storyPanel.add(storyTA);
      
    tabs.addTab("Submit a character", null, charPanel, null);
    tabs.setSelectedIndex(0);
    tabs.addTab("Submit a story", null, storyPanel, null);
    return tabs;
  }
  
  private void initWidgets()
  {
    add(titles());    
    add(Box.createVerticalStrut(12));
    add(tabbedpane());
    add(Box.createVerticalStrut(12));
    add(butts());
  }
  
  private JPanel butts()
  {    
    buttPanel = new JPanel();
    buttPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    buttPanel.setLayout(new BoxLayout(buttPanel,BoxLayout.X_AXIS));
      canButt = ButtonFactory.makeTextButt(ButtonFactory.UPLOADCLOSE);
      //canButt = new JButton("Cancel");
      //canButt.setToolTipText("Close this window and don't submit anything");
      okButt = ButtonFactory.makeTextButt(ButtonFactory.UPLOADSUBMIT);
      //okButt = new JButton("Submit my data");
      //okButt.setToolTipText("Submit my data and close this window");
      //okButt.setEnabled(false);
      //canButt.addActionListener(mf.handlers);
      //canButt.setActionCommand("uploadclose");
      //okButt.addActionListener(mf.handlers);
      //okButt.setActionCommand("uploadsubmit");
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(canButt);
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(okButt);
    buttPanel.add(Box.createHorizontalGlue());
    
    return buttPanel;
  }
  
  private void centerMe()
  {
    Dimension us = this.getSize();
    Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
    int newx = (them.width - us.width) / 2;
    int newy = (them.height - us.height) / 2;
    this.setLocation(newx,newy);
  }
  class storyListListener implements ListSelectionListener
  {
    public void valueChanged(ListSelectionEvent e)
    {
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty())
      {
        storyTF.setEnabled(false);
        nameLab.setEnabled(false);
        storyTA.setEnabled(false);
        describeLab.setEnabled(false);
        okButt.setEnabled(false);
      }
      else if(!e.getValueIsAdjusting())
      {
        //System.out.println(lsm.getMinSelectionIndex());
        storyTF.setEnabled(true);
        nameLab.setEnabled(true);
        storyTA.setEnabled(true);
        describeLab.setEnabled(true);
        okButt.setEnabled(true);
      }
    }
  }
}
