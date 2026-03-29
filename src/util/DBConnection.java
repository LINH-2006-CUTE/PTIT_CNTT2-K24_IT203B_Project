package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/my_prj";
    private static final String USER = "root";
    private static final String PASS = "Linh190426@";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

//            if (conn != null) {
//                System.out.println("Kết nối thành công tới database!");
//            }
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy Driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối MySQL : " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection cn = getConnection();

        if (cn != null) {
            System.out.println("kết nối thành công");
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Kết nối thất bại");
        }
    }
}