package service;

import DAO.MenuItemDAO;
import DAO.OrderDAO;
import DAO.TableDAO;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private MenuItemDAO menuDAO = new MenuItemDAO();
    private TableDAO tableDAO = new TableDAO();
    // đặt bàn
    public boolean placeOrder(int userId, int tableId, int menuItemId, int quantity) {
        int currentStock = menuDAO.getStock(menuItemId);
        if (currentStock < quantity) {
            System.out.println("Lỗi: Kho chỉ còn " + currentStock + " sản phẩm");
            return false;
        }
        boolean created = orderDAO.createOrder(userId, tableId);

        if (created) {
            orderDAO.addItemToTableOrder(tableId, menuItemId, quantity);
            menuDAO.updateStock(menuItemId, currentStock - quantity);

            // thay đổi tràn thái bàn
            tableDAO.updateStatus(tableId, "OCCUPIED");
            return true;
        }
        return false;
    }
    public void viewOrder(int tableId) {
        orderDAO.showOrderDetailsByTable(tableId);
    }


    public boolean addFoodToOrder(int tableId, int menuItemId, int quantity, int id) {
        int currentStock = menuDAO.getStock(menuItemId);
        if (currentStock < quantity) {
            System.out.println("Lỗi: Kho không đủ món này");
            return false;
        }
        orderDAO.addItemToTableOrder(tableId, menuItemId, quantity);
        menuDAO.updateStock(menuItemId, currentStock - quantity);
        return true;
    }

    // tạo hóa đơn riêng cho từng bàn khi đã đặt
    public void createOrder(int userID, int tableID) {
        orderDAO.createOrder(userID,tableID);
    }
}