package tns.messages.benefits;

/**
 * Benefits model the various reasons why a terrorist agent sends, forwards, or 
 * responds to a message.
 * @author  Rob Michael and Zac Staples
 */
public interface Benefit {
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate();
    
} // end interface Benefit
