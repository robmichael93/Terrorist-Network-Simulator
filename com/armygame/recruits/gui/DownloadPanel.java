//
//  DownloadPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Fri Mar 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class DownloadPanel extends RPanel
{
  MainFrame mf;

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
  JTextArea storyTA;
  JTextField storyTF;
  JLabel nameLab;
  JLabel describeLab;
  JTable charTable,storyTable;
  JLabel charWaitLabel;
  int selectedTableRow = -1;

  ImageIcon test;

  DownloadPanel(MainFrame main)
  {
    mf = main;
    initFields();
    defBgd = getBackground();
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

    test = Ggui.imgIconGet("SOLDIERPORTRAIT");
    initWidgets();
    //pack();
//titlePanel.setMaximumSize(titlePanel.getSize());
    //centerMe();
    //show();
    setVisible(false);
  }
  public void go()
  {
    new Thread(new Runnable (){
    public void run()
    {
        try{Thread.sleep(2000);}catch(Exception e){}
        charWaitLabel.setText("Choose a character to download");
        charWaitLabel.setForeground(Color.black);
        makeRealCharTable();
        makeRealStoryTable();
        charPanel.invalidate();
        storyPanel.invalidate();
    }
    }).start();
    super.go();
  }
  private JPanel titles()
  {
    outerTitlePanel = new JPanel();
    outerTitlePanel.setLayout(new BoxLayout(outerTitlePanel,BoxLayout.X_AXIS));
    outerTitlePanel.add(Box.createHorizontalGlue());
      titlePanel = new JPanel();
      titlePanel.setLayout(new BorderLayout());
        title1Lab = new JLabel("Download characters and stories");
        title2Lab = new JLabel("which other players have submitted");
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
      charPanel.setLayout(new BoxLayout(charPanel,BoxLayout.Y_AXIS));
      charPanel.add(Box.createVerticalStrut(12));
        charWaitLabel = new JLabel("Please wait while accessing character list");
        charWaitLabel.setForeground(Color.red);
        charWaitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      charPanel.add(charWaitLabel);
      charPanel.add(Box.createVerticalStrut(12));
      
      charPanel.add(makeEmptyCharTable());
      
      storyPanel = new JPanel();
      storyPanel.setLayout(new BoxLayout(storyPanel,BoxLayout.Y_AXIS));
      storyPanel.add(Box.createVerticalStrut(6));
        JLabel lab1 =  new JLabel("Choose a story to download");
        lab1.setAlignmentX(Component.CENTER_ALIGNMENT);
      storyPanel.add(lab1); 
      storyPanel.add(Box.createVerticalStrut(6));
      
      storySP = makeEmptyStoryTable();
      storyPanel.add(storySP);

      
    tabs.addTab("Get a character", null, charPanel, null);
    tabs.setSelectedIndex(0);
    tabs.addTab("Get a story", null, storyPanel, null);
    return tabs;
  }
  Object emptyObjs[][];
  Object realObjs[][];
  String cols[];
  Object realStoryObjs[][];
  Object emptyStoryObjs[][];
  String storyCols[];
  private void initFields()
  {
    String artPath = System.getProperty("user.dir")+"/art/";

      /*Object[][]*/ emptyObjs = new Object[][]{
      {" "," "," "," "," "},
      {" "," "," "," "," "},
      {" "," "," "," "," "},
      {" "," "," "," "," "}};
        
      /*Object[][]*/ realObjs = new Object[][]{
      {"Bruno", Ggui.imgIconGet("SOLDIERPORTRAIT"),"Bumped up my integrity, man!","skateDude","Dec. 13, 2001"},
      {"Felix", new ImageIcon(artPath+"character.jpg"),"Lying sob","BillBob","June 1, 2001"},
      {"Bubba", new ImageIcon(artPath+"week09v5.jpg"), "Sliders all mid-point","Tex21","Feb. 12, 2002"}
      };
      
      /*String[]*/ cols = new String[]{"Name","","Description","Submitter","Date submitted"};
      
      /*Object[][]*/ realStoryObjs = new Object[][]{
        {"My trip through BASIC",          Ggui.imgIconGet("SOLDIERPORTRAIT"),"skateDude","Mar. 15, 2001"},
        {"The AIT from hell",              Ggui.imgIconGet("SOLDIERPORTRAIT"),"jim431","Dec. 13, 2001"},
        {"Ranger training at Ft. Benning", new ImageIcon(artPath+"character.jpg"),"rambobubba","June 1, 2001"},
        {"Cook school",                    new ImageIcon(artPath+"week09v5.jpg"), "fryGuy", "Feb. 12, 2002"}
      };

      /*Object[][]*/ emptyStoryObjs = new Object[][]{
      {" "," "," "," "},
      {" "," "," "," "},
      {" "," "," "," "},
      {" "," "," "," "}};
    /*String[]*/ storyCols = new String[]{"Story name","","Submitter","Date submitted"};
  }  
  
  private JComponent makeEmptyCharTable()
  {
    charTable = doTable(emptyObjs,cols);
    adjustCharTable();
    return new JScrollPane(charTable);
  }
  private JScrollPane makeEmptyStoryTable()
  {
    storyTable = doTable(emptyStoryObjs,storyCols);
    adjustStoryTable();
    return new JScrollPane(storyTable);
  }
  private void makeRealCharTable()
  {
    charTable.setModel(new charTM(realObjs,cols));    
    adjustCharTable();
  }
  
  private void makeRealStoryTable()
  {
    storyTable.setModel(new charTM(realStoryObjs,storyCols));
    adjustStoryTable();
  }
  
  private void adjustCharTable()
  {
    charTable.setPreferredScrollableViewportSize(new Dimension (600,300));
    charTable.setShowHorizontalLines(true);
    charTable.setShowVerticalLines(true);
    charTable.setIntercellSpacing(new Dimension(2,2));
    charTable.setRowHeight(test.getIconHeight());
    charTable.getColumnModel().getColumn(0).setPreferredWidth(120);
    charTable.getColumnModel().getColumn(1).setPreferredWidth(test.getIconWidth());
    charTable.getColumnModel().getColumn(2).setPreferredWidth(250);
    charTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    charTable.getColumnModel().getColumn(4).setPreferredWidth(130);
  }
  private void adjustStoryTable()
  {
    storyTable.setPreferredScrollableViewportSize(new Dimension (600,300));
    storyTable.setShowHorizontalLines(true);
    storyTable.setShowVerticalLines(true);
    storyTable.setIntercellSpacing(new Dimension(2,2));
    storyTable.setRowHeight(test.getIconHeight());
    storyTable.getColumnModel().getColumn(0).setPreferredWidth(300);
    storyTable.getColumnModel().getColumn(1).setPreferredWidth(test.getIconWidth());
    storyTable.getColumnModel().getColumn(2).setPreferredWidth(150);
    storyTable.getColumnModel().getColumn(3).setPreferredWidth(150);
  }
  private JTable doTable(Object objs[][], String cols[])
  {
    JTable myTable = new JTable();
    myTable.setModel(new charTM(objs,cols));
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.getSelectionModel().addListSelectionListener(new tabSelector());
    return myTable;
  }
  class tabSelector implements ListSelectionListener
  {
    public void valueChanged(ListSelectionEvent e)
    {    
      //Ignore extra messages.
      if (e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if (lsm.isSelectionEmpty())
      {
        //no rows are selected
      }
      else
      {
        selectedTableRow = lsm.getMinSelectionIndex();
        okButt.setEnabled(true);
      }
    }
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
      canButt = ButtonFactory.makeTextButt(ButtonFactory.DOWNLOADCLOSE);
      //canButt = new JButton("Cancel");
      //canButt.setToolTipText("Close this window and don't retrieve anything");
      //okButt = ButtonFactory.makeTempTextButt(ButtonFactory.DOWNLOAD,mf);
      okButt = new JButton("Download");
      okButt.setToolTipText("Download the selected stories and characters and close this window");
      okButt.setEnabled(false);
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(canButt);
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(okButt);
    buttPanel.add(Box.createHorizontalGlue());

    //canButt.addActionListener(mf.handlers);
    //canButt.setActionCommand("downloadclose");

/*    okButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JOptionPane.showMessageDialog(null,"The selected characters and stories are being downloaded.","Retrieving...",JOptionPane.INFORMATION_MESSAGE);
        try{Thread.sleep(3000);}catch(Exception ex){}
        //Download.this.hide();
      }
    });
 */   
    return buttPanel;
  }
  
 // private void centerMe()
 // {
 //   Dimension us = this.getSize();
 //   Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
 //   int newx = (them.width - us.width) / 2;
 //   int newy = (them.height - us.height) / 2;
 //   this.setLocation(newx,newy);
 // }
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
  class charTM extends AbstractTableModel
  {
    Object[][] objs;
    Object[] hdrs;
    charTM(Object[][]obs, Object[]hs )
    {
      super();
      objs = obs;
      hdrs = hs;
    }
    public int getRowCount()
    {
      return objs.length;
    }
    public int getColumnCount()
    {
      return objs[0].length;
    }
    public Object getValueAt(int row, int col)
    {
      return objs[row][col];
    }
    public Class getColumnClass(int colIdx)
    {
      return objs[0][colIdx].getClass();
    }
    public String getColumnName(int col)
    {
      return hdrs[col].toString();
    }
  }
}

