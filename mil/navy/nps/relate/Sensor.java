package mil.navy.nps.relate;

/*******************************************************************************
 * <p><b>This interface implements the RELATE Sensor.</b></p>
 *
 * <p>These objects are defined by the developer as a way of describing specific
 * sensor capabilities for agents.  Sensor objects are not currently implemented
 * in any reference cases but are included in the RELATE package for future work
 * and conceptualization.</p>
 *
 * @author 	  Michael R. Dickson
 * @author 	  Kimberly A. Roddy
 * @version   1.0, 17 Aug 00
 * @since     JDK1.3
 *******************************************************************************
 */
public interface Sensor {
    public double getRange();
    public void setRange(double r);
}// end Sensor interface


