package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import com.armygame.recruits.agentutils.connectors.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 *
 * needs to cleanup the intentConnectors once the next interaction is selected
 * or should the selected interaction retract all of the intent connectors
 * that were used to select it for play?
 */

public class SceneManager {

   Scene activeScene;


   public SceneManager() {
   }

   public void setActiveScene(Scene s) {
      activeScene = s;
   }


   public void generateScene() {
      activeScene.execute();
   }

}