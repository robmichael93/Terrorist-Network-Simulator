/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jul 11, 2002
 * Time: 11:03:43 AM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CloseDialog extends JDialog
{
  CloseDialog(JFrame parent)
  {
    super(parent,"Quit Soldiers?",true);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    Container c = getContentPane();
    c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
    c.setBackground(Ggui.darkBackground);

    c.add(Box.createVerticalStrut(20));

    JLabel q = new JLabel("Are you sure you want to quit Soldiers"); // without saving your game?");
    q.setFont(Ggui.buttonFont());
    q.setForeground(Ggui.buttonForeground());
    q.setAlignmentX(CENTER_ALIGNMENT);
    c.add(q);

    q = new JLabel("without saving your game?");
    q.setFont(Ggui.buttonFont());
    q.setForeground(Ggui.buttonForeground());
    q.setAlignmentX(CENTER_ALIGNMENT);
    c.add(q);

    c.add(Box.createVerticalStrut(10));

    JPanel bPan = new JPanel();
    bPan.setBackground(Ggui.darkBackground);

    bPan.setLayout(new BoxLayout(bPan,BoxLayout.X_AXIS));

    bPan.add(Box.createHorizontalGlue());
    bPan.add(Box.createHorizontalStrut(10));
    JButton y = ButtonFactory.make(ButtonFactory.CLOSEDIALOG_YES,MainFrame.me);
    y.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });
    bPan.add(y);
    bPan.add(Box.createHorizontalStrut(30));
    JButton n = ButtonFactory.make(ButtonFactory.CLOSEDIALOG_NO,MainFrame.me);
    n.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        CloseDialog.this.setVisible(false);
      }
    });
    bPan.add(n);
    bPan.add(Box.createHorizontalStrut(10));
    bPan.add(Box.createHorizontalGlue());

    c.add(bPan);
    c.add(Box.createVerticalStrut(20));

    this.pack();
    int x = parent.getX()+parent.getWidth()/2 - this.getWidth()/2;
    int yy = parent.getY()+parent.getHeight()/2 - this.getHeight()/2;
    this.setLocation(x,yy);
  }
}
