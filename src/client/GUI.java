package client;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Canvas;

public class GUI extends JFrame {
	private ImageIcon icon;
	private Client client;

	ArrayList<Canvas> canvas = new ArrayList<Canvas>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//GUI frame = new GUI();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(Client client) {
		super();
		this.client = client;
		
		setResizable(false);
		setSize(1316,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icon = new ImageIcon();
		getContentPane().setLayout(null);
		canvas.add(new Canvas());
		canvas.get(0).setBounds(12, 12, 640, 480);
		getContentPane().add(canvas.get(0));
		
		
		canvas.add(new Canvas());
		canvas.get(1).setBounds(663, 12, 640, 480);
		getContentPane().add(canvas.get(1));
		
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
		
		JButton btnSynchroniz = new JButton("Synchronous");
		btnSynchroniz.setBounds(664, 565, 314, 49);
		getContentPane().add(btnSynchroniz);
		
		JButton btnAsynchronous = new JButton("Asynchronous");
		btnAsynchronous.setBounds(989, 565, 314, 49);
		getContentPane().add(btnAsynchronous);
		
		
		setVisible(true);
	}
	
	public void refresh(byte[] data, int index) {
		
		Image theImage = getToolkit().createImage(data);
		getToolkit().prepareImage(theImage,-1,-1,null);	    
		icon.setImage(theImage);
		icon.paintIcon(this, canvas.get(index).getGraphics(), 0, 0);
		System.out.println("img");
	}
}
