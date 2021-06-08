/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 20, 2002
 * Time: 4:24:18 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.messaging;

import com.armygame.recruits.*;
import com.armygame.recruits.playlist.*;
import java.util.StringTokenizer;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This message comes from the StoryEngine and goes to Director.  It is constructed from a Playlist object
 * and calls into the object to get the object's XML representation, which it ships to Director.
 */

public class NoPlayListMessage extends Rmessage
/***********************************************/
{
  public NoPlayListMessage()
  //==================================
  {
    super(tNOPLAYLIST,NOPLAYLIST);
  }

  public String toQueryString()
  //---------------------------
  {
    return ("");	//not used
  }

  public Object toObject()
  //----------------------
  {
    return null;
  }

  public String toXML()
  //-------------------
  {
    return "";		// not used; toTSV() is always used when going to director
  }

  /**
    Method to convert this message object to a string that goes across the localhost link.
  */
  public String toTSV()
  //-------------------
  {

    return "NOPLAYLIST\t";
    // debug
    //return "PLAYLIST\tc:\\junkplaylist.tmp";
  }
}
// EOF

