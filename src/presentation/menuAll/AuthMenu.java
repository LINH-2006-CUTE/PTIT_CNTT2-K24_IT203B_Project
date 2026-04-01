package presentation.menuAll;

import model.entity.User;
import util.AnsiColor;

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
                presentation.context.AppContext.currentUser = currentUser;
                System.out.println(util.AnsiColor.GREEN + "Đăng nhập thành công!" + util.AnsiColor.RESET);
                return true;
            } else {
                System.out.println(util.AnsiColor.RED + "Lỗi: Bạn không có quyền truy cập vai trò này" + util.AnsiColor.RESET);
//                currentUser = null;
            }
        } else {
            System.out.println(util.AnsiColor.RED + "Sai thông tin đăng nhập" + util.AnsiColor.RESET);
        }
        return false;
    }

    public static void registerCustomer() {
        String username, password, fullName;

        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println(util.AnsiColor.RED + "Tên tài khoản không được để trống!" + util.AnsiColor.RESET);
            } else break;
        }

        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println(util.AnsiColor.RED + "Mật khẩu không được để trống!" + util.AnsiColor.RESET);
            } else break;
        }

        System.out.print("Họ tên: ");
        fullName = scanner.nextLine().trim();

        userService.registerCustomer(username, password, fullName);
    }


}