package tns.graphics;
import tns.util.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import com.touchgraph.graphlayout.*;
import com.touchgraph.graphlayout.graphelements.*;
import com.touchgraph.graphlayout.interaction.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main drawing panel for the simulation.
 * @author  Rob Michael and Zac Staples
 */
public class TNSPanel extends GLPanel implements LinkChangeListener,
                                                 NodeChangeListener,
                                                 HistoryChangeListener {
    
    /**
     * The sub-network object that manages all the links in the network.
     */                                                 
    protected SubNet subNet;
    /**
     * The collection of agents in the network.
     */
    protected Vector agents;
    /**
     * The collection of graphical rendering objects for the agents in the
     * network.
     */
    protected Vector agentNodes;
    
    /**
     * The default color for nodes.
     */
    private Color defaultColor = Color.lightGray;

    /** Creates a new instance of TNSPanel */
    public TNSPanel() {
        super();
        subNet = new SubNet();
        agents = new Vector();
        agentNodes = new Vector();
    } // end TNSPanel constructor
    
    /** 
     * Creates a new instance of TNSPanel 
     * @param subNet The sub-network that manages all the links in the network.
     * @param agents The collection of agents in the network.
     */
    public TNSPanel(SubNet subNet, Vector agents) {
        super();
        this.subNet = subNet;
        this.agents = agents;
        agentNodes = new Vector();
        try {
            createGraph();
        } catch ( TGException tge ) {
            System.err.println(tge.getMessage());
            tge.printStackTrace(System.err);
        } // end try-catch
        tgPanel.setSelect(tgPanel.getGES().getFirstNode()); //Select first node, so hiding works
    } // end TNSPanel constructor
    
    /**
     * Initializes the graphics panel.
     */
    public void initialize() {
        buildPanel();
        buildLens();
        tgPanel.setLensSet(tgLensSet);
        addUIs();
//        tgPanel.setSelect(tgPanel.getGES().getFirstNode()); //Select first node, so hiding works
        setVisible(true);
    }
    
    /**
     * Adds the user interace controls.
     */
    public void addUIs() {
        tgUIManager = new TGUIManager();
        GLExplicitUI explicitUI = new GLExplicitUI(this);
        tgUIManager.addUI(explicitUI, "Explicit");
        tgUIManager.activate("Explicit");
    }

    /**
     * Builds the graphical panel.  Sets up the buttons for the user interface
     * controls.
     */
    public void buildPanel() {
        final JScrollBar horizontalSB = hvScroll.getHorizontalSB();
        final JScrollBar verticalSB = hvScroll.getVerticalSB();
        final JScrollBar zoomSB = zoomScroll.getZoomSB();
        final JScrollBar rotateSB = rotateScroll.getRotateSB();
        final JScrollBar localitySB = localityScroll.getLocalitySB();

        setLayout(new BorderLayout());

        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(defaultColor);
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JPanel modeSelectPanel = new JPanel();
        modeSelectPanel.setBackground(defaultColor);
        modeSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));

        AbstractAction explicitAction = new AbstractAction("Explicit") {
            public void actionPerformed(ActionEvent e) {
                tgUIManager.activate("Explicit");
            }
        };

/*        AbstractAction editAction = new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                tgUIManager.activate("Edit");
            }
        };*/

/*        JRadioButton rbNavigate = new JRadioButton(navigateAction);
        rbNavigate.setBackground(defaultColor);
        rbNavigate.setSelected(true);
        JRadioButton rbEdit = new JRadioButton(editAction);
        rbEdit.setBackground(defaultColor);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbNavigate);
        bg.add(rbEdit);
*/
        JRadioButton rbExplicit = new JRadioButton(explicitAction);
        rbExplicit.setBackground(defaultColor);
        rbExplicit.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbExplicit);
        
