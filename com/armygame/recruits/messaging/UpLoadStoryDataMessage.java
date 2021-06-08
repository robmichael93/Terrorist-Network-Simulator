/******************************
 * File:	UpLoadStoryDataMessage.java
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

public class UpLoadStoryDataMessage extends Rmessage
/***************************/
{
  public UpLoadStoryDataMessage(StringTokenizer st)
  //=============================================
  {
    super(tUPLOADSTORYDATA,UPLOADSTORYDATA);

    sequence = st.nextToken();
   // username = st.nextToken();
  }
  /*StoryData*/Object storydata;
  public UpLoadStoryDataMessage(/*StoryData*/Object obj)
  //====================================================
  {
    super(tUPLOADSTORYDATA,UPLOADSTORYDATA);
    storydata = obj;
  }
  public String toTSV()
  //-------------------
  {
    return "";	// not used here...doesn't go to director
  }
  public String toQueryString()
  //---------------------------
  {
    return ("");	//?name="+username);
  }
  
  public String toXML()
  //------------------ 
  {
    return "";		// todo...turn the storydata into XML to upload
  }
}
// EOF
