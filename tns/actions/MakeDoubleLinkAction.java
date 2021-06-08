package tns.actions;
import tns.frames.*;
import tns.agents.*;

/**
 * In this action an agent takes the other agent in the connection and adds the 
 * other agent to his mental map; the process occurs for both agents.  The 
 * agents are added to a container of directly linked agents and to a container 
 * of known agents.  The mental map creates an agent pair for the two agents if 
 * one did not already exist.
 * @author  robmichael
 */
public class MakeDoubleLinkAction implements Frame {
    
    /**
     * One of the terrorist agents creating a link.
     */
    private TerroristAgent agent1;
    /**
     * The other terrorist agent creating a link.
     */
    private TerroristAgent agent2;
    
    /** 
     * Creates a new instance of MakeDoubleLinkAction 
     * This constructor takes just one agent with the assumption the other one
     * will be set before executing the action.
     * @param agent1 One of the agents making a link.
     */
    public MakeDoubleLinkAction(TerroristAgent agent1) {
        this.agent1 = agent1;
        agent2 = null;
    } // end MakeDoubleLinkAction contructor
    
    /**
     * Creates a new instance of MakeDoubleLinkAction
     * @param agent1 One of the agents creating a link.
     * @param agent2 The other agent creating a link.
     */
    public MakeDoubleLinkAction(TerroristAgent agent1, TerroristAgent agent2) {
        this(agent1);
        this.agent2 = agent2;
    } // end MakeDoubleLinkAction constructor
    
    /**
     * The execute method delegates responsibility to each agent's mental map
     * to add a directly linked agent.  Each agent then sends a message to its
     * link change listeners to notify them of the change.
     */
    public void execute() {
//        System.out.println(name + " executing...");
        MentalMap myMentalMap = agent1.getMentalMap();
        myMentalMap.addDirectlyLinkedAgent(agent2);
        agent1.changeLink("add", agent1, agent2);
        myMentalMap = agent2.getMentalMap();
        myMentalMap.addDirectlyLinkedAgent(agent1);
        agent2.changeLink("add", agent2, agent1);
    } // end execute
    
    /**
     * Sets one of the agents.
     * @param agent1 One of the agents creating a link.
     */
    public void setAgent1(TerroristAgent agent1) {
        this.agent1 = agent1;
    } // end setAgent1
    
    /**
     * Sets the other agent creating a link.
     * @param agent2 The other agent creating a link.
     */
    public void setAgent2(TerroristAgent agent2) {
        this.agent2 = agent2;
    } // end setAgent2
} // end class MakeDoubleLinkAction
