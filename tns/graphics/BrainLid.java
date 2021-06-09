package tns.graphics;
import tns.agents.*;
import tns.util.*;
import tns.goals.*;
import tns.roles.*;
import mil.navy.nps.relate.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

/**
 * An agent "brain lid" provides the user the ability to peek inside an agent to
 * see the agent's roles, goals, personality, and mental map.  Some roles have 
 * specialized panels that provide additional information particular to those 
 * roles.  On the left side of the "brain lid" is the agent's information and on 
 * the right side the mental map.  In the goals section of the agent info the 
 * goal weight for each goal is shown in parenthesis to the right of each goal 
 * and the active goal is highlighted in red.  Completed goals are marked with 
 * a check to the left of the goal.  The mental map on the right side of the 
 * "brain lid" shows the agent�s worldview of the network.  Only those agents
 * the agent knows directly or knows about indirectly through messaging are
 * displayed on the mental map.  Links in the mental map are unidirectional, 
 * which highlight the indirect links in the agent's mental map, those links 
 * between agents only known about, but not directly linked to.  
 * @author  Rob Michael and Zac Staples
 */
public class BrainLid extends JFrame implements StateChangeListener {
    
    /**
     * The main display panel.
     */
    private JPanel mainPanel;
    /**
     * An etched border.
     */
    private Border etchedBorder;
    /**
     * A raised bevel border.
     */
    private Border raisedBevel;
    /**
     * A lowered bevel border.
     */
    private Border loweredBevel;
    /**
     * A compound border of raised and lowered bevels.
     */
    private Border compoundBorder;
    /**
     * The border for the agent info.
     */
    private Border agentInfoBorder;
    /**
     * The border for the mental map.
     */
    private Border cognitiveMapBorder;
    /**
     * A panel for the agent' vitae.
     */
    private JPanel vitaePanel;
    /**
     * A panel for the agent's roles.
     */
    private JPanel rolesPanel;
    /**
     * The title border for the agent's roles.
     */
    private Border rolesTitle;
    /**
     * A panel for the agent's goals.
     */
    private JPanel goalsPanel;
    /**
     * The title border for the agent's goals.
     */
    private Border goalsTitle;
    /**
     * A panel for the agent's personality.
     */
    private JPanel personalityPanel;
    /**
     * A title border for the agent's personality.
     */
    private Border personalityTitle;
    /**
     * A panel for the agent's allegiance.
     */
    private JPanel allegiancePanel;
    /**
     * A panel for the agent's experience.
     */
    private JPanel experiencePanel;
    /**
     * A panel for the agent's influence.
     */
    private JPanel influencePanel;
    /**
     * The left panel on the brain lid.
     */
    private JPanel leftPanel;
    /**
     * A panel for displaying resources.
     */
    private JPanel resourcePanel;
    /**
     * A panel for the resource level.
     */
    private JPanel resourceLevelPanel;
    /**
     * The panel that contains the mission panel and the mission operator
     * panel.
     */
    private JPanel topMissionPanel;
    /**
     * A panel for mission details.
     */
    private JPanel missionPanel;
    /**
     * A panel for the operators in a mission.
     */
    private JPanel missionOperatorsPanel;
    /**
     * A panel for operators to show which mission they'return in.
     */
    private JPanel operatorMissionPanel;
    /**
     * The graphical display of the agent's mental map.
     */
    private CognitiveMapPanel cognitiveMap;
    
    /**
     * The preferred width of most panels in the brain lid.
     */
    private int panelWidth = 225;
    /**
     * The preferrred height of most panels in the brain lid.
     */
    private int panelHeight = 15;
    
    /**
     * The agent who owns this brain lid.
     */
    private Agent agent;
    
    /** 
     * Creates a new instance of BrainLid.
     * The constructor performs all the work of drawing the panels, creating
     * the layout, setting borders and titles.  Listeners are setup so that
     * changes can be dynamically displayed.
     * @param agent The agent who owns this brain lid.
     */
    public BrainLid(Agent agent) {
        super("Brain Lid for Terrorist Agent: " + agent.getEntityName());
        this.agent = agent;
        ((TerroristAgent)agent).addStateChangeListener(this);
        mainPanel = new JPanel(new BorderLayout());
        etchedBorder = BorderFactory.createEtchedBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
        agentInfoBorder = BorderFactory.createTitledBorder(compoundBorder, "Agent Info", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(agentInfoBorder);
        vitaePanel = new JPanel();
        vitaePanel.setLayout(new BoxLayout(vitaePanel, BoxLayout.Y_AXIS));
        vitaePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        vitaePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        rolesPanel = new JPanel();
        rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.Y_AXIS));
        rolesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rolesPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        rolesTitle = BorderFactory.createTitledBorder(etchedBorder, "Roles", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION);
        rolesPanel.setBorder(rolesTitle);
        goalsPanel = new JPanel();
        goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
        goalsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        goalsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        goalsTitle = BorderFactory.createTitledBorder(etchedBorder, "Goals", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION);
        goalsPanel.setBorder(goalsTitle);
        personalityPanel = new JPanel();
        personalityPanel.setLayout(new BoxLayout(personalityPanel, BoxLayout.Y_AXIS));
        personalityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        personalityPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        personalityTitle = BorderFactory.createTitledBorder(etchedBorder, "Personality", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION);
        personalityPanel.setBorder(personalityTitle);
        
