package windowbuilder;

import java.sql.*;
import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ToolDatabase extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolDatabase frame = new ToolDatabase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection=null;
	private JTable table_1;
	private JTextField textField_oldTag;
	private JTextField textField_newTag;
	private JTextField textField_sharpTagID;
	private JTextField textField_sharpDate;
	private JTable table_2;
	private JTextField textField_from;
	private JTextField textField_to;
	/**
	 * Create the frame.
	 */
	public ToolDatabase() {
		connection=databaseConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1205, 822);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTracking = new JButton("Load Location");
		btnLoadTracking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if (textField_from.getText().length()==10 && textField_to.getText().length()==10){
					String query="SELECT MAX(DATE(date)) AS Date, LEFT(tagID,2) AS Machine, RIGHT(tagID,7) AS Tag_ID, COUNT(RIGHT(tagID,7)) AS Punches FROM tool_database WHERE DATE(date)>='"+textField_from.getText()+"' AND DATE(date) <='"+textField_to.getText()+"' GROUP BY LEFT(tagID,2)";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Date must be in the form 'year-month-day'.");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		btnLoadTracking.setBounds(65, 278, 193, 56);
		contentPane.add(btnLoadTracking);
		
		JScrollPane scrollPane_toolTracking = new JScrollPane();
		scrollPane_toolTracking.setBounds(65, 124, 808, 133);
		contentPane.add(scrollPane_toolTracking);
		
		table = new JTable();
		scrollPane_toolTracking.setViewportView(table);
		
		JScrollPane scrollPane_dateSharpened = new JScrollPane();
		scrollPane_dateSharpened.setBounds(487, 432, 386, 223);
		contentPane.add(scrollPane_dateSharpened);
		
		table_1 = new JTable();
		scrollPane_dateSharpened.setViewportView(table_1);
		
		JLabel lblDateSharpened = new JLabel("Date Sharpened:");
		lblDateSharpened.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDateSharpened.setBounds(476, 387, 204, 37);
		contentPane.add(lblDateSharpened);
		
		JLabel lblNewLabel = new JLabel("Tool Location:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(54, 94, 269, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\moual\\workspace\\importDatabase\\img\\PLI.PNG"));
		lblNewLabel_1.setBounds(431, 13, 249, 73);
		contentPane.add(lblNewLabel_1);
		
		JButton btnAddSharpenedDate = new JButton("Add Sharpened Date");
		btnAddSharpenedDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAddSharpenedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (textField_sharpTagID.getText().length()==7 && textField_sharpDate.getText().length()==10){
					String query="INSERT INTO sharpened (Tag_ID, Sharpened) VALUES (?,?)";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, textField_sharpTagID.getText() );
					pst.setString(2, textField_sharpDate.getText() );
					
					int rs=pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Data Saved");
					
					pst.close();
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Tag ID must be 7 digits and date must be of the format 'year-month-day' ");
					}
				}catch(Exception g){
					g.printStackTrace();
				}
				
			}
		});
		btnAddSharpenedDate.setBounds(940, 613, 181, 31);
		contentPane.add(btnAddSharpenedDate);
		
		JLabel lblReplaceTag = new JLabel("Replace Tag");
		lblReplaceTag.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblReplaceTag.setBounds(940, 80, 139, 31);
		contentPane.add(lblReplaceTag);
		
		textField_oldTag = new JTextField();
		textField_oldTag.setBounds(940, 147, 175, 43);
		contentPane.add(textField_oldTag);
		textField_oldTag.setColumns(10);
		
		textField_newTag = new JTextField();
		textField_newTag.setBounds(940, 235, 175, 43);
		contentPane.add(textField_newTag);
		textField_newTag.setColumns(10);
		
		JLabel lblEnterOldTag = new JLabel("Enter 7 Digit Old Tag ID:");
		lblEnterOldTag.setBounds(940, 124, 175, 16);
		contentPane.add(lblEnterOldTag);
		
		JLabel lblEnterNewTag = new JLabel("Enter 7 Digit New Tag ID:");
		lblEnterNewTag.setBounds(940, 217, 175, 16);
		contentPane.add(lblEnterNewTag);
		
		JButton btnReplaceTag = new JButton("Replace Tag");
		btnReplaceTag.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReplaceTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (textField_newTag.getText().length()==7 && textField_oldTag.getText().length()==7 ){
						String query="UPDATE tool_database set tagID=REPLACE(tagID,'"+textField_oldTag.getText()+"','"+textField_newTag.getText()+"')";
						PreparedStatement pst=connection.prepareStatement(query);
						
						pst.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Data Updated");
						
						pst.close();
						}
					else{
						JOptionPane.showMessageDialog(null, "Tag IDs must be 7 digits.");
					}
					
				}catch(Exception g){
					g.printStackTrace();
				}
				
			}
		});
		btnReplaceTag.setBounds(940, 291, 132, 31);
		contentPane.add(btnReplaceTag);
		
		textField_sharpTagID = new JTextField();
		textField_sharpTagID.setBounds(940, 482, 147, 37);
		contentPane.add(textField_sharpTagID);
		textField_sharpTagID.setColumns(10);
		
		textField_sharpDate = new JTextField();
		textField_sharpDate.setBounds(940, 563, 147, 37);
		contentPane.add(textField_sharpDate);
		textField_sharpDate.setColumns(10);
		
		JLabel lblEnterTagId = new JLabel("Enter 7 Digit Tag ID:");
		lblEnterTagId.setBounds(940, 462, 139, 16);
		contentPane.add(lblEnterTagId);
		
		JLabel lblEnterDateyearmonthday = new JLabel("Enter Date: (year-month-day)");
		lblEnterDateyearmonthday.setBounds(940, 543, 181, 16);
		contentPane.add(lblEnterDateyearmonthday);
		
		JLabel lblAddingSharpenedDate = new JLabel("Add Sharpened Date");
		lblAddingSharpenedDate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAddingSharpenedDate.setBounds(940, 413, 193, 31);
		contentPane.add(lblAddingSharpenedDate);
		
		JButton btnLoadDateSharpened = new JButton("Load Date Sharpened");
		btnLoadDateSharpened.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadDateSharpened.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{					
					String query="SELECT Tag_ID, MAX(sharpened) AS Date_Sharpened FROM sharpened GROUP BY Tag_ID ORDER BY Sharpened DESC ";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		btnLoadDateSharpened.setBounds(487, 661, 229, 56);
		contentPane.add(btnLoadDateSharpened);
		
		JScrollPane scrollPane_toolSummary = new JScrollPane();
		scrollPane_toolSummary.setBounds(65, 432, 386, 223);
		contentPane.add(scrollPane_toolSummary);
		
		table_2 = new JTable();
		scrollPane_toolSummary.setViewportView(table_2);
		
		JLabel lblNewLabel_2 = new JLabel("Tool Summary:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(54, 392, 193, 27);
		contentPane.add(lblNewLabel_2);
		
		JButton btnLoadSummary = new JButton("Load Summary");
		btnLoadSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{					
					String query="SELECT MAX(DATE(date)) AS Date, RIGHT(tagID,7) AS Tag_ID, COUNT(RIGHT(tagID,7)) AS Running_Total FROM tool_database GROUP BY RIGHT(tagID,7)";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table_2.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch(Exception f){
					f.printStackTrace();
				}
			}
		});
		btnLoadSummary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadSummary.setBounds(65, 661, 193, 56);
		contentPane.add(btnLoadSummary);
		
		textField_from = new JTextField();
		textField_from.setBounds(337, 290, 178, 44);
		contentPane.add(textField_from);
		textField_from.setColumns(10);
		
		textField_to = new JTextField();
		textField_to.setBounds(570, 291, 181, 43);
		contentPane.add(textField_to);
		textField_to.setColumns(10);
		
		JLabel lblDateToolWas = new JLabel("From: (year-month-day)");
		lblDateToolWas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateToolWas.setBounds(337, 270, 193, 16);
		contentPane.add(lblDateToolWas);
		
		JLabel lblTodaysDate = new JLabel("To: (year-month-day)");
		lblTodaysDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTodaysDate.setBounds(570, 270, 181, 19);
		contentPane.add(lblTodaysDate);
	}
}
