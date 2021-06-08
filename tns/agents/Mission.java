package tns.agents;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * A Mission keeps track of the current status of accomplishing an attack on a 
 * target, therefore the mission holds the status of the different levels of 
 * resources, how many and which operators have joined the mission, and how many
 * turns the cell has rehearsed or executed the mission.  
 * @author  Rob Michael and Zac Staples
 */
public class Mission {
    
    /**
     * The target associated with the mission.
     */
    private Target target;
    
    /**
     * The collection of operators involved in the mission.
     */
    private Vector operators;
    /**
     * The amount of finances the leader has collected for the mission.
     */
    private int finances;
    /**
     * The amount of logistics the leader has collected for the mission.
     */
    private int logistics;
    /**
     * The amount of arms the leader has collected for the mission.
     */
    private int arms;
    
    /**
     * The number of turns the mission has been rehearsed.
     */
    private int rehearseTurns;
    /**
     * The number of turns the mission has been executed.
     */
    private int executeTurns;
    
    /**
     * Whether or not the mission has all the needed operators.
     */
    private boolean hasNeededOperators;
    /**
     * Whether or not the mission has all the needed finances.
     */
    private boolean hasNeededFinances;
    /**
     * Whether or not the mission has all the needed logistics.
     */
    private boolean hasNeededLogistics;
    /**
     * Whether or not the mission has all the needed arms.
     */
    private boolean hasNeededArms;
    /**
     * Whether or not the mission has a training area.
     */
    private boolean hasTrainingArea;

    /**
     * Whether or not the mission has been rehearsed.
     */
    private boolean rehearsed;
    /**
     * Whether or not the mission has been executed.
     */
    private boolean executed;
    
    /**
     * The total reward pool, which is the sum of all the influence and
     * experience values of the operators in the mission.
     */
    private int totalRewardPool;
    /**
     * The number of award points the operators split at the end of the mission.
     */
    private int operatorsReward;
    /**
     * The number of award points the leader receives at the end of the mission.
     */
    private int leadersReward;
    
    /** 
     * Creates a new instance of Mission 
     * @param target The target associated with the mission.
     */
    public Mission(Target target) {
        this.target = target;
        target.associateMission(this);
        operators = new Vector();
        finances = 0;
        logistics = 0;
        arms = 0;
        rehearseTurns = 0;
        executeTurns = 0;
        totalRewardPool = 0;
        hasNeededOperators = false;
        hasNeededFinances = false;
        hasNeededLogistics = false;
        hasNeededArms = false;
        hasTrainingArea = false;
        rehearsed = false;
        executed = false;
    } // end Mission constructor
    
    /**
     * Returns the target associated with the mission.
     * @return Target The target associated with the mission.
     */
    public Target getTarget() { return target; }
    
    /**
     * Returns the number of operators in the mission.
     * @return int The number of operators in the mission.
     */
    public int getOperatorCount() { return operators.size(); }
    
    /**
     * Adds an operator to the mission.  If the agent added is the last operator
     * needed then the boolean flag for having all the operators is set.  Also,
     * the total reward pool is calculated.
     * @param agent The agent to be added.
     * @return boolean True if the agent was successfully added (agent did not
     * already exist as an operator in the mission).
     */
    public boolean addOperator(Agent agent) {
        if (!operators.contains(agent)) {
            operators.add(agent);
//            System.out.println("\t\t\t\t\t\t\t\tRequired operators: " + target.getRequiredOperators() + " Current Operators: " + operators.size());
            if (operators.size() >= target.getRequiredOperators()) {
                hasNeededOperators = true;
//                System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " has all needed Operators!");
                calculateRewardDistribution();
            } // end if
            return true;
        } else {
            return false;
        } // end if-else
    } // end addOperator
    
