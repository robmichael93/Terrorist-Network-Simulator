package mil.navy.nps.relate;

import com.touchgraph.graphlayout.graphelements.*;
import com.touchgraph.graphlayout.*;
import tns.agents.*;
import tns.relationships.*;
import tns.util.*;
import java.util.*;

/*******************************************************************************
 * <p><b>RelationshipManager is the heart of the RELATE simulation
 * package.</b></p>
 *
 * <p>RelationshipManager is an example of the "singleton" programming pattern.
 * "Singleton" means that there is one, and only one, instance of this class per
 * application.  This is accomplished by the static, synchronized
 * getRelationshipManager() method.  This method creates a new
 * RelationshipManager upon the first request, or returns the existing, unique
 * RelationshipManager if it has already been created.  The RelationshipManager
 * is the only complete Java class in RELATE.  All other classes are either
 * abstract or interfaces.  An abstract class contains one or more abstract
 * methods that are required to be defined by the developer.  An interface has
 * no data members and all methods must be defined by the developer.</p>
 *
 * <p>The RelationshipManager handles the formation and administration of all
 * relationships by requiring agents to form relationships with the
 * checkForRelationships() method.  This method is the most significant method
 * in this class.  It checks for every possible relationship that can be formed
 * between the requesting, passed in agent and other agents in its
 * sensedEnvironment.  Since the requirements for formation of new relationships
 * are defined within the individual relationships, the RelationshipManager
 * instantiates the requested relationship using the createRelationship()
 * method.  This is possible due to the no-argument constructor used in the
 * Relationship interface.  If conditions are met, it adds the agent to this new
 * Relationship.  Otherwise, the relationship is never utilized and it is
 * cleaned up with the automatic garbage collection feature of Java.
 * Relationship administration is accomplished by maintaining a current list, or
 * vector, of active relationships, available through a getter method.  The
 * RelationshipManager adds relationships to this vector, but they are removed
 * by the individual relationships.</p>
 *
 * <p>See the <a href="Relationship.html">Relationship</a> class for more
 * details on how relationships interact with the RelationshipManager.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public class RelationshipManager
{

/*******************************************************************************
 *Contains all currently active relationships in the simulation.
 *******************************************************************************
 */
 public Vector activeRelationshipVec;

/*******************************************************************************
 *Contains all agents that have made the checkForRelationships() call.
 *******************************************************************************
 */
 public Hashtable agentTable;

/*******************************************************************************
 *The static relationshipManager that is passed to all agents.
 *******************************************************************************
 */
 private static RelationshipManager relMan;

/****************************************************************************
 * Private no-argument constructor called from within the class via the
 * "singleton" pattern.
 ****************************************************************************
 */
 private RelationshipManager()
 {
    activeRelationshipVec = new Vector();
    agentTable = new Hashtable();
 }// end RelationshipManager()


/*******************************************************************************
 * Method that returns the singleton relationshipManager.
 * @return: A singleton RelationshipManager
 *******************************************************************************
 */
 public static synchronized RelationshipManager getRelationshipManager()
 {
    if( relMan == null )
    {
       relMan = new RelationshipManager();
    }// end if

    return relMan;
 }// end getRelationshipManager()

/*******************************************************************************
 * Adds the passed in agent to the agentTable Hashtable.
 * @param pAgent: The agent to be added to Hashtable
 *******************************************************************************
 */
 public void addAgent( Agent pAgent )
 {
    //Agent ID as key for Hashtable
    Long tempAgentID = new Long( pAgent.getAgentID() );
    agentTable.put( tempAgentID, pAgent );
 }// end addAgent()


