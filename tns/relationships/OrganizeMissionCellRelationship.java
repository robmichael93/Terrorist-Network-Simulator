package tns.relationships;
import mil.navy.nps.relate.*;
import tns.roles.*;
import tns.connectors.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;
import com.touchgraph.graphlayout.graphelements.*;
import com.touchgraph.graphlayout.*;

/**
 * Mission cell organization is handled by a three-way relationship between 
 * trainers, operators, and leaders.  Once a recruit has become skilled enough 
 * according to a trainer, then he becomes and operator and looks to join a 
 * leader on a mission.  Operators can either come in contact with a leader by 
 * being introduced to a leader via a trainer who knows one, or by asking around 
 * the organization for leaders who need operators and receiving a response from
 * one of those leaders.
 * @author  Rob Michael and Zac Staples
 */
public class OrganizeMissionCellRelationship implements TNSRelationship {
    
    /**
     * The name of the relationship.
     */
    private String name;
    /**
     * <B>Not used by the TNS.</B>
     */
    private int relationshipID;
    /**
     * The collection of members of this relationship.
     */
    private Vector members;
    /**
     * A placeholder for the last agent to leave the relationship.  Used to
     * remove this relationship object from the master list of relationship
     * objects in the simulation.
     */
    private Agent lastAgent = null;
    /**
     * The names of the roles that participate in this relationship.
     */
    private Vector associatedRoles;
    
    /** Creates a new instance of OrganizeMissionCellRelationship */
    public OrganizeMissionCellRelationship() {
        name = new String("OrganizeMissionCellRelationship");
        members = new Vector();
        associatedRoles = new Vector();
        associatedRoles.add(new String("TrainerRole"));
        associatedRoles.add(new String("OperatorRole"));
        associatedRoles.add(new String("LeaderRole"));
//        System.out.println(name + " created.");
    } // end OrganizeMissionCellRelationship
    
    /** Adds the passed in agent to the list of members of this relationship.
     * @param pAgent The agent to be added to this relationship.
     * @return The success of adding the agent:
     * <li> true - pAgent is added
     * <li> false - pAgent was not added
     *
     */
    public boolean addAgent(Agent pAgent) {
        members.add(pAgent);
//        System.out.println(pAgent.getAgentName() + " added to " + this.toString());
        // Find out the role for the agent just added and check to see if it
        // is a trainer, operator, or leader.  If the role is an trainer, add
        // the trainer as a listener of the operator's JoinLeaderConnector.
        // The leader has no connectors involved in this relationship.

        // Get pAgent's Roles
        Vector roles = pAgent.getRoleVector();
        Enumeration e = roles.elements();
        while (e.hasMoreElements()) {
            Role r = (Role)e.nextElement();
            // Check Role type: Trainer, Operator, or Leader?
            if (r instanceof TrainerRole) { // if Trainer
                Vector operators = new Vector();
                Enumeration e2 = members.elements();
                while (e2.hasMoreElements()) {
                    Agent a = (Agent)e2.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e3 = memberRoles.elements();
                    while (e3.hasMoreElements()) {
                        Role agentRole = (Role)e3.nextElement();
                        if (agentRole instanceof OperatorRole) {
                            operators.add(agentRole);
                        } // end if-else
                    } // end while
                } // end while

                // Take the list of Operators and add self as a listener
                // for their JoinLeader connectors.
                for (int i = 0; i < operators.size(); i++) {
                    OperatorRole operatorRole = (OperatorRole)operators.elementAt(i);
                    Vector connectors = operatorRole.getConnectors();
                    Enumeration e7 = connectors.elements();
                    while (e7.hasMoreElements()) {
                        Connector c = (Connector)e7.nextElement();
                        if (c instanceof JoinLeaderConnector) {
/*                            System.out.println("Attempting to add " + ((TNSRole)r).getAgent().getAgentName() +
                                " as a listener to " + operatorRole.getAgent().getAgentName() +
                                "'s JoinLeaderConnector");*/
                            c.addConnectorChangeListener((TrainerRole)r);
                        } // end if-else
                    } // end while
                } // end for
                
            } else if (r instanceof OperatorRole) { // if Operator
                Vector trainers = new Vector();
                // Go through the members list and find all the Trainers
                Enumeration e2 = members.elements();
                while (e2.hasMoreElements()) {
                    Agent a = (Agent)e2.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e3 = memberRoles.elements();
                    while (e3.hasMoreElements()) {
                        Role agentRole = (Role)e3.nextElement();
                        if (agentRole instanceof TrainerRole) {
                            trainers.add(agentRole);
                        }// end if-else
                    } // end while
                } // end while

                // Take the list of Trainers and add them as listeners to the 
                // JoinLeader connector.
                Vector connectors = ((OperatorRole)r).getConnectors();
                Enumeration e7 = connectors.elements();
                while (e7.hasMoreElements()) {
                    Connector c = (Connector)e7.nextElement();
                    if (c instanceof JoinLeaderConnector) {
                        for (int i = 0; i < trainers.size(); i++) {
                            TrainerRole trainer = (TrainerRole)trainers.elementAt(i);
/*                            System.out.println("Attempting to add " + trainer.getAgent().getAgentName() +
                                " as a listener to " + ((TNSRole)r).getAgent().getAgentName() +
                                "'s JoinLeaderConnector");*/
                            c.addConnectorChangeListener(trainer);
                        } // end for
                    } // end if-else
                } // end while
            } else if (r instanceof LeaderRole) { // if Leader
                // Do nothing: No listeners to register
            } // end if-else
        } // end while
        return true;
    } // end addAgent
    
