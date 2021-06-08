package mil.navy.nps.relate;

import java.util.*;

/*******************************************************************************
 * <p><b>This interface implements the RELATE Goal.</b></p>
 *
 * <p>Defines the minimum requirements for a RELATE Goal. Provides a mechanism
 * to select from a collection of methods (rules) that affect an Agent's
 * internal state or it's environment. These methods are selected based on the
 * activeRule of a collection of Rules. The method, or action, taken is intended
 * to satisfy the Goal.  A feedback mechanism is provided in the assignCredit
 * method to determine the health of the Goal as well as the current active
 * Rule.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Goal
{

/*******************************************************************************
 * Adds the passed in Rule to the ruleList Vector.
 * @param pRule: The Rule that is to be added
 *******************************************************************************
 */
 public void addRule( Rule pRule );


/*******************************************************************************
 * Removes the Rule from the ruleList Vector.
 * @param pRule  The Rule that is to be removed
 * @return The success of the removal operation:
 * <li> true - The Rule was successfully removed
 * <li> false - The Rule did not exist in the Goal's ruleList
 *******************************************************************************
 */
 public boolean removeRule( Rule pRule );


/*******************************************************************************
 * Returns the ruleList Vector.
 * @return ruleList
 *******************************************************************************
 */
 public Vector getRuleList();

/*******************************************************************************
 * Assigns credit to itself (i.e. updates goal attainment weight )and the
 * lastRule by receiving the new PerceivedEnvironment to the Goal and letting
 * the Goal determine the credit assignment to itself (goal attainment) and
 * all of the Rules associated with that Goal, both active and inactive.
 * Assigns the Rule with the highest weight to the activeRule.
 * @param pPE The agent's perceived environment.
 *******************************************************************************
 */
 public void assignCredit( SensedEnvironment pPE );


/*******************************************************************************
 * Passes the PerceivedEnvironment to the activeRule in its calculate()
 * method.
 * @return an Object associated with the active Rules calculation
 *******************************************************************************
 */
 public Object runActiveRule( SensedEnvironment pPE );


/*******************************************************************************
 * Sets the PastActiveRule, used for comparison and rule weight calculation
 * @param pRule The agent's past active rule.
 *******************************************************************************
 */
 public void setPastActiveRule( Rule pRule );


/*******************************************************************************
 * Sets the Goals type, the developer should designate specific goal types to
 * be used in the RELATE architecture.
 * @param pType Integer representing the goal type.
 *******************************************************************************
 */
 public void setGoalType(int pType);


/*******************************************************************************
 * Sets the ActiveRule, used for goal attainment.
 * @param pRule The agent's active rule.
 *******************************************************************************
 */
 public void setActiveRule( Rule pRule );


/*******************************************************************************
 * Gets the Goal type
 * @return an Int representing the goal type
 *******************************************************************************
 */
 public int getGoalType();


/*******************************************************************************
 * Gets the Goal name
 * @return a String of the goals name (should be the goal class name)
 *******************************************************************************
 */
 public String getGoalName();


/*******************************************************************************
 * Gets the Goal active rule
 * @return the Goals active rule
 *******************************************************************************
 */
 public Rule getActiveRule();


/*******************************************************************************
 * Gets the Goal past active rule
 * @return the Goals oast active rule
 *******************************************************************************
 */
 public Rule getPastActiveRule();


/*******************************************************************************
 * Returns a formated description of this Goal suitable for printing
 * @return  String message describing this Goal object.
 *******************************************************************************
 */
 public String toString();


}// end Goal interface


