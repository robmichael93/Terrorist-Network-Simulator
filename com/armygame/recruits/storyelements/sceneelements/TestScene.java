package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;



/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 *
 * This object has extended Scene and added a data structure to hold the
 * list of keys that describe the object.  Also added is a getter method.
 * Keys will need to be added to the Scene object for full scale production of
 * scenes.
 */

public class TestScene extends Scene {

   private MainCharacter protag;
   private NewInteraction interaction;
   private SequentialSceneTicket sceneTicket;
   private Vector keys;

   public TestScene(MainCharacter mChar, NewInteraction intx, Vector k) {
      protag = mChar;
      interaction = intx;
      keys = k;
      registerCharacter("protag", protag);
      registerInteraction(intx);
      sceneTicket = new SequentialSceneTicket("Scene Ticket");
      sceneTicket.addFrame(interaction, 0);
      setSceneTicket(sceneTicket);
   }

   public Vector getKeys() {
      return keys;
   }

   public String toString() {
      return interaction.toString();
   }
}