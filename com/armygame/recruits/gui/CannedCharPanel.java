/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 16, 2002
 * Time: 11:15:07 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;

import com.armygame.recruits.storyelements.sceneelements.CharInsides;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CannedCharPanel extends RPanel
{
  MainFrame mf;
  JButton selectButt, cancelButt;
  RecruitsListScroller rls;
  JList jlist;
  private final static int LISTMAXWIDTH = 400;
  private final static int LIST_YOFFSET_TOP = 130;
  private final static int LIST_YOFFSET_BOTTOM = 130;
  private final static int LISTMAXHEIGHT = 480-LIST_YOFFSET_TOP-LIST_YOFFSET_BOTTOM;
  JPanel borderPanel;
  Insets insets;
  int doubleClickedIndex=-1;
  boolean cancelled = false;

  String names[] = {
          "Shelby Carrington",
          "Tamera Davis",
          "James (Jim) Roarke",
          "Michael Levens",
          "Paul Hernandez",
          "Jody Waters",
           };
  String summary[] = {
    "From a small West Texas town.  Has worked hard all his life.  Has high school diploma, "+
    "no advanced education.  Signed up because of Sept. 11.  Ready and willing to learn, but "+
    "suspicious of anyone obviously smarter than himself.",

    "From Inglewood (LA), California.  From a large family.  Combative by nature.  Family solid "+
    "middle class and she wants to do better.  Not athletic, but very competitive.  Wants to go "+
    "to college.  Biggest fear is disappointing her parents.",

    "From a suburb of Seattle, Washington.  Did well in one year of junior college.  Would like "+
    "to make the world a better place.  Has had a good life and wants to give something back.",

    "From Atlanta area.  Good athlete (but not as good as he thinks he is).  Decent grades.  "+
    "Desperate to make something of himself.  Much more of a dreamer/adventurer than his childhood friends.",

    "From small town in New Mexico.  Some vision problems, but made the cut.  Wants to make a career "+
    "to help out his parents and better his life.  Has considerable potential, but constrained by lack "+
    "of intellectual stimulation and the need to service.",

    "Originally from a small town in Nebraska, but lived in several places.  Parents divorced, now lives "+
    "with a girlfriend.  Barely adequate grades and bad habits.  Wicked tongue.  Knows she needs "+
    "self-discipline."
    };

  int values[][] = {
    {30,20,0,20,15,5,10},
    {30,20,0,30,0,20,0},
    {20,5,10,30,20,15,0},
    {20,15,20,10,0,15,20},
    {12,0,25,13,25,25,0},
    {0,10,25,10,15,15,25},
  };
  int resources[][] = {
    {50,50,5,50,5,50},
    {75,45,55,50,5,75},
    {75,80,10,10,55,70},
    {65,50,0,0,0,55},
    {60,0,0,65,0,50},
    {80,55,0,0,0,70},
  };
  //String goals[][] = {
  //}
  CannedCharPanel(MainFrame main)
  {
    mf = main;
    setLayout(null);
    setBackground(new Color(51,51,51));

    selectButt = ButtonFactory.make(ButtonFactory.CANNEDSELECT,mf);
    selectButt.setEnabled(false);
    add(selectButt);

    cancelButt = ButtonFactory.make(ButtonFactory.CANNEDCANCEL,mf);
    add(cancelButt);
 // todo, get this out of careerFields.properties
    rls = new RecruitsListScroller(new Dimension(LISTMAXWIDTH,LISTMAXHEIGHT),names);
    rls.reBuild(names,new Font("Arial",Font.BOLD,18));
     borderPanel = new JPanel();
     borderPanel.setLayout(null);
     borderPanel.setBackground(new Color(51,51,51));   // border uses
     borderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
     insets = borderPanel.getBorder().getBorderInsets(borderPanel);

     dressMeUp();
    borderPanel.add(rls);
    add(borderPanel); //,BorderLayout.CENTER);

    JLabel dragHelp = new JLabel("Drag the bar on the right (if present) up or down to see more names.");
    Font f = new Font("Arial",Font.PLAIN,10);
    dragHelp.setFont(f);
    //dragHelp.setForeground(new Color(230,179,0));
    dragHelp.setForeground(new Color(94,94,94));  // grey
    dragHelp.setSize(dragHelp.getPreferredSize());
    int dy = borderPanel.getY() + borderPanel.getHeight() + 10;
    int dx = (640-dragHelp.getPreferredSize().width)/2;
    dragHelp.setLocation(dx,dy);
    add(dragHelp);

    JLabel doubClickHelp = new JLabel("Double click on a character to learn more about him or her.");
    doubClickHelp.setFont(f);
    doubClickHelp.setForeground(dragHelp.getForeground());
    doubClickHelp.setSize(doubClickHelp.getPreferredSize());
    dy = dragHelp.getY()+dragHelp.getHeight() + 10;
    dx = (640-doubClickHelp.getPreferredSize().width)/2;
    doubClickHelp.setLocation(dx,dy);
    add(doubClickHelp);

    //jlist.addMouseListener( new myMouseAdapter());


    JLabel backLabel = new JLabel(Ggui.imgIconGet("CANNEDBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);
    //setBackground(new Color(38,38,38)); //Color.black);
    setVisible(false);
  }

  private void dressMeUp()
  {
    jlist = rls.jlist;
    int rlsx = (640-rls.getWidth())/2;
    int rlsy =  LIST_YOFFSET_TOP + (LISTMAXHEIGHT-rls.getHeight())/2;

    borderPanel.setBounds(rlsx-insets.left,rlsy-insets.top,
                          rls.getWidth() +insets.left+insets.right,
                          rls.getHeight()+insets.top+insets.bottom);

    rls.setLocation(insets.left,insets.top);

    jlist.addMouseListener( new myMouseAdapter());
  }

  class myMouseAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {

      selectButt.setEnabled(true);
      if (e.getClickCount() == 2)
      {
        doubleClickedIndex = jlist.locationToIndex(e.getPoint());
        if(doubleClickedIndex != -1)
          mf.handlers.eventIn(ButtonFactory.CANNED_DOUBLE_CLICKED, buildString(doubleClickedIndex));
      }
    }
  }
  private String buildString(int indx)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(names[indx]);
    sb.append('\t');
    sb.append(summary[indx]);
    sb.append('\t');

    for(int i = 0;i<values[0].length;i++)
    {
      sb.append(values[indx][i]);
      sb.append('\t');
    }

    for(int i=0;i<resources[0].length;i++)
    {
      sb.append(resources[indx][i]);
      sb.append('\t');
    }
    return sb.toString();
  }

  public void cancel()
  //------------------
  {
    cancelled=true;
  }

  public void done()
  //----------------
  {
    super.done();
    if(!cancelled)
    {
      int indx = rls.jlist.getSelectedIndex();
      CharInsides ci = mf.globals.charinsides;
      double available = ci.unallocated;
      available+=
              ci.loyalty +
              ci.duty +
              ci.respect +
              ci.selfless +
              ci.honor +
              ci.integrity +
              ci.courage;

      ci.loyalty   = values[indx][0]*available/100;
      ci.duty      = values[indx][1]*available/100;
      ci.respect   = values[indx][2]*available/100;
      ci.selfless  = values[indx][3]*available/100;
      ci.honor     = values[indx][4]*available/100;
      ci.integrity = values[indx][5]*available/100;
      ci.courage   = values[indx][6]*available/100;

      available-=(ci.loyalty+ci.duty+ci.respect+ci.selfless+ci.honor+ci.integrity+ci.courage);

      ci.unallocated = available;

      ci.energy = ci.energylast         = resources[indx][0];
      ci.strength = ci.strengthlast     = resources[indx][1];
      ci.knowledge = ci.knowledgelast   = resources[indx][2];
      ci.skill = ci.skilllast           = resources[indx][3];
      ci.financial = ci.financiallast   = resources[indx][4];
      ci.popularity = ci.popularitylast = resources[indx][5];
     }
  }
}


