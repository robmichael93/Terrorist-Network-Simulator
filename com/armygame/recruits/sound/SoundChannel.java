package com.armygame.recruits.sound;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import java.util.Stack;
import java.util.Iterator;

import com.armygame.recruits.playlist.Playlist;
import com.armygame.recruits.playlist.MediaPlaybackSynchronizer;
import com.armygame.recruits.playlist.MediaSynchronizationEvent;
import com.armygame.recruits.globals.ResourceReader;


// This version plays mp3 files

public class SoundChannel implements Runnable {
	Stack sounds = new Stack();
	Thread thread;
	String status;

	public SoundChannel(boolean preBufferFlag) {
		try {
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private class Sound {
		File file;
		AudioInputStream input;
		int count;
		MediaPlaybackSynchronizer invoker = null;
		com.armygame.recruits.playlist.SoundChannel soundChan;

		protected Sound(String filename, int loopCount) throws Exception {
			file = new File(filename);
			read();
			count = loopCount;
		}

		protected void read() throws Exception {
 System.out.println("*******00000000******** "+file.toString());

			input = AudioSystem.getAudioInputStream( ResourceReader.instance().getURL(file.toString()) );
			input = fixFormat(input);
		}
	}

	public synchronized void QueueSound(String filename, int loopCount) {
		try {
			sounds.add(0, new Sound(filename, loopCount));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void Reset() {
		sounds.clear();
	}

	public synchronized void Stop(MediaPlaybackSynchronizer synchronizer,
		   com.armygame.recruits.playlist.SoundChannel soundChan) {
		status = "Sound Stopped";
		//sounds.clear(); // comment out to generate sound stopped events
		thread = null;
	}

	public synchronized void Play(MediaPlaybackSynchronizer invoker,
			com.armygame.recruits.playlist.SoundChannel soundChan) {
		Sound s = (Sound)sounds.firstElement();
		s.invoker = invoker;
		s.soundChan = soundChan;
		status = "Sound Completed";
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private AudioInputStream fixFormat(AudioInputStream input) {
		AudioInputStream ret = input;
		AudioFormat format = input.getFormat();
System.out.println("*************** "+format.getEncoding().toString());
		if ( ( format.getEncoding() == AudioFormat.Encoding.ULAW ) ||
               ( format.getEncoding() == AudioFormat.Encoding.ALAW ) ||
               ( format.getEncoding().toString().indexOf("MP") >= 0) ) {
						AudioFormat	newFormat = new AudioFormat(
						    AudioFormat.Encoding.PCM_SIGNED,
						    format.getSampleRate(),
						    format.getSampleSizeInBits(),
						    format.getChannels(),
						    format.getChannels() * 2,
						    format.getSampleRate(),
						    false);
            ret = AudioSystem.getAudioInputStream(newFormat, input);
		}
		return(ret);
	}

	private void writeSound(Sound s, SourceDataLine line) throws Exception {
		byte b[] =  new byte[1024*4];
		for (int i=0; (i < s.count) && (thread != null); ++i) {
		   if (i > 0)
				s.read();
			while(thread != null) {
			   int qty = s.input.read(b);
			   if (qty < 0) break;
				line.write(b, 0, qty);
			}
		}
	}

	public synchronized void run() {
		while (!sounds.empty()) {
			Sound s = (Sound)sounds.pop();
			AudioFormat format = s.input.getFormat();
			DataLine.Info lineInfo =
				new DataLine.Info(SourceDataLine.class, format);
			SourceDataLine line = null;
			try {
				line = (SourceDataLine)AudioSystem.getLine(lineInfo);
	   		line.open(format);
				line.start();
			   writeSound(s, line);
		      line.drain();
		      line.stop();
			   line.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			if (s.invoker != null)
				s.invoker.TestEvent(new MediaSynchronizationEvent(status,
						   s.soundChan.toString()));
		}
		thread = null;
	}
}
