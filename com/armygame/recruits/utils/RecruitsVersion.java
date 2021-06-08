/**
 * Title: RecruitsVersion
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Neal Elzenga
 * @version 1.0
 * @since Build 1.0
 */

package com.armygame.recruits.utils;


import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import com.armygame.recruits.xml.DefaultXMLSerializer;
import com.armygame.recruits.xml.XMLSerializable;


/**
 * Encapsulates Recruits version numbers into an object so that they may be compared.  This will primarily
 * be used to assure that XML data files read in are compatable with the major version and sub-system
 * versions of the system.
 */
public class RecruitsVersion {

  /**
   * This inner class maintains the Primary, Secondary, and Build Number versions as a struct
   */
  private class Version {
    /**
     * The primary version number
     */
    private int myPrimaryVersion;

    /**
     * The Secondary Version number
     */
    private int mySecondaryVersion;

    /**
     * The Build Number
     */
    private int myBuildNumber;

    /**
     * This no-arg constructor is used to establish a Version which is being read in via XML
     */
    public Version() {
      myPrimaryVersion = 0;
      mySecondaryVersion = 0;
      myBuildNumber = 0;
    }

    /**
     * This constructor is used to initialize versions whose version nmubers are known at construction time
     * @param primary The Primary Version Number
     * @param secondary The Secondary Version Number
     * @param buildNum The Build Number
     */
    public Version( int primary, int secondary, int buildNum ) {
      myPrimaryVersion = primary;
      mySecondaryVersion = secondary;
      myBuildNumber = buildNum;
    }

    /**
     * Set the Primary Version Number
     * @param primary The Primary Version Number
     */
    public void SetPrimaryVersion( int primary ) {
      myPrimaryVersion = primary;
    }
    /**
     * Set the Secondary Version Number
     * @param secondary The Secondary Version Number
     */
    public void SetSecondaryVersion( int secondary ) {
      mySecondaryVersion = secondary;
    }

    /**
     * Set the Build Number
     * @param buildNum The Build Number
     */
    public void SetBuildNumber( int buildNum ) {
      myBuildNumber = buildNum;
    }

    /**
     * Returns the Primary Version
     * @return The Primary Version Number
     */
    public int PrimaryVersion() {
      return myPrimaryVersion;
    }

    /**
     * Returns the Secondary Version
     * @return The Secondary Version Number
     */
    public int SecondaryVersion() {
      return mySecondaryVersion;
    }

    /**
     * Returns the Build Number
     * @return The Build Number
     */
    public int BuildNumber() {
      return myBuildNumber;
    }

    /**
     * Returns <code>true</code> if the build number passed in matches the Primary and Secondary version numbers
     * <b>Note</b> Build Number is currently ignored in this comparison
     * @param rhs The Version to compare
     * @return <code>true</code> if the versions match, else <code>false</code>
     */
    public boolean equals( Version rhs ) {
      boolean Result = false;
      if ( ( myPrimaryVersion == rhs.myPrimaryVersion ) && ( mySecondaryVersion == rhs.mySecondaryVersion ) ) {
        Result = true;
      }
      return Result;
    }

  } // inner class Version

  /**
   * The Principle Version
   */
  private Version myPrincipleVersion;

  /**
   * The list of sub system versions
   */
  private HashMap mySubSystemVersions;

  /**
   * This no-arg constructor is used to set up an emtpy version suitable for reading in from XML
   */
  public RecruitsVersion() {
    myPrincipleVersion = new Version();
    mySubSystemVersions = new HashMap( 4 );
  }

  public RecruitsVersion( int primary, int secondary, int buildNum ) {
    myPrincipleVersion = new Version( primary, secondary, buildNum );
    mySubSystemVersions = new HashMap( 4 );
  }

  /**
   * This constructor allows specification of the version at construction time
   * @param principleVersion A triplet int array of the [primary, secondary, build number]
   * @param subSystemVersions An array of object arrays of ["subsystem name", primary, secondary, build number]
   */
  public RecruitsVersion( int[] principleVersion, Object[][] subSystemVersions ) {
    myPrincipleVersion = new Version( principleVersion[0], principleVersion[1], principleVersion[2] );
    mySubSystemVersions = new HashMap( 4 );

    // Add all the Sub System Versions
    for( int i = 0; i < subSystemVersions.length; i++ ) {
      mySubSystemVersions.put( (String) subSystemVersions[i][0],
                               new Version( ((Integer) subSystemVersions[i][1]).intValue(),
                                            ((Integer) subSystemVersions[i][2]).intValue(),
                                            ((Integer) subSystemVersions[i][3]).intValue() ) );
    } // for
  } // constructor RecruitsVersion

  /**
   * Set the Principle Version
   * @param principleVersion <code>int[3]</code> containing [primary, secondary, build number] as ints
   */
  public void SetPrincipleVersion( int primary, int secondary, int buildNum ) {
    myPrincipleVersion.SetPrimaryVersion( primary );
    myPrincipleVersion.SetSecondaryVersion( secondary );
    myPrincipleVersion.SetBuildNumber( buildNum );
  }

  /**
   * Set a SubSystem version by name and values
   * @param subSystemName The name of the sub system to add version info about
   * @param subSystemVersion <code>int[3]</code> containing [primary, secondary, build number] as ints
   */
  public void SetSubSystemVersion( String subSystemName, int primary, int secondary, int buildNum ) {
    // We add a new Version if we've not previously set version info for this sub system
    if ( mySubSystemVersions.containsKey( subSystemName ) ) {
      Version SubSystemToUpdate = (Version) mySubSystemVersions.get( subSystemName );
      SubSystemToUpdate.SetPrimaryVersion( primary );
      SubSystemToUpdate.SetSecondaryVersion( secondary );
      SubSystemToUpdate.SetBuildNumber( buildNum );
    } else {
      mySubSystemVersions.put( subSystemName, new Version( primary, secondary, buildNum ) );
    }
  }

  /**
   * Returns <code>true</code> if the Principle versions match and there is also an <code>equals</code>
   * relationship amongst all the SubSystem Versions of the <code>rhs</code>
   * @param rhs The version to compare to this version
   * @return <code>true</code> if versions are compatible, <code>false</code> otherwise
   */
  public boolean equals( RecruitsVersion rhs ) {
    boolean Result = false;

    if ( myPrincipleVersion.equals( rhs.myPrincipleVersion ) ) {
      // Tentatively true but checking subsystems could nix it
      Result = true;
      Iterator SubSystemItr = rhs.mySubSystemVersions.keySet().iterator();
      while( SubSystemItr.hasNext() ) {
        String TestKey = (String) SubSystemItr.next();
        if ( mySubSystemVersions.containsKey( TestKey ) ) {
          Version LhsVersion = (Version) mySubSystemVersions.get( TestKey );
          Version RhsVersion = (Version) rhs.mySubSystemVersions.get( TestKey );
          if ( ! LhsVersion.equals( RhsVersion ) ) {
            Result = false;
            break;
          }
        } else {
          Result = false;
          break;
        } // if contains key
      } // while
    }

    return Result;
  }
}
