/******************************
 * File:	CharAttributesMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Mar 01 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import java.util.StringTokenizer;

public class WantCharAttributesMessage extends Rmessage
/***************************/
{
  public String nm="charname";
  public WantCharAttributesMessage(StringTokenizer st)
  {
    super(tWANTCHARATTRIBUTES,WANTCHARATTRIBUTES);
    if(st.hasMoreTokens())
      nm = st.nextToken();
  }
  
  // None of these are used
  public String toTSV()
  {
    return "";
  }
  public String toQueryString()
  {
    return "";
  }
  public String toXML()
  {
    return "";
  }
}
// EOF
