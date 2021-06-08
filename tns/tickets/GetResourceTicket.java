package tns.tickets;
import tns.roles.*;
import tns.agents.*;
import tns.actions.*;
import tns.messages.*;
import mil.navy.nps.relate.*;

/**
 * When this ticket is executed, the recipient of the message and the originator 
 * of the message create links to each other, update their mental maps based on 
 * the message chain, and the history values of the message pairs in the message 
 * chain are updated.  This ticket also sets a resource requested latch for the 
 * specialist, which is used in evaluating the provide resources goal as
 * mentioned above.  This ticket also calculates the weighting applied to the 
 * specialist�s provide resource goal.
 * @author  Rob Michael and Zac Staples
 */
public class GetResourceTicket extends Ticket {
    
    /** 
     * Creates a new instance of GetResourceTicket 
     * @param role The role that owns this ticket.
     * @param to The orginator of the message.
     * @param message The message that owns this ticket.
     */
    public GetResourceTicket(Role role, Agent to, Message message) {
        super(role, "GetResourceTicket");
        Agent from = (Agent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction((TerroristAgent)from, (TerroristAgent)to));
        addFrame(new AddMessageChainToMentalMapAction(from, to, message));
        addFrame(new SetRequestedLatchAction(role));
        addFrame(new IncrementProvideGoalWeightAction(role, to));
        addFrame(new UpdateChainHistoryValuesAction(message));
    } // end GetResourceTicket constructor
    
} // end class GetResourceTicket
