package com.armygame.recruits.playlist;

import java.io.IOException;
import javax.swing.event.*;

import com.armygame.recruits.gui.MainFrame;
import com.armygame.recruits.sound.SoundChannel;


public class MoviePlayer implements Runnable {

  private class MovieEventListener implements ChangeListener {
    private MoviePlayer mySynchronizeCallback;

    public MovieEventListener( MoviePlayer callback ) {
      mySynchronizeCallback = callback;
    }

    public void stateChanged( ChangeEvent e ) {
      // This change listener will be called when a JMF movie finishes
      System.out.println( "Got movie done signal" );
      mySynchronizeCallback.MovieDone();
    }
  }

  private MediaPlaybackSynchronizer mySynchronizer;
  private String myMoviePath;
  private boolean myIsRunningFlag;
  private MediaSynchronizationEvent myTestEvent;
  private MainFrame myMainFrame;
  private MovieEventListener myMovieEventListener;


  public MoviePlayer( MainFrame mainFrame ) {
    // System.out.println( "MoviePlayer Constructor" );
    myMainFrame = mainFrame;
    mySynchronizer = null;
    myMoviePath = null;
    myIsRunningFlag = true;
    myMovieEventListener = null;
  }

  public synchronized void PlayMovie( MediaPlaybackSynchronizer synchronizer, String moviePath ) {
    myMoviePath = moviePath;
    // System.out.println( "In MoviePlayer PlayMovie() with " + myMoviePath );
    myTestEvent = new MediaSynchronizationEvent( "Movie Completed", myMoviePath );
    mySynchronizer = synchronizer;
    myMovieEventListener = new MovieEventListener( this );
    notifyAll();
  }

  public synchronized void MovieDone() {
    notifyAll();
  }

  public void run() {
    WaitForMovieToPlay();
  }

  public synchronized void WaitForMovieToPlay() {
    boolean Waiting = true;

    while( myIsRunningFlag ) {
      try {
        while( Waiting ) {
          synchronized( this ) {
            // System.out.println( "MoviePlayer waiting for next movie" );
            this.wait();
          }
          Waiting = false;
        }
      } catch( InterruptedException ie ) {
        return;
      }

      // System.out.println( "MoviePlayer calling main frame to play movie" );
      Waiting = true;
      myMainFrame.introPanel.addChangeListener( myMovieEventListener );
      myMainFrame.introPanel.setVisible( true );
      myMainFrame.lp.moveToFront(myMainFrame.introPanel);
      myMainFrame.introPanel.go( myMoviePath );

      // Start the movie playing and wait for it to tell us it's done
      try {
        while( Waiting ) {
          synchronized( this ) {
            // System.out.println( "MoviePlayer waiting for movie to finish" );
            this.wait();
          }
          Waiting = false;
        }
      } catch( InterruptedException ie ) {
        return;
      }

      Waiting = true;
      // System.out.println( "Movie finished - Trying to sync with wait for movie done" );
      myMovieEventListener = null;
      mySynchronizer.TestEvent( myTestEvent );

    }
  }

}
