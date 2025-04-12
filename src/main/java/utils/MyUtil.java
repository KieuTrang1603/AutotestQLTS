package utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyUtil {
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
//                .map(s -> s.replaceAll("\\s+", " ")) // Bỏ khoảng trắng thừa trong giữa chuỗi
                .collect(Collectors.toList());
    }
}
