package com.armygame.recruits.interaction.variables;


public class EnumerationValue {
  private int myValue;

  public EnumerationValue( int value ) {
    myValue = value;
  }

  // No means of resetting a value - values only set via contructor,
  // no setValue() method

  public int getValue() {
    return myValue;
  }

}

