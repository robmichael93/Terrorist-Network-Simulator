/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

/**
 * Encapsulates task instructions for constructing an object from the object's XML description parameters
 * <b>IMPORTANT NOTE:</b> This class' <code>Execute()</code> method will only invoke a no-argument constructor,
 * implying that all classes to be des-serialized from XML must have a no argument constructor and include
 * <code>SetXXX()</code> methods (which can be executed by calling <code>Execute()</code> on <code>ConstructionMethodCall</code>
 * objects) in order to 'fill in' the fields of the object.
 */
public class ConstructorCall extends ConstructionOperation {


  /**
   * The package prefix for this class
   */
  private String myPackagePrefix;

  /**
   * The name of the class to construct
   */
  private String myClassName;

  /**
   * Sets up this object to act as a constructor when executed
   */
  public ConstructorCall( String packagePrefix, String className ) {
    myPackagePrefix = packagePrefix;
    myClassName = className;
  }

  public void Execute( ParserStateMachine context ) {
    // System.out.println( "Constructor call for " + myPackagePrefix + "." + myClassName );
    try {
      context.AddNewObject( ( Class.forName( myPackagePrefix + "." + myClassName ) ).newInstance() );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

}
