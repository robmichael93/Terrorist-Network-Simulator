/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 13, 2002
 * Time: 3:49:22 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MessagePanel extends RPanel implements ActionListener
{
  MainFrame mf;
  ArrayList q = new ArrayList(3);
  //JLabel text;
  JEditorPane text;
  JButton nextMessButt, closeButt;
  String[] priorLabels = new String[3];
  JPanel enclosingPanel;
  TitledBorder titledBorder;
  Insets borders;
  JLabel priorityLabel;

  MessagePanel(MainFrame main)
  {
    mf = main;

    priorLabels[AlertMessage.HIGH_PRIORITY] = "Immediate";
    priorLabels[AlertMessage.MEDIUM_PRIORITY] = "Priority";
    priorLabels[AlertMessage.LOW_PRIORITY] = "Routine";

    setLayout(null);
    setBackground(Ggui.darkBackground);
    enclosingPanel = new JPanel();
    enclosingPanel.setLayout(new BoxLayout(enclosingPanel,BoxLayout.Y_AXIS));
    enclosingPanel.setBackground(new Color(130,130,130));

    titledBorder = BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),"Message");
    titledBorder.setTitleColor(new Color(247,214,0));
    enclosingPanel.setBorder(titledBorder);
    borders = enclosingPanel.getInsets();

    JLabel iconLab = new JLabel(Ggui.imgIconGet("MESSAGEICONDEFAULT"));
    iconLab.setAlignmentX(CENTER_ALIGNMENT);
    enclosingPanel.add(iconLab);

    enclosingPanel.add(Box.createVerticalStrut(10));

    priorityLabel = new JLabel("Priority Message");
    priorityLabel.setFont(new Font("Arial",Font.BOLD,16));
    priorityLabel.setAlignmentX(CENTER_ALIGNMENT);
    priorityLabel.setBorder(BorderFactory.createCompoundBorder
                            (BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(4,4,4,4)));
    //priorityLabel.setBorder(BorderFactory.createLineBorder(Color.black));
    enclosingPanel.add(priorityLabel);

    enclosingPanel.add(Box.createVerticalStrut(10));

    //text = new JLabel("message text label");
    text = new JEditorPane();
    text.setContentType("text/html");
    text.setEditable(false);
    //text.setLocation(20,250);
    text.setBackground(new Color(130,130,130));
    text.setAlignmentX(CENTER_ALIGNMENT);
    text.setBorder(BorderFactory.createCompoundBorder
                            (BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(4,4,4,4)));
    enclosingPanel.add(text);

    enclosingPanel.add(Box.createVerticalStrut(10));
    closeButt = ButtonFactory.make(ButtonFactory.MESSAGESHOWINGCLOSE,mf);
    closeButt.setAlignmentX(CENTER_ALIGNMENT);
    enclosingPanel.add(closeButt);

    nextMessButt = ButtonFactory.make(ButtonFactory.MESSAGESHOWINGNEXT,mf);
    nextMessButt.setAlignmentX(CENTER_ALIGNMENT);

   // enclosingPanel.add(nextMessButt);
    nextMessButt.addActionListener(this);
    nextMessButt.setEnabled(false);

    sizeMe();
    centerMe();

    add(enclosingPanel);
    //JLabel backLabel = new JLabel(Ggui.imgIconGet("MAPBACK"));
    //backLabel.setBounds(new Rectangle(0,0,640,480));
    //add(backLabel);
    setVisible(false);
  }

  public void inComingMessage(AlertMessage am)
  {
    q.add(am);
  }

  public void go()
  {
    super.go();
    if (q.isEmpty())
    {
      text.setText("Message: zilch");
      priorityLabel.setText("Priority: zilch");
      remove(nextMessButt);
    }
    else
    {
      AlertMessage am = (AlertMessage)q.remove(0);
      text.setText("<html><b>Message</b>: "+am.getText() + "</html>");
      Dimension d = text.getPreferredSize();
      priorityLabel.setText("Urgency: "+ priorLabels[am.getPriority()]);
      if(q.isEmpty())
      {
        if(nextMessButt.isEnabled())
        {enclosingPanel.remove(nextMessButt); nextMessButt.setEnabled(false);}
      }
      else
      {
        if(nextMessButt.isEnabled()==false)
        {enclosingPanel.add(nextMessButt);nextMessButt.setEnabled(true);}
      }
    }
    sizeMe();
    centerMe();
  }

  public void actionPerformed(ActionEvent ev)
  {
    go();
  }
  private void sizeMe()
  {
    enclosingPanel.setSize(enclosingPanel.getPreferredSize());
    enclosingPanel.setSize(enclosingPanel.getWidth()+20,enclosingPanel.getHeight()+20);
  }
  private void centerMe()
  {
    int x = (640-enclosingPanel.getWidth())/2;
    int y = (480-enclosingPanel.getHeight())/2;
    enclosingPanel.setLocation(x,y);
  }
  public boolean hasMessage()
  {
    return (!q.isEmpty());
  }
}
