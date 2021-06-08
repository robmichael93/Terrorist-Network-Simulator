package tns.messages.risks;

/**
 * Risks model the reasons a terrorist agent refuses to send, forward, or answer 
 * a message.
 * @author  Rob Michael and Zac Staples
 */
public interface Risk {
    
    /**
     * Determines the value of the risk.
     * @return Number The value of the risk.
     */
    public Number calculate();
    
} // end interface Risk
