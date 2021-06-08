
/**

<B>File:</B> Visitable.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.utils;


/**

An <code>Visitable</code> interface allows classes to accept visitors
Note that we allow both refletive and other traditional visitors


@version 1.0
@author Neal Elzenga
@since Build P1

**/
public interface Visitable {


  /**
   * accept
   * Accepts a concrete visitor and makes it go
   * @param visitor - The concrete visitor
   * @returns void
   * @see RelatedMethods
  **/
  public void accept( Visitor visitor );

}// interface Visitable
