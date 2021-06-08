/******************************
 * File:	InetServices.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 11 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.services;

import com.armygame.recruits.*;
import com.armygame.recruits.localhostio.*;
import com.armygame.recruits.messaging.*;

import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLClassLoader;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.InputSource;
import java.lang.reflect.Method;

public class InetServices
/***********************/
{
 //private static String DEFAULT_SERVER = "63.205.28.30";
  private static String DEFAULT_SERVER = "localhost";
  //private static String DEFAULT_PORT = "8080";
  private static String DEFAULT_PORT = "80";
  private static String DEFAULT_PROT = "http";
  //private static String DEFAULT_PATH = "recruits";
  private static String DEFAULT_PATH = "LoginService.xml";

  private String protocol;
  private String server;
  private String port;
  private String path;

  private URL	baseurl;
  /*not used*/private String sessionCookie = "JSESSIONID=129065B4439515A08502E5D274520034";
  private String sessionID = "";

  private URL loginUrl;
  private URL logoffUrl;
  private URL newuserUrl;

  private URL getcharlistUrl;
  private URL getnextcharlistUrl;
  private URL getlastcharlistUrl;
  private URL getcharUrl;
  private URL uploadcharUrl;

  private URL getstorylistUrl;
  private URL getnextstorylistURL;
  private URL getlaststorylistURL;
  private URL getstoryUrl;
  private URL uploadstoryUrl;

  private URL referralUrl;
  private URL getupdatelistUrl;
  private URL getupdateUrl;

  private boolean online = false;
  private String uname;

  public Ithread inetThread;
  RecruitsMessager rmessager;

  //private StoryAbstractList myStoryAbstractList;
  //private CharacterAbstractList myCharacterAbstractList;

  public InetServices()
  //===============================
  {
    this(DEFAULT_PROT,DEFAULT_SERVER,DEFAULT_PORT,DEFAULT_PATH);
  }
  public InetServices(String protocol,String host,String port,String path)
  //======================================================================
  {
    this.protocol = protocol.toLowerCase();
    this.server   = host;
    this.port     = port;
    this.path     = path;
  }
  public void go()
  //--------------
  {
    init("");
    //getsessionid();				// this will be done in the thread below after development
  }
  public boolean isOnline()
  //-----------------------
  {
    return online;
  }

  /* Do initial request to get url list */
  private void init(String name)
  //-------------------------------
  {
    rmessager = RecruitsMain.instance().messager;

    inetThread = new Ithread();
    inetThread.start();

    this.uname  = name;

    // Do it in a thread to let the rest of the app get going
    new Thread(new Runnable()
    {
      public void run()
      {
        try{Thread.sleep(2000);}catch(Exception e){}
        loadUrls();
				getsessionid();
        online = true;
      }
    }).start();
  }

  private void getsessionid()
  //-------------------------
  {
    // To get the Session ID to append to the services urls, we make a login request with a bogus user name.  The
    // session id is retrieved.
    try
    {
//      URL url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/logon.do");
//      doPost(url,"userName=a&password=a","","");
      URL url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/goToCreateNewUser.do");
      doGet(url,null,"","");
    }
    catch(Exception e)
    {
      System.out.println("Error in InetServices.getsessionid()");
    }
  }
  /*
  private void loadUrls()
  //---------------------
  {
    try
    {
      baseurl = new URL(protocol+"://"+server+":"+port+"/"+path);
      HttpURLConnection con = (HttpURLConnection)baseurl.openConnection();

      con.setDoInput(true);
      con.setRequestMethod("GET");

      SAXBuilder bldr = new SAXBuilder();
      Document doc = bldr.build(new InputSource(con.getInputStream()));
      // The connection gets made on the getInputStream call
      sessionCookie = con.getHeaderField("Set-Cookie");

      Element root = doc.getRootElement();
      //System.out.println("Root name: "+root.getName());
      sessionCookie         = root.getChild("sessioncookie").getAttribute("value").getValue();

      loginUrl         = new URL(root.getChild("loginurl").getAttribute("value").getValue());
      newuserUrl       = new URL(root.getChild("newuserurl").getAttribute("value").getValue());
      getcharlistUrl   = new URL(root.getChild("getcharlisturl").getAttribute("value").getValue());
      getstorylistUrl  = new URL(root.getChild("getstorylisturl").getAttribute("value").getValue());
      getstoryUrl      = new URL(root.getChild("getstoryurl").getAttribute("value").getValue());
      getcharUrl       = new URL(root.getChild("getcharurl").getAttribute("value").getValue());
      uploadstoryUrl   = new URL(root.getChild("uploadstoryurl").getAttribute("value").getValue());
      uploadcharUrl    = new URL(root.getChild("uploadcharurl").getAttribute("value").getValue());
      referralUrl      = new URL(root.getChild("referralurl").getAttribute("value").getValue());
      getupdatelistUrl = new URL(root.getChild("getupdatelisturl").getAttribute("value").getValue());
      getupdateUrl     = new URL(root.getChild("getupdateurl").getAttribute("value").getValue());

    }
    catch (Exception e)
    {
      System.out.println("XML error in InetServices");
      return;
    }
  }
  */
  private void loadUrls()
  //---------------------
  {
    try
    {
      //baseurl = new URL("http://sunspot.cs.nps.navy.mil/recruits/struts/index.html");
      baseurl = new URL(protocol+"://"+server+":"+port+"/"+path);
      loginUrl           = new URL(baseurl,"logon.do");
      //logoffUrl;
      newuserUrl         = new URL(baseurl,"saveRegistration.do");
      getcharlistUrl     = new URL(baseurl,"doCharacterSearch.do");
      getnextcharlistUrl = new URL(baseurl,"pageForwardCharacters.do");
      getlastcharlistUrl = new URL(baseurl,"pageBackwardCharacters.do");
      getcharUrl         = new URL(baseurl,"downloadCharacter.do");
      uploadcharUrl      = new URL(baseurl,"uploadCharacter.do");

      getstorylistUrl    = new URL(baseurl,"doStorySearch.do");
      getnextstorylistURL= new URL(baseurl,"pageForwardStories.do");
      getlaststorylistURL= new URL(baseurl,"pageBackwardStories.do");
      getstoryUrl        = new URL(baseurl,"downloadStory.do");
      uploadstoryUrl     = new URL(baseurl,"uploadStory.do");

      referralUrl        = new URL(baseurl,"dispatchContactRecruiterList.do");
      //getupdatelistUrl;
      //getupdateUrl;
    }
    catch(Exception e) {System.out.println("bad url");}
  }

  SimpleObjectFIFO queue = new SimpleObjectFIFO(5);
  // Requests from the client come here
  public void makeRequest(Rmessage msg)
  //-----------------------------------
  {

      do{
      try{
        queue.add(msg);			// will block here if queue is full
        return;
      }
      catch(InterruptedException e){}
    }while (true);
  }

  class Ithread extends Thread
  /**************************/
  {
    volatile boolean fatal = false;

    public void run()
    //---------------
    {
      while(!fatal)
      {
        try{
          Rmessage msg = (Rmessage)queue.remove();
          doRequest(msg);
        }
        catch(InterruptedException e){}
      }
    }
    public void killme()
    //..................
    {
      fatal = true;
      queue.notify();
    }
  }

  
  // Handle client requests off the queue
  private void doRequest(Rmessage msg)
  //----------------------------------
  {
    Rmessage reply = null;
    switch(msg.getType())
    {
    case Rmessage.tLOGIN:
      reply = doPost(loginUrl,msg,"Successful login","Error on login"); //doLogin((LoginMessage)msg);
      break;
    case Rmessage.tLOGOFF:
      reply = doPost(logoffUrl,msg,"","");
      break;
    case Rmessage.tNEWUSER:
      reply = doPost(newuserUrl,msg,"","");	//doNewUser((NewUserMessage)msg);
      break;
    case Rmessage.tGETCHARLIST:
      reply = doPost(getcharlistUrl,msg,"",""); //doGetCharList((GetCharListMessage)msg);
      break;
    case Rmessage.tGETSTORYLIST:
      reply = doPost(getstorylistUrl,msg,"",""); //doGetStoryList((GetStoryListMessage)msg);
      break;
    case Rmessage.tLOADSTORY:
      reply = doPost(getstoryUrl,msg,"",""); //doLoadStory((LoadStoryMessage)msg);
      break;
    case Rmessage.tLOADCHAR:
      reply = doPost(getcharUrl,msg,"",""); //doLoadChar((LoadCharMessage)msg);
      break;
    case Rmessage.tREFERRAL:
      reply = doPost(referralUrl,msg,"",""); //doReferral((ReferralMessage)msg);
      break;
    case Rmessage.tGETUPDATELIST:
      reply = doPost(getupdatelistUrl,msg,"",""); //doGetUpdateList((GetUpdateListMessage)msg);
      break;
    case Rmessage.tGETUPDATE:
      reply = doGetUpdate((GetUpdateMessage)msg);
      break;
    case Rmessage.tUPLOADSTORYDATA:
      reply = doPut(uploadstoryUrl,msg,"",""); //doUploadStory((UpLoadStoryMessage)msg);
      break;
    case Rmessage.tUPLOADCHARDATA:
      reply = doPut(uploadstoryUrl,msg,"",""); //doUploadChar((UpLoadCharMessage)msg);
      break;
    case Rmessage.tOPSLOGIN:
      reply = new ServiceStartedMessage(new InetOpsService(msg));
      break;
    default:
      reply = null;
      break;
    }
    if (reply != null)
	    rmessager.servicesDispatchMsg(reply);
  }

  private Rmessage doPut(URL url, Rmessage msg, String ackmsg, String errmsg)
  //-------------------------------------------
  {
    return doCommon(url,"PUT",msg.toXML(), "text/xml",ackmsg,errmsg,true);
  }
  private Rmessage doGet(URL url, Rmessage msg, String ackmsg, String errmsg)
  {
    return doCommon(url,"GET","","text/ascii",ackmsg,errmsg,false);
  }
  private Rmessage doPost(URL url, Rmessage msg, String ackmsg, String errmsg)
  //-------------------------------------------
  {
    return doCommon(url,"POST",msg.toQueryString(),"application/x-www-form-urlencoded",
                    ackmsg,errmsg,true);
  }
// test
private void doPost(URL url, String query, String ackmsg, String errmsg)
{
  doCommon(url,"POST",query,"application/x-www-form-urlencoded",ackmsg,errmsg,true);
}
  private Rmessage doCommon(URL url, String method, String content, String contentType,
                            String ackmsg, String errmsg, boolean fullCookies)
  //-----------------------------------------------------------------------------------
  {
System.out.println("url:"+url);
System.out.println("content:"+content);
System.out.println("contentType:"+contentType);
    Rmessage retm = null;
    HttpURLConnection con = null;
    try
    {
      con = (HttpURLConnection)url.openConnection();

      con.setDoInput(true);
      if(!method.equals("GET"))
        con.setDoOutput(true);
      con.setRequestMethod(method);
      if(fullCookies)
        if(sessionCookie!=null & sessionCookie.length()>0)
        {
          con.setRequestProperty("Cookie",sessionCookie);
          System.out.println("Setting request cookie: "+sessionCookie);
        }
   //   con.setRequestProperty("jsessionid",sessionID);
      con.setRequestProperty("Content-Type",contentType);
      con.setRequestProperty("Connection","Keep-Alive");
      con.setRequestProperty("Cache-Control","no-cache");
      con.setRequestProperty("Referer","http://sunspot.cs.nps.navy.mil:8080/recruits/struts/goToCreateNewUser.do;jsessionid="+sessionID);	//6980D55854920382F21834841061F1A1");
      con.setRequestProperty("Accept-Encoding","gzip, deflate");
      con.setRequestProperty("Accept-Language","en-us");
      con.setRequestProperty("Host","sunspot.cs.nps.navy.mil:8080");

      if(!method.equals("GET"))
        if(content.length() > 0)
        {
          OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(con.getOutputStream()));
          osw.write(content);
          osw.close();
        }
      // Get the reply (XML ack msg)
      //retm = Rmessage.makeMessage(con.getInputStream());
InputStream is = con.getInputStream();
InputStreamReader isr = new InputStreamReader(is);
BufferedReader bw = new BufferedReader(isr);

      String thissessionID = con.getHeaderField("sessionid");
      if(thissessionID != null && thissessionID.length()>0)
        sessionID = thissessionID;
      String thissessionCookie = con.getHeaderField("Set-Cookie");
      if(thissessionCookie != null && thissessionCookie.length()>0)
        sessionCookie = thissessionCookie;

      String key;
      for (int n=1;n<30;n++)
      {
        key = con.getHeaderFieldKey(n);
        if(key == null)
          break;
        if(key.equalsIgnoreCase("success"))
          retm = new AckMessage("0",ackmsg);
        else if(key.equalsIgnoreCase("error"))
          retm = new ErrorMessage("0",errmsg);
        System.out.println(key+": "+con.getHeaderField(n));
      }
/*
      System.out.println("content:");
      int c = (char)bw.read();
      while (c != -1)
      {
        System.out.print((char)c);
        c = bw.read();
      }
*/
    }
    catch (Exception e)
    {
      System.out.println("Error in InetServices.doCommon("+method+")" + e);
      return null;
    }

    try{Thread.sleep(5000);}catch(Exception e){}


    return retm;
  }

  private Rmessage doGetUpdate(GetUpdateMessage msg)
  {
    return null;
  }
