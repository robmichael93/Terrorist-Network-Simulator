//
//  FilePanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilePanel extends RPanel
{
  MainFrame mf;
  JPanel savePopup,loadPopup,uploadPopup,downloadPopup,newPopup;
  JButton closeButt, loadButt,saveButt,uploadButt,downloadButt,updateButt,exitButt,
  	optionsButt,newButt,opsButt;

  FilePanel(MainFrame mf)
  {
    this.mf = mf;
    setLayout(null);
    setBackground(Ggui.darkBackground);   // for shadow border

    closeButt = ButtonFactory.make(ButtonFactory.FILECANCEL,mf);
    add(closeButt);

    //JPanel tempP = new JPanel();
    //tempP.setLayout(new BoxLayout(tempP,BoxLayout.Y_AXIS));

    loadButt = ButtonFactory.make(ButtonFactory.FILELOAD,mf);
    add(loadButt);
    loadButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(false);
        loadPopup.setVisible(true);
      }
    });

    saveButt = ButtonFactory.make(ButtonFactory.FILESAVE,mf);
    add(saveButt);
    saveButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(false);
        savePopup.setVisible(true);
      }
    });

    uploadButt = ButtonFactory.make(ButtonFactory.FILEUPLOAD,mf);
    add(uploadButt);
    uploadButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(false);
        uploadPopup.setVisible(true);
      }
    });
    downloadButt = ButtonFactory.make(ButtonFactory.FILEDOWNLOAD,mf);
    add(downloadButt);
    downloadButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(false);
        downloadPopup.setVisible(true);
      }
    });

    updateButt = ButtonFactory.make(ButtonFactory.FILEUPDATE,mf);
    add(updateButt);
    exitButt = ButtonFactory.make(ButtonFactory.FILEEXIT,mf);
    add(exitButt);
    
// since the panel is not handled by an outer layout manager, set its size:
//tempP.setSize(tempP.getPreferredSize());
//tempP.setLocation(175,125);
//add(tempP);
    optionsButt = ButtonFactory.make(ButtonFactory.FILEOPTIONS,mf);
    add(optionsButt);

    opsButt = ButtonFactory.make(ButtonFactory.FILEOPS,mf);
    add(opsButt);

    newButt = ButtonFactory.make(ButtonFactory.FILENEW,mf);
    add(newButt);
    newButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(false);
        newPopup.setVisible(true);
      }
    });

    savePopup = makeSavePopup();
    add(savePopup);
    loadPopup = makeLoadPopup();
    add(loadPopup);
    newPopup = makeNewPopup();
    add(newPopup);
    uploadPopup = makeUploadPopup();
    add(uploadPopup);
    downloadPopup = makeDownloadPopup();
    add(downloadPopup);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("FILEBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
        
    setVisible(false);
  }
  private JPanel makeUploadPopup()
  {
    final JPanel savePopup = new JPanel();
    savePopup.setBackground(Ggui.darkBackground);
    savePopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    savePopup.setLayout(new BoxLayout(savePopup,BoxLayout.Y_AXIS));

    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILEUPLOADCHAR,true);
    saveCharButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCharButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILEUPLOADSTORY,true);
    saveGameButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveGameButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCancelButt = ButtonFactory.make(ButtonFactory.FILEUPLOADCANCEL,mf);
    saveCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCancelButt);
    savePopup.add(Box.createVerticalStrut(10));
    saveCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        savePopup.setVisible(false);
      }
    });

    Dimension d = savePopup.getPreferredSize();
    savePopup.setSize(d);
    savePopup.setLocation((640-d.width)/2 + 50,(480-d.height)/2);
    savePopup.setVisible(false);
    return savePopup;
  }

  private JPanel makeDownloadPopup()
  {
    final JPanel savePopup = new JPanel();
    savePopup.setBackground(Ggui.darkBackground);
    savePopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    savePopup.setLayout(new BoxLayout(savePopup,BoxLayout.Y_AXIS));

    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILEDOWNLOADCHAR,true);
    saveCharButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCharButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILEDOWNLOADSTORY,true);
    saveGameButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveGameButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCancelButt = ButtonFactory.make(ButtonFactory.FILEDOWNLOADCANCEL,mf);
    saveCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCancelButt);
    savePopup.add(Box.createVerticalStrut(10));
    saveCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        savePopup.setVisible(false);
      }
    });

    Dimension d = savePopup.getPreferredSize();
    savePopup.setSize(d);
    savePopup.setLocation((640-d.width)/2 + 50,(480-d.height)/2);
    savePopup.setVisible(false);
    return savePopup;
  }

  private JPanel makeSavePopup()
  {
    final JPanel savePopup = new JPanel();
    savePopup.setBackground(Ggui.darkBackground);
    savePopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    savePopup.setLayout(new BoxLayout(savePopup,BoxLayout.Y_AXIS));

    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILESAVECHAR,true);
    saveCharButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCharButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILESAVEGAME,true);
    saveGameButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveGameButt);
    savePopup.add(Box.createVerticalStrut(10));
    JButton saveCancelButt = ButtonFactory.make(ButtonFactory.FILESAVECANCEL,mf);
    saveCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    savePopup.add(saveCancelButt);
    savePopup.add(Box.createVerticalStrut(10));
    saveCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        savePopup.setVisible(false);
      }
    });

    Dimension d = savePopup.getPreferredSize();
    savePopup.setSize(d);
    savePopup.setLocation((640-d.width)/2 + 50,(480-d.height)/2);
    savePopup.setVisible(false);
    return savePopup;
  }
