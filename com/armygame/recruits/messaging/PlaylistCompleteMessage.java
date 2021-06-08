/******************************
 * File:	PlaylistCompleteMessage.java
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
import java.util.ArrayList;

/**
 * This message comes from Director and goes to the Story Engine.  Its first parameter is the reason for
 * the playlist ending.  The second is a list of serial numbers of all game state changes seen by the player
 * for the last playlist given by the Story Engine to director.  It can be empty.
 */

public class PlaylistCompleteMessage extends Rmessage
/**********************************************/
{
  public String reason;
  public ArrayList stateChanges;
  
  public PlaylistCompleteMessage(StringTokenizer st)
  //===========================================
  {
    super(tPLAYLISTCOMPLETE,PLAYLISTCOMPLETE);
    
    reason = st.nextToken();
    
    stateChanges = new ArrayList();
    while(st.hasMoreTokens())
      stateChanges.add(st.nextToken());
  }
  
  public String toTSV()
  //-------------------
  {
    return ("");		// not used PLAYLISTCOMPLETE\t"+reason+"\t"); 
  }
  public String toQueryString()
  //---------------------------
  {
    return ("");	//not used
  }
  public String toXML()
  {
    return "";		// not used <playlistcomplete><reason>blah</reason><statechanges>.....</statechanges></getplaylist>
  }
 


}
// EOF
