package tns;
import tns.agents.*;
import tns.roles.*;
import tns.util.*;
import tns.graphics.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import simkit.*;

/**
 * The main simulation class.  This class handles the arrival of new contacts
 * and recruiters, initializes the simulation, and handles the turn structure.
 * @author  Rob Michael and Zac Staples
 */
public class TNSMainSimulation extends SimEntityBase {
    
    /**
     * The main frame for rendering the simulation.
     */
    private JFrame frame;
    /**
     * The main panel for placing the graphical rendering of the network.
     */
    private TNSPanel tnsPanel;
    /**
     * The agents in the simulation.
     */
    private Vector agents;
    /**
     * A sorting object that performs a merge sort on the container of agents
     * at the beginning of each turn.
     */
    private AgentSorter agentSorter;
    /**
     * The sub-network that manages all the links in the network.
     */
    private SubNet subNet;
    /**
     * The number of agents initially in the simulation.
     */
    private int numberOfAgents;
    /**
     * The width of the simulation logical space.
     */
    private int environmentWidth;
    /**
     * The height of the simulation logical space.
     */
    private int environmentHeight;
    /**
     * The x coordinate of the agent being added to the simulation.
     */
    private int x;
    /**
     * The y coordinate of the agent being added to the simulation.
     */
    private int y;
    
    /**
     * A counter used for assigning a unique ID to each agent.
     */
    private int id;
    /**
     * The number of the current turn (0 based).
     */
    private int turn;
    
    /** Creates a new instance of TNSMainSimulation */
    public TNSMainSimulation() {}
    
    /**
     * Handles the arrival of a new contact.  A new agent is created, the contact
     * role assigned, and a personality is assigned.  The agent is added to the
     * collection of agents for the simulation and then the drawing panel is
     * notified of a new node being added.
     */
    public void doContactArrival() {
        x = (int) (Math.random() * environmentHeight);
        y = (int) (Math.random() * environmentWidth);
        TerroristAgent ta = new TerroristAgent(id, x, y);
        id++;
        TerroristAgentPersonality personality = new TerroristAgentPersonality(1, 1, 1);
	ta.addRole(new ContactRole(ta));
        ta.changeState(ta);
        ta.setPersonality(personality);
        agents.add(ta);
        tnsPanel.NodeChanged(new NodeChangeEvent("add", ta));
    } // end doArrival
    
    /**
     * Handles the arrival of a new recruiter.  A new agent is created, the 
     * contact role assigned, and a personality is assigned.  The agent is added 
     * to the collection of agents for the simulation and then the drawing panel
     * is notified of a new node being added.
     */
    public void doRecruiterArrival() {
        x = (int) (Math.random() * environmentHeight);
        y = (int) (Math.random() * environmentWidth);
        TerroristAgent ta = new TerroristAgent(id, x, y);
        id++;
        TerroristAgentPersonality personality = new TerroristAgentPersonality(2, 2, 10);
	ta.addRole(new RecruiterRole(ta));
        ta.setPersonality(personality);
        agents.add(ta);
        tnsPanel.NodeChanged(new NodeChangeEvent("add", ta));
    } // end doArrival

    /**
     * This method initializes the simulation.  It sets up the size of the
     * logical space and adds the initial agents.  It then sets up the graphics
     * components and lastly schedules the first turn of the simulation.
     */
    public void doRun() {
        agents = new Vector();
        agentSorter = new AgentSorter();
        subNet = new SubNet();
        numberOfAgents = 3;
        environmentWidth = 75;
        environmentHeight = 75;
        x = 0;
        y = 0;
        id = 0;
        turn = 0;
        
        for (id = 0; id < numberOfAgents; id++) {
            x = (int) (Math.random() * environmentHeight);
            y = (int) (Math.random() * environmentWidth);
            TerroristAgent ta = new TerroristAgent(id, x, y);
            TerroristAgentPersonality personality = new TerroristAgentPersonality(1, 1, 1);
            ta.setPersonality(personality);
            agents.add(ta);
        } // end for
        
 	//build leader0
        ((TerroristAgent)agents.get(0)).addRole(new LeaderRole((TerroristAgent)agents.get(0)));
        ((TerroristAgent)agents.get(0)).setPersonality(new TerroristAgentPersonality(8, 8, 8));
        ((TerroristAgent)agents.get(0)).getMentalMap().addDirectlyLinkedAgent((TerroristAgent)agents.get(1));
        ((TerroristAgent)agents.get(0)).getMentalMap().addDirectlyLinkedAgent((TerroristAgent)agents.get(2));
        
	//build Trainer (T)
        ((TerroristAgent)agents.get(1)).addRole(new TrainerRole((TerroristAgent)agents.get(1)));
        ((TerroristAgent)agents.get(1)).setPersonality(new TerroristAgentPersonality(6, 6, 6));
        ((TerroristAgent)agents.get(1)).getMentalMap().addDirectlyLinkedAgent((TerroristAgent)agents.get(0));
 
	//build Recruiter (R)
        ((TerroristAgent)agents.get(2)).addRole(new RecruiterRole((TerroristAgent)agents.get(2)));
        ((TerroristAgent)agents.get(2)).setPersonality(new TerroristAgentPersonality(5, 5, 5));
        ((TerroristAgent)agents.get(2)).getMentalMap().addDirectlyLinkedAgent((TerroristAgent)agents.get(0));
        

        subNet.addPair(new AgentPair((Agent)agents.get(0), (Agent)agents.get(1)));
        subNet.addPair(new AgentPair((Agent)agents.get(0), (Agent)agents.get(2)));

        // Setup the graphics
        frame = new JFrame("Terrorist Network Simulator Multi-Agent System");
        tnsPanel = new TNSPanel(subNet, agents);
        Iterator i = agents.iterator();
        while (i.hasNext()) {
            Agent a = (Agent)i.next();
            ((TerroristAgent)a).changeState(a);
        } // end while
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        frame.getContentPane().add("Center", tnsPanel);
        frame.setSize(800,600);
        frame.setLocation(400, 300);
        frame.setVisible(true);
        waitDelay("Turn", 1.0, 1.0);
    } // end doRun
    
