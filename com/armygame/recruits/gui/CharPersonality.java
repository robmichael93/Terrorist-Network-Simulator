/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 1, 2002
 * Time: 4:09:26 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharPersonality extends RPanel
{
  MainFrame mf;
  JLabel portrait,caption;

  CharPersonality(MainFrame main)
  {
    mf = main;
    setLayout(null);

    JButton selectButt = ButtonFactory.make(ButtonFactory.CHARPERSSELECT,mf);
    add(selectButt);

    JButton saveButt = ButtonFactory.make(ButtonFactory.CHARPERSSAVE,mf);
    add(saveButt);

    JButton finiButt = ButtonFactory.make(ButtonFactory.CHARPERSFINI,mf);
    add(finiButt);

    JButton valuesButt = ButtonFactory.make(ButtonFactory.CHARPERSVALUES,mf);
    add(valuesButt);

    JButton resourceButt = ButtonFactory.make(ButtonFactory.CHARPERSRESOURCES,mf);
    add(resourceButt);

    JButton goalsButt = ButtonFactory.make(ButtonFactory.CHARPERSGOALS,mf);
    add(goalsButt);

    portrait = new JLabel(Ggui.imgIconGet("DEFAULTACTORIMG")); //SOLDIERHEAD0"));
    portrait.setSize(portrait.getPreferredSize());
    portrait.setLocation(45,155);
    add(portrait);

    caption = new JLabel("");
    caption.setLocation(35,239);
    caption.setFont(new Font(caption.getFont().getName(),Font.BOLD,18));
    caption.setSize(100,30);
    caption.setHorizontalAlignment(SwingConstants.CENTER);
    caption.setForeground(new Color(0.93f,0.80f,0.02f));
    add(caption);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("CHARPERSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  public void go()
  {
    portrait.setIcon(mf.globals.charHead);

    if(mf.globals.charinsides.charName.length() > 7)
      caption.setFont(new Font(caption.getFont().getName(),Font.BOLD,16));
    else
      caption.setFont(new Font(caption.getFont().getName(),Font.BOLD,18));

    caption.setText(mf.globals.charinsides.charName);
    super.go();
    mf.setTitleBar("PERSONALITYTITLE");
  }
  public void done()
  {
    super.done();
  }
  public void cancel()
  {
  }
}
