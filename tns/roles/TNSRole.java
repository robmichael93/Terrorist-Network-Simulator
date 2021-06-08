package tns.roles;
import tns.agents.*;
import java.util.*;
import mil.navy.nps.relate.*;
import java.awt.*;

/**
 * This interface extends the RELATE architecture's Role interface by adding 
 * methods specific to the TNS.
 * @author  Rob Michael and Zac Staples
 */
public interface TNSRole extends Role {
    
    /**
     * Returns the agent that owns this role.
     * @return Agent The agent that owns this role.
     */
    public Agent getAgent();
 
    /**
     * Returns the role's connectors.
     * @return Vector The role's connectors.
     */
    public Vector getConnectors();
    
    /**
     * Returns the role's tickets.
     * @return Vector The role's tickets.
     */
    public Vector getTickets();
    
    /**
     * Returns the names of the relationships this role is a part of.
     * @return Vector The names of the relationships this role is a part of.
     */
    public Vector getRelationships();
    
    /**
     * Returns this role's display symbol.
     * @return String This role's display symbol.
     */
    public String getSymbol();
    
    /**
     * Returns this role's display color.
     * @return Color This role's display color.
     */
    public Color getBackgroundColor();
    
    /**
     * Returns this role's display text color.
     * @return Color This role's display text color.
     */
    public Color getTextColor();
    
} // end interface TNSRole
