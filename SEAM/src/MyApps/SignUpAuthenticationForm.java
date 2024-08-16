package MyApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpAuthenticationForm extends JFrame {

	private JPanel contentPane;
	private JTextField tf_fname;
	private JTextField tf_username;
	private JTextField tf_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpAuthenticationForm frame = new SignUpAuthenticationForm();
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
	public SignUpAuthenticationForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 483);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(47, 79, 79));
		contentPane.setForeground(new Color(47, 79, 79));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(22, 43, 462, 342);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_fname = new JPanel();
		panel_fname.setBackground(new Color(255, 255, 255));
		panel_fname.setBounds(162, 76, 223, 32);
		panel.add(panel_fname);
		panel_fname.setLayout(null);
		
		JTextField tf_lname = new JTextField();
		tf_lname.setBounds(0, 0, 223, 32);
		panel_fname.add(tf_lname);
		tf_lname.setColumns(10);
		
		JPanel panel_lname = new JPanel();
		panel_lname.setBackground(new Color(255, 255, 255));
		panel_lname.setBounds(162, 163, 223, 32);
		panel.add(panel_lname);
		panel_lname.setLayout(null);
		
		tf_password = new JTextField();
		tf_password.setColumns(10);
		tf_password.setBounds(0, 0, 223, 32);
		panel_lname.add(tf_password);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFirstName.setBounds(61, 30, 103, 32);
		panel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblLastName.setBounds(61, 76, 103, 32);
		panel.add(lblLastName);
		
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnSignUp.setBounds(162, 270, 89, 23);
		panel.add(btnSignUp);
		String[] userRole = getRole();
		JComboBox<String> cb_role = new JComboBox<>(userRole);
		cb_role.insertItemAt("Select a Role", 0);
		cb_role.setSelectedIndex(0);
		cb_role.setBounds(162, 209, 223, 22);
		panel.add(cb_role);
		btnSignUp.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        //Get user input from txt fields
		    	
		    	if (tf_fname.getText().equals("") || tf_lname.getText().equals("") || tf_username.getText().equals("") || tf_password.getText().equals("")) {
		    	    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
		    	    return;
		    	} else if(cb_role.getSelectedIndex() == 0) {
		    		JOptionPane.showMessageDialog(null, "Please select a role.");
		    	    return;
		    	}

		    	 String firstName = tf_fname.getText();
			     String lastName = tf_lname.getText();
			     String username = tf_username.getText();
			     String password = tf_password.getText();
			     String role = cb_role.getSelectedItem().toString();
			     
		    	String url = "jdbc:mysql://localhost:3306/equipment_management_db";
		  	    String dbusername = "root";
		  	    String dbpassword = "09242003Believeitcovered.";
		  	    
		    	try(Connection connection = DriverManager.getConnection(url, dbusername, dbpassword)){
		    		connection.setAutoCommit(true);
		    		String getRoleIDQuery = "SELECT userRoleID FROM userRole WHERE userRoleName = '" + role + "'";
		    		PreparedStatement stmtGet = connection.prepareStatement(getRoleIDQuery);
		    		ResultSet rs = stmtGet.executeQuery();
		    		
		    		int roleID = 0;
		    		if(rs.next()) {
		    			roleID = rs.getInt("userRoleID");
		    		}
		    		System.out.println(roleID);
		    		String query = "INSERT INTO Users (User_Fname, User_Lname, UserRoleID) VALUES (?, ?, ?)";
		    		PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    		stmt.setString(1, firstName);
		    		stmt.setString(2, lastName);
		    		stmt.setInt(3, roleID);
		    		System.out.println(firstName);
		    		System.out.println(lastName);
		    		System.out.println(roleID);
		    		System.out.println(username);
		    		System.out.println(password);
		    		int rowsInserted = stmt.executeUpdate();
		    	
		    		ResultSet generatedKeys = stmt.getGeneratedKeys();
		    		int userID = -1;
		    		if(generatedKeys.next()) {
		    			userID = generatedKeys.getInt(1);
		    		}
		    	
		    	

			    
			    	
			    	String query2 = "INSERT INTO account(usersID,Username,accountPassword,accountType)VALUES(?,?,?,?)";
			    	PreparedStatement stmt2 = connection.prepareStatement(query2);
			    
			    	stmt2.setInt(1, userID);
			    	stmt2.setString(2, username);
			    	stmt2.setString(3, password);
			    	stmt2.setString(4, role);
			    	rowsInserted = stmt2.executeUpdate();
			    	
			    	System.out.println(rowsInserted);
			    
		    	} catch(SQLException ex) {
		    		System.out.println("Error be");
		    	}
		    	
		    	// Create an instance of the EquipmentRequestForm class
		        AuthenticationForm authForm = new AuthenticationForm();

		        // Make the EquipmentRequestForm visible
		        authForm.setVisible(true);

		        setVisible(false); // To hide the login form
		 
		    	
		    	    }
		});

		JLabel lblRole = new JLabel("Role:");
		lblRole.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblRole.setBounds(61, 201, 103, 32);
		panel.add(lblRole);
		
		tf_fname = new JTextField();
		tf_fname.setBounds(162, 33, 223, 32);
		panel.add(tf_fname);
		tf_fname.setColumns(10);
		
		tf_username = new JTextField();
		tf_username.setBounds(162, 119, 223, 32);
		panel.add(tf_username);
		tf_username.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblUsername.setBounds(61, 119, 103, 32);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblPassword.setBounds(61, 162, 103, 32);
		panel.add(lblPassword);
		
		
	}
	private String [] getRole() {
		String url = "jdbc:mysql://localhost:3306/equipment_management_db";
 	    String username = "root";
 	    String password = "09242003Believeitcovered.";
 	    String userRole [] = new String[4];
 	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
 	    	String query = "SELECT userRoleName FROM userRole";
 	    	PreparedStatement stmt = connection.prepareStatement(query);
 	    	ResultSet rs = stmt.executeQuery();
 	    	int counter = 0;
 	    	while(rs.next()) {
 	    		userRole[counter] = rs.getString("userRoleName");
 	    		counter++;
 	    	}
 	    } catch(SQLException ex) {
 	    	System.out.println("Can't get the roles");
 	    }
		return userRole;
	}
}
