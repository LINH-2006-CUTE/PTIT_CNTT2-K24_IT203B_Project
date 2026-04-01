package service;

import DAO.UserDAO;
import model.entity.User;
import util.AnsiColor;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean registerCustomer(String username, String password, String fullName) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println(AnsiColor.RED + "Lỗi: Tên tài khoản không được để trống!" + AnsiColor.RESET);
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println(AnsiColor.RED + "Mật khẩu không được để trống" + AnsiColor.RESET);
            return  false;
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            System.out.println(AnsiColor.RED + "Lỗi: Họ tên không được để trống!" + AnsiColor.RESET);
            return false;
        }
        // ktra trùng
        if (userDAO.isUsernameExists(username)) {
            System.out.println(AnsiColor.YELLOW + "Lỗi: Tên đăng nhập '" + username + "' đã tồn tại!" + AnsiColor.RESET);
            return false;
        }
        if (userDAO.register(username, password, fullName)) {
            System.out.println(AnsiColor.GREEN + "Đăng ký thành công khách hàng: " + fullName + AnsiColor.RESET);
            return true;
        } else {
            System.out.println(AnsiColor.RED + "Hệ thống gặp sự cố, đăng ký thất bại!" + AnsiColor.RESET);
            return false;
        }
    }

    public User login(String username, String password) {
        User user = userDAO.login(username, password);
        if (user != null) {
            return user;
        }
        return null;
    }
}