/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * User: mike
 * Date: Jun 6, 2002
 * Time: 10:27:13 AM
 */
package com.armygame.recruits.globals;

import com.armygame.recruits.storyelements.sceneelements.CharInsides;

import java.io.Serializable;

public class SavedCharacter implements Serializable
{
  public String firstName="";
  public String lastName="";
  public String bio="";
  public int actorIndex;
  public CharInsides charinsides=new CharInsides();
  String fileName;
  String desc;
  
	public String getFileName() {
		return(fileName);
	}
	
	public void setFileName(String s) {
		fileName = s;
	}
	
	public String getDesc() {
		return(desc);
	}
	
	public void setDesc(String s) {
		desc = s;
	}
  
}
