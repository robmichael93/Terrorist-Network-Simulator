package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;

/**
 * This ticket allows the specialist to increase his stockpile of resources.  
 * The specialist's stockpile is increased by a random amount based on a right 
 * triangle distribution as shown in Figure 8 above, using the specialist's 
 * experience as the maximum and mean value.
 * @author  Rob Michael and Zac Staples
 */
public class ProduceFinancesTicket extends Ticket {
    
    /** 
     * Creates a new instance of ProduceFinancesTicket 
     * @param role The role that owns this ticket.
     */
    public ProduceFinancesTicket(Role role) {
        super(role, "ProduceFinancesTicket");
        addFrame(new ProduceResourceAction(role, "Finances"));
    } // end ProcudeFinancesTicket constructor
    
} // end class ProduceFinancesTicket
