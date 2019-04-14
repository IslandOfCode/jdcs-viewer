package it.islandofcode.jdcsviewer.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer implements Runnable {
	
	private PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	
	private URL URL;
	private String USERNAME;
	private String PASSWORD;
	
	private volatile boolean stop = false;
	
	public AudioPlayer(String url, String user, String pass) throws MalformedURLException{
		this.URL = new URL(url);
		this.USERNAME = user;
		this.PASSWORD = pass;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
        PCS.addPropertyChangeListener("PLAYING", listener);
    }
	
	public void shutdown() {
		stop = true;
	}

	@Override
	public void run() {
		try {
			playURL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void playURL() throws IOException {
		
		Authenticator.setDefault (new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication (USERNAME, PASSWORD.toCharArray());
		    }
		});

		AudioInputStream AIS = null;
		SourceDataLine line = null;

		try {
			AIS = AudioSystem.getAudioInputStream(this.URL);

			AudioFormat format = AIS.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);
			
			int framesize = format.getFrameSize();
			
			if(framesize == AudioSystem.NOT_SPECIFIED)
				framesize = 1;
			
			byte[] buffer = new byte[4 * 1024 * framesize];
			int total = 0;

			boolean playing = false;
			int r, towrite, remaining;
			while( (r = AIS.read(buffer, total, buffer.length - total)) >= 0 ) { //or !=-1
				total += r;
				
				if (!playing) {
					line.start();
					playing = true;
				}
				
				towrite = (total / framesize) * framesize;
				line.write(buffer, 0, towrite);
				
				remaining = total - towrite;
				if (remaining > 0)
					System.arraycopy(buffer, towrite, buffer, 0, remaining);
				total = remaining;
				
				if(stop) {
					line.stop();
					line.flush();
					if (line != null)
						line.close();
					if (AIS != null)
						AIS.close();
					return;
				}
			}

			line.stop();
			line.flush();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			if (line != null)
				line.close();
			if (AIS != null)
				AIS.close();
		}

	}
}
