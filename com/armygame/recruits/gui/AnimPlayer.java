package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;

public class AnimPlayer extends RPanel implements Runnable { //Frame implements Runnable {

    private static SoundTest myPlayer;
    private static SoundTest myBGPlayer;
    private static int FrameNum = 0;
    private JLayeredPane layeredPane;
    private JLabel[] imageLabels = new JLabel[5];
    private ImageIcon[] icons = {
          new ImageIcon("images/FORGRASS.gif"),
          new ImageIcon("images/Brad01.gif"),
          new ImageIcon("images/SA_00000.gif"),
          new ImageIcon("images/SB_00000.gif"),
          new ImageIcon("images/FGSANDBA.gif") };

    private ImageIcon[] animProtag = new ImageIcon[140];
    private ImageIcon[] animBuddy = new ImageIcon[140];

    public AnimPlayer()    {
        //super("Recruits Java Animation Player");

        myPlayer = null;
        myBGPlayer = null;

        // Initialize animation icons
        for( int i = 0; i < 140; i++ ) {
          String LeadingZeros = null;
          Integer id = new Integer(i);
          if ( ( i >= 0 ) && ( i <= 9 ) ) {
            LeadingZeros = "0000";
          } else if ( ( i >=10 ) && ( i<= 99 ) ) {
            LeadingZeros = "000";
          } else {
            LeadingZeros = "00";
          }
          String SAfilename = "images/SA_" + LeadingZeros + id.toString() + ".gif";
          String SBfilename = "images/SB_" + LeadingZeros + id.toString() + ".gif";
          // System.out.println( "Buddy Filename is " + SAfilename );
          // System.out.println( "Protag Filename is " + SBfilename );
          animBuddy[i] = new ImageIcon( SAfilename );
          animProtag[i] = new ImageIcon( SBfilename );
        }

        //Create the layers

        //Create and set up the layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setDoubleBuffered( true );
        layeredPane.setLayout( null );
        layeredPane.setPreferredSize(new Dimension(640, 480));
        layeredPane.setSize(layeredPane.getPreferredSize());


        //Create and add images as labels
        for( int i = 0; i < 5; i++ ) {
          imageLabels[i] = new JLabel( icons[i] );
          switch( i ) {
            case 0:
              imageLabels[i].setBounds( 0, 0, icons[i].getIconWidth(), icons[i].getIconHeight() );
              break;
            case 1:
              imageLabels[i].setBounds( 150, 210, icons[i].getIconWidth(), icons[i].getIconHeight() );
              break;
            case 2:
              imageLabels[i].setBounds( 290, 480 - icons[i].getIconHeight(), icons[i].getIconWidth(), icons[i].getIconHeight() );
              break;
            case 3:
              imageLabels[i].setBounds( 360, 480 - icons[i].getIconHeight(), icons[i].getIconWidth(), icons[i].getIconHeight() );
              break;
            case 4:
              imageLabels[i].setBounds( 0, 411, icons[i].getIconWidth(), icons[i].getIconHeight() );
              break;
          }
          layeredPane.add( imageLabels[i], 0 );
        }


        //Add control pane and layered pane to frame
       // Container contentPane = getContentPane();
        //contentPane.add(layeredPane);
        add(layeredPane);
      }

      public void InitAudio( String[] args ) {      
        myPlayer = new SoundTest();
        myBGPlayer = new SoundTest();
        myBGPlayer.AddSound( args[0] );
        for( int i = 1; i < args.length; i++ ) {
          myPlayer.AddSound( args[i] );
        }
      }

       boolean running = true;

      public void run() {
       Thread me = Thread.currentThread( );
       //me.setPriority(Thread.MIN_PRIORITY);
       myBGPlayer.Loop();
       myPlayer.Play();
       while (running) {
         try {
           me.sleep(142);
         } catch( InterruptedException e ) {
           System.out.println( "CAUGHT" );
           return;
         }

        if(paused == false)
          mypaint();


       }

    }
boolean paused = false;
public void go()
{
  paused = false;
  super.go();
}
public void pause()
{
  paused = true;
}
public void kill()
{
  running = false;
  myPlayer.stop();
  myBGPlayer.stop();
}
    private void mypaint( /*Graphics g*/ ) {
         // System.out.println( "Redraw with FrameNum = " + FrameNum );
         // Modify the values in the pixels array at (x, y, w, h)

         // Send the new data to the interested ImageConsumers
         layeredPane.removeAll();

         imageLabels[2] = new JLabel( animBuddy[FrameNum] );
         imageLabels[3] = new JLabel( animProtag[FrameNum] );
         imageLabels[2].setBounds( 290, 480 - icons[2].getIconHeight(), icons[2].getIconWidth(), icons[2].getIconHeight() );
         imageLabels[3].setBounds( 360, 480 - icons[3].getIconHeight(), icons[3].getIconWidth(), icons[3].getIconHeight() );
         FrameNum++;

         if( FrameNum > 139 ) {
           FrameNum = 0;
           myPlayer.Play();
          }

         for( int i = 0; i < 5; i++ ) {
           // imageLabels[i] = new JLabel( icons[i] );
           switch( i ) {
             case 0:
               imageLabels[i].setBounds( 0, 0, icons[i].getIconWidth(), icons[i].getIconHeight() );
               break;
             case 1:
               imageLabels[i].setBounds( 150, 210, icons[i].getIconWidth(), icons[i].getIconHeight() );
               break;
             case 4:
               imageLabels[i].setBounds( 0, 411, icons[i].getIconWidth(), icons[i].getIconHeight() );
               break;
           }
           // layeredPane.add( imageLabels[i], 2 - i );
           layeredPane.add( imageLabels[i], 0 );
         }
         }
/*
    public void update( Graphics g ) {
      System.out.println( "In update" );
      paint( g );
    }
*/
    public static void main(String[] args) {
      AnimPlayer frame = new AnimPlayer();
      myPlayer = new SoundTest();
      myBGPlayer = new SoundTest();

      myBGPlayer.AddSound( args[0] );

		  for( int i = 1; i < args.length; i++ ) {
			  myPlayer.AddSound( args[i] );
		  }
/*
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        }
      });
*/
      //frame.pack();
      frame.setVisible(true);
      frame.run();
    }
}
