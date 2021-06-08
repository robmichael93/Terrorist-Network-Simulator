//
//  CharGoalsPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import com.armygame.recruits.storyelements.sceneelements.Goal;
import com.armygame.recruits.gui.laf.ShadowBorder;

public class CharGoals5Panel extends RPanel implements ActionListener
{
  MainFrame mf;
  JLabel[] la = new JLabel[5];
  Goal[] goals = new Goal[5];
  double[] emphasis = new double[5];
  boolean[] gChanged = new boolean[5];

  int chosenIdx = -1;
  JLabel[] emphBars = new JLabel[5];
  int emphVals[] = new int[5];
  
  JLabel[] progBars = new JLabel[5];
  JButton[] leftButts = new JButton[5];
  JButton[] rightButts= new JButton[5];
  JButton[] removeButts = new JButton[5];
  JButton[] addButts = new JButton[5];

  int progBarRegPoint;
  int emphBarRegPoint;

  Dimension barSize;
  ImageIcon goldBar;
  int clickIncrement = 2;
  int sliderIncrement = 1;
  boolean cancelled = false;

  int unallocEmphPoints = 0;
  JLabel unallocLabel;
  JButton cancelButt;
  JButton viewallButt;
  JButton okButt;

  CharGoals5Panel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Ggui.darkBackground);

    goldBar = Ggui.imgIconGet("GOLDBAR");

    cancelButt = ButtonFactory.make(ButtonFactory.GOALSCANCEL,mf);
    add(cancelButt);
    viewallButt = ButtonFactory.make(ButtonFactory.GOALSVIEWALL,mf);
    add(viewallButt);
    okButt = ButtonFactory.make(ButtonFactory.GOALSOK,mf);
    add(okButt);

    Font labFont = Ggui.medButtonFont();
    JLabel g1 = new JLabel("Sports Utility Vehicle");
    la[0]=g1;
    la[0].setFont(labFont);
    JLabel g2 = new JLabel("Foreign Language Fluency");
    la[1]=g2;
    la[1].setFont(labFont);
    JLabel g3 = new JLabel("Qualify: Basic PT 2-Mile Run");
    la[2]=g3;
    la[2].setFont(labFont);
    JLabel g4 = new JLabel("click to add goal");
    la[3]=g4;
    la[3].setFont(labFont);
    JLabel g5 = new JLabel("click to add goal");
    la[4]=g5;
    la[4].setFont(labFont);

    g1.setBounds(15,113,197,25);
    JButton a1 = ButtonFactory.make(ButtonFactory.GOAL_ADD,mf);
    addButts[0]=a1;
    int addButtX = 15+125;
    a1.setLocation(addButtX,113+25);
    JButton b1 = ButtonFactory.make(ButtonFactory.GOAL_REMOVE,mf);
    removeButts[0] = b1;
    int revButtX = a1.getX()+a1.getWidth();
    b1.setLocation(revButtX,113+25);

    g2.setBounds(15,165,197,25);
    JButton a2 = ButtonFactory.make(ButtonFactory.GOAL_ADD,mf);
    addButts[1] = a2;
    a2.setLocation(addButtX,165+25);
    JButton b2 = ButtonFactory.make(ButtonFactory.GOAL_REMOVE,mf);
    removeButts[1] = b2;
    b2.setLocation(revButtX,165+25);

    g3.setBounds(15,220,197,25);
    JButton a3 = ButtonFactory.make(ButtonFactory.GOAL_ADD,mf);
    addButts[2] = a3;
    a3.setLocation(addButtX,220+25);
    JButton b3 = ButtonFactory.make(ButtonFactory.GOAL_REMOVE,mf);
    removeButts[2] = b3;
    b3.setLocation(revButtX,220+25);

    g4.setBounds(15,280,197,25);
    JButton a4 = ButtonFactory.make(ButtonFactory.GOAL_ADD,mf);
    addButts[3] = a4;
    a4.setLocation(addButtX,280+25);
    JButton b4 = ButtonFactory.make(ButtonFactory.GOAL_REMOVE,mf);
    removeButts[3] = b4;
    b4.setLocation(revButtX,280+25);

    g5.setBounds(15,337,197,25);
    JButton a5 = ButtonFactory.make(ButtonFactory.GOAL_ADD,mf);
    addButts[4] = a5;
    a5.setLocation(addButtX,337+25);
    JButton b5 = ButtonFactory.make(ButtonFactory.GOAL_REMOVE,mf);
    removeButts[4] = b5;
    b5.setLocation(revButtX,337+25);
    /*
    unallocLabel = new JLabel("000");
    unallocLabel.setSize(unallocLabel.getPreferredSize());
    Font f = unallocLabel.getFont();
    unallocLabel.setFont(new Font(f.getName(),Font.BOLD,12));
    unallocLabel.setLocation(225,73);
    unallocLabel.setForeground(new Color(18,18,18));
    add(unallocLabel);
   */
    unallocLabel = new JLabel("0000");
    unallocLabel.setFont(new Font("Arial",Font.PLAIN,14));
    unallocLabel.setForeground(Color.white);
    unallocLabel.setOpaque(true);
    unallocLabel.setBackground(Ggui.medDarkBackground);
    unallocLabel.setHorizontalAlignment(SwingConstants.CENTER);
    //valuePointsLab.setSize(valuePointsLab.getPreferredSize());
    Dimension d = unallocLabel.getPreferredSize();
    d.width  += 5+5;
    d.height += 5+5;
    unallocLabel.setSize(d);
    unallocLabel.setBorder(new CompoundBorder(new ShadowBorder(4),BorderFactory.createLineBorder(Color.black,1)));
    unallocLabel.setLocation(31,20);
    unallocLabel.setText("0");
    add(unallocLabel);

    JLabel pointsText = new JLabel("Available Points");
    pointsText.setFont(new Font("Arial",Font.PLAIN,14));
    pointsText.setForeground(Color.white);
    pointsText.setSize(pointsText.getPreferredSize());
    pointsText.setLocation(73,27);
    add(pointsText);
