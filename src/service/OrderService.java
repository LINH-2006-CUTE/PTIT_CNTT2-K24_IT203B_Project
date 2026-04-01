package service;

import DAO.MenuItemDAO;
import DAO.OrderDAO;
import DAO.TableDAO;

import java.util.List;
import java.util.Map;

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
        orderDAO.createOrder(userID, tableID);
    }

    // thanh toans
    public double  calculateTableTotal (int tableID) {
        return orderDAO.getTotalAmountByTable(tableID);
    }
    // lấy danh sách cho chef
    // 1. Phương thức lấy danh sách cho Chef
    public List<Map<String, Object>> getPendingItems() {
        return orderDAO.getPendingItems();
    }

    // 2. Phương thức nhảy bậc trạng thái (như mình đã thảo luận)
    public void proceedStep(int detailId, String currentStatus) {
        String nextStatus = "";
        switch (currentStatus) {
            case "PENDING": nextStatus = "COOKING"; break;
            case "COOKING": nextStatus = "READY"; break;
            case "READY":   nextStatus = "SERVED"; break;
            default:
                System.out.println("Món này đã hoàn thành!");
                return;
        }

        if (orderDAO.updateDetailStatus(detailId, nextStatus)) {
            System.out.println("Cập nhật thành công sang: " + nextStatus);
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }
}