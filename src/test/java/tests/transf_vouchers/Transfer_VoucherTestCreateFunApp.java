package tests.transf_vouchers;

import base.BaseTestApp;
import base.BaseTestWeb;
import drivers.DriverManager;
import model.Asset;
import model.Department;
import model.User;
import model.UsersRole;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.*;
import pagesweb.Assets_Page;
import utils.MyUtil;
import utils.SnackbarScreenshot;

public class Transfer_VoucherTestCreateFunApp extends BaseTestApp {
    Transfer_VoucherPageApp transf_voucher;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    Assets_Page as;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        transf_voucher = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoTransfer();
        transf_voucher.clickThemmoi();
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\ChromeDriver114\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Tools\\Chrome114\\chrome-win64\\chrome.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BaseTestWeb.LOGIN_URL);
        DriverManager.setWebDriver(driver);
    }

    @Test(priority = 1)
    public void testCreateAllocation_emptyNguoiBanGiao(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.clearNguoiBanGiao();
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.chonTS();
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn người bàn giao"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 2)
    public void testCreateAllocation_emptyPhongBanBanGiao(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.clearPhongBanBanGiao();
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn phòng bàn giao"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyPhongBanTiepNhan(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.clearPhongBanTiepNhan();
        transf.chonTS();
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn phòng tiếp nhận"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 4)
    public void testCreateAllocation_emptyTrangThaiPhieu(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.clearTrangThaiPhieu();
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        transf.chonTS();
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn trạng thái"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 5)
    public void testCreateAllocation_emptyNguoiTiepNhan(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.chonPhongBanTiepNhanInput();
        transf.clearNguoiTiepNhan();
        transf.chonTS();
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn người tiếp nhận"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 6)
    public void testCreateAllocation_emptyChonTS(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        transf.clearTS();
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Vui lòng chọn tài sản"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 7)
    public void testCreateAllocation_samePhongBan(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(2);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        transf.chonTS();
        transf.setLuu_btn();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());

        Assert.assertTrue(snackbar.verifySnackbarContainsText("Phòng ban bàn giao không được trùng với phòng\n" +
                        "ban tiếp nhận"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(transf.isTransferDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 8)
    public void testCreateAllocation_TrangThaiChoXacNhan(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(1);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(transf.chonTSCP()).getCode());
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Lưu thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách Điều chuyển
        Assert.assertFalse(transf.isTransferDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(transf_voucher.checkBanghiDieuChuyen(taisan.getCode(),1, Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Điều chuyển");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSDieuchuyen(taisan.getCode(), 1,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Phòng ban sử dụng Tài sản bị hiển thị sai");
    }

    @Test(priority = 9)
    public void testCreateAllocation_TrangThaiDaXacNhan(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(2);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(transf.chonTSCP()).getCode());
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Lưu thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách Điều chuyển
        Assert.assertFalse(transf.isTransferDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(transf_voucher.checkBanghiDieuChuyen(taisan.getCode(),2, Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Điều chuyển");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSDieuchuyen(taisan.getCode(), 2,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Phòng ban sử dụng Tài sản bị hiển thị sai");
    }

    @Test(priority = 10)
    public void testCreateAllocation_TrangThaiDaDieuChuyen(){
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        transf.chonPhongBanBanGiaoInput(1);
        transf.chonNguoiBanGiaoInput();
        transf.chonTrangThaiPhieuInput(3);
        transf.chonPhongBanTiepNhanInput();
        transf.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(transf.chonTSCP()).getCode());
        transf.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Lưu thành công"),
                "Thông báo lỗi hiển thị sai!");
        // Kiểm tra xem về trang danh sách Điều chuyển
        Assert.assertFalse(transf.isTransferDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(transf_voucher.checkBanghiDieuChuyen(taisan.getCode(),3, Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Điều chuyển");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSDieuchuyen(taisan.getCode(), 3,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Phòng ban sử dụng Tài sản bị hiển thị sai");
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        Transfer_VoucherCreatePageApp transf = new Transfer_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        Transfer_VoucherPageApp transfer_voucher = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());

        // Nếu form thêm mới bị ẩn (không còn), thì click lại "Thêm mới" để reset màn hình
        if (!transf.isTransferDialogDisplayed()) {
            transfer_voucher.clickThemmoi();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
        boolean isScrollVisible = DriverManager.getAppiumDriver().findElements(By.xpath("//android.widget.Button[contains(@content-desc, 'Chọn tài sản')]")).size() > 0;
        if(!isScrollVisible){
            DriverManager.getAppiumDriver().navigate().back();
        }}
    }
}
