package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.goals.*;
import tns.connectors.*;
import java.util.*;

/**
 * This ticket allows the leader to convert his most experienced operator into a
 * specialist to satisfy an outstanding resource requirement.  The leader finds 
 * the most experienced operator and then extends a connector that the operator 
 * can hear, resulting in the operator changing roles to the desired specialist.  
 * The ticket also resets the stuck counter for requesting a resource.
 * @author  Rob Michael
 */
public class ConvertOperatorTicket extends Ticket {
    
    /** 
     * Creates a new instance of ConvertOperatorTicket 
     * @param role The role that owns this ticket.
     */
    public ConvertOperatorTicket(Role role) {
        super(role, "ConvertOperatorTicket");
        
        String newRole = null;
        Mission mission = ((LeaderRole)role).getMission();
        Vector operators = mission.getOperators();
        int highestExperience = 0;
        int experience = 0;
        Agent preferredAgent = null;
        Iterator i = operators.iterator();
        while (i.hasNext()) {
            Agent agent = (Agent)i.next();
            TerroristAgentPersonality personality = (TerroristAgentPersonality)((TerroristAgent)agent).getPersonality();
            experience = personality.getExperience();
            if (experience > highestExperience) {
                highestExperience = experience;
                preferredAgent = agent;
            } // end if
        } // end while
        Goal leadersLastActiveGoal = ((TNSRole)role).getAgent().getPastActiveGoal();
        if (leadersLastActiveGoal instanceof RequestArmsGoal) {
            newRole = "ArmsDealerRole";
        } else if (leadersLastActiveGoal instanceof RequestFinancesGoal) {
            newRole = "FinancierRole";
        } else if (leadersLastActiveGoal instanceof RequestLogisticsGoal) {
            newRole = "LogisticianRole";
        } // end if-else
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof ConvertOperatorConnector) {
                if (preferredAgent != null) {
                    ((ConvertOperatorConnector)c).associateAgent(preferredAgent);
                } // end if
                if (newRole != null) {
                    ((ConvertOperatorConnector)c).associateNewRole(newRole);
                } // end if
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
        addFrame(new ResetStuckCounterAction(role));
    } // end ConvertOperatorTicket constructor
    
} // end class ConvertOperatorTicket
