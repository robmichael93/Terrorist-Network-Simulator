package com.armygame.recruits.gui;

import java.awt.*;
import java.net.URL;
import java.util.Properties;
import com.armygame.recruits.RecruitsProperties;
/**
 * This file only uses AWT APIs so that it is displayed faster on startup.
 */
public class RecruitsSplash extends Canvas
{
  //public RecruitsProperties guiProps;
  public int numSteps = 7;
  public RecruitsSplash()
  {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    // use this method to get art from the jar
    //guiProps = new RecruitsProperties("recruitsGui.properties");
    //String imgName = guiProps.getProperty("SPLASH");
    //ClassLoader cl = this.getClass().getClassLoader();
    //image = getToolkit().getImage(cl.getResource(imgName));
    image = Ggui.imgIconGet("SPLASH").getImage();

    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(image,0);

    try
    {
      tracker.waitForAll();
    }
    catch(Exception e)
    {
      //Log.log(Log.ERROR,this,e);
    }

    win = new Window(new Frame());

    Dimension screen = getToolkit().getScreenSize();
    Dimension size = new Dimension(image.getWidth(this) + 2,
                                   image.getHeight(this) + 2);
    win.setSize(size);

    win.setLayout(new BorderLayout());
    win.add(BorderLayout.CENTER,this);

    win.setLocation((screen.width - size.width) / 2,
                    (screen.height - size.height) / 2);
    win.validate();
    win.show();

    /*synchronized(this)
    {
    try
    {
    wait();
    }
    catch(InterruptedException ie)
    {
    Log.log(Log.ERROR,this,ie);
    }
    }*/
  }

  public void dispose()
  {
    win.dispose();
  }

  public synchronized void advance()
  {
    progress++;
    repaint();

    // wait for it to be painted to ensure progress is updated
    // continuously
    try
    {
      wait();
    }
    catch(InterruptedException ie)
    {
      //Log.log(Log.ERROR,this,ie);
    }
  }

  public void update(Graphics g)
  {
    paint(g);
  }

  public synchronized void paint(Graphics g)
  {
    Dimension size = getSize();

    if(offscreenImg == null)
    {
      offscreenImg = createImage(size.width,size.height);
      offscreenGfx = offscreenImg.getGraphics();
    }

    offscreenGfx.setColor(Color.black);
    offscreenGfx.drawRect(0,0,size.width - 1,size.height - 1);

    offscreenGfx.drawImage(image,1,1,this);

    // XXX: This should not be hardcoded
    //offscreenGfx.setColor(new Color(206,206,229));
    offscreenGfx.setColor(new Color(247,214,0));
    //offscreenGfx.fillRect(9,199,(384 * progress) / 7,14);
    if(progress != 0)
    //offscreenGfx.fillRect(17,118,(345 * progress) / 7,7);
      offscreenGfx.fillRect(92,405,(308 * progress) / numSteps,numSteps);

    g.drawImage(offscreenImg,0,0,this);

    notify();
  }

  // private members
  private Window win;
  private Image image;
  private Image offscreenImg;
  private Graphics offscreenGfx;
  private int progress;
}
