package tns.messages.benefits;
import mil.navy.nps.relate.*;
import tns.goals.*;

/**
 * The goal completion benefit reflects the fact that if responding to the 
 * message would bring the agent closer to completing a goal, then the agent is
 * more likely to answer that message.
 * @author  Rob Michael
 */
public class GoalCompletionBenefit implements Benefit {
    
    /**
     * The goal related to this benefit.
     */
    private Goal goal;
    
    /** 
     * Creates a new instance of GoalCompletionBenefit 
     * @param goal The goal related to this benefit.
     */
    public GoalCompletionBenefit(Goal goal) {
        this.goal = goal;
    } // end GoalCompletionBenefit constructor
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate() {
        return ((TNSGoal)goal).getGoalWeight();
    } // end calculate
    
} // end class GoalCompletionBenefit
