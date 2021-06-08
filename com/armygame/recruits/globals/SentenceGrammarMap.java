package com.armygame.recruits.globals;

import java.util.HashMap;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * Maintains mappings of the speech name to the grammar to instantiate to determine the specific
 * audio assets to use to express the intent of that speech in the context of the game state
 * (connectors)
 * This class is implemented as a <B>singleton</B> and given global access via the <B>Instance()</B> method.
 */
public class SentenceGrammarMap extends DefaultXMLSerializer implements XMLSerializable {

  private class Sentence {
    private final String mySpeechName;
    private final String myGrammar;

    public Sentence( String speechName , String grammar) {
      mySpeechName = speechName;
      myGrammar = grammar;
    }

    public String SpeechName() {
      return mySpeechName;
    }

    public String Grammar() {
      return myGrammar;
    }

  }

  /**
   * Holds the mappings from speech name to sentence grammar
   */
  private HashMap mySpeechToGrammarMap;

  /**
   * This is the static <B>singleton</B> <CODE>SentenceGrammarMap</CODE> object
   */
  private static SentenceGrammarMap theirSentenceGrammarMap = null;

  /**
   * The constructor is private to enforce <B>singleton</B>.  Sets up a new, empty
   * mapping.
   */
  private SentenceGrammarMap() {
    mySpeechToGrammarMap = new HashMap();
  }

  /**
   * Returns the <CODE>SentenceGrammarMap</CODE> <B>singleton</B>
   *
   * @return The <CODE>SentenceGrammarMap</CODE> <B>singleton</B>
   */
  public static SentenceGrammarMap Instance() {
    if ( theirSentenceGrammarMap == null ) {
      theirSentenceGrammarMap = new SentenceGrammarMap();
    }
    return theirSentenceGrammarMap;
  }


  public void ParseXMLObject( String file ) {
    Parse( this, file );
  }


  /**
   * Clears all the mappings from the map
   */
  public void ClearMappings() {
    mySpeechToGrammarMap.clear();
  }

  public void DefineSentence( String descriptiveName, String speechName, String grammar ) {
    mySpeechToGrammarMap.put( speechName, new Sentence( speechName, grammar ) );
  }

  /**
   * Returns the grammar for the given speech name
   *
   * @param speechName The name of the speech whose grammar is sought
   * @return The grammar to instantiate for this speech
   */
  public String GrammarForSpeech( String descriptiveName ) {
 /* jmb */
   try{
 /* jmb */
    return ( (Sentence) mySpeechToGrammarMap.get( descriptiveName ) ).Grammar();
/* jmb */
   }catch (Exception e)
    {
       System.out.println("exception in SentenceGrammarMap.GrammarForSpeech----------"+descriptiveName);
       e.printStackTrace();
       return null;
   }
/* jmb */
  }

  public String SpeechName( String descriptiveName ) {
    return ( (Sentence) mySpeechToGrammarMap.get( descriptiveName ) ).SpeechName();
  }

}
