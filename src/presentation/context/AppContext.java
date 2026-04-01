package presentation.context;

import java.util.Scanner;
import model.entity.User;
import service.*;

public class AppContext {
    public static Scanner scanner = new Scanner(System.in);
    public static User currentUser = null;
    public static UserService userService = new UserService();
    public static MenuItemService menuService = new MenuItemService();
    public static TableService tableService = new TableService();
    public static OrderService orderService = new OrderService();
}