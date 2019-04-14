package it.islandofcode.jdcsviewer;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class About extends JDialog {

	private static final long serialVersionUID = 2595506337641007806L;

	/**
	 * Create the dialog.
	 */
	public About() {
		setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setType(Type.UTILITY);
		setBounds(100, 100, 338, 271);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JLabel Description = new JLabel("<html>\r\n<div style=\"padding:20px; font-size:14pt; text-align:center\">\r\n\t<p>A simple ipcam viewer, focused on the DCS-xxxx series of D-Link brand.</p>\r\n\t<br/>\r\n\t<i>Author:</i> <strong>Pier Riccardo Monzo</strong>\r\n</div>");
		Description.setBounds(10, 115, 318, 111);
		getContentPane().add(Description);
		
		JLabel Name = new JLabel("<html>\r\n<div style=\"border-left: 2px black solid; padding-left:10px\">\r\n<h1>jDCS-Viewer</h1>\r\n</div>");
		Name.setBounds(114, 11, 214, 93);
		getContentPane().add(Name);
		
		JLabel Logo = new JLabel("");
		Logo.setHorizontalAlignment(SwingConstants.CENTER);
		Logo.setIcon(new ImageIcon(About.class.getResource("/logo/logo64.png")));
		Logo.setBounds(10, 11, 94, 93);
		getContentPane().add(Logo);
		
		JButton B_hide = new JButton("Close");
		B_hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		B_hide.setBounds(239, 237, 89, 23);
		getContentPane().add(B_hide);
		
		JButton btnNewButton = new JButton("Go to the website");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("http://www.islandofcode.it").toURI());
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 237, 144, 23);
		getContentPane().add(btnNewButton);

	}
}
