/**

<B>File:</B> PlayList.java

@version 1.0
@author Neal Elzenga
@since Build P1 - March 3, 2001

**/


package com.armygame.recruits.interaction.intents;

import java.util.HashMap;

import com.armygame.recruits.interaction.generativegrammar.GenerativePhrase;
import com.armygame.recruits.interaction.generativegrammar.GeneratePhraseVisitor;


/**
An <code>PlayList</code> object maintains a linear list of play list
entries as ASCII text lines.

IMPORTANT NOTE:  The text lines must use the character sequence
CR/LF (0x0d/0x0a) to delineate text lines.

@version 1.0
@author Neal Elzenga
@since Build P2

**/
public class IntentActionMap {

  // The IntentActionMap uses the Intent name (as a String) as a key
  // and maps the key onto a GenerativePhrase
  private HashMap myMap;

  // Build a new IntentActionMap
  public IntentActionMap() {
    myMap = new HashMap();
  }

  // Set-up the phrases in this map.
  public void SetPhrases( GenerativePhrase phrases) {
  }

  // Add a new mapping of an Intent onto a Phrase to this map
  public void AddToMap( String intent, GenerativePhrase phraseGrammar ) {
    myMap.put( intent, phraseGrammar );
  }

  // Define an Intent map of Intents to Phrases
  // public void DefineMap( list of Intents, list of GenerativePhrases ) {
  // }

  // Generate play list for an Intent
  public String[] GeneratePhrase( String intent ) {
    GenerativePhrase currentPhrase;
    GeneratePhraseVisitor visitor;

    currentPhrase = (GenerativePhrase) myMap.get( intent );
    visitor = new GeneratePhraseVisitor();
    visitor.visit( currentPhrase );
    return visitor.GetDialog();

  }

} // class IntentActionMap

