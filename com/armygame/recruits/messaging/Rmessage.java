/******************************
 * File:	Rmessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 25 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute,new Integer( Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;
import java.util.HashMap;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.xml.sax.InputSource;

/**
 * The <b>Rmessage</b> class is an abstract base class for messages which cross the boundaries
 * in the Recruits game to/from the Java core to Director (through the localhostio package) and
 * the "web services" interface (through InetServices).  In this class are defined the constants
 * which correspond to all the sub classes, which are the specific messages pushed around in
 * the app.
 *
 * @version 1.0
 * @author  Mike Bailey
 * @-see     All sub classes
 * @since   JDK 1.3.1
 */
abstract public class Rmessage
/*****************************/
{
  static protected int nextSequence = 1;
  protected String sequence;
  protected int type;
  public String typeString;

  abstract public String toTSV();
  abstract public String toQueryString();
  abstract public String toXML();

  // A single place to put all the message types

  public static final String LOGIN = "LOGIN";
  public static final int   tLOGIN = 1;
  //public static final String USERNAME = "USERNAME";
  //public static final int   tUSERNAME = 2;
  public static final String NEWUSER = "NEWUSER";
  public static final int   tNEWUSER = 3;
  public static final String GETCHARLIST = "GETCHARLIST";
  public static final int   tGETCHARLIST = 4;
  public static final String CHARLIST = "CHARLIST";
  public static final int   tCHARLIST = 5;
  public static final String LOADCHAR = "LOADCHAR";
  public static final int   tLOADCHAR = 6;
  public static final String GETSTORYLIST = "GETSTORYLIST";
  public static final int   tGETSTORYLIST = 7;
  public static final String STORYLIST = "STORYLIST";
  public static final int   tSTORYLIST = 8;
  public static final String LOADSTORY = "LOADSTORY";
  public static final int   tLOADSTORY = 9;
  public static final String UPLOADSTORY = "UPLOADSTORY";
  public static final int   tUPLOADSTORY = 10;
  public static final String UPLOADCHAR = "UPLOADCHAR";
  public static final int   tUPLOADCHAR = 11;
  public static final String GETUPDATELIST = "GETUPDATELIST";
  public static final int   tGETUPDATELIST = 12;
  public static final String UPDATELIST = "UPDATELIST";
  public static final int   tUPDATELIST = 13;
  public static final String REFERRAL = "REFERRAL";
  public static final int   tREFERRAL = 14;
  public static final String ASSIGNEDUSERNAME = "ASSIGNEDUSERNAME";
  public static final int   tASSIGNEDUSERNAME = 15;
  public static final String LOADPLAYLIST = "LOADPLAYLIST";
  public static final int   tLOADPLAYLIST = 16;
  public static final String NAVIGATE = "NAVIGATE";
  public static final int   tNAVIGATE = 17;
  public static final String GETUPDATE = "GETUPDATE";
  public static final int   tGETUPDATE = 18;
  public static final String UPLOADCHARDATA = "UPLOADCHARDATA";
  public static final int   tUPLOADCHARDATA = 19;
  public static final String UPLOADSTORYDATA = "UPLOADSTORYDATA";
  public static final int   tUPLOADSTORYDATA = 20;
  public static final String LOADCHARDATA = "LOADCHARDATA";
  public static final int   tLOADCHARDATA = 21;
  public static final String LOADSTORYDATA = "LOADSTORYDATA";
  public static final int   tLOADSTORYDATA = 22;

  //public static final String RUN = "RUN";
  //public static final String PAUSE = "PAUSE";
  //public static final String RESUME = "RESUME";
  //public static final String EXIT = "EXIT";
  public static final String ACK = "ACK";
  public static final int   tACK = 23;
  public static final String HEARTBEAT = "HEARTBEAT";
  public static final int   tHEARTBEAT = 24;
  public static final String LOGOFF = "LOGOFF";
  public static final int   tLOGOFF = 25;
  public static final String ERROR = "ERROR";
  public static final int   tERROR = 26;
  public static final String GETPLAYLIST = "GETPLAYLIST";
  public static final int   tGETPLAYLIST = 27;
  public static final String INVALIDATEPLAYLIST = "INVALIDATEPLAYLIST";
  public static final int   tINVALIDATEPLAYLIST = 28;
  public static final String PLAYLISTCOMPLETE = "PLAYLISTCOMPLETE";
  public static final int   tPLAYLISTCOMPLETE = 29;
  public static final String CHARATTRIBUTES = "CHARATTRIBUTES";
  public static final int   tCHARATTRIBUTES = 30;
  public static final String WANTCHARATTRIBUTES = "WANTCHARATTRIBUTES";
  public static final int   tWANTCHARATTRIBUTES = 31;
  public static final String ALERTRMESSAGE = "ALERTRMESSAGE";
  public static final int   tALERTRMESSAGE = 32;
  public static final String CAREERCHOICE = "CAREERCHOICE";
  public static final int   tCAREERCHOICE = 33;
  public static final String NOPLAYLIST = "NOPLAYLIST";
  public static final int   tNOPLAYLIST = 34;
  public static final String LOADEDGAME = "LOADEDGAME";
  public static final int   tLOADEDGAME = 35;
  public static final String LOADEDCHAR = "LOADEDCHAR";
  public static final int   tLOADEDCHAR = 36;
  public static final String NEWCHARACTER = "NEWCHARACTER";
  public static final int   tNEWCHARACTER = 37;
  public static final String OPSLOGIN = "OPSLOGIN";
  public static final int   tOPSLOGIN = 38;
  public static final String GAMESPEED = "GAMESPEED";
  public static final int   tGAMESPEED = 39;
  public static final String SERVICESTARTED = "SERVICESTARTED";
  public static final int   tSERVICESTARTED = 40;

  private static HashMap nameToNumber;
  static
  {
    nameToNumber = new HashMap();

    nameToNumber.put(LOGIN,new Integer(tLOGIN));
    //nameToNumber.put(USERNAME,new Integer(tUSERNAME));
    nameToNumber.put(NEWUSER,new Integer(tNEWUSER));
    nameToNumber.put(GETCHARLIST,new Integer(tGETCHARLIST));
    nameToNumber.put(CHARLIST,new Integer(tCHARLIST));
    nameToNumber.put(LOADCHAR,new Integer(tLOADCHAR));
    nameToNumber.put(GETSTORYLIST,new Integer(tGETSTORYLIST));
    nameToNumber.put(STORYLIST,new Integer(tSTORYLIST));
    nameToNumber.put(LOADSTORY,new Integer(tLOADSTORY));
    nameToNumber.put(UPLOADSTORY,new Integer(tUPLOADSTORY));
    nameToNumber.put(UPLOADCHAR,new Integer(tUPLOADCHAR));
    nameToNumber.put(GETUPDATELIST,new Integer(tGETUPDATELIST));
    nameToNumber.put(UPDATELIST,new Integer(tUPDATELIST));
    nameToNumber.put(REFERRAL,new Integer(tREFERRAL));
    nameToNumber.put(ASSIGNEDUSERNAME,new Integer(tASSIGNEDUSERNAME));
    nameToNumber.put(LOADPLAYLIST,new Integer(tLOADPLAYLIST));
    nameToNumber.put(NAVIGATE,new Integer(tNAVIGATE));
    nameToNumber.put(GETUPDATE,new Integer(tGETUPDATE));
    nameToNumber.put(UPLOADCHARDATA,new Integer(tUPLOADCHARDATA));
    nameToNumber.put(UPLOADSTORYDATA,new Integer(tUPLOADSTORYDATA));
    nameToNumber.put(LOADCHARDATA,new Integer(tLOADCHARDATA));
    nameToNumber.put(LOADSTORYDATA,new Integer(tLOADSTORYDATA));
    nameToNumber.put(ACK,new Integer(tACK));
    nameToNumber.put(HEARTBEAT,new Integer(tHEARTBEAT));
    nameToNumber.put(LOGOFF,new Integer(tLOGOFF));
    nameToNumber.put(ERROR,new Integer(tERROR));
    nameToNumber.put(GETPLAYLIST,new Integer(tGETPLAYLIST));
    nameToNumber.put(INVALIDATEPLAYLIST,new Integer(tINVALIDATEPLAYLIST));
    nameToNumber.put(PLAYLISTCOMPLETE,new Integer(tPLAYLISTCOMPLETE));
    nameToNumber.put(CHARATTRIBUTES,new Integer(tCHARATTRIBUTES));
    nameToNumber.put(WANTCHARATTRIBUTES,new Integer(tWANTCHARATTRIBUTES));
    nameToNumber.put(ALERTRMESSAGE,new Integer(tALERTRMESSAGE));
    nameToNumber.put(CAREERCHOICE,new Integer(tCAREERCHOICE));
    nameToNumber.put(NOPLAYLIST,new Integer(tNOPLAYLIST));
    nameToNumber.put(LOADEDGAME,new Integer(tLOADEDGAME));
    nameToNumber.put(LOADEDCHAR,new Integer(tLOADEDCHAR));
    nameToNumber.put(NEWCHARACTER,new Integer(tNEWCHARACTER));
    nameToNumber.put(OPSLOGIN,new Integer(tOPSLOGIN));
    nameToNumber.put(GAMESPEED,new Integer(tGAMESPEED));
    nameToNumber.put(SERVICESTARTED,new Integer(tSERVICESTARTED));
  }
  public int getType()
  {
    return type;
  }
  public Object toObject()
  {
    return null;
  }
  public static int getType(String name)
  {
    return ((Integer)nameToNumber.get(name)).intValue();
  }
  // Factories to make messages
  // First gets XML data from an inet connection.
  // Second gets Lingo data from local host

  public static Rmessage makeMessage(InputStream xmlStream)
  //-------------------------------------------------------
  {
    try
    {
      SAXBuilder bldr = new SAXBuilder();
      Document doc = bldr.build(new InputSource(xmlStream));
      Element root = doc.getRootElement();

      int msgIdx = ((Integer)nameToNumber.get(root.getName().toUpperCase())).intValue();
      switch(msgIdx)
      {
      case tCHARLIST:
        return new CharListMessage(doc);
      case tSTORYLIST:
        return new StoryListMessage(doc);
      case tASSIGNEDUSERNAME:
        return new AssignedUserNameMessage(doc);
      case tACK:
        return new AckMessage(doc);

      }
      return null;
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unknown data from inet / Rmessage.makeMessage()");
    }
  }
  protected Rmessage(int typ, String typeString)
  //============================================
  {
    this.type = typ;
    this.typeString = typeString;
    sequence = ""+nextSequence++;
  }
  public static Rmessage makeMessage(String typ, StringTokenizer st)
  //----------------------------------------------------------------
  {
    int msgIdx = ((Integer)nameToNumber.get(typ)).intValue();
    switch(msgIdx)
    {
    case tLOGIN:
      return new LoginMessage(st);
    //case tUSERNAME:
      //return new AssignedUserNameMessage(st);
    case tNEWUSER:
      return new NewUserMessage(st);
    case tGETCHARLIST:
      return new GetCharListMessage(st);
    //case tCHARLIST:
      //return new CharListMessage(st);
    case tLOADCHAR:
      return new LoadCharMessage(st);
    case tGETSTORYLIST:
      return new GetStoryListMessage(st);
    //case tSTORYLIST:
      //return new StoryListMessage(st);
    case tLOADSTORY:
      return new LoadStoryMessage(st);
    case tUPLOADSTORY:
      return new UpLoadStoryMessage(st);
    case tUPLOADCHAR:
      return new UpLoadCharMessage(st);
    case tGETUPDATELIST:							// = 12;
      return new GetUpdateListMessage(st);
//    case tUPDATELIST:
//      return new Update
    case tREFERRAL:
      return new ReferralMessage(st);

    //case tASSIGNEDUSERNAME:
      //return new AssignedUserNameMessage(st);
    //case tLOADPLAYLIST:
      //return new LoadPlaylistMessage(st);
    case tNAVIGATE:
      return new NavigateMessage(st);
    case tGETUPDATE: //= 18;
      return new GetUpdateMessage(st);

    //case tUPLOADCHARDATA:
      //return new UploadCharDataMessage(st);
    //case tUPLOADSTORYDATA:
      //return new UploadStoryDataMessage(st);
    case tLOADCHARDATA:
      return new LoadCharDataMessage(st);
    case tLOADSTORYDATA:
      return new LoadStoryDataMessage(st);
    //case tACK:
      //return new AckMessage(st);
    case tHEARTBEAT:
      return new HeartbeatMessage(st);
    case tLOGOFF:
      return new LogoffMessage(st);
    //case tERROR: // = 26;
    //  return new ErrorMessage(st);
    case tGETPLAYLIST:
      return new GetPlaylistMessage(st);
    case tINVALIDATEPLAYLIST:
      return new InvalidatePlaylistMessage(st);
    case tPLAYLISTCOMPLETE:
      return new PlaylistCompleteMessage(st);
    case tWANTCHARATTRIBUTES:
      return new WantCharAttributesMessage(st);
    case tCHARATTRIBUTES:
      return new CharAttributesMessage(st);
    case tOPSLOGIN:
      return new OpsLoginMessage(st);

    }
    return null;
  }
  public void loadPlayList(/*PlayList pl*/)
  {
  }

}
// EOF
