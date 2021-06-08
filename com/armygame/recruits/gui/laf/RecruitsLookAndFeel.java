/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 21, 2002
 * Time: 9:41:11 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui.laf;

import com.armygame.recruits.gui.Ggui;

import javax.swing.*;
import java.awt.*;

//public class RecruitsLookAndFeel extends javax.swing.plaf.basic.BasicLookAndFeel
public class RecruitsLookAndFeel extends javax.swing.plaf.metal.MetalLookAndFeel
{
  public RecruitsLookAndFeel()
  {

   // UIManager.put("ScrollBar.border",BorderFactory.createLineBorder(Color.green,2));
    UIManager.put("ScrollBar.width",new Integer(21)); //RecruitsScrollBarUI.getRecruitsThumbSize().width));
    UIManager.put("ScrollBar.background",new Color(92,92,92));

    // thumb base color
    UIManager.put("ScrollBar.thumb",new Color(51,51,51));
    // thumb boundaries & highlights
    UIManager.put("ScrollBar.thumbShadow",Color.black);
    UIManager.put("ScrollBar.thumbHighlight",new Color(92,92,92));

    // track
    UIManager.put("ScrollBar.darkShadow",Color.black); //Color.orange);
    UIManager.put("ScrollBar.highlight",new Color(51,51,51));
    UIManager.put("ScrollBar.shadow",new Color(92,92,92)); //Color.red);
    UIManager.put("Button.disabledText",new Color(0.4f,0.4f,0.4f));

    // Titled Border
    UIManager.put("TitledBorder.font",Ggui.buttonFont());
    UIManager.put("TitledBorder.titleColor",Ggui.buttonForeground());

    UIManager.put("ScrollBarUI","com.armygame.recruits.gui.laf.RecruitsScrollBarUI");
    UIManager.put("ButtonUI","com.armygame.recruits.gui.laf.RecruitsButtonUI");

    UIManager.put("Tree.expandedIcon",Ggui.imgIconGetResource("treeexpanded.png"));
    UIManager.put("Tree.collapsedIcon",Ggui.imgIconGetResource("treecollapsed.png"));
  }
}
