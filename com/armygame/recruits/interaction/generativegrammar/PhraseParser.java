
package com.armygame.recruits.interaction.generativegrammar;

import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Stack;
import java.io.File;

import com.armygame.recruits.mediaelements.DialogMediaElement;
import com.armygame.recruits.StoryEngine;
import com.armygame.recruits.storyelements.sceneelements.MainCharacter;
import com.armygame.recruits.globals.MasterConfiguration;
import com.armygame.recruits.globals.MissingAssetLog;
import com.armygame.recruits.globals.ResourceReader;

/**
 * Class for parsing a description of a pharse to concatenate audio files into a
 * sentence.  The description grammar and rules are:
 * SentenceDescription ::= OrPhrase* | SequencePhrase*
 * OrPhrase ::= { OrPhraseList }
 * OrPhraseList ::= SentenceElement | , OrPhraseList
 * SentenceElement ::= Bridge | Variable
 * Bridge ::= B[0-9]+_[0-9]+
 * Variable ::= PlainVariable | ConnectorVariable | NameVariable
 * PlainVariable ::= V[0-9]+_[0-9]+
 * ConnectorVariable ::= V[0-9]+_( ConnectorName )
 * NameVariable ::= V[0-9]+_( name )
 * ConnectorName ::= String
 *
 * The constructed names of the Bridges and Variables above result in the names of the
 * audio media asset resources to play.  For example,
 * B1_1 is the B1_1.wav or mp3 file.  This file is located in a director indexed by the
 * Sentences File Descriptior ( such as AAAB or AABU ) and by the Speaker ID letters of
 * the speaker (Such as AC or BA ).  This allows construction of the resource path to
 * the files to play.
 */
public class PhraseParser {

  /**
   * The set of tokens that delimit the phrase description - "[]" = Sequence Group
   * "{}" = Or Group
   * "()" = Connector Name Delimiter
   * "," = List Item Separator
   */
  final static String theirTokensC = "[{(,)}]";

  public PhraseParser() {
  }

  /**
   * Returns true if the supplied string is a single token in the <CODE>theirTokensC</CODE>
   * token set.
   *
   * @param token The just parsed token
   * @return <B>true</B> if the supplied token is in the token set, else <B>false</B>
   */
  private boolean isToken( String token ) {
    boolean Result = false;
    if ( ( token.length() == 1 ) &&
         ( theirTokensC.indexOf( token ) != -1 ) ) {
      Result = true;
    }
    return Result;
  }

