

/**

<B>File:</B> WaveMediaAsset.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.mediaelements.rawmedia;

import com.armygame.recruits.mediaelements.rawmedia.MediaAsset;
import com.armygame.recruits.exceptions.ExitUtilities;

/**

An <code>WaveMediaAsset</code> holds a wave file audio asset

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public class WaveMediaAsset implements MediaAsset {


  /**
   * The identity of the media asset
  **/
  private String myAssetID;
  private String myDescription;

  public WaveMediaAsset( String asset ) {
    myAssetID = asset;
    myDescription = "";
  } // WaveMediaAsset( String asset )

  public WaveMediaAsset( String asset, String description ) {
    myAssetID = asset;
    myDescription = description;
  } // WaveMediaAsset( String asset )

  public Object clone() {
    ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone " +
                                     this.toString() );
    Object Result = null;

    try {
      Result = super.clone();
    } catch ( CloneNotSupportedException e ) {
      ExitUtilities.ReportError( ExitUtilities.WARN, "Bad Clone" );
    }

    ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone " +
                                     this.toString() );
    return Result;
  }

  public String getAsset() {
    return myAssetID;
  }

  public String getDescription() {
    return myDescription;
  }

  /**
   * play
   * Plays the media asset
   * @param parm - ParmDescription
   * @returns returntype - ReturnDescription
   * @see RelatedMethods
  **/
  public void play() {
      System.out.print( myAssetID + " " );
  } // play()

} // class WaveMediaAsset

