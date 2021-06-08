package tns.actions;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import tns.goals.*;
import tns.relationships.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action increments a mission execution counter.  If the counter exceeds 
 * the required number of execution turns for the target, then the agents in the
 * mission receive experience and influence.  The amount of reward points 
 * available to the agents in the mission is the draw value of the mission.  
 * The leader always receives the largest share of the reward, with a minimum 
 * of 25%.  The rest of the operators in the mission receive a reward 
 * proportional to their influence plus experience compared to the sum of 
 * influence and experience from each of the operators in the mission.  
 * Therefore, more influential and experienced operators receive more influence 
 * and experience than their less experienced and influential brethren, 
 * furthering the rich-get-richer phenomenon.  The reward value becomes the 
 * scalar used in the reward action described above.  Lastly, this action marks 
 * the mission execution goal complete.
 * @author  Rob Michael and Zac Staples
 */
public class ExecuteAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of ExecuteAction 
     * @param role The role performing the action.
     */
    public ExecuteAction(Role role) {
        this.role = role;
    } // end ExecuteAction constructor
    
    /**
     * This method increments the execute turn counter for the role involved.
     * If the number of execute turns exceeds the required number of execute
     * turns for the mission then then a Frame is added to the Ticket to mark
     * the agent's goal associated with mission execution (different for 
     * Operators & Leaders) complete.  A Frame for rewarding the agent and one
     * for cleaning up mission objects is added.
     */
    public void execute() {
        if (role instanceof OperatorRole) {
            ((OperatorRole)role).incrementExecuteTurns();
//            System.out.println(((TNSRole)role).getAgent().getEntityName() + " executed one turn.  Total execute turns is " + ((OperatorRole)role).getExecuteTurns());
            // Get the number of turns the Operator needs to rehearse for the mission
            int threshold = ((OperatorRole)role).getCurrentTarget().getExecuteTime();
            if (((OperatorRole)role).getExecuteTurns() >= threshold) {
//                System.out.println(((TNSRole)role).getAgent().getEntityName() + " finished executing!");
                Vector tickets = ((TNSRole)role).getTickets();
                Enumeration e = tickets.elements();
                while (e.hasMoreElements()) {
                    Ticket t = (Ticket)e.nextElement();
                    if (t instanceof ExecuteMissionTicket) {
                        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
                        t.addFrame(new MarkGoalCompleteAction((TNSGoal)ta.getGoalList().get("ExecuteMissionGoal")));
                        Mission mission = ((OperatorRole)role).getCurrentTarget().getMission();
                        double totalRewardPool = (double) mission.getTotalRewardPool();
                        int operatorsReward = mission.getOperatorsReward();
                        TerroristAgentPersonality personality = (TerroristAgentPersonality)ta.getPersonality();
                        double influence = (double) personality.getInfluence();
                        double experience = (double) personality.getExperience();
                        int agentShare = (int) ((influence + experience)/totalRewardPool) * operatorsReward;
                        t.addFrame(new StandardRewardAction(ta, agentShare));
                        t.addFrame(new MissionCleanupAction(role));
                    } // end if
                } // end while
            } // end if
        } else if (role instanceof LeaderRole) {
            ((LeaderRole)role).getMission().incrementExecuteTurns();
//            System.out.println(((TNSRole)role).getAgent().getEntityName() + " executed one turn.  Total execute turns is " + ((LeaderRole)role).getMission().getExecuteTurns());
            // Get the number of turns the Operator needs to rehearse for the mission
            if (((LeaderRole)role).getMission().executed()) {
//                System.out.println(((TNSRole)role).getAgent().getEntityName() + " finished executing!");
                Vector tickets = ((TNSRole)role).getTickets();
                Enumeration e = tickets.elements();
                while (e.hasMoreElements()) {
                    Ticket t = (Ticket)e.nextElement();
                    if (t instanceof LeadMissionTicket) {
                        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
                        t.addFrame(new MarkGoalCompleteAction((TNSGoal)ta.getGoalList().get("LeadMissionGoal")));
                        Mission mission = ((LeaderRole)role).getMission();
                        int leadersReward = mission.getLeadersReward();
                        t.addFrame(new StandardRewardAction(ta, leadersReward));
                        t.addFrame(new MissionCleanupAction(role));
                    } // end if
                } // end while
            } // end if
        } // end if-else
    } // end execute
    
} // end class ExecuteAction
