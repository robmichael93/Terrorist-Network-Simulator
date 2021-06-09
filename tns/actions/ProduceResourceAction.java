package tns.actions;
import tns.frames.*;
import tns.roles.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import simkit.random.*;

/**
 * This action produces a random number of resource points using a right 
 * triangle distribution with the maximum and mean set to the same value as the 
 * specialist's experience value.
 * @author  Rob Michael and Zac Staples
 */
public class ProduceResourceAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    /**
     * The name of the type of resource to be produced.
     */
    private String type;
    /**
     * A random variate for generating the amount of resources created.
     * Follows the Singleton pattern.
     */
    private static TriangleVariate randomNumberGenerator;
    
    /** 
     * Creates a new instance of ProduceResourceAction 
     * If the random number generator has not been created, then one and only
     * one is created.
     * @param role The role performing the action.
     * @param type The name of the type of resource to be produced.
     */
    public ProduceResourceAction(Role role, String type) {
        this.role = role;
        this.type = type;
        if (randomNumberGenerator == null) {
            randomNumberGenerator = new TriangleVariate();
            randomNumberGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
    } // end ProduceResourceAction contructor
    
    /**
     * The execute method sets up the right triangle distribution and generates
     * an amount of resources equal to the square root of the random number
     * drawn.  The resource level is then updated for the correct resource
     * provider.
     */
    public void execute() {
        int experience = ((TerroristAgentPersonality)((TNSRole)role).getAgent().getPersonality()).getExperience();
        randomNumberGenerator.setParameters(new Object[] {new Integer(0), new Integer(experience), new Integer(experience)} );
        int unitsProduced = (int)(Math.sqrt(randomNumberGenerator.generate()));
        if (type.equalsIgnoreCase("Arms")) {
            ((ArmsDealerRole)role).getArms().updateLevel(unitsProduced);
/*            System.out.println(((TNSRole)role).getAgent().getEntityName() + " produced " + unitsProduced +
                               " units of Arms.");*/
        } else if (type.equalsIgnoreCase("Finances")) {
            ((FinancierRole)role).getFinances().updateLevel(unitsProduced);
/*            System.out.println(((TNSRole)role).getAgent().getEntityName() + " produced " + unitsProduced +
                               " units of Finances.");*/
        } else if (type.equalsIgnoreCase("Logistics")) {
            ((LogisticianRole)role).getLogistics().updateLevel(unitsProduced);
/*            System.out.println(((TNSRole)role).getAgent().getEntityName() + " produced " + unitsProduced +
                               " units of Logistics.");*/
        } // end if-else
    } // end execute
    
} // end class ProduceResourceAction
