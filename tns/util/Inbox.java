package tns.util;
import mil.navy.nps.relate.*;
import tns.messages.*;
import java.util.*;

/**
 * Each agent has an inbox that other agent's can place messages in.  At the 
 * start of any agent's turn, that agent will process the messages in his inbox. 
 * The agent first checks the target role to see if it matches one of his roles. 
 * If it does not, then he places the message in his outbox to be forwarded on. 
 * If the agent has a role that matches the target role, then the agent 
 * evaluates the benefits and risks of answering the message.  If the benefits 
 * exceed the risks, then the agent takes the action specified in the message's 
 * content, which is in the form of a ticket.  The last action an agent takes 
 * in answering a message is to look at the message's forwarding chain and if 
 * any of the agents or links between them are missing from the agent's mental 
 * map he adds those nodes and links.  As each message is examined it is removed
 * from the agent's inbox.
 * @author  Rob Michael and Zac Staples
 */
public class Inbox {
    
    /**
     * The agent who owns this inbox.
     */
    private Agent agent;
    /**
     * The container for the messages in the Inbox.
     */
    private Vector messageQueue;
    
    /** 
     * Creates a new instance of Inbox 
     * @param agent The agent who owns this inbox.
     */
    public Inbox(Agent agent) {
        this.agent = agent;
        messageQueue = new Vector();
    } // end Inbox constructor
    
    /**
     * Simply adds a message to the container of inbox messages.
     * @param m The message to be added.
     * @return True if the message was successfully added.
     */
    public boolean addMessage(Message m) {
//        System.out.println("Adding message " + m.toString() + " added to " + agent.getEntityName() + "'s inbox.");
        return messageQueue.add(m);
    } // end addMessage
    
    /**
     * The process method simply takes each message in the inbox, calls their
     * process() method and then removes the message from the inbox.
     */
    public void process() {
//        System.out.println(agent.getEntityName() + " processing inbox...");
        if (messageQueue.size() > 0) {
            Iterator i = messageQueue.iterator();
            while (i.hasNext()) {
                Message message = (Message)i.next();
                message.process(agent);
                i.remove();
            } // end while
        } // end if
    } // end process
    
} // end class Inbox
