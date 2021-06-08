package com.armygame.recruits.xml.parsestates;

/**
 * Title: SentenceGrammarManifest
 * Description: Auto generated concrete <code>DocumentParseState</code>
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga (Auto Generated)
 * @version 1.0
 */
import org.xml.sax.Attributes;
import com.armygame.recruits.globals.RecruitsPackageConstants;
import com.armygame.recruits.xml.ParserStateMachine;
import com.armygame.recruits.xml.DocumentParseState;
import com.armygame.recruits.xml.SaveObjectForReturnSentinel;

/**
 * Class for handling XML SentenceGrammarManifest tags.  This class is a <b>Singleton</b>
 * (see 'Design Patterns' - Gamma et. al)
 * <b>IMPORTANT NOTE:</b> This class was auto generated by the
 * <b>MakeStates</b> tool, and requires user customization of the
 * following methods:
 * <code>InitializeState()</code>, <code>StartElement()</code>,
 * <code>EndElement</code>, <code>Characters</code>
 */
public class SentenceGrammarManifest extends DocumentParseState implements RecruitsPackageConstants {

  /**
   * The single SentenceGrammarManifest
   */
  private static SentenceGrammarManifest theirInstance = null;

  /**
   * The constructor is <code>private</code> to enforce <b>Singleton</b>
   * behavior
   */
  private SentenceGrammarManifest() {
  }

  /**
   * Return the SentenceGrammarManifest <b>Singleton</b>
   * @return The SentenceGrammarManifest <b>Singleton</b>
   */
  public static SentenceGrammarManifest Instance() {
    if( theirInstance == null ) {
      theirInstance = new SentenceGrammarManifest();
    }
    return theirInstance;
  }

  /**
   * This state is called when we get the end element for state SentenceGrammarManifest
   * @param context The <code>ParserStateMachine</code> that is managing our parsing
   * @param uri
   * @param localName
   * @param qName
   * @see org.xml.SAX#endElement()
   */
  public void EndElement( ParserStateMachine context, String uri, String localName, String qName ) {
    context.AddConstructionTask( new SaveObjectForReturnSentinel() );
  }

}
