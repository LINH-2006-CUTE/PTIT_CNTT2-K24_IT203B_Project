package DAO;

import model.entity.Table;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    // thêm bàn mới
    public boolean add(Table table) {
        String sql = "INSERT INTO tables (table_number, capacity) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, table.getTableNumber());
            ps.setInt(2, table.getCapacity());
            return ps.executeUpdate() > 0;
        } catch (
                SQLException e) {
            return false;
        }
    }

    // cập nhật  trạng thái bàn
    public boolean updateStatus(int tableId, String newStatus) {
        String sql = "UPDATE tables SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, tableId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // danh sách bàn
    public List<Table> getAll() {
        List<Table> list = new
                ArrayList<>();
        String sql = "SELECT * FROM tables";
        try (Connection conn = DBConnection.getConnection();

             Statement st = conn.createStatement();

             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Table(rs.getInt("id"), rs.getInt("table_number"),
                        rs.getInt("capacity"), rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi" + e.getMessage());
        }
        return list;
    }

    // kiểm tra bàn trống
    public boolean isTableFree(int tableId) {
        String sql = "SELECT status FROM tables WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "FREE".equalsIgnoreCase(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi check bàn: " + e.getMessage());
        }
        return false;
    }
}