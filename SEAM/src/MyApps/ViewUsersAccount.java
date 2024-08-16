package MyApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ViewUsersAccount extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnShow;
	private DefaultTableModel tableModel;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewUsersAccount frame = new ViewUsersAccount();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewUsersAccount() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(10, 11, 399, 310);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Users Account Summary");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewLabel.setBounds(26, 11, 198, 45);
		panel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 67, 363, 190);
		panel.add(scrollPane);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("First Name");
		tableModel.addColumn("Last Name");
		tableModel.addColumn("Role");
		tableModel.addColumn("Username");
		tableModel.addColumn("Password");
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JButton btnClearData = new JButton("Clear Data");
		btnClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearData();
			}
		});
		
		
		btnClearData.setBounds(215, 268, 174, 23);
		panel.add(btnClearData);
		
		btnNewButton = new JButton("Return");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

		        EquipmentControlSystem ecs = new EquipmentControlSystem();

		        ecs.setVisible(true);

		        setVisible(false); 
			}
		});
		btnNewButton.setBounds(26, 268, 159, 23);
		panel.add(btnNewButton);
		fetchUser();
	}
	
		private void clearData() {
		    // Database connection details
		    String url = "jdbc:mysql://localhost:3306/equipment_management_db";
		    String username = "root";
		    String password = "09242003Believeitcovered.";

		    try (Connection connection = DriverManager.getConnection(url, username, password)) {
		        // Delete existing data from EquipmentAssignmentToRoom table
		    	String deleteQuery = "DELETE FROM account";
		        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
		        deleteStatement.execute(); 
		        // Commit the changes
		        
		        tableModel.removeRow(0);
		        
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        // Handle any database-related errors

		        
		          
		    }
		
	}

	public void fetchUser() {
		
		ArrayList<String> users= new ArrayList<String>();
		
	//MySQL database connection details
	String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	String username = "root";
	String password = "09242003Believeitcovered.";
	
	try (Connection connection = DriverManager.getConnection(url, username, password)) {
		//fetch and populate data from Users table
		String query = "SELECT us.user_Fname, us.user_Lname, r.userRoleName,ac.Username,ac.accountPassword FROM users us INNER JOIN userRole r ON (us.userRoleID =r.userRoleID) INNER JOIN account ac ON (us.userID = ac.UsersID)";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
	        while (rs.next()) {
	        	System.out.println("Success");
	
	            String Username = rs.getString("Username");
	            String User_Password = rs.getString("AccountPassword");
	            String User_Fname = rs.getString("User_Fname");
	            String User_Lname = rs.getString("User_Lname");
	            String User_RoleName = rs.getString("UserRoleName");
	            // Add the retrieved data to the JTable
	            
	           
	          tableModel.addRow(new Object[]{User_Fname, User_Lname, User_RoleName, Username, User_Password});
	            //tableModel.addRow(new Object[]{equipmentName, equipmentType, condition,roomName, eqStatus});
	        }
} catch (SQLException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
	}
}
	

