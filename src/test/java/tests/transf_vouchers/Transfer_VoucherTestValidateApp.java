package tests.transf_vouchers;

import base.BaseTestApp;
import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.*;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Transfer_VoucherTestValidateApp extends BaseTestApp {
    Transfer_VoucherPageApp trasf;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    UsersRole Users;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        trasf = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());
        User user = Users.getUserByRole("AM");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoTransfer();
        trasf.clickThemmoi();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test(priority = 1)
    public void testNgayTaoPhieu_ShouldBeToday_AndReadonly() {
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = transfer_vou.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày tạo phiếu không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isReadonly = transfer_vou.isNgayTaoPhieuReadonly();
        Assert.assertTrue(isReadonly, "Trường 'Ngày tạo phiếu' không bị readonly!");
    }

    @Test(priority = 2)
    public void testNgayChungTu_ShouldBeToday_AndEdit() {
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = transfer_vou.getNgayChungTuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = transfer_vou.isNgayChungTuEdit();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }

    @Test(priority = 3)
    public void testTrangThaiPhieu(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        Assert.assertTrue(transfer_vou.checkTrangThaiPhieu(MyUtil.getTrangThaiPhieuTransfers()), "Danh sách 'Trạng thái phiếu' chưa hiển thị đúng!");
    }

    @Test(priority = 4)
    public void testPhongBanGiaoDataAndEdit() {
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        String maPBG= "1.8.PVT";
        try {
            String departmentName = DataBaseUtils.getDepartmentNameByCode(maPBG);
            System.out.println("Data from database: " + departmentName);
            Assert.assertTrue(transfer_vou.checkPhongBanBanGiaoFirstly(departmentName),
                    "Trường 'Phòng ban bàn giao' không hiển thị đúng!");
            boolean able = transfer_vou.isPhongBanGiaoAble();
            Assert.assertTrue(able, "Trường 'Phòng bàn giao' bị disable");
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBan();
            Assert.assertTrue(transfer_vou.checkPhongBanBanGiao(expectedDepartments),
                    "Danh sách 'Phòng ban bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 5)
    public void testNguoiBanGiaoClearPBBG(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transfer_vou.clearPhongBanBanGiao();
        boolean able = transfer_vou.isNguoiBanGiaoAble();
        Assert.assertTrue(!able, "Trường 'Người tiếp nhận' không disable khi chưa chọn phòng ban giao");
    }


    @Test(priority = 6)
    public void testNguoiTiepNhanFirstly(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        boolean able = transfer_vou.isNguoiTiepNhanAble();
        Assert.assertTrue(!able, "Trường 'Người tiếp nhận' không disable khi chưa chọn phòng ban giao");
    }

    @Test(priority = 7)
    public void testNguoiBanGiao(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transfer_vou.chonPhongBanBanGiaoInput(2);
        String tenPTN = transfer_vou.getPhongBanBanGiao();
        try {
            String department_id = DataBaseUtils.getDepartmentIdByTen(tenPTN);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(transfer_vou.checkNguoiBanGiao(displayNames),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 8)
    public void testPhongBanTiepNhan(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBan();
            Assert.assertTrue(transfer_vou.checkPhongBanTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 9)
    public void testNguoiTiepNhan(){
        Transfer_VoucherCreatePageApp transfer_vou = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transfer_vou.chonPhongBanTiepNhanInput();
        String tenPTN = transfer_vou.getPhongBanTiepNhan();
        try {
            String department_id = DataBaseUtils.getDepartmentIdByTen(tenPTN);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(transfer_vou.checkNguoiTiepNhan(displayNames),
                    "Danh sách 'Người tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 10)
    public void testDanhsachTSCD() throws SQLException {
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(2);
        String tenPTN = transf.getPhongBanBanGiao();
        String department_id = DataBaseUtils.getDepartmentIdByTen(tenPTN);
        try {
            Assert.assertTrue(transf.checkDS(department_id),
                    "Danh sách 'TSCĐ' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 11)
    public void testDanhsachTSCDAMkhacPBBG_AU() throws SQLException {
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(2);
        try {
            Assert.assertTrue(transf.checkDSHanhChinh(),
                    "Danh sách 'TSCĐ' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        DriverManager.getAppiumDriver().navigate().back();
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        Transfer_VoucherPageApp transfVoucherPage = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());

        // Nếu form thêm mới bị ẩn (không còn), thì click lại "Thêm mới" để reset màn hình
        if (!transf.isTransferDialogDisplayed()) {
            transfVoucherPage.clickThemmoi();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
