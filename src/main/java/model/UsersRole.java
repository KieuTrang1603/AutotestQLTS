package model;

import java.util.HashMap;
import java.util.Map;

public class UsersRole {
    private static Map<String, User> userMap = new HashMap<>();

    static {
        // Khởi tạo thông tin user mặc định cho từng role
        userMap.put("AM", new User("pvt1", "123456"));    // Phòng vật tư
        userMap.put("ORG", new User("bvdka", "123456"));      // Kế toán
        userMap.put("AU", new User("phs21", "123456")); // Admin
        userMap.put("USER", new User("userkn", "123456"));      // Thủ kho
        // ... thêm role khác tùy bạn
    }

    // Hàm để lấy User theo role
    public static User getUserByRole(String role) {
        return userMap.get(role);
    }
}
