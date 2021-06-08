/******************************
 * File:	LoadCharDataMessage.java
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

public class LoadCharDataMessage extends Rmessage
/***************************/
{
  public LoadCharDataMessage(StringTokenizer st)
  //=====================================
  {
    super(tLOADCHARDATA,LOADCHARDATA);
    //sequence = st.nextToken();
   // username = st.nextToken();
  }
  
  public String toTSV()
  //-------------------
  {
    return ("LOADCHARDATA\t"+sequence+"\t"); //+username);
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
