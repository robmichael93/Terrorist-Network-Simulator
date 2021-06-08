/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 25, 2002
 * Time: 8:52:30 AM
 */
package com.armygame.recruits.messaging;

import com.armygame.recruits.storyelements.sceneelements.CharInsides;

import java.util.StringTokenizer;

public class NewCharacterMessage extends Rmessage
{
  StringTokenizer st;
  CharInsides caObj;
  //this is how one of these messages is made when being sent from director
  public NewCharacterMessage(StringTokenizer st)
  //----------------------------------------------
  {
    super(tNEWCHARACTER,NEWCHARACTER);
    this.st = st;
  }

  //this is how one is made by the storyengine to be sent to director
  public NewCharacterMessage(CharInsides charObj)
  //-----------------------------------------------
  {
    super(tNEWCHARACTER,NEWCHARACTER);
    this.caObj = charObj;
  }

  //this converts this message to a string which is sent across to director.  This message will
  // have been created by the story engine, so we know charObj is not null.
  public String toTSV()
  //------------------
  {
    StringBuffer sb = new StringBuffer();
    sb.append("NEWCHARACTER");
    sb.append("\t"+caObj.unallocated);

    sb.append("\t"+caObj.loyalty);
    sb.append("\t"+caObj.duty);
    sb.append("\t"+caObj.respect);
    sb.append("\t"+caObj.selfless);
    sb.append("\t"+caObj.honor);
    sb.append("\t"+caObj.integrity);
    sb.append("\t"+caObj.courage);

    sb.append("\t"+caObj.energy);
    sb.append("\t"+caObj.energylast);
    sb.append("\t"+caObj.skill);
    sb.append("\t"+caObj.skilllast);
    sb.append("\t"+caObj.knowledge);
    sb.append("\t"+caObj.knowledgelast);
    sb.append("\t"+caObj.financial);
    sb.append("\t"+caObj.financiallast);
    sb.append("\t"+caObj.strength);
    sb.append("\t"+caObj.strengthlast);
    sb.append("\t"+caObj.popularity);
    sb.append("\t"+caObj.popularitylast);

    sb.append("\t"+caObj.goalString);


    return sb.toString();
  }

  public Object toObject()
  {
    return caObj;
  }

  // old way:
  // this returns this message to an object to be used by the story engine.  This message will
  // have been create by coming from director, se we know st is not null.
  public CharInsides toCharObj()
  //----------------------------
  {
    return caObj;
  }

  // used if this were going over the inet
  public String toQueryString()
  {
    return "";
  }

  // used if this were required to be XML
  public String toXML()
  {
    return "";
  }
}
// EOF
