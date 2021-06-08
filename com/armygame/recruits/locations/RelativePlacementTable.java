package com.armygame.recruits.locations;

import java.util.HashMap;

public class RelativePlacementTable {
  public static class ActionDirection {
    public static int AT = 1;
    public static int AWAY = -1;
    private int myDirection;
    private ActionDirection( int direction ) {
      myDirection = direction;
    }
    public boolean equals( Object rhs ) {
      boolean Result = false;
      if ( rhs instanceof ActionDirection ) {
        if ( myDirection == ((ActionDirection) rhs).myDirection ) {
          Result = true;
        }
      }
      return Result;
    }
    public int Direction() {
      return myDirection;
    }
  }

  public static ActionDirection ACT_AT = new ActionDirection( ActionDirection.AT );
  public static ActionDirection ACT_AWAY = new ActionDirection( ActionDirection.AWAY );
  private static RelativePlacementTable theirPlacementTable = null;
  private HashMap myPlacements;

  private RelativePlacementTable() {
    myPlacements = new HashMap();
  }

  public static RelativePlacementTable Instance() {
    if ( theirPlacementTable == null ) {
      theirPlacementTable = new RelativePlacementTable();
    }
    return theirPlacementTable;
  }

  public void ForgetPlacements() {
    myPlacements.clear();
  }

  public void AddStaging( String actor, int x ) {
    myPlacements.put( actor, new Integer( x ) );
  }

  public String ResolveRelativeAction( ActionDirection direction, String actor, String objectOfAction ) {
    int ActorX = ( (Integer) myPlacements.get( actor ) ).intValue();
    int ObjectX = ( (Integer) myPlacements.get( objectOfAction ) ).intValue();
    String Result;
    if ( direction.equals( ACT_AT ) ) {
      Result = ( ActorX <= ObjectX ) ? "right" : "left";
    } else {
      Result = ( ActorX <= ObjectX ) ? "left" : "right";
    }
    return Result;
  }
}
