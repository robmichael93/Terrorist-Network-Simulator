package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.agentutils.tickets.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import java.util.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class SequentialStoryTicket extends SequentialTicket implements Ticket {

   private double duration;
   private double projectedEndTime;

   public SequentialStoryTicket(String name) {
      super(name);
      duration = 0;
   }

   public SequentialStoryTicket(String name, double dur) {
      super(name);
      duration = dur;
   }

   public void execute() {
      // if storyLineStack is null, throw and exception
      ExecutableSceneElement ese = (ExecutableSceneElement)getNextFrameElement();
      if (ese != null) {
         ese.setStack(executionStack);
         executionStack.push(this);
         ese.execute();
         if (noMoreFrames()) {
            getNextFrameElement(); // this will allow ticket to exit gracefully
            executionStack.remove(this); // remove this ticket from the stack if it is done
         }
      }
   }

   public void executeInstance() {
   }

   public void initialize() {
      super.initialize();
      projectedEndTime = com.armygame.recruits.StoryEngine.instance().clock().getTime() + duration;
      com.armygame.recruits.StoryEngine.instance().clock().setMaxTime(projectedEndTime);
   }

   public void closeTicket() {
      super.closeTicket();
      double currentTime = com.armygame.recruits.StoryEngine.instance().clock().getTime();
      if ( currentTime < projectedEndTime) {
         com.armygame.recruits.StoryEngine.instance().clock().incrementTime(projectedEndTime - currentTime);
      }
      projectedEndTime = 0.0;
   }

   public void setDuration(int dur) {
      duration = dur;
   }
}