package com.armygame.recruits.interaction.variables;


public class TimeOfDay extends EnumerationVariable {

  private EnumerationValue myValue;
  private int mySize;

  {
    mySize = 3;
  }

  public final static TimeOfDayEnumerationValue MORNING = new TimeOfDayEnumerationValue( 0 );
  public final static TimeOfDayEnumerationValue AFTERNOON = new TimeOfDayEnumerationValue( 1 );
  public final static TimeOfDayEnumerationValue NIGHT = new TimeOfDayEnumerationValue( 2 );


  public TimeOfDay( TimeOfDayEnumerationValue value ) {
    // Watch Aliasing!
    myValue = value;
  }

  public int size() {
    return mySize;
  }

  public boolean compare( EnumerationValue value ) {
    boolean Result = false;

    if ( value instanceof TimeOfDayEnumerationValue ) {
      if ( value.getValue() == myValue.getValue() ) {
        Result = true;
      }
    }

    return Result;

  }

  public boolean compare( EnumerationVariable variable ) {
    boolean Result = false;

    if ( variable instanceof TimeOfDay ) {
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
    if ( variable instanceof TimeOfDay ) {
      myValue = variable.getValue();
    }
  }

  public void setValue( EnumerationValue value ) {
    if ( value instanceof TimeOfDayEnumerationValue ) {
      myValue = value;
    }
  }

  public static void main( String[] args ) {

    TimeOfDay Day1 = new TimeOfDay( TimeOfDay.MORNING );
    TimeOfDay Day2 = new TimeOfDay( TimeOfDay.MORNING );
    Day2.setValue( TimeOfDay.AFTERNOON );

    System.out.println( "Should be NOT EQUAL" );
    if ( Day2.compare( Day1 ) ) {
      System.out.println( "EQUAL" );
    } else {
      System.out.println( "NOT EQUAL" );
    }

    System.out.println( "Should be EQUAL" );
    if ( Day2.compare( TimeOfDay.AFTERNOON ) ) {
      System.out.println( "EQUAL" );
    } else {
      System.out.println( "NOT EQUAL" );
    }

    Day1.setValue( Day2 );
    System.out.println( "Should be EQUAL" );
    if ( Day2.compare( Day1 ) ) {
      System.out.println( "EQUAL" );
    } else {
      System.out.println( "NOT EQUAL" );
    }

    System.out.println( "Convert to index" );
    int I = ((TimeOfDayEnumerationValue)Day2.getValue()).getValue();
    System.out.println( "I = " + I );
    Day2.setValue( TimeOfDay.NIGHT );
    I = ((TimeOfDayEnumerationValue)Day2.getValue()).getValue();
    System.out.println( "I = " + I );
  }

}

