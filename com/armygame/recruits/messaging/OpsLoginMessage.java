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

public class OpsLoginMessage extends Rmessage
/********************************************/
{
  private String username;
  private String password;
  public OpsLoginMessage(StringTokenizer st)
  //=====================================
  {
    super(tOPSLOGIN,OPSLOGIN);
    sequence = st.nextToken();
    username = st.nextToken();
    password = st.nextToken();
  }
  public OpsLoginMessage(String uname, String pword)
  //=============================================
  {
    super(tOPSLOGIN,OPSLOGIN);
    sequence="0";
    username=uname;
    password=pword;
  }
  public String toTSV()
  //-------------------
  {
    return ("OPSLOGIN\t"+sequence+"\t"+username+"\t"+password);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("userName="+username+"&password="+password);
  }
  public String toXML()
  {
    return "<OPSLOGIN><sequence>"+sequence+"</sequence><name>"+username+"</name><password>"+password+"</password></LOGIN>";
  }
  
  public String getUsername() {
  	return(username);
  }
  
  public String getPassword() {
  	return(password);
  }
}
// EOF
