package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.agentutils.tickets.*;
import com.armygame.recruits.storyelements.sceneelements.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class SequentialSceneTicket extends SequentialTicket implements Ticket {

   public SequentialSceneTicket(String name) {
      super(name);
   }

   public void execute() {
      ExecutableSceneElement ese = (ExecutableSceneElement)getNextFrameElement();
      if (ese != null) {
         Scene.ticketControlStack.push(this);
         ese.execute();
      }
   }

   public void executeInstance() {
   }

   public void initialize() {
   }

}