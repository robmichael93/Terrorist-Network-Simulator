/******************************
 * File:	StoryListMessage.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Fri Jan 25 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.messaging;

import com.armygame.recruits.services.*;
import java.util.Iterator;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.InputSource;


public class StoryListMessage extends Rmessage
/********************************************/
{
  public StoryListMessage(Document xmlDoc)
  //=====================================
  {
    super(tSTORYLIST,STORYLIST);
  }
  public String toTSV()
  {
    return "";
  }
  public String toQueryString()
  {
    return "";
  }
  public String toXML()
  {return "";}
}
// EOF
