/*
 * Goals.java
 *
 * Created on December 9, 2002, 11:02 AM
 */

package com.armygame.recruits.xml.parsestates;

import org.xml.sax.*;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.ConstructionMethodCall;
import com.armygame.recruits.xml.StringArgument;
import com.armygame.recruits.xml.EndMethodCallSentinel;

/**
 *
 * @author  robmichael
 */
public class Goals extends DocumentParseState {
   
   private static Goals theirInstance = null;
   
   /** Creates a new instance of Goals */
   public Goals() {
      super();
   }
   
   public static Goals Instance() {
      if ( theirInstance == null ) {
         theirInstance = new Goals();
      } // end if
      return theirInstance;
   } // end Instance
   
   public void InitializeState( ParserStateMachine context ) {
      context.AddConstructionTask( new ConstructionMethodCall( "setGoalDefinitions", new Class[] { String.class } ) );
   } // end InitializeState

   public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
      context.ClearCharacterBuffer();
      ChangeState( context, qName );
   } // end StartElement

   public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
      context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
      context.AddConstructionTask( new EndMethodCallSentinel() );
   } // end EndElement
   
} // end class Goals
