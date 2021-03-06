package tns.messages;
import mil.navy.nps.relate.*;
import tns.tickets.*;
import tns.agents.*;
import java.util.*;
import tns.messages.risks.*;
import tns.messages.benefits.*;

/**
 * This type of message provides an agent the capability to find an agent with a 
 * particular role if the agent does not know one directly.  This message allows 
 * the originator to create a direct link to an agent with the desired role.
 * @author  Rob Michael and Zac Staples
 */
public class FindPersonMessage extends Message {
    
    /**
     * The target role of the message.
     */
    private Role role;

    /** 
     * Creates a new instance of FindPersonMessage 
     * @param originator The sender of the message.
     * @param targetRole The target role for the message.
     * @param content The name of the ticket to be executed when the message
     * is answered.
     * @param type The identifier of the message.  See types above.
     */
    public FindPersonMessage(Agent originator, String targetRole, String content, int type) {
        super(originator, targetRole, content, type);
    } // end FindPersonMessage constructor
    
    /** 
     * Creates a new instance of Message 
     * @param m The message to be used in creating this copy.
     */
    public FindPersonMessage(Message m) {
        super(m);
    } // end FindPersonMessage copy constructor
    
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
            goalSynchronizationRisk = 
                new GoalSynchronizationRisk(agent.getActiveGoal().getGoalType(), Message.FIND_PERSON_MESSAGE);
            separationRisk = new SeparationRisk(this);
            benefit = ((Integer)organizationalBenefit.calculate()).intValue() +
                      ((Integer)influenceBenefit.calculate()).intValue();
            risk = ((Integer)goalSynchronizationRisk.calculate()).intValue() +
                   ((Integer)separationRisk.calculate()).intValue();
//            System.out.println("Benefit: " + benefit + "\tRisk: " + risk);
            if (benefit > risk) {
                Ticket content = createTicket(getContent(), role, getOriginator(), this);
                content.execute();
            } // end if
        } // end if-else
    } // end process

    /**
     * Creates a deep copy of the message.
     * @return Message The deep copy of the original message.
     */
    public Message copyMessage() {
        FindPersonMessage newMessage = new FindPersonMessage(this);
//        System.out.println(this.toString() + " copied to message " + newMessage.toString());
        return newMessage;
    } // end copyMessage
    
} // end class FindPersonMessage
