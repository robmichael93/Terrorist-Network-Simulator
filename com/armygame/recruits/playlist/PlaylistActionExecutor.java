package com.armygame.recruits.playlist;

import java.util.List;
import java.util.Iterator;

import com.armygame.recruits.services.SimpleObjectFIFO;


/**
 * This class maintains a context for executing this playlist
 * It maintains a pointer to the currently executing <CODE>ActionFrame</CODE>,
 * and provides callbacks for use in synchronizing the completion of the frame
 * upon receipt of completion events from animations and sounds that are playing
 */
public class PlaylistActionExecutor implements Runnable, PlaylistSynchronizationCallback {

  static public class PlaylistExecutionCommand {

    final private String myCommand;

    private PlaylistExecutionCommand( String command ) {
      myCommand = command;
    }

    public boolean equals( Object rhs ) {
      boolean Result = false;
      if ( rhs instanceof PlaylistExecutionCommand ) {
        Result = myCommand.equals( ( (PlaylistExecutionCommand) rhs ).myCommand );
      }
      return Result;
    }

    public static final PlaylistExecutionCommand PLAY = new PlaylistExecutionCommand( "Play" );

    public static final PlaylistExecutionCommand PAUSE = new PlaylistExecutionCommand( "Pause" );

    public static final PlaylistExecutionCommand RESUME = new PlaylistExecutionCommand( "Resume" );

    public static final PlaylistExecutionCommand STOP = new PlaylistExecutionCommand( "Stop" );

  }

  private PlaylistExecutor myInvoker;

  private MediaPlayerContext myMediaPlayers;

  private List myActionFrames;

  private SimpleObjectFIFO myRequestQueue;

  /**
   * This iterator traverses the list of <CODE>ActionFrame</CODE>s, pointing to the
   * <CODE>ActionFrame</CODE> currently being executed
   *
   * @see Playlist.myActionFrames
   */
  private Iterator myCurrentActionFrameItr;

  private PlaylistExecutionCommand myCurrentCommand;

  private boolean myIsRunningFlag;


  /**
   * Initializes the iterator to the first element of the <CODE>Playlist</CODE>'s list
   * of <CODE>ActionFrame<CODE>s.
   *
   * @param executor The object that initiated operations on this playlist
   * @param castUse The <CODE>CastUsage</CODE> summary necessary for buffering the resources
   * needed for execution of the <CODE>Playlist</CODE> by this iterator
   * @param charUse The <CODE>CharacterUsage</CODE> summary necessary for buffering the resources
   * needed for execution of the <CODE>Playlist</CODE> by this iterator
   * @param locUse The <CODE>LocationUsage</CODE> summary necessary for buffering the resources
   * needed for execution of the <CODE>Playlist</CODE> by this iterator
   * @param actionFrames The list of <CODE>ActionFrame</CODE>s for this <CODE>Playlist</CODE>
   */
  public PlaylistActionExecutor( MediaPlayerContext playerContext, PlaylistExecutor invoker ) {
    myMediaPlayers = playerContext;
    myInvoker = invoker;
    myCurrentActionFrameItr = null;
    myActionFrames = null;
    myCurrentCommand = null;
    myRequestQueue = new SimpleObjectFIFO( 1 );
    myIsRunningFlag = true;
  }

  public void run() {
    // PlaylistExecutionExecutive();
    try {
      while( myIsRunningFlag ) {
        Playlist PList = (Playlist) myRequestQueue.remove();
        myActionFrames = PList.GetActionFrames();
        myCurrentCommand = PlaylistExecutionCommand.PLAY;
        PlaylistExecutionExecutive();
      }
    } catch( InterruptedException ie ) {
      return;
    }
    //System.out.println( "Playlist Executor Exiting" );
  }

  public synchronized void Play( Playlist pList ) {
    // System.out.println( "Playlist Action Executor asked to play playlist" );
    try {
      myRequestQueue.add( pList );
    } catch( InterruptedException ie ) {
      return;
    }
  }

  public synchronized void Notify() {
    notifyAll();
  }

  public synchronized void FrameComplete() {
    //System.out.println( "PlaylistActionExecutor Received Frame Complete" );
    notifyAll();
  }

  public synchronized void PlaylistExecutionExecutive() {

    // boolean Waiting = true;

    // This blocks waiting for notification that it should start playing the playlist
    // try {
    //   while( Waiting ) {
    //     // System.out.println( "PlaylistActionExecutor waiting for playlist" );
    //     wait(100);
    //     if ( myCurrentCommand != null ) {
    //       if ( myCurrentCommand.equals( PlaylistExecutionCommand.PLAY ) ) {
    //         Waiting = false;
    //       }
    //     }
    //   }
    // } catch( InterruptedException e ) {
    //   return;
    // }

    // We need to synchronize all Collection iterators
    synchronized( myActionFrames ) {


      // Step through all the ActionFrames and execute them
      // We block using wait() after executing each frame for that frame to notify us
      // (via FrameComplete() above) that the frame is finished
      myCurrentActionFrameItr = myActionFrames.iterator();
      while( myCurrentActionFrameItr.hasNext() ) {
        //System.out.println( "Got an action frame to execute" );
        ActionFrame ExecutingFrame = (ActionFrame) myCurrentActionFrameItr.next();

        MediaPlaybackSynchronizer Synchronizer = new MediaPlaybackSynchronizer( myMediaPlayers, this,
                                                                                ExecutingFrame.GetSynchronizationPolicy() );
        // System.out.println( "Made a synchronizer" );
        new Thread( Synchronizer, "Synchronizer" ).start();


        // We pass ourself to the Execute() method so that it can call us back via FrameComplete()
        ExecutingFrame.Execute( myMediaPlayers, Synchronizer );

        boolean FrameExecuting = true;
        try {
          while( FrameExecuting ) {
            //System.out.println( "PlaylistActionExecutor waiting for frame complete" );
            wait();
            //System.out.println( "PlaylistActionExecutor woken up" );
            FrameExecuting = false;
          }
        } catch( InterruptedException fe ) {
          return;
        }
      }

    }

    // Tell whoever called us that we finished playing all the action frames in this playlist
    myMediaPlayers.GetAnimationPlayer().SceneDone();
    myInvoker.PlaylistCompleted();
    myIsRunningFlag = false;

  }



}
