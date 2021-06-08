/*
 * StateVector.java
 *
 * Created on December 9, 2002, 11:00 AM
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
public class StateVector extends DocumentParseState {
   
   private static StateVector theirInstance = null;
   
   /** Creates a new instance of StateVector */
   public StateVector() {
      super();
   }
   
   public static StateVector Instance() {
      if ( theirInstance == null ) {
         theirInstance = new StateVector();
      } // end if
      return theirInstance;
   } // end Instance
   
   public void InitializeState( ParserStateMachine context ) {
      context.AddConstructionTask( new ConstructionMethodCall( "setStateVectorDefinition", new Class[] { String.class } ) );
   } // end InitializeState

   public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
      context.ClearCharacterBuffer();
      ChangeState( context, qName );
   } // end StartElement

   public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
      context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
      context.AddConstructionTask( new EndMethodCallSentinel() );
   } // end EndElement
   
} // end class StateVector
