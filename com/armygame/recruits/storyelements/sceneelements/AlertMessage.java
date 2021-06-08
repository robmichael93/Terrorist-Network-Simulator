/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 13, 2002
 * Time: 2:50:33 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.storyelements.sceneelements;

public class AlertMessage
{
   public static final int HIGH_PRIORITY  = 0;
   public static final int MEDIUM_PRIORITY = 1;
   public static final int LOW_PRIORITY = 2;

   /* alert types */
   public static final int MESSAGE_ALERT = 0;
   public static final int PROMOTION_PROGRESS_ALERT = 1;
   public static final int PROMOTION_ALERT = 2;
   public static final int CUE_CHOOSER_ALERT = 3;
   public static final int DISPLAY_QUIP = 4;
   public static final int DISPLAY_NARRATOR = 5;

   private String message;
   private int prior;
   private int alertType;
   private int promoteProgress;
   private int narratorIdx;
   private String currentRank;
   private String nextRank;
   private String[] chooserOptions;

   public AlertMessage(int alertType, int priority, String message)
   {
      this.alertType = alertType;
      this.message = message;
      this.prior = priority;
   }

   public AlertMessage(int alertType, int priority, int promoteProgress)
   {
      this.alertType = alertType;
      this.prior = priority;
      this.promoteProgress = promoteProgress;
   }

   public AlertMessage(int alertType, int priority, String newRank, String nextRank, String message)
   {
      this.alertType = alertType;
      this.prior = priority;
      this.currentRank = newRank;
      this.nextRank = nextRank;
      this.message = message;
      promoteProgress = 0;
   }

   public AlertMessage(int alertType, String[] chooserOptions) {
      this.alertType = alertType;
      this.chooserOptions = chooserOptions;
   }

   public AlertMessage(int alertType, String quip) {
      this.alertType = alertType;
      this.message = quip;
   }

   public AlertMessage(int alertType, String narratorText, int narratorImageIndex)
   {
      this.message = narratorText;
      this.alertType = alertType;
      this.narratorIdx = narratorImageIndex;
   }
   public String getText()
   {
      return message;
   }

   public int getPriority()
   {
      return prior;
   }

   public int getAlertType()
   {
      return alertType;
   }

   public String getCurrentRank()
   {
      return currentRank;
   }

   public String getNextRank()
   {
     return nextRank;
   }

   public int getProgress()
   {
     return promoteProgress;
   }

   public String[] getChooserOptions()
   {
     return chooserOptions;
   }

   public String getQuip()
   {
      return message;
   }

   public String getNarratorText()
   {
      return message;
   }

   public int getNarratorImageIndex()
   {
      return narratorIdx;
   }
}
