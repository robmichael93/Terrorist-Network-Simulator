//
//  SaveCharPanel.java
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
import com.armygame.recruits.globals.SavedCharacter;

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

public class SaveCharPanel  extends RPanel
{
  JFileChooser fc;
  MainFrame mf;
  JButton saveButt, delButt;
  JList fileList;
  JTextField jtf;
  JTextArea jta;
  Properties descriptions;
  String selectedListObject;
  String [] names;

  SaveCharPanel(MainFrame main)
  //==========================
  {
    mf = main;
    setLayout(null);

    JPanel content = new JPanel();

    content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

    content.add(Box.createVerticalGlue());

    JLabel title = new JLabel("Name your character and describe it");
    title.setForeground(Ggui.buttonForeground());
    title.setFont(Ggui.bigButtonFont());
    title.setAlignmentX(CENTER_ALIGNMENT);
    content.add(title);

    content.add(Box.createVerticalStrut(10));

    JLabel subtitle = new JLabel("Previously saved characters:");
    subtitle.setForeground(Ggui.lightBackground);
    subtitle.setFont(Ggui.buttonFont());
    subtitle.setAlignmentX(CENTER_ALIGNMENT);
    content.add(subtitle);

    fileList = new RecruitsJList(new String[]{"dummy"}); 
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

    JLabel xtitle = new JLabel("Enter a name for your character:");
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

    JLabel subsubtitle = new JLabel("Enter a description of your character:");
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
    buttonPanel.add(ButtonFactory.makeTextButt(ButtonFactory.SAVECHARCANCEL));
    buttonPanel.add(Box.createHorizontalStrut(50));
    saveButt = ButtonFactory.makeTextButt(ButtonFactory.SAVECHARSAVE);
    buttonPanel.add(saveButt);
    buttonPanel.add(Box.createHorizontalStrut(50));
    saveButt.setEnabled(false);
    delButt = ButtonFactory.makeTextButt(ButtonFactory.SAVECHARDEL);
    buttonPanel.add(delButt);
    delButt.setEnabled(false);
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
        delButt.setEnabled(true);
      }
    });

    jtf.getDocument().addDocumentListener(new DocumentListener()
    {
      public void insertUpdate(DocumentEvent e)
      {
        if(e.getDocument().getLength() > 0) {
          saveButt.setEnabled(true);
        	 delButt.setEnabled(true);
        }
      }

      public void removeUpdate(DocumentEvent e)
      {
        if(e.getDocument().getLength() <= 0) {
          saveButt.setEnabled(false);
        	 delButt.setEnabled(false);
        }
      }

      public void changedUpdate(DocumentEvent e)
      {}
    });

    setVisible(false);
  }

	private void buildList() {
		names = new File("./save").list(new FilenameFilter() {
			public boolean accept(File dir,String name) {
				if(name.endsWith(".sch"))
					return true;
				return false;
			}
			});
		Arrays.sort(names);
		for(int i=0;i<names.length;i++)
			names[i] = names[i].substring(0,names[i].length()-4);
		if(fileList != null) 
			fileList.setListData(names);
		else  
			fileList = new RecruitsJList(names);
		}
  
  private void buildDescriptions() {
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/cdesc"));
      descriptions = (Properties)ois.readObject();
    } catch (FileNotFoundException fnfe) {
      descriptions = new Properties();
    }
    catch (Exception e) {
      System.out.println("temp bp");
    }
  }
  
	private boolean validName(String s) {
		boolean ret = true;
		if ((s == null) || (s.trim().equals("")))
			ret = false;
		System.out.println(ret);
		return(ret);
	}
  
	public boolean saveCharacter(SavedCharacter sc) { 
		boolean ret = true;
		String name = jtf.getText();
		if (!validName(name))
			return(false);
		try {
			name = name.trim();
			if (name.endsWith(".sch"))
				name = name.substring(0, name.length() - 4);
			System.out.println("name:"+name);
      	descriptions.setProperty(name, jta.getText());
      	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/cdesc"));
      	oos.writeObject(descriptions);
      	oos.close();
			oos = new ObjectOutputStream(new FileOutputStream("./save/"+name + ".sch"));
			oos.writeObject(sc);
			oos.close();
		} catch(Exception e) {
			ret = false;
		}
		return(ret);
	}
	
	public void delCharacter() {
		int i = fileList.getSelectedIndex();
		if (i < 0)
			return;
		String s = fileList.getSelectedValue().toString();
		File f = new File("./save/" + s + ".sch");
		f.delete();
		descriptions.remove(s);
		buildList();
		fileList.setSelectedIndex(Math.min(i, names.length-1));
	}

  private void saveDescriptions(Object obj)
  {
    try
    {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/cdesc"));
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
    mf.setTitleBar("SAVECHARTITLE");
    super.go();
    buildList();
    buildDescriptions();
    jta.setText("");
    jtf.setText("");
  }

  boolean cancelled=false;;

  public void done()
  {
    super.done();
  }

  public void cancel()
  {
    cancelled = true;
  }
}
