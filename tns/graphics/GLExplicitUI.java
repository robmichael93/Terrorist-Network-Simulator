package tns.graphics;
import com.touchgraph.graphlayout.*;
import com.touchgraph.graphlayout.interaction.*;
import tns.agents.*;
import tns.graphics.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.util.*;


/**
 * This class is a derivation of the TouchGraph User Interface class with
 * controls to allow for double-clicking on agents so that their "brain lid"
 * can be displayed.  Most of the functionality currently implemented is that
 * from the "Edit" user interface in the standard TouchGraph classes.
 * @author  Rob Michael and Zac Staples
 */
public class GLExplicitUI extends TGUserInterface {
    
    /**
     * The TouchGraph drawing panel.
     */
    TGPanel tgPanel;
//    DragAddUI dragAddUI;
    /**
     * A user interface object for dragging nodes.
     */
    DragNodeUI dragNodeUI;
    /**
     * A user interface object for drag selecting multiple nodes.
     */
    DragMultiselectUI dragMultiselectUI;
    /**
     * Not sure what this does.
     */
    TGAbstractClickUI switchSelectUI;
    /**
     * Not sure what this does.
     */
    TGAbstractDragUI hvDragUI;

    /**
     * A TouchGraph mouse listener.
     */
    GLExplicitMouseListener ml;
    /**
     * A TouchGraph mouse motion listener.
     */
    GLExplicitMouseMotionListener mml;

    /**
     * A popup menu for nodes.
     */
    JPopupMenu nodePopup;
    /**
     * A popup menu for edges/links.
     */
    JPopupMenu edgePopup;
    /**
     * A popup menu for the rest of the panel.
     */
    JPopupMenu backPopup;
    /**
     * The node using the popup menu.
     */
    Node popupNode;
    /**
     * The edge using the popup menu.
     */
    Edge popupEdge;

