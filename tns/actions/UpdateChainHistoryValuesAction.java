package tns.actions;
import tns.frames.*;
import tns.messages.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action updates the history value each of the agent pairs in a message's 
 * forwarding chain.
 * @author  Rob Michael and Zac Staples
 */
public class UpdateChainHistoryValuesAction implements Frame {
    
    /**
     * The Message, which its chain is being used to reward history values.
     */
    private Message message;
    
    /** 
     * Creates a new instance of UpdateChainHistoryValuesAction 
     * @param message The Message, which its chain is being used to reward
     * history values.
     */
    public UpdateChainHistoryValuesAction(Message message) {
        this.message = message;
    } // end UpdateChainHistoryValuesAction constructor
    
    /**
     * The execute method take the message chain and updates the history
     * value for each agent pair by a set amount.
     */
    public void execute() {
        Vector chain = message.getChain();
        Iterator i = chain.iterator();
        while (i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            Agent fromAgent = agentPair.getFrom(); // only need one agent in the pair to update the history
            ((TerroristAgent)fromAgent).changeHistory(agentPair, 0);
        } // end while
    } // end execute
    
} // end class UpdateChainHistoryValuesAction
