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
		setSize(750,750);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 748, 500);
		panel.setLayout(new BorderLayout());
		icon = new ImageIcon();
		getContentPane().setLayout(null);
		JLabel label = new JLabel(icon);
		panel.add(label, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(500, 500));
		getContentPane().add(panel);
		JLabel text = new JLabel("Hampeee");
		text.setBounds(0, 710, 748, 15);
		
		text.setForeground(Color.red);
		getContentPane().add(text);
		JButton button = new JButton("Hampeee");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(10, 512, 177, 49);
		getContentPane().add(button);
		
		JButton btnIdle = new JButton("Idle");
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIdle.setBounds(199, 512, 177, 49);
		getContentPane().add(btnIdle);
		
		JButton btnMovie = new JButton("Movie");
		btnMovie.setBounds(388, 512, 177, 49);
		getContentPane().add(btnMovie);
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
