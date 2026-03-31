package presentation.menuAll;

import model.entity.User;

import static presentation.context.AppContext.scanner;
import static presentation.context.AppContext.userService;

public class AuthMenu {
    public static boolean loginAndCheckRole(String requiredRole) {
        System.out.print("Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();
        User currentUser = userService.login(user, pass);

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

    public static void registerCustomer() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Họ tên: ");
        String fullName = scanner.nextLine();

        userService.registerCustomer(username, password, fullName);
    }


}