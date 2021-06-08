/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 21, 2002
 * Time: 11:52:56 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui.laf;

import com.armygame.recruits.gui.MainFrame;
import com.armygame.recruits.gui.Ggui;

import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.net.URL;

public class RecruitsScrollBarUI extends javax.swing.plaf.metal.MetalScrollBarUI
{
  public static ComponentUI createUI(JComponent c)
  {
    return new RecruitsScrollBarUI();
  }
  static ImageIcon upButt,downButt,leftButt,rightButt;
  static private Border myBorder;
  static private JButton dummy;
  static private Dimension dummyDim;
  static
  {
    upButt = Ggui.imgIconGetResource("upthumb1.png");
    downButt = Ggui.imgIconGetResource("downthumb1.png");
    leftButt = Ggui.imgIconGetResource("leftthumb1.png");
    rightButt = Ggui.imgIconGetResource("rightthumb1.png");
    myBorder =  null;
    dummy = new JButton(upButt);
    dummy.setBorder(myBorder);
  }
  public static Dimension getRecruitsButtSize()
  {
    return dummy.getPreferredSize();
  }
  protected Dimension getMinimumButtSize()
  {
    return getRecruitsButtSize();
  }
  protected JButton createDecreaseButton(int orientation)
  {
    switch(orientation)
    {
    case NORTH:
      return makeB(upButt);
    case WEST:
      return makeB(leftButt);
    default:
      System.out.println("bogus...createIncreaseButton()");
    }
    return null;
  }

  protected JButton createIncreaseButton(int orientation)
  {
    switch(orientation)
    {
    case SOUTH:
      return makeB(downButt);
    case EAST:
      return makeB(rightButt);
    default:
      System.out.println("bogus...createIncreaseButton()");
    }
    return null;
  }
  private JButton makeB(ImageIcon ii)
  {
    JButton b = new JButton(ii);
    b.setBorder(myBorder);
    b.setContentAreaFilled(false);
    //b.setPreferredSize(dummyDim);
    return b;
  }
  //protected void paintButt(Graphics g, JComponent c, Rectangle ButtBounds)
  //{
  //  super.paintButt(g, c, ButtBounds);
  //}
}
