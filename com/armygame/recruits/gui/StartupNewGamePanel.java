/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jul 2, 2002
 * Time: 1:23:24 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class StartupNewGamePanel extends RPanel
{
  MainFrame mf;
  JButton newGameButt,loadGameButt;
  JPanel contentP,newGamePopup;
  StartupNewGamePanel(MainFrame main)
  {
    mf = main;

    setLayout(null);
    setOpaque(false);
    setBackground(Ggui.transparent);

    newGamePopup=this.makeNewPopup();
    add(newGamePopup);

    JPanel popup = new JPanel();
    popup.setOpaque(false);
    popup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                          BorderFactory.createLineBorder(Color.black,1)),
                                       BorderFactory.createEmptyBorder(10,10,10,10)));
    popup.setLayout(null);

    contentP = new JPanel();
    contentP.setLayout(new BoxLayout(contentP,BoxLayout.Y_AXIS));
    contentP.setBackground(Ggui.darkBackground);
    contentP.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

    JLabel hdr = new JLabel("How do you want to start?");
    hdr.setForeground(Color.white);
    hdr.setFont(Ggui.buttonFont());
    hdr.setAlignmentX(CENTER_ALIGNMENT);
    contentP.add(hdr);
    contentP.add(Box.createVerticalStrut(10));

    newGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.STARTUPNEWGAME,true);
    newGameButt.setAlignmentX(CENTER_ALIGNMENT);
    newGameButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
   //     newGamePopup.setVisible(true);
      }
    });
    contentP.add(newGameButt);
    contentP.add(Box.createVerticalStrut(10));

    loadGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.STARTUPLOADGAME,true);
    loadGameButt.setAlignmentX(CENTER_ALIGNMENT);
    contentP.add(loadGameButt);

    Dimension d = contentP.getPreferredSize();
    contentP.setSize(d);
    contentP.setLocation(10,10);
    popup.add(contentP);
    popup.setSize(d.width+20,d.height+20);
    popup.setLocation((640-d.width-20)/2,(480-d.height-20)/2);

    add(popup);

  //  JLabel backLabel = new JLabel(Ggui.imgIconGet("STARTUPNEWBACK"));
  //  backLabel.setBounds(new Rectangle(0,0,640,480));
  //  add(backLabel);
    setVisible(false);
  }
  private JPanel makeNewPopup()
  {
    final JPanel newPopup = new JPanel();
    newPopup.setOpaque(false);
    newPopup.setBackground(Ggui.transparent); //darkBackground);
    newPopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    newPopup.setLayout(new BoxLayout(newPopup,BoxLayout.Y_AXIS));

    newPopup.add(Box.createVerticalStrut(10));
    JLabel topLab = new JLabel("Start new game with...");
    topLab.setFont(Ggui.bigButtonFont());
    topLab.setAlignmentX(CENTER_ALIGNMENT);
    topLab.setForeground(Color.white);
    newPopup.add(topLab);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILENEWCHAR,true);
    newCharButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newCharButt);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILENEWEXISTING,true);
    newGameButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newGameButt);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newCancelButt = ButtonFactory.make(ButtonFactory.FILENEWCANCEL,mf);
    newCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newCancelButt);
    newPopup.add(Box.createVerticalStrut(10));
    newCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        newPopup.setVisible(false);
      }
    });

    Dimension d = newPopup.getPreferredSize();
    newPopup.setSize(d);
    newPopup.setLocation((640-d.width)/2,(480-d.height)/2);
    newPopup.setVisible(false);
    return newPopup;
  }

  public void go()
  {
    super.go();
    if(mf.gameChosen == true)
      mf.handlers.eventIn(ButtonFactory.STARTUPCOMPLETE);
  }
  public void done()
  {
    super.done();
  }
}
