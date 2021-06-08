/******************************
 * File:	LoginMessage.java
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

public class LoginMessage extends Rmessage
/********************************************/
{
  private String username;
  private String password;
  public LoginMessage(StringTokenizer st)
  //=====================================
  {
    super(tLOGIN,LOGIN);
    sequence = st.nextToken();
    username = st.nextToken();
    password = st.nextToken();
  }
  public LoginMessage(String uname, String pword)
  //=============================================
  {
    super(tLOGIN,LOGIN);
    sequence="0";
    username=uname;
    password=pword;
  }
  public String toTSV()
  //-------------------
  {
    return ("LOGIN\t"+sequence+"\t"+username+"\t"+password);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("userName="+username+"&password="+password);
  }
  public String toXML()
  {
    return "<LOGIN><sequence>"+sequence+"</sequence><name>"+username+"</name><password>"+password+"</password></LOGIN>";
  }
}
// EOF
