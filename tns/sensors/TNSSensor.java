package tns.sensors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This inteface extends the Sensor interface from the RELATE architecture
 * to include a method for returning a Vector of sensed agents.
 * @author  Rob Michael and Zac Staples
 */
public interface TNSSensor extends Sensor {
    
    /**
     * Returns a Vector of sensed agents sensed by the sensing agent.
     * @param sensingAgent The agent doing the sensing.
     * @param agents The container of agents in the simulation to use in
     * checking for detections.
     * @return The collection of agents sensed by this sensor.
     */
    public Vector findSensedAgents(Agent sensingAgent, Vector agents);
    
} // end interface TNSSensor
