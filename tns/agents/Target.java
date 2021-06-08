package tns.agents;
import mil.navy.nps.relate.*;
import tns.roles.*;
import simkit.random.*;

/**
 * Targets specify the requirements for mission accomplishment, such as how many
 * resources are needed and how long the operatives will need to rehearse and 
 * execute the mission to bring it to completion. Each target has 
 * characteristics of impact, stability, and draw.
 * @author  Rob Michael and Zac Staples
 */
public class Target {
    
    /**
     * The Leader that created the target.
     */
    private Role role;
    /**
     * The mission associated with the target.
     */
    private Mission mission;
    /**
     * The number of operators required for the target.
     */
    private int requiredOperators;
    /**
     * The number of logistics points required for the target.
     */
    private int requiredLogistics;
    /**
     * The number of finance points required for the target.
     */
    private int requiredFinances;
    /**
     * The number of arms points required for the target.
     */
    private int requiredArms;
    
    /**
     * The amount of required rehearsal time.
     */
    private int rehearseTime;
    /**
     * The amount of required execution time.
     */
    private int executeTime;
    
    /**
     * The target impact models the relative worth of the target.  The bombing 
     * of the World Trade Center buildings was a target of high impact, an event
     * that shook the world.  The shooting of several American soldiers outside 
     * Camp Doha in Kuwait was a low impact target, noteworthy, but in the large
     * scheme of effecting a nation, a small event.
     */
    private int impact;
    /**
     * Target stability models the window of opportunity when the target is 
     * vulnerable to attack.  The World Trade Center buildings were rock-solid 
     * stable targets; they weren’t moving.  However, the gassing of thousands 
     * of spectators at the Super Bowl would constitute a low stability target, 
     * where the window of opportunity consisted of a matter of hours.
     */
    private int stability;
    /**
     * Target draw represents the overall relative value of a mission compared 
     * to other missions.  Targets with higher draw create missions that are 
     * more desirable to participate in them because of the potential for fame, 
     * glory, and perceived reward in the afterlife.  Target draw consists of 
     * the product of impact and the base 2 logarithm of stability.
     */
    private int draw;
    
    /**
     * A random number generator for creating target impact.
     */
    private static TriangleVariate impactGenerator;
    /**
     * A random number generator for creating target stability.
     */
    private static UniformVariate stabilityGenerator;
    /**
     * A random number generator for create resource requirements.
     */
    private static TriangleVariate resourceGenerator;

    /** 
     * Creates a new instance of Target 
     * @param role The leader that created the target.
     */
    public Target(Role role) {
//        System.out.println(((TNSRole)role).getAgent().getEntityName() + " generating new Target...");
        this.role = role;
        Agent agent = ((TNSRole)role).getAgent();
        int influence = ((TerroristAgentPersonality)((TerroristAgent)agent).getPersonality()).getInfluence();
        int experience = ((TerroristAgentPersonality)((TerroristAgent)agent).getPersonality()).getExperience();
        int impactSeed = influence + experience;
        if (impactGenerator == null) {
            impactGenerator = new TriangleVariate();
            impactGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
        if (stabilityGenerator == null) {
            stabilityGenerator = new UniformVariate();
            stabilityGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
        if (resourceGenerator == null) {
            resourceGenerator = new TriangleVariate();
            resourceGenerator.getRandomNumber().setSeed(CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        } // end if
        impactGenerator.setParameters(new Object[] {new Integer(1), new Integer(impactSeed), new Integer(impactSeed)});
        stabilityGenerator.setParameters(new Object[] {new Integer(1), new Integer(100)});
        impact = (int) impactGenerator.generate();
        stability = (int) stabilityGenerator.generate();
        draw = impact * (int)(Math.log(impact)/Math.log(2.0));
//        System.out.println("New Target: Impact: " + impact + " Stability: " + stability + " Draw: " + draw);
//        int resourceSeed = (int) Math.pow(draw, (1.0/3.0));
        int resourceSeed = (int) (Math.log(draw)/Math.log(2.0));
//        System.out.println("Operator Seed: " + resourceSeed);
        resourceGenerator.setParameters(new Object[] {new Integer(1), new Integer(resourceSeed), new Integer(resourceSeed)});
        requiredOperators = (int)resourceGenerator.generate();
        resourceSeed = (int) Math.pow(draw, (1.0/2.0));
//        System.out.println("Resource Seed: " + resourceSeed);
        resourceGenerator.setParameters(new Object[] {new Integer(1), new Integer(resourceSeed), new Integer(resourceSeed)});
        requiredLogistics = (int)resourceGenerator.generate();
        requiredFinances = (int)resourceGenerator.generate();
        requiredArms = (int)resourceGenerator.generate();
        rehearseTime = (int)resourceGenerator.generate();
        executeTime = (int)resourceGenerator.generate();
/*        System.out.println("Required Operators: " + requiredOperators);
        System.out.println("Required Logistics: " + requiredLogistics);
        System.out.println("Required Finances: " + requiredFinances);
        System.out.println("Required Arms: " + requiredArms);
        System.out.println("Rehearse Time: " + rehearseTime);
        System.out.println("Execute Time: " + executeTime);*/
        mission = null;
    } // end Target constructor
    
    /**
     * Associates a mission with the target.
     * @param m The mission to be associated with the target.
     */
    public void associateMission(Mission m) {
        mission = m;
    } // end associateMission
    
    /**
     * Returns the mission associated with the target.
     * @return Mission The mission associated with the target.
     */
    public Mission getMission() { return mission; }
    
    /**
     * Returns the leader that owns the target.
     * @return Role The leader that own the target.
     */
    public Role getRole() { return role; }
    
    /**
     * Returns the number of required operators for the target.
     * @return int The number of required operators for the target.
     */
    public int getRequiredOperators() { return requiredOperators; }
    
    /**
     * Returns the number of required logistics points for the target.
     * @return int The number of required logistics points for the target.
     */
    public int getRequiredLogistics() { return requiredLogistics; }
    
    /**
     * Returns the number of required finance points for the target.
     * @return int The number of required finance points for the target.
     */
    public int getRequiredFinances() { return requiredFinances; }
    
    /**
     * Returns the number of required arms points for the target.
     * @return int The number of required arms points for the target.
     */
    public int getRequiredArms() { return requiredArms; }
    
    /**
     * Returns the number of required rehearsal turns for the target.
     * @return int The number of required rehearsal turns for the target.
     */
    public int getRehearseTime() { return rehearseTime; }
    
    /**
     * Returns the number of required execution turns for the target.
     * @return int The number of required execution turns for the target.
     */
    public int getExecuteTime() { return executeTime; }
    
    /**
     * Returns the target's impact value.
     * @return int The target's impact value.
     */
    public int getImpact() { return impact; }
    
    /**
     * Returns the target's stability value.
     * @return int The target's stability value.
     */
    public int getStability() { return stability; }
    
    /**
     * Returns the target's draw value.
     * @return int The target's draw value.
     */
    public int getDraw() { return draw; }
    
} // end class Target
