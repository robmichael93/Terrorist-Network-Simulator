/**
 * Title: RecruitsConfiguration
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.globals;

import java.io.File;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * The Recruits Configuration contains data about the game configuration, such as
 * the locations of various <b>manifest</b> files that catalog the assets needed to
 * initialize game data structures.  It also contains the default locations of saved
 * games.
 */

public class RecruitsConfiguration extends DefaultXMLSerializer implements XMLSerializable {

  /**
   * The full path to the root pathname of the installed program.
   * <b>Note:</b>It is recommended that this root pathname be set to a well-known location
   * established once at program installation time.  The installer can place this path
   * in the Windows registry at a well-known location.  Then, the program that launches the
   * game on the player's PC can read the registry (since Java can't easily without going
   * native) and pass this path to the game's main() as an argument.  Main() can then
   * set up that <code>MasterConfiguration</code> object to know about where to parse
   * the configuration, establishing the root pathname that all other configuration file
   * assets should be relative to.  We use the <code>java.io.File</code> object to help
   * us build pathnames form paths supplied relative to the root in the various manifest files.
   */
  protected File myInstallationRoot;


  /**
   * The path to all locations data (LocationTemplateManifest, LocationMediaElementsManifest,
   * LocationTemplates directory, and LocationMediaAssets directory)
   */
  protected File myLocationsPath;

  /**
   * The path to the manifest file that contains the set of all <code>LocationTemplate</code>s
   * available to the program
   */
  protected File myLocationTemplateManifest;

  /**
   * The path to the manifest file that contains the set of all <code>LocationMediaAsset</code>s
   * available to the program
   */
  protected File myLocationMediaAssetManifest;

  /**
   * The path to the directory that holds all the location templates
   */
  protected File myLocationTemplates;

  /**
   * The path to the directory that holds all the location media asset descriptions
   */
  protected File myLocationMediaAssets;

  /**
   * The path to all range maps
   */
  protected File myRangeMapsPath;

  /**
   * The path to the manifest file that contains the set of all <code>RangeMap</code>s
   * used to define game and character state
   */
  protected File myRangeMapManifest;

  protected File myAnimationDefinitionsPath;
  protected File myAnimationImagesRootPath;

  protected File myAudioAssetsRootPath;

  protected File myLocationImagesPath;

  /**
   * No-arg constructor required for XML processing
   */
  public RecruitsConfiguration() {
    super();
    myInstallationRoot = null;
    myLocationsPath = null;
    myRangeMapsPath = null;
    myLocationTemplateManifest = null;
    myLocationMediaAssetManifest = null;
    myLocationTemplates = null;
    myLocationMediaAssets = null;
    myRangeMapManifest = null;
    myAnimationDefinitionsPath = null;
    myAnimationImagesRootPath = null;
    myAudioAssetsRootPath = null;
    myLocationImagesPath = null;
  }

  /**
   * Read this object in from an XML file
   * @param file The filename to read from
   */
  public void ParseXMLObject( String file ) {
    Parse( this, file );
//    System.out.println( "Recruits Configuration fields:" );
//    System.out.println( "Root Path = " + myInstallationRoot.toString() );
//    System.out.println( "RangeMapManifest = " + myRangeMapManifest.toString() );
//    System.out.println( "LocationTemplateManifest = " + myLocationTemplateManifest.toString() );
//    System.out.println( "LocationMediaAssetManifest = " + myLocationMediaAssetManifest.toString() );
  }


  /**
   * Lops off a trailing filename in a path, leaving the path witha trailing file separator
   * @param filename The full path to the file name
   * @return The path to the file with the trailing file separator
   */
  public String PathOfFile( String filename ) {
    return filename.substring( 0, filename.lastIndexOf( '/' ) + 1 );
  }

  /**
   * Make a full path to an XML file from a parent directory path and a file name
   * @param parentPath The full path to the directory parent of the file
   * @param fileName The name of the file in the parent directory
   * @return The reltivePathItem pre-pended with the root path as a full pathname
   */
  public String MakeFullPath( String parentPath, String fileName ) {
    File Temp = new File( parentPath, fileName );
    return Temp.toString();
  }

  /**
   * Set the root path
   * @param rootPath The root path for the installation of The Recruits.  <b>Note:</b>This
   * path should be terminated with an appropriate path separator so that relative paths
   * can be concatenated with it
   */
  public void SetRootPath( String rootPath ) {
    myInstallationRoot = new File( rootPath );
  }

  /**
   * Return the installation root
   * @return The installation root (with a path separator on the end)
   */
  public String RootPath() {
    return myInstallationRoot.toString();
  }

  /**
   * From a path relative to the installation root, set the full path to the manifest
   * that catalogs all the <code>LocationMediaAsset</code>s
   * @param locationMediaAssetRelativePath The path, relative to the root path, where the manifest catalog of all <code>LocationMediaAsset</code>s can be found
   */
  public void SetLocationMediaAssetRelativePath( String locationMediaAssetRelativePath ) {
    myLocationMediaAssetManifest = new File( myInstallationRoot, locationMediaAssetRelativePath );
    // If it isn't already set, set the path to the locations directory
    if ( myLocationsPath == null ) {
      File Temp = new File( locationMediaAssetRelativePath );
      myLocationsPath = new File( myInstallationRoot, Temp.getParent() );
    }
  }

