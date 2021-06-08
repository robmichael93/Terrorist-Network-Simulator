/******************************
 * File:	StoryEngineMessaging.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Mon Feb 04 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits;

import com.armygame.recruits.messaging.*;
import com.armygame.recruits.playlist.*;
import com.armygame.recruits.storyelements.sceneelements.CharInsides;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;

// This class provides the StoryEngine system with methods to send, and an interface to
// receive messages from the other two parts of the game.

public class StoryEngineMessaging implements RmessageListener
/***********************************************************/
{
  RecruitsMessager messager;
  StoryEngineMessaging()
  //====================
  {
    messager = RecruitsMain.instance().messager;
  }
  public void go()
  {
    messager = RecruitsMain.instance().messager;
    initGuiQueue();
  }
  // Messages from story engine
  public void loadPlayList(Playlist playlist)
  //----------------------------------------------------
  {
    //messager.storyEngineDispatchMsg(new LoadPlaylistMessage(playlist));
    LoadPlaylistMessage m = new LoadPlaylistMessage(playlist);
    messager.storyEngineDispatchMsg(m);
  }
  public void sendNoPlaylist()
  {
    messager.storyEngineDispatchMsg(new NoPlayListMessage());
  }

  public void sendCharAttributes(CharInsides charInner)
  //----------------------------------------------------
  {
    messager.storyEngineDispatchMsg(new CharAttributesMessage(charInner));
  }

  public void sendAlertMessage(AlertMessage am)
  {
    messager.storyEngineDispatchMsg(new AlertRMessage(am));
  }

  public void uploadChar(/*CharData*/ Object chard)
  //-----------------------------------------------
  {
    messager.storyEngineDispatchMsg(new UpLoadCharDataMessage(chard));
  }

  public void uploadStory(/*StoryData*/ Object storyd)
  //----------------------------------------------------
  {
    messager.storyEngineDispatchMsg(new UpLoadStoryDataMessage(storyd));
  }

  // Messages to Story Engine
  public void storyEngineIncoming(Rmessage msg)
  //------------------------------------------
  {
  System.out.println("StoryEngineMessaging got msg: "+msg);
    switch (msg.getType())
    {
    case Rmessage.tGETPLAYLIST:
      // put callout to StoryEngine code here, eg:
      String sceneName = ((GetPlaylistMessage)msg).sceneName;
      RecruitsMain.instance().storyengine.getNextPlaylist(sceneName);
      break;
    case Rmessage.tLOADCHARDATA:				// comes from a download
      // put callout to StoryEngine code here, eg:
      // storyengine.loadCharHandler(msg.toObject);
      break;
    case Rmessage.tLOADSTORYDATA:				// comes from a download
      // put callout to StoryEngine code here, eg:
      // loadStoryHandler(msg.toObject);
      break;
    case Rmessage.tUPLOADSTORY:					// a command from director
      // put callout to StoryEngine code here, eg:
      // uploadStoryHandler(msg.toObject);
      break;
    case Rmessage.tUPLOADCHAR:					// a command from director
      // put callout to StoryEngine code here, eg:
      // uploadCharHandler(msg.toObject);
      break;
    case Rmessage.tNAVIGATE:      				// FYI from director
      System.out.println("StoryEngineMessaging handling NAVIGATE msg");

      if(((NavigateMessage)msg).command.equals("EXIT"))
      {
        System.out.println("Got NAVIGATE/EXIT");
        System.exit(0);
      }
      // put callout to StoryEngine code here, eg:
      // navigateHandler(msg.toObject);			// builds Navigate object
      break;
    case Rmessage.tINVALIDATEPLAYLIST:		// a cmd from director
      System.out.println("StoryEngineMessaging handling INVALIDATEPLAYLIST msg");
      // invalidatePlaylistHandler();		// no param
      break;
    case Rmessage.tPLAYLISTCOMPLETE:
      System.out.println("StoryEngineMessaging handling PLAYLISTCOMPLETE msg");
      PlaylistCompleteMessage pmsg = (PlaylistCompleteMessage)msg;
      // playlistcompleteHandler(pmsg.reason, pmsg.stateChanges);   // sig: (String reason, ArrayList stateChanges)
      break;
    case Rmessage.tWANTCHARATTRIBUTES:
      System.out.println("StoryEngineMessaging handling WANTCHARATTRIBUTES msg");
      RecruitsMain.instance().storyengine.sendCharacterAttributes();
      break;
    case Rmessage.tCHARATTRIBUTES:
      CharAttributesMessage camsg = (CharAttributesMessage)msg;
      RecruitsMain.instance().storyengine.receivedCharacterAttributes(camsg.toCharObj());
      break;
    case Rmessage.tNEWCHARACTER:
      NewCharacterMessage ncmsg = (NewCharacterMessage)msg;
      RecruitsMain.instance().storyengine.receivedNewCharacter(ncmsg.toCharObj());
      break;
    case Rmessage.tCAREERCHOICE:
      CareerChoiceMessage ccmsg = (CareerChoiceMessage)msg;
      RecruitsMain.instance().storyengine.receivedCareerChoice(ccmsg.getChoice());
      break;
    case Rmessage.tGAMESPEED:
      GameSpeedMessage gsmsg = (GameSpeedMessage)msg;
      RecruitsMain.instance().storyengine.receivedGameSpeed(gsmsg.getValue());
      break;
    default:
//      throw new RuntimeException("Unknown message type in StoryEngineMessaging");
      System.out.println("StoryEngineMessaging got unknown msg type: "+msg);
    }
  }
  private Ithread guiThread;
  private com.armygame.recruits.services.SimpleObjectFIFO queue;

  private void initGuiQueue()
  {
    queue = new com.armygame.recruits.services.SimpleObjectFIFO(5);
    // todo: put FIFO in better package
    guiThread = new Ithread();
    guiThread.start();
  }

  // from RecruitsMessager:
  public void messageIn(Rmessage msg)
    //---------------------------------
  {
    do
    {
      try
    {
      queue.add(msg);			// will block here if queue is full
      return;
    }
      catch(InterruptedException e){}
    }while (true);
  }

  class Ithread extends Thread
    /**************************/
  {
    volatile boolean fatal = false;
    public void run()
      //---------------
    {
      while(!fatal)
      {
        try
      {
        Rmessage msg = (Rmessage)queue.remove();
        storyEngineIncoming(msg);
      }
        catch(InterruptedException e){}
      }
    }
    public void killme()
      //..................
    {
      fatal = true;
      queue.notify();
    }
  }
}
// EOF
