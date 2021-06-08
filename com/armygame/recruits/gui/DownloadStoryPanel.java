/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 25, 2002
 * Time: 11:00:06 AM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class DownloadStoryPanel extends RPanel
{
  MainFrame mf;
  DownloadStoryPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Color.pink);
    add(ButtonFactory.make(ButtonFactory.DOWNLOADSTORYCANCEL,mf));
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("DOWNLOADSTORYTITLE");
  }
}