/*  private JPanel makeLoadPopup()
  {
  // This is an attempt to componentize the shadowed border stuff...
    final ShadowedPanel loadPopup = new ShadowedPanel();
    Container c = loadPopup.getContentHolder();
    c.add(Box.createVerticalStrut(10));
    JButton loadCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILELOADCHAR,true);
    loadCharButt.setAlignmentX(CENTER_ALIGNMENT);
    c.add(loadCharButt);
    c.add(Box.createVerticalStrut(10));
    JButton loadGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILELOADGAME,true);
    loadGameButt.setAlignmentX(CENTER_ALIGNMENT);
    c.add(loadGameButt);
    c.add(Box.createVerticalStrut(10));
    JButton loadCancelButt = ButtonFactory.make(ButtonFactory.FILELOADCANCEL,mf);
    loadCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    c.add(loadCancelButt);
    c.add(Box.createVerticalStrut(10));
    loadCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        loadPopup.setVisible(false);
      }
    });
    loadPopup.setSize(loadPopup.getPreferredSize());
    loadPopup.setVisible(false);
    return loadPopup;
  }
  */

  private JPanel makeLoadPopup()
  {
    final JPanel loadPopup = new JPanel();
    loadPopup.setBackground(Ggui.darkBackground);
    loadPopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    loadPopup.setLayout(new BoxLayout(loadPopup,BoxLayout.Y_AXIS));

    loadPopup.add(Box.createVerticalStrut(10));
    JButton loadCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILELOADCHAR,true);
    loadCharButt.setAlignmentX(CENTER_ALIGNMENT);
    loadPopup.add(loadCharButt);
    loadPopup.add(Box.createVerticalStrut(10));
    JButton loadGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILELOADGAME,true);
    loadGameButt.setAlignmentX(CENTER_ALIGNMENT);
    loadPopup.add(loadGameButt);
    loadPopup.add(Box.createVerticalStrut(10));
    JButton loadCancelButt = ButtonFactory.make(ButtonFactory.FILELOADCANCEL,mf);
    loadCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    loadPopup.add(loadCancelButt);
    loadPopup.add(Box.createVerticalStrut(10));
    loadCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        loadPopup.setVisible(false);
      }
    });

    Dimension d = loadPopup.getPreferredSize();
    loadPopup.setSize(d);
    loadPopup.setLocation((640-d.width)/2 + 50,(480-d.height)/2);
    loadPopup.setVisible(false);
    return loadPopup;
  }

  private JPanel makeNewPopup()
  {
    final JPanel newPopup = new JPanel();
    newPopup.setBackground(Ggui.darkBackground);
    newPopup.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                              BorderFactory.createLineBorder(Color.black,1)),
                                           BorderFactory.createEmptyBorder(10,10,10,10)));
    newPopup.setLayout(new BoxLayout(newPopup,BoxLayout.Y_AXIS));

    newPopup.add(Box.createVerticalStrut(10));
    JLabel topLab = new JLabel("Start new game with...");
    topLab.setFont(Ggui.bigButtonFont());
    topLab.setAlignmentX(CENTER_ALIGNMENT);
    topLab.setForeground(Color.white);
    newPopup.add(topLab);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newCharButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILENEWCHAR,true);
    newCharButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newCharButt);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newGameButt = ButtonFactory.makeBigTextButt(ButtonFactory.FILENEWEXISTING,true);
    newGameButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newGameButt);
    newPopup.add(Box.createVerticalStrut(10));
    JButton newCancelButt = ButtonFactory.make(ButtonFactory.FILENEWCANCEL,mf);
    newCancelButt.setAlignmentX(CENTER_ALIGNMENT);
    newPopup.add(newCancelButt);
    newPopup.add(Box.createVerticalStrut(10));
    newCancelButt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        allMainButtsEnabled(true);
        newPopup.setVisible(false);
      }
    });

    Dimension d = newPopup.getPreferredSize();
    newPopup.setSize(d);
    newPopup.setLocation((640-d.width)/2 + 50,(480-d.height)/2);
    newPopup.setVisible(false);
    return newPopup;
  }

  private void allMainButtsEnabled(boolean tf)
  {
    closeButt.setEnabled(tf);
    loadButt.setEnabled(tf);
    saveButt.setEnabled(tf);
    uploadButt.setEnabled(tf);
    downloadButt.setEnabled(tf);
    updateButt.setEnabled(tf);
    exitButt.setEnabled(tf);
    optionsButt.setEnabled(tf);
    newButt.setEnabled(tf);
  }

  JPanel initPopup;
  boolean initCancelEnabled=false;
  public void go()
  {
    super.go();
    mf.setTitleBar("MAINMENUTITLE");
    savePopup.setVisible(false);
    newPopup.setVisible(false);
    loadPopup.setVisible(false);
    uploadPopup.setVisible(false);
    downloadPopup.setVisible(false);
    if(initPopup != null)
    {
      initPopup.setVisible(true);
      allMainButtsEnabled(false);
      // do initCancelEnabled
      initPopup = null;
    }
    else
      allMainButtsEnabled(true);
  }
  public void goFirstPopup()
  {
    initPopup = newPopup;
  }
}
