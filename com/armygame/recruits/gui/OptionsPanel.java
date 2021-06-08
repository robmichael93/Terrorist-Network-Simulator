/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jul 29, 2002
 * Time: 9:15:15 AM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.RecruitsMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsPanel extends RPanel
{
  MainFrame mf;
  OptionsPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    //setBackground(Ggui.darkBackground);

    final JButton soundXisOnButt = ButtonFactory.makeBigTextButt(ButtonFactory.OPTIONSSOUNDISON,true);
    soundXisOnButt.setAlignmentX(LEFT_ALIGNMENT);
    add(soundXisOnButt);

    final JButton soundXisOffButt = ButtonFactory.makeBigTextButt(ButtonFactory.OPTIONSSOUNDISOFF,true);
    soundXisOffButt.setAlignmentX(LEFT_ALIGNMENT);
    add(soundXisOffButt);

    if(RecruitsMain.instance().soundEffectsOn == true)
    {
      soundXisOffButt.setVisible(false);
      soundXisOnButt.setVisible(true);
    }
    else
    {
      soundXisOffButt.setVisible(true);
      soundXisOnButt.setVisible(false);
    }

    add(ButtonFactory.make(ButtonFactory.OPTIONSCANCEL,mf));
    add(ButtonFactory.make(ButtonFactory.OPTIONSOK,mf));

    JLabel backLabel = new JLabel(Ggui.imgIconGet("OPTIONSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);


    soundXisOnButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        soundXisOnButt.setVisible(false);
        soundXisOffButt.setVisible(true);
      }
    });
    soundXisOffButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        soundXisOnButt.setVisible(true);
        soundXisOffButt.setVisible(false);
        MainFrame.playDefaultQuip(true);
      }
    });
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("OPTIONSTITLE");
  }
}
