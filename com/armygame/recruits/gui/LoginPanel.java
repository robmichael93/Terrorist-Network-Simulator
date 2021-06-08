//
//  NewUserPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Fri Mar 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class LoginPanel extends RPanel
{
  MainFrame mf;
  public JTextField idTF;
  public JPasswordField pwTF;
  JButton submitButt;
  public String getLoginName()
  {
    return idTF.getText();
  }
  public String getPassword()
  {
    return new String(pwTF.getPassword());
  }
  LoginPanel(MainFrame main)
  {
     mf = main;
     setLayout(null);

     JPanel p = new JPanel();

     p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
     
     JPanel topP = new JPanel();
     topP.setLayout(new BorderLayout());
     
     JPanel leftP   = new JPanel();
     Color defBgd = leftP.getBackground(); 
 
     leftP.setBorder(BorderFactory.createEtchedBorder()); //BorderFactory.createRaisedBevelBorder());     
     leftP.setLayout(new BoxLayout(leftP,BoxLayout.Y_AXIS));
     
     leftP.add(Box.createRigidArea(new Dimension(0,30)));
       JLabel labL1 = new JLabel("New to Recruits?");
       labL1.setAlignmentX(CENTER_ALIGNMENT);
     leftP.add(labL1);
     leftP.add(Box.createRigidArea(new Dimension(0,30)));
       JButton buL = ButtonFactory.makeTextButt(ButtonFactory.LOGINNEWUSER);
       buL.setAlignmentX(CENTER_ALIGNMENT);
     leftP.add(buL);
     leftP.add(Box.createRigidArea(new Dimension(0,10)));
     
       JTextArea taL = new JTextArea(
       "Registering will allow you to contact a Recruits game server through the Internet.  There, you can upload and download characters and stories to use in your Recruits session. "+
       "Additionally, you will have the opportunity to update your Recruits software when new features become available.");
       taL.setBackground(defBgd);
       taL.setLineWrap(true);
       taL.setWrapStyleWord(true);
       taL.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
       taL.setAlignmentX(CENTER_ALIGNMENT);
     leftP.add(taL);
     leftP.setPreferredSize(new Dimension(320,400));
     
     topP.add(leftP,BorderLayout.WEST);
     
     JPanel rightP = new JPanel();
     rightP.setBorder(BorderFactory.createEtchedBorder()); //BorderFactory.createRaisedBevelBorder()); 
     rightP.setLayout(new BoxLayout(rightP,BoxLayout.Y_AXIS));
     
     rightP.add(Box.createRigidArea(new Dimension(0,30)));
       JLabel labR1 = new JLabel("Registered Recruits players");
       labR1.setAlignmentX(CENTER_ALIGNMENT);
     rightP.add(labR1);
     rightP.add(Box.createRigidArea(new Dimension(0,30)));

     NonEmptyVerifier nev = new NonEmptyVerifier();
     
       JPanel idPanel = new JPanel();
       idPanel.setLayout(new BoxLayout(idPanel,BoxLayout.X_AXIS));
       idPanel.setAlignmentX(CENTER_ALIGNMENT);
       idPanel.add(Box.createRigidArea(new Dimension(20,0)));
         JLabel idLabel = new JLabel();
         idLabel.setText("My user handle");
         idLabel.setAlignmentY(CENTER_ALIGNMENT);
         idLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
         idLabel.setPreferredSize(new Dimension(120,21));
         idLabel.setMaximumSize(idLabel.getPreferredSize());
       idPanel.add(idLabel);
         idTF = new JTextField();
         idTF.setPreferredSize(new Dimension(155, 21));
         idTF.setMaximumSize(idTF.getPreferredSize());
         idTF.setAlignmentY(CENTER_ALIGNMENT); 
         //idTF.setInputVerifier(nev);
         idTF.getDocument().addDocumentListener(nev);
       idPanel.add(idTF);
       idPanel.add(Box.createHorizontalGlue());
     rightP.add(idPanel);
     
       JPanel pwPanel = new JPanel();
       pwPanel.setLayout(new BoxLayout(pwPanel,BoxLayout.X_AXIS));
       pwPanel.setAlignmentX(CENTER_ALIGNMENT);
       pwPanel.add(Box.createRigidArea(new Dimension(20,0)));
         JLabel pwLabel = new JLabel();
         pwLabel.setText("My password");
         pwLabel.setAlignmentY(CENTER_ALIGNMENT);
         pwLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
         pwLabel.setPreferredSize(new Dimension(120,21));
         pwLabel.setMaximumSize(pwLabel.getPreferredSize());
       pwPanel.add(pwLabel);
         pwTF = new JPasswordField();
         pwTF.setPreferredSize(new Dimension(155, 21));
         pwTF.setMaximumSize(pwTF.getPreferredSize());
         pwTF.setAlignmentY(CENTER_ALIGNMENT);
         //pwTF.setInputVerifier(nev);
         pwTF.getDocument().addDocumentListener(nev);
       pwPanel.add(pwTF);
       pwPanel.add(Box.createHorizontalGlue());
     rightP.add(pwPanel);
     rightP.add(Box.createRigidArea(new Dimension(0,30)));
       submitButt  = ButtonFactory.make(ButtonFactory.LOGINSUBMITTED,mf);
       submitButt.setAlignmentX(CENTER_ALIGNMENT);
       submitButt.setEnabled(false);
     rightP.add(submitButt);
     
     Dimension minD = new Dimension(1,20);
     Dimension prefD= new Dimension(1,20);
     Dimension maxD= new Dimension(1,Short.MAX_VALUE);
     rightP.add(new Box.Filler(minD,prefD,maxD));

     rightP.setPreferredSize(new Dimension(320,400));
     
     topP.add(rightP,BorderLayout.EAST);
     p.add(topP);
     
     JButton canButt = ButtonFactory.make(ButtonFactory.LOGINCANCEL,mf);
     canButt.setAlignmentX(CENTER_ALIGNMENT);
     p.add(canButt);
     p.setLocation(0,0);
     p.setSize(p.getPreferredSize());
     add(p);

     JLabel backLabel = new JLabel(Ggui.imgIconGet("LOGINBACK"));
     backLabel.setBounds(new Rectangle(0,0,640,480));
     add(backLabel);

     setVisible(false);
  }
  void adjustSubmitButt()
  {
    String pwText = new String(pwTF.getPassword());
    if((pwText.length() == 0) || (idTF.getText().length() == 0))
      submitButt.setEnabled(false);
    else
      submitButt.setEnabled(true);
  }
  
  class NonEmptyVerifier implements DocumentListener
  {
    public void changedUpdate(DocumentEvent e)
    {
        adjustSubmitButt();
    }
      public void insertUpdate(DocumentEvent e)
    {
        adjustSubmitButt();
    }
      public void removeUpdate(DocumentEvent e)
    {
        adjustSubmitButt();
    }
  }
}
