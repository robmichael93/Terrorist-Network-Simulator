package tns.util;
import java.util.*;
import tns.agents.*;
import tns.roles.*;
import tns.goals.*;
import mil.navy.nps.relate.*;

/**
 * To complement the mental map, a helper class known as the SubNet, or 
 * sub-network, kept track of the edges or links within the individual agent’s 
 * mental map of the network.  Where the mental map tracks exactly who the agent 
 * knows and knows about indirectly, the mental map delegates the task of 
 * tracking which agents know which other agents in the mental map.  The 
 * sub-work only contains unique pairs, so both agent pairs {A, B} and {B, A} 
 * would not exist in the sub-network, but instead just one would.
 *
 * The SubNet is also used by the explicit map of the network to track all of
 * the agent pairs in the simulation.
 * @author  Rob Michael and Zac Staples
 */
public class SubNet {
    
    /**
     * A container of all the unique pairs in the network.
     */
    private Vector uniquePairs;
    
    /** Creates a new instance of SubNet */
    public SubNet() {
        uniquePairs = new Vector();
    } // end SubNet constructor
    
    /**
     * Add a pair to the sub-network.  The pair is added iff the pair does not
     * already exist in the sub-network.
     * @param agentPair The agent pair to be added.
     */
    public void addPair(AgentPair agentPair) {
        if (!uniquePairs.contains(agentPair)) {
            uniquePairs.add(agentPair);
//            System.out.println("Added pair from " + agentPair.getFrom().getEntityName() + " to " + agentPair.getTo().getEntityName());
        } // end if
    } // end addPair
    
