//
//  IntroPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
//import com.armygame.recruits.globals.ResourceReader;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.Color;

import javax.media.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;

public class IntroPanel extends RPanel implements Runnable, ControllerListener
//public class IntroPanel extends RPanel implements Runnable, ControllerListener
{
  String approot;
  MainFrame mf;
  Player player;
  //javax.media.bean.playerbean.MediaPlayer player;
  Component visual;
  private int thisPanelWidth = 640;
  private int thisPanelHeight = 480;

  Object sync = new Object();

  URL movieURL;
  boolean killed = false;

  IntroPanel(MainFrame main)
  {
    approot = System.getProperty("user.dir");
    try{movieURL = new URL("file:/"+approot+"/mov/recruitsintro.mov");}
    catch(Exception e) {System.out.println("error: "+e);}
    killed = false;
    mf = main;
    setLayout(null);
    setBackground(new Color(0,0,0)); //,0));	// transparent
  }
  boolean went = false;
  public void go()
  {
    super.go();
    killed=false;
    new Thread(this).start();
  }
  public void go(String moviepath)
  {
    killed = false;
    try{movieURL= new URL("file:/"+approot+ "/" + moviepath);}
    catch(Exception e){System.out.println("error: "+e); return;}
    go();
  }

  int videoWidth = 0;
  int videoHeight = 0;
  public void run()
  {
    // to allow JMF to work with Swing
    Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
    if(killed)return;
    try
    {
      System.out.println(movieURL.getPath());
      // 22 Apr 02 JMF can't handle a URL pointing to a file in a jar.  Confirmed in Sun db.
      synchronized(sync)
      {
        player = Manager.createPlayer(movieURL);
//MediaLocator ml = new MediaLocator(System.getProperty("user.dir")+"/mov/recruitsintro.mov");
//player = new javax.media.bean.playerbean.MediaPlayer();
//player.setMediaLocator(ml);
        player.addControllerListener(this);
        if(killed)return;
        player.realize();
        if(killed)return;
      }
    }
    catch (Exception e)
    {
      System.out.println("bad url or player creation");
      if(mf != null)
        doEventDone();
    }
  }
  private void doEventDone()
  {
    if(!killed)
      mf.handlers.eventIn(ButtonFactory.INTRODONE);
  }

  public void kill()
  {
    killed = true;
    synchronized(sync)
    {
      player.stop();
    }
  }

  private ChangeListener eomListener = null;
  public void addChangeListener(ChangeListener list)
  {
     eomListener = list;
  }

  public void controllerUpdate(ControllerEvent ce)
  {
    synchronized(sync)
    {
      if (ce instanceof RealizeCompleteEvent)
      {
        player.prefetch();
      }
      else if (ce instanceof PrefetchCompleteEvent)
      {
        //if (visual != null){ System.out.println("returning");
        //  return;}
        mf.skipIntroButt.setEnabled(true);
        if ((visual = player.getVisualComponent()) != null)
        {
          final Dimension size = visual.getPreferredSize();
          videoWidth = size.width;
          videoHeight = size.height;
          visual.setBounds((getWidth()-videoWidth)/2,(getHeight()-videoHeight)/2,videoWidth,videoHeight);
          SwingUtilities.invokeLater(new Runnable(){public void run()
          {
            add( visual);
          }
          });

        }
        else
        {
          System.out.println("visual component == null, "+visual);
          videoWidth = 320;
        }
        /* if you don't want a controller, you don't call this method!  Totally counter-intuitive.*/
        //if ((control = mplayer.getControlPanelComponent()) != null)
        //{
        //controlHeight = control.getPreferredSize().height;
        ///*getContentPane().*/add("South", control);
        //}
        // setSize(videoWidth, videoHeight /*+ controlHeight*/ );

        validate();
        player.start();
      }
      else if (ce instanceof EndOfMediaEvent || ce instanceof ResourceUnavailableEvent ||
               ce instanceof StopByRequestEvent)
      {
        player.removeControllerListener(this);
        if(visual != null)
        {
          SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              remove(visual);
              visual=null;
              IntroPanel.this.setVisible(false);
            }
          });
        }
        if(this.eomListener != null)
        {
          eomListener.stateChanged(new ChangeEvent(IntroPanel.this));
          eomListener = null;
        }
        if(mf != null)
          doEventDone();
        else
        {
          SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              imdone();
            }
          });
        }
      }
    } // synch

  }

  static JFrame jframe;
  static IntroPanel ip;
  static PipedInputStream pis;
  static BufferedReader bis;
  static PrintStream origOutSt;
  static JTextArea ta;
  public static void main(String args[])
  {
    JFrame f = new JFrame("movie test2");
    f.setSize(700,500);
    Container c = f.getContentPane();

    IntroPanel ip = new IntroPanel(null);
    c.setLayout(null);
    ip.setBounds(0,0,640,480);
    c.add(ip);
   // f.pack();
    f.show();
    ip.go();
  }
  public static void xmain(String args[])
  {
    PipedOutputStream pos = new PipedOutputStream();

    PrintStream ps = new PrintStream(pos);

    try{
      pis = new PipedInputStream(pos);
      bis = new BufferedReader(new InputStreamReader(pis));
    }
    catch(Exception exc)
    {
      System.exit(-1);
    }

    ta = new JTextArea("JMF Output:\n");
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    ta.setEditable(false);

    JScrollPane jsp = new JScrollPane(ta);
    jsp.setLocation(0,470);
    jsp.setPreferredSize(new Dimension(300,100));
    jsp.setSize(jsp.getPreferredSize());

    jframe = new JFrame("JMF movie tester");
    jframe.setSize(800,600);
    ip = new IntroPanel(null);
    ip.setBackground(Color.lightGray);
    ip.setBorder(BorderFactory.createEtchedBorder());
    ip.setBounds(50,50,640,480);
    jframe.getContentPane().setLayout(null);
    jframe.getContentPane().add(jsp);
    jframe.getContentPane().add(ip);
    jframe.show();

    new Thread(new Runnable()
    {
      public void run()
      {
        int ch;
        char cha[] = new char[1];
        try{
          while( true )
          //while((ch = pis.read()) != -1)
          {
            //cha[0]=(char)ch;
            //ta.append(new String(cha));
            ip.updateTa(bis.readLine());
          }
        }
        catch(Exception ex)
        {
          System.setOut(origOutSt);
          System.out.println("exception from pipe: "+ex);
        }
      }
    }).start();

    origOutSt = System.out;
    System.setOut(ps);
    System.setErr(ps);

    ip.imdone();
  }
  String lastPath;
  public void imdone()
  {
    JFileChooser chooser = new JFileChooser(lastPath==null?System.getProperty("user.dir"):lastPath);
    int returnVal = chooser.showOpenDialog(jframe);
    if(returnVal == JFileChooser.APPROVE_OPTION)
    {
      lastPath=chooser.getSelectedFile().getPath();
      go(chooser.getSelectedFile().getAbsolutePath());
    }
    else
      System.exit(0);
  }

  private void updateTa(final String s)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        if(s.length()>0)
          ta.append(s+"\n");
        Rectangle visibler = ta.getVisibleRect();
        Rectangle boundsr = ta.getBounds();
        visibler.y = boundsr.height - visibler.height;
        ta.scrollRectToVisible(visibler);
      }
    });
  }
}
