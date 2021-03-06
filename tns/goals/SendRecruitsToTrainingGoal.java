package tns.goals;
import tns.roles.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * The last duty a recruiter performs is to put recruits in contact with a 
 * trainer so that they may go off to a training camp and train up to become 
 * operators.  This goal gives the recruiter the behavior of either introducing
 * recruits to a known trainer, or putting out his feelers throughout the 
 * organization to find a trainer willing to take the new recruits.  When 
 * sending the recruits to a known trainer, the recruiter exhibits the behavior 
 * of passing the recruits to the most influential trainer the recruiter knows 
 * at the time, helping to induce the rich-get-richer phenomenon in the system.
 * @author  Rob Michael and Zac Staples
 */
public class SendRecruitsToTrainingGoal implements TNSGoal {
    
    /**
     * The role that owns this goal.
     */
    private Role role;
    /**
     * The class Name of the goal.
     */
    private String goalName;
    /**
     * The type of goal as defined in the TNSGoal interface.
     */
    private int goalType;
    /**
     * The name of relationship this role belongs to.
     */
    private String relationshipName;
    /**
     * The current weight of the goal.
     */
    private int weight;
    /**
     * Whether or not the goal has been completed.
     */
    private boolean completed;

    /** 
     * Creates a new instance of SendRecruitsToTrainingGoal 
     * @param role The role that owns this goal.
     */
    public SendRecruitsToTrainingGoal(Role role) {
        this.role = role;
        goalName = new String("SendRecruitsToTrainingGoal");
//        System.out.println(goalName + " created.");
        relationshipName = new String("RecruitTrainingRelationship");
        weight = 0;
        completed = false;
        goalType = TNSGoal.ORGANIZE;
    }
    
    /** Adds the passed in Rule to the ruleList Vector. <B>Not used in the TNS.</B>
     * @param pRule The Rule that is to be added
     */
    public void addRule(Rule pRule) {}
    
    /** Assigns credit to itself (i.e. updates goal attainment weight )and the
     * lastRule by receiving the new PerceivedEnvironment to the Goal and letting
     * the Goal determine the credit assignment to itself (goal attainment) and
     * all of the Rules associated with that Goal, both active and inactive.
     * Assigns the Rule with the highest weight to the activeRule.
     * @param pPE The agent's perceived environment.
     *
     */
    public void assignCredit(SensedEnvironment pPE) {
        // Points are given to this goal for each Recruit that the Agent
        // directly knows and has completed its ProveAllegiance goal & has not
        // completed its GetTrained goal
        int recruits = 0;
        Vector directlyLinkedAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getMentalMap().getDirectlyLinkedAgents();
        Enumeration e = directlyLinkedAgents.elements();
        while (e.hasMoreElements()) {
            TerroristAgent ta = (TerroristAgent)e.nextElement();
            Vector roles = ta.getRoleVector();
            Enumeration e2 = roles.elements();
            while (e2.hasMoreElements()) {
                Role role = (Role)e2.nextElement();
                if (role instanceof RecruitRole) {
                    boolean proveAllegianceCompleted = false;
                    boolean getTrainedCompleted = false;
                    Vector goals = role.getGoalListVec();
                    Enumeration e3 = goals.elements();
                    while (e3.hasMoreElements()) {
                        Goal goal = (Goal)e3.nextElement();
                        if (goal instanceof ProveAllegianceGoal && 
                            ((TNSGoal)goal).isCompleted()) {
                            proveAllegianceCompleted = true;
                        } else if (goal instanceof GetTrainedGoal &&
                                   ((TNSGoal)goal).isCompleted()) {
                            getTrainedCompleted = true;
                        } // end if-else
                    } // end while
                    if (proveAllegianceCompleted && !getTrainedCompleted) {
                        recruits++;
                    } // end if
                } // end if
            } // end while
        } // end while
        weight = recruits * 10;
//        System.out.println(goalName + " weight: " + weight);
    }
    
    /** Gets the Goal's active rule. <B>Not used in the TNS.</B>
     * @return the Goals active rule
     */
    public Rule getActiveRule() { return null; }
    
    /** Gets the Goal name
     * @return a String of the goals name (should be the goal class name)
     *
     */
    public String getGoalName() { return goalName; }
    
    /** Gets the Goal type
     * @return an Int representing the goal type
     *
     */
    public int getGoalType() { return goalType; }
    
    /**
     * Returns the weight of the goal.
     * @return Number The weight of the goal.
     */
    public Number getGoalWeight() { return new Integer(weight); }
    
    /** Gets the Goal past active rule. <B>Not used in the TNS.</B>
     * @return the Goals oast active rule
     */
    public Rule getPastActiveRule() { return null; }
    
    /**
     * Returns the role that owns this goal.
     * @return Role The role that owns this goal.
     */
    public Role getRole() { return role; }
    
    /** Returns the ruleList Vector. <B>Not used in the TNS.</B>
     * @return ruleList
     */
    public Vector getRuleList() { return null; }
    
    /**
     * Marks the goal complete by setting the goal completion boolean flag.
     */
    public void markGoalComplete() {
        completed = true;
    }
    
    /** Removes the Rule from the ruleList Vector. <B>Not used in the TNS.</B>
     * @param pRule  The Rule that is to be removed
     * @return The success of the removal operation:
     * <li> true - The Rule was successfully removed
     * <li> false - The Rule did not exist in the Goal's ruleList
     */
    public boolean removeRule(Rule pRule) {
        return false;
    } // end removeRule
    
    /**
     * Passes the PerceivedEnvironment to the activeRule in its calculate()
     * method. <B>Not used in the TNS</B>
     * @param pPE The sensed environment.
     * @return Object An Object associated with the active Rules calculation.
     */
    public Object runActiveRule(SensedEnvironment pPE) {
        return null;
    } // end runActiveRule
    
    /** Sets the ActiveRule, used for goal attainment. <B>Not used in the TNS.</B>
     * @param pRule The agent's active rule.
     */
    public void setActiveRule(Rule pRule) {}
    /** Sets the Goals type, the developer should designate specific goal types to
     * be used in the RELATE architecture.
     * @param pType Integer representing the goal type.
     *
     */
    public void setGoalType(int pType) {
        goalType = pType;
    }
    
    /** Sets the PastActiveRule, used for comparison and rule weight calculation. <B>Not used in the TNS.</B>
     * @param pRule The agent's past active rule.
     */
    public void setPastActiveRule(Rule pRule) {}
    
    /**
     * Takes whatever action is necessary for this particular goal in the form
     * of a ticket.  Replaces the run active rule method from the RELATE
     * architecture in the TNS.
     */
    public void executeActiveTicket() {
    }
    
    /**
     * Returns whether or not the goal has been completed.
     * @return boolean Whether or not the goal has been completed.
     */
    public boolean isCompleted() { return completed; }
    
    /**
     * Resets the goal to the uncompleted state by reseting the boolean flag.
     */
    public void resetGoal() {
        completed = false;
    }
    
}
