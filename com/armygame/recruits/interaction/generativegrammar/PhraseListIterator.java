

package com.armygame.recruits.interaction.generativegrammar;

import java.util.*;
import com.armygame.recruits.exceptions.ExitUtilities;


class PhraseListIterator implements Iterator, Cloneable {

  private Iterator myItr;


  public PhraseListIterator( Iterator itr ) {
    myItr = itr;
    GenerativePhrase GP = (GenerativePhrase)myItr.next();
    System.out.println( "Gp is " + GP );
    if ( GP instanceof GenerativePhrase ) {
      ExitUtilities.ReportError( ExitUtilities.WARN, "New iterator has next" );
    } else {
      ExitUtilities.ReportError( ExitUtilities.ERROR, "New iterator hasn't next" );
    }
  }

  // Implement Iterator
  public boolean hasNext() {
    return myItr.hasNext();
  }

  public Object next() {
    Object Result = null;

    if ( myItr.hasNext() ) {
      Result = myItr.next();
    }

    return Result;

  }

  public void remove() {
    myItr.remove();
  }

  // Implement Cloneable
  public Object clone() {
    Object Result = null;
    try {
      Result = (PhraseListIterator)super.clone();
    } catch( CloneNotSupportedException e ) {
      ExitUtilities.ReportError( ExitUtilities.WARN, "Cloneable not implemented in " +
                                 this.toString() );
    }
    return Result;
  }

}

