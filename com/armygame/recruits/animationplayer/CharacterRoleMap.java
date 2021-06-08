package com.armygame.recruits.animationplayer;


import java.util.HashMap;


public class CharacterRoleMap {

  public class RoleInfo {

    private String myUniform;
    private String myHat;
    private String myName;
    private String myFirstPose;

    public RoleInfo( String uniform, String hat, String name ) {
      myUniform = uniform;
      myHat = hat;
      myName = name;
      myFirstPose = null;
    }

    public String Uniform() {
      return myUniform;
    }

    public String Hat() {
      return myHat;
    }

    public String Name() {
      return myName;
    }

    public void SetActionPose( String actionName ) {
      if ( myFirstPose == null ) {
        System.out.println( "Setting action pose to " + actionName );
        myFirstPose = actionName;
      }
    }

    public String GetActionPose() {
      return myFirstPose;
    }

  }

  private static CharacterRoleMap theirRoleMap = null;

  private HashMap myMap;

  private CharacterRoleMap() {
    myMap = new HashMap();
  }

  public static CharacterRoleMap Instance() {
    if ( theirRoleMap == null ) {
      theirRoleMap = new CharacterRoleMap();
    }
    return theirRoleMap;
  }

  public void Clear() {
    myMap.clear();
  }

  public void AddRole( String role, String uniform, String hat, String name ) {
    // myMap.remove( role );
    if ( !myMap.containsKey( role ) ) {
      System.out.println( "Char Role Info adding: " + role.toString() + ", " + uniform.toString() + ", " + hat.toString() + ", " + name.toString() );
      RoleInfo NewInfo = new RoleInfo( uniform, hat, name );
      myMap.put( role, NewInfo );
    }
  }

  public RoleInfo GetInfo( String role ) {
    // System.out.println( "Asking for char role info for -> " + role );
    return (RoleInfo) myMap.get( role );
  }

}
