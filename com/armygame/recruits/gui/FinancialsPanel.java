/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: May 15, 2002
 * Time: 10:34:09 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.armygame.recruits.gui;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.net.URL;

import com.armygame.recruits.storyelements.sceneelements.Goal;
import com.armygame.recruits.storyelements.sceneelements.FinanceData;

public class FinancialsPanel  extends RPanel
{
  MainFrame mf;
  DrawSurface drawSurface;

  JLabel iconLab;
  JEditorPane content;
  JButton closeButt;
  JTable incomeTab, billsTab;
  Object[] billsHdr = {"","","","","",""};
  Object[] incomeHdr = {"","",""};
  Object data0[][]={
    {"Auto",new Float(110.00),"monthly",new Integer(5),new Integer(21),new Integer(1)},
    {"Rent",new Float(800.00),"monthly",new Integer(5),new Integer(21),new Integer(0)},
    {"Auto",new Float(110.00),"monthly",new Integer(5),new Integer(21),new Integer(1)},
    {"Rent",new Float(800.00),"monthly",new Integer(5),new Integer(21),new Integer(0)},
    {"Auto",new Float(110.00),"monthly",new Integer(5),new Integer(21),new Integer(1)},
    {"Rent",new Float(800.00),"monthly",new Integer(5),new Integer(21),new Integer(0)},
    {"Auto",new Float(110.00),"monthly",new Integer(5),new Integer(21),new Integer(1)},
    {"Rent",new Float(800.00),"monthly",new Integer(5),new Integer(21),new Integer(0)},
  };
  Object data[][]={
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
    {"E1 pay",new Float(1200.00),"bi-week"},
    {"Spouse pay",new Float(1100.00),"bi-week"} ,
  };

