package tns.actions;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import tns.goals.*;
import tns.relationships.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action works much like the recruit verification action, except it adds a
 * point to the recruit’s experience value.  When the value exceeds a threshold 
 * set by the recruit training relationship, the recruit changes roles to an 
 * operator.
 * @author  Rob Michael and Zac Staples
 */
public class TrainRecruitsAction implements Frame {
    
    /**
     * The Recruit being trained.
     */
    private Role role;
    
    /** 
     * Creates a new instance of TrainRecruitsAction 
     * @param role The Recruit being trained.
     */
    public TrainRecruitsAction(Role role) {
        this.role = role;
    } // end TrainRecruitsAction constructor
    
    /**
     * The execute method adds a point to the Recruit's experience value.  If
     * the value exceeds a threshold set by the RecruitTrainingRelationship, 
     * then a frame for marking the BecomeOperatorGoal complete is added along
     * with a frame to change the Recruit's role to an Operator.
     */
    public void execute() {
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        ((TerroristAgentPersonality)ta.getPersonality()).updateExperience(1);
//        System.out.println(ta.getEntityName() + "'s Experience increased by 1.");
        // Get the ProveAllegiance threshold from the RecruitTrainingRelationship object
        int threshold = 0;
        Hashtable relationshipTable = ta.getRelationshipTable();
        Vector relationships = (Vector)relationshipTable.get("RecruitTrainingRelationship");
        if (relationships instanceof Vector &&
            ((Vector)relationships).size() > 0 &&
            ((Vector)relationships).firstElement() instanceof RecruitTrainingRelationship) {
            threshold = ((RecruitTrainingRelationship)((Vector)relationships).firstElement()).getProveAllegianceThreshold();
        } // end if
        // if the new Allegiance value is above the ProveAllegiance
        // threshold, then mark the goal complete
        if (((TerroristAgentPersonality)ta.getPersonality()).getExperience() >= threshold) {
            Vector tickets = ((TNSRole)role).getTickets();
            Enumeration e = tickets.elements();
            while (e.hasMoreElements()) {
                Ticket t = (Ticket)e.nextElement();
                if (t instanceof BecomeOperatorTicket) {
                    t.addFrame(new MarkGoalCompleteAction((TNSGoal)ta.getGoalList().get("BecomeOperatorGoal")));
                    t.addFrame(new ChangeRoleAction(role, "OperatorRole"));
                } // end if
            } // end while
        } // end if
    } // end execute
    
} // end class TrainRecruitsAction
