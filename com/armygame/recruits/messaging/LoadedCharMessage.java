/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 13, 2002
 * Time: 3:43:02 PM
 */
package com.armygame.recruits.messaging;

import com.armygame.recruits.globals.SavedCharacter;

public class LoadedCharMessage extends Rmessage
{
  public LoadedCharMessage(SavedCharacter character)
  {
    super(tLOADEDCHAR,LOADEDCHAR);
    this.character=character;
  }

  SavedCharacter character;
  public SavedCharacter getChar()
  {
    return character;
  }

  public String toTSV(){return null;}
  public String toQueryString(){return null;}
  public String toXML(){return null;}
}
