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

import com.armygame.recruits.services.ServiceThread;

public class ServiceStartedMessage extends Rmessage {
	ServiceThread serviceThread;
	
  public ServiceStartedMessage(ServiceThread serviceThread) {
    super(tSERVICESTARTED, SERVICESTARTED);
    this.serviceThread = serviceThread;
  }

	public ServiceThread getServiceThread() {
		return(serviceThread);
	}
	
  public String toTSV(){return null;}
  public String toQueryString(){return null;}
  public String toXML(){return null;}

}
// EOF
