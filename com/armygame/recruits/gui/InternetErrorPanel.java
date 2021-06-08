//
//  InternetErrorPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Wed Mar 20 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InternetErrorPanel extends RPanel
{
  MainFrame mf;

  public InternetErrorPanel(MainFrame main)
  {
    mf = main;

    setBackground(Ggui.transparent); //new Color(0,0,0,255)); //128));
    setLayout(null);

    JPanel popup = new JPanel();
    popup.setBackground(Ggui.darkBackground);
    popup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                          BorderFactory.createLineBorder(Color.black,1)),
                                       BorderFactory.createEmptyBorder(10,10,10,10)));
    popup.setLayout(new BoxLayout(popup,BoxLayout.Y_AXIS));

    popup.add(Box.createVerticalStrut(10));

    JLabel acc = new JLabel("There was an error accessing the internet.");
    acc.setForeground(Color.white);
    acc.setFont(new Font("Arial",Font.PLAIN,14));
    acc.setAlignmentX(CENTER_ALIGNMENT);
    popup.add(acc);

    JLabel acc1 = new JLabel("Your handle and/or password were not accepted, or");
    acc1.setForeground(Color.white);
    acc1.setFont(new Font("Arial",Font.PLAIN,14));
    acc1.setAlignmentX(CENTER_ALIGNMENT);
    popup.add(acc1);

    JLabel acc2 = new JLabel("the server or your connection may be down.");
    acc2.setAlignmentX(CENTER_ALIGNMENT);
    acc2.setForeground(Color.white);
    acc2.setFont(new Font("Arial",Font.PLAIN,14));
    popup.add(acc2);

    popup.add(Box.createVerticalStrut(10));
    JButton cancelButt = ButtonFactory.make(ButtonFactory.INTERNETERROROK,mf);
    cancelButt.setAlignmentX(CENTER_ALIGNMENT);
    popup.add(cancelButt);
    cancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      }
    });
    //popup.add(Box.createVerticalStrut(10));

    Dimension d = popup.getPreferredSize();
    popup.setSize(d);
    popup.setLocation((640-d.width)/2,(480-d.height)/2);
    add(popup);
    setVisible(false);
  }
}
