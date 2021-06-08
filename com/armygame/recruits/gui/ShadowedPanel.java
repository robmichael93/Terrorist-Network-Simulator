/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jul 3, 2002
 * Time: 4:03:04 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class ShadowedPanel extends JPanel
{
  JPanel outer,inner;
  ShadowedPanel()
  {
    this(Ggui.darkBackground);
  }
  ShadowedPanel(Color back)
  {
    super();
    setOpaque(false);
    setBackground(Ggui.transparent);
    setLayout(null);

    outer = new JPanel();
    outer.setOpaque(false);
    outer.setBorder(new CompoundBorder(new CompoundBorder(new ShadowBorder(8,false,24),
                                                          BorderFactory.createLineBorder(Color.black,1)),
                                       BorderFactory.createEmptyBorder(10,10,10,10)));
    outer.setLayout(null);

    inner = new JPanel();
    inner.setBackground(back);
    inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));


    /// content
    inner.setLocation(10,10);
    outer.add(inner);
    add(outer);

  }
  public Container getContentHolder()
  {
    return inner;
  }
  public Dimension getPreferredSize()
  {
    Dimension d = inner.getPreferredSize();
    inner.setSize(d);
    outer.setSize(d.width+20,d.height+20);
    outer.setLocation((640-d.width-20)/2, (480-d.height-20)/2);
    super.setPreferredSize(outer.getSize());
    super.setSize(outer.getSize());
    return outer.getSize();
  }


}
