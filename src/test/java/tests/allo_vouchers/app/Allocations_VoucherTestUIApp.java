package tests.allo_vouchers.app;

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
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.All_VoucherPageWeb;

import java.util.List;

public class Allocations_VoucherTestUIApp extends BaseTestApp {
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    All_VoucherPageApp all_app;
    All_VoucherPageWeb all_web;
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
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAM() {
        user = Users.getUserByRole("AM");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAU() {
        user = Users.getUserByRole("AU");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
    }

    @AfterMethod
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getWebDriver();
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
            all_app.returnHomeApp();
            homePageApp.setLogOut(user.getRole());
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
