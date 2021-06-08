package tns.util;
import mil.navy.nps.relate.*;
import tns.messages.*;
import tns.messages.benefits.*;
import tns.messages.risks.*;
import tns.agents.*;
import tns.util.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * Each agent also has an outbox for sending messages to other agents.  At the 
 * end of an agent’s turn, the agent will process the messages in his outbox.  
 * The agent keeps track of the messages he has processed by their fully 
 * qualified name, so he does not send more than one message of any given fully 
 * qualified name.  If the agent hasn’t send a message of a given type, then he 
 * first checks to see if he is the originator or if he is forwarding the 
 * message.  Next, he evaluates the benefits and risks of sending or forwarding 
 * the message.  If the benefits exceed the risks, then he creates a new message 
 * forwarding chain if he originated the message, or adds a new element to the 
 * chain if he is forwarding the message.  The agent then puts the message in 
 * the recipient’s inbox and lastly annotates how many times the agent has 
 * communicated with the recipient.  This annotation is used in determining how 
 * familiar an agent is with another agent when evaluating the familiarity risk.
 * @author  Rob Michael and Zac Staples
 */
public class Outbox {
    
    /**
     * The agent that owns this outbox.
     */
    private Agent agent;
    /**
     * The container for the messages in the outbox.
     */
    private Vector messageQueue;
    /**
     * A table that keeps track of which agents had messages sent to them in
     * order to update the owning agent's table of communication counts with
     * agents.
     */
    private Hashtable turnCommunications;
    /**
     * Keeps tracks of which types of messages have been processed by the
     * messages fully qualified name.
     */
    private Hashtable messageTypes;
    /**
     * The organizational benefit for processing a message.
     */
    private OrganizationalBenefit organizationalBenefit = null;
    /**
     * The influence benefit for processsing a message.
     */
    private InfluenceBenefit influenceBenefit = null;
    /**
     * The personal benefit for processing a message.
     */
    private PersonalBenefit personalBenefit = null;
    /**
     * The familiarity risk against processing a message.
     */
    private FamiliarityRisk familiarityRisk = null;
    /**
     * The goal synchronization risk against processing a message.
     */
    private GoalSynchronizationRisk goalSynchronizationRisk = null;
    /**
     * The separation risk against processing a message.
     */
    private SeparationRisk separationRisk = null;
    /**
     * Used to store the total benefit for a message.
     */
    private int benefit = 0;
    /**
     * Used to store the totaol risk for a message.
     */
    private int risk = 0;
    
    /** 
     * Creates a new instance of Outbox 
     * @param agent The agent that owns this outbox.
     */
    public Outbox(Agent agent) {
        this.agent = agent;
        messageQueue = new Vector();
        turnCommunications = new Hashtable();
        messageTypes = new Hashtable();
    } // end Outbox constructor
    
    /**
     * Simply adds a message to the container of outbox messages.
     * @param m The message to be added.
     * @return True if the message was successfully added.
     */
    public boolean addMessage(Message m) {
//        System.out.println("Adding message " + m.toString() + " to " + agent.getEntityName() + "'s outbox.");
        return messageQueue.add(m);
    } // end addMessage
    
    /**
     * The process message looks at each message in the outbox and first checks
     * to see if the fully qualified name for the message is unique.  If the
     * name is unique then it is handed off to the send() method for further
     * processing before sending.  If the message is not unique, then it is
     * just dropped.  All messages are removed from the outbox as they are
     * processed.  After all the messages are sent, the method examines the
     * table of communications for the turn and it updates the owning agent's
     * table of communications with other agents by one count for each agent
     * the owning agent communicated with.
     */
    public void process() {
        turnCommunications = new Hashtable();
        messageTypes = new Hashtable();
//        System.out.println(agent.getEntityName() + " processing outbox...");
        if (messageQueue.size() > 0) {
            Iterator i = messageQueue.iterator();
            while (i.hasNext()) {
                Message message = (Message)i.next();
                String fullyQualifiedName = message.getFullyQualifiedName();
                if (messageTypes.get(fullyQualifiedName) == null) {
                    messageTypes.put(fullyQualifiedName, new Integer(1));
                    send(message);
                } // end if-else
                i.remove();
            } // end while
            Hashtable agentCommunications = ((TerroristAgent)agent).getAgentCommunications();
            Enumeration e = turnCommunications.keys();
            while (e.hasMoreElements()) {
                Agent a = (Agent)e.nextElement();
                if (agentCommunications.get(a) == null) {
                    agentCommunications.put(a, new Integer(1));
/*                    System.out.println("Communication count from " + agent.getEntityName() + " to " +
                                       a.getEntityName() + ": 1");*/
                } else {
                    int currentCount = ((Integer)agentCommunications.get(a)).intValue();
                    currentCount++;
                    agentCommunications.put(a, new Integer(currentCount));
/*                    System.out.println("Communication count from " + agent.getEntityName() + " to " +
                                       a.getEntityName() + ": " + currentCount);*/
                } // end if-else
            } // end while
        } // end if
    } // end process
    
