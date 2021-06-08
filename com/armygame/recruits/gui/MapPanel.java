//
//  MapPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import com.armygame.recruits.gui.laf.*;
import com.armygame.recruits.globals.MasterConfiguration;
import com.armygame.recruits.globals.RecruitsConfiguration;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

public class MapPanel extends RPanel
{
  MainFrame mf;
  int numBases;
  JButton [] bases;
  JButton lastRoll;

  MapPanel(MainFrame main)
  {
    mf = main;
    
    setLayout(null);
    JButton closeButt = ButtonFactory.make(ButtonFactory.MAPCLOSE,mf);
    add(closeButt);

    numBases = Ggui.getIntProp("MAP_NUMPOSTS");
    bases = new JButton[numBases];

    for(int i=0;i<numBases;i++)
    {
      String bfField = "MAPPOST_"+i;
      Field f=null;
      int ID=-1;
      try{
        f = ButtonFactory.instance().getClass().getDeclaredField(bfField);
        ID = f.getInt(ButtonFactory.instance());
      }
      catch(Exception e){System.out.println("Mappanel exception: "+e);}

      bases[i] = ButtonFactory.make(ID,mf);
      Dimension d = bases[i].getSize();
      Icon ic = bases[i].getDisabledIcon();
      if(ic instanceof ImageIcon)
      {
        Image im = ((ImageIcon)ic).getImage();
        im = im.getScaledInstance(d.width,d.height,Image.SCALE_DEFAULT);
        ImageIcon ni = new ImageIcon(im);
        bases[i].setDisabledIcon(ni);
        bases[i].setPressedIcon(ni);
        bases[i].setIcon(ni);
      }

      add(bases[i]);
    }

    JButton showMy = ButtonFactory.make(ButtonFactory.MAPMYBASES,mf);
    add(showMy);
    showMy.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        for(int i=0;i<bases.length;i++)
        {
          int r = (int)(Math.random()*bases.length);
          if(r% 5 == 0)
            bases[i].setIcon(bases[i].getRolloverIcon());
          else
            bases[i].setIcon(bases[i].getDisabledIcon());
        }
      }
    });

    JButton showAll = ButtonFactory.make(ButtonFactory.MAPALLBASES,mf);
    add(showAll);
    showAll.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        for(int i=0;i<bases.length;i++)
          bases[i].setIcon(bases[i].getRolloverIcon());
      }
    });

    JLabel backLabel = new JLabel(Ggui.imgIconGet("MAPBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  public void go()
  {
    super.go();
    for(int i=0;i<bases.length;i++)
      bases[i].setIcon(bases[i].getDisabledIcon());
    mf.setTitleBar("MAPTITLE");

/*
    RecruitsConfiguration rc = MasterConfiguration.Instance();
    System.out.println("AnimationDefinitionsPath = "+rc.AnimationDefinitionsPath());
    System.out.println("AnimationImagesRootPath = "+rc.AnimationImagesRootPath());
    System.out.println("AudioAssetsRootPath = "+rc.AudioAssetsRootPath());
    System.out.println("LocationImagesPath = "+rc.LocationImagesPath());
    System.out.println("LocationMediaAssetManifestPath = "+rc.LocationMediaAssetManifestPath());
    System.out.println("LocationMediaAssetsPath = "+rc.LocationMediaAssetsPath());
    System.out.println("LocationsPath = "+rc.LocationsPath());
    System.out.println("LocationTemplateManifestPath = "+rc.LocationTemplateManifestPath());
    System.out.println("LocationTemplatesPath = "+rc.LocationTemplatesPath());
    System.out.println("RangeMapManifestPath = "+rc.RangeMapManifestPath());
    System.out.println("RangeMapsPath = "+rc.RangeMapsPath());
    System.out.println("RootPath = "+rc.RootPath());
*/
  }

}
