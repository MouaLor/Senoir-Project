package windowbuilder;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Calling class connecting to database
	Connection connection=null;
	private JTextField textField;
	private JPasswordField passwordField;
	
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection=databaseConnection.dbConnector();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 443, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblYouAreConnected = new JLabel("User Name");
		lblYouAreConnected.setBounds(54, 101, 116, 43);
		frame.getContentPane().add(lblYouAreConnected);
		
		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon("C:\\Users\\moual\\workspace\\importDatabase\\img\\PLI.PNG"));
		label.setBounds(90, 13, 240, 59);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(54, 172, 78, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String user=textField.getText();
				String pass=passwordField.getText();
					
					if((user.equals("dbadmin")) && (pass.equals("password4"))){
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
						try {
							Connection conn=DriverManager.getConnection(  
									"jdbc:mysql://localhost:3306/mydb?useSSL=false","dbadmin","password4");
						} catch (SQLException e) {e.printStackTrace();}
						} catch (ClassNotFoundException e) {e.printStackTrace();}  
						JOptionPane.showMessageDialog(frame, "Connection Successful.");
						
						frame.dispose();  //closes frame
						ToolDatabase toolData=new ToolDatabase();
						toolData.setVisible(true);
						
					}
					else{
						JOptionPane.showMessageDialog(frame, "Incorrect Username or Password.");
						
					}
					
			}
		});
		btnLogin.setBounds(285, 233, 116, 32);
		frame.getContentPane().add(btnLogin);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(122, 106, 216, 32);
		frame.getContentPane().add(textField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, 164, 216, 32);
		frame.getContentPane().add(passwordField);
	}
}
