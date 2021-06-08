package com.armygame.recruits.mediaelements;

import com.armygame.recruits.xml.XMLSerializable;

/**
 * Besides the cast member bookeeping of the base class, all image media elements
 * know their diemnsions and registration point
 */
public class StaticImageMediaElement extends MediaElement implements XMLSerializable {

  protected boolean myRegPointSetFlag;

  protected MediaDimensions myDimensions;

  protected RegistrationPoint myRegistrationPoint;

  public StaticImageMediaElement() {
    super();
    myRegPointSetFlag = false;
    myDimensions = null;
    myRegistrationPoint = null;
  }  


  /**
   * Dummy parsing call - 
   */
  public void ParseXMLObject( String file ) {
    super.ParseXMLObject( file );
  }

  public void SetBoundingBox( MediaDimensions dimensions ) {
    myDimensions = dimensions;
  }

  public MediaDimensions Dimensions() {
    return myDimensions;
  }

  public void SetRegistrationPoint( RegistrationPoint regPoint ) {
    myRegistrationPoint = regPoint;
    myRegPointSetFlag = true;
  }

  public RegistrationPoint RegPoint() {
    return myRegistrationPoint;
  }

  public boolean RegPointIsSet() {
    return myRegPointSetFlag;
  }

}

