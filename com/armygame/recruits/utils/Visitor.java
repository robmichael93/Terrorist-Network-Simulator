
/**

<B>File:</B> Visitor.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.utils;


/**

An <code>Visitor</code> interface is used by classes that want to accept
visitors per the GOF Visitor pattern

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public interface Visitor {


  /**
   * visit
   * @param visitor - The concrete visitor visiting this object
   * @returns void
   * @see RelatedMethods
  **/
  public void visit( Object obj );

  public void operate( Object[] args );

  public void reset();

} // interface Visitor

