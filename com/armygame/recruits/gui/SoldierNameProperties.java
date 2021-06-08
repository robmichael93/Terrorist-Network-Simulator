/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 2, 2002
 * Time: 1:55:05 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;
import java.util.Properties;
import java.io.*;

public class SoldierNameProperties extends Properties
{
  public SoldierNameProperties()
  {
    // We will get the properties from the same jar that this file was loaded from.
    ClassLoader loader = this.getClass().getClassLoader();

    InputStream is = loader.getResourceAsStream("gui/lastNames.properties");
    if(is == null)
    {
      System.err.println("Can't load properties file");
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
}