    /**
     * Not sure what this does.
     */
    AbstractAction deleteSelectAction;
    /**
     * Some identifier for the delete key.
     */
    final KeyStroke deleteKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);

    /** 
     * Creates a new instance of GLExplicitUI 
     * @param tgp The associated TouchGraph panel used with this UI.
     */
    public GLExplicitUI(TGPanel tgp) {
        active = false;
        tgPanel = tgp;

        ml = new GLExplicitMouseListener();
        mml = new GLExplicitMouseMotionListener();

        deleteSelectAction = new AbstractAction("DeleteSelect") {
            public void actionPerformed(ActionEvent e) {
                Node select = tgPanel.getSelect();
                if(select!=null) {
                    tgPanel.deleteNode(select);
                    tgPanel.repaint();
                }
            }
        };

//        dragAddUI = new DragAddUI(tgPanel);
        dragNodeUI = new DragNodeUI(tgPanel);
        dragMultiselectUI = new DragMultiselectUI(tgPanel);
        switchSelectUI = tgPanel.getSwitchSelectUI();

        setUpNodePopup();
        setUpEdgePopup();
        setUpBackPopup();
    } // end GLExplicitUI constructor
    

    /**
     * Alternate constructor for the GLExplicitUI
     * @param glPanel The GLPanel associated with this UI.
     */
    public GLExplicitUI( GLPanel glPanel ) {
        this(glPanel.getTGPanel());
        hvDragUI = glPanel.hvScroll.getHVDragUI();
    } // end GLExplicitUI constructor

    /**
     * Some TouchGraph stuff that I'm not sure just what it does.
     */
    public void activate() {
        tgPanel.addMouseListener(ml);
        tgPanel.addMouseMotionListener(mml);
        tgPanel.getActionMap().put("DeleteSelect", deleteSelectAction);
        ComponentInputMap cim = new ComponentInputMap(tgPanel);
        cim.put(deleteKey, "DeleteSelect");
        tgPanel.setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, cim);
        active = true;
    }

    /**
     * Some TouchGraph stuff that I'm not sure just what it does.
     */
    public void deactivate() {
        //A hack.  Want to prevent dragMultiselect from remaining active when user switches to
        //navigate mode.  Keeping an "active" variable resolves some comlex issues with the flow
        //of controll, caused by dragMultiselect calling it's parents deactivate method when it
        //is activated.
        if (!active) dragMultiselectUI.deactivate();

        tgPanel.removeMouseListener(ml);
        tgPanel.removeMouseMotionListener(mml);
        tgPanel.getInputMap().put(deleteKey, null);
        tgPanel.getActionMap().put("DeleteSelect", null);
        active = false;
    }

    /**
     * A mouse listener for this UI.
     */
    class GLExplicitMouseListener extends MouseAdapter {
        /**
         * Processes mouse pressed events.
         * @param e The mouse event.
         */
        public void mousePressed(MouseEvent e) {
            Node mouseOverN = tgPanel.getMouseOverN();
            Node select = tgPanel.getSelect();


            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                if (mouseOverN != null) {
                    if(mouseOverN!=select)
                        dragNodeUI.activate(e);
//                    else
//                        dragAddUI.activate(e);
                }
                else
                    if(hvDragUI!=null) hvDragUI.activate(e);
            }

        }

        /**
         * Processes mouse clicked events.  Manages double-clicks on agent
         * nodes to display their brain lid.
         * @param e The mouse event.
         */
        public void mouseClicked(MouseEvent e) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                switchSelectUI.activate(e);
            } // end if
            if (e.getClickCount() > 1) {
                Node nodeClickedOn = tgPanel.getMouseOverN();
                if (nodeClickedOn != null) {
                    BrainLid brainLid = new BrainLid(((AgentNode)nodeClickedOn).getAgent());
                } // end if
            } // end if
        }

        /**
         * Processes mouse release events.
         * @param e The mouse event.
         */
        public void mouseReleased(MouseEvent e) {
              if (e.isPopupTrigger()) {
                   popupNode = tgPanel.getMouseOverN();
                   popupEdge = tgPanel.getMouseOverE();
                   if (popupNode!=null) {
                       tgPanel.setMaintainMouseOver(true);
                    nodePopup.show(e.getComponent(), e.getX(), e.getY());
                }
                else if (popupEdge!=null) {
                    tgPanel.setMaintainMouseOver(true);
                    edgePopup.show(e.getComponent(), e.getX(), e.getY());
                }
                else {
                    backPopup.show(e.getComponent(), e.getX(), e.getY());
                }
               }
         }
    }

    /**
     * A TouchGraph mouse motion listener that does nothing.
     */
    class GLExplicitMouseMotionListener extends MouseMotionAdapter {
        /**
         * Processes mouse moved events.
         * @param e The mouse event.
         */
        public void mouseMoved(MouseEvent e) {
            //tgPanel.startDamper();
        }
    }

    /**
     * Sets up the node popup menu.
     */
    private void setUpNodePopup() {
        nodePopup = new JPopupMenu();
        JMenuItem menuItem;
        JMenu navigateMenu = new JMenu("Navigate");

        menuItem = new JMenuItem("Delete Node");
        ActionListener deleteNodeAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(popupNode!=null) {
                        tgPanel.deleteNode(popupNode);
                    }
                }
            };

        menuItem.addActionListener(deleteNodeAction);
        nodePopup.add(menuItem);

        menuItem = new JMenuItem("Expand Node");
        ActionListener expandAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(popupNode!=null) {
                        tgPanel.expandNode(popupNode);
                    }
                }
            };

        menuItem.addActionListener(expandAction);
        navigateMenu.add(menuItem);

        menuItem = new JMenuItem("Collapse Node");
        ActionListener collapseAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {                    
                    if(popupNode!=null) {
                        tgPanel.collapseNode(popupNode );
                    }
                }
            };

        menuItem.addActionListener(collapseAction);
        navigateMenu.add(menuItem);
            
        menuItem = new JMenuItem("Hide Node");
        ActionListener hideAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Node select = tgPanel.getSelect();
                    if(popupNode!=null) {
                        tgPanel.hideNode(popupNode);
                    }
                }
            };
                               
        menuItem.addActionListener(hideAction);
        navigateMenu.add(menuItem);

        nodePopup.add(navigateMenu);

        nodePopup.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e) {}
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                tgPanel.setMaintainMouseOver(false);
                tgPanel.setMouseOverN(null);
                tgPanel.repaint();
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
        });

    }

    /**
     * Sets up the edge popup menu.
     */
    private void setUpEdgePopup() {
        edgePopup = new JPopupMenu();
        JMenuItem menuItem;

        menuItem = new JMenuItem("Relax Edge");
        ActionListener relaxEdgeAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(popupEdge!=null) {
                        popupEdge.setLength(popupEdge.getLength()*4);
                        tgPanel.resetDamper();
                    }
                }
            };
        menuItem.addActionListener(relaxEdgeAction);
        edgePopup.add(menuItem);

        menuItem = new JMenuItem("Tighten Edge");
        ActionListener tightenEdgeAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(popupEdge!=null) {
                        popupEdge.setLength(popupEdge.getLength()/4);
                        tgPanel.resetDamper();
                    }
                }
            };
        menuItem.addActionListener(tightenEdgeAction);
        edgePopup.add(menuItem);

        menuItem = new JMenuItem("Delete Edge");
        ActionListener deleteEdgeAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(popupEdge!=null) {
                        tgPanel.deleteEdge(popupEdge);
                    }
                }
            };
        menuItem.addActionListener(deleteEdgeAction);
        edgePopup.add(menuItem);

        edgePopup.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e) {}
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                tgPanel.setMaintainMouseOver(false);
                tgPanel.setMouseOverE(null);
                tgPanel.repaint();
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
        });
    }

    /**
     * Sets up the panel popup menu.
     */
    private void setUpBackPopup() {
        backPopup = new JPopupMenu();
        JMenuItem menuItem;

        menuItem = new JMenuItem("Multi-Select");
        ActionListener multiselectAction = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dragMultiselectUI.activate(GLExplicitUI.this);
                }
            };
        menuItem.addActionListener(multiselectAction);
        backPopup.add(menuItem);

        menuItem = new JMenuItem("Start Over");
        ActionListener startOverAction = new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    tgPanel.clearAll();
                    tgPanel.clearSelect();
                    try {
                        tgPanel.addNode();
                    } catch ( TGException tge ) {
                        System.err.println(tge.getMessage());
                        tge.printStackTrace(System.err);
                    }
                    tgPanel.fireResetEvent();
                    tgPanel.repaint();
                }
            };
        menuItem.addActionListener(startOverAction);
        backPopup.add(menuItem);
    }

} // end class GLExplicitUI
