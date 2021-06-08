/******************************
 * File:	CharacterAbstract.java
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

public class CharacterAbstract
/***************************/
{
  private String name;
  private String description;
  private Object key;

  CharacterAbstract(String name, String description, Object key)
  {
    this.name = name;
    this.description = description;
    this.key = key;
  }
  public String toString()
  {
    return name + "\t" + description + "\t" + key;
  }
}
// EOF
