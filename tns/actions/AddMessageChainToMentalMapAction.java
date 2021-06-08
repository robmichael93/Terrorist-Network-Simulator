package tns.actions;
import tns.frames.*;
import tns.messages.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import com.touchgraph.graphlayout.*;
import com.touchgraph.graphlayout.graphelements.*;
import java.util.*;
import tns.util.*;

/**
 * This action takes a message's forwarding chain and updates the mental maps
 * of the orginator and the recipient by adding nodes and links from the chain
 * that are absent in the respective agents' mental maps.
 * @author Rob Michael and Zac Staples
 */
public class AddMessageChainToMentalMapAction implements Frame {
    
    /**
     * The recipient of the message.
     */
    private Agent target;
    /**
     * The sender of the message.
     */
    private Agent originator;
    /**
     * The associated <CODE>Message</CODE> object.
     */
    private Message message;
    
    /** 
     * Creates a new instance of AddMessageChainToMentalMapAction
     * @param target The recipient of the message.
     * @param originator The sender of the message.
     * @param message The associated <CODE>Message</CODE> object.
     */
    public AddMessageChainToMentalMapAction(Agent target, Agent originator, Message message) {
        this.target = target;
        this.originator = originator;
        this.message = message;
    } // end AddMessageChainToMentalMapAction constructor
    
    /**
     * This method checks the message's forwarding chain and then updates
     * the mental maps of the recipient and the originator based on nodes and
     * links in the forwarding chain that do not exist in the agents' mental
     * maps.
     */
    public void execute() {
//        System.out.println("AddMessageChainToMentalMapAction executing");
        // Start with the originator of the message and construct his mental map
        // Get the originator's mental map
//        System.out.println(originator.getEntityName() + " processing mental map as the originator...");
        MentalMap map = ((TerroristAgent)originator).getMentalMap();
        SubNet subNet = map.getSubNet();
        Vector chain = message.getChain();
        Enumeration e = chain.elements();
        while (e.hasMoreElements()) {
            // find the edge that corresponds to each element in the forwarding
            // chain of the message
            AgentPair agentPair = (AgentPair)e.nextElement();
            Agent fromAgent = agentPair.getFrom();
            Agent toAgent = agentPair.getTo();
/*            System.out.println("Evaluating chain element from " + fromAgent.getEntityName() + " to " +
                               toAgent.getEntityName());*/
            // if the edge doesn't exist (it's null) or the originator doesn't
            // have this edge in his mental map, then do the next check
            if (!subNet.contains(agentPair)) {
                // does the originator's mental map contain the "to" agent in
                // the chain element?
                if (!map.getKnownAgents().contains(toAgent)) {
                    // if not, then add the Node to the originator's mental map
                    map.getKnownAgents().add(toAgent);
                    map.changeNode("add", toAgent);
                } else {
//                    System.out.println(originator.getEntityName() + " already has " + toAgent.getEntityName() + " in his mental map.");
                } // end if-else
                // Add the "edge" to the Target's mental map
                subNet.addPair(agentPair);
                ((TerroristAgent)originator).changeLink("add", fromAgent, toAgent);
                // if the edge came from the originator, then the "to" agent is
                // directly linked, so add to directly linked list
                if (fromAgent == originator) {
                    if (!map.getDirectlyLinkedAgents().contains(toAgent)) {
                        map.getDirectlyLinkedAgents().add(toAgent);
/*                        System.out.println(toAgent.getAgentName() + " added as directly linked agent of " + 
                                           originator.getEntityName());*/
                    } // end if
                } // end if
            } else {
/*                System.out.println(originator.getEntityName() + " already has the edge from " + fromAgent.getEntityName() +
                                   " to " + toAgent.getEntityName());*/
            } // end if-else
        } // end while
//        System.out.println(target.getEntityName() + " processing mental map as the target...");
        // Now go to the target of the message and construct his mental map
        // Get the targets's mental map
        map = ((TerroristAgent)target).getMentalMap();
        subNet = map.getSubNet();
        e = chain.elements();
        while (e.hasMoreElements()) {
            // find the edge that corresponds to each element in the forwarding
            // chain of the message
            AgentPair agentPair = (AgentPair)e.nextElement();
            Agent fromAgent = agentPair.getFrom();
            Agent toAgent = agentPair.getTo();
/*            System.out.println("Evaluating chain element from " + fromAgent.getEntityName() + " to " +
                               toAgent.getEntityName());*/
            // if the edge doesn't exist (it's null) or the originator doesn't
            // have this edge in his mental map, then do the next check
            if (!subNet.contains(agentPair)) {
                // does the originator's mental map contain the "to" agent in
                // the chain element?
                if (!map.getKnownAgents().contains(fromAgent)) {
                    // if not, then add the Node to the originator's mental map
                    map.getKnownAgents().add(fromAgent);
                    map.changeNode("add", fromAgent);
                } else {
//                    System.out.println(target.getEntityName() + " already has " + fromAgent.getEntityName() + " in his mental map.");
                } // end if-else
                // Add the "edge" to the Target's mental map
                subNet.addPair(agentPair);
                ((TerroristAgent)target).changeLink("add", toAgent, fromAgent);
                // if the edge came from the originator, then the "to" agent is
                // directly linked, so add to directly linked list
                if (toAgent == target) {
                    if (!map.getDirectlyLinkedAgents().contains(fromAgent)) {
                        map.getDirectlyLinkedAgents().add(fromAgent);
/*                        System.out.println(fromAgent.getAgentName() + " added as directly linked agent of " + 
                                           target.getEntityName());*/
                    } // end if
                } // end if
            } else {
/*                System.out.println(target.getEntityName() + " already has the edge from " + toAgent.getEntityName() +
                                   " to " + fromAgent.getEntityName());*/
            } // end if-else
        } // end while
    } // end execute
    
} // end class AddMessageChainToMentalMapAction
