package tns.messages.risks;
import tns.messages.*;

/**
 * The separation risk models that people are more likely to do some task for 
 * someone within their own circle of friends or known associates.  If a person 
 * asks someone else to do something for his sister’s friend’s husband’s cousin, 
 * that person is probably pretty unlikely to perform that task because of the 
 * amount of separation.  However, for a person’s immediate friends and maybe 
 * their friends the amount of separation probably is reasonable.
 * @author  Rob Michael and Zac Staples
 */
public class SeparationRisk implements Risk {
    
    /**
     * The message used for determining the amount of separation.
     */
    private Message message;
    
    /** 
     * Creates a new instance of SeparationRisk 
     * @param message The message used for determining the amount of separation.
     */
    public SeparationRisk(Message message) {
        this.message = message;
    } // end SeparationRisk constructor
    
    /**
     * Determines the value of the risk.
     * @return Number The value of the risk.
     */
    public Number calculate() {
//        System.out.println("Separation Risk: " + message.getChain().size());
        return new Integer(message.getChain().size());
    } // end calculate
    
} // end class SeparationRisk
