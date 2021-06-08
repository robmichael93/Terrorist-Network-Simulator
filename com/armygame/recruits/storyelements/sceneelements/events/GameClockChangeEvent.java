package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GameClockChangeEvent {

   double currentTime;

   public GameClockChangeEvent(double newTime) {

      currentTime = newTime;
   }

   public double getTime() {
      return currentTime;
   }
}