        resourcePanel = null;
        topMissionPanel = null;
//        missionPanel = null;
        cognitiveMap = new CognitiveMapPanel(((TerroristAgent)agent).getMentalMap().getSubNet(), 
                                             ((TerroristAgent)agent).getMentalMap().getKnownAgents(),
                                             agent);
        cognitiveMapBorder = BorderFactory.createTitledBorder(compoundBorder, "Mental Map", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        cognitiveMap.setBorder(cognitiveMapBorder);
        
        setSize(790, 530);
        setLocation(100, 100);
        setContentPane(mainPanel);
        
        // de-register listeners on closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ((TerroristAgent)BrainLid.this.agent).getMentalMap().removeNodeChangeListener(cognitiveMap);
//                ((TerroristAgent)BrainLid.this.agent).removeLinkChangeListener(cognitiveMap);
                BrainLid.this.cognitiveMap.deregisterListeners();
                ((TerroristAgent)BrainLid.this.agent).removeStateChangeListener(BrainLid.this);
            } // end windowClosing
        });

        Vector roles = agent.getRoleVector();
        Iterator i = roles.iterator();
        while (i.hasNext()) {
            Role role = (Role)i.next();
            JLabel roleName = new JLabel(role.getRoleName());
            roleName.setPreferredSize(new Dimension(panelWidth, panelHeight));
            rolesPanel.add(roleName);
            if (role instanceof ArmsDealerRole ||
                role instanceof FinancierRole ||
                role instanceof LogisticianRole) {
                resourcePanel = new JPanel();
                resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
                resourceLevelPanel = new JPanel();
                resourceLevelPanel.setLayout(new BoxLayout(resourceLevelPanel, BoxLayout.X_AXIS));
            } else if (role instanceof LeaderRole) {
                if (((LeaderRole)role).getMission() != null) {
                    topMissionPanel = new JPanel();
                    topMissionPanel.setLayout(new BoxLayout(topMissionPanel, BoxLayout.X_AXIS));
                    missionPanel = new JPanel();
                    missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
                    missionOperatorsPanel = new JPanel();
                    missionOperatorsPanel.setLayout(new BoxLayout(missionOperatorsPanel, BoxLayout.Y_AXIS));
                    if (resourcePanel != null) {
                        resourcePanel = null;
                        resourceLevelPanel = null;
                    } // end if
                } // end if-else
            } else if (role instanceof OperatorRole) {
                if (((OperatorRole)role).getCurrentTarget() != null) {
                    operatorMissionPanel = new JPanel();
                    operatorMissionPanel.setLayout(new BoxLayout(operatorMissionPanel, BoxLayout.Y_AXIS));
                } // end if
            } // end if-else
        } // end while
        
        i = roles.iterator();
        while (i.hasNext()) {
            Role role = (Role)i.next();
            Iterator i2 = role.getGoalListVec().iterator();
            while (i2.hasNext()) {
                Goal goal = (Goal)i2.next();
                JPanel goalPanel = new JPanel();
                goalPanel.setLayout(new BoxLayout(goalPanel, BoxLayout.X_AXIS));
                JCheckBox goalCompleteStatus = new JCheckBox();
                goalCompleteStatus.setEnabled(false);
                if (((TNSGoal)goal).isCompleted()) {
                    goalCompleteStatus.setSelected(true);
                } // end if
                goalPanel.add(goalCompleteStatus);
                goalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                String goalName = goal.getGoalName() + "(" + 
                                  ((Integer)((TNSGoal)goal).getGoalWeight()).toString() +
                                  ")";
                goalPanel.add(new JLabel(goalName));
                goalPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                goalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                goalPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                if (goal == agent.getActiveGoal()) {
                    goalPanel.setBackground(Color.red);
                } // end if
                goalsPanel.add(goalPanel);
            } // end while
        } // end while
        
        TerroristAgentPersonality personality = (TerroristAgentPersonality)((TerroristAgent)agent).getPersonality();
        allegiancePanel = new JPanel();
        allegiancePanel.setLayout(new BoxLayout(allegiancePanel, BoxLayout.X_AXIS));
        allegiancePanel.add(new JLabel("Allegiance"));
        allegiancePanel.add(Box.createHorizontalGlue());
        allegiancePanel.add(new JLabel(Integer.toString(personality.getAllegiance())));
        allegiancePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        allegiancePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        allegiancePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.X_AXIS));
        experiencePanel.add(new JLabel("Experience"));
        experiencePanel.add(Box.createHorizontalGlue());
        experiencePanel.add(new JLabel(Integer.toString(personality.getExperience())));
        experiencePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        experiencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        experiencePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        influencePanel = new JPanel();
        influencePanel.setLayout(new BoxLayout(influencePanel, BoxLayout.X_AXIS));
        influencePanel.add(new JLabel("Influence"));
        influencePanel.add(Box.createHorizontalGlue());
        influencePanel.add(new JLabel(Integer.toString(personality.getInfluence())));
        influencePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        influencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        influencePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        personalityPanel.add(allegiancePanel);
        personalityPanel.add(experiencePanel);
        personalityPanel.add(influencePanel);
        vitaePanel.add(rolesPanel);
        vitaePanel.add(goalsPanel);
        vitaePanel.add(personalityPanel);
        leftPanel.add(vitaePanel);
        
        if (resourcePanel != null) { // setup resource panel for resource providers
            JPanel stuckPanel = new JPanel();
            stuckPanel.setLayout(new BoxLayout(stuckPanel, BoxLayout.X_AXIS));
            Border resourceTitle = null;
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof ArmsDealerRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Arms", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((ArmsDealerRole)role).getArms().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((ArmsDealerRole)role).getStuckCounter())));
                } else if (role instanceof FinancierRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Finances", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((FinancierRole)role).getFinances().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((FinancierRole)role).getStuckCounter())));
                } else if (role instanceof LogisticianRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Logistics", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((LogisticianRole)role).getLogistics().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((LogisticianRole)role).getStuckCounter())));
                } // end if-else
            } // end while
            stuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            stuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            stuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);

            resourceLevelPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            resourceLevelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            resourceLevelPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            resourcePanel.add(stuckPanel);
            resourcePanel.add(resourceLevelPanel);
            resourcePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            resourcePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            resourcePanel.setAlignmentY(Component.TOP_ALIGNMENT);
            leftPanel.add(resourcePanel);
        } else if (topMissionPanel != null) { // setup mission panel for leaders
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof LeaderRole) {
                    Mission mission = ((LeaderRole)role).getMission();
                    Target target = mission.getTarget();
                    int draw = target.getDraw();
                    String missionBorderTitle = "Mission (" + Integer.toString(draw) + ")";
                    Border missionTitle = BorderFactory.createTitledBorder(etchedBorder, missionBorderTitle, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    missionPanel.setBorder(missionTitle);
                    
                    JPanel stuckPanel = new JPanel();
                    stuckPanel.setLayout(new BoxLayout(stuckPanel, BoxLayout.X_AXIS));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((LeaderRole)role).getStuckCounter())));
                    stuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    stuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    stuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel getOperatorsStuckPanel = new JPanel();
                    getOperatorsStuckPanel.setLayout(new BoxLayout(getOperatorsStuckPanel, BoxLayout.X_AXIS));
                    getOperatorsStuckPanel.add(new JLabel("Operators Stuck counter: "));
                    getOperatorsStuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    getOperatorsStuckPanel.add(new JLabel(Integer.toString(((LeaderRole)role).getGetOperatorsStuckCounter())));
                    getOperatorsStuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    getOperatorsStuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    getOperatorsStuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JLabel resourceLabel = new JLabel("Resources (Have/Need)", SwingConstants.CENTER);
                    int requiredOperators = target.getRequiredOperators();
                    int requiredArms = target.getRequiredArms();
                    int requiredFinances = target.getRequiredFinances();
                    int requiredLogistics = target.getRequiredLogistics();
                    int rehearseTime = target.getRehearseTime();
                    int executeTime = target.getExecuteTime();
                    int currentOperators = mission.getOperatorCount();
                    int currentArms = mission.getArmsCount();
                    int currentFinances = mission.getFinancesCount();
                    int currentLogistics = mission.getLogisticsCount();
                    int rehearseTurns = mission.getRehearseTurns();
                    int executeTurns = mission.getExecuteTurns();
                    boolean trainingArea = mission.hasTrainingArea();
                    
                    JPanel operatorsPanel = new JPanel();
                    operatorsPanel.setLayout(new BoxLayout(operatorsPanel, BoxLayout.X_AXIS));
                    JCheckBox operatorsCompleteStatus = new JCheckBox();
                    operatorsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededOperators()) {
                        operatorsCompleteStatus.setSelected(true);
                    } // end if
                    operatorsPanel.add(operatorsCompleteStatus);
                    operatorsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String operatorsStatusName = "Operators (" + Integer.toString(currentOperators) +
                        " / " + Integer.toString(requiredOperators) + ")";
                    operatorsPanel.add(new JLabel(operatorsStatusName));
                    operatorsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    operatorsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    operatorsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel armsPanel = new JPanel();
                    armsPanel.setLayout(new BoxLayout(armsPanel, BoxLayout.X_AXIS));
                    JCheckBox armsCompleteStatus = new JCheckBox();
                    armsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededArms()) {
                        armsCompleteStatus.setSelected(true);
                    } // end if
                    armsPanel.add(armsCompleteStatus);
                    armsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String armsStatusName = "Arms (" + Integer.toString(currentArms) +
                        " / " + Integer.toString(requiredArms) + ")";
                    armsPanel.add(new JLabel(armsStatusName));
                    armsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    armsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    armsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel financesPanel = new JPanel();
                    financesPanel.setLayout(new BoxLayout(financesPanel, BoxLayout.X_AXIS));
                    JCheckBox financesCompleteStatus = new JCheckBox();
                    financesCompleteStatus.setEnabled(false);
                    if (mission.hasNeededFinances()) {
                        financesCompleteStatus.setSelected(true);
                    } // end if
                    financesPanel.add(financesCompleteStatus);
                    financesPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String financesStatusName = "Finances (" + Integer.toString(currentFinances) +
                        " / " + Integer.toString(requiredFinances) + ")";
                    financesPanel.add(new JLabel(financesStatusName));
                    financesPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    financesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    financesPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel logisticsPanel = new JPanel();
                    logisticsPanel.setLayout(new BoxLayout(logisticsPanel, BoxLayout.X_AXIS));
                    JCheckBox logisticsCompleteStatus = new JCheckBox();
                    logisticsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededLogistics()) {
                        logisticsCompleteStatus.setSelected(true);
                    } // end if
                    logisticsPanel.add(logisticsCompleteStatus);
                    logisticsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String logisticsStatusName = "Logistics (" + Integer.toString(currentLogistics) +
                        " / " + Integer.toString(requiredLogistics) + ")";
                    logisticsPanel.add(new JLabel(logisticsStatusName));
                    logisticsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    logisticsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    logisticsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel trainingPanel = new JPanel();
                    trainingPanel.setLayout(new BoxLayout(trainingPanel, BoxLayout.X_AXIS));
                    JCheckBox trainingCompleteStatus = new JCheckBox();
                    trainingCompleteStatus.setEnabled(false);
                    if (mission.hasTrainingArea()) {
                        trainingCompleteStatus.setSelected(true);
                    } // end if
                    trainingPanel.add(trainingCompleteStatus);
                    trainingPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String trainingStatusName = "Training Area";
                    trainingPanel.add(new JLabel(trainingStatusName), BorderLayout.CENTER);
                    trainingPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    trainingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    trainingPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel rehearsalPanel = new JPanel();
                    rehearsalPanel.setLayout(new BoxLayout(rehearsalPanel, BoxLayout.X_AXIS));
                    JCheckBox rehearsalCompleteStatus = new JCheckBox();
                    rehearsalCompleteStatus.setEnabled(false);
                    if (mission.rehearsed()) {
                        rehearsalCompleteStatus.setSelected(true);
                    } // end if
                    rehearsalPanel.add(rehearsalCompleteStatus);
                    rehearsalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String rehearsalStatusName = "Rehearsal Time (" + Integer.toString(rehearseTurns) +
                        " / " + Integer.toString(rehearseTime) + ")";
                    rehearsalPanel.add(new JLabel(rehearsalStatusName));
                    rehearsalPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    rehearsalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    rehearsalPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel executionPanel = new JPanel();
                    executionPanel.setLayout(new BoxLayout(executionPanel, BoxLayout.X_AXIS));
                    JCheckBox executionCompleteStatus = new JCheckBox();
                    executionCompleteStatus.setEnabled(false);
                    if (mission.executed()) {
                        executionCompleteStatus.setSelected(true);
                    } // end if
                    executionPanel.add(executionCompleteStatus);
                    executionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String executionStatusName = "Execution Time (" + Integer.toString(executeTurns) +
                        " / " + Integer.toString(executeTime) + ")";
                    executionPanel.add(new JLabel(executionStatusName));
                    executionPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    executionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    executionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    missionPanel.add(stuckPanel);
                    missionPanel.add(getOperatorsStuckPanel);
                    missionPanel.add(resourceLabel);
                    missionPanel.add(operatorsPanel);
                    missionPanel.add(armsPanel);
                    missionPanel.add(financesPanel);
                    missionPanel.add(logisticsPanel);
                    missionPanel.add(trainingPanel);
                    missionPanel.add(rehearsalPanel);
                    missionPanel.add(executionPanel);
