//
//  NewUserPanel.java
//  RecruitsJavaGui
//
//  Created by Mike Bailey on Fri Mar 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;

import com.armygame.recruits.messaging.*;
import com.armygame.recruits.playlist.Playlist;
import com.armygame.recruits.storyelements.sceneelements.CharInsides;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;
import com.armygame.recruits.globals.SavedGame;
import com.armygame.recruits.globals.SavedCharacter;

import java.util.StringTokenizer;

public class RecruitsGuiMessager
{
  MainFrame mf;
  RecruitsGuiMessager(MainFrame mf)
  {
    this.mf = mf;
  }
  public void go()
  {
    mf.go();
  }
  public void loadPlayList(Playlist play)
  {
    mf.handlers.eventIn(ButtonFactory.PLAYLISTRECEIVED,play);
  }
  public void noPlayList()
  {
 System.out.println("Gui got no playlist message");
    mf.handlers.eventIn(ButtonFactory.NOPLAYLISTRECEIVED);
  }
  public void charAttributes(CharInsides charinsides)
  {
    mf.globals.updateValues(charinsides);
    System.out.println("RecruitsGuiMessager.charAttributes()");
  }
  public void alertMessage(AlertMessage am)
  {
    mf.handlers.oobEventIn(ButtonFactory.ALERTMESSAGE, am);
    System.out.println("RecruitsGuiMessageer.alertMessage()");
  }
  public void ack(AckMessage m)
  {
    mf.handlers.eventIn(ButtonFactory.ACK,m);
    System.out.println("RecruitsGuiMessager.ack()");
  }
  public void error(ErrorMessage m)
  {
    mf.handlers.eventIn(ButtonFactory.ERROR,m);
    System.out.println("RecruitsGuiMessager.error()");
  }
  public void event(int i, Rmessage m)
  {
    mf.handlers.eventIn(i, m);
    System.out.println("event:" + m.toString());
  }
  public void assignedUserName(String uname)
  {
    System.out.println("RecruitsGuiMessager.assignedUserName()");
  }
  // from Gui
  public void careerChoice(String s)
  {
    Rmessage msg = new CareerChoiceMessage(s);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void wantPlaylist(String name)
  {
    Rmessage msg;
    if(name == null)
      msg = Rmessage.makeMessage("GETPLAYLIST", null);
    else
      msg = Rmessage.makeMessage("GETPLAYLIST", new StringTokenizer(name));
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void wantCharAttributes(String name)
  {
    Rmessage msg = Rmessage.makeMessage("WANTCHARATTRIBUTES", new StringTokenizer("charname"));
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void charAttrToStoryEngine(CharInsides ci)
  {
    Rmessage msg = new CharAttributesMessage(ci);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void newCharToStoryEngine(CharInsides ci)
  {
    Rmessage msg = new NewCharacterMessage(ci);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void loadedGameToStoryEngine(SavedGame g)
  {
    Rmessage msg = new LoadedGameMessage(g);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void loadedCharToStoryEngine(SavedCharacter c)
  {
    Rmessage msg = new LoadedCharMessage(c);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void servicesLogin(String uname, String pword)
  {
    LoginMessage msg = new LoginMessage(uname,pword);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  
  public void opsLogin(String uname, String pword)
  {
    OpsLoginMessage msg = new OpsLoginMessage(uname,pword);
    mf.rmain.messager.guiDispatchMsg(msg);
  }
  public void setGameSpeed(int speed)
  {
    GameSpeedMessage gsm = new GameSpeedMessage(speed);
    mf.rmain.messager.guiDispatchMsg(gsm);
  }
  public void servicesNewUser(UserVitals uv)
  {
    NewUserMessage msg = new NewUserMessage(
      uv.userID, uv.title, uv.firstName, uv.lastName, uv.email,
      uv.phone, uv.password, uv.passwordHint, uv.street1, uv.street2,
      uv.town, uv.state, uv.zip );

    mf.rmain.messager.guiDispatchMsg(msg);
  }

}
