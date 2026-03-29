package DAO;

import util.DBConnection;

import java.sql.*;

public class OrderDAO {
    // tạo đơn hàng
    public boolean createOrder(int userId, int tableId) {
        String sql = "INSERT INTO orders (user_id, table_id, status) VALUES (?, ?, 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, tableId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // lưu món ăn đã đặt
    public boolean saveOrderDetail(int orderId, int menuItemId, int quantity) {
        String sql = "INSERT INTO order_details (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // cho món vào đúng  đơn đã tạo
    public void addItemToTableOrder(int tableId, int menuId, int qty) {
        String sql = "INSERT INTO order_details (order_id, menu_item_id, quantity) " +
                "SELECT id, ?, ? FROM orders WHERE table_id = ? AND status = 'PENDING' LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ps.setInt(2, qty);
            ps.setInt(3, tableId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi"+ e.getMessage());
        }
    }
    //
}
