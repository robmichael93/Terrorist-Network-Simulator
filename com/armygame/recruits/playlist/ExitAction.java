package com.armygame.recruits.playlist;


/**
 * All exit actions must know how to output themselves as XML
 */
public interface ExitAction {

  /**
   * All <code>ExitAction</code>s must know how to output themselves as XML
   */
  public String toXML();

}
