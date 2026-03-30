package DAO;

import model.entity.User;
import util.DBConnection;
import util.PasswordHasher;

import java.sql.*;

public class UserDAO {
    // kiểm tra người dùng tồn tạị chưa
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi"+ e.getMessage());
        }
        return false;
    }

    // đăng ký
    public boolean register(String username, String password, String fullName) {
        String sql = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, 'CUSTOMER')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, PasswordHasher.hashPassword(password));
            ps.setString(3, fullName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi "+ e.getMessage());
            return false;
        }
    }

    // đăng nhập
    public User login(String username, String password) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("username");
                    String name = rs.getString("full_name");
                    String role = rs.getString("role");

                    return new User(id, user, name, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi"+ e.getMessage());
        }
        return null;
    }
}