package utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyUtil {
    final static String ngaychungtu = "13/04/2025";
    public static List<String> getExpectedMenusORG() {
        return Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục", "Hệ thống");
    }

    public static List<String> getExpectedMenusAM() {
        return Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục");
    }

    public static List<String> getExpectedMenusAU() {
        return Arrays.asList("Trang chủ", "Quản lý");
    }

    public static List<String> getExpectedMenusUser() {
        return Arrays.asList("Trang chủ", "Danh sách TSCĐ", "Danh sách CCDC", "Quản lý");
    }

    public static List<String> normalizeList(List<String> list) {
        return list.stream()
                .map(s -> s.trim())        // Bỏ khoảng trắng đầu/cuối
                .collect(Collectors.toList());
    }

    public static List<String> getButtonORG() {
        return Arrays.asList("Cấp phát TSCĐ", "Nhập Excel", "Xuất Excel", "Tìm kiếm nâng cao");
    }

    public static List<String> getButtonAU() {
        return Arrays.asList("Xuất Excel", "Tìm kiếm nâng cao");
    }

    public static List<String> getTrangThaiPhieuAllocations() {
        return Arrays.asList("Mới tạo", "Đã cấp phát", "Chờ tiếp nhận");
    }

    public static String getMaPhongTiepNhan(String department){
        String[] parts = department.split(" - ");
        String result = parts[1].trim();
        return result;
    }

    public static String getNgaychungtu(){
        return ngaychungtu;
    }

    public static String getSobangi(String text){
        String[] parts = text.split("trong");
        String totalRecords = parts[1].trim();
        return totalRecords;
    }

    public static int convertToInt(String numberString) {
        try {
            return Integer.parseInt(numberString.trim());
        } catch (NumberFormatException e) {
            System.err.println("Không thể chuyển đổi chuỗi thành số: " + numberString);
            return -1; // hoặc throw, hoặc return null nếu bạn sửa sang Integer
        }
    }
}
