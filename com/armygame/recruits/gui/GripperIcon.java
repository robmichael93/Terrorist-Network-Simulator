/*
* Created by IntelliJ IDEA.
* User: mike
* Date: Apr 23, 2002
* Time: 10:45:15 AM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/

package com.armygame.recruits.gui;

import java.awt.*;
import javax.swing.*;

public class GripperIcon implements Icon//, SwingConstants
{
  private int width = 9;
  private int height = 18;
  private Image image;
  private int imgHeight;

  public GripperIcon(ImageIcon img, int height)
  {
    this.height = height;
    this.width  = img.getIconWidth();
    this.image = img.getImage();
    this.imgHeight = img.getIconHeight();
  }

  public int getIconHeight() {
    return height;
  }

  public int getIconWidth() {
    return width;
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    int drawn = 0;
    int workingy = y;
    while(drawn < height)
    {
      g.drawImage(image,x,workingy,null);
      workingy += imgHeight;
      drawn += imgHeight;
    }
  }
}
