package model;

import java.util.HashMap;
import java.util.Map;

public class UsersRole {
    private static Map<String, User> userMap = new HashMap<>();

    static {
        // Khởi tạo thông tin user mặc định cho từng role
        userMap.put("AM", new User("pvt1", "123456",1));    // Phòng vật tư
        userMap.put("ORG", new User("bvdka", "123456",0));      // Admin
        userMap.put("AU", new User("audemo", "123123",2)); // Phòng sử dụng
        userMap.put("USER", new User("userkn", "123456",3));      // Người dùng
        // ... thêm role khác tùy bạn

//        //link qltsdemo
//        userMap.put("AM", new User("trangptt", "123456",1));    // Phòng vật tư
//        userMap.put("ORG", new User("bv199", "bv199@123",0));      // Admin
//        userMap.put("AU", new User("khoagmhs", "123456",2)); // Phòng sử dụng
//        userMap.put("USER", new User("testuser", "123456",3));      // Người dùng
    }

    // Hàm để lấy User theo role
    public static User getUserByRole(String role) {
        return userMap.get(role);
    }
}
