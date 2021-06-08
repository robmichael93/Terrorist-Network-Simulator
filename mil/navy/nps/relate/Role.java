package mil.navy.nps.relate;

import java.util.*;

/*******************************************************************************
 * <p><b>This interface implements the RELATE Role.</b></p>
 *
 * <p>A Role object brings additional capabilities and responsibilities
 * to a host Agent. This can include, but is not limited to, sensors,
 * goals (with their associated Rules), and methods to act upon itself
 * and its environment.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Role
{

/*******************************************************************************
 * Adds the passed in Goal to the goalList.
 * @param pGoal
 *******************************************************************************
 */
 public void addNewGoal( Goal pGoal );


/*******************************************************************************
 * Removes the Goal from the goalList.
 * @param pGoal
 * @return The success of the removal operation:
 * <li> true - The Goal was successfully removed
 * <li> false - The Goal did not exist in the Role's goalList
 *******************************************************************************
 */
 public boolean removeGoal( Goal pGoal );


/*******************************************************************************
 * Getter for goalList
 * @return goalList vector
 *******************************************************************************
 */
 public Vector getGoalListVec();


/*******************************************************************************
 * Getter for sensorList
 * @return sensorList vector
 *******************************************************************************
 */
 public Vector getSensorListVec();


/*******************************************************************************
 * Setter for sensorList
 * @param pSensorList
 *******************************************************************************
 */
 public void setSensorList( Vector pSensorList );


/*******************************************************************************
 * Removes the indicated sensor from the sensorList
 * @param pSensor An integer representing the sensor to be removed
 * @return The success of the removal operation:
 * <li> true - The sensor was successfully removed
 * <li> false - The sensor did not exist in the Role's sensorList
 *******************************************************************************
 */
 public boolean removeSensor( Sensor pSensor );


/*******************************************************************************
 * Adds the indicated sensor to the sensorList
 * @param pSensor An integer representing the sensor to be added
 *******************************************************************************
 */
 public void addSensor( Sensor pSensor );


/*******************************************************************************
 * Getter for actionList.  Returns a Vector containing a list of integers
 * representing all of the actions available to the agent due to this Role
 * @return actionList
 *******************************************************************************
 */
 public Vector getActionListVec();


/*******************************************************************************
 * Setter for actionList.  Replaces the actionList Vector with a new list of
 * integers representing all of the actions available to the agent due to
 * this Role
 * @param pActionList
 *******************************************************************************
 */
 public void setActionList( Vector pActionList );


/*******************************************************************************
 * Removes the indicated action from the actionList
 * @param pAction The action to be removed
 * @return The success of the removal operation:
 * <li> true - The action was successfully removed
 * <li> false - The action did not exist in the Role's actionList
 *******************************************************************************
 */
 public boolean removeAction( Action pAction );


/*******************************************************************************
 * Adds the indicated action to the actionList
 * @param pAction  The action to be added
 *******************************************************************************
 */
 public void addAction( Action pAction);


/*******************************************************************************
 * Sets the role name of the role object, this should be the exact class name
 * of the role.
 * @param pRoleName  The role name
 *******************************************************************************
 */
 public void setRoleName( String pRoleName);


/*******************************************************************************
 * Gets the role name
 * @return The "string" role name.
 *******************************************************************************
 */
 public String getRoleName( );


}// end Role interface


