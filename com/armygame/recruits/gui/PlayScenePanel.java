//
//  HistoryPanel.java
//  RecruitsJavaGUI
//
//  Created by Mike Bailey on Thu Mar 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import com.armygame.recruits.playlist.MediaPlayerContext;
import com.armygame.recruits.playlist.PlaylistPlayer;
import com.armygame.recruits.playlist.PlaylistCompleteCallback;
import com.armygame.recruits.animationplayer.AnimationPlayer;
import com.armygame.recruits.globals.MissingAssetLog;


public class PlayScenePanel extends RPanel implements PlaylistCompleteCallback
{
  private MediaPlayerContext myMediaPlayers;
  private PlaylistPlayer myPlaylistPlayer;

  MainFrame mf;
  //JTextArea ta;
  PlayScenePanel(MainFrame main)
  {
    mf = main;
    setBackground( new Color(0,0,0,0)); //Color.black );
    setLayout( null );
  /*
    myMediaPlayers = new MediaPlayerContext( true, 2, true );
    myPlaylistPlayer = new PlaylistPlayer( myMediaPlayers, this );
    myMediaPlayers.GetAnimationPlayer().setVisible( true );
    add( myMediaPlayers.GetAnimationPlayer() );
 */
    setVisible(true);
  }

  public void runPlaylist(com.armygame.recruits.playlist.Playlist pl)
  {

    final com.armygame.recruits.playlist.Playlist playlist = pl;
     SwingUtilities.invokeLater(new Runnable()
     {
       public void run()
       {
         myPlaylistPlayer.ExecutePlaylist( playlist );
       }
     });
  }

  public void PlaylistComplete()
  {
    System.out.println( "Done playing playlist" );
    System.out.println( "Missing Assets for last Playlist:\n" + MissingAssetLog.Instance().toXML( "" ) );
    mf.handlers.eventIn(ButtonFactory.PLAYLISTFINISHED);
  }

  public void finishInit()
  {
    myMediaPlayers = new MediaPlayerContext( mf, true, 2, true );
    myPlaylistPlayer = new PlaylistPlayer( myMediaPlayers, this );
    AnimationPlayer ap = myMediaPlayers.GetAnimationPlayer();
    ap.setVisible( true );
    //ap.setLocation(0,0);
    //ap.setSize(640,480);
    //ap.setBounds(0,0,640,480);
    add(ap);
    //add( myMediaPlayers.GetAnimationPlayer() );
  }

}
