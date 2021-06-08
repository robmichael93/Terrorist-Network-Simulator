/******************************
 * File:	Updater.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Wed Aug 7 2002      
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Randy Jones
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.services;

import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

public class Updater {  	
 	static final String BASE_URL = "http://sunspot.cs.nps.navy.mil/recruits/update/"; // get this from properites
	Object updater = null;
	Method m_getSize, m_start, m_cancel, m_getClientVersion, m_getServerVersion, 
		m_getUpdateDescription;
	
	public Updater() throws Exception {
		URL[] url = {
			new URL(BASE_URL),
			new URL(BASE_URL + "updater.jar"),
			new URL(BASE_URL + "xml_o_matic.jar") // needs SAX parser
			};
		URLClassLoader l = new URLClassLoader(url);
		Class c = l.loadClass("com.updater.Updater");
		updater = c.newInstance();
		m_getClientVersion = updater.getClass().getMethod("getClientVersion", null); 
		m_getServerVersion = updater.getClass().getMethod("getServerVersion", null); 
		m_getUpdateDescription = updater.getClass().getMethod("getUpdateDescription", null); 
  		m_getSize = updater.getClass().getMethod("getSize", null);
  		m_start = updater.getClass().getMethod("start", null);
  		m_cancel = updater.getClass().getMethod("cancel", null); 		
	}

	// getClientVersion()
	//
	// Get the client version name, i.e. 1.2.2.
	// ----------------------------------------------------------------------
	public String getClientVersion() throws Exception {
	  	String ret = (String)m_getClientVersion.invoke(updater, null);
	  	return(ret); 
	} 
	
	// getServerVersion()
	//
	// Get the server version name, i.e. 1.4.7.
	// ----------------------------------------------------------------------
	public String getServerVersion() throws Exception {
	  	String ret = (String)m_getServerVersion.invoke(updater, null);
	  	return(ret); 
	} 
	
	// getUpdateDescription()
	//
	// Release notes for the update(s).
	// ----------------------------------------------------------------------
	public String getUpdateDescription() throws Exception {
	  	String ret = (String)m_getUpdateDescription.invoke(updater, null);	  	
	  	return(ret); 
	} 
	
	// getSize()
	//
	// Size of zero indicates application is "up to date" or update complete.
	// This can be called during the update to determine progress.
	// ----------------------------------------------------------------------
	public int getSize() throws Exception {
	  	Integer ret = (Integer)m_getSize.invoke(updater, null);
	  	return(ret.intValue()); 
	} 
   
   // start()
   //
   // Start the update.
   // ----------------------------------------------------------------------
	public void start() throws Exception {
	  	m_start.invoke(updater, null);
	} 
   
   // cancel()
   //
   // Returns true if cancel was possible.
   // ----------------------------------------------------------------------
	public boolean cancel() throws Exception {
		Boolean ret = (Boolean)	m_cancel.invoke(updater, null);
		return(ret.booleanValue());
	}  
    
}
// EOF
