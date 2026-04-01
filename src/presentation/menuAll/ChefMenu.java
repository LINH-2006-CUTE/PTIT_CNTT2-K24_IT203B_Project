package presentation.menuAll;

import java.util.List;
import java.util.Map;

import static presentation.context.AppContext.scanner;
import static presentation.menuAll.AuthMenu.loginAndCheckRole;
import static presentation.context.AppContext.orderService;

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
                    List<Map<String, Object>> pendingItems = orderService.getPendingItems();
                    if (pendingItems.isEmpty()) {
                        System.out.println("Hiện không có món nào cần nấu!");
                    } else {
                        System.out.printf("%-5s | %-15s | %-5s | %-10s | %-10s\n", "ID", "Món", "SL", "Bàn", "Trạng thái");
                        for (Map<String, Object> item : pendingItems) {
                            // Lưu ý: key "detail_id" phải khớp với alias AS trong DAO của Linh
                            System.out.printf("%-5d | %-15s | %-5d | %-10d | %-10s\n",
                                    (int) item.get("detail_id"), item.get("item_name"), item.get("quantity"),
                                    item.get("table_id"), item.get("status"));
                        }
                    }
                    break;

                case 2:
                    System.out.print("Nhập ID món muốn cập nhật tiến độ: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();

                    List<Map<String, Object>> currentTasks = orderService.getPendingItems();
                    String currentStatus = null;

                    for (Map<String, Object> task : currentTasks) {
                        if ((int) task.get("detail_id") == idToUpdate) {
                            currentStatus = (String) task.get("status");
                            break;
                        }
                    }

                    if (currentStatus != null) {
                        orderService.proceedStep(idToUpdate, currentStatus);
                    } else {
                        System.out.println("Không tìm thấy món có ID này trong danh sách chờ!");
                    }
                    break;

                case 3:
                    System.out.println("Thoát ");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 3);
    }
}