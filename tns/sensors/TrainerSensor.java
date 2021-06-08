package tns.sensors;
import tns.agents.*;
import tns.roles.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;

/**
 * This sensor senses Trainers.
 * @author  Rob Michael and Zac Staples
 */
public class TrainerSensor implements TNSSensor {
    
    /**
     * The range of the sensor in logical space.
     */
    private double range;

    /** 
     * Creates a new instance of TrainerSensor 
     * @param r The range of the sensor in logical space.
     */
    public TrainerSensor(double r) {
        range = r;
    } // end TrainerSensor constructor
    
    /**
     * Returns the range of the sensor.
     * @return double The range of the sensor.
     */
    public double getRange() { return range; }
    
    /**
     * Sets the range of the sensor.
     * @param r The value to set the sensor's range to.
     */
    public void setRange(double r) {
        range = r;
    } // end setRange
    
    /**
     * Returns a Vector of sensed agents sensed by the sensing agent.
     * @param sensingAgent The agent doing the sensing.
     * @param agents The container of agents in the simulation to use in
     * checking for detections.
     * @return The collection of agents sensed by this sensor.
     */
    public Vector findSensedAgents(Agent sensingAgent, Vector agents) {
        Point location = ((TerroristAgent)sensingAgent).getLocation();
        Vector sensedAgents = new Vector();
        // for each sensor, check to see if it finds agents
        for (int i = 0; i < agents.size(); i++) {
            TerroristAgent t = (TerroristAgent)agents.elementAt(i);
            // if the distance between agents is less than the sensor
            // range, then add the agent to the list of sensed agents
            Vector roles = t.getRoleVector();
            Enumeration e3 = roles.elements();
            while (e3.hasMoreElements()) {
                Role role = (Role)e3.nextElement();
                if (role instanceof TrainerRole && 
                    location.distance(t.getLocation()) <= range && 
                    !sensedAgents.contains(t) && sensingAgent != t ) {
//                    System.out.println(sensingAgent.getEntityName() + " detected " + t.getAgentName());
                    sensedAgents.add(t);
                } // end if
            } // end while
        } // end for
        return sensedAgents;
    } // end findSensedAgents
    
} // end class TrainerSensor
