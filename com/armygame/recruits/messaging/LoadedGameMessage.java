/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 13, 2002
 * Time: 3:43:02 PM
 */
package com.armygame.recruits.messaging;

import com.armygame.recruits.globals.SavedGame;

public class LoadedGameMessage extends Rmessage
{
  public LoadedGameMessage(SavedGame game)
  {
    super(tLOADEDGAME,LOADEDGAME);
    this.game=game;
  }

  SavedGame game;
  public SavedGame getGame()
  {
    return game;
  }

  public String toTSV(){return null;}
  public String toQueryString(){return null;}
  public String toXML(){return null;}
}
