package tns.actions;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;

/**
 * This action increments the resource level collected for the leader's mission 
 * and decrements the resource level the specialist has on hand.  This action 
 * also rewards the specialist using a scalar equal to the number of resource 
 * points exchanged.
 * @author  Rob Michael and Zac Staples
 */
public class ResourceExchangeAction implements Frame {
    
    /**
     * The Leader requesting the resource.
     */
    private Role requester;
    /**
     * The specialist providing the resource.
     */
    private Role provider;
    /**
     * The Leader's request resource ticket.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of ResourceExchangeAction 
     * @param requester The Leader requesting the resource.
     * @param provider The specialisto providing the resource.
     * @param ticket The Leader's request resource ticket.
     */
    public ResourceExchangeAction(Role requester, Role provider, Ticket ticket) {
        this.requester = requester;
        this.provider = provider;
        this.ticket = ticket;
    } // end ResourceExchangeAction constructor
    
    /**
     * The execute method finds the amount of resource the specialist has on
     * hand and compares that to how much the leader needs.  If the specialist
     * has less than what the leader requests, then the specialist will give
     * the leader everything he has.  If has more than the leader needs then he
     * will give the leader just what he needs.
     */
    public void execute() {
//        System.out.println("ResourceExchangeAction executing");
        int requiredAmount = 0;
        int currentAmount = 0;
        int resourceAmountAvailable = 0;
        int requestedAmount = 0;
        int exchangedAmount = 0;
        if (provider instanceof ArmsDealerRole) {
            requiredAmount = ((LeaderRole)requester).getMission().getTarget().getRequiredArms();
            currentAmount = ((LeaderRole)requester).getMission().getArmsCount();
            resourceAmountAvailable = ((ArmsDealerRole)provider).getArms().getLevel();
        } else if (provider instanceof FinancierRole) {
            requiredAmount = ((LeaderRole)requester).getMission().getTarget().getRequiredFinances();
            currentAmount = ((LeaderRole)requester).getMission().getFinancesCount();
            resourceAmountAvailable = ((FinancierRole)provider).getFinances().getLevel();
        } else if (provider instanceof LogisticianRole) {
            requiredAmount = ((LeaderRole)requester).getMission().getTarget().getRequiredLogistics();
            currentAmount = ((LeaderRole)requester).getMission().getLogisticsCount();
            resourceAmountAvailable = ((LogisticianRole)provider).getLogistics().getLevel();
        } // end if-else
//        System.out.println("\t\t\t\t\t\t\t\tRequired Amount: " + requiredAmount + " Current Amount: " + currentAmount + " Available Amount: " + resourceAmountAvailable);
        requestedAmount = requiredAmount - currentAmount;
        if (requestedAmount <= resourceAmountAvailable) { // Leader takes what he needs
            exchangedAmount = requestedAmount;
        } else { // Leader takes all that is available
            exchangedAmount = resourceAmountAvailable;
        } // end if-else
        if (provider instanceof ArmsDealerRole) {
            ((LeaderRole)requester).getMission().updateArmsCount(exchangedAmount);
            ((ArmsDealerRole)provider).getArms().updateLevel(-exchangedAmount);
        } else if (provider instanceof FinancierRole) {
            ((LeaderRole)requester).getMission().updateFinancesCount(exchangedAmount);
            ((FinancierRole)provider).getFinances().updateLevel(-exchangedAmount);
        } else if (provider instanceof LogisticianRole) {
            ((LeaderRole)requester).getMission().updateLogisticsCount(exchangedAmount);
            ((LogisticianRole)provider).getLogistics().updateLevel(-exchangedAmount);
        } // end if-else
        ticket.addFrame(new StandardRewardAction(((TNSRole)provider).getAgent(), exchangedAmount));
    } // end execute
    
} // end class ResourceExchangeAction
