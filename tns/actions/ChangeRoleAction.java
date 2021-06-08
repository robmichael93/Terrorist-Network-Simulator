package tns.actions;
import mil.navy.nps.relate.*;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import java.util.*;
import java.lang.reflect.*;
import java.awt.Color;
import com.touchgraph.graphlayout.*;

/**
 * This action removes the agent from the relationships associated with the 
 * current role, removes the role from the agent’s collection of roles, creates 
 * the new Role object and adds the role to the agent’s collection of roles.  
 * The agent loses the goals associated with the old role and gains the new 
 * goals associated with the new role.
 * @author  robmichael
 */
public class ChangeRoleAction implements Frame {
    
    /**
     * The agent's old role.
     */
    Role oldRole;
    /**
     * The name of the agent's new role.
     */
    String newRole;
    
    /**
     * Creates a new instance of ChangeRoleAction
     * @param oldRole The agent's old role.
     * @param newRole The name of the agent's new role.
     */
    public ChangeRoleAction(Role oldRole, String newRole) {
        this.oldRole = oldRole;
        this.newRole = newRole;
    } // end ChangeRoleAction constructor
    
    /**
     * This method removes the agent from any relationships associated with
     * the old role.  The old role is then removed from the agent's collection
     * of roles.  Lastly, the new role is created using reflection and it is
     * added to the agent's collection of roles.
     */
    public void execute() {
        TerroristAgent agent = (TerroristAgent)((TNSRole)oldRole).getAgent();
        Hashtable relationshipTable = agent.getRelationshipTable();
        String relationshipName = null;
        Vector relationshipNames = ((TNSRole)oldRole).getRelationships();
        Iterator i = relationshipNames.iterator();
        while (i.hasNext()) {
            relationshipName = (String)i.next();
            Object temp = relationshipTable.get(relationshipName);
            if (temp instanceof Vector) {
                Vector removeRelationships = (Vector)temp;
                Vector copyOfRelationshipVector = new Vector();
                Iterator i2 = removeRelationships.iterator();
                while (i2.hasNext()) {
                    Relationship r = (Relationship)i2.next();
                    copyOfRelationshipVector.add(r);
                } // end while
                i2 = copyOfRelationshipVector.iterator();
                while (i2.hasNext()) {
                    Relationship removeRelationship = (Relationship)i2.next();
    //                System.out.println("Attempting to remove " + agent.getEntityName() + " from relationship " + removeRelationship.toString());
                    if (removeRelationship.getMembers().contains(agent)) {
                        removeRelationship.removeAgent(agent);
                    } else {
    //                    System.out.println("Not in this relationship.");
                    } // end if-else
                } // end while
    /*            System.out.println("Remaining relationships");
                i2 = removeRelationships.iterator();
                while (i2.hasNext()) {
                    Relationship r = (Relationship)i2.next();
                    System.out.println(r.toString());
                } // end while*/
                relationshipTable.remove(relationshipName);
            } // end if
        } // end while
        agent.removeRole(oldRole);
        Role addRole = createRole(newRole, agent);
        ((TerroristAgent)agent).addRole(addRole);
    } // end execute
    
    /**
     * Creates the Role the corresponds to the passed in class name and associated
     * Agent.
     * Roles have constructors that take an Agent argument, thus they have to
     * be created by getting the Constructor object for the Role Class object
     * and calling a NewInstance method on it with the Agent object passed in
     * as an argument.
     *
     * @param className The className of the Role to be created.
     * @param agent The agent that will be assigned the role.
     * @return Role The created Role.
     */
    private Role createRole( String className, Agent agent ) {
//        System.out.println("Attempting to create class " + className);
        Class cl = null;
        try {
           cl = Class.forName( "tns.roles." + className );
        } catch ( ClassNotFoundException e ) {
           System.err.println( "Can't find your darned class." );
           System.exit( 0 );
        } // end try-catch

        Constructor c = null;
        Object o = null;
        try {
            c = cl.getConstructor(new Class[] {Agent.class});
            o = c.newInstance(new Object[] {agent});
        } catch (NoSuchMethodException e) {
            System.err.println("Can't find you class' constructor.");
            System.exit(0);
        } catch (SecurityException e) {
            System.err.println("Security Exception. Can't access your class.");
            System.exit(0);
        } catch ( InstantiationException e ) {
           System.err.println( "Can't make your darned class." );
           System.exit( 0 );
        } catch ( IllegalAccessException e ) {
           System.err.println( "Can't access your darned class." );
           System.exit( 0 );
        } catch (IllegalArgumentException e) {
            System.err.println("Can't create your class.  Illegal arguments.");
            System.exit(0);
        } catch (InvocationTargetException e) {
            System.err.println("Can't create your class.  Contructor threw an exception.");
            System.exit(0);
        } // end try-catch

        return (Role)o;

    }// end createRole
} // end class ChangeRoleAction
