package tns.util;
import tns.agents.*;

/**
 * A simple class that encapsulates an agent pair and the amount to update the
 * pair's history by.  Used by history change listeners.
 * @author  Rob Michael and Zac Staples
 */
public class HistoryChangeEvent {
    
    /**
     * The pair whose history value is being updated.
     */
    private AgentPair agentPair;
    /**
     * The amount to update the history value by.
     */
    private int amount;
    
    /** 
     * Creates a new instance of HistoryChangeEvent 
     * @param agentPair The pair whose history value is being updated.
     * @param amount The amount to update the history value by.
     */
    public HistoryChangeEvent(AgentPair agentPair, int amount) {
        this.agentPair = agentPair;
        this.amount = amount;
    } // end HistoryChangeEvent constructor
 
    /**
     * Returns the agent pair.
     * @return AgentPair The agent pair.
     */
    public AgentPair getAgentPair() { return agentPair; }
    
    /**
     * Returns the amount to update the history by.
     * @return int The amount to update the history by.
     */
    public int getAmount() { return amount; }

} // end class HistoryChangeEvent
