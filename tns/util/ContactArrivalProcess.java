package tns.util;
import simkit.*;
import simkit.random.*;
/**
 * This class simulates an arrival process with Simkit.  The process has
 * one state, the cumulative number of arrivals, and one event, Arrival,
 * with times between arrivals scheduled randomly from a given distribution.
 * @author Rob Michael and Zac Staples
**/

public class ContactArrivalProcess extends SimEntityBase {

   // instance variables
   /**
    * The state variable that counts the cummulative number of arrivals
    */
   private int numberArrivals;
   /**
    * A mechanism for creating the random arrival times according to a
    * given distribution
    */
   private RandomVariate arrivalTimeGenerator;
   
   // instance methods
   /**
    * The first event in the simulation that schedules the first arrival
    */
   public void doRun() {
      waitDelay("ContactArrival", (int)arrivalTimeGenerator.generate(), 5.0);
   } // end doRun
   
   /**
    * The arrival event.  It increments the number of arrivals and schedules
    * the next arrival.
    */
   public void doContactArrival() {
      numberArrivals++;
      waitDelay("ContactArrival", (int)arrivalTimeGenerator.generate(), 5.0);
   } // end doArrival
   
   /**
    * Gives the user the current number of arrivals
    * @return int The cummulative number of arrivals
    */
   public int getNumberArrivals() {
      return numberArrivals;
   } // end getNumberArrivals
   
   /**
    * Resets the state variables to the initial values
    */
   public void reset() {
      super.reset();
      numberArrivals = 0;
   } // end reset
   
   /**
    * Gives the user an array of the variables used in creating the random
    * arrivals
    * @return Object[] The array of variables for the arrival process
    * generator
    */
   public Object[] getParameters() {
      return arrivalTimeGenerator.getParameters();
   } // end getParameters
   
   /**
    * Sets the variables used in creating the random arrivals
    * @param params The array of variables for the arrival process generator
    */
   public void setParameters(Object[] params) {
      arrivalTimeGenerator.setParameters(params);
   } // end setParameters
   
   /**
    * Gives the user the random number seed for the arrival process generator
    * @return long The random number seed
    */
   public long getSeed() {
      return arrivalTimeGenerator.getRandomNumber().getSeed();
   } // end getSeed
   
   /**
    * Sets the random number seed for the arrival process generator
    * @param seed The random number seed
    */
   public void setSeed(long seed) {
      arrivalTimeGenerator.getRandomNumber().setSeed(seed);
   } // end setSeed
   
   /**
    * Returns the arrival distribution object to the user
    * @return RandomVariate The arrival distribution object
    */
   public RandomVariate getDistribution() {
      return arrivalTimeGenerator;
   } // end getDistribution

   /**
    * Returns a description of the arrival process distribution
    * @return String The arrival process description
    */
   public String paramString() {
      return "Arrival Process with " + arrivalTimeGenerator +
             " interarrival times";
   } // end paramString
   
   // constructor methods
   /** 
    * Initializes the arrival process generator using parameters from the user
    * @param distribution A string for the arrival distribution
    * @param parameters The array of variables for the arrival process
    * generator
    * @param seed The random number seed
    */
   public ContactArrivalProcess(String distribution, Object[] parameters, long seed) {
      arrivalTimeGenerator = RandomVariateFactory.getInstance(
                             distribution, parameters, seed);
   } // end ContactArrivalProcess constructor

} // end ArrivalProcess