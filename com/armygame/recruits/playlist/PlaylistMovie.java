package com.armygame.recruits.playlist;


/**
 * Encapsulates what we need to know about playing a movie in a playlist
 */
public class PlaylistMovie implements PlaylistMediaAction {


  /**
   * The name of the movie to play
   */
  private String myMovieName;



  /**
   * Constructor when we set an explicit mode rather than a animation loop count
   * @param characterName The name of the character performing this animation
   * @param animationName The name of the action the character is to perform
   * @param loopMode The looping mode for this animation
   * @param specialFlag <code>true</code> if this is a special animation for the character, else <code>false</code>
   */
  public PlaylistMovie( String movieName ) {
    myMovieName = movieName;
  }

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    // System.out.println( "In PlaylistMovie Execute()" );
    playerContext.GetMoviePlayer().PlayMovie( synchronizer, myMovieName );
  }

  /**
   * Emit what we need to know about a character animation as an action as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a location action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<PlayMovie>" );
    Result.append( "<MovieName>" ); Result.append( myMovieName ); Result.append( "</MovieName>" );
    Result.append( "</PlayMovie>" );
    return Result.toString();
  }

}
