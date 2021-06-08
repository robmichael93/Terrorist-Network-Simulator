/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jul 29, 2002
 * Time: 8:53:24 AM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.RecruitsMain;
import com.armygame.recruits.globals.ResourceReader;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collections;
import java.io.FileReader;
import java.io.BufferedReader;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

public class SceneListTestPanel extends RPanel
{
  MainFrame mf;
  RecruitsJList list;
  JPanel contentPanel;
  SceneListTestPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Ggui.darkBackground);
    contentPanel.setLocation(50,50);
    contentPanel.setSize(540,380);
    add(contentPanel);

    add(ButtonFactory.make(ButtonFactory.SCENETESTCANCEL,mf));

    JLabel backLabel = new JLabel(Ggui.imgIconGet("SCENETESTBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  void buildList()
  {
    Vector v = new Vector();
    StringBuffer sb = new StringBuffer(128);
    BufferedReader br = new BufferedReader(ResourceReader.getInputReaderData("Scenes/index.txt"));
    try
    {
      SAXBuilder bldr=new SAXBuilder();
      for(String line=br.readLine();line!=null;line=br.readLine())
      {
        sb.delete(0,sb.length());

        Document doc=null;
        try
        {
          doc=bldr.build(ResourceReader.getInputReaderData("Scenes/"+line));

        }
        catch(Exception e)
        {
          System.out.println("Missing data/Scenes/"+line);
        }
        Element root=doc.getRootElement();
        root = root.getChild("Name");
        if(root != null)
          sb.append(root.getTextTrim());
        sb.append(" :/: ");
        sb.append(line);

        v.add(sb.toString());
      }
    }
    catch(Exception e)
    {
      System.out.println("SceneListTestPanel: "+e) ;
    }
    Collections.sort(v);
    list = new RecruitsJList(v.toArray());
    contentPanel.add(new JScrollPane(list),BorderLayout.CENTER);
    list.addMouseListener(new myMouseAdapter());
  }
  class myMouseAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      if(e.getClickCount() == 2)
      {
        int index = list.locationToIndex(e.getPoint());
        String val = (String)list.getSelectedValue();
        index = val.indexOf(" :/: ");
        val = val.substring(index+5);
        mf.handlers.eventIn(ButtonFactory.SCENETESTSCENECHOSEN,val);
      }
    }
  }

  public String getChosenScene()
  {
    String s = (String)list.getSelectedValue();
    if(s==null)return s;
    int idx = s.indexOf(" :/: ",0);
    return s.substring(idx+5);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar(null);
    if(list == null)
      buildList();
  }
}
