//
//  ContactsPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class ContactsPanel extends RPanel
{
  MainFrame mf;
  ContactsPanel(MainFrame main)
  {
    mf = main;
    
    setLayout(null);

    JButton closeButt = ButtonFactory.makeTextButt(ButtonFactory.CONTACTSCLOSE);
    //closeButt.setLocation(530,450);
    add(closeButt);
    
    JLabel backLabel = new JLabel(Ggui.imgIconGet("CONTACTSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }

}
