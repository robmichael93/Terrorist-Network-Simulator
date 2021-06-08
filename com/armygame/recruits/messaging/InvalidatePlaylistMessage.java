/******************************
 * File:	InvalidatePlaylistMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Thu Feb 07 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;

import java.util.StringTokenizer;

/**
 * This message comes from Director and goes to the Story Engine.  Its single parameter is the name of
 * the character for which the playlist should be constructed.  When the story engine receives this, it
 * must respond with a LoadPlaylistMessage.
 */

public class InvalidatePlaylistMessage extends Rmessage
/******************************************************/
{
  public InvalidatePlaylistMessage(StringTokenizer st)
  //==================================================
  {
    super(tGETPLAYLIST,GETPLAYLIST);
  }
  
  public String toTSV()
  //-------------------
  {
    return ("");		// not used INVALIDATEPLAYLIST\t"+character+"\t"); 
  }
  public String toQueryString()
  //---------------------------
  {
    return ("");
  }
  public String toXML()
  //-------------------
  {
    return "";		// not used <invalidateplaylist></invalidateplaylist>
  }
 


}
// EOF
