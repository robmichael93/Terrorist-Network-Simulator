package tns.tickets;
import tns.frames.*;
import mil.navy.nps.relate.*;
import java.util.*;
import simkit.*;

/**
 * Tickets encapsulate the procedural knowledge that an agent has.  This idea 
 * again was developed by John Hiles while at the MOVES Institute.  The concept 
 * of tickets is explained in greater detail in Brian Osborn's dissertation work
 * on the Story Engine [Osborn2002, 68 - 71], so only a brief description of 
 * their functionality is included here.  Tickets incorporate atomic actions an 
 * agent can take, typically in a sequential manner.  Tickets are not limited to 
 * sequential actions; however, those used in the TNS are all sequential in
 * nature.  Tickets are designed to either complete each intended action, or to
 * have those actions interrupted through interaction with other agents.  The
 * TNS incorporates tickets of both types.  Each ticket consists of one or more 
 * frames, each of which is an atomic action the agent can perform.
 * @author  Rob Michael and Zac Staples
 */
public class Ticket extends SimEntityBase implements Frame {
    
    /**
     * The name of the ticket.
     */
    private String name;
    /**
     * The role that owns the ticket.
     */
    private Role role;
    /**
     * The ticket's frames.
     */
    private Vector frames;
    /**
     * Whether or not the ticket has been completed.
     */
    private boolean completed;
    /**
     * Whether or not the ticket can be interrupted.
     */
    private boolean interruptable;
    /**
     * The current frame being executed.
     */
    private int currentFrameIndex;
    /**
     * A flag used to interrupt the ticket.
     */
    private boolean stopExecution;
    
    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     */
    public Ticket(Role role) {
        this.role = role;
        name = new String();
        frames = new Vector();
        completed = false;
        interruptable = false;
        stopExecution = false;
        currentFrameIndex = 0;
    } // end Ticket constructor
    
    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     * @param interruptable Whether or not the ticket is interruptable.
     */
    public Ticket(Role role, boolean interruptable) {
        this(role);
        this.interruptable = interruptable;
    }

    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     * @param name The name of the ticket.
     */
    public Ticket(Role role, String name) {
        this(role);
        this.name = name;
    } // end Ticket constructor
    
    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     * @param name The name of the ticket.
     * @param interruptable Whether or not the ticket is interruptable.
     */
    public Ticket(Role role, String name, boolean interruptable) {
        this(role, interruptable);
        this.name = name;
    } // end Ticket constructor

    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     * @param name The name of the ticket.
     * @param frames The frames for the ticket.
     */
    public Ticket(Role role, String name, Vector frames) {
        this(role, name);
        this.frames = frames;
    } // end Ticket constructor
    
    /** 
     * Creates a new instance of Ticket 
     * @param role The role that owns this ticket.
     * @param name The name of the ticket.
     * @param frames The frames for the ticket.
     * @param interruptable Whether or not the ticket is interruptable.
     */
    public Ticket(Role role, String name, Vector frames, boolean interruptable) {
        this(role, name, interruptable);
        this.frames = frames; 
    } // end Ticket constructor
    
    /**
     * Returns the ticket's frames.
     * @return Vector The ticket's frames.
     */
    public Vector getFrames() { return frames; }
    
    /**
     * Adds a frame to the ticket.
     * @param frame The frame to be added.
     * @return boolean Whether or not the add was successful.
     */
    public boolean addFrame(Frame frame) {
        return frames.add(frame);
    } // end addFrame
    
    /**
     * Returns the name of the ticket.
     * @return String The name of the ticket.
     */
    public String getName() { return name; }
    
    /**
     * This version of the execute method is used by Simkit to schedule the
     * ticket to execute some number of turns later.
     * If the ticket is not interruptable it will execute each frame in
     * succession.  If the ticket is interruptable, it will do the same
     * provided the interrupt flag is not set during the execution through
     * interaction with another agent.
     */
    public void doExecute() {
        stopExecution = false;
//        System.out.println(this.toString() + " executing...");
        if (!interruptable) { // an uninterruptable ticket
            Enumeration e = frames.elements();
            while (e.hasMoreElements()) {
                Frame f = (Frame)e.nextElement();
                f.execute();
            } // end while
        } else { // an interruptable ticket
            int i = currentFrameIndex;
            while (i < frames.size() && !stopExecution) {
                Frame f = (Frame)frames.elementAt(i);
                f.execute();
                i++;
            } // end while
            if (stopExecution) {
//                System.out.println(name + " halted!");
            } // end if
        } // end if-else
    } // end doExecute
    
    /**
     * If the ticket is not interruptable it will execute each frame in
     * succession.  If the ticket is interruptable, it will do the same
     * provided the interrupt flag is not set during the execution through
     * interaction with another agent.
     */
    public void execute() {
        stopExecution = false;
//        System.out.println(this.toString() + " executing...");
        if (!interruptable) { // an uninterruptable ticket
            Enumeration e = frames.elements();
            while (e.hasMoreElements()) {
                Frame f = (Frame)e.nextElement();
                f.execute();
            } // end while
        } else { // an interruptable ticket
            int i = currentFrameIndex;
            while (i < frames.size() && !stopExecution) {
                Frame f = (Frame)frames.elementAt(i);
                f.execute();
                i++;
            } // end while
            if (stopExecution) {
//                System.out.println(name + " halted!");
            } // end if
        } // end if-else
    } // end execute
    
    /**
     * Returns whether or not the ticket is completed.
     * @return boolean Whether or not the ticket is completed.
     */
    public boolean isCompleted() { return completed; }
    
    /**
     * Returns whether or not the ticket is interruptable.
     * @return boolean Whether or not the ticket is interruptable.
     */
    public boolean isInterruptable() { return interruptable; }
    
    /**
     * Resets the tickets current frame index to zero.
     */
    public void resetTicket() {
        currentFrameIndex = 0;
    } // end resetTicket
    
    /**
     * Sets the interruptable flag so that the ticket will stop executing.
     */
    public void stopExecution() {
        stopExecution = true;
//        System.out.println(this.toString() + "'s stopExecution flag set.");
    } // end stopExecution
    
    /**
     * Sets the current frame for execution to a particular value.
     * @param index The frame the user wants to execute next.
     */
    public void setCurrentFrameIndex(int index) {
        currentFrameIndex = index;
    } // end setCurrentFrameIndex
    
} // end class Ticket
