package com.armygame.recruits.playlist;


/**
 * Encapsulates what we need to know about playing an animation in a playlist
 */
public class PlaylistAnimation implements PlaylistMediaAction {


  /**
   * The Character performing the Animation
   */
  private String myCharacterName;

  /**
   * The Action the Character is performing
   */
  private String myActionName;

  /**
   * The Animation Mode for the Animation
   */
  private AnimationMode myAnimationMode;

  /**
   * The number of iterations to perform the animation if mode is not looping
   */
  private int myAnimationLoopCount;


  /**
   * Flag, when <code>true</code> means the action is a special action, when <code>false</code> indicates it is a normal action
   */
  private boolean myIsSpecialAnimationFlag;


  /**
   * Constructor when we set an explicit mode rather than a animation loop count
   * @param characterName The name of the character performing this animation
   * @param animationName The name of the action the character is to perform
   * @param loopMode The looping mode for this animation
   * @param specialFlag <code>true</code> if this is a special animation for the character, else <code>false</code>
   */
  public PlaylistAnimation( String characterName, String animationName, AnimationMode loopMode, boolean specialFlag ) {
    myCharacterName = characterName;
    myActionName = animationName;
    myAnimationMode = loopMode;
    myAnimationLoopCount = 0;
    myIsSpecialAnimationFlag = specialFlag;
  }

  /**
   * Constructor when we set an explicit loop count rather than an animation loop mode
   * @param characterName The name of the character performing this animation
   * @param animationName The name of the action the character is to perform
   * @param loopCount The number of times to loop this animation
   * @param specialFlag <code>true</code> if this is a special animation for the character, else <code>false</code>
   */
  public PlaylistAnimation( String characterName, String animationName, int loopCount, boolean specialFlag ) {
    myCharacterName = characterName;
    myActionName = animationName;
    myAnimationMode = null;
    myAnimationLoopCount = loopCount;
    myIsSpecialAnimationFlag = specialFlag;
  }

  public void SetAnimationName( String animationName ) {
    myActionName = animationName;
  }

  public String ActionName() {
    return myActionName;
  }


  public String CharRoleName() {
    return myCharacterName;
  }

  public void Execute( MediaPlayerContext playerContext, MediaPlaybackSynchronizer synchronizer ) {
    /*jmb*/
    if(myAnimationMode == null)
      playerContext.GetAnimationPlayer().PlayAnimation(synchronizer, myCharacterName, myActionName, myAnimationLoopCount );
    else
    /* end jmb */
      playerContext.GetAnimationPlayer().PlayAnimation( synchronizer, myCharacterName, myActionName );
  }

  /**
   * Emit what we need to know about a character animation as an action as XML
   * <b>Note:</b> This XML is optimized for Java-Director messaging and does not include
   * whitespace or pretty printing as it is optimized as a message - not intended to be human readbale
   * @return The XML for a location action
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<PlayAnimation>" );
    Result.append( "<Character>" ); Result.append( myCharacterName ); Result.append( "</Character>" );
    Result.append( "<Action>" ); Result.append( myActionName ); Result.append( "</Action>" );
    Result.append( "<Mode>" );
    Integer Iterations;
    if ( myAnimationMode == null ) {
      Iterations = new Integer( myAnimationLoopCount );
    } else {
      Iterations = new Integer( myAnimationMode.AnimationMode() );
    }
    Result.append( Iterations.toString() );
    Result.append( "</Mode>" );
    Result.append( "</PlayAnimation>" );
    return Result.toString();
  }

}
