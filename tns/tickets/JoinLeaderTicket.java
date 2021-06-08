package tns.tickets;
import mil.navy.nps.relate.*;
import tns.roles.*;
import tns.agents.*;
import tns.connectors.*;
import tns.goals.*;
import tns.actions.*;
import tns.messages.*;
import java.util.*;

/**
 *  f the operator knows at least one leader, he first picks out the leaders
 * that need operators on their missions and then he looks at the draw value for 
 * their missions.  The operator joins the leader with the highest draw mission,
 * receiving the leader’s target and resetting a “stuck counter” that determines
 * how bored an operator is (and how close to advancing to a trainer) and how 
 * impatient a leader is for finding recruiters and trainers.  If the operator 
 * does not know a leader directly, he will extend a connector the trainer can 
 * hear and he will put a seek leader message in his outbox unless a trainer 
 * hears his connector and therefore connects with the operator.  If the trainer 
 * does connect with the operator, the trainer will interrupt this ticket, thus 
 * preventing the message from being placed in the outbox.  The reason for this
 * modeling decision was to work with how connectors and the listener model 
 * operate.  The way the model operates is that the extender of a stimulator 
 * connector does not know if a receptor connector made a connection unless the 
 * agent on the receptor end changes some state variable in the agent on the 
 * stimulator end.  Therefore, for the ease of encoding the desired behavior, 
 * it was easier to simply put a frame in the ticket to put a message in the 
 * outbox in the case that the connector was not heard and the ticket therefore 
 * not interrupted. 
 * @author  Rob Michael and Zac Staples
 */
public class JoinLeaderTicket extends Ticket {
    
    /** 
     * Creates a new instance of JoinLeaderTicket 
     * @param role The role that owns this ticket.
     */
    public JoinLeaderTicket(Role role) {
        super(role, "JoinLeaderTicket", true);
        boolean knowALeader = false;
        Vector leaders = new Vector();
        Agent agent = ((TNSRole)role).getAgent();
        Vector directlyLinkedAgents = ((TerroristAgent)agent).getMentalMap().getDirectlyLinkedAgents();
        Iterator i = directlyLinkedAgents.iterator();
        while (i.hasNext()) {
            Agent a = (Agent)i.next();
            Vector roles = a.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role r = (Role)i2.next();
                if (r instanceof LeaderRole) {
                    leaders.add(r);
                    knowALeader = true;
//                    System.out.println(agent.getEntityName() + " knows a leader.");
                } // end if
            } // end while
        } // end while
        if (!knowALeader) {
            addFrame(new IncrementStuckCounterAction(role));
            Vector connectors = ((TNSRole)role).getConnectors();
            Iterator i2 = connectors.iterator();
            while (i2.hasNext()) {
                Connector c = (Connector)i2.next();
                if (c instanceof JoinLeaderConnector) {
                    ((JoinLeaderConnector)c).associateTicket(this);
                    addFrame(new ExtendRetractConnectorAction(c));
                } // end if
            } // end while
            Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
            Object[] arguments = {((TNSRole)role).getAgent(), "LeaderRole", "SeekLeaderTicket", new Integer(Message.SEEK_LEADER_MESSAGE)};
            addFrame(new PutMessageInOutboxAction(((TNSRole)role).getAgent(), "SeekLeaderMessage", argumentTypes, arguments));
        } else {
            LeaderRole preferredLeader = null;
            Vector eligibleLeaders = new Vector();
            int highestDraw = 0;
            int draw = 0;
            
            i = leaders.iterator();
            while (i.hasNext()) {
                LeaderRole leaderRole = (LeaderRole)i.next();
                if (leaderRole.getMission() != null && !leaderRole.getMission().hasNeededOperators()) {
                    eligibleLeaders.add(leaderRole);
                } // end if
            } // end while
            if (eligibleLeaders.size() > 0) {
                i = eligibleLeaders.iterator();
                while (i.hasNext()) {
                    LeaderRole leader = (LeaderRole)i.next();
                    draw = leader.getMission().getTarget().getDraw();
                    if (draw > highestDraw) {
                        highestDraw = draw;
                        preferredLeader = leader;
                    } // end if
                } // end while
                addFrame(new ReceiveTargetAction(preferredLeader, role));
                addFrame(new ResetGetOperatorsStuckCounterAction(preferredLeader));
                addFrame(new ResetStuckCounterAction(role));
                Vector goals = role.getGoalListVec();
                i = goals.iterator();
                while (i.hasNext()) {
                    TNSGoal g = (TNSGoal)i.next();
                    if (g instanceof JoinLeaderGoal) {
                        addFrame(new MarkGoalCompleteAction(g));
                    } // end if
                } // end while
            } else {
                Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
                Object[] arguments = {((TNSRole)role).getAgent(), "LeaderRole", "SeekLeaderTicket", new Integer(Message.SEEK_LEADER_MESSAGE)};
                addFrame(new PutMessageInOutboxAction(((TNSRole)role).getAgent(), "SeekLeaderMessage", argumentTypes, arguments));
            } // end if-else
        } // end if-else
    } // end JoinLeaderTicket constructor
    
} // end class JoinLeaderTicket
