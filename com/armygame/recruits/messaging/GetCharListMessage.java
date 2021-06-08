/******************************
 * File:	GetCharListMessage.java
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

public class GetCharListMessage extends Rmessage
/**************************************************/
{
  private int numWanted;
  private String startkey;
  private String constraint;
  
  public GetCharListMessage(StringTokenizer st)
  //===========================================
  {
    super(tGETCHARLIST,GETCHARLIST);
    numWanted = Integer.parseInt(st.nextToken());
    startkey = st.nextToken();
    constraint = st.nextToken();    
  }
  public String toTSV()
  {
    return "";
  }
  public String toQueryString()
  {
    return "?max="+numWanted+"&key="+startkey+"&constraint="+constraint;
  }
    public String toXML()
  {return "";}

 // protected void process()
  //{
  // Do whatever should be done with this message.
  // This comes from the client (director) and should go to the internet,
  // then be acked to director.
  
  //}

}
// EOF