//                    missionPanel.add(Box.createVerticalGlue());
                    topMissionPanel.add(missionPanel);
                    
                    missionOperatorsPanel.setBorder(etchedBorder);
                    JLabel operatorLabel = null;
                    Vector operators = mission.getOperators();
                    if (operators.size() > 0) {
                        Iterator i3 = operators.iterator();
                        while (i3.hasNext()) {
                            Agent operator = (Agent)i3.next();
                            operatorLabel = new JLabel(operator.getEntityName());
                            operatorLabel.setPreferredSize(new Dimension(40, 10));
                            operatorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                            operatorLabel.setAlignmentY(Component.TOP_ALIGNMENT);
                            missionOperatorsPanel.add(operatorLabel);
                        } // end while
                        topMissionPanel.add(missionOperatorsPanel);
                    } // end if
                    
                    leftPanel.add(topMissionPanel);
                 } // end if
            } // end while
        } else if (operatorMissionPanel != null ) {
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof OperatorRole) {
                    Target target = ((OperatorRole)role).getCurrentTarget();
                    int draw = target.getDraw();
                    Agent leader = ((TNSRole)target.getRole()).getAgent();
                    String operatorMissionBorderTitle = "Mission (" + Integer.toString(draw) + ")";
                    Border operatorMissionTitle = BorderFactory.createTitledBorder(etchedBorder, operatorMissionBorderTitle, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    operatorMissionPanel.setBorder(operatorMissionTitle);
                    JPanel associatedLeaderPanel = new JPanel();
                    associatedLeaderPanel.setLayout(new BoxLayout(associatedLeaderPanel, BoxLayout.X_AXIS));
                    associatedLeaderPanel.add(new JLabel("Leader:"));
                    associatedLeaderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    associatedLeaderPanel.add(new JLabel(leader.getEntityName()));
                    associatedLeaderPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    associatedLeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    associatedLeaderPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    operatorMissionPanel.add(associatedLeaderPanel);
                    leftPanel.add(operatorMissionPanel);
                } // end if
            } // end while
        } else {
            leftPanel.add(Box.createVerticalGlue());
        } // end if
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(cognitiveMap, BorderLayout.CENTER);
        show();
    } // end BrainLid constructor
    
    /**
     * Updates the brain lid panel based on the new state of the agent.
     * @param sce The state change event that captures the agent whose state
     * has changed.
     */
    public void StateChanged(StateChangeEvent sce) {
        Agent agent = sce.getAgent();
        leftPanel.removeAll();
        vitaePanel.removeAll();
        rolesPanel.removeAll();
        goalsPanel.removeAll();
        personalityPanel.removeAll();
        Vector roles = agent.getRoleVector();
        Iterator i = roles.iterator();
        while (i.hasNext()) {
            Role role = (Role)i.next();
            JLabel roleName = new JLabel(role.getRoleName());
            roleName.setPreferredSize(new Dimension(panelWidth, panelHeight));
            rolesPanel.add(roleName);
            if (role instanceof ArmsDealerRole ||
                role instanceof FinancierRole ||
                role instanceof LogisticianRole) {
                if (resourcePanel != null) {
                    resourcePanel.removeAll();
                    resourceLevelPanel.removeAll();
                } else {
                    resourcePanel = new JPanel();
                    resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
                    resourceLevelPanel = new JPanel();
                    resourceLevelPanel.setLayout(new BoxLayout(resourceLevelPanel, BoxLayout.X_AXIS));
                } // end if-else
            } else if (role instanceof LeaderRole) {
                if (((LeaderRole)role).getMission() != null) {
                    if (topMissionPanel != null) {
                        topMissionPanel.removeAll();
                    } else {
                        topMissionPanel = new JPanel();
                        topMissionPanel.setLayout(new BoxLayout(topMissionPanel, BoxLayout.X_AXIS));
                    } // end if-else
                    if (missionPanel != null) {
                        missionPanel.removeAll();
                    } else {
                        missionPanel = new JPanel();
                        missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
                    } // end if-else
                    if (missionOperatorsPanel != null) {
                        missionOperatorsPanel.removeAll();
                    } else {
                        missionOperatorsPanel = new JPanel();
                        missionOperatorsPanel.setLayout(new BoxLayout(missionOperatorsPanel, BoxLayout.Y_AXIS));
                    } // end if-else
                    if (resourcePanel != null) {
                        resourcePanel = null;
                        resourceLevelPanel = null;
                    } // end if
                } else if (((LeaderRole)role).getMission() == null) {
                    missionPanel = null;
                } // end if-else
            } else if (role instanceof OperatorRole) {
                if (((OperatorRole)role).getCurrentTarget() != null) {
                    if (operatorMissionPanel != null) {
                        operatorMissionPanel.removeAll();
                    } else {
                        operatorMissionPanel = new JPanel();
                        operatorMissionPanel.setLayout(new BoxLayout(operatorMissionPanel, BoxLayout.Y_AXIS));
                    } // end if-else
                } else if (((OperatorRole)role).getCurrentTarget() == null) {
                    operatorMissionPanel = null;
                } // end if-else
            } // end if-else
        } // end while
        
        i = roles.iterator();
        while (i.hasNext()) {
            Role role = (Role)i.next();
            Iterator i2 = role.getGoalListVec().iterator();
            while (i2.hasNext()) {
                Goal goal = (Goal)i2.next();
                JPanel goalPanel = new JPanel();
                goalPanel.setLayout(new BoxLayout(goalPanel, BoxLayout.X_AXIS));
                JCheckBox goalCompleteStatus = new JCheckBox();
                goalCompleteStatus.setEnabled(false);
                if (((TNSGoal)goal).isCompleted()) {
                    goalCompleteStatus.setSelected(true);
                } // end if
                goalPanel.add(goalCompleteStatus);
                goalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                String goalName = goal.getGoalName() + "(" + 
                                  ((Integer)((TNSGoal)goal).getGoalWeight()).toString() +
                                  ")";
                goalPanel.add(new JLabel(goalName));
                goalPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                goalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                goalPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                if (goal == agent.getActiveGoal()) {
                    goalPanel.setBackground(Color.red);
                } // end if
                goalsPanel.add(goalPanel);
            } // end while
        } // end while
        
        TerroristAgentPersonality personality = (TerroristAgentPersonality)((TerroristAgent)agent).getPersonality();
        allegiancePanel = new JPanel();
        allegiancePanel.setLayout(new BoxLayout(allegiancePanel, BoxLayout.X_AXIS));
        allegiancePanel.add(new JLabel("Allegiance"));
        allegiancePanel.add(Box.createHorizontalGlue());
        allegiancePanel.add(new JLabel(Integer.toString(personality.getAllegiance())));
        allegiancePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        allegiancePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        allegiancePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.X_AXIS));
        experiencePanel.add(new JLabel("Experience"));
        experiencePanel.add(Box.createHorizontalGlue());
        experiencePanel.add(new JLabel(Integer.toString(personality.getExperience())));
        experiencePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        experiencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        experiencePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        influencePanel = new JPanel();
        influencePanel.setLayout(new BoxLayout(influencePanel, BoxLayout.X_AXIS));
        influencePanel.add(new JLabel("Influence"));
        influencePanel.add(Box.createHorizontalGlue());
        influencePanel.add(new JLabel(Integer.toString(personality.getInfluence())));
        influencePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        influencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        influencePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        personalityPanel.add(allegiancePanel);
        personalityPanel.add(experiencePanel);
        personalityPanel.add(influencePanel);
        vitaePanel.add(rolesPanel);
        vitaePanel.add(goalsPanel);
        vitaePanel.add(personalityPanel);
        leftPanel.add(vitaePanel);
        
        if (resourcePanel != null) { // setup resource panel for resource providers
            JPanel stuckPanel = new JPanel();
            stuckPanel.setLayout(new BoxLayout(stuckPanel, BoxLayout.X_AXIS));
            Border resourceTitle = null;
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof ArmsDealerRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Arms", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((ArmsDealerRole)role).getArms().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((ArmsDealerRole)role).getStuckCounter())));
                } else if (role instanceof FinancierRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Finances", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((FinancierRole)role).getFinances().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((FinancierRole)role).getStuckCounter())));
                } else if (role instanceof LogisticianRole) {
                    resourceTitle = BorderFactory.createTitledBorder(etchedBorder, "Logistics", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    resourcePanel.setBorder(resourceTitle);
                    resourceLevelPanel.add(new JLabel("Current Level:"));
                    resourceLevelPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    resourceLevelPanel.add(new JLabel(Integer.toString(((LogisticianRole)role).getLogistics().getLevel())));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((LogisticianRole)role).getStuckCounter())));
                } // end if-else
            } // end while
            stuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            stuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            stuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            resourceLevelPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            resourceLevelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            resourceLevelPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            resourcePanel.add(stuckPanel);
            resourcePanel.add(resourceLevelPanel);
            resourcePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            resourcePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            resourcePanel.setAlignmentY(Component.TOP_ALIGNMENT);
            leftPanel.add(resourcePanel);
        } else if (missionPanel != null) { // setup mission panel for leaders
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof LeaderRole) {
                    Mission mission = ((LeaderRole)role).getMission();
                    Target target = mission.getTarget();
                    int draw = target.getDraw();
                    String missionBorderTitle = "Mission (" + Integer.toString(draw) + ")";
                    Border missionTitle = BorderFactory.createTitledBorder(etchedBorder, missionBorderTitle, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    missionPanel.setBorder(missionTitle);
                    JLabel resourceLabel = new JLabel("Resources (Have/Need)", SwingConstants.CENTER);
                    int requiredOperators = target.getRequiredOperators();
                    int requiredArms = target.getRequiredArms();
                    int requiredFinances = target.getRequiredFinances();
                    int requiredLogistics = target.getRequiredLogistics();
                    int rehearseTime = target.getRehearseTime();
                    int executeTime = target.getExecuteTime();
                    int currentOperators = mission.getOperatorCount();
                    int currentArms = mission.getArmsCount();
                    int currentFinances = mission.getFinancesCount();
                    int currentLogistics = mission.getLogisticsCount();
                    int rehearseTurns = mission.getRehearseTurns();
                    int executeTurns = mission.getExecuteTurns();
                    boolean trainingArea = mission.hasTrainingArea();
                    
                    JPanel stuckPanel = new JPanel();
                    stuckPanel.setLayout(new BoxLayout(stuckPanel, BoxLayout.X_AXIS));
                    stuckPanel.add(new JLabel("Stuck counter: "));
                    stuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    stuckPanel.add(new JLabel(Integer.toString(((LeaderRole)role).getStuckCounter())));
                    stuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    stuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    stuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel getOperatorsStuckPanel = new JPanel();
                    getOperatorsStuckPanel.setLayout(new BoxLayout(getOperatorsStuckPanel, BoxLayout.X_AXIS));
                    getOperatorsStuckPanel.add(new JLabel("Operators Stuck counter: "));
                    getOperatorsStuckPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    getOperatorsStuckPanel.add(new JLabel(Integer.toString(((LeaderRole)role).getGetOperatorsStuckCounter())));
                    getOperatorsStuckPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    getOperatorsStuckPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    getOperatorsStuckPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel operatorsPanel = new JPanel();
                    operatorsPanel.setLayout(new BoxLayout(operatorsPanel, BoxLayout.X_AXIS));
                    JCheckBox operatorsCompleteStatus = new JCheckBox();
                    operatorsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededOperators()) {
                        operatorsCompleteStatus.setSelected(true);
                    } // end if
                    operatorsPanel.add(operatorsCompleteStatus);
                    operatorsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String operatorsStatusName = "Operators (" + Integer.toString(currentOperators) +
                        " / " + Integer.toString(requiredOperators) + ")";
                    operatorsPanel.add(new JLabel(operatorsStatusName));
                    operatorsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    operatorsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    operatorsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel armsPanel = new JPanel();
                    armsPanel.setLayout(new BoxLayout(armsPanel, BoxLayout.X_AXIS));
                    JCheckBox armsCompleteStatus = new JCheckBox();
                    armsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededArms()) {
                        armsCompleteStatus.setSelected(true);
                    } // end if
                    armsPanel.add(armsCompleteStatus);
                    armsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String armsStatusName = "Arms (" + Integer.toString(currentArms) +
                        " / " + Integer.toString(requiredArms) + ")";
                    armsPanel.add(new JLabel(armsStatusName));
                    armsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    armsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    armsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel financesPanel = new JPanel();
                    financesPanel.setLayout(new BoxLayout(financesPanel, BoxLayout.X_AXIS));
                    JCheckBox financesCompleteStatus = new JCheckBox();
                    financesCompleteStatus.setEnabled(false);
                    if (mission.hasNeededFinances()) {
                        financesCompleteStatus.setSelected(true);
                    } // end if
                    financesPanel.add(financesCompleteStatus);
                    financesPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String financesStatusName = "Finances (" + Integer.toString(currentFinances) +
                        " / " + Integer.toString(requiredFinances) + ")";
                    financesPanel.add(new JLabel(financesStatusName));
                    financesPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    financesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    financesPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel logisticsPanel = new JPanel();
                    logisticsPanel.setLayout(new BoxLayout(logisticsPanel, BoxLayout.X_AXIS));
                    JCheckBox logisticsCompleteStatus = new JCheckBox();
                    logisticsCompleteStatus.setEnabled(false);
                    if (mission.hasNeededLogistics()) {
                        logisticsCompleteStatus.setSelected(true);
                    } // end if
                    logisticsPanel.add(logisticsCompleteStatus);
                    logisticsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String logisticsStatusName = "Logistics (" + Integer.toString(currentLogistics) +
                        " / " + Integer.toString(requiredLogistics) + ")";
                    logisticsPanel.add(new JLabel(logisticsStatusName));
                    logisticsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    logisticsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    logisticsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel trainingPanel = new JPanel();
                    trainingPanel.setLayout(new BoxLayout(trainingPanel, BoxLayout.X_AXIS));
                    JCheckBox trainingCompleteStatus = new JCheckBox();
                    trainingCompleteStatus.setEnabled(false);
                    if (mission.hasTrainingArea()) {
                        trainingCompleteStatus.setSelected(true);
                    } // end if
                    trainingPanel.add(trainingCompleteStatus);
                    trainingPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String trainingStatusName = "Training Area";
                    trainingPanel.add(new JLabel(trainingStatusName), BorderLayout.CENTER);
                    trainingPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    trainingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    trainingPanel.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel rehearsalPanel = new JPanel();
                    rehearsalPanel.setLayout(new BoxLayout(rehearsalPanel, BoxLayout.X_AXIS));
                    JCheckBox rehearsalCompleteStatus = new JCheckBox();
                    rehearsalCompleteStatus.setEnabled(false);
                    if (mission.rehearsed()) {
                        rehearsalCompleteStatus.setSelected(true);
                    } // end if
                    rehearsalPanel.add(rehearsalCompleteStatus);
                    rehearsalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String rehearsalStatusName = "Rehearsal Time (" + Integer.toString(rehearseTurns) +
                        " / " + Integer.toString(rehearseTime) + ")";
                    rehearsalPanel.add(new JLabel(rehearsalStatusName));
                    rehearsalPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    rehearsalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    rehearsalPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    JPanel executionPanel = new JPanel();
                    executionPanel.setLayout(new BoxLayout(executionPanel, BoxLayout.X_AXIS));
                    JCheckBox executionCompleteStatus = new JCheckBox();
                    executionCompleteStatus.setEnabled(false);
                    if (mission.executed()) {
                        executionCompleteStatus.setSelected(true);
                    } // end if
                    executionPanel.add(executionCompleteStatus);
                    executionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    String executionStatusName = "Execution Time (" + Integer.toString(executeTurns) +
                        " / " + Integer.toString(executeTime) + ")";
                    executionPanel.add(new JLabel(executionStatusName));
                    executionPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    executionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    executionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    
                    missionPanel.add(stuckPanel);
                    missionPanel.add(getOperatorsStuckPanel);
                    missionPanel.add(resourceLabel);
                    missionPanel.add(operatorsPanel);
                    missionPanel.add(armsPanel);
                    missionPanel.add(financesPanel);
                    missionPanel.add(logisticsPanel);
                    missionPanel.add(trainingPanel);
                    missionPanel.add(rehearsalPanel);
                    missionPanel.add(executionPanel);
