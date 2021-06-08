package mil.navy.nps.relate;

import java.util.*;

/*******************************************************************************
 * <p><b>This abstract class implements the RELATE Agent.</b></p>
 *
 * <p>Since all Agents are objects, or things, Agent extends Thing, giving it
 * the ability to update and draw itself.  By the definition:</p>
 *
 * <li>an agent "...perceives its environment through sensors and acts
 * upon that environment through effectors to achieve one or more goals."
 *
 * <p>To satisfy these requirements, the developer must first establish a method
 * for the agent to gather information about the environment it exists in.  This
 * information is stored in the  sensedEnvironment data member.  This class
 * allows the developer to do this by defining the abstract
 * getSensedEnvironment() and setSensedEnvironment() methods to interact with
 * the simulation environment directly.  These methods should utilize the
 * sensorList hashtable to define what each agent is capable of sensing, and
 * only allow the agent to sense the specific attributes of the environment that
 * its current sensor list can detect or discern.</p>
 *
 * <p>Next, the agent must select an appropriate action.  Two things are needed
 * for this:  actions that can be selected, and a method or methods to select
 * these actions.  The actions each agent can take are simulation-dependent and
 * must be defined by the developer.  For simpler simulations, actions can be
 * built into the Agent class itself.  An Action interface is provided as a tool
 * for the developer designing more complex simulations. The mechanism for
 * selecting actions is based upon the relationships each agent forms, and the
 * associated roles and goals that the agent attempts to fulfill.</p>
 *
 * <p>Agents have a relationship hashtable that the developer must fill with the
 * class names of all the potential relationships that the agent can form. This
 * can be accomplished by using the addRelationshipName() method.</p>  
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public abstract class Agent extends Thing
{

/*******************************************************************************
 * Contains the roles that this agent is currently fulfilling.
 *******************************************************************************
 */
 protected Vector roleVec;


/*******************************************************************************
 * Contains all relationships that this agent is capable of being a member of.
 * Relationships in this hash table may or may not be instantiated, depending
 * on whether or not they are formed.  You can think of this as a collection of
 * balloon strings that the agent is holding.  These strings are waiting for
 * a specific relationship to form to attach themselves to.  When a relationship
 * is formed, each agent that is a member of that relationship attaches its
 * string to the single relationship balloon (which is actually a kind of agent
 * itself - see Relationship class).
 *******************************************************************************
 */
 protected Hashtable relationshipTable;


/*******************************************************************************
 * Contains the sensors that this agent possesses.
 *******************************************************************************
 */
 private Hashtable sensorList;


/*******************************************************************************
 * Contains the actions that this agent possesses.
 *******************************************************************************
 */
 protected Hashtable actionList;


/*******************************************************************************
 * Hashtable for goalWeights.
 *******************************************************************************
 */
 protected Hashtable goalWeight;


/*******************************************************************************
 * Hashtable for goalWeights.
 *******************************************************************************
 */
 protected Hashtable goalList;


/*******************************************************************************
 * Active role.  If the developer designs a simulation with conflicting roles,
 * this is used to store the active one.
 *******************************************************************************
 */
 protected Role activeRole = null;


/*******************************************************************************
 * Active goal.  Used to identify the active goal when only using one type of
 * goal in the simulation.
 *******************************************************************************
 */
 protected Goal activeGoal = null;


/*******************************************************************************
 * Past active goal.  Used for comparision during goal weight calculations.
 *******************************************************************************
 */
 protected Goal pastActiveGoal;


/*******************************************************************************
 * Agents sensed environment.  Also known as perceived environment.  This is
 * developed by using the agents sensor capabilities to filter perfect
 * knowledge from the environment. The sensed environment is used to provide
 * feedback to actions taken, and provide input to the decision making process.
 *******************************************************************************
 */
 protected SensedEnvironment sensedEnvironment;


/*******************************************************************************
 * Agents personality.  Used when making decisions, determining goal priority,
 * and providing feedback to rules.
 *******************************************************************************
 */
 protected Personality personality;


