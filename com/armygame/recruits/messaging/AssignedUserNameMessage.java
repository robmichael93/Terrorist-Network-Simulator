/******************************
 * File:	AssignedUserNameMessage.java
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

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.InputSource;

public class AssignedUserNameMessage extends Rmessage
/*******************************************************/
{
  private String uName;
  AssignedUserNameMessage(Document doc)
  //===================================
  {
    super(tASSIGNEDUSERNAME,ASSIGNEDUSERNAME);
  }
  AssignedUserNameMessage(String name)
  //==================================
  {
    super(tASSIGNEDUSERNAME,ASSIGNEDUSERNAME);
    sequence = ""+nextSequence++;
    uName = name;
  }
  public String toTSV()
  //-------------------
  {
    return ("USERNAME\t"+sequence+"\t"+uName);
  }
  public String toQueryString()
  //---------------------------
  {
    return ("?name="+uName);
  }
  public String toXML()
  {return "";}
  public Object toObject()
  {
    return uName;
  }
}
// EOF
