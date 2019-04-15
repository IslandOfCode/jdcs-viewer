package it.islandofcode.jdcsviewer.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import com.github.sarxos.webcam.Webcam;

public class VideoRecording implements Runnable {

	private Webcam webcam;
	private int fps;
	
	private String path;
	
	private volatile boolean stop = false;
	
	public VideoRecording(Webcam w, double f) {
		this.webcam = w;
		this.fps = (int) Math.round(f);
	}
	
	public String getPath() {
		return this.path;
	}

	public void shutdown() {
		stop = true;
	}
	
	@Override
	public void run() {
		SeekableByteChannel out = null;
		AWTSequenceEncoder encoder = null;
		path = "video_" + ScreenShoot.getDateFormatted() + ".mp4";
		try {
			out = NIOUtils.writableFileChannel(path);
			// for Android use: AndroidSequenceEncoder
			encoder = new AWTSequenceEncoder(out, Rational.R(fps, 1));
			BufferedImage frame;
			while(!stop) {
				// Generate the image, for Android use Bitmap
				frame = webcam.getImage();
				// Encode the image
				encoder.encodeImage(frame);
				//System.err.println("FRAME ENCODED");
				//Thread.sleep((long) (1000 / fps));
				TimeUnit.SECONDS.toMillis(1000/fps);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Finalize the encoding, i.e. clear the buffers, write the header, etc.
			if(encoder!=null) {
				try {
					encoder.finish();
					System.err.println("\tFINALIZED");
					NIOUtils.closeQuietly(out);
					System.err.println("\tCLOSED");
				} catch (IOException e) {
					e.printStackTrace();
					//FUCK!
				}
			}
		}
	}
	
}