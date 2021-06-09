package tns.agents;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * With the agent's role or roles, the personality gives each agent a distinct 
 * character.  In the RELATE architecture, each personality is unique to the 
 * specific application, and in the TNS, each agent has personality traits of 
 * allegiance, experience, and influence.
 * @author  Rob Michael and Zac Staples
 */
public class TerroristAgentPersonality implements Personality {
    
    /**
     * An agent's allegiance value models the agent's dedication to the 
     * organization.  Agents with higher allegiance are more likely to perform 
     * certain actions out of their devotion to the organization.  For young 
     * contacts and recruits, the agent's allegiance determines how much time a 
     * recruiter has to spend with that agent testing the agent�s mettle for 
     * joining the organization.
     */
    private int allegiance;
    /**
     * The agent's Experience value models how skilled the agent is in 
     * conducting terrorist-related activities.  For specialists, such as arms 
     * dealers, financiers, and logisticians, the experience value determines 
     * the departure point for how much of a resource the agent can produce in a
     * given turn.  For leaders, the experience value helps determine how 
     * attractive of a mission the leader can devise and what the resources will
     * be needed for the operation.  Experienced agents can create more 
     * elaborate, more seductive missions due to their experience and influence,
     * and therefore draw more agents to join on the lucrative missions.
     */
    private int experience;
    /**
     * The agent's influence value is the ultimate determination of where the 
     * agent falls in the organization's pecking order.  Influence combines with 
     * experience for leaders creating missions.  Influence is also used to 
     * determine whether or not an agent is willing to communication with 
     * another agent or is willing to pass on a message coming from another 
     * agent.  Influence and experience also combine together in the specialists
     * to create the notion of status with respect to answer a leader's request 
     * for the specialist to provide a resource.  For instance, if the leader's 
     * mission is below the stature of the specialist, then the specialist will 
     * ignore the leader's request.
     */
    private int influence;
    
    /** 
     * Creates a new instance of AgentPersonality 
     * @param a The allegiance value assigned to the agent.
     * @param e The experience value assigned to the agent.
     * @param i The influence value assigned to the agent.
     */
    public TerroristAgentPersonality(int a, int e, int i) {
        allegiance = a;
        experience = e;
        influence = i;
//        System.out.println("TA Personality created.");
    } // end TerroristAgentPersonality constructor
    
    /**
     * Returns the agent's allegiance value.
     * @return int The agent's allegiance value.
     */
    public int getAllegiance() { return allegiance; }
    /**
     * Sets the agent's allegiance value.
     * @param a The allegiance value assigned to the agent.
     */
    public void setAllegiance(int a) { allegiance = a; }
    /**
     * Updates the agent's allegiance value by a specified amount.
     * @param a The amount to update the agent's allegiance by.
     */
    public void updateAllegiance(int a) {
        allegiance += a;
    } // end updateAllegiance
    
    /**
     * Returns the agent's experience value.
     * @return int The agent's experience value.
     */
    public int getExperience() { return experience; }
    /** Sets the agent's experience value.
     * @param e The experience value assigned to the agent.
     */
    public void setExperience(int e) { experience = e; }
    /** Updates the agent's experience value by a specified amount.
     * @param e The amount to update the agent's experience by.
     */
    public void updateExperience(int e) {
        experience += e;
    } // end updateExperience
    
    /**
     * Returns the agent's influence value.
     * @return int The agent's influence value.
     */
    public int getInfluence() { return influence; }
    /** Sets the agent's influence value.
     * @param i The influence value assigned to the agent.
     */
    public void setInfluence(int i) { influence = i; }
    /** Updates the agent's influence value by a specified amount.
     * @param i The amount to update the agent's influence by.
     */
    public void updateInfluence(int i) {
        influence += i;
    } // endupdateInfluence
    
} // end class TerroristAgentPersonality
