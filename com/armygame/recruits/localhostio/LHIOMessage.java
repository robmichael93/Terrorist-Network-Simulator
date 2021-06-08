/******************************
 * File:	LHIOMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 11 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.localhostio;

public class LHIOMessage
/***************************/
{
  public static final int HEARTBEAT = 0;		// started by java, each side echoes w/ X second delay
  
  // Web services support
  public static final int CHAR_ATTRIBUTES = 1;		// java2dir
  public static final int SEND_CHAR_ATTRIBUTES = 2;	// dir2java, prior to showing control panel
  
  public static final int UPLOAD_CHAR = 3;		// dir2java
  
  public static final int GET_DOWNLOADABLE_CHAR_LIST = 4;	// dir2java, prior to showing selection list
  public static final int DOWNLOADABLE_CHAR_LIST = 5;		// java2dir
  public static final int DOWNLOAD_CHAR = 6;			// dir2java
  
  public static final int UPLOAD_STORY = 7;			// dir2java

  public static final int GET_DOWNLOADABLE_STORY_LIST = 8;	// dir2java, priort to showing selection list
  public static final int DOWNLOADABLE_STORY_LIST = 9;		// java2dir
  public static final int DOWNLOAD_STORY = 10;			// dir2java
  
  public static final int RECRUITER_CONTACT = 11;		// dir2java
  
  // Game flow
  public static final int PLAY = 12;
  public static final int PAUSE = 13;
  public static final int QUIT = 14;
  
  private static final String [] messageType =
  {
    "HEARTBEAT",
    
    "CHAR_ATTRIBUTES",
    "SEND_CHAR_ATTRIBUTES",
    
    "UPLOAD_CHAR",
    "GET_DOWNLOADABLE_CHAR_LIST",
    "DOWNLOADABLE_CHAR_LIST",
    "DOWNLOAD_CHAR",
    
    "UPLOAD_STORY",
    "GET_DOWNLOADABLE_STORY_LIST",
    "DOWNLOADABLE_STORY_LIST",
    "DOWNLOAD_STORY",
    
    "RECRUITER_CONTACT",
    "PLAY",
    "PAUSE",
    "QUIT",
    ""
  };
  
  private int myType;
  private Object myData;
  
  private LHIOMessage(int type, Object o)
  {
    myType = type;
    myData = o;
  }
  
  public static LHIOMessage newHeartbeat()				{return new LHIOMessage(HEARTBEAT,null);}
  public static LHIOMessage newCharAttributes(Object o)			{return new LHIOMessage(CHAR_ATTRIBUTES,o);}
  public static LHIOMessage newSendCharAttributes()			{return new LHIOMessage(SEND_CHAR_ATTRIBUTES,null);}
  public static LHIOMessage newUploadChar(Object o)			{return new LHIOMessage(UPLOAD_CHAR,o);}
  public static LHIOMessage newGetDownloadableCharList(int startKey)	{return new LHIOMessage(GET_DOWNLOADABLE_CHAR_LIST,new Integer(startKey));}
  public static LHIOMessage newDownloadableCharList(Object o)		{return new LHIOMessage(DOWNLOADABLE_CHAR_LIST,o);}
  public static LHIOMessage newDownloadChar(Object o)			{return new LHIOMessage(DOWNLOAD_CHAR,o);}
  public static LHIOMessage newUploadStory(Object o)			{return new LHIOMessage(UPLOAD_STORY,o);}
  public static LHIOMessage newGetDownloadableStoryList(int startkey)	{return new LHIOMessage(GET_DOWNLOADABLE_STORY_LIST,new Integer(startkey));}
  public static LHIOMessage newDownloadableStoryList(Object o)		{return new LHIOMessage(DOWNLOADABLE_STORY_LIST,o);}
  public static LHIOMessage newDownloadStory(Object o)			{return new LHIOMessage(DOWNLOAD_STORY,o);}
  public static LHIOMessage newRecruiterContact(Object o)		{return new LHIOMessage(RECRUITER_CONTACT,o);}
  public static LHIOMessage newPlay()					{return new LHIOMessage(PLAY,null);}
  public static LHIOMessage newPause()					{return new LHIOMessage(PAUSE,null);}
  public static LHIOMessage newQuit()					{return new LHIOMessage(QUIT,null);}  
}
// EOF
