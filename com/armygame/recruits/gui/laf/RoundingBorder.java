/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 29, 2002
 * Time: 10:59:46 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui.laf;
import javax.swing.border.LineBorder;
import javax.swing.*;
import java.awt.*;

public class RoundingBorder extends LineBorder
{
  int thickn=1;
  public RoundingBorder(int thickn)
  {
    super(Color.red,thickn);    // color doesn't matter
    this.thickn = thickn;
  }

  /**
   * Returns the insets of the border.
   * @param c the component for which this border insets value applies
   */
  public Insets getBorderInsets(Component c)
  {
    return new Insets(thickn, thickn, thickn, thickn);
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  {
    Graphics2D g2 = (Graphics2D)g;
    Object oldAntiHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
    Color oldColor = g.getColor();
    Color col = new Color(0,0,0,0); // transparent
    g.setColor(col);
    for(int i=-thickness;i<0;i++)
    {
      if(i == -1)    // single line
      {
        g.setColor(Color.black);
        g.drawRoundRect(x+i,y+i,width-i-i,height-i-i,4*thickness,4*thickness);
        g.setColor(col);
      }
      g.drawRoundRect(x+i,y+i,width-i-i,height-i-i,4*thickness,4*thickness);
    }
    g.setColor(oldColor);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,oldAntiHint);
  }
}
