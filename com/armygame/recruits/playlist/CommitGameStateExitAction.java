package com.armygame.recruits.playlist;


/**
 * Exit action used to remember which game state serial number to record if the 
 * <code>ActionFrame</code> this exit action is attached to completes
 */
public class CommitGameStateExitAction implements ExitAction {


  /**
   * The serial number of the game state action to return if the <code>ActionFrame</code>
   * this exit action is associated with completes.  It is up to the client that creates
   * this exit action to know how to interpret this serial number
   */
  private int myGameStateCommitSerialNumber;

  /**
   * Creates a new exit action to remember which game state serial number to associate with
   * completion of the <code>ActionFrame</code> this exit action is associated with
   * @param stateCommitSerialNumber The serial number of the game state action associated with completion of this <code>ActionFrame</code>
   */
  public CommitGameStateExitAction( int stateCommitSerialNumber ) {
    myGameStateCommitSerialNumber = stateCommitSerialNumber;
  }

  /**
   * Emit this exit action as XML - The output is optimized for Java-Director messaging and
   * contains no whitespace or pretty printing
   */
  public String toXML() {
    StringBuffer Result = new StringBuffer();
    Result.append( "<CommitGameState>" );
    Integer SerialNumber = new Integer( myGameStateCommitSerialNumber );
    Result.append( SerialNumber.toString() );
    Result.append( "</CommitGameState>" );
    return Result.toString();
  }

}


