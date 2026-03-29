package presentation;

import java.util.List;
import java.util.Scanner;

import model.entity.Table;
import model.entity.User;
import service.MenuItemService;
import service.OrderService;
import service.UserService;
import service.TableService;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final MenuItemService menu = new MenuItemService();
    private static final TableService tableService = new TableService();
    private static User currentUser = null;
    private static final OrderService orderService = new OrderService();

    private static void showLoginMenu() {
        int choice;
        do {
            System.out.println("1. Đăng ký ");
            System.out.println("2. Đăng nhập");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thoat");
            }
        } while (choice != 3);
    }

    // nhập vai trò
    public static void main(String[] args) {
            int choice;
        do {
            System.out.println("=== HỆ THỐNG QUẢN LÝ NHÀ HÀNG ===");
            System.out.println("Chọn vai trò:");
            System.out.println("1. Khách hàng");
            System.out.println("2. Quản lý");
            System.out.println("3. Đầu bếp");
            System.out.println("4.Thoát");
            System.out.println("Nhập lựa chọn:");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    customerRole();
                    break;
                case 2:
                    managerRole();
                    break;
                case 3:
                    // vai tro nha bep
                    break;
                case 4:
                    System.out.println("Thoát chương trình");
                    break;
                default:
                    System.out.println("Vui lòng nhập lại");
            }
        } while (choice != 4);
    }

    // phân quyền
    private static boolean loginAndCheckRole(String requiredRole) {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        currentUser = userService.login(user, pass);

        if (currentUser != null) {
            if (currentUser.getRole().equalsIgnoreCase(requiredRole)) {
                System.out.println("Đăng nhập thành công");
                return true;
            } else {
                System.out.println("Lỗi: Bạn không có quyền truy cập vai trò này");
                currentUser = null;
            }
        } else {
            System.out.println("Sai thông tin đăng nhập");
        }
        return false;
    }

    // khách hàng đăng ký
    private static void registerCustomer() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Họ tên: ");
        String fullName = scanner.nextLine();

        userService.registerCustomer(username, password, fullName);
    }

    //  khách hàng đăng nhập
    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("Đăng nhập thành công! Vai trò: " + currentUser.getRole());
            if (currentUser.getRole().equals("CUSTOMER")) {
                showCustomerMenu();
            } else if (currentUser.getRole().equals("MANAGER")) {
                showManagerMenu();
            } else {
                showChefMenu();
            }
        } else {
            System.out.println("Sai thông tin");
        }
    }


    // vai trò khách hàng
    private static void customerRole() {
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
                    login();
                    break;
                case 3:
                    System.out.println("Quay lại...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        } while (customerChoice != 3);
    }

    // chức năng của Khách hàng
    private static void showCustomerMenu() {
        int choice;
        int currentTableId = -1;
        do {
            System.out.println("\n--- MENU KHÁCH HÀNG ---");
            System.out.println("1. Xem thực đơn");
            System.out.println("2. Chọn bàn");
            System.out.println("3. Gọi món");
            System.out.println("4. Đăng xuất");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("--- DANH SÁCH THỰC ĐƠN ---");
                    List<model.entity.MenuItem> items = menu.getAllMenuItems();
                    if (items.isEmpty()) {
                        System.out.println("Không có món nào ");
                    } else {
                        items.forEach(item ->
                                System.out.printf("Món: %-20s | Giá: %,.0f VNĐ", item.getName(), item.getPrice()));
                    }
                    break;

                case 2:
                    System.out.println("Danh sách bàn");
                    List<Table> tables = tableService.getAllTables();
                    for (Table t : tables) {
                        if (t.getStatus().equals("FREE")) {
                            System.out.println("ID" + t.getId() + "Số bàn:" + t.getTableNumber() + "còn chứa:" + t.getCapacity());
                        }

                    }
                    System.out.print("Nhập ID bàn muốn ngồi: ");
                    currentTableId = scanner.nextInt();
                    scanner.nextLine();


                    break;
                case 3:
                    if (currentTableId == -1) {
                        System.out.println("Phải chọn bàn thì mới được chọn món");
                    } else {
                        System.out.println("Bàn"+ currentTableId + "gọi món");
                        menu.getAllMenuItems().forEach(m -> System.out.println("ID: " + m.getId() + " - " + m.getName()));

                        System.out.print("Chọn ID món: ");
                        int menuId = scanner.nextInt();
                        System.out.print("Số lượng: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();

                        if (orderService.addFoodToOrder(currentTableId, menuId, qty)) {
                            System.out.println("Đã thêm món vào đơn hàng");
                        }
                    }
                    break;
                case 4:
                    currentUser = null;
                    System.out.println("Đăng xuất");
                    break;
                default:
                    System.out.println("Chọn lại:");
            }
        } while (choice != 4);
    }

    /// ////////////////////////////////////////////////////////////////////
    // Quản lý đăng nhập
    private static void loginManager() {
        System.out.println("id:");

    }

    // vai trò quản lý
    private static void managerRole() {
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
        System.out.println("=== Quản lý thực đơn ===");
        System.out.println("1. Xem danh sách");
        System.out.println("2. Thêm món mới");
        System.out.print("Chọn: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            menu.getAllMenuItems().forEach(item ->
                    System.out.printf("ID: %d | %s | Giá: %.2f | Kho: %d",
                            item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
        } else if (choice == 2) {
            System.out.print("Tên món: ");
            String name = scanner.nextLine();
            System.out.print("Giá: ");
            double price = scanner.nextDouble();
            System.out.print("Số lượng: ");
            int stock = scanner.nextInt();
            scanner.nextLine();
            menu.addMenuItem(name, price, "FOOD", stock);
        }
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
                for (model.entity.Table t : tables) {
                    System.out.printf("%-5d %-15d %-10d %-10s\n",
                            t.getId(), t.getTableNumber(), t.getCapacity(), t.getStatus());
                }
                System.out.println("--------------------------------------------------");
            } else if (choice == 2) {
                System.out.print("Nhập số bàn: ");
                int num = scanner.nextInt();
                System.out.print("Nhập sức chứa (người): ");
                int cap = scanner.nextInt();
                tableService.addTable(num, cap);
            } else if (choice == 3) {
                break;
            }
        }
    }

    /// /////////////////////////////////////////////////////////////////////
    // chức năng của Đầu bếp
    private static void showChefMenu() {
        System.out.println("\n=== KHU VỰC NHÀ BẾP ===");
        System.out.println("1. Danh sách món đang chờ");
    }
    // đăng nhập của đầu bếp
}