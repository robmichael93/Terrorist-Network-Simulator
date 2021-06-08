package com.armygame.recruits.storyelements.sceneelements.events;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class DisplayQuipEvent {

   private String quipName;

   public DisplayQuipEvent(String quipName) {
      this.quipName = quipName;
   }

   public String getQuipName() {
      return quipName;
   }


}