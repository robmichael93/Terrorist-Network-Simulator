/******************************
 * File:	LoadCharMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Mon Jan 28 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;

import java.util.StringTokenizer;

public class LoadCharMessage extends Rmessage
/***************************/
{
  public LoadCharMessage(StringTokenizer st)
  //=====================================
  {
    super(tLOADCHAR,LOADCHAR);
    sequence = st.nextToken();
   // username = st.nextToken();
  }
  
  public String toTSV()
  //-------------------
  {
    return ("LOADCHAR\t"+sequence+"\t"); //+username);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("");	//?name="+username);
  }
  public String toXML()
  {return "";}
  

}
// EOF
