package it.islandofcode.jdcsviewer.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ScreenShoot {

	/**
	 * Salva l'immagine nel path del programma.
	 * @param bi
	 * @return String
	 */
	public static String writeImg2disk(BufferedImage bi) {
		return writeImg2disk(bi,"");
	}
	
	/**
	 * Salva l'immagine in un path specifico.
	 * @param bi
	 * @param path
	 */
	public static String writeImg2disk(BufferedImage bi, String path) {
		try {
			File f = new File(path+"img_"+getDateFormatted()+".jpg");
			ImageIO.write(bi, "jpg", f);
			return f.getPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getDateFormatted() {
		return (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
	}
}
