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

public class UploadStoryPanel extends RPanel
{
  MainFrame mf;
  UploadStoryPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Color.blue);
    add(ButtonFactory.make(ButtonFactory.UPLOADSTORYCANCEL,mf));
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("UPLOADSTORYTITLE");
  }
}
