package com.armygame.recruits.gui;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

class SoundTest implements Runnable, LineListener {

	private ArrayList mySoundQueue;
	private Object myCurrentSound;
	private Thread myThread;
  private boolean myPlayingFlag;
  private ListIterator myCurrentSoundItr;
  private Clip myPlayingClip;
  private boolean myLoopFlag;


	public SoundTest() {
		mySoundQueue = new ArrayList();
		myCurrentSound = null;
		myThread = null;
    myPlayingFlag = false;
    myCurrentSoundItr = null;
    myPlayingClip = null;
    myLoopFlag = false;
	}

	public void AddSound( String filename ) {
		mySoundQueue.add( new File( filename ) );
	}

	public boolean LoadSound( File currentFile ) {

		try {
			myCurrentSound = AudioSystem.getAudioInputStream( currentFile );
		} catch( IllegalArgumentException iae ) {
			try {
				FileInputStream AltInputStream = new FileInputStream( currentFile );
				myCurrentSound = new BufferedInputStream( AltInputStream, 1024 );
			} catch( Exception e1 ) {
				e1.printStackTrace();
				return false;
			}
		} catch( IOException ie ) {
      ie.printStackTrace();
    } catch( UnsupportedAudioFileException uafe ) {
      uafe.printStackTrace();
    }

		if ( myCurrentSound instanceof AudioInputStream ) {
			try {
				AudioInputStream Stream = (AudioInputStream) myCurrentSound;
				AudioFormat Format = Stream.getFormat();
				if ( ( Format.getEncoding() == AudioFormat.Encoding.ULAW ) ||
						 ( Format.getEncoding() == AudioFormat.Encoding.ALAW ) ) {
					AudioFormat Temp = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED,
																							Format.getSampleRate(),
																							Format.getSampleSizeInBits() * 2,
																							Format.getChannels(),
																							Format.getFrameSize() * 2,
																							Format.getFrameRate(),
																							true );
					Stream = AudioSystem.getAudioInputStream( Temp, Stream );
					Format = Temp;

				}
				DataLine.Info Info = new DataLine.Info( Clip.class,
																								Stream.getFormat(),
																								( (int) Stream.getFrameLength() * Format.getFrameSize() ) );
				Clip PlayClip = (Clip) AudioSystem.getLine( Info );
				PlayClip.addLineListener( this );
        myPlayingFlag = true;
				PlayClip.open( Stream );
				myPlayingClip = PlayClip;
			} catch( Exception e2 ) {
				e2.printStackTrace();
				return false;
			}
		}
		return true;
	}

  public void Loop() {
    myLoopFlag = true;
    Play();
  }
	public void Play() {
    myCurrentSoundItr = mySoundQueue.listIterator();
    start();
	}

	public Thread getThread() {
		return myThread;
	}

	public void start() {
		myThread = new Thread( this );
		myThread.setName( "SoundTest" );
		myThread.start();
	}

	public void stop() {
		if ( myThread != null ) {
			myThread.interrupt();
		}
		myThread = null;
	}

	public void run() {
		// Play all the sounds in the queue
    PlaySound();
  }

  public void StopSound() {
    myPlayingClip.stop();
    myPlayingClip.close();
  }

	public void PlaySound() {
		if( myCurrentSoundItr.hasNext() ) {
			File SoundFile = (File) myCurrentSoundItr.next();
                        System.out.print( "File is -> " + SoundFile.toString() );
			boolean LoadResult = LoadSound( SoundFile );
			if ( LoadResult == false ) {
				System.out.println( "Failed to load " + SoundFile );
			} else {
        if ( myLoopFlag == true ) {
          try {
            myPlayingClip.setLoopPoints( 0, myPlayingClip.getFrameLength() );
            myPlayingClip.loop( javax.sound.sampled.Clip.LOOP_CONTINUOUSLY );
          } catch( IllegalArgumentException iae ) {
            iae.printStackTrace();
          }
        } else {
          myPlayingClip.start();
        }
			}
		} else {
      stop();
      // System.exit(0);
    }
	}

	public void update( LineEvent e ) {
    if ( e.getType() == LineEvent.Type.STOP ) {
			System.out.println( "End of Sound" );
      myPlayingFlag = false;
      StopSound();
      PlaySound();
		}
	}
  public static void main( String[] args ) {
		// Open the sound file given as the program argument and play it
		SoundTest Player = new SoundTest();
		SoundTest Player2 = new SoundTest();
		for( int i = 0; i < args.length; i++ ) {
			Player.AddSound( args[i] );
			Player2.AddSound( args[ args.length - i - 1] );
		}
		Player.Play();
		Player2.Play();
  }
}

