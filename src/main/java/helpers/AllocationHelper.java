package helpers;

import utils.DataBaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AllocationHelper {
    public static final String department_id = "9484b376-8470-4d06-b1a0-e59179f93ca6";
    public static final String status_allocations = "71b6b0a1-d225-44db-95c5-b00a47c4789b";

    public static List<String> prepareEmptyNgayCapPhatData() throws IOException, SQLException, ParseException {
        // Lấy mã phòng ban
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);

        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getAssetsAvailable(department_id, status_allocations);

        // Chỉnh sửa dữ liệu theo test case
        if (taisan.size() > 1) {
            taisan.set(1, "");  // Xóa dữ liệu ngày cấp phát
        }
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }

        return taisan;
    }

    public static List<String> prepareEmptyMaTaiSanData() throws IOException, SQLException, ParseException {
        // Lấy mã phòng ban
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getAssetsAvailable(department_id, status_allocations);
        // Chỉnh sửa dữ liệu theo test case
        if (taisan.size() > 1) {
            taisan.set(2, "");  // Xóa dữ liệu ngày cấp phát
        }
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        return taisan;
    }

    public static List<String> prepareEmptyMaKhoData() throws IOException, SQLException, ParseException {
        // Lấy mã phòng ban
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getAssetsAvailable(department_id, status_allocations);
        // Chỉnh sửa dữ liệu theo test case
        if (taisan.size() > 1) {
            taisan.set(3, "");  // Xóa dữ liệu ngày cấp phát
        }
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        return taisan;
    }

    public static List<String> prepareEmptyMaPBTNData() throws IOException, SQLException, ParseException {
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getAssetsAvailable(department_id, status_allocations);
        return taisan;
    }
}
