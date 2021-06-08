/******************************
 * File:	LoadPlaylistMessage.java
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

public class LoadPlaylistMessage extends Rmessage
/***********************************************/
{
  private Playlist playlist;
  public LoadPlaylistMessage(Playlist obj)
  //==================================
  {
    super(tLOADPLAYLIST,LOADPLAYLIST);
    playlist = obj;
  }

  public String toQueryString()
  //---------------------------
  {
    return ("");	//not used
  }
  
  public Object toObject()
  //----------------------
  {
    return playlist;
  }
  
  public String toXML()
  //-------------------
  {
    return playlist.toXML();		// not used; toTSV() is always used when going to director
  }
  
  /**
    Method to convert this message object to a string that goes across the localhost link.
  */
  public String toTSV()
  //-------------------
  {
    // Use this to send the entire playlist
    //return ("LOADPLAYLIST\t"+ playlist.toXML());
    
    // Use this to send a temp file across
    // make a "tmp" directory in our home dir if it doesn't already exist
    String ud = System.getProperty("user.dir");
    String fs = System.getProperty("file.separator");
    File f = new File(ud+fs+"tmp");
    f.mkdir();
    File tf=null;
    String nm = null;
    try {
      tf = File.createTempFile("pla",null,f);    
      BufferedWriter bf = new BufferedWriter(new FileWriter(tf));
      bf.write(playlist.toXML());
      bf.flush();
      bf.close();
      nm = tf.getCanonicalPath();
    }
    catch(IOException e) {System.out.println("error: "+e);return "error";}
    
    return "PLAYLIST\t"+nm;
    // debug
    //return "PLAYLIST\tc:\\junkplaylist.tmp";
  }
/*
  public static void main(String args[])
  {
     LoadPlaylistMessage msg = new LoadPlaylistMessage(null);
     String x = msg.toTSV();
  }
*/
}
// EOF

