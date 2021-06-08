/******************************
 * File:	NewUser.java
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

import com.armygame.recruits.RecruitsMain;
import java.util.StringTokenizer;
import java.net.*;
import java.io.*;

public class NewUserMessage extends Rmessage
/**********************************************/
{
  private String desiredUsername;
  private String title;
  private String first;
  private String last;
  private String email;
  private String phone;
  private String passWord;
  private String hint;
  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zip;
  
  public NewUserMessage(StringTokenizer st)
  //=======================================
  {
    super(tNEWUSER,NEWUSER);
    //sequence = st.nextToken();
    desiredUsername = st.nextToken();
  }
  public NewUserMessage(String uname, String title, String first,
    String last, String email, String phone, String password,
    String hint, String address1, String address2, String city,
    String state, String zip)
  //=============================================================
  {
    super(tNEWUSER,NEWUSER);
    this.desiredUsername = uname;
    this.title = title;
    this.first = first;
    this.last = last;
    this.email = email;
    this.phone = phone;
    this.passWord = password;
    this.hint = hint;
    this.address1 = address1;
    this.address2 = address2;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }
  
  public String toTSV()
  //-------------------
  {
    return ("NEWUSER\t"+sequence+"\t"+
      desiredUsername+"\t"+
      title+"\t"+
      first+"\t"+
      last+"\t"+
      email+"\t"+
      phone+"\t"+
      passWord+"\t"+
      hint+"\t"+
      address1+"\t"+
      address2+"\t"+
      city+"\t"+
      state+"\t"+
      zip);
  }

  public String toQueryString()
  //---------------------------
  {
    return "action=Create"+
      "&user.title="+title+
      "&user.firstName="+first+
      "&user.lastName="+last+
      "&user.email="+email+
      "&user.phoneNumber="+phone+
      "&user.userName="+desiredUsername+
      "&user.password="+passWord+
      "&user.confirmPassword="+passWord+
      //"&user.passwordHint="+hint+
      "&user.address.address1="+address1+
      "&user.address.address2="+address2+
      "&user.address.city="+city+
      "&user.address.state="+state+
      "&user.address.zip="+zip+
      
      "&submit=Submit";

  }
  public String toXML()
  {return "";}
/*
  protected void process()
  {
  // Do whatever should be done with this message.
  // This comes from the client (director) and should go to the internet,
  // then be acked to director.

    URL url;
    HttpURLConnection con;
    try
    {
      url = RecruitsMain.instance().services.newUserUrl;		// where we connect
      con = (HttpURLConnection)url.openConnection();			
      con.setDoOutput(true);				// to write query string..this call forces post
      OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(con.getOutputStream()));
      out.write(this.toQueryString());
      out.flush();
      out.close();
      
      InputStream is = con.getInputStream(); // this makes the connect() if not already done
      // Assume that we're going to get a good value from the getContentLength()...confirm
      if(con.getContentLength() > 0)
        ;//new ErrorMessage(con.getInputStream()).shipit(); // to Director
      else
        ;//new AckMessage(sequence).shipit();	// to Director
    }
    catch (Exception e){}
  }
  */
}
// EOF
