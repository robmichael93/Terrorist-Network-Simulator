/*
* Created by IntelliJ IDEA.
* User: mike
* Date: Apr 22, 2002
* Time: 11:48:37 AM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package com.armygame.recruits.gui;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

abstract public class RecruitsWidgetScroller extends JPanel implements ActionListener
{
  JComponent jcomp;
  JPanel innerP;
  Insets insets;
  JLabel gripper;
  JButton upArrow,downArrow;
  ImageIcon ic;
  ImageIcon upArr, downArr;
  Dimension maxd;

  Timer sliderTimer;
  int numTimerFirings = 0;
  int sliderTimerDir;
  final static int DEFAULTBUMP = 5;
  private int bumpIncr;

  RecruitsWidgetScroller(Dimension maxd)
  {
    this(maxd,1);
  }
  RecruitsWidgetScroller(Dimension maxd, int bumpMultiplier)
  {
    bumpIncr = DEFAULTBUMP * bumpMultiplier;
    this.maxd = maxd;
    sizeit(this,maxd);
  }
  protected void myConstructor(JComponent comp)
  {
    removeAll();
    jcomp = comp;
    this.setLayout(null);
    this.setBorder(BorderFactory.createEmptyBorder()); //createLineBorder(Color.red,5));
    insets = this.getBorder().getBorderInsets(this);
    ic = Ggui.imgIconGetResource("mikesgripper.png");
    upArr = Ggui.imgIconGetResource("mikesUpArrow.png");
    downArr = Ggui.imgIconGetResource("mikesDownArrow.png");
    this.maxd = maxd;

    if(comp != null)
      reBuild(comp);
  }

  protected void reBuild(JComponent comp)
  {
    if(innerP != null)
    {
      innerP.remove(jcomp);
      if(upArrow != null)
        innerP.remove(upArrow);
      if(downArrow != null)
        innerP.remove(downArrow);
      if(gripper != null)
        innerP.remove(gripper);
      upArrow=downArrow=null;gripper=null;
      remove(innerP);
    }
    jcomp = comp;

    boolean doGripper = false;

    Dimension jd = jcomp.getPreferredSize();
    if(jcomp instanceof JTree)
      jd = ((JTree)jcomp).getPreferredScrollableViewportSize();
    else if(jcomp instanceof JEditorPane)
      jd = ((JEditorPane)jcomp).getPreferredScrollableViewportSize();

    jd.width +=50;	// this is a fudge
    if(jd.height > maxd.height)
      doGripper = true;

    jd.width = Math.min(jd.width,maxd.width-ic.getIconWidth());
    sizeit(jcomp,jd);
    jcomp.setLocation(0,0);

    if(doGripper)
    {
      gripper = new JLabel(new GripperIcon(ic,jcomp.getHeight()));
      sizeit(gripper,gripper.getPreferredSize());
      gripper.setLocation(jcomp.getWidth(),0);

      upArrow = new JButton(upArr);
      upArrow.setBorder(null);upArrow.setBorderPainted(false);upArrow.setContentAreaFilled(false);upArrow.setFocusPainted(false);
      sizeit(upArrow,upArrow.getPreferredSize());
      upArrow.setLocation(insets.left+jcomp.getWidth(),insets.top);
      upArrow.setActionCommand("u");
      upArrow.addActionListener(this);
      upArrow.addMouseListener(new SliderMouseAdapter(-1));

      downArrow = new JButton(downArr);
      downArrow.setBorder(null);downArrow.setBorderPainted(false);downArrow.setContentAreaFilled(false);downArrow.setFocusPainted(false);
      sizeit(downArrow,downArrow.getPreferredSize());
      downArrow.setLocation(insets.left+jcomp.getWidth(),insets.top+upArrow.getHeight()); // test
      downArrow.setActionCommand("d");
      downArrow.addActionListener(this);
      downArrow.addMouseListener(new SliderMouseAdapter(1));

    }

    innerP = new JPanel();
    innerP.setLayout(null);
    innerP.setBackground(new Color(51,51,51));
    innerP.add(jcomp);
    int ih = jcomp.getHeight();
    int iw = jcomp.getWidth();
    if(doGripper)
    {
      add(upArrow);
      add(downArrow);
      innerP.add(gripper);
      iw +=ic.getIconWidth();
    }
    sizeit(innerP,new Dimension(iw,ih));

    innerP.setLocation(insets.left,insets.top);

    add(innerP);

    sizeit(this,new Dimension(Math.min(maxd.width,innerP.getWidth()+insets.left+insets.right),
                              Math.min(maxd.height,innerP.getHeight()+insets.top+insets.bottom)));

Point xxx = this.getLocation();
    xxx.x += 5;
    this.setLocation(xxx);
    xxx.x -= 5;
    this.setLocation(xxx);

    if(gripper != null)
    {
      MouseInputAdapter mia =  new MouseInputAdapter()
      {
        int yoff;
        public void mousePressed(MouseEvent e)
        {
          yoff = e.getY();
        }
        public void mouseDragged(MouseEvent e)
        {
          int dif = RecruitsWidgetScroller.this.getHeight()-jcomp.getSize().height;

          Point p = gripper.getLocation();
          p.y += e.getY()-yoff;
          if(p.y>0)
          {
            p.y = 0;
            yoff = e.getY();
          }
          else if (p.y <= dif)
          {
            p.y = dif;
            yoff = e.getY();
          }
          moveit(p);
        }
      };
      gripper.addMouseMotionListener(mia);
      gripper.addMouseListener(mia);

      ActionListener autoSlider = new ActionListener()
      {
        public void actionPerformed(ActionEvent ev)
        {
          numTimerFirings++;
          if(numTimerFirings == 1)
             MainFrame.startLaser();//MainFrame.doLaser();
          bump(sliderTimerDir==-1);
        }
      };
      sliderTimer = new Timer(40,autoSlider);
      sliderTimer.setInitialDelay(600);

    }

  }
  private void moveit(Point p)
  {
    gripper.setLocation(p);
    Point pp = jcomp.getLocation();
    pp.y = p.y;
    jcomp.setLocation(pp);

    /* if I don't do this, the scroll arrows don't get repainted */
    Point p2 = downArrow.getLocation();
    p2.x++;
    downArrow.setLocation(p2);
    p2.x--;
    downArrow.setLocation(p2);

  }

  private void sizeit(JComponent com, Dimension d)
  {
    com.setSize(d);
    com.setMaximumSize(d);
    com.setMinimumSize(d);
    com.setPreferredSize(d);
  }

  public void actionPerformed(ActionEvent e)
  {
    sliderTimer.stop();
    MainFrame.stopLaser();
    if(numTimerFirings > 0)
      return;
    bump(e.getActionCommand().charAt(0)=='u');
  }
  private void bump(boolean minus)
  {
    Point p = gripper.getLocation();
    if(minus)
      p.y-=5;
    else
      p.y+=5;

    int dif = RecruitsWidgetScroller.this.getHeight()-jcomp.getSize().height;

     if(p.y>0)
       p.y = 0;
     else if (p.y <= dif)
       p.y = dif;

    moveit(p);
  }

  class SliderMouseAdapter extends MouseAdapter
  {
    int direction;
    SliderMouseAdapter(int direction)
    {
      this.direction = direction;
    }
    public void mousePressed(MouseEvent e)
    {
      numTimerFirings = 0;
      sliderTimerDir = direction;

      sliderTimer.restart();
    }
  }


}
