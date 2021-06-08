package tns.messages;
import mil.navy.nps.relate.*;
import tns.tickets.*;
import tns.agents.*;
import tns.goals.*;
import tns.roles.*;
import java.util.*;
import tns.messages.risks.*;
import tns.messages.benefits.*;

/**
 * Operators use this message to find leaders who need manpower for their 
 * mission.  Operators use this type of message when the trainer they just 
 * trained with does not know a leader directly. 
 * @author  Rob Michael and Zac Staples
 */
public class SeekLeaderMessage extends Message {
    
    /**
     * The target role of the message.
     */
    private Role role;
    /**
     * The goal completion benefit for processing a message.
     */
    private GoalCompletionBenefit goalCompletionBenefit = null;

    /** 
     * Creates a new instance of SeekLeaderMessage 
     * @param originator The sender of the message.
     * @param targetRole The target role for the message.
     * @param content The name of the ticket to be executed when the message
     * is answered.
     * @param type The identifier of the message.  See types above.
     */
    public SeekLeaderMessage(Agent originator, String targetRole, String content, int type) {
        super(originator, targetRole, content, type);
    }
    
    /** 
     * Creates a new instance of Message 
     * @param m The message to be used in creating this copy.
     */
    public SeekLeaderMessage(Message m) {
        super(m);
    } // end copy constructor

    /**
     * Creates a deep copy of the message.
     * @return Message The deep copy of the original message.
     */
    public Message copyMessage() {
        SeekLeaderMessage newMessage = new SeekLeaderMessage(this);
//        System.out.println(this.toString() + " copied to message " + newMessage.toString());
        return newMessage;
    }
    
    /**
     * The process method checks if the agent has a role matching the target
     * role.  If the agent does, then B > R is checked.  If benefit does
     * exceed risk, then the content of message is executed.
     * @param agent The agent processing the message.
     */
    public void process(Agent agent) {
        // check to see if the message is meant for this agent/role
        boolean found = false;
        Vector roles = agent.getRoleVector();
        Enumeration e = roles.elements();
        while (e.hasMoreElements() && !found) {
            role = (Role)e.nextElement();
            if (role.getRoleName().equalsIgnoreCase(getTargetRole())) {
                found = true;
            } // end if
        } // end while
        if (!found) { // if not, then place in outbox for forwarding
            ((TerroristAgent)agent).getOutbox().addMessage(this);
        } else { // if message is for this agent/role, next check the benefit
                 // vs. risk before taking action on the message
            benefit = 0;
            risk = 0;
            organizationalBenefit = new OrganizationalBenefit((TerroristAgent)agent);
            influenceBenefit = new InfluenceBenefit((TerroristAgent)getOriginator());
            
            Goal getOperatorsGoal = null;
            Vector goals = role.getGoalListVec();
            Iterator i = goals.iterator();
            while (i.hasNext()) {
                Goal goal = (Goal)i.next();
                if (goal instanceof GetOperatorsGoal) {
                    getOperatorsGoal = goal;
                } // end if
            } // end while
            goalCompletionBenefit = new GoalCompletionBenefit(getOperatorsGoal);
            goalSynchronizationRisk = 
                new GoalSynchronizationRisk(agent.getActiveGoal().getGoalType(), Message.SEEK_LEADER_MESSAGE);
            separationRisk = new SeparationRisk(this);
            benefit = ((Integer)goalCompletionBenefit.calculate()).intValue() +
                      ((Integer)organizationalBenefit.calculate()).intValue() +
                      ((Integer)influenceBenefit.calculate()).intValue();
            risk = ((Integer)goalSynchronizationRisk.calculate()).intValue() +
                   ((Integer)separationRisk.calculate()).intValue();
//            System.out.println("Benefit: " + benefit + "\tRisk: " + risk);
            if (benefit > risk) {
                Mission mission = ((LeaderRole)role).getMission();
                if (mission != null) {
/*                    System.out.println(agent.getEntityName() + " has a mission!");
                    System.out.println("Mission has enough operators: " + ((LeaderRole)role).getMission().hasNeededOperators());*/
                    if (!((LeaderRole)role).getMission().hasNeededOperators()) {
                        Role operatorRole = null;
                        Vector originatorRoles = getOriginator().getRoleVector();
                        Iterator i2 = originatorRoles.iterator();
                        while (i2.hasNext()) {
                            Role r = (Role)i2.next();
                            if (r instanceof OperatorRole) {
                                operatorRole = r;
                            } // end if
                        } // end while
                        if (operatorRole != null && ((OperatorRole)operatorRole).getCurrentTarget() == null) {
//                            System.out.println("Operator " + getOriginator().getEntityName() + " does not have a mission.");
                            Ticket content = createTicket(getContent(), role, getOriginator(), this);
                            content.execute();
                        } // end if
                    } // end if
                } // end if
            } // end if
        } // end if-else
    } // end process
    
} // end class SeekLeaderMessage
