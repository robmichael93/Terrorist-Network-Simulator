package tns.actions;
import tns.frames.*;
import tns.roles.*;
import tns.agents.*;
import mil.navy.nps.relate.*;

/**
 * This action adds the operator to the leader's mission and the operator 
 * receives information about the target.
 * @author  Rob Michael and Zac Staples
 */
public class ReceiveTargetAction implements Frame {
    
    /**
     * The leader providing the target information.
     */
    private Role leader;
    /**
     * The operator receiving the target information.
     */
    private Role operator;
    
    /** 
     * Creates a new instance of ReceiveMissionAction 
     * @param leader The leader providing the target information.
     * @param operator The operator receiving the target information.
     */
    public ReceiveTargetAction(Role leader, Role operator) {
        this.leader = leader;
        this.operator = operator;
    } // end ReceiveTargetAction constructor
    
    /**
     * The execute method adds the operator to the leader's mission and then
     * the history value between the two agents is rewarded.
     */
    public void execute() {
        Mission mission = ((LeaderRole)leader).getMission();
        mission.addOperator(((TNSRole)operator).getAgent());
        ((OperatorRole)operator).assignTarget(mission.getTarget());
        Agent leaderAgent = ((TNSRole)leader).getAgent();
        Agent operatorAgent = ((TNSRole)operator).getAgent();
        AgentPair agentPair = new AgentPair(leaderAgent, operatorAgent);
        ((TerroristAgent)leaderAgent).changeHistory(agentPair, 40);
    } // end execute
    
} // end class ReceiveTargetAction