/*
    MouseAdapter ma = new MouseAdapter()
    {
      public void mouseReleased(MouseEvent ev)
      {
        JLabel lab = (JLabel)ev.getComponent();
        mf.handlers.eventIn(ButtonFactory.GOALSLIST);
        
        for(int i=0;i<la.length;i++)
        {
          if(lab == la[i])
            chosenIdx = i;
        }
      }
    };
    
    g1.addMouseListener(ma);
    g2.addMouseListener(ma);
    g3.addMouseListener(ma);
    g4.addMouseListener(ma);
    g5.addMouseListener(ma);
 */
    ActionListener bal = new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        removeGoal(Integer.parseInt(ev.getActionCommand()));

      }
    };
    b1.addActionListener(bal);b1.setActionCommand("0");
    b2.addActionListener(bal);b2.setActionCommand("1");
    b3.addActionListener(bal);b3.setActionCommand("2");
    b4.addActionListener(bal);b4.setActionCommand("3");
    b5.addActionListener(bal);b5.setActionCommand("4");

    ActionListener aal = new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        chosenIdx = Integer.parseInt(ev.getActionCommand());
        mf.handlers.eventIn(ButtonFactory.GOALSLIST);
      }
    };
    a1.addActionListener(aal);a1.setActionCommand("0");
    a2.addActionListener(aal);a2.setActionCommand("1");
    a3.addActionListener(aal);a3.setActionCommand("2");
    a4.addActionListener(aal);a4.setActionCommand("3");
    a5.addActionListener(aal);a5.setActionCommand("4");

    add(g1); add(a1); add(b1);
    add(g2); add(a2); add(b2);
    add(g3); add(a3); add(b3);
    add(g4); add(a4); add(b4);
    add(g5); add(a5); add(b5);

    JLabel eLab = new JLabel("Emphasis");
    eLab.setFont(new Font("Arial",Font.PLAIN,16));
    eLab.setSize(eLab.getPreferredSize());
    eLab.setForeground(Color.gray);
    eLab.setLocation(280,95);
    add(eLab);

    int [] lids = {ButtonFactory.GOAL_1_LEFT,ButtonFactory.GOAL_2_LEFT,
                   ButtonFactory.GOAL_3_LEFT,ButtonFactory.GOAL_4_LEFT,
                   ButtonFactory.GOAL_5_LEFT};
    int [] rids = {ButtonFactory.GOAL_1_RIGHT,ButtonFactory.GOAL_2_RIGHT,
                   ButtonFactory.GOAL_3_RIGHT,ButtonFactory.GOAL_4_RIGHT,
                   ButtonFactory.GOAL_5_RIGHT};
    for(int i=0;i<lids.length;i++)
    {
      leftButts[i]  = ButtonFactory.make(lids[i],mf);
      leftButts[i].addActionListener(this);
      leftButts[i].setActionCommand(""+i+"L");
      leftButts[i].addMouseListener(new SliderMouseAdapter(i,-1));
      add(leftButts[i]);
      
      rightButts[i]  = ButtonFactory.make(rids[i],mf);
      rightButts[i].addActionListener(this);
      rightButts[i].setActionCommand(""+i+"R");
      rightButts[i].addMouseListener(new SliderMouseAdapter(i,1));
      add(rightButts[i]);
    }

    JLabel leftLabel = new JLabel(Ggui.imgIconGet("GOALS5LEFTBACK"));
    leftLabel.setSize(leftLabel.getPreferredSize());
    leftLabel.setLocation(0,0);
    add(leftLabel);
    
    emphBarRegPoint = leftLabel.getPreferredSize().width;
