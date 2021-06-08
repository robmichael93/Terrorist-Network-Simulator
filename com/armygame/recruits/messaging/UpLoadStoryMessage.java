/******************************
 * File:	UpLoadStoryMessage.java
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

public class UpLoadStoryMessage extends Rmessage
/***************************/
{
  public UpLoadStoryMessage(StringTokenizer st)
  //===========================================
  {
    super(tUPLOADSTORY,UPLOADSTORY);
  }
  public String toTSV()
  {
    return "";
  }
  public String toQueryString()
  {
    return "";
  }
  public String toXML()
  {return "";}

}
// EOF
