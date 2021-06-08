/******************************
 * File:	UpLoadCharDataMessage.java
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

public class UpLoadCharDataMessage extends Rmessage
/***************************/
{
  public UpLoadCharDataMessage(StringTokenizer st)
  //=====================================
  {
    super(tUPLOADCHARDATA,UPLOADCHARDATA);
    sequence = st.nextToken();
   // username = st.nextToken();
  }
  
  /*CharData*/Object chardata;
  public UpLoadCharDataMessage(/*CharData*/Object obj)
  //====================================================
  {
    super(tUPLOADCHARDATA,UPLOADCHARDATA);
    chardata = obj;
  }
  
  public String toTSV()
  //-------------------
  {
    return (Rmessage.UPLOADCHARDATA+"\t"+sequence+"\t"); //+username);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("");	//?name="+username);
  }

  public String toXML()
  //------------------ 
  {
    return "";		// todo...turn the chardata into XML to upload
  }
 


}
// EOF
