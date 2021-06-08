//
//  AchievementPanel.java
//  projectbuilder5
//
//  Created by Mike Bailey on Fri Apr 12 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//

package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class AchievementPanel extends RPanel
{
  MainFrame mf;
  AchievementPanel(MainFrame main)
  {
     mf = main;

    setLayout(null);
// test
setBackground(Color.red);
/*
    String[] listdata = {"First history item.",
    "Second item Second item Second item Second item Second item Second item Second item Second item.",
    "<b>Third</b>"
    };

    JList jl = new JList(listdata);
    jl.setPreferredSize(new Dimension(630,200));
    JScrollPane jsp = new JScrollPane(jl);
*/

    JButton closeButt = ButtonFactory.make(ButtonFactory.ACHIEVEMENTSCLOSE,mf);
    add(closeButt);
    JButton stationsButt = ButtonFactory.make(ButtonFactory.HISTORYSTATIONS,mf);
    add(stationsButt);

    JEditorPane jep = new JEditorPane();
    jep.setContentType("text/html");
    jep.setEditable(false);
    jep.setBackground(new Color(255,247,209));


    jep.setText("<html><body><center><table border='3' width='400'><tr><th>&nbsp;</th><th><font face='Arial'>Date</th><th><font face='Arial'>Description</th></tr>"+
    "<tr><td><font face='Arial'>1</td><td><font face='Arial'>19 DEC 01</td><td><font face='Arial'>National Defense Medal</td></tr>"+
    "<tr><td><font face='Arial'>2</td><td><font face='Arial'>14 JAN 02</td><td><font face='Arial'>AIT: Certificate of Achievement</td></tr>"+
    "<tr><td><font face='Arial'>3</td><td><font face='Arial'>21 JUL 02</td><td><font face='Arial'>Army Achievement Medal</td></tr>"+
     "</table></center></body></html>");
    jep.setPreferredSize(new Dimension(480,140));
    jep.setBorder(null);
    JScrollPane jsp = new JScrollPane(jep);

    jsp.setBorder(null);
    jsp.setPreferredSize(new Dimension(480,140));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setLocation(38,240);

    add(jsp);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("ACHIEVEMENTSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

}
