package presentation;

import java.util.List;
import java.util.Scanner;

import model.entity.MenuItem;
import model.entity.Table;
import model.entity.User;
import presentation.menuAll.AuthMenu;
import presentation.menuAll.CustomerMenu;
import presentation.menuAll.ManagerMenu;
import service.MenuItemService;
import service.OrderService;
import service.UserService;
import service.TableService;

import static presentation.menuAll.AuthMenu.loginAndCheckRole;
import static presentation.menuAll.ChefMenu.chefRole;
import static presentation.menuAll.ManagerMenu.managerRole;

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
                    AuthMenu.registerCustomer();
                    break;
                case 2:
                    if (loginAndCheckRole("CUSTOMER")) {
                        CustomerMenu.showCustomerMenu();
                    }
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
                    CustomerMenu.customerRole();
                    break;
                case 2:
                    ManagerMenu.managerRole();
//                    showManagerMenu();
                    break;
                case 3:
                    chefRole();
                    break;
                case 4:
                    System.out.println("Thoát chương trình");
                    break;
                default:
                    System.out.println("Vui lòng nhập lại");
            }
        } while (choice != 4);
    }

}