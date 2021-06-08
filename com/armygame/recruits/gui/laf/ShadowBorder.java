/*
* Created by IntelliJ IDEA.
* User: mike
* Date: May 22, 2002
* Time: 2:36:20 PM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package com.armygame.recruits.gui.laf;

import javax.swing.border.LineBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Area;

public class ShadowBorder extends LineBorder
{
  Insets rins;
  int arc;
  public ShadowBorder(int thickn)
  //=============================
  {
    this(thickn,false,0);
  }
  public ShadowBorder(int thickn,boolean rounded, int arc)
  //=============================================
  {
    super(Color.red,thickn,rounded);  // color doesn't matter
    this.arc = arc;
  }
  /**
   * Returns the insets of the border.
   * @param c the component for which this border insets value applies
   */
  public Insets getBorderInsets(Component c)
  //----------------------------------------
  {
    rins = new Insets(thickness,thickness,thickness,thickness);
    if(c instanceof JButton)
    {
      rins = ((JButton)c).getMargin();

      rins.top+=rins.top;
      rins.left+=rins.left;
      rins.bottom+=rins.bottom;
      rins.right+=rins.right;
    }
    return rins;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
  //-----------------------------------------------------------------------------------
  {
    Graphics2D g2d = (Graphics2D)g;

    Shape oldClip  = g.getClip();
    Color oldColor = g.getColor();

    int rwidth = width-thickness-thickness;
    int rheight= height-thickness-thickness;

    int rx = 0;
    int ry = thickness + thickness;

    int startalpha = 255;
    int alphaincr = startalpha/thickness;
    startalpha+=alphaincr;    // to start with

    g2d.setColor(c.getParent().getBackground()); //new Color(0,0,0,0));
    for(int i=thickness-1,j=1;i>=0;i--,j++)
    {
      g.drawRect(x+i,y+i,width-i-i-1,height-i-i-1);

    }

    if(!roundedCorners)
    {
      Polygon p = new Polygon(new int[]{0,0,width,width,thickness,thickness},
                              new int[]{0,height,height,height-thickness,height-thickness,0},6);
      g2d.clip(p);
    }
    else
    {
      Area a = new Area(new RoundRectangle2D.Double( (double)(x+thickness),(double)(y+thickness),
                                                     (double)(width-thickness-thickness),
                                                     (double)(height-thickness-thickness),
                                                     arc,arc));
      a.exclusiveOr(new Area(new Rectangle(x,y,width,height)));
      g2d.clip(a);
    }

    for(int i=thickness-1,j=1;i>=0;i--,j++)
    {
      g2d.setColor(new Color(0,0,0,startalpha-alphaincr*j));
      if(this.roundedCorners)
        g.drawRoundRect(rx+i,ry+i,rwidth-i-i-1, rheight-i-i-1,arc,arc);
      else
        g.drawRect(rx+i,ry+i,rwidth-i-i-1, rheight-i-i-1);
    }
    g.setColor(oldColor);
    g.setClip(oldClip);
  }
}
