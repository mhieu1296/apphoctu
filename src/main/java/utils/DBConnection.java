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

    private static boolean isInitialized = false;

    public static Connection getConnection() throws SQLException {
        try {
            // Dang ky JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver class not found: " + e.getMessage());
        }

        if (!isInitialized) {
            initializeDatabase();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static synchronized void initializeDatabase() {
        if (isInitialized) return;
        
        String baseUrl = "jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        
        // 1. Tao database neu chua co
        try (Connection conn = DriverManager.getConnection(baseUrl, USER, PASSWORD);
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS `" + DATABASE + "` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        } catch (SQLException e) {
            System.err.println("[DB Init] Khong the ket noi de tao database: " + e.getMessage());
            return;
        }

        // 2. Kiem tra xem cac bang da ton tai chua
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean tableExists = false;
            try (java.sql.ResultSet rs = conn.getMetaData().getTables(null, null, "tai_khoan", null)) {
                if (rs.next()) {
                    tableExists = true;
                }
            }
            
            // Neu bang tai_khoan chua ton tai, tien hanh import file schema.sql
            if (!tableExists) {
                java.io.File schemaFile = new java.io.File("database/schema.sql");
                if (!schemaFile.exists()) {
                    System.err.println("[DB Init] Canh bao: Khong tim thay file database/schema.sql de khoi tao du lieu!");
                    isInitialized = true;
                    return;
                }
                
                StringBuilder sb = new StringBuilder();
                try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(schemaFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().startsWith("--") || line.trim().startsWith("#") || line.trim().isEmpty()) {
                            continue;
                        }
                        sb.append(line).append("\n");
                    }
                } catch (java.io.IOException e) {
                    System.err.println("[DB Init] Loi khi doc file schema.sql: " + e.getMessage());
                    return;
                }
                
                String[] sqlStatements = sb.toString().split(";");
                try (java.sql.Statement stmt = conn.createStatement()) {
                    for (String sql : sqlStatements) {
                        String trimmed = sql.trim();
                        if (!trimmed.isEmpty()) {
                            stmt.execute(trimmed);
                        }
                    }
                    System.out.println("[DB Init] Tu dong khoi tao database va nap du lieu mau thanh cong!");
                }
            }
            isInitialized = true;
        } catch (SQLException e) {
            System.err.println("[DB Init] Loi khi khoi tao cac bang: " + e.getMessage());
        }
    }
}
