package tns.agents;
import mil.navy.nps.relate.*;

/**
 * A pair is simply a class the captures the fact that two agents are linked to 
 * each other and therefore know each other. Agent pairs also have one other 
 * characteristic, a history value.
 * @author  Rob Michael and Zac Staples
 */
public class AgentPair {
    
    /**
     * One of the agents in the pair.  Named "to" for ease of reference.
     */
    private Agent to;
    /**
     * The other agent in the pair.  Named "from" for ease of reference.
     */
    private Agent from;
    /**
     * The history value for the pair.
     */
    private int history;
    
    /** 
     * Creates a new instance of AgentPair 
     * The starting history value is 25.
     * @param from One of the agents in the pair.  "From" used for ease of 
     * reference.
     * @param to The other agent in the pair.  "To" used for ease of
     * reference.
     */
    public AgentPair(Agent from, Agent to) {
        this.from = from;
        this.to = to;
        history = 25;
    } // end AgentPair constructor
    
    /**
     * Returns the "to" agent in the pair.
     * @return Agent The "to" agent in the pair.
     */
    public Agent getTo() { return to; }
    
    /**
     * Returns the "from" agent in the pair.
     * @return Agent The "from" agent in the pair.
     */
    public Agent getFrom() { return from; }
    
    /**
     * Checks to see if the "to" or "from" agent in the pair is the agent
     * in question.
     * @param a The agent in question.
     * @return boolean True if the pair contains the agent in question.
     */
    public boolean contains(Agent a) {
        if (a.equals(to) || a.equals(from)) {
            return true;
        } else {
            return false;
        } // end if-else
    } // end contains
    
    /**
     * Returns the history value of the pair.
     * @return int The history value of the pair.
     */
    public int getHistory() { return history; }
    
    /**
     * Decrements the history of the pair by one.
     */
    public void decrementHistory() {
        history--;
/*        System.out.println("\t\t\t\t\t\t\t\t\t\tHistory between " + from.getEntityName() + " and " +
                           to.getEntityName() + " decremented by 1.  New history value: " +
                           history);*/
    } // end decrementHistory
    
    /**
     * Increments the history of the pair by the specified amount.
     * @param amount The specified amount to increment the history by.
     */
    public void incrementHistory(int amount) {
        history += amount; //zac changed this to 4 from 2
/*        System.out.println("\t\t\t\t\t\t\t\t\t\tHistory between " + from.getEntityName() + " and " +
                           to.getEntityName() + " incremented by " + amount + ".  New history value: " +
                           history);*/
    } // end incrementHistory
} // end AgentPair
