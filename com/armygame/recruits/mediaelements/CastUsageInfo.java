package com.armygame.recruits.mediaelements;

import java.io.File;


/**
 * Encapsulates the information that an <CODE>AnimationPlayer</CODE> needs to know
 * in order to buffer cast members that describe image files.
 * 
 * This includes the filename of the image which is constructed as a concatenation
 * of the cast name (which corresponds to the directory of the media asset relative
 * to the media asset root path) and the cast member name 
 * (which corresponds to the file name of the media asset) and the filename extension
 * (we assume all images in the system will be of type PNG with extension .png)
 * 
 * @see CastUsage
 * @see AnimationPlayer
 */
public class CastUsageInfo {

  private final static String DEFAULT_EXTENSION = ".png";
  private String myCastName;
  private String myCastMemberName;
  private String myFilename;
  private String myUniqueName;

  public CastUsageInfo( String castName, String castMemberName ) {
    myCastName = castName;
    myCastMemberName = castMemberName;
    File Temp = new File( castName + "/" + castMemberName + DEFAULT_EXTENSION );
    myFilename = Temp.toString();
    myUniqueName = castName + castMemberName;
  }

  public String CastName() {
    return myCastName;
  }

  public String CastMemberName() {
    return myCastMemberName;
  }

  public String Filename() {
    return myFilename;
  }

  public String UniqueName() {
    return myUniqueName;
  }

}
