package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class ESEManager implements ExecutableSceneElement {

   ESESelector selector;
   Collection eseElements;

   public ESEManager() {
      eseElements = new Vector();
      selector = new DefaultSelector();
   }

   public ESEManager(ESESelector s) {
      eseElements = new Vector();
      selector = s;
   }

   public void execute() {
      if (eseElements.size() > 0) {
         ExecutableSceneElement ese = selector.selectESE(eseElements);
         eseElements.clear();
         ese.execute();
      }
      else {
         return;
      }
   }

   public void addESE(ExecutableSceneElement ese) {
      if (((Collection) eseElements).contains(ese)) {
         return;
      }
      else {
         ((Collection) eseElements).add(ese);
      }
   }

   public void executeInstance() {}

   public void setStack(Stack s) {}

   public class DefaultSelector implements ESESelector {
      public ExecutableSceneElement selectESE(Collection c) {
         ExecutableSceneElement item = null;
         int index = (int)(Math.random()*(double)c.size());
         Iterator i = c.iterator();

         for (int idx = 0; idx <= index; idx++) {
            item = (ExecutableSceneElement)i.next();
         }
         return item;
      }
   }
}