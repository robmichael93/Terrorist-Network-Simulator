package com.armygame.recruits.interaction.variables;


public class Thing extends EnumerationVariable {

  private EnumerationValue myValue;
  private int mySize;

  {
    mySize = 1;
  }

  public final static ThingEnumerationValue NIGHTFIGHTINGMANUAL = new ThingEnumerationValue( 0 );


  public Thing( ThingEnumerationValue value ) {
    // Watch Aliasing!
    myValue = value;
  }

  public int size() {
    return mySize;
  }

  public boolean compare( EnumerationValue value ) {
    boolean Result = false;

    if ( value instanceof ThingEnumerationValue ) {
      if ( value.getValue() == myValue.getValue() ) {
        Result = true;
      }
    }

    return Result;

  }

  public boolean compare( EnumerationVariable variable ) {
    boolean Result = false;

    if ( variable instanceof Thing ) {
      if ( variable.compare( myValue ) ) {
        Result = true;
      }
    }

    return Result;
  }

  public EnumerationValue getValue() {
    return myValue;
  }

  public void setValue( EnumerationVariable variable ) {
    if ( variable instanceof Thing ) {
      myValue = variable.getValue();
    }
  }

  public void setValue( EnumerationValue value ) {
    if ( value instanceof ThingEnumerationValue ) {
      myValue = value;
    }
  }

}