  FinancialsPanel(MainFrame main)
  {
    mf = main;
    setBackground(Ggui.darkBackground);
    setLayout(null);

    billsTab = new JTable(new MyTableModel(data0,billsHdr));

    //JScrollPane billsTab = new JScrollPane(table);
    //table.setPreferredScrollableViewportSize(new Dimension(450,120));
    //billsTab.setDefaultRenderer(String.class,new TabRender
    billsTab.setShowVerticalLines(false);
    billsTab.setShowHorizontalLines(false);
    //billsTab.setLocation(125,340);
    //billsTab.setSize(billsTab.getPreferredSize());
    billsTab.setBorder(null);
    billsTab.setFont(Ggui.medButtonFont());
    billsTab.setForeground(Ggui.buttonForeground());
    billsTab.setBackground(Ggui.darkBackground);
    billsTab.setSelectionBackground(Ggui.buttonRollColor());
    billsTab.setSelectionForeground(Color.black);

    JScrollPane billsJsp = new JScrollPane(billsTab);
    billsTab.setPreferredScrollableViewportSize(new Dimension (465,110));
    billsJsp.setLocation(130,300); //340);
    billsJsp.setSize(billsJsp.getPreferredSize());
    billsJsp.setBorder(BorderFactory.createLineBorder(Ggui.darkBackground));
    billsJsp.getViewport().setBackground(Ggui.darkBackground);
    add(billsJsp);

    incomeTab = new JTable(new MyTableModel(data,incomeHdr));

    //JScrollPane incomeTab = new JScrollPane(table);
    //table.setPreferredScrollableViewportSize(new Dimension(450,120));
    incomeTab.setShowVerticalLines(false);
    incomeTab.setShowHorizontalLines(false);
    //incomeTab.setLocation(125,105);
    //incomeTab.setSize(incomeTab.getPreferredSize());
    incomeTab.setBorder(null);
    incomeTab.setFont(Ggui.medButtonFont());
    incomeTab.setForeground(Ggui.buttonForeground());
    incomeTab.setBackground(Ggui.darkBackground);
    incomeTab.setSelectionBackground(Ggui.buttonRollColor());
    incomeTab.setSelectionForeground(Color.black);

    JScrollPane incomeJsp = new JScrollPane(incomeTab);
    incomeTab.setPreferredScrollableViewportSize(new Dimension (270,175));
    incomeJsp.setLocation(130,65); //105);
    incomeJsp.setSize(incomeJsp.getPreferredSize());
    incomeJsp.setBorder(BorderFactory.createLineBorder(Ggui.darkBackground));
    incomeJsp.getViewport().setBackground(Ggui.darkBackground);
    add(incomeJsp);

    JLabel lab = new JLabel("INCOME");
    lab.setFont(Ggui.bigButtonFont());
    lab.setForeground(Ggui.buttonForeground());
    lab.setSize(lab.getPreferredSize());
    lab.setLocation(30,80); //120);
    add(lab);
    lab=new JLabel("BILLS");
    lab.setFont(Ggui.bigButtonFont());
    lab.setForeground(Ggui.buttonForeground());
    lab.setSize(lab.getPreferredSize());
    lab.setLocation(50,310); //350);
    add(lab);

    JPanel incomeHeader = new JPanel();
    incomeHeader.setLayout(new BoxLayout(incomeHeader,BoxLayout.X_AXIS));

    incomeHeader.add(makeHeaderLabel("Name"));
    incomeHeader.add(Box.createHorizontalStrut(50));
    incomeHeader.add(makeHeaderLabel("Amount"));
    incomeHeader.add(Box.createHorizontalStrut(30));
    incomeHeader.add(makeHeaderLabel("Frequency"));
    incomeHeader.setOpaque(false);
    incomeHeader.setSize(incomeHeader.getPreferredSize());
    incomeHeader.setLocation(130,40); //80);
    add(incomeHeader);

    JPanel billsHeader = new JPanel();
    billsHeader.setLayout(new BoxLayout(billsHeader,BoxLayout.X_AXIS));
    billsHeader.add(makeHeaderLabel("Name"));
    billsHeader.add(Box.createHorizontalStrut(20));
    billsHeader.add(makeHeaderLabel("Amount"));
    billsHeader.add(Box.createHorizontalStrut(20));
    billsHeader.add(makeHeaderLabel("Frequency"));
    billsHeader.add(Box.createHorizontalStrut(20));
    billsHeader.add(makeHeaderLabel("Max"));
    billsHeader.add(Box.createHorizontalStrut(20));
    JPanel p = new JPanel();
    p.setOpaque(false);
    p.setAlignmentY(BOTTOM_ALIGNMENT);
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
    p.add(makeHeaderLabel("Payments"));
    p.add(makeHeaderLabel("Remaining"));
    billsHeader.add(p);
    billsHeader.add(Box.createHorizontalStrut(20));
    p = new JPanel();
    p.setOpaque(false);
    p.setAlignmentY(BOTTOM_ALIGNMENT);
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
    p.add(makeHeaderLabel("Payments"));
    p.add(makeHeaderLabel("Missed"));
    billsHeader.add(p);

    billsHeader.setOpaque(false);
    billsHeader.setSize(billsHeader.getPreferredSize());
    billsHeader.setLocation(130,250); //290);
    add(billsHeader);

    closeButt = ButtonFactory.make(ButtonFactory.FINANCIALSCLOSE,mf);
    add(closeButt);

    drawSurface = new DrawSurface();
    drawSurface.setBounds(0,0,640,480);
    drawSurface.setOpaque(false);
    add(drawSurface);

    JLabel backLabel = new JLabel(Ggui.imgIconGet("FINANCIALSBACK"));
    backLabel.setBounds(new Rectangle(0,0,640,480));
    add(backLabel);

    setVisible(false);
  }
  JLabel makeHeaderLabel(String s)
  {
    JLabel lab = new JLabel(s);
    lab.setFont(Ggui.medButtonFont());
    lab.setForeground(Ggui.buttonForeground());
    lab.setAlignmentX(LEFT_ALIGNMENT);
    lab.setAlignmentY(BOTTOM_ALIGNMENT);
    return lab;
  }
  public void go()
  {
    rebuild();
    super.go();
    mf.setTitleBar("FINANCIALSTITLE");
  }
  private Object[][] doFinanceDebitRecords(Vector v)
  {
    int i;
    Iterator itr;

    if(v == null)
      return new Object[0][0];  //return data0;
    else
    {
      Object[][] data = new Object[v.size()][6];
      for(itr = v.iterator(),i=0;itr.hasNext();i++)
      {
        FinanceData d = (FinanceData)itr.next();
        data[i][0] = d.name;
        data[i][1] = new Integer(d.amount);
        data[i][2] = new Integer(d.frequency);
        data[i][3] = new Integer(d.maxNumberOfOccurances);
        data[i][4] = new Integer(d.occurancesRemaining);
        data[i][5] = new Integer(d.missedPayments);
      }
      return data;
    }
  }
  private Object[][] doFinanceCreditRecords(Vector v)
  {
    int i;
    Iterator itr;
    if(v == null)
      return new Object[0][0];  // return data
    else
    {
      Object[][] data = new Object[v.size()][3];

      for(itr = v.iterator(),i=0;itr.hasNext();i++)
      {
        FinanceData d = (FinanceData)itr.next();
        data[i][0] = d.name;
        data[i][1] = new Integer(d.amount);
        data[i][2] = new Integer(d.frequency);
      }
      return data;
    }
  }
  private void rebuild()
  {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(0);
    String timeField = nf.format(mf.globals.charinsides.time);

    //NumberFormat nf = NumberFormat.getCurrencyInstance();
    String bankBalance = nf.format(mf.globals.charinsides.bankBalance);

    Vector v = mf.globals.charinsides.debits;
    if(v != null && v.size() == 0)
      v = null;
    billsTab.setModel(new MyTableModel(doFinanceDebitRecords(v),billsHdr));
    TableColumnModel tcm = billsTab.getColumnModel();
    tcm.getColumn(0).setPreferredWidth(65);
    tcm.getColumn(1).setPreferredWidth(80);
    tcm.getColumn(2).setPreferredWidth(100);
    tcm.getColumn(3).setPreferredWidth(50);
    tcm.getColumn(4).setPreferredWidth(100);
    tcm.getColumn(5).setPreferredWidth(70);

    v = mf.globals.charinsides.credits;
    if(v != null && v.size() == 0)
      v = null;
    incomeTab.setModel(new MyTableModel(doFinanceCreditRecords(v),incomeHdr));
    tcm = incomeTab.getColumnModel();
    tcm.getColumn(0).setPreferredWidth(100);
    tcm.getColumn(1).setPreferredWidth(70);
    tcm.getColumn(2).setPreferredWidth(100);

  }

