//
//  RecruitsProperties.java
//  
//
//  Created by Mike Bailey on Thu Apr 11 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits;
import com.armygame.recruits.gui.RoundedImageIcon;

import javax.swing.*;
import java.util.Properties;
import java.util.HashMap;
import java.io.*;
import java.net.URL;

public class RecruitsProperties extends Properties
{
  ClassLoader loader;
  HashMap imgHm;
  public RecruitsProperties(String resname)
  {
    // We will get the properties from the same jar that this file was loaded from.
    loader = this.getClass().getClassLoader();
    imgHm = new HashMap(500);

    InputStream is = loader.getResourceAsStream(resname);//"recruits.properties");
    if(is == null)
    {

      System.err.println("Can't load properties file/resource: "+resname);
      System.exit(-1);
    }

    try
    {
      this.load(is);
    }
    catch (IOException ex)
    {
      System.err.println("Properties load failed: "+ex);
      System.exit(-1);
    }
  }
  public String getProperty(String key)
  {
    String p = super.getProperty(key);
    if(p == null)
    {
      System.out.println("Can't find property "+key);
      System.out.println("Quitting");
      new Throwable().printStackTrace();
      System.exit(-1);
    }
    return p;
  }
  public URL getResource(String rn)
  {
    URL u = loader.getResource(rn);
    if(u == null)
    {
      System.out.println("Can't find resource "+rn);
      System.out.println("Quitting");
      new Throwable().printStackTrace();
      System.exit(-1);
    }
    return u;
  }

  public ImageIcon imgIconGetResource(String resourcename)
  {
    return imgIconGetResource(resourcename,false,0.0);
  }

  public ImageIcon imgIconGetResource(String resourcename, boolean rounded, double arc)
  {
    URL res = getResource(resourcename);
    ImageIcon ii = (ImageIcon)imgHm.get(res);
    if(ii == null)
    {
      if(rounded)
        ii = new RoundedImageIcon(res,arc);
      else
        ii = new ImageIcon(res);
      imgHm.put(res,ii);
    }
    return ii;
  }

}
