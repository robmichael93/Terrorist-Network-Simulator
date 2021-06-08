/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 25, 2002
 * Time: 10:59:50 AM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class DownloadCharPanel extends RPanel
{
  MainFrame mf;
  DownloadCharPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Color.red);
    add(ButtonFactory.make(ButtonFactory.DOWNLOADCHARCANCEL,mf));
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("DOWNLOADCHARTITLE");
  }
}
