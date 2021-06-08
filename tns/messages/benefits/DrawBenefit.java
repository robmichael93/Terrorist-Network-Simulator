package tns.messages.benefits;
import tns.agents.*;
import tns.roles.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * The mission draw benefit models the fact that if a leader needs a resource 
 * for an important mission that an agent might be more inclined to respond to 
 * that request than if the mission some small scale operation.
 * @author  Rob Michael and Zac Staples
 */
public class DrawBenefit implements Benefit {
    
    /**
     * The leader agent with the mission.
     */
    TerroristAgent agent;
    
    /** 
     * Creates a new instance of DrawBenefit 
     * @param agent The Leader agent with the mission.
     */
    public DrawBenefit(TerroristAgent agent) {
        this.agent = agent;
    } // end DrawBenefit constructor
    
    /**
     * Determines the value of the benefit.
     * @return Number The value of the benefit.
     */
    public Number calculate() {
        int draw = 0;
        Vector roles = agent.getRoleVector();
        Iterator i = roles.iterator();
        while (i.hasNext()) {
            Role role = (Role)i.next();
            if (role instanceof LeaderRole) {
                Mission mission = ((LeaderRole)role).getMission();
                if (mission != null) {
                    Target target = mission.getTarget();
                    draw = target.getDraw();
                } // end if
            } // end if
        } // end while
//        System.out.println("Draw Benefit: " + draw);
        return new Integer(draw);
    } // end calculate
    
} // end class DrawBenefit
