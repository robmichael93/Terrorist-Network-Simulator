package tns.messages;
import tns.messages.benefits.*;
import tns.messages.risks.*;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.tickets.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * The messaging model in the TNS allows agents to communicate with agents they
 * are not directly linked to in the network, which is critical given that the 
 * connector communication paradigm only works within the confines of directly 
 * linked agents and in the use of the sensory model.  In the messaging model, 
 * messages are placed in an outbox, evaluated to whether or not they should be 
 * sent to a particular person, delivered, placed in other agents' inboxes, and 
 * then when an agent checks his inbox, he evaluates whether or not those 
 * messages should be answered.  Each message includes the originator (so that 
 * the person who answers it knows who sent it), the intended target, stated in
 * terms of the role that should answer the message, the type of ticket to
 * execute when the message is answered, and an identifier for the type of
 * message.
 * @author  Rob Michael and Zac Staples
 */
public abstract class Message {
    
    /**
     * An identifier for find person messages.
     */
    public static final int FIND_PERSON_MESSAGE = 0;
    /**
     * An identifier for get resource messages.
     */
    public static final int GET_RESOURCE_MESSAGE = 1;
    /**
     * An identifier for seek leader messages.
     */
    public static final int SEEK_LEADER_MESSAGE = 2;
    /**
     * An identifier for seek operator messages.
     */
    public static final int SEEK_OPERATOR_MESSAGE = 2;
    
    /**
     * The target role for the message.
     */
    private String targetRole;
    /**
     * The message's forwarding chain.
     */
    private Vector chain;
    /**
     * The sender of the message.
     */
    private Agent originator;
    /**
     * The name of the message's content to be executed when the message is
     * answered.  The content is the name of a ticket.
     */
    private String content;
    /**
     * The identifier for the message as defined above.
     */
    private int type;
    /**
     * The fully qualified name of the message.  A FQN consists of the message's
     * class name + "." + the target role + "." + the originator's name.
     * i.e. FindPersonMessage.TrainerRole.TA9
     */
    private String fullyQualifiedName;
    
    /**
     * The organizational benefit for processing a message.
     */
    protected OrganizationalBenefit organizationalBenefit = null;
    /**
     * The influence benefit for processsing a message.
     */
    protected InfluenceBenefit influenceBenefit = null;
    /**
     * The goal synchronization risk against processing a message.
     */
    protected GoalSynchronizationRisk goalSynchronizationRisk = null;
    /**
     * The separation risk against processing a message.
     */
    protected SeparationRisk separationRisk = null;
    /**
     * Used to store the total benefit for a message.
     */
    protected int benefit = 0;
    /**
     * Used to store the totaol risk for a message.
     */
    protected int risk = 0;

    /** 
     * Creates a new instance of Message 
     * @param originator The sender of the message.
     * @param targetRole The target role for the message.
     * @param content The name of the ticket to be executed when the message
     * is answered.
     * @param type The identifier of the message.  See types above.
     */
    public Message(Agent originator, String targetRole, String content, int type) {
        this(new Vector(), originator, targetRole, content, type);
    } // end Message constructor
    
    /**
     * Creates a new instance of Message
     * @param chain The forwarding chain for this message.
     * @param originator The sender of the message.
     * @param targetRole The target role for the message.
     * @param content The name of the ticket to be executed when the message
     * is answered.
     * @param type The identifier of the message.  See types above.
     */
    public Message(Vector chain, Agent originator, String targetRole, String content, int type) {
        this.originator = originator;
        this.chain = chain;
        this.targetRole = targetRole;
        this.content = content;
        this.type = type;
        fullyQualifiedName = this.getClass().getName() + "." + targetRole + "." + originator.getEntityName();
    } // end Message constructor
    
    /** 
     * Creates a new instance of Message 
     * @param m The message to be used in creating this copy.
     */
    public Message(Message m) {
        originator = m.getOriginator();
        targetRole = m.getTargetRole();
        content = m.getContent();
        fullyQualifiedName = m.getFullyQualifiedName();
        chain = new Vector();
        Vector oldChain = m.getChain();
        Enumeration e = oldChain.elements();
        while (e.hasMoreElements()) {
            AgentPair agentPair = (AgentPair)e.nextElement();
            chain.add(new AgentPair(agentPair.getFrom(), agentPair.getTo()));
        } // end while
        type = m.getType();
    } // end Message copy constructor
    
    /**
     * Returns the message's forwarding chain.
     * @return Vector The message's forwarding chain.
     */
    public Vector getChain() { return chain; }
    
    /**
     * Returns the message's sender.
     * @return Agent The message's sender.
     */
    public Agent getOriginator() { return originator; }
    
    /**
     * Returns the message's target role.
     * @return String The message's target role.
     */
    public String getTargetRole() { return targetRole; }
    
    /**
     * Returns the name of the ticket to be executed when the message is 
     * answered.
     * @return String The name of the ticket to be executed when the message
     * is answered.
     */
    public String getContent() { return content; }
    
    /**
     * Returns the message's fully qualified name.
     * @return String The message's fully qualified name.
     */
    public String getFullyQualifiedName() { return fullyQualifiedName; }
    
    /**
     * Returns the message's type.
     * @return int The message's type.
     */
    public int getType() { return type; }
    
    /**
     * This method is instantiated by any descendents to process the message.
     * @param agent The agent processing the message.
     */
    public abstract void process(Agent agent);
    
    /**
     * This method is instantiated by any descendents to replicate the message
     * for sending on.
     * @return Message The deep copy of the original message.
     */
    public abstract Message copyMessage();
    
    /**
     * Creates the Ticket that corresponds to the passed in class name and 
     * associated arguments.
     *
     * @param className The className of the Ticket to be created.
     * @param role The target role for the message.
     * @param originator The sender of the message.
     * @param message The message associated with this ticket.
     * @return Ticket The created Ticket.
     */
    protected Ticket createTicket( String className, Role role, Agent originator, Message message) {
//        System.out.println("Attempting to create class " + className);
        Class cl = null;
        try {
           cl = Class.forName( "tns.tickets." + className );
        } catch ( ClassNotFoundException e ) {
           System.err.println( "Can't find your darned class." );
           System.exit( 0 );
        } // end try-catch

        Constructor c = null;
        Object o = null;
        try {
            c = cl.getConstructor(new Class[] {Role.class, Agent.class, Message.class});
            o = c.newInstance(new Object[] {role, originator, message});
        } catch (NoSuchMethodException e) {
            System.err.println("Can't find you class' constructor.");
            System.exit(0);
        } catch (SecurityException e) {
            System.err.println("Security Exception. Can't access your class.");
            System.exit(0);
        } catch ( InstantiationException e ) {
           System.err.println( "Can't make your darned class." );
           System.exit( 0 );
        } catch ( IllegalAccessException e ) {
           System.err.println( "Can't access your darned class." );
           System.exit( 0 );
        } catch (IllegalArgumentException e) {
            System.err.println("Can't create your class.  Illegal arguments.");
            System.exit(0);
        } catch (InvocationTargetException e) {
            System.err.println("Can't create your class.  Contructor threw an exception.");
            System.exit(0);
        } // end try-catch

        return (Ticket)o;

    } // end createTicket
    
} // end abstract class Message
