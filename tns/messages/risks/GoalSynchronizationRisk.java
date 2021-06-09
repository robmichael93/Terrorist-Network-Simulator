package tns.messages.risks;

/**
 * This risk models the fact that in planning, preparing, and carrying out 
 * missions, agents progress along a story line template of particular stages, 
 * and that like any traditional story during certain stages some events make 
 * sense to happen and in some stages others do not.  During the climax of a 
 * story, it would not make sense to introduce entirely new main characters to 
 * the story.  Likewise, as an agent progresses along the story line of the life
 * cycle of a mission, it makes sense for an agent to process or participate in 
 * certain types of communications and ignore others.  While Mohammed Atta is 
 * in Boston about to board a plane destined for the north World Trade Center 
 * building and he gets a call from an associate looking for Mohammed to 
 * introduce a friend to a well-connected arms dealer, Mohammed wouldn't take 
 * the call because he's in the middle of an important mission in which he knows
 * he's going to die for the glory of his cause.    The message type identifier 
 * is used in evaluating the goal synchronization risk against a table created
 * by the authors to model this behavior.
 * @author  Rob Michael and Zac Staples
 */
public class GoalSynchronizationRisk implements Risk {
    
    /**
     * A matrix of values for goal synchronization risk.  Column headings are
     * the message types as defined in class Message.  Rows are the goal types
     * as defined in interface TNSGoal.
     */
    private static int[][] goalMessageMatrix = {{5, 7, 1}, {7, 5, 1}, {15, 20, 20}, {20, 30, 30}};
    
    /**
     * The goal type being compared.
     */
    private int goalType;
    /**
     * The message type being compared.
     */
    private int messageType;
    
    /** 
     * Creates a new instance of GoalSynchronizationRisk 
     * @param goalType The goal type being compared.  See class Message.
     * @param messageType The message type being compared.  See interface
     * TNSGoal.
     */
    public GoalSynchronizationRisk(int goalType, int messageType) {
        this.goalType = goalType;
        this.messageType = messageType;
    } // end GoalSynchronizationRisk constructor
    
    /**
     * Determines the value of the risk.
     * @return Number The value of the risk.
     */
    public Number calculate() {
        if (messageType > 2) {
//            System.err.println("Unknown Message Type");
            System.exit(1);
            return null;
        } else {
//            System.out.println("Goal sync Risk: " + goalMessageMatrix[goalType][messageType]);
            return new Integer(goalMessageMatrix[goalType][messageType]);
        } // end if-else
    } // end calculate
    
} // end class GoalSynchronizationRisk