/*        modeSelectPanel.add(rbNavigate);
        modeSelectPanel.add(rbEdit);*/
        modeSelectPanel.add(rbExplicit);

        final JPanel topPanel = new JPanel();
        topPanel.setBackground(defaultColor);
        topPanel.setLayout(new GridBagLayout());
        c.gridy=0; c.fill=GridBagConstraints.HORIZONTAL;
        /*
        c.gridx=0;c.weightx=0;
        topPanel.add(new Label("Zoom",Label.RIGHT), c);
        c.gridx=1;c.weightx=0.5;
        topPanel.add(zoomSB,c);
        c.gridx=2;c.weightx=0;
        topPanel.add(new Label("Locality",Label.RIGHT), c);
        c.gridx=3;c.weightx=0.5;
        topPanel.add(localitySB,c);
        */
        c.gridx=0;c.weightx=0;c.insets = new Insets(0,10,0,10);
        topPanel.add(modeSelectPanel,c);
        c.insets=new Insets(0,0,0,0);
        c.gridx=1;c.weightx=1;

        scrollBarHash.put(zoomLabel, zoomSB);
        scrollBarHash.put(rotateLabel, rotateSB);
        scrollBarHash.put(localityLabel, localitySB);

        JPanel scrollselect = scrollSelectPanel(new String[] {zoomLabel, rotateLabel, localityLabel});
        scrollselect.setBackground(defaultColor);
        topPanel.add(scrollselect,c);

        add(topPanel, BorderLayout.NORTH);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 1;
        scrollPanel.add(tgPanel,c);

        c.gridx = 1; c.gridy = 1; c.weightx = 0; c.weighty = 0;
        scrollPanel.add(verticalSB,c);

        c.gridx = 0; c.gridy = 2;
        scrollPanel.add(horizontalSB,c);

        add(scrollPanel,BorderLayout.CENTER);

        glPopup = new JPopupMenu();
        glPopup.setBackground(defaultColor);

        JMenuItem menuItem = new JMenuItem("Toggle Controls");
        ActionListener toggleControlsAction = new ActionListener() {
                boolean controlsVisible = true;
                public void actionPerformed(ActionEvent e) {
                    controlsVisible = !controlsVisible;
                    horizontalSB.setVisible(controlsVisible);
                    verticalSB.setVisible(controlsVisible);
                    topPanel.setVisible(controlsVisible);
                }
            };
        menuItem.addActionListener(toggleControlsAction);
        glPopup.add(menuItem);
    }

    /**
     * Sets the sub-network for the panel.
     * @param subNet The sub-network to be set for the panel.
     */
    public void setSubNet(SubNet subNet) {
        this.subNet = subNet;
    } // end setSubNet
    
    /**
     * Returns the sub-network for this panel.
     * @return SubNet The sub-network for this panel.
     */
    public SubNet getSubNet() { return subNet; }
    
    
    /**
     * Creates the graph and adds the link and history change listeners.
     */
    public void createGraph() throws TGException {
        Vector uniquePairs = subNet.getUniquePairs();
        Iterator i = agents.iterator();
        while (i.hasNext()) {
            Agent agent = (Agent)i.next();
//            AgentNode addNode = new AgentNode(agent);
            AgentNode addNode = ((TerroristAgent)agent).getNode();
            agentNodes.add(addNode);
            try {
                tgPanel.addNode(addNode);
            } catch ( TGException tge ) {
                System.err.println(tge.getMessage());
                tge.printStackTrace(System.err);
            } // end try-catch*/
            ((TerroristAgent)agent).addLinkChangeListener(this);
            ((TerroristAgent)agent).addHistoryChangeListener(this);
            
        } // end while
        i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            Agent fromAgent = agentPair.getFrom();
            Agent toAgent = agentPair.getTo();
            AgentNode fromAgentNode = null;
            AgentNode toAgentNode = null;
            Enumeration e = agentNodes.elements();
            while (e.hasMoreElements()) {
                AgentNode node = (AgentNode)e.nextElement();
                Agent agent = node.getAgent();
                if (agent == fromAgent) {
                    fromAgentNode = node;
                } else if (agent == toAgent) {
                    toAgentNode = node;
                } // end if
            } // end while
            if (fromAgentNode != null && toAgentNode != null) {
                int distance = (int) ((TerroristAgent)fromAgent).getLocation().distance(((TerroristAgent)toAgent).getLocation());
                tgPanel.addEdge(fromAgentNode, toAgentNode, distance * 100);
                tgPanel.addEdge(toAgentNode, fromAgentNode, distance * 100);
            } // end if
        } // end while
    }
    
    /**
     * Handles adding and removing links from the panel.  This method handles
     * adding or removing pairs from the SubNet as well.
     */
    public void LinkChanged(LinkChangeEvent lce) {
//        System.out.println("Entering LinkChanged in " + this.toString());
        String type = lce.getChangeType();
        if (type.equalsIgnoreCase("add")) {
            // do add method
            Agent agent1 = lce.getAgent1();
            Agent agent2 = lce.getAgent2();
            AgentNode fromAgentNode = null;
            AgentNode toAgentNode = null;
            Enumeration e = agentNodes.elements();
            while (e.hasMoreElements()) {
                AgentNode node = (AgentNode)e.nextElement();
                Agent agent = node.getAgent();
                if (agent == agent1) {
                    fromAgentNode = node;
                } else if (agent == agent2) {
                    toAgentNode = node;
                } // end if
            } // end while
            if (fromAgentNode != null && toAgentNode != null && tgPanel.getGES().findEdge(fromAgentNode, toAgentNode) == null) {
                int distance = (int) ((TerroristAgent)agent1).getLocation().distance(((TerroristAgent)agent2).getLocation());
                AgentPair newPair = new AgentPair(agent1, agent2);
                if (!subNet.contains(newPair)) {
                    subNet.addPair(newPair);
                } // end if
                tgPanel.addEdge(fromAgentNode, toAgentNode, distance * 100);
            } // end if
        } else if (type.equalsIgnoreCase("remove")) {
            // do remove method
            Agent agent1 = lce.getAgent1();
            Agent agent2 = lce.getAgent2();
            AgentNode fromAgentNode = null;
            AgentNode toAgentNode = null;
            Enumeration e = agentNodes.elements();
            while (e.hasMoreElements()) {
                AgentNode node = (AgentNode)e.nextElement();
                Agent agent = node.getAgent();
                if (agent == agent1) {
                    fromAgentNode = node;
                } else if (agent == agent2) {
                    toAgentNode = node;
                } // end if
            } // end while
            if (fromAgentNode != null && toAgentNode != null) {
                tgPanel.deleteEdge(fromAgentNode, toAgentNode);
                AgentPair removePair = null;
                Vector uniquePairs = subNet.getUniquePairs();
                Iterator i = uniquePairs.iterator();
                while (i.hasNext()) {
                    AgentPair ap = (AgentPair)i.next();
                    if (ap.contains(agent1) && ap.contains(agent2)) {
                        removePair = ap;
                    } // end if
                } // end while
//                System.out.println(this.toString() + " attempting to remove pair from " + agent1.getEntityName() + " to " + agent2.getEntityName());
                if (removePair != null && subNet.contains(removePair)) {
                    subNet.removePair(removePair);
                } else {
//                    System.out.println(this.toString() + " apparently already removed this pair.");
                } // end if-else
            } // end if
        } // end if-else
        tgPanel.resetDamper();
//        tgPanel.validate();
//        tgPanel.repaint();
    }
    
