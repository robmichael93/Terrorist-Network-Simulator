package tns.goals;
import mil.navy.nps.relate.*;

/**
 * This interface extends the RELATE architecture's Goal interface to provide
 * functionality specific to the TNS.
 * @author  Rob Michael and Zac Staples
 */
public interface TNSGoal extends Goal {

    /**
     * An indentifier for goals in the organizing phase of a story template.
     */
    public static final int ORGANIZE = 0;
    /**
     * An indentifier for goals in the planning phase of a story template.
     */
    public static final int PLAN = 1;
    /**
     * An indentifier for goals in the training and rehearsal phase of a 
     * story template.
     */
    public static final int TRAIN = 2;
    /**
     * An indentifier for goals in the execution phase of a story template.
     */
    public static final int EXECUTE = 3;
    
    /**
     * Returns the weight of the goal.
     * @return Number The weight of the goal.
     */
    public Number getGoalWeight();
 
    /**
     * Returns the role that owns this goal.
     * @return Role The role that owns this goal.
     */
    public Role getRole();
    
    /**
     * Returns whether or not the goal has been completed.
     * @return boolean Whether or not the goal has been completed.
     */
    public boolean isCompleted();
    
    /**
     * Marks the goal complete by setting the goal completion boolean flag.
     */
    public void markGoalComplete();
    
    /**
     * Resets the goal to the uncompleted state by reseting the boolean flag.
     */
    public void resetGoal();
    
    /**
     * Takes whatever action is necessary for this particular goal in the form
     * of a ticket.  Replaces the execute active rule method from the RELATE
     * architecture in the TNS.
     */
    public void executeActiveTicket();

}
