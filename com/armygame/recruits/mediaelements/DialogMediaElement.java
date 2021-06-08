
/**

<B>File:</B> MediaElement.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/


package com.armygame.recruits.mediaelements;


import java.lang.Cloneable;
import java.util.Iterator;

import com.armygame.recruits.utils.Visitable;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.exceptions.RecruitsException;
import com.armygame.recruits.mediaelements.rawmedia.*;
import com.armygame.recruits.mediaelements.players.MediaPlayer;
import com.armygame.recruits.exceptions.ExitUtilities;
import com.armygame.recruits.utils.ConcreteVisitor;


/**

An <code>MediaElement</code> object defines the basic capabilities of
a media element.  MediaElements contain a set of MediaAssets that they
know how to control and play, as well as a synchronization and playback
interface that provides basic media playback capabilities that are
to be implemented by the concrete MediaElement

Known implementors: WaveMediaElement

@see - MediaAsset

@version 1.0
@author Neal Elzenga
@since Build P1

**/



public class DialogMediaElement implements Cloneable, Visitable {

  /**
   * The List of MediaAssets
  **/
  private String myMediaElementName;


  /**
   * MediaElement
   * Constructs a new media element.  Note that it is OK for a new Media
   * element to not be associated with an asset (null)
   * @param - void
   * @see MediaElement( MediaAsset asset )
  **/
  public DialogMediaElement( String elementName ) {
    myMediaElementName = elementName;
  }

  // Implement Visitable
  public void accept( Visitor visitor ) {
      visitor.operate( new Object[] { (String) myMediaElementName } );
  }

  // Implement Cloneable
  public Object clone() {
    ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone " +
                                     this.toString() );
    Object Result = null;

    try {
      Result = super.clone();
      ((DialogMediaElement)Result).myMediaElementName = myMediaElementName;
      // Don't clone the player because it's a singleton
    } catch( CloneNotSupportedException e ) {
      ExitUtilities.ReportError( ExitUtilities.WARN, "No Clone" );
    }

    ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone " +
                                     this.toString() );
    return Result;

  }

} // class MediaElement