//                    missionPanel.add(Box.createVerticalGlue());
                    topMissionPanel.add(missionPanel);
                    
                    missionOperatorsPanel.setBorder(etchedBorder);
                    JLabel operatorLabel = null;
                    Vector operators = mission.getOperators();
                    if (operators.size() > 0) {
                        Iterator i3 = operators.iterator();
                        while (i3.hasNext()) {
                            Agent operator = (Agent)i3.next();
                            operatorLabel = new JLabel(operator.getEntityName());
                            operatorLabel.setPreferredSize(new Dimension(40, 10));
                            operatorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                            operatorLabel.setAlignmentY(Component.TOP_ALIGNMENT);
                            missionOperatorsPanel.add(operatorLabel);
                        } // end while
                        topMissionPanel.add(missionOperatorsPanel);
                    } // end if
                    
                    topMissionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    topMissionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    leftPanel.add(topMissionPanel);
                 } // end if
            } // end while
        } else if (operatorMissionPanel != null ) {
            roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role role = (Role)i2.next();
                if (role instanceof OperatorRole) {
                    Target target = ((OperatorRole)role).getCurrentTarget();
                    int draw = target.getDraw();
                    Agent leader = ((TNSRole)target.getRole()).getAgent();
                    String operatorMissionBorderTitle = "Mission (" + Integer.toString(draw) + ")";
                    Border operatorMissionTitle = BorderFactory.createTitledBorder(etchedBorder, operatorMissionBorderTitle, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
                    operatorMissionPanel.setBorder(operatorMissionTitle);
                    JPanel associatedLeaderPanel = new JPanel();
                    associatedLeaderPanel.setLayout(new BoxLayout(associatedLeaderPanel, BoxLayout.X_AXIS));
                    associatedLeaderPanel.add(new JLabel("Leader:"));
                    associatedLeaderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                    associatedLeaderPanel.add(new JLabel(leader.getEntityName()));
                    associatedLeaderPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    associatedLeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    associatedLeaderPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    operatorMissionPanel.add(associatedLeaderPanel);
                    leftPanel.add(operatorMissionPanel);
                } // end if
            } // end while
        } else {
            leftPanel.add(Box.createVerticalGlue());
        } // end if
        leftPanel.validate();
        leftPanel.repaint();
    } // end StateChanged
    
} // end class BrainLid
