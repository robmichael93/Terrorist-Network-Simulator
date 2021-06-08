//
//  MsgPanel.java
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

public class MsgPanel extends RPanel
{
  MainFrame mf;

  public MsgPanel(MainFrame main)
  {
    mf = main;
    setBackground(Color.black); //new Color(0,0,0,255)); //128));
    setLayout(null);

    setVisible(false);
  }
  
  public void setMsg(String[] s) {
  	removeAll();
    JPanel popup = new JPanel();
    popup.setBackground(Ggui.darkBackground);
    popup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                          BorderFactory.createLineBorder(Color.black,1)),
                                       BorderFactory.createEmptyBorder(10,10,10,10)));
    popup.setLayout(new BoxLayout(popup,BoxLayout.Y_AXIS));
    popup.add(Box.createVerticalStrut(10));
		for (int i=0; i < s.length; ++i) {
	    JLabel acc = new JLabel(s[i]);
	    acc.setForeground(Color.white);
	    acc.setFont(new Font("Arial",Font.PLAIN,14));
	    acc.setAlignmentX(CENTER_ALIGNMENT);
	    popup.add(acc);
		}
    popup.add(Box.createVerticalStrut(10));
    JButton cancelButt = ButtonFactory.make(ButtonFactory.MSG_OK,mf);
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
 	} 
 	
 	public void setMsg(String s) {
 		setMsg(new String[]{s});
 	}
}