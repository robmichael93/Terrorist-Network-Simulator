/******************************
 * File:	ErrorMessage.java
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
import java.io.InputStream;

public class ErrorMessage extends Rmessage
/********************************************/
{
  public String seqOfReq;
  public String errorInfo;
  public ErrorMessage(InputStream in)
  //=================================
  {
    super(tERROR,ERROR);
  }
  public ErrorMessage(String reqSeq,String info)
  {
    super(tERROR,ERROR);
    seqOfReq = reqSeq;
    errorInfo = info;
  }
  public String toTSV(){return "";}
  public String toQueryString(){return "";}
  public String toXML()
  {return "";}


}
// EOF
