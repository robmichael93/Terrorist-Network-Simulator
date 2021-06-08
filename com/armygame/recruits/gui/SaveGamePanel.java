//
//  SaveGamePanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Mon Mar 18 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
//
//  SaveGamePanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Mon Mar 18 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;
import com.armygame.recruits.storyelements.sceneelements.CharInsides;
import com.armygame.recruits.globals.SavedGame;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Properties;

public class SaveGamePanel  extends RPanel
{
  JFileChooser fc;
  MainFrame mf;
  JButton saveButt;
  JList fileList;
  JTextField jtf;
  JTextArea jta;
  Properties descriptions;
  String selectedListObject;

  SaveGamePanel(MainFrame main)
  //==========================
  {
    mf = main;
    setLayout(null);

    JPanel content = new JPanel();

    content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

    content.add(Box.createVerticalGlue());

    JLabel title = new JLabel("Name your game and describe it");
    title.setForeground(Ggui.buttonForeground());
    title.setFont(Ggui.bigButtonFont());
    title.setAlignmentX(CENTER_ALIGNMENT);
    content.add(title);

    content.add(Box.createVerticalStrut(10));

    JLabel subtitle = new JLabel("Previously saved games:");
    subtitle.setForeground(Ggui.lightBackground);
    subtitle.setFont(Ggui.buttonFont());
    subtitle.setAlignmentX(CENTER_ALIGNMENT);
    content.add(subtitle);

    fileList = new RecruitsJList(new String[]{"dummy"}); //buildList();
    //fileList.setBackground(new Color(38,38,38));
    //fileList.setForeground(new Color(230,179,0));
    //fileList.setSelectionForeground(Color.green);
    //fileList.setSelectionBackground(fileList.getBackground());
     //Font f = fileList.getFont();
    //f = new Font(f.getName(),f.getStyle(),16);
    //fileList.setFont(f);

    JScrollPane jsp = new JScrollPane(fileList);
    jsp.setPreferredSize(new Dimension(400,150));
    jsp.setMaximumSize(jsp.getPreferredSize());
    jsp.setBorder(new ShadowBorder(8,false,24));
    jsp.setAlignmentX(CENTER_ALIGNMENT);

    content.add(jsp);
    content.add(Box.createVerticalStrut(10));

    JLabel xtitle = new JLabel("Enter a name for your game:");
    xtitle.setForeground(Ggui.lightBackground);
    xtitle.setFont(Ggui.buttonFont());
    xtitle.setAlignmentX(CENTER_ALIGNMENT);

    content.add(xtitle);

    jtf = new JTextField("");
    jtf.setMaximumSize(new Dimension (400,40));
    jtf.setAlignmentX(CENTER_ALIGNMENT);
    jtf.setBackground(new Color(38,38,38));
    jtf.setForeground(new Color(230,179,0));
    Font f = jtf.getFont();
    f = new Font(f.getName(),f.getStyle(),16);
    jtf.setFont(f);
    //jtf.setOpaque(false);
    jtf.setBorder(new ShadowBorder(8,false,24));
    jtf.setCaretColor(Ggui.buttonForeground());

    content.add(jtf);

    content.add(Box.createVerticalStrut(10));

    JLabel subsubtitle = new JLabel("Enter a description of your game:");
    subsubtitle.setForeground(Ggui.lightBackground);
    subsubtitle.setFont(Ggui.buttonFont());
    subsubtitle.setAlignmentX(CENTER_ALIGNMENT);
    content.add(subsubtitle);

    jta = new JTextArea("");
    jta.setLineWrap(true);
    jta.setWrapStyleWord(true);
    jta.setBackground(new Color(38,38,38));
    jta.setForeground(new Color(230,179,0));
    jta.setCaretColor(Ggui.buttonForeground());
    f = jta.getFont();
    f = new Font(f.getName(),f.getStyle(),16);
    jta.setFont(f);

    jsp = new JScrollPane(jta);
    jsp.setPreferredSize(new Dimension(400,100));
    jsp.setMaximumSize(jsp.getPreferredSize());
    jsp.setBorder(new ShadowBorder(8,false,24));
    jsp.setAlignmentX(CENTER_ALIGNMENT);

    content.add(jsp);

    content.add(Box.createVerticalStrut(10));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(ButtonFactory.makeTextButt(ButtonFactory.SAVEGAMECANCEL));
    buttonPanel.add(Box.createHorizontalStrut(50));
    saveButt = ButtonFactory.makeTextButt(ButtonFactory.SAVEGAMESAVE);
    buttonPanel.add(saveButt);
    saveButt.setEnabled(false);
    buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
    buttonPanel.add(Box.createHorizontalGlue());

    content.add(buttonPanel);

    content.add(Box.createVerticalGlue());

    content.setLocation(0,0);
    content.setSize(640,480);
    add(content);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("SAVEGAMEBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fileList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if(e.getValueIsAdjusting())
          return;
        if(fileList.isSelectionEmpty())
          return;
        selectedListObject = (String)fileList.getSelectedValue();
        jtf.grabFocus();
        saveButt.setEnabled(true);
      }
    });

    jtf.getDocument().addDocumentListener(new DocumentListener()
    {
      public void insertUpdate(DocumentEvent e)
      {
        if(e.getDocument().getLength() > 0)
          saveButt.setEnabled(true);
      }

      public void removeUpdate(DocumentEvent e)
      {
        if(e.getDocument().getLength() <= 0)
          saveButt.setEnabled(false);
      }

      public void changedUpdate(DocumentEvent e)
      {}
    });

    setVisible(false);
  }

  JList buildList(JList lis)
  {
    String [] names = new File("./save").list(new FilenameFilter()
    {
      public boolean accept(File dir,String name)
      {
        if(name.endsWith(".sgm"))
          return true;
        return false;

      }
    });
    Arrays.sort(names);
    for(int i=0;i<names.length;i++)
      names[i] = names[i].substring(0,names[i].length()-4);

    if(lis != null)
    {
      lis.setListData(names);
      return lis;
    }
    return new RecruitsJList(names);
  }
  private void buildDescriptions()
  {
    try
    {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/gdesc"));
      descriptions = (Properties)ois.readObject();
    }
    catch (FileNotFoundException fnfe)
    {
      descriptions = new Properties();
    }
    catch (Exception e)
    {
      System.out.println("temp bp");
    }
  }
  private String saveGame(String name)
  {
    name = name.replace('/','_');
    name = name.replace('\\','_');
    name = name.replace(' ','_');
    String newname=name;
    if(!name.endsWith(".sgm"))
      name = name + ".sgm";
    else
      newname = name.substring(0,name.length()-4);
    SavedGame sg = new SavedGame();
    try
    {
    ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("./save/"+name));
    ois.writeObject(sg);
    ois.close();
    }
    catch(Exception e)
    {
      System.out.println("error saving game");
    }
    return newname;
  }

  private void saveDescriptions(Object obj)
  {
    try
    {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/gdesc"));
      oos.writeObject(obj);
    }
    catch(Exception e)
    {
      System.out.println("error saving descriptions");
    }

  }

  public void go()
  //--------------
  {
    mf.setTitleBar("SAVEGAMETITLE");
    super.go();
    buildList(fileList);
    buildDescriptions();
    jta.setText("");
    jtf.setText("");
  }

  boolean cancelled=false;;

  public void done()
  {
    if(!cancelled)
    {
      String namest = jtf.getText();
      if(namest.endsWith(".sgm"))
        namest = namest.substring(0,namest.length()-4);
      namest = saveGame(namest);
      descriptions.setProperty(namest,jta.getText());
      saveDescriptions(descriptions);
    }
    cancelled = false;
    super.done();
  }

  public void cancel()
  {
    cancelled = true;
  }
}
