package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.storyelements.sceneelements.events.*;
import java.util.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class GameClock {

   private double currentTime;
   private double lastTime;
   private double maxTime;
   private double increment;
   private boolean isAtMaxTime;

   private Vector listeners;

   public GameClock() {

      currentTime = 0;
      lastTime = 0;
      maxTime = 0;
      increment = 0;
      isAtMaxTime = true;

      listeners = new Vector();
   }

   public double getTime() {
      return currentTime;
   }

   public void incrementTime(double delta) {
      if (!isAtMaxTime) {
         lastTime = currentTime;
         currentTime += delta;
         if (currentTime >= maxTime) {
            currentTime = maxTime;
            isAtMaxTime = true;
         }
         notifyListeners();
         System.out.println("Increment time (" + delta + "): new time is " + currentTime);
      }
      else {
         System.out.println("** Unable to increment time *** Exceeding Max Time");
      }
   }

   public void setMaxTime(double time) {
      maxTime = time;
      if (maxTime > currentTime) {
         isAtMaxTime = false;
      }
   }

   public double getMaxTime() {
      return maxTime;
   }

   public void setIncrement(double inc) {
      increment = inc;
   }

   public void incrementTime() {
      incrementTime(increment);
   }

   public void addClockListener(GameClockListener g) {
      if (!listeners.contains(g)) {
         listeners.add(g);
      }
   }

   public void removeClockListener(GameClockListener g) {
      listeners.remove(g);
   }

   private void notifyListeners() {
      Enumeration enum = listeners.elements();
      while (enum.hasMoreElements()) {
         ((GameClockListener)enum.nextElement()).gameClockChanged(new GameClockChangeEvent(currentTime));
      }
   }

   public boolean isAtMaxTime() {
      return isAtMaxTime;
   }

}