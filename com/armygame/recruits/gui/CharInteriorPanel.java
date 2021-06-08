//
//  CharInteriorPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharInteriorPanel extends RPanel
{
  MainFrame mf;

  CharInteriorPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    JButton closeButt = ButtonFactory.make(ButtonFactory.CHARINTCLOSE,mf);
    add(closeButt);
    
    JButton valuesButt = ButtonFactory.make(ButtonFactory.CHARINTVALUES,mf);
    add(valuesButt);
    
    JButton resourceButt = ButtonFactory.make(ButtonFactory.CHARINTRESOURCES,mf);
    add(resourceButt);
    
    JButton goalsButt = ButtonFactory.make(ButtonFactory.CHARINTGOALS,mf);
    add(goalsButt);
    
    JLabel backLabel = new JLabel(Ggui.imgIconGet("SOLDIERINTERIORBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }
  
  public void go()
  {
    super.go();
  }
  public void done()
  {
    super.done();
  }
  public void cancel()
  {
  } 
}
