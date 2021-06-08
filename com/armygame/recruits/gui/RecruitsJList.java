/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 13, 2002
 * Time: 1:20:27 PM
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;

public class RecruitsJList extends JList
{
  RecruitsJList(Object[] oa)
  {
    super(oa);
    setForeground(Color.white);
    setBackground(Ggui.darkBackground);
    setFont(Ggui.statusLineFont);
    setSelectionBackground(Ggui.buttonRollColor());
    setSelectionForeground(Color.black);
  }
}
