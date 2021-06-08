//
//  CharResourcesPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import com.armygame.recruits.globals.ResourceReader;
/*
CHARRESOURCESUPARROW		resourcesUpArrow.png
CHARRESOURCESDOWNARROW		resourcesDownArrow.png
CHARRESOURCESSIDEARROW		resourcesSideArrow.png

 ENERGYARROW_x			550
 ENERGYARROW_y			100
 STRENGTH_x			550
 STRENGTH_y			150
 KNOWLEDGE_x			550
 KNOWLEDGE_y			200
 SKILL_x				550
 SKILL_y				250
 FINANCIAL_x			550
 FINANCIAL_y			300
 POPULARITY_x			550
 POPULARITY_y			350

*/

public class CharResourcesPanel extends RPanel
{
  MainFrame mf;
  JLabel energyBar,strengthBar,knowledgeBar,skillBar,financialBar,popularityBar;
  JLabel bars[] = new JLabel[6];
  JLabel leftBackLabel;
  Dimension barSize;
  ImageIcon goldBar;
  ImageIcon upArrow;
  ImageIcon downArrow;
  ImageIcon sideArrow;
  int vValue[] = {0,20,40,50,60,80};
  JLabel energyArrow,strengthArrow,knowledgeArrow,skillArrow,financialArrow,popularityArrow;
  JButton leftButts[] = new JButton[6];
  JButton rightButts[] = new JButton[6];
  int clickIncr = 2;

  static final int NAMES_X = 150 -120;
  static final int BARS_X = 289 -120;
  static final int BARS_Y_OFFS = 10;
  static final int FIRSTLINE_Y = 115;
  static final int LINE_YDIFF = 55;
  static final int ARROWS_X = 350;
  static final int ARROWS_YOFFS = 20;
  static final int LEFTBUTT_X = 430;
  static final int RIGHTBUTT_X = 460;
  static final int BUTTS_YOFFS = 12;


  JEditorPane description;
  String htmlPre = "<center><font face='Arial' color='white' size='+2'>";
  String htmlMid = "<br><font size='+1'><br>";
  String defaultDescriptText = "";//(Move your mouse over each of the seven values on the left to see its explanation.)";
  String energyText =    htmlPre+"<b>Energy</b>"+htmlMid;//+"Description of the energy resource and how it is used in this game.";
  String strengthText =  htmlPre+"<b>Strength</b>"+htmlMid;//+"Description of the strength resource and how it is used in this game.";
  String knowledgeText = htmlPre+"<b>Knowledge</b>"+htmlMid;//+"Description of the knowledge resource and how it is used in this game.";
  String skillText =     htmlPre+"<b>Skill</b>"+htmlMid;//+"Description of the energy skill and how it is used in this game.";
  String financialText = htmlPre+"<b>Financial</b>"+htmlMid;//+"Description of the financial resource and how it is used in this game.";
  String popularityText =htmlPre+"<b>Popularity</b>"+htmlMid;//+"Description of the popularity resource and how it is used in this game.";

  Rectangle defaultBounds = new Rectangle(409,94,205,295);
  Rectangle squishedBounds= new Rectangle(500,94,125,400);
  CharResourcesPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    loadDescriptionsFromFile();

    goldBar   = Ggui.imgIconGet("GOLDBAR");
    upArrow   = Ggui.imgIconGet("CHARRESOURCESUPARROW");
    downArrow = Ggui.imgIconGet("CHARRESOURCESDOWNARROW");
    sideArrow = Ggui.imgIconGet("CHARRESOURCESSIDEARROW");
      
    //JButton cancelButt = ButtonFactory.make(ButtonFactory.CHARRESOURCESCANCEL,mf);
    //add(cancelButt);
    
    JButton okButt = ButtonFactory.make(ButtonFactory.CHARRESOURCESOK,mf);
    add(okButt);

