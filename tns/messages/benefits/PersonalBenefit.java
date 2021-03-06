package tns.messages.benefits;
import tns.agents.*;
import tns.messages.*;

/**
 * The personal benefit models the fact that people are generally somewhat 
 * selfish in nature and that they are more likely to serve their own needs 
 * before those of others and therefore will send their own messages.
 * @author  Rob Michael and Zac Staples
 */
public class PersonalBenefit implements Benefit {
    
    /**
     * The message used to check for the originator.
     */
    private Message message;
    /**
     * The agent whom the benefit is based on.
     */
    private TerroristAgent agent;
    
    /** 
     * Creates a new instance of PersonalBenefit 
     * @param message The message used to check for the originator.
     * @param agent The agent whom the benefit is based on.
     */
    public PersonalBenefit(Message message, TerroristAgent agent) {
        this.message = message;
        this.agent = agent;
    } // end PersonalBenefit constructor
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate() {
        if (message.getOriginator() == agent) {
//            System.out.println("Personal benefit: 5");
            return new Integer(5);
        } else {
//            System.out.println("Personal benefit: 0");
            return new Integer(0);
        } // end if-else
    } // end calculate
    
} // end class PersaonlBenefit
