//
//  ReferralPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;

import com.armygame.recruits.globals.ResourceReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReferralPanel extends RPanel implements ActionListener
{
  MainFrame mf;
  boolean perStateChecked = false,emailStateChecked = false;
  Icon noCheckIcon;
  Icon yesCheckIcon;
  JButton checkPersButt, checkEmailButt;

  public ReferralPanel(MainFrame main)
  {
    mf = main;

    setLayout(null);

    JButton okButt = ButtonFactory.make(ButtonFactory.REFERRALOK,mf);
    add(okButt);

    yesCheckIcon = Ggui.imgIconGet("REFERRALCHECKBOXCHECKED");

    checkPersButt = ButtonFactory.make(ButtonFactory.REFERRALCHECKPERS,mf);
    add(checkPersButt);
    checkPersButt.addActionListener(this);
    checkPersButt.setActionCommand("p");
    noCheckIcon = checkPersButt.getIcon();

    checkEmailButt = ButtonFactory.make(ButtonFactory.REFERRALCHECKEMAIL, mf);
    add(checkEmailButt);
    checkEmailButt.addActionListener(this);
    checkEmailButt.setActionCommand("e");

    JButton cancelButt = ButtonFactory.make(ButtonFactory.REFERRALCANCEL,mf);
    add(cancelButt);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("REFERRALBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }
  public void actionPerformed(ActionEvent e)
  {
    if(e.getActionCommand().charAt(0) == 'p')
    {
      if(perStateChecked == false)
      {
        checkPersButt.setIcon(yesCheckIcon);
        checkPersButt.setRolloverIcon(yesCheckIcon);
        checkPersButt.setPressedIcon(yesCheckIcon);
        perStateChecked = true;
      }
      else
      {
        checkPersButt.setIcon(noCheckIcon);
        checkPersButt.setRolloverIcon(noCheckIcon);
        checkPersButt.setPressedIcon(noCheckIcon);
        perStateChecked = false;
      }
    }
    else
    {
    }
  }
  public void go()
  {
    mf.setTitleBar("REFERRALTITLE");
    super.go();
  }
}

/*
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ReferralPanel extends RPanel
{
  JLabel logoLabel;
  JLabel titleLabel = new JLabel();
  JLabel nameLabel = new JLabel();
  JLabel phoneLabel = new JLabel();
  JLabel street1Label = new JLabel();
  JLabel street2Label = new JLabel();
  JLabel townLabel = new JLabel();
  JLabel stateLabel = new JLabel();
  JLabel zipLabel = new JLabel();
  JLabel emailLabel = new JLabel();

  JRadioButton personalButton = new JRadioButton();
  JRadioButton emailButton = new JRadioButton();

  JPanel nameAddressPanel = new JPanel();
  JPanel namePanel = new JPanel();
  JPanel phonePanel = new JPanel();
  JPanel street1Panel = new JPanel();
  JPanel street2Panel = new JPanel();
  JPanel townPanel = new JPanel();
  JPanel statePanel = new JPanel();
  JPanel zipPanel = new JPanel();
  JPanel emailPanel = new JPanel();

  JTextField nameTF = new JTextField();
  JTextField phoneTF = new JTextField();
  JTextField street1TF = new JTextField();
  JTextField street2TF = new JTextField();
  JTextField townTF = new JTextField();
  JTextField stateTF = new JTextField();
  JTextField zipTF = new JTextField();
  JTextField emailTF = new JTextField();

  JComboBox stateCB;
  JTextArea questionTA = new JTextArea();
  JButton canButt;
  JButton okButt;

  MainFrame mf;
  public ReferralPanel(MainFrame main)
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

  private void jbInit() throws Exception
  {
    final Color defBgd = getBackground();
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

    JPanel logoPanel = new JPanel();
    logoPanel.setLayout(new BorderLayout());
    logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      logoLabel = new JLabel(new ImageIcon("RecruitsLogo.jpg"));
      logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    logoPanel.add(logoLabel,BorderLayout.NORTH);
    add(logoPanel);
    add(Box.createVerticalStrut(15));

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BorderLayout());
      JLabel title1Lab = new JLabel();
      title1Lab.setFont(new Font("SansSerif",Font.PLAIN,14));
      title1Lab.setText("Enter the following information to");
      title1Lab.setHorizontalAlignment(SwingConstants.CENTER);
      //title1Lab.setAlignmentX(Component.CENTER_ALIGNMENT);
      JLabel title2Lab = new JLabel();
      title2Lab.setFont(new Font("SansSerif",Font.PLAIN,14));
      title2Lab.setText("contact an Army recruiter");
      title2Lab.setHorizontalAlignment(SwingConstants.CENTER);
      //title2Lab.setAlignmentX(Component.CENTER_ALIGNMENT);
    titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    titlePanel.add(title1Lab,BorderLayout.NORTH);
    titlePanel.add(title2Lab,BorderLayout.SOUTH);
    titlePanel.setBorder(BorderFactory.createEtchedBorder()); //BorderFactory.createRaisedBevelBorder());

    //titleLabel.setFont(new java.awt.Font("SansSerif", 1, 14));
    //titleLabel.setText("Enter the following information to contact an Army recruiter");
    //titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    personalButton.setText("Have a recruiter contact me personally");
    personalButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    personalButton.setSelected(true);

    emailButton.setText("Have a recruiter send me by email an answer to the following question");
    emailButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    emailButton.setSelected(false);


    nameAddressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    nameAddressPanel.setLayout(new BoxLayout(nameAddressPanel,BoxLayout.Y_AXIS));
    nameAddressPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
      namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
        nameLabel.setText("My Name");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setPreferredSize(new Dimension(120,21));
        nameTF.setPreferredSize(new Dimension(163, 21));
        nameTF.setAlignmentX(Component.CENTER_ALIGNMENT);
      namePanel.add(Box.createHorizontalStrut(20));
      namePanel.add(nameLabel);
      namePanel.add(nameTF);
      namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      namePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    //nameAddressPanel.add(namePanel);

      phonePanel.setLayout(new BoxLayout(phonePanel,BoxLayout.X_AXIS));
        phoneLabel.setText("My phone");
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setPreferredSize(new Dimension(120,21));
        phoneTF.setPreferredSize(new Dimension(163, 21));
        //phoneTF.setText("phoneTF");
        phoneTF.setAlignmentX(Component.CENTER_ALIGNMENT);
      phonePanel.add(Box.createHorizontalStrut(20));
      phonePanel.add(phoneLabel);
      phonePanel.add(phoneTF);
    nameAddressPanel.add(phonePanel);

      street1Panel.setLayout(new BoxLayout(street1Panel,BoxLayout.X_AXIS));
        street1Label.setText("My street address");
        street1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        street1Label.setPreferredSize(new Dimension(120,21));
        street1TF.setPreferredSize(new Dimension(163, 21));
        street1TF.setAlignmentX(Component.CENTER_ALIGNMENT);
      street1Panel.add(Box.createHorizontalStrut(20));
      street1Panel.add(street1Label);
      street1Panel.add(street1TF);
    nameAddressPanel.add(street1Panel);

      street2Panel.setLayout(new BoxLayout(street2Panel,BoxLayout.X_AXIS));
        street2Label.setText("");
        street2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        street2Label.setPreferredSize(new Dimension(120,21));
        street2TF.setPreferredSize(new Dimension(163, 21));
        street2TF.setAlignmentX(Component.CENTER_ALIGNMENT);
      street2Panel.add(Box.createHorizontalStrut(20));
      street2Panel.add(street2Label);
      street2Panel.add(street2TF);
    nameAddressPanel.add(street2Panel);

      townPanel.setLayout(new BoxLayout(townPanel,BoxLayout.X_AXIS));
        townLabel.setText("My town");
        townLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        townLabel.setPreferredSize(new Dimension(120,21));
        townTF.setPreferredSize(new Dimension(163, 21));
        townTF.setAlignmentX(Component.CENTER_ALIGNMENT);
        townTF.setToolTipText("Enter your town or city name in this box");
      townPanel.add(Box.createHorizontalStrut(20));
      townPanel.add(townLabel);
      townPanel.add(townTF);
    nameAddressPanel.add(townPanel);

      statePanel.setLayout(new BoxLayout(statePanel,BoxLayout.X_AXIS));
        stateLabel.setText("My state");
        stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stateLabel.setPreferredSize(new Dimension(120,21));

        stateCB = new JComboBox(stateList);
        //stateTF.setPreferredSize(new Dimension(163, 21));
        //stateTF.setAlignmentX(Component.CENTER_ALIGNMENT);
      statePanel.add(Box.createHorizontalStrut(20));
      statePanel.add(stateLabel);
      //statePanel.add(stateTF);
      statePanel.add(stateCB);
    nameAddressPanel.add(statePanel);

      zipPanel.setLayout(new BoxLayout(zipPanel,BoxLayout.X_AXIS));
        zipLabel.setText("My zip code");
        zipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        zipLabel.setPreferredSize(new Dimension(120,21));
        zipTF.setPreferredSize(new Dimension(163, 21));
        zipTF.setAlignmentX(Component.CENTER_ALIGNMENT);
        zipTF.setToolTipText("Enter your zip code in this box");
      zipPanel.add(Box.createHorizontalStrut(20));
      zipPanel.add(zipLabel);
      zipPanel.add(zipTF);
    nameAddressPanel.add(zipPanel);

    JPanel outerEmailPanel = new JPanel();
    outerEmailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    outerEmailPanel.setLayout(new BoxLayout(outerEmailPanel,BoxLayout.Y_AXIS));
    outerEmailPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

      emailPanel.setLayout(new BoxLayout(emailPanel,BoxLayout.X_AXIS));
      emailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setText("My email address");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setPreferredSize(new Dimension(120,21));
        emailLabel.setEnabled(false);
        emailTF.setPreferredSize(new Dimension(163, 21));
        emailTF.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTF.setToolTipText("Enter your email address in this box");
        emailTF.setEnabled(false);
      emailPanel.add(Box.createHorizontalStrut(20));
      emailPanel.add(emailLabel);
      emailPanel.add(emailTF);

      questionTA.setRows(12);
      questionTA.setAlignmentX(Component.LEFT_ALIGNMENT);
      //questionTA.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      questionTA.setBorder(BorderFactory.createEtchedBorder());
      questionTA.setLineWrap(true);
      questionTA.setWrapStyleWord(true);
      questionTA.setEnabled(false);
      questionTA.setBackground(defBgd);
      questionTA.setToolTipText("Type your question in this box");
    outerEmailPanel.add(emailPanel);
    outerEmailPanel.add(Box.createVerticalStrut(3));
    outerEmailPanel.add(questionTA);

    JPanel buttPanel = new JPanel();

    canButt = ButtonFactory.makeTempTextButt(ButtonFactory.REFERRALCANCELLED,mf);
    //canButt = new JButton("Cancel");
    //canButt.setToolTipText("Close this window and don't send anything to the recruiter");
    okButt = ButtonFactory.makeTempTextButt(ButtonFactory.REFERRALSUBMITTED,mf,false);
    //okButt = new JButton("Send to recruiter");
    //okButt.setToolTipText("Send my data to a recruiter and close this window");
    buttPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    buttPanel.setLayout(new BoxLayout(buttPanel,BoxLayout.X_AXIS));
    //buttPanel.setBorder(BorderFactory.createEtchedBorder());
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(canButt);
    buttPanel.add(Box.createHorizontalGlue());
    buttPanel.add(okButt);
    buttPanel.add(Box.createHorizontalGlue());


    //mainPanel.add(titleLabel);
    add(titlePanel);
    add(Box.createVerticalStrut(12));
    add(namePanel);
    add(Box.createVerticalStrut(6));
    add(personalButton);
    add(Box.createVerticalStrut(6));
    add(nameAddressPanel);
    add(Box.createVerticalStrut(12));
    add(emailButton);
    add(Box.createVerticalStrut(6));
    add(outerEmailPanel);
    add(Box.createVerticalStrut(12));
    add(buttPanel);

    personalButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if(personalButton.isSelected())
        {
          nameLabel.setEnabled(true);
          phoneLabel.setEnabled(true);
          street1Label.setEnabled(true);
          street2Label.setEnabled(true);
          townLabel.setEnabled(true);
          stateLabel.setEnabled(true);
          zipLabel.setEnabled(true);
          nameTF.setEnabled(true);
          phoneTF.setEnabled(true);
          street1TF.setEnabled(true);
          street2TF.setEnabled(true);
          townTF.setEnabled(true);
          stateTF.setEnabled(true);
          stateCB.setEnabled(true);
          zipTF.setEnabled(true);
         }
        else
        {
          //nameLabel.setEnabled(false);
          phoneLabel.setEnabled(false);
          street1Label.setEnabled(false);
          street2Label.setEnabled(false);
          townLabel.setEnabled(false);
          stateLabel.setEnabled(false);
          zipLabel.setEnabled(false);
          //nameTF.setEnabled(false);
          phoneTF.setEnabled(false);
          street1TF.setEnabled(false);
          street2TF.setEnabled(false);
          townTF.setEnabled(false);
          stateTF.setEnabled(false);
          stateCB.setEnabled(false);
          zipTF.setEnabled(false);
        }
      }
    });
    emailButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if(emailButton.isSelected())
        {
          questionTA.setEnabled(true);
          questionTA.setBackground(Color.white);
          emailLabel.setEnabled(true);
          emailTF.setEnabled(true);
        }
        else
        {
          questionTA.setEnabled(false);
          questionTA.setBackground(defBgd);
          emailLabel.setEnabled(false);
          emailTF.setEnabled(false);
        }
      }
    });

    okButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if(personalButton.isSelected() || emailButton.isSelected())
        {
          if(dataIsGood())
          {
            mf.handlers.eventIn(ButtonFactory.REFERRALSUBMITTED);
          }
          else
            JOptionPane.showMessageDialog(null,"You must enter data in all the required fields.","Sorry!",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          JOptionPane.showMessageDialog(null,"At least one of the two options must be selected.","Sorry!",JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }
  public HashMap getData()
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
  private boolean dataIsGood()
  {
    if(personalButton.isSelected())
    {
      if(nameTF.getText().equals(""))
        return false;
      if(townTF.getText().equals("") || stateCB.getSelectedItem().toString().startsWith("Click"))
      {
        if(phoneTF.getText().equals(""))
          return false;
      }
      if(townTF.getText().equals("") && stateCB.getSelectedItem().toString().startsWith("Click"))
        return false;
    }
    if(emailButton.isSelected())
    {
      if(emailTF.getText().equals(""))
        return false;
    }
    return true;
  }

  private void centerMe()
  {
    Dimension us = this.getSize();
    Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
    int newx = (them.width - us.width) / 2;
    int newy = (them.height - us.height) / 2;
    this.setLocation(newx,newy);
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
*/