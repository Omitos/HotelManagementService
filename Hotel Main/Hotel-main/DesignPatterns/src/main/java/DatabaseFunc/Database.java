package DatabaseFunc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String connStr = "jdbc:postgresql://localhost:5432/DesignPatterns";
    private static Database instance = null;
    private Connection connection = null;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("JDBC Driver has been registered");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try {
            connection = DriverManager.getConnection(connStr, "postgres", "Aspan3012+");
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e);
        }
        return connection;
    }

    public void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void dbExecuteQuery(String sqlSmt) throws SQLException {
        Statement statement = null;
        try {
            getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sqlSmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred");
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
    }
}
