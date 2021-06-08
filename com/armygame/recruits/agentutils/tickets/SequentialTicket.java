package com.armygame.recruits.agentutils.tickets;

import java.util.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public abstract class SequentialTicket extends TicketBase implements Ticket {

   public SequentialTicket(String name) {
      super(name);
   }

   public void nextFrame() {
      currentFrameIndex++;
      if (currentFrameIndex >= frames.size()) {
         setTicketComplete(true);
      }
   }

   public boolean noMoreFrames() {
      return (currentFrameIndex + 1 >= frames.size());
   }

}