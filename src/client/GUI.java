package client;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
		SwingUtilities.invokeLater(new Runnable() {
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
		btnIdle.setBounds(12, 565, 314, 49);
		getContentPane().add(btnIdle);
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.IDLE_MODE, 0);
				        client.setMode(Client.IDLE_MODE, 1);
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnMovie = new JButton("Movie");
		btnMovie.setBounds(338, 565, 314, 49);
		getContentPane().add(btnMovie);
		btnMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.MOVIE_MODE, 0);
				        client.setMode(Client.MOVIE_MODE, 1);
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnConnect1 = new JButton("Connect");
		btnConnect1.setBounds(12, 504, 314, 49);
		getContentPane().add(btnConnect1);
		btnConnect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.connectCamera("localhost", 6077); //Kanske att man ska ange adress/port när man klickar på connect.
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnDisconnect1 = new JButton("Disconnect");
		btnDisconnect1.setBounds(338, 504, 314, 49);
		getContentPane().add(btnDisconnect1);
		btnDisconnect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.disconnect(0);
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnConnect2 = new JButton("Connect");
		btnConnect2.setBounds(663, 504, 314, 49);
		getContentPane().add(btnConnect2);
		btnConnect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.connectCamera("localhost", 6078); //Kanske att man ska ange adress/port när man klickar på connect. 
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnDisconnect2 = new JButton("Disconnect");
		btnDisconnect2.setBounds(989, 504, 314, 49);
		getContentPane().add(btnDisconnect2);
		btnDisconnect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.disconnect(1); 
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnSynchronous = new JButton("Synchronous");
		btnSynchronous.setBounds(664, 565, 314, 49);
		getContentPane().add(btnSynchronous);
		btnSynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setType(Client.SYNCHRONOUS_MODE); 
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnAsynchronous = new JButton("Asynchronous");
		btnAsynchronous.setBounds(989, 565, 314, 49);
		getContentPane().add(btnAsynchronous);
		btnAsynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setType(Client.ASYNCHRONOUS_MODE); 
				        System.out.println("Button clicked");
				      }
				};
				queryThread.start();
			}
		});
		
		setVisible(true);
	}
	
	public void refresh(byte[] data, int index) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	System.out.println("img");
		    	Image theImage = getToolkit().createImage(data);
				getToolkit().prepareImage(theImage,-1,-1,null);
		    	icon.setImage(theImage);
		    	icon.paintIcon(GUI.this, canvas.get(index).getGraphics(), 0, 0);
		    }
		 });
		
	}
}
