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
 *
 * This object searches an AttributeTrie populated with Tickets, selects the
 * tickets that meet the search criteria as specified by the rangesOfInterest and
 * requiredConnectors.  If more than one ticket is selected, then the ticket to
 * execute is chosen at random and executed.
 *
 * Issues:
 * 1. What should happen if no ticket meets the search criteria?
 *    - This may not be an issue if we have a way to test the search against
 *    the AttributeTrie of ticket when we define the ticketReference.  The only
 *    problem is that we may not be able to test every search combintion.
 * 2. The requiredConnector vector contains StateVectorRange names for
 * explicit criteria we want in the search.  To do this, it requires that we
 * set the desired bit in the stateVector corresponding to this connector value.
 * Two problems here:
 *  - By changing the stateVector directly, the associated TypeValue
 *  connector won't know of the change and they will be out of sync.
 *  - Not all of the bits in the stateVector are controlled by TypeValue
 *  connectos.  The personality related ones are controlled by connectors in the
 *  characterStateSystem.  They change value by corresponding changes in the
 *  associated trait value (i.e., integrity).  It would not make sense to be
 *  able to set these bits directly.  There is nothing in the stateVector
 *  to stop this from occuring.
 *
 * For now, I will change the stateVector directly and ignore these issues.
 * But, they must be resolved before the code is finalized.
 *
 * ALSO, this is identical to the SceneReference except this uses the execution
 * stack.  SceneReference and TicketReference can be combined into one class.
 * It won't hurt for the SceneReference to have an executionStack.  It will
 * simply ignore it.
 */

public class StoryElementReference implements ExecutableSceneElement {

   private AttributeTrie searchTrie;
   private StateVector currentState;
   private Vector rangesOfInterest;
   private Vector requiredConnectors;
   private String description;
   private Stack executionStack;
   private MainCharacter currentProtag;

   public StoryElementReference(AttributeTrie s, MainCharacter mainChar, Vector rOI, Vector rC, String desc) {
      searchTrie = s;
      currentProtag = mainChar;
      currentState = currentProtag.getStoryState().getStateVector();
      rangesOfInterest = rOI;
      requiredConnectors = rC;
      description = desc;
   }

   public void execute() {
      ExecutableSceneElement ese;
      ese = getSingleMatch();
      if (ese != null) {
         // setting the stack setStack() is meaningless if the returned object
         // is a Scene.  It is only used by Tickets.
         ese.setStack(executionStack);
         ese.execute();
      }
      else {
         return;
      }
   }

   public void executeInstance() {}

   public void setStack(Stack s) {
      executionStack = s;
   }

   public String toString() {
      return new String(description);
   }

   public String getDescription() {
      return new String(description);
   }

   public Vector getRangesOfInterest() {
      return rangesOfInterest;
   }

   public Vector getRequiredConnectors() {
      return requiredConnectors;
   }

   public Vector getAllMatches() {
      Enumeration enum = requiredConnectors.elements();
      Object[] typeValue;
      String type;
      String value;
      while (enum.hasMoreElements()) {
         typeValue = (Object[]) enum.nextElement();
         type = (String) typeValue[0];
         value = (String) typeValue[1];
         currentProtag.ConnectorState().changeConnectorValue(type, value);
         rangesOfInterest.add(value);
      }
      QueryVector qVector = currentState.MakeQueryVector(rangesOfInterest);
      ArrayList results = searchTrie.PatriciaQuery(qVector);
      Vector matches = new Vector();
      if (results.size() > 0) {
         for (Iterator i = results.iterator(); i.hasNext(); ) {
            matches.add(i.next());
         }
      }
      else {
         matches = null;
      }
      return matches;
   }

   public ExecutableSceneElement getSingleMatch() {
      Vector results = getAllMatches();
      ExecutableSceneElement ese = null;
      if (results != null) {
         int x = (int)(Math.random()*results.size());
         if (x == results.size()) {
            x = x--;
         }
         ese = (ExecutableSceneElement) results.get(x);
      }
      return ese;
   }


}