package tns.tickets;
import mil.navy.nps.relate.*;
import tns.messages.*;
import tns.actions.*;
import tns.agents.*;
import tns.goals.*;
import tns.roles.*;
import java.util.*;

/**
 * Just like the other message tickets, the recipient and the originator create 
 * links to each other, update their mental maps based on the message chain, and
 * update the histories of the agent pairs in the message chain.  The operator 
 * receives the target from the leader, then the leader's stuck counter for 
 * getting operators is reset and lastly the operator's stuck counter (for 
 * changing to a trainer) is reset as well.
 * @author  Rob Michael and Zac Staples
 */
public class SeekLeaderTicket extends Ticket {
    
    /** 
     * Creates a new instance of SeekLeaderTicket 
     * @param role The role that owns this ticket.
     * @param to The orginator of the message.
     * @param message The message that owns this ticket.
     */
    public SeekLeaderTicket(Role role, Agent to, Message message) {
        super(role, "SeekLeaderTicket");
        Agent from = (Agent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction((TerroristAgent)from, (TerroristAgent)to));
        addFrame(new AddMessageChainToMentalMapAction(from, to, message));
        Role operator = null;
        Vector roles = to.getRoleVector();
        Iterator i = roles.iterator();
        while (i.hasNext()) {
            Role r = (Role)i.next();
            if (r instanceof OperatorRole) {
                operator = r;
            } // end if
        } // end while
        addFrame(new ReceiveTargetAction(role, operator));
        addFrame(new ResetGetOperatorsStuckCounterAction(role));
        addFrame(new ResetStuckCounterAction(operator));
        Vector goals = operator.getGoalListVec();
        i = goals.iterator();
        while (i.hasNext()) {
            TNSGoal g = (TNSGoal)i.next();
            if (g instanceof JoinLeaderGoal) {
                addFrame(new MarkGoalCompleteAction(g));
            } // end if
        } // end while
        addFrame(new UpdateChainHistoryValuesAction(message));
    } // end SeekLeaderTicket constructor
    
} // end class SeekLeaderTicket
