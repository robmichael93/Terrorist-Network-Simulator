/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 25, 2002
 * Time: 10:59:17 AM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class ContactArmyPanel extends RPanel
{
  MainFrame mf;
  ContactArmyPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
    contentPanel.setBackground(Ggui.darkBackground);

    //setBackground(Ggui.darkBackground);
    JButton goarmyButt = ButtonFactory.makeBigTextButt(ButtonFactory.CONTACTARMYGOARMY,true);
    goarmyButt.setAlignmentX(LEFT_ALIGNMENT);
    contentPanel.add(goarmyButt);
    contentPanel.add(Box.createVerticalStrut(10));

    JButton amerarmyButt = ButtonFactory.makeBigTextButt(ButtonFactory.CONTACTARMYAMERICASARMY,true);
    amerarmyButt.setAlignmentX(LEFT_ALIGNMENT);
    contentPanel.add(amerarmyButt);
    contentPanel.add(Box.createVerticalStrut(10));

    JButton refButt = ButtonFactory.makeBigTextButt(ButtonFactory.CONTACTARMYREFERRAL,true);
    refButt.setAlignmentX(LEFT_ALIGNMENT);
    contentPanel.add(refButt);

    contentPanel.setLocation(50,50);
    contentPanel.setSize(contentPanel.getPreferredSize());
    add(contentPanel);

    add(ButtonFactory.make(ButtonFactory.CONTACTARMYCANCEL,mf));

    JLabel backLabel = new JLabel(Ggui.imgIconGet("CONTACTARMYBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("CONTACTARMYTITLE");
  }
}
