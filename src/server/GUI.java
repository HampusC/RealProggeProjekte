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
		setSize(1000,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
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
		btnIdle.setBounds(12, 552, 235, 49);
		getContentPane().add(btnIdle);
		
		JButton btnMovie = new JButton("Movie");
		btnMovie.setBounds(259, 552, 235, 49);
		getContentPane().add(btnMovie);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(500, 500));
		panel_1.setBounds(504, 12, 482, 429);
		getContentPane().add(panel_1);
		
		JLabel label_1 = new JLabel((Icon) null);
		panel_1.add(label_1);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(12, 453, 235, 49);
		getContentPane().add(btnConnect);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(259, 453, 235, 49);
		getContentPane().add(btnDisconnect);
		
		JButton button = new JButton("Connect");
		button.setBounds(504, 453, 235, 49);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("Disconnect");
		button_1.setBounds(751, 453, 235, 49);
		getContentPane().add(button_1);
		
		JButton btnSynchroniz = new JButton(" Synchronous");
		btnSynchroniz.setBounds(504, 552, 235, 49);
		getContentPane().add(btnSynchroniz);
		
		JButton btnAsynchronous = new JButton(" Asynchronous");
		btnAsynchronous.setBounds(751, 552, 235, 49);
		getContentPane().add(btnAsynchronous);
		setVisible(true);
	}
	
	
	public void refresh(byte[] data) {
		Image theImage = getToolkit().createImage(data);
		getToolkit().prepareImage(theImage,-1,-1,null);	    
		icon.setImage(theImage);
		icon.paintIcon(this, this.getGraphics(), 5, 5);
		validate();
		repaint();
		System.out.println("img");
	}
}
