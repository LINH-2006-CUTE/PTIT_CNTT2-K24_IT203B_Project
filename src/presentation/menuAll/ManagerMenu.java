package presentation.menuAll;

import model.entity.Table;

import java.util.List;

import util.AnsiColor;

import static presentation.context.AppContext.*;
import static presentation.menuAll.AuthMenu.loginAndCheckRole;

public class ManagerMenu {

    // vai trò quản lý
    public static void managerRole() {
        System.out.println("===Quản lý ===");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Thoát");
        System.out.println("Chọn:");
        int managerChoice = scanner.nextInt();
        scanner.nextLine();
        if (managerChoice == 1) {
            if (loginAndCheckRole("MANAGER")) {
                showManagerMenu();
            }
        } else {
            System.out.println("Thoát chương trình");
        }
    }

    // chức năng của Quản lý
    private static void showManagerMenu() {
        int choice;
        do {
            System.out.println("1. Quản lý Thực đơn");
            System.out.println("2. Quản lý Bàn ăn");
            System.out.println("3. Đăng xuất");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manageMenuItems();
                    break;
                case 2:
                    manageTables();
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("Đăng xuất");
            }
        } while (choice != 3);
    }

    // Quản lý Menu của Quản lý
    private static void manageMenuItems() {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ THỰC ĐƠN (CRUD) ===");
            System.out.println("1. Xem danh sách món");
            System.out.println("2. Thêm món mới");
            System.out.println("3. Cập nhật thông tin món");
            System.out.println("4. Xóa món");
            System.out.println("5. Quay lại");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Danh sách món ăn:");
                    menuService.getAllMenuItems().forEach(item ->
                            System.out.printf("ID: %-3d | %-20s | Giá: %,.0f | Kho: %d\n",
                                    item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
                    break;
                case 2:
                    System.out.println("\n--- THÊM MÓN MỚI ---");
                    System.out.print("Tên món: ");
                    String name = scanner.nextLine();

                    System.out.print("Giá tiền: ");
                    double price = scanner.nextDouble();

                    System.out.print("Số lượng nhập kho: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();

                    menuService.addMenuItem(name, price, "FOOD", stock);
                    break;

                case 3:
                    System.out.println("--- Cập nhật món ăn ---");
                    System.out.println("Nhập ID món ăn muốn cập nhật:");
                    int up = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();

                    System.out.print("Nhập giá mới: ");
                    double newPrice = scanner.nextDouble();

                    System.out.print("Nhập số lượng kho mới: ");
                    int newStock = scanner.nextInt();
                    scanner.nextLine();
                    if (menuService.updateMenuItem(up, newName, newPrice, newStock)) {
                        System.out.println(AnsiColor.GREEN + "Cập nhật món ăn thành công" + AnsiColor.RESET);
                    } else {
                        System.out.println("Không  tìm thấy ID món");
                    }
                    break;

                case 4:
                    System.out.println("\n--- XÓA MÓN ĂN ---");
                    System.out.print("Nhập ID món muốn xóa: ");
                    int idDel = scanner.nextInt();
                    scanner.nextLine();
                    menuService.deleteMenuItem(idDel);
                    break;

                case 5:
                    System.out.println("Thoát");
                default:
                    System.out.println("Nhập lại");
            }
        } while (choice != 5);
    }

    // Quản lý bàn của Quản lý
    private static void manageTables() {
        while (true) {
            System.out.println("--- QUẢN LÝ BÀN ĂN ---");
            System.out.println("1. Xem danh sách bàn");
            System.out.println("2. Thêm bàn mới");
            System.out.println("3. Quay lại");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                List<Table> tables = tableService.getAllTables();
                System.out.println("--------------------------------------------------");
                System.out.printf("%-5s %-15s %-10s %-10s\n", "ID", "Số Bàn", "Sức Chứa", "Trạng Thái");
                for (model.entity.Table table : tables) {
                    System.out.printf("%-5d %-15d %-10d %-10s\n",
                            table.getId(), table.getTableNumber(), table.getCapacity(), table.getStatus());
                }
                System.out.println("--------------------------------------------------");
            } else if (choice == 2) {
                System.out.print("Nhập số bàn: ");
                int num = scanner.nextInt();
                System.out.print("Nhập số người: ");
                int cap = scanner.nextInt();
                tableService.addTable(num, cap);
            } else if (choice == 3) {
                break;
            }
        }
    }
}
