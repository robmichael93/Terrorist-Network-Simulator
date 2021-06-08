/******************************
 * File:	ReferralMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 25 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;

public class ReferralMessage extends Rmessage
/*******************************************/
{
  public ReferralMessage(StringTokenizer st)
  //=====================================
  {
    super(tREFERRAL,REFERRAL);
    //sequence = st.nextToken();
    //username = st.nextToken();
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
}
// EOF
