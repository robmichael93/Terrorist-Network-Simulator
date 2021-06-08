/******************************
 * File:	LHIOMessageListener.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Thu Feb 07 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.localhostio;

import java.util.StringTokenizer;

public interface LHIOMessageListener
/**********************************/
{
  public void lineReceiver(String typ, StringTokenizer s);
  //------------------------------------------------------
}
// EOF