    /** Verifies conditions are maintained to continue this relationship.
     * <B> Not used in the TNS. </B>
     * @return The success of verifying conditions are maintained:
     * <li> true - Conditions are maintained
     * <li> false - Conditions are not maintained
     *
     */
    public boolean conditionsMaintained() {
        return false;
    } // end conditionsMaintained
    
    /** Verifies conditions are met to form this relationship.  If conditions are
     * met, this method adds the accepted agents to the members Vector.  This allows
     * the relationshipManager to assign membership to this relationship to the
     * agents relationship Hashtable.
     * <B> Not used in the TNS. </B>
     * @param pAgent The agent attempting to form the relationship
     * @param pSensedAgents A vector containing all of the sensed agents
     * @return The success of verifying conditions are met and addition of agents to
     * the members vector.
     * <li> true - Conditions are met
     * <li> false - Conditions are not met
     *
     */
    public boolean conditionsMet(Agent pAgent, Vector pSensedAgents) {
        return true;
    } // end conditionsMet
    
    /** When conditions are no longer met to maintain this relationship, this method
     * is called.  It removes all reference to itself from agents in its members
     * list as well as the RelationshipManager
     *
     */
    public void destroyRelationship() {
        //remove this relationship from the relationshipManagers
        //formedRelationships vector.  This will effectively remove all
        //references to the relationship and garbage collection will take care
        //of the rest

//        System.out.println(this.toString() + " being destroyed.");
        if (lastAgent != null) {
            Vector relationships = lastAgent.getRelationshipManager().getFormedRelationships();
            for (int ix = 0; ix < relationships.size(); ix++) {
                Relationship tempRelationship =
                                      (Relationship)relationships.elementAt(ix);
                if (tempRelationship.getClassName().equals(name)) {
                    Vector tempMembers = tempRelationship.getMembers();
                    if (tempMembers.size() == 0) {
                        relationships.remove(tempRelationship);
                        ix--;
                    }//end if
                }//end if
            }//end for
        }//end if
    } // end destroyRelationship
    
    /** Returns The class name of this relationship.
     * @return Class name
     *
     */
    public String getClassName() { return name; }
    
    /** Returns the ID number of this relationship.
     * @return ID number
     *
     */
    public int getIDNumber() { return relationshipID; }
    
    /** Returns the vector of members currently in this relationship
     * @return members
     *
     */
    public Vector getMembers() { return members; }
    
    /** Issues the appropriate roles to each member in the relationship.
     *
     */
    public void issueRoles() {
    }
    