/*  public static void main(String args[])
  {
    URL u=null;
    // first slash is important
    try{u = new URL("http","localhost","/miketest/PutServlet");}catch (Exception e)
    {
      System.out.println("whoa");
    }
    junkdoPut(u,new LoginMessage(new StringTokenizer("1\tBob")));
  }
  */

  //private Rmessage doGetPost(String getURL, String postURL, String username, String password)
  static private Rmessage doGetPost(String getURL, String postURL, String query)
  {
    Rmessage retm;
    try
    {
      URL url = new URL(getURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();

      con.setDoInput(true);
      con.setDoOutput(true);
      con.setRequestMethod("GET");
      //con.setRequestProperty("Cookie",sessionCookie);
      //con.setRequestProperty("Content-type",contentType);

      //OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(con.getOutputStream()));
      //osw.write("?username="+username+"&password="+password);
      //osw.close();
      BufferedReader inStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String currentLine;
      while(null != (currentLine = inStream.readLine()))
        System.out.println(currentLine);

      int retcode = con.getResponseCode();
      String rets = con.getResponseMessage();
      String errorSt = con.getHeaderField("error");
      String successSt = con.getHeaderField("success");

      // Get the reply (XML ack msg)
      retm = Rmessage.makeMessage(con.getInputStream());
    }
    catch (Exception e)
    {
     // System.out.println("XML error in InetServices.doCommon("+method+")" + e);
      return null;
    }
    return retm;

  }
  public static void main(String args[])
  {
    InetServices svcs = new InetServices();
    svcs.go();
    URL url=null;
    /*
    try{
    url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/logon.do");
    //url = new URL("http://localhost/miketest/ShowRequest");
    }catch(Exception e)
    { System.out.println("nuts1"); }
    svcs.doPost(url,"userName=a&password=a");

    try{
    url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/doCharacterSearch.do");
    }catch(Exception e)
    { System.out.println("nuts2"); }
    svcs.doPost(url,"whichUsers=allUsers&"+
                    //"subString=null&"+
                    //"onOrAfter=null&"+
                    "returnAsXml=true&"+
                    "pageSize=4");
*/
    try{
    url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/saveRegistration.do;jsessionid="+svcs.sessionID);
    }catch(Exception e)
    { System.out.println("nuts3"); }
    svcs.doPost(url,
              "action=Create&"+
              "user.title=mr&"+
              "user.firstName=Homar4&"+
              "user.lastName=Smithson4&"+
              "user.email="+URLEncoder.encode("jmbailey@nps.navy.mil")+"&"+
              "user.phoneNumber="+URLEncoder.encode("831 656-3876")+"&"+
              "user.userName=homie4&"+
              "user.password=marge4&"+
              "user.confirmPassword=marge4&"+
              "user.address.address1="+URLEncoder.encode("15 Main St.")+"&"+
              "user.address.address2="+URLEncoder.encode("Apt. 12")+"&"+
              "user.address.city=Monterey&"+
              "user.address.state=CA&"+
              "user.address.zip=93940&"+
              "submit=Submit",
              "",""
              );
    /*try{
    url = new URL("http://sunspot.cs.nps.navy.mil:8080/recruits/struts/saveRegistration.do;jsessionid="+svcs.sessionID);
    }catch(Exception e)
    { System.out.println("nuts3"); }
    svcs.doPost(url,
              "action=Create&"+
              "user.title=mr&"+
              "user.firstName=Homar3&"+
              "user.lastName=Smithson&"+
              "user.email="+URLEncoder.encode("jmbailey@nps.navy.mil")+"&"+
              "user.phoneNumber="+URLEncoder.encode("831 656-3876")+"&"+
              "user.userName=homie3&"+
              "user.password=marge3&"+
              "user.confirmPassword=marge3&"+
              "user.address.address1="+URLEncoder.encode("15 Main St.")+"&"+
              "user.address.address2="+URLEncoder.encode("Apt. 12")+"&"+
              "user.address.city=Monterey&"+
              "user.address.state=CA&"+
              "user.address.zip=93940&"+
              "submit=Submit"
              );
*/
  }


}
// EOF
