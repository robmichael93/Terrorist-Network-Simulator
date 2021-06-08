package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 *
 * ?? Should these be broken apart ??
 *
 */

public interface InteractionChangeListener {

   public void InteractionStatusChanged(InteractionChangeEvent e); /* Scene */

   public void CharacterValueAndConnectorUpdate(CharacterValueAndConnectorUpdateEvent e); /* Scene */

   public void UpdateGoal(GoalUpdateEvent e); /* Scene */

   public void ExtendConnectorRequest(ExtendConnectorRequestEvent e); /* IntentConnector */

   public void RetractConnectorRequest(RetractConnectorRequestEvent e); /* IntentConnector */

   public void GeneratePhrase(GeneratePhraseEvent e); /* Scene */

   public void cueChooser(CueChooserEvent e); /* Scene */

   public void displayQuip(DisplayQuipEvent e); /* Scene */

}