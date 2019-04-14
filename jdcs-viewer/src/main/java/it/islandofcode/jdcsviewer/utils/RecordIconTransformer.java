package it.islandofcode.jdcsviewer.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.WebcamImageTransformer;

/**
 * Applica, all'immagine dalla webcam, il logo di registrazione.
 * Si noti che il png viene applicato prima della visualizzazione,
 * quindi ridimensionando il frame, l'intera immagine cambia dimensione.
 * @author Riccardo
 *
 */
public class RecordIconTransformer implements WebcamImageTransformer {
	
	private static final BufferedImage IMAGE_FRAME = getImage("recording.png");

	private static final BufferedImage getImage(String image) {
		try {
			return ImageIO.read(RecordIconTransformer.class.getResourceAsStream("/" + image));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BufferedImage transform(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		BufferedImage modified = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = modified.createGraphics();
		g2.drawImage(image, null, 0, 0);
		g2.drawImage(IMAGE_FRAME, null, 5, 5);
		g2.dispose();

		modified.flush();

		return modified;
	}

}
