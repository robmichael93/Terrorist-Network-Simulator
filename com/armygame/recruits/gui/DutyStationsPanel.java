//
//  DutyStationsPanel.java
//
//  Created by Mike Bailey on Fri Apr 12 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//

package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;

public class DutyStationsPanel extends RPanel
{
  MainFrame mf;
  DutyStationsPanel(MainFrame main)
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

    JButton closeButt = ButtonFactory.make(ButtonFactory.STATIONSCLOSE,mf);
    add(closeButt);

    JButton achievementsButt = ButtonFactory.make(ButtonFactory.HISTORYACHIEVEMENTS,mf);
    add(achievementsButt);

    JEditorPane jep = new JEditorPane();
    jep.setContentType("text/html");
    jep.setEditable(false);
    jep.setBackground(new Color(255,247,209));
    jep.setFont(new Font("Arial",Font.PLAIN,8));

    jep.setText("<html><body><center><table border='3' width='450'><font size='4'><tr>"+
    "<th><font size='1' face='Arial'    >Post</font></th>"+
    "<th><font size='1' face='Arial'    >Time period</font></th>"+
    "<th><font size='1' face='Arial'    >Unit</font></th>"+
    "<th><font size='1' face='Arial'    >Description of duties</font></th></tr>"+
    "<tr><td><font size='1' face='Arial'>Fort Benning, GA</font></td>"+
    "<td><font size='1' face='Arial'    >3 OCT 2001 - 20 DEC 2001</font></td>"+
    "<td>&nbsp;</td>"+
    "<td><font size='1' face='Arial'    >Basic Combat Training</font></td></tr>"+
    "<tr><td><font size='1' face='Arial'>Fort Sill, OK</font></td>"+
    "<td><font size='1' face='Arial'    >3 JAN 2002 - 25 MAR 2002</font></td>"+
    "<td>&nbsp;</td>"+
    "<td><font size='1' face='Arial'    >Advanced Individual Training (13E30 - Artillery Fire Direction Control</font></td></tr>"+
    "<tr><td><font size='1' face='Arial'>Camp Stanley, ROK</font></td>"+
    "<td><font size='1' face='Arial'    >1 APR 2002 - 31 MAY 2003</font></td>"+
    "<td><font size='1' face='Arial'    >Co. E, 11th BN (FA), 2nd Inf. Div.</font></td>"+
    "<td><font size='1' face='Arial'    >Deployment</font></td></tr>"+
    "<tr><td><font size='1' face='Arial'>Fort Campbell, KY</font></td>"+
    "<td><font size='1' face='Arial'    >3 JUN 2003 - 2 OCT 2003</font></td>"+
    "<td><font size='1' face='Arial'    >Co. A, 13th BN (FA), 101st Airmobile Div.</font></td>"+
    "<td><font size='1' face='Arial'    >Deployment</font></td></tr>"+
    "</table></center></body></html>");


    jep.setPreferredSize(new Dimension(480,220));
    jep.setBorder(null);
    JScrollPane jsp = new JScrollPane(jep);

    jsp.setBorder(null);
    jsp.setPreferredSize(new Dimension(480,220));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setLocation(38,160);

    add(jsp);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("STATIONSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

}
