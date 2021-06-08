
package com.armygame.recruits.mediaelements.players;

import com.armygame.recruits.mediaelements.rawmedia.MediaAsset;
import com.armygame.recruits.mediaelements.rawmedia.MediaAssetPlaylist;
import com.armygame.recruits.mediaelements.rawmedia.WaveMediaAsset;

public class WaveMediaPlayer extends MediaPlayer {


  private Object[] myArgs;

  public WaveMediaPlayer( Object[] args ) {
    super();
    myArgs = args;
  }

  public void play( MediaAssetPlaylist playlist ) {
    super.play( this, playlist );
    System.out.println();
  }

  public void play( MediaAsset asset ) {
    if ( asset instanceof WaveMediaAsset ) {
      System.out.print( ((WaveMediaAsset)asset).getAsset() + " " );
    }
  }

} // class WaveMediaPlayer

