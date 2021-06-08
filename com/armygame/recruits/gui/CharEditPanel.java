//
//  FilePanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class CharEditPanel extends RPanel
{
  ImageIcon[] imgs = new ImageIcon[21];     // sb_22 is dup of 1
  Rectangle[] targets = new Rectangle[21];
  ImageIcon[] zoomImgs = new ImageIcon[27];

  /* buttons on the panel
  JButton     valuesButt,goalsButt,resButt;
  */

  AudioClip missedClip, zoomClip;

  int idx = -1;
  int zoomIdx = -1;
  JButton globButt;
  JLabel zoomLabel;
  MainFrame mf;
  String imgpre, imgpost;
  CharEditPanel me;
  boolean hitIt;
  //JLabel onbox[];
  //JLabel offbox[];
  int activeBox = 0;
  int loopcount = 0;
  int threadSleepMs = 150;
  int zoomThreadSleepMs = 75; //50;
  JLabel indicatorOn,indicatorOff;
  Rectangle offRect,offBounds;

  CharEditPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    me = this;

    final JButton closeButt = ButtonFactory.make(ButtonFactory.CHAREDITCLOSE,mf);
    add(closeButt);

    //JButton viewButt = ButtonFactory.make(ButtonFactory.CHAREDITVIEW,mf);
    //add(viewButt);


    JLabel title = new JLabel("Access your soldier interior to control your soldier's Values, Goals and Resources.");
    title.setFont(Ggui.buttonFont());
    title.setForeground(Ggui.buttonForeground());
    title.setSize(title.getPreferredSize());
    title.setLocation(20,20);
    add(title);


    JLabel info = new JLabel("Use your mouse to click on the door on the side of the spinning ball.");
    info.setFont(Ggui.buttonFont());
    info.setForeground(Ggui.buttonForeground());
    info.setSize(title.getPreferredSize());
    info.setLocation(20,50);
    add(info);

    imgpre = "sb_";
    imgpost= ".png";
    String istr;
    for(int i=1;i<=imgs.length;i++)
    {
       if(i<10) istr="0"+i;else istr = ""+i;
       imgs[i-1]    = Ggui.imgIconGetResource(imgpre + istr + imgpost);
       targets[i-1] = new Rectangle(0,0,1,1); // one pixel
    }
    //imgpre = "if_000";
    imgpre = "if_0000000";
    for(int i=0;i<zoomImgs.length;i++)
    {
      if(i<10) istr="0"+i;else istr = ""+i;
      zoomImgs[i] = Ggui.imgIconGetResource(imgpre + istr + imgpost);
    }
    // Now set up the rectangles for the images where the door is exposed:
    targets[0] = new Rectangle(70,50,40,68); //20,15,44,62); // sb_01
    targets[1] = new Rectangle(52,56,38,65); //15,15,38,62); // sb_02
    targets[2] = new Rectangle(40,56,30,62); //8, 15,34,62); // sb_03
    targets[3] = new Rectangle(35,57,20,60); //4, 15,30,62); // sb_04
    //targets[4] = new Rectangle(4, 15,22,62); // sb_05
    //targets[16]= new Rectangle(68,15,20,62); // sb_17
    targets[17]= new Rectangle(131,60,14,55);//62,15,26,62); // sb_18
    targets[18]= new Rectangle(122,62,21,55);//56,15,32,62); // sb_19
    targets[19]= new Rectangle(103,60,36,62);//47,15,40,62); // sb_20
    targets[20]= new Rectangle(89,56,38,66); //37,15,40,62); // sb_21
 //   targets[21]= new Rectangle(70,55,40,66);  // sb_22       // dup!

    zoomLabel = new JLabel(zoomImgs[0]);
    zoomLabel.setSize(zoomLabel.getPreferredSize());
    zoomLabel.setLocation(230,169);
    zoomLabel.setVisible(false);
    add(zoomLabel);

    globButt = new JButton(imgs[0]);
    globButt.setBorder(null);
    globButt.setFocusPainted(false);
    globButt.setContentAreaFilled(false);
    globButt.setSize(globButt.getPreferredSize());
    globButt.setLocation(230,169); //(280,225);
    add(globButt);
  /*
    onbox  = new JLabel[5];
    offbox = new JLabel[5];
    ImageIcon offBoxImg = Ggui.imgIconGetResource("globe_boxoff.png");
    for(int i=0;i<onbox.length;i++)
    {
      onbox[i] = new JLabel(Ggui.imgIconGetResource("globe_box"+i+"on.png"));
      offbox[i] = new JLabel(offBoxImg); //Ggui.imgIconGetResource("globe_box"+i+"off.gif"));
      onbox[i].setSize(onbox[i].getPreferredSize());
      offbox[i].setSize(offbox[i].getPreferredSize());
      onbox[i].setLocation(409,216+i*23);
      offbox[i].setLocation(409,216+i*23);
      // hack:
      if(i==0) {
        onbox[i].setLocation(409,218+i*23);
        offbox[i].setLocation(409,218+i*23);
      }
      add(onbox[i]);
      add(offbox[i]);
    }
    activeBox = 0;
   */
    indicatorOff = new ClippedLabel(Ggui.imgIconGetResource("spinning_ball_indicator_OFF.png"));
    indicatorOff.setSize(indicatorOff.getPreferredSize());
    indicatorOff.setLocation(500,160);
    add(indicatorOff);

    offRect = indicatorOff.getBounds(offRect);
    offBounds = indicatorOff.getBounds(offBounds);
    offBounds.x = offBounds.y = 0;

    indicatorOn = new JLabel(Ggui.imgIconGetResource("spinning_ball_indicator_ON.png"));
    indicatorOn.setSize(indicatorOn.getPreferredSize());
    indicatorOn.setLocation(500,160);
    add(indicatorOn);

    JLabel ballBack = new JLabel(Ggui.imgIconGetResource("ball_background_02.png"));
    ballBack.setSize(ballBack.getPreferredSize());
    ballBack.setLocation(215,150);
    add(ballBack);
/* button on the panel
    resButt = ButtonFactory.make(ButtonFactory.CHAREDITRES,mf);
    add(resButt);
    resButt.setVisible(false);
    valuesButt = ButtonFactory.make(ButtonFactory.CHAREDITVALUES,mf);
    add(valuesButt);
    valuesButt.setVisible(false);
    goalsButt = ButtonFactory.make(ButtonFactory.CHAREDITGOALS,mf);
    add(goalsButt);
    goalsButt.setVisible(false);
*/
    JLabel backLabel = new JLabel(Ggui.imgIconGet("GLOBEWINDOWBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    hitIt = false;


    globButt.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent ev)
      {
        if ( (hitIt == false && targets[idx].contains(ev.getPoint())) ||
            missedCount > 7)
        {
          hitIt = true;
          zoomLabel.setVisible(true);
          globButt.setVisible(false);
          playZoomingSound();
        }
        else
        {
          incrementBox();
          playMissedSound();
        }
      }
    });
    
    new Thread (new Runnable()
    {
      public void run()
      {
        try
        {
          while(true)
          {
            loopcount++;
            if(me.isVisible())
            {
              if(hitIt == false)
              {
                if(idx < (imgs.length-1))
                  idx++;
                else
                  idx=0;
             //   if(loopcount%4 == 0)flashBox();
                SwingUtilities.invokeLater(new Runnable(){public void run() {  // not required, but should be?
                  globButt.setIcon(imgs[idx]);
                  globButt.setPressedIcon(imgs[idx]);
                 }});
                Thread.sleep(threadSleepMs);
              }
              else
              {
                if(zoomIdx < (zoomImgs.length+25))//8))//(zoomImgs.length-1))
                {
                  zoomIdx++;
                  zoomLabel.setIcon(zoomImgs[Math.min(zoomIdx,zoomImgs.length-1)]);
                  Thread.sleep(zoomThreadSleepMs);
                }
                else
                {
                  fireDone();
        //jmbtest          me.setVisible(false);
        //jmbtest          zoomLabel.setVisible(false);
                  hitIt = false;
                  zoomIdx = -1;
                }
              }
            }
            else
              Thread.sleep(400);
          }
        }
        catch (Exception e){}
      }
    }).start();
    
    
    try
    {
      missedClip = Applet.newAudioClip(Ggui.resourceGet("MISS_SOUND"));//new URL("file:art/miss.wav"));
      zoomClip   = Applet.newAudioClip(Ggui.resourceGet("LOCK_SOUND"));//new URL("file:art/lock.wav"));
    }
    catch(Exception e)
    {
      System.out.println("can't find wav files");
    }
    setVisible(false);
  }

  private void fireDone() // pretend that a button was hit to do this event.
  {
    /* buttons on the panel
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        valuesButt.setVisible(true);
        goalsButt.setVisible(true);
        resButt.setVisible(true);
      }
    });
    */
    mf.handlers.eventIn(ButtonFactory.CHARHIT);
  }

  public void go()
  {
    activeBox=0;
    threadSleepMs = 50;
    globButt.setVisible(true);
    missedCount=0;
    offRect = null;

    //for(int i=0;i<onbox.length;i++)
    //  onbox[i].setVisible(true);
    super.go();

  }
  
  private void xflashBox()
  {
    //onbox[activeBox].setVisible(!onbox[activeBox].isVisible());
  }
  private void playMissedSound()
  {
    missedClip.play();
  }
  private void playZoomingSound()
  {
    zoomClip.play();
  }
  int missedCount = 0;
  private void incrementBox()
  {
    missedCount++;
    indicatorOff.repaint(offBounds);

    //if(activeBox < onbox.length-1)
    //{
      //onbox[activeBox].setVisible(true);
      //activeBox++;
      // uncomment to speed up w/ each miss
      // threadSleepMs -= 10;
    //}
  }

  class ClippedLabel extends JLabel
  {
    ClippedLabel(Icon image)
    {
      super(image);
    }
    protected void paintComponent(Graphics g)
    {
      if(offRect == null)
        offRect = indicatorOff.getBounds(offRect);
      offRect.height = Math.max(offBounds.height-missedCount*27,0);
      g.setClip(0,0,offRect.width,offRect.height);
      super.paintComponent(g);
    }
  }
}