    /**
     * Remove an agent pair from the sub-network.  The method takes in an agent
     * pair (does not have to be the actual object of one of the agent pairs
     * in the sub-network) and finds the matching agent pair that exists in the
     * container of unique pairs.  The method then removes that pair.  This
     * approach relieves the user from having to search the sub-network for the
     * actual AgentPair object by matching on the underlying agents in the pair.
     * @param agentPair An agent pair describing which pair to remove.
     * @return boolean True if the pair was found and removed.
     */
    public boolean removePair(AgentPair agentPair) {
        AgentPair removePair = null;
        Agent toAgent = agentPair.getTo();
        Agent fromAgent = agentPair.getFrom();
        Iterator i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair ap = (AgentPair)i.next();
            if (ap.contains(toAgent) && ap.contains(fromAgent)) {
                removePair = ap;
            } // end if
        } // end while
        boolean removed = uniquePairs.remove(removePair);
        if (removed) {
//            System.out.println("Removed pair from " + agentPair.getFrom().getEntityName() + " to " + agentPair.getTo().getEntityName());
            return true;
        } else {
            return false;
        } // end if-else
    } // end removePair
    
    /**
     * This method takes any agent pair and does a search in the sub-network for
     * a matching pair with the same underlying agents.  This method relies on
     * AgentPairs ability to check either half of an agent pair for a particular
     * agent.
     * @param agentPair An agent pair describing which pair to find.
     * @return boolean True if the pair was found.
     */
    public boolean contains(AgentPair agentPair) {
        if (uniquePairs.size() == 0) {
            return false;
        } // end if
        Agent toAgent = agentPair.getTo();
        Agent fromAgent = agentPair.getFrom();
        Iterator i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair ap = (AgentPair)i.next();
            if (ap.contains(toAgent) && ap.contains(fromAgent)) {
                return true;
            } // end if
        } // end while
        return false;
    } // end contains
    
    /**
     * Returns a count of the number of unique pairs in the sub-network.
     * @return int The number of unique pairs in the sub-network.
     */
    public int numberOfUniquePairs() { return uniquePairs.size(); }
    
    /**
     * Returns the container of unique pairs in the network.
     * @return Vector The container of unique pairs in the network.
     */
    public Vector getUniquePairs() { return uniquePairs; }
    
    /**
     * This method iterates through each of the agent pairs and decrements the
     * history value of each pair.
     */
    public void decrementAllHistories() {
        Iterator i = uniquePairs.iterator();
        while(i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            agentPair.decrementHistory();
        } // end while
     } // end decrementAllHistories
    
    /**
     * This method iterates through each of the agent pairs in the sub-network.
     * When it finds an agent pair whose history value has dropped below zero,
     * it removes each agent from the other's mental map and sends a change
     * link message to any listeners that the link was removed.  If the agent
     * pair that just broke was an Operator-Leader pair, then that pair needs
     * to be checked to see if the Operator was in the Leader's mission.  If
     * that condition is the case, then the Operator is removed from the
     * Leader's mission and the Leader has to find a replacement.  The Operator's
     * goals with respect to the mission (JoinLeader, RehearseMission, and
     * ExecuteMission) are reset and the target is de-referenced from the
     * Operator role.
     */
    public void checkAgingRelationships() {
        Vector copyOfUniquePairs = new Vector();
        Iterator i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair copyPair = (AgentPair)i.next();
            copyOfUniquePairs.add(copyPair);
        } // end while
        i = copyOfUniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            if (agentPair.getHistory() < 0) {
                TerroristAgent agent1 = (TerroristAgent)agentPair.getTo();
                TerroristAgent agent2 = (TerroristAgent)agentPair.getFrom();
                MentalMap myMentalMap = agent1.getMentalMap();
                myMentalMap.removeDirectlyLinkedAgent(agent2);
                agent1.changeLink("remove", agent1, agent2);
                myMentalMap = agent2.getMentalMap();
                myMentalMap.removeDirectlyLinkedAgent(agent1);
                agent2.changeLink("remove", agent2, agent1);
                
                Role agent1Role = null;
                Role agent2Role = null;
                Vector agent1Roles = agent1.getRoleVector();
                Vector agent2Roles = agent2.getRoleVector();
                Iterator i2 = agent1Roles.iterator();
                while (i2.hasNext()) {
                    Role r1 = (Role)i2.next();
                    if (r1 instanceof LeaderRole) {
                        agent1Role = r1;
                    } else if (r1 instanceof OperatorRole) {
                        agent1Role = r1;
                    } // end if-else
                } // end while
                i2 = agent2Roles.iterator();
                while (i2.hasNext()) {
                    Role r2 = (Role)i2.next();
                    if (r2 instanceof LeaderRole) {
                        agent2Role = r2;
                    } else if (r2 instanceof OperatorRole) {
                        agent2Role = r2;
                    } // end if-else
                } // end while
                if ( agent1Role != null && agent2Role != null && 
                     (agent1Role instanceof LeaderRole && agent2Role instanceof OperatorRole) ||
                     (agent1Role instanceof OperatorRole && agent2Role instanceof LeaderRole)) {
                     System.out.println("\t\t\t\t\t\t\t\t\t\tLeader - Operator pair breaking...");
                   if (agent1Role instanceof LeaderRole) {
                       Mission leaderMission = ((LeaderRole)agent1Role).getMission();
                       Target operatorsTarget = ((OperatorRole)agent2Role).getCurrentTarget();
                       if (operatorsTarget != null) {
                           Mission operatorMission = operatorsTarget.getMission();
                           System.out.println("Leader's mission: " + leaderMission.toString());
                           System.out.println("Operator's mission: " + operatorMission.toString());
                           if (leaderMission == operatorMission) {
                               leaderMission.removeOperator(((TNSRole)agent2Role).getAgent());
                               ((OperatorRole)agent2Role).clearTarget();
                                Vector goals = ((TNSRole)agent2Role).getGoalListVec();
                                Iterator i3 = goals.iterator();
                                while (i3.hasNext()) {
                                    Goal goal = (Goal)i3.next();
                                    if (goal instanceof JoinLeaderGoal ||
                                        goal instanceof RehearseMissionGoal ||
                                        goal instanceof ExecuteMissionGoal) {
                                        ((TNSGoal)goal).resetGoal();
                                    } // end if
                                } // end while
                               System.out.println(((TNSRole)agent1Role).getAgent().getEntityName() + " removed " + 
                                                  ((TNSRole)agent2Role).getAgent().getEntityName() + " from mission " + leaderMission);
                               System.out.println(((TNSRole)agent2Role).getAgent().getEntityName() + "'s mission is null? " + 
                                                  ((OperatorRole)agent2Role).getCurrentTarget());
                           } // emd if
                       } // end if
                   } else if (agent1Role instanceof OperatorRole) {
                       Mission leaderMission = ((LeaderRole)agent2Role).getMission();
                       Target operatorsTarget = ((OperatorRole)agent1Role).getCurrentTarget();
                       if (operatorsTarget != null) {
                           Mission operatorMission = operatorsTarget.getMission();
                           if (leaderMission == operatorMission) {
                               leaderMission.removeOperator(((TNSRole)agent1Role).getAgent());
                               ((OperatorRole)agent1Role).clearTarget();
                                Vector goals = ((TNSRole)agent1Role).getGoalListVec();
                                Iterator i3 = goals.iterator();
                                while (i3.hasNext()) {
                                    Goal goal = (Goal)i3.next();
                                    if (goal instanceof JoinLeaderGoal ||
                                        goal instanceof RehearseMissionGoal ||
                                        goal instanceof ExecuteMissionGoal) {
                                        ((TNSGoal)goal).resetGoal();
                                    } // end if
                                } // end while
                               System.out.println(((TNSRole)agent2Role).getAgent().getEntityName() + " removed " + 
                                                  ((TNSRole)agent1Role).getAgent().getEntityName() + " from mission " + leaderMission);
                               System.out.println(((TNSRole)agent1Role).getAgent().getEntityName() + "'s mission is null? " + 
                                                  ((OperatorRole)agent1Role).getCurrentTarget());
                           } // emd if
                       } // end if
                   } // end if-else
                } // end if
            } // end if
        } // end while
    } // end if
    
} // end class SubNet
