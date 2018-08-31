package software;

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

@SuppressWarnings("serial")
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
	private JTextField textField_toolID;
	private JTextField textField_newTag;
	private JTextField textField_sharpTagID;
	private JTextField textField_sharpDate;
	private JTable table_2;
	private JTextField textField_SearchTagID;
	private JTextField textField_searchTool;
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
		
		JButton btnLoadTracking = new JButton("Search");
		btnLoadTracking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadTracking.addActionListener(new ActionListener() {
			//@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent arg0) {
				try{
					if (textField_SearchTagID.getText().length()==5 || textField_searchTool.getText().length()<=10){
						if (textField_SearchTagID.getText().length()==5){
							String query="select*from main_table where tagID ='"+textField_SearchTagID.getText()+"'";
							PreparedStatement pst=connection.prepareStatement(query);
							ResultSet rs=pst.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
						
							pst.close();
							rs.close();
						}
						else if (textField_searchTool.getText().length()!=0){
							String query1="select*from main_table where tool_number ='"+textField_searchTool.getText()+"'";
							PreparedStatement pst=connection.prepareStatement(query1);
							ResultSet rs=pst.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
						
							pst.close();
							rs.close();
						
					}
					else{
						
						JOptionPane.showMessageDialog(null, "Incorrect or no input. Add leading zero for 4 digit tag IDs.");
						
					}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		btnLoadTracking.setBounds(580, 278, 193, 56);
		contentPane.add(btnLoadTracking);
		
		JScrollPane scrollPane_toolTracking = new JScrollPane();
		scrollPane_toolTracking.setToolTipText("");
		scrollPane_toolTracking.setBounds(65, 160, 808, 89);
		contentPane.add(scrollPane_toolTracking);
		
		table = new JTable();
		scrollPane_toolTracking.setViewportView(table);
		
		JScrollPane scrollPane_dateSharpened = new JScrollPane();
		scrollPane_dateSharpened.setBounds(487, 432, 386, 223);
		contentPane.add(scrollPane_dateSharpened);
		
		table_1 = new JTable();
		scrollPane_dateSharpened.setViewportView(table_1);
		
		JLabel lblDateSharpened = new JLabel("Dates Sharpened:");
		lblDateSharpened.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDateSharpened.setBounds(476, 387, 204, 37);
		contentPane.add(lblDateSharpened);
		
		JLabel lblNewLabel = new JLabel("Search:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(54, 114, 343, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\moual\\workspace\\importDatabase\\img\\PLI.PNG"));
		lblNewLabel_1.setBounds(431, 13, 249, 73);
		contentPane.add(lblNewLabel_1);
		
		JButton btnAddSharpenedDate = new JButton("Add Date");
		btnAddSharpenedDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAddSharpenedDate.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				try{
					if (textField_sharpTagID.getText().length()==5 && textField_sharpDate.getText().length()==10){
					String query="INSERT INTO punches (tagID, punches,date_sharpened) VALUES (?,(select current_punches from main_table where tagID='"+textField_sharpTagID.getText()+"'),?)";
					String query1="update main_table set date_sharpened='"+textField_sharpDate.getText()+"', current_punches=0 where tagID='"+textField_sharpTagID.getText()+"'";
					PreparedStatement pst=connection.prepareStatement(query);
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst.setString(1, textField_sharpTagID.getText() );
					pst.setString(2, textField_sharpDate.getText() );
					
					int rs=pst.executeUpdate();
					int rs1=pst1.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Data Saved");
					
					pst.close();
					pst1.close();
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Tag ID must be 5 digits and date must be of the format '0000-00-00'. Add leading zero for 4 digit tag IDs. ");
					}
				}catch(Exception g){
					g.printStackTrace();
				}
				
			}
		});
		btnAddSharpenedDate.setBounds(940, 624, 132, 31);
		contentPane.add(btnAddSharpenedDate);
		
		JLabel lblReplaceTag = new JLabel("Enter New Tag");
		lblReplaceTag.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblReplaceTag.setBounds(940, 80, 181, 31);
		contentPane.add(lblReplaceTag);
		
		textField_toolID = new JTextField();
		textField_toolID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_toolID.setBounds(940, 147, 175, 43);
		contentPane.add(textField_toolID);
		textField_toolID.setColumns(10);
		
		textField_newTag = new JTextField();
		textField_newTag.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_newTag.setBounds(940, 235, 175, 43);
		contentPane.add(textField_newTag);
		textField_newTag.setColumns(10);
		
		JLabel lblEnterOldTag = new JLabel("Enter Tool ID:");
		lblEnterOldTag.setBounds(940, 124, 175, 16);
		contentPane.add(lblEnterOldTag);
		
		JLabel lblEnterNewTag = new JLabel("Enter New 5 Digit Tag ID:");
		lblEnterNewTag.setBounds(940, 217, 175, 16);
		contentPane.add(lblEnterNewTag);
		
		JButton btnReplaceTag = new JButton("Update");
		btnReplaceTag.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReplaceTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (textField_toolID.getText().length()!=0 && textField_newTag.getText().length()==5){
						//String query="UPDATE tool_database set tagID=REPLACE(tagID,'"+textField_toolID.getText()+"','"+textField_newTag.getText()+"')";
						String query="update main_table set tool_number='" +textField_toolID.getText()+ "' where tagID='" +textField_newTag.getText()+ "'";
						PreparedStatement pst=connection.prepareStatement(query);
						
						pst.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Data Updated");
						
						pst.close();
						}
					else{
						JOptionPane.showMessageDialog(null, "Incorrect or no input. Add leading zeros for 4 digit tag IDs.");
					}
					
				}catch(Exception g){
					g.printStackTrace();
				}
				
			}
		});
		btnReplaceTag.setBounds(940, 291, 132, 31);
		contentPane.add(btnReplaceTag);
		
		textField_sharpTagID = new JTextField();
		textField_sharpTagID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_sharpTagID.setBounds(940, 485, 175, 37);
		contentPane.add(textField_sharpTagID);
		textField_sharpTagID.setColumns(10);
		
		textField_sharpDate = new JTextField();
		textField_sharpDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_sharpDate.setBounds(940, 574, 175, 37);
		contentPane.add(textField_sharpDate);
		textField_sharpDate.setColumns(10);
		
		JLabel lblEnterTagId = new JLabel("Enter 5 Digit Tag ID:");
		lblEnterTagId.setBounds(940, 468, 181, 16);
		contentPane.add(lblEnterTagId);
		
		JLabel lblEnterDateyearmonthday = new JLabel("Enter Date: ");
		lblEnterDateyearmonthday.setBounds(940, 535, 181, 16);
		contentPane.add(lblEnterDateyearmonthday);
		
		JLabel lblAddingSharpenedDate = new JLabel("Add Sharpened Date");
		lblAddingSharpenedDate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAddingSharpenedDate.setBounds(940, 423, 235, 31);
		contentPane.add(lblAddingSharpenedDate);
		
		JButton btnLoadDateSharpened = new JButton("Load Dates Sharpened");
		btnLoadDateSharpened.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadDateSharpened.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{					
					String query="SELECT tagID, punches, MAX(date_sharpened) AS date_sharpened FROM punches GROUP BY tagID, punches ORDER BY date_sharpened DESC ";
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
					String query="SELECT tool_number as Tool_Number, tagID AS Tag_ID, overall_punches AS Overal_Punches, current_punches as Current_Punches FROM main_table GROUP BY tagID";
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
		
		textField_SearchTagID = new JTextField();
		textField_SearchTagID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_SearchTagID.setColumns(10);
		textField_SearchTagID.setBounds(65, 286, 175, 43);
		contentPane.add(textField_SearchTagID);
		
		JLabel lblEnterDigit = new JLabel("Enter 5 Digit Tag ID:");
		lblEnterDigit.setBounds(65, 262, 155, 16);
		contentPane.add(lblEnterDigit);
		
		textField_searchTool = new JTextField();
		textField_searchTool.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_searchTool.setColumns(10);
		textField_searchTool.setBounds(317, 286, 175, 43);
		contentPane.add(textField_searchTool);
		
		JLabel lblEnterToolId = new JLabel("Enter Tool ID:");
		lblEnterToolId.setBounds(317, 262, 155, 16);
		contentPane.add(lblEnterToolId);
		
		JLabel lblNewLabel_or = new JLabel("OR");
		lblNewLabel_or.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_or.setBounds(266, 286, 39, 37);
		contentPane.add(lblNewLabel_or);
		
		JLabel lblyearmonthday = new JLabel("\"0000-00-00\" (year-month-day)");
		lblyearmonthday.setBounds(940, 555, 209, 16);
		contentPane.add(lblyearmonthday);
	}
}
