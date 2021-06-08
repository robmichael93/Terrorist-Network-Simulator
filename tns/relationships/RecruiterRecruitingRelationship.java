package tns.relationships;
import tns.roles.*;
import tns.connectors.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Leaders and recruiters share a relationship when leaders sense other 
 * recruiters in the environment that are not directly linked to the leader.  
 * This relationship models the fact that leaders seek out locally 
 * knowledgeable, influential, and willing individuals who can assist the leader 
 * in adding numbers to the organization’s roster.  Without operators in the 
 * organization, the organization cannot grow, so recruiters perform half of the 
 * critical role of feeding these individuals to the leaders.
 * @author  Rob Michael and Zac Staples
 */
public class RecruiterRecruitingRelationship implements TNSRelationship {
    
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
    
    /** Creates a new instance of OperatorRecruitingRelationship */
    public RecruiterRecruitingRelationship() {
        name = new String("RecruiterRecruitingRelationship");
        members = new Vector();
        associatedRoles = new Vector();
        associatedRoles.add(new String("LeaderRole"));
        associatedRoles.add(new String("RecruiterRole"));
//        System.out.println(name + " created.");
    } // end RecruiterRecruitingRelationship
    
    /** Adds the passed in agent to the list of members of this relationship.
     * @param pAgent The agent to be added to this relationship.
     * @return The success of adding the agent:
     * <li> true - pAgent is added
     * <li> false - pAgent was not added
     *
     */
    public boolean addAgent(Agent pAgent) {
        if (!members.contains(pAgent)) {
            members.add(pAgent);
//            System.out.println(pAgent.getAgentName() + " added to " + this.toString());
        // Find out the role for the agent just added and check to see if it
        // is a leader or recruiter, then add the recruiter as a listener
        // to the leader's FindRecruiterConnector.

            // Get pAgent's Roles
            Vector roles = pAgent.getRoleVector();
            Enumeration e = roles.elements();
            while (e.hasMoreElements()) {
                Role r = (Role)e.nextElement();
                // Check Role type: Leader or Recruiter?
                if (r instanceof LeaderRole) { // if Leader
                    Vector recruiters = new Vector();
                    // Go through the members list and find all the Recruiters
                    Enumeration e2 = members.elements();
                    while (e2.hasMoreElements()) {
                        Agent a = (Agent)e2.nextElement();
                        // take each Agent and find the roles
                        Vector memberRoles = a.getRoleVector();
                        Enumeration e3 = memberRoles.elements();
                        while (e3.hasMoreElements()) {
                            Role agentRole = (Role)e3.nextElement();
                            // look for Recruiter roles & add to temp container
                            if (agentRole instanceof RecruiterRole) {
                                recruiters.add(agentRole);
                            } // end if
                        } // end while
                    } // end while
                    Vector connectors = ((LeaderRole)r).getConnectors();
                    Enumeration e7 = connectors.elements();
                    while (e7.hasMoreElements()) {
                        Connector c = (Connector)e7.nextElement();
                        if (c instanceof FindRecruiterConnector) {
                            for (int i = 0; i < recruiters.size(); i++) {
                                RecruiterRole recruiter = (RecruiterRole)recruiters.elementAt(i);
/*                                System.out.println("Attempting to add " + recruiter.getAgent().getAgentName() +
                                    " as a listener to " + ((TNSRole)r).getAgent().getAgentName() +
                                    "'s FindRecruiterConnector");*/
                                c.addConnectorChangeListener(recruiter);
                            } // end for
                        } // end if
                    } // end while
                    
                } else if (r instanceof RecruiterRole) { // if Recruiter
                    Vector leaders = new Vector();
                    Enumeration e5 = members.elements();
                    while (e5.hasMoreElements()) {
                        Agent a = (Agent)e5.nextElement();
                        // take each Agent and find the roles
                        Vector memberRoles = a.getRoleVector();
                        Enumeration e6 = memberRoles.elements();
                        while (e6.hasMoreElements()) {
                            Role agentRole = (Role)e6.nextElement();
                            if (agentRole instanceof LeaderRole) {
                                leaders.add(agentRole);
                            } // end if
                        } // end while
                    } // end while

                    for (int i = 0; i < leaders.size(); i++) {
                        LeaderRole leaderRole = (LeaderRole) leaders.elementAt(i);
                        Vector connectors = leaderRole.getConnectors();
                        Enumeration e4 = connectors.elements();
                        while (e4.hasMoreElements()) {
                            Connector c = (Connector)e4.nextElement();
                            if (c instanceof FindRecruiterConnector) {
/*                                System.out.println("Attempting to add " + ((TNSRole)r).getAgent().getAgentName() +
                                    " as a listener to " + leaderRole.getAgent().getAgentName() +
                                    "'s FindRecruiterConnector");*/
                                c.addConnectorChangeListener((RecruiterRole)r);
                            } // end if
                        } // end while
                    } // end for
                } // end if-else
            } // end while
            return true;
        } else {
            return false;
        } // end if-else
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
    public void issueRoles() {}
    
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
        // is a leader or recruiter, then remove the recruiter as a listener
        // to the leader's FindRecruiterConnector.
        
        // Get pAgent's Roles
        Vector roles = pAgent.getRoleVector();
        Enumeration e = roles.elements();
        while (e.hasMoreElements()) {
            Role r = (Role)e.nextElement();
            // Check Role type: Leader or Recruiter?
            if (r instanceof LeaderRole) { // if Leader
                Vector recruiters = new Vector();
                // Go through the members list and find all the Recruiters
                Enumeration e2 = members.elements();
                while (e2.hasMoreElements()) {
                    Agent a = (Agent)e2.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e3 = memberRoles.elements();
                    while (e3.hasMoreElements()) {
                        Role agentRole = (Role)e3.nextElement();
                        // look for Recruiter roles & add to temp container
                        if (agentRole instanceof RecruiterRole) {
                            recruiters.add(agentRole);
                        } // end if
                    } // end while
                } // end while

                Vector connectors = ((LeaderRole)r).getConnectors();
                Enumeration e7 = connectors.elements();
                while (e7.hasMoreElements()) {
                    Connector c = (Connector)e7.nextElement();
                    if (c instanceof FindRecruiterConnector) {
                        for (int i = 0; i < recruiters.size(); i++) {
                            RecruiterRole recruiter = (RecruiterRole)recruiters.elementAt(i);
/*                                System.out.println("Attempting to add " + contact.getAgent().getAgentName() +
                                " as a listener to " + ((TNSRole)r).getAgent().getAgentName() +
                                "'s FindContactConnector");*/
                            c.removeConnectorChangeListener(recruiter);
                        } // end for
                    } // end if
                } // end while
                
            } else if (r instanceof RecruiterRole) { // if Recruiter
                Vector leaders = new Vector();
                Enumeration e5 = members.elements();
                while (e5.hasMoreElements()) {
                    Agent a = (Agent)e5.nextElement();
                    // take each Agent and find the roles
                    Vector memberRoles = a.getRoleVector();
                    Enumeration e6 = memberRoles.elements();
                    while (e6.hasMoreElements()) {
                        Role agentRole = (Role)e6.nextElement();
                        if (agentRole instanceof LeaderRole) {
                            leaders.add(agentRole);
                        } // end if
                    } // end while
                } // end while

                for (int i = 0; i < leaders.size(); i++) {
                    LeaderRole leaderRole = (LeaderRole) leaders.elementAt(i);
                    // get the Recruiter's connectors (currently only has one)
                    Vector connectors = leaderRole.getConnectors();
                    Enumeration e4 = connectors.elements();
                    while (e4.hasMoreElements()) {
                        Connector c = (Connector)e4.nextElement();
                        if (c instanceof FindRecruiterConnector) {
/*                                System.out.println("Attempting to add " + ((TNSRole)r).getAgent().getAgentName() +
                                " as a listener to " + recruitRole.getAgent().getAgentName() +
                                "'s FindContactConnector");*/
                            c.removeConnectorChangeListener((RecruiterRole)r);
                        } // end if
                    } // end while
                } // end for
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
    
} // end class RecruiterRecruitingRelationship