/*******************************************************************************
 * A "singleton" object that handles all relationships for this agent.  Used as
 * a conduit to forming or joining all possible realtionships this agent can be
 * a part of.
 *******************************************************************************
 */
 protected RelationshipManager relationshipManager;


/*******************************************************************************
 * Constructor for this Agent
 * @param pName: The agent's string name representation
 * @param pID: unique ID number
 *******************************************************************************
 */
 public Agent( long pID, String pName )
 {
    super( pID, pName );
    roleVec = new Vector();
    goalWeight = new Hashtable();
    goalList = new Hashtable();
    relationshipTable = new Hashtable();
    relationshipManager = RelationshipManager.getRelationshipManager();
    relationshipManager.addAgent( this );

 }


 /*******************************************************************************
 * Gets the SensedEnvironment.
 * @return A SensedEnvironment containing the perceived environement.
 *******************************************************************************
 */
 public abstract SensedEnvironment getSensedEnvironment();


/*******************************************************************************
 * Sets the agents SensedEnvironment to the passed in object
 * @param pPEnv: SensedEnvironment object for use by this agent.
 *******************************************************************************
 */
 public abstract void setSensedEnvironment( SensedEnvironment pPEnv );


/*******************************************************************************
 * Assigns credit to current goal set.  This should call assignCredit() in all
 * of the agents held goals. The result of these calls will assign weights to
 * all goals.  These weights will determine the current active goal for each
 * goaltype held.
 *******************************************************************************
 */
 public abstract void assignCredit( );


 /*******************************************************************************
 * Adds the passed in class name of the relationship in question.  The integer
 * 32 is set as the element in the hashtable.  This integer is used by various
 * classes to check to see if the agent is actually a member of a relationship.
 * If the agent is a member, a relationship object replaces the integer.
 * @param pRelationshipName: String class name of the relationship.
 *******************************************************************************
 */
 public void addRelationshipName( String pRelationshipName )
 {
    relationshipTable.put( pRelationshipName, new Integer( 32 ) );
 }


/*******************************************************************************
 * Adds the passed in class name vector of the agents possible relationships.
 * The integer 32 is set as the element in the hashtable.  This integer is used
 * by various classes to check to see if the agent is actually a member of a
 * relationship.  If the agent is a member, a relationship object replaces the
 * integer.
 * @param pRelationshipName: String class name of the relationship.
 *******************************************************************************
 */
 public void addRelationshipNames(Vector pRelationshipNames)
 {
    for( int ix = 0; ix < pRelationshipNames.size(); ix++ )
    {
       String tempName = (String)pRelationshipNames.elementAt( ix );
       relationshipTable.put( tempName, new Integer( 32 ) );
    }
 }


/*******************************************************************************
 * Removes the associated relationship corresponding to the passed in
 * relationship class name from the relationship hashtable.  Replaces the
 * relationship object with an integer.
 *
 *******************************************************************************
 */
 public void removeRelationshipName( String pRelationshipName )
 {
    relationshipTable.put( pRelationshipName, new Integer( 32 ) );
 }


/*******************************************************************************
 * Adds the passed in role to the role vector.  It then adds the new Role's
 * Goals to the goalList.
 * @param pRole The new Role being assigned
 *******************************************************************************
 */
 public void addRole( Role pRole )
 {
    Role temp = pRole;
    roleVec.add( temp );
    Vector tempVec = temp.getGoalListVec();
    for( int ix = 0; ix < tempVec.size(); ix++ )
    {
       Goal tempGoal = (Goal)tempVec.get( ix );
       //store these in a hashtable with the goal name being the key and the
       //goal being the element.
       String tempString = tempGoal.getGoalName();
       goalList.put( tempString, tempGoal );
    }
 }


