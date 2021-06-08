package mil.navy.nps.relate;import java.util.*;/*******************************************************************************
 * <p><b>This interface implements the RELATE SensedEnvironment.</b></p>
 *
 * <p>A SensedEnvironment is a complex data structure unique to the simulation
 * and defined by the developer.  It must contain appropriate data members to
 * store all aspects of the perceived environment.  This interface requires the
 * developer to implement methods to get the SensedEnvironment object, as well
 * as a vector of the sensed agents that is used by the RelationshipManager.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface SensedEnvironment
{

/****************************************************************************
 * Returns an Object that contains all required elements of the
 * SensedEnvironment.  This method puts all of the various elements
 * of the PerceivedEnvironment into an appropriate Object using
 * getter-methods.
 * @return  A SensedEnvironment object.
 ****************************************************************************
 */
 public SensedEnvironment getSensedEnvironment();


/*******************************************************************************
 * Vector containing all sensed agents.  This is used by the relationshipManager
 * in checking for new or formed relationships.
 * @return  A vector containing all currently sensed agents.
 *******************************************************************************
 */
 public Vector getSensedAgents();


}// end PerceivedEnvironment interface



