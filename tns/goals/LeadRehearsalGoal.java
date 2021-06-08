package tns.goals;
import tns.roles.*;
import tns.tickets.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * The lead mission rehearsal goal gives the leader the behavior that once the 
 * preponderance of the resources for the mission have been obtained, all the 
 * necessary people have been recruited, and the leader knows a trainer who can 
 * setup the necessary training facilities for him, the leader can take his cell
 * and run them through the mission for the required period of time.  Once the 
 * rehearsal finishes, the leader can return to gathering any remaining 
 * resources before launching off into the mission.
 * @author  Rob Michael and Zac Staples
 */
public class LeadRehearsalGoal implements TNSGoal {
    
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
     * Creates a new instance of LeadRehearsalGoal 
     * @param role The role that owns this goal.
     */
    public LeadRehearsalGoal(Role role) {
        this.role = role;
        goalName = new String("LeadRehearsalGoal");
//        System.out.println(goalName + " created.");
        relationshipName = new String("CellOpsRelationship");
        weight = 0;
        completed = false;
        goalType = TNSGoal.TRAIN;
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
     * @param sensedEnvironment The agent's perceived environment.
     *
     */
    public void assignCredit(SensedEnvironment sensedEnvironment) {
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
            boolean trainerFound = false;
            Vector knownAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getMentalMap().getKnownAgents();
            Iterator i = knownAgents.iterator();
            while (i.hasNext() && !trainerFound) {
                Agent agent = (Agent)i.next();
                Vector roles = agent.getRoleVector();
                Iterator i2 = roles.iterator();
                while (i2.hasNext()) {
                    Role role = (Role)i2.next();
                    if (role instanceof TrainerRole) {
                        trainerFound = true;
                    } // end if
                } // end while
            } // end while
//            System.out.println("Trainer found: " + trainerFound);
            Mission mission = ((LeaderRole)role).getMission();
            Target target = mission.getTarget();
            int financeCount = mission.getFinancesCount();
            int requiredFinances = target.getRequiredFinances();
            int logisticsCount = mission.getLogisticsCount();
            int requiredLogistics = target.getRequiredLogistics();
            int armsCount = mission.getArmsCount();
            int requiredArms = target.getRequiredArms();
            boolean enoughLogistics = false;
            boolean enoughFinances = false;
            boolean enoughArms = false;
            
            if (financeCount >= (int)(0.8 * requiredFinances)) {
                enoughFinances = true;
            } // end if
            if (logisticsCount >= (int)(0.8 * requiredLogistics)) {
                enoughLogistics = true;
            } // end if
            if (armsCount >= (int)(0.8 * requiredArms)) {
                enoughArms = true;
            } // end if
            boolean hasOperators = mission.hasNeededOperators();
/*            System.out.println("Enough Logistics: " + enoughLogistics);
            System.out.println("Enough Finances: " + enoughFinances);
            System.out.println("Enough Arms: " + enoughArms);
            System.out.println("All Operators: " + hasOperators);*/
            if (trainerFound && hasOperators && enoughFinances && enoughLogistics && enoughArms) {
                int operatorsNeedingRehearsal = 0;
/*                System.out.println(((TNSRole)role).getAgent().getEntityName() + " has " + 
                                   ((LeaderRole)role).getMission().getOperators().size() +
                                   " operators in the mission.");*/
                if (((LeaderRole)role).getMission().getOperators().size() > 0) {
                    i = ((LeaderRole)role).getMission().getOperators().iterator();
                    while (i.hasNext()) {
                        Agent operator = (Agent)i.next();
//                        System.out.println(((TNSRole)role).getAgent().getEntityName() + " checking operator " + operator.getEntityName());
                        Role operatorRole = null;
                        Vector roles = operator.getRoleVector();
                        Iterator i2 = roles.iterator();
                        while (i2.hasNext()) {
                            Role role = (Role)i2.next();
                            if (role instanceof OperatorRole) {
                                operatorRole = role;
                            } // end if
                        } // end while
                        Goal rehearseMission = null;
                        Vector operatorGoals = operatorRole.getGoalListVec();
                        Iterator i3 = operatorGoals.iterator();
                        while (i3.hasNext()) {
                            Goal g = (Goal)i3.next();
                            if (g instanceof RehearseMissionGoal) {
                                rehearseMission = g;
                            } // end if-else
                        } // end while
                        if (!((TNSGoal)rehearseMission).isCompleted()) {
                            operatorsNeedingRehearsal++;
                        } // end if
                    } // end while
    //                System.out.println("Operators needing rehearsal: " + operatorsNeedingRehearsal);
                    if (operatorsNeedingRehearsal > 0) {
                        weight = operatorsNeedingRehearsal * 10;
                    } else {
                        weight = 0;
                        completed = true;
                    } // end if-else
                } else {
                    weight = 0;
                } // end if-else
            } else {
//                System.out.println("Don't have all requirements.");
                weight = 0;
            } // end if-else
        } else {
//            System.out.println("Mission not planned.");
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
            if (t instanceof LeadRehearsalTicket) {
                t.execute();
            }  // end if
        } // end while
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
