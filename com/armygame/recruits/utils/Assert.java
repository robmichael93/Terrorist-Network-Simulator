
package com.armygame.recruits.utils;

import com.armygame.recruits.exceptions.AssertFailedException;
//import recruits.exceptions.AssertFailedException;



public class Assert {

  public static void Assert( boolean expression ) throws AssertFailedException {
    if ( ! expression ) {
      throw new AssertFailedException();
    }

  }

}
