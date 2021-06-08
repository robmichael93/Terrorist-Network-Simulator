//
//  MainPanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Wed Apr 03 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainPanel extends RPanel
{
  MainFrame mf;
  MainPanel(MainFrame main)
  {
    mf = main;

    setLayout(null);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("MAINPANELBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  Vector evV = new Vector();
  Vector obV = new Vector();

  public void setNextEvent(int evnum, Object o)
  {
    evV.add(new Integer(evnum));
    obV.add(o);
    if(active)
      checkAndSend();
  }
  boolean active = false;
  private void checkAndSend()
  {
    if(!evV.isEmpty())
    {
      int evn = ((Integer)(evV.remove(0))).intValue();
      Object o = obV.remove(0);
      mf.handlers.eventIn(evn,o);
      if(active)
        checkAndSend();   // will recurse, but never too deep
    }
  }

  public void go()
  {
    super.go();
    checkAndSend();
  //  active = true;
    mf.setTitleBar(null);
  }
  public void done()
  {
    super.done();
  //  active = false;
  }
}