    /** Removes the passed in agent from the list of members of this relationship.
     * @param pAgent The agent to be removed from this relationship.
     * @return The success of removing the agent:
     * <li> true - pAgent is removed
     * <li> false - pAgent was not removed
     *
     */
    public boolean removeAgent(Agent pAgent) {
        // This method works in reverse of the addAgent method.
        // Find out the role for the agent being removed and check to see if it
        // is a trainer, operator, or leader.  If the role is an trainer, remove
        // the trainer as a listener of the operator's JoinLeaderConnector.
        // The leader has no connectors involved in this relationship.
        
        // Get pAgent's Roles
        Vector roles = pAgent.getRoleVector();
        Enumeration e = roles.elements();
        while (e.hasMoreElements()) {
            Role r = (Role)e.nextElement();
            // Check Role type: Trainer, Operator, or Leader?
            if (r instanceof TrainerRole) { // if Trainer
                Vector operators = new Vector();
                // Go through the members list and find all the Operators
                Enumeration e2 = members.elements();
                while (e2.hasMoreElements()) {
                    Agent a = (Agent)e2.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e3 = memberRoles.elements();
                    while (e3.hasMoreElements()) {
                        Role agentRole = (Role)e3.nextElement();
                        if (agentRole instanceof OperatorRole) {
                            operators.add(agentRole);
                        } // end if-else
                    } // end while
                } // end while

                // Take the list of Operators and add self as a listener
                // for their JoinLeader connectors.
                for (int i = 0; i < operators.size(); i++) {
                    OperatorRole operatorRole = (OperatorRole)operators.elementAt(i);
                    Vector connectors = operatorRole.getConnectors();
                    Enumeration e7 = connectors.elements();
                    while (e7.hasMoreElements()) {
                        Connector c = (Connector)e7.nextElement();
                        if (c instanceof JoinLeaderConnector) {
/*                            System.out.println("Attempting to remove " + ((TNSRole)r).getAgent().getAgentName() +
                                " as a listener from " + operatorRole.getAgent().getAgentName() +
                                "'s JoinLeaderConnector");*/
                            c.removeConnectorChangeListener((TrainerRole)r);
                        } // end if-else
                    } // end while
                } // end for
                
            } else if (r instanceof OperatorRole) { // if Operator
                Vector trainers = new Vector();
                // Go through the members list and find all the Trainers
                Enumeration e2 = members.elements();
                while (e2.hasMoreElements()) {
                    Agent a = (Agent)e2.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e3 = memberRoles.elements();
                    while (e3.hasMoreElements()) {
                        Role agentRole = (Role)e3.nextElement();
                        if (agentRole instanceof TrainerRole) {
                            trainers.add(agentRole);
                        }// end if-else
                    } // end while
                } // end while

                // Take the list of Trainers and add them as listeners to the 
                // JoinLeader connector.
                Vector connectors = ((TNSRole)r).getConnectors();
                Enumeration e7 = connectors.elements();
                while (e7.hasMoreElements()) {
                    Connector c = (Connector)e7.nextElement();
                    if (c instanceof JoinLeaderConnector) {
                        for (int i = 0; i < trainers.size(); i++) {
                            TrainerRole trainer = (TrainerRole)trainers.elementAt(i);
/*                            System.out.println("Attempting to remove " + trainer.getAgent().getAgentName() +
                                " as a listener from " + ((TNSRole)r).getAgent().getAgentName() +
                                "'s JoinLeaderConnector");*/
                            c.removeConnectorChangeListener(trainer);
                        } // end for
                    } // end if-else
                } // end while
            } else if (r instanceof LeaderRole) { // if Leader
                // Do nothing: No listeners to register
            } // end if-else
        } // end while
        members.remove(pAgent);
//        System.out.println(pAgent.getAgentName() + " removed from " + this.toString());
        Hashtable relationshipTable = pAgent.getRelationshipTable();
        e = relationshipTable.keys();
        while (e.hasMoreElements()) {
            String relationshipName = (String)e.nextElement();
            Object temp = relationshipTable.get(relationshipName);
            if (temp instanceof Vector) {
                Vector removeRelationships = (Vector)temp;
/*                System.out.println(pAgent.getEntityName() + " has " + removeRelationships.size() +
                                   " relationship" + (removeRelationships.size() > 1 ? "s " : " ") +
                                   " of type " + relationshipName);*/
                Enumeration e2 = removeRelationships.elements();
                while (e2.hasMoreElements()) {
                    Relationship removeRelationship = (Relationship)e2.nextElement();
                    if (removeRelationship.equals(this)) {
//                        System.out.println(pAgent.getEntityName() + " removing " + this.toString() + " from the Vector of relationships.");
                        removeRelationships.remove(removeRelationship);
                    } // end if
                } // end while
/*                System.out.println("Remaining relationships:");
                e2 = removeRelationships.elements();
                while (e2.hasMoreElements()) {
                    Relationship r = (Relationship)e2.nextElement();
                    System.out.println(r.toString());
                } // end while*/
                if (removeRelationships.size() == 0) {
//                    System.out.println(pAgent.getEntityName() + " has no more relationships of this type.  Adding placeholder to hashtable.");
                    pAgent.getRelationshipTable().put(name, new Integer(32));
                } // end if
            } // end if
        } // end while
        if (members.size() <= 0) {
            lastAgent = pAgent;
            destroyRelationship();
        }//end if
        return true;
    } // end removeAgent
    
    /** Sets the class name of this relationship
     * @param pClassName The new class name
     *
     */
    public void setClassName(String pClassName) {
        name = pClassName;
    } // end setClassName
    
    /** Sets the ID number of this relationship
     * @param pID The new ID number
     *
     */
    public void setIDNumber(int pID) {
        relationshipID = pID;
    } // end setIDNumber
    
    /**
     * Returns the names of the roles that participate in this relationship.
     * @return Vector The names of the roles that participate in this
     * relationship.
     */
    public Vector getAssociatedRoles() { return associatedRoles; }
    
} // end class OrganizeMissionCellRelationship
