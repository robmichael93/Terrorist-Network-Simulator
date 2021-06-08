package tns.tickets;
import tns.agents.*;
import tns.roles.*;
import tns.actions.*;
import tns.connectors.*;
import tns.frames.*;
import tns.messages.*;
import tns.goals.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This ticket works like a blend between the recruiter’s “send recruits to 
 * training” ticket and the operator’s “join leader on a mission” ticket.  The 
 * trainer checks to see if he knows any leaders directly.  If he does, he finds
 * the leaders who need operators for their missions.  Next he finds the leader 
 * with the highest draw mission and introduces the operator to the leader.  The 
 * operator receives the leader’s target and resets his “stuck” or bored counter 
 * and the leader’s “stuck” counter for finding operators.  The trainer then 
 * receives a point of experience or influence for each operator he sends to a 
 * leader.  If the trainer doesn’t know any leaders at all or any leaders that 
 * need operators, he sends out a find person message looking for a leader role.
 * @author  Rob Michael and Zac Staples
 */
public class SendOperatorsToLeaderTicket extends Ticket {
    
    /** 
     * Creates a new instance of SendOperatorsToLeadersTicket 
     * @param role The role that owns this ticket.
     * @param c The connector extended by the operator.
     */
    public SendOperatorsToLeaderTicket(Role role, Connector c) {
        super(role, "SendOperatorsToLeaderTicket");
        TerroristAgent trainer = (TerroristAgent)((TNSRole)role).getAgent();
        LeaderRole preferredLeader = null;
        Vector leaders = new Vector();
        Vector directlyLinkedAgents = ((TerroristAgent)trainer).getMentalMap().getDirectlyLinkedAgents();
        Enumeration e = directlyLinkedAgents.elements();
        while (e.hasMoreElements()) {
            Agent a = (Agent)e.nextElement();
            Vector roles = a.getRoleVector();
            Enumeration e2 = roles.elements();
            while (e2.hasMoreElements()) {
                Role r = (Role)e2.nextElement();
                if (r instanceof LeaderRole) {
                    leaders.add(a);
                } // end if
            } // end while
        } // end while
//        System.out.println(trainer.getEntityName() + " knows " + leaders.size() + " leaders.");
        if (leaders.size() > 0) {
            Vector eligibleLeaders = new Vector();
            Agent operator = ((TNSRole)((JoinLeaderConnector)c).getRole()).getAgent();
            int highestDraw = 0;
            int draw = 0;
            Iterator i = leaders.iterator();
            while (i.hasNext()) {
                Agent leader = (Agent)i.next();
                Vector leaderRoles = leader.getRoleVector();
                LeaderRole leaderRole = null;
                Iterator i2 = leaderRoles.iterator();
                while (i2.hasNext()) {
                    Role r = (Role)i2.next();
                    if (r instanceof LeaderRole) {
                        leaderRole = (LeaderRole)r;
                    } // end if
                } // end while
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
                addFrame(new MakeDoubleLinkAction((TerroristAgent)preferredLeader.getAgent(), (TerroristAgent)operator));
                Vector goals = ((TNSRole)((JoinLeaderConnector)c).getRole()).getGoalListVec();
                i = goals.iterator();
                while (i.hasNext()) {
                    TNSGoal g = (TNSGoal)i.next();
                    if (g instanceof JoinLeaderGoal) {
                        addFrame(new MarkGoalCompleteAction(g));
                    } // end if
                } // end while
                OperatorRole operatorRole = null;
                Vector roles = operator.getRoleVector();
                i = roles.iterator();
                while (i.hasNext()) {
                    Role r = (Role)i.next();
                    if (r instanceof OperatorRole) {
                        operatorRole = (OperatorRole)r;
                    } // end if
                } // end while
                addFrame(new ResetStuckCounterAction(operatorRole));
                addFrame(new ReceiveTargetAction(preferredLeader, operatorRole));
                Ticket ticket = ((JoinLeaderConnector)c).getTicket();
                addFrame(new InterruptTicketAction(ticket));
                addFrame(new StandardRewardAction(trainer, 1));
                // update the history between the Recruiter and the Trainer
                AgentPair historyPair = new AgentPair(trainer, ((TNSRole)preferredLeader).getAgent());
                trainer.changeHistory(historyPair, 5);
            } else {
                Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
                Object[] arguments = {trainer, "LeaderRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
                addFrame(new PutMessageInOutboxAction(trainer, "FindPersonMessage", argumentTypes, arguments));
            } // end if
        } else {
            Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
            Object[] arguments = {trainer, "LeaderRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
            addFrame(new PutMessageInOutboxAction(trainer, "FindPersonMessage", argumentTypes, arguments));
       } // end if-else
    } // end SendOperatorsToLeaderTicket constructor
    
} // end class SendOperatorsToLeaderTicket
