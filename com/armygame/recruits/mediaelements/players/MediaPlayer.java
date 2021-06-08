
package com.armygame.recruits.mediaelements.players;

import java.util.Iterator;
import com.armygame.recruits.mediaelements.rawmedia.MediaAsset;
import com.armygame.recruits.mediaelements.rawmedia.MediaAssetPlaylist;

abstract public class MediaPlayer {

  abstract public void play( MediaAssetPlaylist playlist );
  abstract public void play( MediaAsset asset );

  protected void play( MediaPlayer player, MediaAssetPlaylist playlist ) {

    Iterator Itr = playlist.iterator();

    while( Itr.hasNext() ) {
      player.play( (MediaAsset)Itr.next() );
    }

  }

} // abstract class MediaPlayer
