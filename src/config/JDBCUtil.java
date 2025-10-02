package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Nạp driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Chuỗi kết nối
            String url = "jdbc:mysql://localhost:3306/quanlikhohang?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = ""; // nếu bạn không đặt mật khẩu trong phpMyAdmin

            // Tạo kết nối
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Kết nối MySQL (phpMyAdmin) thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("🔒 Đã đóng kết nối MySQL!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
