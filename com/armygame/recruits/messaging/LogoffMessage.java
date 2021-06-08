/******************************
 * File:	LogoffMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Mon Feb 04 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;

public class LogoffMessage extends Rmessage
/*****************************************/
{
  public LogoffMessage(StringTokenizer st)
  //======================================
  {
    super(tLOGOFF,LOGOFF);
  }
  public String toTSV()
  //-------------------
  {
    return "";//return ("LOGOFF\t"+sequence+"\t"+username);
  }
  
  public String toQueryString()
  //---------------------------
  {
    return "";//return ("?name="+username);
  }

  public String toXML()
  //-------------------
  {return "";}
  
}
// EOF
