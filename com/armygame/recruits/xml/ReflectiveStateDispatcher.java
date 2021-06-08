/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package com.armygame.recruits.xml;

import java.lang.reflect.*;
import com.armygame.recruits.globals.RecruitsPackageConstants;


public class ReflectiveStateDispatcher implements RecruitsPackageConstants {

  public ReflectiveStateDispatcher() {
  }

  public DocumentParseState StateForTag( String name ) {
    String NormalizedName = NormalizeName( name );
    DocumentParseState Result = null;
    try {
      Result = (DocumentParseState) Class.forName( XMLPARSESTATES_PACKAGE + "." + NormalizedName ).getMethod( "Instance", new Class[] {} ).invoke( null, new Object[] {} );
    } catch( ClassNotFoundException e1 ) {
    } catch ( NoSuchMethodException e2 ) {
    } catch ( IllegalAccessException e3 ) {
    } catch ( InvocationTargetException e4 ) {
    }

    return Result;
  }

  /**
   * Removes dashes in names to normalize them since dashes aren't legal in identifiers
   */
  public static String NormalizeName( String name ) {
    StringBuffer Result = new StringBuffer( name );

    for( int i = 0; i < Result.length(); i++ ) {
      if ( Result.charAt(i) == '-' ) {
        Result.deleteCharAt(i);
      }
    }

    return Result.toString();

  }
}