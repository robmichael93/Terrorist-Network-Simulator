package com.armygame.recruits.animationplayer;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.Timer;


public class ScreenTransition {

  public interface ScreenTransitionDoneCallback {
    public void ScreenTransitionDone();
    public void ScreenTransitionStep();
  }

  private class ScreenTransListener implements ActionListener {
    public void actionPerformed( ActionEvent e ) {
      DoTransitionStep();
      myDoneCallback.ScreenTransitionStep();
    }
  }
  private final static Color theirDefaultFadeToColor = Color.black;
  private final static int theirDefaultAlphaStep = 12;
  private final static int theirDefaultDelay = 1000 / 24;

  private ScreenTransition.ScreenTransitionDoneCallback myDoneCallback;
  private int myAlphaStep;
  private int myAlpha;
  private ScreenTransitionImage myTransitionImage;
  private Timer myScreenTransTimer;

  public ScreenTransition( ScreenTransition.ScreenTransitionDoneCallback callback, int width, int height ) {
    myDoneCallback = callback;
    myScreenTransTimer = new Timer( theirDefaultDelay, new ScreenTransListener() );
    Initialize( width, height, theirDefaultFadeToColor );
  }

  private void Initialize(  int width, int height, Color fadeToColor ) {
    myAlpha = 0;
    myAlphaStep = theirDefaultAlphaStep;
    myTransitionImage = new ScreenTransitionImage( width, height, fadeToColor );
  }

  public Image GetImage() {
    return myTransitionImage;
  }

  public void Reset() {
    myAlpha = 0;
    myTransitionImage.UpdateFadeToImage( myAlpha );
  }

  public void Start() {
    myScreenTransTimer.start();
  }

  public Image DoTransitionStep() {
    Image Result = null;
    if ( myAlpha == 255 ) {
      myScreenTransTimer.stop();
      myDoneCallback.ScreenTransitionDone();
      Reset();
      myAlpha = 0;
    } else {
      myAlpha += myAlphaStep;
      if ( myAlpha > 255 ) {
        myAlpha = 255;
      }
      myTransitionImage.UpdateFadeToImage( myAlpha );
    }
    Result = myTransitionImage;
    return Result;
  }

}
