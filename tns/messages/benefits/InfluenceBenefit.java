package tns.messages.benefits;
import tns.agents.*;

/**
 * The influence benefit models the fact that who sent the message matters.  If 
 * an influential arms dealer receives a message from some low level operator 
 * looking for a leader so that he may join a mission, the arms dealer will be 
 * less likely to pass the message than if the sender of the message was an 
 * influential leader such as Osama bin Laden looking for operators to join his
 * latest mission.
 * @author  Rob Michael and Zac Staples
 */
public class InfluenceBenefit implements Benefit {
    
    /**
     * The agent whose influence this benefit is based on.
     */
    private TerroristAgent agent;
    
    /** 
     * Creates a new instance of InfluenceBenefit 
     * @param agent The agent whose influence this benefit is based on.
     */
    public InfluenceBenefit(TerroristAgent agent) {
        this.agent = agent;
    } // end InfluenceBenefit constructor
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate() {
        int value = ((TerroristAgentPersonality)agent.getPersonality()).getInfluence();
//        System.out.println("Influence Benefit: " + value);
        return new Integer(value);
    } // end calculate
    
} // end class Influence Benefit
