package com.armygame.recruits.mediaelements;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * All media elements in the Recruits have a cast name and cast member name for use
 * by Director
 */
public class MediaElement extends DefaultXMLSerializer implements XMLSerializable {

  /**
   * The Director cast name where this media element resides on the Director side
   */
  protected String myCastName;


  /**
   * The cast member name within the Director cast where this element resides on the Director side
   */
  protected String myCastMemberName;

  /**
   * No-arg constructor
   */
  public MediaElement() {
    myCastName = myCastMemberName = null;
  }

  /**
   * This is a dummy parsing method as only sub-classes of this class will actually parse XML
   * Sub-classes MUST override this if they are really parsing XML
   * @param file The file to parse
   */
  public void ParseXMLObject( String file ) {}

  /**
   * Set the name of the cast on the Director side where this media element will be found
   * @param castName The name of the Director cast where this media element will be found
   */
  public void SetCastName( String castName ) {
    myCastName = castName;
  }

  /**
   * Return the cast name on the Director side where this media element will be found
   * @return The cast name on the Director side where this media element will be found
   */ 
  public String CastName() {
    return myCastName;
  }


  /**
   * Set the cast member name on the Director side where this media element will be found
   * @param castMemberName The cast member name on the Director side where this Media element will be found
   */
  public void SetCastMemberName( String castMemberName ) {
    myCastMemberName = castMemberName;
  }

  /**
   * Return the cast member name on the Director side where this media element is located
   * @return The cast member name on the Director side where this media element is located
   */
  public String CastMemberName() {
    return myCastMemberName;
  }

}


