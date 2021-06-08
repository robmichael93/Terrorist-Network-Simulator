package tns.tickets;
import tns.agents.*;
import tns.roles.*;
import tns.actions.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This ticket allows a specialist to potentially advance to a leader role.  If 
 * the specialist is not directly connected to a leader (which means most likely
 * the specialist has become disconnected from the network, or disavowed), then 
 * the specialist changes to a leader.  If the specialist is still connected to 
 * a leader, then a similar check occurs as was described for operators 
 * advancing to trainers.  If the ratio of operators to leaders in the system
 * exceeds a certain threshold, then the specialist will advance to a leader.  
 * Again, the reason for this modeling decision was to avoid saturating the 
 * system with leaders, who would subsequently require operators and operators 
 * are a scarcer resource than those the specialists provide, as will be 
 * discussed further below.
 * @author  Rob Michael and Zac Staples
 */
public class AdvanceTicket extends Ticket {
    
    /** 
     * Creates a new instance of AdvanceTicket 
     * @param role The role that owns this ticket.
     */
    public AdvanceTicket(Role role) {
        super(role, "AdvanceTicket");
        
        boolean foundLeader = false;
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
                    foundLeader = true;
                } // end if
            } // end while
        } // end while
        
        if (foundLeader) {
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + ((TNSRole)role).getAgent().getEntityName() + " knows a leader...");
            int leaders = 0;
            int operators = 0;
            Vector simulationAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getSimulationAgents();
            Iterator i2 = simulationAgents.iterator();
            while (i2.hasNext()) {
                Agent a = (Agent)i2.next();
                // take each Agent and find the roles
                Vector memberRoles = a.getRoleVector();
                Iterator i3 = memberRoles.iterator();
                while (i3.hasNext()) {
                    Role agentRole = (Role)i3.next();
                    // look for Recruiter roles & add to temp container
                    if (agentRole instanceof LeaderRole) {
                        leaders++;
                    } else if (agentRole instanceof OperatorRole) {
                        operators++;
                    } // end if-else
                } // end while
            } // end while
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tLeaders: " + leaders);
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tOperators: " + operators);
            if (((double)operators / (double)leaders) > 5.0 ) {
                addFrame(new ChangeRoleAction(role, "LeaderRole"));
            } // end if
        } else {
            addFrame(new ChangeRoleAction(role, "LeaderRole"));
        } // end if-else
    } // end AdvanceTicket constructor
    
} // end class AdvanceTicket
