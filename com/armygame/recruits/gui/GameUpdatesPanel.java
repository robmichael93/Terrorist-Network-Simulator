//
//  GameUpdatesPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Mon Mar 18 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import com.armygame.recruits.services.Updater;

public class GameUpdatesPanel extends RPanel
{
  MainFrame mf;
  JLabel check2,check3,check4,check45,check5,check6,errorLab;
  JButton proceedButt,canButt;
  public static GameUpdatesPanel me;
  GameUpdatesPanel(MainFrame main)
  {
    mf = main;
    me = this;
    setLayout(null);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("UPDATESBACK"));

    JLabel check1 = new JLabel("1. Accessing the internet to find the update server...");
    check1.setFont(Ggui.bigButtonFont());
    check1.setForeground(Ggui.buttonForeground());
    check1.setSize(check1.getPreferredSize());
    check1.setLocation(150,50);
    add(check1);

    check2 = new JLabel("2. Checking for update status...");
    check2.setFont(Ggui.bigButtonFont());
    check2.setForeground(Ggui.buttonForeground());
    check2.setSize(check2.getPreferredSize());
    check2.setLocation(150,100);
    check2.setEnabled(false);
    add(check2);

    check3 = new JLabel("Updating the game will restart Soldiers.  Click Cancel if you");
    check3.setFont(Ggui.bigButtonFont());
    check3.setForeground(Ggui.buttonForeground());
    check3.setSize(check3.getPreferredSize());
    check3.setLocation(100,150);
    check3.setVisible(false);
    add(check3);

    check4 = new JLabel("have not saved your game or do not wish to update now.");
    check4.setFont(Ggui.bigButtonFont());
    check4.setForeground(Ggui.buttonForeground());
    check4.setSize(check4.getPreferredSize());
    check4.setLocation(115,175);
    check4.setVisible(false);
    add(check4);

    check45 = new JLabel("Othersize, click Proceed.");
    check45.setFont(Ggui.bigButtonFont());
    check45.setForeground(Ggui.buttonForeground());
    check45.setSize(check45.getPreferredSize());
    check45.setLocation(225,200);
    check45.setVisible(false);
    add(check45);

    check5 = new JLabel("3. Retrieving the update...");
    check5.setFont(Ggui.bigButtonFont());
    check5.setForeground(Ggui.buttonForeground());
    check5.setSize(check5.getPreferredSize());
    check5.setLocation(150,250);
    check5.setEnabled(false);
    add(check5);

    check6 = new JLabel("4. Restarting soldiers...");
    check6.setFont(Ggui.bigButtonFont());
    check6.setForeground(Ggui.buttonForeground());
    check6.setSize(check6.getPreferredSize());
    check6.setLocation(150,300);
    check6.setEnabled(false);
    add(check6);

    errorLab = new JLabel("");        //
    add(errorLab);

    canButt = ButtonFactory.make(ButtonFactory.UPDATECANCEL,mf);
    add(canButt);

    proceedButt = ButtonFactory.make(ButtonFactory.UPDATEDO,mf);
    proceedButt.setEnabled(false);
    add(proceedButt);

    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }
  public JLabel makeErrorLabel(String s)
  {
    errorLab.setText(s);
    errorLab.setBorder(BorderFactory.createEtchedBorder());
    errorLab.setFont(Ggui.bigButtonFont());
    errorLab.setForeground(Ggui.buttonForeground());
    errorLab.setOpaque(true);
    errorLab.setBackground(Ggui.lightBackground);
    Dimension d = errorLab.getPreferredSize();
    errorLab.setSize(d);
    errorLab.setLocation((640-d.width)/2,400);
    return errorLab;
  }

  public void go()
  {
    mf.setTitleBar("GAMEUPDATETITLE");
    super.go();
    cancelled=false;
    startHerOff();
  }
  boolean cancelled = false;
  public void cancel()
  {
    cancelled=true;
    try{updater.cancel();}catch(Exception e){}
  }
  private void startHerOff()
  {
    new Thread(run1).start();
  }
  Updater updater;
  Runnable run1 = new Runnable()
  {
    public void run()
    {
      try{Thread.sleep(2000);}catch(Exception e){}
      try
      {
        updater = new Updater();
      }
      catch(Exception e)
      {
        // Handle error case
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            GameUpdatesPanel.this.errorLab = GameUpdatesPanel.this.makeErrorLabel("Error connecting to server.  Click Cancel.");
            GameUpdatesPanel.this.proceedButt.setEnabled(false);
            GameUpdatesPanel.this.canButt.setEnabled(true);
            MainFrame.playDefaultQuip(true);
          }
        });
        return;
      }
      if(cancelled)
        return;
      check2.setEnabled(true);
      try{Thread.sleep(1000);}catch(Exception e){}
      int size=0;
      try
      {
        size = updater.getSize();
      }
      catch(Exception e)
      {
        // Handle error case
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            GameUpdatesPanel.this.errorLab = GameUpdatesPanel.this.makeErrorLabel("Error retrieving update.  Click Cancel.");
            GameUpdatesPanel.this.proceedButt.setEnabled(false);
            GameUpdatesPanel.this.canButt.setEnabled(true);
            MainFrame.playDefaultQuip(true);
          }
        });
        return;
      }
      if(cancelled)
        return;

System.out.println("updater size:"+size);
//System.out.println(updater.getClientVersion());
//System.out.println(updater.getServerVersion());
//System.out.println(updater.getUpdateDescription());

      if (size <= 0)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            GameUpdatesPanel.this.errorLab = GameUpdatesPanel.this.makeErrorLabel("Your game is up-to-date.  Click Cancel.");
            GameUpdatesPanel.this.proceedButt.setEnabled(false);
            GameUpdatesPanel.this.canButt.setEnabled(true);
          }
        });
      }
      if(cancelled)
        return;
      check3.setVisible(true);
      check4.setVisible(true);
      check45.setVisible(true);
      proceedButt.setEnabled(true);
    }
  };

  public void doUpdate()
  {
    new Thread(run2).start();
  }

  Runnable run2 = new Runnable()
  {
    public void run()
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          check5.setEnabled(true);
        }
      });
      try
      {
        updater.start();
      }
      catch(Exception e)
      {
        // Handle error case
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            GameUpdatesPanel.this.errorLab = GameUpdatesPanel.this.makeErrorLabel("Error running updater.  Click Cancel.");
            GameUpdatesPanel.this.proceedButt.setEnabled(false);
            GameUpdatesPanel.this.canButt.setEnabled(true);
            MainFrame.playDefaultQuip(true);
          }
        });
        return;
      }
      try
      {
        while (true)
        {
          if(cancelled)
          {
            return;
          }
          int remaining = updater.getSize();
          System.out.println(remaining);
          if (remaining <= 0)
          {
            SwingUtilities.invokeLater(new Runnable()
            {
              public void run()
              {
                check6.setEnabled(true);
              }
            });
            Thread.sleep(3000);
            Runtime.getRuntime().exec("soldiers.exe");
            System.exit(0);
            break;
          }
          Thread.sleep(500);
        }
      }
      catch (Exception e)
      {
        // Handle error case
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            GameUpdatesPanel.this.errorLab = GameUpdatesPanel.this.makeErrorLabel("Error restarting Soldiers.  Click Cancel.");
            GameUpdatesPanel.this.proceedButt.setEnabled(false);
            GameUpdatesPanel.this.canButt.setEnabled(true);
            MainFrame.playDefaultQuip(true);
          }
        });
      } // catch()
    } // run()
  }; // runnable()
}
  