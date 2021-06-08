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

public class Possession {

   private Goal parentGoal;
   private GoalManager goalMgr;

   private String name;
   private int cost;
   private int recurringCost;
   private int freq;
   private double timeOfLastUpdate;
   private int paymentsToDate;
   private int numberOfPayments;
   private boolean canRepossess;
   private Vector repossesionPenalties;

   private boolean available;
   private boolean financed;
   private boolean owned;
   private boolean repossessed;
   private boolean income;
   private int missedPayments;
   private int cumulativeMissedPayments;

   public Possession(String possessionName, int itemCost, int recurCost, int frequency,
                     int numbPayments, boolean canRepo, Vector repoPenalties) {

      name = possessionName;
      cost = itemCost;
      recurringCost = recurCost;
      freq = frequency;
      numberOfPayments = numbPayments;
      canRepossess = canRepo;
      repossesionPenalties = repoPenalties;
      missedPayments = 0;
      income = false;
   }

   public Possession(String possessionName, int itemCost, int recurCost, int frequency,
                     int numbPayments, GoalManager gm) {
      this(possessionName, itemCost, recurCost, frequency, numbPayments, false, null);
      goalMgr = gm;
      repossessed = false;
      timeOfLastUpdate = com.armygame.recruits.StoryEngine.instance().clock().getTime();
   }


   public void obtainPossession(Goal g) {
      if (numberOfPayments > 0) {
         financed = true;
      }
      timeOfLastUpdate = com.armygame.recruits.StoryEngine.instance().clock().getTime();
      paymentsToDate = 0;
      repossessed = false;
      parentGoal = g;
      goalMgr = parentGoal.getGoalManager();
      goalMgr.updateResource("bankBalance", -1.0 * ((double)cost));
   }

   public void payRecurringCost(int currentTime) {
      double payment;
      System.out.println("Executing recurring event (" + name + ")");
      if (income) { // income i.e., add to bankBalance
         payment = ((double)recurringCost);
      }
      else { // payment i.e., subtract from bankBalance
         payment = -1.0 * ((double)recurringCost);
      }
      goalMgr.updateResource("bankBalance", payment);
      timeOfLastUpdate += freq;
   }

   public boolean paymentDue(int currentTime) {
      return ( ((timeOfLastUpdate + freq) <= currentTime) & !arePaymentsComplete());
   }

   public void repossess() {
      available = true;
      financed = false;
      owned = false;
      repossessed = true;
      paymentsToDate = 0;
      String value = null;
      String penalty = null;
      Object[] temp;
      Enumeration enum = repossesionPenalties.elements();
      while (enum.hasMoreElements()) {
         temp = (Object[])enum.nextElement();
         value = (String)temp[0];
         penalty = (String)temp[1];
         goalMgr.updateResource(value, new Double(penalty).doubleValue());
      }
      parentGoal.deselectGoal();
   }

   private boolean arePaymentsComplete() {
      if (numberOfPayments == 0) {
         return repossessed;
      }
      else {
         return ( (paymentsToDate >= numberOfPayments) | repossessed);
      }
   }

   public void cancelEvent() {
      repossessed = true;
   }

   public void madePayment() {
      paymentsToDate++;
      if (missedPayments > 0) {
         missedPayments--;
      }
   }

   public void missedAPayment() {
      AlertMessage am;
      double percentMissed;
      String message;
      int priority;
      cumulativeMissedPayments++;
      missedPayments++;
      percentMissed = (double)cumulativeMissedPayments / (double)paymentsToDate;
      if (cumulativeMissedPayments >=4) {
         if (percentMissed > .5) {
            priority = AlertMessage.HIGH_PRIORITY;
            message = "Your soldier has missed " + ((int)(100.0 * percentMissed)) + " percent of their " + getName() + " payments.";
            message += "<br>Their " + getName() + " has been repossessed!";
            repossess();
            am = new AlertMessage(AlertMessage.MESSAGE_ALERT, priority, message);
            com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
         }
         else {
            if (percentMissed > .3) {
            priority = AlertMessage.MEDIUM_PRIORITY;
            message = "Your soldier has missed " + ((int)(100.0 * percentMissed)) + " percent of their " + getName() + " payments.";
            message += "<br>It is in danger of being repossessed!";
            am = new AlertMessage(AlertMessage.MESSAGE_ALERT, priority, message);
            com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
            }
         }
      }
      else {
         switch (missedPayments) {
            case 1:
               message = "Your soldier missed a " + getName() + " payment.";
               priority = AlertMessage.LOW_PRIORITY;
               am = new AlertMessage(AlertMessage.MESSAGE_ALERT, priority, message);
               com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
               break;
            case 2:
               message = "Your soldier missed a second " + getName() + " payment.";
               message += "<br>One more missed payment and the " + getName() + " will be repossessed!";
               priority = AlertMessage.MEDIUM_PRIORITY;
               am = new AlertMessage(AlertMessage.MESSAGE_ALERT, priority, message);
               com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
               break;
            case 3:
               message = "Three " + getName() + " payments in a row have been missed.";
               message += "<br>Your soldier's " + getName() + " has been repossessed!";
               priority = AlertMessage.HIGH_PRIORITY;
               repossess();
               am = new AlertMessage(AlertMessage.MESSAGE_ALERT, priority, message);
               com.armygame.recruits.StoryEngine.instance().sendAlertMessage(am);
               break;
         }
      }
   }

   public boolean isRepossessed() {
      return repossessed;
   }

   public String getName() {
      return name;
   }

   public int getRemainingPayments() {
      return numberOfPayments - paymentsToDate;
   }

   public int getRecurringCost() {
      return recurringCost;
   }

   public int getNumberOfPayments() {
      return numberOfPayments;
   }

   public int getPaymentsToDate() {
      return paymentsToDate;
   }

   public int getFrequency() {
      return freq;
   }

   public int getMissedPayements() {
      return missedPayments;
   }

   public void setName(String n) {
      name = n;
   }

   public void setRecurringCost(int c) {
      recurringCost = c;
   }

   public void setRecurringIncome(int c) {
      recurringCost = c;
      income = true;
   }

}