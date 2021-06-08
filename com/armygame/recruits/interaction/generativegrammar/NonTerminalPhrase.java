
/**

<B>File:</B> NonTerminalPhrase.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/

package com.armygame.recruits.interaction.generativegrammar;

import com.armygame.recruits.utils.Visitor;
import com.armygame.recruits.utils.Assert;
import com.armygame.recruits.exceptions.NoPhraseList;

// Debug
import com.armygame.recruits.exceptions.ExitUtilities;


/**

An <code>NonTerminalPhrase</code> object  defines the operations on
generative grammar phrase nodes that contain or compose other phrase
nodes as non-terminals in the production system

@version 1.0
@author Neal Elzenga
@since Build P1

**/
public abstract class NonTerminalPhrase extends GenerativePhrase implements PhraseList {

  // Stuff for looping this phrase
  protected int myBeginLoopRange;
  protected int myEndLoopRange;
  private final static java.util.Random theirRandomSource = new java.util.Random();

  protected NonTerminalPhrase() {
    myBeginLoopRange = 0;
    myEndLoopRange = 1;
  }

  protected NonTerminalPhrase( int looptimes ) {
    myBeginLoopRange = 0;
    myEndLoopRange = looptimes;
  }

  protected NonTerminalPhrase( int minloop, int maxloop ) {
    try {
      Assert.Assert( maxloop > minloop );
      myBeginLoopRange = minloop;
      myEndLoopRange = maxloop;
    } catch( Exception e ) {
    }
  }

  protected int PhraseCount() {
    int Result = 0;
    try {
      if ( myBeginLoopRange == 0 ) {
        Result = myEndLoopRange;
      } else {
        int Times = myEndLoopRange - myBeginLoopRange;
        Assert.Assert( Times > 0 );
        int Count = ( java.lang.Math.abs(theirRandomSource.nextInt()) % (Times+1) );
        System.out.println( "Count = " + Count );
        Result =  Count + myBeginLoopRange;
        System.out.println( "Begin = " + myBeginLoopRange + " end = " + myEndLoopRange + " Result -> " + Result );
      }
    } catch( Exception e ) {
    }
    return Result;
  }

  protected int beginLoopCount() {
    return myBeginLoopRange;
  }

  protected int endLoopCount() {
    return myEndLoopRange;
  }

  // Placeholder for Implementation of PhraseList
  public abstract void clear() throws NoPhraseList;
  public abstract int size() throws NoPhraseList;

  public abstract void addPhrase( Object[] args );

  public abstract void addPhrase( GenerativePhrase phrase );

  // Implement Cloneable
  public Object clone() {
    Object Result = null;
    Result =  super.clone();
    return Result;
  }

  // Implement Visitable
  abstract public void accept( Visitor visitor );

} // abstract class NonTerminalPhrase
