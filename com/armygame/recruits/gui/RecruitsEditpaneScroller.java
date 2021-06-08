/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 17, 2002
 * Time: 5:16:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.*;

public class RecruitsEditpaneScroller extends RecruitsWidgetScroller
{
  Object[] data;
  public JEditorPane ep;

  RecruitsEditpaneScroller(Dimension maxd, JEditorPane ep)
  {
    super(maxd);
    this.ep=ep;
    myConstructor(ep);
  }
}
