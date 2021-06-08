package com.armygame.recruits.playlist;

public class MediaSynchronizationEvent {

  final private String myEventID;
  final private String myEventType;

  public MediaSynchronizationEvent( String type, String id ) {
    myEventType = type;
    myEventID = id;
  }

  public boolean equals( Object rhs ) {
    boolean Result = false;
    if ( rhs instanceof MediaSynchronizationEvent ) {
      Result = myEventType.equals( ( (MediaSynchronizationEvent) rhs ).myEventType ) &&
               myEventID.equals( ( (MediaSynchronizationEvent) rhs ).myEventID );
    }
    return Result;
  }

  public int hashCode() {
    return new String( myEventType + myEventID ).hashCode();
  }

  public String toString() {
    return myEventType + "-" + myEventID;
  }

}
