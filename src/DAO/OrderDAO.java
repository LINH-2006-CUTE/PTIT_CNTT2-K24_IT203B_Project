package DAO;

import util.DBConnection;

import java.sql.*;
import java.util.*;

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
            System.out.println("Lỗi" + e.getMessage());
        }
    }

    // xem bàn đã gọi món nào
    public void showOrderDetailsByTable(int tableId) {
        String sql = "SELECT m.name, od.quantity, od.status " +
                "FROM order_details od " +
                "JOIN menu_items m ON od.menu_item_id = m.id " +
                "WHERE od.order_id = (SELECT id FROM orders WHERE table_id = ? AND status = 'PENDING' LIMIT 1)";

        try (Connection conn = util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- DANH SÁCH MÓN ĐÃ ĐẶT (BÀN " + tableId + ") ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("- %-20s | SL: %-2d | Trạng thái: [%s]\n",
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("status"));
            }
            if (!hasData) {
                System.out.println("Bàn này hiện chưa có món nào trong danh sách!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi hiển thị: " + e.getMessage());
        }
    }

    /// tính tổng tiền món đã order
    public double getTotalAmountByTable(int tableId) {
        String sql = "SELECT SUM(mi.price * oi.quantity) as total " +
                "FROM orders o " +
                "JOIN order_items oi ON o.id = oi.order_id " +
                "JOIN menu_items mi ON oi.menu_item_id = mi.id " +
                "WHERE o.table_id = ? AND o.status = 'PENDING'";
        // tính những order chưa thanh toán
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tính tổng tiền: " + e.getMessage());
        }
        return 0.0;
    }


    // lấy các món đang pending hoặc cooking để đầu bếp biết mà làm
    public List<Map<String, Object>> getPendingItems() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT od.id AS detail_id, od.order_id, mi.name, od.quantity, od.status, o.table_id " +
                "FROM order_details od " +
                "JOIN menu_items mi ON od.menu_item_id = mi.id " +
                "JOIN orders o ON od.order_id = o.id " +
                "WHERE od.status IN ('PENDING', 'COOKING') " +
                "ORDER BY o.id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("detail_id", rs.getInt("detail_id"));
                item.put("order_id", rs.getInt("order_id"));
                item.put("item_name", rs.getString("name"));
                item.put("quantity", rs.getInt("quantity"));
                item.put("status", rs.getString("status"));
                item.put("table_id", rs.getInt("table_id"));
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi Chef truy vấn: " + e.getMessage());
        }
        return list;
    }

    public boolean updateDetailStatus(int detailId, String nextStatus) {
        String sql = "UPDATE order_details SET status = ? WHERE id = ?";
        try (Connection conn = util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nextStatus);
            ps.setInt(2, detailId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}