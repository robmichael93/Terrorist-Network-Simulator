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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class LoadCharPanel extends RPanel
{
  MainFrame mf;
  //Dimension leftD = new Dimension(57-28,132-60);
  //Dimension rightD= new Dimension(358-28,132-60);
  Point leftP = new Point(57-28,132-60);
  Point rightP= new Point(358-28,132-60);
  JList nameList;
  JTextArea ta;
  Properties descriptions;
  JButton proceedButt;
  String chosenName;

  public LoadCharPanel(MainFrame mf)
  {
    this.mf = mf;
    setLayout(null);
    setBackground(Ggui.transparent); // for shadow border

    JLabel header = new JLabel("Select a previously created soldier and then proceed");
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

    JPanel leftNames = new JPanel();
    fillLeftPanel(leftNames);
    d.height-=20;
    d.width-=20;
    leftNames.setSize(d);
    leftNames.setLocation(new Point(leftP.x+10,leftP.y+10));
leftNames.setBackground(Ggui.transparent);
    add(leftNames);
    add(leftBack);

    JLabel rightBack= new JLabel(Ggui.imgIconGet("SUBWINDOWBACK"));
    d = rightBack.getPreferredSize();
    d.height+=5+5;
    d.width+=5+5;
    rightBack.setSize(d);
    rightBack.setBorder(new ShadowBorder(5));
    rightBack.setLocation(rightP);

    JPanel rightDescriptions = new JPanel();
    fillRightPanel(rightDescriptions);
    d.height-=20;
    d.width-=20;
    rightDescriptions.setSize(d);
    rightDescriptions.setLocation(new Point(rightP.x+10,rightP.y+10));
rightDescriptions.setBackground(Ggui.transparent);
    add(rightDescriptions);
    add(rightBack);

    proceedButt = ButtonFactory.make(ButtonFactory.LOADCHAR_PROCEED,mf);
    proceedButt.setEnabled(false);
    add(proceedButt);
    JButton cancelButt = ButtonFactory.make(ButtonFactory.LOADCHAR_CANCEL,mf);
    add(cancelButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("LOADCHARBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    nameList.addMouseListener(new myMouseAdapter());
    setVisible(false);

  }
  class myMouseAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      if (e.getClickCount() == 2)
      {
        //doubleClickedIndex = jlist.locationToIndex(e.getPoint());
        //if(doubleClickedIndex != -1)
         // mf.handlers.eventIn(ButtonFactory.CANNED_DOUBLE_CLICKED, buildString(doubleClickedIndex));
      }
      else if (e.getClickCount() == 1)
      {
        int idx = nameList.locationToIndex(e.getPoint());
        if(idx != -1)
        {
          chosenName = names[idx];
          String desc = descriptions.getProperty(names[idx]);
          if(desc == null || desc == "")
            ta.setText("no description");
          else
            ta.setText(desc);
          proceedButt.setEnabled(true);
          mf.clickButton();
        }
      }
    }
  }

  private void fillLeftPanel(JPanel p)
  {
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("Soldiers");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    p.add(l);

    p.add(Box.createVerticalStrut(10));

    nameList = new RecruitsJList(new String[]{"Shelby Carrington","Tamera Davis", "James (Jim) Roarke"});
    JScrollPane jsp = new JScrollPane(nameList);
    jsp.setPreferredSize(new Dimension(p.getWidth(),50));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setAlignmentX(Box.LEFT_ALIGNMENT);
    jsp.setBackground(Ggui.medDarkBackground);
    jsp.setBorder(null);
    p.add(jsp);
  }

  private void fillRightPanel(JPanel p)
  {
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

    JLabel l = new JLabel("Personality");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    l.setFont(Ggui.bigButtonFont());
    l.setForeground(Color.white);
    p.add(l);

    p.add(Box.createVerticalStrut(10));

    l = new JLabel("Tamera Davis");
    l.setAlignmentX(Box.LEFT_ALIGNMENT);
    Font f = Ggui.bigButtonFont();
    l.setFont(new Font(f.getFontName(),Font.BOLD,f.getSize()));
    l.setForeground(Color.white);

    p.add(Box.createVerticalStrut(5));

    ta = new JTextArea();
    ta.setAlignmentX(Box.LEFT_ALIGNMENT);
    ta.setFont(Ggui.statusLineFont);
    ta.setBackground(Ggui.darkBackground);      // transparent doesn't work here
    ta.setForeground(Color.white);
    ta.setWrapStyleWord(true);
    ta.setLineWrap(true);
    ta.setEditable(false);
    ta.setText(
        "Bio:  From a large family.  Combative by nature. "+
        "Her greatest fear is displeasing her parents. "+
        "Her turn-ons are the beach and waffles.");
    p.add(ta);
  }
  String [] names;
  void buildList(JList lis)
  //-----------------------
  {
    names = new File("./save").list(new FilenameFilter()
    {
      public boolean accept(File dir,String name)
      {
        if(name.endsWith(".sch"))
          return true;
        return false;

      }
    });
    Arrays.sort(names);
    for(int i=0;i<names.length;i++)
      names[i] = names[i].substring(0,names[i].length()-4);
    lis.setListData(names);
  }

  private void buildDescriptions()
  {
    try
    {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/cdesc"));
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

  public void go()
  //--------------
  {
    super.go();
    mf.setTitleBar("LOADCHARTITLE");
    buildList(nameList);
    buildDescriptions();
    ta.setText("");
  }

  boolean cancelled=false;;

  public SavedCharacter getLoadedCharacter()
  //----------------------------------------
  {
    try
    {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/"+chosenName+".sch"));
      SavedCharacter sc = (SavedCharacter)ois.readObject();
      return sc;
    }
    catch (Exception e)
    {
      System.out.println("bad char load "+e);
     }
    return null;
  }
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
