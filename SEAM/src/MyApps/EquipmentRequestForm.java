package MyApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lowagie.text.List;

import MyLibs.SingletonDatabaseConnectionManager;
import MyLibs.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EquipmentRequestForm extends JFrame {
	
	public int UserID;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	  public int getID() {
	        return this.UserID;
	    }

	    public void setID(int newID) {
	        this.UserID = newID;
	    }
	    
	    private int currentUserId;
	/**
	 * Create the frame.
	 */
	public EquipmentRequestForm(int userId) {
		//int id=AuthenticationForm.userID;
		currentUserId = userId;
		System.out.println(userId + "Pls lang ulit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 504);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(47, 79, 79));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 250));
		panel.setBounds(10, 25, 397, 429);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Equipment Request Form");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewLabel.setBounds(36, 0, 262, 62);
		panel.add(lblNewLabel);
		
		String[] eqName = fetchEquipmentName();
		JComboBox<String> cb_equipmentName = new JComboBox<>(eqName);
//		JComboBox<String> roomComboBox = new JComboBox<>(putRoomItems);
		
		cb_equipmentName.setBounds(185, 198, 153, 22);
		cb_equipmentName.insertItemAt("Select an equipment", 0);
		panel.add(cb_equipmentName);
		
		JComboBox cb_condition = new JComboBox();
		cb_condition.setModel(new DefaultComboBoxModel(new String[] {"Not Working", "Needs Repair" , "Good Condition"}));
		cb_condition.insertItemAt("Select a Condition", 0);
		cb_condition.setSelectedIndex(0);
		cb_condition.setBounds(185, 248, 153, 22);
		panel.add(cb_condition);
		
		JComboBox cb_room = new JComboBox();
		cb_room.setModel(new DefaultComboBoxModel(new String[] {"MPO301", "MPO302", "MPO303"}));
		cb_room.insertItemAt("Select a Room", 0);
		cb_room.setSelectedIndex(0);
		cb_room.setBounds(185, 297, 153, 22);
		panel.add(cb_room);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 	JOptionPane.showMessageDialog(null, "Please select an equipment");
				try {
				
					SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
					   
					Connection Conn = connectionManager.getConnection();
					
					String query = "UPDATE equipment SET equipmentCondition = ? WHERE idEquipmentID = ?";
					PreparedStatement stmt = Conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, cb_condition.getSelectedItem().toString());
					stmt.setInt(2, cb_equipmentName.getSelectedIndex() + 1);
					stmt.executeUpdate();
						
				} catch(Exception ex) {
					ex.printStackTrace();
					//JOptionPane.showMessageDialog(null, e1);
				}	
			}
		});
		btnNewButton.setBounds(120, 338, 89, 23);
		panel.add(btnNewButton);
		int userID = getID();
		System.out.println(getID() + "Haysttttt");
		System.out.println(userID +"Di makuha");
		String [] userInfo = getuserInfo(userID);
		for(int i = 0;i<userInfo.length; i++)
		{
			System.out.println(userInfo[i]);
		}
		String fname = userInfo[0];
		String lname = userInfo[1];
		String userName = userInfo[2];
		String userRoleName = userInfo[3];
		String fullname = fname + " " + lname;
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(73, 73, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Username:");
		lblNewLabel_1_1.setBounds(73, 104, 60, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Role:");
		lblNewLabel_1_1_1.setBounds(73, 135, 60, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lbl_name = new JLabel();
		lbl_name.setText(fullname);
		lbl_name.setBounds(143, 73, 131, 14);
		panel.add(lbl_name);
		
		JLabel lbl_username = new JLabel();
		lbl_username.setBounds(143, 104, 155, 14);
		lbl_username.setText(userName);
		panel.add(lbl_username);
		
		JLabel lbl_role = new JLabel("");
		lbl_role.setBounds(143, 135, 155, 14);
		lbl_role.setText(userRoleName);
		panel.add(lbl_role);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(261, 249, 1, 1);
		panel.add(desktopPane);
		
		JLabel lblNewLabel_2 = new JLabel("Equipment Name:");
		lblNewLabel_2.setBounds(36, 202, 103, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Condition:");
		lblNewLabel_3.setBounds(36, 252, 83, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Room:");
		lblNewLabel_3_1.setBounds(36, 301, 83, 14);
		panel.add(lblNewLabel_3_1);
	}

	    public String [] fetchEquipmentName() {
		String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "09242003Believeitcovered.";
	    String equipmentName[] = new String[5];
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			String query = "SELECT equipmentName FROM equipment WHERE eqStatus != 'In Storage'";
			PreparedStatement stmt = connection.prepareStatement(query);	
			ResultSet rs = stmt.executeQuery();
			
			int counter = 0;
			while(rs.next()) {
				equipmentName[counter] = rs.getString("equipmentName");
				System.out.println(equipmentName[counter]);
				counter++;
			}
		} catch (SQLException e1) {
	        e1.printStackTrace();
	    }
		
		
		return equipmentName;
		
	}
	    
    
	public void setUserData(String firstName, String lastName, String username, String role) {
		// TODO Auto-generated method stub
		
	}
	
	public String[] getuserInfo(int userID) {
		
		String[] userInfo = new String[4];
		System.out.println(userID + "Ito yong ID");
		//Connection details
		String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "09242003Believeitcovered.";
	   
	    try(Connection connection = DriverManager.getConnection(url, username, password)) {
	    	
	    	//javax.swing.JOptionPane.showMessageDialog(this, "Your user id is: " +  currentUserId);
	    	
	    	
	    	String query = "SELECT us.user_fname, us.user_lname, ac.Username, ur.userRoleName FROM users us INNER JOIN userRole ur ON(us.userRoleID = ur.userRoleID) INNER JOIN account ac ON(ac.usersID = us.userID) WHERE userID = " + currentUserId ; 
	    	PreparedStatement stmt = connection.prepareStatement(query);
//	    	stmt.setInt(1, userID);
	    	ResultSet rs = stmt.executeQuery();
	    	System.out.println("true");
	    	System.out.println(UserID + " eto");
	    	
	    	
	    	if (rs.next()) {
	    	   String fname = rs.getString("User_fname");
	    	   String lname = rs.getString("User_Lname");
	    	   String userName = rs.getString("Username");
	    	   String userrolename = rs.getString("UserRoleName");
	    	   
	    	   userInfo[0] = fname;
	    	   userInfo[1]= lname;
	    	   userInfo[2] = userName;
	    	   userInfo[3] = userrolename;
	    	   System.out.println(fname);
	    	   
	    	}
	    		
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo ;
	
	}
	
	//FUNCTION TO UPDATE DETAILS IN JASPER REPORT
	public void fetchEquipmentReport() {
		//Connection details
		String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "09242003Believeitcovered.";
	    
	    try(Connection connection = DriverManager.getConnection(url, username, password)) {
	    	String query = "SELECT idEquipmentID, equipmentName, equipmentCondition FROM equipment";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
		    	System.out.println("true");
		    	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   	
	    }
	}
	

