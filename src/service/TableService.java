package service;

import DAO.TableDAO;
import model.entity.Table;

import java.util.List;

public class TableService {
    private final TableDAO tableDAO = new TableDAO();

    public List<Table> getAllTables() {
        return tableDAO.getAll();
    }

    public void addTable(int tableNumber, int capacity) {
        if (tableNumber <= 0) {
            System.out.println("Số bàn phải lớn hơn 0");
            return;
        }
        if (capacity <= 0) {
            System.out.println("Không đủ không gian cho khách");
            return;
        }

        Table table = new Table(0, tableNumber, capacity, "FREE");
        if (tableDAO.add(table)) {
            System.out.println("Thêm bàn số " + tableNumber + " thành công");
        } else {
            System.out.println("Không thêm được bàn ");
        }
    }
    public boolean isTableFree(int tableId) {
        return tableDAO.isTableFree(tableId);
    }
}