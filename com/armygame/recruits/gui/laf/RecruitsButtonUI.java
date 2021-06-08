/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 23, 2002
 * Time: 2:48:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui.laf;

//import javax.swing.plaf.basic.BasicGraphicsUtils;
import com.armygame.recruits.gui.Ggui;

import javax.swing.plaf.ComponentUI;
import javax.swing.*;
import java.awt.*;

public class RecruitsButtonUI extends javax.swing.plaf.metal.MetalButtonUI
{
  public static ComponentUI createUI(JComponent c)
  {
    return new RecruitsButtonUI();
  }
  protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text)
  {

    AbstractButton b = (AbstractButton) c;
    ButtonModel model = b.getModel();
    Color oldFore = b.getForeground();
    if(model.isEnabled())
    {
      if(model.isRollover())
         b.setForeground(Ggui.buttonRollColor());
      if(model.isPressed())
        b.setForeground(Ggui.buttonRollColor().darker().darker());
    }
    super.paintText(g,c,textRect,text);
    b.setForeground(oldFore);
  }

}