  public GenerativePhrase PhraseFromDescriptor( String role, String speechName, String speakerCode, String descriptor ) {

    // Create a token parser for the descriptor passed in
    StringTokenizer Parser = new StringTokenizer( descriptor, theirTokensC, true );

    // The stack of phrases under construction
    Stack PhraseStack = new Stack();

    boolean NextIsConnector = false;

    String SaveToken = "";


    // Parse all the tokens in the descriptor
    // These will either be delimiters that describe the generative grammar
    // composition of the descriptor, or quoted strings that represent
    // terminals.  The terminals for the PhraseParser represent the
    // asset names of the sound files that produce a piece of the phrase
    while( Parser.hasMoreTokens() ) {
      String Token = Parser.nextToken();
      if ( isToken( Token ) ) {
        switch( Token.charAt(0) ) {
          case '[' :
            PhraseStack.push( new SequencePhrase() );
            break;
          case '{':
            PhraseStack.push( new OrPhrase() );
            break;
          case '(':
            NextIsConnector = true;
            break;
          case ')':
            NextIsConnector = false;
            break;
          case ',' :
          case '}' :
          case ']' :
            GenerativePhrase Arg1 = (GenerativePhrase) PhraseStack.pop();
            GenerativePhrase Arg2 = (GenerativePhrase) PhraseStack.pop();
            Arg2.addPhrase( Arg1 );
            PhraseStack.push( Arg2 );
            break;
        }
      } else {

        String AssetName = "";
        String AssetPath = "";

        // It's the name of a Terminal
        if ( NextIsConnector ) {

          // If this connector is 'name' we substitute the current global name value
          if ( Token.equalsIgnoreCase( "name" ) ) {
            // The save token becomes the directory relative path from the audio assets folder to the sub-directory
            // where the prerecorded .wavs of the person speaking the sentence saying the MC's name are kept
            AssetPath = "Names/" + speakerCode ;

            // We currently only allow the MC's name to be substituted
            AssetName = com.armygame.recruits.StoryEngine.instance().castManager.mainCharacter().getCharLastName();
          } else {
            // Ask Brian for the value of the connector that goes with this
            String NameOfSetConnector = com.armygame.recruits.StoryEngine.instance().mainCharacter().getStoryState().getNameOfBitSetInRange( Token ); // brianobj.NameOfSetConnector( Token ) [Handle the unknown case with Other]
            // If what Brian returns has a ':' colon in it we need to extract just the
            // stuff to the left of the colon
            int ColonIndex = NameOfSetConnector.indexOf( ":" );
            if ( ColonIndex > -1 ) {
              NameOfSetConnector = NameOfSetConnector.substring( 0, ColonIndex );
            }
            AssetName = SaveToken + NameOfSetConnector;
            AssetPath = speechName + "/" + speakerCode;
          }

          // Make sure the asset name we've determined exists on the file system.  If it doesn't exist
          // we substitute an 'Other' phrase


          PhraseStack.push( new TerminalPhrase( new DialogMediaElement( ResolveAssetToPlay( AssetPath, AssetName ) ) ) );

        } else {
          // If the last char of the token is an underscore we are waiting for a connector
          if ( Token.endsWith( "_" ) ) {
            SaveToken = Token;
          } else {
            AssetName = Token;
            AssetPath = speechName + "/" + speakerCode;
            PhraseStack.push( new TerminalPhrase( new DialogMediaElement( ResolveAssetToPlay( AssetPath, AssetName ) ) ) );
          }
        }
      }
    } // while

    return (GenerativePhrase) PhraseStack.pop();

  }

  // Determine the actual asset to play by consulting the file system to see if the requested asset exists.
  // If the requested asset doesn't exist this method gens up a suitable replacement asset
  private String ResolveAssetToPlay( String assetPath, String assetName ) {
    String Result = "";
    if ( TestAsset( assetPath + "/" + assetName ) ) {
      Result = assetPath + "/" + assetName;
    } else {
      MissingAssetLog.Instance().AddMissingAsset( assetPath + "/" + assetName );
      // We didn't find the asset - so change this asset to be spoken as an error
      String ErrorAssetPath = "XXXX/XX";
      // Assets take one of the forms: B#_#, V#_name, V#_#, V#_Connector
      String ErrorAssetName = assetName;
      if ( ! TestAsset( ErrorAssetPath + "/" + ErrorAssetName ) ) {
        MissingAssetLog.Instance().AddMissingAsset( ErrorAssetPath + "/" + ErrorAssetName );
        ErrorAssetName = "unknown";
      }

      Result = ErrorAssetPath + "/" + ErrorAssetName;
    }
    return Result;
  }

  // NOTE This routine uses the file system, NOT the JAR - will likely want to change this
  // true if the specified asset (relative to the audioassetrootpath) exists and can be read
  private boolean TestAsset( String assetRelativePath ) {
    boolean Result = false;
    File AssetTestPath = new File( MasterConfiguration.Instance().AudioAssetsRootPath() + "/" + assetRelativePath + ".mp3" ); //".wav" );
    System.out.println( "Testing for sound asset " + AssetTestPath.toString() );
    if ( ResourceReader.instance().Exists( AssetTestPath.toString() ) ) {
      System.out.println( "Tested asset exists" );
      Result = true;
    } else {
      System.out.println( "Tested asset does not exist" );
      Result = false;
      MissingAssetLog.Instance().AddMissingAsset( assetRelativePath );
    }
    return Result;
  }

  public String[] InstantiateSentence( String role, String speechName, String speakerCode, String grammar ) {
    GenerativePhrase GP = PhraseFromDescriptor( role, speechName, speakerCode, grammar );
    GeneratePhraseVisitor visitor;
    visitor = new GeneratePhraseVisitor();
    visitor.visit( GP );
    return visitor.GetDialog();
  }

}

