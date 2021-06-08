/******************************
 * File:	GetStoryListMessage.java
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

public class GetStoryListMessage extends Rmessage
/***************************************************/
{
  private int numWanted;
  private String startkey;
  private String constraint;
  
  public GetStoryListMessage(StringTokenizer st)
  //===========================================
  {
    super(tGETSTORYLIST,GETSTORYLIST);
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

}
// EOF
