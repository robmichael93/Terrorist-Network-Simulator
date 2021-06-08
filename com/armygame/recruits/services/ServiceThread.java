/******************************
 * File:	ServiceThread.java
 * Title:	Army Game / Recruits
 * Description:
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.services;

import com.armygame.recruits.messaging.*;
import com.armygame.recruits.RecruitsMain;

public abstract class ServiceThread extends Thread {
	Rmessage msg;
	boolean cancel;
	
	ServiceThread(Rmessage msg) {
		this.msg = msg;
	}
	
	public void setCancel(boolean b) {
		this.cancel = b;
	}
	
	void dispatch(Rmessage msg) {
		if (!cancel)
			RecruitsMain.instance().messager.servicesDispatchMsg(msg);
	}
}
// EOF
