package mil.navy.nps.relate;import java.util.*;/******************************************************************************* * <p><b>Relationships are the life-blood of the RELATE architecture.</b></p>
 *
 * <p>One of the most important aspects of a Java class that implements the
 * Relationship interface is that it must have a no-argument constructor.  As
 * described above, this allows the RelationshipManager to create it dynamically
 * to verify prerequisites and assign members. Relationships have a
 * conditionsMaintained() method that is used by the RelationshipManager to
 * verify prerequisites are met prior to creating the relationship.  Once
 * created by the RelationshipManager, the Relationship objects are independent
 * agents that issue roles to each member agent using the issueRoles() method.
 * They also monitor conditions of, their members.  They also destroy themselves
 * if minimum requirements for existence are not maintained using the method
 * destroyRelationship().  When this happens, the relationship withdrawals all
 * of its associated Role objects from each member, then removes itself from the
 * RelationshipManager's active relationship vector.</p>
 *
 * <p>See the <a href="RelationshipManager.html">RelationshipManager</a> class
 * for more details on how relationships interact with the
 * RelationshipManager.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Relationship
{

/*******************************************************************************
 * Verifies conditions are met to form this relationship.  If conditions are
 * met, this method adds the accepted agents to the members Vector.  This allows
 * the relationshipManager to assign membership to this relationship to the
 * agents relationship Hashtable.
 *
 * @param pAgent The agent attempting to form the relationship
 * @param pSensedAgents A vector containing all of the sensed agents
 * @return The success of verifying conditions are met and addition of agents to
 * the members vector.
 * <li> true - Conditions are met
 * <li> false - Conditions are not met
 *******************************************************************************
 */
 public boolean conditionsMet( Agent pAgent, Vector pSensedAgents );


/*******************************************************************************
 * Verifies conditions are maintained to continue this relationship.
 * @return The success of verifying conditions are maintained:
 * <li> true - Conditions are maintained
 * <li> false - Conditions are not maintained
 *******************************************************************************
 */
 public boolean conditionsMaintained();


/*******************************************************************************
 * When conditions are no longer met to maintain this relationship, this method
 * is called.  It removes all reference to itself from agents in its members
 * list as well as the RelationshipManager
 *******************************************************************************
 */
 public void destroyRelationship();


 /*******************************************************************************
 * Issues the appropriate roles to each member in the relationship.
 *******************************************************************************
 */
 public void issueRoles();

 
/*******************************************************************************
 * Adds the passed in agent to the list of members of this relationship.
 * @param pAgent The agent to be added to this relationship.
 * @return The success of adding the agent:
 * <li> true - pAgent is added
 * <li> false - pAgent was not added
 *******************************************************************************
 */
 public boolean addAgent(Agent pAgent);


/*******************************************************************************
 * Removes the passed in agent from the list of members of this relationship.
 * @param pAgent The agent to be removed from this relationship.
 * @return The success of removing the agent:
 * <li> true - pAgent is removed
 * <li> false - pAgent was not removed
 *******************************************************************************
 */
 public boolean removeAgent(Agent pAgent);


/*******************************************************************************
 * Returns the ID number of this relationship.
 * @return ID number
 *******************************************************************************
 */
 public int getIDNumber();


/*******************************************************************************
 * Sets the ID number of this relationship
 * @param pID The new ID number
 *******************************************************************************
 */
 public void setIDNumber(int pID);


/*******************************************************************************
 * Sets the class name of this relationship
 * @param pClassName The new class name
 *******************************************************************************
 */
 public void setClassName(String pClassName);


/*******************************************************************************
 * Returns The class name of this relationship.
 * @return Class name
 *******************************************************************************
 */
 public String getClassName();


/*******************************************************************************
 * Returns the vector of members currently in this relationship
 * @return members
 *******************************************************************************
 */
 public Vector getMembers();


/*******************************************************************************
 * Returns a formated description of this relationship suitable for printing
 * @return Relationship string description
 *******************************************************************************
 */
 public String toString();


}// end Relationship class


