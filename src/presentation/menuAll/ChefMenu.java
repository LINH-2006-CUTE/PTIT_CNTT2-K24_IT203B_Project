package presentation.menuAll;

import static presentation.context.AppContext.scanner;
import static presentation.menuAll.AuthMenu.loginAndCheckRole;

public class ChefMenu {
    public static void chefRole() {
        System.out.println("\n=== ĐĂNG NHẬP ĐẦU BẾP ===");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Quay lại");
        System.out.print("Chọn: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            if (loginAndCheckRole("CHEF")) {
                showChefMenu();
            }
        }
    }

    // chức năng của Đầu bếp
    public static final service.ChefService chefService = new service.ChefService();

    private static void showChefMenu() {
        int choice;
        do {
            System.out.println("\n=== KHU VỰC NHÀ BẾP ===");
            System.out.println("1. Xem danh sách món cần nấu");
            System.out.println("2. Cập nhật trạng thái món");
            System.out.println("3. Đăng xuất");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("qbc");
                    break;
                case 2:
                    System.out.println("abc");
                    break;
            }
        } while (choice != 3);
    }
}
