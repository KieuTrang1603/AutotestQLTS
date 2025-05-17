package tests.transf_vouchers;

import base.BaseTestApp;
import base.BaseTestWeb;
import drivers.DriverManager;
import helpers.AllocationHelper;
import model.Allocation;
import model.User;
import model.UsersRole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesapp.Transfer_VoucherPageApp;
import pagesweb.Transfer_VoucherPageWeb;

import java.util.List;

public class Transfer_VoucherTestUIApp extends BaseTestApp {
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    Transfer_VoucherPageApp transf_app;
    Transfer_VoucherPageWeb transf_web;
    UsersRole Users;
    User user;

    @BeforeMethod
    public void creatsetup(){
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\ChromeDriver114\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Tools\\Chrome114\\chrome-win64\\chrome.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BaseTestWeb.LOGIN_URL);
        DriverManager.setWebDriver(driver);
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebORG() {
        user = Users.getUserByRole("ORG");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoTransfer();
        transf_app = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());
        transf_web = new Transfer_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = transf_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        transf_web.navigateToTransfer_VoucherPage(user.getUsername(), user.getPassword());
        transf_web.closeMenu();
        List<Allocation> webRecords = transf_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAM() {
        user = Users.getUserByRole("AM");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoTransfer();
        transf_app = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());
        transf_web = new Transfer_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = transf_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        transf_web.navigateToTransfer_VoucherPage(user.getUsername(), user.getPassword());
        transf_web.closeMenu();
        List<Allocation> webRecords = transf_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAU() {
        user = Users.getUserByRole("AU");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoTransfer();
        transf_app = new Transfer_VoucherPageApp(DriverManager.getAppiumDriver());
        transf_web = new Transfer_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = transf_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        transf_web.navigateToTransfer_VoucherPage(user.getUsername(), user.getPassword());
        transf_web.closeMenu();
        List<Allocation> webRecords = transf_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @AfterMethod
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getWebDriver();
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
            transf_app.returnHomeApp();
            homePageApp.setLogOut(user.getRole());
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
