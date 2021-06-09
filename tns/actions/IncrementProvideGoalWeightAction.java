package tns.actions;
import tns.frames.*;
import tns.messages.risks.*;
import tns.messages.benefits.*;
import tns.roles.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action creates a weight value to be added to the specialist's "provide 
 * a resource" goal if the specialist processes a "get resource" message.  
 * The amount of the additional weight is the difference between the draw of the
 * leader's mission and the status risk of the specialist.  If a leader and a 
 * specialist on nearly on par with each other in terms of influence and 
 * experience, this value should be small, reflecting the relative influential 
 * power agents have with each other when dealing with missions and resources.
 * @author  Rob Michael and Zac Staples
 */
public class IncrementProvideGoalWeightAction implements Frame {
    
    /**
     * The specialist providing a resource.
     */
    private Role provider;
    /**
     * The leader agent requesting a resource.
     */
    private Agent requester;
    /**
     * The draw of the leader's mission.
     */
    private DrawBenefit drawBenefit = null;
    /**
     * The specialist's status.
     */
    private StatusRisk statusRisk = null;
    
    /** 
     * Creates a new instance of IncrementProvideGoalWeightAction 
     * @param provider The specialist role providing a resource.
     * @param requester The leader agent requesting a resource.
     */
    public IncrementProvideGoalWeightAction(Role provider, Agent requester) {
        this.provider = provider;
        this.requester = requester;
    }
    
    /**
     * The execute method simply finds the difference between the leader's draw
     * and the provider's status.  This value is used to affect the specialists
     * "provide a resource" goal.  If the leader has enough draw to lure the
     * specialist, then the specialist will change goals to providing the
     * resource the leader needs.
     */
    public void execute() {
//        System.out.println("SetProvideLatchAction executing");
        drawBenefit = new DrawBenefit((TerroristAgent)requester);
        statusRisk = new StatusRisk((TerroristAgent)((TNSRole)provider).getAgent());
        int weight = ((Integer)drawBenefit.calculate()).intValue() -
                     ((Integer)statusRisk.calculate()).intValue();
        if (provider instanceof ArmsDealerRole) {
            ((ArmsDealerRole)provider).setRequestWeight(weight);
        } else if (provider instanceof FinancierRole) {
            ((FinancierRole)provider).setRequestWeight(weight);
        } else if (provider instanceof LogisticianRole) {
            ((LogisticianRole)provider).setRequestWeight(weight);
        } // end if-else
    } // end execute
    
} // end IncrementProvideGoalWeightAction
