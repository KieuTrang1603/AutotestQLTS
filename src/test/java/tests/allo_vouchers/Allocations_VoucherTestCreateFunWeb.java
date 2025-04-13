package tests.allo_vouchers;

import Dialogs.DS_TSCD_Dialog;
import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.DataBaseUtils.getConnection;

public class Allocations_VoucherTestCreateFunWeb extends BaseTestWeb {
    All_VoucherPageWeb all_vou;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("pvt1", "123456");
        all_vou.All_Btn_click();
    }
    @Test
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

    @Test
    public void testNgayChungTu_ShouldBeToday_AndEdit() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = all.isNgayChungTuEditable();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }

    @Test
    public void testNhapNgayKhongHopLe(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.setNgayChungTuInput("29/02/2025");
        // Kiểm tra trường có bị đánh dấu là không hợp lệ không
        Assert.assertTrue(all.isNgayChungTuKhongHopLe(), "Không báo lỗi khi nhập ngày không hợp lệ");
    }

    @Test
    public void testPhongBanGiao() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        String maPBG= "1.8.PVT";
        try {
            String departmentName = DataBaseUtils.getDepartmentNameByCode(maPBG);
                System.out.println("Data from database: " + departmentName);
                Assert.assertTrue(all.checkPhongBanBanGiao(departmentName),
                        "Trường 'Phòng ban bàn giao' không hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNguoiBanGiao(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        String department_id = "9484b376-8470-4d06-b1a0-e59179f93ca6";
        try {
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiBanGiao(displayNames),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTrangThaiPhieu(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        Assert.assertTrue(all.checkTrangThaiPhieu(MyUtil.getTrangThaiPhieuAllocations()), "Danh sách 'Trạng thái phiếu' chưa hiển thị đúng!");
    }

    @Test
    public void testPhongBanTiepNhan(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        try {
            List<String> expectedDepartments = DataBaseUtils.getPhongBan();
            Assert.assertTrue(all.checkPhongBanTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test()
    public void testNguoiTiepNhanFirstly(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        boolean able = all.isNguoiTiepNhanAble();
        Assert.assertTrue(!able, "Trường 'Người tiếp nhận' không disable khi chưa chọn phòng ban giao");
    }

    @Test(dependsOnMethods = "testNguoiTiepNhanFirstly")
    public void testNguoiTiepNhan(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.chonPhongBanTiepNhanInput();
        String maPTN = MyUtil.getMaPhongTiepNhan(all.getPhongBanTiepNhanInput());
        try {
            String department_id = DataBaseUtils.getDepartmentIdByCode(maPTN);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiTiepNhan(displayNames),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDanhsachTSCD(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.openDialogChonTS();
        String department_id = "9484b376-8470-4d06-b1a0-e59179f93ca6";
        String status_allocations = "71b6b0a1-d225-44db-95c5-b00a47c4789b";
        try {
            Integer sobanghi = DataBaseUtils.countAssetsAvailable(department_id, status_allocations);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all.checkSobanghi(sobanghi),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
