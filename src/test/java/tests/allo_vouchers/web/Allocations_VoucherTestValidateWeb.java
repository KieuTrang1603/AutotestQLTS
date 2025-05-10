package tests.allo_vouchers.web;

import model.Department;
import model.User;
import model.UsersRole;
import model.enums.AllocationStatus;
import pagesweb.DS_TSCD_Dialog;
import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Allocations_VoucherTestValidateWeb extends BaseTestWeb {
    All_VoucherPageWeb all_vou;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(),user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
    }
    @Test(priority = 1)
    public void testNgayTaoPhieu_ShouldBeToday_AndReadonly() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày tạo phiếu không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isReadonly = all.isNgayTaoPhieuReadonly();
        Assert.assertTrue(isReadonly, "Trường 'Ngày tạo phiếu' không bị readonly!");
    }

    @Test(priority = 2)
    public void testNgayChungTu_ShouldBeToday_AndEdit() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayChungTu();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = all.isNgayChungTuEditable();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }

    @Test(priority = 3)
    public void testNhapNgayKhongHopLe(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.setNgayChungTuInput("29/02/2025");
        // Kiểm tra trường có bị đánh dấu là không hợp lệ không
        Assert.assertTrue(all.isNgayChungTuKhongHopLe(), "Không báo lỗi khi nhập ngày không hợp lệ");
    }

    @Test(priority = 4)
    public void testPhongBanGiaoDataAndReadonly() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        try {
            String departmentName = DataBaseUtils.getDepartmentNameByCode(Department.DEPARTMENT_CODE_AM);
            System.out.println("Data from database: " + departmentName);
            Assert.assertTrue(all.checkPhongBanBanGiao(departmentName),
                    "Trường 'Phòng ban bàn giao' không hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 5)
    public void testNguoiBanGiao(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        try {
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(Department.DEPARTMENT_ID_AM);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiBanGiao(displayNames),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 6)
    public void testTrangThaiPhieu(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        Assert.assertTrue(all.checkTrangThaiPhieu(MyUtil.getTrangThaiPhieuAllocations()), "Danh sách 'Trạng thái phiếu' chưa hiển thị đúng!");
    }

    @Test(priority = 8)
    public void testPhongBanTiepNhan(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBan();
            Assert.assertTrue(all.checkPhongBanTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 7)
    public void testNguoiTiepNhanFirstly(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        boolean able = all.isNguoiTiepNhanAble();
        Assert.assertTrue(!able, "Trường 'Người tiếp nhận' không disable khi chưa chọn phòng ban giao");
    }

    @Test(priority = 9)
    public void testNguoiTiepNhan(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.openDropdownPhongBanTiepNhan();
        all.chonPhongBanTiepNhanInput();
        String maPTN = MyUtil.getMaPhong(all.getPhongBanTiepNhanInput());
        try {
            String department_id = DataBaseUtils.getDepartmentIdByCode(maPTN);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiTiepNhan(displayNames),
                    "Danh sách 'Người tiếp nhận' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 10)
    public void testDanhsachTSCD(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        DS_TSCD_Dialog ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        ds.getPaginationText();
        try {
            Integer sobanghi = DataBaseUtils.countAssetsAvailable(Department.DEPARTMENT_ID_AM, AllocationStatus.WAIT_RECEPT.getCode());
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(ds.checkSobanghi(sobanghi),
                    "Danh sách 'TSCĐ' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
