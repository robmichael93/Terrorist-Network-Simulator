/*
 * Possessions.java
 *
 * Created on December 9, 2002, 11:11 AM
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
public class Possessions extends DocumentParseState {
   
   private static Possessions theirInstance = null;
   
   /** Creates a new instance of Possessions */
   public Possessions() {
      super();
   }
   
   public static Possessions Instance() {
      if ( theirInstance == null ) {
         theirInstance = new Possessions();
      } // end if
      return theirInstance;
   } // end Instance
   
   public void InitializeState( ParserStateMachine context ) {
      context.AddConstructionTask( new ConstructionMethodCall( "setPossessionDefinitions", new Class[] { String.class } ) );
   } // end InitializeState

   public void StartElement( ParserStateMachine context, String uri, String localName, String qName, Attributes atts ) {
      context.ClearCharacterBuffer();
      ChangeState( context, qName );
   } // end StartElement

   public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
      context.AddConstructionTask( new StringArgument( context.GetCharBuffer() ) );
      context.AddConstructionTask( new EndMethodCallSentinel() );
   } // end EndElement
   
} // end class Possessions
