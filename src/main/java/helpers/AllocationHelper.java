package helpers;

import model.Allocation;
import model.Department;
import model.enums.AllocationStatus;
import model.enums.AssetStatus;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AllocationHelper {
    public static AllocationStatus all_status = AllocationStatus.WAIT_RECEPT;

    public static List<String> prepareEmptyData(int a) throws IOException, SQLException, ParseException {
        // Lấy mã phòng ban
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);

        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);

        // Chỉnh sửa dữ liệu theo test case
        if (taisan.size() > 1) {
            taisan.set(a, "");  // Xóa dữ liệu ngày cấp phát
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
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        return taisan;
    }

    public static List<String> isCorrectMaPBTNData() throws IOException, SQLException, ParseException {
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        if (taisan.size() > 4) {
            taisan.add(4, "29/02/2025");  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(4, "29/02/2025"); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        return taisan;
    }

    public static List<String> isCorrectData(int a) throws IOException, SQLException, ParseException {
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        // Đảm bảo list có đủ dữ liệu đến ít nhất index = 3 để set phòng ban
        while (taisan.size() <= 4) {
            taisan.add("");
        }

        // Set lại phòng ban tại index = 3 (Mã phòng ban tiếp nhận)
        taisan.set(4, maPBTN);

        // Gán giá trị sai tại cột bạn mong muốn (columnIndex)
        if (taisan.size() > a) {
            taisan.set(a, "29/02/2025");
        } else {
            while (taisan.size() < a) {
                taisan.add(""); // thêm phần tử trống cho đủ
            }
            taisan.add("29/02/2025"); // thêm phần tử sai đúng vị trí
        }
        return taisan;
    }

    public static List<String> isCorrectNguoiDungData() throws IOException, SQLException, ParseException {
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        // Chỉnh sửa dữ liệu theo test case
        String user = DataBaseUtils.getUserNonDepartment(maPBTN);
        if (taisan.size() > 5) {
            taisan.add(5, user);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(user); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        return taisan;
    }

    public static List<String> isCorrectNgayCapPhatData() throws IOException, SQLException, ParseException {
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        // Chỉnh sửa dữ liệu theo test case
        taisan.set(1, MyUtil.subtractOneDayFromDate(taisan.get(1)));
        return taisan;
    }

    public static List<String> CorrectData() throws IOException, SQLException, ParseException {
        String maPBTN = DataBaseUtils.getCodePhongBan().get(0);
        // Lấy danh sách tài sản
        List<String> taisan = DataBaseUtils.getOneAssetsAvailable(Department.DEPARTMENT_ID_AM, all_status.getCode(), true);
        if (taisan.size() > 4) {
            taisan.add(4, maPBTN);  // Thêm mã phòng ban tại vị trí cụ thể
        } else {
            taisan.add(maPBTN); // Thêm cuối nếu chưa đủ 4 phần tử
        }
        return taisan;
    }

    public static List<Allocation> getAllocationMobile(List<String> list){
        List<Allocation> mobileRecords;
        mobileRecords = list.stream()
                .map(Allocation::new)
                .collect(Collectors.toList());
        System.out.println(mobileRecords);
        return mobileRecords;
    }

    public static boolean soSanhCapPhatGiuaAppVaWeb(List<String> list, List<Allocation> list1){
        boolean match = false;
        List<Allocation> list2 = getAllocationMobile(list);
        for (Allocation pApp : list2) {
            match = list1.stream().anyMatch(pWeb ->
                    pWeb.getNgay().equals(pApp.getNgay()) &&
                            pWeb.getTrangThai().equalsIgnoreCase(pApp.getTrangThai()) &&
                            pWeb.getPhongGiao().equalsIgnoreCase(pApp.getPhongGiao()) &&
                            pWeb.getNguoiGiao().equalsIgnoreCase(pApp.getNguoiGiao()) &&
                            pWeb.getPhongNhan().equalsIgnoreCase(pApp.getPhongNhan()) &&
                            pWeb.getNguoiNhan().equalsIgnoreCase(pApp.getNguoiNhan())
            );
            System.out.println(pApp);
        }
        return match;
    }

}
