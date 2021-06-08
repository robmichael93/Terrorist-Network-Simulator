package com.armygame.recruits.animationplayer;


import java.awt.image.*;
import java.awt.*;


public class ScreenTransitionImage extends BufferedImage {

  private int myFadeRed;
  private int myFadeGreen;
  private int myFadeBlue;

  private int myWidth;
  private int myHeight;
  private byte[] myWriteToPixels;
  private int myCurrentAlpha;
  private DataBufferByte myDataBuffer;
  private WritableRaster myAlphaRaster;

  public ScreenTransitionImage( int width, int height, Color fadeToColor ) {
    super( width, height, BufferedImage.TYPE_4BYTE_ABGR );
    myWidth = width;
    myHeight = height;
    myWriteToPixels = new byte[ 4 * myWidth * myHeight ];
    myFadeRed = fadeToColor.getRed();
    myFadeGreen = fadeToColor.getGreen();
    myFadeBlue = fadeToColor.getBlue();
    myDataBuffer = new DataBufferByte( new byte[][] { myWriteToPixels }, myWidth * myHeight );
    myAlphaRaster = Raster.createWritableRaster( this.getSampleModel(), myDataBuffer, null ); 
    Reset();
  }

  public void UpdateFadeToImage( int alpha ) {
    for( int y = 0; y < myHeight; y++ ) {
      for( int x = 0; x < myWidth; x++ ) {
        myAlphaRaster.setPixel( x, y, new int[] { myFadeRed, myFadeGreen, myFadeBlue, alpha } );
      }
    }
    super.setData( myAlphaRaster );
  }

  public void Reset() {
    myCurrentAlpha = 0;
    UpdateFadeToImage( 0 );
  }

}

