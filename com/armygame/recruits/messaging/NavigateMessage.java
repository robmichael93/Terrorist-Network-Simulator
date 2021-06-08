/******************************
 * File:	NavigateMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Wed Jan 23 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;

public class NavigateMessage extends Rmessage
/*******************************************/
{
  public String command;
  public NavigateMessage(StringTokenizer st)
  //=====================================
  {
    super(tNAVIGATE,NAVIGATE);
    command = st.nextToken();
  }
  
  public String toTSV()
  //-------------------
  {
    return "";//return ("LOGIN\t"+sequence+"\t"+username);
  }
  public String toQueryString()
  //---------------------------
  {
    return "";//return ("?name="+username);
  }
   public String toXML()
  {return "";}
 
  public /*NavigateObject*/ Object toObject()
  {
    return "placeholder";	//new NavigateObject();	// todo: implement
  }
}
// EOF