    /**
     * Before a message is actually sent, it has to be checked for benefit >
     * risk, or B > R.  All of the benefits and risks are calculated and summed.
     * If the total benefit (B) is greater than the total risk (R), then the 
     * message is sent.  If the owning agent is the originator of the message,
     * then a new message chain is created with the first element being the
     * pair between the owning agent and the recipient.  If the owning agent is
     * forwarding a message, then an agent pair consisting of the owning agent
     * and the recipient is added to the existing message chain.  When a message
     * is sent, a separate instantiation is made of the message by a copy
     * constructor in the Message class.  Lastly, the turn communications table
     * is updated to reflect who the agent sent the message to.
     * @param message The message to be sent.
     */
    public void send(Message message) {
//        System.out.println("Processing message " + message.getFullyQualifiedName());
        Vector chain = message.getChain();
        if (chain.size() == 0) { // self-originated
//            System.out.println(agent.getEntityName() + " originated message " + message.toString());
            Vector directlyLinkedAgents = ((TerroristAgent)message.getOriginator()).getMentalMap().getDirectlyLinkedAgents();
            Iterator i = directlyLinkedAgents.iterator();
            while (i.hasNext()) {
                benefit = 0;
                risk = 0;
                Agent recipient = (Agent)i.next();
                organizationalBenefit = new OrganizationalBenefit((TerroristAgent)agent);
                influenceBenefit = new InfluenceBenefit((TerroristAgent)message.getOriginator());
                personalBenefit = new PersonalBenefit(message, (TerroristAgent)agent);
                familiarityRisk = new FamiliarityRisk((TerroristAgent)agent, (TerroristAgent)recipient);
                goalSynchronizationRisk = 
                    new GoalSynchronizationRisk(agent.getActiveGoal().getGoalType(), message.getType());
                separationRisk = new SeparationRisk(message);
                benefit = ((Integer)organizationalBenefit.calculate()).intValue() +
                          ((Integer)influenceBenefit.calculate()).intValue() +
                          ((Integer)personalBenefit.calculate()).intValue();
                risk = ((Integer)familiarityRisk.calculate()).intValue() +
                       ((Integer)goalSynchronizationRisk.calculate()).intValue() +
                       ((Integer)separationRisk.calculate()).intValue();
//                System.out.println("Benefit: " + benefit + "\tRisk: " + risk);
                if (benefit > risk) {
                    Inbox recipientsInbox = ((TerroristAgent)recipient).getInbox();
                    Message sendMessage = message.copyMessage();
                    AgentPair firstChain = new AgentPair(message.getOriginator(), recipient);
                    sendMessage.getChain().add(firstChain);
/*                    System.out.println("Adding chain element from " + message.getOriginator().getEntityName() + " to " +
                                       recipient.getEntityName());
                    System.out.println("Chain for this message is: ");
                    Vector sendChain = sendMessage.getChain();
                    Enumeration e2 = sendChain.elements();
                    while (e2.hasMoreElements()) {
                        AgentPair sendPair = (AgentPair)e2.nextElement();
                        System.out.println(sendPair.getFrom().getEntityName() + " to " + sendPair.getTo().getEntityName());
                    } // end while*/
                    recipientsInbox.addMessage(sendMessage);
                    if (turnCommunications.get(recipient) == null) {
                        turnCommunications.put(recipient, new Integer(1));
                    } else {
                        int currentCount = ((Integer)turnCommunications.get(recipient)).intValue();
                        currentCount++;
                        turnCommunications.put(recipient, new Integer(currentCount));
                    } // end if-else
                } else {
                    if (turnCommunications.get(recipient) == null) {
                        turnCommunications.put(recipient, new Integer(1));
                    } else {
                        int currentCount = ((Integer)turnCommunications.get(recipient)).intValue();
                        currentCount++;
                        turnCommunications.put(recipient, new Integer(currentCount));
                    } // end if-else
             }  // end if
            } // end while
        } else { // forwarding
//            System.out.println(agent.getEntityName() + " forwarding message " + message.toString());
            Vector directlyLinkedAgents = ((TerroristAgent)agent).getMentalMap().getDirectlyLinkedAgents();
            Agent forwarder = null;
            Enumeration e = chain.elements();
            while (e.hasMoreElements()) {
                AgentPair chainElement = (AgentPair)e.nextElement();
                if (chainElement.contains(agent)) {
                    if (chainElement.getTo().equals(agent)) {
                        forwarder = chainElement.getFrom();
                    } else if (chainElement.getFrom().equals(agent)) {
//                        System.out.println(agent.getEntityName() + " killing round trip message " + message.toString());
                        return;  // round trip: kill the message
                    } // end if-else
                } // end if
            } // end while
            Iterator i = directlyLinkedAgents.iterator();
            while (i.hasNext()) {
                Agent recipient = (Agent)i.next();
                if (recipient != forwarder) {
                    benefit = 0;
                    risk = 0;
                    organizationalBenefit = new OrganizationalBenefit((TerroristAgent)agent);
                    influenceBenefit = new InfluenceBenefit((TerroristAgent)message.getOriginator());
                    personalBenefit = new PersonalBenefit(message, (TerroristAgent)agent);
                    familiarityRisk = new FamiliarityRisk((TerroristAgent)agent, (TerroristAgent)recipient);
                    goalSynchronizationRisk = 
                        new GoalSynchronizationRisk(agent.getActiveGoal().getGoalType(), message.getType());
                    separationRisk = new SeparationRisk(message);
                    benefit = ((Integer)organizationalBenefit.calculate()).intValue() +
                              ((Integer)influenceBenefit.calculate()).intValue() +
                              ((Integer)personalBenefit.calculate()).intValue();
                    risk = ((Integer)familiarityRisk.calculate()).intValue() +
                           ((Integer)goalSynchronizationRisk.calculate()).intValue() +
                           ((Integer)separationRisk.calculate()).intValue();
//                    System.out.println("Benefit: " + benefit + "\tRisk: " + risk);
                    if (benefit > risk) {
                        Inbox recipientsInbox = ((TerroristAgent)recipient).getInbox();
                        Message sendMessage = message.copyMessage();
                        AgentPair newChain = new AgentPair(agent, recipient);
                        sendMessage.getChain().add(newChain);
//                        System.out.println(agent.getEntityName() + " adding self to " + sendMessage.toString() + "'s chain.");
/*                        System.out.println("Chain for this message is: ");
                        Vector sendChain = sendMessage.getChain();
                        Enumeration e2 = sendChain.elements();
                        while (e2.hasMoreElements()) {
                            AgentPair sendPair = (AgentPair)e2.nextElement();
                            System.out.println(sendPair.getFrom().getEntityName() + " to " + sendPair.getTo().getEntityName());
                        } // end while*/
                        recipientsInbox.addMessage(sendMessage);
                        if (turnCommunications.get(recipient) == null) {
                            turnCommunications.put(recipient, new Integer(1));
                        } else {
                            int currentCount = ((Integer)turnCommunications.get(recipient)).intValue();
                            currentCount++;
                            turnCommunications.put(recipient, new Integer(currentCount));
                        } // end if-else
                    } else {
                        if (turnCommunications.get(recipient) == null) {
                            turnCommunications.put(recipient, new Integer(1));
                        } else {
                            int currentCount = ((Integer)turnCommunications.get(recipient)).intValue();
                            currentCount++;
                            turnCommunications.put(recipient, new Integer(currentCount));
                        } // end if-else
                    } // end if
                } else {
/*                    System.out.println(agent.getEntityName() + " killing ping-pong message " + message.toString() +
                                       " to " + forwarder.getEntityName());*/
                } // end if-else
            } // end while            
        } // end if-else
    } // end send
    
} // end class Outbox
