package service;

import DAO.UserDAO;
import model.entity.User;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean registerCustomer(String username, String password, String fullName) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Tài khoản và mật khẩu không được để trống");
            return false;
        }

//        if (userDAO.register(username, password, fullName)) {
//            System.out.println("Đăng ký thành công: " + fullName);
//        } else {
//            System.out.println("Đăng ký thất bại");
//        }
        // ktra trùng
        if (userDAO.isUsernameExists(username)) {
            System.out.println("Lỗi: Tên đăng nhập '" + username + "' đã tồn tại!");
            return false;
        }
        if (userDAO.register(username, password, fullName)) {
            System.out.println("Đăng ký thành công khách hàng: " + fullName);
            return true;
        } else {
            System.out.println("Đăng ký thất bại");
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