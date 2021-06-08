package tns.relationships;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This interface extends the RELATE Relationship interface with methods
 * particular to the TNS.
 * @author  Rob Michael and Zac Staples
 */
public interface TNSRelationship extends Relationship {
    
    /**
     * Returns the names of the roles that participate in this relationship.
     * @return Vector The names of the roles that participate in this
     * relationship.
     */
    public Vector getAssociatedRoles();
    
} // end interface TNSRelationship
