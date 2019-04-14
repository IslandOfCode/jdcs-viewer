package it.islandofcode.jdcsviewer.struct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import it.islandofcode.jdcsviewer.SettingsUI;
import it.islandofcode.jdcsviewer.SettingsUI.PROP_KEY;
import it.islandofcode.jdcsviewer.xml.Ipcamdata;

public class Settings {
	
	private static final String PLACEHOLDER_IP = "%IP%";
	private static final String PLACEHOLDER_DEGREE = "%DEGREE%";
	private static final String PLACEHOLDER_PRESET = "%PRESET%";

	private String IP;
	private String username;
	private String password;
	private String model;
	private Ipcamdata ipcam;
	
	/**
	 * Legge direttamente il file di configurazione e genera tutte le impostazioni necessarie
	 * @throws InconsistentSettingException 
	 */
	public Settings() throws InconsistentSettingException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(SettingsUI.PROP_FILE_NAME);

			Properties props = new Properties();
			props.load(in);
			
			this.IP = (String) props.getOrDefault(PROP_KEY.ip.name(), "192.168.1.1");
			this.username = (String) props.getOrDefault(PROP_KEY.username.name(), "");
			this.password = (String) props.getOrDefault(PROP_KEY.password.name(), "");
			this.model = ((String) props.getOrDefault(PROP_KEY.model.name(), ""))+".xml";
			in.close();
		} catch (IOException fnf){
			System.err.println("Config file not found");
			throw new InconsistentSettingException(fnf.getClass().getName(), fnf.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new InconsistentSettingException(e.getClass().getName(), e.getMessage());
				}
			}
		}
		
		try {
			boolean internal;

			if(Settings.class.getResourceAsStream("/default_model/"+model)!=null) {
				internal = true;
			} else if((new File("model/"+model)).exists()) {
				internal = false;
			} else {
				throw new InconsistentSettingException("model loading", model+" missing!");
			}
			
			this.ipcam = modelFileRead(model, internal);
			
		} catch (JAXBException jaxe) {
			throw new InconsistentSettingException(jaxe.getClass().getName(), jaxe.getMessage());
		}
		
	}
	
	public String getIP() {
		return IP;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getModelFileName() {
		return model;
	}
	
	public String getModel() {
		return model.replace(".xml", "");
	}

	public Ipcamdata getIpcamData() {
		return ipcam;
	}
	
	public Boolean hasAuthentication() {
		return (!this.username.isEmpty() || !this.password.isEmpty());
	}

	private Ipcamdata modelFileRead(String model, boolean internal) throws JAXBException {
		Source source;
		
		if(internal) {
			ClassLoader CL = getClass().getClassLoader();
			InputStream is = CL.getResourceAsStream("default_model/"+model);
			source = new StreamSource(is);
		} else {
			source = new StreamSource(new File("model/"+model));
		}
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Ipcamdata.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		JAXBElement<Ipcamdata> radix = unmarshaller.unmarshal(source, Ipcamdata.class);
		return radix.getValue();
	}
	
	public boolean hasAudio() {
		return (this.ipcam.getAudio()!=null && this.ipcam.getAudio().getURLstream()!=null && !this.ipcam.getAudio().getURLstream().isEmpty());
	}
	
	public boolean isPannable() {
		return (this.ipcam.getPanning()!=null && this.ipcam.getPanning().isPannable());
	}
	
	public boolean hasNV() {
		return (this.ipcam.getCommand()!=null && this.ipcam.getCommand().isNVtogglable());
	}
	
	public boolean hasMotionDetection() {
		return (this.ipcam.getCommand()!=null && this.ipcam.getCommand().isMotionTogglable());
	}
	
	public String getFileModelVersion() {
		return this.ipcam.getVersion();
	}
	
	public String getFileModelAuthor() {
		return this.ipcam.getAuthor();
	}
	
	public String getFileModelFullName() {
		return this.ipcam.getModel();
	}
	
	public String getVideoURL() {
		return this.ipcam.getVideo().getURLstream().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getImageURL() {
		return this.ipcam.getVideo().getURLimage().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getAudioURL() {
		return this.ipcam.getAudio().getURLstream().replaceAll(PLACEHOLDER_IP, IP);
	}
	
	public String getURLpanUp(String degree) {
		return this.ipcam.getPanning().getURLpanUp()
				.replaceAll(PLACEHOLDER_IP, this.IP)
				.replaceAll(PLACEHOLDER_DEGREE, degree);
	}
	
	public String getURLpanDown(String degree) {
		return this.ipcam.getPanning().getURLpanDown()
				.replaceAll(PLACEHOLDER_IP, this.IP)
				.replaceAll(PLACEHOLDER_DEGREE, degree);
	}
	
	public String getURLpanLeft(String degree) {
		return this.ipcam.getPanning().getURLpanLeft()
				.replaceAll(PLACEHOLDER_IP, this.IP)
				.replaceAll(PLACEHOLDER_DEGREE, degree);
	}
	
	public String getURLpanRight(String degree) {
		return this.ipcam.getPanning().getURLpanRight()
				.replaceAll(PLACEHOLDER_IP, this.IP)
				.replaceAll(PLACEHOLDER_DEGREE, degree);
	}
	
	public String getURLpreset(String preset) {
		return this.ipcam.getPanning().getURLpreset()
				.replaceAll(PLACEHOLDER_IP, this.IP)
				.replaceAll(PLACEHOLDER_PRESET, preset);
	}
	
	public String getNVon() {
		return this.ipcam.getCommand().getURLNVon().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getNVoff() {
		return this.ipcam.getCommand().getURLNVoff().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getNVauto() {
		return this.ipcam.getCommand().getURLNVauto().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getURLmotionOn() {
		return this.ipcam.getCommand().getURLmotionOn().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
	public String getURLmotionOff() {
		return this.ipcam.getCommand().getURLmotionOff().replaceAll(PLACEHOLDER_IP, this.IP);
	}
	
}
