package windowbuilder;

import java.awt.EventQueue;
import java.io.*;


import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class firstwindow {

	private JFrame frame1;
	//private JFrame frame2;
	private JTextField username_text;
	private JPasswordField password_text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					firstwindow window = new firstwindow();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public firstwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 450, 355);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		username_text = new JTextField();
		username_text.setFont(new Font("Tahoma", Font.PLAIN, 14));
		username_text.setBounds(56, 124, 240, 31);
		frame1.getContentPane().add(username_text);
		username_text.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(56, 103, 115, 25);
		frame1.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(55, 178, 97, 27);
		frame1.getContentPane().add(lblNewLabel_1);
		
		JButton enter_button = new JButton("Enter");
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String user=username_text.getText();
				String pass=password_text.getText();
					
					if((user.equals("dbadmin")) && (pass.equals("password4"))){
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
						try {
							Connection con=DriverManager.getConnection(  
									"jdbc:mysql://localhost:3306/mydb?useSSL=false","dbadmin","password4");
						} catch (SQLException e) {e.printStackTrace();}
						} catch (ClassNotFoundException e) {e.printStackTrace();}  
						JOptionPane.showMessageDialog(frame1, "Connection Successful.");
						
						frame1.dispose();  //Remove frame 1
						//new frame2().setVisible(true); // frame 2 to show after the Login (frame1) Form
						//frame2.setVisible(true) //Show other frame
						
					}
					else{
						JOptionPane.showMessageDialog(frame1, "Incorrect Username or Password.");
						
					}
					
				}
		});
		enter_button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		enter_button.setToolTipText("enter\r\n");
		enter_button.setBounds(305, 257, 115, 38);
		frame1.getContentPane().add(enter_button);
		
		password_text = new JPasswordField();
		password_text.setBounds(56, 200, 240, 31);
		frame1.getContentPane().add(password_text);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\moual\\Desktop\\Java\\Capture.PNG"));
		lblNewLabel_2.setBounds(89, 23, 240, 59);
		frame1.getContentPane().add(lblNewLabel_2);
	}
}
