package com.armygame.recruits.storyelements.sceneelements;

import com.armygame.recruits.utils.*;
import com.armygame.recruits.storyelements.sceneelements.*;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class StoryLineManager implements ExecutableSceneElement {

   private Stack storyLine;
   private AttributeTrie scenes;
   private AttributeTrie tickets;

   public StoryLineManager(AttributeTrie s, AttributeTrie t) {
      scenes = s;
      tickets = t;
      storyLine = new Stack();
   }

   public StoryLineManager(AttributeTrie s, AttributeTrie t, ExecutableSceneElement e) {
      scenes = s;
      tickets = t;
      storyLine = new Stack();
      initialize(e);
   }

   private void initialize (ExecutableSceneElement ese) {
      storyLine.push(ese);
   }

   public void execute() {
      ExecutableSceneElement ese = (ExecutableSceneElement) storyLine.pop();
      ese.setStack(storyLine);
      if (ese != null) {
         ese.execute();
      }
   }

   public boolean isComplete() {
      return storyLine.isEmpty();
   }

   public void setStack(Stack s) {}

   public void executeInstance() {}

   public void pushElement(ExecutableSceneElement ese) {
      storyLine.push(ese);
   }
}