/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 12, 2002
 * Time: 2:57:31 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;
import com.armygame.recruits.globals.SavedGame;
import com.armygame.recruits.globals.SavedCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class OpsPanel extends RPanel {

	public OpsPanel(MainFrame mf) { 
    //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
    setLayout(null);
		add(new GuiLabel(100, 100, 
			"Transferring your game progress to the Operations server"));
		add(ButtonFactory.make(ButtonFactory.FILECANCEL,mf));
    setVisible(false);
	}

}