    /**
     * Removes an operator from the mission.  If the agent brings the number
     * of operators below the required amount, the boolean flag for having all
     * of the operators is reset.
     * @param agent The operator to be removed from the mission.
     * @return boolean True if the agent was successfully removed (the agent
     * had to already exist in the mission to be removed).
     */
    public boolean removeOperator(Agent agent) {
        if (operators.contains(agent)) {
            operators.remove(agent);
//            System.out.println("\t\t\t\t\t\t\t\tRequired operators: " + target.getRequiredOperators() + " Current Operators: " + operators.size());
            if (operators.size() < target.getRequiredOperators()) {
                hasNeededOperators = false;
//                System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " now needs another Operator!");
            } // end if
            return true;
        } else {
            return false;
        } // end if-else
    } // end addOperator
    
    /**
     * Calculates the reward distribution between the leader and the rest of
     * the operators.  It the number of operators is less than four, then the
     * leader gets half of the reward and the operators get the other half.  If
     * the number of operators is four or greater, the leader get a quarter of
     * the reward and the operators get the rest.
     */
    public void calculateRewardDistribution() {
        int draw = target.getDraw();
        int numberOfOperators = operators.size();
        Iterator i = operators.iterator();
        while (i.hasNext()) {
            Agent agent = (Agent)i.next();
            Personality personality = agent.getPersonality();
            int experience = ((TerroristAgentPersonality)personality).getExperience();
            int influence = ((TerroristAgentPersonality)personality).getInfluence();
            totalRewardPool += (experience + influence);
        } // end while
        if (numberOfOperators > 3) {
            leadersReward = (int) Math.round(0.25 * draw);
            operatorsReward = (int) Math.round(0.75 * draw);
        } else {
            leadersReward = (int) Math.round(0.5 * draw);
            operatorsReward = (int) Math.round(0.5 * draw);
        } // end if-else
    } // end calculateRewardDistribution
    
    /**
     * Returns the total reward pool for the operators.
     * @return int The total reward pool for the operators.
     */
    public int getTotalRewardPool() { return totalRewardPool; }
    
    /**
     * Returns the amount of reward points the operators split.
     * @return int The amount of reward points the operators split.
     */
    public int getOperatorsReward() { return operatorsReward; }
    
    /**
     * Returns the amount of reward the leader receives.
     * @return int The amount of reward the leader receives.
     */
    public int getLeadersReward() { return leadersReward; }
    
    /**
     * The collection of operator agents in the mission.
     * @return Vector The collection of operator agents in the mission.
     */
    public Vector getOperators() { return operators; }
    
    /**
     * Returns the current level of finances collected for the mission.
     * @return int The current level of finances collected for the misison.
     */
    public int getFinancesCount() { return finances; }
    
    /**
     * Updates the amount of finances collected for the mission by the
     * specified amount.  If the amount collected reaches or exceeds the
     * required amount, then the boolean flag for having all the required
     * finances is set.
     * @param f The amount of finances to update the mission by.
     */
    public void updateFinancesCount(int f) {
        finances += f;
//        System.out.println("\t\t\t\t\t\t\t\tRequired Finances: " + target.getRequiredFinances() + " Current Finances: " + finances);
        if (finances >= target.getRequiredFinances()) {
            hasNeededFinances = true;
//            System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " has all needed Finances!");
        } // end if
    } // end updateFinancesCount
    
    /**
     * Returns the current level of logistics collected for the mission.
     * @return int The current level of logistics collected for the mission.
     */
    public int getLogisticsCount() { return logistics; }
    
    /** Updates the amount of logistics collected for the mission by the
     * specified amount.  If the amount collected reaches or exceeds the
     * required amount, then the boolean flag for having all the required
     * logistics is set.
     * @param l The amount of logistics to update the mission by.
     */
    public void updateLogisticsCount(int l) {
        logistics += l;
//        System.out.println("\t\t\t\t\t\t\t\tRequired Logistics: " + target.getRequiredLogistics() + " Current Logistics: " + logistics);
        if (logistics >= target.getRequiredLogistics()) {
            hasNeededLogistics = true;
//            System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " has all needed Logistics!");
        } // end if
    } // end updateLogisticsCount
    
