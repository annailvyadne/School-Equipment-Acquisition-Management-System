package MyLibs;

//This class administers EquipmentControlSystem Frame
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EquipmentManager {

    private static final String url = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String username = "yourusername";
    private static final String password = "yourpassword";

    public static List<Equipment> fetchEquipmentData() {
        List<Equipment> equipmentList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT equipment_name, equipment_type, condition, room, status FROM your_table";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String equipmentName = resultSet.getString("equipment_name");
                String equipmentType = resultSet.getString("equipment_type");
                String condition = resultSet.getString("condition");
                String room = resultSet.getString("room");
                String status = resultSet.getString("status");

                Equipment equipment = new Equipment(equipmentName, equipmentType, condition, room, status);
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }
}
	
