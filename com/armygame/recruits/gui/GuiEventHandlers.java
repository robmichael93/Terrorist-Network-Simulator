//
//  GuiEventHandlers.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Tue Mar 12 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;

import com.armygame.recruits.RecruitsMain;
import com.armygame.recruits.globals.SavedCharacter;
import com.armygame.recruits.globals.SavedGame;
import com.armygame.recruits.messaging.*;
import com.armygame.recruits.playlist.Playlist;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;
//import com.armygame.recruits.services.InetServices;
import com.armygame.recruits.services.ServiceThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

public class GuiEventHandlers
    /***************************/
{
  MainFrame main;

  public GuiEventHandlers(MainFrame mf)
      //===================================
  {
    main=mf;
    defCursor=main.getCursor();
    buildStateObjects();
/*
 new Thread(new Runnable()
{
   public void run()
   {
     try
     {
       Thread.sleep(60000);
       oobEventIn(ButtonFactory.ALERTMESSAGE,new AlertMessage(AlertMessage.PROMOTION_ALERT,
                                                            AlertMessage.HIGH_PRIORITY,
                                                            "e3","e4","promotion"));
       Thread.sleep(5000);
       oobEventIn(ButtonFactory.ALERTMESSAGE,new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                              AlertMessage.MEDIUM_PRIORITY,
                                                              33));

       Thread.sleep(5000);
       oobEventIn(ButtonFactory.ALERTMESSAGE,new AlertMessage(AlertMessage.PROMOTION_PROGRESS_ALERT,
                                                              AlertMessage.LOW_PRIORITY,
                                                              50));

       Thread.sleep(5000);
       oobEventIn(ButtonFactory.ALERTMESSAGE,new AlertMessage(AlertMessage.PROMOTION_ALERT,
                                                              AlertMessage.HIGH_PRIORITY,
                                                              "e4","e5","promotion"));
       //Thread.sleep(5000);
     }
     catch(Exception e)
     {
     }


   }
 }).start();
 */
  }

  GuiState currentState;
  RPanel currentPanel;

  StoryInlineEvent storyEvents = new StoryInlineEvent();  // used for chooser requests

  public void go()
      //--------------
  {
    // to start off
    currentState=mainShowing;
    stateStack.push(currentState);
    currentPanel=main.mainPanel;
    panelStack.push(currentPanel);
    currentState.go();
    currentPanel.go();
  }

  ////////////////////////////
  public Stack panelStack=new Stack();
  private boolean panelChange=false;
  private boolean lastPanelStaysActive=false;

  public void popPanel()
      //--------------------
  {
    popPanel(1);
  }

  public void popPanel(int n)
      //-------------------------
  {
    panelChange=true;
    while(n-->0)
      panelStack.pop();
  }

  public void nextPanel(RPanel p)
      //-----------------------------
  {
    nextPanel(p,false);
  }

  public void nextPanel(RPanel p,boolean keepLastPanelActive)
      //----------------------------------------------------------
  {
    lastPanelStaysActive=keepLastPanelActive;
    panelChange=true;
    panelStack.push(p);
  }

  ///////////////////////////

  public Stack stateStack=new Stack();
  boolean stateChange=false;

  private void nextState(GuiState st)
      //---------------------------------
  {
    stateChange=true;
    stateStack.push(st);
  }

  public void popState()
      //--------------------
  {
    popState(1);
  }

  private void popState(int n)
      //--------------------------
  {
    stateChange=true;
    while(n-->0)
      stateStack.pop();
  }

  /////////////////////////////////////////

  public void eventIn(int evNum)
      //----------------------------
  {
    eventIn(evNum,null);
  }

  // This is how we protect the event machine from being reentered
  synchronized public void eventIn(final int evNum,final Object obj)
      //----------------------------------------
  {
    // We run the event machine in the Swing event thread because of its obvious updating
    // of the gui.
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        _eventIn(evNum,obj);
      }
    });
  }


  private void _eventIn(int evNum,Object obj)
      //-----------------------------------------
  {
    boolean handled=currentState.eventHandled(evNum,obj);
    if(handled == false)
      System.out.println(ButtonFactory.names[evNum]+" event not handled, state = "+currentState.name());
    if(panelChange)
    {
      panelChange=false;
      main.panelSwitch=true;

      currentPanel.done();
      try
      {
        currentPanel=(RPanel)panelStack.peek();
      }
      catch(Exception e)
      {
        System.out.println("bad peek on panel stack");
      }
      main.lp.moveToFront(currentPanel);
      currentPanel.go();
    }
    if(stateChange)
    {
      stateChange=false;
      currentState.done();
      currentState=(GuiState)stateStack.peek();
      currentState.go();
    }

  }

  ///////////////////////////////////////////////

  // One oob event at a time
  synchronized public void oobEventIn(final int evNum,final Object obj)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        boolean handled=oobHandler.eventHandled(evNum,obj);
        if(handled == false)
          System.out.println(ButtonFactory.names[evNum]+" event not handled, oobState");
      }
    });
  }

  GuiState oobHandler;
  GuiState introPlaying,faceChooseShowing,/*nameChooseShowing,*/globeShowing,
           /*personalityShowing,*/financialsShowing, contactArmyShowing,
           startupNewShowing;
  GuiState valuesShowing,resourcesShowing,goalsShowing,
           goalTreeShowing,goalsListShowing,chooseGoalShowing,goalHelpShowing;

  GuiState mainShowing,historyShowing,achievementsShowing,stationsShowing,
           mapShowing,referralShowing,linksShowing,contactsShowing;

  GuiState saveCharShowing, loadGameShowing,saveGameShowing,
           uploadShowing, sceneTestShowing,
           uploadCharShowing, uploadStoryShowing,
           downloadShowing,updatesShowing,newUserShowing,/*loginShowing*/
           downloadCharShowing, downloadStoryShowing,
           internetWaitShowing,internetErrorShowing;

  GuiState scenePlaying,/*scenePaused,*/sceneRequested,messageShowing/*,cannedShowing*//*,cannedHelpShowing*/,
           chooserShowing, mosShowing, loadCharShowing;
  GuiState createSoldierShowing, loginOrRegisterShowing, optionsShowing;

  InteriorShowing interiorShowing; // oddball

  //JComponent afterLogin;
  //GuiState   afterLoginState;
  Cursor defCursor;

  private void saveNewUserData()
      //----------------------------
  {
    main.globals.userVitals.userID   =main.newUserPanel.handleTF.getText();
    main.globals.userVitals.password =main.newUserPanel.passwordTF.getText();
    main.globals.userVitals.firstName=main.newUserPanel.nameTF.getText();
    main.globals.userVitals.lastName =main.newUserPanel.nameTF.getText(); // fix this
    main.globals.userVitals.phone    =main.newUserPanel.phoneTF.getText();
    main.globals.userVitals.street1  =main.newUserPanel.street1TF.getText();
    main.globals.userVitals.street2  =main.newUserPanel.street2TF.getText();
    main.globals.userVitals.town     =main.newUserPanel.townTF.getText();
    main.globals.userVitals.state    =main.newUserPanel.stateTF.getText();
    main.globals.userVitals.zip      =main.newUserPanel.zipTF.getText();
    main.globals.userVitals.email    =main.newUserPanel.emailTF.getText();
  }

  boolean flashMessButtEnabled=false;
  boolean urgentMessButtEnabled=false;
  boolean normMessButtEnabled=false;

  public void disableAllButts()
  {
    setAllButts(false);
  }

  private void enableAllButts()
  {
    setAllButts(true);
  }

  private void setAllButts(boolean wh)
  {
    main.fileButt.setEnabled(wh);
    main.historyButt.setEnabled(wh);
    main.continueButt.setEnabled(wh);
    main.topPlayButt.setEnabled(wh);
    main.pauseButt.setEnabled(wh);
    main.topPauseButt.setEnabled(wh);
    main.charButt.setEnabled(wh);
    main.mapButt.setEnabled(wh);
    main.financialsButt.setEnabled(wh);
    main.contactArmyButt.setEnabled(wh);
    //main.contPlayOnButt.setEnabled(wh);
   // main.contPlayOffButt.setEnabled(wh);
    //main.referralButt.setEnabled(wh);
    //main.goArmyButt.setEnabled(wh);
    //main.amerArmyButt.setEnabled(wh);
    //main.contactsButt.setEnabled(wh);
    //main.linksButt.setEnabled(wh);

    if(wh == true && flashMessButtEnabled == true)
      main.flashMessButt.setEnabled(wh);
    else
      main.flashMessButt.setEnabled(false);
    if(wh == true && urgentMessButtEnabled == true)
      main.urgentMessButt.setEnabled(wh);
    else
      main.urgentMessButt.setEnabled(false);
    if(wh == true && normMessButtEnabled == true)
      main.normMessButt.setEnabled(wh);
    else
      main.normMessButt.setEnabled(false);
  }

  private void setScenePlayingButtons()
  {
    disableAllButts();
    main.continueButt.setVisible(false);
    main.topPlayButt.setVisible(false);
    main.pauseButt.setVisible(true);
    main.pauseButt.setEnabled(true);
    main.topPauseButt.setVisible(true);
    main.topPauseButt.setEnabled(true);
  }
  private void setScenePausingButtons()
  {
    main.pauseButt.setEnabled(false);
    main.topPauseButt.setEnabled(false);
    main.contPlayOffButt.setVisible(true);
    main.contPlayOnButt.setVisible(false);
  }
  private void setSceneStoppedButtons()
  {
    main.continueButt.setVisible(true);
    main.continueButt.setEnabled(true);
    main.topPlayButt.setVisible(true);
    main.topPlayButt.setEnabled(true);
    main.pauseButt.setVisible(false);
    main.topPauseButt.setVisible(false);
  }


  IntroPanel nip=null;


  // Various flags that are global to the state objects
  boolean endOfGameScenes=false;
  //boolean handleAutoPlay = true;          // chooserShowing


  private void buildStateObjects()
  {
    mainShowing=new GuiState("mainShowing","MAIN_STATLINE")
    {
      boolean firstRun = true;
      //boolean continueHitOnce=false;

      public void go()
      {
        /*
        if(RecruitsMain.instance().autoPlay == true)
        {
          if(continueHitOnce && !endOfGameScenes)
          {
            // This is how we implement autoplay
            Timer tim=new Timer(3000,new ActionListener()
            {
              public void actionPerformed(ActionEvent ev)
              {
                eventIn(ButtonFactory.MAINCONTINUE);
              }
            });
            tim.setRepeats(false);
            tim.start();
          }
        }
        */
        if(firstRun)
        {
          disableAllButts();
          firstRun = false;
        }
        else
          enableAllButts();
        if(main.gameChosen)
        {
          main.contPlayOffButt.setEnabled(true);
          main.contPlayOnButt.setEnabled(true);
        }
        main.setStatusLine(statusLine());
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINPLAYINTRO:
          nextPanel(main.introPanel); //main.introPanel.setVisible(true);
          nextState(introPlaying);
          //main.skipIntroButt.setEnabled(true);
          main.skipIntroButt.setEnabled(false);      // enable this in introPanel
          break;
        case ButtonFactory.MAINMAIN:
          nextPanel(main.filePanel);
          nextState(new FileMenuShowing());
          break;
        case ButtonFactory.MAINFINANCIALS:
          main.gmess.wantCharAttributes("charname");
          nextPanel(main.financialsPanel);
          nextState(financialsShowing);
          break;
        case ButtonFactory.MAINHISTORY:
          nextState(historyShowing);
          nextPanel(main.historyPanel);
          break;
        case ButtonFactory.MAINCHAR:
          nextState(globeShowing);
          main.gmess.wantCharAttributes("charname");
          nextPanel(main.charEditPanel);
          break;
        case ButtonFactory.MAINMAP:
//nextState(chooserShowing);
//nextPanel(main.chooserPanel);
//nextState(internetWaitShowing);
//nextPanel(main.inetWaitPanel); //,true);
//main.quipPanel.showQuip("This would be a clever witicism "+
//"generated by the Story Engine.");
 main.narratorPanel.showNarrator("This is the narrator panel",0);
          nextState(mapShowing);
          nextPanel(main.mapPanel);
          break;
        case ButtonFactory.MAINREFERRAL:
          nextState(referralShowing);
          nextPanel(main.referralPanel);
          break;
        case ButtonFactory.MAINCONTACTARMY:
          nextState(contactArmyShowing);
          nextPanel(main.contactArmyPanel);
          break;
          /*
        case ButtonFactory.MAINGOARMY:
          //try{edu.stanford.ejalbert.BrowserLauncher.openURL("http://www.goarmy.com");}catch(Exception ex){}
          try
          {
            BrowserLauncher.openURL("http://www.goarmy.com");
          }
          catch(Exception ex)
          {
          }
          enableAllButts();
          break;
        case ButtonFactory.MAINAMERARMY:
          //try{edu.stanford.ejalbert.BrowserLauncher.openURL("http://www.goarmy.com");}catch(Exception ex){}
          try
          {
            BrowserLauncher.openURL("http://www.americasarmy.com");
          }
          catch(Exception ex)
          {
          }
          enableAllButts();
          break;
          */
        case ButtonFactory.MAINHELP:
          try
          {
            BrowserLauncher.openURL("file://"+System.getProperty("user.dir")+"/help.html");
          }
          catch(Exception ex)
          {
          }
          enableAllButts();
          break;
        case ButtonFactory.MAINCONTINUE:
        case ButtonFactory.MAINTOPPLAY:
          if(storyEvents.checkAndHandle())    // chooser request
            break;
          if(RecruitsMain.instance().sceneTest == false)
          {
            nextState(sceneRequested);
            main.gmess.wantPlaylist(null);
          }
          else
          {
            nextState(sceneTestShowing);
            nextPanel(main.sceneTestPanel);
          }
          break;

        case ButtonFactory.MAINMESSAGEVIEW:
        case ButtonFactory.MAINMESSAGEFLASH:
        case ButtonFactory.MAINMESSAGEURGENT:
        case ButtonFactory.MAINMESSAGENORMAL:
          nextState(messageShowing);
          nextPanel(main.messagePanel);
          break;
        case ButtonFactory.CHOOSERSHOW:     // queued up
          main.chooserPanel.setMessage((AlertMessage)obj);
          nextState(chooserShowing);
          nextPanel(main.chooserPanel);
          break;

        default:
          return false; // not handled
        }
        return true;
      }
    };
    startupNewShowing=new GuiState("startupNewShowing","STARTUPNEW_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.STARTUPNEWGAME:
          // this is not right...need to create new soldier or load one
          nextState(createSoldierShowing);
          nextPanel(main.createSoldierPanel);
          break;
        case ButtonFactory.STARTUPLOADGAME:
          nextState(loadGameShowing);
          nextPanel(main.loadGamePanel);
          break;
        case ButtonFactory.STARTUPCOMPLETE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }

    };
 /*
    cannedShowing=new GuiState("cannedShowing","CANNED_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CANNED_DOUBLE_CLICKED:
          nextState(cannedHelpShowing);
          main.cannedHelpPanel.setData(obj);
          nextPanel(main.cannedHelpPanel);
          break;
        case ButtonFactory.CANNEDCANCEL:
          main.cannedCharPanel.cancel();
          // fall through
        case ButtonFactory.CANNEDSELECT:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
  */
    loadCharShowing=new GuiState("loadCharShowing","LOADCHAR_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.LOADCHAR_PROCEED:
          SavedCharacter sc = main.loadCharPanel.getLoadedCharacter();
          main.globals.updateValues(sc.charinsides);
          main.setActorIndex(sc.actorIndex);
          main.setCharName(sc.lastName);
          // should indicate a completely new char
          //main.gmess.charAttrToStoryEngine(main.globals.getLatest());
          // this does
          main.gmess.newCharToStoryEngine(main.globals.getLatest());
          // fall through
        case ButtonFactory.LOADCHAR_CANCEL:
          popPanel();
          popState();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    /*
    cannedHelpShowing=new GuiState("cannedHelpShowing","CANNED_HELP_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CANNEDHELPCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    */
    financialsShowing=new GuiState("financialsShowing","FINANCIALS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.FINANCIALSCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    introPlaying=new GuiState("introPlaying","INTRO_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINSKIPINTRO:
          main.introPanel.kill();
        case ButtonFactory.INTRODONE:
          main.gmess.wantCharAttributes("charname"); //playlist
          popState(); // don't want to come back here
          popPanel(); // don't want to come back here
          //nextState(faceChooseShowing);
          //nextState(createSoldierShowing);
          nextState(startupNewShowing);
          //nextState(fileMenuShowing);
          //nextPanel(main.charSelectPanel);
          //nextPanel(main.createSoldierPanel);
          nextPanel(main.startupNewPanel);
          //nextPanel(main.filePanel);
          main.skipIntroButt.setVisible(false);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
/*
    faceChooseShowing=new GuiState("faceChooseShowing","FACECHOOSE_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CHARSELECT0:
          return setSelected(0);
        case ButtonFactory.CHARSELECT1:
          return setSelected(1);
        case ButtonFactory.CHARSELECT2:
          return setSelected(2);
        case ButtonFactory.CHARSELECT3:
          return setSelected(3);
        case ButtonFactory.CHARSELECT4:
          return setSelected(4);
        case ButtonFactory.CHARSELECT5:
          return setSelected(5);
        case ButtonFactory.CREATEOK:
          popState(); // don't come back here
          nextState(nameChooseShowing);
          popPanel(); // don't come back here
          nextPanel(main.charNamePanel);
          break;
        case ButtonFactory.CHARSELECTLEFT:
        case ButtonFactory.CHARSELECTRIGHT:
          // handled within panel
          break;
        default:
          return false; // not handled
        }
        return true;
      }

      boolean setSelected(int i)
      {
        main.setActorIndex(i);
        main.charSelectPanel.selectedIndex=i;
        main.charSelectPanel.okButt.setEnabled(true);
        return true;
      }
    };
*/
    createSoldierShowing=new GuiState("createSoldierShowing","CREATESOLDIER_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CREATESOLDIERGOALS:
          nextState(goalsShowing);
          nextPanel(main.charGoalsPanel);
          break;
        case ButtonFactory.CREATESOLDIERRESOURCES:
          nextState(resourcesShowing);
          nextPanel(main.charResourcesPanel);
          main.charResourcesPanel.makeEditable(true);
          break;
        case ButtonFactory.CREATESOLDIERVALUES:
          nextState(valuesShowing);
          nextPanel(main.charValuesPanel);
          break;
        case ButtonFactory.CREATESOLDIERSAVE:
         // nextState(saveChar);
         // nextPanel(saveCharPanel);
          System.out.println("mike ... implement this");
          break;
        case ButtonFactory.CREATESOLDIERCANCEL:
          popPanel();
          popState();
          break;
        case ButtonFactory.CREATESOLDIERPROCEED:
          main.setActorIndex(main.createSoldierPanel.getActorIndex());
          main.setCharName(main.createSoldierPanel.getSoldierName());
          //main.gmess.charAttrToStoryEngine(main.globals.getLatest());
          main.gmess.newCharToStoryEngine(main.globals.getLatest());
          main.gameChosen = true;      // allows startupgame panel to go away
          popState();
          popPanel();
          break;
        }
        return true;
      }
    };
