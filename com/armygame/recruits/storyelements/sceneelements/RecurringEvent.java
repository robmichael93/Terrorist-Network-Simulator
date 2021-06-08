package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class RecurringEvent {

   private String description;
   private int timeOfLastUpdate;
   private int frequency;
   private int maxNumberOfOccurances; // if zero, then no max number
   private int occurancesToDate;
   private int financialEventAmount;
   private ConditionAction recurringAction;
   private Vector recurringActions;
   private boolean eventCancelled;

   public RecurringEvent(String desc, int freq, int maxOccur, Vector actions) {
      description = desc;
      frequency = setFrequency(freq);
      maxNumberOfOccurances = maxOccur;
      recurringActions = actions;
      eventCancelled = false;
   }

   public void execute(int currentTime, ActionHandler handler) {
      boolean actionExecuted = false;
      while ( ((timeOfLastUpdate + frequency) <= currentTime) & !isEventComplete()) {
         System.out.println("Executing recurring event (" + description + ")");
         Enumeration enum = recurringActions.elements();
         while ( !((ConditionAction)enum.nextElement()).execute(handler) ) {
         }
         timeOfLastUpdate += frequency;
         occurancesToDate++;
      }
   }

   private int setFrequency(int freq) {
      if (freq <= 0) {
         System.out.println("** Error(RecurringEvent):(" + description+ ") Frequency <= zero");
         return 1;
      }
      else {
         return freq;
      }
   }

   private boolean isEventComplete() {
      if (maxNumberOfOccurances == 0) {
         return eventCancelled;
      }
      else {
         return ( (occurancesToDate < maxNumberOfOccurances) | eventCancelled);
      }
   }

   public void initialize(int currTime) {
      timeOfLastUpdate = currTime;
      eventCancelled = false;
   }

   public void cancelEvent() {
      eventCancelled = true;
   }

   public boolean isEventCancelled() {
      return eventCancelled;
   }

   public Object[] getFinancialEvent() {
      Object[] tempArray = new Object[3];
      Vector updateResourceActions;
      Enumeration enum = recurringActions.elements();
      while (enum.hasMoreElements()) {
         recurringAction = (ConditionAction)enum.nextElement();
         if (recurringAction.allConditionsMet()) {
            updateResourceActions = recurringAction.getUpdateResourceActions();
            Enumeration enum1 = updateResourceActions.elements();
            while (enum1.hasMoreElements()) {
               Object[] temp = (Object[])enum1.nextElement();
               if ( ((String)temp[0]).equalsIgnoreCase("bankBalance") ) {
                  tempArray[0] = temp[0];
                  tempArray[1] = temp[1];
                  financialEventAmount = (int)((Double) temp[1]).doubleValue();
                  tempArray[2] = new Integer(frequency);
                  return tempArray;
               }
            }
         }
      }
      return null;
   }

   public String getDescription() {
      return description;
   }

   public int getRemainingOccurances() {
      return maxNumberOfOccurances - occurancesToDate;
   }

   public int getMaxNumberOfOccurances() {
      return maxNumberOfOccurances;
   }

   public int getOccurancesToDate() {
      return occurancesToDate;
   }

   public int getFinancialEventAmount() {
      return financialEventAmount;
   }

   public int getFrequency() {
      return frequency;
   }
}