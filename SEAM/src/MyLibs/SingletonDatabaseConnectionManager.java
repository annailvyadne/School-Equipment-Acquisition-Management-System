package MyLibs;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SingletonDatabaseConnectionManager {
    private static SingletonDatabaseConnectionManager instance;
    private BasicDataSource dataSource;

    private SingletonDatabaseConnectionManager() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/equipment_management_db");
        dataSource.setUsername("root");
        dataSource.setPassword("09242003Believeitcovered.");
    }

    public static synchronized SingletonDatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new SingletonDatabaseConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}