/*
    personalityShowing=new GuiState("personalityShowing","PERSONALITY_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CHARPERSSELECT:
          //nextState(cannedShowing);
          //nextPanel(main.cannedCharPanel);
          nextState(loadCharShowing);
          nextPanel(main.loadCharPanel);
          break;
        case ButtonFactory.CHARPERSSAVE:
          System.out.println("// todo: save created char");
          break;
        case ButtonFactory.CHARPERSFINI:
          main.gmess.charAttrToStoryEngine(main.globals.getLatest());
          popState();
          popPanel();
          break;

        case ButtonFactory.CHARPERSGOALS:
          nextState(goalsShowing);
          nextPanel(main.charGoalsPanel);
          break;
        case ButtonFactory.CHARPERSVALUES:
          nextState(valuesShowing);
          nextPanel(main.charValuesPanel);
          break;
        case ButtonFactory.CHARPERSRESOURCES:
          nextState(resourcesShowing);
          nextPanel(main.charResourcesPanel);
          main.charResourcesPanel.makeEditable(true);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
 */
 /*
    nameChooseShowing=new GuiState("nameChooseShowing","NAMECHOOSE_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.NAMEOK:
          main.setCharName(main.charNamePanel.getChosen());
          popState();		// don't come back here
          nextState(personalityShowing); //(globeShowing);
          popPanel(); // don't come back here
          nextPanel(main.charPersonalityPanel); //main.charEditPanel);
          //  main.charEditPanel.setVisible(true);
          //  main.charNamePanel.setVisible(false);
          break;

        default:
          return false; // not handled
        }
        return true;
      }
    };
 */
    globeShowing=new GuiState("globeShowing","GLOBE_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CHAREDITRES:
        case ButtonFactory.CHAREDITVALUES:
        case ButtonFactory.CHAREDITGOALS:

        // not used now
        case ButtonFactory.CHARHIT:
          interiorShowing.line=Ggui.getProp("INTERIOR_STATLINE");

          popState(); // don't come back here
          nextState(interiorShowing);
          popPanel(); // don't come back here
          nextPanel(main.charInteriorPanel);
          break;
        case ButtonFactory.CHAREDITVIEW:
          // set the read-only flag here (view only)
          interiorShowing.line=Ggui.getProp("INTERIORVIEW_STATLINE");

          popState(); // don't come back here
          nextState(interiorShowing);
          popPanel(); // don't come back here
          nextPanel(main.charInteriorPanel);
          break;
        case ButtonFactory.CHAREDITCLOSE:
          popState();
          popPanel();
          enableAllButts();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    interiorShowing=new InteriorShowing();

    valuesShowing=new GuiState("valuesShowing","VALUES_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CHARVALUESCANCEL:
          main.charValuesPanel.cancelling();
          break;
        case ButtonFactory.CHARVALUESOK:
          break;
          // These are handled within panel code, but put here
          //  to kill Sysout msg.
        case ButtonFactory.LOYALTYARROWLEFT:
        case ButtonFactory.LOYALTYARROWRIGHT:
        case ButtonFactory.DUTYARROWLEFT:
        case ButtonFactory.DUTYARROWRIGHT:
        case ButtonFactory.RESPECTARROWLEFT:
        case ButtonFactory.RESPECTARROWRIGHT:
        case ButtonFactory.SELFLESSARROWLEFT:
        case ButtonFactory.SELFLESSARROWRIGHT:
        case ButtonFactory.HONORARROWLEFT:
        case ButtonFactory.HONORARROWRIGHT:
        case ButtonFactory.INTEGRITYARROWLEFT:
        case ButtonFactory.INTEGRITYARROWRIGHT:
        case ButtonFactory.COURAGEARROWLEFT:
        case ButtonFactory.COURAGEARROWRIGHT:
          return true;
        default:
          return false; // not handled
        }
        popState();//main.charValuesPanel.setVisible(false);
        popPanel();//main.charInteriorPanel.setVisible(true);
        return true;
      }
    };

    resourcesShowing=new GuiState("resourcesShowing","RESOURCES_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.RESOURCESENERGYLEFT:
          main.charResourcesPanel.buttonHit(0,true);
          return true;
        case ButtonFactory.RESOURCESSTRENGTHLEFT:
          main.charResourcesPanel.buttonHit(1,true);
          return true;
        case ButtonFactory.RESOURCESKNOWLEDGELEFT:
          main.charResourcesPanel.buttonHit(2,true);
          return true;
        case ButtonFactory.RESOURCESSKILLLEFT:
          main.charResourcesPanel.buttonHit(3,true);
          return true;
        case ButtonFactory.RESOURCESFINANCIALLEFT:
          main.charResourcesPanel.buttonHit(4,true);
          return true;
        case ButtonFactory.RESOURCESPOPULARITYLEFT:
          main.charResourcesPanel.buttonHit(5,true);
          return true;
        case ButtonFactory.RESOURCESENERGYRIGHT:
          main.charResourcesPanel.buttonHit(0,false);
          return true;
        case ButtonFactory.RESOURCESSTRENGTHRIGHT:
          main.charResourcesPanel.buttonHit(1,false);
          return true;
        case ButtonFactory.RESOURCESKNOWLEDGERIGHT:
          main.charResourcesPanel.buttonHit(2,false);
          return true;
        case ButtonFactory.RESOURCESSKILLRIGHT:
          main.charResourcesPanel.buttonHit(3,false);
          return true;
        case ButtonFactory.RESOURCESFINANCIALRIGHT:
          main.charResourcesPanel.buttonHit(4,false);
          return true;
        case ButtonFactory.RESOURCESPOPULARITYRIGHT:
          main.charResourcesPanel.buttonHit(5,false);
          return true;

        case ButtonFactory.CHARRESOURCESCANCEL:		// should be "CLOSE" only
          break;
        case ButtonFactory.CHARRESOURCESOK:
          break;
        default:
          return false; // not handled
        }
        popState();
        popPanel();//main.charResourcesPanel.setVisible(false);
        //main.charInteriorPanel.setVisible(true);
        return true;
      }
    };

    goalsShowing=new GuiState("goalsShowing","GOALS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.GOALSCANCEL:
          main.charGoalsPanel.cancel();
          //fall through
        case ButtonFactory.GOALSOK:
          popState();
          popPanel();
          break;
        case ButtonFactory.GOALSVIEWALL:
          nextState(goalTreeShowing);
          nextPanel(main.charGoalsViewAllPanel);
          break;
        case ButtonFactory.GOALSLIST:
          nextState(goalsListShowing);
          nextPanel(main.charGoalsListPanel);
          break;
        case ButtonFactory.GOAL_1_LEFT:
        case ButtonFactory.GOAL_2_LEFT:
        case ButtonFactory.GOAL_3_LEFT:
        case ButtonFactory.GOAL_4_LEFT:
        case ButtonFactory.GOAL_5_LEFT:
        case ButtonFactory.GOAL_1_RIGHT:
        case ButtonFactory.GOAL_2_RIGHT:
        case ButtonFactory.GOAL_3_RIGHT:
        case ButtonFactory.GOAL_4_RIGHT:
        case ButtonFactory.GOAL_5_RIGHT:

        case ButtonFactory.GOAL_REMOVE:
        case ButtonFactory.GOAL_ADD:
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    goalsListShowing=new GuiState("goalTreeShowing","GOALSLIST_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
  //      case ButtonFactory.GOAL_DOUBLE_CLICKED:
  //        nextState(goalHelpShowing);
 //         main.charGoalsHelpPanel.setGoal(obj);
 //         nextPanel(main.charGoalsHelpPanel,true);
 //         main.charGoalsListPanel.inHelp=true;
 //         break;
        case ButtonFactory.GOALSLISTCANCEL:
          main.charGoalsListPanel.cancel();
          // fall through
        case ButtonFactory.GOALSLISTSELECT:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    goalHelpShowing=new GuiState("goalHelpShowing","GOAL_HELP_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.GOALHELPCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }

    };
    goalTreeShowing=new GuiState("goalTreeShowing","ALLGOALS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.GOAL_DOUBLE_CLICKED:
       //   nextState(goalHelpShowing);
       //   main.charGoalsHelpPanel.setGoal(obj);
       //   nextPanel(main.charGoalsHelpPanel);
          break;
        case ButtonFactory.GOALSVIEWALLCLOSE:
          popState();
          popPanel();
          //main.charGoalsPanel.setVisible(true);
          //main.charGoalsViewAllPanel.setVisible(false);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    chooseGoalShowing=new GuiState("chooseGoalShowing","CHOOSE_GOAL_STATLINE")
    {
      public void go()
      {
        super.go();
      }
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case 0:
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    historyShowing=new GuiState("historyShowing","HISTORY_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.HISTORYACHIEVEMENTS:
          popState();
          popPanel(); // don't come back here
          nextState(achievementsShowing);
          nextPanel(main.achievementsPanel);
          break;
        case ButtonFactory.HISTORYSTATIONS:
          popState();
          popPanel(); // don't come back here
          nextState(stationsShowing);
          nextPanel(main.dutyStationsPanel);
          break;
        case ButtonFactory.HISTORYCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    stationsShowing=new GuiState("stationsShowing","STATIONS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.STATIONSCLOSE:
          popState();
          popPanel();
          break;
        case ButtonFactory.HISTORYACHIEVEMENTS:
          popState();
          popPanel(); // don't come back here
          nextState(achievementsShowing);
          nextPanel(main.achievementsPanel);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    achievementsShowing=new GuiState("achievementsShowing","ACHIEVEMENTS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.ACHIEVEMENTSCLOSE:
          popState();
          popPanel();
          break;
        case ButtonFactory.HISTORYSTATIONS:
          popState();
          popPanel(); // don't come back here
          nextState(stationsShowing);
          nextPanel(main.dutyStationsPanel);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    mapShowing=new GuiState("mapShowing","MAP_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAPCLOSE:
          popState();
          popPanel();
          break;
        case ButtonFactory.MAPPOST_0:
        case ButtonFactory.MAPPOST_1:
        case ButtonFactory.MAPPOST_2:
        case ButtonFactory.MAPPOST_3:
        case ButtonFactory.MAPPOST_4:
        case ButtonFactory.MAPPOST_5:
        case ButtonFactory.MAPPOST_6:
        case ButtonFactory.MAPPOST_7:
        case ButtonFactory.MAPPOST_8:
        case ButtonFactory.MAPPOST_9:
        case ButtonFactory.MAPPOST_10:
        case ButtonFactory.MAPPOST_11:
        case ButtonFactory.MAPPOST_12:
        case ButtonFactory.MAPPOST_13:
        case ButtonFactory.MAPPOST_14:
        case ButtonFactory.MAPPOST_15:
        case ButtonFactory.MAPPOST_16:
        case ButtonFactory.MAPPOST_17:
        case ButtonFactory.MAPPOST_18:
        case ButtonFactory.MAPPOST_19:
        case ButtonFactory.MAPPOST_20:
        case ButtonFactory.MAPPOST_21:
        case ButtonFactory.MAPPOST_22:
        case ButtonFactory.MAPPOST_23:
        case ButtonFactory.MAPPOST_24:
          String s = getProp(ButtonFactory.names[evNum]+"_web");
          if(s != null && s != "")
            sendBrowser(s);
          break;
        case ButtonFactory.MAPALLBASES:       // handled in panel code
        case ButtonFactory.MAPMYBASES:
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    chooserShowing=new GuiState("chooserShowing","CHOOSER_STATLINE")
    {
      public void go()
      {
        super.go();
        /*
        if(handleAutoPlay)
        {
          continuousPlay = RecruitsMain.instance().autoPlay;
          RecruitsMain.instance().autoPlay = false;
          handleAutoPlay = false;
        }
        */
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINCONTINUE:
          break;
        case ButtonFactory.CHOOSERSELECT:
          String mos=main.chooserPanel.getSelectedTicket();
          if(mos == null) break;
          main.gmess.careerChoice(mos);

          popState();
          popPanel();

          if(storyEvents.checkAndHandle())    // another story event?
            break;

          if(RecruitsMain.instance().autoPlay == true)
          {
            nextState(sceneRequested);  // scene requested state
            nextPanel(main.mainPanel);  // main panel showing
            main.gmess.wantPlaylist(null); //playlist
          }
          else
            setSceneStoppedButtons();
          /*
            SwingUtilities.invokeLater(new Runnable()
            {
              public void run()
              {
                try{Thread.sleep(400);}catch(Exception e){}
                eventIn(ButtonFactory.MAINCONTINUE);
              }
            });
            */
          break;
        case ButtonFactory.MOSCLICKED:
          main.mosPanel.setData((String)obj);
          nextState(mosShowing);
          nextPanel(main.mosPanel);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    mosShowing=new GuiState("mosShowing","MOS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MOSCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    referralShowing=new GuiState("referralShowing","REFERRAL_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.REFERRALCANCEL:
          popState();
          popPanel();
          break;
        case ButtonFactory.REFERRALOK:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    linksShowing=new GuiState("linksShowing","LINKS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case 0:
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    newUserShowing=new GuiState("newUserShowing","NEWUSER_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {

        case ButtonFactory.NEWUSERCANCEL:
          popState(); // lose this one
          popPanel();
          popState(); // and upload or download
          popPanel();
          saveNewUserData();
          break;
        case ButtonFactory.NEWUSERSUBMITTED:
          saveNewUserData();
          main.getMessager().servicesNewUser(main.globals.userVitals);
          nextState(internetWaitShowing);
          nextPanel(main.inetWaitPanel); //,true);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    contactsShowing=new GuiState("contactsShowing","CONTACTS_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CONTACTSCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    loginOrRegisterShowing = new GuiState("loginOrRegisterShowing","LOGINORREG_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
         {
         case ButtonFactory.LOGINREG_LOGIN:
           popState(); // don't come back here
           popPanel();
           main.getMessager().servicesLogin(main.loginOrRegisterPanel.getLoginName(),
                                            main.loginOrRegisterPanel.getPassword());
           nextState(internetWaitShowing);
           nextPanel(main.inetWaitPanel); //,true);

           break;
         case ButtonFactory.LOGINREG_REGISTER:
           popState();  // don't come back here
           popPanel();  // ditto
           nextState(newUserShowing);
           nextPanel(main.newUserPanel);
           break;
         case ButtonFactory.LOGINREG_CANCEL:
           popState(); // lose current
           popPanel();
           popState(); // and one back (upload or download)
           popPanel();
           break;
         default:
           return false; // not handled
         }
         return true;
       }
    };

    /*
    loginShowing=new GuiState("loginShowing","LOGIN_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.LOGINNEWUSER:
          popState();
          popPanel();
          nextState(newUserShowing);
          nextPanel(main.newUserPanel);
          break;
        case ButtonFactory.LOGINCANCEL:
          popState(2);	// skip download, etc.
          popPanel(2);
          //afterLogin=null;
          //afterLoginState = null;
          break;
        case ButtonFactory.LOGINSUBMITTED:
          main.getMessager().servicesLogin(main.loginPanel.getLoginName(),
                                           main.loginPanel.getPassword());
          nextState(internetWaitShowing);
          nextPanel(main.inetWaitPanel); //,true);
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    */
    loadGameShowing=new GuiState("loadGameShowing","LOADGAME_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.LOADGAME_PROCEED:
          SavedGame sc = main.loadGamePanel.getLoadedGame();
          //main.setActorIndex(sc.actorIndex);
          //main.setCharName(sc.lastName);
          //main.globals.updateValues(sc.charinsides);

          main.gmess.loadedGameToStoryEngine(sc);

          // Neither of these may be required:
          //main.gmess.charAttrToStoryEngine(main.globals.getLatest());
          main.gmess.newCharToStoryEngine(main.globals.getLatest());
          // fall through
          main.gameChosen=true;
        case ButtonFactory.LOADGAME_CANCEL:
        //case ButtonFactory.LOADGAMECOMPLETE:
        //case ButtonFactory.LOADGAMELOAD:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    saveCharShowing=new GuiState("saveCharShowing","SAVECHAR_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.SAVECHARCANCEL:
          main.saveCharPanel.cancel();
          popState();
          popPanel();
          break;
        case ButtonFactory.SAVECHARSAVE:
        	 SavedCharacter sc = new SavedCharacter();
          sc.charinsides = main.globals.getLatest();
          sc.actorIndex = main.getActorIndex();
          sc.lastName = main.getCharName();
          if (main.saveCharPanel.saveCharacter(sc)) {
	          popState();
   	       popPanel();
   	    } else {
   	    	main.setStatusLine("Invalid file name.");
   	    }
          break;
        case ButtonFactory.SAVECHARDEL:
        	 main.saveCharPanel.delCharacter();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    saveGameShowing=new GuiState("saveGameShowing","SAVEGAME_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.SAVEGAMECANCEL:
          main.saveGamePanel.cancel();
          // fall through
        case ButtonFactory.SAVEGAMESAVE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    uploadShowing=new GuiState("uploadShowing","UPLOAD_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.UPLOADCLOSE:
        case ButtonFactory.UPLOADSUBMIT:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    uploadCharShowing=new GuiState("uploadCharShowing","UPLOADCHAR_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.UPLOADCHARCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }
        return true;
      }
    };
    uploadStoryShowing=new GuiState("uploadStoryShowing","UPLOADSTORY_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.UPLOADSTORYCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }
        return true;
      }
    };
    sceneTestShowing = new GuiState("sceneTestShowing","OPTIONS_STATLINE") // bogus statline
    {
      public boolean eventHandled(int evNum, Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.SCENETESTSCENECHOSEN:
          //popState();     // uncommenting these will return to mainpanel
          //popPanel();     // leaving them in will return to scenetest
          nextState(sceneRequested);
          main.gmess.wantPlaylist((String)obj);
          break;
        case ButtonFactory.SCENETESTCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }
        return true;
    }
    };
    optionsShowing = new GuiState("optionsShowing","OPTIONS_STATLINE")
    {
      boolean soundFX;
      public void go()
      {
        super.go();
        soundFX = RecruitsMain.instance().soundEffectsOn;
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.OPTIONSSOUNDISOFF:
          soundFX = true;
          break;
        case ButtonFactory.OPTIONSSOUNDISON:
          soundFX = false;
          break;
        case ButtonFactory.OPTIONSOK:
          RecruitsMain.instance().soundEffectsOn = soundFX;
        case ButtonFactory.OPTIONSCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }

        return true;
      }
    };
    contactArmyShowing=new GuiState("contactArmyShowing","CONTACTARMY_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.CONTACTARMYREFERRAL:
          try
          {
            BrowserLauncher.openURL("http://www.goarmy.com/contact/");
          }
          catch(Exception ex)
          {
          }
          popState();   // don't come back here
          popPanel();
          enableAllButts();
          //nextState(referralShowing);
          //nextPanel(main.referralPanel);
          break;
        case ButtonFactory.CONTACTARMYGOARMY:
          //try{edu.stanford.ejalbert.BrowserLauncher.openURL("http://www.goarmy.com");}catch(Exception ex){}
          try
          {
            BrowserLauncher.openURL("http://www.goarmy.com");
          }
          catch(Exception ex)
          {
          }
          popState();
          popPanel();
          enableAllButts();
          break;
        case ButtonFactory.CONTACTARMYAMERICASARMY:
          //try{edu.stanford.ejalbert.BrowserLauncher.openURL("http://www.goarmy.com");}catch(Exception ex){}
          try
          {
            BrowserLauncher.openURL("http://www.americasarmy.com");
          }
          catch(Exception ex)
          {
          }
          popState();
          popPanel();
          enableAllButts();
          break;
        case ButtonFactory.CONTACTARMYCANCEL:
          popState();
          popPanel();
          enableAllButts();
          break;
        default:
          return false;
        }
        return true;
      }
    };
    downloadShowing=new GuiState("downloadShowing","DOWNLOAD_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.DOWNLOADCLOSE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };
    downloadCharShowing=new GuiState("downloadCharShowing","DOWNLOADCHAR_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.DOWNLOADCHARCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }
        return true;
      }
    };
    downloadStoryShowing=new GuiState("downloadStoryShowing","DOWNLOADSTORY_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.DOWNLOADSTORYCANCEL:
          popState();
          popPanel();
          break;
        default:
          return false;
        }
        return true;
      }
    };

    updatesShowing=new GuiState("updatesShowing","UPDATES_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.UPDATEDO:
          // call into panel here
          main.updatesPanel.doUpdate();
          break;
        case ButtonFactory.UPDATECANCEL:
          main.updatesPanel.cancel();
          //fall through
        case ButtonFactory.UPDATEDONE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    internetWaitShowing=new GuiState("internetWaitShowing","INTERNETWAIT_STATLINE")
    {
      public void go()
      {
        super.go();
        defCursor=main.getCursor();
        main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        main.setCursor(defCursor);
        switch(evNum)
        {
        case ButtonFactory.ERROR:
          popState();   // Don't come back here
          popPanel();   // Ditto
          popState();   // and forget the target state, since we bombed
          popPanel();
          nextState(internetErrorShowing);
          nextPanel(main.inetErrorPanel);
          break;
        case ButtonFactory.ACK:
          main.globals.loggedIn=true;
        case ButtonFactory.INTERNETCANCEL:
        case ButtonFactory.INTERNETDONE:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    internetErrorShowing =new GuiState("internetErrorShowing","INTERNETERROR_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        main.setCursor(defCursor);
        switch(evNum)
        {
        case ButtonFactory.INTERNETERROROK:
          popState();
          popPanel();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    sceneRequested=new GuiState("sceneRequested","SCENEREQUESTED_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.PLAYLISTRECEIVED:
          popState();	// don't come back here // panel is still MainPanel
          main.playscenePanel.runPlaylist((Playlist)obj);
          nextState(scenePlaying);
          nextPanel(main.playscenePanel);  // this does mainPanel.done()
          break;
          // if no more scenes....
        case ButtonFactory.NOPLAYLISTRECEIVED:
          endOfGameScenes=true;
          //popPanel();  don't do this, panel is still MainPanel
          popState();
          break;
        default:
          return false; // not handled
        }
        return true;
      }
    };

    scenePlaying=new GuiState("scenePlaying","SCENEPLAYING_STATLINE")
    {
      public void go()
      {
        super.go();
        setScenePlayingButtons();
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINPAUSE:
        case ButtonFactory.MAINTOPPAUSE:
          // just toggle the pause flag
          //nextState(scenePaused);
          //main.playbackPanel.pause();
          RecruitsMain.instance().autoPlay = false;
          setScenePausingButtons();
          break;
        case ButtonFactory.PLAYLISTFINISHED:
          popState();     //back to mainPanel
          popPanel();     // back to mainPanel

          if(storyEvents.checkAndHandle())              // chooser show?
            break;

          if(RecruitsMain.instance().autoPlay == true)
          {
            nextState(sceneRequested);
            nextPanel(main.mainPanel);
            main.gmess.wantPlaylist(null); //playlist
          }
 /*         {
            // This is how we implement autoplay
             Timer tim=new Timer(1000,new ActionListener()
             {
               public void actionPerformed(ActionEvent ev)
               {
                 eventIn(ButtonFactory.MAINCONTINUE);
               }
             });
             tim.setRepeats(false);
             tim.start();
          }
*/
          else
            setSceneStoppedButtons();

          break;
        case ButtonFactory.INTRODONE:
          main.introPanel.setVisible(false);
          break;

        default:
          return false; // not handled
        }
        return true;
      }
    };
/*
    scenePaused=new GuiState("scenePaused","SCENEPAUSED_STATLINE")
    {
      public void go()
      {
        super.go();
        main.continueButt.setVisible(true);
        main.continueButt.setEnabled(true);
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINCONTINUE:
          popState();
          main.continueButt.setEnabled(false);
          main.continueButt.setVisible(false);
          break;

        default:
          return false; // not handled
        }
        return true;
      }
    };
*/
    messageShowing=new GuiState("messageShowing","MESSAGESHOWING_STATLINE")
    {
      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MESSAGESHOWINGCLOSE:
          if(main.messagePanel.hasMessage() == false)
          {
            normMessButtEnabled=false;
            flashMessButtEnabled=false;
            urgentMessButtEnabled=false;
          }
          popState();
          popPanel();
          break;
        case ButtonFactory.MESSAGESHOWINGNEXT:
          break;    // also handled by MessagePanel
        default:
          return false; // not handled
        }
        return true;
      }
    };
    // Not really a state...handles alert messages anytime they're received.

    oobHandler=new GuiState("oobHandler","OOB_STATLINE")
    {
      public void go()
      {
      }

      public boolean eventHandled(int evNum,Object obj)
      {
        switch(evNum)
        {
        case ButtonFactory.MAINDEPTHMETER_FOCUS:
          main.meterFocusButt.setVisible(false);
          main.meterOverviewButt.setVisible(false);
          main.meterMediumButt.setVisible(true);
          main.gmess.setGameSpeed(GameSpeedMessage.MEDIUM);
          break;
        case ButtonFactory.MAINDEPTHMETER_MEDIUM:
          main.meterMediumButt.setVisible(false);
          main.meterFocusButt.setVisible(false);
          main.meterOverviewButt.setVisible(true);
          main.gmess.setGameSpeed(GameSpeedMessage.OVERVIEW);

          break;
        case ButtonFactory.MAINDEPTHMETER_OVERVIEW:
          main.meterOverviewButt.setVisible(false);
          main.meterMediumButt.setVisible(false);
          main.meterFocusButt.setVisible(true);
          main.gmess.setGameSpeed(GameSpeedMessage.FOCUS);
          break;
        case ButtonFactory.MAINHELP:
          try
          {
            BrowserLauncher.openURL("file://"+System.getProperty("user.dir")+"/help.html");
          }
          catch(Exception ex)
          {
          }
          break;
        case ButtonFactory.MAINCONTPLAYON:
          RecruitsMain.instance().autoPlay = false;
          main.contPlayOnButt.setVisible(false);
          main.contPlayOffButt.setVisible(true);
          break;
        case ButtonFactory.MAINCONTPLAYOFF:
          if(RecruitsMain.instance().sceneTest == false)
            RecruitsMain.instance().autoPlay = true;       // don't do autoplay if scenetest mode
          main.contPlayOnButt.setVisible(true);
          main.contPlayOffButt.setVisible(false);
          break;
        case ButtonFactory.ALERTMESSAGE:
          AlertMessage am=(AlertMessage)obj;

          switch(am.getAlertType())
          {
          case AlertMessage.CUE_CHOOSER_ALERT:
            //main.mainPanel.setNextEvent(ButtonFactory.CHOOSERSHOW,am);
            storyEvents.queueEvent(ButtonFactory.CHOOSERSHOW,am);
            break;
          case AlertMessage.DISPLAY_QUIP:
            main.quipPanel.showQuip(am.getQuip());
            break;
          case AlertMessage.DISPLAY_NARRATOR:
            main.narratorPanel.showNarrator(am.getNarratorText(),am.getNarratorImageIndex());
          case AlertMessage.PROMOTION_ALERT:
            main.rankPanel.setCurrentRank(am.getCurrentRank());
            main.rankPanel.setNextRank(am.getNextRank());
            main.rankPanel.setProgress(0);
            main.messagePanel.inComingMessage(am);

            main.normMessButt.setEnabled(true);
            normMessButtEnabled=true;
            break;
          case AlertMessage.PROMOTION_PROGRESS_ALERT:
            main.rankPanel.setProgress(am.getProgress());
            break;
          case AlertMessage.MESSAGE_ALERT:
            if(am.getPriority() == AlertMessage.HIGH_PRIORITY)
            {
              main.flashMessButt.setEnabled(true);
              main.messageViewButt.setEnabled(true);
              flashMessButtEnabled=true;
            }
            else if(am.getPriority() == AlertMessage.MEDIUM_PRIORITY)
            {
              main.urgentMessButt.setEnabled(true);
              main.messageViewButt.setEnabled(true);
              urgentMessButtEnabled=true;

            }
            else if(am.getPriority() == AlertMessage.LOW_PRIORITY)
            {
              main.normMessButt.setEnabled(true);
              main.messageViewButt.setEnabled(true);
              normMessButtEnabled=true;
            }
            main.messagePanel.inComingMessage((AlertMessage)obj);
            return true;
          default:
            return false; // not handled
          }
          break;

        default:
          return false; // not handled
        }
        return true;
      }
    };
  }
  
  class FileMenuShowing extends GuiState {
  
    FileMenuShowing() {
    	super("fileMenuShowing","FILEMENU_STATLINE");
    }
    
		public boolean eventHandled(int evNum,Object obj)	{
		  switch(evNum) {
		  case ButtonFactory.FILENEW:
		  case ButtonFactory.FILENEWCANCEL:
		    break;
		  case ButtonFactory.FILENEWCHAR:
		    popState(); // don't come back here
		    popPanel();
		    nextState(createSoldierShowing);
		    nextPanel(main.createSoldierPanel);
		    break;
		  case ButtonFactory.FILENEWEXISTING:
		    //popState(); // don't come back here
		    //popPanel();
		    nextState(loadCharShowing);
		    nextPanel(main.loadCharPanel);
		    break;
		  case ButtonFactory.FILELOAD:
		  case ButtonFactory.FILELOADCANCEL:
		    break;
		  case ButtonFactory.FILELOADCHAR:
		    //popState(); // don't come back here
		    //popPanel();
		    nextState(loadCharShowing);
		    nextPanel(main.loadCharPanel);
		    break;
		  case ButtonFactory.FILELOADGAME:
		    //popState(); // don't come back here
		    //popPanel();
		    nextState(loadGameShowing);
		    nextPanel(main.loadGamePanel);
		    break;
		  case ButtonFactory.FILEOPTIONS:
		    nextState(optionsShowing);
		    nextPanel(main.optionsPanel);
		    break;
		  case ButtonFactory.FILESAVE:  // handled internal to panel
		  case ButtonFactory.FILESAVECANCEL:
		    break;                      // to avoid the error msg
		  case ButtonFactory.FILESAVECHAR:
		    //popState(); // don't come back here
		    //popPanel();
		    nextState(saveCharShowing);
		    nextPanel(main.saveCharPanel);
		    break;
		  case ButtonFactory.FILESAVEGAME:
		    //popState(); // don't come back here
		    //popPanel();
		    nextState(saveGameShowing);
		    break;
		  case ButtonFactory.FILEUPLOAD:
		    break;
		  case ButtonFactory.FILEUPLOADCHAR:
		    //popState(); // don't come back here
		    //popPanel();
		    if(main.globals.loggedIn)
		    {
		      nextState(uploadCharShowing);
		      nextPanel(main.uploadCharPanel);
		    }
		    else
		    {
		      nextState(uploadCharShowing);
		      nextState(loginOrRegisterShowing);
		      nextPanel(main.uploadCharPanel);
		      nextPanel(main.loginOrRegisterPanel);
		    }
		    break;
		  case ButtonFactory.FILEUPLOADSTORY:
		    //popState(); // don't come back here
		    //popPanel();
		    if(main.globals.loggedIn)
		    {
		      nextState(uploadStoryShowing);
		      nextPanel(main.uploadStoryPanel);
		    }
		    else
		    {
		      nextState(uploadStoryShowing);
		      nextState(loginOrRegisterShowing);
		      nextPanel(main.uploadStoryPanel);
		      nextPanel(main.loginOrRegisterPanel);
		    }
		    break;
		  case ButtonFactory.FILEUPLOADCANCEL:
		    break;
		  case ButtonFactory.FILEDOWNLOAD:
		    break;
		  case ButtonFactory.FILEDOWNLOADCANCEL:
		    break;
		  case ButtonFactory.FILEDOWNLOADSTORY:
		    //popState(); // don't come back here
		    //popPanel();
		    if(main.globals.loggedIn)
		    {
		      nextState(downloadStoryShowing);
		      nextPanel(main.downloadStoryPanel);
		    }
		    else
		    {
		      nextState(downloadStoryShowing);
		      nextState(loginOrRegisterShowing);
		      nextPanel(main.downloadStoryPanel);
		      nextPanel(main.loginOrRegisterPanel);
		    }
		    break;
		  case ButtonFactory.FILEDOWNLOADCHAR:
		    //popState(); // don't come back here
		    //popPanel();
		    if(main.globals.loggedIn)
		    {
		      nextState(downloadCharShowing);
		      nextPanel(main.downloadCharPanel);
		    }
		    else
		    {
		      nextState(downloadCharShowing);
		      nextState(loginOrRegisterShowing);
		      nextPanel(main.downloadCharPanel);
		      nextPanel(main.loginOrRegisterPanel);
		    }
		    break;
		  case ButtonFactory.FILEUPDATE:
		    //popState();	// don't come back here
		    //popPanel();
		    	// user didn't cancel but update didn't work???
		    	nextState(updatesShowing);
		    	nextPanel(main.updatesPanel);
		    break;
		  case ButtonFactory.FILECANCEL:
		    popState();
		    popPanel();
		    break;
		  case ButtonFactory.FILEOPS:
		    nextState(new OpsLoginShowing());
		    nextPanel(main.loginOrRegisterPanel);
		    break;
		  case ButtonFactory.FILEEXIT:
		    System.exit(0);
		    break;
		  default:
		    return false; // not handled
		  }
		  return true;
		}  
  }

  class OpsLoginShowing extends GuiState {

  	OpsLoginShowing() {
  		super("opsLoginShowing","OPSLOGIN_STATLINE");
  	}

		public boolean eventHandled(int evNum,Object obj) {
			switch(evNum) {
			case ButtonFactory.LOGINREG_LOGIN:
			  main.getMessager().opsLogin(main.loginOrRegisterPanel.getLoginName(),
			                                   main.loginOrRegisterPanel.getPassword());
			  break;
			case ButtonFactory.SERVICESTARTED:
				ServiceStartedMessage msg = (ServiceStartedMessage)obj;
			  nextState(new LoginWaitShowing(msg.getServiceThread()));
			  nextPanel(main.inetWaitPanel);
			  break;
			case ButtonFactory.LOGINREG_CANCEL:
			  popState(); // lose current
			  popPanel();
			  break;
			default:
			  return false; // not handled
			}
			return true;
			}
		}

  class LoginWaitShowing extends GuiState {
		ServiceThread serviceThread;
		
  	LoginWaitShowing(ServiceThread serviceThread) {
  		super("loginWaitShowing","INTERNETWAIT_STATLINE");
  		this.serviceThread = serviceThread;
  	}

		public void go() {
		  super.go();
		  defCursor=main.getCursor();
		  main.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		public boolean eventHandled(int evNum,Object obj) {
      
		  main.setCursor(defCursor);
		  switch(evNum)
		  {
		  case ButtonFactory.ERROR:
		    popState();   // Don't come back here
		    popPanel();   // Ditto
		    nextState(new MsgShowing());
		    main.msgPanel.setMsg(new String[]{"Error contacting Ops",
                                          "game server.",
                                          ((ErrorMessage)obj).errorInfo});;
		    nextPanel(main.msgPanel);
		    break;
		  case ButtonFactory.ACK:
        popState();      // don't come back here
		    popPanel();
        popState();      // and skip login data entry
        popPanel();
		    nextState(new MsgShowing());
		  	String[] goNoGo = new String[]{"Your game status has been",
		    	"transferred to the Operations", "Server.", "Click OK"};
		    main.msgPanel.setMsg(goNoGo);
		    nextPanel(main.msgPanel);
		    break;
		  case ButtonFactory.INTERNETCANCEL:
		  	serviceThread.setCancel(true);
		    popState();
		    popPanel();
		    break;
		  default:
		    return false; // not handled
		  }
		  return true;
		}
	}

	class MsgShowing extends GuiState {

		MsgShowing() {
			super("msgShowing","INTERNETERROR_STATLINE");
		}

		public boolean eventHandled(int evNum,Object obj) {
		  main.setCursor(defCursor);
		  switch(evNum)
		  {
		  case ButtonFactory.MSG_OK:
		    popState();
		    popPanel();
		    break;
		  default:
		    return false; // not handled
		  }
		  return true;
		}
	}


  private void sendBrowser(String where)
  {
    try
    {
      BrowserLauncher.openURL(where);
    }
    catch(Exception ex)
    {
    }
  }

  private String getProp(String p)
  {
    return Ggui.getProp(p);
  }

  class InteriorShowing extends GuiState
  {
    InteriorShowing()
    {
      super("interiorShowing","");
    }
    public String line;

    public String statusLine()
    {
      return line;
    }

    public boolean eventHandled(int evNum,Object obj)
    {
      switch(evNum)
      {
      case ButtonFactory.CHARINTCLOSE:
        main.gmess.charAttrToStoryEngine(main.globals.getLatest());
      case ButtonFactory.CHARINTCANCEL:
        popState();
        popPanel();
        break;
      case ButtonFactory.CHARINTGOALS:
        nextState(goalsShowing);
        nextPanel(main.charGoalsPanel);
        break;
      case ButtonFactory.CHARINTVALUES:
        nextState(valuesShowing);
        nextPanel(main.charValuesPanel);
        break;
      case ButtonFactory.CHARINTRESOURCES:
        nextState(resourcesShowing);
        nextPanel(main.charResourcesPanel);
        main.charResourcesPanel.makeEditable(false);
        break;

      default:
        return false; // not handled
      }
      return true;
    }
  }

  abstract class GuiState
  {
    String name;
    String statlineProp;
    GuiState(String name, String statlineProp)
    {
      this.name = name;
      this.statlineProp = statlineProp;
    }

    abstract public boolean eventHandled(int evNum,Object obj);

    public String statusLine()
    {
      return Ggui.getProp(statlineProp);
    }
    public void go()
    {
      main.setStatusLine(statusLine());
      disableAllButts();
    }
    public String name()
    {
      return name;
    }
    public void done()
    {
    }
    public String toString()
    {
      return name();
    }
  }
  class StoryInlineEvent
  {
    Vector evV = new Vector();
    Vector obV = new Vector();

    public void queueEvent(int evnum, Object o)
    {
      evV.add(new Integer(evnum));
      obV.add(o);
    }
    public boolean checkAndHandle()
    {
      if(!evV.isEmpty())
      {
        int evn = ((Integer)(evV.remove(0))).intValue();
        Object o = obV.remove(0);
        eventIn(evn,o);         // gets cued on Swing Event thread
        return true;
      }
      return false;
    }

  }

} // GuiEventHandler
