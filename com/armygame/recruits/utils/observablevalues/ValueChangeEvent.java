package com.armygame.recruits.utils.observablevalues;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 1.0
 */

public class ValueChangeEvent {

   private ObservableValue source;
   private boolean increment;

   public ValueChangeEvent(ObservableValue s) {
      source = s;
      increment = false;
   }

   public ObservableValue getSource() {
      return source;
   }
}