/*******************************************************************************
 * Checks for all possible relationships between the passed in agent and all
 * of the agent's sensed agents.  If the agent can join a perviously formed
 * relationship, it is added and further checking for that relationship is
 * stopped.  If no perviously formed relationships can be joined, a new
 * relationship is formed and tested to see if its conditions are met with the
 * agent and all sensed agents that are not currently in that realtionship.  If
 * they are, then the relationship is added to the active realtionship vector
 * and the agents are issued roles/goals/rules from the new relationship.  If
 * conditions are not met the relationship is destroyed and the agent does not
 * join a relationship.
 *
 * @param pAgent: The agent to be checked for relationships
 *******************************************************************************
 */
    public void checkForRelationships( Agent agent ) {
        //temporary agent relationship HashTable
        Hashtable agentRelationships = agent.getRelationshipTable();
        Enumeration relEnum = agentRelationships.keys();

        while (relEnum.hasMoreElements()) {
            boolean appropriateRelationship = true;
            String relationshipName = (String)relEnum.nextElement();
            Object temp = agentRelationships.get(relationshipName);
            if (temp instanceof Vector && ((Vector)temp).size() > 0 &&
                    ((Vector)temp).firstElement() instanceof Relationship) {
                Vector relationships = (Vector)temp;
                Vector copyOfRelationshipVector = new Vector();
                Iterator i = relationships.iterator();
                while (i.hasNext()) {
                    Relationship r = (Relationship)i.next();
                    copyOfRelationshipVector.add(r);
                } // end while
                i = copyOfRelationshipVector.iterator();
                while (i.hasNext()) {
                    appropriateRelationship = true;
                    Relationship relationship = (Relationship)i.next();
                    Vector members = relationship.getMembers();
                    if (members.size() <= 1) {
                        appropriateRelationship = false;
                    } // end if
                    Vector associatedRoles = ((TNSRelationship)relationship).getAssociatedRoles();
                    Vector copyOfMemberVector = new Vector();
                    Iterator i2 = members.iterator();
                    while (i2.hasNext()) {
                        Agent a = (Agent)i2.next();
                        copyOfMemberVector.add(a);
                    } // end while
                    i2 = copyOfMemberVector.iterator();
                    while (i2.hasNext() && appropriateRelationship) {
                        Agent member = (Agent)i2.next();
                        boolean appropriateMember = false;
                        Vector roles = member.getRoleVector();
                        Iterator i3 = roles.iterator();
                        while (i3.hasNext() && !appropriateMember) {
                            Role role = (Role)i3.next();
                            String roleName = role.getRoleName();
                            Iterator i4 = associatedRoles.iterator();
                            while (i4.hasNext()) {
                                String associatedRole = (String)i4.next();
                                if (roleName.equalsIgnoreCase(associatedRole)) {
                                    appropriateMember = true;
                                } // end if
                            } // end while
                        } // end while
                        if (!appropriateMember) {
                            relationship.removeAgent(agent);
                        } // end if
                    } // end while
                    if (!appropriateRelationship) {
                        relationship.removeAgent(agent);
                    } // end if
                } // end while
            } // end if
        } // end while
        
        relEnum = agentRelationships.keys();
        //loop through to see if there are any relationships to be formed
        while( relEnum.hasMoreElements() ) {
            String relationshipClassName = (String)relEnum.nextElement();
//            System.out.println(agent.getEntityName() + " evaluating relationship " + relationshipClassName);

            //form a vector of (relationship common) agents in the sensed environment
            Vector sensedAgents = agent.getSensedEnvironment().getSensedAgents();

            //container for agents that can form this type of relationship
            Vector commonAgents = new Vector();

            for( int ax = 0; ax < sensedAgents.size(); ax++ ) {
                Agent a = (Agent)sensedAgents.elementAt( ax );
                Hashtable relationships = a.getRelationshipTable();
                if( relationships.containsKey( relationshipClassName ) ) {
/*                    System.out.println(a.getAgentName() +  " added to " + 
                                       agent.getAgentName() + "'s list of common agents for " + 
                                       relationshipClassName);*/
                    commonAgents.add( a );
                }//end if
            }//end for

            if (commonAgents.size() > 0) {
//               System.out.println("Common agents greater than 0...");
               Enumeration e = commonAgents.elements();
               while (e.hasMoreElements()) {
                    Agent commonAgent = (Agent) e.nextElement();
//                    System.out.println("Evaluating agent " + commonAgent.getEntityName());
                    Hashtable commonAgentRelationshipTable = commonAgent.getRelationshipTable();
                    Object commonAgentRelationships = commonAgentRelationshipTable.get(relationshipClassName);
                    Object possibleRelationships = agentRelationships.get(relationshipClassName);
                    // do I have a relationship of the type in question?
                    if (possibleRelationships instanceof Vector && 
                        ((Vector)possibleRelationships).size() > 0 &&
                        ((Vector)possibleRelationships).firstElement() instanceof Relationship) {
                        // is the agent I want in one of my relationships
                        // if yes, then skip that agent
                        int n = ((Vector)possibleRelationships).size();
/*                        System.out.println(agent.getEntityName() + " has " + n +
                                           (n > 1 ? " relationships" : " relationship") + 
                                           " of type " + relationshipClassName);*/
                        Enumeration e2 = ((Vector)possibleRelationships).elements();
                        boolean inRelationship = false;
                        while (e2.hasMoreElements() && !inRelationship) {
                            Relationship relationship = (Relationship)e2.nextElement();
//                            System.out.println("Checking relationship " + relationship.toString());
                            if (relationship.getMembers().contains(commonAgent)) {
//                                System.out.println(commonAgent.getEntityName() + " belongs to this relationship.");
                                inRelationship = true;
                            } else { // still not in one of my relationships
                                // is the agent in question maximally connected with the
                                // members of this relationship?
                                if (isMaxConnected(commonAgent, relationship)) {
//                                    System.out.println(commonAgent.getEntityName() + " is maximally connected in the relationship.");
                                    relationship.addAgent(commonAgent);
                                    Object relationships = commonAgentRelationshipTable.get(relationshipClassName);
                                    if (relationships instanceof Vector && ((Vector)relationships).size() > 0 &&
                                        ((Vector)relationships).firstElement() instanceof Relationship) {
//                                        System.out.println(commonAgent.getEntityName() + " already has a relationship of this type. Adding to vector.");
                                        ((Vector)relationships).add(relationship);
//                                        System.out.println("New vector size: " + ((Vector)relationships).size());
                                    } else {
//                                        System.out.println(commonAgent.getEntityName() + " does not already have a relationship of this type.  Creating new vector.");
                                        Vector commonAgentsRelationships = new Vector();
                                        commonAgentsRelationships.add(relationship);
//                                        System.out.println("New vector size: " + ((Vector)commonAgentsRelationships).size());
                                        commonAgent.getRelationshipTable().put(relationshipClassName, commonAgentsRelationships);
                                    } // end if-else
                                    inRelationship = true; // set flag to stop processing relationships
                                } // end if
                            } // end if-else
                        } // end while
                        // if not maximally connected to any of the relationships, 
                        // then create a new relationship object and add myself
                        // and the agent in question
                        if (!inRelationship) {
//                            System.out.println(commonAgent.getEntityName() + " is NOT maximally connected to any of the relationships.");
                            createNewRelationship(relationshipClassName, agent, commonAgent);
                            inRelationship = true; // set flag to stop processing relationships
                        } // end if-else
                    // does the common agent in question have a Relationship object of
                    // the type being considered?
                    } else if (commonAgentRelationships instanceof Vector &&
                               ((Vector)commonAgentRelationships).size() > 0 &&
                               ((Vector)commonAgentRelationships).firstElement() instanceof Relationship) {
/*                        System.out.println(agent.getEntityName() + " DOES NOT HAVE a relationship of type " + 
                                           relationshipClassName + ", BUT " + commonAgent.getEntityName() + " does.");*/
                        int n = ((Vector)commonAgentRelationships).size();
/*                        System.out.println(commonAgent.getEntityName() + " has " + n +
                                           (n > 1 ? " relationships" : " relationship") + 
                                           " of type " + relationshipClassName);*/
                        // now ask am I maximally connected to the common agent in question
                        // if yes, then add me to the common agent's relationship
                        Enumeration e3 = ((Vector)commonAgentRelationships).elements();
                        boolean inRelationship = false;
                        while (e3.hasMoreElements() && !inRelationship) {
                            Relationship relationship = (Relationship)e3.nextElement();
//                            System.out.println("Checking relationship " + relationship.toString());
                            if (relationship.getMembers().contains(agent)) {
                                inRelationship = true;
//                                System.out.println(agent.getEntityName() + " belongs to this relationship.");
                            } else { // still not in one of my relationships
                                // is the agent in question maximally connected with the
                                // members of this relationship?
                                if (isMaxConnected(agent, relationship)) {
//                                    System.out.println(agent.getEntityName() + " is maximally connected in the relationship.");
                                    relationship.addAgent(agent);
                                    Object relationships = agentRelationships.get(relationshipClassName);
                                    if (relationships instanceof Vector && ((Vector)relationships).size() > 0 &&
                                        ((Vector)relationships).firstElement() instanceof Relationship) {
//                                        System.out.println(agent.getEntityName() + " already has a relationship of this type. Adding to vector.");
                                        ((Vector)relationships).add(relationship);
//                                        System.out.println("New vector size: " + ((Vector)relationships).size());
                                    } else {
//                                        System.out.println(agent.getEntityName() + " does not already have a relationship of this type.  Creating new vector.");
                                        Vector agentsRelationships = new Vector();
                                        agentsRelationships.add(relationship);
//                                        System.out.println("New vector size: " + ((Vector)agentsRelationships).size());
                                        agent.getRelationshipTable().put(relationshipClassName, agentsRelationships);
                                    } // end if-else            
//                                    relationship.addAgent(commonAgent);
                                    inRelationship = true; // set flag to stop processing relationships
                                } // end if
                            } // end if-else
                        } // end while
                        // if I'm not maximally connected to any of the common agents'
                        // relationships, then create a new relationship and add us both to it.
                        if (!inRelationship) {
//                            System.out.println(agent.getEntityName() + " is NOT maximally connected to any of the relationships.");
                            createNewRelationship(relationshipClassName, agent, commonAgent);
                            inRelationship = true; // set flag to stop processing relationships
                        } // end if
                    // if neither of us has a Relationship object of the type in question,
                    // then create a new one and add us both to it.
                    } else {
/*                        System.out.println("Neither " + agent.getEntityName() + " nor " + commonAgent.getEntityName() +
                                           " have a relationshp of type " + relationshipClassName);*/
                        createNewRelationship(relationshipClassName, agent, commonAgent);
                    } // end if-else
               } // end while
            } else {
//                System.out.println("Not enough agents to create a relationship.");
            } // end if-else
        }// end while
    }//end checkForRelationships()


    /*******************************************************************************
    * Creates the Relationship the corresponds to the passed in class name.
    * Relationships have no-argument constructors, thus they have the ability to
    * be formed with this method.
    *
    * @param className: The className of the Relationship to be created
    * @return: The created Relationship.
    *******************************************************************************
    */
    private Relationship createRelationship( String className ) {
//        System.out.println("Attempting to create class " + className);
        Class cl = null;
        try
        {
           cl = Class.forName( "tns.relationships." + className );
        }//end try
        catch( ClassNotFoundException e )
        {
           System.err.println( "Can't find your darned class." );
           System.exit( 0 );
        }//end catch

        Object o = null;
        try
        {
           o = cl.newInstance();
        }//end try
        catch( InstantiationException e )
        {
           System.err.println( "Can't make your darned class." );
           System.exit( 0 );
        }//end catch
        catch( IllegalAccessException e )
        {
           System.err.println( "Can't access your darned class." );
           System.exit( 0 );
        }//end catch

        return (Relationship)o;

    }// end createRelationship()


    /*******************************************************************************
    * Simple getter method for getting the active relationship vector.
    * @return: The active relationship vector.
    *******************************************************************************
    */
    public Vector getFormedRelationships() { return activeRelationshipVec; }


    public boolean isMaxConnected(Agent agent, Relationship relationship) {
        boolean maxConnected = false;
        Vector members = relationship.getMembers();
        int proposedNodeCount = members.size() + 1;
//        System.out.println("Sub net node count for " + relationship.toString() + ": " + proposedNodeCount);
        SubNet subNet = new SubNet();
        Vector proposedMembers = new Vector();
        proposedMembers.addAll(members);
        proposedMembers.add(agent);
        Enumeration enumerate = proposedMembers.elements();
        while (enumerate.hasMoreElements()) {
            TerroristAgent ta = (TerroristAgent)enumerate.nextElement();
            MentalMap map = ta.getMentalMap();
//            System.out.println(ta.getEntityName() + " has " + map.getSubNet().numberOfUniquePairs() + " edges.");
            Iterator i = map.getSubNet().getUniquePairs().iterator();
            if (i != null) {
                while (i.hasNext()) {
                    AgentPair agentPair = (AgentPair)i.next();
//                    System.out.println("Evaluating edge " + agentPair.getFrom().getEntityName() + " to " + agentPair.getTo().getEntityName());
                    Agent toAgent = agentPair.getTo();
                    Agent fromAgent = agentPair.getFrom();
                    if (proposedMembers.contains(toAgent) && proposedMembers.contains(fromAgent)) {
//                        System.out.println("Sub net contains " + toAgent.getEntityName());
                        if (!subNet.contains(agentPair)) {
/*                            System.out.println("Sub net does not contain unique pair: " + fromAgent.getEntityName() +
                                               " and " + toAgent.getEntityName());*/
                            subNet.addPair(agentPair);
                        } // end if
                    } // end if
                } // end while
            } // end if
        } // end while
        int proposedLinkCount = subNet.numberOfUniquePairs();
//        System.out.println("Sub net edge count for " + relationship.toString() + ": " + proposedLinkCount);
        if ((proposedNodeCount * (proposedNodeCount - 1))/2 == proposedLinkCount) {
            maxConnected = true;
        } // end if
        return maxConnected;
    } // end isMaxConnected
    
    
    public void createNewRelationship(String relationshipClassName, Agent agent, Agent commonAgent) {
//        System.out.println("Attempting to create new " + relationshipClassName);
        Relationship newRelationship = createRelationship(relationshipClassName);
        newRelationship.addAgent(agent);
        newRelationship.addAgent(commonAgent);
        Hashtable agentRelationships = agent.getRelationshipTable();
        Hashtable commonAgentRelationships = commonAgent.getRelationshipTable();
        Object relationships = agentRelationships.get(relationshipClassName);
        if (relationships instanceof Vector && ((Vector)relationships).size() > 0 &&
            ((Vector)relationships).firstElement() instanceof Relationship) {
//            System.out.println(agent.getEntityName() + " already has a relationship of this type. Adding to vector.");
            ((Vector)relationships).add(newRelationship);
//            System.out.println("New vector size: " + ((Vector)relationships).size());
        } else {
//            System.out.println(agent.getEntityName() + " does not already have a relationship of this type.  Creating new vector.");
            Vector agentsRelationships = new Vector();
            agentsRelationships.add(newRelationship);
//            System.out.println("New vector size: " + ((Vector)agentsRelationships).size());
            agent.getRelationshipTable().put(relationshipClassName, agentsRelationships);
        } // end if-else            
        relationships = commonAgentRelationships.get(relationshipClassName);
        if (relationships instanceof Vector && ((Vector)relationships).size() > 0 &&
            ((Vector)relationships).firstElement() instanceof Relationship) {
//            System.out.println(commonAgent.getEntityName() + " already has a relationship of this type. Adding to vector.");
            ((Vector)relationships).add(newRelationship);
//            System.out.println("New vector size: " + ((Vector)relationships).size());
        } else {
//            System.out.println(commonAgent.getEntityName() + " does not already have a relationship of this type.  Creating new vector.");
            Vector commonAgentsRelationships = new Vector();
            commonAgentsRelationships.add(newRelationship);
//            System.out.println("New vector size: " + ((Vector)commonAgentsRelationships).size());
            commonAgent.getRelationshipTable().put(relationshipClassName, commonAgentsRelationships);
        } // end if-else
    } // end createNewRelationship
}// end RelationshipManager class





