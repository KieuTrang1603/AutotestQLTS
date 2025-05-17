package model;

import model.enums.PlatformType;
import model.enums.UserRole;

import java.util.*;

public class AllocationMenu {
    private static final Map<UserRole, Map<PlatformType, List<String>>> roleMenus = new HashMap<>();

    static {
        // ORG
        roleMenus.put(UserRole.ORG, Map.of(
                PlatformType.WEB, Arrays.asList("Thêm mới", "Nhập Excel", "Xuất Excel", "TK nâng cao")
        ));

        // AM
        roleMenus.put(UserRole.AM, Map.of(
                PlatformType.WEB, Arrays.asList("Thêm mới", "Nhập Excel", "Xuất Excel", "TK nâng cao")
        ));

        // AU
        roleMenus.put(UserRole.AU, Map.of(
                PlatformType.WEB, Arrays.asList("Xuất Excel", "TK nâng cao")
        ));
    }

    public static List<String> getExpectedMenus(UserRole role, PlatformType platform) {
        return roleMenus
                .getOrDefault(role, Collections.emptyMap())
                .getOrDefault(platform, Collections.emptyList());
    }
}
