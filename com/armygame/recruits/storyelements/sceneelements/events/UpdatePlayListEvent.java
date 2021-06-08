package com.armygame.recruits.storyelements.sceneelements.events;

import com.armygame.recruits.storyelements.sceneelements.*;

/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 */

public class UpdatePlayListEvent {

   private StringPairVector playList;

   public UpdatePlayListEvent() {
      playList = new StringPairVector();
   }

   public void clear() {
      playList.addStringPair(new MethodParameterPair("0", null));
   }

   public void beginTogether() {
      playList.addStringPair(new MethodParameterPair("1", null));
   }

   public void endTogether() {
      playList.addStringPair(new MethodParameterPair("2", null));
   }

   public void add(String line) {
      playList.addStringPair(new MethodParameterPair("3", line));
      //System.out.println(line);
   }

   public void dumpToFile(String file) {
      playList.addStringPair(new MethodParameterPair("4", file));
   }

   public StringPairVector getPlayList() {
      return playList;
   }

   public void clearUpdatePlayListEvent() {
      playList.getStringPairVector().clear();
   }


}