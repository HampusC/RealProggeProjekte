package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class GUI extends JFrame {
	
	public GUI(){
		super();
		setSize(750,750);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon();
		JLabel label = new JLabel(icon);
		panel.add(label, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(500, 500));

		setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		JLabel text = new JLabel("Hampeee");
		
		text.setForeground(Color.red);
		add(text, BorderLayout.SOUTH);
		add(new JButton("Hampeee"), BorderLayout.CENTER);
		setVisible(true);
	}

}
