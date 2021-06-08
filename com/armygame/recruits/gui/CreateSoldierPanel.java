/*
 * Soldiers / Army Game Project
 * MOVES Institute, Naval Postgraduate School
 * By:   Mike Bailey
 * Date: Jun 11, 2002
 * Time: 1:53:28 PM
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.gui.laf.ShadowBorder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;

public class CreateSoldierPanel extends RPanel
/********************************************/
{
  MainFrame mf;
  Object[] oa;
  int lastSelected = -1;
  Border selectedBorder,unselectedBorder;
  JButton heads[];
  ImageIcon headsI[];
  ImageIcon heads_grey[];
  JLabel portrait;
  JList jlist;
  JScrollPane jsp;
  String chosen;
  JLabel caption;
  JButton valuesButt,goalsButt,resButt,saveButt,proceedButt,cancelButt;
  JLabel name, adjust;

  CreateSoldierPanel(MainFrame main)
  //================================
  {
    mf=main;
    setLayout(null);

    selectedBorder = BorderFactory.createCompoundBorder(
                     BorderFactory.createLineBorder(Ggui.buttonForeground(),1),
                     BorderFactory.createRaisedBevelBorder());

    unselectedBorder = BorderFactory.createCompoundBorder(
                     BorderFactory.createEmptyBorder(1,1,1,1),
                     BorderFactory.createRaisedBevelBorder());

    ActionListener selList = new ActionListener()
    {
       public void actionPerformed(ActionEvent ev)
       {
         int i = Integer.parseInt(ev.getActionCommand());

         if(lastSelected != -1)
         {
           heads[lastSelected].setIcon(heads_grey[lastSelected]);
           heads[lastSelected].setBorder(unselectedBorder);
         }
         heads[i].setIcon(headsI[i]);
         heads[i].setBorder(selectedBorder);
         lastSelected=i;
         portrait.setIcon(headsI[i]);

         jlist.setEnabled(true);
         jsp.getVerticalScrollBar().setEnabled(true);
         name.setEnabled(true);
         mf.clickButton();
       }
    };

    MouseAdapter mouseList = new MouseAdapter()
    {
      public void mouseEntered(MouseEvent e)
      {
        IndexedJButton butt = (IndexedJButton)e.getSource();
        butt.setIcon(headsI[butt.idx]);
      }
      public void mouseExited(MouseEvent e)
      {
        IndexedJButton butt = (IndexedJButton)e.getSource();
        if(butt.idx != lastSelected)
          butt.setIcon(heads_grey[butt.idx]);
      }
    };

    int numHeads = Ggui.getIntProp("NUM_SOLDIERHEADS");
    String[] names = new String[numHeads];
             heads = new JButton[numHeads];
            headsI = new ImageIcon[numHeads];
        heads_grey = new ImageIcon[numHeads];

    JPanel headPanel = new JPanel();
    headPanel.setLayout(new BoxLayout(headPanel,BoxLayout.X_AXIS));
    headPanel.setBackground(Ggui.darkBackground);

    for(int i=0;i<numHeads;i++)
    {
      headPanel.add(Box.createHorizontalStrut(20));

      names[i] = Ggui.getProp("ACTOR"+i);
      headsI[i] = Ggui.imgIconGet(names[i] + "portrait");
      heads_grey[i] = Ggui.imgIconGet(names[i] + "portrait_grey");

      heads[i] = new IndexedJButton(i,heads_grey[i]);
      heads[i].setBorder(unselectedBorder);
      heads[i].addActionListener(selList);
      heads[i].setActionCommand(""+i);

      heads[i].addActionListener(selList);
      heads[i].addMouseListener(mouseList);
      headPanel.add(heads[i]);
    }

    headPanel.add(Box.createHorizontalStrut(20));
    JScrollPane hjsp = new JScrollPane(headPanel);
    hjsp.setPreferredSize(new Dimension(460,heads[0].getPreferredSize().height+30));
    hjsp.setSize(hjsp.getPreferredSize());
    hjsp.setLocation(95,52);
    hjsp.setBorder(null);
    add(hjsp);

    portrait = new JLabel(Ggui.imgIconGet("Blankportrait"));
    portrait.setSize(portrait.getPreferredSize());
    portrait.setBorder(BorderFactory.createRaisedBevelBorder());
    portrait.setLocation(160,230);
    add(portrait);

    JLabel plateImg = new JLabel(Ggui.imgIconGetResource("nameplate.png"));
    plateImg.setLocation(0,0);
    plateImg.setSize(plateImg.getPreferredSize());

    JPanel plateBack = new JPanel();
    plateBack.setLayout(null);
    plateBack.setSize(plateImg.getSize());

    caption = new JLabel("");
    int captionY = 230 + portrait.getSize().height + 5;

    caption.setLocation(0,0);
    caption.setSize(plateImg.getSize());
    caption.setForeground(Ggui.buttonForeground());
    caption.setHorizontalAlignment(SwingConstants.CENTER);
    caption.setFont(Ggui.buttonFont());

    plateBack.add(caption);
    plateBack.add(plateImg);
    plateBack.setLocation(160,captionY);
    add(plateBack);

    SoldierNameProperties namePs = new SoldierNameProperties();

    Vector vv = new Vector();

    for(Enumeration enn = namePs.propertyNames();enn.hasMoreElements();)
    {
      vv.add(enn.nextElement());
    }
    Collections.sort(vv);
    oa = vv.toArray();

    jlist = new RecruitsJList(oa); //rls.jlist;
    //jlist.setForeground(Color.white); //Ggui.buttonForeground());
    //jlist.setBackground(Ggui.darkBackground); //mediumBackground);
    //jlist.setFont(Ggui.statusLineFont); //Ggui.buttonFont());
    //jlist.setSelectionBackground(Ggui.buttonForeground());
    //jlist.setSelectionForeground(Color.black);
    jlist.setEnabled(false);

    jlist.getSelectionModel().addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if(e.getValueIsAdjusting())
          return;
        int index = jlist.getSelectedIndex();
        chosen = (String)oa[index];
        caption.setText(chosen);

        valuesButt.setEnabled(true);
        goalsButt.setEnabled(true);
        resButt.setEnabled(true);
        //saveButt.setEnabled(true);
        proceedButt.setEnabled(true);
        adjust.setEnabled(true);
        mf.clickButton();
      }
    });



    jsp = new JScrollPane(jlist);

    jsp.setPreferredSize(new Dimension(180,135));
    jsp.setSize(jsp.getPreferredSize());
    jsp.setBackground(Ggui.medDarkBackground);
    jsp.setBorder(BorderFactory.createRaisedBevelBorder());
    jsp.getVerticalScrollBar().setEnabled(false);

    jsp.setLocation(300,201);
    add(jsp);

    JLabel actor = new JLabel("Select the actor you wish to play your soldier.");
    actor.setFont(Ggui.createSoldierFont);
    actor.setForeground(Color.white);
    actor.setSize(actor.getPreferredSize());
    actor.setLocation(20,11);
    add(actor);

    name = new JLabel("Choose a name for your soldier.");
    name.setFont(Ggui.createSoldierFont);
    name.setForeground(Color.white);
    name.setSize(name.getPreferredSize());
    name.setLocation(20,171);
    name.setEnabled(false);
    add(name);

    adjust = new JLabel("Adjust your soldier's Values, Goals and Resources.");
    adjust.setFont(Ggui.createSoldierFont);
    adjust.setForeground(Color.white);
    adjust.setSize(adjust.getPreferredSize());
    adjust.setLocation(20,362);
    adjust.setEnabled(false);
    add(adjust);

    ActionListener saveButtonEnabler = new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        saveButt.setEnabled(true);
      }
    };
    valuesButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERVALUES,mf);
    valuesButt.setEnabled(false);
    valuesButt.addActionListener(saveButtonEnabler);
    add(valuesButt);

    goalsButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERGOALS,mf);
    goalsButt.setEnabled(false);
    goalsButt.addActionListener(saveButtonEnabler);
    add(goalsButt);

    resButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERRESOURCES,mf);
    resButt.setEnabled(false);
    resButt.addActionListener(saveButtonEnabler);
    add(resButt);

    saveButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERSAVE,mf);
    saveButt.setEnabled(false);
    add(saveButt);

    cancelButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERCANCEL,mf);
    cancelButt.setEnabled(true);
    add(cancelButt);

    proceedButt = ButtonFactory.make(ButtonFactory.CREATESOLDIERPROCEED,mf);
    proceedButt.setEnabled(false);
    add(proceedButt);


    JLabel backLabel = new JLabel(Ggui.imgIconGet("CREATESOLDIERBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    //backLabel.setBorder(BorderFactory.createRaisedBevelBorder());
    add(backLabel);


    setVisible(false);
  }
  public void go()
  {
    mf.setTitleBar("CREATESOLDIERTITLE");
    super.go();
  }

  public int getActorIndex()
  //------------------------
  {
    return lastSelected;
  }

  public String getSoldierName()
  //----------------------------
  {
    return chosen;
  }
  class IndexedJButton extends JButton
  {
    int idx;
    IndexedJButton(int idx, ImageIcon ic)
    {
      super(ic);
      this.idx = idx;
    }
  }
}
