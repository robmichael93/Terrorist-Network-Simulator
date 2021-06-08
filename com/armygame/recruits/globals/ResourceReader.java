/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: Apr 19, 2002
 * Time: 1:46:22 PM
 */
package com.armygame.recruits.globals;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.lang.ClassLoader;

public class ResourceReader
/*************************/
{
  static ClassLoader resources;
  static ResourceReader me;

  private ResourceReader()
  //======================
  {
    resources = this.getClass().getClassLoader();
  }

  public static ResourceReader instance()
  //-------------------------------------
  {
    if(me == null)
      me = new ResourceReader();
    return me;
  }

  public static boolean Exists( String resourceName ) {
    URL TestURL = getURL( resourceName );
    boolean Result = ( TestURL != null ) ? true : false;
    return Result;
  }

  public static InputStream getInputStream (String name) throws IOException
  {
    name = loseBackslashes(name);
    InputStream is = resources.getResourceAsStream(name);
    if(is == null)
      throw new IOException("Can't read "+name+" resource");
    else
      return is;
  }

  public static InputStreamReader getInputReader(String name) throws IOException
  //---------------------------------------------------------
  {
    name = loseBackslashes(name);
    InputStreamReader isr = getInputReader(name,resources);
    if(isr == null)
      throw new IOException("Can't read "+name+" resource");
    else
      return isr;
  }

  public static InputStreamReader getInputReader(String name, ClassLoader cl)
  //-------------------------------------------------------------------
  {
     String s = loseBackslashes(name);
     System.out.println(s);
     InputStream is = cl.getResourceAsStream(s);
     System.out.println(is);
     return new InputStreamReader(is);
//    return new InputStreamReader(cl.getResourceAsStream(loseBackslashes(name)));
  }

  public static URL getURL(String name)
  //-----------------------------------
  {
    return getURL(name,resources);
  }

  public static URL getURL(String name, ClassLoader cl)
  //---------------------------------------------------
  {
    name = loseBackslashes(name);
 //URL u = cl.getResource(name);
 // System.out.println("ResourceReader.getURL("+name+")="+u);
    return cl.getResource(name);
  }


  public static InputStreamReader getInputReaderData(String name)
  //--------------------------------------------------------------
  {
    return getInputReader("data/"+name,resources);
  }

  public static InputStreamReader getInputReaderGui(String name)
  //------------------------------------------------------------
  {
    return getInputReader("gui/"+name,resources);
  }

  public static InputStreamReader getInputReaderAudio(String name)
  //--------------------------------------------------------------
  {
    return getInputReader("audio/"+name,resources);
  }

  public static InputStreamReader getInputReaderAnim(String name)
  //-------------------------------------------------------------
  {
    return getInputReader("anim/"+name,resources);
  }

  public static InputStreamReader getInputReaderBackground(String name)
  //-------------------------------------------------------------------
  {
    return getInputReader("background/"+name,resources);
  }

  public static InputStreamReader getInputReaderMov(String name)
  //------------------------------------------------------------
  {
    return getInputReader("mov/"+name,resources);
  }

  private static String loseBackslashes(String s)
  {
     System.out.println(s);
    return s.replace('\\','/');
  }

}
