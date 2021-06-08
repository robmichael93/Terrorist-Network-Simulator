/******************************
 * File:	CharListMessage.java
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

import com.armygame.recruits.services.*;
import java.util.Iterator;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.xml.sax.InputSource;

public class CharListMessage extends Rmessage
/***********************************************/
{
  CharacterAbstractList lis;
  String ackseq;
  CharListMessage(Document xmlDoc)
  //==============================
  {
    super(tCHARLIST,CHARLIST);
    lis = CharacterAbstractList.build(xmlDoc);
    this.lis = lis;
    this.ackseq = xmlDoc.getRootElement().getChild("sequence").getAttribute("value").getValue();
  }
  public CharacterAbstractList getList()
  //--------------
  {
    return lis;
  }
  public String toTSV()
  //-------------------
  {
    String rets = "CHARLIST\t"+sequence+"\t"+ackseq+"\t"+lis.size();
    //Iterator ir = lis.iterator();
    //while(ir.hasNext())
    for(Iterator ir=lis.iterator(); ir.hasNext();)
    {
      CharacterAbstract ca = (CharacterAbstract)ir.next();
      rets = rets.concat("\t"+ca.toString());
    }
    return rets;
  }
  public String toQueryString()
  //---------------------------
  {
    return "";
  }
  public String toXML()
  {return "";}
  public Object toObject()
  {
    return lis;
  }
}
// EOF
