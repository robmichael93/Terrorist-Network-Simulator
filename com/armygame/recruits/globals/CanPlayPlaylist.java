package com.armygame.recruits.globals;


public class CanPlayPlaylist {

  private static CanPlayPlaylist theirCanPlayPlaylist = null;

  private boolean myCanPlayFlag;

  private CanPlayPlaylist() {
    myCanPlayFlag = false;
  }

  public static CanPlayPlaylist Instance() {
    if ( theirCanPlayPlaylist == null ) {
      theirCanPlayPlaylist = new CanPlayPlaylist();
    }
    return theirCanPlayPlaylist;
  }

  public boolean CanPlay() {
    return myCanPlayFlag;
  }

  public void SetCanPlayFlag( boolean flagVal ) {
    myCanPlayFlag = flagVal;
  }
}
