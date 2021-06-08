/******************************
 * File:	CharacterAbstractList.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Tue Jan 15 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

public class CharacterAbstractList extends ArrayList
/**************************************************/
{
  public static CharacterAbstractList build(Document doc)
  {
    Element root = doc.getRootElement();
    //System.out.println("Root name: "+root.getName());
      
    List allch = root.getChildren("character");
    Iterator itr = allch.iterator();
    
    CharacterAbstractList lis = new CharacterAbstractList();
   
    while(itr.hasNext())
    {
      Element el = (Element)itr.next();
      String name = el.getAttributeValue("name").trim();
      String key  = el.getAttributeValue("key").trim();
      String data = el.getChild("description").getText().trim();
        
      lis.add(new CharacterAbstract(name,key,data));
    }
   
   return lis;
 }
}
// EOF
