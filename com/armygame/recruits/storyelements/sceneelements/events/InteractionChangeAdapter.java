package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public abstract class InteractionChangeAdapter  implements InteractionChangeListener {

   public void InteractionStatusChanged(InteractionChangeEvent e) {}

   public void CharacterValueAndConnectorUpdate(CharacterValueAndConnectorUpdateEvent e) {}

   public void UpdateGoal(GoalUpdateEvent e) {}

   public void ExtendConnectorRequest(ExtendConnectorRequestEvent e) {}

   public void RetractConnectorRequest(RetractConnectorRequestEvent e) {}

   public void GeneratePhrase(GeneratePhraseEvent e) {}

}