// emphasis bars
    int[] ex = {emphBarRegPoint,emphBarRegPoint,emphBarRegPoint,emphBarRegPoint,emphBarRegPoint};
    int[] ey = {115,167,224,285,339};
    for(int i=0;i<5;i++)
    {
      emphBars[i] = new JLabel(goldBar);
      emphBars[i].setSize(emphBars[i].getPreferredSize());
      emphBars[i].setLocation( ex[i],ey[i] );
      add(emphBars[i]);
    }

    JLabel dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(leftButts[0].getX()+leftButts[0].getWidth()-2,leftButts[0].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(leftButts[1].getX()+leftButts[1].getWidth()-2,leftButts[1].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(leftButts[2].getX()+leftButts[2].getWidth()-2,leftButts[2].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(leftButts[3].getX()+leftButts[3].getWidth()-2,leftButts[3].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(leftButts[4].getX()+leftButts[4].getWidth()-2,leftButts[4].getY()+7);
    add(dial);

    JLabel midLabel = new JLabel(Ggui.imgIconGet("GOALS5MIDBACK"));
    midLabel.setSize(midLabel.getPreferredSize());
    midLabel.setLocation(0,0);
    add(midLabel);
    
    progBarRegPoint = midLabel.getPreferredSize().width;

// progress bars
    int[] px = {progBarRegPoint,progBarRegPoint,progBarRegPoint,progBarRegPoint,progBarRegPoint};
    int[] py = {115,167,224,285,339};
    for(int i=0;i<5;i++)
    {
      progBars[i] = new JLabel(goldBar);
      progBars[i].setSize(progBars[i].getPreferredSize());
      progBars[i].setLocation( px[i],py[i] );
      add(progBars[i]);
    }

    eLab = new JLabel("Progress");
    eLab.setFont(new Font("Arial",Font.PLAIN,16));
    eLab.setSize(eLab.getPreferredSize());
    eLab.setForeground(Color.gray);
    eLab.setLocation(495,95);
    add(eLab);

    barSize = progBars[0].getSize();  // all the same
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(progBarRegPoint,leftButts[0].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(progBarRegPoint,leftButts[1].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(progBarRegPoint,leftButts[2].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(progBarRegPoint,leftButts[3].getY()+7);
    add(dial);
    dial = new JLabel(Ggui.imgIconGet("GOALSDIAL"));
    dial.setSize(dial.getPreferredSize());
    dial.setLocation(progBarRegPoint,leftButts[4].getY()+7);
    add(dial);


    JLabel backLabel = new JLabel(Ggui.imgIconGet("GOALS5BACK"));
    backLabel.setSize(backLabel.getPreferredSize());
    backLabel.setLocation(0,0);
    //backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
    ActionListener autoSlider = new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        numTimerFirings++;
        if(numTimerFirings == 1)
          mf.startLaser(); //mf.doLaser();
        bump(sliderTimerWh,sliderTimerDir<0,sliderIncrement);
      }
    };

    sliderTimer = new Timer(40,autoSlider);
    sliderTimer.setInitialDelay(600);
  }

  Timer sliderTimer;
  int numTimerFirings = 0;
  int sliderTimerWh;
  int sliderTimerDir;

  public void goalChosen(ConnectedGoal cg)
  {
    if(goals[chosenIdx] == cg.goal)
      return;
    // remove the emphasis points of the replaced goal..they go into the bank
    this.unallocEmphPoints += emphasis[chosenIdx];
    gChanged[chosenIdx] = true;
    emphasis[chosenIdx] = 0;

    goals[chosenIdx]=cg.goal;
    la[chosenIdx].setText(cg.goal.getName());
    updateBar(progBars[chosenIdx],cg.goal.getMeasure(),progBarRegPoint);
    updateBar(emphBars[chosenIdx],emphasis[chosenIdx],emphBarRegPoint);
    leftButts[chosenIdx].setEnabled(true);
    rightButts[chosenIdx].setEnabled(true);
    updateBank();
  }

  private void removeGoal(int i)
  {
    if(goals[i] == null)
      return;
    unallocEmphPoints += emphasis[i];
    gChanged[i] = true;
    goals[i] = null;
    la[i].setForeground(Color.gray);
    la[i].setText("None selected");
    updateBar(progBars[i],0.0,progBarRegPoint);
    updateBar(emphBars[i],0.0,emphBarRegPoint);
    leftButts[i].setEnabled(false);
    rightButts[i].setEnabled(false);
    emphasis[i] = 0;
    updateBank();
    addButts[i].setEnabled(true);removeButts[i].setEnabled(false);
  }

  public void go()
  {
    mf.setTitleBar("GOALSTITLE");
    cancelled=false;
    goals = mf.globals.charinsides.chosenGoals;
    emphasis = mf.globals.charinsides.goalEmphasis;
    gChanged = mf.globals.charinsides.goalChangedByGui;
    unallocEmphPoints = 0;

    for(int i=0;i<5;i++)
    {
      //gChanged[i] = false;

      if (goals[i] == null)
      {
        la[i].setText("None Selected");
        la[i].setForeground(Color.gray);
        updateBar(progBars[i],0.0,progBarRegPoint);
        updateBar(emphBars[i],0.0,emphBarRegPoint);
        leftButts[i].setEnabled(false);
        rightButts[i].setEnabled(false);
        emphasis[i] = 0;
        addButts[i].setEnabled(true);removeButts[i].setEnabled(false);
      }
      else
      {
        la[i].setText(goals[i].getName());
        la[i].setForeground(Color.white);
        updateBar(progBars[i],goals[i].getProgress(),progBarRegPoint);
        updateBar(emphBars[i],emphasis[i],emphBarRegPoint);
        leftButts[i].setEnabled(true);
        rightButts[i].setEnabled(true);
        unallocEmphPoints += emphasis[i];
        addButts[i].setEnabled(false);removeButts[i].setEnabled(true);
      }
    }
    unallocEmphPoints = 100- unallocEmphPoints;
    unallocEmphPoints = Math.max(Math.min(100,unallocEmphPoints),0);
    updateBank();
    super.go();

  }
  private void updateBank()
  {
    unallocLabel.setText(""+unallocEmphPoints);
  }
  private void updateBar(JLabel bar, double value, int regPointX)
  {
    value = Math.max(0.,Math.min(value,100.));
    Point p = bar.getLocation();
    p.x = valueToPosition(value, regPointX);
    bar.setLocation(p);
  }
  private int valueToPosition(double value, int regPoint)
  {
    int val = (int)value;
    return (barSize.width*val)/100 - barSize.width + regPoint;
  }
  public void actionPerformed(ActionEvent e)
  {
    sliderTimer.stop();
    mf.stopLaser();
    if(numTimerFirings > 0)
      return;

    boolean left = false;
    String cmd = e.getActionCommand();
    int i = Integer.parseInt(cmd.substring(0,1));
    gChanged[i] = true;
    if(cmd.charAt(1) == 'L')
      left = true;
    bump(i,left,clickIncrement);
  }
  private void bump(int i, boolean left, int increment)
  {
    int vIncr = 0;

    int startEmph = (int)emphasis[i];
    JLabel myBar = emphBars[i];
    Dimension d = myBar.getSize();

    if(left)
    {
      vIncr = Math.min(startEmph,increment);
      vIncr = vIncr * -1;
    }
    else
    {
      vIncr = increment;
      vIncr = Math.min(unallocEmphPoints,vIncr);
    }
    if(vIncr == 0)
    {
      mf.stopLaser();
      return;
    }

    unallocEmphPoints -= vIncr;
    emphasis[i] += (double)vIncr;

    Point p = myBar.getLocation();
    p.x = valueToPosition(emphasis[i],emphBarRegPoint);
    myBar.setLocation(p);

    updateBank();
  }

  public void done()
  {
    super.done();
    if(!cancelled)
    {
      mf.globals.charinsides.chosenGoals = goals;
      mf.globals.charinsides.goalEmphasis = emphasis;
      mf.globals.charinsides.goalChangedByGui = gChanged;
    }
  }

  public void cancel()
  {
    cancelled = true;// to do...change this to enable a complete cancel from the page
    //toLocal(); //fromTo(mf.globals.values,vValue);
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
      JButton b = (JButton)e.getSource();
      if(!b.isEnabled())
        return;
      numTimerFirings = 0;
      sliderTimerWh = wh;
      sliderTimerDir = direction;

      sliderTimer.restart();
    }
  }
}
