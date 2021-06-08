/*
* Created by IntelliJ IDEA.
* User: mike
* Date: May 15, 2002
* Time: 2:03:59 PM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package com.armygame.recruits.gui;

import java.awt.*;

public class RankProgressBar extends Canvas
{
  private  int progress;
  //private Image offscreenImg;
  //private Graphics offscreenGfx;
  private Color background;

  RankProgressBar(Color background, Dimension mySize)
  {
    this.background = background;
    setSize(mySize);
  }
  Image barImg;
  RankProgressBar(Color background, Image barImg)
  {
    this.background = background;
    this.barImg = barImg;
  }

  public synchronized void paint(Graphics g)
  {
    Dimension size = getSize();

    if(background != null)
      g.setColor(background);
    else
      g.setColor(new Color(57,57,57));

    g.fillRect(0,0,size.width,size.height);
    g.drawImage(barImg,0,0,(size.width * progress) / 100,size.height,null);
    //notify();
  }
  public void setProgress(int p)
  {
    progress = p;
    repaint();
  }
}