/*******************************************************************************
 * Removes the passed in role from the role vector. It then removes the roles
 * associated goals from the goalList.
 * @param pRole  The Role that is to be removed
 * @return The success of the removal operation
 *******************************************************************************
 */
 public boolean removeRole ( Role pRole )
 {
    //first remove the role from the role vector
    Role temp = pRole;
    roleVec.remove( temp );
    //now we need to get it's goals and pull them out of the agents
    //goal list, remember these goals have unique names so getting
    //the right ones should be simple
    Vector tempVec = temp.getGoalListVec();
    for( int ix = 0; ix < tempVec.size(); ix++ )
    {
       Goal tempGoal = (Goal)tempVec.get( ix );
       String tempString = tempGoal.getGoalName();
       goalList.remove( tempString );
    }
    return true;
 }


/*******************************************************************************
 * checkForRelationships() calls the relationshipManagers method of the same
 * name.  This is the mechanism that allows the agent to join or form a
 * designeted relationship.
 *******************************************************************************
 */
 public void checkForRelationships( )
 {
    relationshipManager.checkForRelationships( this );
 }


/*******************************************************************************
 * Gets the active role object.
 * @return  The active Role for this Agent.
 *******************************************************************************
 */
 public Role getActiveRole() { return activeRole; }


/*******************************************************************************
 * Gets the active goal object.
 * @return  The active Goal for this Agent.
 *******************************************************************************
 */
 public Goal getActiveGoal() { return activeGoal; }


/*******************************************************************************
 * Sets the active goal
 * @param pGoal  The Agents new active goal.
 *******************************************************************************
 */
 public void setActiveGoal( Goal pGoal ) { activeGoal = pGoal; }


/*******************************************************************************
 * Gets the Agents past active goal
 * @return  This Agent's past active Goal.
 *******************************************************************************
 */
 public Goal getPastActiveGoal() { return pastActiveGoal; }


/*******************************************************************************
 * Sets the Agents past active goal
 * @param pGoal  The Agent's past active goal.
 *******************************************************************************
 */
 public void setPastActiveGoal( Goal pGoal ) { pastActiveGoal = pGoal; }


/*******************************************************************************
 * Gets the Agent's goalList
 * @return goalList
 *******************************************************************************
 */
 public Hashtable getGoalList() { return goalList; }


/*******************************************************************************
 * Gets the Agent's sensorList
 * @return sensorList
 *******************************************************************************
 */
 public Hashtable getSensorList() { return sensorList; }


/*******************************************************************************
 * Sets the Agent's sensorList
 * @param pSensorList
 *******************************************************************************
 */
 public void setSensorList( Hashtable pSensorList ) {sensorList = pSensorList;}


/*******************************************************************************
 * Gets the Agent's ID by using the super's getEntityID method
 * @return agentID
 *******************************************************************************
 */
 public long getAgentID( ) { return super.getEntityID(); }


/*******************************************************************************
 * Sets the Agent's ID by using the super's setEntityID method
 * @param pID: new agentID number
 *******************************************************************************
 */
 protected void setAgentID( long pID ) { setEntityID( pID ); }


/*******************************************************************************
 * Gets the Agent's personality
 * @return personality
 *******************************************************************************
 */
 public Personality getPersonality( ) { return personality; }


/*******************************************************************************
 * Sets the Agent's personality
 * @param pPersonality: personality object for the Agent's personality
 *******************************************************************************
 */
 public void setPersonality( Personality pPersonality )
 {
   personality = pPersonality;
 }


/*******************************************************************************
 * Gets the Agent's relationship Hashtable
 * @return relationshipTable
 *******************************************************************************
 */
 public Hashtable getRelationshipTable() { return relationshipTable; }


/*******************************************************************************
 * Gets the Agent's name by using the super's getEntityName method
 * @return agentName
 *******************************************************************************
 */
 public String getAgentName() { return super.getEntityName(); }


/*******************************************************************************
 * Sets the Agent's name by using the super's SetEntityName method
 * @param pAgentName: String rejpresentation of the agents name.
 *******************************************************************************
 */
 protected void setAgentName(String pAgentName)
 {
    setEntityName( pAgentName );
 }


/*******************************************************************************
 * Gets the Agent's role vector
 * @return roleVec
 *******************************************************************************
 */
 public Vector getRoleVector() { return roleVec; }

 
 public RelationshipManager getRelationshipManager() {
     return relationshipManager;
 } // end getRelationshipManager
}// end Agent class


