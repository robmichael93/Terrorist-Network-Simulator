package mil.navy.nps.relate;

/*******************************************************************************
 * <p><b>This interface implements the RELATE Rule.</b></p>
 *
 * <p>This interface requires the user to define the method "calculate" which
 * receives a sensedEnvironment object and returns an object representing the
 * result of the rules calculation. A toString() method is required to assist in
 * the display and evaluation of the Rule.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Rule
{

/*******************************************************************************
 * Uses the passed in perceived environment as input to a calculation which
 * returns an object which is the result of the calculation.
 *
 * @param   pPE  The PerceivedEnvironment that the Rule uses for calculation.
 * @return  An object corresponding to the result of this calculation.
 *******************************************************************************
 */
 public Object calculate( SensedEnvironment pPE );


/*******************************************************************************
 * Returns a formated description of this Rule suitable for printing
 * @return  String message describing this Rule object.
 *******************************************************************************
 */
 public String toString();

 
}// end Rule class





