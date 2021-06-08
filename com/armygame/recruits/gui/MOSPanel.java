/*
* Created by IntelliJ IDEA.
* User: mike
* Date: May 17, 2002
* Time: 4:28:06 PM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class MOSPanel extends RPanel
{
  MainFrame mf;
  ConnectedGoal cgoal;
  Insets borders;
  JEditorPane content;
  JButton closeButt;

  String mosTitle="MOS Title";
  String mosContent="<html><body>dummy</body></html>";
  JScrollPane jsp;
  JLabel titleLab=new JLabel("");
  JPanel myPanel;
  MOSPanel(MainFrame main)
  {
    mf=main;
    //setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    //setBackground(Ggui.mediumBackground);

    setLayout(null);

    myPanel = new JPanel();
    myPanel.setOpaque(false);
    myPanel.setBackground(Ggui.transparent); //darkBackground);   // for shadow border
    myPanel.setBounds(0,0,640,460);  // space for close
    myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.Y_AXIS));

    content=new JEditorPane();
    makeit("<html><body>dummy</body></html>",myPanel);
    add(myPanel);

    closeButt=ButtonFactory.make(ButtonFactory.MOSCLOSE,mf);
    add(closeButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("MOSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  boolean made=false;

  private void makeit(String s, Container c)
  {
    if(!made)
    {
      //removeAll();                      a

      //c.add(Box.createVerticalGlue());
      c.add(Box.createVerticalStrut(40));
      //titleLab.setText(mosTitle);
      //titleLab.setForeground(Ggui.buttonForeground());
      //titleLab.setFont(Ggui.bigButtonFont());
      //titleLab.setAlignmentX(JComponent.CENTER_ALIGNMENT);
      //c.add(titleLab);

      //c.add(Box.createVerticalStrut(5));
      jsp=new JScrollPane(content);
    }

    makeContent(s);
    if(!made)
    {
      made=true;
      jsp.setBorder(new ShadowBorder(8));
      jsp.setOpaque(false);
      jsp.setPreferredSize(new Dimension(450,400)); //350));
      jsp.setMaximumSize(jsp.getPreferredSize());
      jsp.setAlignmentX(JComponent.CENTER_ALIGNMENT);
      c.add(jsp);


      //c.add(Box.createVerticalStrut(10));

      //closeButt=ButtonFactory.make(ButtonFactory.MOSCLOSE,mf);
      //closeButt.setAlignmentX(CENTER_ALIGNMENT);
      //add(closeButt);

      add(Box.createVerticalGlue());
    }

    // Can't figure out how else to make the page start out at the top
    Timer tim=new Timer(400,new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
      }
    });
    tim.setRepeats(false);
    tim.start();
  }

  private void makeContent(String s)
  {
    if(!made)
    {
      content.setContentType("text/html");
      content.setEditable(false);
      content.setFont(new Font("Arial",Font.PLAIN,24));
      content.setAlignmentX(CENTER_ALIGNMENT);
      content.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); //createLineBorder(Color.red,2));
      content.setBackground(Ggui.lightlightBackground);
    }
    content.setText(s);
  }

  public void go()
  {
    titleLab.setText(mosTitle);
    makeit(this.mosContent,myPanel);

    super.go();
  }

  public void setData(String d)
  {
    StringTokenizer st=new StringTokenizer(d,"\t");
    mosTitle=st.nextToken();
    mosContent="<html><body><font face='Arial'>"+st.nextToken()+"</font></body></html>";
  }
}
