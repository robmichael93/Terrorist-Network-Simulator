/******************************
 * File:	RecruitsMessager.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Wed Jan 23 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;

import com.armygame.recruits.*;
import com.armygame.recruits.services.InetServices;
import com.armygame.recruits.playlist.Playlist;
import com.armygame.recruits.storyelements.sceneelements.*;
//import com.armygame.recruits.localhostio.*;
import java.util.StringTokenizer;
import com.armygame.recruits.gui.*;

public class RecruitsMessager
/***************************/
{
  InetServices inet;
  //LHIOMessageWrapper lhio;
  RecruitsGuiMessager gui;
  StoryEngineMessaging story;

  public RecruitsMessager()
  {
    // Our other two pieces must be in place first
    RecruitsMain main = RecruitsMain.instance();
    inet = main.services;
    //lhio = main.localhostio;
    story = main.storyenginemessaging;
    gui = main.guimessager;

    if( inet == null || gui == null /*lhio == null*/ || story == null)
      throw new IllegalStateException("InetServices, LHIOMessageWrapper and StoryEngineMessaging"+
        " must be instantiated before RecruitsMessager.");

    plumbThisSystem();
  }

  private void plumbThisSystem()
  //----------------------------
  {
/*
    // Set up handlers for messages incoming from Java
    // Director-to-Inet
    LHIOMessageListener straightToInet = new LHIOMessageListener()
    //............................................................
    {
      // Messages from Director which go to the inet services module come here
      public void lineReceiver(String typ, StringTokenizer s)
      {
        Rmessage m = Rmessage.makeMessage(typ,s);
        switch (m.getType(typ))
        {
        case Rmessage.tGETCHARLIST:
        case Rmessage.tGETSTORYLIST:
        case Rmessage.tGETUPDATELIST:
        case Rmessage.tGETUPDATE:
        case Rmessage.tLOADSTORY:	// response goes to storyengine
        case Rmessage.tLOADCHAR:	// response goes to storyengine
        case Rmessage.tLOGIN:
        case Rmessage.tLOGOFF:
        case Rmessage.tNEWUSER:
        case Rmessage.tREFERRAL:
          if(inet.isOnline())
            inet.makeRequest(m);
          else
            System.out.println("Inet not online.  Discard "+typ);
          break;
        default:
          throw new RuntimeException("Unexpected message type ("+typ+") in RecruitsMessager.toInet()");
        }
      }
    };
    lhio.setReceiver(Rmessage.GETCHARLIST, straightToInet);
    lhio.setReceiver(Rmessage.GETSTORYLIST, straightToInet);
    lhio.setReceiver(Rmessage.GETUPDATELIST, straightToInet);
    lhio.setReceiver(Rmessage.GETUPDATE, straightToInet);
    lhio.setReceiver(Rmessage.LOADSTORY, straightToInet);	// response goes to storyengine
    lhio.setReceiver(Rmessage.LOADCHAR, straightToInet);	// response goes to storyengine
    lhio.setReceiver(Rmessage.LOGIN, straightToInet);
    lhio.setReceiver(Rmessage.LOGOFF, straightToInet);
    lhio.setReceiver(Rmessage.NEWUSER, straightToInet);
    lhio.setReceiver(Rmessage.REFERRAL, straightToInet);

    //Director-to-Story Engine
    LHIOMessageListener toStoryEngine = new LHIOMessageListener()
    //...........................................................
    {
      // Messages from GUI which go to the Story Engine come here
      public void lineReceiver(String typ, StringTokenizer s)
      {
        Rmessage msg = Rmessage.makeMessage(typ,s);

        switch (msg.getType(typ))
        {
        case Rmessage.tNAVIGATE:
        case Rmessage.tUPLOADCHAR:
        case Rmessage.tUPLOADSTORY:
        case Rmessage.tGETPLAYLIST:
        case Rmessage.tINVALIDATEPLAYLIST:
        case Rmessage.tPLAYLISTCOMPLETE:
        case Rmessage.tWANTCHARATTRIBUTES:
        case Rmessage.tCHARATTRIBUTES:
          story.messageIn(msg);		// uses current data
          break;
        default:
          throw new RuntimeException("Unexpected message type ("+typ+") in RecruitsMessager.toStoryEngine.");
        }
      }
    };
    lhio.setReceiver(Rmessage.NAVIGATE, toStoryEngine);
    lhio.setReceiver(Rmessage.UPLOADCHAR, toStoryEngine);	// responds with uploadchardata
    lhio.setReceiver(Rmessage.UPLOADSTORY, toStoryEngine);	// responds with uploadstorydata
    lhio.setReceiver(Rmessage.GETPLAYLIST, toStoryEngine);
    lhio.setReceiver(Rmessage.INVALIDATEPLAYLIST, toStoryEngine);
    lhio.setReceiver(Rmessage.PLAYLISTCOMPLETE, toStoryEngine);
    lhio.setReceiver(Rmessage.WANTCHARATTRIBUTES, toStoryEngine);
    lhio.setReceiver(Rmessage.CHARATTRIBUTES, toStoryEngine);

    // Heartbeat from director
    LHIOMessageListener heartbeatlistener = new LHIOMessageListener()
    //---------------------------------------------------------------
    {
      // Heartbeat messages from director come here
      public void lineReceiver(String typ, StringTokenizer s)
      {
        // Do something with received heartbeats.
      }
    };

    lhio.setReceiver(Rmessage.HEARTBEAT, heartbeatlistener);
*/
  }
 /* put this in the RecruitsMessager*/
  public void doHeartbeat()
  //-----------------------
  {
    //final HeartbeatMessage hb = new HeartbeatMessage();
/*
    new Thread(new Runnable()
    {
      public void run()
      {
        while (true)
        {
          try {Thread.sleep(10000);}catch(InterruptedException e){}
          lhio.send(new HeartbeatMessage().toTSV());
        }
      }
    },"Heartbeat").start();
*/
  }

  public void guiDispatchMsg(Rmessage msg)
  {
    // Handle all messages the gui could send
    switch(msg.getType())
    {
      // to Inet
      case Rmessage.tGETCHARLIST:
      case Rmessage.tGETSTORYLIST:
      case Rmessage.tGETUPDATELIST:
      case Rmessage.tGETUPDATE:
      case Rmessage.tLOADSTORY:	// response goes to storyengine
      case Rmessage.tLOADCHAR:	// response goes to storyengine
      case Rmessage.tLOGIN:
      case Rmessage.tOPSLOGIN:
        if(inet.isOnline())
          inet.makeRequest(msg);
        else
          System.out.println("Inet not online.  Discard "+msg.getType());
        break;
      case Rmessage.tLOGOFF:
      case Rmessage.tNEWUSER:
      case Rmessage.tREFERRAL:
        if(inet.isOnline())
          inet.makeRequest(msg);
        else
          System.out.println("Inet not online.  Discard "+msg.getType());
        break;
      // to the Story Engine come here
      case Rmessage.tCAREERCHOICE:
      case Rmessage.tNAVIGATE:
      case Rmessage.tUPLOADCHAR:
      case Rmessage.tUPLOADSTORY:
      case Rmessage.tGETPLAYLIST:
      case Rmessage.tINVALIDATEPLAYLIST:
      case Rmessage.tPLAYLISTCOMPLETE:
      case Rmessage.tWANTCHARATTRIBUTES:
      case Rmessage.tCHARATTRIBUTES:
      case Rmessage.tLOADEDCHAR:
      case Rmessage.tLOADEDGAME:
      case Rmessage.tNEWCHARACTER:
      case Rmessage.tGAMESPEED:
        story.messageIn(msg);		// uses current data
        break;
      default:
        throw new RuntimeException("Unexpected message type ("+msg.getType()+") in RecruitsMessager.toStoryEngine.");
    }
  }
  // This is where the story engine sends things
  public void storyEngineDispatchMsg(Rmessage msg)
  //----------------------------------------------
  {
  // Handle all messages the story engine could send
    switch(msg.getType())
    {
    case Rmessage.tLOADPLAYLIST:
      gui.loadPlayList((Playlist)msg.toObject());
      break;
    case Rmessage.tNOPLAYLIST:
      gui.noPlayList();
      break;
    case Rmessage.tCHARATTRIBUTES:
      gui.charAttributes((CharInsides)msg.toObject());
      break;
    case Rmessage.tALERTRMESSAGE:
      gui.alertMessage((AlertMessage)msg.toObject());
      break;
    case Rmessage.tUPLOADCHARDATA:
    case Rmessage.tUPLOADSTORYDATA:
      inet.makeRequest(msg);
      break;
    default:
      throw new RuntimeException("Unexpected message type from Story Engine("+msg.getType()+") in RecruitsMessager.storyEngineDispatchMsg.");
    }
  }
  // This is where the inet services system sends things
  public void servicesDispatchMsg(Rmessage msg)
  //-------------------------------------------
  {
    switch(msg.getType())
    {
    case Rmessage.tACK:
      gui.ack((AckMessage)msg);
      break;
    case Rmessage.tERROR:
      gui.error((ErrorMessage)msg);
      break;
    case Rmessage.tASSIGNEDUSERNAME:
      gui.assignedUserName((String)msg.toObject());
      break;
    case Rmessage.tCHARLIST:
      //gui.charList((CharAbstractList)msg.toObject());
      break;
    case Rmessage.tSTORYLIST:
      //gui.storyList((StoryList)msg.toObject());
      break;
    case Rmessage.tUPDATELIST:
      //gui.updateList((UpdateList)msg.toObject());
      break;
    case Rmessage.tLOADSTORYDATA:
      //gui.loadStory((Story)msg.toObject());
      break;
    case Rmessage.tLOADCHARDATA:
      story.messageIn(msg);
      break;
    //case Rmessage.tERROR:
      //break;
    case Rmessage.tSERVICESTARTED:
      gui.event(ButtonFactory.SERVICESTARTED, msg);
      break;
    default:
      throw new RuntimeException("Unexpected message type from Services("+msg.getType()+") in RecruitsMessager.servicesDispatchMsg.");
    }
  }
}
// EOF
