/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 17, 2002
 * Time: 6:03:10 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.messaging;

public class GameSpeedMessage extends Rmessage
{
  public GameSpeedMessage(int val)
  {
    super(tGAMESPEED,GAMESPEED);
    this.val=val;
  }
  static public final int FOCUS = 0;
  static public final int MEDIUM = 1;
  static public final int OVERVIEW = 2;

  int val;
  public int getValue()
  {
    return val;
  }

  public String toTSV(){return null;}
  public String toQueryString(){return null;}
  public String toXML(){return null;}
}