  /**
   * Return the full path to the <b>LocationMediaAssetManifest</b>
   * @return The full path to the <code>LocationMediaAsset</code> manifest
   */
  public String LocationMediaAssetManifestPath() {
    return myLocationMediaAssetManifest.toString();
  }

  /**
   * From a path relative to the installation root, Set the full path to the manifest
   * that catalogs all the <code>LocationTemplate</code>s
   * @param locationTemplateRelativePath The path, relative to the root path, where the manifest catalog of all <code>LocationTemplate</code>s can be found (<b>Note:</b> The root path has a path seperator already)
   */
  public void SetLocationTemplateRelativePath( String locationTemplateRelativePath ) {
    myLocationTemplateManifest = new File( myInstallationRoot, locationTemplateRelativePath );
    // If it isn't already set, set the path to the locations directory
    if ( myLocationsPath == null ) {
      File Temp = new File( locationTemplateRelativePath );
      myLocationsPath = new File( myInstallationRoot, Temp.getParent() );
    }
  }

  /**
   * Return the full path to the <b>LocationTemplateManifest</b>
   * @return The full path to the <code>LocationTemplate</code> manifest
   */
  public String LocationTemplateManifestPath() {
    return myLocationTemplateManifest.toString();
  }

  /**
   * From a path relative to the installation root, set the full path to the manifest
   * that catalogs all the <code>RangeMap</code>s
   * @param rangeMapRelativePath The path, relativce to the root path, to the catalog of <code>RangeMap</code>s
   */
  public void SetRangeMapRelativePath( String rangeMapRelativePath ) {
    myRangeMapManifest = new File( myInstallationRoot, rangeMapRelativePath );
    // Set the path to find the range map files
    if ( myRangeMapsPath == null ) {
      File Temp = new File( rangeMapRelativePath );
      myRangeMapsPath = new File( myInstallationRoot, Temp.getParent() );
    }
  }

  /**
   * Return the FULL path to the <code>RangeMap</code> manifest
   * @return The FULL path to the <code>RangeMap</code> manifest
   */
  public String RangeMapManifestPath() {
    return myRangeMapManifest.toString();
  }


  /**
   * Return the path to all the locations stuff
   * @return The full path to all the locations stuff as a string
   */
  public String LocationsPath() {
    return myLocationsPath.toString();
  }

  /**
   * Return the path to all the range map stuff
   * @return The full path to all the range map stuff as a string
   */
  public String RangeMapsPath() {
    return myRangeMapsPath.toString();
  }


  /**
   * Set the full path to the directory that holds all the location templates
   * @param locationTemplatesRelativePath The path, relative to the installation root, to the location templates directory
   */
  public void SetLocationTemplatesRelativePath( String locationTemplatesRelativePath ) {
    myLocationTemplates = new File( myInstallationRoot, locationTemplatesRelativePath );
  }

  /**
   * Return the full path to the location templates
   * @return The full path to the directory that holds the location templates
   */
  public String LocationTemplatesPath() {
    return myLocationTemplates.toString();
  }

  /**
   * Set the full path to the directory that holds all the location media assets
   * @param locationMediaAssetsRelativePath The path, relative to the installation root, to the location media assets directory
   */
  public void SetLocationMediaAssetsRelativePath( String locationMediaAssetsRelativePath ) {
    myLocationMediaAssets = new File( myInstallationRoot, locationMediaAssetsRelativePath );
  }

  /**
   * Return the full path to the location media assets
   * @return The full path to the directory that holds the location media assets
   */
  public String LocationMediaAssetsPath() {
    return myLocationMediaAssets.toString();
  }

  public void SetAnimationDefinitionsPath( String animationDefRelativePath ) {
    myAnimationDefinitionsPath = new File( myInstallationRoot, animationDefRelativePath );
  }

  public String AnimationDefinitionsPath() {
    return myAnimationDefinitionsPath.toString();
  }

  public void SetAnimationImagesRootPath( String animationImageRootRelativePath ) {
    myAnimationImagesRootPath = new File( myInstallationRoot, animationImageRootRelativePath );
  }

  public String AnimationImagesRootPath() {
    return myAnimationImagesRootPath.toString();
  }

  public void SetAudioAssetsRootPath( String audioAssetRootPath ) {
    myAudioAssetsRootPath = new File( myInstallationRoot, audioAssetRootPath );
  }

  public String AudioAssetsRootPath() {
    return myAudioAssetsRootPath.toString();
  }

  public void SetLocationImagesPath( String locationImagePath ) {
    myLocationImagesPath = new File( myInstallationRoot, locationImagePath );
  }

  public String LocationImagesPath() {
    return myLocationImagesPath.toString();
  }
}
