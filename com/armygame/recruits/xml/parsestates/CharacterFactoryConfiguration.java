/*
 * CharacterFactoryConfiguration.java
 *
 * Created on December 9, 2002, 10:29 AM
 */

package com.armygame.recruits.xml.parsestates;

import org.xml.sax.*;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
/**
 *
 * @author  robmichael
 */
public class CharacterFactoryConfiguration extends DocumentParseState {
   
   /**
    * The single RecruitsConfiguration
    */
   private static CharacterFactoryConfiguration theirInstance = null;

  /** Creates a new instance of CharacterFactoryConfiguration */
   public CharacterFactoryConfiguration() {
      super();
   }
   
   /**
   * Return the RecruitsConfiguration <b>Singleton</b>
   * @return The RecruitsConfiguration <b>Singleton</b>
   */
   public static CharacterFactoryConfiguration Instance() {
      if( theirInstance == null ) {
         theirInstance = new CharacterFactoryConfiguration();
      }
      return theirInstance;
   }
}