    /**
     * This method runs the turn structure for the simulation. To preserve the 
     * idea of preferential attachment and the rich-get-richer phenomenon, at 
     * the beginning of each turn the agents are sorted into a list in 
     * decreasing order of influence.  This sorting allows the most influential 
     * agents to always take their turn before the less influential ones.  Next, 
     * the history values for each of the agent pairs in the simulation are 
     * decremented to create the aging relationship behavior in the model.  The
     * next part of the turn constitutes the main section where agents take 
     * their individual turns.  Each agent first processes his inbox, and then 
     * he checks for sensed contacts.  He adds those agents he senses to those
     * he is directly connected to as his sensed environment and uses that 
     * sensed environment as necessary for evaluating his goals, which comes
     * next.  After evaluating his goals, he takes the highest weighted goal and 
     * executes whatever ticket is associated with that goal.  Some tickets are 
     * not executed until the agent connects to another agent's connector, so 
     * some goals do not have tickets that necessarily execute during the agent's 
     * turn, but get executed on another agent's turn when a connection is made. 
     * The last part of each agent's turn is to process his outbox.  Once all 
     * the agents have taken their turns, the simulation checks for any 
     * relationships that should be terminated because their history dropped 
     * past a minimum threshold.
     */
    public void doTurn() {
        // sort the agents
        Object[] agentArray = agents.toArray();
        agentSorter.sort(agentArray);
        agents = new Vector();
//       System.out.println("Turn " + (turn + 1) + "'s list of sorted agents:");
        for (int k = 0; k < agentArray.length; k++) {
            agents.add((TerroristAgent)agentArray[k]);
/*            System.out.println(((TerroristAgent)agentArray[k]).getEntityName() + ", Influence: " + 
                               ((TerroristAgentPersonality)((TerroristAgent)agentArray[k]).getPersonality()).getInfluence());*/
        } // end for
        SubNet net = tnsPanel.getSubNet();
        net.decrementAllHistories();
        System.out.println("\t\t\t\t\t\t\t\t\t\t*********Turn " + (turn + 1) + "*********");
        for (int j = 0; j < agents.size(); j++) {
            TerroristAgent a = (TerroristAgent)agents.get(j);
//            System.out.println("\t**** Agent " + a.getAgentName() + "'s turn " + (turn + 1) + " START ****");
            a.processInbox();
            a.detectAgents(agents);
            a.checkForRelationships();
            a.assignCredit();
            a.executeActiveGoal();
            a.processOutbox();
//            System.out.println("\t**** Agent " + a.getAgentName() + "'s turn " + (turn + 1) + " END ****");
//            waitDelay("Sleep", 0.0, 20.0);
        } // end for
        net.checkAgingRelationships();
        checkForUnjoinedContacts();
        turn++;
//        tnsPanel.validate();
        tnsPanel.repaint();
        waitDelay("Turn", 1.0, 1.0);
    } // end doTurn
    
    /**
     * An experimental method for creating a drawing pause between each agent
     * instead of at the end of each turn.
     */
    public void doSleep() {
    } // end doSleep
    
    /** 
     * The last part of the turn is to remove any 
     * contacts that have not been contacted for a set number of turns or have
     * been contacted, but decided not to join the organization.
     */
    private void checkForUnjoinedContacts() {
        Vector agentVector = (Vector)agents.clone();
        Iterator i = agentVector.iterator();
        while (i.hasNext()) {
            Agent a = (Agent)i.next();
            Vector roles = a.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role r = (Role)i2.next();
                if (r instanceof ContactRole) {
                    int stuckCounter = ((ContactRole)r).getStuckCounter();
                    int stuckThreshold = ((ContactRole)r).getStuckThreshold();
                    boolean beenContacted = ((ContactRole)r).beenContacted();
                    boolean joined = ((ContactRole)r).joined();
                    if ((beenContacted && !joined) || (stuckCounter > stuckThreshold)) {
                        // remove Contact from the simulation
                        agents.remove(a);
                        tnsPanel.NodeChanged(new NodeChangeEvent("remove", a));
                    } // end if-else
                } // end if
            } // end while
        } // end while
    } // end checkForUnjoinedContacts
    
} // end clas TNSMainSimulation
