package co.edu.udec.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=7502410050_X_biblioteca_v2;encrypt=false";
    private static final String USER = "SA";
    private static final String PASSWORD = "$>5l1d^1NEQf";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexión exitosa a SQL Server: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}
