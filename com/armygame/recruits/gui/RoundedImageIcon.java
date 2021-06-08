/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 29, 2002
 * Time: 3:34:15 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.net.URL;

public class RoundedImageIcon extends ImageIcon
{
  double arc;
  public RoundedImageIcon (URL location, double arc)
  {
    super(location);
    this.arc = arc;
  }

  public synchronized void paintIcon(Component c, Graphics g, int x, int y)
  {
    Graphics2D g2 = (Graphics2D)g;
    Color oldCol = g.getColor();

    g.setColor(new Color(0,0,0,0));
    ImageObserver imob = super.getImageObserver();
    g.fillRect(x,y,super.getImage().getWidth(imob),super.getImage().getHeight(imob));


    Shape s = new RoundRectangle2D.Double( (double)x,(double)y,
    (double)super.getImage().getWidth(imob),
    (double)super.getImage().getHeight(imob),
    arc,arc);

    Shape sold = g.getClip();
    g2.clip(s);
    if(super.getImageObserver() == null)
    {
      g.drawImage(super.getImage(), x, y, c);
    }
    else
    {
      g.drawImage(super.getImage(), x, y, super.getImageObserver());
    }
    g.setClip(sold);
  }

}
