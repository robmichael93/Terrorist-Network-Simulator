//
//  NewUserPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Fri Mar 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class NewUserPanel extends RPanel
{
  JLabel logoLabel;
  JLabel titleLabel = new JLabel();
  JLabel nameLabel = new JLabel();
  JLabel handleLabel = new JLabel();
  JLabel passwordLabel = new JLabel();
  JLabel phoneLabel = new JLabel();
  JLabel street1Label = new JLabel();
  JLabel street2Label = new JLabel();
  JLabel townLabel = new JLabel();
  JLabel stateLabel = new JLabel();
  JLabel zipLabel = new JLabel();
  JLabel emailLabel = new JLabel();
  
  JPanel nameAddressPanel = new JPanel();
  JPanel handlePanel = new JPanel();
  JPanel namePanel = new JPanel();
  JPanel passwordPanel = new JPanel();
  JPanel phonePanel = new JPanel();
  JPanel street1Panel = new JPanel();
  JPanel street2Panel = new JPanel();
  JPanel townPanel = new JPanel();
  JPanel statePanel = new JPanel();
  JPanel zipPanel = new JPanel();
  JPanel emailPanel = new JPanel();
  
  public JTextField nameTF = new JTextField("");
  public JTextField handleTF = new JTextField("");
  public JTextField passwordTF = new JPasswordField("");
  public JTextField phoneTF = new JTextField("");
  public JTextField street1TF = new JTextField("");
  public JTextField street2TF = new JTextField("");
  public JTextField townTF = new JTextField("");
  public JTextField stateTF = new JTextField("");
  public JTextField zipTF = new JTextField("");
  public JTextField emailTF = new JTextField("");
  
  JComboBox stateCB;
  JTextArea questionTA = new JTextArea();

  MainFrame mf;
  public NewUserPanel( MainFrame main )
  {
    mf = main;
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    setVisible(false);
  }
  private void jbInit()//
  {
   
    final Color defBgd = getBackground();
    setLayout(null);
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
 /*
    JPanel logoPanel = new JPanel();
    logoPanel.setLayout(new BorderLayout());
    logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      logoLabel = new JLabel(Ggui.imgIconGet("RECRUITSLOGO"));
      logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    logoPanel.add(logoLabel,BorderLayout.NORTH);
    p.add(logoPanel);
    p.add(Box.createVerticalStrut(15));
 */
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(Ggui.transparent);
    titlePanel.setLayout(new BorderLayout());
      JLabel title1Lab = new JLabel();
      title1Lab.setFont(new Font("Arial",Font.PLAIN,14));
      title1Lab.setForeground(Color.white);
      title1Lab.setText("Enter the following information to login to the Recruits game server.");
      title1Lab.setHorizontalAlignment(SwingConstants.CENTER);
      //title1Lab.setAlignmentX(Component.CENTER_ALIGNMENT);
      JLabel title2Lab = new JLabel();
      title2Lab.setFont(new Font("Arial",Font.PLAIN,14));
      title2Lab.setForeground(Color.white);
      title2Lab.setText("Items in bold type are required.");
      title2Lab.setHorizontalAlignment(SwingConstants.CENTER);
      //title2Lab.setAlignmentX(Component.CENTER_ALIGNMENT);
    titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    titlePanel.add(title1Lab,BorderLayout.NORTH);
    titlePanel.add(title2Lab,BorderLayout.SOUTH);
    //titlePanel.setBorder(BorderFactory.createEtchedBorder()); //BorderFactory.createRaisedBevelBorder());
       
    //titleLabel.setFont(new java.awt.Font("SansSerif", 1, 14));
    //titleLabel.setText("Enter the following information to contact an Army recruiter");
    //titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    //personalButton.setText("Have a recruiter contact me personally");
    //personalButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    //personalButton.setSelected(true);

    //emailButton.setText("Email me an answer to the following question");
    //emailButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    //emailButton.setSelected(false);
    
    
    nameAddressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    nameAddressPanel.setLayout(new BoxLayout(nameAddressPanel,BoxLayout.Y_AXIS));
    nameAddressPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    nameAddressPanel.setBackground(Ggui.transparent);

    handlePanel = makeMiniPanel("Game Handle",handleLabel,handleTF,true);
    nameAddressPanel.add(handlePanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    passwordPanel = makeMiniPanel("Password",passwordLabel, passwordTF,true);
    nameAddressPanel.add(passwordPanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));
    
    namePanel = makeMiniPanel("Real Name",nameLabel, nameTF,true);
    nameAddressPanel.add(namePanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    phonePanel = makeMiniPanel("Phone",phoneLabel,phoneTF,false);
    nameAddressPanel.add(phonePanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    street1Panel = makeMiniPanel("Street Address",street1Label,street1TF,false);
    nameAddressPanel.add(street1Panel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    street2Panel = makeMiniPanel("",street2Label,street2TF,false);
    nameAddressPanel.add(street2Panel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    townPanel = makeMiniPanel("City",townLabel,townTF,false);
    nameAddressPanel.add(townPanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

      statePanel.setLayout(new BoxLayout(statePanel,BoxLayout.X_AXIS));
      statePanel.setBackground(Ggui.darkBackground);
        stateLabel.setText("State");
        stateLabel.setFont(new Font("Arial",Font.PLAIN,18));
        stateLabel.setForeground(Color.white);
        stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stateLabel.setPreferredSize(new Dimension(200,25));
        
        stateCB = new JComboBox(stateList);
        stateCB.setBorder(new ShadowBorder(4));
        stateCB.setBackground(Color.white);
        stateCB.setForeground(Color.black);
        stateCB.setPreferredSize(new Dimension(300,25));
      statePanel.add(Box.createHorizontalStrut(20));
      statePanel.add(stateLabel);
      statePanel.add(stateCB);
    nameAddressPanel.add(statePanel);
    nameAddressPanel.add(Box.createVerticalStrut(8));

    zipPanel = makeMiniPanel("Zip",zipLabel,zipTF,false);
    nameAddressPanel.add(zipPanel);
    
    JPanel outerEmailPanel = new JPanel();
    outerEmailPanel.setBackground(Ggui.darkBackground);
    outerEmailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    outerEmailPanel.setLayout(new BoxLayout(outerEmailPanel,BoxLayout.Y_AXIS));
   // outerEmailPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
    
      emailPanel.setLayout(new BoxLayout(emailPanel,BoxLayout.X_AXIS));
      emailPanel.setBackground(Ggui.darkBackground);
      emailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setText("Email Address");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setFont(new Font("Arial",Font.PLAIN,18));
        emailLabel.setForeground(Color.white);
        emailLabel.setPreferredSize(new Dimension(200,25));
        //emailLabel.setEnabled(false);
        emailTF.setPreferredSize(new Dimension(300,25));
        emailTF.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTF.setBorder(new ShadowBorder(4));

        //emailTF.setEnabled(false);
      emailPanel.add(Box.createHorizontalStrut(20));
      emailPanel.add(emailLabel);
      emailPanel.add(emailTF);
    outerEmailPanel.add(emailPanel);
 /*     
      questionTA.setRows(12);
      questionTA.setAlignmentX(Component.LEFT_ALIGNMENT);
      //questionTA.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      questionTA.setBorder(BorderFactory.createEtchedBorder());
      questionTA.setLineWrap(true);
      questionTA.setWrapStyleWord(true);
      questionTA.setEnabled(false);
      questionTA.setBackground(defBgd);
      questionTA.setToolTipText("Type your question in this box");
    outerEmailPanel.add(Box.createVerticalStrut(3));
    outerEmailPanel.add(questionTA);
*/    
    JButton canButt = ButtonFactory.make(ButtonFactory.NEWUSERCANCEL,mf);
    add(canButt);

    JButton okButt = ButtonFactory.make(ButtonFactory.NEWUSERLOGIN,mf);
    add(okButt);
    
    //add(titleLabel);
    p.add(titlePanel);
    p.add(Box.createVerticalStrut(6));
    p.add(nameAddressPanel);
    p.add(Box.createVerticalStrut(8));
    p.add(outerEmailPanel);
    p.setSize(p.getPreferredSize());
    p.setLocation(50,25);
    p.setBackground(Ggui.transparent);
    add(p);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("NEWUSERBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);


    okButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if(dataIsGood())
        {
           mf.handlers.eventIn(ButtonFactory.NEWUSERSUBMITTED);
          // mf.handlers.actionPerformed(new ActionEvent(okButt,ActionEvent.ACTION_PERFORMED,"newusersubmitted")); // okButt is nop            
        }
        else
            JOptionPane.showMessageDialog(null,"You must enter data in all the required fields.","Sorry!",JOptionPane.ERROR_MESSAGE);
        }
    });
  }
  JPanel makeMiniPanel(String labelText, JLabel lab, JTextField tf, boolean makeBold)
  {
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
    p.setBackground(Ggui.darkBackground);
    if(makeBold)
      lab.setFont(new Font("Arial",Font.BOLD,18));
    else
      lab.setFont(new Font("Arial",Font.PLAIN,18));
    lab.setForeground(Color.white);
    lab.setText(labelText);
    lab.setAlignmentX(Component.CENTER_ALIGNMENT);
    lab.setPreferredSize(new Dimension(200,25));
    tf.setPreferredSize(new Dimension(300, 25));
    tf.setAlignmentX(Component.CENTER_ALIGNMENT);
    tf.setBorder(new ShadowBorder(4));
    p.add(Box.createHorizontalStrut(20));
    p.add(lab);
    p.add(tf);    
    return p;
  }

  public void go()
  {
    mf.setTitleBar("CREATEACCOUNTTITLE");
    super.go();
  }
  /*
  public HashMap xgetData()
  {
    HashMap hm = new HashMap(10);
    hm.put("validated",(dataIsGood()?"yes":"no"));
    hm.put("name",nameTF.getText());
    hm.put("phone",phoneTF.getText());
    hm.put("street1",street1TF.getText());
    hm.put("street2",street2TF.getText());
    hm.put("town",townTF.getText());
    hm.put("state",stateTF.getText());
    hm.put("zip",zipTF.getText());
    hm.put("email",emailTF.getText());
    hm.put("question",questionTA.getText());
    return hm;
  }
  */
  private boolean dataIsGood()
  {
    if(handleTF.getText().equals(""))
      return false;
    if(passwordTF.getText().equals(""))
      return false;
    if(nameTF.getText().equals(""))
      return false;
    String stt = (String)stateCB.getSelectedItem();
    if(stt.startsWith("Click"))
      return false;
/*
    if(townTF.getText().equals("") || stateCB.getSelectedItem().toString().startsWith("Click"))
    {
      if(phoneTF.getText().equals(""))
        return false;
    }
    if(townTF.getText().equals("") && stateCB.getSelectedItem().toString().startsWith("Click"))
      return false;

    if(emailTF.getText().equals(""))
      return false;
*/
    return true;
  }

  String stateList[] = {
"Click to select",
"Alabama",
"Alaska",
"Arizona",
"Arkansas",
"California",
"Colorado",
"Connecticut",
"Delaware",
"District of Columbia",
"Florida",
"Georgia",
"Hawaii",
"Idaho",
"Illinois",
"Indiana",
"Iowa",
"Kansas",
"Kentucky",
"Louisiana",
"Maine",
"Maryland",
"Massachusetts",
"Michigan",
"Minnesota",
"Mississippi",
"Missouri",
"Montana",
"Nebraska",
"Nevada",
"New Hampshire",
"New Jersey",
"New Mexico",
"New York",
"North Carolina",
"North Dakota",
"Ohio",
"Oklahoma",
"Oregon",
"Pennsylvania",
//"Puerto Rico",
"Rhode Island",
"South Carolina",
"South Dakota",
"Tennessee",
"Texas",
"Utah",
"Vermont",
"Virginia",
"Washington",
"West Virginia",
"Wisconsin",
"Wyoming"};  
}