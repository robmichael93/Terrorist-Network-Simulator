package com.armygame.recruits.agentutils.tickets;

import java.util.*;
import com.armygame.recruits.storyelements.sceneelements.*;


/**
 * Title:        Recruits Scene Elements
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Army Game Project
 * @author Brian Osborn
 * @version 1.0
 *
 * The closeTicket() method from the Ticket interface has been implemented in
 * this class (see below).  *** read the description of the getNextFrameElement method.
 */

public abstract class TicketBase implements ExecutableSceneElement, Ticket {

   protected Vector frames;
   protected Stack executionStack;
   protected int currentFrameIndex;
   protected boolean initialExecution;
   protected boolean ticketComplete;
   protected String name;
   protected String description;
   protected Vector keys;

   protected TicketBase(String n) {
      name = n;
      description = "";
      frames = new Vector(0);
      keys = new Vector();
      currentFrameIndex = -1;
      initialExecution = true;
      ticketComplete = false;
   }

   protected boolean isInitialExecution() {
      return initialExecution;
   }

   protected int getCurrentFrameIndex() {
      return currentFrameIndex;
   }

   protected void setCurrentFrameIndex(int i) {
      currentFrameIndex = i;
   }

/**
 * This method returns the next frame element to the calling method.
 * Note: If the end of the ticket is reached, this method will call the
 * closeTicket() method (implemented in this class), and return null.
 */
   private Object getCurrentFrameElement() {
      if (!isTicketComplete()) {
         return frames.get(currentFrameIndex);
      }
      else {
         closeTicket();
         return null;
      }
   }

   protected Object getNextFrameElement() {
      if (initialExecution) {
         initialize();
      }
      nextFrame();
      return getCurrentFrameElement();
   }

   public void addFrame(Object o, int i) {
      frames.insertElementAt(o, i);
   }

   protected boolean removeFrameElement(Object o) {
      return frames.removeElement(o);
   }

   protected boolean removeFrameElementAt(int i) {
      frames.removeElementAt(i);
      return true;
   }

   protected Vector getFrames() {
      return frames;
   }

   public void setStack(Stack eS) {
      executionStack = eS;
   }

   protected boolean isTicketComplete() {
      return ticketComplete;
   }

   protected void setTicketComplete(boolean b) {
      ticketComplete = b;
   }

   public void initialize() {
      initialExecution = false;
   }

   public void closeTicket() {
      currentFrameIndex = -1;
      setTicketComplete(false);
      initialExecution = true;
      System.out.println("Ticket closed: " + name);
   }

   public String getName() {
      return new String(name);
   }

   public void setName(String n) {
      name = n;
   }

   public String getDescription() {
      return new String(description);
   }

   public void setDescription(String desc) {
      description = desc;
   }

   public void addKey(String k) {
      keys.add(k);
   }

   public Vector getKeys() {
      return new Vector(keys);
   }

   public void setKeys(Vector k) {
      keys = k;
   }
}