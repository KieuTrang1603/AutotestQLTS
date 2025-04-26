package tests.allo_vouchers.app;

import base.BaseTestApp;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.All_VoucherCreatePageApp;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import pagesweb.DS_TSCD_Dialog;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Allocations_VoucherTestValidateApp extends BaseTestApp {
    All_VoucherPageApp all_vou;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        all_vou = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        loginPageapp.login("pvt1", "123456");
        homePageApp.navigationtoAllocation();
        all_vou.clickThemmoi();
    }
    @Test(priority = 1)
    public void testNgayTaoPhieu_ShouldBeToday_AndReadonly() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
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
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayChungTuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = all.isNgayChungTuEdit();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }

    @Test(priority = 3)
    public void testPhongBanGiaoDataAndReadonly() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
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

    @Test(priority = 4)
    public void testNguoiBanGiao(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        String department_id = "9484b376-8470-4d06-b1a0-e59179f93ca6";
        try {
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiBanGiao(displayNames),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DriverManager.getAppiumDriver().navigate().back();
    }

    @Test(priority = 5)
    public void testTrangThaiPhieu(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        Assert.assertTrue(all.checkTrangThaiPhieu(MyUtil.getTrangThaiPhieuAllocations()), "Danh sách 'Trạng thái phiếu' chưa hiển thị đúng!");
        DriverManager.getAppiumDriver().navigate().back();
    }

    @Test(priority = 6)
    public void testNguoiTiepNhanFirstly(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        boolean able = all.isNguoiTiepNhanAble();
        Assert.assertTrue(!able, "Trường 'Người tiếp nhận' không disable khi chưa chọn phòng ban giao");
    }

    @Test(priority = 7)
    public void testPhongBanTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBan();
            Assert.assertTrue(all.checkPhongBanTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DriverManager.getAppiumDriver().navigate().back();
    }

    @Test(priority = 8)
    public void testNguoiTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonPhongBanTiepNhanInput();
        String maPTN = MyUtil.getMaPhongTiepNhan(all.getPhongBanTiepNhanInput());
        try {
            String department_id = DataBaseUtils.getDepartmentIdByCode(maPTN);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(all.checkNguoiTiepNhan(displayNames),
                    "Danh sách 'Người tiếp nhận' chưa hiển thị đúng!");
            DriverManager.getAppiumDriver().navigate().back();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 9)
    public void testDanhsachTSCD(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        try {
            Assert.assertTrue(all.checkDS(),
                    "Danh sách 'TSCĐ' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        DriverManager.getAppiumDriver().navigate().back();
    }
}
