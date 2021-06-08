package mil.navy.nps.relate;/*******************************************************************************
 * <p><b>This interface implements the RELATE Personality.</b></p>
 *
 * <p>Defines the minimum requirements for a Personality in the RELATE
 * architecture.  This should be a simple data structure can be modified or
 * added to for expandability.  Personality is used to influence goal selection
 * and measurement as well as credit assignment to rules.  Typically they are
 * mathematical factors that capture key aspects of the individual in the
 * particular situation being modeled.</p>
 *
 * <p>Example personality traits could include the following:</p>
 *
 * <li>Loyalty - An agent's propensity to reward a Rule when it was successful.
 * <li>Persistence - An agent's propensity to penalize a Rule when it fails.
 * <li>Tolerance - An agent's ability to reward varying degrees of accuracy.
 * <li>Obedience - An agent's propensity to take direction from another source.
 * <li>Independence - A agent's propensity to operate alone.
 *
 * <p>A simulation may be designed to use the same personality for all agents,
 * or randomly issue personalities to stress variations.  Personalities may
 * remain fixed or be allowed to change based on experience or external
 * pressures.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Personality{}// end Personality class


