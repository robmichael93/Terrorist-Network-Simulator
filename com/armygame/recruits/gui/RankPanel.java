/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 14, 2002
 * Time: 4:58:23 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class RankPanel extends JPanel
{

  MainFrame mf;
  JLabel bigRank;
  JLabel smallRank;

  RankProgressBar bar;
  RankPanel(MainFrame main)
  {
    mf = main;

    setLayout(null);
    setBorder(null);
    setBackground(Ggui.transparent);

    // Trying to make the progress bar show up initially on Windows
    bar = new RankProgressBar(null,Ggui.imgIconGet("RANKBAR").getImage());
    bar.setBounds(9,77,80,6);
    add(bar);

    bigRank = new JLabel(Ggui.imgIconGet("RANKE3"));
    bigRank.setSize(bigRank.getPreferredSize());
    bigRank.setLocation(20,2);
    add(bigRank);

   // smallRank = new JLabel(Ggui.imgIconGet("RANKE4SMALL"));
   // smallRank.setSize(smallRank.getPreferredSize());
  //  smallRank.setLocation(66,60);

    //add(smallRank);


    setSize(97,89);
    setVisible(true);
    setCurrentRank(1);
    //setNextRank(2);

    // The bar is not initially painted under windows...try this...
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run(){setProgress(0);}
    });
  }

  public void setCurrentRank(int r)
  {
    bigRank.setIcon(Ggui.imgIconGet("RANKE"+r));
    bigRank.setSize(bigRank.getPreferredSize());
  }
  public void setCurrentRank(String s)
  {
    setCurrentRank(toDigit(s));
  }
  public void setNextRank(int r)
  {
  //  smallRank.setIcon(Ggui.imgIconGet("RANKE"+r+"SMALL"));
  }
  public void setNextRank(String s)
  {
    setNextRank(toDigit(s));
  }
  public void setProgress(int p)
  {
    bar.setProgress(p);
  }
  private int toDigit(String s)
  {
    for(int i=0;i<10;i++)
     if(s.indexOf(""+i) != -1)
       return i;
    return 0;
  }

}