    JPanel toplabPanel = new JPanel();
    toplabPanel.setOpaque(false);
    toplabPanel.setLayout(new BoxLayout(toplabPanel,BoxLayout.X_AXIS));
    toplabPanel.add(new MyLabel("A "));
    toplabPanel.add(new MyLabel("RED ",Color.red));
    toplabPanel.add(new MyLabel("bar means a resource is declining, "));
    toplabPanel.add(new MyLabel("GREEN ",Color.green));
    toplabPanel.add(new MyLabel("indicates advancing, and "));
    toplabPanel.add(new MyLabel("YELLOW ",Color.yellow));
    toplabPanel.add(new MyLabel("means stable."));
    toplabPanel.setSize(toplabPanel.getPreferredSize());
    toplabPanel.setLocation(25,10);
    add(toplabPanel);

    JLabel trendLab = new JLabel("Trend");
    trendLab.setFont(new Font("Arial",Font.PLAIN,14));
    trendLab.setSize(trendLab.getPreferredSize());
    trendLab.setForeground(Color.gray);
    trendLab.setLocation(ARROWS_X,85);
    add(trendLab);

    energyArrow = new JLabel(sideArrow);
    energyArrow.setSize(energyArrow.getPreferredSize());
    energyArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 0*LINE_YDIFF);
    add(energyArrow);
    strengthArrow = new JLabel(sideArrow);
    strengthArrow.setSize(strengthArrow.getPreferredSize());
    strengthArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 1*LINE_YDIFF);
    add(strengthArrow);
    knowledgeArrow = new JLabel(sideArrow);
    knowledgeArrow.setSize(knowledgeArrow.getPreferredSize());
    knowledgeArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 2*LINE_YDIFF);
    add(knowledgeArrow);
    skillArrow = new JLabel(sideArrow);
    skillArrow.setSize(skillArrow.getPreferredSize());
    skillArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 3*LINE_YDIFF);
    add(skillArrow);
    financialArrow = new JLabel(sideArrow);
    financialArrow.setSize(financialArrow.getPreferredSize());
    financialArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 4*LINE_YDIFF);
    add(financialArrow);
    popularityArrow = new JLabel(sideArrow);
    popularityArrow.setSize(popularityArrow.getPreferredSize());
    popularityArrow.setLocation(ARROWS_X,FIRSTLINE_Y - ARROWS_YOFFS + 5*LINE_YDIFF);
    add(popularityArrow);

    JLabel nameLab = makeResourceLabel("Energy");
      nameLab.setLocation(NAMES_X,energyArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(energyText);}});
    add(nameLab);
    nameLab = makeResourceLabel("Strength");
      nameLab.setLocation(NAMES_X,strengthArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(strengthText);}});
    add(nameLab);
    nameLab = makeResourceLabel("Knowledge");
      nameLab.setLocation(NAMES_X,knowledgeArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(knowledgeText);}});
    add(nameLab);
    nameLab = makeResourceLabel("Skill");
      nameLab.setLocation(NAMES_X,skillArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(skillText);}});
    add(nameLab);
    nameLab = makeResourceLabel("Financial");
      nameLab.setLocation(NAMES_X,financialArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(financialText);}});
    add(nameLab);
    nameLab = makeResourceLabel("Popularity");
      nameLab.setLocation(NAMES_X,popularityArrow.getY());
      nameLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(popularityText);}});
    add(nameLab);

    // left background piece used to "hide" the left side of the bars
    leftBackLabel = new JLabel(Ggui.imgIconGet("RESOURCESLEFTBACK"));
    leftBackLabel.setSize(leftBackLabel.getPreferredSize());
    leftBackLabel.setLocation(0,0);
    add(leftBackLabel);
     
    energyBar = new JLabel(goldBar);
    energyBar.setSize(energyBar.getPreferredSize());
    energyBar.setLocation( BARS_X,FIRSTLINE_Y-BARS_Y_OFFS);
    add(energyBar);
    bars[0]=energyBar;

    strengthBar = new JLabel(goldBar);
    strengthBar.setSize(strengthBar.getPreferredSize());
    strengthBar.setLocation( BARS_X,FIRSTLINE_Y+1*LINE_YDIFF-BARS_Y_OFFS);
    add(strengthBar);
    bars[1]=strengthBar;

    knowledgeBar = new JLabel(goldBar);
    knowledgeBar.setSize(knowledgeBar.getPreferredSize());
    knowledgeBar.setLocation( BARS_X,FIRSTLINE_Y+2*LINE_YDIFF-BARS_Y_OFFS);
    add(knowledgeBar);
    bars[2]=knowledgeBar;

    skillBar = new JLabel(goldBar);
    skillBar.setSize(skillBar.getPreferredSize());
    skillBar.setLocation( BARS_X,FIRSTLINE_Y+3*LINE_YDIFF-BARS_Y_OFFS);
    add(skillBar);
    bars[3]=skillBar;

    financialBar = new JLabel(goldBar);
    financialBar.setSize(financialBar.getPreferredSize());
    financialBar.setLocation( BARS_X,FIRSTLINE_Y+4*LINE_YDIFF-BARS_Y_OFFS);
    add(financialBar);
    bars[4]=financialBar;

    popularityBar = new JLabel(goldBar);
    popularityBar.setSize(popularityBar.getPreferredSize());
    popularityBar.setLocation( BARS_X,FIRSTLINE_Y+5*LINE_YDIFF-BARS_Y_OFFS);
    add(popularityBar);
    bars[5]=popularityBar;
    barSize = popularityBar.getSize();		// all the same

    JLabel dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(energyBar.getX()-5,energyBar.getY()-3);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(strengthBar.getX()-5,strengthBar.getY()-3);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(knowledgeBar.getX()-5,knowledgeBar.getY()-3);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(skillBar.getX()-5,skillBar.getY()-3);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(financialBar.getX()-5,financialBar.getY()-3);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("RESOURCESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(popularityBar.getX()-5,popularityBar.getY()-3);
    add(dial);

    leftButts[0] = ButtonFactory.make(ButtonFactory.RESOURCESENERGYLEFT,mf);
     leftButts[0].setLocation(LEFTBUTT_X,FIRSTLINE_Y+0*LINE_YDIFF-BUTTS_YOFFS);
    leftButts[1] = ButtonFactory.make(ButtonFactory.RESOURCESSTRENGTHLEFT,mf);
     leftButts[1].setLocation(LEFTBUTT_X,FIRSTLINE_Y+1*LINE_YDIFF-BUTTS_YOFFS);
    leftButts[2] = ButtonFactory.make(ButtonFactory.RESOURCESKNOWLEDGELEFT,mf);
     leftButts[2].setLocation(LEFTBUTT_X,FIRSTLINE_Y+2*LINE_YDIFF-BUTTS_YOFFS);
    leftButts[3] = ButtonFactory.make(ButtonFactory.RESOURCESSKILLLEFT,mf);
     leftButts[3].setLocation(LEFTBUTT_X,FIRSTLINE_Y+3*LINE_YDIFF-BUTTS_YOFFS);
    leftButts[4] = ButtonFactory.make(ButtonFactory.RESOURCESFINANCIALLEFT,mf);
     leftButts[4].setLocation(LEFTBUTT_X,FIRSTLINE_Y+4*LINE_YDIFF-BUTTS_YOFFS);
    leftButts[5] = ButtonFactory.make(ButtonFactory.RESOURCESPOPULARITYLEFT,mf);
     leftButts[5].setLocation(LEFTBUTT_X,FIRSTLINE_Y+5*LINE_YDIFF-BUTTS_YOFFS);

    rightButts[0] = ButtonFactory.make(ButtonFactory.RESOURCESENERGYRIGHT,mf);
     rightButts[0].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+0*LINE_YDIFF-BUTTS_YOFFS);
    rightButts[1] = ButtonFactory.make(ButtonFactory.RESOURCESSTRENGTHRIGHT,mf);
     rightButts[1].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+1*LINE_YDIFF-BUTTS_YOFFS);
    rightButts[2] = ButtonFactory.make(ButtonFactory.RESOURCESKNOWLEDGERIGHT,mf);
     rightButts[2].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+2*LINE_YDIFF-BUTTS_YOFFS);
    rightButts[3] = ButtonFactory.make(ButtonFactory.RESOURCESSKILLRIGHT,mf);
     rightButts[3].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+3*LINE_YDIFF-BUTTS_YOFFS);
    rightButts[4] = ButtonFactory.make(ButtonFactory.RESOURCESFINANCIALRIGHT,mf);
     rightButts[4].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+4*LINE_YDIFF-BUTTS_YOFFS);
    rightButts[5] = ButtonFactory.make(ButtonFactory.RESOURCESPOPULARITYRIGHT,mf);
     rightButts[5].setLocation(RIGHTBUTT_X,FIRSTLINE_Y+5*LINE_YDIFF-BUTTS_YOFFS);

    for(int i=0;i<leftButts.length;i++)
    {
      add(leftButts[i]);
      add(rightButts[i]);
    }

    description = new JEditorPane("text/html",defaultDescriptText);

    description.setFont(new Font("Arial",Font.PLAIN,20));
    description.setForeground(Color.white);
    description.setBounds(409,94,205,295);
    description.setEditable(false);
    description.setOpaque(false);
    add(description);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("RESOURCESBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  JLabel makeResourceLabel(String name)
  {
    JLabel lab = new JLabel(name);
    lab.setFont(new Font("Arial",Font.BOLD,20));
    lab.setSize(lab.getPreferredSize());
    lab.setForeground(Color.white);
    lab.setHorizontalAlignment(SwingConstants.LEFT);
    return lab;
  }

  public void go()
  {
    System.out.println("resources.go()");
    toLocal();
    for(int i=0;i<vValue.length;i++)
    {
      Point p = bars[i].getLocation();
      p.x = valueToPosition(vValue[i]);
      bars[i].setLocation(p);
    }
    super.go();
    mf.setTitleBar("RESOURCESTITLE");
  }
  public void done()
  {
    super.done();
    toGlobal();
  }

  public void buttonHit(int idx, boolean left)
  {
    Point p = bars[idx].getLocation();
    int multiplier = 1;
    if(left)
      multiplier = -1;

    vValue[idx] = Math.max(0,Math.min(100,vValue[idx]+(clickIncr*multiplier)));

    p.x = valueToPosition(vValue[idx] + (left?-1:1));
    bars[idx].setLocation(p);
  }
  public void makeEditable(boolean wh)
  {
    for(int i=0;i<leftButts.length;i++)
    {
      leftButts[i].setVisible(wh);
      rightButts[i].setVisible(wh);
    }
    if(wh)    // yes editable
      description.setBounds(squishedBounds);
    else
      description.setBounds(defaultBounds);
  }
  private void toGlobal()
  {
    mf.globals.charinsides.energy = vValue[0];
    mf.globals.charinsides.strength = vValue[1];
    mf.globals.charinsides.knowledge = vValue[2];
    mf.globals.charinsides.skill = vValue[3];
    mf.globals.charinsides.financial = vValue[4];
    mf.globals.charinsides.popularity = vValue[5];
  }

  private void toLocal()
  {
    vValue[0]=(int)mf.globals.charinsides.energy;
    vValue[1]=(int)mf.globals.charinsides.strength;
    vValue[2]=(int)mf.globals.charinsides.knowledge;
    vValue[3]=(int)mf.globals.charinsides.skill;
    vValue[4]=(int)mf.globals.charinsides.financial;
    vValue[5]=(int)mf.globals.charinsides.popularity;
    
    setArrow(energyArrow, mf.globals.charinsides.energylast,
                          mf.globals.charinsides.energy);
    setArrow(strengthArrow, mf.globals.charinsides.strengthlast,
                          mf.globals.charinsides.strength);
    setArrow(knowledgeArrow, mf.globals.charinsides.knowledgelast,
                          mf.globals.charinsides.knowledge);
    setArrow(skillArrow, mf.globals.charinsides.skilllast,
                          mf.globals.charinsides.skill);
    setArrow(financialArrow, mf.globals.charinsides.financiallast,
                          mf.globals.charinsides.financial);
    setArrow(popularityArrow, mf.globals.charinsides.popularitylast,
                          mf.globals.charinsides.popularity);
  }
  
  private void setArrow(JLabel arr, double last, double now)
  {
    if(last < now)
      arr.setIcon(upArrow);
    else if (last > now)
      arr.setIcon(downArrow);
    else
      arr.setIcon(sideArrow);
  }
  
  private int valueToPosition(int val)
  {
    return (barSize.width*val)/100 - barSize.width + leftBackLabel.getWidth();
  }
  private int positionToValue(int pos)
  {
    return (pos + barSize.width - 214) * 100 / leftBackLabel.getWidth();
  }
  class MyLabel extends JLabel
  {
    MyLabel(String t)
    {
      this(t,Ggui.buttonForeground());
    }
    MyLabel(String t, Color c)
    {
      super(t);
      setFont(Ggui.buttonFont());
      setForeground(c);
      setSize(getPreferredSize());
    }
  }
  class myMouseAdapter extends MouseAdapter
  {
    public void mouseExited(MouseEvent e)
    {
      description.setText(defaultDescriptText);
    }
  }
  private void loadDescriptionsFromFile()
  {
    SAXBuilder bldr=new SAXBuilder();
    Document doc=null;
    try
    {
      doc=bldr.build(ResourceReader.getInputReaderData("ValuesResourcesDescriptions.xml"));
    }
    catch(Exception e)
    {
      System.out.println("Missing ValuesResourcesDescriptions.xml in classpath");
    }
    Element root=doc.getRootElement();
    //root = root.getChild("mosCatalog");
    java.util.List groupList=root.getChildren();
    //Iterator it=groupList.iterator();
    int grpIndex=-1;
    for(Iterator it=groupList.iterator();it.hasNext();)
    //while(it.hasNext())
    {
      grpIndex++;

      Element el=(Element)it.next();
      if(el.getName().equalsIgnoreCase("Resource"))
      {
        if(el.getAttributeValue("title").equalsIgnoreCase("Energy"))
          energyText = energyText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Strength"))
          strengthText = strengthText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Knowledge"))
          knowledgeText = knowledgeText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Skills"))
          skillText = skillText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Finance"))
          financialText = financialText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Popularity"))
          popularityText = popularityText + el.getTextTrim();
      }
    }
  }

  /*
     // titlesv.add(el.getAttributeValue("title"));
     // mosv.add(el.getAttributeValue("number"));
     // ticketv.add(el.getAttributeValue("ticket"));

      java.util.List mosList=el.getChildren();
      //Iterator mit=mosList.iterator();
      int mosIndex=-1;
      ArrayList al=new ArrayList();
      for(Iterator mit=mosList.iterator();mit.hasNext();)
      //while(mit.hasNext())
      {
        mosIndex++;
        Element mos=(Element)mit.next();
        String title=mos.getAttributeValue("title");
        String number=mos.getAttributeValue("number");
        String restr=mos.getAttributeValue("restriction");
        String desc=mos.getTextTrim();
        al.add(new mosData(title,number,desc,restr));
        //get escaped apostrophe...
      }
      contentv.add(al);
    }
 */

}
