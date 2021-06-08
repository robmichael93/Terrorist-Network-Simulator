package tns.agents;
import simkit.random.*;

/**
 * This class determines if a Contact decides to join the organization.
 * @author  Rob Michael and Zac Staples
 */
public class ContactDecision {
    
    /**
     * The random number generator that generates a one or a zero if the
     * agent decides or the agent does not respectively.  Implemented as a
     * Singleton.
     */
    private static BinomialVariate decisionGenerator;
    
    /** 
     * Creates a new instance of ContactDecision 
     * @param recruiterInfluence The influence of the Recruiter.
     * @param contactAllegiance The allegiance of the Contact.
     */
    public ContactDecision(int recruiterInfluence, int contactAllegiance) {
        if (decisionGenerator == null) {
            decisionGenerator = new BinomialVariate();
            decisionGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
        double probability = 1.0 - ((double)contactAllegiance / (double)recruiterInfluence);
        decisionGenerator.setParameters(new Object[] { new Integer(1), new Double(probability) });
    } // end ContactDecision constructor
    
    /**
     * This method creates a random number against the probability derived in
     * the constructor.  If the number is within the probability, then the agent
     * decides to join and the method returns true, false otherwise.
     * @return boolean True if the Contact decides to join the organization.
     */
    public boolean decide() {
//        System.out.println("\t\t\t\t\t\t\t\t\t\t\tProbability: " + decisionGenerator.getProbability());
        int decision = decisionGenerator.generateInt();
//        System.out.println("\t\t\t\t\t\t\t\t\t\t\tDecision value: " + decision);
        if (decision > 0) {
            return true;
        } else {
            return false;
        } // end if-else
    } // end decide
} // end class ContactDecision
