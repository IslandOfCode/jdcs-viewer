package it.islandofcode.jdcsviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamAuth;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

import it.islandofcode.jdcsviewer.struct.InconsistentSettingException;
import it.islandofcode.jdcsviewer.struct.Settings;
import it.islandofcode.jdcsviewer.utils.AudioPlayer;
import it.islandofcode.jdcsviewer.utils.HttpUtils;
import it.islandofcode.jdcsviewer.utils.Notification;
import it.islandofcode.jdcsviewer.utils.RecordIconTransformer;
import it.islandofcode.jdcsviewer.utils.ScreenShoot;
import it.islandofcode.jdcsviewer.utils.VideoRecording;
import javax.swing.JCheckBoxMenuItem;


public class Viewer {
	
	public static final List<Image> FRAME_ICONS;
	static {
		ArrayList<Image> i = new ArrayList<>();
		i.add( new ImageIcon(Viewer.class.getClass().getResource("/cam16.png")).getImage() );
		i.add( new ImageIcon(Viewer.class.getClass().getResource("/cam24.png")).getImage() );
		i.add( new ImageIcon(Viewer.class.getClass().getResource("/cam32.png")).getImage() );
		i.add( new ImageIcon(Viewer.class.getClass().getResource("/cam64.png")).getImage() );
		//i.add( new ImageIcon(EBill.class.getClass().getResource("/icon128.png")).getImage() );
		FRAME_ICONS = Collections.unmodifiableList(i);
	}
	
	static {
		Webcam.setDriver(new IpCamDriver());
	}
	
	private static enum DIRECTION{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	private JFrame frmJdcsviewer;
	private JPanel P_control;
	private JPanel P_webcam;
	
	private JMenu mnCattura;
	private JMenuItem M_connect;
	private JLabel L_nosignal;
	private JPanel P_other;
	private JPanel P_pan;
	private JPanel P_degree;
	private JPanel P_default;
	private JComboBox<String> CB_degree;
	private JComboBox<String> CB_location;
	private JPanel P_nvControl;
	private JPanel P_audioControl;
	private JButton B_nvOn;
	private JButton B_nvOff;
	private JButton B_nvAuto;
	private JButton B_playAudio;
	
	private JMenuItem M_record;
	
	private boolean isRecording = false;
	private Thread recordThr = null;
	private VideoRecording recordCls = null;
	private Thread aupThr = null;
	
