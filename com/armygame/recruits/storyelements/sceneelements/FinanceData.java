package com.armygame.recruits.storyelements.sceneelements;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class FinanceData {

   public String name;
   public int maxNumberOfOccurances; /* if negative, then no limit */
   public int occurancesRemaining;
   public int amount;
   public int frequency;
   public int missedPayments;
   public static String[] FREQ_LABELS = {"0",             /* 0 */
                                         "weekly",        /* 1 */
                                         "semi-monthly",  /* 2 */
                                         "3",             /* 3 */
                                         "monthly"};      /* 4 */

   public FinanceData(Possession p) {
      name = p.getName();
      maxNumberOfOccurances = p.getNumberOfPayments();
      if (maxNumberOfOccurances == 0) {
         maxNumberOfOccurances --;
      }
      occurancesRemaining = p.getRemainingPayments();
      amount = p.getRecurringCost();
      frequency = p.getFrequency();
      missedPayments = p.getMissedPayements();
   }

   public FinanceData(Goal g) {
      name = g.recurringFinancialEventName;
      maxNumberOfOccurances = g.recurringFinancialEventMaxPayments;
      if (maxNumberOfOccurances == 0) {
         maxNumberOfOccurances --;
      }
      occurancesRemaining = maxNumberOfOccurances - g.recurringFinancialEventPaymentsComplete;
      amount = Math.abs(g.recurringFinancialEventAmt);
      frequency = g.recurringFinancialEventFreq;
      missedPayments = 0;
   }
}