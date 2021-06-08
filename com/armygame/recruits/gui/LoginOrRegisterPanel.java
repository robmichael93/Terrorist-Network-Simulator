/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 19, 2002
 * Time: 8:57:30 AM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LoginOrRegisterPanel extends RPanel
{
  MainFrame mf;
  JPasswordField passwordTF = new JPasswordField(18);
  JTextField handleTF = new JTextField(18);
  JButton regButt, loginButt, cancelButt;

  public LoginOrRegisterPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);

    JPanel popup = new JPanel();
    popup.setLayout(null);
    popup.setBackground(Ggui.darkBackground);

    JLabel popupBack = new JLabel(Ggui.imgIconGet("SUBWINDOWWIDEBACK"));
    Dimension d = popupBack.getPreferredSize();

    JPanel content = new JPanel();
    content.setBackground(Ggui.transparent);
    content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
    //content.add(Box.createRigidArea(new Dimension(d.width,1)));
    JLabel lab = new JLabel("LOGIN");
    lab.setFont(new Font("Arial",Font.BOLD,18));
    lab.setForeground(Color.white);
    lab.setAlignmentX(LEFT_ALIGNMENT);
    content.add(lab);
    content.add(Box.createVerticalStrut(15));
    lab = new JLabel("You must be a registered player to LOGIN.");
    lab.setFont(new Font("Arial",Font.PLAIN,13));
    lab.setForeground(Color.white);
    lab.setAlignmentX(LEFT_ALIGNMENT);
    content.add(lab);
    content.add(Box.createVerticalStrut(20));

    JComponent c = makeLittlePanel("Game Handle",handleTF);
    content.add(c);
    content.add(Box.createVerticalStrut(15));
    content.add(makeLittlePanel("Password",passwordTF));

    content.setLocation(15,10);
    content.setSize(content.getPreferredSize());
    popup.add(content);

    regButt = ButtonFactory.make(ButtonFactory.LOGINREG_REGISTER,mf);
    popup.add(regButt);
    loginButt = ButtonFactory.make(ButtonFactory.LOGINREG_LOGIN,mf);
    loginButt.setEnabled(false);
    popup.add(loginButt);
    cancelButt = ButtonFactory.make(ButtonFactory.LOGINREG_CANCEL,mf);
    popup.add(cancelButt);

    d.width += 5+5;
    d.height += 5+5;
    popupBack.setSize(d);
    popupBack.setBorder(new ShadowBorder(5));
    popupBack.setLocation(0,0);
    popup.add(popupBack);

    popup.setSize(d);
    popup.setLocation((640-d.width)/2, (480-d.height)/2);
    add(popup);

    JLabel backLab = new JLabel(Ggui.imgIconGet("LOGINORREGBACK"));
    backLab.setSize(backLab.getPreferredSize());
    backLab.setLocation(0,0);
    add(backLab);

    NonEmptyVerifier nev = new NonEmptyVerifier();
    passwordTF.getDocument().addDocumentListener(nev);
    handleTF.getDocument().addDocumentListener(nev);

    setVisible(false);
  }
  
  String getLoginName()
  {
    return handleTF.getText().trim();
  }
  
  String getPassword()
  {
    return (new String(passwordTF.getPassword())).trim();
  }
  
  JComponent makeLittlePanel(String labelSt, JTextField field)
  {
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
    p.setBackground(Ggui.darkBackground);
    JLabel l = new JLabel(labelSt);
    l.setFont(new Font("Arial",Font.BOLD,14));
    l.setForeground(Color.white);

    p.add(Box.createHorizontalGlue());
    p.add(l);
    p.add(Box.createHorizontalStrut(5));

    field.setBorder(new ShadowBorder(3));
    field.setMaximumSize(field.getPreferredSize());
    p.add(field);
    p.setAlignmentX(LEFT_ALIGNMENT);
    return p;
  }

  void adjustLoginButt()
  {
    String pwText = new String(passwordTF.getPassword());
    if((pwText.length() == 0) || (handleTF.getText().length() == 0))
      loginButt.setEnabled(false);
    else
      loginButt.setEnabled(true);
  }

  class NonEmptyVerifier implements DocumentListener
  {
    public void changedUpdate(DocumentEvent e)
    {
      adjustLoginButt();
    }
    public void insertUpdate(DocumentEvent e)
    {
      adjustLoginButt();
    }
    public void removeUpdate(DocumentEvent e)
    {
      adjustLoginButt();
    }
  }
  public void go()
  {
    //passwordTF.setText("");
    super.go();
  }

}