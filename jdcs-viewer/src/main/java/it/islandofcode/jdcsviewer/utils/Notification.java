package it.islandofcode.jdcsviewer.utils;

import java.io.InputStream;

import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.NotificationManager;
import com.notification.manager.SimpleManager;
import com.notification.types.IconNotification;
import com.notification.types.TextNotification;
import com.theme.ThemePackagePresets;
import com.utils.IconUtils;
import com.utils.Time;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Notification {

	public static enum SOUND {
		PING, NOSOUND
	}
	
	public static final String ICON_INFO = "/notification/info48.png";
	public static final String ICON_ERROR = "/notification/error48.png";
	public static final String ICON_RECORD_START = "/notification/record48.png";
	public static final String ICON_RECORD_STOP = "/notification/stopRec48.png";
	public static final String ICON_SCREENSHOT= "/notification/screenshot48.png";
	public static final String ICON_MOVEMENT_DETECTED = "/notification/movement48.png";

	// makes a factory with the built-in clean theme
	public static final NotificationFactory NFctr = new NotificationFactory(ThemePackagePresets.cleanLight());
	// a normal manager that just pops up the notification
	public static final NotificationManager NMngr = new SimpleManager(Location.NORTHEAST);

	/**
	 * Esegue un suono tra quelli registrati Uso:<br/>
	 * <code>Notification.playSound(Notification.SOUND.PING);</code>
	 * 
	 * @param sound
	 */
	public static void playSound(SOUND sound) {
		if (sound == SOUND.NOSOUND)
			return;

		try {
			// get the sound file as a resource out of my jar file;
			// the sound file must be in the same directory as this class file.
			// the input stream portion of this recipe comes from a javaworld.com article.
			InputStream inputStream = Notification.class.getResourceAsStream("/" + sound.toString() + ".wav");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showTextNotification(String title, String mex) {
		TextNotification notification = NFctr.buildTextNotification(title, mex);
		notification.setCloseOnClick(true);
		// the notification will disappear after 2 seconds, or after you click it
		NMngr.addNotification(notification, Time.seconds(4));
	}

	public static void showIconNotification(String title, String mex, String icon) {
		IconNotification note = NFctr.buildIconNotification(title, mex,
				IconUtils.createIcon(icon, 48, 48));
		note.setCloseOnClick(true);
		// make it show in the queue for two seconds
		NMngr.addNotification(note, Time.seconds(4));
	}

}
