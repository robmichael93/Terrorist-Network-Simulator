
/**

<B>File:</B> GenerativePhrase.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.utils.Visitable;
import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.exceptions.ExitUtilities;


/**

An <code>GenerativePhrase</code> is an abstract base class that unifies
a grammar hierarhy to allow different types of nodes (terminal and
non-terminal)

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public abstract class GenerativePhrase implements Visitable, Cloneable {

  // Implement Cloneable


  /**
   * clone
   * Performs deep clone of phrases
   * @returns A copy of the GenerativePhrase object
   * @see Cloneable
   * @see Visitable
  **/
  public Object clone() {

    ExitUtilities.ReportError( ExitUtilities.SILENT, "In Clone GenerativePhrase"  );
    Object Result = null;
    try {
      // No composed objects so bit-copy good enough
      Result = super.clone();
    } catch( CloneNotSupportedException e ) {
      ExitUtilities.ReportError( ExitUtilities.SILENT, "Cloneable not implemented in " +
                                       this.toString() );
    }
    ExitUtilities.ReportError( ExitUtilities.SILENT, "Out Clone GenerativePhrase" );
    return Result;

  } // Object clone()


  // Implement Visitable
  /**
   * accept
   * Forces derived classes to implement Visitable
   * @param visitor - the concrete visitor to accept
   * @returns void
  **/
  abstract public void accept( Visitor visitor );
  public abstract void addPhrase( GenerativePhrase phrase );
} // abstract class GenerativePhrase

