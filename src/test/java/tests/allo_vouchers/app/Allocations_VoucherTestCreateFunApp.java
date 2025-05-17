package tests.allo_vouchers.app;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.All_VoucherCreatePageApp;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.Assets_Page;
import utils.MyUtil;
import utils.SnackbarScreenshot;

public class Allocations_VoucherTestCreateFunApp extends BaseTestApp {
    All_VoucherPageApp all_vou;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    Assets_Page as;
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
        all.chonPhongBanTiepNhanInputCP();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(all.chonTSCP()).getCode());
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(all_vou.checkBanghiCapphat(taisan.getCode(),1,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Cấp phát");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 1, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @Test(priority = 7)
    public void testCreateAllocation_TrangThaiChoTiepNhan(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(3);
        all.chonPhongBanTiepNhanInputCP();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(all.chonTSCP()).getCode());
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(all_vou.checkBanghiCapphat(taisan.getCode(),2,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Cấp phát");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 2, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @Test(priority = 8)
    public void testCreateAllocation_TrangThaiDaCapPhat(){
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        all.chonNguoiBanGiaoInput();
        all.chonTrangThaiPhieuInput(2);
        all.chonPhongBanTiepNhanInputCP();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(MyUtil.parseAsset(all.chonTSCP()).getCode());
        all.setLuu_btn();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(all_vou.checkBanghiCapphat(taisan.getCode(),3,Department.DEPARTMENT_NAME_AM, Department.DEPARTMENT_NAME_AU1),
                "Chưa hiển thị bản ghi Cấp phát");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 3, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
        // Kiểm tra tài sản ở màn danh sách role AU
        as= new Assets_Page(DriverManager.getWebDriver());
        user = UsersRole.getUserByRole("AU");
        as.navigateToAssetsPagetoLogin(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 3, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        All_VoucherPageApp allVoucherPage = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        if (allVoucherPage.isAllocatonDialogDisplayed()) {
            allVoucherPage.clickThemmoi();
        } else {
        boolean isScrollVisible = DriverManager.getAppiumDriver().findElements(By.xpath("//android.widget.Button[contains(@content-desc, 'Chọn tài sản cấp phát')]")).size() > 0;
        if(!isScrollVisible){
            DriverManager.getAppiumDriver().navigate().back();
        }}
    }
    @AfterClass
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getWebDriver();
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
