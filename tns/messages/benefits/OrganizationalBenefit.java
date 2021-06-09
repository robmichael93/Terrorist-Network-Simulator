package tns.messages.benefits;
import tns.agents.*;

/**
 * An agent's allegiance defines the organizational benefit.  This benefit models
 * the behavior that in general, the more committed an agent is to the 
 * organization the more likely the agent will act on the message.
 * @author  Rob Michael and Zac Staples
 */
public class OrganizationalBenefit implements Benefit {
    
    /**
     * The agent whose allegiance this benefit is based on.
     */
    private TerroristAgent agent;
    
    /** 
     * Creates a new instance of OrganizationalBenefit 
     * @param agent The agent whose allegiance this benefit is based on.
     */
    public OrganizationalBenefit(TerroristAgent agent) {
        this.agent = agent;
    } // end OrganizationalBenefit constructor
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate() {
        int value = ((TerroristAgentPersonality)agent.getPersonality()).getAllegiance();
//        System.out.println("Organizational Benefit: " + value);
        return new Integer(value);
    } // end calculate
    
} // end class OrganizationalBenefit
