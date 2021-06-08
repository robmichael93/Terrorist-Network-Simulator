//
//  AchievementMedalPanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Fri Apr 12 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class AchievementMedalPanel extends RPanel
{
  MainFrame mf;
  AchievementMedalPanel(MainFrame main)
  {
    mf = main;

    setLayout(null);
    setBackground(new Color(0,0,0,128));		// 50% transparent
    
    JButton closeButt = ButtonFactory.makeTextButt(ButtonFactory.GENERICCLOSE);
    closeButt.setLocation(100,200);
    add(closeButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("ACHIEVEMENTMEDALBACK"));
    Dimension d = backLabel.getPreferredSize();
    
    backLabel.setBounds((640-d.width)/2,(480-d.height)/2,d.width,d.height);    

    add(backLabel);

    setVisible(false);
  }

}
