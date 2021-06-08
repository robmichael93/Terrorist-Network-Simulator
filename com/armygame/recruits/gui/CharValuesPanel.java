//
//  CharValuesPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;
import com.armygame.recruits.globals.ResourceReader;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public class CharValuesPanel extends RPanel implements ActionListener
{
  static final int LOYALTY=0,DUTY=1,RESPECT=2,SELFLESS=3,HONOR=4,INTEGRITY=5,COURAGE=6;

  MainFrame mf;
  JLabel loyaltyBar,dutyBar,respectBar,selflessBar,honorBar,integrityBar,courageBar;
  JLabel loyaltyGrey,dutyGrey,respectGrey,selflessGrey,honorGrey,integrityGrey,courageGrey;
  JLabel bars [] = {loyaltyBar,dutyBar,respectBar,selflessBar,honorBar,integrityBar,courageBar}; // these are null to start, but
  JLabel greyBars[] = {loyaltyGrey,dutyGrey,respectGrey,selflessGrey,honorGrey,integrityGrey,courageGrey};
  Dimension barSize;
  JLabel valuePointsLab;
  int clickIncrement = 2;
  int sliderIncrement = 1;
  int valuePoints= 0;
  int vValue[] = {0,20,40,50,60,80,100};

  int yincr = 57;
  int loyaltyBaseY = 63;
  int dutyBaseY = loyaltyBaseY+yincr;
  int respectBaseY = dutyBaseY+yincr;
  int serviceBaseY = respectBaseY+yincr;
  int honorBaseY = serviceBaseY+yincr;
  int integrityBaseY = honorBaseY+yincr;
  int courageBaseY = integrityBaseY+yincr;

  JEditorPane/*JTextArea*/ description;
  String htmlPre = "<center><font face='Arial' color='white' size='+2'>";
  String htmlMid = "<br><font size='+1'><br>";
  String defaultDescriptText = "";//(Move your mouse over each of the seven values on the left to see its explanation.)";
  String loyaltyText =   htmlPre+"<b>Loyalty</b>"+htmlMid; //+"Bear true faith and allegiance to the U.S. Constitution, the Army, your unit, and other soldiers.";
  String dutyText =      htmlPre+"<b>Duty</b>"+htmlMid; //+"Fulfill your obligations.  Each task is completed fully.";
  String respectText =   htmlPre+"<b>Respect</b>"+htmlMid; //+"Treat people as they should be treated.  Part of respect is self-respect, which means putting out your best effort.";
  String selflessText =  htmlPre+"<b>Selfless service</b>"+htmlMid; //+"Put the welfare of the Nation, the Army, and your subordinates before your own.  Do something extra to add to the effort.";
  String honorText =     htmlPre+"<b>Honor</b>"+htmlMid; //+"Live up to Army values.  Express Army values through your actions.";
  String integrityText = htmlPre+"<b>Integrity</b>"+htmlMid; //+"Do what's right, legally and morally.";
  String courageText =   htmlPre+"<b>Personal courage</b>"+htmlMid; //+"Face fear, danger, or adversity (physical or moral).";

  CharValuesPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Ggui.darkBackground);

    loadDescriptionsFromFile();

    valuePointsLab = new JLabel("0000");
    valuePointsLab.setFont(new Font("Arial",Font.PLAIN,14));
    valuePointsLab.setForeground(Color.white);
    valuePointsLab.setOpaque(true);
    valuePointsLab.setBackground(Ggui.medDarkBackground);
    valuePointsLab.setHorizontalAlignment(SwingConstants.CENTER);
    //valuePointsLab.setSize(valuePointsLab.getPreferredSize());
    Dimension d = valuePointsLab.getPreferredSize();
    d.width  += 5+5;
    d.height += 5+5;
    valuePointsLab.setSize(d);
    valuePointsLab.setBorder(new CompoundBorder(new ShadowBorder(4),BorderFactory.createLineBorder(Color.black,1)));
    valuePointsLab.setLocation(31,20);
    valuePointsLab.setText("0");
    add(valuePointsLab);

    JLabel pointsText = new JLabel("Available Points");
    pointsText.setFont(new Font("Arial",Font.PLAIN,14));
    pointsText.setForeground(Color.white);
    pointsText.setSize(pointsText.getPreferredSize());
    pointsText.setLocation(73,27);
    add(pointsText);

    JButton cancelButt = ButtonFactory.make(ButtonFactory.CHARVALUESCANCEL,mf);
    add(cancelButt);
    JButton okButt = ButtonFactory.make(ButtonFactory.CHARVALUESOK,mf);
    add(okButt);
    description = new /*JTextArea*/JEditorPane("text/html",defaultDescriptText);

    description.setFont(new Font("Arial",Font.PLAIN,20));
    description.setForeground(Color.white);
    description.setBounds(409,94,205,295);
    description.setEditable(false);
   // description.setLineWrap(true);
   // description.setWrapStyleWord(true);
    description.setOpaque(false);
    // debug description.setBackground(Color.red);
    add(description);
    
    // put empty labels over the buttons on the left to do the descriptions
    JLabel loyaltyLab = makeValueLabel("Loyalty");
    loyaltyLab.setBounds(10,loyaltyBaseY,150,30);
    add(loyaltyLab);
    loyaltyLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(loyaltyText);}});
    JLabel dutyLab = makeValueLabel("Duty");
    dutyLab.setBounds(10,dutyBaseY/*133*/,150,30);
    add(dutyLab);
    dutyLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(dutyText);}});
    JLabel respectLab = makeValueLabel("Respect");
    respectLab.setBounds(10,respectBaseY/*183*/,150,30);
    add(respectLab);
    respectLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(respectText);}});
    JLabel selflessLab = makeValueLabelSmFnt("Selfless Service");        // don't change JH
    selflessLab.setBounds(10,serviceBaseY/*231*/,150,30);
    add(selflessLab);
    selflessLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(selflessText);}});
    JLabel honorLab = makeValueLabel("Honor");
    honorLab.setBounds(10,honorBaseY/*283*/,150,30);
    add(honorLab);
    honorLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(honorText);}});
    JLabel integrityLab = makeValueLabel("Integrity");
    integrityLab.setBounds(10,integrityBaseY/*331*/,150,30);
    add(integrityLab);
    integrityLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(integrityText);}});
    JLabel courageLab = makeValueLabelSmFnt("Personal Courage");          // don't change JH
    courageLab.setBounds(10,courageBaseY/*379*/,150,30);
    add(courageLab);
    courageLab.addMouseListener( new myMouseAdapter(){public void mouseEntered(MouseEvent e){description.setText(courageText);}});
    
    // arrow buttons
    JButton loyaltyLeft  = ButtonFactory.make(ButtonFactory.LOYALTYARROWLEFT,mf);
    loyaltyLeft.addActionListener(this);
    loyaltyLeft.setActionCommand("0L");
    loyaltyLeft.addMouseListener(new SliderMouseAdapter(LOYALTY,-1));
    Point ad = loyaltyLeft.getLocation();ad.y=loyaltyBaseY+6;loyaltyLeft.setLocation(ad);
    add(loyaltyLeft);
    JButton loyaltyRight = ButtonFactory.make(ButtonFactory.LOYALTYARROWRIGHT,mf);
    loyaltyRight.addActionListener(this);
    loyaltyRight.setActionCommand("0R");
    loyaltyRight.addMouseListener(new SliderMouseAdapter(LOYALTY,1));
    ad = loyaltyRight.getLocation();ad.y=loyaltyBaseY+6;loyaltyRight.setLocation(ad);
    add(loyaltyRight);

    JButton dutyLeft  = ButtonFactory.make(ButtonFactory.DUTYARROWLEFT,mf);
    dutyLeft.addActionListener(this);
    dutyLeft.setActionCommand("1L");
    dutyLeft.addMouseListener(new SliderMouseAdapter(DUTY,-1));
    ad = dutyLeft.getLocation();ad.y=dutyBaseY+6;dutyLeft.setLocation(ad);
    add(dutyLeft);
    JButton dutyRight = ButtonFactory.make(ButtonFactory.DUTYARROWRIGHT,mf);
    dutyRight.addActionListener(this);
    dutyRight.setActionCommand("1R");
    dutyRight.addMouseListener(new SliderMouseAdapter(DUTY,1));
    ad = dutyRight.getLocation();ad.y=dutyBaseY+6;dutyRight.setLocation(ad);
    add(dutyRight);

    JButton respectLeft  = ButtonFactory.make(ButtonFactory.RESPECTARROWLEFT,mf);
    respectLeft.addActionListener(this);
    respectLeft.setActionCommand("2L");
    respectLeft.addMouseListener(new SliderMouseAdapter(RESPECT,-1));
    ad = respectLeft.getLocation();ad.y=respectBaseY+6;respectLeft.setLocation(ad);
    add(respectLeft);
    JButton respectRight = ButtonFactory.make(ButtonFactory.RESPECTARROWRIGHT,mf);
    respectRight.addActionListener(this);
    respectRight.setActionCommand("2R");
    respectRight.addMouseListener(new SliderMouseAdapter(RESPECT,1));
    ad = respectRight.getLocation();ad.y=respectBaseY+6;respectRight.setLocation(ad);
    add(respectRight);

    JButton selflessLeft  = ButtonFactory.make(ButtonFactory.SELFLESSARROWLEFT,mf);
    selflessLeft.addActionListener(this);
    selflessLeft.setActionCommand("3L");
    selflessLeft.addMouseListener(new SliderMouseAdapter(SELFLESS,-1));
    ad = selflessLeft.getLocation();ad.y=serviceBaseY+6;selflessLeft.setLocation(ad);
    add(selflessLeft);
    JButton selflessRight = ButtonFactory.make(ButtonFactory.SELFLESSARROWRIGHT,mf);
    selflessRight.addActionListener(this);
    selflessRight.setActionCommand("3R");
    selflessRight.addMouseListener(new SliderMouseAdapter(SELFLESS,1));
    ad = selflessRight.getLocation();ad.y=serviceBaseY+6;selflessRight.setLocation(ad);
    add(selflessRight);

    JButton honorLeft  = ButtonFactory.make(ButtonFactory.HONORARROWLEFT,mf);
    honorLeft.addActionListener(this);
    honorLeft.setActionCommand("4L");
    honorLeft.addMouseListener(new SliderMouseAdapter(HONOR,-1));
    ad = honorLeft.getLocation();ad.y=honorBaseY+6;honorLeft.setLocation(ad);
    add(honorLeft);
    JButton honorRight = ButtonFactory.make(ButtonFactory.HONORARROWRIGHT,mf);
    honorRight.addActionListener(this);
    honorRight.setActionCommand("4R");
    honorRight.addMouseListener(new SliderMouseAdapter(HONOR,1));
    ad = honorRight.getLocation();ad.y=honorBaseY+6;honorRight.setLocation(ad);
    add(honorRight);

    JButton integrityLeft  = ButtonFactory.make(ButtonFactory.INTEGRITYARROWLEFT,mf);
    integrityLeft.addActionListener(this);
    integrityLeft.setActionCommand("5L");
    integrityLeft.addMouseListener(new SliderMouseAdapter(INTEGRITY,-1));
    ad = integrityLeft.getLocation();ad.y=integrityBaseY+6;integrityLeft.setLocation(ad);
    add(integrityLeft);
    JButton integrityRight = ButtonFactory.make(ButtonFactory.INTEGRITYARROWRIGHT,mf);
    integrityRight.addActionListener(this);
    integrityRight.setActionCommand("5R");
    integrityRight.addMouseListener(new SliderMouseAdapter(INTEGRITY,1));
    ad = integrityRight.getLocation();ad.y=integrityBaseY+6;integrityRight.setLocation(ad);
    add(integrityRight);

    JButton courageLeft  = ButtonFactory.make(ButtonFactory.COURAGEARROWLEFT,mf);
    courageLeft.addActionListener(this);
    courageLeft.setActionCommand("6L");
    courageLeft.addMouseListener(new SliderMouseAdapter(COURAGE,-1));
    ad = courageLeft.getLocation();ad.y=courageBaseY+6;courageLeft.setLocation(ad);
    add(courageLeft);
    JButton courageRight = ButtonFactory.make(ButtonFactory.COURAGEARROWRIGHT,mf);
    courageRight.addActionListener(this);
    courageRight.setActionCommand("6R");
    courageRight.addMouseListener(new SliderMouseAdapter(COURAGE,1));
    ad = courageRight.getLocation();ad.y=courageBaseY+6;courageRight.setLocation(ad);
    add(courageRight);

    // left background piece used to "hide" the left side of the bars    
    JLabel leftLabel = new JLabel(Ggui.imgIconGet("VALUESLEFTPIECE"));
    leftLabel.setSize(leftLabel.getPreferredSize());
    leftLabel.setLocation(0,0);
    add(leftLabel);
    
    ImageIcon goldBar = Ggui.imgIconGet("GOLDBAR");
    ImageIcon greyBar = Ggui.imgIconGet("GREYBAR");

    loyaltyBar = new JLabel(goldBar);
    loyaltyBar.setSize(loyaltyBar.getPreferredSize());
    loyaltyBar.setLocation( 214,loyaltyBaseY+17); //16);
    add(loyaltyBar);
    bars[0]=loyaltyBar;
      loyaltyGrey = new JLabel(greyBar);
      loyaltyGrey.setSize(loyaltyGrey.getPreferredSize());
      loyaltyGrey.setLocation(214,loyaltyBaseY+12); //11);
      add(loyaltyGrey);
      greyBars[0]=loyaltyGrey;
    dutyBar = new JLabel(goldBar);
    dutyBar.setSize(dutyBar.getPreferredSize());
    dutyBar.setLocation( 214,dutyBaseY+17/*150*/);
    add(dutyBar);
    bars[1]=dutyBar;
      dutyGrey = new JLabel(greyBar);
      dutyGrey.setSize(dutyGrey.getPreferredSize());
      dutyGrey.setLocation(214,dutyBaseY+12/*145*/);
      add(dutyGrey);
      greyBars[1]=dutyGrey;
    respectBar = new JLabel(goldBar);
    respectBar.setSize(respectBar.getPreferredSize());
    respectBar.setLocation( 214,respectBaseY+17/*198*/);
    add(respectBar);
    bars[2]=respectBar;
      respectGrey = new JLabel(greyBar);
      respectGrey.setSize(respectGrey.getPreferredSize());
      respectGrey.setLocation(214,respectBaseY+12/*193*/);
      add(respectGrey);
      greyBars[2]=respectGrey;
    selflessBar = new JLabel(goldBar);
    selflessBar.setSize(selflessBar.getPreferredSize());
    selflessBar.setLocation( 214,serviceBaseY+17/*245*/);
    add(selflessBar);
    bars[3]=selflessBar;
      selflessGrey = new JLabel(greyBar);
      selflessGrey.setSize(selflessGrey.getPreferredSize());
      selflessGrey.setLocation(214,serviceBaseY+12/*240*/);
      add(selflessGrey);
      greyBars[3]=selflessGrey;
    honorBar = new JLabel(goldBar);
    honorBar.setSize(honorBar.getPreferredSize());
    honorBar.setLocation( 214,honorBaseY+17/*298*/);
    add(honorBar);
    bars[4]=honorBar;
      honorGrey = new JLabel(greyBar);
      honorGrey.setSize(honorGrey.getPreferredSize());
      honorGrey.setLocation(214,honorBaseY+12/*293*/);
      add(honorGrey);
      greyBars[4]=honorGrey;
    integrityBar = new JLabel(goldBar);
    integrityBar.setSize(integrityBar.getPreferredSize());
    integrityBar.setLocation( 214,integrityBaseY+17/*346*/);
    add(integrityBar);
    bars[5]=integrityBar;
      integrityGrey = new JLabel(greyBar);
      integrityGrey.setSize(integrityGrey.getPreferredSize());
      integrityGrey.setLocation(214,integrityBaseY+12/*341*/);
      add(integrityGrey);
      greyBars[5]=integrityGrey;
    courageBar = new JLabel(goldBar);
    courageBar.setSize(courageBar.getPreferredSize());
    courageBar.setLocation( 214,courageBaseY+17); //15/*394*/);
    add(courageBar);
    bars[6]=courageBar;
      courageGrey = new JLabel(greyBar);
      courageGrey.setSize(courageGrey.getPreferredSize());
      courageGrey.setLocation(214,courageBaseY+12); //10/*389*/);
      add(courageGrey);
      greyBars[6]=courageGrey;
    barSize = courageBar.getSize();		// all the same

    JLabel dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(loyaltyLeft.getX()+loyaltyLeft.getWidth()-2,loyaltyLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(dutyLeft.getX()+dutyLeft.getWidth()-2,dutyLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(respectLeft.getX()+respectLeft.getWidth()-2,respectLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(selflessLeft.getX()+selflessLeft.getWidth()-2,selflessLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(honorLeft.getX()+honorLeft.getWidth()-2,honorLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(integrityLeft.getX()+integrityLeft.getWidth()-2,integrityLeft.getY()-5);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("VALUESDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(courageLeft.getX()+courageLeft.getWidth()-2,courageLeft.getY()-5);
    add(dial);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("VALUESBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    toLocal(); //fromTo(mf.globals.values,vValue);  // init locals
    setVisible(false);


    ActionListener autoSlider = new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        numTimerFirings++;
        if(numTimerFirings == 1)
          mf.startLaser(); //mf.doLaser();
        bump(sliderTimerWh,sliderIncrement*sliderTimerDir);
      }
    };

    sliderTimer = new Timer(40,autoSlider);
    sliderTimer.setInitialDelay(600);
  }

  private JLabel makeValueLabel(String name)
  {
    JLabel lab = new JLabel(name);
    lab.setFont(new Font("Arial",Font.BOLD,20));
    lab.setForeground(Color.white);
    lab.setHorizontalAlignment(SwingConstants.RIGHT);
    return lab;
  }
  private JLabel makeValueLabelSmFnt(String name)
  {
    JLabel lab = makeValueLabel(name);
    lab.setFont(new Font("Arial",Font.BOLD,16));
    return lab;
  }

  Timer sliderTimer;
  int numTimerFirings = 0;
  int sliderTimerWh;
  int sliderTimerDir;

  public void actionPerformed(ActionEvent e)
  {
    sliderTimer.stop();
    mf.stopLaser();
    if(numTimerFirings > 0)
      return;

    int vIncr = 0;
    String cmd = e.getActionCommand();
    int i = Integer.parseInt(cmd.substring(0,1));    
    if(cmd.charAt(1) == 'L')
      vIncr = clickIncrement * -1;//incr = -5;
    else
      vIncr = clickIncrement; //incr = 5;

    bump(i,vIncr);
  }
  private void bump(int i, int vIncr)
  {
    JLabel myBar;
    int incr=0;
    Dimension d;
    myBar = bars[i];
    d = myBar.getSize();
    if((vValue[i]+vIncr) < 0 || (vValue[i]+vIncr) >100)
    {
      mf.stopLaser();
      return;
    }
    if(valuePoints - vIncr < 0)
    {
      mf.stopLaser();
      return;
    }
    valuePoints -= vIncr;
    valuePointsLab.setText(""+valuePoints);
    vValue[i] += vIncr;
    
    Point p = myBar.getLocation();
    p.x = valueToPosition(vValue[i]);
  
    myBar.setLocation(p);
  }
  public void cancelling()
  {
    toLocal(); //fromTo(mf.globals.values,vValue);
  }
  private int valueToPosition(int val)
  {
    return (barSize.width*val)/100 - barSize.width + 214;
  }
  private int positionToValue(int pos)
  {
    return (pos + barSize.width - 214) * 100 / barSize.width;
  }
  private void xfromTo(int[]f,int[]t)
  {
    for(int i=0;i<vValue.length;i++)
      t[i]=f[i];
  }
  private void toLocal()
  {
    vValue[0]=(int)mf.globals.charinsides.loyalty;
    vValue[1]=(int)mf.globals.charinsides.duty;
    vValue[2]=(int)mf.globals.charinsides.respect;
    vValue[3]=(int)mf.globals.charinsides.selfless;
    vValue[4]=(int)mf.globals.charinsides.honor;
    vValue[5]=(int)mf.globals.charinsides.integrity;
    vValue[6]=(int)mf.globals.charinsides.courage;
    valuePoints=(int)mf.globals.charinsides.unallocated;    
  }
  private void fromLocal()
  {
    mf.globals.charinsides.loyalty=vValue[0];
    mf.globals.charinsides.duty=vValue[1];
    mf.globals.charinsides.respect=vValue[2];
    mf.globals.charinsides.selfless=vValue[3];
    mf.globals.charinsides.honor=vValue[4];
    mf.globals.charinsides.integrity=vValue[5];
    mf.globals.charinsides.courage=vValue[6];
    mf.globals.charinsides.unallocated=valuePoints;
  }
    
  boolean cancelled = false;
  boolean initted = false;
  public void go()
  {
    initted = true;
    cancelled = false;
    toLocal(); //fromTo(mf.globals.values,vValue);
    for(int i=0;i<vValue.length;i++)
    {
      Point p = bars[i].getLocation();
      Point pg = greyBars[i].getLocation();
      p.x = valueToPosition(vValue[i]);
      pg.x = p.x;
      bars[i].setLocation(p);
      greyBars[i].setLocation(pg);
    }
    valuePointsLab.setText(""+valuePoints);
    super.go();
    mf.setTitleBar("VALUESTITLE");
  }
  public void done()
  {
    if(initted && !cancelled)
    {
      fromLocal(); //fromTo(vValue,mf.globals.values);
    }
    super.done();
  }
  public void cancel()
  {
    cancelled = true;  
  }
  
  class myMouseAdapter extends MouseAdapter
  {
    public void mouseExited(MouseEvent e)
    {
      description.setText(defaultDescriptText);
    }
  }

  class SliderMouseAdapter extends MouseAdapter
  {
    int wh,direction;
    SliderMouseAdapter(int wh, int direction)
    {
      this.wh = wh;
      this.direction = direction;
    }
    public void mousePressed(MouseEvent e)
    {
      numTimerFirings = 0;
      sliderTimerWh = wh;
      sliderTimerDir = direction;

      sliderTimer.restart();
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
      if(el.getName().equalsIgnoreCase("Value"))
      {
        if(el.getAttributeValue("title").equalsIgnoreCase("Loyalty"))
          loyaltyText = loyaltyText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Duty"))
          dutyText = dutyText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Respect"))
          respectText = respectText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Selfless service"))
         selflessText = selflessText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Honor"))
          honorText = honorText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Integrity"))
          integrityText = integrityText + el.getTextTrim();
        else if(el.getAttributeValue("title").equalsIgnoreCase("Personal courage"))
          courageText = courageText + el.getTextTrim();
      }
    }
  }

}
