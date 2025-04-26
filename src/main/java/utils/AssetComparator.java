package utils;

import model.Asset;

import java.util.List;

public class AssetComparator {
    public static boolean compareAssetLists(List<Asset> dbList, List<Asset> uiList) {
        int size = Math.min(dbList.size(), uiList.size());

        for (int i = 0; i < size; i++) {
            Asset db = dbList.get(i);
            Asset ui = uiList.get(i);

            boolean codeMatch = db.getCode().trim().equals(ui.getCode().trim());
            boolean nameMatch = db.getName().trim().equals(ui.getName().trim());

            if (!codeMatch || !nameMatch) {
                System.out.println(" Lệch tại vị trí " + i + ":");
                System.out.println("  DB : " + db.getCode() + " | " + db.getName());
                System.out.println("  UI : " + ui.getCode() + " | " + ui.getName());
                return false;
            }
        }

        if (dbList.size() != uiList.size()) {
            System.out.println(" Kích thước danh sách khác nhau: DB(" + dbList.size() + ") vs UI(" + uiList.size() + ")");
        }
        return true;
    }
}
