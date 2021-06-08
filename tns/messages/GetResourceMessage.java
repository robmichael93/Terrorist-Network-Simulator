package tns.messages;
import mil.navy.nps.relate.*;
import tns.tickets.*;
import tns.agents.*;
import java.util.*;
import tns.messages.risks.*;
import tns.messages.benefits.*;

/**
 * This message allows a leader to seek out resource providers if he does not 
 * know any, or to try to persuade a known specialist that isn’t responding to 
 * the leader’s attempt to use a connector to reach the specialist because the 
 * specialist is off producing a resource.
 * @author  Rob Michael and Zac Staples
 */
public class GetResourceMessage extends Message {
    
    /**
     * The target role of the message.
     */
    private Role role;
    /**
     * The draw benefit for processing a message.
     */
    private DrawBenefit drawBenefit = null;
    /**
     * The status risk against processing a message.
     */
    private StatusRisk statusRisk = null;
    
    /** 
     * Creates a new instance of GetResourceMessage 
     * @param originator The sender of the message.
     * @param targetRole The target role for the message.
     * @param content The name of the ticket to be executed when the message
     * is answered.
     * @param type The identifier of the message.  See types above.
     */
    public GetResourceMessage(Agent originator, String targetRole, String content, int type) {
        super(originator, targetRole, content, type);
    } // end GetResourceMessage constructor
    
    /** 
     * Creates a new instance of Message 
     * @param m The message to be used in creating this copy.
     */
    public GetResourceMessage(Message m) {
        super(m);
    } // end GetResourceMessage copy constructor
    
    /**
     * Creates a deep copy of the message.
     * @return Message The deep copy of the original message.
     */
    public Message copyMessage() {
        GetResourceMessage newMessage = new GetResourceMessage(this);
//        System.out.println(this.toString() + " copied to message " + newMessage.toString());
        return newMessage;
    } // end copyMessage
    
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
            drawBenefit = new DrawBenefit((TerroristAgent)getOriginator());
            statusRisk = new StatusRisk((TerroristAgent)agent);
            goalSynchronizationRisk = 
                new GoalSynchronizationRisk(agent.getActiveGoal().getGoalType(), Message.GET_RESOURCE_MESSAGE);
            separationRisk = new SeparationRisk(this);
            benefit = ((Integer)drawBenefit.calculate()).intValue() +
                      ((Integer)organizationalBenefit.calculate()).intValue() +
                      ((Integer)influenceBenefit.calculate()).intValue();
            risk = ((Integer)statusRisk.calculate()).intValue() +
                   ((Integer)goalSynchronizationRisk.calculate()).intValue() +
                   ((Integer)separationRisk.calculate()).intValue();
//            System.out.println("Benefit: " + benefit + "\tRisk: " + risk);
            if (benefit > risk) {
                Ticket content = createTicket(getContent(), role, getOriginator(), this);
                content.execute();
            } // end if
        } // end if-else
    } // end process
    
} // end class GetResourceMessage
