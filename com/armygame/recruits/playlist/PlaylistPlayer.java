package com.armygame.recruits.playlist;

public class PlaylistPlayer implements PlaylistExecutor {

  private MediaPlayerContext myMediaPlayers;
  private PlaylistResourceLoader myResourceLoader;
  private PlaylistActionExecutor myPlaylistExecutor;
  private PlaylistCompleteCallback myInvoker;
  private Playlist myPlaylist;

  public PlaylistPlayer( MediaPlayerContext players, PlaylistCompleteCallback invoker ) {
    myMediaPlayers = players;
    myInvoker = invoker;
    myPlaylist = null;
    // myResourceLoader = new PlaylistResourceLoader( myMediaPlayers, this );
    // new Thread( myResourceLoader ).start();
    // myPlaylistExecutor = new PlaylistActionExecutor( myMediaPlayers, this );
    // new Thread( myPlaylistExecutor ).start();
  }

  private void StartWorkers() {
    myResourceLoader = new PlaylistResourceLoader( myMediaPlayers, this );
    myPlaylistExecutor = new PlaylistActionExecutor( myMediaPlayers, this );
    new Thread( myResourceLoader, "ResourceLoader" ).start();
    new Thread( myPlaylistExecutor, "PlaylistExecutor" ).start();
  }
  /**
   * This is the routine that the <CODE>Playlist</CODE> executing due to a <CODE>ExecutePlaylist</CODE>
   * call will call back to to inform the caller that the playing of the playlist is
   * complete.
   */
  public void PlaylistCompleted() {
    myInvoker.PlaylistComplete();
  }
  /**
   * Causes the specified <CODE>Playlist</CODE> to process its header information
   * and buffer all required assets in preparation for playing the playlist.  This
   * call will return immediately, but the Playlist will continue extended processing
   * and buffering of playlist resources.  Upon
   * completion of buffering the <CODE>Playlist</CODE> object will call back to
   * this object's <CODE>BufferingCompleted()</CODE> method, indicating that
   * buffering is complete.  This callback architecture is designed to permit the
   * caller to manage the user interface during a time consuming buffering operation.
   * The caller can defer updating the GUI to the playback screen during buffering to
   * provide a latency hiding interface graphic/animation.
   *
   * Prototypical Implementation:
   * The <CODE>BufferPlaylist</CODE> method will typically be implemented as follows:
   * <CODE>public void BufferPlaylist( Playlist pList ) { pList.BufferResources( this ); }</CODE>
   *
   * @param pList The <CODE>Playlist</CODE> to buffer
   */
  public void BufferPlaylist(Playlist pList) {
    // System.out.println( "Buffering playlist" );
    System.gc();
    myResourceLoader.Buffer( pList );
  }
  /**
   * Starts execution of the specified <CODE>Playlist</CODE>.  This call assumes
   * that the <CODE>Playlist</CODE> was previously initialized by a call to the
   * playlist's <CODE>BufferResources</CODE> method (typically handled by the
   * <CODE>BufferPlaylist</CODE> method above).  Playing a playlist is an extended
   * action, so this routine will return immediately to the caller - the media actions
   * in the playlist will play out in separate threads in the playlists output
   * <CODE>JPanel</CODE>.  The call to execute the playlist will pass the calling
   * object (as a <CODE>PlaylistExecutor</CODE>) to the Playlist for use as a callback.
   * The playlist will use this object to call back to the <CODE>PlaylistCompleted</CODE>
   * method when the playlist is done playing so the caller can know when it has
   * finished.
   *
   * Prototypical Implementation:
   * The <CODE>ExecutePlaylist</CODE> method will typically be implemented as follows:
   * <CODE>public void ExecutePlaylist( Playlist pList ) { pList.PlayPlaylist( this ); }</CODE>
   *
   * @param pList The <CODE>Playlist</CODE> to play
   */
  public void ExecutePlaylist(Playlist pList) {
    StartWorkers();
    // System.out.println( "Playlist player asked to play playlist" );
    myPlaylist = pList;
    BufferPlaylist( myPlaylist );
  }
  /**
   * Resumes execution of a playlist that had been halted by a previous call to
   * <CODE>PausePlaylist</CODE>
   *
   * Prototypical Implementation:
   * The <CODE>ResumePlaylist</CODE> method will typically be implemented as:
   * <CODE>public void ResumePlaylist( Playlist pList ) { pList.ResumePlayback(); }</CODE>
   *
   * @param pList The <CODE>Playlist</CODE> whose playback is to be resumed
   */
  public void ResumePlaylist(Playlist pList) {
  }
  /**
   * This is the routine that the <CODE>Playlist</CODE> executing due to a <CODE>BufferPlaylist</CODE>
   * call will call back to to inform the caller that the buffering of the playlist is
   * complete.  The caller can use this callback to update the GUI or other latency hiding
   * mechanisms that it started during the (potentially) length playlist buffering and
   * initialization process.
   */
  public void BufferingCompleted() {
    // System.out.println( "Playlist buffering completed" );
    myPlaylistExecutor.Play( myPlaylist );
  }
  /**
   * Causes execution of a currently playing playlist to pause (audio and video) pending
   * a future call to <CODE>ResumePlaylist</CODE>
   *
   * Prototypical Implementation:
   * <CODE>PausePlaylist<CODE> will typically be implemented as follows:
   * <CODE>public void PausePlaylist( Playlist pList ) { pList.PausePlayback(); }</CODE>
   *
   * @param pList The <CODE>Playlist</CODE> whose playback is to be paused
   */
  public void PausePlaylist(Playlist pList) {
  }
}
