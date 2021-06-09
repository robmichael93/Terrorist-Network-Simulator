package tns.messages.risks;
import mil.navy.nps.relate.*;
import tns.agents.*;

/**
 * The status risk models the fact that the terrorist agents have a certain 
 * stature within the organization and that their time is valuable.  Therefore, 
 * if an influential logistician receives a request from some second-rate leader
 * looking for some fake passports, he is probably going to turn him down 
 * because the leader is beneath him, but if he receives a call from Pablo 
 * Escobar looking for the same thing, he's probably going to accommodate him. 
 * To place the status risk on par with mission draw for the purpose of 
 * processing the "get resource" message as described below, this risk is based 
 * on the product of the specialist�s influence and experience.
 * @author  Rob Michael and Zac Staples
 */
public class StatusRisk implements Risk {
    
    /**
     * The agent whose status is the basis of this risk.
     */
    private TerroristAgent agent;
    
    /** 
     * Creates a new instance of StatusRisk 
     * @param agent The agent whose status is the basis of this risk.
     */
    public StatusRisk(TerroristAgent agent) {
        this.agent = agent;
    } // end StatusRisk constructor
    
    /**
     * Determines the value of the risk.
     * @return Number The value of the risk.
     */
    public Number calculate() {
        int experience = ((TerroristAgentPersonality)agent.getPersonality()).getExperience();
        int influence = ((TerroristAgentPersonality)agent.getPersonality()).getInfluence();
        int value = experience * influence;
//        System.out.println("Status Risk: " + value);
        return new Integer(value);
    } // end calculate
    
} // end class StatusRisk