	private Settings settings;
	private IpCamDevice ICD;
	private AudioPlayer AUP = null;
	private JMenuItem DBG_testSettings;
	private JMenu mnDebug;
	private JSeparator separator_1;
	private JMenu mnExtra;
	private JCheckBoxMenuItem CBM_movedet;
	private JCheckBoxMenuItem CBM_facedet;
	private JMenuItem DBG_repaintControlPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewer window = new Viewer();
					window.frmJdcsviewer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Viewer() {
		initialize();
		P_control.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJdcsviewer = new JFrame();
		frmJdcsviewer.setTitle("jDCS-Viewer");
		frmJdcsviewer.setBounds(100, 100, 614, 687);
		frmJdcsviewer.setMinimumSize(new java.awt.Dimension(500,600));
		frmJdcsviewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJdcsviewer.setIconImages(FRAME_ICONS);
		
		JMenuBar menuBar = new JMenuBar();
		frmJdcsviewer.setJMenuBar(menuBar);
		
		JMenu mnConnessione = new JMenu("Connection");
		menuBar.add(mnConnessione);
		
		M_connect = new JMenuItem("Connect");
		M_connect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		M_connect.setIcon(new ImageIcon(Viewer.class.getResource("/icons/connect.png")));
		M_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isConnected())
					disconnect();
				else
					connect();
			}
		});
		mnConnessione.add(M_connect);
		
		JMenuItem M_settings = new JMenuItem("Configuration");
		M_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		M_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsUI SUI = new SettingsUI();
				SUI.setModal(true);
				SUI.setLocationRelativeTo(null);
				SUI.setVisible(true);
			}
		});
		M_settings.setIcon(new ImageIcon(Viewer.class.getResource("/icons/config.png")));
		mnConnessione.add(M_settings);
		
		JSeparator separator = new JSeparator();
		mnConnessione.add(separator);
		
		JMenuItem M_exit = new JMenuItem("Exit");
		M_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		M_exit.setIcon(new ImageIcon(Viewer.class.getResource("/icons/exit.png")));
		M_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO chiedere se si è sicuri!
				if(Webcam.getWebcams().size()>0)
					disconnect();
				System.exit(0);
			}
		});
		mnConnessione.add(M_exit);
		
		mnCattura = new JMenu("Capture");
		mnCattura.setEnabled(false);
		menuBar.add(mnCattura);
		
		M_record = new JMenuItem("Record");
		M_record.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		M_record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isRecording) {
					Webcam.getWebcams().get(0).setImageTransformer(new RecordIconTransformer());
					Notification.showIconNotification("Recording...", "", Notification.ICON_RECORD_START);
					recordCls = new VideoRecording(Webcam.getWebcams().get(0), Webcam.getWebcams().get(0).getFPS());
					recordThr = new Thread(recordCls, "recording-thread");
					//recordThr.setDaemon(true); //removed because will continue to work ad other reason.
					recordThr.start();
				} else {
					Webcam.getWebcams().get(0).setImageTransformer(null);
					recordCls.shutdown();
					//recordThr.interrupt(); //nope: the file will be corrupted.
					//while(!recordThr.isInterrupted()) {	} //make the gui hang, it's a no-no-nope
					Notification.showIconNotification("Stop recording.", recordCls.getPath(), Notification.ICON_RECORD_STOP);
					recordThr = null;
				}
				
				toggleRecordButton();
			}
		});
		M_record.setIcon(new ImageIcon(Viewer.class.getResource("/icons/record.png")));
		mnCattura.add(M_record);
		
		JMenuItem M_screenshot = new JMenuItem("Screenshot");
		M_screenshot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		M_screenshot.setIcon(new ImageIcon(Viewer.class.getResource("/icons/screenshot.png")));
		M_screenshot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isConnected()) {
					String path = ScreenShoot.writeImg2disk(Webcam.getWebcams().get(0).getImage());
					if(path.isEmpty())
						Notification.showIconNotification("Error", "failed save the file!", Notification.ICON_ERROR);
					Notification.showIconNotification("Screenshot", path, Notification.ICON_SCREENSHOT);
				}
			}
		});
		mnCattura.add(M_screenshot);
		
		mnExtra = new JMenu("Extra");
		mnExtra.setEnabled(false);
		menuBar.add(mnExtra);
		
		CBM_movedet = new JCheckBoxMenuItem("Movement Detection");
		CBM_movedet.setIcon(new ImageIcon(Viewer.class.getResource("/icons/run.png")));
		mnExtra.add(CBM_movedet);
		
		CBM_facedet = new JCheckBoxMenuItem("Face Detection");
		CBM_facedet.setIcon(new ImageIcon(Viewer.class.getResource("/icons/face.png")));
		mnExtra.add(CBM_facedet);
		
		JMenu mnHelp = new JMenu("?");
		menuBar.add(mnHelp);
		
		JMenuItem M_update = new JMenuItem("Update");
		M_update.setEnabled(false);
		M_update.setIcon(new ImageIcon(Viewer.class.getResource("/icons/update.png")));
		mnHelp.add(M_update);
		
		JMenuItem M_help = new JMenuItem("About");
		M_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new About()).setVisible(true);
			}
		});
		M_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		M_help.setIcon(new ImageIcon(Viewer.class.getResource("/icons/about.png")));
		mnHelp.add(M_help);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator_1);
		
		mnDebug = new JMenu("Debug");
		mnDebug.setForeground(Color.RED);
		menuBar.add(mnDebug);
		
		DBG_testSettings = new JMenuItem("Test settings");
		mnDebug.add(DBG_testSettings);
		DBG_testSettings.setIcon(new ImageIcon(Viewer.class.getResource("/icons/bug.png")));
		
		DBG_repaintControlPanel = new JMenuItem("Repaint Control Panel");
		DBG_repaintControlPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println("[DBG] Forced repaint event on Control Panel!");
				if(P_control.isVisible())
					P_control.repaint();
			}
		});
		DBG_repaintControlPanel.setIcon(new ImageIcon(Viewer.class.getResource("/icons/bug.png")));
		mnDebug.add(DBG_repaintControlPanel);
		DBG_testSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File config = new File(SettingsUI.PROP_FILE_NAME);
				
				if(!config.exists()) {
					JOptionPane.showMessageDialog(null, "<html>No configuration file found.<br/>"
							+ "Please use the <b>Connection/Configuration</b> menu to create the file.<br/>"
							+ "If the problem persist, restart the application.", "Warning!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				settings = null;
				try {
					settings = new Settings();
				} catch (InconsistentSettingException e1) {
					JOptionPane.showMessageDialog(null, "<html>Problem with configuration file.<br/>"
							+ "Plese check the configuration file and the model file.", "Error!",
							JOptionPane.ERROR_MESSAGE);
					// TODO per debug, ma poi rimuovere
					e1.printStackTrace();
					return;
				}
				
				JOptionPane.showMessageDialog(null, "ALL GOOD!", "Test settings",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		frmJdcsviewer.getContentPane().setLayout(new BorderLayout(0, 0));
		
		P_webcam = new JPanel();
		P_webcam.setBackground(Color.DARK_GRAY);
		frmJdcsviewer.getContentPane().add(P_webcam, BorderLayout.CENTER);
		P_webcam.setLayout(new BorderLayout(0, 0));
		
		L_nosignal = new JLabel("NO SIGNAL");
		L_nosignal.setIcon(new ImageIcon(Viewer.class.getResource("/javax/swing/plaf/basic/icons/image-failed.png")));
		L_nosignal.setForeground(Color.LIGHT_GRAY);
		L_nosignal.setHorizontalAlignment(SwingConstants.CENTER);
		L_nosignal.setFont(new Font("Dialog", Font.PLAIN, 24));
		P_webcam.add(L_nosignal);
		
		P_control = new JPanel();
		P_control.setBorder(new TitledBorder(null, "Control Panel", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		frmJdcsviewer.getContentPane().add(P_control, BorderLayout.SOUTH);
		FlowLayout fl_P_control = new FlowLayout(FlowLayout.CENTER, 15, 5);
		P_control.setLayout(fl_P_control);
				
		P_pan = new JPanel();
		P_pan.setBorder(new TitledBorder(null, "Pan camera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		P_control.add(P_pan);
		P_pan.setLayout(new BorderLayout(0, 0));
		
		JButton B_PanLeft = new JButton("<");
		P_pan.add(B_PanLeft, BorderLayout.WEST);
		
		JButton B_PanUp = new JButton("^");
		P_pan.add(B_PanUp, BorderLayout.NORTH);
		
		JButton B_PanRight = new JButton(">");
		P_pan.add(B_PanRight, BorderLayout.EAST);
		
		JButton B_PanDown = new JButton("v");
		P_pan.add(B_PanDown, BorderLayout.SOUTH);
		B_PanDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panning(DIRECTION.DOWN, (String) CB_degree.getSelectedItem());
			}
		});
		B_PanRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panning(DIRECTION.RIGHT, (String) CB_degree.getSelectedItem());
			}
		});
		B_PanUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panning(DIRECTION.UP, (String) CB_degree.getSelectedItem());
			}
		});
		B_PanLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panning(DIRECTION.LEFT, (String) CB_degree.getSelectedItem());
			}
		});
		
		JPanel P_panSetting = new JPanel();
		P_control.add(P_panSetting);
		P_panSetting.setLayout(new BorderLayout(0, 0));
		
		P_degree = new JPanel();
		P_degree.setBorder(new TitledBorder(null, "Degree pan", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		P_panSetting.add(P_degree, BorderLayout.NORTH);
		P_degree.setLayout(new BorderLayout(0, 0));
		
		CB_degree = new JComboBox<String>();
		CB_degree.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "25", "30"}));
		P_degree.add(CB_degree, BorderLayout.CENTER);
		
		P_default = new JPanel();
		P_default.setBorder(new TitledBorder(null, "Default Location", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		P_panSetting.add(P_default, BorderLayout.SOUTH);
		P_default.setLayout(new BorderLayout(0, 0));
		
		CB_location = new JComboBox<String>();
		CB_location.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					String index = ((String) e.getItem()).replace("Loc", "");
					System.out.println("NEW LOCATION: " + index );
					
					HttpUtils HU = new HttpUtils(settings.getIP(), new IpCamAuth(settings.getUsername(), settings.getPassword()));
					if (ICD.isOnline()) {
						try {
							HU.get(HttpUtils.toURI(HttpUtils.toURL( settings.getURLpreset(index) )));
						} catch (UnsupportedOperationException | IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		CB_location.setModel(new DefaultComboBoxModel<String>(new String[] {"Loc01","Loc02","Loc03","Loc04","Loc05"}));
		P_default.add(CB_location, BorderLayout.SOUTH);
		
		JLabel L4keepLargePanelDONOTDELETEME = new JLabel("                                           ");
		P_panSetting.add(L4keepLargePanelDONOTDELETEME, BorderLayout.CENTER);
				
		P_other = new JPanel();
		P_other.setBorder(new TitledBorder(null, "Other", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		P_control.add(P_other);
		P_other.setLayout(new BorderLayout(5, 5));
		
		
		
		
		P_nvControl = new JPanel();
		P_nvControl.setBorder(new TitledBorder(null, "Night Vision", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		P_other.add(P_nvControl, BorderLayout.NORTH);
		GridBagLayout gbl_P_nvControl = new GridBagLayout();
		gbl_P_nvControl.columnWidths = new int[]{66, 66, 0};
		gbl_P_nvControl.rowHeights = new int[]{26, 26, 0};
		gbl_P_nvControl.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_P_nvControl.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		P_nvControl.setLayout(gbl_P_nvControl);
		
		B_nvOn = new JButton("");
		B_nvOn.setIcon(new ImageIcon(Viewer.class.getResource("/icons/moon.png")));
		B_nvOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleNV(1);
				Notification.showIconNotification("Night vision ON", "", Notification.ICON_INFO);
			}
		});
		GridBagConstraints gbc_B_nvOn = new GridBagConstraints();
		gbc_B_nvOn.fill = GridBagConstraints.BOTH;
		gbc_B_nvOn.insets = new Insets(0, 0, 5, 5);
		gbc_B_nvOn.gridx = 0;
		gbc_B_nvOn.gridy = 0;
		P_nvControl.add(B_nvOn, gbc_B_nvOn);
		
		B_nvOff = new JButton("");
		B_nvOff.setIcon(new ImageIcon(Viewer.class.getResource("/icons/sun.png")));
		B_nvOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleNV(0);
				Notification.showIconNotification("Night vision OFF", "", Notification.ICON_INFO);
			}
		});
		GridBagConstraints gbc_B_nvOff = new GridBagConstraints();
		gbc_B_nvOff.fill = GridBagConstraints.BOTH;
		gbc_B_nvOff.insets = new Insets(0, 0, 5, 0);
		gbc_B_nvOff.gridx = 1;
		gbc_B_nvOff.gridy = 0;
		P_nvControl.add(B_nvOff, gbc_B_nvOff);
		
		B_nvAuto = new JButton("AUTO");
		B_nvAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleNV(2);
				Notification.showIconNotification("Night vision AUTO", "", Notification.ICON_INFO);
			}
		});
		GridBagConstraints gbc_B_nvAuto = new GridBagConstraints();
		gbc_B_nvAuto.gridwidth = 2;
		gbc_B_nvAuto.fill = GridBagConstraints.VERTICAL;
		gbc_B_nvAuto.insets = new Insets(0, 0, 0, 5);
		gbc_B_nvAuto.gridx = 0;
		gbc_B_nvAuto.gridy = 1;
		P_nvControl.add(B_nvAuto, gbc_B_nvAuto);
		
		P_audioControl = new JPanel();
		P_audioControl.setBorder(new TitledBorder(null, "Audio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		P_other.add(P_audioControl, BorderLayout.SOUTH);
		P_audioControl.setLayout(new BorderLayout(5, 0));
		
		JLabel lblPlayAudio = new JLabel("Play Audio");
		P_audioControl.add(lblPlayAudio, BorderLayout.WEST);
		
		B_playAudio = new JButton("");
		B_playAudio.setIcon(new ImageIcon(Viewer.class.getResource("/icons/audioOn.png")));
		B_playAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(B_playAudio.getText().equals("")) {

					try {
						AUP = new AudioPlayer(
								settings.getAudioURL(),
								settings.getUsername(),
								settings.getPassword()
								);
						
						AUP.addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getPropertyName().equals("PLAYING") && evt.getNewValue().equals(true)) {
									Notification.showIconNotification("SOUND NOW PLAYING", "", Notification.ICON_INFO);
								}
							}
						});
						
						aupThr = new Thread(AUP, "audio-stream-thread");
						//aupThr.setDaemon(true);
						aupThr.start();
						
						//AUP.run();
						
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}

					B_playAudio.setText("Stop");
					B_playAudio.setIcon(new ImageIcon(Viewer.class.getResource("/icons/audioOff.png")));
				} else {
					
					if(AUP!=null) {
						AUP.shutdown();
						aupThr.interrupt();
					}
					
					AUP = null;
					aupThr = null;
					
					B_playAudio.setText("");
					B_playAudio.setIcon(new ImageIcon(Viewer.class.getResource("/icons/audioOn.png")));
				}
			}
		});
		P_audioControl.add(B_playAudio, BorderLayout.EAST);
	}
	
	
	private void connect() {
				
		File config = new File(SettingsUI.PROP_FILE_NAME);
		
		if(!config.exists()) {
			JOptionPane.showMessageDialog(null, "<html>No configuration file found.<br/>"
					+ "Please use the <b>Connection/Configuration</b> menu to create the file.<br/>"
					+ "If the problem persist, restart the application.", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		settings = null;
		try {
			settings = new Settings();
		} catch (InconsistentSettingException e1) {
			JOptionPane.showMessageDialog(null, "<html>Problem with configuration file.<br/>"
					+ "Plese check the configuration file and the model file.", "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO per debug, ma poi rimuovere
			e1.printStackTrace();
			return;
		}
		
		setControlPanel(settings);
	
		IpCamAuth auth = null;
		if(settings.hasAuthentication()) {
			auth = new IpCamAuth(settings.getUsername(), settings.getPassword());
		}
		//Se auth==null, allora non necessita di autenticazione
		
		try {
			ICD = new IpCamDevice(settings.getFileModelFullName(), settings.getVideoURL(), IpCamMode.PUSH, auth);	
			//IpCamMyDevice ICD = new IpCamMyDevice(settings.getFileModelFullName(), settings.getVideoURL(), IpCamMode.PUSH, auth);
			
			if(!ICD.isOnline()) {
				JOptionPane.showMessageDialog(null, "<html>Can't connect to<br/>"
						+ "<code>"+settings.getIP()+"</code><br/>"
								+ "Please check the configuration or the device and try again.", "Connection error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//IpCamMyDeviceRegistry.register(ICD);
			IpCamDeviceRegistry.register(ICD);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Webcam W = Webcam.getWebcams().get(0);
		W.open(true);
		WebcamPanel WP = new WebcamPanel(W);
		
		WP.setFPSDisplayed(true);
		
		//GUI Part
		P_webcam.removeAll();
		P_webcam.add(WP,BorderLayout.CENTER);
		P_webcam.revalidate(); //FONDAMENTALE altrimenti bisogna ridimensionare la finestra per mostrare il pannello
		toggleConnectButton(false);
	}
	
	private void disconnect() {
		Webcam.getWebcams().get(0).close();
		//IpCamMyDeviceRegistry.unregisterAll();
		IpCamDeviceRegistry.unregisterAll();
		settings = null;
		ICD.dispose();
		ICD = null;
		
		
		//GUI Part
		P_webcam.removeAll();
		P_webcam.add(L_nosignal, BorderLayout.CENTER);
		P_webcam.repaint();
		P_webcam.revalidate();
		restoreControlPanel();
		toggleConnectButton(true);
	}
	
	/**
	 * False se si vuole far apparire Disconnetti, True per Connetti
	 * @param toggle Boolean
	 */
	private void toggleConnectButton(boolean toggle) {
		if(toggle) {
			M_connect.setText("Connetti");
			mnCattura.setEnabled(false);
		} else {
			M_connect.setText("Disconnetti");
			mnCattura.setEnabled(true);
		}
	}
	
	private boolean isConnected() {
		return ( (Webcam.getWebcams().size()>0) && (Webcam.getWebcams().get(0).isOpen()) );
	}
	
	/**
	 * False se si vuole far apparire Stop, true per Record
	 * @param toggle
	 */
	private void toggleRecordButton() {
		if(isRecording) {
			M_record.setText("Record");
			M_record.setIcon(new ImageIcon(Viewer.class.getResource("/icons/record.png")));
		} else {
			M_record.setText("Stop recording");
			M_record.setIcon(new ImageIcon(Viewer.class.getResource("/icons/stopRec.png")));
		}
		isRecording = !isRecording;
	}
	
	private void setControlPanel(Settings S) {
		/*
		 * Rimuovi parti di UI in base alle impostazioni 
		 */
		if(!S.isPannable() && !S.hasNV() && !S.hasAudio())
			P_control.setVisible(false);
		else {
			P_control.setVisible(true);
			if (!S.isPannable()) {
				P_pan.setVisible(false);
				P_degree.setVisible(false);
				P_default.setVisible(false);
			}
			if (!S.hasNV())
				P_nvControl.setVisible(false);
			if (!S.hasAudio())
				P_audioControl.setVisible(false);
		}
	}
	
	private void restoreControlPanel() {
		P_control.setVisible(false);
		P_pan.setVisible(true);
		P_degree.setVisible(true);
		P_default.setVisible(true);
		P_nvControl.setVisible(true);
		P_audioControl.setVisible(true);
	}
	
	
	
	private void panning(DIRECTION where, String degree) {
		HttpUtils HU = new HttpUtils(settings.getIP(), new IpCamAuth(settings.getUsername(), settings.getPassword()));
		if (ICD.isOnline()) {
			try {
				switch (where) {
				case DOWN:
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getURLpanDown(degree))));
					break;
				case LEFT:
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getURLpanLeft(degree))));
					break;
				case RIGHT:
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getURLpanRight(degree))));
					break;
				case UP:
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getURLpanUp(degree))));
					break;
				default:
					System.err.println("THE HELL YOU TRY TO DO, PUNK?");
					break;
				}
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void toggleNV(int toggle) {
		HttpUtils HU = new HttpUtils(settings.getIP(), new IpCamAuth(settings.getUsername(), settings.getPassword()));
		if(ICD.isOnline()) {
			try {
				switch(toggle) {
				case(0):
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getNVoff())));
					break;
				case(1):
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getNVon())));
					break;
				case(2):
					HU.get(HttpUtils.toURI(HttpUtils.toURL(settings.getNVauto())));
					break;
				}
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
