//
//  LinksPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class LinksPanel extends RPanel
{
  MainFrame mf;
  LinksPanel(MainFrame main)
  {
    mf = main;
    
    setLayout(null);

    // this panel is not used
    //JButton closeButt = ButtonFactory.makeTempTextButt(ButtonFactory.LINKSCLOSE,mf);
    //add(closeButt);
    
    JLabel backLabel = new JLabel(Ggui.imgIconGet("LINKSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }

}
