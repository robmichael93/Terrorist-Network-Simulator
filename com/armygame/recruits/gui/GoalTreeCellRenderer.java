//
//  GoalTreeCellRenderer.java
//  projectbuilder5
//
//  Created by Mike Bailey on Mon Apr 15 2002.
//  Copyright (c) 2001 MOVES Institute, Naval Postgraduate School. All rights reserved.
//
package com.armygame.recruits.gui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.*;

public class GoalTreeCellRenderer extends DefaultTreeCellRenderer
{
  //ImageIcon expandedI;
  //ImageIcon collapsedI;
  GoalTreeCellRenderer()
  {
    Color myBg = Ggui.darkBackground;
    setBackground(myBg);
    setBackgroundNonSelectionColor(myBg);
    setBackgroundSelectionColor(Ggui.buttonRollColor()); //myBg);
    
    setFont(new Font("Arial",Font.PLAIN,16));
    setBorderSelectionColor(Color.black); //Color.white);
    setTextNonSelectionColor(Color.white); //new Color(230,179,0));
    setTextSelectionColor(Color.black); //Color.orange);
    //setFont(Font font);
    //expandedI = Ggui.imgIconGetResource("treeexpanded.png");
    //collapsedI = Ggui.imgIconGetResource("treecollapsed.png");
  }
  public Component getTreeCellRendererComponent( JTree tree,
                                                 Object value,
                                                 boolean sel,
                                                 boolean expanded,
                                                 boolean leaf,
                                                 int row,
                                                 boolean hasFocus)
  {
    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    ConnectedGoal cg = (ConnectedGoal) value;

    setOpenIcon(null); //expandedI);
    setClosedIcon(null); //collapsedI);
    setLeafIcon(null); //cg.icon);
    setIcon(null); //cg.icon);
    //setToolTipText(cg.tt);
    if(cg.goal != null && cg.goal.isSelectable())
    {
      setForeground(Color.white);
      //setBorder(new EmptyBorder(5,0,0,0));             no effect
      //setFont(getFont().deriveFont(getFont().getSize()+4));
    }
    return this;
  }
}
