/******************************
 * File:	AckMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 18 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.InputSource;


public class AckMessage extends Rmessage
/******************************************/
{
  public String seqOfReq="";
  public String info="";
  public AckMessage(Document xmlDoc)
  //================================
  {
    super(tACK,ACK);
    Element root = xmlDoc.getRootElement();
    Element acked = root.getChild("ackedsequence");
    if(acked != null)
      seqOfReq = acked.getAttribute("value").getValue();
    else
      seqOfReq = "-1";
  }

  public AckMessage(String sequenceOfRequest, String msg)		// seq number we're acking
  //=========================================
  {
    super(tACK,ACK);
    seqOfReq = sequenceOfRequest;
    info = msg;
  }
  public String toTSV(){return "";}
  public String toQueryString(){return "";}
  public String toXML()
  //-------------------
  {
    return "<ACK><sequence>"+sequence+"</sequence><ackedsequence>"+seqOfReq +
           "</ackedsequence></ACK>";
  }

}
// EOF
