//
//  CharNamePanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2002 Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.Collections;

import com.armygame.recruits.RecruitsProperties;
import com.armygame.recruits.gui.laf.ShadowBorder;

public class CharNamePanel extends RPanel implements ActionListener
{
  MainFrame mf;
  JButton jonesButt,smithButt,millerButt,marshButt,masonButt,starkeyButt;
  JButton okButt;
  String chosen = "Jones";
  Object[] oa;
  JLabel portrait,caption;
  CharNamePanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    portrait = new JLabel(Ggui.imgIconGet("DEFAULTACTORIMG"));
    portrait.setSize(portrait.getPreferredSize());
    portrait.setLocation(193,205);
    add(portrait);

    SoldierNameProperties namePs = new SoldierNameProperties();

    Vector vv = new Vector();

    for(Enumeration enn = namePs.propertyNames();enn.hasMoreElements();)
    {
      vv.add(enn.nextElement());
    }
    Collections.sort(vv);
    oa = vv.toArray();

    final JList jlist = new JList(oa); //rls.jlist;
    jlist.setForeground(Ggui.buttonForeground());
    jlist.setBackground(Ggui.mediumBackground);
    jlist.setFont(Ggui.buttonFont());
    JScrollPane jsp = new JScrollPane(jlist);

    jsp.setPreferredSize(new Dimension(180,135));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setBackground(Ggui.medDarkBackground);
    jsp.setBorder(new CompoundBorder(new ShadowBorder(5,true,10),
                                   BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
                                                                    new Color(91,91,91),
                                                                    new Color(51,51,51))));
    jsp.setLocation(320,193);
    add(jsp);

  // removed...  okButt = ButtonFactory.make(ButtonFactory.NAMEOK,mf);
    okButt.setEnabled(false);
    add(okButt);

    caption = new JLabel("");
    caption.setLocation(182,285);
    caption.setFont(new Font(caption.getFont().getName(),Font.BOLD,18));
    caption.setSize(100,30);
    caption.setHorizontalAlignment(SwingConstants.CENTER);
    caption.setForeground(new Color(0.93f,0.80f,0.02f));
    caption.setText(chosen);
    add(caption);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("CHARNAMEBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    jlist.getSelectionModel().addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if(e.getValueIsAdjusting())
          return;
        int index = jlist.getSelectedIndex();
        chosen = (String)oa[index];
        caption.setText(chosen);
        mf.setCharName(chosen);
        okButt.setEnabled(true);
      }
    });

    setVisible(false);
  }

  public void actionPerformed(ActionEvent ev)
  {
  }

  public String getChosen()
  {
    return chosen;
  }

  public void go()
  {
    super.go();
    portrait.setIcon(mf.globals.charHead);
  }

  public void done()
  {
    super.done();
    okButt.setEnabled(false);
  }
}
