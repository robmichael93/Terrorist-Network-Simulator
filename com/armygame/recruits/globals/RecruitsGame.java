/**
 * Title: RecruitsGame
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.globals;

import java.io.File;

import com.armygame.recruits.locations.LocationsConfiguration;
import com.armygame.recruits.utils.AttributeGrove;
import com.armygame.recruits.utils.AttributeTrie;
import com.armygame.recruits.utils.RangeMap;
import com.armygame.recruits.utils.RangeMapIndex;
import com.armygame.recruits.utils.RecruitsVersion;
import com.armygame.recruits.globals.MasterRangeMapIndex;
import com.armygame.recruits.globals.RecruitsConfiguration;
import com.armygame.recruits.globals.MasterVersion;


/**
 * This object encapsulates the configuration state of a Recruits game.  It handles the
 * the parsing of the configuration files and manifests to read in the configuration state
 * of the system. This object is desinged as a <b>Singleton</b>
 *
 * This class contains the main entry point for the Java game client.
 */
public class RecruitsGame {

  /**
   * Holds the <b>Singleton</b> instance of the <code>RecruitsGame</code> object
   */
  private static RecruitsGame theirRecruitsGame = null;


  /**
   * Defines the master <code>RecruitsVersion</code> object (a <b>Singleton</b>) for the current release build
   * of the game.  This version is set in the constructor so that it is 'compiled-in' to the
   * code.  This master version will be used by all XML parsing operations to assure that the
   * data being read is in sync with the sub-system version for that item
   */
  private RecruitsVersion myMasterVersion;

  /**
   * The master <code>LocationsConfiguration</code> (a <b>Singleton</b>).  The <code>LocationsConfiguration</code>
   * knows about the <code>LocationTemplates</code> and <code>LocationMediaElements</code> available
   * to the game.
   */
  private LocationsConfiguration myMasterLocationsConfiguration;

  /**
   * The configuration object that caches the location of the various manifest files that
   * define the Java game client configuration
   */
  private RecruitsConfiguration myConfiguration;

  /**
   * The set of all range maps know by the game
   */
  private RangeMapIndex myRangeMapIndex;

  /**
   * Sets up the <code>RecruitsGame</code> object.  This constructor is private to
   * enforce <b>Singleton</b>
   */
  private RecruitsGame() {
    // We need to set the master version as the numero uno thing so that subsequent XML parsing
    // operations used during configuration will succeed
    myMasterVersion = MasterVersion.Instance();
    myMasterVersion.SetPrincipleVersion( 1, 0, 1 );
    myMasterVersion.SetSubSystemVersion( "Locations", 1, 0, 1 );
    myMasterVersion.SetSubSystemVersion( "MediaElements", 1, 0, 1 );
    myMasterVersion.SetSubSystemVersion( "Animations", 1, 0, 1 );

    myConfiguration = MasterConfiguration.Instance(); // new RecruitsConfiguration();

    myMasterLocationsConfiguration = MasterLocationsConfiguration.Instance();
    myRangeMapIndex = MasterRangeMapIndex.Instance();

//    CharacterRoleMap.Instance().AddCharacterRoleMapping( "bass", "MC" );
//    CharacterRoleMap.Instance().AddCharacterRoleMapping( "bass", "MC" );
//    CharacterRoleMap.Instance().AddCharacterRoleMapping( "bass", "MC" );
  }

  /**
   * Return the <code>RecruitsGame</code> <b>Singleton</b>, intializing it during frist access
   * @return The <code>RecruitsGame</code> instance
   */
  public static RecruitsGame Instance() {
    if ( theirRecruitsGame == null ) {
      theirRecruitsGame = new RecruitsGame();
    }
    return theirRecruitsGame;
  }

  public void Initialize( String configurationFile ) {
//    // Read the configuration file, but first set the root path
//    myConfiguration.SetRootPath( configurationFile.substring( 0, configurationFile.lastIndexOf( File.separator ) + 1 ) );
    myConfiguration.SetRootPath( configurationFile.substring( 0, configurationFile.lastIndexOf( "/" ) + 1 ) );
    myConfiguration.ParseXMLObject( configurationFile );
//
//    // Now initialize the various game state sub-components from the manifests specified in the
//    // <code>RecruitsConfiguration</code>
    InitializeRangeMaps( myConfiguration.RangeMapManifestPath() );
    InitializeLocations( myConfiguration.LocationTemplateManifestPath(), myConfiguration.LocationMediaAssetManifestPath() );
    InitializeSentenceGrammars( myConfiguration.AudioAssetsRootPath() );
  }

