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

public interface ExecutableSceneElement {

   public void execute();
   public void executeInstance();
   public void setStack(Stack s);

}