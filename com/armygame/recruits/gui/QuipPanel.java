/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 21, 2002
 * Time: 5:07:53 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.AttributeSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.applet.AudioClip;
import java.applet.Applet;

public class QuipPanel extends RPanel implements Runnable
{
  MainFrame mf;
  JTextArea ta;
  JButton closeButt;
  final static int INCR = 8;
  final static int SLEEPMILLI = 15;
  final static boolean SHOWING = true;
  final static boolean HIDING = false;
  int thumbOffset;
  JLabel thumb;
  int sleepmilli = SLEEPMILLI;
  int numPushes;
  int numNotes;
  int numClips;
  Icon pushes[];
  Icon notes[];
  AudioClip clips[];

  JLabel back;
  QuipPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setOpaque(false);

    buildArrays();
    back = new JLabel(Ggui.imgIconGet("QUIPNOTE_DEFAULT"));//QUIPBACK"));
    back.setLocation(0,0);

    thumb = new JLabel(Ggui.imgIconGet("QUIPPUSH_DEFAULT")); //"QUIPTHUMB"));

    ta = new JTextArea();
    ta.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
    ta.setForeground(Color.black);
    ta.setEditable(false);
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    ta.setOpaque(false);
    ta.setLocation(10,10);

    sizeAll();
    add(thumb);
    add(ta);
    add(back);

    MouseAdapter myma = new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if(killTimer != null)
          killTimer.stop();
        hideQuip(true);
      }
    };

    back.addMouseListener(myma);
    ta.addMouseListener(myma);
  }
  private void sizeAll()
  {
    // This is not required if all the thumbs and notes are the same size, which they are,
    // but it's here just in case.
    Dimension d = back.getPreferredSize();
    back.setSize(d);
    this.setSize(d);
    thumb.setSize(thumb.getPreferredSize());
    thumbOffset = d.height-thumb.getHeight();
    initThumbLoc();
    Dimension tad = new Dimension(d);
    tad.width-=20;
    tad.height-=20;
    ta.setSize(tad);
  }
  private void buildArrays()
  {
    numPushes = Ggui.getIntProp("QUIPPUSHES_NUM");
    numNotes = Ggui.getIntProp("QUIPNOTES_NUM");
    numClips = Ggui.getIntProp("QUIPCLIPS_NUM");
    pushes = new Icon[numPushes];
    notes = new Icon[numNotes];
    clips = new AudioClip[numClips];

    for(int i=0;i<numPushes;i++)
      pushes[i] = Ggui.imgIconGet("QUIPPUSH_"+i);
    for(int i=0;i<numNotes;i++)
      notes[i]  = Ggui.imgIconGet("QUIPNOTE_"+i);
    for(int i=0;i<clips.length;i++)
      clips[i] = Applet.newAudioClip(Ggui.resourceGet("QUIPCLIPS_"+i));
  }
  int lastNote=-1;
  private Icon randomNote()
  {
    //double r = Math.random()*numNotes;
    //if(r == (double)numNotes) r-=.01;
    //return notes[(int)Math.floor(r)];
    if(++lastNote >= notes.length)
      lastNote = 0;
    return notes[lastNote];
  }
  int lastPush=-1;
  private Icon randomPush()
  {
    //double r = Math.random()*numPushes;
    //if(r == (double)numPushes) r-=.01;
    //return pushes[(int)Math.floor(r)];
    if(++lastPush >= pushes.length)
      lastPush = 0;
    return pushes[lastPush];
  }
  int lastClip=-1;
  private AudioClip randomClip()
  {
    if(++lastClip >= clips.length)
      lastClip = 0;
    return clips[lastClip];
  }
  private void initThumbLoc()
  {
    thumb.setLocation(0,thumbOffset);
  }

  void showQuip(final String text)
  {
    Timer tim=new Timer(3000,new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      { // we're in the swing event thread at this point
        _showQuip(text);
      }
    });
    tim.setRepeats(false);
    tim.start();
  }

  void _showQuip(String text)
  {
    ta.setText(text);
    if(this.getY() < 600)
      return;
    back.setIcon(randomNote());
    thumb.setIcon(randomPush());
    sizeAll();
    //initThumbLoc();
    sleepmilli=SLEEPMILLI;
    direction = SHOWING;
    Thread x = new Thread(this);
    x.setPriority(Thread.MAX_PRIORITY);
    x.start();
    //new Thread(this).start();
    //mf.playQuip();
    mf.playThisQuip(randomClip());
  }
  boolean direction = true;   // or
  public void run()
  {
    if(direction == SHOWING)
      showme();
    else
      hideme();
  }
  boolean thumbing;
  private synchronized void showme()
  {
    if(showing == true) return;
    showing = true;
    hiding = false;
    thumbing = false;

    while(true)
    {
      if(!thumbing)
      {
        if(this.getY() <= (600-this.getHeight()))
        {
          sleepmilli *= 6;
          thumbing = true;
          try{Thread.sleep(1000);}catch(Exception e){}
        }
      }
      if(thumb.getY()>= QuipPanel.this.getHeight())
      {
        setKillTimer();
        return;
      }
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          if(thumbing)
          {
            int amount = Math.min(INCR,QuipPanel.this.getHeight()-thumb.getY());
            thumb.setLocation(0,thumb.getY()+amount);
          }
          else
          {
          // move exactly to the edge and no more:
          int amount = Math.min(INCR,QuipPanel.this.getHeight()+QuipPanel.this.getY()-600);
          QuipPanel.this.setLocation(QuipPanel.this.getX(),QuipPanel.this.getY()-amount);
          }
        }
      });
      try{Thread.sleep(SLEEPMILLI);}catch(Exception e){}
    }
  }
  Timer killTimer;
  private void setKillTimer()
  {
    killTimer=new Timer(4000,new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        hideQuip(false);
      }
    });
    killTimer.setRepeats(false);
    killTimer.start();
  }

  private boolean hiding = false;
  private boolean showing = false;

  private synchronized void hideme()
  {
    if(hiding == true) return;
    hiding = true;
    showing = false;

    if(playCloseSound)
      mf.playQuipOff();
    while(true)
    {
      if(this.getY() >= 600)
        return;
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          QuipPanel.this.setLocation(QuipPanel.this.getX(),QuipPanel.this.getY()+INCR);
        }
      });
      try{Thread.sleep(SLEEPMILLI);}catch(Exception e){}
    }
  }
  boolean playCloseSound;
  void hideQuip(boolean playSound)
  {
    playCloseSound = playSound;
    direction = HIDING;
    new Thread(this).start();
  }
}
