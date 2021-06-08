//
//  HistoryPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends RPanel
{
  MainFrame mf;
  HistoryPanel(MainFrame main)
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
/*
    JEditorPane jep = new JEditorPane();
    jep.setContentType("text/html");
    jep.setEditable(false);
    jep.setBackground(new Color(0.75f,0.75f,0.75f));
    jep.setText("<center><table border='3' width='610'><tr><th>&nbsp;</th><th>description</th><th>Blah blah</th></tr>"+
    "<tr><td>1</td><td>Now is the time</td><td>and blah blah blah</td></tr>"+
    "<tr><td>2</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>3</td><td>First</td><td>Second</td></tr>"+
    "<tr><td>4</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>5</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>6</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>7</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>8</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>9</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>10</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>11</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>12</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "<tr><td>13</td><td> Second item Second item Second item Second item Second item Second item Second item Second item.</td><td>&nbsp;</td></tr>"+
    "</table>");
    jep.setPreferredSize(new Dimension(630,200));
    JScrollPane jsp = new JScrollPane(jep);
    
    //jsp.setBorder(null);
    jsp.setPreferredSize(new Dimension(640,420));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setLocation(0,0);

    add(jsp);
 */
    
    JButton stationsButt = ButtonFactory.make(ButtonFactory.HISTORYSTATIONS,mf);
    add(stationsButt);

    JButton achievementsButt = ButtonFactory.make(ButtonFactory.HISTORYACHIEVEMENTS,mf);
    add(achievementsButt);

    JButton closeButt = ButtonFactory.make(ButtonFactory.HISTORYCLOSE,mf);
    add(closeButt);
    
    JLabel backLabel = new JLabel(Ggui.imgIconGet("HISTORYBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    
    setVisible(false);
  }

  public void go()
  {
    super.go();
    mf.setTitleBar("HISTORYTITLE");
  }

}
