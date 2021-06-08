package com.armygame.recruits.mediaelements;


import com.armygame.recruits.xml.XMLSerializable;

public class LocationMediaElement extends StaticImageMediaElement implements XMLSerializable {

  protected String myRole;

  protected int myLayer;

  /**
   *  The offset in the x-axis for this media asset when used in the above role
   */
  protected int myXOffset;

  /**
   * The offset in the y-axis for this media asset when used in the above role
   */
  protected int myYOffset;

  public LocationMediaElement() {
    super();
    myRole = null;
    myLayer = 0;
    myXOffset = myYOffset = 0;
  }

  public void ParseXMLObject( String file ) {
    super.ParseXMLObject( file );
    Parse( this, file );
  }

  public void SetRole( String role ) {
    myRole = role;
  }

  public String Role() {
    return myRole;
  }

  public void SetLayer( int layer ) {
    myLayer = layer;
  }

  public int Layer() {
    return myLayer;
  }

  public void SetOffsetX( int xOffset ) {
    myXOffset = xOffset;
  }

  public void SetOffsetY( int yOffset ) {
    myYOffset = yOffset;
  }

  public int XOffset() {
    return myXOffset;
  }

  public int YOffset() {
    return myYOffset;
  }

}