  /**
   * The master orchestrator of game intialization - It reads the master <code>RecruitsConfiguration</code>
   * and reads each of the various manifests it contains to initialize other global game state
   * items
   * @param configurationFile The full pathname to the <code>RecruitsConfiguration</code> XML definition.  <b>Note:</b> This path is intended to specify a well-known location of the <code>RecruitsConfiguration</code> XML file and is expected to come as a command line argument to the invocation of this class' <code>main()</code> entry point
   */
  public void Initialize( String configurationFile, String rangeMapName, RangeMap rangeMap) {
    // Read the configuration file, but first set the root path
//    myConfiguration.SetRootPath( configurationFile.substring( 0, configurationFile.lastIndexOf( File.separator ) + 1 ) );
    myConfiguration.SetRootPath( configurationFile.substring( 0, configurationFile.lastIndexOf( "/" ) + 1 ) );
    myConfiguration.ParseXMLObject( configurationFile );

    // Now initialize the various game state sub-components from the manifests specified in the
    // <code>RecruitsConfiguration</code>
    // InitializeRangeMaps( myConfiguration.RangeMapManifestPath() );
    myRangeMapIndex.AddRangeMap( rangeMapName, rangeMap );
    InitializeLocations( myConfiguration.LocationTemplateManifestPath(), myConfiguration.LocationMediaAssetManifestPath() );
    InitializeSentenceGrammars( myConfiguration.AudioAssetsRootPath() );
  }

//  public void InitializeRangeMap(String rangeMapName, RangeMap rangeMap) {
//    myRangeMapIndex.AddRangeMap( rangeMapName, rangeMap );
//  }


  /**
   * Return the configuration
   * @return The configuration
   */
  public RecruitsConfiguration Configuration() {
    return myConfiguration;
  }

  /**
   * Initialize the master <code>RangeMapIndex</code> to include all of the <code>RangeMap</code>s
   * defined in the Range Map manifest
   * @param rangeMapManifestPath The FULL pathname to the <code>RangeMap</code> manifest file
   */
  public void InitializeRangeMaps( String rangeMapManifestPath ) {
    // System.out.println( "Initializing Range Maps from manifest: " + rangeMapManifestPath );
    myRangeMapIndex.ParseXMLObject( rangeMapManifestPath );
    myRangeMapIndex.Debug();
    GetRangeMap( "Story" ).Debug();
  }

  /**
   * Initialize the master <code>LocationsConfiguration</code> from the manifests that define the
   * set of <code>LocationTemplates</code> and <code>LocationMediaElements</code>
   * @param locationTemplatesManifestPath The FULL pathname to the manifest that defines all the <code>LocationTemplate</code>s available to the game
   * @param locationMediaAssetManifestPath The FULL pathname to the manifest that defines all the <code>LocationMediaElement</code>s available to the game
   */
  public void InitializeLocations( String locationTemplatesManifestPath, String locationMediaAssetManifestPath ) {
    // Initialize the AttributeGrove to include all the media elements known for instantiating Locations
    //System.out.println( "Initializing LocationMediaElements from manifest: " + locationMediaAssetManifestPath );
    myMasterLocationsConfiguration.SetLocationMediaElementsGrove( new AttributeGrove() );
    myMasterLocationsConfiguration.LocationMediaElementsGrove().ParseXMLObject( locationMediaAssetManifestPath );
    //myMasterLocationsConfiguration.LocationMediaElementsGrove().Debug();

    // Initialize the AttributeTrie to include all the LocationTemplates known to the game
    //System.out.println( "Initializing LocationTemplates from manifest: " + locationTemplatesManifestPath );
    myMasterLocationsConfiguration.SetLocationTemplateTrie( new AttributeTrie() );
    myMasterLocationsConfiguration.LocationTemplateTrie().ParseXMLObject( locationTemplatesManifestPath );
    //myMasterLocationsConfiguration.LocationTemplateTrie().PrintTrie();
  }

  public void InitializeSentenceGrammars( String sentenceGrammarPath ) {
    System.out.println( "Initializing sentence grammars from " + new File( sentenceGrammarPath + "/SentenceGrammarManifest.xml" ).toString() );
    SentenceGrammarMap.Instance().ParseXMLObject( new File( sentenceGrammarPath + "/SentenceGrammarManifest.xml" ).toString() );
    // System.out.println( "Got grammar " + SentenceGrammarMap.Instance().GrammarForSpeech( "AAAB" ) );
  }

  /**
   * Retrieves a <code>RangeMap</code> defined during initialization by name
   * @param rangeMapName The name of the <code>RangeMap</code> to retrieve
   * @return The <code>RangeMap</code> under the given name or <code>null</code> if a <code>RangeMap</code> by that name doesn't exist
   */
  public RangeMap GetRangeMap( String rangeMapName ) {
    RangeMap Result = null;
    if ( myRangeMapIndex.Exists( rangeMapName ) ) {
      Result = myRangeMapIndex.GetRangeMap( rangeMapName );
    }
    return Result;
  }

}

