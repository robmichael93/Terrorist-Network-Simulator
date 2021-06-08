package com.armygame.recruits;
/******************************
 * File:	RecruitsMain.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 11 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

import com.armygame.recruits.globals.ResourceReader;
import com.armygame.recruits.globals.SavedCharacter;
import com.armygame.recruits.globals.SavedGame;
import com.armygame.recruits.gui.*;
import com.armygame.recruits.gui.laf.RecruitsLookAndFeel;
import com.armygame.recruits.messaging.RecruitsMessager;
import com.armygame.recruits.services.InetServices;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTheme;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.Vector;

public class RecruitsMain
/***********************/
{
  private static RecruitsMain me;
  private static MainFrame mf;

  public InetServices services;
  public StoryEngine storyengine;
  public StoryEngineMessaging storyenginemessaging;
  public RecruitsMessager messager;
  public RecruitsGuiMessager guimessager;

  public ResourceReader resourceReader;

  // command-line controlled:
  public boolean autoPlay = true;
  public boolean soundEffectsOn = true;
  public boolean sceneTest = false;

  private RecruitsMain(String[] args)	// singleton...can be accessed throughout VM
  //====================
  {
    me = this;
    processArgs(args);
    resourceReader = ResourceReader.instance();

    // Order of initting:
    // 00. Build any directories which are required but not present (e.g., /save)
    // 0. Load properties, make accessible throughout app
    // 1. Instantiate services object, so it can be connecting in the background
    // 2. Build story engine
    // 3. Build gui

    firstTimeInit();

    loadProperties();

    startInetServices();

    startGui();
    // StoryEngine must start AFTER the Gui, since GoalTree ref's MainFrame
    startStoryEngine();

    messager = new RecruitsMessager();

    // Some rely on others...now tell them all to go
    goInetServices();
    goStoryEngine();
    goGui();            // Gui will immed ask for CharAttr from story E., so he's last
  }

  private Properties appProperties;
  private void loadProperties()
  //---------------------------
  {
    appProperties = new RecruitsProperties("data/recruits.properties");
  }
  public String getProp(String s)
  //-----------------------------
  {
    return appProperties.getProperty(s);
  }
  private void firstTimeInit()
  //--------------------------
  {
    // build the save directory if required
    File f = new File("./save");
    if(!f.exists())
    {
      f.mkdir();
      makeCannedChars();
      makeCannedGames();
    }
  }

  private void startInetServices()
  //------------------------------
  {
    services = new InetServices( getProp("SERVICES_PROTOCOL"),
                                 getProp("SERVICES_SERVER"),
                                 getProp("SERVICES_PORT"),
                                 getProp("SERVICES_PATH"));
  }
  private void goInetServices()
  //---------------------------
  {
    services.go();
  }
  private void startStoryEngine()
  //-----------------------------
  {
    storyengine = StoryEngine.instance();
    storyenginemessaging = new StoryEngineMessaging();
  }

  private void goStoryEngine()
  //--------------------------
  {
    storyengine.go();
    storyenginemessaging.go();
  }

  private void startGui()
  {
    new Ggui();   // will init all the statics
    RecruitsSplash sp = new RecruitsSplash();

    try
    {
      MetalTheme myTheme =  new RecruitsTheme("gui/recruitsGui.theme");
      RecruitsLookAndFeel.setCurrentTheme(myTheme);
      UIManager.setLookAndFeel("com.armygame.recruits.gui.laf.RecruitsLookAndFeel");
    }
    catch(Exception e)
    {
      System.out.println("Couldn't set Recruits theme/look and feel");
      System.out.println(e);
    }

    mf = new MainFrame(this,sp);
    mf.setResizable(false);

    guimessager = mf.getMessager();

    // Temp Swing window output/input
    //new DirectorStandin().setVisible(true);
  }

  private void goGui()
  {
    guimessager.go();
  }

  public static RecruitsMain instance()
  //-----------------------------------
  {
     return me;
  }

  public static MainFrame getMainFrame()
  //-----------------------------------
  {
     return mf;
  }

  private final static String NOAUTOPLAY = "noauto";
  private final static String NOSOUNDEFFECTS = "nosoundeffects";
  private final static String SCENETEST = "scenetest";

  private void processArgs(String[] args)
  //-------------------------------------
  {
    boolean sctst = false;  // local for override
    for(int i=0;i<args.length;i++)
    {
      if(args[i].equalsIgnoreCase(NOAUTOPLAY))
        autoPlay = false;
      else if(args[i].equalsIgnoreCase(NOSOUNDEFFECTS))
        soundEffectsOn = false;
      else if(args[i].equalsIgnoreCase(SCENETEST))
      {
        sceneTest = true;
        sctst = true;
      }
    }
    if(sctst == true) autoPlay = false;
  }

  private void makeCannedChars()
  //----------------------------
  {
    Properties cdProps = new Properties();
    for(int i=0;i<20;i++)
    {
      try
      {
        SavedCharacter sc = (SavedCharacter)(Class.forName("com.armygame.recruits.globals.CannedChar"+i)).newInstance();
        String fn = sc.firstName+"_"+sc.lastName;
        cdProps.setProperty(fn,sc.bio);
        fn = fn+".sch";
        ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("./save/"+fn));
        ois.writeObject(sc);
        ois.close();
      }
      catch(Exception e)
      {
        if(i==0)
          System.out.println("error initting canned char "+i+" "+e);
        break;
      }
    }
    try
    {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/cdesc"));
      oos.writeObject(cdProps);
    }
    catch(Exception e)
    {
      System.out.println("Error writing charbios properties "+e);
    }
  }

  private void makeCannedGames()
  //----------------------------
  {
    Properties gameProps = new Properties();
    for(int i=0;i<20;i++)
    {
      try
      {
        SavedGame sg = (SavedGame)(Class.forName("com.armygame.recruits.globals.CannedGame"+i)).newInstance();
        String fn = sg.name+".sgm";
        ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("./save/"+fn));
        ois.writeObject(sg);
        ois.close();
        gameProps.setProperty(sg.name,sg.description);
      }
      catch(Exception e)
      {
        if(i==0)
          System.out.println("error initting canned game "+i+" "+e);
        break;
      }
      try
      {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/gdesc"));
        oos.writeObject(gameProps);
      }
      catch(Exception e)
      {
        System.out.println("Error writing chgamedesc properties "+e);
      }
    }
  }

  public static void main(String[] args)
  //------------------------------------
  {
    //for (int i=0; i < args.length; ++i)
	 //   System.out.println(args[i]);
    //System.out.println("Classpath:");
    //System.out.println(System.getProperty("java.class.path"));
  	 //for (int i=0; i < args.length; ++i)
	 //   System.out.println(args[i]);
	 //InetServices.autoUpdate();
    //System.out.println("Total memory: "+Runtime.getRuntime().totalMemory());
    //System.out.println("ha ha ha");
    //JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);

    new RecruitsMain(args);
  }
}

// EOF
