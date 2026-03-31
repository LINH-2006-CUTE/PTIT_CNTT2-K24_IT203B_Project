package service;

import java.util.Scanner;

import DAO.MenuItemDAO;
import model.entity.MenuItem;
import java.util.List;

public class MenuItemService {
    private final MenuItemDAO menuItemDAO = new MenuItemDAO();

    // danh sách món ăn
    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.getAll();
    }

    // Thêm món ăn
    public void addMenuItem(String name, double price, String type, int stock) {
        if (name == null || name.isEmpty()) {
            System.out.println("Tên món không được để trống");
        }
        if (price <= 0) {
            System.out.println("Giá tiền phải là số dương");
            return;
        }

        MenuItem item = new MenuItem(name, price, type, stock);
        if (menuItemDAO.add(item)) {
            System.out.println("Thêm món thành công");
        } else {
            System.out.println("Thêm món thất bại");
        }
    }
    // update món ăn
    public boolean updateMenuItem(int id, String name, double price, int stock) {
        if (name == null || name.isEmpty() || price <= 0) {
            return false;
        }
        MenuItem item = new MenuItem(id, name, price, "FOOD", stock);
//        System.out.println("Cập nhật món ăn thành công");
        return menuItemDAO.update(item);
    }

    // xoas mon
    public void deleteMenuItem(int id) {
        if (menuItemDAO.delete(id)) {
            System.out.println("Đã xóa món ăn");
        } else {
            System.out.println("Món đang được gọi");
        }
    }
}