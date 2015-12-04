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

public class GUI extends JFrame {
	private ImageIcon icon;
	private Client client;
	private ClientMonitor monitor;

	private ArrayList<Canvas> canvas = new ArrayList<Canvas>();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel lblModeDisplay;
	private JLabel lblSyncTypeDisplay;
	private JButton btnIdle, btnMovie, btnConnect1, btnDisconnect1, btnConnect2, btnDisconnect2, btnSynchronous, btnAsynchronous, btnAuto;
	private ArrayList<JLabel> delays = new ArrayList<JLabel>();
	private JLabel lblAuto;
	private ArrayList<JLabel> motions = new ArrayList<JLabel>();

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
	public GUI(Client client, ClientMonitor monitor) {
		super();
		this.client = client;
		this.monitor=monitor;
		
	
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
				        setIdleMode(true);
				        btnAuto.setEnabled(true);
						btnIdle.setEnabled(false);
						btnMovie.setEnabled(true);
				        lblAuto.setText("Manual");	
				        monitor.setAutoMode(false);
				        resetMotionTriggered();
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
						btnAuto.setEnabled(true);
						btnIdle.setEnabled(true);
						btnMovie.setEnabled(false);
						lblAuto.setText("Manual");
						setIdleMode(false);
						monitor.setAutoMode(false);
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
				    		  client.connectCamera(0,textField_1.getText(), Integer.parseInt(textField_2.getText())); 
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
				        monitor.buffertConfirmedCleared(0);
				        try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        motions.get(0).setText("");
				        canvas.get(0).getGraphics().clearRect(0, 0, 640, 480);
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
				    		  client.connectCamera(1,textField_3.getText(), Integer.parseInt(textField_4.getText())); 
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
				        monitor.buffertConfirmedCleared(1);
				        try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        motions.get(1).setText("");
				        canvas.get(1).getGraphics().clearRect(0, 0, 640, 480);
			
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
						btnAuto.setEnabled(true);
						btnSynchronous.setEnabled(false);
						btnAsynchronous.setEnabled(true);
						setSyncType(true);
						lblAuto.setText("Manual");
						monitor.setAutoMode(false);
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
						btnAuto.setEnabled(true);
						btnSynchronous.setEnabled(true);
						btnAsynchronous.setEnabled(false);
						setSyncType(false);
						lblAuto.setText("Manual");
						monitor.setAutoMode(false);
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
					    btnAuto.setEnabled(false);
						btnIdle.setEnabled(true);
						btnMovie.setEnabled(true);
						btnSynchronous.setEnabled(true);
						btnAsynchronous.setEnabled(true);  
						setAuto(true);
				      }
				};
				queryThread.start();
			}
		});
		btnAuto.setBounds(501, 786, 314, 49);
		getContentPane().add(btnAuto);
		
		JLabel lblDelay = new JLabel("Delay: ");
		lblDelay.setBounds(12, 500, 62, 15);
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
		label.setBounds(663, 500, 70, 15);
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
		
		lblModeDisplay = new JLabel("");
		lblModeDisplay.setBounds(420, 612, 70, 15);
		getContentPane().add(lblModeDisplay);
		
		lblSyncTypeDisplay = new JLabel("");
		lblSyncTypeDisplay.setBounds(760, 612, 167, 15);
		getContentPane().add(lblSyncTypeDisplay);
		
		delays.add(new JLabel(""));
		delays.get(0).setBounds(86, 500, 70, 15);
		getContentPane().add(delays.get(0));
		
		delays.add(new JLabel(""));
		delays.get(1).setBounds(745, 500, 70, 15);
		getContentPane().add(delays.get(1));
		
		lblAuto = new JLabel("");
		lblAuto.setBounds(94, 612, 75, 15);
		getContentPane().add(lblAuto);
		
		JLabel lblControl = new JLabel("Control:");
		lblControl.setBounds(12, 612, 70, 15);
		getContentPane().add(lblControl);
		
		motions.add(new JLabel("")); 
		motions.get(0).setBounds(256, 500, 300, 15);
		getContentPane().add(motions.get(0));
		
		motions.add(new JLabel(""));
		motions.get(1).setBounds(907, 500, 300, 15);
		getContentPane().add(motions.get(1));
		
//		if(client.isAutoMode()){
//			setAuto();
//		}
		
		setVisible(true);
	}
	
	/**
	 * Updates the GUI
	 */
	public void refresh(byte[] data, int index, long timestamp) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	System.out.println("img");
		    	Image theImage = getToolkit().createImage(data);
				getToolkit().prepareImage(theImage,-1,-1,null);
		    	icon.setImage(theImage);
		    	icon.paintIcon(GUI.this, canvas.get(index).getGraphics(), 0, 0);
		    	updateDelay(timestamp, index);
		    }
		 });
	}
	
	/**
	 * Sets the operating mode of the system. 0 or 1
	 */
	public void setIdleMode(boolean mode){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if(mode){
		    		setIdleModeLabel(mode);
					monitor.setIdle(true);
				}
				else{
					setIdleModeLabel(mode);
					monitor.setIdle(false);
				}
		    }
		 });	
	}
	
	public void setIdleModeLabel(boolean mode) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if(mode){
					lblModeDisplay.setText("Idle");
				}
				else{
					lblModeDisplay.setText("Movie");
				}
		    }
		 });	
	}
	
	/**
	 * Sets the synchronization type of the system. 0 or 1
	 */
	public void setSyncType(boolean isSynced){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if(isSynced){
		    		setSyncTypeLabel(isSynced);
					monitor.setSyncMode(isSynced);
				}
				else{
					setSyncTypeLabel(isSynced);
					monitor.setSyncMode(isSynced);
				}
		    }
		 });
	}
	
	public void setSyncTypeLabel(boolean isSynced) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if(isSynced){
					lblSyncTypeDisplay.setText("Synchronous");
				}
				else{
					lblSyncTypeDisplay.setText("Asynchronous");
				}
		    }
		 });
	}
	
	/**
	 * Sets auto mode
	 */
	public void setAuto(boolean isAuto){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				setAutoLabel(isAuto);
				monitor.setAutoMode(true);
				
		    }
		 });
	}
	
	public void setAutoLabel(boolean isAuto){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if(isAuto){
		    		lblAuto.setText("Auto");
		    	}else{
		    		lblAuto.setText("Manual");
		    	}
		    }
		 });
	}
	
	/**
	 * Updates the delay label
	 */
	private void updateDelay(long delay, int index){
		delays.get(index).setText(delay + "");
	}
	
	public void motionTriggedBy(int index){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	motions.get(index).setText("Motion Triggered By Camera " + (index+1));
		    }
		 });
	}
	
	private void resetMotionTriggered(){
        for(int i = 0; i < motions.size(); i++){
        	motions.get(i).setText("");
        }
	}
	
}
