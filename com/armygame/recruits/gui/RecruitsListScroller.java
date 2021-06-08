/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: Apr 26, 2002
 * Time: 8:40:32 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import javax.swing.*;
import java.awt.*;


// The JList widget doesn't support well the idea of swapping in and out models.
// best to rebuild the list each time
public class RecruitsListScroller extends RecruitsWidgetScroller
{
  Object[] data;
  public JList jlist;
  RecruitsListScroller(Dimension maxd, Object[]data)
  {
    this(maxd,data,1);
  }
  RecruitsListScroller(Dimension maxd, Object[]data, int bumpMultiplier)
  {
    super(maxd,bumpMultiplier);
    this.data = data;

    jlist = makeList(data,null);
    myConstructor(jlist);
  }

  private JList makeList(Object[] data, Font ff)
  {
    JList list = new JList(data);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setBackground(new Color(38,38,38));
    list.setForeground(new Color(230,179,0));
    list.setSelectionForeground(Color.green);
    list.setSelectionBackground(list.getBackground());
    if(ff == null)
    {
    Font f = list.getFont();
    f = new Font(f.getName(),f.getStyle(),16);
    list.setFont(f);
  }
    else
      list.setFont(ff);

    return list;
  }
  public void reBuild(Object[]data)
  {
    super.reBuild(jlist = makeList(data,null));
  }
  public void reBuild(Object[]data, Font f)
  {
    super.reBuild(jlist = makeList(data,f));
  }

}
