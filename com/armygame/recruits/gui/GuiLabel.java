//
//  Rpanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Wed Apr 03 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;

public class GuiLabel extends JLabel {
	
	public GuiLabel(String s) {
		super(s);   
		setFont(Ggui.bigButtonFont());
    setForeground(Ggui.buttonForeground());
   	setSize(getPreferredSize());
	}
	
	public GuiLabel(int x, int y, String s) {
		this(s);
		setLocation(x, y);
	}
}
