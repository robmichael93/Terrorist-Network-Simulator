package mil.navy.nps.relate;import java.util.*;/******************************************************************************* * <p><b>This abstract class implements the RELATE Thing.</b></p>
 *
 * <p>This is the minimal entity that can exist in a RELATE simulation.  This
 * class defines the minimum requirements for a Thing in the RELATE
 * architecture. A Thing has a unique entity identification number and name with
 * associated getter and setter methods.  If the entity identification number
 * and name are not provided in the constructor, a no-argument constructor will
 * assign the number "0" and name "unnamed" to the Thing.  The only other
 * methods that a Thing has are the step() and drawSelf() abstract methods.
 * The step() method is used to update the object and the drawSelf() method is
 * used to update the appearance during the simulation run.  Both methods are
 * unique to each simulation and therefore must be defined by the developer.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */public abstract class Thing extends Object{

/*******************************************************************************
 * The entityID is a unique identifier for this entity in the environment.
 * No other entities should have the same ID.
 *******************************************************************************
 */
 protected long entityID;


/*******************************************************************************
 * The entityName could be a unique name for the entity, or it could be a simple
 * identifier such as "BlueTeam" or "RedTeam".  The simulation developer is
 * responsible for sselecting and tracking this variable.
 *******************************************************************************
 */
 protected String entityName;


/*******************************************************************************
 * Two argument constructor for the "Thing" object
 * @param pID: Unique entityID
 * @param pName: The entity name
 *******************************************************************************
 */
 public Thing(long pID, String pName)
 {
    setEntityID(pID);
    setEntityName(pName);
 }


/*******************************************************************************
 * No argument constructor with an entityID of "0" and entityName of "Unnamed".
 *******************************************************************************
 */
 public Thing()
 {
    entityID = 0;
    entityName = "Unnamed";
 }


/*******************************************************************************
 * step() can be used by the developer to indicate a simulation cycle or
 * discrete event occurrance.
 *******************************************************************************
 */
 public abstract void step();


 /*******************************************************************************
 * drawSelf() can be used by the developer to draw the geometry of the entity
 * if there is to be a visual representation of the entity in the simulation.
 *******************************************************************************
 */
 public abstract void drawSelf();


/*******************************************************************************
 * Sets the entityID to the passed in "Long" value
 * @param pID: unique entityID
 *******************************************************************************
 */
 protected void setEntityID(long pID) { entityID = pID; }


/*******************************************************************************
 * Gets the unique entityID
 * @return: Unique entityID.
 *******************************************************************************
 */
 public long getEntityID( ) { return entityID; }


/*******************************************************************************
 * Sets the entityName to the passed in "String" value
 * @param pEntityName: entityName
 *******************************************************************************
 */
 protected void setEntityName(String pEntityName) { entityName = pEntityName; }


/*******************************************************************************
 * Gets the entityName
 * @return: entityName.
 *******************************************************************************
 */
 public String getEntityName( ) { return entityName; }

}// end Thing class


