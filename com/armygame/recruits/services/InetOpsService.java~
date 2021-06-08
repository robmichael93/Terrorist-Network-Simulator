/******************************
 * File:	InetOpsServices.java
 * Title:	Army Game / Recruits
 * Description:
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits.services;

import com.armygame.recruits.messaging.*;

import java.io.*;
import java.util.StringTokenizer;
import BlowfishJ.*;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.soap.encoding.soapenc.Base64;

public class InetOpsService extends ServiceThread {
	
	public InetOpsService(Rmessage msg) {
		super(msg);
		start();
	}
	
	public void run() {
		dispatch(doOpsPost(this.msg));			
	}

	private String hexDump(byte[] b) {
  		StringBuffer sb = new StringBuffer();
  		String comma = "";
  		for (int i=0; i < b.length; ++i) {
  			sb.append(comma + Integer.toHexString((char)b[i] & 0xFF));
  			comma = ",";
  		}
  		return(sb.toString());
  	}

	private byte[] getKey() { // obfuscate the key
		byte ret[] = new byte[9];
		int i = 0;
		int ii = 83 + 42 - '*' - i;
		ret[0] = (byte)(char)(83 + 42 - '*' - i++); // S
		ret[1] = (byte)(char)(101 + 43 - '*' - i++); // e
		ret[2] = (byte)(char)(99 + 44 - '*' - i++); // c
		ret[3] = (byte)(char)(117 + 45 - '*' - i++); // u
		ret[4] = (byte)(char)(114 + 46 - '*' - i++); // r
		ret[5] = (byte)(char)(101 + 47 - '*' - i++); // e
		ret[6] = (byte)(char)(75 + 48 - '*' - i++); // K
		ret[7] = (byte)(char)(101 + 49 - '*' - i++); // e
		ret[8] = (byte)(char)(121 + 50 - '*' - i++); // y
		return(ret);
	}

  private byte[] align(String s) {
  	// align to the next 8 byte border
		byte[] messbuf, tempbuf = s.getBytes();
		int nMessSize = s.length();
		int nRest = nMessSize & 7;

		if (nRest != 0) {
			messbuf = new byte[(nMessSize & (~7)) + 8];
			System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);
			for (int nI = nMessSize; nI < messbuf.length ; nI++) {
				messbuf[nI] = 0x20;
			}
			System.out.println("message with " + nMessSize + " bytes aligned to " +
		                    messbuf.length + " bytes");
		} else {
			messbuf = new byte[nMessSize];
			System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);
		}
		return(messbuf);
	}

  // type 2 msg gets -5 back from server
	private String opsContent_(String user, String pass, InetAddress ia) {
		StringBuffer sb = new StringBuffer();
		char term = (char)255;
		sb.append("2" + term); 				// msg id
		sb.append("G54sa3" + term); 		// secret key
		sb.append("1.2.1" + term); 		// version
		sb.append(user + term); 			// user name
		BlowfishECB bfecb = new BlowfishECB(getKey());
		byte b[] = align(pass);
		bfecb.encrypt(b);
		sb.append(Base64.encode(b) + term); 						// password
		sb.append(ia.getHostAddress() + term); // ip address
		sb.append("\r\n");
		byte bb[] = align(sb.toString());
		System.out.println("pre: " + new String(bb));
		System.out.println("hex: " + hexDump(bb));
		bfecb.encrypt(bb);
		String ret = Base64.encode(bb);
		System.out.println("post: " + ret);
		return(ret);
	}

	// type 3 msg gets back 1,0,0,0,0,0,0,0,0...
	private String opsContent(String user, String pass, InetAddress ia) {
		StringBuffer sb = new StringBuffer();
		char term = (char)255;
		sb.append("3" + term); 				// msg id
		sb.append("G54sa3" + term); 		// secret key
		sb.append("1.2.1" + term); 		// version
		sb.append(user + term); 			// user name
		BlowfishECB bfecb = new BlowfishECB(getKey());
		byte b[] = align(pass);
		bfecb.encrypt(b);
		sb.append(Base64.encode(b) + term); // password
		sb.append("-1" + term + "-1" + term + "0" + term);
		sb.append("\r\n");
		byte bb[] = align(sb.toString());
		System.out.println("pre: " + new String(bb));
		System.out.println("hex: " + hexDump(bb));
		bfecb.encrypt(bb);
		String ret = Base64.encode(bb);
		System.out.println("post: " + ret);
		return(ret);
	}

	private String readResponse(BufferedReader is) throws Exception {
		StringBuffer sb = new StringBuffer();
		char[] bytearray = new char[512];
		int len = 0;
		while ((len = is.read(bytearray)) != -1) {
			sb.append(new String(bytearray, 0, len));
		}
		is.close();
		return(sb.toString());
	}

	Rmessage interpretResponse(String s) {
		Rmessage ret = new ErrorMessage("0", "Failed.");
		BlowfishECB bfecb = new BlowfishECB(getKey());
		byte b[] = Base64.decode(s);
		bfecb.decrypt(b);
		String ss = new String(b).trim();
		System.out.println("response:<" + new String(b).trim() + ">");
		StringTokenizer st = new StringTokenizer(ss, ",");
		String t1 = st.nextToken();
		if (t1.equals("1"))
			ret = new AckMessage("0","Success");
		else if (t1.equals("-1"))
			ret = new ErrorMessage("0", "Invalid password.");
		else if (t1.equals("-2"))
			ret = new ErrorMessage("0", "Invalid user id.");
		return(ret);
	}

	private Rmessage doOpsPost(Rmessage msg, Socket socket) {
		OpsLoginMessage olm = (OpsLoginMessage)msg;
		Rmessage ret = new AckMessage("0","Success");
		InetAddress ia = socket.getLocalAddress();
		String content = opsContent(olm.getUsername(), olm.getPassword(), ia);
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "ASCII"));
			BufferedWriter out =
				new BufferedWriter(new OutputStreamWriter(
					new BufferedOutputStream(socket.getOutputStream()), "ASCII"));
			out.write(content);
			out.flush();
			String s = readResponse(in);
			ret = interpretResponse(s);
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return(ret);
	}

	private Rmessage doOpsPost(Rmessage msg) {
		Rmessage ret = null;
		final String server[] = {"authwest.homelan.com", "autheast.homelan.com",
			"authcentral.homelan.com"};
		final int PORT = 20045;
		Socket socket = null;
		for (int i=0; (i < server.length) && !cancel; ++i) {
			System.out.println("creating socket to: " + server[i]);
			try {
				socket = new Socket(server[i], PORT);
				break;
			} catch(Exception e) {
				System.out.println(e);
        ret = new  ErrorMessage("0", e.getLocalizedMessage());
			}
		}
		if ((socket != null) && !cancel)
      ret = doOpsPost(msg,socket);
    return(ret); 
	}

}
// EOF
