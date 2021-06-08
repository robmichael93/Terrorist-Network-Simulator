//
//  AudioClipPlayer.java
//  projectbuilder5
//
//  Created by Mike Bailey on Mon Apr 08 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;

import java.applet.Applet;
import java.applet.AudioClip;
import com.armygame.recruits.services.SimpleObjectFIFO;

public class AudioClipPlayer implements Runnable
{
  SimpleObjectFIFO queue;
  MainFrame mf;
  boolean loop = false;
  AudioClipPlayer(MainFrame mf, boolean loop)
  {
    this.mf = mf;
    this.loop = loop;

    queue = new SimpleObjectFIFO(5);
    new Thread(this).start();
  }
  AudioClipPlayer(MainFrame mf)
  {
    this(mf,false);
  }

  volatile boolean fatal = false;
  AudioClip aClip;
  public void run()
  //---------------
  {
    while(!fatal)
    {
      try
      {
        String clipName;
        Object o = queue.remove();
        if(o instanceof String)
        {
          aClip = Applet.newAudioClip(Ggui.resourceGet((String)o));
        }
        else
          aClip = (AudioClip)o;
        
        if(loop)
          aClip.loop();
        else
          aClip.play();
      }
      catch(InterruptedException e){}
    }
  }
  public void killme()
    //..................
  {
    fatal = true;
    queue.notify();
  }
  public void play(String resourceName)
  {
    addToQueue(resourceName);
  }
  public void play(AudioClip clip)
  {
    addToQueue(clip);
  }
  public void stop()
  {
    if(aClip != null)
      aClip.stop();
  }
  private void addToQueue(Object o)
  {
    do
    {
      try
      {
        queue.add(o);			// will block here if queue is full
        return;
      }
      catch(InterruptedException e){}
    }
    while (true);
  }
}
