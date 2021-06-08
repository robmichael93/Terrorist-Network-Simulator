/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * User: mike
 * Date: Jun 6, 2002
 * Time: 2:56:34 PM
 */
package com.armygame.recruits.globals;

public class CannedGame0 extends SavedGame
{
  public CannedGame0()
  {
    name = "Basic_with_Pvt_Miranda";

    character = new CannedChar0();
    description = "This game starts at the beginning of Basic "+
       "Combat Training with soldier "+character.firstName+" "+
       character.lastName+".  " + character.bio;
  }
}
