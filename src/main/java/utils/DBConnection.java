package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Ban co the thay doi cac thong so ket noi CSDL o day:
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "apphoctu";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Nhap password MySQL cua ban vao day

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {
        try {
            // Dang ky JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver class not found: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
