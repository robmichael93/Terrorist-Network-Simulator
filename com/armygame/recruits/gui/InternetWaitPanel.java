//
//  InternetWaitPanel.java
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

public class InternetWaitPanel extends RPanel
{
  MainFrame mf;
  
  public InternetWaitPanel(MainFrame main)
  {
    mf = main;
    setOpaque(false);
    setBackground(Ggui.transparent); //new Color(0,0,0,255)); //128));
    setLayout(null);

    JPanel popup = new JPanel();
    popup.setOpaque(false);
    popup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                          BorderFactory.createLineBorder(Color.black,1)),
                                       BorderFactory.createEmptyBorder(10,10,10,10)));
    popup.setLayout(null); //new BoxLayout(popup,BoxLayout.Y_AXIS));

    JPanel inner = new JPanel();
    inner.setBackground(Ggui.darkBackground);
    inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
    inner.add(Box.createVerticalStrut(10));
    inner.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    JLabel acc = new JLabel("Accessing the Internet.");
    acc.setForeground(Color.white);
    acc.setFont(new Font("Arial",Font.PLAIN,14));
    acc.setAlignmentX(CENTER_ALIGNMENT);
    inner.add(acc);
    JLabel acc2 = new JLabel("Please wait or cancel.");
    acc2.setAlignmentX(CENTER_ALIGNMENT);
    acc2.setForeground(Color.white);
    acc2.setFont(new Font("Arial",Font.PLAIN,14));
    inner.add(acc2);
    inner.add(Box.createVerticalStrut(10));
    JButton cancelButt = ButtonFactory.make(ButtonFactory.INTERNETCANCEL,mf);
    cancelButt.setAlignmentX(CENTER_ALIGNMENT);
    inner.add(cancelButt);
    cancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      }
    });
    //popup.add(Box.createVerticalStrut(10));

    Dimension d = inner.getPreferredSize();
    inner.setSize(d);
    inner.setLocation(10,10);
    popup.add(inner);
    popup.setSize(d.width+20,d.height+20);
    popup.setLocation((640-d.width-20)/2,(480-d.height-20)/2);
    add(popup);
    setVisible(false);
  }
}
