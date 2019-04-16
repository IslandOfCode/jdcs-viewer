package it.islandofcode.jdcsviewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class SettingsUI extends JDialog {

	private static final long serialVersionUID = -2539414293558792749L;
	public static final String PROP_FILE_NAME = "config.ini";
	public static enum PROP_KEY {
		model,
		username,
		password,
		ip,
	}
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField T_username;
	private JTextField T_password;
	private JTextField T_ip;
	private JComboBox<String> CB_model;
	
	private JLabel L_status;
	
	
	/**
	 * Create the dialog.
	 */
	public SettingsUI() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Settings");
		setResizable(false);
		setBounds(100, 100, 325, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblModelloCamera = new JLabel("Camera model:");
		lblModelloCamera.setBounds(10, 11, 101, 14);
		contentPanel.add(lblModelloCamera);
		
		CB_model = new JComboBox<String>();
		CB_model.setBounds(121, 8, 180, 20);
		contentPanel.add(CB_model);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 36, 101, 14);
		contentPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 61, 101, 14);
		contentPanel.add(lblPassword);
		
		JLabel lblIndirizzoIp = new JLabel("IP address:");
		lblIndirizzoIp.setBounds(10, 86, 101, 14);
		contentPanel.add(lblIndirizzoIp);
		
		T_username = new JTextField();
		T_username.setToolTipText("Lasciare vuoto se non necessario");
		T_username.setBounds(121, 33, 180, 20);
		contentPanel.add(T_username);
		T_username.setColumns(10);
		
		T_password = new JTextField();
		T_password.setBounds(121, 58, 180, 20);
		contentPanel.add(T_password);
		T_password.setColumns(10);
		
		T_ip = new JTextField();
		T_ip.setToolTipText("Se URL, per favore NON inserire \"http://\" o \"https://\" e aggiungere la porta (laddove necessario)");
		T_ip.setBounds(121, 83, 180, 20);
		contentPanel.add(T_ip);
		T_ip.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton B_save = new JButton("");
				B_save.setToolTipText("Save configuration");
				B_save.setIcon(new ImageIcon(SettingsUI.class.getResource("/icons/save.png")));
				B_save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveSettings();
					}
				});
				
				L_status = new JLabel("");
				buttonPane.add(L_status);
				buttonPane.add(B_save);
				getRootPane().setDefaultButton(B_save);
			}
			{
				JButton B_close = new JButton("");
				B_close.setToolTipText("Return without save");
				B_close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						//setVisible(false);
					}
				});
				B_close.setIcon(new ImageIcon(SettingsUI.class.getResource("/icons/undo.png")));
				buttonPane.add(B_close);
			}
		}
		loadModelList();
		readSettings();
	}
	
	/**
	 * Legge i modelli e li carica nella combobox.
	 */
	private void loadModelList() {
		//System.out.println("INTERNAL MODEL FOLDER:");
		ArrayList<String> L = new ArrayList<>();
		//int i = 1;
		L.add(0,"");
		/*for(File F : getResourceFolderFiles("default_model")) {
			//System.out.println("\t"+F.getName());
			L.add(i, F.getName().replace(".xml", ""));
			i++;
		}*/
		//leggo i nomi dei file interni e li aggiungo alla lista
		L.addAll(getResourceFolderFiles());
		//aggiungo alla lista i file esterni, se esistono.
		L.addAll(getExternalModelList());
		
		CB_model.setModel(new DefaultComboBoxModel<String>(L.toArray(new String[0])) );
		
		L_status.setText("<html>Model list loaded");
	}
	
	private ArrayList<String> getExternalModelList(){
		ArrayList<String> list = new ArrayList<>();
		
		File DIR = new File("model");
		File[] LS = DIR.listFiles();
		
		//listFiles ritorna null se DIR non è una directory
		if(LS==null || LS.length<=0)
			return list;
		
		//System.out.println("EXTERNAL MODEL FOLDER:");
		
		for(int f=0; f<LS.length; f++) {
			if( LS[f].isFile() && LS[f].getName().matches("(?i).*\\.xml$") && validateAgainstXSD(LS[f].getPath()) ) {
				/*
				 * Se è un file
				 * Se termina per .xml/.XML
				 * Se è un xml aderente allo schema XSD
				 */
				//System.out.println("\t"+LS[f].getName());
				list.add(LS[f].getName().replace(".xml", ""));
			}
		}

		return list;
	}
	
	static boolean validateAgainstXSD(String xml)
	{
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(SettingsUI.class.getResourceAsStream("/ipcam_model.xsd")));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(new File(xml)));
	        return true;
	    }
	    catch(Exception ex)
	    {
	        return false;
	    }
	}
	
	private void saveSettings() {
		
		if( ((String)CB_model.getSelectedItem()).isEmpty() || this.T_ip.getText().isEmpty() ) {
			// user e password possono essere vuoti
			JOptionPane.showMessageDialog(null, "Model or IP address missing.", "Missing field!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Properties P = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(PROP_FILE_NAME);

			for(PROP_KEY K : PROP_KEY.values()) {
				switch(K){
				case ip:
					P.setProperty("ip", T_ip.getText());
					break;
				case model:
					P.setProperty("model", (String) CB_model.getSelectedItem());
					break;
				case password:
					P.setProperty("password", T_password.getText().trim());
					break;
				case username:
					P.setProperty("username", T_username.getText().trim());
					break;
				default:
					break;
				}
				
			}

			P.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
			L_status.setText("<html><p style=\"color:red\">Error!</p>");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					L_status.setText("<html><p style=\"color:red\">Error!</p>");
				}
			}

		}
		L_status.setText("<html><p style=\"color:green\">Saved!</p>");
	}
	
	/**
	 * 
	 */
	private void readSettings() {
		FileInputStream in = null;
		try {
			in = new FileInputStream(PROP_FILE_NAME);

			Properties props = new Properties();
			props.load(in);
			
			for(Object K : props.keySet()) {
				switch(PROP_KEY.valueOf((String) K)){
				case ip:
					this.T_ip.setText((String) props.getOrDefault(K, ""));
					break;
				case model:
					this.CB_model.setSelectedItem(props.getOrDefault(K, ""));
					break;
				case password:
					this.T_password.setText((String) props.getOrDefault(K, ""));
					break;
				case username:
					this.T_username.setText((String) props.getOrDefault(K, ""));
					break;
				default:
					break;
				}
				
			}
			in.close();
			L_status.setText("<html><p style=\"color:orange\">Config file loaded!</p>");
		} catch (IOException fnf){
			System.err.println("Config file not found");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					L_status.setText("<html><p style=\"color:red\">Error!</p>");
				}
			}
		}
	}
	
	/* 
	//Non posso usare questo perchè non possono enumerare una cartella dentro un file JAR
	private static File[] getResourceFolderFiles(String folder) {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource(folder);
	    String path = url.getPath();
	    return new File(path).listFiles();
	}*/
	
	private static ArrayList<String> getResourceFolderFiles(){
		ArrayList<String> list = new ArrayList<>();
		
		InputStream is = SettingsUI.class.getResourceAsStream("/default_model/LIST");
		
		String FN = "";
		char C;
		int R;
		try {
			while( (R = is.read()) != -1 ) {
				C = (char) R;
				if(C == '\n' || C == '\r') {
					if(!FN.isEmpty()) { //ho una riga piena
						list.add(FN);
					}
					//ho un ritorno a capo singolo
					FN = "";
					continue;
				}
				FN += C;
			}
			if(!FN.isEmpty())
				list.add(FN);
		} catch (IOException e) {
			return list;
		}
		
		return list;
	}
}
