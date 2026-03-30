package util;

public class DataValidator {
        // Kiểm tra chuỗi trống
        public static boolean isStringEmpty(String input) {
            return input == null || input.trim().isEmpty();
        }

        // Kiểm tra số dương
        public static boolean isPositive(double value) {
            return value > 0;

    }
}
