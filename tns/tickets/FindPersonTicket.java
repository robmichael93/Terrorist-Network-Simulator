package tns.tickets;
import tns.roles.*;
import tns.agents.*;
import tns.actions.*;
import tns.messages.*;
import mil.navy.nps.relate.*;

/**
 * When this ticket is executed, the recipient and the originator create links 
 * to each other, they update their mental maps with the message chain, and 
 * lastly they update the history value for each of the pairs represented by the
 * message chain.  Adjusting the amount the pairs receive affects the look of 
 * the network topology.  The less the chain is rewarded, the easier 
 * relationships eventually degrade and fall apart, resulting in a more 
 * plausible looking scale-free network.
 * @author  Rob Michael and Zac Staples
 */
public class FindPersonTicket extends Ticket {
    
    /** 
     * Creates a new instance of FindPersonTicket 
     * @param role The role that owns this ticket.
     * @param to The orginator of the message.
     * @param message The message that owns this ticket.
     */
    public FindPersonTicket(Role role, Agent to, Message message) {
        super(role, "FindPersonTicket");
        Agent from = (Agent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction((TerroristAgent)from, (TerroristAgent)to));
        addFrame(new AddMessageChainToMentalMapAction(from, to, message));
        addFrame(new UpdateChainHistoryValuesAction(message));
    } // end FindPersonTicket constructor
    
} // end class FindPersonTicket
