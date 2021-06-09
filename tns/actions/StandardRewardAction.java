package tns.actions;
import tns.frames.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import simkit.random.*;
/**
 * This action iterates a number of times equal to a scalar value, as mentioned 
 * above in the tickets that use this action, and awards one experience point or
 * one influence point on an equal basis during each iteration.  When that part 
 * of the action is finished, the agent is awarded allegiance on a triangle 
 * distribution with a minimum of -1, a maximum of 1, and a mid-point of 0.5.  
 * The reason for the modeling decision to use this distribution for allegiance 
 * was that a terrorist agent can gain influence or experience based the event 
 * that they just participated in, whether it be sending an operator to a 
 * leader, exchanging resources, or finishing a mission, but the agent either 
 * has a good experience, a bad experience, or a neutral experience.  
 * The authors weighted the distribution so that agents had a lower chance of 
 * having a bad experience and a higher chance of having a neutral experience, 
 * so that an agent's commitment to the organization slowly increased over time.
 * A feature the authors left for future work was to remove an agent from the 
 * organization when the allegiance value dropped below a certain threshold.
 * @author  robmichael
 */
public class StandardRewardAction implements Frame {
    
    /**
     * The agent receiving the reward.
     */
    private Agent agent;
    /**
     * The amount of total reward for influence and experience.
     */
    private int scalar;
    /**
     * A random number generator for creating the allegiance reward.
     */
    private static TriangleVariate rewardGenerator;
    /**
     * A generaic uniform distribution random number generator.
     */
    private static UniformVariate rng;

    /** 
     * Creates a new instance of StandardRewardAction 
     * @param agent The agent receiving the reward.
     * @param scalar The amount of the total reward for influence and experience.
     */
    public StandardRewardAction(Agent agent, int scalar) {
        this.agent = agent;
        this.scalar = scalar;
        if (rewardGenerator == null) {
            rewardGenerator = new TriangleVariate();
            rewardGenerator.setParameters(new Object[] {new Double(-1.0), new Double(1.0), new Double(0.5) });
            rewardGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
        if (rng == null) {
            rng = new UniformVariate();
            rng.setParameters(new Object[] {new Double(0.0), new Double(1.0) });
            rng.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
    } // end StandardRewardAction constructor
    
    /**
     * The execute mission runs a number of iterations based on the scalar value
     * and awards a point of either experience or influence on a 50/50 basis.
     * Lastly, the agent receives an allegiance reward based on a triangle
     * distribution as described above.
     */
    public void execute() {
        TerroristAgentPersonality personality = (TerroristAgentPersonality)((TerroristAgent)agent).getPersonality();
        for (int i = 0; i < scalar; i++) {
            double random = rng.generate();
            if (random < 0.5) {
                personality.updateExperience(1);
            } else {
                personality.updateInfluence(1);
            } // end if
        } // end for
        personality.updateAllegiance((int)Math.round(rewardGenerator.generate()));
    } // end execute
    
} // end class StandardRewardAction
