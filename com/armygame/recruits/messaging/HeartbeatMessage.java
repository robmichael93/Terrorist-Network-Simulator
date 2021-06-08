/******************************
 * File:	HeartbeatMessage.java
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

import java.util.StringTokenizer;

public class HeartbeatMessage extends Rmessage
/************************************************/
{
  HeartbeatMessage()
  //================
  {
    super(tHEARTBEAT,HEARTBEAT);
  }
  HeartbeatMessage(StringTokenizer st)
  //==================================
  {
    this();
  }
  public String toTSV()
  //-------------------
  {
    return ("HEARTBEAT\t"+sequence);
  }

  public String toQueryString()
  //---------------------------
  {
    return "";
  }
  public String toXML()
  {return "";}


}
// EOF
