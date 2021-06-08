package com.armygame.recruits.gui;


public class PlaybackPanel extends PlayScenePanel
{ //RPanel

  private AnimPlayer myPlayer;
  private MainFrame mf;

  public PlaybackPanel(MainFrame main)
  {
    //super();
    super(main);
    mf=main;
    myPlayer=new AnimPlayer();
    setLayout(null);
    myPlayer.setBounds(0,0,640,480);

    super.setVisible(false);

    add(myPlayer);

  }

  boolean showed=false;

  public void xShow(String[] args)
  {
    if(showed == false)
    {
      myPlayer.setVisible(true);
      myPlayer.InitAudio(args);

      new Thread(myPlayer).start();
      showed=true;
    }
    else
      myPlayer.go();
  }

  public void xpause()
  {
    myPlayer.pause();
  }
/*
  public void setVisible(boolean wh)
  {
    if(wh == false)
      myPlayer.kill();
    super.setVisible(wh);
  }
*/
}
