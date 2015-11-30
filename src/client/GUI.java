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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GUI extends JFrame {
	private ImageIcon icon;
	private Client client;

	ArrayList<Canvas> canvas = new ArrayList<Canvas>();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	JLabel lblModeDisplay;
	JLabel lblSyncTypeDisplay;

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
		setSize(1316,900);
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
		btnIdle.setBounds(12, 725, 314, 49);
		getContentPane().add(btnIdle);
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.IDLE_MODE);
				        setMode(client.IDLE_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnMovie = new JButton("Movie");
		btnMovie.setBounds(338, 725, 314, 49);
		getContentPane().add(btnMovie);
		btnMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.MOVIE_MODE);
				        setMode(client.MOVIE_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnConnect1 = new JButton("Connect");
		btnConnect1.setBounds(12, 664, 314, 49);
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
		btnDisconnect1.setBounds(338, 664, 314, 49);
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
		btnConnect2.setBounds(663, 664, 314, 49);
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
		btnDisconnect2.setBounds(989, 664, 314, 49);
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
		btnSynchronous.setBounds(664, 725, 314, 49);
		getContentPane().add(btnSynchronous);
		btnSynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setSyncType(Client.SYNCHRONOUS_MODE); 
				        setSyncType(client.SYNCHRONOUS_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnAsynchronous = new JButton("Asynchronous");
		btnAsynchronous.setBounds(989, 725, 314, 49);
		getContentPane().add(btnAsynchronous);
		btnAsynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setSyncType(Client.ASYNCHRONOUS_MODE); 
				        setSyncType(client.ASYNCHRONOUS_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		JButton btnAuto = new JButton("Auto");
		btnAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setAutoMode();
				        setAuto();
				      }
				};
				queryThread.start();
			}
		});
		btnAuto.setBounds(501, 786, 314, 49);
		getContentPane().add(btnAuto);
		
		JLabel lblDelay = new JLabel("Delay: ");
		lblDelay.setBounds(12, 500, 314, 15);
		getContentPane().add(lblDelay);
		
		textField = new JTextField();
		textField.setToolTipText("Server address");
		textField.setBounds(12, 540, 314, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Port number");
		textField_1.setColumns(10);
		textField_1.setBounds(338, 540, 314, 19);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("Server address");
		textField_2.setColumns(10);
		textField_2.setBounds(663, 540, 314, 19);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setToolTipText("Port number");
		textField_3.setColumns(10);
		textField_3.setBounds(989, 540, 314, 19);
		getContentPane().add(textField_3);
		
		JLabel label = new JLabel("Delay: ");
		label.setBounds(663, 500, 314, 15);
		getContentPane().add(label);
		
		JLabel lblServerAddress = new JLabel("Server address");
		lblServerAddress.setBounds(12, 565, 314, 15);
		getContentPane().add(lblServerAddress);
		
		JLabel lblPortNumber = new JLabel("Port number");
		lblPortNumber.setBounds(338, 565, 314, 15);
		getContentPane().add(lblPortNumber);
		
		JLabel label_1 = new JLabel("Server address");
		label_1.setBounds(663, 565, 314, 15);
		getContentPane().add(label_1);
		
		JLabel lblPortNumber_1 = new JLabel("Port number");
		lblPortNumber_1.setBounds(989, 565, 314, 15);
		getContentPane().add(lblPortNumber_1);
		
		
		
		JLabel lblMode = new JLabel("Mode: ");
		lblMode.setBounds(338, 612, 70, 15);
		getContentPane().add(lblMode);
		
		JLabel lblSynctype = new JLabel("SyncType:");
		lblSynctype.setBounds(663, 612, 85, 15);
		getContentPane().add(lblSynctype);
		
		lblModeDisplay = new JLabel("soigdsg");
		lblModeDisplay.setBounds(420, 612, 70, 15);
		getContentPane().add(lblModeDisplay);
		
		lblSyncTypeDisplay = new JLabel("kdjfg");
		lblSyncTypeDisplay.setBounds(760, 612, 167, 15);
		getContentPane().add(lblSyncTypeDisplay);
		
		
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
	
	public void setMode(int mode){
		if(mode == client.IDLE_MODE){
			lblModeDisplay.setText("Idle");
		}
		else if(mode == client.MOVIE_MODE){
			lblModeDisplay.setText("Movie");
		}
	}
	
	public void setSyncType(int type){
		if(type == client.SYNCHRONOUS_MODE){
			lblSyncTypeDisplay.setText("Synchronous");
		}
		else if(type == client.ASYNCHRONOUS_MODE){
			lblSyncTypeDisplay.setText("Asynchronous");
		}
	}
	
	public void setAuto(){
		lblModeDisplay.setText("Auto");
		lblSyncTypeDisplay.setText("Auto");
	}
}
