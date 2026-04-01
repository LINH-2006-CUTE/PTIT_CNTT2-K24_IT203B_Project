package presentation.menuAll;

import model.entity.MenuItem;
import model.entity.Table;
import util.AnsiColor;

import java.util.List;

import static java.awt.SystemColor.menu;
import static presentation.context.AppContext.*;
import static presentation.menuAll.AuthMenu.loginAndCheckRole;
import static presentation.menuAll.AuthMenu.registerCustomer;

public class CustomerMenu {
    // vai trò cuar khách hàng
    public static void customerRole() {
        int customerChoice;
        do {
            System.out.println("\n=== KHÁCH HÀNG ===");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("3. Quay lại Menu chính");
            System.out.print("Chọn: ");
            customerChoice = scanner.nextInt();
            scanner.nextLine();

            switch (customerChoice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    if (loginAndCheckRole("CUSTOMER")) {
                        showCustomerMenu();
                    }
                    break;
                case 3:
                    System.out.println("Quay lại");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        } while (customerChoice != 3);
    }

    // chức năng của khách hàng
    public static void showCustomerMenu() {
        int choice;
        int currentTableId = -1;
//        int currentOrderId = -1;
        do {
            System.out.println("\n--- MENU KHÁCH HÀNG ---");
            System.out.println("1. Xem thực đơn");
            System.out.println("2. Chọn bàn");
            System.out.println("3. Gọi món");
            System.out.println("4. Xem trạng thái món đã đặt");
            System.out.println("5.Thanh toán hóa đơn");
            System.out.println("6. Đăng xuất");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("--- DANH SÁCH THỰC ĐƠN ---");
                    List<MenuItem> items = menuService.getAllMenuItems();
                    if (items.isEmpty()) {
                        System.out.println("Không có món nào ");
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.printf("| %-5s | %-25s | %-12s |\n", "ID", "Tên món", "Giá ");
                        System.out.println("--------------------------------------------------");
                        for (MenuItem item : items) {
                            System.out.printf("| %-5d | %-30s | %-10f |\n",
                                    item.getId(),
                                    item.getName(),
                                    item.getPrice());
                        }
                    }
                    break;

                case 2:
                    List<Table> tables = tableService.getAllTables();
                    boolean foundFree = false;
                    System.out.println("\n--- DANH SÁCH BÀN TRỐNG ---");
                    System.out.println("---------------------------------------------");
                    System.out.printf("| %-5s | %-12s | %-15s |\n", "ID", "Số Bàn", "Sức Chứa");
                    System.out.println("---------------------------------------------");
                    for (Table t : tables) {
                        if (t.getStatus().equals("FREE")) {
                            System.out.printf("| %-5d | %-12s | %-15d |\n",
                                    t.getId(),
                                    t.getTableNumber(),
                                    t.getCapacity());
                            foundFree = true;
                        }
                        System.out.println("---------------------------------------------");
                    }
                    if (!foundFree) {
                        System.out.println(AnsiColor.RED + "Hiện tại nhà hàng đã hết bàn trống!" + AnsiColor.RESET);

                    } else {
                        System.out.print("Nhập số bàn muốn ngồi: ");
                        currentTableId = scanner.nextInt();
                        scanner.nextLine();
                        if (tableService.isTableFree(currentTableId)) {
                            tableService.updateStatus(currentTableId, "OCCUPIED");
                            orderService.createOrder(currentUser.getId(), currentTableId);
                            System.out.println("Đã chọn bàn " + currentTableId + " thành công");
                        } else {
                            System.out.println("Không đặt được bàn");
                            currentTableId = -1;
                        }
                    }

                    break;
                case 3:
                    if (currentTableId == -1) {
                        System.out.println("Phải chọn bàn thì mới được chọn món");
                    } else {
                        System.out.println("Bàn" + currentTableId + "gọi món");
                        List<MenuItem> itemList = menuService.getAllMenuItems();
                        for (MenuItem m : itemList) {
                            System.out.printf("ID: %-3d | %-20s | Giá: %,.0f VNĐ\n",
                                    m.getId(), m.getName(), m.getPrice());
                        }

                        System.out.print("Chọn ID món: ");
                        int menuId = scanner.nextInt();
                        System.out.print("Số lượng: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();

                        if (orderService.addFoodToOrder(currentTableId, menuId, qty, currentUser.getId())) {
                            System.out.println("Đã thêm món vào đơn hàng");
                        } else {
                            System.out.println("Không thể thêm món");
                        }
                    }
                    break;
                case 4:
                    if (currentTableId == -1) {
                        System.out.println("Bạn chưa chọn bàn");
                    } else {
                        System.out.println("\n--- Đã chọn bàn: " + currentTableId + " ---");
                        orderService.viewOrder(currentTableId);
                    }
                    break;
                case 5:
                    if (currentTableId == -1) {
                        System.out.println(AnsiColor.RED + "Bạn chưa chọn bàn!!" + AnsiColor.RESET);
                    } else {
                        double total = orderService.calculateTableTotal(currentTableId);
                        System.out.println("--------------------------------");
                        System.out.printf("TỔNG CỘNG: %,.0f VNĐ\n", total);
                        System.out.println("--------------------------------");
                    }
                    break;
                case 6:
                    currentUser = null;
                    System.out.println("Đăng xuất");
                    break;
                default:
                    System.out.println("Chọn lại:");
            }
        } while (choice != 6);
    }
}
