package tests.allo_vouchers.app;

import base.BaseTestApp;
import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.All_VoucherCreatePageApp;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import pagesweb.DS_TSCD_Dialog;
import utils.MyUtil;
import utils.SnackbarScreenshot;

public class Allocations_VoucherTestCreateFunApp extends BaseTestApp {
    All_VoucherPageApp all_vou;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        all_vou = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoAllocation();
        all_vou.clickThemmoi();
    }

    @Test(priority = 1)
    public void testCreateAllocation_emptyNguoiBanGiao(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.clearNguoiBanGiao();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.chonTrangThaiPhieuInput(1);
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Chưa chọn người bàn giao"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 2)
    public void testCreateAllocation_emptyPhongBanTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(1);
        all.clearPhongBanTiepNhan();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Chưa chọn phòng ban tiếp nhận"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyTrangThaiPhieu(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.clearTrangThaiPhieu();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Chưa chọn trạng thái phiếu"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyNguoiTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(1);
        all.chonPhongBanTiepNhanInput();
        all.clearNguoiTiepNhan();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Chưa chọn người tiếp nhận"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 5)
    public void testCreateAllocation_emptyChonTS(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(1);
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.clearTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Chưa chọn tài sản cấp phát"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 6)
    public void testCreateAllocation_TrangThaiMoiTao(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(1);
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Cấp phát thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }

    @Test(priority = 7)
    public void testCreateAllocation_TrangThaiChoTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(3);
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Cấp phát thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }

    @Test(priority = 8)
    public void testCreateAllocation_TrangThaiDaCapPhat(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(2);
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.chonTS();
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Cấp phát thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        All_VoucherPageApp allVoucherPage = new All_VoucherPageApp(DriverManager.getAppiumDriver());

        // Nếu form thêm mới bị ẩn (không còn), thì click lại "Thêm mới" để reset màn hình
        if (!all.isAllocatonDialogDisplayed()) {
            allVoucherPage.clickThemmoi();
        }
    }
}