    /**
     * Returns the current level of arms collected for the mission.
     * @return int The current level of arms collected for the mission.
     */
    public int getArmsCount() { return arms; }
    
    /** Updates the amount of arms collected for the mission by the
     * specified amount.  If the amount collected reaches or exceeds the
     * required amount, then the boolean flag for having all the required
     * arms is set.
     * @param a The amount of arms to update the mission by.
     */
    public void updateArmsCount(int a) {
        arms += a;
//        System.out.println("\t\t\t\t\t\t\t\tRequired Arms: " + target.getRequiredArms() + " Current Arms: " + arms);
        if (arms >= target.getRequiredArms()) {
            hasNeededArms = true;
//            System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " has all needed Arms!");
        } // end if
    } // end updateArmsCount
    
    /**
     * Returns the number of turns the mission has been rehearsed.
     * @return int The number of turns the mission has been rehearsed.
     */
    public int getRehearseTurns() { return rehearseTurns; }
    
    /**
     * Increments the number of turns the mission has been rehearsed by one.  If
     * the number of turns reaches the required numberf of rehearsal turns, then
     * the boolean flag for having the mission rehearsed is set.
     */
    public void incrementRehearseTurns() {
        rehearseTurns++;
//        System.out.println("\t\t\t\t\t\t\t\tRequired Rehearsal Turns: " + target.getRehearseTime() + " Current Rehearsal Turns: " + rehearseTurns);
        if (rehearseTurns >= target.getRehearseTime()) {
            rehearsed = true;
//            System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " rehearsed!");
        } // end if
    } // end incrementRehearseTurns
    
    /**
     * Returns the number of turns the mission has been executed.
     * @return int The number of turns the mission has been executed.
     */
    public int getExecuteTurns() { return executeTurns; }
    
    /**
     * Increments the number of turns the mission has been executed by one.  If
     * the number of turns reaches the required numberf of execution turns, then
     * the boolean flag for having the mission executed is set.
     */
    public void incrementExecuteTurns() {
        executeTurns++;
//        System.out.println("\t\t\t\t\t\t\t\tRequired Execute Turns: " + target.getExecuteTime() + " Current Execute Turns: " + executeTurns);
        if (executeTurns >= target.getExecuteTime()) {
            executed = true;
//            System.out.println("\t\t\t\t\t\t\t\tMission " + this.toString() + " executed!");
        } // end if
    }
    
    /**
     * Returns whether or not the mission has all the required operators.
     * @return boolean True if the mission has all the required operators.
     */
    public boolean hasNeededOperators() { return hasNeededOperators; }
    
    /**
     * Returns whether or not the mission has all the required finances.
     * @return boolean True if the mission has all the required finances.
     */
    public boolean hasNeededFinances() { return hasNeededFinances; }
    
    /**
     * Returns whether or not the mission has all the required logistics.
     * @return boolean True if the mission has all the required logistics.
     */
    public boolean hasNeededLogistics() { return hasNeededLogistics; }
    
    /**
     * Returns whether or not the mission has all the required arms.
     * @return boolean True if the mission has all the required arms.
     */
    public boolean hasNeededArms() { return hasNeededArms; }
    
    /**
     * Returns whether or not the mission has a training area.
     * @return boolean True if the mission has a training area.
     */
    public boolean hasTrainingArea() { return hasTrainingArea; }
    
    /**
     * Sets the boolean flag for having a training area.
     */
    public void setTrainingAreaComplete() { hasTrainingArea = true; }
    
    /**
     * Returns whether or not the mission has been rehearsed.
     * @return boolean True if the mission has been rehearsed.
     */
    public boolean rehearsed() { return rehearsed; }
    
    /**
     * Returns whether or not the mission has been executed.
     * @return boolean True if the mission has been executed.
     */
    public boolean executed() { return executed; }
    
} // end class Mission
