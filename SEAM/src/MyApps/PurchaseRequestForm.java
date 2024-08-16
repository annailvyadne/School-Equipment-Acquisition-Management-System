package MyApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.jdbc.Driver;

import MyLibs.EquipmentInfo;
import MyLibs.ObserverAdmin;
import MyLibs.SingletonDatabaseConnectionManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JComboBox;
import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;



import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class PurchaseRequestForm extends JFrame {

	private JPanel contentPane;
	private double total = 0;
	/**
	 * Launch the application.
	 */	
	
	//ADMIN
	private int currentUserId;
	private ObserverAdmin[] admin = new ObserverAdmin[4]; //3 admins set but can be change
	public PurchaseRequestForm(int userId) {
		
		EquipmentInfo[] equiment = new EquipmentInfo[5];
	    equiment[0] = new EquipmentInfo("Aircon", 6000, "Working");
	    equiment[1] = new EquipmentInfo("Monitor", 2000, "Working");
	    equiment[2] = new EquipmentInfo("Keyboard", 800, "Working");
	    equiment[3] = new EquipmentInfo("Table", 150, "Working");
	    equiment[4] = new EquipmentInfo("Chair", 100, "Working");
	
		SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
		   
		try (Connection connection = connectionManager.getConnection()) {
			String query = "SELECT Username FROM account";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;			
			while(rs.next()) {
				String username = rs.getString("Username");
				admin[i] = new ObserverAdmin(username, equiment);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		currentUserId = userId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		panel.setBounds(10, 23, 511, 283);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Purchase Request Form");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setBounds(28, 11, 213, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Supplier Name: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(28, 54, 111, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Address:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1_1.setBounds(28, 79, 90, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Acquire equipment");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(28, 130, 127, 14);
		panel.add(lblNewLabel_2);
		
		JButton btnCheckout = new JButton("Check out");
		
		btnCheckout.setBounds(203, 234, 111, 23);
		panel.add(btnCheckout);
		
		JCheckBox chkAircon = new JCheckBox("Aircon");
		chkAircon.setBounds(28, 151, 97, 23);
		panel.add(chkAircon);
		
		JCheckBox chkMonitor = new JCheckBox("Monitor");
		chkMonitor.setBounds(28, 184, 97, 23);
		panel.add(chkMonitor);
		
		JCheckBox chkKeyboard = new JCheckBox("Keyboard");
		chkKeyboard.setBounds(194, 151, 97, 23);
		panel.add(chkKeyboard);
		
		JCheckBox chkTable = new JCheckBox("Table");
		chkTable.setBounds(194, 184, 97, 23);
		panel.add(chkTable);
		
		JCheckBox chkChair = new JCheckBox("Chair");
		chkChair.setBounds(356, 151, 97, 23);
		panel.add(chkChair);
		
		JLabel lblNewLabel_3 = new JLabel("TechVerse Shop");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(124, 53, 98, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_2 = new JLabel("Makati City, Philippines");
		lblNewLabel_3_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_3_2.setBounds(124, 78, 130, 14);
		panel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_4 = new JLabel("PHP 6,000");
		lblNewLabel_4.setBounds(129, 155, 77, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("PHP 2,000");
		lblNewLabel_4_1.setBounds(131, 188, 77, 14);
		panel.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("PHP 800");
		lblNewLabel_4_2.setBounds(295, 155, 77, 14);
		panel.add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_4_2_1 = new JLabel("PHP 150");
		lblNewLabel_4_2_1.setBounds(297, 188, 77, 14);
		panel.add(lblNewLabel_4_2_1);
		
		JLabel lblNewLabel_4_2_1_1 = new JLabel("PHP 100");
		lblNewLabel_4_2_1_1.setBounds(459, 155, 77, 14);
		panel.add(lblNewLabel_4_2_1_1);
		
		JButton btnReceipt = new JButton("Generate Receipt");
		btnReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				try {
					SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
					   
					Connection Conn = connectionManager.getConnection();
					
					Map<String, Object> parameters = new HashMap<>();
					String reportpath = "C:/Users/Lyza/JaspersoftWorkspace/MyReports/Receipt.jrxml";
					JasperReport jasperReport =  JasperCompileManager.compileReport(reportpath);
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Conn);

					JasperViewer.viewReport(jasperPrint);				
					Conn.close(); 

				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnReceipt.setBounds(352, 234, 149, 23);
		panel.add(btnReceipt);
		
		JButton btnReturn = new JButton("Go back");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create an instance of the EquipmentRequestForm class
		        EquipmentControlSystem equipmentControlSystem = new EquipmentControlSystem(currentUserId);

		        // Make the EquipmentRequestForm visible
		        equipmentControlSystem.setVisible(true);

		        setVisible(false); // To hide the login form
			}
		});
		btnReturn.setBounds(50, 234, 89, 23);
		panel.add(btnReturn);
		
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 	JOptionPane.showMessageDialog(null, "Check out submitted");

				if(chkAircon.isSelected()) {
					insertOrder(currentUserId, 1);
					equiment[0].setCondition("Not Working");
				}
				if(chkChair.isSelected()) {
					insertOrder(currentUserId, 5) ;
					equiment[4].setCondition("Not Working");
				}
				if(chkTable.isSelected()) {
					insertOrder(currentUserId, 4);
					equiment[3].setCondition("Not Working");
				}
				if(chkMonitor.isSelected()) {
					insertOrder(currentUserId, 2);
					equiment[1].setCondition("Not Working");
				}
				if(chkKeyboard.isSelected()) {
					insertOrder(currentUserId, 3);
					equiment[2].setCondition("Not Working");
				}
				
			}
		});
	}
	
	public void insertOrder(int userID, int prodID)  {
		  
		 //Connection details
		 SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
		    
		    try(Connection connection = connectionManager.getConnection()) {
		    	
				String query = "INSERT INTO orderInfo(orderDate, UserID) VALUES(?,?)";
				PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
				stmt.setInt(2, userID);
				stmt.executeUpdate();
				
	    		ResultSet generatedKeys = stmt.getGeneratedKeys();
	    		int orderID = -1;
	    		if(generatedKeys.next()) {
	    			orderID = generatedKeys.getInt(1);
	    		}
			
				String query2 = "INSERT INTO order_has_products(orderID, productID) VALUES(?,?)";
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, orderID);
				stmt2.setInt(2, prodID);
				stmt2.executeUpdate();
		
		    } catch (SQLException ex) {  
		    	ex.printStackTrace();
		    }

	}
		//CHECK BOX PROCESS
	//FIELDS
	    private JCheckBox airconCheckBox;
	    private JCheckBox keyboardCheckBox;
	    private JCheckBox monitorCheckBox;
	    private JButton generateReportButton;
		private JCheckBox chairCheckBox;
		private JCheckBox tableCheckBox;
		private JButton checkOutBtn;
	    
	    public void EquipmentAcquisition() {
	    	airconCheckBox = new JCheckBox("Aircon");
	    	keyboardCheckBox = new JCheckBox("Keyboard");
	        monitorCheckBox = new JCheckBox("Monitor");
	        tableCheckBox = new JCheckBox("table");
	        chairCheckBox = new JCheckBox("chair");
	        
	        checkOutBtn = new JButton("Check Out");
	        
	        
	        getContentPane().add(airconCheckBox);
	        getContentPane().add(keyboardCheckBox);
	        getContentPane().add(monitorCheckBox);
	        getContentPane().add(tableCheckBox);
	        getContentPane().add(chairCheckBox);
	        getContentPane().add(generateReportButton);
	        
	        pack();
	        setLocationRelativeTo(null);	

	    }
}
