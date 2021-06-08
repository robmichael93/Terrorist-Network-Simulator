/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 16, 2002
 * Time: 11:58:29 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.armygame.recruits.storyelements.sceneelements.Goal;

public class CannedHelpPanel  extends RPanel
{
  MainFrame mf;
  ConnectedGoal cgoal;
  JPanel enclosingPanel;
  Insets borders;
  JEditorPane contentJLabel;
  JButton closeButt;
  TitledBorder titledBorder;


  String htmlA = "<html><center><b>";
  String htmlB = "</b></center><br>";
  String htmlC = "</html>";

  CannedHelpPanel(MainFrame main)
  {
    mf = main;
    setBackground(Ggui.darkBackground);
    setLayout(null);

    enclosingPanel = new JPanel();
    enclosingPanel.setLayout(new BoxLayout(enclosingPanel,BoxLayout.Y_AXIS));
    enclosingPanel.setBackground(Ggui.mediumBackground);
    titledBorder = BorderFactory.createTitledBorder(
                              BorderFactory.createRaisedBevelBorder(),
                              "Character description");
    titledBorder.setTitleColor(new Color(247,214,0));
    enclosingPanel.setBorder(titledBorder);
    borders = enclosingPanel.getInsets();

    //JLabel iconLab = new JLabel(MainFrame.imgIconGet("GOALHELPICONDEFAULT"));
    //iconLab.setAlignmentX(CENTER_ALIGNMENT);
    //enclosingPanel.add(iconLab);

    //enclosingPanel.add(Box.createVerticalStrut(10));

    contentJLabel = new JEditorPane();
    contentJLabel.setContentType("text/html");
    contentJLabel.setEditable(false);
    contentJLabel.setFont(new Font("Arial",Font.PLAIN,24));
    contentJLabel.setAlignmentX(CENTER_ALIGNMENT);
    contentJLabel.setBorder(BorderFactory.createLineBorder(Color.black,2));
    contentJLabel.setBackground(new Color(130,130,130)); //Color.white);
    contentJLabel.setOpaque(true);
    enclosingPanel.add(contentJLabel);

    closeButt = ButtonFactory.make(ButtonFactory.CANNEDHELPCLOSE,mf);
    closeButt.setAlignmentX(CENTER_ALIGNMENT);

    enclosingPanel.add(closeButt);
    enclosingPanel.setSize(enclosingPanel.getPreferredSize());
    enclosingPanel.setSize(enclosingPanel.getWidth()+20,enclosingPanel.getHeight()+20); // fudge
    enclosingPanel.setLocation(centerMe());
    add(enclosingPanel);

    setVisible(false);
  }
  public void setData(Object d)
  {
    String s = (String) d;
    StringBuffer sb = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s,"\t");
    sb.append(htmlA);
    String name = st.nextToken();
    sb.append(name);
    titledBorder.setTitle(name);
    sb.append(htmlB);
    sb.append(st.nextToken());
    sb.append(htmlC);

    contentJLabel.setText(sb.toString());
    Dimension dim = new Dimension(300,300);
    contentJLabel.setMaximumSize(dim);
    enclosingPanel.validate();
    enclosingPanel.setSize(enclosingPanel.getPreferredSize());
    enclosingPanel.setLocation(centerMe());
  }
   private Point centerMe()
  {
    int x = (640-enclosingPanel.getWidth())/2;
    int y = (480-enclosingPanel.getHeight())/2;
    return new Point(x,y);
  }
}
