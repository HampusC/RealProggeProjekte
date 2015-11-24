package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

public class GUI extends JFrame {
	private ImageIcon icon;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		super();
		setResizable(false);
		setSize(1316,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 640, 480);
		icon = new ImageIcon();
		getContentPane().setLayout(null);
		JLabel label = new JLabel(icon);
		panel.add(label);
		panel.setPreferredSize(new Dimension(500, 500));
		getContentPane().add(panel);
		
		JButton btnIdle = new JButton("Idle");
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIdle.setBounds(12, 565, 314, 49);
		getContentPane().add(btnIdle);
		
		JButton btnMovie = new JButton("Movie");
		btnMovie.setBounds(338, 565, 314, 49);
		getContentPane().add(btnMovie);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(12, 504, 314, 49);
		getContentPane().add(btnConnect);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(338, 504, 314, 49);
		getContentPane().add(btnDisconnect);
		
		JButton button = new JButton("Connect");
		button.setBounds(663, 504, 314, 49);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("Disconnect");
		button_1.setBounds(989, 504, 314, 49);
		getContentPane().add(button_1);
		
		JButton btnSynchroniz = new JButton(" Synchronous");
		btnSynchroniz.setBounds(664, 565, 314, 49);
		getContentPane().add(btnSynchroniz);
		
		JButton btnAsynchronous = new JButton(" Asynchronous");
		btnAsynchronous.setBounds(989, 565, 314, 49);
		getContentPane().add(btnAsynchronous);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(500, 500));
		panel_1.setBounds(663, 12, 640, 480);
		getContentPane().add(panel_1);
		
		JLabel label_1 = new JLabel((Icon) null);
		panel_1.add(label_1);
		setVisible(true);
	}
	
	
	public void refresh(byte[] data) {
		Image theImage = getToolkit().createImage(data);
		getToolkit().prepareImage(theImage,-1,-1,null);	    
		icon.setImage(theImage);
		icon.paintIcon(this, this.getGraphics(), 5, 5);
		System.out.println("img");
	}
}
