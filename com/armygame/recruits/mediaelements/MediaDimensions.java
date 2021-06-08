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
 * Encapsulates the media dimensions as ( width, height ) in pixels
 */
public class MediaDimensions {

  /**
   * The width in pixels of a media element
   */
  private int myWidth;

  /**
   * The height in pixels of a media element
   */
  private int myHeight;

  /**
   * A utility initilizer
   * @param width The width of this media element in pixels
   * @param height The height of this media element in pixels
   */
  private void Initialize( int width, int height ) {
    myWidth = width;
    myHeight = height;
  }

  /**
   * The no-arg constructor required of <code>fromXML()</code>
   */
  public MediaDimensions() {
    Initialize( 0, 0 );
  }

  /**
   * A full constructor
   * @param width The width of this media element in pixels
   * @param height The height of this media element in pixels
   */
  public MediaDimensions( int width, int height ) {
    Initialize( width, height );
  }

  /**
   * Set the width
   * @param width The width of this media element in pixels
   */
  public void SetWidth( int width ) {
    myWidth = width;
  }

  /**
   * Set the height
   * @param height The height of this media element in pixels
   */
  public void SetHeight( int height ) {
    myHeight = height;
  }

  /**
   * Get the width
   * @return The width of the media element in pixels
   */
  public int Width() {
    return myWidth;
  }

  /**
   * Get the height
   * @return The height of the media element in pixels
   */
  public int Height() {
    return myHeight;
  }

}