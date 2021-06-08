
/**

<B>File:</B> MediaAsset.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.mediaelements.rawmedia;



/**

An <code>MediaAsset</code> defines the interface that all Recruits
Media Assets must implement

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public interface MediaAsset extends Cloneable {

  String getAsset();
  /**
   * play
   * Plays the media asset
   * @param parm - ParmDescription
   * @returns returntype - ReturnDescription
   * @see RelatedMethods
  **/
  void play();

  Object clone();

} // interface MediaAsset

