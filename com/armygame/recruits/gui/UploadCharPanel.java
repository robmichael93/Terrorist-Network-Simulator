/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 25, 2002
 * Time: 10:59:03 AM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class UploadCharPanel extends RPanel
{
  MainFrame mf;
  UploadCharPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(Color.cyan);
    add(ButtonFactory.make(ButtonFactory.UPLOADCHARCANCEL,mf));
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("UPLOADCHARTITLE");
  }
}
