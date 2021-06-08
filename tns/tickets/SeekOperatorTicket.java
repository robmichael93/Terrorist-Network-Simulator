package tns.tickets;
import mil.navy.nps.relate.*;
import tns.messages.*;
import tns.actions.*;
import tns.agents.*;
import tns.goals.*;
import tns.roles.*;
import java.util.*;

/**
 * This ticket works like the SeekLeaderTicket, but it is sent from Leaders to
 * Operators. <B> Not currently in use as it was experimental.</B>
 * @author  Rob Michael and Zac Staples
 */
public class SeekOperatorTicket extends Ticket {
    
    /** 
     * Creates a new instance of SeekOperatorTicket 
     * @param role The role that owns this ticket.
     * @param to The orginator of the message.
     * @param message The message that owns this ticket.
     */
    public SeekOperatorTicket(Role role, Agent to, Message message) {
        super(role, "SeekOperatorTicket");
        Agent from = (Agent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction((TerroristAgent)from, (TerroristAgent)to));
        addFrame(new AddMessageChainToMentalMapAction(from, to, message));
        Role leader = null;
        Vector roles = to.getRoleVector();
        Iterator i = roles.iterator();
        while (i.hasNext()) {
            Role r = (Role)i.next();
            if (r instanceof LeaderRole) {
                leader = r;
            } // end if
        } // end while
        addFrame(new ReceiveTargetAction(leader, role));
        addFrame(new UpdateChainHistoryValuesAction(message));
    } // end SeekOperatorTicket constructor
    
} // end class SeekOperatorTicket
