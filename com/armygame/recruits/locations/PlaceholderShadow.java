package com.armygame.recruits.locations;

import com.armygame.recruits.mediaelements.RegistrationPoint;
import com.armygame.recruits.mediaelements.MediaDimensions;


public class PlaceholderShadow {

  // When true shadow is placed in front of character, behind if false
  private boolean myInFrontFlag;

  private RegistrationPoint myRegPt;

  private MediaDimensions myShadowLoc;

  private String myImageName;

  public PlaceholderShadow() {
    myInFrontFlag = false;
    myRegPt = null;
    myShadowLoc = null;
    myImageName = null;
  }

  public void SetLayering( String where ) {
    System.out.println( "Layer is " + where + "<-----------------------------------------" );
    if ( where.equalsIgnoreCase( "InFront" ) ) {
      myInFrontFlag = true;
    } else if ( where.equalsIgnoreCase( "Behind" ) ) {
      myInFrontFlag = false;
    }
  }

  public boolean InFront() {
    return myInFrontFlag;
  }

  public void SetRegistrationPoint( RegistrationPoint regPt ) {
    myRegPt = regPt;
  }

  public RegistrationPoint RegPoint() {
    return myRegPt;
  }

  public void SetBoundingBox( MediaDimensions box ) {
    myShadowLoc = box;
  }

  public MediaDimensions BBox() {
    return myShadowLoc;
  }

  public void SetImageName( String imageName ) {
    myImageName = imageName;
  }

  public String ImageName() {
    return myImageName;
  }

}
