/**
 * Title:        Army Game Project - The Recruits
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Emergent Design
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.mediaelements;

/**
 * Define a registration point in (x,y) coordinates
 * The point defined can also be a registration point representing a (delta-x, delta-y)
 * <b>Note:</b> There is not provision to turn a registration point into an offset except
 * at construction time.
 */
public class RegistrationPoint {

  /**
   * The x-coordinate of the registration point
   */
  private int myRegX;

  /**
   * The y-coordinate of the registration point
   */
  private int myRegY;

  /**
   * When this flag is <code>true</code> this <code>RegistrationPoint</code> represents a registration
   * offset (delta-x,delta-y)
   */
  private boolean myIsOffsetFlag;

  /**
   * A universal initializer
   * @param x The x-coordinate of the registration point
   * @param y The y_coordinate of the registration point
   * @param isOffset <code>true</code> if this is really an offset, not a registration point
   */
  private void Initialize( int x, int y, boolean isOffset ) {
    myRegX = x;
    myRegY = y;
    myIsOffsetFlag = isOffset;
  }

  /**
   * The no-arg constructor required by <code>fromXML()</code>
   */
  public RegistrationPoint() {
    Initialize( 0, 0, false );
  }

  /**
   * This constructor makes a <code>RegistrationPoint</code>, not an offset
   * @param x The x-coordinate of the registration point
   * @param y The y_coordinate of the registration point
   */
  public RegistrationPoint( int x, int y ) {
    Initialize( x, y, false );
  }

  /**
   * A full constructor
   * @param x The x-coordinate of the registration point
   * @param y The y_coordinate of the registration point
   * @param isOffset <code>true</code> if this is really an offset, not a registration point
   */
  public RegistrationPoint( int x, int y, boolean isOffset ) {
    Initialize( x, y, isOffset );
  }

  /**
   * Set the registration point
   * @param x The x-coordinate of the registration point
   * @param y The y_coordinate of the registration point
   */
  public void SetRegistrationPoint( int x, int y ) {
    myRegX = x;
    myRegY = y;
  }

  /**
   * Set the x-coordinate
   * @param x The x-coordinate of the registration point
   */
  public void SetRegX( int x ) {
    myRegX = x;
  }

  /**
   * Set the y-coordinate
   * @param y The y_coordinate of the registration point
   */
  public void SetRegY( int y ) {
    myRegY = y;
  }

  /**
   * Get the x-coordinate
   * @return The x-coordinate of the registration point
   */
  public int RegX() {
    return myRegX;
  }

  /**
   * Get the y-coordinate
   * @return The y-coordinate of the registration point
   */
  public int RegY() {
    return myRegY;
  }

  /**
   * Get the offset flag
   * @return <code>true</code> if this is really an offset, <code>false</code> if it is a registration point
   */
  public boolean IsOffset() {
    return myIsOffsetFlag;
  }

}