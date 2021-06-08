/**
 * Title: ConstructionMethodCall
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.xml;


public class ConstructionMethodCall extends ConstructionOperation {

  /**
   * The method call parameter signature
   */
  private Class[] myCallSignature;

  /**
   * The name of the method to invoke
   */
  private String myMethodName;

  public ConstructionMethodCall( String methodName, Class[] callSignature ) {
    myMethodName = methodName;
    myCallSignature = callSignature;
  }

  public void Execute( ParserStateMachine context ) {
//    System.out.println( "Calling method " + myMethodName + " expecting " + myCallSignature.length );
    try {
      context.SetPendingMethodCall( context.GetLastClass().getMethod( myMethodName, myCallSignature ) );
      context.NewArgumentList();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    // System.out.println( "Called method " + myMethodName );
  }
}
