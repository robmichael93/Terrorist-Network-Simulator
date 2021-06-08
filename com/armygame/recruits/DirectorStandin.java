/******************************
 * File:	DirectorStandin.java
 * Title:	Army Game / Recruits
 * Description:
 * Created: 	Wed Feb 06 2002
 * Copyright:	Copyright (c) 2002
 * Organization: MOVES Institute, Naval Postgraduate School
 * @version	1.0
 * @author	Mike Bailey
 * @since	JDK 1.3.1
 *****************************/

package com.armygame.recruits;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class DirectorStandin extends JFrame
/***************************/
{
  JTextField hostTextField;
  JTextArea results;
  PrintWriter os = null;
  JScrollPane jsp;
    //ServerSocket sock;

  ActionListener runner = new ActionListener()
  {
    // Button clicks come here
    public void actionPerformed(ActionEvent evt)
    {
      String cmd = evt.getActionCommand();
      System.out.print("Director got button event: "+cmd);
        os.print(cmd );
      os.flush();
      updateGUI("....sent "+cmd);
    }
  };

  // first = button text, 2nd = cmd sent to Storyengine
  String [][] buttonData = {
  {"NAVIGATE",		"NAVIGATE\tPAUSE\n"},
  {"GETPLAYLIST",	"GETPLAYLIST\tHOMER\n"},
  {"INVAL.PLIST",	"INVALIDATEPLAYLIST\tThis is the reason\tfirst serial number\tsecond serial number\n"},
  {"GETCHARLIST",	"GETCHARLIST\t8\t*\t*\n"},
  //{"LOADSTORY",		"LOADSTORY\tstoryname\n"},
  {"PLAYLISTCOMPLETE", "PLAYLISTCOMPLETE\tThis is the reason\tstateChange1\tstateChange2\tstateChange3\n"},
  {"LOGIN",				"LOGIN\thomer\tsimpson\n"},
  {"NEWUSER",			"NEWUSER\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\tnuser\n"},
  //{"REFERRAL",		"REFERRAL\tblah\tblah\tblah\tblah\tblah\tblah\tblah\tblah\tblah\tblah\tblah\tblah\n"}
  {"WANT", "WANTCHARATTRIBUTES\n"}
  };

  public DirectorStandin ()
  //=======================
  {
    super("Director Stand-in for Recruits");
    addWindowListener(new WindowCloser());

    JPanel p;

    Container cp = getContentPane();
    cp.add(BorderLayout.NORTH, p = new JPanel());
    p.setBorder(new TitledBorder("Send these events to story engine:"));

    JButton [] butts = new JButton[buttonData.length];
    for(int i=0;i<buttonData.length;i++)
    {
      butts[i] = new JButton(buttonData[i][0]);
      p.add(butts[i]);
      butts[i].setActionCommand(buttonData[i][1]);
      butts[i].addActionListener(runner);
    }

    results = new JTextArea(20,60);
    results.setLineWrap(true);
    jsp = new JScrollPane(results);
    jsp.setBorder(new TitledBorder("Received from story engine:"));

    cp.add(BorderLayout.CENTER, jsp);
    pack();
    setVisible(true);

    // Now the network stuff; both connection and i/o done in separate thread
    new Thread(new Runnable()
    {
      public void run()
      {
        Socket ios = null;
        BufferedReader is = null;
        //PrintWriter os = null;
        int tries=0;
        while(true)
        {
          try
          {
            System.err.println("Connecting");
            ios = new Socket("localhost",3002);
            break;
          }
          catch(IOException e)
          {
            if(tries++ >=10)
               System.exit(0);
            System.err.println("Director retrying connection.");
            try{Thread.sleep(3000);}catch(Exception ee){}
          }
        } // while
        System.err.println("Connected.");
        try
        {
          is = new BufferedReader(
                  new InputStreamReader(
                    ios.getInputStream(), "8859_1"));
          os = new PrintWriter(
                  new OutputStreamWriter(
                    ios.getOutputStream(), "8859_1"), true);
          String msg;
          while((msg = is.readLine()) != null)
          {
            if(msg.length() > 8192)
              updateGUI(""+msg.length()+" characters received (\""+msg.substring(0,26)+"\").\n");
            else
              updateGUI(msg+"\n");

            os.print("ACK\n");
            System.out.println("Director sent ACK.");
          } // while
        }
        catch(IOException ex)
        {
          System.err.println(ex);
        }
        finally
        {
          try
          {
            if(is != null)
              is.close();
            if(os != null)
              os.close();
            if(ios != null)
              ios.close();
          }
          catch (IOException exc)
          {
            System.err.println("IO Error in close");
          }
        } // finally
        System.exit(0);
      }
    }).start();

    // constructor returns here, io thread keeps running
  }

  private void updateGUI(String s)
  //------------------------------
  // Used to update widget in Swing event thread where updates need to happen, instead of net io thread.
  {
       SwingUtilities.invokeLater(new RunnableWithParm(results,jsp,s));
   }

  // How to essentially fire off a thread, passing it parameters: (easier way somewhere?)
  class RunnableWithParm implements Runnable
  /****************************************/
  {
    JTextArea ta;
    JScrollPane jsp;
    String s;
    RunnableWithParm(JTextArea ta, JScrollPane jsp, String s)
    //=======================================================
    {
      this.ta = ta;
      this.jsp = jsp;
      this.s = s;
    }
    public void run()
    //---------------
    {
      ta.append(s);
      jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
    }
  }

  class WindowCloser extends WindowAdapter
  /**************************************/
  {
    public void windowClosing(WindowEvent e)
    {
      DirectorStandin.this.setVisible(false);
      DirectorStandin.this.dispose();
      System.exit(0);
    }
  }

  public static void main(String[] args)
  //------------------------------------
  {
    new DirectorStandin().setVisible(true);
  }

}
// EOF
