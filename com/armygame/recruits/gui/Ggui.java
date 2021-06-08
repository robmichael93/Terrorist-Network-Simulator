/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 23, 2002
 * Time: 9:33:59 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.RecruitsProperties;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Enumeration;

// Globals and Statics for the Recruits Gui
public class Ggui
{
  static
  {

  }
  private static String mainFontFace = "Arial";
  private static int buttFontSize = 13;
  private static int medButtFontSize = 16;
  private static int bigButtFontSize = 18;
  private static int bigBigButtFontSize = 24;
  private static int buttFontStyle = Font.PLAIN;
  private static int medButtFontStyle = Font.PLAIN;
  private static int bigButtFontStyle = Font.PLAIN;
  private static int bigBigButtFontStyle = Font.BOLD;

  private static Font buttFont = new Font(mainFontFace,buttFontStyle,buttFontSize);
  private static Font medButtFont=new Font(mainFontFace,medButtFontStyle,medButtFontSize);
  private static Font bigButtFont = new Font(mainFontFace,bigButtFontStyle,bigButtFontSize);
  private static Font bigBigButtFont=new Font(mainFontFace,bigBigButtFontStyle,bigBigButtFontSize);

  public static Font buttonFont()
  {
    return buttFont;
  }
  public static Font medButtonFont()
  {
    return medButtFont;
  }
  public static Font bigButtonFont()
  {
    return bigButtFont;
  }
  public static Font bigBigButtonFont()
  {
    return bigBigButtFont;
  }

  private static int buttForeRed = 242;
  private static int buttForeGreen = 194;
  private static int buttForeBlue = 0;
  private static Color buttRollColor = new Color(buttForeRed,buttForeGreen,buttForeBlue);
  private static Color buttForeColor = Color.white;
  public static Color buttonForeground()
  {
    return buttForeColor;
  }
  public static Color buttonRollColor()
  {
    return buttRollColor;
  }
  public static Color transparent = new Color(0,0,0,0);
  public static Color darkBackground = new Color(40,40,40);
  public static Color medDarkBackground = new Color(51,51,51);
  public static Color mediumBackground = new Color(60,60,60);
  public static Color lightBackground = new Color(100,100,100);
  public static Color lightlightBackground = new Color(173,173,173);
  public static Color statusLineColor = Color.white;
  public static Font  solderNameFont = buttFont;
  public static Color soldierNameColor = Color.white;

  public static Font statusLineFont = new Font("Arial",Font.PLAIN,14);
  public static Font createSoldierFont = statusLineFont;

  public static RecruitsProperties guiProps = new RecruitsProperties("gui/recruitsGui.properties");;
  static public String getProp(String propName)
  {
    return guiProps.getProperty(propName.trim());
  }
  static public int getIntProp(String propName)
  {
    return Integer.parseInt(getProp(propName.trim()));
  }
  static public ImageIcon imgIconGet(String propName)
  {
    return imgIconGetResource(getProp(propName));
  }
  static public ImageIcon imgIconGetResource(String resourcename)
  {
    return guiProps.imgIconGetResource("gui/"+resourcename.trim());
  }
  static public URL resourceGet(String resName)
  {
    return guiProps.getResource("gui/"+getProp(resName));
  }
  static public RoundedImageIcon roundedImgIconGetResource(String resourcename,double arc)
  {
    return (RoundedImageIcon)guiProps.imgIconGetResource("gui/"+resourcename,true,arc);
  }
  static public RoundedImageIcon roundedImgIconGet(String propertyName,double arc)
  {
    return roundedImgIconGetResource(getProp(propertyName),arc);
  }

  static public void main(String[] args)
  {
    System.out.println("GuiProps");
    System.out.println("Key\tValue");
    for(Enumeration en = guiProps.propertyNames();
         en.hasMoreElements();)
    {
      String key = (String)en.nextElement();
      String val = guiProps.getProperty(key);
      System.out.println(key+"\t"+val);

      if(val.endsWith(".png") || val.endsWith(".jpg") || val.endsWith(".gif"))
      {
        guiProps.getResource(val);  // will bomb and Sysout if can't find
      }
    }
  }

}