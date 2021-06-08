

package com.armygame.recruits.exceptions;


public class ExitUtilities {

  public final static int SILENT = 0;
  public final static int WARN   = 1;
  public final static int ERROR  = 2;

  public static void ReportError( int errorcode, String msg ) {

    switch( errorcode ) {

      case SILENT : break;
      case WARN   : System.err.println( msg ); break;
      case ERROR  : System.err.println( msg ); System.exit( 1 ); break;

      // We ignore bogus error codes
      default : break;

    } // switch

  }
}

