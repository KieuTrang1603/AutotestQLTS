package model;

import model.enums.PlatformType;
import model.enums.UserRole;

import java.util.*;

public class HomeMenu {
    private static final Map<UserRole, Map<PlatformType, List<String>>> roleMenus = new HashMap<>();

    static {
        // ORG
        roleMenus.put(UserRole.ORG, Map.of(
                PlatformType.WEB, Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục", "Hệ thống"),
                PlatformType.APP, Arrays.asList("Kiểm kê", "Sửa chữa", "Cấp phát", "Điều chuyển", "Thanh lý", "Chuyển đi", "Thu hồi", "Bảo trì", "In QR", "Ghi chú")
        ));

        // AM
        roleMenus.put(UserRole.AM, Map.of(
                PlatformType.WEB, Arrays.asList("Trang chủ", "Yêu cầu trình duyệt", "Quản lý", "Báo cáo tổng hợp", "Danh mục"),
                PlatformType.APP, Arrays.asList("Kiểm kê", "Sửa chữa", "Cấp phát", "Điều chuyển", "Thanh lý", "Chuyển đi", "Thu hồi", "Bảo trì", "In QR", "Ghi chú")
        ));

        // AU
        roleMenus.put(UserRole.AU, Map.of(
                PlatformType.WEB, Arrays.asList("Trang chủ", "Quản lý"),
                PlatformType.APP, Arrays.asList("Sửa chữa", "Cấp phát", "Điều chuyển","Thu hồi", "Bảo trì", "In QR", "Ghi chú","DS tài sản" )
        ));

        // AU
        roleMenus.put(UserRole.USER, Map.of(
                PlatformType.WEB, Arrays.asList("Trang chủ","Danh sách TSCĐ", "Danh sách CCDC", "Quản lý"),
                PlatformType.APP, Arrays.asList("In QR", "DS tài sản")
        ));
    }

    public static List<String> getExpectedMenus(UserRole role, PlatformType platform) {
        return roleMenus
                .getOrDefault(role, Collections.emptyMap())
                .getOrDefault(platform, Collections.emptyList());
    }
}
