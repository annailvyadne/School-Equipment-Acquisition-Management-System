package MyApps;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import MyLibs.SingletonDatabaseConnectionManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JasperViewer;

public class EquipmentControlSystem extends JFrame {

    private JPanel contentPane;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;

    
    //LAUNCH THE APPLICATION
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EquipmentControlSystem frame = new EquipmentControlSystem();
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
    private int currentUserId;
	public EquipmentControlSystem(int userId) {
		currentUserId = userId;
		initControlsAndEvent();		
	}

	public EquipmentControlSystem() {
		initControlsAndEvent();
	}
	private void initControlsAndEvent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Create the table model
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Equipment Name");
		tableModel.addColumn("Equipment Type");
		tableModel.addColumn("Condition");
		tableModel.addColumn("Room");
		tableModel.addColumn("Status");
		
		equipmentTable = new JTable(tableModel);
		
		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(equipmentTable);
		scrollPane.setBounds(27, 187, 466, 100);
		contentPane.add(scrollPane);

		// Fetch and populate data from the database
		fetchAndPopulateData();

		// "Equipment Condition Report" button
		JButton btnReports = new JButton("Equipment Condition Report");
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				try {
					SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
					   
					Connection Conn = connectionManager.getConnection();
					
					Map<String, Object> parameters = new HashMap<>();
					String reportpath = "C:/Users/Lyza/JaspersoftWorkspace/MyReports/Condition_Report.jrxml";
					JasperReport jasperReport =  JasperCompileManager.compileReport(reportpath);
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Conn);

					JasperViewer.viewReport(jasperPrint);				
					Conn.close(); 

				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnReports.setBounds(17, 22, 236, 23);
		contentPane.add(btnReports);

        addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
            	fetchAndPopulateData();
                System.out.println("Frame is loaded.");
            }
        });

	
		JButton btnNewButton = new JButton("Acquire from Suppliers");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			        PurchaseRequestForm prf = new PurchaseRequestForm(currentUserId);

			        prf.setVisible(true);

			        setVisible(false); 
			}
		});
		btnNewButton.setBounds(17, 56, 236, 23);
		contentPane.add(btnNewButton);
		
		JButton btnReports1 = new JButton("Purchase Equipment Report");
		btnReports1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();
					   
					Connection Conn = connectionManager.getConnection();
					
					Map<String, Object> parameters = new HashMap<>();
					String reportpath = "C:/Users/Lyza/JaspersoftWorkspace/MyReports/Purchase_Report.jrxml";
					JasperReport jasperReport =  JasperCompileManager.compileReport(reportpath);
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Conn);

					JasperViewer.viewReport(jasperPrint);				
					

				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		
		btnReports1.setBounds(269, 56, 225, 23);
		contentPane.add(btnReports1);
		
		JButton btnTransactionReport = new JButton("Transaction Report");
		btnTransactionReport.setBounds(269, 90, 225, 23);
		contentPane.add(btnTransactionReport);
		
		JButton btnEquipmentAssignmentTo = new JButton("Equipment Assignment to Room");
		btnEquipmentAssignmentTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EquipmentAssignmentToRoom equipmentToRoom = new EquipmentAssignmentToRoom(currentUserId);
				
				equipmentToRoom.setVisible(true);
				dispose();
			}
		});
		

		btnEquipmentAssignmentTo.setBounds(17, 90, 236, 23);
		contentPane.add(btnEquipmentAssignmentTo);
		
		JButton btnViewUsersAccounts = new JButton("View Users Accounts");
		btnViewUsersAccounts.addActionListener(new ActionListener() {
			private ResultSet resultSet;
			public void actionPerformed(ActionEvent e) {

		        ViewUsersAccount vua = new ViewUsersAccount();

		        vua.setVisible(true);

		        setVisible(false); 
			}
			

		});
		
		btnViewUsersAccounts.setBounds(269, 22, 225, 23);
		contentPane.add(btnViewUsersAccounts);
		
		JLabel lblNewLabel = new JLabel("Currently assigned");
		lblNewLabel.setBounds(27, 173, 111, 14);
		contentPane.add(lblNewLabel);
		
		JButton btn_Reset = new JButton("Reset");
		btn_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetData();
			}
		});
		btn_Reset.setBounds(404, 289, 89, 23);
		contentPane.add(btn_Reset);
	}
	

	private void resetData() {
	    // Database connection details
	    String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "09242003Believeitcovered.";

	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	        // Delete existing data from EquipmentAssignmentToRoom table
	    	String deleteQuery = "DELETE FROM Equipment_has_room";
	        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
	        deleteStatement.executeUpdate(); 
	        // Commit the changes
	        
	        String updateQuery = "UPDATE equipment SET eqStatus = 'In Storage'";
	        PreparedStatement stmt = connection.prepareStatement(updateQuery);
	        stmt.executeUpdate();
	        tableModel.setRowCount(0);
	   
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        // Handle any database-related errors
	    }
	}
	

	public void fetchAndPopulateData() {
	    // Database connection details

	    String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "09242003Believeitcovered.";

	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	    	String query = "SELECT eq.equipmentName, eq.equipmentType, eq.Equipmentcondition, r.RoomName, eq.eqStatus " +
	                "FROM Equipment eq " +
	                "INNER JOIN equipment_has_room ehr ON ehr.idequipmentID = eq.idequipmentID " +
	                "INNER JOIN room r ON r.roomID = ehr.Room_RoomID";

	        System.out.println("Connected Successfully");
	        
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        // Clear any existing data from the table
	        tableModel.setRowCount(0);

	        while (resultSet.next()) {
	        	System.out.println("Success");
	
	            String equipmentName = resultSet.getString("equipmentName");
	            String equipmentType = resultSet.getString("equipmentType");
	            String condition = resultSet.getString("equipmentCondition");
	            String roomName = resultSet.getString("roomName");
	            String eqStatus = resultSet.getString("eqStatus");
	            // Add the retrieved data to the JTable
	            tableModel.addRow(new Object[]{equipmentName, equipmentType, condition,roomName, eqStatus});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	

}

	
	
	