/*    public void NodeChanged(NodeChangeEvent nce) {
        String type = nce.getChangeType();
        if (type.equalsIgnoreCase("add")) {
            // do add method
            Agent agent = nce.getAgent();
            AgentNode agentNode = null;
            Iterator i = agentNodes.iterator();
            while (i.hasNext()) {
                AgentNode node = (AgentNode)i.next();
                Agent nodeAgent = node.getAgent();
                if (agent == nodeAgent) {
                    agentNode = node;
                } // end
            } // end while
            if (agentNode != null) {
                try {
                    tgPanel.addNode(agentNode);
                } catch ( TGException tge ) {
                    System.err.println(tge.getMessage());
                    tge.printStackTrace(System.err);
                } // end try-catch
            } // end if
        } else if (type.equalsIgnoreCase("remove")) {
            Agent agent = nce.getAgent();
            AgentNode agentNode = null;
            Iterator i = agentNodes.iterator();
            while (i.hasNext()) {
                AgentNode node = (AgentNode)i.next();
                Agent nodeAgent = node.getAgent();
                if (agent == nodeAgent) {
                    agentNode = node;
                } // end
            } // end while
            if (agentNode != null) {
                tgPanel.deleteNode(agentNode);
            } // end if
        } // end if-else
        tgPanel.validate();
        tgPanel.repaint();
    }*/
    public void NodeChanged(NodeChangeEvent nce) {
        String type = nce.getChangeType();
        if (type.equalsIgnoreCase("add")) {
            // do add method
            Agent agent = nce.getAgent();
//            AgentNode agentNode = new AgentNode(agent);
            AgentNode agentNode = ((TerroristAgent)agent).getNode();
            agentNodes.add(agentNode);
            if (agentNode != null) {
                try {
                    tgPanel.addNode(agentNode);
                } catch ( TGException tge ) {
                    System.err.println(tge.getMessage());
                    tge.printStackTrace(System.err);
                } // end try-catch
            } // end if
            ((TerroristAgent)agent).addLinkChangeListener(this);
            ((TerroristAgent)agent).addHistoryChangeListener(this);
        } else if (type.equalsIgnoreCase("remove")) {
            Agent agent = nce.getAgent();
            AgentNode agentNode = null;
            Iterator i = agentNodes.iterator();
            while (i.hasNext()) {
                AgentNode node = (AgentNode)i.next();
                Agent nodeAgent = node.getAgent();
                if (agent == nodeAgent) {
                    agentNode = node;
                } // end
            } // end while
            if (agentNode != null) {
                tgPanel.deleteNode(agentNode);
                ((TerroristAgent)agent).removeLinkChangeListener(this);
                ((TerroristAgent)agent).removeHistoryChangeListener(this);
            } // end if
        } // end if-else
    }
    
    public void HistoryChanged(HistoryChangeEvent hce) {
//        System.out.println(this.toString() + " attempting to update history...");
        AgentPair pair = hce.getAgentPair();
        int amount = hce.getAmount();
        Agent agent1 = pair.getTo();
        Agent agent2 = pair.getFrom();
        AgentPair changedPair = null;
        Vector uniquePairs = subNet.getUniquePairs();
        Iterator i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair ap = (AgentPair)i.next();
            if (ap.contains(agent1) && ap.contains(agent2)) {
                changedPair = ap;
//                System.out.println("Found pair between " + agent1.getEntityName() + " and " + agent2.getEntityName());
            } // end if
        } // end while
        if (changedPair != null) {
            changedPair.incrementHistory(amount);
        } // end if
    }
    
    public String toString() {
        return getClass().getName();
    } // end toString
} // end createGraph
