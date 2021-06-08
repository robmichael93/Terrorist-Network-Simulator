package com.armygame.recruits.interaction.variables;


public class Rank extends EnumerationVariable {

  private EnumerationValue myValue;
  private int mySize;

  {
    mySize = 7;
  }

  public final static RankEnumerationValue PRIVATE = new RankEnumerationValue( 0 );
  public final static RankEnumerationValue RECRUIT = new RankEnumerationValue( 1 );
  public final static RankEnumerationValue SERGEANT = new RankEnumerationValue( 2 );
  public final static RankEnumerationValue MASTERSERGEANT = new RankEnumerationValue( 3 );
  public final static RankEnumerationValue SERGEANTMAJOR = new RankEnumerationValue( 4 );
  public final static RankEnumerationValue CORPORAL = new RankEnumerationValue( 5 );
  public final static RankEnumerationValue DRILLSERGEANT = new RankEnumerationValue( 6 );


  public Rank( RankEnumerationValue value ) {
    // Watch Aliasing!
    myValue = value;
  }

  public int size() {
    return mySize;
  }

  public boolean compare( EnumerationValue value ) {
    boolean Result = false;

    if ( value instanceof RankEnumerationValue ) {
      if ( value.getValue() == myValue.getValue() ) {
        Result = true;
      }
    }

    return Result;

  }

  public boolean compare( EnumerationVariable variable ) {
    boolean Result = false;

    if ( variable instanceof Rank ) {
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
    if ( variable instanceof Rank ) {
      myValue = variable.getValue();
    }
  }

  public void setValue( EnumerationValue value ) {
    if ( value instanceof RankEnumerationValue ) {
      myValue = value;
    }
  }

}

