package com.armygame.recruits.playlist;

import com.armygame.recruits.animationplayer.AnimationPlayer;
import com.armygame.recruits.sound.SoundChannel;
import com.armygame.recruits.gui.MainFrame;


public class MediaPlayerContext {

  private AnimationPlayer myAnimationPlayer;
  private SoundChannel[] mySoundChannels;
  private MoviePlayer myMoviePlayer;

  public MediaPlayerContext( MainFrame mainFrame, boolean needAnimationPlayer, int numSoundChannels, boolean needMoviePlayer ) {
    // myAnimationPlayer = null;
    // mySoundChannels = null;
    myMoviePlayer = null;

    if ( needAnimationPlayer ) {
     myAnimationPlayer = new AnimationPlayer();
     new Thread( myAnimationPlayer, "AnimationPlayer" ).start();
    }

    if ( numSoundChannels > 0 ) {
      mySoundChannels = new SoundChannel[ numSoundChannels + 1 ];
      for( int i = 1; i <= numSoundChannels; i++ ) {
        mySoundChannels[i] = new SoundChannel( true );
      }
    }

    if ( needMoviePlayer ) {
      // We pass the movie player themain frame to use for playing back JMF media
      myMoviePlayer = new MoviePlayer( mainFrame );
      new Thread( myMoviePlayer, "MoviePlayer" ).start();
    }

  }

  public MoviePlayer GetMoviePlayer() {
    return myMoviePlayer;
  }

  public AnimationPlayer GetAnimationPlayer() {
    return myAnimationPlayer;
  }

  public SoundChannel GetSoundChannel( int channelID ) {
    return mySoundChannels[ channelID ];
  }

}
