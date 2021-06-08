/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 17, 2002
 * Time: 1:10:22 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */

package com.armygame.recruits.gui;

import com.armygame.recruits.globals.ResourceReader;
import com.armygame.recruits.gui.laf.RoundedLineBorder;
import com.armygame.recruits.gui.laf.ShadowBorder;
import com.armygame.recruits.storyelements.sceneelements.AlertMessage;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class ChooserPanel extends RPanel
{
  MainFrame mf;
  JButton selectButt;
  JPanel mosImages;
  JPanel stationsImages;
  JScrollPane stationsJsp;

  ChooserPanel(MainFrame main)
  {
    mf=main;
    loadDataFromFile();

    setLayout(null);
    selectButt=ButtonFactory.make(ButtonFactory.CHOOSERSELECT,mf);
    add(selectButt);
    selectButt.setEnabled(false);    // not until select

    JLabel header = new JLabel("Career Chooser");
    header.setFont(Ggui.bigButtonFont());
    header.setForeground(Ggui.buttonForeground());
    header.setSize(header.getPreferredSize());
    header.setLocation(280,20);
    add(header);

    JLabel dclickLab=new JLabel("Double click on a speciality to learn more about it.");
    Font f=new Font("Arial",Font.PLAIN,10);
    dclickLab.setFont(f);
    dclickLab.setBackground(new Color(94,94,94));  // grey
    dclickLab.setSize(dclickLab.getPreferredSize());
    dclickLab.setLocation(400,106);
    add(dclickLab);

    mosImages=new JPanel();
    mosImages.setLayout(new BoxLayout(mosImages,BoxLayout.Y_AXIS));
    mosImages.setBackground(Ggui.lightBackground);
    stationsImages=new JPanel();
    stationsImages.setLayout(new BoxLayout(stationsImages,BoxLayout.X_AXIS));
    stationsImages.setBackground(Ggui.lightBackground);

    adjustList(null);

    JScrollPane mosJsp=new JScrollPane(mosImages);
    mosJsp.getVerticalScrollBar().setUnitIncrement(15);
    mosJsp.getVerticalScrollBar().setBlockIncrement(86);

    mosJsp.setBorder(BorderFactory.createLineBorder(Color.black,1));
    mosJsp.setPreferredSize(new Dimension(157,312));
    mosJsp.setSize(mosJsp.getPreferredSize());
    mosJsp.setLocation(470,122);
    add(mosJsp);

    JScrollPane stationsJsp=new JScrollPane(stationsImages);
    stationsJsp.getHorizontalScrollBar().setUnitIncrement(22);
    stationsJsp.getHorizontalScrollBar().setBlockIncrement(133);

    stationsJsp.setBorder(BorderFactory.createLineBorder(Color.black,1));
    stationsJsp.setPreferredSize(new Dimension(396,312));
    stationsJsp.setSize(stationsJsp.getPreferredSize());
    stationsJsp.setLocation(34,122);
    add(stationsJsp);

    JLabel backLabel=new JLabel(Ggui.imgIconGet("CHOOSERBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }

  ImageListMouseAdapter ilma=new ImageListMouseAdapter();
  StationsListMouseAdapter stama=new StationsListMouseAdapter();

  JLabel makeMosImage(int i,boolean available)
  {
    JLabel mosL=new TaggedJLabel(Ggui.roundedImgIconGet("MOS"+mosv.get(i)+"IMAGE",24.0),i,available);
    Dimension d=mosL.getPreferredSize();
    d.width+=4+4;
    d.height+=4+4;
    mosL.setSize(d);
    mosL.setPreferredSize(d);
    mosL.setBorder(new ShadowBorder(4,true,24));
    mosL.setAlignmentX(CENTER_ALIGNMENT);
    mosL.setBackground(new Color(0,0,0,0));
    mosL.setOpaque(false);
    mosL.addMouseListener(ilma);
    return mosL;
  }

  JLabel makeStationsImage(int i)
  {
    JLabel sta=new JLabel(Ggui.roundedImgIconGet("CAREERSTEPIMAGE_"+i,24.0));
    Dimension d=sta.getPreferredSize();
    d.width+=4+4;
    d.height+=4+4;
    sta.setSize(d);
    sta.setPreferredSize(d);
    sta.setBorder(new ShadowBorder(4,true,24));
    sta.setAlignmentY(CENTER_ALIGNMENT);
    sta.setBackground(new Color(0,0,0,0));
    sta.setOpaque(false);
    sta.addMouseListener(stama);
    return sta;
  }

  JLabel lastStaClicked;

  class StationsListMouseAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      if(e.getClickCount() == 1)
      {
        JLabel lab=(JLabel)e.getComponent();
        lab.setBorder(new RoundedLineBorder(Ggui.buttonForeground(),4,24));
        if(lastStaClicked != null)
          lastStaClicked.setBorder(new ShadowBorder(4,true,24));
        lastStaClicked=lab;
      }
    }
  }

  JComponent lastClicked;
  int selectedIndex=-1;
  TaggedJLabel selectedTaggedLabel=null;

  class ImageListMouseAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      selectButt.setEnabled(true);
      if(e.getClickCount() == 1)
      {
        JComponent lab=(JComponent)e.getComponent();
        if(((TaggedJLabel)lab).available)
        {
          lab.setBorder(new RoundedLineBorder(Ggui.buttonForeground(),4,24));
          selectedIndex=((TaggedJLabel)lab).idx;
          selectedTaggedLabel=(TaggedJLabel)lab;
          if(lastClicked != null && lastClicked != lab)
            lastClicked.setBorder(new ShadowBorder(4,true,24));
          lastClicked=lab;
        }
      }

      else if(e.getClickCount() == 2)
      {
        TaggedJLabel lab=(TaggedJLabel)e.getComponent();
        int doubleClickedIndex=lab.idx;

        selectedIndex=((TaggedJLabel)lab).idx;
        selectedTaggedLabel=(TaggedJLabel)lab;

        StringBuffer sb=new StringBuffer();
        sb.append(titlesv.get(doubleClickedIndex));

        sb.append("\t");
        sb.append("<b><center><font size=+1>"+titlesv.get(doubleClickedIndex)+"</font></center></b>");
        ArrayList al=(ArrayList)contentv.get(doubleClickedIndex);
        for(Iterator itr=al.iterator();itr.hasNext();)
        //Iterator itr=al.iterator();
        //while(itr.hasNext())
        {
          sb.append("<hr>");
          mosData md=(mosData)itr.next();

          sb.append("<i><b>"+md.title+"</b> "+md.number+"</i><br>");
          if(md.restriction != null)
            sb.append("("+md.restriction+")<br>");
          sb.append(md.description);
        }
        mf.handlers.eventIn(ButtonFactory.MOSCLICKED,sb.toString());
      }
    }
  }

  class TaggedJLabel extends JLabel
  {
    public int idx;
    public boolean available;

    TaggedJLabel(ImageIcon ic,int idx,boolean available)
    {
      super(ic);
      this.idx=idx;
      this.available=available;
      if(!available)
      {
        super.setText("X");
        super.setHorizontalTextPosition(SwingConstants.CENTER);
      }
    }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      if(available)
        return;
      // paint the "not available"
      Color oldc=g.getColor();
      Font oldf=g.getFont();
      g.setColor(new Color(0,0,0,64));
      g.setFont(Ggui.buttonFont());
      ((Graphics2D)g).setStroke(new BasicStroke(3.0f));
      Dimension d=getSize();
      g.fillOval(d.width/2-30,d.height/2-30,60,60);
      g.setColor(Color.black);
      g.drawOval(d.width/2-30,d.height/2-30,60,60);
      double mysine=Math.sin(Math.PI/4);

      g.drawLine((int)(d.width/2-30*mysine),
                 (int)(d.height/2-30*mysine),
                 (int)(d.width/2+30*mysine),
                 (int)(d.height/2+30*mysine));
      g.drawString("not available",20,45);

      g.setFont(oldf);
      g.setColor(oldc);
    }
  }

  public String getSelectedTicket()
  {
    int i=selectedIndex;
    if(selectedTaggedLabel.available)
      return (String)ticketv.get(i);
    else
      return null;
  }

  public void setMessage(AlertMessage m)
  {
    String[] opts=m.getChooserOptions();
    if(opts == null || opts.length == 0)
      return;
    adjustList(opts);
  }

  private void adjustList(String[] opts)
  {
    mosImages.removeAll();
    for(int i=0; i<mosv.size(); i++)
    {
      if(opts != null)
        mosImages.add(makeMosImage(i,matchedTick((String)ticketv.get(i),opts)));
      else
        mosImages.add(makeMosImage(i,true));
      mosImages.add(Box.createVerticalStrut(5));
    }

    stationsImages.removeAll();
    for(int i=0; i<14; i++)
    {
      stationsImages.add(this.makeStationsImage(i));

      stationsImages.add(Box.createHorizontalStrut(3));
      JLabel arrow=new JLabel(Ggui.imgIconGetResource("chooserArrow.png"));
      arrow.setAlignmentY(CENTER_ALIGNMENT);
      stationsImages.add(arrow);
      stationsImages.add(Box.createHorizontalStrut(3));
    }
  }

  private boolean matchedTick(String s,String[] sa)
  {
    for(int i=0; i<sa.length; i++)
    {
      if(sa[i] != null)
        if(s.equalsIgnoreCase(sa[i]))
          return true;
    }
    return false;
  }

  public void done()
  {
    super.done();
  }

  private void loadDataFromFile()
  {
    SAXBuilder bldr=new SAXBuilder();
    Document doc=null;
    try
    {
      doc=bldr.build(ResourceReader.getInputReaderData("MosDescriptions.xml"));
    }
    catch(Exception e)
    {
      System.out.println("Missing MosDescriptions.xml in classpath");
    }
    Element root=doc.getRootElement();
    //root = root.getChild("mosCatalog");
    java.util.List groupList=root.getChildren();
    //Iterator it=groupList.iterator();
    int grpIndex=-1;
    for(Iterator it=groupList.iterator();it.hasNext();)
    //while(it.hasNext())
    {
      grpIndex++;

      Element el=(Element)it.next();

      titlesv.add(el.getAttributeValue("title"));
      mosv.add(el.getAttributeValue("number"));
      ticketv.add(el.getAttributeValue("ticket"));

      java.util.List mosList=el.getChildren();
      //Iterator mit=mosList.iterator();
      int mosIndex=-1;
      ArrayList al=new ArrayList();
      for(Iterator mit=mosList.iterator();mit.hasNext();)
      //while(mit.hasNext())
      {
        mosIndex++;
        Element mos=(Element)mit.next();
        String title=mos.getAttributeValue("title");
        String number=mos.getAttributeValue("number");
        String restr=mos.getAttributeValue("restriction");
        String desc=mos.getTextTrim();
        al.add(new mosData(title,number,desc,restr));
        //get escaped apostrophe...
      }
      contentv.add(al);
    }
  }

  class mosData
  {
    String title,number,description,restriction;

    mosData(String title,String number,String description,String restriction)
    {
      this.title=title;
      this.number=number;
      this.description=description;
      this.restriction=restriction;
    }
  }

  ArrayList mosv=new ArrayList(20);
  ArrayList ticketv=new ArrayList(20);
  ArrayList titlesv=new ArrayList(20);
  ArrayList contentv=new ArrayList(20);
}


