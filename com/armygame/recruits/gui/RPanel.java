//
//  Rpanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Wed Apr 03 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;

public class RPanel extends JPanel
{
  public void go()
  {
     setVisible(true);
  }
  // The below comments were for go().  They're wrong.
  // We're called each time we're made visible.
  // The subclasses can override if they want different behavior.

  // when panel is first to be made visible
    // note: this is NOT called each time the panel appears...if it is "popped"
    // from a sub-panel, it is not.  If it is brought up from the main menu
    // it is.
  public void done()
  {
  }

  public void cancel(){}	// when user has hit cancel button on panel,
    // will come right before done()
}
