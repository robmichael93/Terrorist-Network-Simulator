/******************************
 * File:	GetUpdateMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 18 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;

public class GetUpdateMessage extends Rmessage
/************************************************/
{
  Object key;
  public GetUpdateMessage(StringTokenizer st)
  //=========================================
  {
    super(tGETUPDATE,GETUPDATE);
    key = st.nextToken();
  }
  
  public String toTSV()
  //-------------------
  {
    return ("GETUPDATE\t"+sequence+"\t"+key);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("?key="+key);
  }
    public String toXML()
  {return "";}

  //protected void process()
  //{
  // Do whatever should be done with this message.
  // This comes from the client (director) and should go to the internet, the update should be downloaded,
  // then ack this msg to director.
  
  //}

}
// EOF
