package com.armygame.recruits.storyelements.sceneelements.intentconnectors;

import java.util.*;
import com.armygame.recruits.utils.observablevalues.*;
import com.armygame.recruits.storyelements.sceneelements.events.*;
import com.armygame.recruits.agentutils.connectors.*;

/**
 * Title:        Recruits Character
 * Description:  This project defines a Recruits character.
 * Copyright:    Copyright (c) 2001
 * Company:      Recruits Project
 * @author Brian Osborn
 * @version 1.0
 */

public class IntentConnector extends Connector implements InteractionChangeListener {

   public IntentConnector(String n, int i, boolean s) {
      //super(n, i, s);
      super(n, 0.0, 0.0, "");
   }

   public IntentConnector(String n, int i) {
      this(n, i, false);
   }

   public IntentConnector(String name) {
      super(name, 0.0, 0.0, "");
   }

   public void changeStatus(boolean s) {
      status = s;
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         IntentConnectorChangeListener ccl = (IntentConnectorChangeListener) enum.nextElement();
         ccl.IntentConnectorChanged(new IntentConnectorChangeEvent(this));
      }
   }

   public void ExtendConnectorRequest(ExtendConnectorRequestEvent e) {
      if (e.getName().equalsIgnoreCase(this.getName())) {
         extendConnector();
      }
   }

   public void RetractConnectorRequest(RetractConnectorRequestEvent e) {
      if (e.getName().equalsIgnoreCase(this.getName())) {
         retractConnector();
      }
   }

   public void InteractionStatusChanged(InteractionChangeEvent e) {}

   public void CharacterValueAndConnectorUpdate(CharacterValueAndConnectorUpdateEvent e) {}

   public void UpdateGoal(GoalUpdateEvent e) {}

   public void GeneratePhrase(GeneratePhraseEvent e) {}

   public void cueChooser(CueChooserEvent e) {}

   public void displayQuip(DisplayQuipEvent e) {}

}