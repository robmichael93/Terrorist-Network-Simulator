package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.utils.observablevalues.*;
import java.util.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class PossessionsManager {

   private Hashtable possessions;
   private Vector heldPossessions;
   private Possession currentBillBeingPaid;
   private boolean payingBill;

   public PossessionsManager(Hashtable possessionsTable) {
      possessions = possessionsTable;
      heldPossessions = new Vector();
   }

   public double payBills(int currentTime) {
      double currentTransaction = 0;
      double billsPerTimeUnit = 0;
      double timeUnits= 0;
      Enumeration enum = heldPossessions.elements();
      while (enum.hasMoreElements()) {
         currentBillBeingPaid = (Possession)enum.nextElement();
         while (currentBillBeingPaid.paymentDue(currentTime)) {
            payingBill = true;
            currentBillBeingPaid.payRecurringCost(currentTime);
            payingBill = false;
            if (currentBillBeingPaid.isRepossessed()) {
               removeHeldPossession(currentBillBeingPaid.getName());
            }
            else {
               currentTransaction = currentBillBeingPaid.getRecurringCost();
               timeUnits = (double)currentBillBeingPaid.getFrequency();
               billsPerTimeUnit += (currentTransaction / timeUnits);
            }
         }
      }
      return billsPerTimeUnit;
   }

   public void addBillsToDebitVector(Vector debits) {
      Enumeration enum = heldPossessions.elements();
      while (enum.hasMoreElements()) {
         debits.add(new FinanceData((Possession)enum.nextElement()));
      }
   }

   public void ValueChanged(ValueChangeEvent e) {
      if ((e.getSource().getName().equalsIgnoreCase("bankBalance")) & payingBill ) {
         if (e.getSource().getValue() < .00) {
            currentBillBeingPaid.missedAPayment();
         }
         else {
            currentBillBeingPaid.madePayment();
         }
      }
   }

   public void obtainPossession(String possessionName, Goal sourceGoal) {
      ((Possession)possessions.get(possessionName)).obtainPossession(sourceGoal);
      heldPossessions.add((Possession)possessions.get(possessionName));
            System.out.println("++ Item purchased ++ " + possessionName);
      // send message to GUI notifying player of purchase
   }

   public void removeHeldPossession(String possessionName) {
      heldPossessions.remove((Possession)possessions.get(possessionName));
   }



}