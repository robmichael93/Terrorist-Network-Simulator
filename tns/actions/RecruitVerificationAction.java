package tns.actions;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import tns.goals.*;
import tns.relationships.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action adds a point to the recruit’s allegiance value.  If the recruit’s 
 * allegiance value then exceeds a threshold set by the recruit training 
 * relationship, then the recruit’s “prove allegiance” goal is marked complete.
 * @author  Rob Michael and Zac Staples
 */
public class RecruitVerificationAction implements Frame {
    
    /**
     * The Recuit being verified.
     */
    private Role role;
    
    /** 
     * Creates a new instance of RecruitVerificationAction 
     * @param role The Recruit being verified.
     */
    public RecruitVerificationAction(Role role) {
        this.role = role;
    } // end RecruitVerificationAction constructor
    
    /**
     * The execute mission adds a point of allegiance to the Recruit's allegiance
     * value, then it checks to see if the new value exceeds a threshold set
     * by the RecruitTrainingRelationship.  If the threshold is exceeded, then
     * the Recruit's ProveAllegianceGoal is marked complete.
     */
    public void execute() {
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        ((TerroristAgentPersonality)ta.getPersonality()).updateAllegiance(1);
//        System.out.println(ta.getEntityName() + "'s Allegiance increased by 1.");
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
        if (((TerroristAgentPersonality)ta.getPersonality()).getAllegiance() >= threshold) {
            ((TNSGoal)ta.getGoalList().get("ProveAllegianceGoal")).markGoalComplete();
        } // end if
    } // end execute
    
} // end class RecruitVerificationAction
