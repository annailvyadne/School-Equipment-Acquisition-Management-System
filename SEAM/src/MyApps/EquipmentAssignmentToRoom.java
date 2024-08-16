package MyApps;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MyLibs.SingletonDatabaseConnectionManager;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EquipmentAssignmentToRoom extends JFrame {
    private JPanel contentPane;

    private int currentUserId;
    public EquipmentAssignmentToRoom(int userId) {
    	currentUserId = userId;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Use a layout manager or adjust the layout as needed
        
        // Equipment list 
        String[] putEquipmentItems = fetchEquipment();
        JComboBox<String> equipmentComboBox = new JComboBox<>(putEquipmentItems);
        equipmentComboBox.insertItemAt("Select an equipment", 0);
        equipmentComboBox.setSelectedIndex(0);
        equipmentComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selectedOption = (String) equipmentComboBox.getSelectedItem();
        	}
        });
        
        equipmentComboBox.setBounds(140, 50, 150, 30);
        contentPane.add(equipmentComboBox);
        String putRoomItems[] = fetchRoom();
        
        JComboBox<String> roomComboBox = new JComboBox<>(putRoomItems);
        roomComboBox.insertItemAt("Select an item",0);
        roomComboBox.setSelectedIndex(0);
        roomComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selectedRoom = (String) roomComboBox.getSelectedItem();
        	}
        });
        
        roomComboBox.setBounds(140, 100, 150, 30);
        contentPane.add(roomComboBox);

        JButton assignButton = new JButton("Assign Equipment");
        assignButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
                
        		EquipmentControlSystem EquipmentSystem = new EquipmentControlSystem(currentUserId);
        		EquipmentSystem.setVisible(true);
        		dispose();
        		
        	}
        });
        assignButton.setBounds(140, 150, 150, 30);
        contentPane.add(assignButton);

        JLabel equipmentLabel = new JLabel("Select Equipment:");
        equipmentLabel.setBounds(30, 50, 100, 30);
        contentPane.add(equipmentLabel);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setBounds(30, 100, 100, 30);
        contentPane.add(roomLabel);
        
        JLabel lblNewLabel = new JLabel("Equipment Assignment to Room");
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNewLabel.setBounds(10, 11, 269, 14);
        contentPane.add(lblNewLabel);
        
        JButton btn_GoBack = new JButton("Go back");
        btn_GoBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Create an instance of the EquipmentRequestForm class
		        EquipmentControlSystem equipmentControlSystem = new EquipmentControlSystem(currentUserId);

		        // Make the EquipmentRequestForm visible
		        equipmentControlSystem.setVisible(true);

		        setVisible(false); // To hide the login form

        	}
        });
        btn_GoBack.setBounds(30, 199, 89, 23);
        contentPane.add(btn_GoBack);

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(equipmentComboBox.getSelectedIndex() == 0) {
                	JOptionPane.showMessageDialog(null, "Please select an equipment");
                	
                } else if(roomComboBox.getSelectedIndex() == 0) {
                	JOptionPane.showMessageDialog(null, "Please select a room");
                }
                    
            	//SINGLETON DESIGN
        	    SingletonDatabaseConnectionManager connectionManager = SingletonDatabaseConnectionManager.getInstance();

        	    try (Connection connection = connectionManager.getConnection()) {
        	    	String selectedEquipment =(String) equipmentComboBox.getSelectedItem();
        	    	String selectedRoom = (String) roomComboBox.getSelectedItem();
        	    	System.out.println(selectedEquipment);
        	    	System.out.println(selectedRoom);
        	    	
        	    	String query = "SELECT idEquipmentID FROM equipment WHERE equipmentName = '" + selectedEquipment + "'";
        	    	
        	    	PreparedStatement Statement = connection.prepareStatement(query);
        	    	
        	    	int equipmentID = 0;
        	    	ResultSet rs = Statement.executeQuery();
        	    	if(rs.next()) {
        	    		equipmentID = rs.getInt("idEquipmentID");
        	    	}
        	    	System.out.println(equipmentID);
        	    	String query2 = "SELECT roomID FROM room WHERE roomName='" + selectedRoom +"'";
        	    	PreparedStatement Statement2 = connection.prepareStatement(query2);
        	    	ResultSet rs2 = Statement2.executeQuery();
        	    	
        	    	int roomID = 0;
        	    	if(rs2.next()) {
        	    		roomID = rs2.getInt("roomID");
        	    	}
        	    	
        	    	System.out.println(roomID);
        	    	String query3 = "INSERT INTO equipment_has_room(idEquipmentID, Room_RoomID) VALUES("+ equipmentID + "," + roomID + ")";
        	    	System.out.println(query3);
        	    	PreparedStatement stmt3 = connection.prepareStatement(query3);
        	    	 stmt3.executeUpdate();
        	    	 
        	    	String query4 ="UPDATE equipment SET eqStatus ='In Used' WHERE idequipmentID = "+ equipmentID;
        	    	PreparedStatement stmt4 = connection.prepareStatement(query4);
        	    	stmt4.executeUpdate();
        	    	
        	    	
        	    } catch (SQLException e1) {
        	        e1.printStackTrace();
        	    }
            }
        });
    }
 
    
    private String[] fetchEquipment() {
	    // Database connection details

	    String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "123";
	    String EquipmentName[] = new String[5];
	   
	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	       String query = "SELECT equipmentName FROM equipment WHERE eqStatus = 'In Storage'";
	        
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	        int counter = 0;
	        while (resultSet.next()) {
	        	System.out.println("Success");
	            String equipmentName = resultSet.getString("equipmentName");
	            EquipmentName[counter] = equipmentName;
	        
	            counter++;
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return EquipmentName;
	}
    
    //FUNCTION FOR ROOMS
    private String[] fetchRoom() {
	    // Database connection details

	    String url = "jdbc:mysql://localhost:3306/equipment_management_db";
	    String username = "root";
	    String password = "123";
	    String RoomName[] = new String[3];
	   
	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	        String query = "SELECT RoomName FROM Room";
	        System.out.println("Connected Successfully");
	        
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	        int counter = 0;
	
	        while (resultSet.next()) {
	        	System.out.println("Success");
	            String AssignedRoomName = resultSet.getString("RoomName");
	            RoomName[counter] = AssignedRoomName;
	        
	            counter++;
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return RoomName;
	}
}
