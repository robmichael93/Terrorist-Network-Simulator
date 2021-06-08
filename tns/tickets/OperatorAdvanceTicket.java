package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;
import tns.roles.*;
import tns.agents.*;
import java.util.*;

/**
 * This ticket removes the operator from a mission if he is in one and then 
 * changes the operator�s role to that of a trainer on one caveat.  The operator 
 * will only become a trainer if the system can handle having another trainer.  
 * The ratio between the number of operators in the system and the number of 
 * trainers in the system is computed and if that ratio exceeds a particular 
 * value then the operator can become a trainer.  The idea behind this modeling 
 * decision was to prevent the system from becoming saturated with trainers.
 * @author  Rob Michael and Zac Staples
 */
public class OperatorAdvanceTicket extends Ticket {
    
    /** 
     * Creates a new instance of OperatorAdvanceTicket 
     * @param role The role that owns this ticket.
     * @param newRole The name of the role to change to.
     */
    public OperatorAdvanceTicket(Role role, String newRole) {
        super(role, "OperatorAdvanceTicket");
/*        boolean foundLeader = false;
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
        } // end while*/
        
//        if (foundLeader) {
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + ((TNSRole)role).getAgent().getEntityName() + " knows a leader...");
            int trainers = 0;
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
                    if (agentRole instanceof TrainerRole) {
                        trainers++;
                    } else if (agentRole instanceof OperatorRole) {
                        operators++;
                    } // end if-else
                } // end while
            } // end while
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tTrainers: " + trainers);
//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tOperators: " + operators);
            if (((double)operators / (double)trainers) > 5.0 ) {
                addFrame(new ChangeRoleAction(role, "TrainerRole"));
            } // end if
/*        } else {
            addFrame(new ChangeRoleAction(role, "TrainerRole"));
        } // end if-else*/
    } // end OperatorAdvanceTicket constructor
    
} // end class OperatorAdvanceTicket
