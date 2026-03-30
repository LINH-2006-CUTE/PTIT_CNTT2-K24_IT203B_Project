package DAO;

import util.DBConnection;

import java.sql.*;

import model.entity.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    // thêm món
    public boolean add(MenuItem item) {
        if (item.getPrice() <= 0) return false;
        String sql = "INSERT INTO menu_items (name, price, type, stock_quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getType());
            ps.setInt(4, item.getStockQuantity());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }


    // lấy số lượng trong kho
    public int getStock(int menuItemId) {
        String sql = "SELECT stock_quantity FROM menu_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, menuItemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock_quantity");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy tồn kho: " + e.getMessage());
        }
        return 0;
    }

    //  cập nhật số  lượng tồn kho
    public boolean updateStock(int menuItemId, int newQuantity) {
        String sql = "UPDATE menu_items SET stock_quantity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, menuItemId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật kho: " + e.getMessage());
            return false;
        }
    }

    // cập nhật món
    public boolean update(MenuItem item) {
        String sql = "UPDATE menu_items SET name = ?, price = ?, type = ?, stock_quantity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getType());
            ps.setInt(4, item.getStockQuantity());
            ps.setInt(5, item.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi cập nhật món ăn: " + e.getMessage());
            return false;
        }
    }

    // xóa món
    public boolean delete(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    // danh sách món ăn
    public List<MenuItem> getAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("stock_quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Loi"+ e.getMessage());
        }
        return list;
    }
}