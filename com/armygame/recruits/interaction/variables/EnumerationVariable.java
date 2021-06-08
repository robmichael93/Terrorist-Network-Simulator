package com.armygame.recruits.interaction.variables;

abstract public class EnumerationVariable extends InteractionVariable {
  abstract public boolean compare( EnumerationValue value );
  abstract public boolean compare( EnumerationVariable variable );
  abstract public EnumerationValue getValue();
  abstract public void setValue( EnumerationVariable variable );
  abstract public void setValue( EnumerationValue value );
  abstract public int size();
}

