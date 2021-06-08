/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 30, 2002
 * Time: 9:08:15 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui.laf;

import javax.swing.border.LineBorder;
import javax.swing.*;
import java.awt.*;

public class RoundedLineBorder extends LineBorder
{
  int arc;

  public RoundedLineBorder(Color c, int thickn, int arc)
  {
    super(c,thickn,true);
    this.arc = arc;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  {
    Graphics2D g2 = (Graphics2D)g;
    Color oldColor = g.getColor();
    g.setColor(lineColor);
    Object oldAntiHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

    for(int i = 0; i < thickness; i++)
    {
      if(!roundedCorners)
        g.drawRect(x+i, y+i, width-i-i-1, height-i-i-1);
      else
        g.drawRoundRect(x+i, y+i, width-i-i-1, height-i-i-1, arc,arc);
    }
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,oldAntiHint);
    g.setColor(oldColor);
  }
}