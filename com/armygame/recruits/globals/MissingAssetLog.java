package com.armygame.recruits.globals;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Iterator;


public class MissingAssetLog {

  private class MissingAsset {

    private String myMissingSceneName;

    private String myMissingAssetPath;

    public MissingAsset( String scene, String assetPath ) {
      myMissingSceneName = scene;
      myMissingAssetPath = assetPath;
    }

    public String toXML( String indent ) {
      StringBuffer Result = new StringBuffer();
      Result.append( indent + "<MissingAsset>\n" );
      Result.append( indent + "  <MissingSceneName>" + myMissingSceneName + "</MissingSceneName>\n" ); 
      Result.append( indent + "  <MissingAssetName>" + myMissingAssetPath + "</MissingAssetName>\n" ); 
      Result.append( indent + "</MissingAsset>\n" );
      return Result.toString();
    }
  }

  private ArrayList myMissingAssets;
  private String myCurrentScene;

  private static MissingAssetLog theirMissingAssetLog = null;

  private MissingAssetLog() {
    myMissingAssets = new ArrayList();
    myCurrentScene = "UNKNOWN";
  }

  public static MissingAssetLog Instance() {
    if ( theirMissingAssetLog == null ) {
      theirMissingAssetLog = new MissingAssetLog();
    }
    return theirMissingAssetLog;
  }

  public void SetCurrentScene( String scene ) {
    myCurrentScene = scene;
  }

  public void Clear() {
    myCurrentScene = "UNKNOWN";
    myMissingAssets.clear();
  }

  public void AddMissingAsset( String assetPath ) {
    myMissingAssets.add( new MissingAsset( myCurrentScene, assetPath ) );
  }

  public String toXML( String indent ) {
    StringBuffer Result = new StringBuffer();
    Result.append( indent + "<MissingAssets>\n" );
    Iterator Itr = myMissingAssets.iterator();
    while( Itr.hasNext() ) {
      MissingAsset Missing = (MissingAsset) Itr.next();
      Result.append( Missing.toXML( indent + "    " ) );
    }
    Result.append( indent + "</MissingAssets>\n" );
    return Result.toString();
  }

}

