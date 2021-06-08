package tns.agents;
import mil.navy.nps.relate.*;

/**
 * This class represents the resources that the specialists produce and
 * provide.
 * @author  Rob Michael and Zac Staples
 */
public class Resource {
    
    /**
     * The specialist that owns this resource.
     */
    private Role role;
    /**
     * The current level of the resource.
     */
    private int level;
    
    /** 
     * Creates a new instance of Resource 
     * @param role The specialist that owns this message.
     */
    public Resource(Role role) {
        this.role = role;
        level = 0;
    } // end Resource constructor
    
    /**
     * Returns the current level of the resource.
     * @return int The current level of the resource.
     */
    public int getLevel() { return level; }
    
    /**
     * Updates the level of the resource by a specified amount.
     * @param l The specified amount to update the resource by.
     */
    public void updateLevel(int l) {
        level += l;
        if (l < 0) {
//            System.out.println("\t\t\t\t\t\t\t\tResource reduced by " + (-l) + " units.");
        } else {
//            System.out.println("\t\t\t\t\t\t\t\tResource increased by " + l + " units.");
        } // end if-else
    } // end updateLevel
} // end class Resource
