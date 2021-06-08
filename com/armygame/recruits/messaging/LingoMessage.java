/******************************
 * File:	LingoMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Thu Jan 17 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;

abstract public class LingoMessage implements Runnable
/****************************************************/
{
  static protected int nextSequence = 1;
  protected String sequence;
  
  abstract public String toTSV();
  abstract public String toQueryString();
  abstract protected void process();
  
  // A single place to put all the message types
  
  public static final String LOGIN = "LOGIN";
  public static final String USERNAME = "USERNAME";
  public static final String NEWUSER = "NEWUSER";
  public static final String GETCHARLIST = "GETCHARLIST";
  public static final String CHARLIST = "CHARLIST";
  public static final String GETLOADCHAR = "GETLOADCHAR";
  public static final String GETSTORYLIST = "GETSTORYLIST";
  public static final String STORYLIST = "STORYLIST";
  public static final String GETLOADSTORY = "GETLOADSTORY";
  public static final String UPLOADSTORY = "UPLOADSTORY";
  public static final String UPLOADCHAR = "UPLOADCHAR";
  public static final String GETUPDATELIST = "GETUPDATELIST";
  public static final String UPDATELIST = "UPDATELIST";
  public static final String REFERRAL = "REFERRAL";
  public static final String LOADPLAYLIST = "LOADPLAYLIST";
  public static final String RUN = "RUN";
  public static final String PAUSE = "PAUSE";
  public static final String RESUME = "RESUME";
  public static final String EXIT = "EXIT";
  public static final String ACK = "ACK";
  public static final String HEARTBEAT = "HEARTBEAT";
  
  public void shipit()
  {
    new Thread(this).start();
  }
  public void run()
  {
    process();
  }
}
// EOF
