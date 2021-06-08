/******************************
 * File:	GetUpdateListMessage.java
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

public class GetUpdateListMessage extends Rmessage
/****************************************************/
{
  private int numWanted;
  private String startkey;
  private String constraint;
  
  public GetUpdateListMessage(StringTokenizer st)
  //===========================================
  {
    super(tGETUPDATELIST,GETUPDATELIST);
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

  //protected void process()
  //{
  // Do whatever should be done with this message.
  // This comes from the client (director) and should go to the internet, the list should be
  // gotten and sent to director.  The ack is part of that msg.
  //}

}
// EOF
