package DAO;

import model.entity.MenuItem;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuDAO {
    public boolean addMenu(MenuItem item) {
        String sql = "INSERT INTO menu_items (name, price, stock_quantity, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, item.getName());
            pre.setDouble(2, item.getPrice());
            pre.setInt(3, item.getStockQuantity());
            pre.setString(4, item.getType());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}