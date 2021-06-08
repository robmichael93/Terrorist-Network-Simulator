
package com.armygame.recruits.utils;

import java.util.*;


public abstract class ConcreteVisitor implements ReflectiveVisitor {

  public void visit( Object obj ) {
    try {
      ReflectiveVisitor.CommonImplementation.invokeVisitor( obj.getClass(), this.getClass(), Visitor.class, this, obj );
    } catch ( Exception e ) {
      e.printStackTrace();
      System.out.println( "Could not visit " + obj );
    }
  }

  public abstract void operate( Object[] args );
  public abstract void reset();

} // class ConcreteVisitor

