package tns.goals;
import tns.roles.*;
import tns.tickets.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This goal gives leaders the behavior of seeking out the source of operators 
 * for missions: recruiters and trainers.  If the leader detects any recruiters
 * or trainers he will try to contact them, much the same way recruiters
 * introduce themselves to contacts.  If the leader doesn't know either one of 
 * these types of agents, then he will post a request to those he knows to find 
 * these agents so that he can get the agents he needs.
 * @author  Rob Michael and Zac Staples
 */
public class GetOperatorsGoal implements TNSGoal {
    
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
     * Creates a new instance of GetOperatorsGoal 
     * @param role The role that owns this goal.
     */
    public GetOperatorsGoal(Role role) {
        this.role = role;
        goalName = new String("GetOperatorsGoal");
//        System.out.println(goalName + " created.");
        relationshipName = new String("OrganizeMissionCellRelationship");
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
        Goal planMission = null;
        Vector goals = role.getGoalListVec();
        Enumeration e = goals.elements();
        while (e.hasMoreElements()) {
            Goal g = (Goal)e.nextElement();
            if (g instanceof PlanMissionGoal) {
                planMission = g;
            } // end if-else
        } // end while
        boolean missionPlanned = ((TNSGoal)planMission).isCompleted();
        if (missionPlanned) {
            if (!((LeaderRole)role).getMission().hasNeededOperators()) {
                Mission mission = ((LeaderRole)role).getMission();
                Target target = mission.getTarget();
                if (target != null) {
                    int requiredOperators = target.getRequiredOperators();
                    int neededOperators = requiredOperators - mission.getOperatorCount();
                    if (neededOperators <= 0) {
                        weight = 0;
                    } else {
                        weight = neededOperators * 10;
                        completed = false;
                    } // end if-else
                    
                    boolean recruiterFound = false;
                    Vector directlyLinkedAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getMentalMap().getDirectlyLinkedAgents();
                    Iterator i = directlyLinkedAgents.iterator();
                    while (i.hasNext() && !recruiterFound) {
                        Agent agent = (Agent)i.next();
                        Vector roles = agent.getRoleVector();
                        Iterator i2 = roles.iterator();
                        while (i2.hasNext()) {
                            Role r = (Role)i2.next();
                            if (r instanceof RecruiterRole) {
                                recruiterFound = true;
                            } // end if
                        } // end while
                    } // end while
                    boolean trainerFound = false;
                    i = directlyLinkedAgents.iterator();
                    while (i.hasNext() && !trainerFound) {
                        Agent agent = (Agent)i.next();
                        Vector roles = agent.getRoleVector();
                        Iterator i2 = roles.iterator();
                        while (i2.hasNext()) {
                            Role r = (Role)i2.next();
                            if (r instanceof TrainerRole) {
                                trainerFound = true;
                            } // end if
                        } // end while
                    } // end while
                    
                    if (!recruiterFound && !trainerFound) {
                        int getOperatorsStuckCounter = ((LeaderRole)role).getGetOperatorsStuckCounter();
                        weight += (getOperatorsStuckCounter * 10);
                    } // end if
                } else {
                    weight = 0;
                } // end if-else
            } else {
                weight = 0;
                completed = true;
            } // end if-else
        } else {
            weight = 0;
        } // end if-else
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
//        System.out.println(goalName + " completed.");
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
        Vector tickets = ((TNSRole)role).getTickets();
        Enumeration e = tickets.elements();
        while (e.hasMoreElements()) {
            Ticket t = (Ticket)e.nextElement();
            if (t instanceof GetOperatorsTicket) {
                t.execute();
            }  // end if
        } // end while
        Ticket findRecruiter = new FindRecruiterTicket(role);
        findRecruiter.execute();
        Ticket findTrainer = new FindTrainerTicket(role);
        findTrainer.execute();
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
