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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;

public class GUI extends JFrame {
	private ImageIcon icon;
	private Client client;

	ArrayList<Canvas> canvas = new ArrayList<Canvas>();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel lblModeDisplay;
	private JLabel lblSyncTypeDisplay;
	JButton btnIdle, btnMovie, btnConnect1, btnDisconnect1, btnConnect2, btnDisconnect2, btnSynchronous, btnAsynchronous, btnAuto;

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
		
		btnIdle = new JButton("Idle");
		btnIdle.setBounds(12, 725, 314, 49);
		getContentPane().add(btnIdle);
		btnIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.IDLE_MODE);
				        setMode(Client.IDLE_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		btnMovie = new JButton("Movie");
		btnMovie.setBounds(338, 725, 314, 49);
		getContentPane().add(btnMovie);
		btnMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setMode(Client.MOVIE_MODE);
				        setMode(Client.MOVIE_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		btnConnect1 = new JButton("Connect");
		btnConnect1.setBounds(12, 664, 314, 49);
		getContentPane().add(btnConnect1);
		btnConnect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				    	  try {
				    		  client.connectCamera(textField_1.getText(), Integer.parseInt(textField_2.getText())); 
				    	  } catch (Exception e) {
				    		  JOptionPane.showMessageDialog(GUI.this, "Couldn't connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
				    	  }
				      }
				};
				queryThread.start();
			}
		});
		
		btnDisconnect1 = new JButton("Disconnect");
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
		
		btnConnect2 = new JButton("Connect");
		btnConnect2.setBounds(663, 664, 314, 49);
		getContentPane().add(btnConnect2);
		btnConnect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				    	  try {
				    		  client.connectCamera(textField_3.getText(), Integer.parseInt(textField_4.getText())); 
				    	  } catch (Exception e) {
				    		  JOptionPane.showMessageDialog(GUI.this, "Couldn't connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
				    	  }
				      }
				};
				queryThread.start();
			}
		});
		
		btnDisconnect2 = new JButton("Disconnect");
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
		
		btnSynchronous = new JButton("Synchronous");
		btnSynchronous.setBounds(664, 725, 314, 49);
		getContentPane().add(btnSynchronous);
		btnSynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setSyncType(Client.SYNCHRONOUS_MODE); 
				        setSyncType(Client.SYNCHRONOUS_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		btnAsynchronous = new JButton("Asynchronous");
		btnAsynchronous.setBounds(989, 725, 314, 49);
		getContentPane().add(btnAsynchronous);
		btnAsynchronous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setSyncType(Client.ASYNCHRONOUS_MODE); 
				        setSyncType(Client.ASYNCHRONOUS_MODE);
				      }
				};
				queryThread.start();
			}
		});
		
		btnAuto = new JButton("Auto");
		btnAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread queryThread = new Thread() {
				      public void run() {
				        client.setAutoMode(Client.AUTO_MODE);
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
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Server address");
		textField_1.setBounds(12, 540, 314, 19);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("Port number");
		textField_2.setColumns(10);
		textField_2.setBounds(338, 540, 314, 19);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setToolTipText("Server address");
		textField_3.setColumns(10);
		textField_3.setBounds(663, 540, 314, 19);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("Port number");
		textField_4.setColumns(10);
		textField_4.setBounds(989, 540, 314, 19);
		getContentPane().add(textField_4);
		
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
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setToolTipText("Progress");
		progressBar.setMaximum(5);
		progressBar.setBounds(12, 474, 314, 14);
		getContentPane().add(progressBar);
		
		
		setVisible(true);
	}
	
	/**
	 * Updates the GUI
	 */
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
	
	/**
	 * Sets the operating mode of the system. 0 or 1
	 */
	public void setMode(int mode){
		if(mode == Client.IDLE_MODE){
			lblModeDisplay.setText("Idle");
			btnAuto.setEnabled(true);
			btnIdle.setEnabled(false);
			btnMovie.setEnabled(true);
		}
		else if(mode == Client.MOVIE_MODE){
			lblModeDisplay.setText("Movie");
			btnAuto.setEnabled(true);
			btnIdle.setEnabled(true);
			btnMovie.setEnabled(false);
		}
	}
	
	/**
	 * Sets the synchronization type of the system. 0 or 1
	 */
	public void setSyncType(int type){
		if(type == Client.SYNCHRONOUS_MODE){
			lblSyncTypeDisplay.setText("Synchronous");
			btnAuto.setEnabled(true);
			btnSynchronous.setEnabled(false);
			btnAsynchronous.setEnabled(true);
		}
		else if(type == Client.ASYNCHRONOUS_MODE){
			btnAuto.setEnabled(true);
			btnSynchronous.setEnabled(true);
			btnAsynchronous.setEnabled(false);
			lblSyncTypeDisplay.setText("Asynchronous");
		}
	}
	
	/**
	 * Sets auto mode
	 */
	public void setAuto(){
		btnAuto.setEnabled(false);
		btnIdle.setEnabled(true);
		btnMovie.setEnabled(true);
		btnSynchronous.setEnabled(true);
		btnAsynchronous.setEnabled(true);
		lblModeDisplay.setText("Auto");
		lblSyncTypeDisplay.setText("Auto");
	}
}
