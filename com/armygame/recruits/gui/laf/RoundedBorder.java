package com.armygame.recruits.gui.laf;

import javax.swing.border.LineBorder;
import javax.swing.*;
import java.awt.*;

public class RoundedBorder extends LineBorder
{
  int thickn=1;
  public RoundedBorder(int thickn)
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
    Insets rins = new Insets(thickn, thickn, thickn, thickn);
    if(c instanceof JButton)
    {
      Insets cins = ((JButton)c).getMargin();

      rins.top+=cins.top;
      rins.left+=cins.left;
      rins.bottom+=cins.bottom;
      rins.right+=cins.right;
    }
    return rins;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  {
    Color oldColor = g.getColor();
    Color start = c.getBackground();
    Component par = c.getParent();
    start = par.getBackground();

    Color col = c.getBackground();

    float lightner=0.25f,darkener = 0.6f;
    float comps[] = col.getColorComponents(null);
    float rincr = ((1.0f-comps[0]) * lightner) / thickness;
    float gincr = ((1.0f-comps[1]) * lightner) / thickness;
    float bincr = ((1.0f-comps[2]) * lightner) / thickness;
    Polygon topRight = new Polygon(new int[]{0,width-1,width-1},new int[]{0,0,height-1},3);
    Polygon botLeft  = new Polygon(new int[]{0,0,width-1},new int[]{0,height-1,height-1},3);
    Shape oldClip = g.getClip();

    g.setClip(topRight);

    for(int i = thickness-1,j=1;i>=0;i--,j++)
    {
      g.setColor(new Color(comps[0]+(rincr*j),comps[1]+(gincr*j),comps[2]+(bincr*j)));
      g.drawRoundRect(x+i,y+i,width-i-i-1, height-i-i-1,3*thickness,3*thickness);
    }

    rincr = comps[0]*darkener / thickness;
    gincr = comps[1]*darkener / thickness;
    bincr = comps[2]*darkener / thickness;

    g.setClip(botLeft);

    for(int i=thickness-1,j=1;i>=0;i--,j++)
    {
      g.setColor(new Color(comps[0]-(rincr*j),comps[1]-(gincr*j),comps[2]-(bincr*j)));
      g.drawRoundRect(x+i,y+i,width-i-i-1, height-i-i-1,3*thickness,3*thickness);
    }

    g.setColor(oldColor);
    g.setClip(oldClip);
  }
}