  class DrawSurface extends JPanel
  {
    Color top = new Color(128,128,128);
    Color bot = new Color(167,167,167);
    Color left = new Color(197,197,197);
    Color mid = Color.white;
    Color right = new Color(101,101,101);

    Rectangle topR = new Rectangle( 50,/*100*/60,570,/*100*/60 );
    Rectangle botR = new Rectangle( 50,/*330*/290,570,/*330*/290 );
    Rectangle leftR = new Rectangle( 115,/*80*/40,115,/*455*/415 );
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);

      g.setColor(top);
      g.drawLine(topR.x,topR.y,topR.width,topR.height);
      g.setColor(bot);
      g.drawLine(topR.x,topR.y+1,topR.width,topR.height+1);

      g.setColor(top);
      g.drawLine(botR.x,botR.y,botR.width,botR.height);
      g.setColor(bot);
      g.drawLine(botR.x,botR.y+1,botR.width,botR.height+1);

      g.setColor(left);
      g.drawLine(leftR.x,leftR.y,leftR.width,leftR.height);
      g.setColor(mid);
      g.drawLine(leftR.x+1,leftR.y,leftR.width+1,leftR.height);
      g.setColor(right);
      g.drawLine(leftR.x+2,leftR.y,leftR.width+2,leftR.height);
    }
  }
  class MyTableModel extends AbstractTableModel
  {
    Object data[][];
    Object hdr[];
    MyTableModel(Object data[][],Object hdr[])
    {
      this.data = data;
      this.hdr = hdr;
    }
    public boolean isCellEditable(int rowIndex,int columnIndex)
    {
      return false;
    }
    public int getRowCount() { return data.length; }
    public int getColumnCount() { return hdr.length; }
    public String getColumnName(int column) { return "";/*hdr[column];*/ }

    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);

    }
  }
}
/* in charinsides
  public Vector debits;
   public Vector credits;
   public double bankBalance;
   public double time;
*/