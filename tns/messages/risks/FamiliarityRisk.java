package tns.messages.risks;
import tns.agents.*;
import java.util.*;

/**
 *  The familiarity risks models the fact that people are more likely to 
 * communicate with people they know than complete strangers.  This risk allows 
 * agents to build a rapport with each other over time so that their familiarity 
 * risk is eventually driven down to zero and two agents become more likely to
 * communicate with each other because of the relationship they have developed.
 * @author  Rob Michael and Zac Staples
 */
public class FamiliarityRisk implements Risk {
    
    /**
     * The sender of a message.
     */
    private TerroristAgent sender;
    /**
     * The receiver of a message.
     */
    private TerroristAgent receiver;
    
    /**
     * The maximum amount of risk for familiarity.
     */
    private static final int maximumRisk = 5;
    
    /** 
     * Creates a new instance of FamiliarityRisk 
     * @param sender The sender of a message.
     * @param receiver The receiver of a message.
     */
    public FamiliarityRisk(TerroristAgent sender, TerroristAgent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    } // end FamiliarityRisk constructor
    
    /**
     * Determines the value of the risk.
     * @return Number The value of the risk.
     */
    public Number calculate() {
        Hashtable agentCommunications = sender.getAgentCommunications();
        if (agentCommunications.get(receiver) == null) {
//            System.out.println("Familiarity Risk: " + maximumRisk);
            return new Integer(maximumRisk);
        } else {
            int communicationCount = ((Integer)agentCommunications.get(receiver)).intValue();
            int risk = maximumRisk - communicationCount;
            if (risk < 0) {
//                System.out.println("Familiarity Risk: 0");
                return new Integer(0);
            } else {
//                System.out.println("Familiarity Risk: " + risk);
                return new Integer(risk);
            } // end if-else
        } // end if-else
    } // end calculate
    
} // end class FamiliarityRisk
